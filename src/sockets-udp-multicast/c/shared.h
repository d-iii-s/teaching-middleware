#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <unistd.h>

#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>

#define FALSE 0
#define TRUE !0

#define GROUP_ADDR "239.255.255.88"
#define GROUP_PORT 8888

#define SOCKET_BUFFER_SIZE 16384

// Custom assert macro for custom message in error report.
// Reuses assert helper definitions from standard library.

#define ASSERT(cond,text)                                                   \
    ((cond)                                                                 \
        ? __ASSERT_VOID_CAST (0)                                            \
        : __assert_fail (text, __FILE__, __LINE__, __ASSERT_FUNCTION))
