# Thrift Based Server

## Running the example

Make sure the `thrift` package version in `pyproject.toml` matches the local installation of the interface compiler.

```shell
thrift -gen py example.thrift
./server.py &
./client.py
```
