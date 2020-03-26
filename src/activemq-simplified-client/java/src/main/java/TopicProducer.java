import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class TopicProducer {
    public static void main (String [] args) {
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
                Destination topic = context.createTopic (Shared.TOPIC_NAME);

                // Keep sending messages.
                JMSProducer producer = context.createProducer ();
                int counter = 0;
                while (true) {
                    producer.send (topic, "Hello number " + counter + " from Java Topic Producer !");
                    System.out.print (".");
                    Thread.sleep (1000);
                    counter ++;
                }
            }
        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
