import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;

public class Peer {

    public static void main (String [] arguments) {
        try {
            HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance ();
            String name = hazelcast.getName ();
            IExecutorService executor = hazelcast.getExecutorService ("ExampleExecutor");

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
                int rhyme = (int) (Math.random () * poem.length);
                Gibberer runner = new Gibberer ("Peer " + name + " says: " + poem [rhyme]);
                executor.execute (runner);
                Thread.sleep ((int) (Math.random () * 10000 + 5000));
            }
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
