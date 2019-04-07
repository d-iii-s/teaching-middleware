import javax.naming.InitialContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class InitialProducer {
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
            Destination destination = (Destination) namingContext.lookup ("InitialProducerTarget");

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
