import java.time.Duration;
import java.util.Map;
import java.util.TreeMap;
import java.util.Properties;
import java.util.Collections;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class Monitor {
    public static void main (String [] args) {
        try {
            Properties config = new Properties ();
            config.put (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Shared.KAFKA_BOOTSTRAP_ADDRESS);
            config.put (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName ());
            config.put (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName ());
            config.put (ConsumerConfig.GROUP_ID_CONFIG, "Monitor");
            KafkaConsumer <String, Long> consumer = new KafkaConsumer <> (config);

            Map <String, Long> table = new TreeMap <String, Long> ();

            consumer.subscribe (Collections.singletonList (Shared.KAFKA_COUNTS_TOPIC));

            while (true) {

                ConsumerRecords <String, Long> records = consumer.poll (Duration.ofDays (365));
                for (ConsumerRecord <String, Long> record : records) {
                    table.put (record.key (), record.value ());
                }

                // A standard clear screen command :-)
                System.out.print ("\033c");

                // Map display sorted by key because the map itself is sorted.
                System.out.println ("===[Table size " + table.size () + "]===");
                for (Map.Entry <String, Long> entry : table.entrySet ()) {
                    System.out.println (entry.getKey () + " : " + entry.getValue ().toString ());
                }

                // Make sure the output is visible.
                System.out.flush ();
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
