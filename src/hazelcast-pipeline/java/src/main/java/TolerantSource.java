import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.config.ProcessingGuarantee;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.SourceBuilder;
import com.hazelcast.jet.pipeline.StreamSource;

import java.util.List;

public class TolerantSource {

    private static final class SourceContext {

        private static final String [] POEM = {
                "Twas brillig, and the slithy toves",
                "Did gyre and gimble in the wabe,",
                "All mimsy were the borogoves,",
                "And the mome raths outgrabe.",
                "Beware the Jabberwock, my son!",
                "The jaws that bite, the claws that catch!",
                "Beware the Jubjub bird, and shun",
                "The frumious Bandersnatch!"
        };

        private long lastCount = 0;
        private long lastTimestamp = 0;

        void fillBuffer (SourceBuilder.SourceBuffer<String> buffer) {
            long thisTimestamp = System.nanoTime ();
            if (lastTimestamp == 0) lastTimestamp = thisTimestamp;

            while (thisTimestamp > lastTimestamp) {
                buffer.add (String.format ("%d: %s", lastCount, POEM [(int) (lastCount % POEM.length)]));
                lastTimestamp += 1_000_000_000;
                lastCount++;
            }
        }

        long createSnapshot () {
            // Remember the last count emitted.
            return lastCount;
        }

        void restoreSnapshot (List<Long> snapshots) {
            // Recover the last count emitted from the only snapshot element.
            // Distributed snapshots would have multiple elements.
            lastCount = snapshots.get (0);
            lastTimestamp = 0;
        }
    }

    private static StreamSource<String> tolerantStream () {
        return SourceBuilder
            .stream ("tolerantStream", context -> new SourceContext ())
            .<String>fillBufferFn (SourceContext::fillBuffer)
            .createSnapshotFn (SourceContext::createSnapshot)
            .restoreSnapshotFn (SourceContext::restoreSnapshot)
            .build ();
    }

    public static void main (String [] arguments) {
        try {
            Config config = new Config ();
            config.getJetConfig ().setEnabled (true);
            HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance (config);

            Pipeline pipeline = Pipeline.create ();
            pipeline
                .readFrom (tolerantStream ())
                .withIngestionTimestamps ()
                .map ((item) -> item.toUpperCase ())
                .writeTo (Sinks.logger ());

            JobConfig job = new JobConfig ()
                    .setName ("tolerant-stream-source")
                    .setSnapshotIntervalMillis (10_000)
                    .setProcessingGuarantee (ProcessingGuarantee.EXACTLY_ONCE);

            hazelcast.getJet ().newJob (pipeline, job);
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
