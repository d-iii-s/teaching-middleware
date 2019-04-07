import javax.naming.InitialContext;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Splitter {
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

            // Look up message destinations in JNDI.
            Destination sourceDestination = (Destination) namingContext.lookup ("SplitterSource");
            Destination targetDestinationText = (Destination) namingContext.lookup ("SplitterTargetText");
            Destination targetDestinationNumber = (Destination) namingContext.lookup ("SplitterTargetNumber");

            // Split incoming messages into text and number parts and send those on.
            MessageConsumer consumer = session.createConsumer (sourceDestination);
            MessageProducer producerText = session.createProducer (targetDestinationText);
            MessageProducer producerNumber = session.createProducer (targetDestinationNumber);

            while (true) {
                TextMessage message = (TextMessage) consumer.receive ();
                String sourceText = message.getText ();
                String targetText = sourceText.replaceAll ("[0-9]+", "#");
                String targetNumber = sourceText.replaceAll ("[^0-9]+", "");
                producerText.send (session.createTextMessage (targetText));
                producerNumber.send (session.createTextMessage (targetNumber));
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
