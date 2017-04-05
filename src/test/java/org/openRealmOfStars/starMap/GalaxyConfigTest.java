package org.openRealmOfStars.starMap;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
 * Test for GalaxyConfig
 *
 */
public class GalaxyConfigTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalaxyConfig() {
    GalaxyConfig config = new GalaxyConfig();
    assertEquals(75, config.getSizeX());
    assertEquals(75, config.getSizeY());
    assertEquals(4, config.getMaxPlayers());
    assertEquals(12, config.getSolarSystemDistance());
    config.setRace(0, SpaceRace.HUMAN);
    assertEquals(SpaceRace.HUMAN, config.getRace(0));
    assertEquals(true,config.isUniqueName("Test of Human"));
    config.setPlayerName(0, "Test of Human");
    assertEquals("Test of Human",config.getPlayerName(0));
    assertEquals(false,config.isUniqueName("Test of Human"));
    assertEquals(null,config.getPlayerName(9));
    config.setMaxPlayers(3);
    assertEquals(3, config.getMaxPlayers());
    config.setSolarSystemDistance(13, 0);
    assertEquals(13, config.getSolarSystemDistance());
    assertEquals(0, config.getSunDensityIndex());
    config.setSize(50, 0);
    assertEquals(50, config.getSizeX());
    assertEquals(50, config.getSizeY());
    assertEquals(0, config.getGalaxySizeIndex());
    config.setStartingPosition(GalaxyConfig.START_POSITION_RANDOM);
    assertEquals(GalaxyConfig.START_POSITION_RANDOM,
        config.getStartingPosition());
  }


}
