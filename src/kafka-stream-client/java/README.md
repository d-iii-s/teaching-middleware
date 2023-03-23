# Kafka Stream Client

## Running the example

The individual clients expect a Kafka server to be running on `localhost`.
Launch a Kafka instance using the `kafka-server-start.sh` command.

Use `mvn compile` to compile the example.
Then, use separate windows to run clients at your leisure.

- Use `mvn exec:java@producer` to launch a producer that produces text lines.
- Use `mvn exec:java@rewriter` to launch a stream processor that reverses text lines.
- Use `mvn exec:java@consumer` to launch a consumer that prints reversed text lines.
- Use `mvn exec:java@counter` to launch a stream processor that counts letters.
- Use `mvn exec:java@monitor` to launch a consumer that prints counts.

## Exploring the cluster

The Kafka cluster can also be explored using the command line interface.

```shell
BS="--bootstrap-server localhost:9092"

bin/kafka-topics.sh ${BS} --list
bin/kafka-topics.sh ${BS} --topic ProducerTopic --describe
bin/kafka-topics.sh ${BS} --topic ProducerTopic --alter --partitions=8

bin/kafka-console-consumer.sh ${BS} --topic ProducerTopic --property print.key=true \
    --key-deserializer org.apache.kafka.common.serialization.IntegerDeserializer
bin/kafka-console-consumer.sh ${BS} --topic ConsumerTopic --property print.key=true \
    --key-deserializer org.apache.kafka.common.serialization.IntegerDeserializer

bin/kafka-console-consumer.sh ${BS} --topic CountsTopic --property print.key=true \
    --value-deserializer org.apache.kafka.common.serialization.LongDeserializer

bin/kafka-console-consumer.sh ${BS} --topic Counter-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition \
    --property print.key=true
bin/kafka-console-consumer.sh ${BS} --topic Counter-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition \
    --property print.key=true --partition 0
bin/kafka-console-consumer.sh ${BS} --topic Counter-KSTREAM-AGGREGATE-STATE-STORE-0000000003-repartition \
    --property print.key=true --partition 1

bin/kafka-console-consumer.sh ${BS} --topic Counter-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog \
    --property print.key=true --value-deserializer org.apache.kafka.common.serialization.LongDeserializer
bin/kafka-console-consumer.sh ${BS} --topic Counter-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog \
    --property print.key=true --value-deserializer org.apache.kafka.common.serialization.LongDeserializer \
    --partition 0
bin/kafka-console-consumer.sh ${BS} --topic Counter-KSTREAM-AGGREGATE-STATE-STORE-0000000003-changelog \
    --property print.key=true --value-deserializer org.apache.kafka.common.serialization.LongDeserializer \
    --partition 1

bin/kafka-consumer-groups.sh ${BS} --list
bin/kafka-consumer-groups.sh ${BS} --group Counter --describe
```
