# gRPC Based Server with Open Telemetry Tracing Support

## Running the example

The example assumes a running Open Telemetry gRPC receiver on `http://localhost:4317`.
One such receiver is available in Jaeger and can be run in an ephemeral container with
`podman run --rm -p 4317:4317 -p 16686:16686 jaegertracing/jaeger:latest`. An interactive
user interface is available at `http://localhost:16686`.

As an alternative to Jaeger, a console exporter can be enabled in the `client.py` and `server.py` files.

```shell
python -m venv .venv
. .venv/bin/activate
pip install -r requirements.txt
python -m grpc.tools.protoc --python_out="." --grpc_python_out="." --proto_path="." example.proto
./server.py &
./client.py
```
