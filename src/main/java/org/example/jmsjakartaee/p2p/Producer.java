package org.example.jmsjakartaee.p2p;

import javax.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * *OBS: Legacy Projekt Javax.jms  nya Jakarta.jms
 * JMS Producer som skickar flera textmeddelanden till en queue via Apache ActiveMQ Artemis.
 *
 * Använder point-to-point kommunikation (Queue).
 * Viktigt: Artemis använder javax.jms (inte jakarta.jms)i detta prokjekt..
 *
 * Resurser (session, producer) stängs automatiskt via try-with-resources.
 */
public class Producer {
    public static final String URL = "tcp://localhost:61616";
    public static void main(String[] args) throws Exception {
        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(URL);
             Connection connection = factory.createConnection()) {
            connection.start();
            try (Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
                Queue queue = session.createQueue("test.queue");

                try (MessageProducer producer = session.createProducer(queue)) {
                    for (int i = 1; i <= 5; i++) {
                        TextMessage message = session.createTextMessage("Meddelande " + i);
                        producer.send(message);
                        System.out.println("[Producer] Skickat: Meddelande " + i);
                    }
                    TextMessage quote = session.createTextMessage("I'm the creator of my destiny");
                    producer.send(quote);
                    System.out.println("[Producer] Skickat: Citat");
                }
            }
        }
    }
}
