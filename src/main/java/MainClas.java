import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Главный класс приложения.
 */
final class Program
{
    /**
     * Entry point приложения.
     */
    public static void main(String[] args)
    {
        try
        {
            JmsProducer producer = new JmsProducer();
            JmsConsumer consumer = new JmsConsumer( "test.in");
            producer.start();
            consumer.init();

            BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while (!(line = rdr.readLine()).equalsIgnoreCase("stop")) // для выхода нужно набрать в консоли stop
            {
                producer.send(line);
            }
            System.out.println("Bye!");
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

}