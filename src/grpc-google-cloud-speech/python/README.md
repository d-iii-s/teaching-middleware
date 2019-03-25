# Speech Recognition with gRPC Interface

## Getting audio sample

Use for example http://www.voxforge.org. The service can accept WAV and FLAC files.

## Running the example

```
> python3 -m venv .
> . bin/activate
> pip install requests protobuf grpcio google-auth
> git clone http://github.com/googleapis/googleapis.git
> cd googleapis
> make OUTPUT=.. LANGUAGE=python PROTOINCLUDE=$(pkg-config --variable=includedir protobuf) GRPCPLUGIN=$(pkg-config --variable=prefix grpc++)/bin/grpc_python_plugin google/cloud/speech/v1/cloud_speech.pb.cc
> cd ..
> python client.py /path/to/speech.wav
```
