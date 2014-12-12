package server.handler.games;

import com.sun.net.httpserver.HttpExchange;
import server.ServerFacade;
import server.handler.Credentials;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.logging.Logger;
import server.result.IResult;
import server.result.ListGamesResult;
import serverProxy.request.ListGamesRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Server Handler to process /games/list
 */
public class ListGamesHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("List Games Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = "";
        String requestMethod = httpExchange.getRequestMethod();        
        if(requestMethod.equals("POST"))
        	requestMethod = "GET";        
        if(!requestMethod.equals("GET")) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            result = "Bad Request Method: " + httpExchange.getRequestMethod();

        } else {
            try{
                ServerTranslator translator = new ServerTranslator(httpExchange.getRequestBody());
                ListGamesRequest request = translator.getListGamesRequest();
                IResult listOfGames = new ListGamesResult(ServerFacade.getSingleton().listGames(request));
                result = translator.makeListGamesResponse(listOfGames).toString();
                httpExchange.getResponseHeaders().add("content-type", "application/json");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            catch(Exception e) {
                httpExchange.getResponseHeaders().add("content-type", "text/plain");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                result = "FAIL - " + e.getMessage();
            }
        }


        os.write(result.toString().getBytes());
        os.close();
        httpExchange.getResponseBody().close();
    }
}
