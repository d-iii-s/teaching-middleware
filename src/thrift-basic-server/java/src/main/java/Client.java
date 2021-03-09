import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {
    public static void main (String args []) {
        try {
            // Create the object stack used to connect to the server.
            TTransport transport = new TSocket (Shared.SERVER_ADDR, Shared.SERVER_PORT);
            TProtocol protocol = new TBinaryProtocol (transport);
            Example.Client client = new Example.Client (protocol);

            transport.open ();
            try {
                client.printString ("Hello from Thrift in Java !");
            }
            finally {
                transport.close ();
            }
        }
        catch (Exception e) {
            System.out.println ("Exception:");
            System.out.println (e);
            e.printStackTrace ();
        }
    }
}
