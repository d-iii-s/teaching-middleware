import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.KeeperException;


public class DataProducer {

    // ZooKeeper service object.
    //
    // This object makes the ZooKeeper service functions available to the client code.
    private static ZooKeeper zoo;

    // Watcher class.
    //
    // This class reacts to events delivered by ZooKeeper.
    // In this case it just prints the event information.
    private static class DataWatcher implements Watcher {
        @Override public void process (WatchedEvent event) {
            System.out.println (event);
        };
    }

    public static void main (String [] args) {
        try {

            // Initialize the connection to the ZooKeeper service.
            // The session timeout is set to one minute.
            zoo = new ZooKeeper ("localhost", 60*1000, new DataWatcher ());

            String [] poem = {
                "Twas brillig, and the slithy toves",
                "Did gyre and gimble in the wabe,",
                "All mimsy were the borogoves,",
                "And the mome raths outgrabe.",
                "Beware the Jabberwock, my son!",
                "The jaws that bite, the claws that catch!",
                "Beware the Jubjub bird, and shun",
                "The frumious Bandersnatch!"
            };

            // Create the data node where the data is published.
            // The node is ephemeral which means it is deleted on our exit.
            try {
                zoo.create (
                    Shared.ZNODE_PATH,
                    new byte [0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
            } catch (KeeperException e) {
                // Ignore exceptions to permit demonstrating recovery.
                System.out.println (e);
            }

            while (true) {
                for (String line : poem) {
                    try {
                        // Publish occasional updates to the data node.
                        // Ignore existing version in this example.
                        zoo.setData (Shared.ZNODE_PATH, line.getBytes (), -1);
                    } catch (KeeperException e) {
                        // Ignore exceptions to permit demonstrating recovery.
                        System.out.println (e);
                    }
                    Thread.sleep ((int) Math.random () * 10000 + 5000);
                }
            }

        } catch (Exception e) {
            // In case something goes wrong.
            System.out.println (e);
        }
    }
}
