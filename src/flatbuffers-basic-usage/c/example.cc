#include <assert.h>

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
    flatbuffers::FlatBufferBuilder empty_table_generic_builder;
    auto empty_another_table = CreateAnotherTable (empty_table_generic_builder);
    empty_table_generic_builder.Finish (empty_another_table);
    std::cout << "Empty table:" << std::endl;
    dump_buffer_builder (empty_table_generic_builder);
    std::cout << std::endl;

    // Fill three out of four fields and show what the serialized table looks like.
    // Constructor argument order follows table field key order.
    flatbuffers::FlatBufferBuilder another_table_generic_builder;
    auto some_another_table = CreateAnotherTableDirect (another_table_generic_builder, 0x1234, "Hello FlatBuffers !", 0x12345678);
    another_table_generic_builder.Finish (some_another_table);
    std::cout << "Table with two integer fields and one string field:" << std::endl;
    dump_buffer_builder (another_table_generic_builder);
    std::cout << std::endl;

    // Use typed builder to fill some fields and show what the serialized table looks like.
    flatbuffers::FlatBufferBuilder yet_another_table_generic_builder;
    AnotherTableBuilder yet_another_table_typed_builder (yet_another_table_generic_builder);
    yet_another_table_typed_builder.add_a_float (1.2345678);
    yet_another_table_typed_builder.add_a_long (0x12345678);
    auto yet_another_table = yet_another_table_typed_builder.Finish ();
    yet_another_table_generic_builder.Finish (yet_another_table);
    std::cout << "Table with one integer field and one float field:" << std::endl;
    dump_buffer_builder (yet_another_table_generic_builder);
    std::cout << std::endl;

    // Show what a serialized union looks like.
    flatbuffers::FlatBufferBuilder root_table_builder;
    auto some_string = root_table_builder.CreateString ("Twice !");
    int32_t some_enum_array [] { SomeEnum::SomeEnum_One };
    auto some_enum_vector = root_table_builder.CreateVector (some_enum_array, 1);
    auto some_table = CreateSomeTable (root_table_builder, 0x55, 0x55AA, some_string, some_string, some_enum_vector);
    auto root_table = CreateRootTable (root_table_builder, SomeUnion_SomeTable, some_table.Union ());
    root_table_builder.Finish (root_table);
    std::cout << "Table with union:" << std::endl;
    dump_buffer_builder (root_table_builder);
    std::cout << std::endl;

    // Decode earlier buffer instance.
    uint8_t *buffer = root_table_builder.GetBufferPointer ();
    int size = root_table_builder.GetSize ();
    auto verifier = flatbuffers::Verifier (buffer, size);
    assert (VerifyRootTableBuffer (verifier));
    auto decoded_root_table = GetRootTable (buffer);
    std::cout << "Decoded buffer:" << std::endl;
    if (decoded_root_table->content_type () == SomeUnion::SomeUnion_SomeTable) {
        auto decoded_some_table = static_cast<const SomeTable *> (decoded_root_table->content ());
        std::cout << decoded_some_table->a_byte () <<
            " - " << decoded_some_table->an_int () <<
            " - " << decoded_some_table->a_string ()->str () <<
            std::endl <<
            std::endl;
    }
}
