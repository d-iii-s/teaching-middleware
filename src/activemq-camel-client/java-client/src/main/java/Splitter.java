import jakarta.jms.Connection;
import jakarta.jms.Destination;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Splitter {
    public static void main (String [] args) {
        try {
            // Create and start a connection to the broker.
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory ();
            Connection connection = connectionFactory.createConnection ();
            connection.start ();

            // Any communication takes place inside session.
            // Remember that multiple threads must not call one session simultaneously.
            Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);

            // Create message destinations.
            Destination sourceDestination = session.createQueue ("SplitterSource");
            Destination targetDestinationText = session.createTopic ("SplitterTargetText");
            Destination targetDestinationNumber = session.createQueue ("SplitterTargetNumber");

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
