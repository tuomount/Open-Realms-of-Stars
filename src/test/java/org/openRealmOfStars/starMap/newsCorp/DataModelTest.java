package org.openRealmOfStars.starMap.newsCorp;

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
* News Corporation Data Model Test
*
*/
public class DataModelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test() {
    DataModel data = new DataModel();
    assertEquals(0, data.getSize());
    data.addData(5);
    assertEquals(1, data.getSize());
    assertEquals(5, data.getData()[0]);
    data.addData(3);
    assertEquals(2, data.getSize());
    assertEquals(5, data.getData()[0]);
    assertEquals(3, data.getData()[1]);
    data.addData(6);
    assertEquals(3, data.getSize());
    assertEquals(5, data.getData()[0]);
    assertEquals(3, data.getData()[1]);
    assertEquals(6, data.getData()[2]);
  }

}
