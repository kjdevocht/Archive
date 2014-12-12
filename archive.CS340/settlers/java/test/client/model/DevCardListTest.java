package client.model;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.definitions.DevCardType;

public class DevCardListTest {

	@Test
	public void testGettersAndSetters() {
		IDevCardList devCardList = new DevCardList();
		//Test that they all initialize to 0
		assertEquals(0, devCardList.getDevCardCount(DevCardType.MONOPOLY));
		assertEquals(0, devCardList.getDevCardCount(DevCardType.MONUMENT));
		assertEquals(0, devCardList.getDevCardCount(DevCardType.ROAD_BUILD));
		assertEquals(0, devCardList.getDevCardCount(DevCardType.SOLDIER));
		assertEquals(0, devCardList.getDevCardCount(DevCardType.YEAR_OF_PLENTY));
		
		devCardList.setDevCardCount(DevCardType.MONOPOLY, 1);
		devCardList.setDevCardCount(DevCardType.MONUMENT, 1);
		devCardList.setDevCardCount(DevCardType.ROAD_BUILD, 1);
		devCardList.setDevCardCount(DevCardType.SOLDIER, 1);
		devCardList.setDevCardCount(DevCardType.YEAR_OF_PLENTY, 1);
		
		//test to see if they were all set to 1
		assertEquals(1, devCardList.getDevCardCount(DevCardType.MONOPOLY));
		assertEquals(1, devCardList.getDevCardCount(DevCardType.MONUMENT));
		assertEquals(1, devCardList.getDevCardCount(DevCardType.ROAD_BUILD));
		assertEquals(1, devCardList.getDevCardCount(DevCardType.SOLDIER));
		assertEquals(1, devCardList.getDevCardCount(DevCardType.YEAR_OF_PLENTY));
	}
	@Test
	public void testTotalCardCount() {
		IDevCardList devCardList = new DevCardList();
		
		devCardList.setDevCardCount(DevCardType.MONOPOLY, 1);
		devCardList.setDevCardCount(DevCardType.MONUMENT, 1);
		devCardList.setDevCardCount(DevCardType.ROAD_BUILD, 1);
		devCardList.setDevCardCount(DevCardType.SOLDIER, 1);
		devCardList.setDevCardCount(DevCardType.YEAR_OF_PLENTY, 1);
		
		//test to make sure the get total count sums them all.
		assertEquals(5, devCardList.getTotalCardCount());
	}

}
