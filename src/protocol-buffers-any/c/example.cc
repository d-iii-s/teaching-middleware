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

    // Create message instance.
    AnExampleMessage message;
    message.set_some_integer (0xDEAD);
    message.set_another_integer (0xBEEF);
    message.set_some_string ("Hello Protocol Buffers !");
    std::cout << "Message:" << std::endl;
    dump_message (message);
    std::cout << std::endl;

    // Message instance with an empty any.
    MoreExampleMessages messages;
    std::cout << "Message with an empty any:" << std::endl;
    dump_message (messages);
    std::cout << std::endl;

    // Message instance with another message in any.
    messages.mutable_something ()->PackFrom (message);
    std::cout << "Message with another message in any:" << std::endl;
    dump_message (messages);
    std::cout << std::endl;

    // We can also debug dump a message.
    std::string dump = messages.DebugString ();
    std::cout << "Debug dump:" << std::endl;
    std::cout << dump << std::endl;
}
