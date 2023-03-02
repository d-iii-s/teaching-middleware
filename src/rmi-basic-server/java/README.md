# Java RMI Based Server

## Running the example

```shell
mvn compile
mvn exec:java@server &
mvn exec:java@client
```

The server launches an in process RMI registry,
see for example the output of `nmap localhost`:

```
Nmap scan report for localhost (127.0.0.1)
...
PORT     STATE SERVICE
1099/tcp open  rmiregistry

Nmap done: 1 IP address (1 host up) scanned in 0 seconds
```
