# Thrift Based Server

## Running the example

```shell
python -m venv .venv
. .venv/bin/activate
pip install -r requirements.txt
thrift -gen py example.thrift
./server.py &
./client.py
```
