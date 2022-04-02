# Multicast Socket

## Running the example

```
> make
> ./server &
> ./client
```

The multicast protocol behavior is not easily apparent.
It is possible to launch multiple servers even when
none specifies the `SO_REUSEPORT` option, but on
`localhost` this is not so exciting.

One can also observe the IGMP traffic.
