package server.handler.game;

import com.sun.net.httpserver.HttpExchange;
import server.ServerFacade;
import server.handler.Credentials;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.model.game.IGameModel;
import serverProxy.request.GameModelRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Server Handler to process /game/model
 */
public class ModelHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //Logger.getSingleton().info("Get Game Model Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = "";
        String requestMethod = httpExchange.getRequestMethod();
        if(requestMethod.equals("POST"))
            requestMethod = "GET";
        if(!requestMethod.equals("GET")) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            result = "Bad Request Method: " + httpExchange.getRequestMethod();
            
        } else {
        	try {
	        	ServerTranslator translator = new ServerTranslator(httpExchange);
	            Credentials credentials = translator.getCookieCredentials();
	            
	            GameModelRequest request = translator.getGameModelRequest();
	            IGameModel gameModel = ServerFacade.getSingleton().getGameModel(request, credentials);
	            
	            if(gameModel == null) {
	            	throw new Exception("unable to show model");
	            }


	            result = translator.makeGameModelResponse(gameModel, request.getVersionNumber());
                httpExchange.getResponseHeaders().add("content-type", "application/json");
	            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
	            
        	}catch (Exception e) {
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
