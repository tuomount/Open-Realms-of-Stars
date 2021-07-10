package org.openRealmOfStars.gui.infopanel;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.buttons.SpaceButton;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.player.PlayerInfo;

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
* Test for MapInfoPanel
*
*/
public class MapInfoPanelTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testButton() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    MapInfoPanel panel = new MapInfoPanel(listener);
    SpaceButton btn = panel.getFixTradeBtn();
    assertEquals("Fix fleet", btn.getText());
    panel.disableFixTradeBtn();
    assertEquals(false, panel.getFixTradeBtn().isEnabled());
    panel.setFixBtn();
    btn = panel.getFixTradeBtn();
    assertEquals("Fix fleet", btn.getText());
    assertEquals(true, btn.isEnabled());
    panel.setTradeBtn();
    assertEquals("Trade", panel.getFixTradeBtn().getText());
    assertEquals(true, panel.getFixTradeBtn().isEnabled());
    panel.disableFixTradeBtn();
    assertEquals(false, panel.getFixTradeBtn().isEnabled());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNulls() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    MapInfoPanel panel = new MapInfoPanel(listener);
    assertEquals(null, panel.getFleetShowing());
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getDescription()).thenReturn("");
    panel.showTile(tile);
    panel.showPlanet(null, false, null);
    PlayerInfo owner = Mockito.mock(PlayerInfo.class);
    panel.showFleet(null, owner, false);
    assertEquals(owner, panel.getFleetOwner());
  }

}
