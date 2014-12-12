package server.handler.util;

import com.sun.net.httpserver.HttpExchange;
import server.handler.IHandler;
import server.logging.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by mitch10e on 11/4/14.
 */

public class HelloHandler implements IHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("Hello Handler");
        String result = "Hello.";
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os = httpExchange.getResponseBody();
        os.write(result.getBytes());
        os.close();
        httpExchange.getResponseBody().close();
    }
}
