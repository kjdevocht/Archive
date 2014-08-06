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


class Message
{
    public:
        Message();
        Message(string, string, int, string);
        //~Message();

        void setDebug(bool);
        string getName();
        void setName(string);
        string getSubject();
        void setSubject(string);
        int getLength();
        void setLength(int);
        string getMessage();
        void setMessage(string);

    protected:

        string name;
        string subject;
        int length;
        string message;
        bool debug;
};
