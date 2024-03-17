import jakarta.jms.Connection;
import jakarta.jms.Destination;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class QueueProducer {
    public static void main (String [] args) {
        try {
            // Acquire broker connection factory.
            //
            // This code is ActiveMQ specific and therefore not portable.
            // In a container environment, initial objects would be
            // looked up through JNDI instead.
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory ();

            // Create and start a connection to the broker.
            Connection connection = connectionFactory.createConnection ();
            connection.start ();

            // Any communication takes place inside session.
            // Remember that multiple threads must not call one session simultaneously.
            Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);

            // Connect to a hardcoded destination name.
            //
            // ActiveMQ does not need explicit destination configuration,
            // the first client to use the destination name will cause
            // the destination to be created, all other clients will
            // simply connect to the same destination.
            Destination queue = session.createQueue (Shared.QUEUE_NAME);

            // Keep sending messages.
            MessageProducer producer = session.createProducer (queue);

            int counter = 0;

            while (true) {
                TextMessage message = session.createTextMessage ("Hello number " + counter + " from Java Queue Producer !");
                producer.send (message);
                System.out.print (".");
                Thread.sleep (1000);
                counter ++;
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
