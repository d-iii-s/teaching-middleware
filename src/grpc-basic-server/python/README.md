# gRPC Based Server

## Running the example

```shell
uv run python -m grpc.tools.protoc --python_out="." --grpc_python_out="." --proto_path="." example.proto
./server.py &
./client.py
```
