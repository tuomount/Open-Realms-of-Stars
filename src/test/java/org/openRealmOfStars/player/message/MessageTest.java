package org.openRealmOfStars.player.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.starMap.Coordinate;

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
 * Test for Message class
 */
public class MessageTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMessage() {
    Icon16x16 icon = Mockito.mock(Icon16x16.class);
    Message message = new Message(MessageType.CONSTRUCTION,
        "New construction is done!", icon);
    assertEquals(MessageType.CONSTRUCTION, message.getType());
    assertEquals(-1, message.getIndex());
    assertEquals(-1, message.getX());
    assertEquals(-1, message.getY());
    assertEquals(null, message.getMatchByString());
    assertEquals("New construction is done!", message.getMessage());
    assertEquals(icon, message.getIcon());
    message.setMatchByString("Planet A");
    assertEquals("Planet A", message.getMatchByString());
    message.setIndex(0);
    assertEquals(0, message.getIndex());
    Coordinate coordinate = Mockito.mock(Coordinate.class);
    Mockito.when(coordinate.getX()).thenReturn(5);
    Mockito.when(coordinate.getY()).thenReturn(6);
    message.setCoordinate(coordinate);
    assertEquals(5, message.getX());
    assertEquals(6, message.getY());
    message.setMessage("Foobar");
    assertEquals("Foobar", message.getMessage());
    message.setType(MessageType.INFORMATION);
    assertEquals(MessageType.INFORMATION, message.getType());
    assertEquals("Information - Foobar", message.toString());
  }


}
