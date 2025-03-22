package p2p;

import org.junit.jupiter.api.Test;
import javax.jms.*;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ProducerConsumerTest {

    @Test
    public void testSendAndReceiveMessage() throws Exception {
        String expected = "Testmeddelande";

        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
             Connection connection = factory.createConnection()) {

            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("test.queue");

            // Producer
            MessageProducer producer = session.createProducer(queue);
            producer.send(session.createTextMessage(expected));

            // Consumer
            MessageConsumer consumer = session.createConsumer(queue);
            Message message = consumer.receive(3000);

            assertNotNull(message, "Meddelande kunde inte tas emot.");
            assertInstanceOf(TextMessage.class, message);
            assertEquals(expected, ((TextMessage) message).getText());
        }
    }
}
