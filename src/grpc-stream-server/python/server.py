#!/usr/bin/env python3

import grpc
import time

from concurrent import futures

from shared import *

from example_pb2 import *
from example_pb2_grpc import *


# Service implementation.
#
# The implementation inherits from a generated base class.

class MyServicer (AnExampleServiceServicer):
    def EchoMessages (self, request_iterator, context):
        print ('Call.')
        for message in request_iterator:
            print ('Request:')
            print (message)
            time.sleep (0.666)
            yield message
        print ('Done.')


# Create the server object.
#
# The server object represents the server runtime.
# It needs to be told what service to provide
# and what port to listen on.

server = grpc.server (futures.ThreadPoolExecutor (max_workers = SERVER_THREAD_COUNT))
add_AnExampleServiceServicer_to_server (MyServicer (), server)
server.add_insecure_port (SERVER_ADDR)
server.start ()
server.wait_for_termination ()
