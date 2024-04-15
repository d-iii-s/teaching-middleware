import java.time.Duration;
import java.nio.file.Files;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Suppressed;


public class Counter {
    public static void main (String [] args) {
        try {
            Properties config = new Properties ();
            config.put (StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Shared.KAFKA_BOOTSTRAP_ADDRESS);
            config.put (StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer ().getClass ().getName ());
            config.put (StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String ().getClass ().getName ());
            config.put (StreamsConfig.APPLICATION_ID_CONFIG, "Counter");

            // Disable output record cache to avoid compacting the changelog records.
            config.put (StreamsConfig.STATESTORE_CACHE_MAX_BYTES_CONFIG, 0);

            // Use unique state directory to permit demonstrating multiple instances locally.
            config.put (StreamsConfig.STATE_DIR_CONFIG, Files.createTempDirectory (null).toString ());

            StreamsBuilder builder = new StreamsBuilder ();

            builder
                // Consume a stream from the input topic.
                // Consumed helper used to inject types into the expression.
                // Output: KStream (1: Foo, 2: Bar ...)
                .stream (Shared.KAFKA_PRODUCER_TOPIC, Consumed.with (Serdes.Integer (), Serdes.String ()))
                // Map each letter into value.
                // Output: KStream (1: F, 1: o, 1: o, 2: B, 2: a, 2: r ...)
                .flatMapValues (value -> value.chars ().mapToObj (code -> Character.toString ((char) code)).collect (Collectors.toList ()))
                // Group records by values.
                // This creates intermediate representation before subsequent aggregation.
                // Output: KGroupedStream (F: (F), o: (o, o), B: (B), a: (a), r: (r) ...)
                .groupBy ((key, value) -> value, Grouped.with (Serdes.String (), Serdes.String ()))
                // Count records in groups.
                // Output: KTable (F: 1, o: 2, B: 1, a: 1, r: 1 ...) with default changelog granularity
                .count ()
                // Aggregate updates.
                // Output: KTable (F: 1, o: 2, B: 1, a: 1, r: 1 ...) with updated changelog granularity
                .suppress (Suppressed.untilTimeLimit (Duration.ofSeconds (11), Suppressed.BufferConfig.unbounded ()))
                // Stream aggregated updates.
                // Output: KStream (F: 1, o: 2, B: 1, a: 1, r: 1 ...)
                .toStream ()
                // Produce the counts topic.
                .to (Shared.KAFKA_COUNTS_TOPIC, Produced.with (Serdes.String (), Serdes.Long ()));

            // Print the processor topology.
            Topology topology = builder.build ();
            System.out.println (topology.describe ());

            // Execute the processor.
            KafkaStreams processor = new KafkaStreams (topology, config);
            processor.start ();

            // Prevent termination when executing from Maven.
            synchronized (Thread.currentThread ()) {
                Thread.currentThread ().wait ();
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
