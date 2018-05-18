package org.openRealmOfStars.game.config;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

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
* Tests for Config file class
*
*/

public class ConfigFileTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEmpty() {
    ConfigFile file = new ConfigFile();
    assertEquals(0, file.getNumberOfLines());
    assertEquals(null, file.getLine(0));
    assertEquals(null, file.getLineByKey("Key"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNonEmpty() {
    ConfigLine line = Mockito.mock(ConfigLine.class);
    Mockito.when(line.getType()).thenReturn(ConfigLineType.KEY_VALUE);
    Mockito.when(line.getKey()).thenReturn("Key");
    ConfigFile file = new ConfigFile();
    file.add(line);
    assertEquals(1, file.getNumberOfLines());
    assertEquals(line, file.getLine(0));
    assertEquals(line, file.getLineByKey("Key"));
  }

}
