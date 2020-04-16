# Hazelcast Basic Peer

## Running the example

Use `mvn compile` to compile the example.
The example uses UDP multicast for discovery so possibly disable firewall.
Then, use separate windows to run multiple peer instances with `mvn exec:java` at your leisure.
Observe how the map entries are distributed across multiple peer instances
and how killing individual instances does not lose data.

Update the configuration settings to change the backup count and test again.
