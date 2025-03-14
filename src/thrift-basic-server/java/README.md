# Thrift Based Server

## Running the example

Make sure the `thrift.version` setting in `pom.xml` matches the local installation of the interface compiler.

```shell
> mvn compile
> mvn exec:java@server &
> mvn exec:java@client
```
