package server;

import server.command.*;
import server.handler.Credentials;
import server.model.*;
import server.model.game.*;
import server.model.game.ai.*;
import server.result.LoginResult;
import server.result.ModelResult;
import serverProxy.request.*;

import java.util.List;

/**
 * The IServerFacade is a single Class that the handlers can call in order to
 * actually process the request.  It has a function for each end point.
 */
public interface IServerFacade {

    /**
     * Change the level of logging on the server.
     * @param level See LogLevel for options
     * @return result of change
     */
    public String changeLogLevel(String level);

    /**
     * Logs in User
     * @param request Login User Request Parameters
     * @return Success Status
     */
    public LoginResult loginUser(LoginUserRequest request);

    /**
     * Registers a User
     * @param request Register User Request Parameters
     * @return Success Status
     */
    public LoginResult registerUser(RegisterUserRequest request);

    /**
     * Allows a user to join a game
     * @param request Join Game Request Parameters
     * @return Success Status
     */
    public boolean joinGame(JoinGameRequest request, Credentials credientials);

    /**
     * Allows a user to create a game
     * @param request Create Games Request Parameters
     * @return Success Status
     */
    public ModelResult createGame(CreateGamesRequest request);

    /**
     * Get a list of created games
     *
     * @param request List Games Request Parameters
     * @return List of Games
     */
    public List<IGameModel> listGames(ListGamesRequest request);

    /**
     * Loads a game that has been previously saved
     * @param request Load Game Reqeust Parameters
     * @return Success Status
     */
    public String loadGame(LoadGameRequest request);

    /**
     * Saves a game, can restart server and load saved game
     * @param request Save Game Request Parameters
     * @return Success Status
     */
    public String saveGame(SaveGameRequest request);

    /**
     * Add an AI Player to a game
     * @param request Add AI Request Parameters
     * @return Success Status
     */
    public String addAI(AddAIRequest request);

    /**
     * Get a list of the available AI Player Types
     * @param request List AI Request Parameters
     * @return List of AI Types
     */
    public AIList listAI(ListAIRequest request);

    /**
     * Get a list of commands available to send to the server
     * @param request Get Game Commands Request Parameters
     * @return List of Game Commands
     */
    public ICommandList getCommands(GetGameCommandsRequest request);

    /**
     * Send a list of game commands to the server
     * @param request Post Game Commands Request Parameters
     * @return a Game Model
     */
    public IGameModel postCommands(PostGameCommandsRequest request);

    /**
     * Get the most recent Game Model
     * @param request Game Model Request Parameters
     * @return a Game Model
     */
    public IGameModel getGameModel(GameModelRequest request, Credentials credientals);

    /**
     * Reset the game model
     * @param request Reset Game Request Parameters
     * @return Success Status
     */
    public String resetGame(ResetGameRequest request);

    /**
     * Send Command to Accept a Trade
     * @param request Accept Trade Request Parameters
     * @return a Game Model
     */
    public IGameModel acceptTrade(AcceptTradeRequest request, Credentials credentials);

    /**
     * Send Command to Build a City
     * @param request Build City Request Parameters
     * @return a Game Model
     */
    public IGameModel buildCity(BuildCityRequest request, Credentials credentials);

    /**
     * Send Command to Build a Road
     * @param request Build Road Request Parameters
     * @return a Game Model
     */
    public IGameModel buildRoad(BuildRoadRequest request, Credentials credentials);

    /**
     * Send Command to Build a Settlement
     *
     * @param request Build Settlement Request Parameters
     * @param credentials
     * @return a Game Model
     */
    public IGameModel buildSettlement(BuildSettlementRequest request, Credentials credentials);

    /**
     * Send Command to Buy A Development Card
     * @param request Buy Dev Card Request Parameters
     * @return a Game Model
     */
    public IGameModel buyDevCard(BuyDevCardRequest request, Credentials credentials);

    /**
     * Send Command to Discard Cards
     * @param request Discard Cards Request Parameters
     * @return a Game Model
     */
    public IGameModel discardCards(DiscardCardsRequestServer request, Credentials credentials);

    /**
     * Send Command to Finish Turn
     * @param request Finish Turn Request Parameters
     * @return a Game Model
     */
    public IGameModel finishTurn(FinishTurnRequest request, Credentials credentials);

    /**
     * Send Command to Make a Maritime Trade
     * @param request Maritime Trade Request Parameters
     * @return a Game Model
     */
    public IGameModel maritimeTrade(MaritimeTradeRequest request, Credentials credentials);

    /**
     * Send Command to Play Monopoly Dev Card
     * @param request Monopoly Request Parameters
     * @return a Game Model
     */
    public IGameModel monopoly(MonopolyRequest request, Credentials credentials);

    /**
     * Send Command to Play Monument Card
     * @param request Monument Request Parameters
     * @return a Game Model
     */
    public IGameModel monument(MonumentRequest request, Credentials credentials);

    /**
     * Send Command to Make a Domestic Trade
     * @param request Offer Trade Request Parameters
     * @return a Game Model
     */
    public IGameModel offerTrade(OfferTradeRequestServer request, Credentials credentials);

    /**
     * Send Command to Play Road Building Card
     * @param request Road Building Request Parameters
     * @return a Game Model
     */
    public IGameModel roadBuilding(RoadBuildingRequest request, Credentials credentials);

    /**
     * Send Command to Rob Player
     * @param request Rob Player Request Parameters
     * @return a Game Model
     */
    public IGameModel robPlayer(RobPlayerRequest request, Credentials credentials);

    /**
     * Send Command to Roll Number
     * @param request Roll Number Request Parameters
     * @return a Game Model
     */
    public IGameModel rollNumber(RollNumberRequest request, Credentials credentials);

    /**
     * Send Command to Send Chat
     * @param request Send Chat Request Parameters
     * @return a Game Model
     */
    public IGameModel sendChat(SendChatRequest request, Credentials credentials);

    /**
     * Send Command to Play Soldier Card
     * @param request Soldier Request Parameters
     * @return a Game Model
     */
    public IGameModel soldier(SoldierRequest request, Credentials credentials);

    /**
     * Send Command to Play Year of Plenty Card
     * @param request Year of Plenty Request Parameters
     * @return a Game Model
     */
    public IGameModel yearOfPlenty(YearOfPlentyRequest request, Credentials credentials);

}
