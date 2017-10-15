package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;
import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
* Test for ShipView class
*
*/
public class ShipViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void test() throws IOException {
    ActionListener listener = Mockito.mock(ActionListener.class);
    PlayerInfo player = new PlayerInfo(SpaceRace.CENTAURS, 2, 0);
    ShipView shipView = new ShipView(player, listener);
    shipView.updateList();
    assertEquals(null, shipView.getSelectedShip());
    assertEquals(null, shipView.getSelectedStat());
    assertEquals(player, shipView.getPlayerInfo());
    assertEquals(false, shipView.isCopyClicked());
    shipView.setCopyClicked(true);
    assertEquals(true, shipView.isCopyClicked());
  }

}
