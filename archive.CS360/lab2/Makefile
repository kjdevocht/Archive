# Makefile for socket examples

CXX=			g++ $(CCFLAGS)
ECHO-SERVER=		echo-server.o inet-server.o server.o message.o thread-queue.o
ECHO-CLIENT=		echo-client.o inet-client.o client.o
OBJS =			$(ECHO-SERVER) $(ECHO-CLIENT)

LIBS= -pthread

CCFLAGS= -g

all:	echo-server echo-client

echo-server:$(ECHO-SERVER)
	$(CXX) -o msgd $(ECHO-SERVER) $(LIBS)

echo-client:$(ECHO-CLIENT)
	$(CXX) -o msg $(ECHO-CLIENT) $(LIBS)

clean:
	rm -f $(OBJS) $(OBJS:.o=.d)

realclean:
	rm -f $(OBJS) $(OBJS:.o=.d) msgd msg


# These lines ensure that dependencies are handled automatically.
%.d:	%.cc
	$(SHELL) -ec '$(CC) -M $(CPPFLAGS) $< \
		| sed '\''s/\($*\)\.o[ :]*/\1.o $@ : /g'\'' > $@; \
		[ -s $@ ] || rm -f $@'

include	$(OBJS:.o=.d)
