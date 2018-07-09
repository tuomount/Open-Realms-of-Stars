package org.openRealmOfStars.gui.icons;

import static org.junit.Assert.*;

import org.junit.Test;

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
* Class for testing Icons
*
*/
public class IconsTest {

  @Test
  public void testHappyFaces() {
    Icon16x16 icon = Icons.getHappyFace(5);
    assertEquals(Icons.getIconByName(Icons.ICON_VERY_HAPPY), icon);
    icon = Icons.getHappyFace(4);
    assertEquals(Icons.getIconByName(Icons.ICON_VERY_HAPPY), icon);
    icon = Icons.getHappyFace(3);
    assertEquals(Icons.getIconByName(Icons.ICON_HAPPY), icon);
    icon = Icons.getHappyFace(2);
    assertEquals(Icons.getIconByName(Icons.ICON_HAPPY), icon);
    icon = Icons.getHappyFace(1);
    assertEquals(Icons.getIconByName(Icons.ICON_OKAY), icon);
    icon = Icons.getHappyFace(0);
    assertEquals(Icons.getIconByName(Icons.ICON_OKAY), icon);
    icon = Icons.getHappyFace(-1);
    assertEquals(Icons.getIconByName(Icons.ICON_OKAY), icon);
    icon = Icons.getHappyFace(-2);
    assertEquals(Icons.getIconByName(Icons.ICON_SAD), icon);
    icon = Icons.getHappyFace(-3);
    assertEquals(Icons.getIconByName(Icons.ICON_SAD), icon);
    icon = Icons.getHappyFace(-4);
    assertEquals(Icons.getIconByName(Icons.ICON_VERY_SAD), icon);
    icon = Icons.getHappyFace(-5);
    assertEquals(Icons.getIconByName(Icons.ICON_VERY_SAD), icon);
  }

}
