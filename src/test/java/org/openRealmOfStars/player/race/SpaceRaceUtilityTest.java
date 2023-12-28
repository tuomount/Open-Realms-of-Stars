package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.planet.WorldType;

/**
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
      default:
        assertEquals(0, result);
        break;
      }
      //System.out.println(race.getName() + ": " + result + "(" + race.getMaxRad()+ ")");
    }
  }

}
