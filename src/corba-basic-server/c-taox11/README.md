# CORBA TAOx11 Based Server

## Requirements

Last tested with TAOx11 2.5.8 downloaded from https://github.com/remedyit/taox11.git and GCC 9.2.1 on Fedora 31.

## Running the example

```shell
${TAOX11_ROOT}/bin/mwc.pl -type make
make
./server &
./client
```

You can also display the server object IOR:

```shell
tao_catior -f ior
```
