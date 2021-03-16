package org.openRealmOfStars.player.leader;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020,2021 Tuomo Untinen
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
  public void testMechion() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.MECHIONS, Gender.NONE);
      assertNotNull(name);
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testReborgian() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.REBORGIANS, Gender.NONE);
      assertNotNull(name);
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCentaurMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.CENTAURS, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCentaurFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.CENTAURS, Gender.FEMALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGreyanMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.GREYANS, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGreyanFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.GREYANS, Gender.FEMALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHomarianMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.HOMARIANS, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHomarianFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.HOMARIANS, Gender.FEMALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaurianMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.SCAURIANS, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScaurianFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.SCAURIANS, Gender.FEMALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMothoidMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.MOTHOIDS, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMothoidFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.MOTHOIDS, Gender.FEMALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testChiraloidMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.CHIRALOIDS, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testChiraloidFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.CHIRALOIDS, Gender.FEMALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTeuthidaeMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.TEUTHIDAES, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
      assertEquals(false, name.contains("''"));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTeuthidaeFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.TEUTHIDAES, Gender.FEMALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
      assertEquals(false, name.contains("''"));
    }
  }

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
  public void testSporkFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.SPORKS, Gender.FEMALE);
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

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLithorianMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.LITHORIANS, Gender.MALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLithorianFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(SpaceRace.LITHORIANS, Gender.FEMALE);
      assertNotNull(name);
      assertEquals(true, name.contains(" "));
    }
  }

}
