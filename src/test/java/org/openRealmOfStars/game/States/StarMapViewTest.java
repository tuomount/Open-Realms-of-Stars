package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.gui.mapPanel.PopupPanel;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2018 Tuomo Untinen
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
* Test for StarMapView
*
*/
public class StarMapViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testCreatingAndActions() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    Game game = new Game(false);
    game.setLoadedGame(starMap);
    StarMapView view = new StarMapView(game.getStarMap(), game.getPlayers(),
        game);
    game.setStarMapView(view);
    view.setReadyToMove(false);
    assertEquals(false, view.getReadyToMove());
    view.setReadyToMove(true);
    assertEquals(true, view.getReadyToMove());
    view.setAutoFocus(false);
    assertEquals(false, view.isAutoFocus());
    view.setAutoFocus(true);
    assertEquals(true, view.isAutoFocus());
    ActionEvent arg0 = Mockito.mock(ActionEvent.class);
    Mockito.when(arg0.getActionCommand()).thenReturn(
        GameCommands.COMMAND_ANIMATION_TIMER);
    view.handleActions(arg0);
    Mockito.when(arg0.getActionCommand()).thenReturn(
        GameCommands.COMMAND_NEXT_MSG);
    assertEquals(0, game.getPlayers().getCurrentPlayerInfo().getMsgList()
        .getCurrentMsgIndex());
    view.handleActions(arg0);
    assertEquals(1, game.getPlayers().getCurrentPlayerInfo().getMsgList()
        .getCurrentMsgIndex());
    Mockito.when(arg0.getActionCommand()).thenReturn(
        GameCommands.COMMAND_PREV_MSG);
    view.handleActions(arg0);
    assertEquals(0, game.getPlayers().getCurrentPlayerInfo().getMsgList()
        .getCurrentMsgIndex());
    assertEquals(null, view.getPopup());
    PopupPanel popup = Mockito.mock(PopupPanel.class);
    view.setPopup(popup);
    assertEquals(popup, view.getPopup());
  }

}
