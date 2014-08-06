#include <stdlib.h>
#include <unistd.h>

#include <iostream>

#include "inet-client.h"

using namespace std;

int
main(int argc, char **argv)
{
    int option;

    // setup default arguments
    int port = 5000;
    string server = "localhost";
    bool debug = false;

    // process command line options using getopt()
    // see "man 3 getopt"
    while ((option = getopt(argc,argv,"ds:p:")) != -1) {
        switch (option) {
            case 'p':
                port = atoi(optarg);
                if(debug) cout<<"Port: "<<port<<endl;
                break;
            case 's':
                server = optarg;
                if(debug) cout<<"Server: "<<server<<endl;
                break;
            case 'd':
                debug = true;
                    if(debug)
                    {
                        cout<<"Debug is True!\n";
                    }
                break;
            default:
                cout << "client [-s server] [-p port]" << endl;
                exit(EXIT_FAILURE);
        }
    }

    InetClient client = InetClient(server, port);
    client.setDebug(debug);
    client.run();
}

