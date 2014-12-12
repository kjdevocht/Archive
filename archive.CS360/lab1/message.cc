#include "message.h"

Message::Message()
{}

Message::Message(string name0, string subject0, int length0, string message0)
{
    this->name = name0;
    this->subject = subject0;
    this->length = length0;
    this->message = message0;
}

/*Message::~Message()
 * {
 *     //dtor
 *     }*/
void Message::setDebug(bool debug0)
{
    this->debug = debug0;
}

string Message::getName()
{
    return this->name;
}

void Message::setName(string name0)
{
    this->name = name0;
}

string Message::getSubject()
{
    return this->subject;
}

void Message::setSubject(string subject0)
{
    this->subject = subject0;
}

int Message::getLength()
{
    return this->length;
}

void Message::setLength(int length0)
{
    this->length = length0;
}

string Message::getMessage()
{
    return this->message;
}

void Message::setMessage(string message0)
{
    this->message = message0;
}


