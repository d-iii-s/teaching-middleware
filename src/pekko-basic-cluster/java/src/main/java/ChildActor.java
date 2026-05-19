import java.io.Serializable;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.receptionist.Receptionist;
import org.apache.pekko.actor.typed.receptionist.ServiceKey;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public final class ChildActor extends AbstractBehavior<ChildActor.Command> {

    public static final ServiceKey<Command> CHILD_SERVICE_KEY = ServiceKey.create (Command.class, "child");

    // Classes representing messages accepted by this actor.
    // All classes implement the `Command` marker interface,
    // this is used for typing.

    public interface Command {}

    public static final class MessageCommand implements Command, Serializable {
        final String message;
        final ActorRef<GuardianActor.Command> guardian;

        public MessageCommand (String message, ActorRef<GuardianActor.Command> guardian) {
            this.message = message;
            this.guardian = guardian;
        }
    }

    // Behavior implementation.

    public static Behavior<Command> create () {
        return Behaviors.setup (ChildActor::new);
    }

    private ChildActor (ActorContext<Command> context) {
        super (context);
        context.getLog ().info ("Child actor created.");

        // Cluster receptionist registration makes the child visible to the guardian.
        Receptionist.get (context.getSystem ()).ref ().tell (
            Receptionist.register (CHILD_SERVICE_KEY, context.getSelf ())
        );
    }

    @Override public Receive<Command> createReceive () {
        return newReceiveBuilder ()
            .onMessage (MessageCommand.class, this::onMessageCommand)
            .build ();
    }

    private Behavior<Command> onMessageCommand (MessageCommand message) {
        getContext ().getLog ().info (
            "{} in process {} prints: {}",
            getContext ().getSelf ().path ().name (),
            ProcessHandle.current ().pid (),
            message.message
        );
        message.guardian.tell (new GuardianActor.LinePrintedCommand (getContext ().getSelf (), message.message));
        return this;
    }
}
