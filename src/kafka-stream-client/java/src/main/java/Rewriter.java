import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Consumed;


public class Rewriter {
    public static void main (String [] args) {
        try {
            Properties config = new Properties ();
            config.put (StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Shared.KAFKA_BOOTSTRAP_ADDRESS);
            config.put (StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer ().getClass ().getName ());
            config.put (StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String ().getClass ().getName ());
            config.put (StreamsConfig.APPLICATION_ID_CONFIG, "Processor");

            StreamsBuilder builder = new StreamsBuilder ();

            builder
                // Consume a stream from the input topic.
                // Consumed helper used to inject types into the expression.
                .stream (Shared.KAFKA_PRODUCER_TOPIC, Consumed.with (Serdes.Integer (), Serdes.String ()))
                // Map each value into revese value.
                .mapValues (value -> new StringBuilder (value).reverse ().toString ())
                // Produce the output topic.
                .to (Shared.KAFKA_CONSUMER_TOPIC);

            KafkaStreams processor = new KafkaStreams (builder.build (), config);

            processor.start ();

            // Just prevent the main thread from exitting.
            while (true) Thread.sleep (1000);

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
