package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
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
 * Tests for Attitude
 */
public class AttitudeTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIndexes() {
    for (int i = 0;i < 8; i++) {
      assertEquals(i,Attitude.getTypeByIndex(i).getIndex());
    }
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOutOfBound() {
     Attitude.getTypeByIndex(255).getIndex();
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRandom() {
    for (int i = 0;i < 100; i++) {
      Attitude.getRandom();
      // Throws illegal exeption if random fails
    }
  }


}
