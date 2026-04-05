# Flink Pipeline

## Running the example

Use `mvn compile` to compile the example.
Then, use separate windows to run the source and sink components first.

- Use `mvn exec:java@monitor` to launch a monitor that prints counts.
- Use `mvn exec:java@source` to launch a source that produces text lines.
- Use `mvn exec:java@sink` to launch a sink that prints reversed text lines.

After the source and sink components are ready, use separate windows to run the jobs.

- Use `mvn exec:exec@rewriter` to launch a job that reverses text lines.
- Use `mvn exec:exec@counter` to launch a job that counts letters in timed window.
