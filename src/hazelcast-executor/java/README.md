# Hazelcast Executor

## Running the example

Use `mvn compile` to compile the example.
The example uses UDP multicast for discovery so possibly disable firewall.
Then, use separate windows to run multiple peer instances with `mvn exec:java` at your leisure.
Observe how the message printing tasks are distributed across multiple peer instances.
