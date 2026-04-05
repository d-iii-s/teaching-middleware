import java.io.PrintWriter;
import java.net.Socket;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Rewriter {
    public static void main (String [] arguments) {
        try {
            StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment ();

            environment
                .socketTextStream (Shared.HOST, Shared.SOURCE_PORT, "\n")
                .map (line -> new StringBuilder (line).reverse ().append ('\n').toString ())
                 // The socket sink is intended for debugging and does not participate in checkpointing.
                .writeToSocket (Shared.HOST, Shared.SINK_PORT, new SimpleStringSchema ());

            environment.execute ("Rewriter");
        }
        catch (Exception e) {
            System.out.println (e);
            e.printStackTrace ();
        }
    }
}
