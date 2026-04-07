# Flink Pipeline

The example demonstrates a Flink pipeline built with the data stream interface.
Socket sources and sinks are used, this makes the example easy to run but
restricts parallelism and checkpointing, as these sources and sinks are
intended for debugging rather than production use.

## Running the example

Use `mvn compile` to compile the example.
Then, use separate windows to run the source and sink components first.

- Use `mvn exec:java@monitor` to launch a monitor that prints counts.
- Use `mvn exec:java@source` to launch a source that produces text lines.
- Use `mvn exec:java@sink` to launch a sink that prints reversed text lines.

After the source and sink components are ready, use separate windows to run the jobs.
Note the use of `exec:exec` rather than `exec:java`, needed to set the classpath correctly.

- Use `mvn exec:exec@rewriter` to launch a job that reverses text lines.
- Use `mvn exec:exec@counter` to launch a job that counts letters in timed window.
