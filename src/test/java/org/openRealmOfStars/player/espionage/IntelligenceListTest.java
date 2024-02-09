package org.openRealmOfStars.player.espionage;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
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
import org.openRealmOfStars.player.fleet.FleetType;

/**
*
* Intelligence List Test
*
*/
public class IntelligenceListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    IntelligenceList list = new IntelligenceList(3);
    assertEquals(0, list.getSize());
    assertEquals(null, list.getIntelligence(0));
    assertEquals(3, list.getPlayerIndex());
    assertEquals(0, list.getTotalBonus());
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 1, "Fleet #1");
    assertEquals(1, list.getSize());
    assertEquals(1, list.getTotalBonus());
    assertEquals(1, list.getOwnBonus());
    assertEquals("Fleet #1", list.getIntelligence(0).getDescription());
    list.addIntelligenceBonus(IntelligenceBonusType.TRADE, 5, "Spy trade");
    assertEquals(2, list.getSize());
    assertEquals(6, list.getTotalBonus());
    assertEquals(1, list.getOwnBonus());
    assertEquals("Spy trade", list.getIntelligence(1).getDescription());
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 5, "Fleet #2");
    assertEquals(3, list.getSize());
    assertEquals(10, list.getTotalBonus());
    assertEquals(6, list.getOwnBonus());
    assertEquals("Fleet #2", list.getIntelligence(2).getDescription());
    assertEquals("SPY_FLEET - Fleet #1 : 1,"
        + "TRADE - Spy trade : 5,"
        + "SPY_FLEET - Fleet #2 : 5,", list.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsRecognized() {
    IntelligenceList list = new IntelligenceList(3);
    assertEquals(0, list.getSize());
    assertEquals(null, list.getIntelligence(0));
    assertEquals(3, list.getPlayerIndex());
    assertEquals(0, list.getTotalBonus());
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 1, "Fleet #1");
    assertEquals(1, list.getSize());
    assertEquals(1, list.getTotalBonus());
    assertEquals("Fleet #1", list.getIntelligence(0).getDescription());
    list.addIntelligenceBonus(IntelligenceBonusType.TRADE, 2, "Spy trade");
    assertEquals(false, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.PRIVATEER));
    assertEquals(2, list.getSize());
    assertEquals(3, list.getTotalBonus());
    assertEquals("Spy trade", list.getIntelligence(1).getDescription());
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 1, "Fleet #2");
    assertEquals(3, list.getSize());
    assertEquals(4, list.getTotalBonus());
    assertEquals("Fleet #2", list.getIntelligence(2).getDescription());
    assertEquals(true, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.PRIVATEER));
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 2, "Fleet #3");
    assertEquals(true, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.PRIVATEER));
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 2, "Fleet #4");
    assertEquals(true, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(false, list.isFleetTypeRecognized(FleetType.PRIVATEER));
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 2, "Fleet #5");
    assertEquals(true, list.isFleetTypeRecognized(FleetType.NON_MILITARY));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.STARBASE));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.MILITARY));
    assertEquals(true, list.isFleetTypeRecognized(FleetType.PRIVATEER));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDescription() {
    assertEquals("No intelligence", IntelligenceList.getTotalBonusAsDescriptions(0));
    assertEquals("+-40% military power estimation\n",
        IntelligenceList.getTotalBonusAsDescriptions(1));
    assertEquals("+-40% military power estimation\nIntelligence trade\n",
        IntelligenceList.getTotalBonusAsDescriptions(2));
    assertEquals("+-30% military power estimation\nIntelligence trade\n",
        IntelligenceList.getTotalBonusAsDescriptions(3));
    assertEquals("+-30% military power estimation\nIntelligence trade"
        + "\nVisibility of non military ships.\n",
        IntelligenceList.getTotalBonusAsDescriptions(4));
    assertEquals("+-20% military power estimation\nIntelligence trade"
        + "\nVisibility of non military ships.\n",
        IntelligenceList.getTotalBonusAsDescriptions(5));
    assertEquals("+-20% military power estimation\nIntelligence trade"
        + "\nVisibility of non military ships.\nVisibility of deployed starbases.\n",
        IntelligenceList.getTotalBonusAsDescriptions(6));
    assertEquals("+-10% military power estimation\nIntelligence trade"
        + "\nVisibility of non military ships.\nVisibility of deployed starbases.\n",
        IntelligenceList.getTotalBonusAsDescriptions(7));
    assertEquals("+-10% military power estimation\nIntelligence trade"
        + "\nVisibility of all fleets expect privateers.",
        IntelligenceList.getTotalBonusAsDescriptions(8));
    assertEquals("Accurate knowledge of military power.\nIntelligence trade"
        + "\nVisibility of all fleets expect privateers.",
        IntelligenceList.getTotalBonusAsDescriptions(9));
    assertEquals("Accurate knowledge of military power.\nIntelligence trade"
        + "\nVisibility of all fleets.",
        IntelligenceList.getTotalBonusAsDescriptions(10));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIntelligenceEstimate() {
    IntelligenceList list = new IntelligenceList(5);
    int value = list.getIntelligenceLevel1Estimate();
    if (value < -40 || value > 40) {
      assertTrue("Estimate level 1 out of scope", false);
    }
    value = list.getIntelligenceLevel3Estimate();
    if (value < -30 || value > 30) {
      assertTrue("Estimate level 3 out of scope", false);
    }
    value = list.getIntelligenceLevel5Estimate();
    if (value < -20 || value > 20) {
      assertTrue("Estimate level 5 out of scope", false);
    }
    value = list.getIntelligenceLevel7Estimate();
    if (value < -10 || value > 10) {
      assertTrue("Estimate level 7 out of scope", false);
    }
    list.setIntelligenceLevel7Estimate(-10);
    assertEquals(-10, list.getIntelligenceLevel7Estimate());
    list.setIntelligenceLevel5Estimate(18);
    assertEquals(18, list.getIntelligenceLevel5Estimate());
    list.setIntelligenceLevel3Estimate(-28);
    assertEquals(-28, list.getIntelligenceLevel3Estimate());
    list.setIntelligenceLevel1Estimate(38);
    assertEquals(38, list.getIntelligenceLevel1Estimate());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIntelligenceEstimation() {
    IntelligenceList list = new IntelligenceList(5);
    list.setIntelligenceLevel7Estimate(-5);
    list.setIntelligenceLevel5Estimate(18);
    list.setIntelligenceLevel3Estimate(-28);
    list.setIntelligenceLevel1Estimate(38);
    assertEquals(0, list.estimateMilitaryPower(100));
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 1, "Test");
    assertEquals(138, list.estimateMilitaryPower(100));
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 2, "Test");
    assertEquals(72, list.estimateMilitaryPower(100));
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 2, "Test");
    assertEquals(118, list.estimateMilitaryPower(100));
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 2, "Test");
    assertEquals(95, list.estimateMilitaryPower(100));
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 2, "Test");
    assertEquals(100, list.estimateMilitaryPower(100));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIntelligenceOverTurn() {
    IntelligenceList list = new IntelligenceList(2);
    list.setIntelligenceLevel7Estimate(-5);
    assertEquals(-5, list.getIntelligenceLevel7Estimate());
    list.setIntelligenceLevel5Estimate(8);
    assertEquals(8, list.getIntelligenceLevel5Estimate());
    list.setIntelligenceLevel3Estimate(-8);
    assertEquals(-8, list.getIntelligenceLevel3Estimate());
    list.setIntelligenceLevel1Estimate(3);
    assertEquals(3, list.getIntelligenceLevel1Estimate());
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 1, "Fleet #1");
    assertEquals(1, list.getSize());
    assertEquals(1, list.getTotalBonus());
    assertEquals("Fleet #1", list.getIntelligence(0).getDescription());
    list.addIntelligenceBonus(IntelligenceBonusType.TRADE, 5, "Spy trade");
    assertEquals(2, list.getSize());
    assertEquals(6, list.getTotalBonus());
    assertEquals("Spy trade", list.getIntelligence(1).getDescription());
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 5, "Fleet #2");
    assertEquals(3, list.getSize());
    assertEquals(10, list.getTotalBonus());
    assertEquals("Fleet #2", list.getIntelligence(2).getDescription());
    list.clearList();
    assertEquals(-5, list.getIntelligenceLevel7Estimate());
    assertEquals(8, list.getIntelligenceLevel5Estimate());
    assertEquals(-8, list.getIntelligenceLevel3Estimate());
    assertEquals(3, list.getIntelligenceLevel1Estimate());
    assertEquals(0, list.getSize());
    list.addIntelligenceBonus(IntelligenceBonusType.SPY_FLEET, 1, "Fleet #1");
    assertEquals(1, list.getSize());
    assertEquals(1, list.getTotalBonus());
    assertEquals("Fleet #1", list.getIntelligence(0).getDescription());
  }

}
