import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.google.protobuf.InvalidProtocolBufferException;

import example.Example.AnExampleMessage;
import example.Example.MoreExampleMessages;

public class Example {

    public static final int DUMP_LINE_SIZE = 16;

    public static void dumpBuffer (byte [] buffer) {
        for (int offset = 0 ; offset < buffer.length ; offset += DUMP_LINE_SIZE) {

            // This is where we collect the output.
            StringBuilder builder = new StringBuilder ();

            // Offset column.
            builder.append (String.format ("%08x", offset));

            // Show numeric values.
            for (int position = 0 ; position < DUMP_LINE_SIZE ; position ++) {
                builder.append (" ");
                if (offset + position < buffer.length) builder.append (String.format ("%02x", buffer [offset + position]));
                                                  else builder.append ("--");
            }

            // Show character values.
            builder.append (" ");
            for (int position = 0 ; position < DUMP_LINE_SIZE ; position ++) {
                if (offset + position < buffer.length) {
                    if (buffer [offset + position] < ' ') builder.append (".");
                                                     else builder.append ((char) buffer [offset + position]);
                }
            }

            // New line.
            System.out.println (builder);
        }
    }


    public static void dumpMessage (Message message) {
        byte [] buffer = message.toByteArray ();
        System.out.println ("Buffer size is " + buffer.length + " bytes.");
        dumpBuffer (buffer);
    }


    public static void main (String [] args) {

        // Create message instance.
        AnExampleMessage message = AnExampleMessage.newBuilder ()
            .setSomeInteger (0xDEAD)
            .setAnotherInteger (0xBEEF)
            .setSomeString ("Hello Protocol Buffers !")
            .build ();
        System.out.println ("Message:");
        dumpMessage (message);
        System.out.println ();

        // Message instance with an empty any.
        MoreExampleMessages messages_one = MoreExampleMessages.newBuilder ()
            .build ();
        System.out.println ("Message with an empty any:");
        dumpMessage (messages_one);
        System.out.println ();

        // We can also debug dump a message.
        System.out.println ("Debug dump:");
        System.out.println (messages_one.toString ());

        // Message instance with another message in any.
        MoreExampleMessages messages_two = MoreExampleMessages.newBuilder ()
            .setSomething (Any.pack (message))
            .build ();
        System.out.println ("Message with an other message in any:");
        dumpMessage (messages_two);
        System.out.println ();

        // We can also debug dump a message.
        System.out.println ("Debug dump:");
        System.out.println (messages_two.toString ());
    }
}
