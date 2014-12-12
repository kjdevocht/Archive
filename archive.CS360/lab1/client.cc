#include "client.h"

Client::Client() {
    // setup variables
    buflen_ = 1024;
    buf_ = new char[buflen_+1];
    debug = false;
}

Client::~Client() {
}

void Client::setDebug(bool debug0)
{
    this->debug = debug0;
}

void Client::run() {
    // connect to the server and run echo program
    create();
    echo();
}

void
Client::create() {
}

void
Client::close_socket() {
}

vector<string> Client::parse_request(string line)
{
    istringstream ss(line);
    vector<string> request;

    for(string word; ss >> word; )
    {
        request.push_back(word);
    }

    return request;
}

string Client::format_put(vector<string> request){
        string formatted_request = "";


        formatted_request.append("put ");
        for(int i = 1; i<3; i++)
        {
            formatted_request.append(request.at(i)+" ");
        }

        string message = "";
        string line = "";
        while (getline(cin,line))
        {
            if(line == "")
            {
                break;
            }
            else
            {


                message.append(line+"\n");
            }
        }

        stringstream ss;
        ss << message.length();
        string strLength = ss.str();

        formatted_request.append( strLength +" \n"+message);
        formatted_request.append(line.length()+" ");

        return formatted_request;
}

string Client::format_get(vector<string> request){
    string formatted_request = "get ";
	if(request.size()<3){
		return "error missing value\n";
	}
    for(int i = 1; i<3; i++)
    {
        formatted_request.append(request.at(i)+" ");
    }

    return formatted_request+"\n";
}

string Client::format_request(vector<string> request)
{
    string formatted_request = "";
    string first_value = request.at(0);

    if(first_value == "send")
    {
		cout<<"- Type your message. End with a blank line -"<<endl;
        return formatted_request = format_put(request);
    }

    else if(first_value == "list")
    {
        return "list "+request.at(1)+"\n";
    }
    else if(first_value == "read")
    {
        return formatted_request = format_get(request);
    }
    else if(first_value == "quit")
    {

        exit(0);
    }
    else
    {

        return "error unknown command";
    }

}

void
Client::echo() {
    string line = "";
    cout<<"% ";
    // loop to handle user interface
    while (getline(cin,line)) {
        // append a newline
        vector<string> request = parse_request(line);
        line = format_request(request);
        if(line == "error unknown command") {
            cout<<"I don't recognize that command.\n% ";
            continue;
        }

        // send request
        bool success = send_request(line);


        // break if an error occurred
        if (not success)
            break;

        // get a response
        get_response();

        cout<<"% ";
    }

    close_socket();
}

bool
Client::send_request(string request) {


    // prepare to send request
    const char* ptr = request.c_str();
    int nleft = request.length();
    int nwritten;
    // loop to be sure it is all sent
    while (nleft) {
        if ((nwritten = send(server_, ptr, nleft, 0)) < 0) {
            if (errno == EINTR) {
                // the socket call was interrupted -- try again
                continue;
            } else {
                // an error occurred, so break out
                perror("write");
                return false;
            }
        } else if (nwritten == 0) {
            // the socket is closed
            return false;
        }
        nleft -= nwritten;
        ptr += nwritten;
    }
    return true;
}

void
Client::get_response() {
    string response = "";
    int nread; 

    // read until we get a newline
    while (response.find("\n") == string::npos) { 
		nread = recv(server_,buf_,1024,0);
		if(debug){
			cout << "\033[34m" <<"==DEBUG==\n" << "nreadin intial while loop: "<<nread << "\033[0m" <<endl;
		}
        if (nread < 0) {
            if (errno == EINTR)
                // the socket call was interrupted -- try again
                continue;
            else
                break;// an error occurred, so break out

        } else if (nread == 0) {
            // the socket is closed
                break;
        }
        // be sure to use append in case we have binary data
        response.append(buf_,nread);
    }

    istringstream iss(response);
    string responseType = "";
    iss >> responseType;
    if(responseType == "error"){
        string errorMsg = "Server returned bad message: "+responseType+" ";
           while (iss)
            {
                string n;
                iss >> n;
                errorMsg.append(n+" ");
            }
            errorMsg.append("\n");
            cout<<errorMsg;
    }
    else if(responseType == "list"){
        string listMsg = "";
        string num;
            iss>>num;
            while (iss)
            {
                string index;
                iss >> index;
                listMsg.append(index+" ");
                string subject;
                iss >> subject;
                listMsg.append(subject+"\n");
            }
            cout<<listMsg;

    }
    else if(responseType == "message"){
        string message = "";
        string subject = "";
        int length;
        string output = "";


        int msgStart = response.find("\n")+1;
        iss>>subject;
        iss>>length;
		message.append(buf_, msgStart, length);
		output.append(subject+"\n");
        while (message.length()<length) {
            nread = recv(server_,buf_,1024,0);
            if (nread < 0) {
                if (errno == EINTR){
                    // the socket call was interrupted -- try again
                    continue;
                }
                else{
                    // an error occurred, so break out
                    cout<<"error an unknown error occuried"<<endl;
                    break;
                }
            } else if (nread == 0) {
                // the socket is closed
                break;
            }
            // be sure to use append in case we have binary data
            message.append(buf_,nread);
        }
        output.append(message+"\b");
        cout<<output;
    }
    else if(responseType == "OK"){

    }
    else{
	if(debug){
		cout << "\033[34m" <<"==DEBUG==\n" << "Error Response: "<<response << "\033[0m" <<endl;
	}
        cout<<"error uknown response from server\n";
    }
}
