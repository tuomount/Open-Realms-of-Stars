package org.openRealmOfStars.player.government;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Tuomo Untinen
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

/**
* Government Utility tests
*
*/
public class GovernmentUtilityTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testComparison() {
    for (Government gov : GovernmentFactory.getValues()) {
      boolean identical = false;
      boolean similar = false;
      boolean differentGroup = false;
      boolean zero = false;
      boolean different = false;
      for (Government gov2 : GovernmentFactory.getValues()) {
        int value = GovernmentUtility.getGovernmentComparison(gov, gov2);
        if (value == 100) {
          identical = true;
        } else if (value >= 9) {
          similar = true;
        } else if (value >= 2) {
          differentGroup = true;
        } else if (value == 0) {
          zero = true;
        } else {
          different = true;
        }
      }
      assertTrue(identical);
      assertTrue(similar);
      assertTrue(differentGroup || zero);
      assertTrue(different);
    }
  }


}
