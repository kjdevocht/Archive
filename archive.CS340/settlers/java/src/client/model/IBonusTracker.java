package client.model;
/**
 * A tracker for the bonus items of longest road and largest army.
 * @author mitch10e
 */
/**
 * public BonusTracker()
 * @.pre None
 * @.post creates a <code>BonusTracker</code>
 * 
 * Domain: the index of the player with the longest road and largest army.
 */
public interface IBonusTracker {
    /**
     * @.obviousGetter
     */
    public int getLongestRoad();
    /**
     * @.obviousSetter
     */
    public void setLongestRoad(int longestRoad);
    /**
     * @.obviousGetter
     */
    public int getLargestArmy();
    /**
     * @.obviousSetter
     */
    public void setLargestArmy(int largestArmy);

}
