import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class ExampleClient {
    public static void main (String args []) {
        try {
            Registry registry = LocateRegistry.getRegistry ();
            Example object = (Example) registry.lookup ("ExampleServer");
            object.printString ("Hello RMI !");
        }
        catch (Exception e) {
            System.out.println ("Exception:");
            System.out.println (e);
            e.printStackTrace ();
        }
    }
}
