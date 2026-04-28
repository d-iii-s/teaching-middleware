.PHONY: all run clean

# Settings

MAKEFLAGS += --no-builtin-rules --no-builtin-variables

CC = mpicc
CC_OPTS = -std=c11 -g -Wall -Wextra -Wno-unused-parameter -Werror
LD = mpicc
LD_OPTS = -std=c11 -g
RN = mpirun
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
