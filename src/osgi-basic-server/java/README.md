# OSGi Basic Server

## Running the example

The example requires Apache Felix.

Use `mvn install` to compile the example and install the example bundle into the local OBR repository.
Use `mvn bundle:index` to index the bundle dependencies for use in the local OBR repository.
Then, use Apache Felix to load the bundle:

```
java -jar bin/felix.jar
obr:repos add file:/path/to/home/.m2/repository/repository.xml
obr:deploy osgi-basic-server
felix:lb
felix:start 9 # Number of the osgi-basic-server bundle
felix:start 13 # Number of the Apache Felix HTTP Jetty bundle
```

The server listens at `localhost:8080`.

Try stopping and starting the Apache Felix HTTP Jetty service to see the example register the servlet.
