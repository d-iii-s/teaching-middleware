# ActiveMQ Based JMS Client With Camel

## Running the example

The clients (producers and consumers) expect the ActiveMQ broker to be running.
Launch the broker in a separate window using the `mvn activemq:run` command.

Use `mvn compile` to compile the example.
Then, use separate windows to run producers and consumers.

- Use `mvn exec:java@initial-producer` to launch an initial producer.
- Use `mvn exec:java@splitter` to launch a splitter that will separate text and number content.
- Use `mvn exec:java@text-consumer` to launch consumer(s) for text content.
- Use `mvn exec:java@number-consumer` to launch consumer(s) for number content.

Consult the `activemq.xml` file to see how the individual clients are connected.

See [the list of technologies that the broker can be connected to](https://camel.apache.org/components/latest/index.html).
See [the list of enterprise integration patterns for inspiration](https://camel.apache.org/components/latest/eips/enterprise-integration-patterns.html).
