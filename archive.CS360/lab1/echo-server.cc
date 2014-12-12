#include <stdlib.h>
#include <unistd.h>

#include <iostream>

#include "inet-server.h"

using namespace std;

int
main(int argc, char **argv)
{
    int option, port;
	bool debug;

    // setup default arguments
    port = 5000;
	debug = false;

    // process command line options using getopt()
    // see "man 3 getopt"
    while ((option = getopt(argc,argv,"dp:")) != -1) {
        switch (option) {
            case 'p':
                port = atoi(optarg);
                break;
			case 'd':
				debug = true;
				break;
            default:
                cout << "server [-p port]" << endl;
                exit(EXIT_FAILURE);
        }
    }

    InetServer server = InetServer(port);
	if(debug){
cout << "\033[34m" <<"==DEBUG==\n" << "Listening on port "<< port << "\033[0m" <<endl;
}
	server.setDebug(debug);
    server.run();
}
