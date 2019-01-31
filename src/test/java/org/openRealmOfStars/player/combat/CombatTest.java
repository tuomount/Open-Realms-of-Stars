package org.openRealmOfStars.player.combat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017, 2018, 2019  Tuomo Untinen
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
  public void testNoCombatShips() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(
        info1, ShipSize.SMALL, false);
    ShipDesign design2 = ShipGenerator.createBattleShip(
        info2, ShipSize.SMALL, false);
    Ship scout1 = new Ship(design1);
    Ship scout2 = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(scout2, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    CombatShip ship = combat.getCurrentShip();
    combat.escapeShip(ship);
    ship = combat.getCurrentShip();
    combat.escapeShip(ship);
    assertEquals(null, combat.getCurrentShip());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFirstPlayerWin() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(
        info1, ShipSize.SMALL, false);
    ShipDesign design2 = ShipGenerator.createBattleShip(
        info2, ShipSize.SMALL, false);
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
    combat.nextShip(null);
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
  public void testWormHole() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(
        info1, ShipSize.SMALL, false);
    ShipDesign design2 = ShipGenerator.createBattleShip(
        info2, ShipSize.SMALL, false);
    Ship scout1 = new Ship(design1);
    Ship scout2 = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(scout2, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    assertEquals(null, combat.getWormHoleCoordinate());
    combat.createWormHole();
    assertNotEquals(null, combat.getWormHoleCoordinate());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testWormHole2() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(
        info1, ShipSize.SMALL, false);
    ShipDesign design2 = ShipGenerator.createBattleShip(
        info2, ShipSize.SMALL, false);
    Ship scout1 = new Ship(design1);
    Ship scout2 = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(scout2, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    assertEquals(null, combat.getWormHoleCoordinate());
    int rounds = combat.getTimerForWormHole() + 1;
    for (int i = 0; i < rounds; i++) {
      combat.endRound(null);
    }
    assertNotEquals(null, combat.getWormHoleCoordinate());
    assertEquals(0, combat.getTimerForWormHole());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRealCombat() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    info1.setEmpireName("Terran alliance");
    info2.setEmpireName("Spork empire");
    ShipDesign design1 = ShipGenerator.createBattleShip(
        info1, ShipSize.SMALL, false);
    ShipDesign design2 = ShipGenerator.createColony(info2, false);
    Ship scout1 = new Ship(design1);
    Ship colony = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(colony, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    assertEquals(fleet2, combat.getDefendingFleet());
    combat.doFastCombat();
    assertEquals("Terran alliance attacked against Spork empire "
        + "with  single ship against one ship. Combat was victorious"
        + " for Terran alliance. Single ship survived in victorious "
        + "fleet. Loser's fleet was totally destroyed!"
        ,combat.getCombatEvent().getText());
    assertEquals(info1, combat.getWinner());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRealCombatWithOrbitalDefense() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    info1.setEmpireName("Terran alliance");
    info2.setEmpireName("Spork empire");
    info2.getTechList().addTech(TechFactory.createDefenseTech("Shield Mk2", 2));
    ShipDesign design1 = ShipGenerator.createBattleShip(
        info1, ShipSize.SMALL, false);
    ShipDesign design2 = ShipGenerator.createBattleShip(
        info2, ShipSize.SMALL, false);
    Ship scout1 = new Ship(design1);
    Ship scout2 = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(scout2, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test I");
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(coord.sameAs((Coordinate)Mockito.any())).thenReturn(true);
    Mockito.when(planet.getCoordinate()).thenReturn(coord);
    Mockito.when(planet.howManyBuildings("Orbital defense grid")).thenReturn(1);
    combat.setPlanet(planet);
    combat.doFastCombat();
    assertEquals("Terran alliance attacked against Spork empire with"
        + "  single ship against one ship. Combat happened in orbit of Test I."
        + " Combat was victorious for Spork empire. Single ship survived in"
        + " victorious fleet. Loser's fleet was totally destroyed!"
        ,combat.getCombatEvent().getText());
    assertEquals(info2, combat.getWinner());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRealCombat2() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(
        info1, ShipSize.SMALL, false);
    ShipDesign design2 = ShipGenerator.createColony(info2, false);
    Ship scout1 = new Ship(design1);
    Ship colony = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(colony, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    combat.createWormHole();
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
    ShipDesign design1 = ShipGenerator.createBattleShip(info1, ShipSize.SMALL, false);
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
  public void testRealCombatWithPrivateer() {
    DiceGenerator.initializeGenerators(5L, 1234);
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    info1.getTechList().addTech(TechFactory.createHullTech(
        TechFactory.HULL_TECH_LEVEL5_NAMES[2], 5));
    info1.getTechList().addTech(TechFactory.createDefenseTech(
        "Shield Mk3", 3));
    info1.getTechList().addTech(TechFactory.createCombatTech(
        "Photon torpedo Mk2", 2));
    info1.getTechList().addTech(TechFactory.createPropulsionTech(
        "Nuclear drive Mk2", 3));
    ShipDesign design1 = ShipGenerator.createPrivateerShip(
        info1, ShipSize.MEDIUM);
    ShipDesign design2 = ShipGenerator.createFreighter(info2);
    Ship privateer1 = new Ship(design1);
    Ship colony = new Ship(design2);
    colony.setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, true);
    Fleet fleet1 = new Fleet(privateer1, 5, 5);
    Fleet fleet2 = new Fleet(colony, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    combat.setTimerForWormHole(10000);
    assertEquals(0, info1.getTotalCredits());
    combat.doFastCombat(false);
    assertEquals(info1, combat.getWinner());
    assertEquals(3, info1.getTotalCredits());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRealCombatWithPrivateers() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    info1.getTechList().addTech(TechFactory.createHullTech(
        TechFactory.HULL_TECH_LEVEL5_NAMES[2], 5));
    info1.getTechList().addTech(TechFactory.createDefenseTech(
        "Shield Mk3", 3));
    info1.getTechList().addTech(TechFactory.createCombatTech(
        "Photon torpedo Mk3", 3));
    info1.getTechList().addTech(TechFactory.createPropulsionTech(
        "Nuclear drive Mk2", 3));
    ShipDesign design1 = ShipGenerator.createPrivateerShip(
        info1, ShipSize.MEDIUM);
    ShipDesign design2 = ShipGenerator.createFreighter(info2);
    ShipDesign design3 = ShipGenerator.createBattleShip(
        info2, ShipSize.SMALL, false);
    Ship privateer1 = new Ship(design1);
    Ship privateer2 = new Ship(design1);
    Ship privateer3 = new Ship(design1);
    Ship colony = new Ship(design2);
    Ship scout = new Ship(design3);
    colony.setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, true);
    Fleet fleet1 = new Fleet(privateer1, 5, 5);
    fleet1.addShip(privateer2);
    fleet1.addShip(privateer3);
    Fleet fleet2 = new Fleet(colony, 6, 5);
    fleet2.addShip(scout);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    // Setting long time for wormhole appear
    // Making sure that colony cannot escape
    combat.setTimerForWormHole(200);
    assertEquals(0, info1.getTotalCredits());
    combat.doFastCombat(false);
    assertEquals(info1, combat.getWinner());
    assertEquals(3, info1.getTotalCredits());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSecondPlayerWin() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(
        info1, ShipSize.SMALL, false);
    ShipDesign design2 = ShipGenerator.createBattleShip(
        info2, ShipSize.SMALL, false);
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
    combat.nextShip(null);
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
  public void testStarbaseFleetLose() {
    PlayerInfo defender = new PlayerInfo(SpaceRace.SPACE_PIRATE);
    PlayerInfo attacker = new PlayerInfo(SpaceRace.SPORKS);
    attacker.getTechList().addTech(TechFactory.createCombatTech(
        "Photon torpedo Mk8", 8));
    attacker.getTechList().addTech(TechFactory.createDefenseTech(
        "Shield Mk8", 8));
    attacker.getTechList().addTech(TechFactory.createPropulsionTech(
        "Tachyon source Mk2", 5));
    attacker.getTechList().addTech(TechFactory.createHullTech(
        "Corvette Mk3", 8));
    ShipDesign design1 = ShipGenerator.createStarbase(defender, ShipSize.SMALL);
    ShipDesign design2 = ShipGenerator.createBattleShip(
        attacker, ShipSize.SMALL, false);
    Ship starbase = new Ship(design1);
    starbase.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
    Ship starbase2 = new Ship(design1);
    starbase2.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
    Ship scout2 = new Ship(design2);
    Fleet defenderFleet = new Fleet(starbase, 5, 5);
    Fleet starbaseFleet = new Fleet(starbase2, 5, 5);
    Fleet attackerFleet = new Fleet(scout2, 6, 5);
    defender.getFleets().add(defenderFleet);
    defender.getFleets().add(starbaseFleet);
    attacker.getFleets().add(attackerFleet);
    Combat combat = new Combat(attackerFleet, defenderFleet, attacker,
        defender);
    CombatShip first = combat.getCurrentShip();
    int deployedBases = 0;
    for (int i = 0; i < 3; i++) {
      CombatShip ship = combat.getCurrentShip();
      if (ship.getShip().getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
        deployedBases++;
      }
      if (i == 0) {
        assertEquals(first, ship);
      } else {
        assertNotEquals(first, ship);
      }
      combat.nextShip(null);
    }
    assertEquals(2, deployedBases);
    combat.doFastCombat();
    assertEquals(attacker, combat.getWinner());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testStarbaseFleetWins() {
    PlayerInfo defender = new PlayerInfo(SpaceRace.SPACE_PIRATE);
    PlayerInfo attacker = new PlayerInfo(SpaceRace.SPORKS);
    defender.getTechList().addTech(TechFactory.createCombatTech(
        "Photon torpedo Mk5", 5));
    defender.getTechList().addTech(TechFactory.createDefenseTech(
        "Shield Mk5", 5));
    defender.getTechList().addTech(TechFactory.createPropulsionTech(
        "Tachyon source Mk2", 5));
    ShipDesign design1 = ShipGenerator.createStarbase(defender, ShipSize.SMALL);
    ShipDesign design2 = ShipGenerator.createBattleShip(
        attacker, ShipSize.SMALL, false);
    Ship starbase = new Ship(design1);
    starbase.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
    Ship starbase2 = new Ship(design1);
    starbase2.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
    Ship scout2 = new Ship(design2);
    Fleet defenderFleet = new Fleet(starbase, 5, 5);
    Fleet starbaseFleet = new Fleet(starbase2, 5, 5);
    Fleet attackerFleet = new Fleet(scout2, 6, 5);
    defender.getFleets().add(defenderFleet);
    defender.getFleets().add(starbaseFleet);
    attacker.getFleets().add(attackerFleet);
    Combat combat = new Combat(attackerFleet, defenderFleet, attacker,
        defender);
    CombatShip first = combat.getCurrentShip();
    int deployedBases = 0;
    for (int i = 0; i < 3; i++) {
      CombatShip ship = combat.getCurrentShip();
      if (ship.getShip().getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
        deployedBases++;
      }
      if (i == 0) {
        assertEquals(first, ship);
      } else {
        assertNotEquals(first, ship);
      }
      combat.nextShip(null);
    }
    assertEquals(2, deployedBases);
    combat.doFastCombat();
    assertEquals(defender, combat.getWinner());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHumanPlayer() {
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN);
    info1.setHuman(true);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS);
    ShipDesign design1 = ShipGenerator.createBattleShip(
        info1, ShipSize.SMALL, false);
    ShipDesign design2 = ShipGenerator.createBattleShip(
        info2, ShipSize.SMALL, false);
    Ship scout1 = new Ship(design1);
    Ship scout2 = new Ship(design2);
    Fleet fleet1 = new Fleet(scout1, 5, 5);
    Fleet fleet2 = new Fleet(scout2, 6, 5);
    info1.getFleets().add(fleet1);
    info2.getFleets().add(fleet2);
    Combat combat = new Combat(fleet1, fleet2, info1, info2);
    assertEquals(true, combat.isHumanPlayer());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPrivateering() {
    PlayerInfo privateer = Mockito.mock(PlayerInfo.class);
    PlayerInfo target = Mockito.mock(PlayerInfo.class);
    FleetList list = Mockito.mock(FleetList.class);
    Mockito.when(target.getFleets()).thenReturn(list);

    Ship privateerShip = Mockito.mock(Ship.class);
    Mockito.when(privateerShip.isPrivateeringShip()).thenReturn(true);
    Ship targetShip = Mockito.mock(Ship.class);

    CombatShip combatPirate = Mockito.mock(CombatShip.class);
    Mockito.when(combatPirate.getShip()).thenReturn(privateerShip);
    CombatShip combatTarget = Mockito.mock(CombatShip.class);
    Mockito.when(combatTarget.getShip()).thenReturn(targetShip);

    Fleet attackerFleet = new Fleet(privateerShip, 5, 5);
    Fleet defenderFleet = new Fleet(targetShip, 6, 6);
    Combat combat = new Combat(attackerFleet, defenderFleet, privateer, target);
    ShipDamage damage = combat.doPrivateering(privateer, combatPirate, target,
        combatTarget);
    assertEquals("Ship has no cargo!", damage.getMessage());
    Mockito.when(targetShip.getCargoType()).thenReturn(Ship.CARGO_TYPE_POPULATION);
    damage = combat.doPrivateering(privateer, combatPirate, target,
        combatTarget);
    assertEquals("Murderered colonists and stole valuables!", damage.getMessage());
    Mockito.when(targetShip.getCargoType()).thenReturn(Ship.CARGO_TYPE_TRADE_GOODS);
    damage = combat.doPrivateering(privateer, combatPirate, target,
        combatTarget);
    assertEquals("Privateered trade goods from trade ship!", damage.getMessage());
    Mockito.when(targetShip.getCargoType()).thenReturn(Ship.CARGO_TYPE_TROOPS);
    damage = combat.doPrivateering(privateer, combatPirate, target,
        combatTarget);
    assertEquals("Ship is full of troops and cannot be raided!", damage.getMessage());
    Mockito.when(targetShip.getCargoType()).thenReturn(Ship.CARGO_TYPE_METAL);
    Mockito.when(privateerShip.getFreeCargoMetal()).thenReturn(0);
    damage = combat.doPrivateering(privateer, combatPirate, target,
        combatTarget);
    assertEquals("Cargo cannot be fitted in your ship!", damage.getMessage());
    Mockito.when(privateerShip.getFreeCargoMetal()).thenReturn(5);
    Mockito.when(targetShip.getMetal()).thenReturn(3);
    damage = combat.doPrivateering(privateer, combatPirate, target,
        combatTarget);
    assertEquals("All valuable metal has been stolen!", damage.getMessage());
    Mockito.when(privateerShip.getFreeCargoMetal()).thenReturn(3);
    Mockito.when(targetShip.getMetal()).thenReturn(5);
    damage = combat.doPrivateering(privateer, combatPirate, target,
        combatTarget);
    assertEquals("You raided 3 units of metal!", damage.getMessage());
  }

}
