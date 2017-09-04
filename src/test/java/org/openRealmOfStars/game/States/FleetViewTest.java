package org.openRealmOfStars.game.States;

import static org.junit.Assert.assertEquals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.mapTiles.Tile;
import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Test for FleetViewTest class
 *
 */
public class FleetViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFleetViewOneFleetInDeepSpace() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    info.getTechList().addTech(TechFactory.createHullTech("Destroyer Mk1", 1));
    info.getTechList().addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    info.getTechList().addTech(TechFactory.createCombatTech("Railgun Mk1", 1));
    info.getTechList().addTech(TechFactory.createCombatTech("Photon torpedo"
        + " Mk1", 1));
    ShipDesign scoutDesign = ShipGenerator.createScout(info);
    Ship scout = new Ship(scoutDesign);
    Fleet fleet1 = new Fleet(scout, 5, 5);
    info.getFleets().add(fleet1);
    ActionListener listener = Mockito.mock(ActionListener.class);
    FleetView view = new FleetView(null, fleet1, info.getFleets(), info, true,
        listener);
    assertEquals(fleet1,view.getFleet());
    assertEquals(null,view.getPlanet());
    assertEquals(info,view.getInfo());
    assertEquals(info.getFleets(),view.getFleetList());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFleetViewOneColonyOrbitingPlanet() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    info.getTechList().addTech(TechFactory.createHullTech("Colony", 1));
    ShipDesign colonyDesign = ShipGenerator.createColony(info, false);
    Ship colony = new Ship(colonyDesign);
    Fleet fleet1 = new Fleet(colony, 5, 5);
    info.getFleets().add(fleet1);
    Planet planet = new Planet(new Coordinate(5, 5), "Test", 1, false);
    planet.setRadiationLevel(1);
    planet.setWorkers(Planet.FOOD_FARMERS, 5);
    planet.setMetal(50);
    planet.setPlanetOwner(0, info);
    ActionListener listener = Mockito.mock(ActionListener.class);
    ActionEvent event = Mockito.mock(ActionEvent.class);
    Mockito.when(event.getActionCommand()).thenReturn(GameCommands
        .COMMAND_COLONIST_PLUS);
    FleetView view = new FleetView(planet, fleet1, info.getFleets(), info, true,
        listener);
    assertEquals(0,view.getFleet().getTotalCargoColonist());
    view.handleAction(event);
    assertEquals(1,view.getFleet().getTotalCargoColonist());
    event = Mockito.mock(ActionEvent.class);
    Mockito.when(event.getActionCommand()).thenReturn(GameCommands
        .COMMAND_COLONIST_MINUS);
    view.handleAction(event);
    assertEquals(0,view.getFleet().getTotalCargoColonist());
    assertEquals(0,view.getFleet().getTotalCargoMetal());
    event = Mockito.mock(ActionEvent.class);
    Mockito.when(event.getActionCommand()).thenReturn(GameCommands
        .COMMAND_METAL_CARGO_PLUS);
    view.handleAction(event);
    assertEquals(10,view.getFleet().getTotalCargoMetal());
    event = Mockito.mock(ActionEvent.class);
    Mockito.when(event.getActionCommand()).thenReturn(GameCommands
        .COMMAND_METAL_CARGO_MINUS);
    view.handleAction(event);
    assertEquals(0,view.getFleet().getTotalCargoMetal());
    StarMap map = Mockito.mock(StarMap.class);
    Tile tile = Mockito.mock(Tile.class);
    Mockito.when(tile.getName()).thenReturn(TileNames.DEEP_SPACE_ANCHOR1);
    Mockito.when(map.getTile(Mockito.anyInt(), Mockito.anyInt())).thenReturn(tile);
    view.setStarmap(map);
  }

}