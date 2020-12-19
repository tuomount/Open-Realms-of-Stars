package org.openRealmOfStars.utilities.json.values;

import static org.junit.Assert.*;

import java.io.IOException;

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
* JUnit for NumberValue Test
*
*/
public class NumberValueTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    NumberValue number = new NumberValue("0");
    assertEquals(0, number.getValueAsInt());
    assertEquals(0.0, number.getValueAsDouble(), 0);
    number = new NumberValue("-0");
    assertEquals(0, number.getValueAsInt());
    assertEquals(0.0, number.getValueAsDouble(), 0);
    number = new NumberValue("-1.2");
    assertEquals(-1, number.getValueAsInt());
    assertEquals(-1.2, number.getValueAsDouble(), 0.1);
    number = new NumberValue("2.4");
    assertEquals(2, number.getValueAsInt());
    assertEquals(2.4, number.getValueAsDouble(), 0.1);
    number = new NumberValue("0.4e4");
    assertEquals(4000, number.getValueAsInt());
    assertEquals(4000, number.getValueAsDouble(), 0.1);
    number = new NumberValue("0.4e-4");
    assertEquals(0, number.getValueAsInt());
    assertEquals(0.00004, number.getValueAsDouble(), 0.0001);
    number = new NumberValue("0.4E-4");
    assertEquals(0, number.getValueAsInt());
    assertEquals(0.00004, number.getValueAsDouble(), 0.0001);
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNotNumber() throws IOException {
    NumberValue number = new NumberValue("This is not a number");
    assertEquals(1, number.getValueAsInt());
  }

}
