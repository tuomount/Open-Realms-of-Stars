package org.openRealmOfStars.player.government;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018, 2020 Tuomo Untinen
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
    assertEquals(9, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.DEMOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.HEGEMONY, governments[6]);
    assertEquals(GovernmentType.HIERARCHY, governments[7]);
    assertEquals(GovernmentType.KINGDOM, governments[8]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCentaursGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.CENTAURS);
    assertEquals(9, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.DEMOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.HEGEMONY, governments[6]);
    assertEquals(GovernmentType.HIERARCHY, governments[7]);
    assertEquals(GovernmentType.KINGDOM, governments[8]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGreyansGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.GREYANS);
    assertEquals(9, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.DEMOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.HEGEMONY, governments[6]);
    assertEquals(GovernmentType.HIERARCHY, governments[7]);
    assertEquals(GovernmentType.KINGDOM, governments[8]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMechionsGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.MECHIONS);
    assertEquals(10, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.DEMOCRACY, governments[1]);
    assertEquals(GovernmentType.FEDERATION, governments[2]);
    assertEquals(GovernmentType.REPUBLIC, governments[3]);
    assertEquals(GovernmentType.ENTERPRISE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.AI, governments[6]);
    assertEquals(GovernmentType.MECHANICAL_HORDE, governments[7]);
    assertEquals(GovernmentType.HEGEMONY, governments[8]);
    assertEquals(GovernmentType.HIERARCHY, governments[9]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSporksGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.SPORKS);
    assertEquals(9, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.FEDERATION, governments[1]);
    assertEquals(GovernmentType.REPUBLIC, governments[2]);
    assertEquals(GovernmentType.CLAN, governments[3]);
    assertEquals(GovernmentType.HORDE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.HEGEMONY, governments[6]);
    assertEquals(GovernmentType.HIERARCHY, governments[7]);
    assertEquals(GovernmentType.KINGDOM, governments[8]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHomariansGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.HOMARIANS);
    assertEquals(9, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.FEDERATION, governments[1]);
    assertEquals(GovernmentType.REPUBLIC, governments[2]);
    assertEquals(GovernmentType.CLAN, governments[3]);
    assertEquals(GovernmentType.HORDE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.HEGEMONY, governments[6]);
    assertEquals(GovernmentType.NEST, governments[7]);
    assertEquals(GovernmentType.KINGDOM, governments[8]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMothoidsGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.MOTHOIDS);
    assertEquals(10, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.FEDERATION, governments[1]);
    assertEquals(GovernmentType.REPUBLIC, governments[2]);
    assertEquals(GovernmentType.CLAN, governments[3]);
    assertEquals(GovernmentType.HORDE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.HEGEMONY, governments[6]);
    assertEquals(GovernmentType.NEST, governments[7]);
    assertEquals(GovernmentType.KINGDOM, governments[8]);
    assertEquals(GovernmentType.HIVEMIND, governments[9]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testScauriansGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.SCAURIANS);
    assertEquals(10, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.FEDERATION, governments[1]);
    assertEquals(GovernmentType.REPUBLIC, governments[2]);
    assertEquals(GovernmentType.ENTERPRISE, governments[3]);
    assertEquals(GovernmentType.GUILD, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.HEGEMONY, governments[6]);
    assertEquals(GovernmentType.HIERARCHY, governments[7]);
    assertEquals(GovernmentType.KINGDOM, governments[8]);
    assertEquals(GovernmentType.DEMOCRACY, governments[9]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTeuthidaesGovernments() {
    GovernmentType[] governments = GovernmentUtility.getGovernmentsForRace(
        SpaceRace.TEUTHIDAES);
    assertEquals(10, governments.length);
    assertEquals(GovernmentType.ALLIANCE, governments[0]);
    assertEquals(GovernmentType.FEDERATION, governments[1]);
    assertEquals(GovernmentType.REPUBLIC, governments[2]);
    assertEquals(GovernmentType.ENTERPRISE, governments[3]);
    assertEquals(GovernmentType.HORDE, governments[4]);
    assertEquals(GovernmentType.EMPIRE, governments[5]);
    assertEquals(GovernmentType.HEGEMONY, governments[6]);
    assertEquals(GovernmentType.HIERARCHY, governments[7]);
    assertEquals(GovernmentType.KINGDOM, governments[8]);
    assertEquals(GovernmentType.DEMOCRACY, governments[9]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGovernmentIndexes() {
    GovernmentType[] govs = GovernmentType.values();
    for (int i = 0; i < govs.length; i++) {
      assertEquals(govs[i], GovernmentUtility.getGovernmentByIndex(i));
    }
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGovernmentIndexesBig() {
    GovernmentUtility.getGovernmentByIndex(255);
  }

}
