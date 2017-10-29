package org.openRealmOfStars.utilities.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Test for MissionRepository
*
*/
public class MissionRepositoryTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTypes() {
    for (int i = 0; i < MissionType.values().length; i++) {
      MissionType type = MissionType.values()[i];
      MissionType type2 = MissionRepository.getTypeIndex(i);
      assertEquals(type, type2);
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPhases() {
    for (int i = 0; i < MissionPhase.values().length; i++) {
      MissionPhase phase = MissionPhase.values()[i];
      MissionPhase phase2 = MissionRepository.getPhaseIndex(i);
      assertEquals(phase, phase2);
    }
  }

}
