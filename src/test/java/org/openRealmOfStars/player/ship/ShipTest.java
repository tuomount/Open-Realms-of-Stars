package org.openRealmOfStars.player.ship;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

import static org.junit.Assert.*;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
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
    assertEquals(10, ship.getDefenseValue());
    assertEquals(false, ship.isColonyShip());
    assertEquals(false, ship.isPrivateeringShip());
    assertEquals(false, ship.isTrooperShip());
    assertEquals(false, ship.isColonyModule());
    assertEquals(false, ship.isTrooperModule());
    assertEquals(100, ship.getHitChance(weapon));
    
    design = new ShipDesign(hull);
    design.addComponent(energy);
    design.addComponent(engine);
    design.addComponent(shield);
    design.addComponent(weapon);
    ship = new Ship(design);
    assertEquals(false,ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED));
    assertEquals(false,ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(false,ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
    ship.setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, true);
    ship.setFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD, true);
    assertEquals(true,ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED));
    assertEquals(true,ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(true,ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, false);
    ship.setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, false);
    ship.setFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD, false);
    assertEquals(false,ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED));
    assertEquals(false,ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(false,ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    assertEquals(0,ship.getCulture());
    ship.setCulture(3);
    assertEquals(3,ship.getCulture());
    assertEquals(4, ship.getHullPoints());
    assertEquals(0,ship.getArmor());
    assertEquals(1,ship.getShield());
    ship.setShield(0);
    assertEquals(0,ship.getShield());
    ship.regenerateShield();
    assertEquals(1,ship.getShield());
    assertEquals(0,ship.damageComponent(1, new ShipDamage(-2, "Damaged!")));
    assertEquals(3, ship.getHullPoints());
    ship.fixShip(true);
    assertEquals(4, ship.getHullPoints());
    

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTopPrivateeringShip() {
    ShipHull hull = ShipHullFactory.createByName("Privateer Mk3", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("HE Missile Mk8");
    ShipComponent engine = ShipComponentFactory.createByName("Impulse engine Mk4");
    ShipComponent energy = ShipComponentFactory.createByName("Zero-point source Mk2");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk10");
    ShipComponent shield = ShipComponentFactory.createByName("Shield Mk10");
    ShipComponent scanner = ShipComponentFactory.createByName("Scanner Mk5");
    ShipComponent cloak = ShipComponentFactory.createByName("Cloaking device Mk6");
    design.addComponent(energy);
    design.addComponent(engine);
    design.addComponent(armor);
    design.addComponent(weapon);
    design.addComponent(shield);
    design.addComponent(scanner);
    design.addComponent(cloak);
    Ship ship = new Ship(design);
    
    assertEquals(0,ship.getExperience());
    ship.setExperience(5);
    assertEquals(5,ship.getExperience());
    assertEquals(0,ship.getCulture());
    ship.setCulture(3);
    assertEquals(3,ship.getCulture());
    assertEquals(10,ship.getArmor());
    assertEquals(10,ship.getShield());
    assertEquals(7,ship.getFtlSpeed());
    assertEquals(3,ship.getSpeed());
    assertEquals(2,ship.getTacticSpeed());
    assertEquals(0,ship.getColonist());
    assertEquals(0,ship.getMetal());
    assertEquals(3,ship.getHullPointForComponent(0));
    assertEquals(0,ship.getHullPointForComponent(99));
    assertEquals(true,ship.hasWeapons());
    assertEquals(false,ship.hasBombs());
    assertEquals(100, ship.getScannerDetectionLvl());
    assertEquals(5, ship.getScannerLvl());
    assertEquals(0, ship.getDefenseValue());
    assertEquals(false, ship.isColonyShip());
    assertEquals(true, ship.isPrivateeringShip());
    assertEquals(false, ship.isTrooperShip());
    assertEquals(false, ship.isColonyModule());
    assertEquals(false, ship.isTrooperModule());
    assertEquals(50, ship.getHitChance(weapon));

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testProbeFTLSpeedp() {
    ShipHull hull = ShipHullFactory.createByName("Probe", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent scanner = ShipComponentFactory.createByName("Scanner Mk1");
    design.addComponent(engine);
    design.addComponent(scanner);
    Ship ship = new Ship(design);
    
    assertEquals(3,ship.getFtlSpeed());
    assertEquals(2,ship.getSpeed());
    assertEquals(1,ship.getTacticSpeed());

  }

}
