import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Shared {
    public static final String HOST = "127.0.0.1";
    public static final int SINK_PORT = 8833;
    public static final int SOURCE_PORT = 8888;
    public static final int MONITOR_PORT = 3388;

    public static class Subscriber {
        private final Socket socket;
        public final PrintWriter writer;
        public final BufferedReader reader;

        public Subscriber (Socket socket) throws Exception {
            this.socket = socket;
            this.writer = new PrintWriter (socket.getOutputStream (), true);
            this.reader = new BufferedReader (new InputStreamReader (socket.getInputStream ()));
        }

        public void close () {
            try {
                writer.close ();
                reader.close ();
                socket.close ();
            }
            catch (Exception e) {
                System.out.println (e);
            }
        }
    }
}
