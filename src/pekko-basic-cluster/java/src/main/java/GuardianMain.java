import org.apache.pekko.actor.typed.ActorSystem;

public class GuardianMain {

    public static void main (String [] args) {
        ActorSystem.create (
            GuardianActor.create (),
            ClusterConfiguration.SYSTEM_NAME,
            ClusterConfiguration.create (ClusterConfiguration.GUARDIAN_PORT)
        );
    }
}
