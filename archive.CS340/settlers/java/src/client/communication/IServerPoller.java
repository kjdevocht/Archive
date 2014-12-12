package client.communication;

public interface IServerPoller {

    /**
     * Begins polling the server for the current model. Updates the client model
     * if necessary.
     *
     * @.pre The ServerPoller must have been given a valid ClientModel when it
     *       was created
     * @.pre The given ClientModel must still be valid and not null.
     * @.post The ServerPoller begins continuously polling the server for the
     *        model data. Will update the ClientModel as necessary.
     */
    public void start();

    /**
     * Stop polling the server
     *
     * @.pre None. This method will always work.
     * @.post If the ServerPoller was polling the server, it will stop.
     */
    public void stop();

}
