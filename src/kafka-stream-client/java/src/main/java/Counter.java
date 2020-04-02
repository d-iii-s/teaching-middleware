import java.time.Duration;
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

            StreamsBuilder builder = new StreamsBuilder ();

            builder
                // Consume a stream from the input topic.
                // Consumed helper used to inject types into the expression.
                .stream (Shared.KAFKA_PRODUCER_TOPIC, Consumed.with (Serdes.Integer (), Serdes.String ()))
                // Map each letter into value.
                // The result type is KStream.
                .flatMapValues (value -> value.chars ().mapToObj (code -> Character.toString ((char) code)).collect (Collectors.toList ()))
                // Group records by values.
                // The result type is KGroupedStream.
                .groupBy ((key, value) -> value, Grouped.with (Serdes.String (), Serdes.String ()))
                // Count records in groups.
                // The result type is KTable.
                .count ()
                // Suppress frequent updates.
                // The result type is KTable.
                .suppress (Suppressed.untilTimeLimit (Duration.ofSeconds (11), Suppressed.BufferConfig.unbounded ()))
                // Stream remaining updates.
                // The result type is KStream.
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
