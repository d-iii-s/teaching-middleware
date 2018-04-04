# ActiveMQ Based JMS Server

## Requirements

The example requires a standalone ActiveMQ broker installation from http://activemq.apache.org.
Last tested with ActiveMQ 5.15.3 and OpenJDK 1.8.0-162 on Fedora 27.

## Running the example

The clients (producers and consumers) expect the ActiveMQ broker to be running.
Launch the broker using the `activemq start` command from the broker installation.

Use `mvn compile` to compile the example.
Then, use separate windows to run producers and consumers at your leisure.

- Use `mvn exec:java@queue-producer` to launch a producer that puts text messages into a queue.
- Use `mvn exec:java@queue-consumer` to launch a consumer that prints text messages from a queue.
- Use `mvn exec:java@verbose-queue-consumer` to launch a consumer that prints the messages with headers.

- Use `mvn exec:java@topic-producer` to launch a producer that puts text messages into a topic.
- Use `mvn exec:java@topic-consumer` to launch a consumer that prints text messages from a topic.
- Use `mvn exec:java@verbose-topic-consumer` to launch a consumer that prints the messages with headers.
