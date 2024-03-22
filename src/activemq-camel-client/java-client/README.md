# ActiveMQ Based JMS Client with Camel

## Running the example

This is the client part of the example, see also the broker part of the example.

In the client part of the example, launch ActiveMQ in a separate window using the `mvn activemq:run` command.

Use `mvn compile` to compile the example.
Then, use separate windows to run producers and consumers.

- Use `mvn exec:java@initial-producer` to launch an initial producer.
- Use `mvn exec:java@splitter` to launch a splitter that will separate text and number content.
- Use `mvn exec:java@text-consumer` to launch consumer(s) for text content.
- Use `mvn exec:java@number-consumer` to launch consumer(s) for number content.
