import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Server {

    private static final int REGISTRY_DEFAULT_PORT = 1099;

    public static void main (String args []) {
        try {
            // Instantiate the object to be served.
            ExampleImpl object = new ExampleImpl ();

            // Register the object with a well known name.
            Registry registry = LocateRegistry.createRegistry (REGISTRY_DEFAULT_PORT);
            registry.bind (Shared.SERVER_NAME, object);

            // Prevent termination when executing from Maven.
            synchronized (Thread.currentThread ()) {
                Thread.currentThread ().wait ();
            }
        }
        catch (Exception e) {
            System.out.println ("Exception:");
            System.out.println (e);
            e.printStackTrace ();
        }
    }
}
