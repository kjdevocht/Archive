package server.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import server.model.game.IGameModel;
import server.model.game.IUser;
import server.model.game.ai.AIList;
import server.result.CommandListResult;
import server.result.IResult;
import server.result.ListAIResult;
import server.result.ModelResult;
import serverProxy.request.*;

import java.io.IOException;

/**
 * The IServerTranslator is used to convert the HTTP Request
 * and JSON into java objects.  It also converts java objects
 * back into JSON so that the server can send the response.
 */
public interface IServerTranslator {

    /**
     * Read in the HttpExchange Request Body
     * @return JsonObject from Request
     * @throws IOException
     */
    public JsonObject getRequestBody() throws IOException;

    /**
     * Parse Login User Request Parameters from the Request Body
     * @return Login User Request Parameters
     * @throws IOException
     */
    public LoginUserRequest getLoginUserRequest() throws IOException;

    /**
     * Parse Register User Request Parameters from the Request Body
     * @return Register User Request Parameters
     * @throws IOException
     */
    public RegisterUserRequest getRegisterUserRequest() throws IOException;

    /**
     * Parse Create Games Request Parameters from the Request Body
     * @return Create Games Request Parameters
     * @throws IOException
     */
    public CreateGamesRequest getCreateGameRequest() throws IOException;

    /**
     * Parse Join Game Request Parameters from the Request Body
     * @return Join Game Request Parameters
     * @throws IOException
     */
    public JoinGameRequest getJoinGameRequest() throws IOException;

    /**
     * Parse Load Game Request Parameters from the Request Body
     * @return Load Game Request Parameters
     * @throws IOException
     */
    public LoadGameRequest getLoadGameRequest() throws IOException;

    /**
     * Parse Save Game Request Parameters from the Request Body
     * @return Save Game Request Parameters
     * @throws IOException
     */
    public SaveGameRequest getSaveGameRequest() throws IOException;

    /**
     * Parse Get List Games Request Parameters from the Request Body
     * @return Get List Games Request Parameters
     * @throws IOException
     */
    public ListGamesRequest getListGamesRequest() throws IOException;

    /**
     * Create a response for the List Games Request
     * @return Json List Games Response
     */
    public JsonArray makeListGamesResponse(IResult result);

    /**
     * Parse Add AI Request Parameters from the Request Body
     * @return Add AI Request Parameters
     * @throws IOException
     */
    public AddAIRequest getAddAIRequest() throws IOException;

    /**
     * Parse GET Commands Request Parameters from the Request Body
     * @return GET Commands Request Parameters
     * @throws IOException
     */
    public GetGameCommandsRequest getCommandsRequest() throws IOException;

    /**
     * Parse POST Commands Request Parameters from the Request Body
     * @return POST Commands Request Parameters
     * @throws IOException
     */
    public PostGameCommandsRequest getPostCommandsRequest() throws IOException;

    /**
     * Parse List AI Request Parameters from the Request Body
     * @return List AI Request Parameters
     * @throws IOException
     */
    public ListAIRequest getListAIRequest() throws IOException;

    /**
     * Parse Game Model Request Parameters from the Request Body
     * @return Game Model Request Parameters
     * @throws IOException
     */
    public GameModelRequest getGameModelRequest() throws IOException;

    /**
     * Parse Reset Game Request Parameters from the Request Body
     * @return Reset Game Request Parameters
     * @throws IOException
     */
    public ResetGameRequest getResetGameRequest() throws IOException;

    /**
     * Make a Json Response of a Game Model
     * @return a Game Model
     * @throws IOException
     */
    public String makeGameModelResponse(IGameModel model, int versionNumber)
			throws IOException;
    public String makeGameModelResponse(IGameModel model)
			throws IOException;
    /**
     * Make a Json Response of an AI List
     * @return a Json AI List
     * @throws IOException
     */
    public JsonObject makeAIListResponse(IResult result) throws IOException;

