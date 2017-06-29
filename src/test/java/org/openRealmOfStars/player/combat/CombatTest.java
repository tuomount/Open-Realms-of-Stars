package org.openRealmOfStars.player.combat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;

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
    assertEquals(false, combat.isHumanPlayer());
    CombatShip shooter = combat.getCurrentShip();
    assertEquals(0, shooter.getShip().getExperience());
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
    assertEquals(1, shooter.getShip().getExperience());
    Coordinate coord = new Coordinate(6,5);
    assertEquals(true,
        info1.getFleets().getFirst().getCoordinate().sameAs(coord));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRealCombat() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(info1, ShipSize.SMALL);
    ShipDesign design2 = ShipGenerator.createColony(info2, false);
    Ship scout1 = new Ship(design1);
    Ship colony = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(colony, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    combat.doFastCombat();
    assertEquals(info1, combat.getWinner());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRealCombatDraw() {
    TechList techList = new TechList();
    Tech tech = TechFactory.createCombatTech("Laser Mk1", 1);
    if (tech != null) {
      techList.addTech(tech);
    }
    tech = TechFactory.createDefenseTech("Shield Mk1", 1);
    if (tech != null) {
      techList.addTech(tech);
    }
    tech = TechFactory.createHullTech("Colony", 1);
    if (tech != null) {
      techList.addTech(tech);
    }
    tech = TechFactory.createHullTech("Scout Mk1", 1);
    if (tech != null) {
      techList.addTech(tech);
    }
    tech = TechFactory.createPropulsionTech("Ion drive Mk1", 1);
    if (tech != null) {
      techList.addTech(tech);
    }
    tech = TechFactory.createPropulsionTech("Fission source Mk1", 1);
    if (tech != null) {
      techList.addTech(tech);
    }
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    info1.setTechList(techList);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    info2.setTechList(techList);
    ShipDesign design1 = ShipGenerator.createScout(info1);
    ShipDesign design2 = ShipGenerator.createScout(info2);
    Ship colony1 = new Ship(design1);
    Ship colony2 = new Ship(design2);
    Fleet fleet1 = new Fleet(colony1, 5, 5);
    Fleet fleet2 = new Fleet(colony2, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    combat.doFastCombat();
    assertEquals(null, combat.getWinner());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRealCombatOnPlanet() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(info1, ShipSize.SMALL);
    ShipDesign design2 = ShipGenerator.createColony(info2, false);
    Ship scout1 = new Ship(design1);
    Ship colony = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(colony, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    Planet planet = new Planet(new Coordinate(6, 5), "Test", 1, false);
    planet.setPlanetOwner(1, info2);
    combat.setPlanet(planet);
    combat.doFastCombat();
    assertEquals(info1, combat.getWinner());
    assertNotEquals(null, info2.getMissions().getMissionForPlanet("Test I",
        MissionPhase.PLANNING));
    
    Mission mission = info2.getMissions().getMissionForPlanet("Test I",
        MissionPhase.PLANNING);
    mission.setPhase(MissionPhase.EXECUTING);
    assertEquals(null, info2.getMissions().getMissionForPlanet("Test I",
        MissionPhase.PLANNING));
    fleet2 = new Fleet(colony, 6, 5);
    info2.getFleets().add(fleet2);
    combat = new Combat(fleet1, fleet2, info1, info2);
    combat.setPlanet(planet);
    combat.doFastCombat();
    assertEquals(info1, combat.getWinner());
    assertNotEquals(null, info2.getMissions().getMissionForPlanet("Test I",
        MissionPhase.PLANNING));
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
    assertEquals(null, combat.getPlanet());
    Planet planet = new Planet(new Coordinate(6, 5), "Test", 1, false);
    combat.setPlanet(planet);
    assertEquals(planet, combat.getPlanet());
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

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHumanPlayer() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    info1.setHuman(true);
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
    assertEquals(true, combat.isHumanPlayer());
  }

}
