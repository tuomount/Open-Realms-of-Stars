package org.openRealmOfStars.player.combat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.gui.mapPanel.MapPanel;
import org.openRealmOfStars.player.ship.ShipDamage;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Test for CombatMapMouseListener
*
*/
public class CombatMapMouseListenerTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFirstPlayerWin() {
    Combat combat = Mockito.mock(Combat.class);
    MapPanel panel = Mockito.mock(MapPanel.class);
    BattleInfoPanel infoPanel = Mockito.mock(BattleInfoPanel.class);
    CombatMapMouseListener listener = new CombatMapMouseListener(combat,
        panel, infoPanel);
    CombatShip ship = Mockito.mock(CombatShip.class);
    listener.setActiveShip(ship);
    assertEquals(ship, listener.getActiveShip());
    listener.setComponentUse(1);
    assertEquals(1, listener.getComponentUse());
    listener.setRoutePlanning(true);
    assertEquals(true, listener.isRoutePlanning());
    ShipDamage damage = Mockito.mock(ShipDamage.class);
    listener.setShipDamage(damage);
    assertEquals(damage, listener.getShipDamage());
  }

}
