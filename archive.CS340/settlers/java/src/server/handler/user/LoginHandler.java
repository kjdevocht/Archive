package server.handler.user;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;

import server.ServerFacade;
import server.handler.IHandler;
import server.handler.ServerTranslator;
import server.logging.Logger;
import server.model.game.IUser;
import server.result.IResult;
import server.result.LoginResult;
import server.result.SuccessResult;
import serverProxy.request.LoginUserRequest;
import serverProxy.request.RegisterUserRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * Server Handler to process /user/login
 */
public class  LoginHandler implements IHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Logger.getSingleton().info("Login User Handler");
        OutputStream os = httpExchange.getResponseBody();
        String result = null;
        if(!httpExchange.getRequestMethod().equals("POST")) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            
            result = "Bad Request Method: " + httpExchange.getRequestMethod();
        } else {
            
            ServerTranslator translator = new ServerTranslator(httpExchange.getRequestBody());
            
            LoginUserRequest request = null;
            try{
            	request = translator.getLoginUserRequest();
            	IResult loginResult = null;
            	loginResult = ServerFacade.getSingleton().loginUser(request);
                if(loginResult.getResult() == null) {
                	throw new Exception("Could not login");
                }
                else {
                	IUser user = (IUser)loginResult.getResult();
                	httpExchange.getResponseHeaders().add("set-cookie", "catan.user="+URLEncoder.encode(translator.makeLoginCookieResponse(user).toString())+"; Path=/;");
                	result = "Success";
                	httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }                
                
            }catch(Exception e) {
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
