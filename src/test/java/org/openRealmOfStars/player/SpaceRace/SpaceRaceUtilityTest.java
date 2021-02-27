package org.openRealmOfStars.player.SpaceRace;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.government.GovernmentType;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018 Tuomo Untinen
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
    assertEquals(Attitude.DIPLOMATIC, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.DEMOCRACY);
    assertEquals(true, str.contains("Terran") || str.contains("Human"));
    assertEquals(true, str.contains("Democracy"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorMechions() {
    SpaceRace race = SpaceRace.MECHIONS;
    assertEquals(Attitude.LOGICAL, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.AI);
    assertEquals(true, str.contains("Mechion") || str.contains("Steel"));
    assertEquals(true, str.contains("AI"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorCentaurs() {
    SpaceRace race = SpaceRace.CENTAURS;
    assertEquals(Attitude.DIPLOMATIC, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.ALLIANCE);
    assertEquals(true, str.contains("Centaur") || str.contains("Sagittarian"));
    assertEquals(true, str.contains("Alliance"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorGreyans() {
    SpaceRace race = SpaceRace.GREYANS;
    assertEquals(Attitude.SCIENTIFIC, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.KINGDOM);
    assertEquals(true, str.contains("Greyan") || str.contains("Aesir"));
    assertEquals(true, str.contains("Kingdom"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorHomarians() {
    SpaceRace race = SpaceRace.HOMARIANS;
    assertEquals(Attitude.PEACEFUL, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.NEST);
    assertEquals(true, str.contains("Homarian") || str.contains("Cancerian"));
    assertEquals(true, str.contains("Nest"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorSporks() {
    SpaceRace race = SpaceRace.SPORKS;
    assertEquals(Attitude.AGGRESSIVE, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.CLAN);
    assertEquals(true, str.contains("Spork") || str.contains("Taurus"));
    assertEquals(true, str.contains("Clan"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorMothoids() {
    SpaceRace race = SpaceRace.MOTHOIDS;
    assertEquals(Attitude.EXPANSIONIST, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.HIVEMIND);
    assertEquals(true, str.contains("Mothoid") || str.contains("Scorpio"));
    assertEquals(true, str.contains("Hive-mind"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorTechtidae() {
    SpaceRace race = SpaceRace.TEUTHIDAES;
    assertEquals(Attitude.MILITARISTIC, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.FEDERATION);
    assertEquals(true, str.contains("Teuthidae") || str.contains("Squiddan"));
    assertEquals(true, str.contains("Federation"));
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorScaurians() {
    SpaceRace race = SpaceRace.SCAURIANS;
    assertEquals(Attitude.MERCHANTICAL, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.EMPIRE);
    assertEquals(true, str.contains("Scaurian") || str.contains("Nemean"));
    assertEquals(true, str.contains("Empire"));
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorChiraloids() {
    SpaceRace race = SpaceRace.CHIRALOIDS;
    assertEquals(Attitude.AGGRESSIVE, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.HORDE);
    assertEquals(true, str.contains("Chiraloid") || str.contains("Capricorn"));
    assertEquals(true, str.contains("Horde"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFullDescriptions() {
    String[] expectedResult = new String[SpaceRaceUtility.RACE_SELECTION.length];
    expectedResult[0] = "### Humans\n"+
        "Humans are great diplomats but\nthey are about average in everything else.\n"+
        "* Max radiation: 4\n"+
        "* Troop power: 10\n"+
        "* Leader lifespan: 80\n"+
        "* Production: 100%\n"+
        "* Mining: 100%\n"+
        "* Research: 100%\n"+
        "* Food production: 100%\n"+
        "* Growth: 100%\n"+
        "* Food require: 100%\n"+
        "* Culture: 100%\n"+
        "* Diplomacy bonus: 2\n"+
        "* War resistance: 60\n"+
        "* Rush: Credit\n"+
        "* Special: None";
    expectedResult[1] = "### Mechions\n"+
        "Mechanical beings whom do not eat food.\nEach population must be built.\n"+
        "* Max radiation: 8\n"+
        "* Troop power: 12\n"+
        "* Leader lifespan: 2000\n"+
        "* Production: 100%\n"+
        "* Mining: 150%\n"+
        "* Research: 50%\n"+
        "* Food production: 0%\n"+
        "* Growth: 0%\n"+
        "* Food require: 0%\n"+
        "* Culture: 50%\n"+
        "* Diplomacy bonus: -2\n"+
        "* War resistance: 50\n"+
        "* Rush: Population\n"+
        "* Special: Population needs to be built and no heirs";
    expectedResult[2] = "### Sporks\n"+
        "Aggressive and warmongering species.\n"+
        "* Max radiation: 5\n"+
        "* Troop power: 12\n"+
        "* Leader lifespan: 75\n"+
        "* Production: 100%\n"+
        "* Mining: 100%\n"+
        "* Research: 100%\n"+
        "* Food production: 100%\n"+
        "* Growth: 100%\n"+
        "* Food require: 100%\n"+
        "* Culture: 100%\n"+
        "* Diplomacy bonus: -3\n"+
        "* War resistance: 50\n"+
        "* Rush: Credit and population\n"+
        "* Special: Extra scout ship and higher combat tech at start";
    expectedResult[3] = "### Greyans\n"+
        "Humanoid creatures with grey skin and big eyes.\nGreyan are excellent researchers.\n"+
        "* Max radiation: 6\n"+
        "* Troop power: 8\n"+
        "* Leader lifespan: 110\n"+
        "* Production: 100%\n"+
        "* Mining: 100%\n"+
        "* Research: 150%\n"+
        "* Food production: 100%\n"+
        "* Growth: 50%\n"+
        "* Food require: 100%\n"+
        "* Culture: 100%\n"+
        "* Diplomacy bonus: 0\n"+
        "* War resistance: 40\n"+
        "* Rush: Credit\n"+
        "* Special: Electronics and propulsion techs are higher at start";
    expectedResult[4] = "### Centaurs\n"+
        "Quadrupedal humanoid creatures which are big,\nabout 5 meters tall."
        + " Due their enormous size their space\nships are more rigid. "
        + "\n"+
        "* Max radiation: 3\n"+
        "* Troop power: 14\n"+
        "* Leader lifespan: 120\n"+
        "* Production: 100%\n"+
        "* Mining: 100%\n"+
        "* Research: 100%\n"+
        "* Food production: 100%\n"+
        "* Growth: 50%\n"+
        "* Food require: 100%\n"+
        "* Culture: 100%\n"+
        "* Diplomacy bonus: -1\n"+
        "* War resistance: 70\n"+
        "* Rush: Credit\n"+
        "* Special: Stronger ships";
    expectedResult[5] = "### Mothoids\n"+
        "Mothoids are sentient insects with capability to hivemind."
        + "\nThey are fast breeding race. Their song is hypnotic so\n"
        + "cultural bonus is granted. Mothoids exo-skeleton is weak\n"
        + "and therefore get negative bonus on mining and troop power.\n"+
        "* Max radiation: 6\n"+
        "* Troop power: 9\n"+
        "* Leader lifespan: 70\n"+
        "* Production: 100%\n"+
        "* Mining: 50%\n"+
        "* Research: 100%\n"+
        "* Food production: 100%\n"+
        "* Growth: 150%\n"+
        "* Food require: 100%\n"+
        "* Culture: 150%\n"+
        "* Diplomacy bonus: 0\n"+
        "* War resistance: 30\n"+
        "* Rush: Population\n"+
        "* Special: No defense tech but one Planetary improvement tech at start";
    expectedResult[6] = "### Teuthidaes\n"
        + "Teuthidaes are octopus like creatures. They are "
        + "scientific\nand military focused race. Their ships have built-in\n"
        + "cloaking devices.\n"
        + "* Max radiation: 4\n"
        + "* Troop power: 10\n"
        + "* Leader lifespan: 80\n"
        + "* Production: 100%\n"
        + "* Mining: 100%\n"
        + "* Research: 150%\n"
        + "* Food production: 100%\n"
        + "* Growth: 100%\n"
        + "* Food require: 100%\n"
        + "* Culture: 100%\n"
        + "* Diplomacy bonus: -2\n"
        + "* War resistance: 60\n"
        + "* Rush: Credit\n"
        + "* Special: Each ship has built-in cloaking device";
    expectedResult[7] = "### Scaurians\n"
        + "Scaurians are small but wide humanoid. They are "
        + "merchantical\nrace. They focus make better trades with other "
        + "and gain more\ncredits.\n"
        + "* Max radiation: 5\n"
        + "* Troop power: 12\n"
        + "* Leader lifespan: 90\n"
        + "* Production: 100%\n"
        + "* Mining: 50%\n"
        + "* Research: 100%\n"
        + "* Food production: 100%\n"
        + "* Growth: 50%\n"
        + "* Food require: 100%\n"
        + "* Culture: 100%\n"
        + "* Diplomacy bonus: 1\n"
        + "* War resistance: 40\n"
        + "* Rush: Credit\n"
        + "* Special: Trade fleet gain 50% more credits and better trade buildings.";
    expectedResult[8] = "### Homarians\n"
        + "Homarians are very strong creatures. "
        + "They have humanoid\nform but they have very thick and "
        + "hard exoskeleton.\nDue their strength they are good in "
        + "physical tasks.\n"
        + "* Max radiation: 3\n"
        + "* Troop power: 11\n"
        + "* Leader lifespan: 70\n"
        + "* Production: 150%\n"
        + "* Mining: 150%\n"
        + "* Research: 50%\n"
        + "* Food production: 200%\n"
        + "* Growth: 100%\n"
        + "* Food require: 100%\n"
        + "* Culture: 50%\n"
        + "* Diplomacy bonus: 1\n"
        + "* War resistance: 50\n"
        + "* Rush: Population\n"
        + "* Special: Starts with 5 population";
    expectedResult[9] = "### Chiraloids\n"
        + "Chiraloids are creatures with four arms and two legs."
        + "\nThey have hard exoskeleton. They also have special"
        + " gland\nwhich uses radioactivity to create nutrient."
        + " This is called\nradiosynthesis.\n"
        + "* Max radiation: 10\n"
        + "* Troop power: 9\n"
        + "* Leader lifespan: 100\n"
        + "* Production: 50%\n"
        + "* Mining: 100%\n"
        + "* Research: 50%\n"
        + "* Food production: 100%\n"
        + "* Growth: 50%\n"
        + "* Food require: 100%\n"
        + "* Culture: 100%\n"
        + "* Diplomacy bonus: -4\n"
        + "* War resistance: 50\n"
        + "* Rush: None\n"
        + "* Special: Radiosynthesis (+1 food per radiation per population)";
    expectedResult[10] = "### Cygruts\n"
        + "Cygruts are organism combined with bionic and"
        + " robotic parts. So they are cyborgs. They can synthesize any"
        + " living space race to their own race, so they are fearful"
        + " conquerors. They need only very little food surviving,"
        + " but their reproduction is very slow.\n"
        + "* Max radiation: 5\n"
        + "* Troop power: 13\n"
        + "* Leader lifespan: 150\n"
        + "* Production: 100%\n"
        + "* Mining: 100%\n"
        + "* Research: 100%\n"
        + "* Food production: 100%\n"
        + "* Growth: 50%\n"
        + "* Food require: 50%\n"
        + "* Culture: 50%\n"
        + "* Diplomacy bonus: -8\n"
        + "* War resistance: 60\n"
        + "* Rush: Population\n"
        + "* Special: Gain dead enemies as own population."
        + " Steal technology by conquering planets.";
    for (int i = 0; i <  SpaceRaceUtility.RACE_SELECTION.length; i++) {
      SpaceRace race = SpaceRaceUtility.getRaceByName(
          SpaceRaceUtility.RACE_SELECTION[i]);
      assertEquals(expectedResult[i],race.getFullDescription(true, false));
      if (i == 0) {
        String result = race.getFullDescription(false, false);
        if (!result.startsWith("<html>")) {
          assertFalse(true);
        }
      }
      //System.out.println(race.getFullDescription(true, true));
    }
  }

}
