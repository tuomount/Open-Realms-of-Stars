package org.openRealmOfStars.player.combat;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipDesign;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.starMap.Coordinate;

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
* Test for Combat
*
*/
public class CombatTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFirstPlayerWin() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(info1, ShipSize.SMALL);
    ShipDesign design2 = ShipGenerator.createBattleShip(info2, ShipSize.SMALL);
    Ship scout1 = new Ship(design1);
    Ship scout2 = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(scout2, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    CombatShip shooter = combat.getCurrentShip();
    combat.nextShip();
    CombatShip target = combat.getCurrentShip();
    ShipComponent weapon = null;
    for (int i = 0; i < 12; i++) {
      ShipComponent comp = shooter.getShip().getComponent(i);
      if (comp != null && comp.isWeapon()) {
        weapon = comp;
        break;
      }
    }
    assertFalse(weapon == null);
    assertEquals(info1, combat.getPlayer1());
    assertEquals(info2, combat.getPlayer2());
    CombatAnimation anim = new CombatAnimation(shooter, target, weapon, -2);
    combat.setAnimation(anim);
    combat.destroyShip(target);
    combat.isCombatOver();
    assertEquals(info1, combat.getWinner());
    combat.handleEndCombat();
    Coordinate coord = new Coordinate(6,5);
    assertEquals(true,
        info1.getFleets().getFirst().getCoordinate().sameAs(coord));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSecondPlayerWin() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(info1, ShipSize.SMALL);
    ShipDesign design2 = ShipGenerator.createBattleShip(info2, ShipSize.SMALL);
    Ship scout1 = new Ship(design1);
    Ship scout2 = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(scout2, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    CombatShip target = combat.getCurrentShip();
    combat.nextShip();
    CombatShip shooter = combat.getCurrentShip();
    ShipComponent weapon = null;
    for (int i = 0; i < 12; i++) {
      ShipComponent comp = shooter.getShip().getComponent(i);
      if (comp != null && comp.isWeapon()) {
        weapon = comp;
        break;
      }
    }
    assertFalse(weapon == null);
    assertEquals(info1, combat.getPlayer1());
    assertEquals(info2, combat.getPlayer2());
    CombatAnimation anim = new CombatAnimation(shooter, target, weapon, -2);
    combat.setAnimation(anim);
    combat.destroyShip(target);
    combat.isCombatOver();
    assertEquals(info2, combat.getWinner());
    combat.handleEndCombat();
    // Defending player does not move
    Coordinate coord = new Coordinate(6,5);
    assertEquals(true,
        info2.getFleets().getFirst().getCoordinate().sameAs(coord));
    assertEquals(true,combat.getCombatCoordinates().sameAs(coord));
  }

}
