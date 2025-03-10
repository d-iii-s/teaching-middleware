#!/usr/bin/env python3

import grpc

from opentelemetry import trace
from opentelemetry.sdk.trace import TracerProvider
from opentelemetry.sdk.trace.export import ConsoleSpanExporter, SimpleSpanProcessor
from opentelemetry.exporter.otlp.proto.grpc.trace_exporter import OTLPSpanExporter
from opentelemetry.instrumentation.grpc import GrpcInstrumentorServer

from concurrent import futures

from shared import *

from example_pb2 import *
from example_pb2_grpc import *


# Service implementation.
class MyServicer (AnExampleServiceServicer):
    def CloneMessage (self, request, context):
        # Print the input.
        print ('gRPC server in Python cloning:')
        print (request)
        # Create the response by copying the request twice.
        response = MoreExampleMessages ()
        response.messages.add ().CopyFrom (request)
        response.messages.add ().CopyFrom (request)
        return response

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
grpc_client_instrumentor = GrpcInstrumentorServer ()
grpc_client_instrumentor.instrument ()

# Initialize and execute the server.
server = grpc.server (futures.ThreadPoolExecutor (max_workers = SERVER_THREAD_COUNT))
add_AnExampleServiceServicer_to_server (MyServicer (), server)
server.add_insecure_port (SERVER_ADDR)
server.start ()
server.wait_for_termination ()
