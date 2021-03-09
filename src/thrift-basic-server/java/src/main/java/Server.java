import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class Server {
    public static void main (String args []) {
        try {
            // Create the object stack used to implement the server.
            ExampleHandler handler = new ExampleHandler ();
            Example.Processor processor = new Example.Processor<Example.Iface> (handler);
            TServerTransport transport = new TServerSocket (Shared.SERVER_PORT);
            TServer server = new TSimpleServer (new Args (transport).processor (processor));

            // Enter the server request handling loop.
            server.serve ();
        }
        catch (Exception e) {
            System.out.println ("Exception:");
            System.out.println (e);
            e.printStackTrace ();
        }
    }
}
