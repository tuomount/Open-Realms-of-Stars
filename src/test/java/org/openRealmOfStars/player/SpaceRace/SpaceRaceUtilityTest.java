package org.openRealmOfStars.player.SpaceRace;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.planet.WorldType;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
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
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.UNION);
    assertEquals(true, str.contains("Centaur") || str.contains("Sagittarian"));
    assertEquals(true, str.contains("Union"));
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
  public void testRandomNameGeneratorReborgians() {
    SpaceRace race = SpaceRace.REBORGIANS;
    assertEquals(Attitude.AGGRESSIVE, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.COLLECTIVE);
    assertEquals(true, str.contains("Reborgian") || str.contains("Bionian"));
    assertEquals(true, str.contains("Collective"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorLithorians() {
    SpaceRace race = SpaceRace.LITHORIANS;
    assertEquals(Attitude.EXPANSIONIST, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.UNION);
    assertEquals(true, str.contains("Lithorian") || str.contains("Metavore"));
    assertEquals(true, str.contains("Union"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorAlteirians() {
    SpaceRace race = SpaceRace.ALTEIRIANS;
    assertEquals(Attitude.SCIENTIFIC, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.DEMOCRACY);
    assertEquals(true, str.contains("Alteirian") || str.contains("Floteirian"));
    assertEquals(true, str.contains("Democracy"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorSmaugirians() {
    SpaceRace race = SpaceRace.SMAUGIRIANS;
    assertEquals(Attitude.MILITARISTIC, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.REGIME);
    assertEquals(true, str.contains("Smaugirian") || str.contains("Harean"));
    assertEquals(true, str.contains("Regime"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomNameGeneratorSynthdroids() {
    SpaceRace race = SpaceRace.SYNTHDROIDS;
    assertEquals(Attitude.LOGICAL, race.getAttitude());
    String str = SpaceRaceUtility.getRandomName(race, GovernmentType.AI);
    assertEquals(true, str.contains("Synthdroid") || str.contains("Huskdroid"));
    assertEquals(true, str.contains("AI"));
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
        "* Minimum leader population: 5\n"+
        "* Production: 100%\n"+
        "* Mining: 100%\n"+
        "* Research: 100%\n"+
        "* Food production: 100%\n"+
        "* Growth: 10 turns\n"+
        "* Food require: 100%\n"+
        "* Culture: 100%\n"+
        "* Diplomacy bonus: 2\n"+
        "* War resistance: 60\n"+
        "* Rush: Credit\n"+
        "* Start planet value: 100\n"+
        "* Special: None";
    expectedResult[1] = "### Mechions\n"+
        "Mechanical beings whom do not eat food.\nEach population must be built.\n"+
        "* Max radiation: 8\n"+
        "* Troop power: 12\n"+
        "* Leader lifespan: 2000\n"+
        "* Minimum leader population: 5\n"+
        "* Production: 100%\n"+
        "* Mining: 150%\n"+
        "* Research: 50%\n"+
        "* Food production: 0%\n"+
        "* Growth: never\n"+
        "* Food require: 0%\n"+
        "* Culture: 50%\n"+
        "* Diplomacy bonus: -2\n"+
        "* War resistance: 50\n"+
        "* Rush: Population\n"+
        "* Start planet value: 100\n"+
        "* Special: Population needs to be built and no heirs";
    expectedResult[2] = "### Sporks\n"+
        "Aggressive and warmongering species.\n"+
        "* Max radiation: 5\n"+
        "* Troop power: 12\n"+
        "* Leader lifespan: 75\n"+
        "* Minimum leader population: 5\n"+
        "* Production: 100%\n"+
        "* Mining: 100%\n"+
        "* Research: 100%\n"+
        "* Food production: 100%\n"+
        "* Growth: 10 turns\n"+
        "* Food require: 100%\n"+
        "* Culture: 100%\n"+
        "* Diplomacy bonus: -3\n"+
        "* War resistance: 50\n"+
        "* Rush: Credit and population\n"+
        "* Start planet value: 100\n"+
        "* Special: Extra scout ship and higher combat tech at start";
    expectedResult[3] = "### Greyans\n"+
        "Humanoid creatures with grey skin and big eyes.\nGreyan are excellent researchers.\n"+
        "* Max radiation: 6\n"+
        "* Troop power: 8\n"+
        "* Leader lifespan: 110\n"+
        "* Minimum leader population: 5\n"+
        "* Production: 100%\n"+
        "* Mining: 100%\n"+
        "* Research: 150%\n"+
        "* Food production: 100%\n"+
        "* Growth: 20 turns\n"+
        "* Food require: 100%\n"+
        "* Culture: 100%\n"+
        "* Diplomacy bonus: 0\n"+
        "* War resistance: 40\n"+
        "* Rush: Credit\n"+
        "* Start planet value: 125\n"+
        "* Special: Electronics and propulsion techs are higher at start";
    expectedResult[4] = "### Centaurs\n"+
        "Quadrupedal humanoid creatures which are big,\nabout 5 meters tall."
        + " Due their enormous size their space\nships are more rigid. "
        + "\n"+
        "* Max radiation: 3\n"+
        "* Troop power: 14\n"+
        "* Leader lifespan: 120\n"+
        "* Minimum leader population: 5\n"+
        "* Production: 100%\n"+
        "* Mining: 100%\n"+
        "* Research: 100%\n"+
        "* Food production: 100%\n"+
        "* Growth: 20 turns\n"+
        "* Food require: 100%\n"+
        "* Population limit: +2\n"+
        "* Culture: 100%\n"+
        "* Diplomacy bonus: -1\n"+
        "* War resistance: 70\n"+
        "* Rush: Credit\n"+
        "* Start planet value: 125\n"+
        "* Special: Stronger ships";
    expectedResult[5] = "### Mothoids\n"+
        "Mothoids are sentient insects with capability to hivemind."
        + "\nThey are fast breeding race. Their song is hypnotic so\n"
        + "cultural bonus is granted. Mothoids exo-skeleton is weak\n"
        + "and therefore get negative bonus on mining and troop power.\n"+
        "* Max radiation: 6\n"+
        "* Troop power: 9\n"+
        "* Leader lifespan: 70\n"+
        "* Minimum leader population: 5\n"+
        "* Production: 100%\n"+
        "* Mining: 50%\n"+
        "* Research: 100%\n"+
        "* Food production: 100%\n"+
        "* Growth: 6 turns\n"+
        "* Food require: 100%\n"+
        "* Culture: 150%\n"+
        "* Diplomacy bonus: 0\n"+
        "* War resistance: 30\n"+
        "* Rush: Population\n"+
        "* Start planet value: 100\n"+
        "* Special: No defense tech but one Planetary improvement tech at start";
    expectedResult[6] = "### Teuthidaes\n"
        + "Teuthidaes are octopus like creatures. They are "
        + "scientific\nand military focused race. Their ships have built-in\n"
        + "cloaking devices.\n"
        + "* Max radiation: 4\n"
        + "* Troop power: 10\n"
        + "* Leader lifespan: 80\n"
        + "* Minimum leader population: 5\n"
        + "* Production: 100%\n"
        + "* Mining: 100%\n"
        + "* Research: 150%\n"
        + "* Food production: 100%\n"
        + "* Growth: 10 turns\n"
        + "* Food require: 100%\n"
        + "* Culture: 100%\n"
        + "* Diplomacy bonus: -2\n"
        + "* War resistance: 60\n"
        + "* Rush: Credit\n"
        + "* Start planet value: 125\n"
        + "* Special: Each ship has built-in cloaking device";
    expectedResult[7] = "### Scaurians\n"
        + "Scaurians are small but wide humanoid. They are "
        + "merchantical\nrace. They focus make better trades with other "
        + "and gain more\ncredits.\n"
        + "* Max radiation: 5\n"
        + "* Troop power: 10\n"
        + "* Leader lifespan: 90\n"
        + "* Minimum leader population: 5\n"
        + "* Production: 100%\n"
        + "* Mining: 50%\n"
        + "* Research: 100%\n"
        + "* Food production: 100%\n"
        + "* Growth: 20 turns\n"
        + "* Food require: 100%\n"
        + "* Culture: 100%\n"
        + "* Diplomacy bonus: 1\n"
        + "* War resistance: 40\n"
        + "* Rush: Credit\n"
        + "* Start planet value: 100\n"
        + "* Special: Trade fleet gain 50% more credits and better trade buildings.";
    expectedResult[8] = "### Homarians\n"
        + "Homarians are very strong creatures. "
        + "They have humanoid\nform but they have very thick and "
        + "hard exoskeleton.\nDue their strength they are good in "
        + "physical tasks.\n"
        + "* Max radiation: 3\n"
        + "* Troop power: 11\n"
        + "* Leader lifespan: 70\n"
        + "* Minimum leader population: 5\n"
        + "* Production: 150%\n"
        + "* Mining: 150%\n"
        + "* Research: 50%\n"
        + "* Food production: 200%\n"
        + "* Growth: 10 turns\n"
        + "* Food require: 100%\n"
        + "* Culture: 50%\n"
        + "* Diplomacy bonus: 1\n"
        + "* War resistance: 50\n"
        + "* Rush: Population\n"
        + "* Start planet value: 125\n"
        + "* Special: Starts with 5 population";
    expectedResult[9] = "### Chiraloids\n"
        + "Chiraloids are creatures with four arms and two legs."
        + "\nThey have hard exoskeleton. They also have special"
        + " gland\nwhich uses radioactivity to create nutrient."
        + " This is called\nradiosynthesis.\n"
        + "* Max radiation: 10\n"
        + "* Troop power: 9\n"
        + "* Leader lifespan: 100\n"
        + "* Minimum leader population: 5\n"
        + "* Production: 50%\n"
        + "* Mining: 100%\n"
        + "* Research: 50%\n"
        + "* Food production: 100%\n"
        + "* Growth: 20 turns\n"
        + "* Food require: 100%\n"
        + "* Culture: 100%\n"
        + "* Diplomacy bonus: -4\n"
        + "* War resistance: 50\n"
        + "* Rush: None\n"
        + "* Start planet value: 100\n"
        + "* Special: Radiosynthesis (+1 food per radiation per population)";
    expectedResult[10] = "### Reborgians\n"
        + "Reborgians are organism combined with bionic and"
        + " robotic parts. So they are cyborgs. They can synthesize any"
        + " living space race to their own race, so they are fearful"
        + " conquerors. They need only very little food surviving,"
        + " but their reproduction is very slow.\n"
        + "* Max radiation: 5\n"
        + "* Troop power: 13\n"
        + "* Leader lifespan: 150\n"
        + "* Minimum leader population: 5\n"
        + "* Production: 100%\n"
        + "* Mining: 100%\n"
        + "* Research: 100%\n"
        + "* Food production: 100%\n"
        + "* Growth: always 20 turns\n"
        + "* Food require: 50%\n"
        + "* Culture: 50%\n"
        + "* Diplomacy bonus: -8\n"
        + "* War resistance: 60\n"
        + "* Rush: Population\n"
        + "* Start planet value: 100\n"
        + "* Special: Gain dead enemies as own population."
        + " Steal technology by conquering planets."
        + " No heirs available.";
    expectedResult[11] = "### Lithorians\n"
        + "Lithorians are creatures that eat metal instead of food. They have"
        + " slow grow rate and they have -2 population limit."
        + " They have excellent ability to mine metal.\n"
        + "* Max radiation: 7\n"
        + "* Troop power: 12\n"
        + "* Leader lifespan: 110\n"
        + "* Minimum leader population: 5\n"
        + "* Production: 100%\n"
        + "* Mining: 200%\n"
        + "* Research: 100%\n"
        + "* Food production: 0%\n"
        + "* Growth: 20 turns limited\n"
        + "* Food require: 0%\n"
        + "* Population limit: -2\n"
        + "* Culture: 100%\n"
        + "* Diplomacy bonus: -2\n"
        + "* War resistance: 70\n"
        + "* Rush: None\n"
        + "* Start planet value: 125\n"
        + "* Special: Population eats metal instead of food.";
    expectedResult[12] = "### Alteirians\n"
        + "Alteirian are creatures that live in zero gravity. Because of this"
        + " they need special suites to move planet surface."
        + " Most of their time they live on planet's orbit. Their orbitals are"
        + " more improved than other space race.\n"
        + "* Max radiation: 7\n"
        + "* Troop power: 6\n"
        + "* Leader lifespan: 90\n"
        + "* Minimum leader population: 4\n"
        + "* Production: 50%\n"
        + "* Mining: 50%\n"
        + "* Research: 200%\n"
        + "* Food production: 100%\n"
        + "* Growth: 10 turns\n"
        + "* Food require: 100%\n"
        + "* Culture: 150%\n"
        + "* Diplomacy bonus: -1\n"
        + "* War resistance: 50\n"
        + "* Rush: Credit\n"
        + "* Start planet value: 100\n"
        + "* Special: Lives only on orbitals. Orbitals are cheaper and"
        + " colonized planets have space port.";
    expectedResult[13] = "### Smaugirians\n"
        + "Smaugirians are humanoids that are known for smuggling goods. Their"
        + " cargo ships can contain single weapon or privateering modules.\n"
        + "* Max radiation: 5\n"
        + "* Troop power: 11\n"
        + "* Leader lifespan: 80\n"
        + "* Minimum leader population: 5\n"
        + "* Production: 100%\n"
        + "* Mining: 100%\n"
        + "* Research: 100%\n"
        + "* Food production: 100%\n"
        + "* Growth: 10 turns\n"
        + "* Food require: 100%\n"
        + "* Culture: 100%\n"
        + "* Diplomacy bonus: -2\n"
        + "* War resistance: 70\n"
        + "* Rush: Credit\n"
        + "* Start planet value: 100\n"
        + "* Special: Weapon allowed in cargo ships.";
    expectedResult[14] = "### Synthdroids\n"
        + "Artificial beings that eat only small amount of food. Each "
        + "population must be built.\n"
        + "* Max radiation: 5\n"
        + "* Troop power: 10\n"
        + "* Leader lifespan: 2000\n"
        + "* Minimum leader population: 5\n"
        + "* Production: 100%\n"
        + "* Mining: 100%\n"
        + "* Research: 100%\n"
        + "* Food production: 100%\n"
        + "* Growth: never\n"
        + "* Food require: 50%\n"
        + "* Culture: 50%\n"
        + "* Diplomacy bonus: 0\n"
        + "* War resistance: 60\n"
        + "* Rush: None\n"
        + "* Start planet value: 100\n"
        + "* Special: Population needs to be built and no heirs";
    expectedResult[15] = "### Alonians\n"
        + "Alonian realm starts without"
        + " homeplanet.\nHowever they get higher starting technology and have"
        + " special ability\nwhere single colony ship produces one research point."
        + "\nAlso starbase's laboratories produces more research points.\n"
        + "* Max radiation: 6\n"
        + "* Troop power: 11\n"
        + "* Leader lifespan: 100\n"
        + "* Minimum leader population: 4\n"
        + "* Production: 100%\n"
        + "* Mining: 100%\n"
        + "* Research: 100%\n"
        + "* Food production: 100%\n"
        + "* Growth: 10 turns\n"
        + "* Food require: 100%\n"
        + "* Population limit: +1\n"
        + "* Culture: 100%\n"
        + "* Diplomacy bonus: 1\n"
        + "* War resistance: 50\n"
        + "* Rush: Credit\n"
        + "* Start planet value: 100\n"
        + "* Special: No starting planet, but starbase labs have higher"
        + " research. One colony ship in realm produces one research point"
        + " if not orbiting planet.";
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

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testWorldTypes() {
    for (int i = 0; i <  SpaceRaceUtility.RACE_SELECTION.length; i++) {
      SpaceRace race = SpaceRaceUtility.getRaceByName(
          SpaceRaceUtility.RACE_SELECTION[i]);
      int result = 0;
      for (int j = 0; j < WorldType.values().length; j++) {
        WorldType type = WorldType.values()[j];
        result = result + race.getWorldTypeBaseValue(type);
      }
      //System.out.println(race.toString() + " Rad: " + race.getMaxRad() + " Sum: " + result);
      switch (race) {
      case HUMAN:
        assertEquals(650, result);
        break;
      case MECHIONS:
        assertEquals(625, result);
        break;
      case SPORKS:
        assertEquals(625, result);
        break;
      case GREYANS:
        assertEquals(625, result);
        break;
      case CENTAURS:
        assertEquals(750, result);
        break;
      case MOTHOIDS:
        assertEquals(600, result);
        break;
      case TEUTHIDAES:
        assertEquals(650, result);
        break;
      case SCAURIANS:
        assertEquals(625, result);
        break;
      case HOMARIANS:
        assertEquals(700, result);
        break;
      case CHIRALOIDS:
        assertEquals(600, result);
        break;
      case REBORGIANS:
        assertEquals(650, result);
        break;
      case LITHORIANS:
        assertEquals(575, result);
        break;
      case ALTEIRIANS:
        assertEquals(900, result);
        break;
      case SMAUGIRIANS:
        assertEquals(625, result);
        break;
      case SYNTHDROIDS:
        assertEquals(600, result);
        break;
      case ALONIANS:
        assertEquals(625, result);
        break;
      default:
        assertEquals(0, result);
        break;
      }
      //System.out.println(race.getName() + ": " + result + "(" + race.getMaxRad()+ ")");
    }
  }

}
