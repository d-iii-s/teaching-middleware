# Kafka Client

## Running the example

The clients (producer and consumer) expect a Kafka server to be running on `localhost`.
Launch a Kafka instance using the `kafka-server-start.sh` command.

Use `mvn compile` to compile the example.
Then, use separate windows to run producers and consumers at your leisure.

- Use `mvn exec:java@producer` to launch a producer that puts data into a topic.
- Use `mvn exec:java@consumer` to launch a consumer that prints data from a topic.

The Kafka cluster can also be explored using the command line interface.

```shell
BS="--bootstrap-server localhost:9092"

# Receive records (with keys).
bin/kafka-console-consumer.sh ${BS} --topic SomeTopic
bin/kafka-console-consumer.sh ${BS} --topic SomeTopic --key-deserializer org.apache.kafka.common.serialization.IntegerDeserializer --property print.key=true

# Receive records starting with specific position.
bin/kafka-console-consumer.sh ${BS} --topic SomeTopic --from-beginning
bin/kafka-console-consumer.sh ${BS} --topic SomeTopic --partition 0 --offset 0

# Alter partition count.
bin/kafka-topics.sh ${BS} --topic SomeTopic --describe
bin/kafka-topics.sh ${BS} --topic SomeTopic --alter --partitions 8

# Inspect consumer group.
bin/kafka-consumer-groups.sh ${BS} --group BasicConsumer --describe
```
