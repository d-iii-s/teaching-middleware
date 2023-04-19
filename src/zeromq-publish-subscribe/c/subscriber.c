#include <stdio.h>
#include <stdbool.h>

#include <zmq.h>

int main () {

    // Context with single thread.
    void *context = zmq_ctx_new ();

    // Socket of subscriber type in publish-subscribe pattern.
    void *subscriber = zmq_socket (context, ZMQ_SUB);

    // Connect to one of the available transports.
    zmq_connect (subscriber, "tcp://localhost:8888");
    // zmq_connect (subscriber, "ipc://example");
    // zmq_connect (subscriber, "pgm://127.0.0.1;239.255.255.88:8888");

    // Set an accept-all filter on the subscription.
    // The default filter is discard-all.
    zmq_setsockopt (subscriber, ZMQ_SUBSCRIBE, NULL, 0);

    while (true) {

        char buffer [1024];

        zmq_recv (subscriber, buffer, sizeof (buffer), 0);
        printf ("Message: %s\n", buffer);
    }
}
