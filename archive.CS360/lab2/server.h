#pragma once

#include <errno.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <iostream>
#include <sstream>
#include <string>
#include <map>
#include <semaphore.h>
#include "message.h"
#include "thread-queue.h"
using namespace std;

class Server {
public:
    Server();
    ~Server();

    void run();
	void setDebug(bool);
	void* runThread();
	static void* callMemberFunction(void *arg) { return ((Server*)arg)->runThread(); }
    
protected:
    virtual void create();
    virtual void close_socket();
    void serve();
    void handle();
    string get_request(int, char*);
    bool send_response(int, string);
    string handle_put(int, string, istringstream&, char*);
    string handle_list(istringstream&);
	string handle_get(istringstream&, char*);
    string its(int);

    int server_;
    int buflen_;
	sem_t s;
    bool debug;
    map<string, vector<Message> > allMessages;
	ThreadQueue threadQueue;
};
