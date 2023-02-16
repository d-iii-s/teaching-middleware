.PHONY: all clean

# Settings

MAKEFLAGS += --no-builtin-rules --no-builtin-variables

FC = flatc
FC_OPTS =
CC = g++
CC_OPTS = -std=c++11 -O0 -g $(shell pkg-config --cflags flatbuffers) -Wall -Wextra -Wno-unused-parameter -Werror
LD = g++
LD_OPTS = -std=c++11 -O0 -g $(shell pkg-config --libs flatbuffers)

# Files

ALL_EX = example
ALL_OO = $(addsuffix .o, $(ALL_EX))
ALL_DD = $(addsuffix .dep, $(ALL_EX))
ALL_FB = $(addsuffix _generated.h, $(ALL_EX))

# Rules

all: $(ALL_EX)

clean:
	rm -f *.o
	rm -f *.dep
	rm -f $(ALL_FB)
	rm -f $(ALL_EX)

example: example.o
	$(LD) $^ $(LD_OPTS) -o $@

example.cc: example_generated.h

%.o: %.cc
	$(CC) $(CC_OPTS) -c -o $@ $<

%_generated.h: %.fbs
	$(FC) $(FC_OPTS) --cpp example.fbs

# Deps

-include $(ALL_DD)

%.dep: %.cc
	$(CC) $(CC_OPTS) -MM -MT "$@ $(basename $@).o" -o $@ $<
