# Speech Recognition with gRPC Interface

This example makes use of the gRPC interface definition to invoke the Goole Speech Recognition API.
For common use, Google provides a client library that wraps the gRPC interface.
The purpose of the example is to show that there is gRPC underneath.

## Getting audio sample

Use for example http://www.voxforge.org. The service can accept WAV and FLAC files.

## Running the example

Before running the example, register with Google Cloud, create a project,
enable the Google Speech Recognition API within the project,
and create and download a service account key.
Put the key in a file called `account.json`.

```
> python -m virtualenv .
> . bin/activate
> pip install requests grpcio google-auth
> git clone http://github.com/googleapis/googleapis.git
> cd googleapis
> make OUTPUT=.. LANGUAGE=python GRPCPLUGIN=$(pkg-config --variable=exec_prefix grpc++)/bin/grpc_python_plugin
> cd ..
> find google -type d -exec touch {}/__init__.py \;
> python client.py /path/to/speech.wav
```
