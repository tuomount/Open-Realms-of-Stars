package org.openRealmOfStars.player.SpaceRace;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
 * Test for SpaceRaceUtility class
 */

public class SpaceRaceUtilityTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorHuman() {
    SpaceRace race = SpaceRace.HUMAN;
    for (int i=0;i<100;i++) {
      String tmp = SpaceRaceUtility.getRandomName(race);
      String[] parts = tmp.split(" ");
      if (parts.length == 2) {
        assertFalse(!parts[0].equals("Terran") && !parts[0].equals("Human"));
        assertFalse(!parts[1].equals("Empire") && !parts[1].equals("Federation")
            && !parts[1].equals("Republic") && !parts[1].equals("Alliance")
            && !parts[1].equals("Kingdom") && !parts[1].equals("Democracy")
            && !parts[1].equals("Hegemony") && !parts[1].equals("Hiearchy"));
          
      } else if (parts.length == 3) {
        assertFalse(!parts[0].equals("Empire") && !parts[0].equals("Federation")
            && !parts[0].equals("Republic") && !parts[0].equals("Alliance")
            && !parts[0].equals("Kingdom") && !parts[0].equals("Democracy")
            && !parts[0].equals("Hegemony") && !parts[0].equals("Hiearchy"));
        assertEquals("of", parts[1]);
        assertFalse(!parts[2].equals("Terran") && !parts[2].equals("Humans"));
      }

    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorMechions() {
    SpaceRace race = SpaceRace.MECHIONS;
    for (int i=0;i<100;i++) {
      String tmp = SpaceRaceUtility.getRandomName(race);
      String[] parts = tmp.split(" ");
      if (parts.length == 2) {
        assertEquals("Mechion", parts[0]);
        assertFalse(!parts[1].equals("Empire") && !parts[1].equals("Federation")
            && !parts[1].equals("Republic") && !parts[1].equals("Alliance")
            && !parts[1].equals("Horde") && !parts[1].equals("AI")
            && !parts[1].equals("Hegemony") && !parts[1].equals("Hiearchy"));
          
      } else if (parts.length == 3) {
        assertFalse(!parts[0].equals("Empire") && !parts[0].equals("Federation")
            && !parts[0].equals("Republic") && !parts[0].equals("Alliance")
            && !parts[0].equals("Horde") && !parts[0].equals("AI")
            && !parts[0].equals("Hegemony") && !parts[0].equals("Hiearchy"));
        assertEquals("of", parts[1]);
        assertEquals("Mechions", parts[2]);
      }
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorCentaurs() {
    SpaceRace race = SpaceRace.CENTAURS;
    for (int i=0;i<100;i++) {
      String tmp = SpaceRaceUtility.getRandomName(race);
      String[] parts = tmp.split(" ");
      if (parts.length == 2) {
        assertEquals("Centaur", parts[0]);
        assertFalse(!parts[1].equals("Empire") && !parts[1].equals("Federation")
            && !parts[1].equals("Republic") && !parts[1].equals("Alliance")
            && !parts[1].equals("Kingdom") && !parts[1].equals("Democracy")
            && !parts[1].equals("Hegemony") && !parts[1].equals("Hiearchy"));
          
      } else if (parts.length == 3) {
        assertFalse(!parts[0].equals("Empire") && !parts[0].equals("Federation")
            && !parts[0].equals("Republic") && !parts[0].equals("Alliance")
            && !parts[0].equals("Kingdom") && !parts[0].equals("Democracy")
            && !parts[0].equals("Hegemony") && !parts[0].equals("Hiearchy"));
        assertEquals("of", parts[1]);
        assertEquals("Centaurs", parts[2]);
      }
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorGreyans() {
    SpaceRace race = SpaceRace.GREYANS;
    for (int i=0;i<100;i++) {
      String tmp = SpaceRaceUtility.getRandomName(race);
      String[] parts = tmp.split(" ");
      if (parts.length == 2) {
        assertEquals("Greyan", parts[0]);
        assertFalse(!parts[1].equals("Empire") && !parts[1].equals("Federation")
            && !parts[1].equals("Republic") && !parts[1].equals("Alliance")
            && !parts[1].equals("Kingdom") && !parts[1].equals("Democracy")
            && !parts[1].equals("Hegemony") && !parts[1].equals("Hiearchy"));
          
      } else if (parts.length == 3) {
        assertFalse(!parts[0].equals("Empire") && !parts[0].equals("Federation")
            && !parts[0].equals("Republic") && !parts[0].equals("Alliance")
            && !parts[0].equals("Kingdom") && !parts[0].equals("Democracy")
            && !parts[0].equals("Hegemony") && !parts[0].equals("Hiearchy"));
        assertEquals("of", parts[1]);
        assertEquals("Greyans", parts[2]);
      }
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorSporks() {
    SpaceRace race = SpaceRace.SPORKS;
    for (int i=0;i<100;i++) {
      String tmp = SpaceRaceUtility.getRandomName(race);
      String[] parts = tmp.split(" ");
      if (parts.length == 2) {
        assertEquals("Spork", parts[0]);
        assertFalse(!parts[1].equals("Empire") && !parts[1].equals("Federation")
            && !parts[1].equals("Republic") && !parts[1].equals("Alliance")
            && !parts[1].equals("Horde") && !parts[1].equals("Democracy")
            && !parts[1].equals("Clan") && !parts[1].equals("Hiearchy"));
          
      } else if (parts.length == 3) {
        assertFalse(!parts[0].equals("Empire") && !parts[0].equals("Federation")
            && !parts[0].equals("Republic") && !parts[0].equals("Alliance")
            && !parts[0].equals("Horde") && !parts[0].equals("Democracy")
            && !parts[0].equals("Clan") && !parts[0].equals("Hiearchy"));
        assertEquals("of", parts[1]);
        assertEquals("Sporks", parts[2]);
      }
    }
  }

}
