import java.util.List;
import java.util.Random;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public final class ChildActor {

    private ChildActor () {}

    // Classes representing messages accepted by this actor.
    // All classes implement the `Command` marker interface,
    // this is used for typing.

    public interface Command {}

    public static final class ChildrenCommand implements Command {
        final List<ActorRef<Command>> children;
        final ActorRef<GuardianActor.Command> guardian;

        public ChildrenCommand (List<ActorRef<Command>> children, ActorRef<GuardianActor.Command> guardian) {
            // Local message passing uses references so remember to make copies.
            this.children = List.copyOf (children);
            this.guardian = guardian;
        }
    }

    public static final class MessageCommand implements Command {
        final String message;

        public MessageCommand (String message) {
            this.message = message;
        }
    }

    public static Behavior<Command> create () {
        return Behaviors.setup (InitialChildActor::new);
    }

    // Initial behavior implementation.

    private static final class InitialChildActor extends AbstractBehavior<Command> {

        InitialChildActor (ActorContext<Command> context) {
            super (context);
            context.getLog ().info ("Child actor created.");
        }

        @Override public Receive<Command> createReceive () {
            return newReceiveBuilder ()
                .onMessage (ChildrenCommand.class, this::onChildrenCommand)
                .build ();
        }

        private Behavior<Command> onChildrenCommand (ChildrenCommand childrenCommand) {
            getContext ().getLog ().info ("Child actor received {} child references.", childrenCommand.children.size ());
            return new ActiveChildActor (getContext (), childrenCommand.children, childrenCommand.guardian);
        }
    }

    // Subsequent behavior implementation.

    private static final class ActiveChildActor extends AbstractBehavior<Command> {

        private final Random random = new Random ();

        private final ActorRef<GuardianActor.Command> guardian;
        private List<ActorRef<Command>> children;

        ActiveChildActor (
            ActorContext<Command> context,
            List<ActorRef<Command>> children,
            ActorRef<GuardianActor.Command> guardian
        ) {
            super (context);
            this.guardian = guardian;
            this.children = children;
        }

        @Override public Receive<Command> createReceive () {
            return newReceiveBuilder ()
                .onMessage (ChildrenCommand.class, this::onChildrenCommand)
                .onMessage (MessageCommand.class, this::onMessage)
                .build ();
        }

        private Behavior<Command> onChildrenCommand (ChildrenCommand childrenCommand) {
            children = childrenCommand.children;
            getContext ().getLog ().info ("Child actor updated to {} child references.", children.size ());
            return this;
        }

        private Behavior<Command> onMessage (MessageCommand message) {
            double choice = random.nextDouble ();

            if (choice < 0.6) {
                ActorRef<Command> child = children.get (random.nextInt (children.size ()));
                getContext ().getLog ().info ("Forwarding to {}: {}", child.path ().name (), message.message);
                child.tell (message);
            } else if (choice < 0.9) {
                System.out.printf ("%s prints: %s%n", getContext ().getSelf ().path ().name (), message.message);
                guardian.tell (new GuardianActor.LinePrintedCommand (getContext ().getSelf (), message.message));
            } else {
                // Fake actor failure to demonstrate how the guardian watches children.
                throw new IllegalStateException ("Child actor failed while handling: " + message.message);
            }

            return this;
        }
    }
}
