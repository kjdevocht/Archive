#include "Lex.h"

#include "Input.h"
#include "TokenType.h"
#include "Utils.h"
#include <iostream>
#include <fstream>

using namespace std;

Lex::Lex() {
	input = new Input();
    generateTokens(input);
}

Lex::Lex(const char* filename) {
    input = new Input(filename);
    generateTokens(input);
}

Lex::Lex(istream& istream) {
    input = new Input(istream);
    generateTokens(input);
}

Lex::Lex(const Lex& lex) {
    input = new Input(*lex.input);
    tokens = new vector<Token*>();

    vector<Token*>::iterator iter;
    for(iter=lex.tokens->begin(); iter != lex.tokens->end(); iter++) {
        Token* newToken = new Token(**iter);
        tokens->push_back(newToken);
    }
    index = lex.index;
    state = lex.state;
}

Lex::~Lex(){
    for (int i = 0; i < tokens->size(); i++) {
        delete (*tokens)[i];
    }
    delete tokens;
    delete input;
}

bool Lex::operator==(const Lex& lex) {
    bool result = (tokens->size() == lex.tokens->size()) && (index == lex.index);
    if(result) {
        vector<Token*>::iterator iter1;
        vector<Token*>::iterator iter2;
        iter1 = tokens->begin();
        iter2 = lex.tokens->begin();
        while(result && iter1 != tokens->end() && iter2 != lex.tokens->end()) {
            result = **iter1 == **iter2;
            iter1++;
            iter2++;
        }
        result = result && iter1 == tokens->end() && iter2 == lex.tokens->end();
    }
    return result;
}

string Lex::toString() const {
    int count = 0;
    string result;
	if(tokens->size() !=0) {
		while(count < tokens->size()) {
			Token* token = (*tokens)[count];
			result += token->toString();
			count++;
		}
		result += "\nTotal Tokens = ";
		string countToString;
		result += itoa(countToString, count);
		result += "\n";
	}
	else {
	result = "Total Tokens = 0";
	}
    return result;
}

void Lex::generateTokens(Input* input) {
    tokens = new vector<Token*>();
    index = 0;
    state = Start;
    while(state != End) {
	
		state = nextState();
    }
}

Token* Lex::getCurrentToken() {
    return (*tokens)[index];
}

void Lex::advance() {
    index++;
}

bool Lex::hasNext() {
    return index < tokens->size();
}

void Lex::sawAQuote(State& result, char& character) {
	character = input->getCurrentCharacter();
	if(character == '\'') {
		result = PossibleEndOfString;
	} else if(character == -1) {
		result = Undefined;
	} else { //Every other character
		result = ProcessingString;
	}
	input->advance();
}

void Lex::sawPound(State& result, char& character) {
	character = input->getCurrentCharacter();
	if(character == '|') {
		result = ProcessingMultiComment;
		input->advance();
	} else if(character == '\n') {
		emit(COMMENT);
		result = getNextState();
	
	} else { //Every other character
		result = ProcessingComment;
		input->advance();
	}
}

void Lex::sawColon(State& result, char& character) {
	character = input->getCurrentCharacter();
	if(character == '-') {
		result = Colon_Dash;
		input->advance();
	} else { //Every other character
		result = Colon;
	}
}

void Lex::possibleId(State& result, char& character) {
	character = input->getCurrentCharacter();
	if(isalnum(character) !=0)
	{
		result = PossibleId;
		input->advance();
	}
	else{
		if(input->getTokensValue().compare("Schemes") == 0) {
			result = Schemes;
		}
		else if ( input->getTokensValue().compare("Facts") == 0) {
			result = Facts;
		}
		else if ( input->getTokensValue().compare("Rules") == 0) {
			result = Rules;
		}
		else if ( input->getTokensValue().compare("Queries") == 0) {
			result = Queries;
		}				
		else {
		result = Id;
		}
	}
}

void Lex::processingMultiComment(State& result, char& character){
	character = input->getCurrentCharacter();
	if(character == '|') {
		result = PossibleEndOfMultiComment;
		input->advance();
	} else if(character == -1) {
		result = Undefined;
	} else { //Every other character
		result = ProcessingMultiComment;
		input->advance();
	}
}

void Lex::processingComment(State& result, char& character){
	character = input->getCurrentCharacter();
	if(character == '\n'  || character == -1) {
		emit(COMMENT);
		result = getNextState();
	}
	else {
		input->advance();
		result = ProcessingComment;
	}
}

