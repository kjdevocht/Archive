package client.join;

import client.base.*;
import client.data.PlayerInfo;
import client.model.ClientModel;
import client.model.IPlayer;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import serverProxy.ClientCommunicator;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {
    private boolean hasAllPlayers = false;
	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);


        EventObservable.getSingleton().subscribeToEvent(Event.UpdateAddBlankPlayer, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {

                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        if(ClientModel.getUpdatableModel().getPlayers().size()  == 4) {
                            if(getView().isModalShowing()) {
                                getView().closeModal();
                            }
                        }
                        else {
                            List<PlayerInfo> playerInfos = new ArrayList<>();
                            for(IPlayer player: ClientModel.getUpdatableModel().getPlayers()) {
                                PlayerInfo playerInfo = new PlayerInfo();
                                playerInfo.setColor(player.getColor());
                                playerInfo.setId(player.getPlayerId());
                                playerInfo.setPlayerIndex(player.getPlayerIndex());
                                playerInfo.setName(player.getName());
                                playerInfos.add(playerInfo);
                            }
                            getView().setPlayers(playerInfos.toArray(new PlayerInfo[playerInfos.size()]));

                            getView().setTitleLabel("Waiting for Players: Need " + (4-playerInfos.size())+" more");
                        }
                    }

                });
            }
        });
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
        try {
            List<String> ais = ClientCommunicator.getClientCommunicator().listAi();
            getView().setAIChoices(ais.toArray(new String[ais.size()]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        getView().showModal();
	}

	@Override
	public void addAI() {
        boolean success = false;
        try {
            success = ClientCommunicator.getClientCommunicator().addAi(getView().getSelectedAI());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        // TEMPORARY
//
//        if(success)
//		    getView().closeModal();
	}

}

