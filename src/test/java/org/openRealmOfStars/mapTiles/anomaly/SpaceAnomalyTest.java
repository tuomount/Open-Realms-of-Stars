package org.openRealmOfStars.mapTiles.anomaly;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, see http://www.gnu.org/licenses/
*
*
* Space anomaly 
*
*/
public class SpaceAnomalyTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test() {
    SpaceAnomaly anomaly = new SpaceAnomaly(AnomalyType.CREDIT, 5);
    assertEquals(AnomalyType.CREDIT, anomaly.getType());
    assertEquals(5, anomaly.getValue());
    assertEquals(null, anomaly.getImage());
    assertEquals(null, anomaly.getText());
    anomaly.setText("Test");
    assertEquals("Test", anomaly.getText());
    BufferedImage image = Mockito.mock(BufferedImage.class);
    anomaly.setImage(image);
    assertEquals(image, anomaly.getImage());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreditAnomaly() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(6);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.SPACE_ANOMALY_CREDITS);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(5, 6)).thenReturn(tile);
    SpaceAnomaly anomaly = SpaceAnomaly.createAnomalyEvent(map, info, fleet);
    assertEquals(AnomalyType.CREDIT, anomaly.getType());
    assertNotNull(anomaly.getImage());
    assertNotEquals(0, anomaly.getValue());
    assertNotNull(anomaly.getText());
    assertEquals(null, anomaly.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMapAnomaly() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(6);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.SPACE_ANOMALY_MAP);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(5, 6)).thenReturn(tile);
    SpaceAnomaly anomaly = SpaceAnomaly.createAnomalyEvent(map, info, fleet);
    assertEquals(AnomalyType.MAP, anomaly.getType());
    assertEquals(null, anomaly.getImage());
    assertEquals(7, anomaly.getValue());
    assertNotNull(anomaly.getText());
    assertEquals(null, anomaly.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechAnomalyNoTech() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList techList = Mockito.mock(TechList.class);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(6);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.SPACE_ANOMALY_TECH);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(5, 6)).thenReturn(tile);
    SpaceAnomaly anomaly = SpaceAnomaly.createAnomalyEvent(map, info, fleet);
    assertEquals(null, anomaly);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTechAnomaly() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    TechList techList = Mockito.mock(TechList.class);
    Tech tech = Mockito.mock(Tech.class);
    Mockito.when(techList.addNewRandomTech(info)).thenReturn(tech);
    Mockito.when(info.getTechList()).thenReturn(techList);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(6);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.SPACE_ANOMALY_TECH);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(5, 6)).thenReturn(tile);
    SpaceAnomaly anomaly = SpaceAnomaly.createAnomalyEvent(map, info, fleet);
    assertEquals(AnomalyType.TECH, anomaly.getType());
    assertEquals(null, anomaly.getImage());
    assertEquals(0, anomaly.getValue());
    assertNotNull(anomaly.getText());
    assertEquals(null, anomaly.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDsaAnomaly() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(6);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.SPACE_ANOMALY_DSA);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(5, 6)).thenReturn(tile);
    SpaceAnomaly anomaly = SpaceAnomaly.createAnomalyEvent(map, info, fleet);
    assertEquals(AnomalyType.DEEP_SPACE_ANCHOR, anomaly.getType());
    assertEquals(null, anomaly.getImage());
    assertEquals(0, anomaly.getValue());
    assertNotNull(anomaly.getText());
    assertEquals(null, anomaly.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWormholeAnomaly() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(6);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.SPACE_ANOMALY);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(5, 6)).thenReturn(tile);
    SpaceAnomaly anomaly = SpaceAnomaly.createAnomalyEvent(map, info, fleet);
    assertEquals(AnomalyType.WORMHOLE, anomaly.getType());
    assertEquals(GuiStatics.IMAGE_BLACKHOLE, anomaly.getImage());
    assertEquals(0, anomaly.getValue());
    assertNotNull(anomaly.getText());
    assertEquals(null, anomaly.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPirateLairAnomaly() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    PlayerInfo board = Mockito.mock(PlayerInfo.class);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(board.getFleets()).thenReturn(fleetList);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(6);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    Ship[] ships = new Ship[0];
    Mockito.when(fleet.getShips()).thenReturn(ships);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.SPACE_ANOMALY_LAIR);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(5, 6)).thenReturn(tile);
    Mockito.when(map.addSpaceAnomalyEnemy(Mockito.anyInt(), Mockito.anyInt(),
        (PlayerInfo) Mockito.any(), Mockito.anyInt())).thenReturn(fleet);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getBoardPlayer()).thenReturn(board);
    SpaceAnomaly anomaly = SpaceAnomaly.createAnomalyEvent(map, info, fleet);
    assertEquals(AnomalyType.LAIR, anomaly.getType());
    assertEquals(null, anomaly.getImage());
    assertEquals(0, anomaly.getValue());
    assertNotNull(anomaly.getText());
    assertNotNull(anomaly.getCombat());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPirateAnomaly() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    PlayerInfo board = Mockito.mock(PlayerInfo.class);
    FleetList fleetList = Mockito.mock(FleetList.class);
    Mockito.when(board.getFleets()).thenReturn(fleetList);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getX()).thenReturn(5);
    Mockito.when(fleet.getY()).thenReturn(6);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord);
    Ship[] ships = new Ship[0];
    Mockito.when(fleet.getShips()).thenReturn(ships);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.SPACE_ANOMALY_PIRATE);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getTile(5, 6)).thenReturn(tile);
    Mockito.when(map.addSpaceAnomalyEnemy(Mockito.anyInt(), Mockito.anyInt(),
        (PlayerInfo) Mockito.any(), Mockito.anyInt())).thenReturn(fleet);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(playerList.getBoardPlayer()).thenReturn(board);
    SpaceAnomaly anomaly = SpaceAnomaly.createAnomalyEvent(map, info, fleet);
    assertEquals(AnomalyType.PIRATE, anomaly.getType());
    assertEquals(GuiStatics.IMAGE_PIRATE_PILOT, anomaly.getImage());
    assertEquals(0, anomaly.getValue());
    assertNotNull(anomaly.getText());
    assertNotNull(anomaly.getCombat());
  }

}
