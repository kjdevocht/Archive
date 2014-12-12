package client.model.map;

import client.model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import shared.definitions.CatanColor;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by Kevin on 9/30/2014.
 */
public class CatanMapTest {

    private CatanMap map;

    @BeforeClass
    public static void printStatement() {
        System.out.println("[INFO]\tRunning CatanMapTest");
    }

    @Before
    public void setUp() {

        map = new CatanMap();
    }

    @After
    public void tearDown() {
        map = null;
    }

    //These are all tested as part of the ClientModel
    @Test
    public void testCanPlaceRoadAtLocation(){
//        //Test True
//        Player player = new Player("Bob", 3, CatanColor.RED);
//        EdgeValue road = new EdgeValue();
//        road.setOwner(player);
//        HexLocation hexLocation = new HexLocation(1,1);
//        EdgeLocation location = new EdgeLocation(hexLocation, EdgeDirection.North);
//        road.setOwner(player);
//        road.setLocation(location);
//        ArrayList<IEdgeValue> roads = new ArrayList<IEdgeValue>();
//        roads.add(road);
//        map.setRoads(roads);
//        assertTrue(map.canPlaceRoadAtLocation(location, 3, "firstround"));
//
//
//
//        //Test False

    }

    @Test
    public void testCanPlaceSettlementAtLocation(){

    }

    @Test
    public void testCanPlaceCityAtLocation(){

    }

    @Test
    public void testCanPlaceRobberAtLocation(){

    }

    @Test
    public void testGetVictims(){

    }
}
