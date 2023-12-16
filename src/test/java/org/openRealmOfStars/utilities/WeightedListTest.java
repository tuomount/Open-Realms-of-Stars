package org.openRealmOfStars.utilities;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 Tuomo Untinen
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Weighted list class JUnits
 *
 */
public class WeightedListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFixedArrays() {
    WeightedList<Integer> listOfNumber = new WeightedList<>(
        new double[] {
            3, 6, 9, 6, 3
        },
        new Integer[] {
            0, 1, 2, 3, 4
        });
    assertEquals(false, listOfNumber.isEmpty());
    assertEquals(5, listOfNumber.size());
    assertEquals("WeightedList [entries="
        + "[Entry [weight=0.0, value=0], Entry [weight=3.0, value=1],"
        + " Entry [weight=9.0, value=2], Entry [weight=18.0, value=3],"
        + " Entry [weight=24.0, value=4]], total=27.0]",
        listOfNumber.toString());
    Integer selected = listOfNumber.pickRandom();
    assertNotNull(selected);
    listOfNumber.clear();
    assertEquals(true, listOfNumber.isEmpty());
  }

  @Test(expected = UnsupportedOperationException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEmptyRandomPick() {
    WeightedList<Integer> listOfNumber = new WeightedList<>();
    Integer selected = listOfNumber.pickRandom();
    assertNull(selected);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFixedArraysWithAdd() {
    WeightedList<Integer> listOfNumber = new WeightedList<>(
        new double[] {
            3, 6, 9
        },
        new Integer[] {
            0, 1, 2
        });
    listOfNumber.add(6, Integer.valueOf(3));
    assertEquals(4, listOfNumber.size());
    listOfNumber.add(3, Integer.valueOf(4));
    assertEquals(5, listOfNumber.size());
    assertEquals("WeightedList [entries="
        + "[Entry [weight=0.0, value=0], Entry [weight=3.0, value=1],"
        + " Entry [weight=9.0, value=2], Entry [weight=18.0, value=3],"
        + " Entry [weight=24.0, value=4]], total=27.0]",
        listOfNumber.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFixedArraysWithAdd2() {
    WeightedList<Integer> listOfNumber = new WeightedList<>(
        new double[] {
            3, 6, 9
        },
        new Integer[] {
            0, 1, 2
        });
    listOfNumber.add(WeightedList.entry(6, Integer.valueOf(3)));
    assertEquals(4, listOfNumber.size());
    listOfNumber.add(WeightedList.entry(3, Integer.valueOf(4)));
    assertEquals(5, listOfNumber.size());
    assertEquals("WeightedList [entries="
        + "[Entry [weight=0.0, value=0], Entry [weight=3.0, value=1],"
        + " Entry [weight=9.0, value=2], Entry [weight=18.0, value=3],"
        + " Entry [weight=24.0, value=4]], total=27.0]",
        listOfNumber.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEmptyDifferentSizeOfLists() {
    WeightedList<Integer> listOfNumber = new WeightedList<>(
        new double[] {
            3, 6, 9, 6, 3
        },
        new Integer[] {
            0, 1, 2
        });
    assertTrue(listOfNumber.isEmpty());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEmpty() {
    WeightedList<Integer> listOfNumber = new WeightedList<>();
    assertTrue(listOfNumber.isEmpty());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFixedArraysWithFixedNumbers() {
    WeightedList<Integer> listOfNumber = new WeightedList<>(
        new double[] {
            1, 1, 1, 1, 1
        },
        new Integer[] {
            0, 1, 2, 3, 4
        });
    long[] values = new long[5];
    int max = 5;
    for (int i = 0; i < max; i++) {
      Integer value = listOfNumber.pickRandom(i);
      values[value.intValue()]++;
    }
    assertEquals(1, values[0]);
    assertEquals(1, values[1]);
    assertEquals(1, values[2]);
    assertEquals(1, values[3]);
    assertEquals(1, values[4]);
  }

}
