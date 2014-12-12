package client.points;

import client.base.*;
import client.join.JoinGameController;
import client.join.JoinGameView;
import client.join.NewGameView;
import client.join.PlayerWaitingController;
import client.join.PlayerWaitingView;
import client.join.SelectColorView;
import client.login.LoginController;
import client.login.LoginView;
import client.main.Catan;
import client.misc.MessageView;
import client.model.ClientModel;
import client.model.IPlayer;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;

import java.awt.*;

import javax.swing.SwingUtilities;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, final IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		
		initFromModel();
        EventObservable.getSingleton().subscribeToEvent(Event.UpdatePlayerVictoryPoints, new IObserver<Integer>() {
            @Override
            public void update(Integer metadata) {
                final int index = metadata;
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
                        if(playerIndex == index) {
                            getPointsView().setPoints(ClientModel.getUpdatableModel().getLocalPlayer().getVictoryPoints());
                        }
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateWinner, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ClientModel model = ClientModel.getUpdatableModel();
                        if(model.getWinner() != -1) {
                            IPlayer winner = null;
//                            IPlayer winner = model.getPlayers().get(model.getWinner());
                            for (IPlayer player : model.getPlayers()) {
                                if(player.getPlayerId() == model.getWinner()) {
                                    winner = player;
                                }
                            }

                            boolean isLocalPlayer = (model.getLocalPlayer().getPlayerIndex() == winner.getPlayerIndex());
                            finishedView.setWinner(winner.getName(), isLocalPlayer);
                            finishedView.showModal();
                        }
                    }
                });
            }
        });
	}

	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		//<temp>		
//		getPointsView().setPoints(5);
		//</temp>
	}
	
}

