package org.openRealmOfStars.player.leader;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020-2024 Tuomo Untinen
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

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
* Leader Name Generator Tests.
*
*/
public class NameGeneratorTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRobot() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.ROBOT,
          Gender.NONE);
      assertNotNull(name);
      assertFalse(name.startsWith(" "));
    }
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.ROBOT,
          Gender.MALE);
      assertNotNull(name);
      assertFalse(name.startsWith(" "));
    }
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.ROBOT,
          Gender.FEMALE);
      assertNotNull(name);
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFemaleRobot() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.FEMALE_ROBOT,
          Gender.FEMALE);
      assertNotNull(name);
      assertFalse(name.startsWith(" "));
    }
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.FEMALE_ROBOT,
          Gender.MALE);
      assertNotNull(name);
      assertFalse(name.startsWith(" "));
    }
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.FEMALE_ROBOT,
          Gender.NONE);
      assertNotNull(name);
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCyborg() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.CYBORG,
          Gender.NONE);
      assertNotNull(name);
      assertFalse(name.startsWith(" "));
    }
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.CYBORG,
          Gender.MALE);
      assertNotNull(name);
      assertFalse(name.startsWith(" "));
    }
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.CYBORG,
          Gender.FEMALE);
      assertNotNull(name);
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLongNameMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.LONG_NAMES,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.LONG_NAMES,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.LONG_NAMES,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBigPlanetNameMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.BIG_PLANT,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBigPlanetNameNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.BIG_PLANT,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBigPlanetNameFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.BIG_PLANT,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLongNameFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.LONG_NAMES,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientNordicMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.ANCIENT_NORDIC,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGAncientNordicFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.ANCIENT_NORDIC,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGAncientNordicNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.ANCIENT_NORDIC,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDeepCreatureMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.DEEP_CREATURE,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDeepCreatureFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.DEEP_CREATURE,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDeepCreatureNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.DEEP_CREATURE,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRomanMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.ANCIENT_ROMAN,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRomanFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.ANCIENT_ROMAN,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRomanNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.ANCIENT_ROMAN,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInsectMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.INSECT,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInsectFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.INSECT,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInsectNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.INSECT,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEvilMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.EVIL_CREATURE,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEvilFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.EVIL_CREATURE,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEvilNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.EVIL_CREATURE,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientDeepMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(
          NameGeneratorType.DEEP_ANCIENT_MONSTER, Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
      assertFalse(name.contains("''"));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientDeepFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(
          NameGeneratorType.DEEP_ANCIENT_MONSTER, Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
      assertFalse(name.contains("''"));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAncientDeepNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(
          NameGeneratorType.DEEP_ANCIENT_MONSTER, Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
      assertFalse(name.contains("''"));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceOrcMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.SPACE_ORC,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceOrcFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.SPACE_ORC,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSpaceOrcNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.SPACE_ORC,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMushroomMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.MUSHROOM,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMushroomFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.MUSHROOM,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMushroomNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.MUSHROOM,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScifiHumanMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.SCIFI_HUMAN,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScifiHumanNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.SCIFI_HUMAN,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScifiHumanFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.SCIFI_HUMAN,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStonePeopleMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.STONE_PEOPLE,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStonePeopleFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.STONE_PEOPLE,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStonePeopleNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.STONE_PEOPLE,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGaseousPeopleMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(
          NameGeneratorType.GASEOUS_CREATURE, Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGaseousPeopleFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(
          NameGeneratorType.GASEOUS_CREATURE, Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGaseousPeopleNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(
          NameGeneratorType.GASEOUS_CREATURE, Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPirateMale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.PIRATE,
          Gender.MALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPirateFemale() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.PIRATE,
          Gender.FEMALE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPirateNone() {
    for (int i = 0; i < 50; i++) {
      String name = NameGenerator.generateName(NameGeneratorType.PIRATE,
          Gender.NONE);
      assertNotNull(name);
      assertTrue(name.contains(" "));
      assertFalse(name.startsWith(" "));
    }
  }

}
