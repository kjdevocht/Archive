package client.model;

public class BonusTracker implements IBonusTracker {
    private int longestRoad;
    private int largestArmy;

    public BonusTracker() {
        this.largestArmy = -1;
        this.longestRoad = -1;
    }

    public int getLongestRoad() {
        return longestRoad;
    }

    public void setLongestRoad(int longestRoad) {
        if(longestRoad >= -1 && longestRoad < 4) {
            this.longestRoad = longestRoad;
        }
    }

    public int getLargestArmy() {
        return largestArmy;
    }

    public void setLargestArmy(int largestArmy) {
        if(largestArmy >= -1 && largestArmy < 4) {
            this.largestArmy = largestArmy;
        }
    }
}
