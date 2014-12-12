package client.model;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.definitions.ResourceType;

public class ResourceListTest {

	@Test
	public void testGettersAndSetters() {
		IResourceList resourseList = new ResourceList();
		
		//They all start as 0
		assertEquals(0, resourseList.getQuantity(ResourceType.BRICK));
		assertEquals(0, resourseList.getQuantity(ResourceType.ORE));
		assertEquals(0, resourseList.getQuantity(ResourceType.SHEEP));
		assertEquals(0, resourseList.getQuantity(ResourceType.WHEAT));
		assertEquals(0, resourseList.getQuantity(ResourceType.WOOD));
		
		resourseList.setQuantity(ResourceType.BRICK, 1);
		resourseList.setQuantity(ResourceType.ORE, 1);
		resourseList.setQuantity(ResourceType.SHEEP, 1);
		resourseList.setQuantity(ResourceType.WHEAT, 1);
		resourseList.setQuantity(ResourceType.WOOD, 1);
		
		//testing correct setting
		assertEquals(1, resourseList.getQuantity(ResourceType.BRICK));
		assertEquals(1, resourseList.getQuantity(ResourceType.ORE));
		assertEquals(1, resourseList.getQuantity(ResourceType.SHEEP));
		assertEquals(1, resourseList.getQuantity(ResourceType.WHEAT));
		assertEquals(1, resourseList.getQuantity(ResourceType.WOOD));
	}
	@Test
	public void testTotalResourceCount() {
		IResourceList resourseList = new ResourceList();

		resourseList.setQuantity(ResourceType.BRICK, 1);
		resourseList.setQuantity(ResourceType.ORE, 1);
		resourseList.setQuantity(ResourceType.SHEEP, 1);
		resourseList.setQuantity(ResourceType.WHEAT, 1);
		resourseList.setQuantity(ResourceType.WOOD, 1);
		
		//the sum of the resources is correct
		assertEquals(5, resourseList.getTotalResourceCount());
	}
	@Test
	public void testIsGreaterOrEqualThan() {
		IResourceList resourseList = new ResourceList();

		resourseList.setQuantity(ResourceType.BRICK, 1);
		resourseList.setQuantity(ResourceType.ORE, 1);
		resourseList.setQuantity(ResourceType.SHEEP, 1);
		resourseList.setQuantity(ResourceType.WHEAT, 1);
		resourseList.setQuantity(ResourceType.WOOD, 1);
		
		IResourceList resourseList2 = new ResourceList();

		resourseList2.setQuantity(ResourceType.BRICK, 2);
		resourseList2.setQuantity(ResourceType.ORE, 2);
		resourseList2.setQuantity(ResourceType.SHEEP, 2);
		resourseList2.setQuantity(ResourceType.WHEAT, 2);
		resourseList2.setQuantity(ResourceType.WOOD, 2);
		
		//that the list can be properly compared that one list is greater or equal for all resources
		assertEquals(false, resourseList.isGreaterOrEqualThanInput(resourseList2));
		assertEquals(true, resourseList2.isGreaterOrEqualThanInput(resourseList));
		
		resourseList = new ResourceList();

		resourseList.setQuantity(ResourceType.BRICK, 0);
		resourseList.setQuantity(ResourceType.ORE, 0);
		resourseList.setQuantity(ResourceType.SHEEP, 1);
		resourseList.setQuantity(ResourceType.WHEAT, 1);
		resourseList.setQuantity(ResourceType.WOOD, 1);
		
		resourseList2 = new ResourceList();

		resourseList2.setQuantity(ResourceType.BRICK, 1);
		resourseList2.setQuantity(ResourceType.ORE, 1);
		resourseList2.setQuantity(ResourceType.SHEEP, 0);
		resourseList2.setQuantity(ResourceType.WHEAT, 0);
		resourseList2.setQuantity(ResourceType.WOOD, 0);
		
		//Neither list will be greater or equal for all the resources
		assertEquals(false, resourseList.isGreaterOrEqualThanInput(resourseList2));
		assertEquals(false, resourseList2.isGreaterOrEqualThanInput(resourseList));
	}

}
