package org.openRealmOfStars.starMap.history.event;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018 Tuomo Untinen
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
 */

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.Coordinate;

public class PlayerStartEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    PlayerStartEvent event = new PlayerStartEvent(coord,
        "Test planet", 0);
    assertEquals(EventType.PLAYER_START, event.getType());
    assertEquals(coord, event.getCoordinate());
    assertEquals("Test planet", event.getName());
    assertEquals(0, event.getPlayerIndex());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEncodingAndParsing() throws IOException {
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(22);
    Mockito.when(coord.getY()).thenReturn(11);
    PlayerStartEvent event = new PlayerStartEvent(coord, "Test I", 1);
    byte[] buf = event.createByteArray();
    PlayerStartEvent event2 = PlayerStartEvent.createStartEvent(buf);
    assertEquals(EventType.PLAYER_START, event2.getType());
    assertEquals(event.getName(), event2.getName());
    assertEquals(22, event2.getCoordinate().getX());
    assertEquals(11, event2.getCoordinate().getY());
    assertEquals(1, event2.getPlayerIndex());
  }

}
