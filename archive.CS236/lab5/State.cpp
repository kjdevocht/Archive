#include "State.h"

using namespace std;

string StateToString(State tokenType){
    string result = "";
    switch(tokenType){
        case Comma:                      result = "Comma"; break;
        case Period:                     result = "Period"; break;
		case Q_Mark:					 result = "Q_Mark"; break;
		case Left_Paren:    			 result = "Left_Paren"; break;
		case Right_Paren:                result = "Right_Paren"; break;
        case SawColon:                   result = "SawColon"; break;
		case Colon:						 result = "Colon"; break;
        case Colon_Dash:                 result = "Colon_Dash"; break;
		case Schemes:					 result = "Schemes"; break;
		case Facts:						 result = "Facts"; break;
		case Rules:						 result = "Rules"; break;
		case Queries:					 result = "Queries"; break;
		case Multiply:					 result = "Multiply"; break;
		case Add:						 result = "Add"; break;
        case SawAQuote:                  result = "SawAQuote"; break;
        case ProcessingString:           result = "ProcessingString"; break;
        case PossibleEndOfString:        result = "PossibleEndOfString"; break;
        case Start:                      result = "Start"; break;
        case End:                        result = "End"; break;
		case WhiteSpace:				 result = "WhiteSpace"; break;
		case Undefined:					 result = "Undefined"; break;
		case SawPound:					 result = "SawPound"; break;
		case ProcessingMultiComment:     result = "ProcessingMultiComment"; break;
		case ProcessingComment:          result = "ProcessingComment"; break;
		case PossibleEndOfMultiComment:  result = "PossibleEndOfMultiComment"; break;
		case PossibleId:				 result = "PossibleId"; break;
		case Id:						 result = "Id"; break;
    }
    return result;
};
