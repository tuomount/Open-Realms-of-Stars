package org.openRealmOfStars.game.tutorial;

import static org.junit.Assert.assertEquals;

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
* Test help line for tutorial.
*
*/
public class HelpLineTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    HelpLine line = new HelpLine(0);
    assertEquals(0, line.getIndex());
    assertEquals("", line.getCategory());
    assertEquals("", line.getTitle());
    assertEquals("", line.getText());
    assertEquals(false, line.isShown());
    line.setCategory("Test category");
    line.setText("Test text");
    line.setTitle("Test title");
    line.setShown(true);
    assertEquals("Test category", line.getCategory());
    assertEquals("Test title", line.getTitle());
    assertEquals("Test text", line.getText());
    assertEquals(true, line.isShown());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic2() {
    HelpLine line = new HelpLine(33);
    assertEquals(33, line.getIndex());
    line.setCategory("Test category");
    line.setText("Test text");
    line.setTitle("Test title");
    assertEquals("33: Test category - Test title", line.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testParsing() {
    HelpLine line = HelpLine.parseHelpline("00|Start|Start of Open Realm of Stars|Text");
    assertEquals(0, line.getIndex());
    assertEquals("0: Start - Start of Open Realm of Stars", line.toString());
    assertEquals("Text", line.getText());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testParsingComment() {
    HelpLine line = HelpLine.parseHelpline("#00|Start|Start of Open Realm of Stars|Text");
    assertEquals(null, line);
  }

}
