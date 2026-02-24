# gRPC Based Secure Server

## Running the example

Use `localhost` as CN when generating the certificate.

```shell
uv run python -m grpc.tools.protoc --python_out="." --grpc_python_out="." --proto_path="." example.proto
openssl req -newkey rsa:4096 -nodes -keyout server.key -x509 -days 365 -out server.crt
./server.py &
./client.py
```
