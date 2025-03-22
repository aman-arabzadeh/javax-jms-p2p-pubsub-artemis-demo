package org.example.jmsjakartaee.p2p;

import javax.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import static org.example.jmsjakartaee.p2p.Producer.URL;

/** * OBS: Legacy Project Javax.jms
 *
 * JMS Consumer som lyssnar på en queue och hämtar alla tillgängliga textmeddelanden.
 * Resurser (session, consumer) stängs automatiskt via try-with-resources.
 */
public class Consumer {

    public static void main(String[] args) throws JMSException {
        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(URL);
             Connection connection = factory.createConnection()) {

            connection.start();

            try (Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
                Queue queue = session.createQueue("test.queue");

                try (MessageConsumer consumer = session.createConsumer(queue)) {
                    while (true) {
                        Message message = consumer.receive(2000);

                        if (message == null) {
                            System.out.println("[Consumer] Inga fler meddelanden. Avslutar.");
                            break;
                        }

                        if (message instanceof TextMessage textMessage) {
                            System.out.println("[Consumer] Mottaget: " + textMessage.getText());
                        } else {
                            System.out.println("[Consumer] Okänt meddelande: " + message);
                        }
                    }
                }
            }
        }
    }
}
