#include <string>
#include <iomanip>
#include <iostream>

#include <capnp/message.h>
#include <capnp/serialize.h>
#include <capnp/pretty-print.h>

#include "example.capnp.h"

#define BUFFER_SIZE 16384
#define DUMP_LINE_SIZE 16


// Helper function to dump buffer content.
void dump_buffer (unsigned char *buffer, size_t size) {

    // Preserve output stream settings across invocation.
    std::ios stream_state (NULL);
    stream_state.copyfmt (std::cout);

    // Switch output to hex base.
    std::cout << std::hex << std::setfill ('0');

    for (size_t offset = 0 ; offset < size ; offset += DUMP_LINE_SIZE) {

        // Offset column.
        std::cout << std::setw (8) << offset;

        // Show numeric values.
        for (size_t position = 0 ; position < DUMP_LINE_SIZE ; position ++) {
            std::cout << " ";
            if (offset + position < size) std::cout << std::setw (2) << (int) buffer [offset + position];
                                     else std::cout << "--";
        }

        // Show character values.
        std::cout << " ";
        for (size_t position = 0 ; position < DUMP_LINE_SIZE ; position ++) {
            if (offset + position < size) {
                if (buffer [offset + position] < ' ') std::cout << '.';
                                                 else std::cout << buffer [offset + position];
            } else std::cout << '.';
        }

        // New line;
        std::cout << std::endl;
    }

    // Preserve output stream settings across invocation.
    std::cout.copyfmt (stream_state);
}


// Helper function to dump message content.
void dump_message (capnp::MessageBuilder &builder) {
    auto word_array = messageToFlatArray (builder);
    auto byte_array = word_array.asBytes ();
    auto buffer = byte_array.begin ();
    auto size = byte_array.size ();
    std::cout << "Buffer size is " << size << " bytes." << std::endl;
    dump_buffer (buffer, size);
}


int main (void) {

    // Show what a serialized empty message looks like.
    std::cout << "Empty message:" << std::endl;
    capnp::MallocMessageBuilder default_message_builder;
    default_message_builder.initRoot<AnExampleMessage> ();
    dump_message (default_message_builder);
    std::cout << std::endl;

    // Fill two out of three fields and show what the serialized message looks like.
    capnp::MallocMessageBuilder message_one_builder;
    AnExampleMessage::Builder message_one = message_one_builder.initRoot<AnExampleMessage> ();
    message_one.setSomeInteger (0xDEAD);
    message_one.setSomeString ("Hello Cap'n Proto !");
    std::cout << "Message with one integer field and one string field:" << std::endl;
    dump_message (message_one_builder);
    std::cout << std::endl;

    // Fill the third field and show what the serialized message looks like.
    capnp::MallocMessageBuilder message_two_builder;
    AnExampleMessage::Builder message_two = message_two_builder.initRoot<AnExampleMessage> ();
    message_two.setSomeInteger (0xDEAD);
    message_two.setAnotherInteger (0xBEEF);
    message_two.setSomeString ("Hello Cap'n Proto !");
    std::cout << "Message with two integer fields and one string field:" << std::endl;
    dump_message (message_two_builder);
    std::cout << std::endl;

    // Create message instance with repeated fields.
    capnp::MallocMessageBuilder messages_builder;
    MoreExampleMessages::Builder messages = messages_builder.initRoot<MoreExampleMessages> ();
    auto messagesList = messages.initMessages (2);
    messagesList.setWithCaveats (0, message_one.asReader ());
    messagesList.setWithCaveats (1, message_two.asReader ());
    std::cout << "Message with two more embedded messages:" << std::endl;
    dump_message (messages_builder);
    std::cout << std::endl;

    // Encode and decode message instance to show it also works.
    auto messages_flat_array = messageToFlatArray (messages_builder);
    capnp::FlatArrayMessageReader reader (messages_flat_array);
    auto parsed_messages = reader.getRoot<MoreExampleMessages> ();
    std::cout << "Encoded and decoded message:" << std::endl;
    for (auto message : parsed_messages.getMessages ()) {
        std::cout << message.getSomeInteger () <<
            " - " << message.getAnotherInteger () <<
            " - " << message.getSomeString ().cStr () << std::endl;
    }
    std::cout << std::endl;

    // We can also debug dump a message.
    std::cout << "Debug dump:" << std::endl;
    auto dump = capnp::prettyPrint (parsed_messages);
    std::cout << dump.flatten ().cStr () << std::endl;
}
