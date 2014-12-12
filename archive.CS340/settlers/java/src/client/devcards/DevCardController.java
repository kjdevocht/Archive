package client.devcards;

import serverProxy.ClientCommunicator;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.*;
import client.model.ClientModel;
import client.model.IDevCardList;
import client.model.IPlayer;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		if(ClientModel.getUpdatableModel().getDeck().getTotalCardCount() <= 0)
			return;
        if(!ClientModel.getUpdatableModel().isLocalPlayersTurn())
            return;
        if(!ClientModel.getUpdatableModel().getLocalPlayer().canBuyDevCard())
            return;

		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		
		if(ClientModel.getUpdatableModel().canBuyDevCard()) {
			int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
			ClientModel model= null;
			try {
				model = ClientCommunicator.getClientCommunicator().buyDevCard(playerIndex);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(model != null) {
				getBuyCardView().closeModal();
			}
		}		
	}

	@Override
	public void startPlayCard() {
		
		if(!ClientModel.getUpdatableModel().isLocalPlayersTurn())
			return;
		
		IPlayer localPlayer = ClientModel.getUpdatableModel().getLocalPlayer();
		IDevCardList oldDevCards = ClientModel.getUpdatableModel().getLocalPlayer().getOldDevCards();
        IDevCardList newDevCards = ClientModel.getUpdatableModel().getLocalPlayer().getNewDevCards();




		getPlayCardView().setCardAmount(DevCardType.MONOPOLY, newDevCards.getDevCardCount(DevCardType.MONOPOLY)+oldDevCards.getDevCardCount(DevCardType.MONOPOLY));
		getPlayCardView().setCardAmount(DevCardType.MONUMENT, newDevCards.getDevCardCount(DevCardType.MONUMENT)+oldDevCards.getDevCardCount(DevCardType.MONUMENT));
		getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, newDevCards.getDevCardCount(DevCardType.ROAD_BUILD)+oldDevCards.getDevCardCount(DevCardType.ROAD_BUILD));
		getPlayCardView().setCardAmount(DevCardType.SOLDIER, newDevCards.getDevCardCount(DevCardType.SOLDIER)+oldDevCards.getDevCardCount(DevCardType.SOLDIER));
		getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, newDevCards.getDevCardCount(DevCardType.YEAR_OF_PLENTY)+oldDevCards.getDevCardCount(DevCardType.YEAR_OF_PLENTY));

        if(localPlayer.isPlayedDevCard()) {
            for(DevCardType type: DevCardType.values()) {
                getPlayCardView().setCardEnabled(type, false);
            }
            return;
        }

		if(localPlayer.isPlayedDevCard() || localPlayer.getOldDevCards().getDevCardCount(DevCardType.MONOPOLY) == 0) {
			getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
		}
		else {
			getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
		}
		
		if(localPlayer.isPlayedDevCard() || newDevCards.getDevCardCount(DevCardType.MONUMENT)+oldDevCards.getDevCardCount(DevCardType.MONUMENT) == 0) {
			getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
		}
		else {
			getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
		}
		
		if(localPlayer.isPlayedDevCard() || localPlayer.getOldDevCards().getDevCardCount(DevCardType.ROAD_BUILD) == 0) {
			getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
		}
		else {
			getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
		}
		
		if(localPlayer.isPlayedDevCard() || localPlayer.getOldDevCards().getDevCardCount(DevCardType.SOLDIER) == 0) {
			getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
		}
		else {
			getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
		}
		
		if(localPlayer.isPlayedDevCard() || localPlayer.getOldDevCards().getDevCardCount(DevCardType.YEAR_OF_PLENTY) == 0) {
			getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
		}
		else {
			getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
		}
		
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		if(ClientModel.getUpdatableModel().canPlayMonopoly()) {
			int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
			ClientModel model= null;
			try {
				model = ClientCommunicator.getClientCommunicator().monopoly(playerIndex, resource);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(model != null) {
				getPlayCardView().closeModal();
			}
		}
	}

	@Override
	public void playMonumentCard() {
		if(ClientModel.getUpdatableModel().canPlayMonument()) {
			int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
			ClientModel model= null;
			try {
				model = ClientCommunicator.getClientCommunicator().monument(playerIndex);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(model != null) {
				getPlayCardView().closeModal();
			}
		}
	}

	@Override
	public void playRoadBuildCard() {
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		if(ClientModel.getUpdatableModel().canPlayYearOfPlenty(resource1, resource2)) {
			int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
			ClientModel model= null;
			try {
				model = ClientCommunicator.getClientCommunicator().yearOfPlenty(playerIndex, resource1, resource2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(model != null) {
				getPlayCardView().closeModal();
			}
		}
	}

}

