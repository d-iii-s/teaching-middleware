import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.WatchedEvent;


public class DataConsumer {

    // ZooKeeper service object.
    //
    // This object makes the ZooKeeper service functions available to the client code.
    private static ZooKeeper zoo;

    // Watcher class.
    //
    // This class reacts to events delivered by ZooKeeper.
    // In this case it prints the data node on data change.
    private static class DataWatcher implements Watcher {
        @Override public void process (WatchedEvent event) {
            try {
                // In any case print the event.
                System.out.println (event);
                // Only print the data node if this is a relevant data change event.
                if ((event.getType () == Watcher.Event.EventType.NodeDataChanged) &&
                    (event.getPath ().equals (Shared.ZNODE_PATH))) {
                    // Getting the data also creates new one time event subscription.
                    System.out.println (new String (zoo.getData (Shared.ZNODE_PATH, true, null)));
                }
            } catch (Exception e) {
                System.out.println (e);
            }
        };
    }

    public static void main (String [] args) {
        try {

            // Initialize the connection to the ZooKeeper service.
            // The session timeout is set to one minute.
            zoo = new ZooKeeper ("localhost", 60*1000, new DataWatcher ());

            // Create one time event subscription on the data node.
            zoo.exists (Shared.ZNODE_PATH, true);

            // Just prevent the main thread from exitting.
            while (true) Thread.sleep (1000);

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
