# ActiveMQ Based JMS Client With JNDI

## Running the example

The clients (producers and consumers) expect the ActiveMQ broker to be running.
Launch the broker in a separate window using the `mvn activemq:run` command.

Use `mvn compile` to compile the example.
Then, use separate windows to run producers and consumers.

- Use `mvn exec:java@initial-producer` to launch an initial producer.
- Use `mvn exec:java@splitter` to launch a splitter that will separate text and number content.
- Use `mvn exec:java@text-consumer` to launch consumer(s) for text content.
- Use `mvn exec:java@number-consumer` to launch consumer(s) for number content.

Consult the `jndi.properties` file to see how the individual clients are connected.

To prevent producers and consumers terminating on broker failure, use
`mvn "-DBROKER_BIND_URL=failover:(tcp://localhost:61616)" ...`.
