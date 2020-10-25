package org.openRealmOfStars.utilities;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* JUnit for getting pseudo random numbers
*
*/
public class DiceGeneratorTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test10Basic() {
    int max = 10;
    int loop = 100000;
    int[] scores = new int[max];
    for (int i = 0; i < loop; i++) {
      int index = DiceGenerator.getRandom(max - 1);
      scores[index]++;
    }
    boolean success = true;
    for (int i = 0; i < max; i++) {
      if (Math.abs(scores[i] - loop/max) > loop / max) {
        success = false;
        System.out.println("Possible deviation of randomness!");
        System.out.println(i + ": " + scores[i]);
      }
    }
    assertEquals(true, success);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test100Basic() {
    int max = 100;
    int loop = 100000;
    int[] scores = new int[max];
    for (int i = 0; i < loop; i++) {
      int index = DiceGenerator.getRandom(max - 1);
      scores[index]++;
    }
    boolean success = true;
    for (int i = 0; i < max; i++) {
      if (Math.abs(scores[i] - loop/max) > loop / max) {
        success = false;
        System.out.println("Possible deviation of randomness!");
        System.out.println(i + ": " + scores[i]);
      }
    }
    assertEquals(true, success);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test10Range() {
    int max = 10;
    int loop = 100000;
    int[] scores = new int[max];
    for (int i = 0; i < loop; i++) {
      int index = DiceGenerator.getRandom(10, 20 - 1);
      index = index -10;
      scores[index]++;
    }
    boolean success = true;
    for (int i = 0; i < max; i++) {
      if (Math.abs(scores[i] - loop/max) > loop / max) {
        success = false;
        System.out.println("Possible deviation of randomness!");
        System.out.println(i + ": " + scores[i]);
      }
    }
    assertEquals(true, success);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testZero() {
    int loop = 100;
    boolean success = true;
    for (int i = 0; i < loop; i++) {
      int index = DiceGenerator.getRandom(0);
      if (index != 0) {
        success = false;
      }
    }
    assertEquals(true, success);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNegative() {
    int loop = 100;
    boolean success = true;
    for (int i = 0; i < loop; i++) {
      try {
        DiceGenerator.getRandom(-1);
        success = false;
      } catch (IllegalArgumentException e) {
        // Do nothing
      }
    }
    assertEquals(true, success);
  }

}
