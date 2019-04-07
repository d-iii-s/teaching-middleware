import java.time.Duration;
import java.util.Properties;
import java.util.Collections;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class Consumer {
    public static void main (String [] args) {
        try {
            Properties config = new Properties ();
            config.put (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Shared.KAFKA_BOOTSTRAP_ADDRESS);
            config.put (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName ());
            config.put (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName ());
            config.put (ConsumerConfig.GROUP_ID_CONFIG, "BasicConsumer");
            KafkaConsumer <String, String> consumer = new KafkaConsumer <> (config);

            // Subscribe to the topic.
            // Kafka will connect the consumer to a subset of partitions
            // in that topic such that the consumer group will receive all messages.
            // Kafka will also dynamically balance the group in response to consumer churn.
            consumer.subscribe (Collections.singletonList (Shared.KAFKA_TOPIC));
            while (true) {
                // Consume messages from the topic.
                // Kafka will use the default auto commit settings.
                ConsumerRecords <String, String> records = consumer.poll (Duration.ofDays (365));
                for (ConsumerRecord <String, String> record : records) {
                    System.out.println (record);
                }
            }
        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
