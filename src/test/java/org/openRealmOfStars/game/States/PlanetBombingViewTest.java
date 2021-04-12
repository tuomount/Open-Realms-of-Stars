package org.openRealmOfStars.game.States;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;
/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2019,2021 Tuomo Untinen
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
 * Test for PlanetBombingView class
 *
 */
public class PlanetBombingViewTest {
    private PlanetBombingView planetBombingView;

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Planet planet = Mockito.mock(Planet.class);
    Fleet fleet = Mockito.mock(Fleet.class);
    Ship firstShip = Mockito.mock(Ship.class);
    ShipHull shipHull = Mockito.mock(ShipHull.class);
    Mockito.when(firstShip.getHull()).thenReturn(shipHull);
    Mockito.when(fleet.getFirstShip()).thenReturn(firstShip);
    Mockito.when(fleet.getShips()).thenReturn(new Ship[]{firstShip});
    Mockito.when(fleet.getNumberOfShip()).thenReturn(1);

    PlayerInfo attackerPlayerInfo = Mockito.mock(PlayerInfo.class);
    int attackerPlayerIndex = 0;
    ActionListener listener = Mockito.mock(ActionListener.class);

    planetBombingView = new PlanetBombingView(planet, fleet, 
        attackerPlayerInfo, attackerPlayerIndex, listener);
    assertEquals(planet, planetBombingView.getPlanet());
    Planet planet2 = Mockito.mock(Planet.class);
    planetBombingView.setPlanet(planet2);
    assertEquals(planet2, planetBombingView.getPlanet());
    assertEquals(fleet, planetBombingView.getFleet());
    Fleet fleet2 = Mockito.mock(Fleet.class);
    planetBombingView.setFleet(fleet2);
    assertEquals(fleet2, planetBombingView.getFleet());
    // Just running void method
    planetBombingView.resetComponentUsage();
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBombing() {
    Planet planet = Mockito.mock(Planet.class);

    PlayerInfo attackerPlayerInfo = new PlayerInfo(SpaceRace.SPORKS, 2, 1);
    attackerPlayerInfo.getTechList().addTech(TechFactory.createCombatTech(
        "Orbital bombs Mk1", 3));
    attackerPlayerInfo.getTechList().addTech(TechFactory.createHullTech(
        "Destroyer Mk1", 1));
    Ship ship = null;
    int loop = 0;
    do {
      ShipDesign design = ShipGenerator.createBattleShip(attackerPlayerInfo,
          ShipSize.MEDIUM, true, false);
      ship = new Ship(design);
      loop++;
      // There should be a bomber on first try
      assertEquals(1, loop);
    } while (!ship.hasBombs());
    Fleet fleet = new Fleet(ship, 5, 5);
    int attackerPlayerIndex = 0;
    ActionListener listener = Mockito.mock(ActionListener.class);

    planetBombingView = new PlanetBombingView(planet, fleet, 
        attackerPlayerInfo, attackerPlayerIndex, listener);
    for (int i = 0; i < ship.getNumberOfComponents(); i++) {
       planetBombingView.shipComponentUsage(i);
    }
    int index = planetBombingView.getUsedComponentIndex();
    assertEquals(ShipComponentType.ORBITAL_BOMBS, ship.getComponent(index).getType());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBombingNuke() {
    Planet planet = Mockito.mock(Planet.class);

    PlayerInfo attackerPlayerInfo = new PlayerInfo(SpaceRace.SPORKS, 2, 1);
    attackerPlayerInfo.getTechList().addTech(TechFactory.createCombatTech(
        "Orbital nuke", 4));
    attackerPlayerInfo.getTechList().addTech(TechFactory.createHullTech(
        "Destroyer Mk1", 1));
    Ship ship = null;
    int loop = 0;
    do {
      ShipDesign design = ShipGenerator.createBattleShip(attackerPlayerInfo,
          ShipSize.MEDIUM, true, false);
      ship = new Ship(design);
      loop++;
      // There should be a bomber on first try
      assertEquals(1, loop);
    } while (!ship.hasBombs());
    Fleet fleet = new Fleet(ship, 5, 5);
    int attackerPlayerIndex = 0;
    ActionListener listener = Mockito.mock(ActionListener.class);

    planetBombingView = new PlanetBombingView(planet, fleet, 
        attackerPlayerInfo, attackerPlayerIndex, listener);
    for (int i = 0; i < ship.getNumberOfComponents(); i++) {
       planetBombingView.shipComponentUsage(i);
    }
    int index = planetBombingView.getUsedComponentIndex();
    assertEquals(ShipComponentType.ORBITAL_NUKE, ship.getComponent(index).getType());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAiConquering() {
    PlayerInfo defender = new PlayerInfo(SpaceRace.CENTAURS, 3, 2);
    Planet planet = new Planet(new Coordinate(5, 5), "Testopia", 1, false);
    planet.setPlanetOwner(2, defender);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);

    PlayerInfo attackerPlayerInfo = new PlayerInfo(SpaceRace.SPORKS, 3, 1);
    attackerPlayerInfo.getTechList().addTech(TechFactory.createCombatTech(
        "Orbital bombs Mk1", 3));
    attackerPlayerInfo.getTechList().addTech(TechFactory.createHullTech(
        "Destroyer Mk1", 1));
    attackerPlayerInfo.getTechList().addTech(TechFactory.createHullTech(
        "Small freighter", 2));
    attackerPlayerInfo.getTechList().addTech(TechFactory.createCombatTech(
        "Planetary invasion module", 2));
    Ship ship = null;
    do {
      ShipDesign design = ShipGenerator.createBattleShip(attackerPlayerInfo,
          ShipSize.MEDIUM, true, false);
      ship = new Ship(design);
    } while (!ship.hasBombs());
    Fleet fleet = new Fleet(ship, 5, 5);
    ShipDesign design = ShipGenerator.createColony(attackerPlayerInfo, true);
    ship = new Ship(design);
    ship.setColonist(2);
    fleet.addShip(ship);
    int attackerPlayerIndex = 0;
    ActionListener listener = Mockito.mock(ActionListener.class);

    planetBombingView = new PlanetBombingView(planet, fleet, 
        attackerPlayerInfo, attackerPlayerIndex, listener);
    assertEquals(defender, planet.getPlanetPlayerInfo());
    planetBombingView.handleAiToAiAttack();
    assertEquals(attackerPlayerInfo, planet.getPlanetPlayerInfo());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAiConqueringHuman() {
    PlayerInfo defender = new PlayerInfo(SpaceRace.CENTAURS, 3, 0);
    Planet planet = new Planet(new Coordinate(5, 5), "Testopia", 1, false);
    planet.setPlanetOwner(2, defender);
    planet.setWorkers(Planet.FOOD_FARMERS, 2);

    PlayerInfo attackerPlayerInfo = new PlayerInfo(SpaceRace.SPORKS, 3, 1);
    attackerPlayerInfo.getTechList().addTech(TechFactory.createCombatTech(
        "Orbital bombs Mk1", 3));
    attackerPlayerInfo.getTechList().addTech(TechFactory.createHullTech(
        "Destroyer Mk1", 1));
    attackerPlayerInfo.getTechList().addTech(TechFactory.createHullTech(
        "Small freighter", 2));
    attackerPlayerInfo.getTechList().addTech(TechFactory.createCombatTech(
        "Planetary invasion module", 2));
    Ship ship = null;
    do {
      ShipDesign design = ShipGenerator.createBattleShip(attackerPlayerInfo,
          ShipSize.MEDIUM, true, false);
      ship = new Ship(design);
    } while (!ship.hasBombs());
    Fleet fleet = new Fleet(ship, 5, 5);
    ShipDesign design = ShipGenerator.createColony(attackerPlayerInfo, true);
    ship = new Ship(design);
    ship.setColonist(2);
    fleet.addShip(ship);
    int attackerPlayerIndex = 0;
    ActionListener listener = Mockito.mock(ActionListener.class);

    planetBombingView = new PlanetBombingView(planet, fleet, 
        attackerPlayerInfo, attackerPlayerIndex, listener);
    assertEquals(defender, planet.getPlanetPlayerInfo());
    ActionEvent action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_ANIMATION_TIMER);

    int safetyCounter = 10000;
    while (!planetBombingView.isAiDone()) {
      planetBombingView.handleAction(action);
      planetBombingView.handleAction(action);
      planetBombingView.skipAnimation();
      safetyCounter--;
      if (safetyCounter <= 0) {
        assertFalse(true);
      }
    }
    assertEquals(attackerPlayerInfo, planet.getPlanetPlayerInfo());
  }

}