#include <string>
#include <iomanip>
#include <iostream>

#include "example_generated.h"
using namespace SomeNamespace;

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


// Helper function to dump buffer builder content.
void dump_buffer_builder (const flatbuffers::FlatBufferBuilder &builder) {
    size_t size = builder.GetSize ();
    unsigned char *buffer = builder.GetBufferPointer ();
    std::cout << "Buffer size is " << size << " bytes." << std::endl;
    dump_buffer (buffer, size);
}


int main (void) {

    // Show what a serialized empty table looks like.
    flatbuffers::FlatBufferBuilder empty_another_table_builder;
    auto empty_another_table = CreateAnotherTable (empty_another_table_builder);
    empty_another_table_builder.Finish (empty_another_table);
    std::cout << "Empty table:" << std::endl;
    dump_buffer_builder (empty_another_table_builder);
    std::cout << std::endl;

    // Fill three out of four fields and show what the serialized table looks like.
    // Constructor argument order follows table field key order.
    flatbuffers::FlatBufferBuilder some_another_table_builder;
    auto some_another_table = CreateAnotherTableDirect (some_another_table_builder, 0x1234, "Hello FlatBuffers !", 0x12345678);
    some_another_table_builder.Finish (some_another_table);
    std::cout << "Table with two integer fields and one string field:" << std::endl;
    dump_buffer_builder (some_another_table_builder);
    std::cout << std::endl;
}
