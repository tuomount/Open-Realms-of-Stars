package org.openRealmOfStars.mapTiles.anomaly;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
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
  }

}
