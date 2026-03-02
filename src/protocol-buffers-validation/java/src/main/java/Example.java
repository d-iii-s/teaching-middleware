import build.buf.protovalidate.ValidationResult;
import build.buf.protovalidate.Validator;
import build.buf.protovalidate.ValidatorFactory;
import build.buf.protovalidate.Violation;
import build.buf.protovalidate.exceptions.ValidationException;
import com.google.protobuf.Message;

import example.Example.AnExampleMessage;

public class Example {

    private static final Validator validator = ValidatorFactory.newBuilder ().build ();

    private static void validateMessage (Message message) {
        try {
            ValidationResult result = validator.validate (message);
            if (result.isSuccess ()) {
                System.out.println ("The message has passed validation checks.");
            } else {
                for (Violation violation : result.getViolations ()) {
                    System.out.println (violation);
                }
            }
        } catch (ValidationException e) {
            System.out.println (e);
        }
    }


    public static void main (String [] args) {

        // Am empty message.
        System.out.println ("Empty message:");
        validateMessage (AnExampleMessage.getDefaultInstance ());
        System.out.println ();

        // Fill two out of four fields.
        AnExampleMessage message_one = AnExampleMessage.newBuilder ()
            .setSomeInteger (0xDEAD)
            .setSomeString ("Hello Protocol Buffers !")
            .build ();
        System.out.println ("Message with one integer field and one string field:");
        validateMessage (message_one);
        System.out.println ();

        // Fill three out of four fields.
        AnExampleMessage message_two = AnExampleMessage.newBuilder ()
            .setSomeInteger (11)
            .setAnotherInteger (33)
            .setSomeString ("Hello Protocol Buffers !")
            .build ();
        System.out.println ("Message with two integer fields and one string field:");
        validateMessage (message_two);
        System.out.println ();

        // Fill four out of four fields.
        AnExampleMessage message_tre = AnExampleMessage.newBuilder ()
            .setSomeInteger (33)
            .setAnotherInteger (11)
            .setSomeString ("Hello Protocol Buffers !")
            .addSomeMoreStrings ("More strings ...")
            .addSomeMoreStrings ("More strings ...")
            .build ();
        System.out.println ("Message with two integer fields and two string fields:");
        validateMessage (message_tre);
        System.out.println ();

        // Finally validates ?
        AnExampleMessage message_for = AnExampleMessage.newBuilder ()
                .setSomeInteger (33)
                .setAnotherInteger (11)
                .setSomeString ("Hello Protocol Buffers !")
                .addSomeMoreStrings ("More strings ...")
                .addSomeMoreStrings ("Less strings ...")
                .build ();
        System.out.println ("Message with two integer fields and two string fields:");
        validateMessage (message_for);
        System.out.println ();
    }
}
