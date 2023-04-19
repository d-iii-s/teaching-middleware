#include <stdio.h>

#include <zmq.hpp>

int main () {

    // Context with single thread.
    zmq::context_t context (1);

    // Socket of subscriber type in publish-subscribe pattern.
    zmq::socket_t subscriber (context, ZMQ_SUB);

    // Connect to one of the available transports.
    // Uncomment as needed to evaluate transport.
    subscriber.connect ("tcp://localhost:8888");
    // subscriber.connect ("ipc://example");
    // subscriber.connect ("pgm://127.0.0.1;239.255.255.88:8888");

    // Set an accept-all filter on the subscription.
    // The default filter is discard-all.
    subscriber.set (zmq::sockopt::subscribe, "");

    while (true) {

        zmq::message_t message;

        subscriber.recv (message, zmq::recv_flags::none);
        printf ("Message: %s\n", (char *) message.data ());

        // Note the message is deallocated here.
    }
}
