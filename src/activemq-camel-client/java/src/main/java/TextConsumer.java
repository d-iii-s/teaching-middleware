import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class TextConsumer {
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
            Destination destination = session.createTopic ("TextConsumerSource");

            // Keep receiving messages.
            MessageConsumer consumer = session.createConsumer (destination);

            while (true) {
                TextMessage message = (TextMessage) consumer.receive ();
                System.out.println (message.getText ());
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
