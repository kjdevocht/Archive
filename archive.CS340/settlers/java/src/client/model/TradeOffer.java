package client.model;

/**
 * Represents a single Trade Offer.
 * 
 * Domain: the sender, the receiver and the resources for the trade with
 * the positive being what is offered and the negative what will be received
 * by the sender.
 */
public class TradeOffer implements ITradeOffer{
	private int senderId;
	private int receiverId;
	private IResourceList resources;
	
	/**
	 * Basic constructor for a TradeOffer
	 * 
	 * @.pre SenderId and ReceiverId are valid (and unique) player IDs
	 * @.pre Resources is a valid ResourceList.
	 * @.post The TradeOffer is created
	 * 
	 * @param senderid the index of the sending player
	 * @param receiverId the index of the receiving player
	 * @param resources a list of resources to be traded
	 */
	public TradeOffer(int senderid, int receiverId, IResourceList resources) {
		this.senderId = senderid;
		this.receiverId = receiverId;
		this.resources = resources;
	}

	/**
	 * @.obviousGetter
	 */
	public int getSenderId() {
		return senderId;
	}

	/**
	 * @.obviousGetter
	 */
	public int getReceiverId() {
		return receiverId;
	}

	/**
	 * @.obviousGetter
	 */
	public IResourceList getResources() {
		return resources;
	}
}
