# Zookeper Client

## Running the example

The clients (producer and consumers) expect a ZooKeeper server to be running on localhost.
Launch the server in a separate window using the `zkServer.sh start` command.

Use `mvn compile` to compile the example.
Then, use separate windows to first run a producer and then multiple consumers at your leisure.

- Use `mvn exec:java@data-producer` to launch a producer that puts data into a znode.
- Use `mvn exec:java@data-consumer` to launch a consumer that prints data from a znode.

The ZooKeeper service can also be explored using the command line interface in the `zkCli.sh` command.
Use the `ls /` command to see the data node used to exchange data between producer and consumers.
Use the `get /verse` command to see how the version of the data changes on updates.
Use the `set /verse "It seems very pretty."` command to publish data.
Restart the ZooKeeper service to check durability.
