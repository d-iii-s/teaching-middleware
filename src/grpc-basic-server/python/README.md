# gRPC Based Server

## Running the example

```
> python3 -m venv .
> . bin/activate
> pip install grpcio
> protoc --plugin=protoc-gen-grpc=$(pkg-config --variable=exec_prefix grpc++)/bin/grpc_python_plugin --python_out="." --grpc_out="." example.proto
> python server.py &
> python client.py
```
