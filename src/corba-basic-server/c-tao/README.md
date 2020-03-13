# CORBA TAO Based Server

## Requirements

Last tested with TAO 2.5.8 downloaded from http://download.opensuse.org/repositories/devel:libraries:/ACE:/micro and GCC 9.2.1 on Fedora 31.

## Running the example

```shell
make
./server &
./client
```

You can also display the server object IOR:

```shell
tao_catior -f ior
```