void Lex::processingString(State& result, char& character){
	character = input->getCurrentCharacter();
	if(character == '\'') {
		result = PossibleEndOfString;
		input->advance();
	} else if(character == -1) {
		result = Undefined;
	} else { //Every other character
		result = ProcessingString;
		input->advance();
	}
}

void Lex::possibleEndOfMultiComment(State& result, char& character){
	if(input->getCurrentCharacter() == '#') {
		input->advance();
		emit(COMMENT);
		result = getNextState();	
	} else { //Every other character
		input->advance();
		result = ProcessingMultiComment;    
	}
}
void Lex::possibleEndofString(State& result, char& character){
	if(input->getCurrentCharacter() == '\'') {
		input->advance();
		result = ProcessingString;
	} else { //Every other character
		emit(STRING);
		result = getNextState();
	}
}

State Lex::nextState() {
    State result;
    char character;
    switch(state) {
		case Start:               result = getNextState(); break;
		//Terminal Cases
        case Comma:               emit(COMMA); result = getNextState(); break;
        case Period:              emit(PERIOD); result = getNextState(); break;
		case Q_Mark:			  emit(Q_MARK); result = getNextState(); break;
		case Left_Paren:          emit(LEFT_PAREN); result = getNextState(); break;
		case Right_Paren:         emit(RIGHT_PAREN); result = getNextState(); break;
		case Multiply:            emit(MULTIPLY); result = getNextState(); break;
		case Add:                 emit(ADD); result = getNextState(); break;
		case Colon:				  emit(COLON); result = getNextState(); break;
        case Colon_Dash:          emit(COLON_DASH); result = getNextState(); break;
		case WhiteSpace:          input->mark(); result = getNextState(); break;
		case Id:				  emit(ID); result = getNextState(); break;
		case Schemes:			  emit(SCHEMES); result = getNextState(); break;
		case Facts:				  emit(FACTS); result = getNextState(); break;
		case Rules:				  emit(RULES); result = getNextState(); break;
		case Queries:			  emit(QUERIES); result = getNextState(); break;
		case Undefined:			  emit(UNDEFINED); result = getNextState(); break;
		//Prep Cases
		case SawAQuote:  		  sawAQuote(result, character); break;
		case SawPound:			  sawPound(result, character); break;
        case SawColon:			  sawColon(result, character); break;
		case PossibleId:		  possibleId(result, character); break;
		//Processing Cases
		case ProcessingMultiComment: processingMultiComment(result, character); break;
		case ProcessingComment:   processingComment(result, character); break;
		case ProcessingString:    processingString(result, character); break;	
		//Possible End Cases
		case PossibleEndOfMultiComment: possibleEndOfMultiComment(result, character); break;		
        case PossibleEndOfString: possibleEndofString(result, character); break;
        case End: emit(END); break;
    };
    return result;
}

State Lex::getNextState() {
    State result;
    char currentCharacter = input->getCurrentCharacter();
	
    //The handling of checking for whitespace and setting the result to Whilespace and
    //checking for letters and setting the result to Id will probably best be handled by
    //if statements rather then the switch statement.
    switch(currentCharacter) {
        case ','  : result = Comma; break;
        case '.'  : result = Period; break;
        case ':'  : result = SawColon; break;
        case '\'' : result = ProcessingString; break;
		case '?'  : result = Q_Mark; break;
		case '('  : result = Left_Paren; break;
		case ')'  : result = Right_Paren; break;
		case '#'  : result = SawPound; break;
		case '*'  : result = Multiply; break;
		case '+'  : result = Add; break;
        case -1   : result = End; emit(END); break;
        default:
			if(isspace(currentCharacter) != 0){result = WhiteSpace;}
			else if(isalpha(currentCharacter) !=0 ){result = PossibleId;}
			else{result = Undefined;}
    }
	//if(result != End) {
		input->advance();
	//}
    return result;
}

void Lex::emit(TokenType tokenType) {
    Token* token = new Token(tokenType, input->getTokensValue(), input->getCurrentTokensLineNumber());
    storeToken(token);
	input->mark();
}

void Lex::storeToken(Token* token) {
	tokens->push_back(token);
}

int main(int argc, char* argv[]) {
    Lex lex(argv[1]);
    cout<<lex.toString();
    return 0;
}
