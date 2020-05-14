# OSGi Basic Server

## Running the example

The example requires Apache Felix.

Use `mvn install` to compile the example and install the example bundle into the local OBR repository.
Use `mvn bundle:index` to index the bundle dependencies for use in the local OBR repository.
Then, use Apache Felix to load the bundle:

```
java -jar bin/felix.jar
obr:repos add file:/your/home/path/.m2/repository/repository.xml
obr:deploy osgi-basic-server
lb
start 10 # Number of the Apache Felix Http Jetty bundle
start 7 # Number of the osgi-basic-server bundle
```

The server listens at `localhost:8080`.
