package org.openRealmOfStars.game.States;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.tech.TechList;


/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Tuomo Untinen
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
* Test for ResearchView class
*
*/
public class ResearchViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasic() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    TechList list = new TechList(info.getRace());
    Mockito.when(info.getTechList()).thenReturn(list);
    int totalResearch = 5;
    String focusTech = null;
    ActionListener listener = Mockito.mock(ActionListener.class);
    ResearchView view = new ResearchView(info, totalResearch, focusTech, 300,
        listener);
    view.updatePanel();
    ActionEvent event = Mockito.mock(ActionEvent.class);
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_COMBAT_INFO);
    view.handleAction(event);
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_DEFENSE_INFO);
    view.handleAction(event);
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_HULL_INFO);
    view.handleAction(event);
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_IMPROVEMENT_INFO);
    view.handleAction(event);
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_PROPULSION_INFO);
    view.handleAction(event);
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_ELECTRONICS_INFO);
    view.handleAction(event);
  }

}
