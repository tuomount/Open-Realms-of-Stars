package org.openRealmOfStars.player.government;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2023 Tuomo Untinen
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
* Government Type enumeration tests
*
*/
public class GovernmentTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDescriptions() {
    for (Government gov : GovernmentFactory.getValues()) {
      //System.out.println(gov.getDescription(true));
      assertEquals(4, gov.getTraitValue());
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDescriptions2() {
    for (Government gov : GovernmentFactory.getValues()) {
      String result = gov.getDescription(true, true);
      assertEquals(true, result.contains("Points: 4"));
      assertEquals(4, gov.getTraitValue());
    }
  }

}
