package client.model;

/**
 * @author mitch10e
 * An object used to keep track of whose turn it is,
 * who has the Largest Army, and who has the Longest Road.
 */
/**
 * 
 * Hold the data to keep track of the turn information
 * 
 * Domain: The player index of who has the current turn. The games status: "playing", "rolling", "firstround", "secondround", "discarding"
 * 
 *
 */
public interface ITurnTracker {
    /**
     * @.obviousGetter
     */
    public int getCurrentTurn();
    /**
     * @.obviousSetter
     */
    public void setCurrentTurn(int turn);
    /**
     * @.obviousGetter
     */
    public String getStatus();
    /**
     * @.obviousSetter
     */
    public void setStatus(String status);

    /**
     * Call this when a player is finished with all of their actions
     * @.pre must have an initialized game
     * @.post advances <code>currentTurn</code> to the next player correctly, with the order the same
     *
     */
    public void endTurn();
}
