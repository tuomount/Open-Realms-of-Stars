package org.openRealmOfStars.starMap.randomEvent;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* JUnits for Random Event
*
*/
public class RandomEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGoodType() {
    RandomEvent event = new RandomEvent(GoodRandomType.MYSTERIOUS_SIGNAL);
    assertEquals(null, event.getBadType());
    assertEquals(GoodRandomType.MYSTERIOUS_SIGNAL, event.getGoodType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBadType() {
    RandomEvent event = new RandomEvent(BadRandomType.RAIDERS);
    assertEquals(BadRandomType.RAIDERS, event.getBadType());
    assertEquals(null, event.getGoodType());
  }

}
