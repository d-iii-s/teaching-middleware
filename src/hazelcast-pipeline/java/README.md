# Hazelcast Pipeline

## Running the example

Use `mvn compile` to compile the example.
The example uses UDP multicast for discovery so possibly disable firewall.
Then, use separate windows to run multiple peer instances with `mvn exec:java` at your leisure.

Note how, after a peer terminates, other peers take over the job submitted by the terminated peer.
Since the source used in the example is neither distributed nor fault tolerant, job migration
results in simple restart.
