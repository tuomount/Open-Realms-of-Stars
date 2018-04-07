package org.openRealmOfStars.starMap.history;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.history.event.CombatEvent;
import org.openRealmOfStars.starMap.history.event.CultureEvent;
import org.openRealmOfStars.starMap.history.event.DiplomaticEvent;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.history.event.GalacticEvent;
import org.openRealmOfStars.starMap.history.event.PlayerStartEvent;

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
* Historical turn test
*
*/
public class HistoryTurnTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    DiplomaticEvent event = Mockito.mock(DiplomaticEvent.class);
    HistoryTurn historyTurn = new HistoryTurn(10);
    assertEquals(10, historyTurn.getTurn());
    assertEquals(0, historyTurn.getNumberOfEvents());
    historyTurn.addEvent(event);
    assertEquals(1, historyTurn.getNumberOfEvents());
    assertEquals(event, historyTurn.getEvent(0));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEncodingAndParsing() throws IOException {
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.getX()).thenReturn(22);
    Mockito.when(coord.getY()).thenReturn(11);
    CombatEvent combatEvent = new CombatEvent(coord);
    combatEvent.setText("Historical");
    combatEvent.setPlanetName("Test I");
    CultureEvent cultureEvent = new CultureEvent(coord, 0);
    DiplomaticEvent diplomaticEvent = new DiplomaticEvent(coord);
    diplomaticEvent.setText("Historical");
    diplomaticEvent.setPlanetName("Test planet");
    EventOnPlanet colonizedEvent = new EventOnPlanet(EventType.PLANET_COLONIZED, coord,
        "Test planet", 0);
    colonizedEvent.setText("Historical");
    EventOnPlanet conqueredEvent = new EventOnPlanet(EventType.PLANET_CONQUERED, coord,
        "Test planet", 0);
    conqueredEvent.setText("Historical");
    GalacticEvent event = new GalacticEvent("Test text");
    PlayerStartEvent startEvent = new PlayerStartEvent(coord, "Test I", 1);
    HistoryTurn turn = new HistoryTurn(2);
    turn.addEvent(combatEvent);
    turn.addEvent(cultureEvent);
    turn.addEvent(colonizedEvent);
    turn.addEvent(conqueredEvent);
    turn.addEvent(diplomaticEvent);
    turn.addEvent(event);
    turn.addEvent(startEvent);
    assertEquals(6, turn.getNumberOfTextualEvents());
    assertEquals(1, turn.getEventNumber(0));
    assertEquals(1, turn.getEventNumber(1));
    assertEquals(2, turn.getEventNumber(2));
    assertEquals(3, turn.getEventNumber(3));
    assertEquals(4, turn.getEventNumber(4));
    assertEquals(5, turn.getEventNumber(5));
    assertEquals(6, turn.getEventNumber(6));
    byte[] buffer = turn.createByteArray();
    ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
    HistoryTurn turn2 = HistoryTurn.parseHistoryTurn(bis);
    assertEquals(turn.getTurn(), turn2.getTurn());
    assertEquals(turn.getNumberOfEvents(), turn2.getNumberOfEvents());
    for (int i = 0; i < turn.getNumberOfEvents(); i++) {
      assertEquals(turn.getEvent(i).getType(), turn2.getEvent(i).getType());
    }
  }

}
