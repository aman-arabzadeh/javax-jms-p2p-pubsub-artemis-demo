package org.example.jmsjakartaee.topic;

import javax.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 *  * OBS: Legacy Project Javax.jms
 * JMS Consumer som prenumererar på meddelanden från en topic via Apache ActiveMQ Artemis.
 */
public class ConsumerOne {
    static final String BROKER_URL = "tcp://localhost:61616";

    public static void main(String[] args) throws JMSException {
        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
             Connection connection = factory.createConnection()) {

            connection.setClientID("consumer-one-client");
            connection.start();

            try (Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
                Topic topic = session.createTopic("test.topic");

                try (TopicSubscriber subscriber = session.createDurableSubscriber(topic, "consumer-one")) {
                    while (true) {
                        Message message = subscriber.receive(2000);

                        if (message == null) {
                            System.out.println("[ConsumerOne] Inga fler meddelanden. Avslutar.");
                            break;
                        }

                        if (message instanceof TextMessage textMessage) {
                            System.out.println("[ConsumerOne] Mottaget: " + textMessage.getText());
                        } else {
                            System.out.println("[ConsumerOne] Okänt meddelande: " + message);
                        }
                    }
                }
            }
        }
    }
}
