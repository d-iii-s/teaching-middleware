#!/usr/bin/env python3

import grpc
import time
import threading

from shared import *

from example_pb2 import *
from example_pb2_grpc import *


# Create the channel used to connect to the server.
with grpc.insecure_channel (SERVER_ADDR) as channel:

    # Create a stub object that provides the service interface.
    stub = AnExampleServiceStub (channel)

    message = AnExampleMessage ()
    message.some_integer = 12345
    message.some_string = 'Hello gRPC !'
    print ('Message:')
    print (message)

    # Synchronize the sending and receiving activities.
    continueSemaphore = threading.Semaphore (0)

    # Prepare the iterator that will send messages through the stream.
    def RequestGenerator ():
        for i in range (8):
            time.sleep (0.666)
            yield (message)
            continueSemaphore.acquire ()

    # Call the service through the stub object.
    response_iterator = stub.EchoMessages (RequestGenerator ())
    for response in response_iterator:
        print ('Response:')
        print (response)
        continueSemaphore.release ()
