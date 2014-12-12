package server;

import com.sun.net.httpserver.HttpServer;

import server.handler.game.*;
import server.handler.games.*;
import server.handler.moves.*;
import server.handler.user.LoginHandler;
import server.handler.user.RegisterHandler;
import server.handler.util.ChangeLogLevelHandler;
import server.handler.util.HelloHandler;
import server.logging.Logger;
import server.model.ServerModel;
import server.persistance.IPersistanceProvider;
import server.persistance.PersistanceProvider;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by mitch10e on 11/4/14.
 */
public class Server implements IServer {

    public static int TEST_PORT = 55555;
    private static int DEFAULT_PORT = 8081;
    private static int MAX_WAITING_CONNECTIONS = 10;
    private static Logger logger;
    private HttpServer server;

    public Server(){
        logger = Logger.getSingleton();
        logger.debug("Logger Initialized");
    }

    @Override
    public void run(int port) {
        logger.info("Using Port: " + DEFAULT_PORT);
        try {
            server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            logger.error("Failed to create HttpServer. " + e.getMessage());
        }

        server.setExecutor(null);
        logger.info("Creating Endpoints");
        createContexts();

        logger.info("Starting Server");
        server.start();
    }

    private void createContexts() {
        server.createContext("/hello", new HelloHandler());

        server.createContext("/user/login", new LoginHandler());
        server.createContext("/user/register", new RegisterHandler());

        server.createContext("/games/list", new ListGamesHandler());
        server.createContext("/games/create", new CreateGameHandler());
        server.createContext("/games/join", new JoinGameHandler());
        server.createContext("/games/save", new SaveGameHandler());
        server.createContext("/games/load", new LoadGameHandler());

        server.createContext("/game/model", new ModelHandler());
        server.createContext("/game/reset", new ResetHandler());
        server.createContext("/game/commands", new CommandsHandler());
        server.createContext("/game/addAI", new AddAIHandler());
        server.createContext("/game/listAI", new ListAIHandler());

        server.createContext("/moves/sendChat", new SendChatHandler());
        server.createContext("/moves/rollNumber", new RollNumberHandler());
        server.createContext("/moves/robPlayer", new RobPlayerHandler());
        server.createContext("/moves/finishTurn", new FinishTurnHandler());
        server.createContext("/moves/buyDevCard", new BuyDevCardHandler());
        server.createContext("/moves/Year_of_Plenty", new YearOfPlentyHandler());
        server.createContext("/moves/Road_Building", new RoadBuildingHandler());
        server.createContext("/moves/Soldier", new SoldierHandler());
        server.createContext("/moves/Monopoly", new MonopolyHandler());
        server.createContext("/moves/Monument", new MonumentHandler());
        server.createContext("/moves/buildRoad", new BuildRoadHandler());
        server.createContext("/moves/buildSettlement", new BuildSettlementHandler());
        server.createContext("/moves/buildCity", new BuildCityHandler());
        server.createContext("/moves/offerTrade", new OfferTradeHandler());
        server.createContext("/moves/acceptTrade", new AcceptTradeHandler());
        server.createContext("/moves/maritimeTrade", new MaritimeTradeHandler());
        server.createContext("/moves/discardCards", new DiscardCardsHandler());

        server.createContext("/util/changeLogLevel", new ChangeLogLevelHandler());
    }

    public static void main(final String args[]) {
    	
    	String type = "sqliteJar.jar";
    	int count = 5;
		//System.out.println("Ode to Curtis Wigington: maritime trade 2 wheat traded for log game me nothing");

		//PersistanceProvider.init(type, count);

    	PersistanceProvider.init(args[0], Integer.parseInt(args[1]));
        ServerModel model = ServerModel.getSingleton();
        IPersistanceProvider pp = PersistanceProvider.getSingleton();
        model.addAllUsers(pp.getUsersDAO().getUsers());
		pp.getGamesDAO().loadGames();

        new Server().run(DEFAULT_PORT);
        //System.out.println("maritime trade 2 wheat traded for log game me nothing");
        //System.out.println("- Curtis");
    }
}
