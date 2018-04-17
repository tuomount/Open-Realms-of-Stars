package org.openRealmOfStars.player.espionage;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.fleet.FleetType;

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
* Espionage List Test
*
*/
public class EspionageListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    EspionageList list = new EspionageList(3);
    assertEquals(0, list.getSize());
    assertEquals(null, list.getEspionage(0));
    assertEquals(3, list.getPlayerIndex());
    assertEquals(0, list.getTotalBonus());
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Fleet #1");
    assertEquals(1, list.getSize());
    assertEquals(1, list.getTotalBonus());
    assertEquals(1, list.getOwnBonus());
    assertEquals("Fleet #1", list.getEspionage(0).getDescription());
    list.addEspionageBonus(EspionageBonusType.TRADE, 5, "Spy trade");
    assertEquals(2, list.getSize());
    assertEquals(6, list.getTotalBonus());
    assertEquals(1, list.getOwnBonus());
    assertEquals("Spy trade", list.getEspionage(1).getDescription());
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 5, "Fleet #2");
    assertEquals(3, list.getSize());
    assertEquals(10, list.getTotalBonus());
    assertEquals(6, list.getOwnBonus());
    assertEquals("Fleet #2", list.getEspionage(2).getDescription());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsRecognized() {
    EspionageList list = new EspionageList(3);
    assertEquals(0, list.getSize());
    assertEquals(null, list.getEspionage(0));
    assertEquals(3, list.getPlayerIndex());
    assertEquals(0, list.getTotalBonus());
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Fleet #1");
    assertEquals(1, list.getSize());
    assertEquals(1, list.getTotalBonus());
    assertEquals("Fleet #1", list.getEspionage(0).getDescription());
    list.addEspionageBonus(EspionageBonusType.TRADE, 2, "Spy trade");
    assertEquals(false, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.PRIVATEER));
    assertEquals(2, list.getSize());
    assertEquals(3, list.getTotalBonus());
    assertEquals("Spy trade", list.getEspionage(1).getDescription());
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Fleet #2");
    assertEquals(3, list.getSize());
    assertEquals(4, list.getTotalBonus());
    assertEquals("Fleet #2", list.getEspionage(2).getDescription());
    assertEquals(true, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.PRIVATEER));
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Fleet #3");
    assertEquals(true, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.PRIVATEER));
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Fleet #4");
    assertEquals(true, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.PRIVATEER));
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Fleet #5");
    assertEquals(true, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.PRIVATEER));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDescription() {
    assertEquals("No espionage", EspionageList.getTotalBonusAsDescriptions(0));
    assertEquals("+-40% military power estimation\n",
        EspionageList.getTotalBonusAsDescriptions(1));
    assertEquals("+-40% military power estimation\nEspionage trade\n",
        EspionageList.getTotalBonusAsDescriptions(2));
    assertEquals("+-30% military power estimation\nEspionage trade\n",
        EspionageList.getTotalBonusAsDescriptions(3));
    assertEquals("+-30% military power estimation\nEspionage trade"
        + "\nVisibility of non military ships.\n",
        EspionageList.getTotalBonusAsDescriptions(4));
    assertEquals("+-20% military power estimation\nEspionage trade"
        + "\nVisibility of non military ships.\n",
        EspionageList.getTotalBonusAsDescriptions(5));
    assertEquals("+-20% military power estimation\nEspionage trade"
        + "\nVisibility of non military ships.\nVisibility of deployed starbases.\n",
        EspionageList.getTotalBonusAsDescriptions(6));
    assertEquals("+-10% military power estimation\nEspionage trade"
        + "\nVisibility of non military ships.\nVisibility of deployed starbases.\n",
        EspionageList.getTotalBonusAsDescriptions(7));
    assertEquals("+-10% military power estimation\nEspionage trade"
        + "\nVisibility of all fleets expect privateers.",
        EspionageList.getTotalBonusAsDescriptions(8));
    assertEquals("Accurate knowledge of military power.\nEspionage trade"
        + "\nVisibility of all fleets expect privateers.",
        EspionageList.getTotalBonusAsDescriptions(9));
    assertEquals("Accurate knowledge of military power.\nEspionage trade"
        + "\nVisibility of all fleets.",
        EspionageList.getTotalBonusAsDescriptions(10));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEspionageEstimate() {
    EspionageList list = new EspionageList(5);
    int value = list.getEspionageLevel1Estimate();
    if (value < -40 || value > 40) {
      assertTrue("Estimate level 1 out of scope", false);
    }
    value = list.getEspionageLevel3Estimate();
    if (value < -30 || value > 30) {
      assertTrue("Estimate level 3 out of scope", false);
    }
    value = list.getEspionageLevel5Estimate();
    if (value < -20 || value > 20) {
      assertTrue("Estimate level 5 out of scope", false);
    }
    value = list.getEspionageLevel7Estimate();
    if (value < -10 || value > 10) {
      assertTrue("Estimate level 7 out of scope", false);
    }
    list.setEspionageLevel7Estimate(-10);
    assertEquals(-10, list.getEspionageLevel7Estimate());
    list.setEspionageLevel5Estimate(18);
    assertEquals(18, list.getEspionageLevel5Estimate());
    list.setEspionageLevel3Estimate(-28);
    assertEquals(-28, list.getEspionageLevel3Estimate());
    list.setEspionageLevel1Estimate(38);
    assertEquals(38, list.getEspionageLevel1Estimate());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEspionageEstimation() {
    EspionageList list = new EspionageList(5);
    list.setEspionageLevel7Estimate(-5);
    list.setEspionageLevel5Estimate(18);
    list.setEspionageLevel3Estimate(-28);
    list.setEspionageLevel1Estimate(38);
    assertEquals(0, list.estimateMilitaryPower(100));
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Test");
    assertEquals(138, list.estimateMilitaryPower(100));
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(72, list.estimateMilitaryPower(100));
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(118, list.estimateMilitaryPower(100));
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(95, list.estimateMilitaryPower(100));
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 2, "Test");
    assertEquals(100, list.estimateMilitaryPower(100));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEspionageOverTurn() {
    EspionageList list = new EspionageList(2);
    list.setEspionageLevel7Estimate(-5);
    assertEquals(-5, list.getEspionageLevel7Estimate());
    list.setEspionageLevel5Estimate(8);
    assertEquals(8, list.getEspionageLevel5Estimate());
    list.setEspionageLevel3Estimate(-8);
    assertEquals(-8, list.getEspionageLevel3Estimate());
    list.setEspionageLevel1Estimate(3);
    assertEquals(3, list.getEspionageLevel1Estimate());
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Fleet #1");
    assertEquals(1, list.getSize());
    assertEquals(1, list.getTotalBonus());
    assertEquals("Fleet #1", list.getEspionage(0).getDescription());
    list.addEspionageBonus(EspionageBonusType.TRADE, 5, "Spy trade");
    assertEquals(2, list.getSize());
    assertEquals(6, list.getTotalBonus());
    assertEquals("Spy trade", list.getEspionage(1).getDescription());
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 5, "Fleet #2");
    assertEquals(3, list.getSize());
    assertEquals(10, list.getTotalBonus());
    assertEquals("Fleet #2", list.getEspionage(2).getDescription());
    list.clearList();
    assertEquals(-5, list.getEspionageLevel7Estimate());
    assertEquals(8, list.getEspionageLevel5Estimate());
    assertEquals(-8, list.getEspionageLevel3Estimate());
    assertEquals(3, list.getEspionageLevel1Estimate());
    assertEquals(0, list.getSize());
    list.addEspionageBonus(EspionageBonusType.SPY_FLEET, 1, "Fleet #1");
    assertEquals(1, list.getSize());
    assertEquals(1, list.getTotalBonus());
    assertEquals("Fleet #1", list.getEspionage(0).getDescription());
  }

}
