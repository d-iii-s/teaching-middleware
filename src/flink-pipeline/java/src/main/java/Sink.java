import java.net.ServerSocket;
import java.net.Socket;

public class Sink {
    public static void main (String [] arguments) {
        try (ServerSocket server_socket = new ServerSocket (Shared.SINK_PORT)) {

            // The SO_REUSEADDR option makes it possible to restart the server
            // without waiting for the TIME_WAIT socket states to expire.
            server_socket.setReuseAddress (true);

            System.out.println ("Waiting for incoming connection.");
            while (true) {
                try {
                    // Wait until an incoming connection arrives and accept it.
                    Socket client_socket = server_socket.accept ();
                    Thread reader_thread = new Thread (() -> {
                        try {
                            Shared.Subscriber subscriber = new Shared.Subscriber (client_socket);
                            while (true) {
                                String line = subscriber.reader.readLine ();
                                if (line == null) break;
                                System.out.println ("[" + Thread.currentThread ().threadId () + "] " + line);
                            }
                            subscriber.close ();
                        }
                        catch (Exception e) {
                            System.out.println ("[" + Thread.currentThread ().threadId () + "] " + e);
                        }
                    });
                    reader_thread.start ();
                }
                catch (Exception e) {
                    System.out.println (e);
                }
            }
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
