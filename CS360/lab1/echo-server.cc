#include <stdlib.h>
#include <unistd.h>

#include <iostream>

#include "inet-server.h"

using namespace std;

int
main(int argc, char **argv)
{
    int option, port;

    // setup default arguments
    port = 5000;
    bool debug = false;

    // process command line options using getopt()
    // see "man 3 getopt"
    while ((option = getopt(argc,argv,"dp:")) != -1) {
        switch (option) {
            case 'p':
                port = atoi(optarg);
                if(debug) cout<<"Port: "<<port<<endl;
                break;
            case 'd':
                debug = true;
                if(debug)
                {
                    cout<<"Debug is True!\n";
                }
                break;
            default:
                cout << "server [-p port]" << endl;
                exit(EXIT_FAILURE);
        }
    }

    InetServer server = InetServer(port);
    server.setDebug(debug);
    server.run();
}
