.PHONY: all run clean

# Settings

MAKEFLAGS += --no-builtin-rules --no-builtin-variables

CC = gcc
CC_OPTS = -std=c11 -g $(shell pkg-config --cflags ompi-c) -Wall -Wextra -Wno-unused-parameter -Werror
LD = gcc
LD_OPTS = -std=c11 -g $(shell pkg-config --libs ompi-c)
RN = $(shell pkg-config --variable=prefix ompi-c)/bin/mpirun
RN_OPTS = --oversubscribe -n 10

# Files

ALL_EX = broadcast scan
ALL_OO = $(addsuffix .o, $(ALL_EX))
ALL_DD = $(addsuffix .dep, $(ALL_EX))

# Rules

all: $(ALL_EX)

run: $(ALL_EX)
	$(foreach EX, $^, $(RN) $(RN_OPTS) $(EX) ;)

clean:
	rm -f *.o
	rm -f *.dep
	rm -f $(ALL_EX)

broadcast: broadcast.o
	$(LD) $(LD_OPTS) -o $@ $^

scan: scan.o
	$(LD) $(LD_OPTS) -o $@ $^

%.o: %.c
	$(CC) $(CC_OPTS) -c -o $@ $<

# Deps

-include $(ALL_DD)

%.dep: %.c
	$(CC) $(CC_OPTS) -MM -MT "$@ $(basename $@).o" -o $@ $<
