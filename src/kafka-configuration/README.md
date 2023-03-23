# Kafka Configuration Files

These are minimum configuration files that can be used with the examples.
Copy into the `config` directory of a Kafka installation.

## Single Node

```
bin/kafka-storage.sh format --cluster-id $(bin/kafka-storage.sh random-uuid) --config config/server.properties
bin/kafka-server-start.sh config/server.properties
```

## Three Nodes Cluster

```
export CI="$(bin/kafka-storage.sh random-uuid)"
bin/kafka-storage.sh format --cluster-id ${CI} --config config/server-0.properties
bin/kafka-storage.sh format --cluster-id ${CI} --config config/server-1.properties
bin/kafka-storage.sh format --cluster-id ${CI} --config config/server-2.properties
bin/kafka-server-start.sh config/server-0.properties
bin/kafka-server-start.sh config/server-1.properties
bin/kafka-server-start.sh config/server-2.properties
```
