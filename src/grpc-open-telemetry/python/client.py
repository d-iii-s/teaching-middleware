#!/usr/bin/env python3

import grpc

from opentelemetry import trace
from opentelemetry.sdk.trace import TracerProvider
from opentelemetry.sdk.trace.export import ConsoleSpanExporter, SimpleSpanProcessor
from opentelemetry.exporter.otlp.proto.grpc.trace_exporter import OTLPSpanExporter
from opentelemetry.instrumentation.grpc import GrpcInstrumentorClient

from shared import *

from example_pb2 import *
from example_pb2_grpc import *


# Create a minimum tracing infrastructure.
tracer_provider = TracerProvider ()
# A span exporter that exports to a local endpoint.
span_exporter = OTLPSpanExporter(endpoint='localhost:4317', insecure = True)
# A span exporter that exports to the console.
# span_exporter = ConsoleSpanExporter ()
span_processor = SimpleSpanProcessor (span_exporter)
tracer_provider.add_span_processor (span_processor)
trace.set_tracer_provider (tracer_provider)

# Instrument the grpc module to inject tracing interceptors.
grpc_client_instrumentor = GrpcInstrumentorClient ()
grpc_client_instrumentor.instrument ()

# Create the channel used to connect to the server.
with grpc.insecure_channel (SERVER_ADDR) as channel:

    # Create a stub object that provides the service interface.
    stub = AnExampleServiceStub (channel)

    message = AnExampleMessage ()
    message.some_integer = 0xDEAD
    message.some_string = 'Hello from gRPC in Python !'
    print ('Message:')
    print (message)

    # Call the service through the stub object.
    response = stub.CloneMessage (message)

    print ('Response:')
    print (response)
