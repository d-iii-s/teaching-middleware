import java.util.Properties;

import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;


public class Producer {
    public static void main (String [] args) {
        try {
            Properties config = new Properties ();
            config.put (ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Shared.KAFKA_BOOTSTRAP_ADDRESS);
            config.put (ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName ());
            config.put (ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName ());
            config.put (ProducerConfig.CLIENT_ID_CONFIG, "Producer");
            KafkaProducer <Integer, String> producer = new KafkaProducer <> (config);

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

            int key = 0;

            while (true) {
                for (String line : poem) {
                    ProducerRecord<Integer, String> record = new ProducerRecord <> (Shared.KAFKA_PRODUCER_TOPIC, key, line);
                    producer.send (record);
                    Thread.sleep (1000);
                    key ++;
                }
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
