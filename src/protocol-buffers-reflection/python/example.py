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


# Fill two out of three fields and show what the serialized message looks like.
message = AnExampleMessage ()
setattr (message, 'some_integer', 0xDEAD)
setattr (message, 'some_string', 'Hello Protocol Buffers !')
print ('Message with one integer field and one string field:')
dumpMessage (message)
print ()

# We can also debug dump a message.
print ('Debug dump:')
print (message)
