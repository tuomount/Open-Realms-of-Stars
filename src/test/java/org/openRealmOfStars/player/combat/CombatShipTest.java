package org.openRealmOfStars.player.combat;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipHull;

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
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getName()).thenReturn("Test hull");
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getName()).thenReturn("Test ship Mk1");
    Mockito.when(ship.getTacticSpeed()).thenReturn(2);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getInitiative()).thenReturn(10);
    Mockito.when(ship.getHullPoints()).thenReturn(8);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(8);
    Mockito.when(ship.getShield()).thenReturn(0);
    Mockito.when(ship.getTotalShield()).thenReturn(2);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    CombatShip combatShip = new CombatShip(ship, info, 5, 3, false, null);
    assertEquals(ship, combatShip.getShip());
    assertEquals(info, combatShip.getPlayer());
    assertEquals(5, combatShip.getX());
    assertEquals(3, combatShip.getY());
    assertEquals(2, combatShip.getMovesLeft());
    assertEquals(0, combatShip.getPrivateeredCredits());
    assertEquals(false, combatShip.isFlipY());
    assertEquals("Test ship Mk1 - Test hull\n" +
        "X: 5 Y: 3\n" +
        "Initiative: 10\n" +
        "Moves: 2\n" +
        "Hull points: 8/8\n" +
        "No weapons\n" +
        "shields down\n", combatShip.toString());
    assertEquals("Test ship Mk1 - Test hull\n\n" +
        "Initiative: 10\n" +
        "Moves: 2\n" +
        "Shield: 0/2 Armor: 0/0\n" +
        "Hull points: 8/8\n" +
        "No weapons\n" +
        "shields down\n", combatShip.getDescription());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic2() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getName()).thenReturn("Test hull");
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getName()).thenReturn("Test ship Mk1");
    Mockito.when(ship.getTacticSpeed()).thenReturn(2);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getInitiative()).thenReturn(10);
    Mockito.when(ship.getHullPoints()).thenReturn(8);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(8);
    Mockito.when(ship.getShield()).thenReturn(0);
    Mockito.when(ship.getTotalShield()).thenReturn(0);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    CombatShip combatShip = new CombatShip(ship, info, 5, 3, true, null);
    assertEquals(ship, combatShip.getShip());
    assertEquals(info, combatShip.getPlayer());
    assertEquals(5, combatShip.getX());
    assertEquals(3, combatShip.getY());
    assertEquals(2, combatShip.getMovesLeft());
    assertEquals(0, combatShip.getPrivateeredCredits());
    assertEquals(true, combatShip.isFlipY());
    assertEquals("Test ship Mk1 - Test hull\n" +
        "X: 5 Y: 3\n" +
        "Initiative: 10\n" +
        "Moves: 2\n" +
        "Hull points: 8/8\n" +
        "No weapons\n" +
        "No shields\n", combatShip.toString());
    assertEquals("Test ship Mk1 - Test hull\n\n" +
        "Initiative: 10\n" +
        "Moves: 2\n" +
        "Shield: 0/0 Armor: 0/0\n" +
        "Hull points: 8/8\n" +
        "No weapons\n" +
        "No shields\n", combatShip.getDescription());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarbaseNotDeployed() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getName()).thenReturn("Starbase");
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getName()).thenReturn("Test base Mk1");
    Mockito.when(ship.getTacticSpeed()).thenReturn(2);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getInitiative()).thenReturn(10);
    Mockito.when(ship.getHullPoints()).thenReturn(8);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(8);
    Mockito.when(ship.getShield()).thenReturn(0);
    Mockito.when(ship.getTotalShield()).thenReturn(0);
    Mockito.when(ship.isStarBase()).thenReturn(true);
    Mockito.when(ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)).thenReturn(false);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    CombatShip combatShip = new CombatShip(ship, info, 5, 3, true, null);
    combatShip.setAiShotsLeft(1);
    assertEquals(ship, combatShip.getShip());
    assertEquals(info, combatShip.getPlayer());
    assertEquals(5, combatShip.getX());
    assertEquals(3, combatShip.getY());
    assertEquals(2, combatShip.getMovesLeft());
    assertEquals(1, combatShip.getAiShotsLeft());
    assertEquals(0, combatShip.getPrivateeredCredits());
    assertEquals(true, combatShip.isFlipY());
    combatShip.reInitShipForRound();
    assertEquals(0, combatShip.getAiShotsLeft());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStarbaseDeployed() {
    ShipHull hull = Mockito.mock(ShipHull.class);
    Mockito.when(hull.getName()).thenReturn("Starbase");
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getName()).thenReturn("Test base Mk1");
    Mockito.when(ship.getTacticSpeed()).thenReturn(2);
    Mockito.when(ship.getHull()).thenReturn(hull);
    Mockito.when(ship.getInitiative()).thenReturn(10);
    Mockito.when(ship.getHullPoints()).thenReturn(8);
    Mockito.when(ship.getMaxHullPoints()).thenReturn(8);
    Mockito.when(ship.getShield()).thenReturn(0);
    Mockito.when(ship.getTotalShield()).thenReturn(0);
    Mockito.when(ship.isStarBase()).thenReturn(true);
    Mockito.when(ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)).thenReturn(true);
    Mockito.when(ship.getNumberOfComponents()).thenReturn(1);
    ShipComponent weapon = Mockito.mock(ShipComponent.class);
    Mockito.when(weapon.isWeapon()).thenReturn(true);
    Mockito.when(ship.getComponent(0)).thenReturn(weapon);
    Mockito.when(ship.componentIsWorking(0)).thenReturn(true);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    CombatShip combatShip = new CombatShip(ship, info, 5, 3, true, null);
    combatShip.setAiShotsLeft(0);
    assertEquals(ship, combatShip.getShip());
    assertEquals(info, combatShip.getPlayer());
    assertEquals(5, combatShip.getX());
    assertEquals(3, combatShip.getY());
    assertEquals(0, combatShip.getMovesLeft());
    assertEquals(0, combatShip.getAiShotsLeft());
    assertEquals(0, combatShip.getPrivateeredCredits());
    assertEquals(true, combatShip.isFlipY());
    combatShip.reInitShipForRound();
    assertEquals(1, combatShip.getAiShotsLeft());
    assertEquals(0, combatShip.getMovesLeft());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPrivateered() {
    Ship ship = Mockito.mock(Ship.class);
    Mockito.when(ship.getTacticSpeed()).thenReturn(2);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    CombatShip combatShip = new CombatShip(ship, info, 5, 3, false, null);
    assertEquals(0, combatShip.getPrivateeredCredits());
    combatShip.setPrivateeredCredits(2);
    assertEquals(2, combatShip.getPrivateeredCredits());
    combatShip.setPrivateeredCredits(4);
    assertEquals(4, combatShip.getPrivateeredCredits());
    combatShip.setPrivateeredCredits(0);
    assertEquals(0, combatShip.getPrivateeredCredits());
  }

}
