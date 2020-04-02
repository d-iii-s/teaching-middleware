# Kafka Client

## Running the example

The clients (producer and consumer) expect a Kafka server to be running on `localhost`.
First launch a ZooKeeper instance using the `zkServer.sh start` command.
Then launch a Kafka instance using the `kafka-server-start.sh` command.

Use `mvn compile` to compile the example.
Then, use separate windows to run producers and consumers at your leisure.

- Use `mvn exec:java@producer` to launch a producer that puts data into a topic.
- Use `mvn exec:java@consumer` to launch a consumer that prints data from a topic.

The Kafka cluster can also be explored using the command line interface.

Use `bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic SomeTopic` to receive messages.
Use `bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic SomeTopic --from-beginning` to receive messages including those from history.
Use `bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic SomeTopic --partition 0 --offset 0` to receive messages from specified partition and offset.

Use `bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic SomeTopic --describe` to inspect a topic.
Use `bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic SomeTopic --alter --partitions 8` to increase the number of partitions.

Use `bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group BasicConsumer --describe` to inspect a consumer group.
