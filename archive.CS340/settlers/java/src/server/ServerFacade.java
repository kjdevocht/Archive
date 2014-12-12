package server;

import server.command.ICommandList;
import server.command.IMoveCommand;
import server.command.game.*;
import server.command.games.*;
import server.command.moves.*;
import server.command.user.*;
import server.command.util.*;
import server.handler.Credentials;
import server.model.game.IGameModel;
import server.model.game.ai.AIList;
import server.persistance.IPersistanceProvider;
import server.persistance.PersistanceProvider;
import server.result.LoginResult;
import server.result.ModelResult;
import serverProxy.request.*;

import java.util.List;

/**
 * Created by mitch10e on 11/5/14.
 */
public class ServerFacade implements IServerFacade {

    private static ServerFacade singleton;
    public static ServerFacade getSingleton() {
        if(singleton == null) {
            singleton = new ServerFacade();
        }
        return singleton;
    }

    @Override
    public String changeLogLevel(String level) {
        ChangeLogLevelCommand command = new ChangeLogLevelCommand(level);
        return command.execute();
    }

    @Override
    public LoginResult loginUser(LoginUserRequest request) {
        LoginUserCommand command = new LoginUserCommand(request);
        return command.execute();
    }

    @Override
    public LoginResult registerUser(RegisterUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(request);
        return command.execute();
    }

    @Override
    public boolean joinGame(JoinGameRequest request, Credentials credientials) {
        JoinGameCommand command = new JoinGameCommand(request, credientials);
        return command.execute();
    }

    @Override
    public ModelResult createGame(CreateGamesRequest request) {
        CreateGameCommand command = new CreateGameCommand(request);
        return command.execute();
    }

    @Override
    public List<IGameModel> listGames(ListGamesRequest request) {
        ListGamesCommand command = new ListGamesCommand(request);
        return command.execute();
    }

    @Override
    public String loadGame(LoadGameRequest request) {
        LoadGameCommand command = new LoadGameCommand(request);
        return command.execute();
    }

    @Override
    public String saveGame(SaveGameRequest request) {
        SaveGameCommand command = new SaveGameCommand(request);
        return command.execute();
    }

    @Override
    public String addAI(AddAIRequest request) {
        AddAICommand command = new AddAICommand(request);
        return command.execute();
    }

    @Override
    public AIList listAI(ListAIRequest request) {
        ListAICommand command = new ListAICommand(request);
        return command.execute();
    }

    @Override
    public ICommandList getCommands(GetGameCommandsRequest request) {
        ListCommandsCommand command = new ListCommandsCommand(request);
        return command.execute();
    }

    @Override
    public IGameModel postCommands(PostGameCommandsRequest request) {
        RunCommandsCommand command = new RunCommandsCommand(request);
        return command.execute();
    }

    @Override
    public IGameModel getGameModel(GameModelRequest request, Credentials credientals) {
        ModelCommand command = new ModelCommand(request, credientals);
        return command.execute();
    }

    @Override
    public String resetGame(ResetGameRequest request) {
        ResetCommand command = new ResetCommand(request);
        return command.execute();
    }

    @Override
    public IGameModel acceptTrade(AcceptTradeRequest request, Credentials credentials) {
        AcceptTradeCommand command = new AcceptTradeCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel buildCity(BuildCityRequest request, Credentials credentials) {
        BuildCityCommand command = new BuildCityCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel buildRoad(BuildRoadRequest request, Credentials credentials) {
        BuildRoadCommand command = new BuildRoadCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel buildSettlement(BuildSettlementRequest request, Credentials credentials) {
        BuildSettlementCommand command = new BuildSettlementCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel buyDevCard(BuyDevCardRequest request, Credentials credentials) {
        BuyDevCardCommand command = new BuyDevCardCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel discardCards(DiscardCardsRequestServer request, Credentials credentials) {
        DiscardCardsCommand command = new DiscardCardsCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel finishTurn(FinishTurnRequest request, Credentials credentials) {
        FinishTurnCommand command = new FinishTurnCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel maritimeTrade(MaritimeTradeRequest request, Credentials credentials) {
        MaritimeTradeCommand command = new MaritimeTradeCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel monopoly(MonopolyRequest request, Credentials credentials) {
        MonopolyCommand command = new MonopolyCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel monument(MonumentRequest request, Credentials credentials) {
        MonumentCommand command = new MonumentCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel offerTrade(OfferTradeRequestServer request, Credentials credentials) {
        OfferTradeCommand command = new OfferTradeCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel roadBuilding(RoadBuildingRequest request, Credentials credentials) {
        RoadBuildingCommand command = new RoadBuildingCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel robPlayer(RobPlayerRequest request, Credentials credentials) {
        RobPlayerCommand command = new RobPlayerCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel rollNumber(RollNumberRequest request, Credentials credentials) {
        RollNumberCommand command = new RollNumberCommand(request,  credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel sendChat(SendChatRequest request, Credentials credentials) {
        SendChatCommand command = new SendChatCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel soldier(SoldierRequest request, Credentials credentials) {
        SoldierCommand command = new SoldierCommand(request, credentials);
        return this.executeGameCommand(command);
    }

    @Override
    public IGameModel yearOfPlenty(YearOfPlentyRequest request, Credentials credentials) {
        YearOfPlentyCommand command = new YearOfPlentyCommand(request, credentials);
        return this.executeGameCommand(command);
    }
    
    @SuppressWarnings("unused")
	private IGameModel executeGameCommand(IMoveCommand command)
    {
		//System.out.println("Executing a Command");

        IGameModel gameModel = (IGameModel)command.execute();

        if(gameModel!= null) {
		    IPersistanceProvider p = PersistanceProvider.getSingleton();
            p.getGamesDAO().storeCommand(command.getGameId(), command);
        }
        return gameModel;
    }

}
