import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.receptionist.Receptionist;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;
import org.apache.pekko.actor.typed.javadsl.TimerScheduler;

public class GuardianActor extends AbstractBehavior<GuardianActor.Command> {

    // Classes representing messages accepted by this actor.
    // All classes implement the `Command` marker interface,
    // this is used for typing.

    public interface Command {}

    public enum SendLineCommand implements Command { INSTANCE }

    public static final class ChildListingCommand implements Command {
        final Receptionist.Listing listing;

        public ChildListingCommand (Receptionist.Listing listing) {
            this.listing = listing;
        }
    }

    public static final class LinePrintedCommand implements Command, Serializable {
        final ActorRef<ChildActor.Command> child;
        final String message;

        public LinePrintedCommand (ActorRef<ChildActor.Command> child, String message) {
            this.child = child;
            this.message = message;
        }
    }

    // Behavior state.

    private static final String [] POEM = {
        "Twas brillig, and the slithy toves",
        "Did gyre and gimble in the wabe,",
        "All mimsy were the borogoves,",
        "And the mome raths outgrabe.",
        "Beware the Jabberwock, my son!",
        "The jaws that bite, the claws that catch!",
        "Beware the Jubjub bird, and shun",
        "The frumious Bandersnatch!"
    };

    private final Random random = new Random ();
    private Set<ActorRef<ChildActor.Command>> children = Set.of ();

    // Behavior implementation.

    public static Behavior<Command> create () {
        return Behaviors.withTimers (timers -> Behaviors.setup (context -> new GuardianActor (context, timers)));
    }

    private GuardianActor (ActorContext<Command> context, TimerScheduler<Command> timers) {
        super (context);
        context.getLog ().info ("Guardian actor created.");

        ActorRef<Receptionist.Listing> listingAdapter = context.messageAdapter (
            Receptionist.Listing.class,
            ChildListingCommand::new
        );
        Receptionist.get (context.getSystem ()).ref ().tell (
            Receptionist.subscribe (ChildActor.CHILD_SERVICE_KEY, listingAdapter)
        );

        timers.startTimerAtFixedRate (SendLineCommand.INSTANCE, Duration.ofSeconds (1));
    }

    @Override public Receive<Command> createReceive () {
        return newReceiveBuilder ()
            .onMessage (ChildListingCommand.class, this::onChildListingCommand)
            .onMessage (SendLineCommand.class, ignored -> onSendLineCommand ())
            .onMessage (LinePrintedCommand.class, this::onLinePrintedCommand)
            .build ();
    }

    private Behavior<Command> onChildListingCommand (ChildListingCommand message) {
        children = message.listing.getServiceInstances (ChildActor.CHILD_SERVICE_KEY);
        return this;
    }

    private Behavior<Command> onSendLineCommand () {

        // Do nothing if there are no children.
        if (children.isEmpty ()) return this;

        String line = POEM [random.nextInt (POEM.length)];
        ActorRef<ChildActor.Command> child = new ArrayList<> (children).get (random.nextInt (children.size ()));
        getContext ().getLog ().info ("Sending to {}: {}", child.path ().name (), line);
        child.tell (new ChildActor.MessageCommand (line, getContext ().getSelf ()));

        return this;
    }

    private Behavior<Command> onLinePrintedCommand (LinePrintedCommand message) {
        getContext ().getLog ().info ("{} confirmed printing: {}", message.child.path ().name (), message.message);
        return this;
    }
}
