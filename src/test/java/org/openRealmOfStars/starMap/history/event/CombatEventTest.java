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
* Combat event test
*
*/
public class CombatEventTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Coordinate coord = Mockito.mock(Coordinate.class);
    CombatEvent event = new CombatEvent(coord, 0);
    assertEquals(EventType.SPACE_COMBAT, event.getType());
    assertEquals(0, event.getPlayerIndex());
    assertEquals(coord, event.getCoordinate());
    assertEquals(null, event.getPlanetName());
    assertEquals("", event.getText());
    event.setPlanetName("Test planet");
    assertEquals("Test planet", event.getPlanetName());
    event.setText("Text");
    assertEquals("Text", event.getText());
  }

}
