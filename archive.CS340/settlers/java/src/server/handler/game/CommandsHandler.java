package server.handler.game;

import com.sun.net.httpserver.HttpExchange;
import server.ServerFacade;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.logging.Logger;
import server.model.game.IGameModel;
import server.result.CommandListResult;
import server.result.IResult;
import server.result.ModelResult;
import serverProxy.request.GetGameCommandsRequest;
import serverProxy.request.PostGameCommandsRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Server Handler to process /game/commands
 */
public class CommandsHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("COMMANDS Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = "";
        if(httpExchange.getRequestMethod().equals("GET")) {
            Logger.getSingleton().debug("GET");
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            ServerTranslator translator = new ServerTranslator(httpExchange.getRequestBody());
            GetGameCommandsRequest request = translator.getCommandsRequest();
            IResult commandList = new CommandListResult(ServerFacade.getSingleton().getCommands(request));
            result = translator.makeCommandList(commandList).toString();

        } else if(httpExchange.getRequestMethod().equals("POST")){
            Logger.getSingleton().debug("POST");
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            ServerTranslator translator = new ServerTranslator(httpExchange.getRequestBody());
            PostGameCommandsRequest request = translator.getPostCommandsRequest();
            IResult gameModel = new ModelResult(ServerFacade.getSingleton().postCommands(request));
            result = translator.makeGameModelResponse((IGameModel)gameModel.getResult(), -1).toString();

        } else {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            result = "Bad Request Method: " + httpExchange.getRequestMethod();
        }

        os.write(result.getBytes());
        os.close();
        httpExchange.getResponseBody().close();
    }
}
