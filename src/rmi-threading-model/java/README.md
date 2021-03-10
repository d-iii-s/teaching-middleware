# Java RMI Based Server

The example performs various simple calls that demonstrate the properties of the RMI threading model.

## Running the example

```shell
mvn compile
mvn exec:java@server &
mvn exec:java@client
```

Also run this with `watch -n 1 lsof -i tcp -a -c java` to see connection creation.
