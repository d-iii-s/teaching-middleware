#include <iostream>

using namespace std;

#include <grpc++/grpc++.h>
using namespace grpc;

#include "example.grpc.pb.h"
using namespace example;

#include "shared.h"


// Service implementation.
//
// The implementation uses generic callback service base class.

class MyServiceReactor : public ServerGenericBidiReactor {

    ByteBuffer request;
    ByteBuffer response;

public:

    MyServiceReactor () {
        // Initiate read operation, content ready in OnReadDone.
        StartRead (&request);
    }

    void OnReadDone (bool ok) override {
        if (ok) {
            Status status;

            AnExampleMessage typed_request;
            status = GenericDeserialize<ProtoBufferReader, AnExampleMessage> (&request, &typed_request);
            if (!status.ok ()) {
                Finish (status);
                return;
            }

            // Print the input.
            cout << "gRPC server in C++ cloning:" << endl;
            cout << typed_request.DebugString () << endl;

            // Create the response by copying the request twice.
            MoreExampleMessages typed_response;
            typed_response.add_messages ()->CopyFrom (typed_request);
            typed_response.add_messages ()->CopyFrom (typed_request);

            bool owner;
            status = GenericSerialize<ProtoBufferWriter, MoreExampleMessages> (typed_response, &response, &owner);
            if (!status.ok ()) {
                Finish (status);
                return;
            }

            StartWrite (&response);
        }
    }

    void OnWriteDone (bool ok) override {
        if (ok) {
            Finish (Status::OK);
        }
    }

    void OnDone () override {
        delete this;
    }
};

class MyService : public CallbackGenericService {
    ServerGenericBidiReactor *CreateReactor (GenericCallbackServerContext *context) override {
        if (context->method() == "/example.AnExampleService/CloneMessage") {
            return new MyServiceReactor ();
        } else {
            return CallbackGenericService::CreateReactor (context);
        }
    }
};


int main () {

    // Create the server object.
    //
    // The server object represents the server runtime.
    // It needs to be told what service to provide
    // and what port to listen on.

    MyService service;
    ServerBuilder builder;
    builder.AddListeningPort (SERVER_ADDR, InsecureServerCredentials ());
    builder.RegisterCallbackGenericService (&service);
    auto server (builder.BuildAndStart ());

    // The server is never asked to terminate in this example,
    // it therefore waits here until interrupted from outside.

    server->Wait ();

    return (0);
}
