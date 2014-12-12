package server.handler.game;

import com.sun.net.httpserver.HttpExchange;

import server.ServerFacade;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.logging.Logger;
import server.result.IResult;
import server.result.ListAIResult;
import server.result.ListGamesResult;
import serverProxy.request.ListAIRequest;
import serverProxy.request.ListGamesRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Server Handler to process /game/listAI
 */
public class ListAIHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("List AI Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = "";
        
        String requestMethod = httpExchange.getRequestMethod();        
        if(requestMethod.equals("POST"))
        	requestMethod = "GET";        
        if(!requestMethod.equals("GET")) {        	
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);            
            result = "Bad Request Method: " + httpExchange.getRequestMethod();

        } else {
        	
        	result = "[\"No AIs\"]";
        	httpExchange.getResponseHeaders().add("content-type", "application/json");
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);            
        }

        os.write(result.toString().getBytes());
        os.close();
        httpExchange.getResponseBody().close();
    }
}
