package org.openRealmOfStars.player.leader;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* Leader Name Generator Tests.
*
*/
public class NameGeneratorTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSporkMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.SPORKS, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHumanMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.HUMAN, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHumanFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.HUMAN, Gender.FEMALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

}
