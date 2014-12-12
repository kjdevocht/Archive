package client.map;

import client.data.RobPlayerInfo;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public interface IState{
    boolean canPlaceRoad(EdgeLocation edgeLoc);

    boolean canPlaceSettlement(VertexLocation vertLoc);

    boolean canPlaceCity(VertexLocation vertLoc);

    boolean canPlaceRobber(HexLocation hexLoc);

    void placeRoad(EdgeLocation edgeLoc);

    void placeSettlement(VertexLocation vertLoc);

    void placeCity(VertexLocation vertLoc);


    void placeRobber(HexLocation hexLoc, IMapView view, IRobView robView);

    void startMove(PieceType pieceType, boolean isFree,
                   boolean allowDisconnected, IMapView view, IRobView robView);

    void cancelMove();

    void playSoldierCard(IMapView view);

    void playRoadBuildingCard(IMapView view);

    void robPlayer(RobPlayerInfo victim);

}
