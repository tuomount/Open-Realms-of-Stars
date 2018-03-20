package org.openRealmOfStars.starMap.history;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.history.event.DiplomaticEvent;

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

}
