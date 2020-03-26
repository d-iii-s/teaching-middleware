# ActiveMQ Based JMS Client with Simplified API

## Running the example

The clients (producers and consumers) expect the ActiveMQ Artemis broker to be running locally.
You can download the broker from `https://activemq.apache.org/components/artemis/download`.
Create the broker configuration and run the broker:

```shell
BROKER="$(mktemp --directory)"
artemis create --user admin --password secret --allow-anonymous "${BROKER}"
"${BROKER}/bin/artemis" run
```

Use `mvn compile` to compile the example.
Then, use separate windows to run producers and consumers at your leisure.

- Use `mvn exec:java@queue-producer` to launch a producer that puts text messages into a queue.
- Use `mvn exec:java@queue-consumer` to launch a consumer that prints text messages from a queue.
- Use `mvn exec:java@verbose-queue-consumer` to launch a consumer that prints the messages with headers.

- Use `mvn exec:java@topic-producer` to launch a producer that puts text messages into a topic.
- Use `mvn exec:java@topic-consumer` to launch a consumer that prints text messages from a topic.
- Use `mvn exec:java@verbose-topic-consumer` to launch a consumer that prints the messages with headers.
- Use `mvn exec:java@durable-topic-consumer` to launch a consumer that gets the messages from durable storage.

You can connect to the web console at `http://localhost:8161/console` to observe and control the broker.
You can also use the command line to produce and consume messages:

```shell
artemis producer --destination queue://ExampleQueue --message "Hello from Command Line Producer !" --message-count 8
artemis producer --destination topic://ExampleTopic --message "Hello from Command Line Producer !" --message-count 8
```

To prevent producers and consumers terminating on broker failure, use
`mvn "-DBROKER_BIND_URL=tcp://localhost:61616?reconnectAttempts=-1" ...`.
