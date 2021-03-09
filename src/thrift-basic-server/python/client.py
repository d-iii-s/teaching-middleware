#!/usr/bin/env python3

import sys
sys.path.append ('gen-py')

from thrift import Thrift
from thrift.protocol import TBinaryProtocol
from thrift.transport import TSocket
from thrift.transport import TTransport

from shared import *
from example import *


# Create the object stack used to connect to the server.
socket = TSocket.TSocket (SERVER_ADDR, SERVER_PORT)
transport = TTransport.TBufferedTransport (socket)
protocol = TBinaryProtocol.TBinaryProtocol (transport)
client = Example.Client (protocol)

transport.open ()
client.printString ('Hello from Thrift in Python !')
transport.close ()
