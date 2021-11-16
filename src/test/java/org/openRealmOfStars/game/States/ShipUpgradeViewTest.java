package org.openRealmOfStars.game.States;


import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2021 Tuomo Untinen
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
* Test for ShipUpgradeView class
*
*/
public class ShipUpgradeViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasic() {
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
    Planet planet = new Planet(new Coordinate(5,5), "Planet I", 1, false);
    planet.setPlanetOwner(0, info);
    planet.setWorkers(Planet.FOOD_FARMERS, 1);
    ShipUpgradeView view = new ShipUpgradeView(info, fleet1, planet, listener);
  }

}
