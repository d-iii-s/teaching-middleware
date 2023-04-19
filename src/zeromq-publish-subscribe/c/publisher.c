#include <unistd.h>
#include <stdbool.h>

#include <zmq.h>

int main () {

    // Context with single thread.
    void *context = zmq_ctx_new ();

    // Socket of publisher type in publish-subscribe pattern.
    void *publisher = zmq_socket (context, ZMQ_PUB);

    // Multiple transports can be used with a socket.
    zmq_bind (publisher, "tcp://*:8888");
    // zmq_bind (publisher, "ipc://example");
    // zmq_bind (publisher, "pgm://127.0.0.1;239.255.255.88:8888");

    while (true) {

        // Just send to all connected subscribers.
        const char greeting [] = "Hello 0MQ !";
        zmq_send (publisher, greeting, sizeof (greeting), 0);

        sleep (1);
    }
}
