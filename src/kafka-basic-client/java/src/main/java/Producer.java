import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;


public class Producer {
    public static void main (String [] args) {
        try {
            Properties config = new Properties ();
            config.put (ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Shared.KAFKA_BOOTSTRAP_ADDRESS);
            config.put (ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName ());
            config.put (ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName ());
            config.put (ProducerConfig.CLIENT_ID_CONFIG, "BasicProducer");
            KafkaProducer <String, String> producer = new KafkaProducer <> (config);

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

            while (true) {
                for (String line : poem) {

                    // Publish occasional messages to the topic.
                    // The record has no key and no partition identifier.
                    // Kafka will use the default partitioning logic in that case.
                    // Depending on the platform, this may be round robin or sticky.
                    ProducerRecord<String, String> record = new ProducerRecord <> (
                        Shared.KAFKA_TOPIC,
                        line);

                    // Send is non blocking.
                    // Wait happens when waiting on the future object.
                    Future <RecordMetadata> result = producer.send (record);
                    System.out.println (result.get ());

                    Thread.sleep ((int) Math.random () * 10000 + 5000);
                }
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
