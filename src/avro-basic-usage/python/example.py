#!/usr/bin/env python3

import io
import json

import avro.io
import avro.schema


DUMP_LINE_SIZE = 16


def dumpBuffer (buffer):
    for offset in range (0, len (buffer), DUMP_LINE_SIZE):
        print ('{offset:08x}'.format (offset = offset), end = '')
        for position in range (0, DUMP_LINE_SIZE):
            if offset + position < len (buffer):
                print (' {data:02x}'.format (data = buffer [offset + position]), end = '')
            else:
                print (' --', end = '')
        print (' ', end = '')
        for position in range (0, DUMP_LINE_SIZE):
            if offset + position < len (buffer):
                if buffer [offset + position] < ord (' '):
                    print ('.', end = '')
                else:
                    print ('{data:1c}'.format (data = buffer [offset + position]), end = '')
            else:
                print ('.', end = '')
        print ()


def dumpMessage (message, schema):
    stream = io.BytesIO ()
    encoder = avro.io.BinaryEncoder (stream)
    writer = avro.io.DatumWriter (schema)
    writer.write (message, encoder)
    buffer = stream.getvalue ()
    print ('Buffer size is', len (buffer), 'bytes.')
    dumpBuffer (buffer)


# Load schema to work with.
types_registry = avro.schema.Names ()
schema_file = open ('example.avsc', 'r')
schema_dict = json.load (schema_file)
schema_parsed = avro.schema.make_avsc_object (schema_dict, types_registry)
schema_an_example_message = types_registry.get_name ('example.AnExampleMessage')
schema_more_example_messages = types_registry.get_name ('example.MoreExampleMessages')

# Show what a serialized empty message looks like.
print ('Default message:')
dumpMessage ({ 'some_integer' : 0, 'some_string' : '', 'some_more_strings' : [] }, schema_an_example_message)
print ()

# Fill two out of four fields and show what the serialized message looks like.
message_one = {
    'some_integer' : 0xDEAD,
    'some_string' : 'Hello Avro !',
    'some_more_strings' : [],
}
print ('Message with one integer field and one string field:')
dumpMessage (message_one, schema_an_example_message)
print ()

# Fill the second integer field and show what the serialized message looks like.
message_two = message_one.copy ()
message_two ['another_integer'] = 0xBEEF
print ('Message with two integer fields and one string field:')
dumpMessage (message_two, schema_an_example_message)
print ()

# Create message instance with repeated fields.
messages = {
    'messages' : [ message_one, message_two ],
}
print ('Message with two more embedded messages:')
dumpMessage (messages, schema_more_example_messages)
print ()

# Encode and decode message instance to show it also works.
output_stream = io.BytesIO ()
encoder = avro.io.BinaryEncoder (output_stream)
writer = avro.io.DatumWriter (schema_more_example_messages)
writer.write (messages, encoder)
buffer = output_stream.getvalue ()
input_stream = io.BytesIO (buffer)
decoder = avro.io.BinaryDecoder (input_stream)
reader = avro.io.DatumReader (schema_more_example_messages)
parsed_messages = reader.read (decoder)
print ('Encoded and decoded message:')
print (parsed_messages)
