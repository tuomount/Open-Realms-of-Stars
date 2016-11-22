package org.openRealmOfStars.mapTiles;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace;

import static org.junit.Assert.*;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Test for Fleet Tile Info
 *
 */
public class FleetTileInfoTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateFleetTileInfo() {
    FleetTileInfo tileInfo = new FleetTileInfo(SpaceRace.CENTAURS, 0, 1, 2);
    assertEquals("Space race was not Centaurs", SpaceRace.CENTAURS.getIndex(),
        tileInfo.getRace().getIndex());
    assertEquals("No matching image index", 0,tileInfo.getImageIndex());
    assertEquals("No matching player index", 1,tileInfo.getPlayerIndex());
    assertEquals("No matching fleet index", 2,tileInfo.getFleetIndex());
    tileInfo.setRace(SpaceRace.MECHIONS);
    tileInfo.setImageIndex(1);
    tileInfo.setPlayerIndex(2);
    tileInfo.setFleetIndex(3);
    assertEquals("Space race was not Mechions", SpaceRace.MECHIONS.getIndex(),
        tileInfo.getRace().getIndex());
    assertEquals("No matching image index", 1,tileInfo.getImageIndex());
    assertEquals("No matching player index", 2,tileInfo.getPlayerIndex());
    assertEquals("No matching fleet index", 3,tileInfo.getFleetIndex());
  }

}
