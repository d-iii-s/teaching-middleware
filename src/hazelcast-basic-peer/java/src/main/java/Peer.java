import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;

public class Peer {

    public static void main (String [] arguments) {
        try {
            HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance ();
            IMap<Integer,String> map = hazelcast.getMap ("ExampleMap");
            map.addEntryListener ((MapListener) new EntryEventHandler ("Global"), true);
            map.addLocalEntryListener ((MapListener) new EntryEventHandler ("Local"));
            map.addPartitionLostListener (new PartitionEventHandler ());

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

            while (true) {
                int index = (int) (Math.random () * 1000);
                int rhyme = (int) (Math.random () * poem.length);
                System.out.println ("Writing index " + index + " into a map of " + map.size () + " elements.");
                map.put (index, poem [rhyme]);
                System.out.println ("The map now has " + map.size () + " elements.");
                Thread.sleep ((int) (Math.random () * 10000 + 5000));
            }
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
