package org.openRealmOfStars.AI.Mission;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Tuomo Untinen
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
* Mission List test
*
*/
public class MissionListTest {

  @Test
  public void testToStrings() {
    MissionList list = new MissionList();
    Mission mission = Mockito.mock(Mission.class);
    Mockito.when(mission.toString()).thenReturn("Mission 1 toString");
    Mission mission2 = Mockito.mock(Mission.class);
    Mockito.when(mission2.toString()).thenReturn("Mission 2 toString");
    list.add(mission);
    list.add(mission2);
    assertEquals("Mission 0:\n"
        + "Mission 1 toString\n\n"
        + "Mission 1:\n"
        + "Mission 2 toString\n\n", list.toString());
  }

}
