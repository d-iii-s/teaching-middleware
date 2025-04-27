import java.util.Arrays;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.config.Config;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.test.TestSources;

public class Peer {

    public static void main (String [] arguments) {
        try {
            Config config = new Config ();
            config.getJetConfig ().setEnabled (true);
            HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance (config);

            String [] poem = {
                "Twas brillig, and the slithy toves",
                "Did gyre and gimble in the wabe,",
                "All mimsy were the borogoves,",
                "And the mome raths outgrabe.",
                "Beware the Jabberwock, my son!",
                "The jaws that bite, the claws that catch!",
                "Beware the Jubjub bird, and shun",
                "The frumious Bandersnatch!"
            };

            Pipeline pipeline = Pipeline.create ();
            pipeline
                .readFrom (TestSources.itemsDistributed (Arrays.asList (poem)))
                .map ((item) -> item.toUpperCase ())
                .writeTo (Sinks.logger ());

            hazelcast.getJet ().newJob (pipeline);
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
