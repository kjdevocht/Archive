#pragma once

#include <errno.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <vector>

#include <fstream>
#include <iostream>
#include <sstream>
#include <string>

using namespace std;

class Client {
public:
    Client();
    ~Client();

    void run();
    void setDebug(bool);

protected:
    virtual void create();
    virtual void close_socket();
    void echo();
    bool send_request(string);
    void get_response();
    vector<string> parse_request(string);
    string format_request(vector<string>);
    string format_put(vector<string>);
    string format_get(vector<string>);

    int server_;
    int buflen_;
    char* buf_;
    bool debug;
};
