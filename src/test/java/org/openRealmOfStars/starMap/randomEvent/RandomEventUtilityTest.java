package org.openRealmOfStars.starMap.randomEvent;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.tech.TechType;


/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* JUnits for Random Event utility.
*
*/
public class RandomEventUtilityTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMassiveDataLost() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    info.getTechList().setTechResearchPoints(TechType.Combat, 20);
    info.getTechList().setTechResearchPoints(TechType.Defense, 20);
    info.getTechList().setTechResearchPoints(TechType.Hulls, 20);
    info.getTechList().setTechResearchPoints(TechType.Propulsion, 20);
    info.getTechList().setTechResearchPoints(TechType.Improvements, 20);
    info.getTechList().setTechResearchPoints(TechType.Electrics, 20);
    RandomEvent event = new RandomEvent(BadRandomType.MASSIVE_DATA_LOST, info);
    RandomEventUtility.handleMassiveDataLost(event);
    boolean found = false;
    for (TechType type : TechType.values()) {
      if (info.getTechList().getTechResearchPoints(type) == 0) {
        found = true;
      }
    }
    assertEquals(true, found);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTechBreakThrough() {
    PlayerInfo info = new PlayerInfo(SpaceRace.GREYANS);
    RandomEvent event = new RandomEvent(GoodRandomType.TECHNICAL_BREAKTHROUGH,
        info);
    RandomEventUtility.handleTechnicalBreakThrough(event);
    boolean found = false;
    for (TechType type : TechType.values()) {
      if (info.getTechList().getTechResearchPoints(type) > 10) {
        found = true;
      }
    }
    assertEquals(true, found);
  }

}
