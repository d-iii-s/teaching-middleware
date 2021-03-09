#include <iostream>

#include <thrift/server/TSimpleServer.h>
#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/transport/TSocket.h>
#include <thrift/transport/TServerSocket.h>
#include <thrift/transport/TTransportUtils.h>

using namespace apache::thrift::server;
using namespace apache::thrift::protocol;
using namespace apache::thrift::transport;

#include "shared.h"

#include "gen-cpp/Example.h"


// Service implementation.
//
// The implementation inherits from a generated base class.

class ExampleHandler : virtual public ExampleIf {
    void printString (const std::string &text) override {
        std::cout << "Thrift server in C++: " << text << std::endl;
    }
};


int main () {

    // Create the object stack used to implement the server.
    std::shared_ptr<ExampleHandler> handler (new ExampleHandler ());
    std::shared_ptr<ExampleProcessor> processor (new ExampleProcessor (handler));
    std::shared_ptr<TServerTransport> transport (new TServerSocket (SERVER_PORT));
    std::shared_ptr<TTransportFactory> transport_factory (new TBufferedTransportFactory ());
    std::shared_ptr<TProtocolFactory> protocol_factory (new TBinaryProtocolFactory ());
    std::shared_ptr<TServer> server (new TSimpleServer (processor, transport, transport_factory, protocol_factory));

    server->serve ();

    return (0);
}
