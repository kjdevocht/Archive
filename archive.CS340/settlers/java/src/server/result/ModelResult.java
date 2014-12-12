package server.result;

import server.model.game.IGameModel;

/**
 * The result returned for any end point that returns the model.
 */
public class ModelResult implements IResult {

    private IGameModel gameModel;

    public ModelResult(IGameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public IGameModel getResult() {
        return gameModel;
    }

    @Override
    public void setResult(Object object) {
        this.gameModel = (IGameModel)object;
    }
}
