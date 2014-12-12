package server.handler.game;

import com.sun.net.httpserver.HttpExchange;
import server.ServerFacade;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.logging.Logger;
import server.result.SuccessResult;
import serverProxy.request.CreateGamesRequest;
import serverProxy.request.ResetGameRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Server Handler to process /game/reset
 */
public class ResetHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("Reset Game Handler");
        OutputStream os = httpExchange.getResponseBody();
        SuccessResult result = new SuccessResult();
        if(!httpExchange.getRequestMethod().equals("POST")) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            result.setResult("Bad Request Method: " + httpExchange.getRequestMethod());
        } else {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            ServerTranslator translator = new ServerTranslator(httpExchange.getRequestBody());
            ResetGameRequest request = translator.getResetGameRequest();
            result.setResult(ServerFacade.getSingleton().resetGame(request));
        }

        os.write(((String)result.getResult()).getBytes());
        os.close();
        httpExchange.getResponseBody().close();
    }
}
