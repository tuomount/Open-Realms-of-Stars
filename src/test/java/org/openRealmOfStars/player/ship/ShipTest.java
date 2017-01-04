package org.openRealmOfStars.player.ship;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

import static org.junit.Assert.*;

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
 * Test for Ship class
 */
public class ShipTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testScoutShip() {
    ShipHull hull = ShipHullFactory.createByName("Scout Mk1", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    ShipComponent shield = ShipComponentFactory.createByName("Shield Mk1");
    design.addComponent(energy);
    design.addComponent(engine);
    design.addComponent(armor);
    design.addComponent(weapon);
    Ship ship = new Ship(design);
    
    
    assertEquals(1,ship.getArmor());
    assertEquals(0,ship.getShield());
    assertEquals(2,ship.getFtlSpeed());
    assertEquals(2,ship.getSpeed());
    assertEquals(1,ship.getTacticSpeed());
    assertEquals(0,ship.getColonist());
    assertEquals(0,ship.getMetal());
    assertEquals(1,ship.getHullPointForComponent(0));
    assertEquals(0,ship.getHullPointForComponent(99));
    assertEquals(true,ship.hasWeapons());
    assertEquals(false,ship.hasBombs());
    assertEquals(0, ship.getScannerDetectionLvl());
    assertEquals(0, ship.getScannerLvl());
    
    design = new ShipDesign(hull);
    design.addComponent(energy);
    design.addComponent(engine);
    design.addComponent(shield);
    design.addComponent(weapon);
    ship = new Ship(design);
    assertEquals(0,ship.getArmor());
    assertEquals(1,ship.getShield());
    ship.setShield(0);
    assertEquals(0,ship.getShield());
    ship.regenerateShield();
    assertEquals(1,ship.getShield());
    assertEquals(0,ship.damageComponent(1, new ShipDamage(-2, "Damaged!")));

  }


}
