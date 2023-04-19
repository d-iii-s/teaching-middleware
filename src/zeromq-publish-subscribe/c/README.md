# ZeroMQ Publish Subscribe

## Running the example

```shell
make
./publisher &
./subscriber
```

## Transports

Multiple transports are supported.
Simulated packet loss can be used
to evaluate transport behavior.

```shell
tc qdisc add dev lo root netem loss 33%
```
