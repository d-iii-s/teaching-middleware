import jakarta.jms.Connection;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class DurableTopicConsumer {
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
            connection.setClientID ("SomeClientName");
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
            Topic topic = session.createTopic (Shared.TOPIC_NAME);

            // Register as durable consumer and keep receiving messages.
            MessageConsumer consumer = session.createDurableSubscriber (topic, "SomeSubscriptionName");

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
