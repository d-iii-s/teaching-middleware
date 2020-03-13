.PHONY: all clean

# Settings

MAKEFLAGS += --no-builtin-rules --no-builtin-variables

CC = g++
CC_OPTS = -g -Wall -Werror
LD = g++
LD_OPTS = -g -lomniORB4 -lomnithread

# Files

ALL_EX = client server
ALL_OO = $(addsuffix .o, $(ALL_EX))
ALL_DD = $(addsuffix .dep, $(ALL_EX))

ALL_IF = example
ALL_ST = $(addsuffix .hh, $(ALL_IF)) $(addsuffix SK.cc, $(ALL_IF))

# Rules

all: $(ALL_EX)

clean:
	rm -f ior
	rm -f *.o
	rm -f *.dep
	rm -f $(ALL_ST)
	rm -f $(ALL_EX)

client: client.o exampleSK.o
	$(LD) $(LD_OPTS) -o $@ $^

server: server.o exampleSK.o
	$(LD) $(LD_OPTS) -o $@ $^

client.cpp: example.hh exampleSK.cc

server.cpp: example.hh exampleSK.cc

%.o: %.cpp
	$(CC) $(CC_OPTS) -c -o $@ $<

%.o: %.cc
	$(CC) $(CC_OPTS) -c -o $@ $<

%.hh %SK.cc: %.idl
	omniidl -bcxx $<

# Deps

-include $(ALL_DD)

%.dep: %.cpp
	$(CC) $(CC_OPTS) -MM -MT "$@ $(basename $@).o" -o $@ $<
