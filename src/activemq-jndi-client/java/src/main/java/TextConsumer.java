import javax.naming.InitialContext;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class TextConsumer {
    public static void main (String [] args) {
        try {
            // Look up broker connection factory in JNDI.
            InitialContext namingContext = new InitialContext ();
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup ("ConnectionFactory");

            // Create and start a connection to the broker.
            Connection connection = connectionFactory.createConnection ();
            connection.start ();

            // Any communication takes place inside session.
            // Remember that multiple threads must not call one session simultaneously.
            Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);

            // Look up message destination in JNDI.
            Destination destination = (Destination) namingContext.lookup ("TextConsumerSource");

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
