#include <string>
#include <iomanip>
#include <iostream>

#include "example.pb.h"
using namespace example;

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
void dump_message (const google::protobuf::Message &message) {
    unsigned char buffer [BUFFER_SIZE];
    message.SerializeToArray (buffer, sizeof (buffer));
    size_t size = message.ByteSizeLong ();
    std::cout << "Buffer size is " << size << " bytes." << std::endl;
    dump_buffer (buffer, size);
}


int main (void) {

    // Library version check.
    // Aborts on version mismatch.
    GOOGLE_PROTOBUF_VERIFY_VERSION;

    // Show what a serialized empty message looks like.
    std::cout << "Empty message:" << std::endl;
    dump_message (AnExampleMessage::default_instance ());
    std::cout << std::endl;

    // Fill two out of three fields and show what the serialized message looks like.
    AnExampleMessage message;
    auto message_descriptor = message.GetDescriptor ();
    auto message_reflection = message.GetReflection ();
    auto some_integer_field_descriptor = message_descriptor->FindFieldByName ("some_integer");
    if ((some_integer_field_descriptor->type () == google::protobuf::FieldDescriptor::TYPE_UINT32) &&
        (some_integer_field_descriptor->label () == google::protobuf::FieldDescriptor::LABEL_OPTIONAL)) {
        message_reflection->SetUInt32 (&message, some_integer_field_descriptor, 0xDEAD);
    }
    auto some_string_field_descriptor = message_descriptor->FindFieldByName ("some_string");
    if ((some_string_field_descriptor->type () == google::protobuf::FieldDescriptor::TYPE_STRING) &&
        (some_string_field_descriptor->label () == google::protobuf::FieldDescriptor::LABEL_OPTIONAL)) {
        message_reflection->SetString (&message, some_string_field_descriptor, "Hello Protocol Buffers !");
    }
    std::cout << "Message with one integer field and one string field:" << std::endl;
    dump_message (message);
    std::cout << std::endl;

    // We can also debug dump a message.
    std::string dump = message.DebugString ();
    std::cout << "Debug dump:" << std::endl;
    std::cout << dump << std::endl;
}
