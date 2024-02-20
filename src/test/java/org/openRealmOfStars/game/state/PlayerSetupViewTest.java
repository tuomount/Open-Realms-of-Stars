package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2024 Tuomo Untinen
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
 */

import static org.junit.Assert.assertEquals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.player.AiDifficulty;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.starMap.GalaxyConfig;

/**
*
* Test for PlayerSetupView class
*
*/
public class PlayerSetupViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    GalaxyConfig config = Mockito.mock(GalaxyConfig.class);
    SpaceRace race = SpaceRaceFactory.createOne("HUMANS");
    GovernmentType gov = GovernmentType.UNION;
    AiDifficulty dif = AiDifficulty.NORMAL;
    Mockito.when(config.getRace(Mockito.anyInt())).thenReturn(race);
    Mockito.when(config.getPlayerGovernment(Mockito.anyInt())).thenReturn(gov);
    Mockito.when(config.getDifficulty(Mockito.anyInt())).thenReturn(dif);
    Mockito.when(config.getDifficultyLevel()).thenReturn(dif);
    ActionListener listener = Mockito.mock(ActionListener.class);
    PlayerSetupView view = new PlayerSetupView(config, listener);
    assertEquals(config, view.getConfig());
    view.getNamesToConfig();
    ActionEvent action = Mockito.mock(ActionEvent.class);
    String command = GameCommands.COMMAND_GALAXY_SETUP + "1";
    Mockito.when(action.getActionCommand()).thenReturn(command);
    view.handleActions(action);
  }

}
