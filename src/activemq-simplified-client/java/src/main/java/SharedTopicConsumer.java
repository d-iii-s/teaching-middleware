import javax.jms.Topic;
import javax.jms.JMSContext;
import javax.jms.JMSConsumer;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class SharedTopicConsumer {
    public static void main (String [] sArgs) {
        try {
            // Acquire broker connection factory.
            //
            // This code is ActiveMQ specific and therefore not portable.
            // In a container environment, initial objects would be
            // looked up through JNDI instead.
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory ();

            // Create a context for communication.
            try (JMSContext context = connectionFactory.createContext ()) {

                // Connect to a hardcoded destination name.
                //
                // ActiveMQ does not need explicit destination configuration,
                // the first client to use the destination name will cause
                // the destination to be created, all other clients will
                // simply connect to the same destination.
                Topic topic = context.createTopic (Shared.TOPIC_NAME);

                // Keep receiving messages.
                try (JMSConsumer consumer = context.createSharedConsumer (topic, "SomeConsumerName")) {
                    while (true) {
                        String message = consumer.receiveBody (String.class);
                        System.out.println (message);
                    }
                }
            }
        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
