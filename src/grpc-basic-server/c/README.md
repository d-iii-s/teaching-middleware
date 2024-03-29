# gRPC Based Server

## Running the example

```shell
make
./server &
./client
```

## Tracing execution

Launch with `GRPC_VERBOSITY=info` and `GRPC_TRACE=all` to enable logging.

## Inspecting server API

The example is compiled with server reflection support.
Server reflection permits inspecting the API,
using for example command line tools.

```shell
grpc_cli ls localhost:8888
grpc_cli ls localhost:8888 --l
grpc_cli type localhost:8888 example.AnExampleMessage
grpc_cli call localhost:8888 example.AnExampleService.CloneMessage "some_integer: 8"
```
