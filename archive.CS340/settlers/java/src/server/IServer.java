package server;

/**
 * The IServer holds all of the handlers and is in charge of receiving requests,
 * routing them to the proper handlers and then returning the appropriate response
 */
public interface IServer {

    /**
     * create the contexts and configure the server. Start Running once configured.
     * @param port the port the server runs on. Default: 8081
     */
    public void run(int port);

}
