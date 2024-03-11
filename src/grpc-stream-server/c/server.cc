#include <chrono>
#include <thread>
#include <iostream>

#include <grpc++/grpc++.h>

// To make the API calls easily visible, the example does not use the grpc namespace.
// A standard application would likely be "using namespace grpc" here.
// For similar reasons, the use of auto type deduction is avoided.

#include "example.grpc.pb.h"
using namespace example;

#include "shared.h"


// Service implementation.
//
// The implementation inherits from a generated base class.

class MyService : public AnExampleService::Service {
    grpc::Status EchoMessages (grpc::ServerContext *context, grpc::ServerReaderWriter<AnExampleMessage, AnExampleMessage> *request_response) override {
        std::cout << "Call." << std::endl;
        AnExampleMessage message;
        while (request_response->Read (&message)) {
            std::cout << "Request:" << std::endl;
            std::cout << message.DebugString () << std::endl;
            std::this_thread::sleep_for (std::chrono::milliseconds (666));
            request_response->Write (message);
        }
        std::cout << "Done." << std::endl;
        return (grpc::Status::OK);
    }
};


int main ()
{
    // Create the server object.
    //
    // The server object represents the server runtime.
    // It needs to be told what service to provide
    // and what port to listen on.

    MyService service;
    grpc::ServerBuilder builder;
    builder.AddListeningPort (SERVER_ADDR, grpc::InsecureServerCredentials ());
    builder.RegisterService (&service);
    std::unique_ptr<grpc::Server> server (builder.BuildAndStart ());

    // The server is never asked to terminate in this example,
    // it therefore waits here until interrupted from outside.

    server->Wait ();

    return (0);
}
