package client.model;

/**
 * Represents a single Trade Offer.
 * 
 * Domain: the sender, the receiver and the resources for the trade with
 * the positive being what is offered and the negative what will be received
 * by the sender.
 * 
 * Constructor: A constructor with a sender id, receiver id and a resource list.
 */
public interface ITradeOffer {


	/**
	 * @.obviousGetter
	 */
	public int getSenderId();

	/**
	 * @.obviousGetter
	 */
	public int getReceiverId();

	/**
	 * @.obviousGetter
	 */
	public IResourceList getResources();
}
