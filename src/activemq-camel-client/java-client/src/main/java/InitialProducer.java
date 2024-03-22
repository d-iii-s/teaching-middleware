import jakarta.jms.Connection;
import jakarta.jms.Destination;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class InitialProducer {
    public static void main (String [] args) {
        try {
            // Create and start a connection to the broker.
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory ();
            Connection connection = connectionFactory.createConnection ();
            connection.start ();

            // Any communication takes place inside session.
            // Remember that multiple threads must not call one session simultaneously.
            Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);

            // Create message destination.
            Destination destination = session.createQueue ("InitialProducerTarget");

            // Keep sending messages.
            MessageProducer producer = session.createProducer (destination);

            int counter = 0;

            while (true) {
                TextMessage message = session.createTextMessage ("Hello number " + counter + " from Initial Producer !");
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
