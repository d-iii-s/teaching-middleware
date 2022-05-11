# OSGi Basic Server

## Running the example

The example requires Apache Felix with Apache Felix HTTP bundles.
Place the current versions of `org.apache.felix.http.jetty.jar`
and `org.apache.felix.http.servlet-api.jar` into the `bundle`
directory of your Apache Felix installation.

Use `mvn install` to compile the example and add the example bundle into the local OBR repository index.

```
java -jar bin/felix.jar
obr:repos add file:/path/to/home/.m2/repository/repository.xml
obr:deploy osgi-basic-server
felix:lb
felix:start 9 # Number of the osgi-basic-server bundle
```

The server listens at `localhost:8080`.

Try stopping and starting the Apache Felix HTTP Jetty service to see the example register the servlet.
