import com.google.protobuf.Message;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
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

        // Fill two out of three fields and show what the serialized message looks like.
        Descriptor messageDescriptor = AnExampleMessage.getDescriptor ();
        FieldDescriptor someIntegerFieldDescriptor = messageDescriptor.findFieldByName ("some_integer");
        FieldDescriptor someStringFieldDescriptor = messageDescriptor.findFieldByName ("some_string");
        AnExampleMessage message = AnExampleMessage.newBuilder ()
            .setField (someIntegerFieldDescriptor, 0xDEAD)
            .setField (someStringFieldDescriptor, "Hello Protocol Buffers !")
            .build ();
        System.out.println ("Message with one integer field and one string field:");
        dumpMessage (message);
        System.out.println ();

        // We can also debug dump a message.
        System.out.println ("Debug dump:");
        System.out.println (message.toString ());
    }
}
