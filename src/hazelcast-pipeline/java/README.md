# Hazelcast Pipeline

## Running the example

Use `mvn compile` to compile the example.
The example uses UDP multicast for discovery so possibly disable firewall.
Then execute:
- a plain worker node with `mvn exec:java@node`,
- a worker node that instantiates a finite batch source with `mvn exec:java@batch`, or
- a worker node that instantiates an infinite stream source with `mvn exec:java@stream`.

Note how the behavior of the streams differs depending on the source:

- The batch source is distributed, so it runs on all nodes available.

- The stream source is not distributed, so it runs on one node only.
  It is also not fault tolerant, so migration to another node
  results in a restart.
