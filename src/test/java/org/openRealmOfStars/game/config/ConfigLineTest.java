package org.openRealmOfStars.game.config;

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
* Tests for Config Line class
*
*/

public class ConfigLineTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEmpty() {
    ConfigLine line = new ConfigLine(null);
    assertEquals(ConfigLineType.EMPTY, line.getType());
    assertEquals(null, line.getComment());
    assertEquals(null, line.getKey());
    assertEquals(null, line.getValue());
    assertEquals("", line.toString());
    line.setValue("test");
    assertEquals(null, line.getComment());
    assertEquals(null, line.getKey());
    assertEquals(null, line.getValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testComment() {
    ConfigLine line = new ConfigLine("# Test comment");
    assertEquals(ConfigLineType.COMMENT, line.getType());
    assertEquals("# Test comment", line.getComment());
    assertEquals(null, line.getKey());
    assertEquals(null, line.getValue());
    assertEquals("# Test comment", line.toString());
    line.setValue("test");
    assertEquals("# Test comment", line.getComment());
    assertEquals(null, line.getKey());
    assertEquals(null, line.getValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testKeyValue() {
    ConfigLine line = new ConfigLine("Key=value");
    assertEquals(ConfigLineType.KEY_VALUE, line.getType());
    assertEquals(null, line.getComment());
    assertEquals("Key", line.getKey());
    assertEquals("value", line.getValue());
    assertEquals("Key=value", line.toString());
    line.setValue("test");
    assertEquals(null, line.getComment());
    assertEquals("Key", line.getKey());
    assertEquals("test", line.getValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testKeyEmptyValue() {
    ConfigLine line = new ConfigLine("Key=");
    assertEquals(ConfigLineType.KEY_VALUE, line.getType());
    assertEquals(null, line.getComment());
    assertEquals("Key", line.getKey());
    assertEquals("", line.getValue());
    line.setValue("test");
    assertEquals(null, line.getComment());
    assertEquals("Key", line.getKey());
    assertEquals("test", line.getValue());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testInvalidKeyValue() {
    ConfigLine line = new ConfigLine("Key=value=error");
    assertEquals(ConfigLineType.KEY_VALUE, line.getType());
  }

}
