.PHONY: all clean

# Settings

MAKEFLAGS += --no-builtin-rules --no-builtin-variables

CC = g++
CC_OPTS = -g -Wall -Werror
LD = g++
LD_OPTS = -g -lACE -lTAO -lTAO_PortableServer -lTAO_AnyTypeCode -lTAO_Valuetype

# Files

ALL_EX = client server
ALL_OO = $(addsuffix .o, $(ALL_EX))
ALL_DD = $(addsuffix .dep, $(ALL_EX))

ALL_IF = example
ALL_ST = $(addsuffix C.h, $(ALL_IF)) $(addsuffix C.inl, $(ALL_IF)) $(addsuffix C.cpp, $(ALL_IF)) $(addsuffix S.h, $(ALL_IF)) $(addsuffix S.cpp, $(ALL_IF))

# Rules

all: $(ALL_EX)

clean:
	rm -f *.o
	rm -f *.dep
	rm -f $(ALL_ST)
	rm -f $(ALL_EX)

client: client.o exampleC.o
	$(LD) $(LD_OPTS) -o $@ $^

server: server.o exampleC.o exampleS.o
	$(LD) $(LD_OPTS) -o $@ $^

client.cpp: exampleC.h

server.cpp: exampleS.h

%.o: %.cpp
	$(CC) $(CC_OPTS) -c -o $@ $<

%C.h %C.inl %C.cpp %S.h %S.cpp: %.idl
	tao_idl $<

# Deps

-include $(ALL_DD)

%.dep: %.cpp
	$(CC) $(CC_OPTS) -MM -MT "$@ $(basename $@).o" -o $@ $<
