#include <unistd.h>

#include <zmq.hpp>

int main () {

    // Context with single thread.
    zmq::context_t context (1);

    // Socket of publisher type in publish-subscribe pattern.
    zmq::socket_t publisher (context, ZMQ_PUB);

    // Multiple transports can be used with a socket.
    // publisher.bind ("ipc://example");
    publisher.bind ("tcp://*:8888");

    while (true) {

        const char *greeting = "Hello 0MQ !";

        // Create message from the static string.
        // The string is read only hence we do not provide free function.
        zmq::message_t message ((void *) greeting, strlen (greeting) + 1, NULL);

        // Just send to all connected subscribers.
        publisher.send (message, zmq::send_flags::none);

        sleep (1);

        // Note the message is deallocated here.
    }
}
