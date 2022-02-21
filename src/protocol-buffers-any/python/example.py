#!/usr/bin/env python3

from example_pb2 import *


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


def dumpMessage (message):
    print ('Buffer size is', message.ByteSize (), 'bytes.')
    dumpBuffer (message.SerializeToString ())


# Create message instance.
message = AnExampleMessage ()
message.some_integer = 0xDEAD
message.another_integer = 0xBEEF
message.some_string = 'Hello Protocol Buffers !'
print ('Message:')
dumpMessage (message)
print ()

# Message instance with an empty any.
messages = MoreExampleMessages ()
print ('Message with an empty any:')
dumpMessage (messages)
print ()

# Message instance with another message in any.
messages.something.Pack (message)
print ('Message with another message in any:')
dumpMessage (messages)
print ()

# We can also debug dump a message.
print ('Debug dump:')
print (messages)
