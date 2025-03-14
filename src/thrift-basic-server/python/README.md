# Thrift Based Server

## Running the example

Make sure the `thrift` package version in `requirements.txt` matches the local installation of the interface compiler.

```shell
python -m venv .venv
. .venv/bin/activate
pip install -r requirements.txt
thrift -gen py example.thrift
./server.py &
./client.py
```
