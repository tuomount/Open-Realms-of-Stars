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
* Historical event on planet tests
*
*/
public class EventOnPlanetTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    EventOnPlanet event = new EventOnPlanet(EventType.PLANET_COLONIZED, coord,
        "Test planet", 0);
    assertEquals(EventType.PLANET_COLONIZED, event.getType());
    assertEquals(coord, event.getCoordinate());
    assertEquals("Test planet", event.getName());
    assertEquals("", event.getText());
    assertEquals(0, event.getPlayerIndex());
    event.setText("Historical stuff");
    assertEquals("Historical stuff", event.getText());
    event = new EventOnPlanet(EventType.PLANET_CONQUERED, coord,
        "Test planet", 0);
    assertEquals(0, event.getPlayerIndex());
    assertEquals(EventType.PLANET_CONQUERED, event.getType());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFail() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    EventOnPlanet event = new EventOnPlanet(EventType.CULTURE_CHANGE, coord,
        "Test planet", 0);
    assertEquals(EventType.CULTURE_CHANGE, event.getType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEncodingAndParsing() throws IOException {
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(22);
    Mockito.when(coord.getY()).thenReturn(11);
    EventOnPlanet event = new EventOnPlanet(EventType.PLANET_COLONIZED, coord, "Test I", 1);
    event.setText("Historical");
    byte[] buf = event.createByteArray();
    EventOnPlanet event2 = EventOnPlanet.createEventOnPlanet(buf);
    assertEquals(EventType.PLANET_COLONIZED, event2.getType());
    assertEquals(event.getText(), event2.getText());
    assertEquals(event.getName(), event2.getName());
    assertEquals(22, event2.getCoordinate().getX());
    assertEquals(11, event2.getCoordinate().getY());
    assertEquals(1, event2.getPlayerIndex());
    event = new EventOnPlanet(EventType.PLANET_CONQUERED, coord, "Test I", 2);
    event.setText("Historical");
    buf = event.createByteArray();
    event2 = EventOnPlanet.createEventOnPlanet(buf);
    assertEquals(EventType.PLANET_CONQUERED, event2.getType());
    assertEquals(event.getText(), event2.getText());
    assertEquals(event.getName(), event2.getName());
    assertEquals(22, event2.getCoordinate().getX());
    assertEquals(11, event2.getCoordinate().getY());
    assertEquals(2, event2.getPlayerIndex());
  }

}
