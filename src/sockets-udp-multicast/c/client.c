#include "shared.h"

int main ()
{
    // Create client socket object.
    //
    // The object state resides in the operating system kernel.
    // What we have is merely a file descriptor refering to it.

    int client_socket = socket (AF_INET, SOCK_DGRAM, 0);
    ASSERT (client_socket != -1, "Failed to create client socket object.");

    // Construct multicast group address.
    //
    // Note the need to use network order inside address fields.

    struct sockaddr_in group_address;
    group_address.sin_family = AF_INET;
    int aton_status = inet_aton (GROUP_ADDR, (in_addr *) &group_address.sin_addr.s_addr);
    ASSERT (aton_status == 1, "Failed to parse group address.");
    group_address.sin_port = htons (GROUP_PORT);

    // Connect to server.
    //
    // This does not really connect at the protocol level but sets the default destination address.

    int connect_status = connect (client_socket, (struct sockaddr *) &group_address, sizeof (group_address));
    ASSERT (connect_status == 0, "Failed to set group address.");

    // Just send something.

    const char *message = "Hello !";
    ssize_t message_size = strlen (message);
    ssize_t write_size = write (client_socket, message, message_size);
    ASSERT (write_size == message_size, "Failed to write to outgoing connection.");

    // Clean up by closing the socket.

    int close_status = close (client_socket);
    ASSERT (close_status == 0, "Failed to close outgoing connection.");
}
