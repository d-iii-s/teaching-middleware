# ActiveMQ Based JMS Server With JNDI

## Requirements

The example requires a standalone ActiveMQ broker installation from http://activemq.apache.org.
Last tested with ActiveMQ 5.15.3 and OpenJDK 1.8.0-162 on Fedora 27.

## Running the example

The clients (producers and consumers) expect the ActiveMQ broker to be running.
Launch the broker using the `activemq start` command from the broker installation.

Use `mvn compile` to compile the example.
Then, use separate windows to run producers and consumers.

- Use `mvn exec:java@initial-producer` to launch an initial producer.
- Use `mvn exec:java@splitter` to launch a splitter that will separate text and number content.
- Use `mvn exec:java@text-consumer` to launch consumer(s) for text content.
- Use `mvn exec:java@number-consumer` to launch consumer(s) for number content.

Consult the `jndi.properties` file to see how the individual clients are connected.
