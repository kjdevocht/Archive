package server.handler.games;

import com.sun.net.httpserver.HttpExchange;

import server.ServerFacade;
import server.handler.Credentials;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.logging.Logger;
import server.model.game.IGameModel;
import server.result.IResult;
import server.result.ModelResult;
import server.result.SuccessResult;
import serverProxy.request.CreateGamesRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Server Handler to process /games/create
 */
public class CreateGameHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("Create Game Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = null;
        if(!httpExchange.getRequestMethod().equals("POST")) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            result = "Bad Request Method: " + httpExchange.getRequestMethod();
        } else {
        	try {
	            ServerTranslator translator = new ServerTranslator(httpExchange.getRequestBody());
	            CreateGamesRequest request = translator.getCreateGameRequest();
	            IResult modelResult = ServerFacade.getSingleton().createGame(request);
	            if(modelResult == null) {
	            	throw new Exception("failed to create game");
	            }
	            else {
	            	IGameModel gameModel = (IGameModel) modelResult.getResult();
	            	
	            	result = translator.makeSimpleCatanGameObject(gameModel).toString();
	            	
	                httpExchange.getResponseHeaders().add("content-type", "application/json");
	            	httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
	            }
        	}catch(Exception e) {
        		httpExchange.getResponseHeaders().add("content-type", "text/plain");
            	httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            	result = "FAIL - " + e.getMessage();
        	}
        }

        os.write(result.getBytes());
        os.close();
        httpExchange.getResponseBody().close();
    }
}
