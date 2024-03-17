import jakarta.jms.Connection;
import jakarta.jms.Destination;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class VerboseQueueConsumer {
    public static void main (String [] sArgs) {
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

            // Keep receiving messages.
            //
            // We print the message with all headers to demonstrate what they look like.
            MessageConsumer consumer = session.createConsumer (queue);

            while (true) {
                Message message = consumer.receive ();
                System.out.println (message);
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
