#!/usr/bin/env python3

import socket

from shared import *

# Safe handling of resources that require releasing on exceptions.
with socket.socket (socket.AF_INET, socket.SOCK_STREAM) as server_socket:

    # The SO_REUSEADDR option makes it possible to restart the server
    # without waiting for the TIME_WAIT socket states to expire.
    server_socket.setsockopt (socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    server_socket.bind (('', SERVER_PORT))
    server_socket.listen (SERVER_SOCKET_BACKLOG)

    print ('Waiting for incoming connection.')

    while True:

        # Safe handling of resources that require releasing on exceptions.
        with server_socket.accept () [0] as client_socket:

            print ('Accepted an incoming connection.')

            while True:
                data = client_socket.recv (SOCKET_BUFFER_SIZE)
                if len (data) == 0:
                    break
                # Note that send does not necessarily transfer all data.
                # Return value says how many bytes were sent.
                client_socket.send (data)

            print ('Client disconnected.')

            # Shutdown precedes close to make sure protocol level shutdown is executed completely.
            # Close without shutdown may use RST instead of FIN to terminate connection, dropping data that is in flight.
            #
            # It is also possible to use shutdown to close input and output streams independently.
            client_socket.shutdown (socket.SHUT_RDWR)
