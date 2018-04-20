package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.history.History;
import org.openRealmOfStars.starMap.history.HistoryTurn;
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
* Test for HistoryView class
*
*/
public class HistoryViewTest {

  /**
   * This just test that there are no NPE or exceptions.
   */
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testView() {
    StarMap map = Mockito.mock(StarMap.class);
    History history = Mockito.mock(History.class);
    HistoryTurn turn = Mockito.mock(HistoryTurn.class);
    Mockito.when(turn.getNumberOfTextualEvents()).thenReturn(0);
    Mockito.when(history.getLatestTurn()).thenReturn(turn);
    Mockito.when(history.getByIndex(Mockito.anyInt())).thenReturn(turn);
    Mockito.when(history.numberOfTurns()).thenReturn(5);
    Mockito.when(map.getHistory()).thenReturn(history);
    ActionListener listener = Mockito.mock(ActionListener.class);
    HistoryView view = new HistoryView(map, listener);
    assertEquals(0, view.getTurn());
    assertEquals(0, view.getEventNumber());
    view.updateTurnLabel();
    view.updateTextArea();
    ActionEvent event = Mockito.mock(ActionEvent.class);
    Mockito.when(event.getActionCommand()).thenReturn(GameCommands
        .COMMAND_ANIMATION_TIMER);
    view.handleAction(event);
    Mockito.when(event.getActionCommand()).thenReturn(GameCommands
        .COMMAND_NEXT_YEAR);
    view.handleAction(event);
    assertEquals(1, view.getTurn());
    Mockito.when(event.getActionCommand()).thenReturn(GameCommands
        .COMMAND_PREV_YEAR);
    view.handleAction(event);
    assertEquals(0, view.getTurn());
  }

}
