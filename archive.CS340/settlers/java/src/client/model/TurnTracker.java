package client.model;

/**
 * @author mitch10e
 * An object used to keep track of whose turn it is,
 * who has the Largest Army, and who has the Longest Road.
 */
public class TurnTracker implements ITurnTracker {
    private int currentTurn;
    private String status;

    /**
     * Basic constructor for <code>TurnTracker</code>
     * @.pre None
     * @.post Gives a Turn tracker that is ready to start a new game
     *
     */
    public TurnTracker() {
        setCurrentTurn(0);
        setStatus("");
    }

    public TurnTracker(int currentTurn, String status) {
        this.currentTurn = currentTurn;
        this.status = status;
    }

    /**
     * @.obviousGetter
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * @.obviousSetter
     */
    public void setCurrentTurn(int currentTurn) {
        if(currentTurn >= 0 && currentTurn < 4) {
            this.currentTurn = currentTurn;
        }
    }

    /**
     * @.obviousGetter
     */
    public String getStatus() {
        return status;
    }

    /**
     * @.obviousSetter
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Call this when a player is finished with all of their actions
     * @.pre must have an initialized game
     * @.post advances <code>currentTurn</code> to the next player correctly, with the order the same
     *
     */
    public void endTurn() {
        this.currentTurn++;
        if(this.currentTurn == 4) {
            this.currentTurn = 0;
        }
        // TODO: Send signal that turn is ended to server.
    }

}
