package server.model.game.ai;

import server.model.game.IGameModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Object representing a list of the AI Player options.
 */
public class AIList {

    private List<IAIPlayer> aiList;

    public AIList() {
        aiList = new ArrayList<>();
    }

    public List<IAIPlayer> getGamesList() {
        return aiList;
    }

}
