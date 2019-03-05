#include <chrono>
#include <thread>
#include <iostream>

#include <grpc++/grpc++.h>
// To make the API calls easily visible, the example does not use the grpc namespace.
// A standard application would likely be "using namespace grpc" here.

#include "shared.h"

#include "example.grpc.pb.h"
using namespace example;


int main ()
{
    // Create the channel used to connect to the server.
    std::shared_ptr<grpc::Channel> channel = grpc::CreateChannel (SERVER_ADDR, grpc::InsecureChannelCredentials ());

    // Create a stub object that provides the service interface.
    std::shared_ptr<AnExampleService::Stub> stub = AnExampleService::NewStub (channel);

    AnExampleMessage message;
    message.set_some_integer (0xDEAD);
    message.set_some_string ("Hello gRPC !");
    std::cout << "Message:" << std::endl;
    std::cout << message.DebugString () << std::endl;

    grpc::ClientContext context;

    // Call the service through the stub object.
    std::shared_ptr<grpc::ClientReaderWriter<AnExampleMessage, AnExampleMessage>> request_response (stub->EchoMessages (&context));
    for (int i = 0 ; i < 8 ; i ++) {
        // Send messages through the stream.
        // This uses a blocking interface for simplicity.
        if (request_response->Write (message)) {
            if (request_response->Read (&message)) {
                std::cout << "Response:" << std::endl;
                std::cout << message.DebugString () << std::endl;
            }
        }
        std::this_thread::sleep_for (std::chrono::milliseconds (666));
    }
    // Notify about the end of the stream.
    request_response->WritesDone ();

    grpc::Status status_reader = request_response->Finish ();

    return (0);
}
