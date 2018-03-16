package org.openRealmOfStars.starMap.history.event;

import static org.junit.Assert.*;

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
        "Test planet");
    assertEquals(EventType.PLANET_COLONIZED, event.getType());
    assertEquals(coord, event.getCoordinate());
    assertEquals("Test planet", event.getName());
    assertEquals("", event.getText());
    event.setText("Historical stuff");
    assertEquals("Historical stuff", event.getText());
    event = new EventOnPlanet(EventType.PLANET_CONQUERED, coord,
        "Test planet");
    assertEquals(EventType.PLANET_CONQUERED, event.getType());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFail() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    EventOnPlanet event = new EventOnPlanet(EventType.CULTURE_CHANGE, coord,
        "Test planet");
    assertEquals(EventType.CULTURE_CHANGE, event.getType());
  }

}
