package org.openRealmOfStars.gui.labels;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Test for SquareInfo class
 * 
 */
public class SquareInfoTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateStarFieldTextArea() {
    StarFieldTextArea text = new StarFieldTextArea();
    text.setText("Test");
    assertEquals("Test", text.getText());
    assertEquals("Smoothscrolling even shoud not be", false,
        text.isSmoothScroll());
    text.setCharacterWidth(0);
    text.setScrollText("Foobar", 2);
    text.getNextLine();
    text.getPrevLine();
    text.disableScrollText();
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateStarFieldTextArea2() {
    StarFieldTextArea text = new StarFieldTextArea(5,5);
    text.setSmoothScroll(true);
    assertEquals("No smoothscrolling", true, text.isSmoothScroll());
    text.setCharacterWidth(3);
    assertEquals("Too early next row", false, text.getSmoothScrollNextRow());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateStarFieldTextArea3() {
    StarFieldTextArea text = new StarFieldTextArea("FOOBAR");
    text.setSmoothScroll(true);
    assertEquals("No smoothscrolling", true, text.isSmoothScroll());
    text.setCharacterWidth(3);
    assertEquals("Too early next row", false, text.getSmoothScrollNextRow());
  }

}
