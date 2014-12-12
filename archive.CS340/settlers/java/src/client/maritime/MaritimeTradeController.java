package client.maritime;

import client.model.ClientModel;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import serverProxy.ClientCommunicator;
import shared.definitions.*;
import client.base.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	private Map<ResourceType, Integer> tradeRatio;
    private ResourceType giveResource = null;
    private ResourceType getResource = null;
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);

    }
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {
        getTradeOverlay().reset();
        if(!ClientModel.getUpdatableModel().isLocalPlayersTurn() || !ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase().equals("playing")){
            getTradeOverlay().setStateMessage("Not Your Turn");
            getTradeOverlay().setTradeEnabled(false);

            getTradeOverlay().showGiveOptions(new ResourceType[0]);

            getTradeOverlay().showModal();
            return;
        }
        getTradeOverlay().setStateMessage("Trade");
//        System.out.println("[INFO]\tStart Trade");
        tradeRatio = ClientModel.getUpdatableModel().getTradeRatios();
        List<ResourceType> types = new ArrayList<>();
        for(ResourceType type: ResourceType.values()) {
            if(ClientModel.getUpdatableModel().getLocalPlayer().getResources().getQuantity(type) >= tradeRatio.get(type)) {
                types.add(type);
            }
        }
        getTradeOverlay().showGiveOptions(types.toArray(new ResourceType[types.size()]));

        getTradeOverlay().setTradeEnabled(false);

        getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {
        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
        try {
            ClientCommunicator.getClientCommunicator().maritimeTrade(playerIndex, tradeRatio.get(giveResource), giveResource, getResource);
        } catch (Exception e) {
        }
//        System.out.println("[INFO]\tMake Trade");
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {
//        System.out.println("[INFO]\tCancel Trade");
		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
//        System.out.println("[INFO]\tSet Get Resource");
        getTradeOverlay().selectGetOption(resource, 1);
        getResource = resource;
        getTradeOverlay().setTradeEnabled(true);
	}

	@Override
	public void setGiveResource(ResourceType resource) {
        giveResource = resource;
//        System.out.println("[INFO]\tSet Give Resource");
        List<ResourceType> types = new ArrayList<>();
        for(ResourceType type: ResourceType.values()) {
            if(type != resource)
                types.add(type);
        }

        getTradeOverlay().showGetOptions(types.toArray(new ResourceType[types.size()]));
        getTradeOverlay().selectGiveOption(resource, tradeRatio.get(resource));

	}

	@Override
	public void unsetGetValue() {
//        System.out.println("[INFO]\tUnset Get Value");
        setGiveResource(giveResource);
        getResource = null;
        getTradeOverlay().setTradeEnabled(false);

	}

	@Override
	public void unsetGiveValue() {

        getTradeOverlay().reset();

        tradeRatio = ClientModel.getUpdatableModel().getTradeRatios();
        List<ResourceType> types = new ArrayList<>();
        for(ResourceType type: ResourceType.values()) {
            if(ClientModel.getUpdatableModel().getLocalPlayer().getResources().getQuantity(type) >= tradeRatio.get(type)) {
                types.add(type);
            }
        }
        getTradeOverlay().showGiveOptions(types.toArray(new ResourceType[types.size()]));
        getTradeOverlay().showGetOptions(new ResourceType[0]);
        getTradeOverlay().setTradeEnabled(false);
        giveResource = null;
        getResource = null;
	}

}

