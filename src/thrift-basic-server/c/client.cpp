#include <iostream>

#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/transport/TSocket.h>
#include <thrift/transport/TTransportUtils.h>

using namespace apache::thrift::protocol;
using namespace apache::thrift::transport;

#include "shared.h"

#include "gen-cpp/Example.h"


int main ()
{
    // Create the object stack used to connect to the server.
    std::shared_ptr<TTransport> socket (new TSocket (SERVER_ADDR, SERVER_PORT));
    std::shared_ptr<TTransport> transport (new TBufferedTransport (socket));
    std::shared_ptr<TProtocol> protocol (new TBinaryProtocol (transport));
    std::shared_ptr<ExampleClient> client (new ExampleClient (protocol));

    transport->open ();
    client->printString ("Hello from Thrift in C++ !");
    transport->close ();

    return (0);
}
