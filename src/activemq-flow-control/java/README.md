# ActiveMQ Based JMS Client for Flow Control Demonstration

This example contains producers that generate messages at maximum speed,
and consumers that either consume messages at maximum speed, or consume
one message per second. Running combinations of fast and slow consumers
reveals flow control behavior.

See the `activemq.xml` configuration file for relevant flow control settings:

- `sendFailIfNoSpace` - block or fail in producer on no space
- `sendFailIfNoSpaceAfterTimeout` - wait or fail after timeout on no space
- `memoryUsage limit` - broker memory usage cap
- `storeUsage limit` - broker persistent store usage cap
- `tempUsage limit` - broker temporary store usage cap
- `producerFlowControl` - apply flow control on producer side
- `cursorMemoryHighWaterMark` - when to start evicting messages
- `storeUsageHighWaterMark` - when to consider store full
- `queuePrefetch` - how much to buffer queue messages on consumer side
- `topicPrefetch` - how much to buffer topic messages on consumer side

## Running the example

The clients (producers and consumers) expect the ActiveMQ broker to be running.
Launch the broker in a separate window using the `mvn activemq:run` command.

Use `mvn compile` to compile the example.
Then, use separate windows to run producers and consumers at your leisure.

- Use `mvn exec:java@queue-producer` to launch a fast queue producer.
- Use `mvn exec:java@queue-fast-consumer` to launch a fast queue consumer.
- Use `mvn exec:java@queue-slow-consumer` to launch a slow queue consumer.

- Use `mvn exec:java@topic-producer` to launch a fast topic producer.
- Use `mvn exec:java@topic-fast-consumer` to launch a fast topic consumer.
- Use `mvn exec:java@topic-slow-consumer` to launch a slow topic consumer.
