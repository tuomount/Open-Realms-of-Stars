package org.openRealmOfStars.starMap.history;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.history.event.GalacticEvent;

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
* Historical information about game being played
*
*/
public class HistoryTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    History history = new History();
    assertEquals(0, history.numberOfTurns());
    assertEquals(null, history.getByIndex(0));
    history.addTurn(1);
    assertEquals(1, history.numberOfTurns());
    GalacticEvent event = Mockito.mock(GalacticEvent.class);
    history.addEvent(event);
    assertEquals(event, history.getByIndex(0).getEvent(0));
    assertEquals(history.getByIndex(0), history.getLatestTurn());
    GalacticEvent event2 = Mockito.mock(GalacticEvent.class);
    history.addEvent(event2);
    assertEquals(1, history.numberOfTurns());
    assertEquals(event2, history.getByIndex(0).getEvent(1));
    assertEquals(history.getByIndex(0), history.getLatestTurn());
    history.addTurn(2);
    assertEquals(2, history.numberOfTurns());
  }

}
