package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
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
 * Tests for Diplomacy Bonus
 */
public class DiplomacyBonusTest {

  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() { 
    DiplomacyBonus bonus = new DiplomacyBonus(
        DiplomacyBonusType.getTypeByIndex(0), SpaceRace.HUMAN);
    bonus.setBonusLasting(9);
    bonus.setBonusValue(5);
    assertEquals(9, bonus.getBonusLasting());
    assertEquals(5, bonus.getBonusValue());
    bonus.setBonusLasting(256);
    assertEquals(255, bonus.getBonusLasting());
    bonus.setBonusLasting(-1);
    assertEquals(0, bonus.getBonusLasting());
    assertEquals(0, bonus.getBonusValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testHuman() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE 
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -2,   3,  -5,  5,   5,  5, -3};
    int[] bonusLasting = {255, 255, 255, 255, 200,  20,  50, 200, 80, 255, 1, 100};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.HUMAN);
      assertEquals(bonusValues[i], bonus.getBonusValue());
    
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
      if (i == 0 || i == 2 || i == 3) {
        assertEquals(true, bonus.isOnlyOne());
      }
      if (i == 5) {
        assertEquals(false, bonus.isOnlyOne());
      }
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCentaurs() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE 
    int[] bonusValues =  {-30,  -8,  12,  25,  -8,  -2,   2,  -5,  4,   5,  5, -3};
    int[] bonusLasting = {255, 255, 255, 255, 200,  20,  50, 200, 80, 255, 1, 100};
    int[] bonusLasting2 = {255, 255, 255, 255, 199,  19,  49, 199, 79, 255, 2, 99};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.CENTAURS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
      bonus.handleBonusForTurn();
      assertEquals(bonusLasting2[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGreyans() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE 
    int[] bonusValues =  {-40,  -8,  18,  30,  -5,  -2,   2,  -5,  4,   5,  5, -3};
    int[] bonusLasting = {255, 255, 255, 255, 200,  20,  50, 200, 80, 255, 1, 100};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.GREYANS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSporks() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE 
    int[] bonusValues =  {-30,  -2,  12,  25,  -3,  -1,   2,  -1,  4,   2,  1, -2};
    int[] bonusLasting = {255, 255, 255, 255, 200,  10,  50, 128, 80, 255, 1, 80};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.SPORKS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMechions() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE 
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -3,   2,  -5,  4,  -3,  5, -1};
    int[] bonusLasting = {255, 255, 255, 255, 200,  10,  50, 200, 80, 255, 1, 40};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.MECHIONS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
      System.out.print(bonus.getType().toString()+", ");
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMothoids() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE 
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -2,   2,  -5,  4,   5,  8, -3};
    int[] bonusLasting = {255, 255, 255, 255, 200,  20,  50, 200, 80, 255, 1, 100};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.MOTHOIDS);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTeuthidaes() {
    //                 IN_WAR,WDEC,INTA,IN_A,DICA,BOCR,GVAL,DEMA,DTR,SRAC,LONG_PEACE 
    int[] bonusValues =  {-30,  -8,  12,  25,  -5,  -2,   2,  -5,  4,   -3,  5, -3};
    int[] bonusLasting = {255, 255, 255, 255, 200,  20,  50, 200, 80, 255, 1, 100};
    for (int i = 0; i < DiplomacyBonusType.MAX_BONUS_TYPE; i++) {
      DiplomacyBonus bonus = new DiplomacyBonus(
          DiplomacyBonusType.getTypeByIndex(i), SpaceRace.TEUTHIDAES);
      assertEquals(bonusValues[i], bonus.getBonusValue());
      assertEquals(bonusLasting[i], bonus.getBonusLasting());
    }
  }

}
