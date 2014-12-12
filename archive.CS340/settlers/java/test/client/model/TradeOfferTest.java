package client.model;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.definitions.ResourceType;

public class TradeOfferTest {

	@Test
	public void testGettersAndSetters() {
		IResourceList resources = new ResourceList();
		resources.setQuantity(ResourceType.BRICK, 1);
		ITradeOffer tradeOffer = new TradeOffer(0, 1, resources);
		
		//make sure the constructor put all the correct values
		assertEquals(1, tradeOffer.getReceiverId());
		assertEquals(0, tradeOffer.getSenderId());
		assertEquals(1, tradeOffer.getResources().getQuantity(ResourceType.BRICK));
	}

}
