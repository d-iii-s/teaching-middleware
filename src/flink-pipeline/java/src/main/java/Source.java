import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Source {
    public static final String [] POEM = {
        "Twas brillig, and the slithy toves",
        "Did gyre and gimble in the wabe,",
        "All mimsy were the borogoves,",
        "And the mome raths outgrabe.",
        "Beware the Jabberwock, my son!",
        "The jaws that bite, the claws that catch!",
        "Beware the Jubjub bird, and shun",
        "The frumious Bandersnatch!"
    };

    public static void main (String [] arguments) {
        try (ServerSocket server_socket = new ServerSocket (Shared.SOURCE_PORT)) {
            List <Shared.Subscriber> subscribers = new CopyOnWriteArrayList <> ();

            // The SO_REUSEADDR option makes it possible to restart the server
            // without waiting for the TIME_WAIT socket states to expire.
            server_socket.setReuseAddress (true);

            Thread accepter = new Thread (() -> {
                System.out.println ("Waiting for incoming connection.");
                while (true) {
                    try {
                        Socket client_socket = server_socket.accept ();
                        subscribers.add (new Shared.Subscriber (client_socket));
                        System.out.println ("Accepted an incoming connection.");
                    }
                    catch (Exception e) {
                        System.out.println (e);
                    }
                }
            });
            accepter.start ();

            while (true) {
                for (String line : POEM) {
                    for (Shared.Subscriber subscriber : subscribers) {
                        subscriber.writer.println (line);
                        if (subscriber.writer.checkError ()) {
                            subscribers.remove (subscriber);
                            subscriber.close ();
                        }
                    }

                    Thread.sleep (1000);
                }
            }
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
