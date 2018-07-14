package org.openRealmOfStars.starMap.planet;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
* Happiness effect test
*
*/
public class HappinessEffectTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    HappinessEffect effect = new HappinessEffect(HappinessBonus.NONE, 0);
    assertEquals(HappinessBonus.NONE, effect.getType());
    assertEquals(0, effect.getValue());
    assertEquals("HappinessEffect [bonus=NONE, value=0]", effect.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomHappinessZero() {
    HappinessEffect effect = HappinessEffect.createHappinessEffect(0);
    assertEquals(HappinessBonus.NONE, effect.getType());
    assertEquals(0, effect.getValue());
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomHappinessOne() {
    for (int i = 0; i < 1000; i++) {
      HappinessEffect effect = HappinessEffect.createHappinessEffect(1);
      if (effect.getType() == HappinessBonus.PRODUCTION) {
        assertEquals(1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.NONE) {
        assertEquals(0, effect.getValue());
      }
    }
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomHappinessMinusOne() {
    for (int i = 0; i < 1000; i++) {
      HappinessEffect effect = HappinessEffect.createHappinessEffect(-1);
      if (effect.getType() == HappinessBonus.PRODUCTION) {
        assertEquals(-1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.NONE) {
        assertEquals(0, effect.getValue());
      }
    }
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomHappinessTwo() {
    for (int i = 0; i < 1000; i++) {
      HappinessEffect effect = HappinessEffect.createHappinessEffect(2);
      if (effect.getType() == HappinessBonus.PRODUCTION) {
        assertEquals(1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.CREDIT) {
        assertEquals(1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.METAL) {
        assertEquals(1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.CULTURE) {
        assertEquals(1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.NONE) {
        assertEquals(0, effect.getValue());
      }
    }
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomHappinessMinusTwo() {
    for (int i = 0; i < 1000; i++) {
      HappinessEffect effect = HappinessEffect.createHappinessEffect(-2);
      if (effect.getType() == HappinessBonus.PRODUCTION) {
        assertEquals(-1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.CREDIT) {
        assertEquals(-1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.METAL) {
        assertEquals(-1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.CULTURE) {
        assertEquals(-1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.NONE) {
        assertEquals(0, effect.getValue());
      }
    }
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomHappinessThree() {
    for (int i = 0; i < 1000; i++) {
      HappinessEffect effect = HappinessEffect.createHappinessEffect(3);
      if (effect.getType() == HappinessBonus.PRODUCTION) {
        assertEquals(true, effect.getValue() == 1 || effect.getValue() == 2);
      }
      if (effect.getType() == HappinessBonus.CREDIT) {
        assertEquals(true, effect.getValue() == 1 || effect.getValue() == 2);
      }
      if (effect.getType() == HappinessBonus.METAL) {
        assertEquals(true, effect.getValue() == 1 || effect.getValue() == 2);
      }
      if (effect.getType() == HappinessBonus.CULTURE) {
        assertEquals(true, effect.getValue() == 1 || effect.getValue() == 2);
      }
      if (effect.getType() == HappinessBonus.NONE) {
        assertEquals(0, effect.getValue());
      }
    }
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomHappinessMinusThree() {
    for (int i = 0; i < 1000; i++) {
      HappinessEffect effect = HappinessEffect.createHappinessEffect(-3);
      if (effect.getType() == HappinessBonus.PRODUCTION) {
        assertEquals(-2, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.CREDIT) {
        assertEquals(-2, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.METAL) {
        assertEquals(-2, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.KILL_POPULATION) {
        assertEquals(1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.NONE) {
        assertEquals(0, effect.getValue());
      }
    }
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomHappinessFour() {
    for (int i = 0; i < 1000; i++) {
      HappinessEffect effect = HappinessEffect.createHappinessEffect(4);
      if (effect.getType() == HappinessBonus.PRODUCTION) {
        assertEquals(2, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.CREDIT) {
        assertEquals(2, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.METAL) {
        assertEquals(2, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.CULTURE) {
        assertEquals(2, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.NONE) {
        assertEquals(0, effect.getValue());
      }
    }
  }
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandomHappinessMinusFour() {
    for (int i = 0; i < 1000; i++) {
      HappinessEffect effect = HappinessEffect.createHappinessEffect(-4);
      if (effect.getType() == HappinessBonus.PRODUCTION) {
        assertEquals(-2, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.CREDIT) {
        assertEquals(-2, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.KILL_POPULATION) {
        assertEquals(1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.DESTROY_BUILDING) {
        assertEquals(1, effect.getValue());
      }
      if (effect.getType() == HappinessBonus.NONE) {
        assertEquals(0, effect.getValue());
      }
    }
  }

}
