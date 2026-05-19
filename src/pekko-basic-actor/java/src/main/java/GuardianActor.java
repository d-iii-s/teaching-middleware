import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.Terminated;
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

    // Message with no body is represented by single static instance.
    private enum SendLineCommand implements Command { INSTANCE }

    public static final class LinePrintedCommand implements Command {
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
    private static final int CHILD_COUNT = 8;

    private final Random random = new Random ();

    private final List<ActorRef<ChildActor.Command>> children = new ArrayList<> ();

    // Behavior implementation.

    public static Behavior<Command> create () {
        return Behaviors.withTimers (timers -> Behaviors.setup (context -> new GuardianActor(context, timers)));
    }

    private GuardianActor(ActorContext<Command> context, TimerScheduler<Command> timers) {
        super (context);
        context.getLog ().info ("Guardian actor created.");

        for (int index = 0 ; index < CHILD_COUNT ; index ++) {
            ActorRef<ChildActor.Command> child = context.spawn (ChildActor.create (), "child-" + index);
            context.watch (child);
            children.add (child);
        }

        tellChildrenAboutChildren ();

        timers.startTimerAtFixedRate (SendLineCommand.INSTANCE, Duration.ofSeconds (1));
    }

    @Override public Receive<Command> createReceive () {
        return newReceiveBuilder ()
            .onMessage (SendLineCommand.class, ignored -> onSendLineCommand ())
            .onMessage (LinePrintedCommand.class, this::onLinePrintedCommand)
            .onSignal (Terminated.class, this::onTerminated)
            .build ();
    }

    private Behavior<Command> onSendLineCommand () {

        String line = POEM [random.nextInt (POEM.length)];
        ActorRef<ChildActor.Command> child = children.get (random.nextInt (children.size ()));
        getContext ().getLog ().info ("Sending to {}: {}", child.path ().name (), line);
        child.tell (new ChildActor.MessageCommand (line));

        // Continue with the same behavior.
        return this;
    }

    private Behavior<Command> onLinePrintedCommand (LinePrintedCommand message) {

        // In a real system we would implement resending logic but here just log.
        getContext ().getLog ().info ("{} confirmed printing: {}", message.child.path ().name (), message.message);

        // Continue with the same behavior.
        return this;
    }

    private Behavior<Command> onTerminated (Terminated signal) {

        // Remove the terminated child from children.
        children.removeIf (child -> child.equals (signal.getRef ()));
        getContext ().getLog ().info ("Collected stopped child actor {}.", signal.getRef ().path ().name ());

        // Terminate guardian if no children left.
        if (children.isEmpty ()) return Behaviors.stopped ();

        tellChildrenAboutChildren ();

        // Continue with the same behavior.
        return this;
    }

    // Helper function to notify children about current children list.

    private void tellChildrenAboutChildren () {
        for (ActorRef<ChildActor.Command> child : children) {
            child.tell (new ChildActor.ChildrenCommand (children, getContext ().getSelf ()));
        }
    }
}