    /**
     * Make a Json Response of a Command List
     * @return json Command List
     * @throws IOException
     */
    public JsonObject makeCommandList(IResult result) throws IOException;


    /**
     * Parse Accept Trade Request Parameters from the Request Body
     * @return Accept Trade Request Parameters
     * @throws IOException
     */
    public AcceptTradeRequest getAcceptTradeRequest() throws IOException;

    /**
     * Parse Build City Request Parameters from the Request Body
     * @return Build City Request Parameters
     * @throws IOException
     */
    public BuildCityRequest getBuildCityRequest() throws IOException;

    /**
     * Parse Build Road Request Parameters from the Request Body
     * @return Build Road Request Parameters
     * @throws IOException
     */
    public BuildRoadRequest getBuildRoadRequest() throws IOException;

    /**
     * Parse Build Settlement Request Parameters from the Request Body
     * @return Build Settlement Request Parameters
     * @throws IOException
     */
    public BuildSettlementRequest getBuildSettlementRequest() throws IOException;

    /**
     * Parse Buy Dev Card Request Parameters from the Request Body
     * @return Buy Dev Card Request Parameters
     * @throws IOException
     */
    public BuyDevCardRequest getBuyDevCardRequest() throws IOException;

    /**
     * Parse Discard Cards Request Parameters from the Request Body
     * @return Discard Cards Request Parameters
     * @throws IOException
     */
    public DiscardCardsRequestServer getDiscardCardsRequest() throws IOException;

    /**
     * Parse Finish Turn Request Parameters from the Request Body
     * @return Finish Turn Request Parameters
     * @throws IOException
     */
    public FinishTurnRequest getFinishTurnRequest() throws IOException;

    /**
     * Parse Maritime Trade Request Parameters from the Request Body
     * @return Maritime Trade Request Parameters
     * @throws IOException
     */
    public MaritimeTradeRequest getMaritimeTradeRequest() throws IOException;

    /**
     * Parse Monopoly Request Parameters from the Request Body
     * @return Monopoly Request Parameters
     * @throws IOException
     */
    public MonopolyRequest getMonopolyRequest() throws IOException;

    /**
     * Parse Monument Request Parameters from the Request Body
     * @return Monument Request
     * @throws IOException
     */
    public MonumentRequest getMonumentRequest() throws IOException;

    /**
     * Parse Offer Trade Request Parameters from the Request Body
     * @return Offer Trade Request Parameters
     * @throws IOException
     */
    public OfferTradeRequestServer getOfferTradeRequest() throws IOException;

    /**
     * Parse Road Building Request Parameters from the Request Body
     * @return Road Building Request Parameters
     * @throws IOException
     */
    public RoadBuildingRequest getRoadBuildingRequest() throws IOException;

    /**
     * Parse Rob Player Request Parameters from the Request Body
     * @return Rob Player Request Parameters
     * @throws IOException
     */
    public RobPlayerRequest getRobPlayerRequest() throws IOException;

    /**
     * Parse Roll Number Request Parameters from the Request Body
     * @return Roll Number Request Parameters
     * @throws IOException
     */
    public RollNumberRequest getRollNumberRequest() throws IOException;

    /**
     * Parse Send Chat Request Parameters from the Request Body
     * @return Send Chat Request Parameters
     * @throws IOException
     */
    public SendChatRequest getSendChatRequest() throws IOException;

    /**
     * Parse Soldier Request Parameters from the Request Body
     * @return Soldier Request Parameters
     * @throws IOException
     */
    public SoldierRequest getSoldierRequest() throws IOException;

    /**
     * Parse Year of Plenty Request Parameters from the Request Body
     * @return Year of Plenty Request Parameters
     * @throws IOException
     */
    public YearOfPlentyRequest getYearOfPlentyRequest() throws IOException;
    public JsonObject makeLoginCookieResponse(IUser user);

	JsonObject makeSimpleCatanGameObject(IGameModel gameModel);

	public String makeJoinGameCookie(int gameId, Credentials credentials);


}
