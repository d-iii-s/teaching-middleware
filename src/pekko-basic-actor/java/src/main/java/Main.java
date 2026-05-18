import org.apache.pekko.actor.typed.ActorSystem;

public class Main {
    public static void main (String [] args) {
        ActorSystem.create (GuardianActor.create (), "guardian");
    }
}
