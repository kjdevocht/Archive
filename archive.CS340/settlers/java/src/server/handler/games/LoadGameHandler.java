package server.handler.games;

import com.sun.net.httpserver.HttpExchange;
import server.ServerFacade;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.logging.Logger;
import server.result.SuccessResult;
import serverProxy.request.LoadGameRequest;
import serverProxy.request.SaveGameRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Server Handler to process /games/load
 */
public class LoadGameHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("Load Game Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = "FAIL_";
        if(!httpExchange.getRequestMethod().equals("POST")) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            result = "Bad Request Method: " + httpExchange.getRequestMethod();
        } else {
            try{

                ServerTranslator translator = new ServerTranslator(httpExchange);

                LoadGameRequest request = translator.getLoadGameRequest();

                result = ServerFacade.getSingleton().loadGame(request);

                httpExchange.getResponseHeaders().add("content-type", "text/plain");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            catch(Exception e) {
                httpExchange.getResponseHeaders().add("content-type", "text/plain");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                result = "FAIL - " + e.getMessage();
            }
        }

        os.write((result).getBytes());
        os.close();
        httpExchange.getResponseBody().close();
    }
}
