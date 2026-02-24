.PHONY: all clean

# Settings

MAKEFLAGS += --no-builtin-rules --no-builtin-variables

PC = $(shell pkg-config --variable=prefix capnp)/bin/capnpc
PC_OPTS =
CC = g++
CC_OPTS = -std=c++14 -g $(shell pkg-config --cflags capnp) -Wall -Wextra -Werror
LD = g++
LD_OPTS = -std=c++14 -g $(shell pkg-config --libs capnp)

# Files

ALL_EX = example
ALL_OO = $(addsuffix .o, $(ALL_EX))
ALL_DD = $(addsuffix .dep, $(ALL_EX))
ALL_CP = $(addsuffix .capnp.h, $(ALL_EX)) $(addsuffix .capnp.c++, $(ALL_EX))

# Rules

all: $(ALL_EX)

clean:
	rm -f *.o
	rm -f *.dep
	rm -f $(ALL_CP)
	rm -f $(ALL_EX)

example: example.o example.capnp.c++
	$(LD) $^ $(LD_OPTS) -o $@

example.cc: example.capnp.h

%.o: %.cc
	$(CC) $(CC_OPTS) -c -o $@ $<

%.o: %.c++
	$(CC) $(CC_OPTS) -c -o $@ $<

%.capnp.h %.capnp.c++: %.capnp
	$(PC) $(PC_OPTS) -o c++ $<

# Deps

-include $(ALL_DD)

%.dep: %.cc
	$(CC) $(CC_OPTS) -MM -MT "$@ $(basename $@).o" -o $@ $<

%.dep: %.c++
	$(CC) $(CC_OPTS) -MM -MT "$@ $(basename $@).o" -o $@ $<
