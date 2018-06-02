package org.openRealmOfStars.starMap.newsCorp.scoreBoard;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

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
* Score board row test
*
*/
public class RowTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Row a = new Row(5, 0);
    Row b = new Row(8, 1);
    Row c = new Row(10, 2, 3);
    assertEquals(0, a.getRealm());
    assertEquals(5, a.getScore());
    assertEquals(false, a.isAlliance());
    assertEquals(-1, a.getAllianceRealm());
    assertEquals(1, b.getRealm());
    assertEquals(8, b.getScore());
    assertEquals(false, b.isAlliance());
    assertEquals(-1, b.getAllianceRealm());
    assertEquals(2, c.getRealm());
    assertEquals(10, c.getScore());
    assertEquals(true, c.isAlliance());
    assertEquals(3, c.getAllianceRealm());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCompare() {
    Row a = new Row(5, 0);
    Row b = new Row(8, 1);
    Row c = new Row(10, 2);
    assertEquals(-3, a.compareTo(b));
    assertEquals(-2, b.compareTo(c));
    assertEquals(5, c.compareTo(a));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSort() {
    Row a = new Row(5, 0);
    Row b = new Row(8, 1);
    Row c = new Row(10, 2);
    ArrayList<Row> list = new ArrayList<>();
    list.add(c);
    list.add(b);
    list.add(a);
    Collections.sort(list);
    assertEquals(a, list.get(0));
    assertEquals(b, list.get(1));
    assertEquals(c, list.get(2));
  }
}
