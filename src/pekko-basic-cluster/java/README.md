# Pekko Cluster

## Running the example

Use `mvn compile` to compile the example.

Start the guardian node in one terminal:

```shell
mvn exec:java@guardian
```

Start one or more child nodes in other terminals:

```shell
mvn exec:java@child
```
