# ActiveMQ Based JMS Client with Camel

## Running the example

This is the broker part of the example, see also the client part of the example.

In the broker part of the example, launch Camel in a separate window using the `mvn compile camel:run` command.

Consult the `routes.yaml` file to see how the individual clients are connected.

See [the list of technologies that the broker can be connected to](https://camel.apache.org/components/latest/index.html).
See [the list of enterprise integration patterns for inspiration](https://camel.apache.org/components/latest/eips/enterprise-integration-patterns.html).

## Conecting more technologies

For a trivial example of an additional sink, edit the `routes.yaml` configuration and add another route:

```yaml
- route:
     id: "temporary-output"
     from:
         uri: "activemq:topic:SplitterTargetText"
         steps:
         - to:
            uri: "file://target/output"
```

This will make the text content also go to the `target/output` directory of the example. For the other direction, use:

```yaml
- route:
    id: "temporary-input"
    from:
        uri: "file://target/input"
        steps:
        - to:
            uri: "activemq:queue:SplitterSource?jmsMessageType=Text"
```
