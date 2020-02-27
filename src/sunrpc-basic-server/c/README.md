# Sun RPC Based Server

## Running the example

First, generate the stub code together with example client and example server.

```
> rpcgen -a example.x
```

Next, edit the example client and example server.

In `example_client.c`, modify the code to include a string argument:

```
void
print_1(char *host)
{
        CLIENT *clnt;
        void  *result_1;
        char *print_str_1_arg = "Hello RPC !";
...
```

In `example_server.c`, modify the code to print the string argument:

```
#include <stdio.h>

void *
print_str_1_svc(char **argp, struct svc_req *rqstp)
{
    static char *result = NULL;
    puts (*argp);
    return (void *) &result;
}

```

Finally, build and run the client and the server.
Remember that the `rpcbind` service must also be running.

```
> CFLAGS="-I/usr/include/tirpc" LDFLAGS="-ltirpc" make -f Makefile.example
> ./example_server &
> ./example_client localhost
```
