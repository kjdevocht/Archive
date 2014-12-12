package server.handler.games;

import com.sun.net.httpserver.HttpExchange;
import server.ServerFacade;
import server.handler.Credentials;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.logging.Logger;
import server.result.SuccessResult;
import serverProxy.request.JoinGameRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Server Handler to process /games/join
 */
public class JoinGameHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("Join Game Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = null;
        if(!httpExchange.getRequestMethod().equals("POST")) {;
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            result = "Bad Request Method: " + httpExchange.getRequestMethod();
        } else {
            try {

                ServerTranslator translator = new ServerTranslator(httpExchange.getRequestBody(), httpExchange.getRequestHeaders());

                JoinGameRequest request = translator.getJoinGameRequest();
                Credentials credentials = translator.getCookieCredentials();

                boolean successJoin = ServerFacade.getSingleton().joinGame(request, credentials);

                if(!successJoin) {
                    throw new Exception("Could not add game");
                }
                result = "Success";
                httpExchange.getResponseHeaders().add("content-type", "text/plain");
                String cookieSet = translator.makeJoinGameCookie(request.getGameId(), credentials);
                Logger.getSingleton().debug("Setting Cookie: "+cookieSet);
                httpExchange.getResponseHeaders().add("set-cookie", cookieSet);
 
                
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
