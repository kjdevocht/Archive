package client.domestic;

import client.data.PlayerInfo;
import client.model.*;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import serverProxy.ClientCommunicator;
import shared.definitions.*;
import client.base.*;
import client.misc.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

    private IDomesticTradeOverlay tradeOverlay;
    private IWaitView waitOverlay;
    private IAcceptTradeOverlay acceptOverlay;
    private ResourceList tradeOfferToBe;
    private int senderIndex;
    private int receiverIndex;

    /**
     * DomesticTradeController constructor
     *
     * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
     * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
     * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
     * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
     */
    public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
                                   IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

        super(tradeView);
        tradeOfferToBe = new ResourceList();
        senderIndex =  -1;
        receiverIndex = -1;
        setTradeOverlay(tradeOverlay);
        setWaitOverlay(waitOverlay);
        setAcceptOverlay(acceptOverlay);


        EventObservable.getSingleton().subscribeToEvent(Event.UpdateTradeOffer, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                handleTradeOffer();
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateCurrentTurn, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                if(!ClientModel.getUpdatableModel().isLocalPlayersTurn()) {
                    if(getTradeOverlay().isModalShowing()) {
                        getTradeOverlay().closeModal();
                    }
                }
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateStatus, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                if(!ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase().equals("playing")) {
                    if(getTradeOverlay().isModalShowing()) {
                        getTradeOverlay().closeModal();
                    }
                }
            }
        });
    }
    private void handleTradeOffer() {
        getAcceptOverlay().reset();
        getTradeOverlay().reset();
        
        ITradeOffer tradeOffer = ClientModel.getUpdatableModel().getTradeOffer();
        if(tradeOffer == null) {
            if (getAcceptOverlay().isModalShowing()){
                getAcceptOverlay().closeModal();

            }
            if (getWaitOverlay().isModalShowing()){

                getWaitOverlay().closeModal();
            }
            return;
        }
        IPlayer localplayer = ClientModel.getUpdatableModel().getLocalPlayer();

        if(localplayer.getPlayerIndex() == tradeOffer.getSenderId()) {
            if(getTradeOverlay().isModalShowing()) {
                getTradeOverlay().closeModal();
            }
            getWaitOverlay().showModal();
        }
        else if(localplayer.getPlayerIndex() == tradeOffer.getReceiverId()) {
            setUpAccept();
            getAcceptOverlay().showModal();
        }

    }
    private void setUpAccept() {


        boolean canAccept = ClientModel.getUpdatableModel().canAcceptTrade();
        IResourceList tradeList = ClientModel.getUpdatableModel().getTradeOffer().getResources();
        getAcceptOverlay().setAcceptEnabled(canAccept);


        addResourceToAccept(ResourceType.BRICK, tradeList.getQuantity(ResourceType.BRICK));
        addResourceToAccept(ResourceType.WHEAT, tradeList.getQuantity(ResourceType.WHEAT));
        addResourceToAccept(ResourceType.WOOD, tradeList.getQuantity(ResourceType.WOOD));
        addResourceToAccept(ResourceType.ORE, tradeList.getQuantity(ResourceType.ORE));
        addResourceToAccept(ResourceType.SHEEP, tradeList.getQuantity(ResourceType.SHEEP));

        ITradeOffer tradeOffer = ClientModel.getUpdatableModel().getTradeOffer();
        IPlayer offeringPlayer = ClientModel.getUpdatableModel().getPlayers().get(tradeOffer.getSenderId());
        getAcceptOverlay().setPlayerName(offeringPlayer.getName());
    }
    private void addResourceToAccept(ResourceType type, int qty) {
        if(qty == 0)
            return;
        else if(qty < 0) {
            getAcceptOverlay().addGetResource(type, -1 * qty);
        }
        else {
            getAcceptOverlay().addGiveResource(type, qty);
        }
    }
    public IDomesticTradeView getTradeView() {

        return (IDomesticTradeView)super.getView();
    }

    public IDomesticTradeOverlay getTradeOverlay() {
        return tradeOverlay;
    }

    public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
        this.tradeOverlay = tradeOverlay;
    }

    public IWaitView getWaitOverlay() {
        return waitOverlay;
    }

    public void setWaitOverlay(IWaitView waitView) {
        this.waitOverlay = waitView;
    }

    public IAcceptTradeOverlay getAcceptOverlay() {
        return acceptOverlay;
    }

    public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
        this.acceptOverlay = acceptOverlay;
    }

    @Override
    public void startTrade() {
//    	getTradeOverlay().setPlayers(new PlayerInfo[0]);
    	getTradeOverlay().reset();
        if(!ClientModel.getUpdatableModel().isLocalPlayersTurn()) {

            getTradeOverlay().setResourceSelectionEnabled(false);
            getTradeOverlay().setStateMessage("Not Your Turn");
            getTradeOverlay().setPlayerSelectionEnabled(false);
            getTradeOverlay().showModal();
            return;
        }


        if(!ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase().equals("playing")) {
            getTradeOverlay().setTradeEnabled(false);
            getTradeOverlay().setResourceSelectionEnabled(false);
            getTradeOverlay().setStateMessage("Can't Make Trade During this part of the game");
            getTradeOverlay().setPlayerSelectionEnabled(false);
            getTradeOverlay().showModal();
            return;
        }
        getTradeOverlay().setPlayerSelectionEnabled(true);
        getTradeOverlay().setTradeEnabled(true);
        getTradeOverlay().setStateMessage("Default");

        tradeOfferToBe = new ResourceList();
        senderIndex =  ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
        receiverIndex = -1;

        setEnabled();

        List<PlayerInfo> playerInfoList = new ArrayList<>();

        for(IPlayer player: ClientModel.getUpdatableModel().getPlayers()) {
            if(ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex() == player.getPlayerIndex())
                continue;
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setPlayerIndex(player.getPlayerIndex());
            playerInfo.setId(player.getPlayerId());
            playerInfo.setName(player.getName());
            playerInfo.setColor(player.getColor());
            playerInfoList.add(playerInfo);
        }
        
        if(((DomesticTradeOverlay) getTradeOverlay()).playerButtons.size() == 0)        
        	getTradeOverlay().setPlayers(playerInfoList.toArray(new PlayerInfo[playerInfoList.size()]));

        setEnabled();
        getTradeOverlay().showModal();
    }

    @Override
    public void decreaseResourceAmount(ResourceType resource) {
        int tradeCount = tradeOfferToBe.getQuantity(resource);
        if(tradeCount < 0) {
            int newValue = (tradeOfferToBe.getQuantity(resource) + 1);
            tradeOfferToBe.setQuantity(resource, newValue);
            getTradeOverlay().setResourceAmount(resource, ""+Math.abs(newValue));
        }
        else {
            int newValue = (tradeOfferToBe.getQuantity(resource) - 1);
            tradeOfferToBe.setQuantity(resource, newValue);
            getTradeOverlay().setResourceAmount(resource, ""+Math.abs(newValue));
        }
        setEnabled();
    }

    @Override
    public void increaseResourceAmount(ResourceType resource) {
        int tradeCount = tradeOfferToBe.getQuantity(resource);
        if(tradeCount < 0) {
            int newValue = (tradeOfferToBe.getQuantity(resource) - 1);
            tradeOfferToBe.setQuantity(resource, newValue);
            getTradeOverlay().setResourceAmount(resource, ""+Math.abs(newValue));
        }
        else {
            int newValue = (tradeOfferToBe.getQuantity(resource) + 1);
            tradeOfferToBe.setQuantity(resource, newValue);
            getTradeOverlay().setResourceAmount(resource, ""+Math.abs(newValue));
        }
        setEnabled();
    }

    public void setEnabled() {
        setEnabled(ResourceType.BRICK);
        setEnabled(ResourceType.WOOD);
        setEnabled(ResourceType.WHEAT);
        setEnabled(ResourceType.ORE);
        setEnabled(ResourceType.SHEEP);


    }
    public void setEnabled(ResourceType resourse) {
        IResourceList playerResources = ClientModel.getUpdatableModel().getLocalPlayer().getResources();


        if(receiverIndex != -1) {
            boolean canMakeTrade = ClientModel.getUpdatableModel().canOfferTrade(new TradeOffer(senderIndex, receiverIndex, tradeOfferToBe));
            getTradeOverlay().setTradeEnabled(canMakeTrade);
            getTradeOverlay().setResourceSelectionEnabled(true);
            getTradeOverlay().setStateMessage("Trade");
        }
        else {
            getTradeOverlay().setTradeEnabled(false);
            getTradeOverlay().setResourceSelectionEnabled(false);
            if(((DomesticTradeOverlay) getTradeOverlay()).playerButtons.size() != 0)  
            	getTradeOverlay().setStateMessage("Select Player");
        }

        int tradeOfferCount = tradeOfferToBe.getQuantity(resourse);

        if(tradeOfferCount == 0) {
            getTradeOverlay().setResourceAmountChangeEnabled(resourse, false, false);
            return;
        }

        //what you will receive
        if(tradeOfferCount > 0) {
            if(tradeOfferCount <= 1)
                getTradeOverlay().setResourceAmountChangeEnabled(resourse, true, false);
            else
                getTradeOverlay().setResourceAmountChangeEnabled(resourse, true, true);
            return;
        }
        else
            getTradeOverlay().setResourceAmountChangeEnabled(resourse, false, false);

        int playerCount = playerResources.getQuantity(resourse);


        if(playerCount == 0) {
            getTradeOverlay().setResourceAmountChangeEnabled(resourse, false, false);
        }
        else {
            if(tradeOfferCount < 0) {
                boolean canIncrease = true;
                boolean canDecrease = true;

                if(tradeOfferCount * -1 >= playerCount)
                    canIncrease = false;
                if(tradeOfferCount * -1 <= 1)
                    canDecrease = false;

                getTradeOverlay().setResourceAmountChangeEnabled(resourse, canIncrease, canDecrease);
            }
        }
    }

    @Override
    public void sendTradeOffer() {
        try {
            ClientCommunicator.getClientCommunicator().offerTrade(senderIndex, tradeOfferToBe, receiverIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getTradeOverlay().closeModal();
    }

    @Override
    public void setPlayerToTradeWith(int playerIndex) {
        receiverIndex = playerIndex;
        setEnabled();
    }

    @Override
    public void setResourceToReceive(ResourceType resource) {

        tradeOfferToBe.setQuantity(resource, 1);
        getTradeOverlay().setResourceAmount(resource, ""+1);
        setEnabled();
    }

    @Override
    public void setResourceToSend(ResourceType resource) {
        IResourceList playerResources = ClientModel.getUpdatableModel().getLocalPlayer().getResources();

        if(playerResources.getQuantity(resource) == 0) {
            tradeOfferToBe.setQuantity(resource, 0);
            getTradeOverlay().setResourceAmount(resource, ""+0);
            setEnabled();
            return;
        }

        tradeOfferToBe.setQuantity(resource, -1);
        getTradeOverlay().setResourceAmount(resource, ""+1);
        setEnabled();
    }

    @Override
    public void unsetResource(ResourceType resource) {
        tradeOfferToBe.setQuantity(resource, 0);
        getTradeOverlay().setResourceAmount(resource, ""+0);
        setEnabled();
    }

    @Override
    public void cancelTrade() {
        getTradeOverlay().closeModal();
    }

    @Override
    public void acceptTrade(boolean willAccept) {
        try {
            ClientCommunicator.getClientCommunicator().acceptTrade(ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex(), willAccept);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getAcceptOverlay().closeModal();
    }

}

