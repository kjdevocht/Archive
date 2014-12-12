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
#include "message.h"

using namespace std;

class Server {
public:
    Server();
    ~Server();

    void run();
	void setDebug(bool);
    
protected:
    virtual void create();
    virtual void close_socket();
    void serve();
    void handle(int);
    string get_request(int);
    bool send_response(int, string);
    string handle_put(int, string, istringstream&);
    string handle_list(istringstream&);
	string handle_get(istringstream&);
    string its(int);

    int server_;
    int buflen_;
    char* buf_;
    bool debug;
    map<string, vector<Message> > allMessages;
};
