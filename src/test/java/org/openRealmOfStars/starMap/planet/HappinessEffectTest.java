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

}
