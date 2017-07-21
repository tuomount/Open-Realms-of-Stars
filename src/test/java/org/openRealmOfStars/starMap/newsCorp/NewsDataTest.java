package org.openRealmOfStars.starMap.newsCorp;

import static org.junit.Assert.*;

import org.junit.Test;

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
* News Data class tests
*
*/
public class NewsDataTest {

  @Test
  public void testBasic() {
    NewsData newsData = new NewsData();
    assertEquals("", newsData.getNewsText());
    assertEquals("", newsData.getImageInstructions());
    newsData.setNewsText("Test");
    newsData.setImageInstructions("Background:BLACK");
    assertEquals("Test", newsData.getNewsText());
    assertEquals("Background:BLACK", newsData.getImageInstructions());
    assertEquals("Test", newsData.toString());
    newsData.setNewsText("ABCDEFGHIJKLMNOPQRSTUVWXYZ!!! This is not shown");
    assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ!!!", newsData.toString());
  }

}
