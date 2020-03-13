# CORBA omniORB Based Server

## Requirements

Last tested with omniORB 4.2.3 and GCC 9.2.1 on Fedora 31.

## Running the example

```shell
make
./server &
./client
```

You can also display the server object IOR:

```shell
catior $(cat ior)
```
