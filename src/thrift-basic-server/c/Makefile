.PHONY: all clean

# Settings

MAKEFLAGS += --no-builtin-rules --no-builtin-variables

TC = $(shell pkg-config --variable=prefix thrift)/bin/thrift
TC_OPTS = -gen cpp:no_skeleton
CC = g++
CC_OPTS = -std=c++11 $(shell pkg-config --cflags thrift) -Wall -Wextra -Werror
LD = g++
LD_OPTS = -std=c++11 $(shell pkg-config --libs thrift)

# Files

ALL_EX = client server
ALL_OO = $(addsuffix .o, $(ALL_EX))
ALL_DD = $(addsuffix .dep, $(ALL_EX))

ONE_TF = example
ONE_TC = Example
ONE_TG = gen-cpp
ONE_TH = $(addprefix $(ONE_TG)/, $(ONE_TF)_types.h $(ONE_TC).cpp $(ONE_TC).h)

# Rules

all: $(ALL_EX)

clean:
	rm -f *.o
	rm -f *.dep
	rm -f $(ONE_TH)
	rm -d $(ONE_TG)
	rm -f $(ALL_EX)

client: client.o Example.o
	$(LD) $^ $(LD_OPTS) -o $@

server: server.o Example.o
	$(LD) $^ $(LD_OPTS) -o $@

client.cpp: $(addprefix $(ONE_TG)/, example_types.h Example.h)

server.cpp: $(addprefix $(ONE_TG)/, example_types.h Example.h)

%.o: %.cpp
	$(CC) $(CC_OPTS) -c -o $@ $<

%.o: $(ONE_TG)/%.cpp
	$(CC) $(CC_OPTS) -c -o $@ $<

$(ONE_TH) &: $(ONE_TF).thrift
	$(TC) $(TC_OPTS) $<

# Deps

-include $(ALL_DD)

%.dep: %.cpp
	$(CC) $(CC_OPTS) -MM -MT "$@ $(basename $@).o" -o $@ $<
