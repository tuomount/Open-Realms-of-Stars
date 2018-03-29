package org.openRealmOfStars.starMap.history.event;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.Coordinate;

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
* Combat event test
*
*/
public class DiplomaticEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    DiplomaticEvent event = new DiplomaticEvent(coord);
    assertEquals(EventType.DIPLOMATIC_RELATION_CHANGE, event.getType());
    assertEquals(coord, event.getCoordinate());
    assertEquals(null, event.getPlanetName());
    assertEquals("", event.getText());
    event.setText("Historical");
    assertEquals("Historical", event.getText());
    event.setPlanetName("Test planet");
    assertEquals("Test planet", event.getPlanetName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEncodingAndParsing() throws IOException {
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(22);
    Mockito.when(coord.getY()).thenReturn(11);
    DiplomaticEvent event = new DiplomaticEvent(coord);
    event.setPlanetName("Test I");
    event.setText("Historical");
    byte[] buf = event.createByteArray();
    DiplomaticEvent event2 = DiplomaticEvent.createDiplomaticEvent(buf);
    assertEquals(EventType.DIPLOMATIC_RELATION_CHANGE, event2.getType());
    assertEquals(22, event2.getCoordinate().getX());
    assertEquals(11, event2.getCoordinate().getY());
    assertEquals("Test I", event2.getPlanetName());
    assertEquals("Historical", event2.getText());
    event = new DiplomaticEvent(coord);
    event.setText("Historical");
    buf = event.createByteArray();
    event2 = DiplomaticEvent.createDiplomaticEvent(buf);
    assertEquals(null, event2.getPlanetName());
  }

}
