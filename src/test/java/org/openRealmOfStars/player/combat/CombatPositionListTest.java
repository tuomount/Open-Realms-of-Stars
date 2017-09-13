package org.openRealmOfStars.player.combat;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2017  Tuomo Untinen
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
 * Test for Combat position lists
 */
public class CombatPositionListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAttackerList() {
    CombatPositionList list = new BottomPositionList();
    assertEquals(true, list.getCoordinate(0).sameAs(new CombatCoordinate(4, 7)));
    assertEquals(true, list.getCoordinate(1).sameAs(new CombatCoordinate(3, 7)));
    assertEquals(true, list.getCoordinate(2).sameAs(new CombatCoordinate(5, 7)));
    assertEquals(true, list.getCoordinate(3).sameAs(new CombatCoordinate(2, 7)));
    assertEquals(true, list.getCoordinate(4).sameAs(new CombatCoordinate(6, 7)));
    assertEquals(true, list.getCoordinate(5).sameAs(new CombatCoordinate(4, 8)));
    assertEquals(true, list.getCoordinate(6).sameAs(new CombatCoordinate(1, 7)));
    assertEquals(true, list.getCoordinate(7).sameAs(new CombatCoordinate(7, 7)));
    assertEquals(true, list.getCoordinate(8).sameAs(new CombatCoordinate(3, 8)));
    assertEquals(true, list.getCoordinate(9).sameAs(new CombatCoordinate(5, 8)));
    assertEquals(true, list.getCoordinate(10).sameAs(new CombatCoordinate(2, 8)));
    assertEquals(true, list.getCoordinate(11).sameAs(new CombatCoordinate(6, 8)));
    assertEquals(true, list.getCoordinate(12).sameAs(new CombatCoordinate(0, 7)));
    assertEquals(true, list.getCoordinate(13).sameAs(new CombatCoordinate(8, 7)));
    assertEquals(true, list.getCoordinate(14).sameAs(new CombatCoordinate(1, 8)));
    assertEquals(true, list.getCoordinate(15).sameAs(new CombatCoordinate(7, 8)));
    assertEquals(true, list.getCoordinate(16).sameAs(new CombatCoordinate(0, 8)));
    assertEquals(true, list.getCoordinate(17).sameAs(new CombatCoordinate(8, 8)));
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAttackerExceed() {
    CombatPositionList list = new BottomPositionList();
    list.getCoordinate(18);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDefenderList() {
    CombatPositionList list = new TopPositionList();
    assertEquals(true, list.getCoordinate(0).sameAs(new CombatCoordinate(4, 1)));
    assertEquals(true, list.getCoordinate(1).sameAs(new CombatCoordinate(3, 1)));
    assertEquals(true, list.getCoordinate(2).sameAs(new CombatCoordinate(5, 1)));
    assertEquals(true, list.getCoordinate(3).sameAs(new CombatCoordinate(2, 1)));
    assertEquals(true, list.getCoordinate(4).sameAs(new CombatCoordinate(6, 1)));
    assertEquals(true, list.getCoordinate(5).sameAs(new CombatCoordinate(4, 0)));
    assertEquals(true, list.getCoordinate(6).sameAs(new CombatCoordinate(1, 1)));
    assertEquals(true, list.getCoordinate(7).sameAs(new CombatCoordinate(7, 1)));
    assertEquals(true, list.getCoordinate(8).sameAs(new CombatCoordinate(3, 0)));
    assertEquals(true, list.getCoordinate(9).sameAs(new CombatCoordinate(5, 0)));
    assertEquals(true, list.getCoordinate(10).sameAs(new CombatCoordinate(2, 0)));
    assertEquals(true, list.getCoordinate(11).sameAs(new CombatCoordinate(6, 0)));
    assertEquals(true, list.getCoordinate(12).sameAs(new CombatCoordinate(0, 1)));
    assertEquals(true, list.getCoordinate(13).sameAs(new CombatCoordinate(8, 1)));
    assertEquals(true, list.getCoordinate(14).sameAs(new CombatCoordinate(1, 0)));
    assertEquals(true, list.getCoordinate(15).sameAs(new CombatCoordinate(7, 0)));
    assertEquals(true, list.getCoordinate(16).sameAs(new CombatCoordinate(0, 0)));
    assertEquals(true, list.getCoordinate(17).sameAs(new CombatCoordinate(8, 0)));
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDefenderExceed() {
    CombatPositionList list = new TopPositionList();
    list.getCoordinate(18);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarbaseList() {
    CombatPositionList list = new StarbasePositionList();
    assertEquals(true, list.getCoordinate(0).sameAs(new CombatCoordinate(4, 2)));
    assertEquals(true, list.getCoordinate(1).sameAs(new CombatCoordinate(3, 2)));
    assertEquals(true, list.getCoordinate(2).sameAs(new CombatCoordinate(5, 2)));
    assertEquals(true, list.getCoordinate(3).sameAs(new CombatCoordinate(2, 2)));
    assertEquals(true, list.getCoordinate(4).sameAs(new CombatCoordinate(6, 2)));
    assertEquals(true, list.getCoordinate(5).sameAs(new CombatCoordinate(1, 2)));
    assertEquals(true, list.getCoordinate(6).sameAs(new CombatCoordinate(7, 2)));
  }
  
  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarbaseExceed() {
    CombatPositionList list = new StarbasePositionList();
    list.getCoordinate(17);
  }

}
