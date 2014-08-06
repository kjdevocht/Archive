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
    if(debug) cout<<"DEBUG: client.cc-parse_request--request.size: "<<request.size()<<endl;

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
        if(debug) cout<<"DEBUG: client.cc-format_request--message.length() "<<message.length()<<endl;
        formatted_request.append(line.length()+" ");

        if(debug) cout<<"DEBUG: client.cc-format_request--formatted_request "<<formatted_request<<endl;
        return formatted_request;
}

string Client::format_get(vector<string> request){
    string formatted_request = "get ";
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
    if(debug) cout<<"DEBUG: client.cc-format_request--first_value: "<<first_value<<endl;

    if(first_value == "send")
    {
        if(debug) cout<<"DEBUG: client.cc-format_request--first_value == send"<<endl;
        return formatted_request = format_put(request);
    }

    else if(first_value == "list")
    {
        if(debug) cout<<"client.cc-format_request--request.at(1)"<<request.at(1)<<endl;
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
        if(debug) cout<<"DEBUG: client.cc-echo--line: "<<line<<endl;
        vector<string> request = parse_request(line);
        line = format_request(request);
        if(line == "error unknown command") {
        if(debug) cout<<"DEBUG: client.cc-echo: line == unknown command: "<<line<<endl;
            cout<<"% ";
            continue;
        }
        if(debug) cout<<"DEBUG: client.cc-echo: Returned from format_request"<<endl;

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

    if(debug) cout<<"DEBUG: client.cc-send_request--request: "<<request<<endl;

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
    // read until we get a newline
    int nread;
    while (response.find("\n") == string::npos) {
        nread = recv(server_,buf_,1024,0);
        if (nread < 0) {
            if (errno == EINTR)
                // the socket call was interrupted -- try again
                continue;
            else
                if(debug)cout<<"DEBUG: client.cc-get_response: An error occured\n";
                break;// an error occurred, so break out

        } else if (nread == 0) {
            // the socket is closed
            if(debug)cout<<"DEBUG: client.cc-get_response: The socket is closed\n";
                break;
        }
        // be sure to use append in case we have binary data
        response.append(buf_,nread);
    }

    istringstream iss(response);
    string responseType = "";
    iss >> responseType;
    if(debug) cout<<"DEBUG: client.cc-get_response--responseType: "<<responseType<<endl;
    if(responseType == "error"){
        string errorMsg = responseType+" ";
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
        message.append(buf_, msgStart,nread);
        iss>>subject;
        iss>>length;
        output.append(subject+"\n");
        if(debug)cout<<"DEBUG: client.cc-get_response--length"<<length<<endl;
        if(debug)cout<<"DEBUG: client.cc-get_response--message.length()"<<message.length()<<endl;
        while (message.length()<length) {
            if(debug) cout<<"DEBUG: client.cc-get_response--buf_: "<<buf_<<endl;
            int nread = recv(server_,buf_,1024,0);
            if (nread < 0) {
            if(debug) cout<<"DEBUG: client.cc-get_response: nread < 0"<<endl;
                if (errno == EINTR){
                    // the socket call was interrupted -- try again
                    continue;
                }
                else{
                    // an error occurred, so break out
                    if(debug) cout<<"DEBUG: client.cc-get_response: ERROR"<<endl;
                    cout<<"error an unknown error occuried"<<endl;
                    break;
                }
            } else if (nread == 0) {
                // the socket is closed
                if(debug) cout<<"DEBUG: client.cc-get_response: Socket Closed"<<endl;
                break;
            }
            // be sure to use append in case we have binary data
            message.append(buf_,nread);
            if(debug) cout<<"DEBUG: client.cc-get_response--message.length(): "<<message.length()<<endl;
        }
        output.append(message+"\b");
        cout<<output;
    }
    else if(responseType == "OK"){

    }
    else{
        cout<<"error uknown response from server\n";
    }
}
