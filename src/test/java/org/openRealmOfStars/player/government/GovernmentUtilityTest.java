package org.openRealmOfStars.player.government;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
* Government Utility Test
*
*/
public class GovernmentUtilityTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHumanGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.HUMAN);
    assertEquals(8, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.DEMOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.EMPIRE, governments[4]);
    assertEquals(GovernmentType.HEGEMONY, governments[5]);
    assertEquals(GovernmentType.HIERARCHY, governments[6]);
    assertEquals(GovernmentType.KINGDOM, governments[7]);
  }

}
