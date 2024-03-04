#include <iostream>
#include <semaphore>

using namespace std;

#include <grpc++/grpc++.h>
#include <grpc++/generic/generic_stub.h>
using namespace grpc;

#include "example.grpc.pb.h"
using namespace example;

#include "shared.h"


int main ()
{
    // Create the channel used to connect to the server.
    auto channel = CreateChannel (SERVER_ADDR, InsecureChannelCredentials ());

    // Create a stub object that provides the generic invocation interface.
    auto stub = new TemplatedGenericStub<google::protobuf::Message, google::protobuf::Message> (channel);

    AnExampleMessage message;
    message.set_some_integer (0xDEAD);
    message.set_some_string ("Hello from gRPC in C++ !");
    cout << "Message:" << endl;
    cout << message.DebugString () << endl;

    StubOptions options;
    ClientContext context;
    MoreExampleMessages response;

    Status call_status;
    binary_semaphore call_done (0);

    // Call the service through the stub object.
    stub->UnaryCall (
        &context,
        "/example.AnExampleService/CloneMessage",
        options,
        &message,
        &response,
        [&] (Status status) {
            call_status = move (status);
            call_done.release ();
        });

    call_done.acquire ();
    if (call_status.ok ()) {
        cout << "Response:" << endl;
        cout << response.DebugString () << endl;
    }

    return (0);
}
