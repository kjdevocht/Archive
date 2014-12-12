package server.handler.util;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import server.ServerFacade;
import server.handler.ServerTranslator;
import server.handler.IHandler;
import server.logging.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Http handler for 
 */
public class ChangeLogLevelHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("Change Log Level Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = "";
        if(!httpExchange.getRequestMethod().equals("POST")) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            result = "Bad Request Method: " + httpExchange.getRequestMethod();
        } else {
            try {
                ServerTranslator translator = new ServerTranslator(httpExchange.getRequestBody());
                JsonObject request = translator.getRequestBody();
                String level = request.get("logLevel").getAsString();
                result = ServerFacade.getSingleton().changeLogLevel(level);

                httpExchange.getResponseHeaders().add("content-type", "text/plain");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            catch(Exception e) {
                e.printStackTrace();
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
