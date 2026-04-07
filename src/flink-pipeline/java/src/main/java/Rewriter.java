import java.io.PrintWriter;
import java.net.Socket;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Rewriter {
    public static void main (String [] arguments) {
        try {
            StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment ();

            environment
                // The socket source limits parallelism.
                .socketTextStream (Shared.HOST, Shared.SOURCE_PORT, "\n")
                // Parallelism is facilitated here by partitioning.
                .rebalance ()
                // The map operator will execute in parallel now,
                // inserting thread identity to demonstrate.
                .map (line -> String.format ("[%d] %s\n",
                    Thread.currentThread ().threadId (),
                    new StringBuilder (line).reverse ().toString ()))
                // The socket sink limits parallelism again.
                .writeToSocket (Shared.HOST, Shared.SINK_PORT, new SimpleStringSchema ());

            environment.setParallelism (Runtime.getRuntime ().availableProcessors ());
            environment.execute ("Rewriter");
        }
        catch (Exception e) {
            System.out.println (e);
            e.printStackTrace ();
        }
    }
}
