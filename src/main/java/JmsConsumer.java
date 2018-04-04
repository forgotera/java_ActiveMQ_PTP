import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Класс получателя сообщений
 * Логика работы событийная:
 * после инициализации класс подписывается на событие onMessage,
 * которое возникает при получении целевым объектом сообщения.
 * Данный класс реализует интерфейс MessageListener, что делает
 * возможным передавать его экзепляры в метод _consumer.setMessageListener(...);
 *
 * @author allknower
 *
 */
public class JmsConsumer implements MessageListener, AutoCloseable
{
    private final ActiveMQConnectionFactory _connectionFactory;
    private Connection _connection = null;
    private Session _session = null;
    private MessageConsumer _consumer;
    private String _queueName;

    /**
     * Конструктор используется в случае, когда брокер не требует авторизации.
     */
    JmsConsumer(String queue)
    {
        _connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
        _queueName = queue;
    }

    /**
     * подобен аналогичному методу отправителя
     * @throws JMSException
     */
    public void init() throws JMSException
    {
        System.out.println("Init consumer...");

        _connection = _connectionFactory.createConnection();
        _connection.start();
        _session = _connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = _session.createQueue(_queueName);
        _consumer = _session.createConsumer(dest);
        _consumer.setMessageListener(this); // подписываемся на событие onMessage

        System.out.println("Consumer successfully initialized");

    }

    /**
     * Обработчик появления сообщения в обьекте
     * реализация метода интерфейса MessageListener
     */
    public void onMessage(Message msg)
    {
        if (msg instanceof TextMessage)
        {
            try
            {
                System.out.println("Received message: " + ((TextMessage) msg).getText());
            }
            catch (JMSException e)
            {
                e.printStackTrace();
            }
        }
        else System.out.println("Received message: " + msg.getClass().getName());
    }


    public void close() {
        try
        {
            if (_session != null)
                _session.close();
        }
        catch (JMSException jmsEx)
        {
            jmsEx.printStackTrace();
        }
        try
        {
            if (_connection != null)
                _connection.close();
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }
    }
}