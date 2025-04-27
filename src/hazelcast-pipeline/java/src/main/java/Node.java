import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.config.Config;

public class Node {

    public static void main (String [] arguments) {
        try {
            Config config = new Config ();
            config.getJetConfig ().setEnabled (true);
            HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance (config);

            System.out.println ("Instance " + hazelcast.getName () + " in a cluster of " + hazelcast.getCluster ().getMembers ().size () + " members");
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
