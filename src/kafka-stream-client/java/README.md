# Kafka Stream Client

## Running the example

The individual clients expect a Kafka server to be running on localhost.
First launch a ZooKeeper instance using the `zkServer.sh start` command.
Then launch a Kafka instance using the `kafka-server-start.sh` command.

Use `mvn compile` to compile the example.
Then, use separate windows to run clients at your leisure.

- Use `mvn exec:java@producer` to launch a producer that produces text lines.
- Use `mvn exec:java@rewriter` to launch a stream processor that reverses text lines.
- Use `mvn exec:java@consumer` to launch a consumer that prints reversed text lines.
- Use `mvn exec:java@counter` to launch a stream processor that counts letters.
- Use `mvn exec:java@monitor` to launch a consumer that prints counts.

The Kafka cluster can also be explored using the command line interface.
