package org.openRealmOfStars.mapTiles;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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
 */

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.race.SpaceRaceFactory;

import static org.junit.Assert.*;

/**
 * Test for Fleet Tile Info
 *
 */
public class FleetTileInfoTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateFleetTileInfo() {
    FleetTileInfo tileInfo = new FleetTileInfo(SpaceRaceFactory.createOne(
        "CENTAURS"), 0, 1, 2);
    assertEquals("Space race was not Centaurs", SpaceRaceFactory.createOne(
        "CENTAURS"), tileInfo.getRace());
    assertEquals("No matching image index", 0,tileInfo.getImageIndex());
    assertEquals("No matching player index", 1,tileInfo.getPlayerIndex());
    assertEquals("No matching fleet index", 2,tileInfo.getFleetIndex());
    assertEquals("No matching planet index", -1, tileInfo.getPlanetIndex());
    tileInfo.setRace(SpaceRaceFactory.createOne("MECHIONS"));
    tileInfo.setImageIndex(1);
    tileInfo.setPlayerIndex(2);
    tileInfo.setFleetIndex(3);
    tileInfo.setPlanetIndex(4);
    assertEquals("Space race was not Mechions", SpaceRaceFactory.createOne(
        "MECHIONS"), tileInfo.getRace());
    assertEquals("No matching image index", 1,tileInfo.getImageIndex());
    assertEquals("No matching player index", 2,tileInfo.getPlayerIndex());
    assertEquals("No matching fleet index", 3,tileInfo.getFleetIndex());
    assertEquals("No matching planet index", 4, tileInfo.getPlanetIndex());
    assertEquals(-1, tileInfo.getConflictIndex());
    tileInfo.setConflict(2);
    assertEquals(2, tileInfo.getConflictIndex());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateFleetTileInfo2() {
    FleetTileInfo tileInfo = new FleetTileInfo(SpaceRaceFactory.createOne(
        "CENTAURS"), 0, 1);
    assertEquals("Space race was not Centaurs", SpaceRaceFactory.createOne(
        "CENTAURS"), tileInfo.getRace());
    assertEquals("No matching image index", 0,tileInfo.getImageIndex());
    assertEquals("No matching player index", -1,tileInfo.getPlayerIndex());
    assertEquals("No matching fleet index", -1,tileInfo.getFleetIndex());
    assertEquals("No matching planet index", 1, tileInfo.getPlanetIndex());
    assertEquals(-1, tileInfo.getConflictIndex());
  }

}
