.PHONY: all clean

# Settings

MAKEFLAGS += --no-builtin-rules --no-builtin-variables

CC = gcc
CC_OPTS = -std=c11 -g $(shell pkg-config --cflags libzmq) -Wall -Wextra -Wno-unused-parameter -Werror
LD = gcc
LD_OPTS = -std=c11 -g $(shell pkg-config --libs libzmq)

# Files

ALL_EX = publisher subscriber
ALL_OO = $(addsuffix .o, $(ALL_EX))
ALL_DD = $(addsuffix .dep, $(ALL_EX))

# Rules

all: $(ALL_EX)

clean:
	rm -f *.o
	rm -f *.dep
	rm -f $(ALL_EX)

publisher: publisher.o
	$(LD) $(LD_OPTS) -o $@ $^

subscriber: subscriber.o
	$(LD) $(LD_OPTS) -o $@ $^

%.o: %.c
	$(CC) $(CC_OPTS) -c -o $@ $<

# Deps

-include $(ALL_DD)

%.dep: %.c
	$(CC) $(CC_OPTS) -MM -MT "$@ $(basename $@).o" -o $@ $<
