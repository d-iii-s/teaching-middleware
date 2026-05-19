import org.apache.pekko.actor.typed.ActorSystem;

public class ChildMain {

    public static void main (String [] args) {
        ActorSystem.create (
            ChildActor.create (),
            ClusterConfiguration.SYSTEM_NAME,
            ClusterConfiguration.create (0)
        );
    }
}
