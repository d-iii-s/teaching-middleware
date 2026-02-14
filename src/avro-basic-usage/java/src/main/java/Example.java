import example.AnExampleMessage;
import example.MoreExampleMessages;

import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class Example {

    private static final int DUMP_LINE_SIZE = 16;

    private static final EncoderFactory ENCODER_FACTORY = EncoderFactory.get ();
    private static final DecoderFactory DECODER_FACTORY = DecoderFactory.get ();

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


    public static void dumpMessage (SpecificRecord record) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream ();
        BinaryEncoder encoder = ENCODER_FACTORY.directBinaryEncoder (stream, null);

        try {
            DatumWriter<SpecificRecord> writer = new SpecificDatumWriter<SpecificRecord> (record.getSchema ());
            writer.write (record, encoder);
        }
        catch (Exception e) {
            System.out.println (e);
        }

        byte [] buffer = stream.toByteArray ();
        System.out.println ("Buffer size is " + buffer.length + " bytes.");
        dumpBuffer (buffer);
    }


    public static void main (String [] args) {

        // Show what a serialized empty message looks like.
        // All non null fields must have values otherwise serialization throws.
        System.out.println ("Default message:");
        dumpMessage (new AnExampleMessage (0, null, "", List.of ()));
        System.out.println ();

        // Fill two out of four fields and show what the serialized message looks like.
        AnExampleMessage message_one = AnExampleMessage.newBuilder ()
            .setSomeInteger (0xDEAD)
            .setSomeString ("Hello Avro !")
            .setSomeMoreStrings (List.of ())
            .build ();
        System.out.println ("Message with one integer field and one string field:");
        dumpMessage (message_one);
        System.out.println ();

        // Fill the second integer field and show what the serialized message looks like.
        AnExampleMessage message_two = AnExampleMessage.newBuilder (message_one)
            .setAnotherInteger (0xBEEF)
            .build ();
        System.out.println ("Message with two integer fields and one string field:");
        dumpMessage (message_two);
        System.out.println ();

        // Create message instance with repeated fields.
        MoreExampleMessages messages = MoreExampleMessages.newBuilder ()
            .setMessages (List.of (message_one, message_two))
            .build ();
        System.out.println ("Message with two more embedded messages:");
        dumpMessage (messages);
        System.out.println ();

        // Encode and decode message instance to show it also works.
        try {
            ByteArrayOutputStream output_stream = new ByteArrayOutputStream ();
            BinaryEncoder encoder = ENCODER_FACTORY.directBinaryEncoder (output_stream, null);
            DatumWriter<SpecificRecord> writer = new SpecificDatumWriter<SpecificRecord> (messages.getSchema ());
            writer.write (messages, encoder);
            ByteArrayInputStream input_stream = new ByteArrayInputStream (output_stream.toByteArray ());
            BinaryDecoder decoder = DECODER_FACTORY.directBinaryDecoder (input_stream, null);
            DatumReader<MoreExampleMessages> reader = new SpecificDatumReader<MoreExampleMessages> (MoreExampleMessages.class);
            MoreExampleMessages parsed_messages = reader.read (null, decoder);
        }
        catch (Exception e) {
            System.out.println (e);
        }

        System.out.println ("Encoded and decoded message:");
        for (AnExampleMessage message : messages.getMessages ()) {
            System.out.println (message.getSomeInteger () + " - " + message.getAnotherInteger () + " - " + message.getSomeString ());
        }
        System.out.println ();

        // We can also debug dump a message.
        System.out.println ("Debug dump:");
        System.out.println (messages.toString ());
        System.out.println ();

        // And we can also dump the schema.
        System.out.println ("Schema:");
        System.out.println (MoreExampleMessages.getClassSchema ().toString (true));
    }
}
