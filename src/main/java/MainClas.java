import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainClas {
    public static void main (String[] args)
    {
        String url = "tcp://localhost:61616";
        try(JmsProducer producer = new JmsProducer(url));
            jmsConsumer comsumer = new JmsConsumer(url, "test.in");
        {
            producer.start();
            comsumer.init();


            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String lane;
            while (!(line = reader.readLine().equalsIgnoreCase("stop")){
                producer.send(line);
        }
            System.out.println("Bye");
        }
        catch (Throwable e)
        {
            e.printStactTrace()
        }
    }
}
