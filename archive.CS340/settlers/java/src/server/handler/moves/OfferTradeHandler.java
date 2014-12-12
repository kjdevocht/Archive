package server.handler.moves;

import com.sun.net.httpserver.HttpExchange;
import server.ServerFacade;
import server.handler.Credentials;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.logging.Logger;
import server.model.game.IGameModel;
import server.result.IResult;
import server.result.ModelResult;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.OfferTradeRequest;
import serverProxy.request.OfferTradeRequestServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Server Handler to process /moves/offerTrade
 */
public class OfferTradeHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("Accept Trade Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = "";
        if(!httpExchange.getRequestMethod().equals("POST")) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            result = "Bad Request Method: " + httpExchange.getRequestMethod();
        } else {
            try{

                ServerTranslator translator = new ServerTranslator(httpExchange);
                Credentials credentials = translator.getCookieCredentials();
                OfferTradeRequestServer request = translator.getOfferTradeRequest();
                IGameModel gameModel = new ModelResult(ServerFacade.getSingleton().offerTrade(request, credentials)).getResult();
                if(gameModel == null) {
                    throw new Exception("unable complete move");
                }

                result = translator.makeGameModelResponse(gameModel);
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
