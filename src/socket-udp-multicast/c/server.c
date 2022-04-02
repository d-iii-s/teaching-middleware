#include "shared.h"

int main ()
{
    // Create server socket object.
    //
    // The object state resides in the operating system kernel.
    // What we have is merely a file descriptor refering to it.

    int server_socket = socket (AF_INET, SOCK_DGRAM, 0);
    ASSERT (server_socket != -1, "Failed to create server socket object.");

    // Set socket options.
    //
    // The SO_REUSEADDR option makes it possible to restart the server
    // without waiting for the TIME_WAIT socket states to expire.

    int reuseaddr_flag = true;
    int setsockopt_status_reuse = setsockopt (server_socket, SOL_SOCKET, SO_REUSEADDR, &reuseaddr_flag, sizeof (reuseaddr_flag));
    ASSERT (setsockopt_status_reuse == 0, "Failed to set server socket address reuse option.");

    // Bind socket to listen to constant port on all local interfaces.
    //
    // Note the need to use network order inside address fields.

    struct sockaddr_in server_address;
    server_address.sin_family = AF_INET;
    server_address.sin_addr.s_addr = htonl (INADDR_ANY);
    server_address.sin_port = htons (GROUP_PORT);

    int bind_status = bind (server_socket, (struct sockaddr *) &server_address, sizeof (server_address));
    ASSERT (bind_status == 0, "Failed to bind to server socket.");

    // Construct multicast group address.
    //
    // Note the need to use network order inside address fields.

    struct in_addr group_address;
    int aton_status = inet_aton (GROUP_ADDR, &group_address);
    ASSERT (aton_status == 1, "Failed to parse group address.");

    // Set socket options.
    //
    // The IP_ADD_MEMBERSHIP option directs the kernel to join the given multicast group.

    struct ip_mreqn multicast_membership;
    multicast_membership.imr_multiaddr = group_address;
    multicast_membership.imr_address.s_addr = htonl (INADDR_ANY);
    multicast_membership.imr_ifindex = 0;
    int setsockopt_status_membership = setsockopt (server_socket, IPPROTO_IP, IP_ADD_MEMBERSHIP, &multicast_membership, sizeof (multicast_membership));
    ASSERT (setsockopt_status_membership == 0, "Failed to set server socket group membership option.");

    // The rest happens in an infinite loop.

    printf ("Waiting for incoming messages.\n");

    char buffer [SOCKET_BUFFER_SIZE];
    while (TRUE) {

        ssize_t read_size = read (server_socket, buffer, sizeof (buffer));
        ASSERT (read_size >= 0, "Failed to read data.");
        printf ("Received message: %s\n", buffer);
    }

    // Should not be reached.

    return (0);
}
