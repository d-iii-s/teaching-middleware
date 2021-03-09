#!/usr/bin/env python3

import sys
sys.path.append ('gen-py')

from thrift.server import TServer
from thrift.protocol import TBinaryProtocol
from thrift.transport import TSocket
from thrift.transport import TTransport

from shared import *
from example import *


# Service implementation.
#
# The implementation must provide expected methods.

class ExampleHandler:
    def printString (self, text):
        print ('Thrift server in Python:', text)


# Create the object stack used to implement the server.
handler = ExampleHandler ()
processor = Example.Processor (handler)
transport = TSocket.TServerSocket (SERVER_ADDR, SERVER_PORT)
transport_factory = TTransport.TBufferedTransportFactory ()
protocol_factory = TBinaryProtocol.TBinaryProtocolFactory ()
server = TServer.TSimpleServer (processor, transport, transport_factory, protocol_factory)

# Enter the server request handling loop.
server.serve ()
