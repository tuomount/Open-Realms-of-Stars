package org.openRealmOfStars.player.combat;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.Ship;

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
* Test for CombatShip
*
*/
public class CombatShipTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTacticSpeed()).thenReturn(2);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    CombatShip combatShip = new CombatShip(ship, info, 5, 3, false);
    assertEquals(ship, combatShip.getShip());
    assertEquals(info, combatShip.getPlayer());
    assertEquals(5, combatShip.getX());
    assertEquals(3, combatShip.getY());
    assertEquals(2, combatShip.getMovesLeft());
    assertEquals(0, combatShip.getPrivateeredCredits());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPrivateered() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTacticSpeed()).thenReturn(2);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    CombatShip combatShip = new CombatShip(ship, info, 5, 3, false);
    assertEquals(0, combatShip.getPrivateeredCredits());
    combatShip.setPrivateeredCredits(2);
    assertEquals(2, combatShip.getPrivateeredCredits());
    combatShip.setPrivateeredCredits(4);
    assertEquals(4, combatShip.getPrivateeredCredits());
    combatShip.setPrivateeredCredits(0);
    assertEquals(0, combatShip.getPrivateeredCredits());
  }

}
