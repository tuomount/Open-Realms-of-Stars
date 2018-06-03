package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.starMap.GalaxyConfig;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2016  Tuomo Untinen
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
* Test for GalaxyCreationView class
*
*/
public class GalaxyCreationViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasic() {
    GalaxyConfig config = new GalaxyConfig();
    ActionListener listener = Mockito.mock(ActionListener.class);
    GalaxyCreationView view = new GalaxyCreationView(config, listener);
    ActionEvent event = Mockito.mock(ActionEvent.class);
    Mockito.when(event.getActionCommand()).thenReturn(GameCommands
        .COMMAND_GALAXY_SETUP);
    view.handleActions(event);
    config = view.getConfig();
    assertEquals(75, config.getSizeX());
    assertEquals(75, config.getSizeY());
    assertEquals(1, config.getScoreLimitCulture());
  }

}
