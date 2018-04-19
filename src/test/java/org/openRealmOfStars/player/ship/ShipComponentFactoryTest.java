package org.openRealmOfStars.player.ship;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
* Ship component factory test
*/
public class ShipComponentFactoryTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testEngines() {
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk4");
    assertEquals(3, engine.getSpeed());
    assertEquals(4, engine.getFtlSpeed());
    assertEquals(3, engine.getTacticSpeed());
    assertEquals(4, engine.getEnergyResource());
    assertEquals(0, engine.getEnergyRequirement());
    assertEquals(7, engine.getCost());
    assertEquals(7, engine.getMetalCost());
    engine = ShipComponentFactory.createByName("Impulse engine Mk4");
    assertEquals(3, engine.getSpeed());
    assertEquals(7, engine.getFtlSpeed());
    assertEquals(2, engine.getTacticSpeed());
    assertEquals(0, engine.getEnergyResource());
    assertEquals(2, engine.getEnergyRequirement());
    assertEquals(9, engine.getCost());
    assertEquals(8, engine.getMetalCost());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testWeapons() {
    ShipComponent weapon = ShipComponentFactory.createByName("HE missile Mk2");
    assertEquals(4, weapon.getDamage());
    assertEquals(3, weapon.getWeaponRange());
    assertEquals(0, weapon.getEnergyRequirement());
    assertEquals(3, weapon.getCost());
    assertEquals(7, weapon.getMetalCost());
    weapon = ShipComponentFactory.createByName("Massdrive Mk1");
    assertEquals(6, weapon.getDamage());
    assertEquals(3, weapon.getWeaponRange());
    assertEquals(3, weapon.getEnergyRequirement());
    assertEquals(5, weapon.getCost());
    assertEquals(5, weapon.getMetalCost());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDefense() {
    ShipComponent defense = ShipComponentFactory.createByName("Shield Mk8");
    assertEquals(8, defense.getDefenseValue());
    assertEquals(3, defense.getEnergyRequirement());
    assertEquals(11, defense.getCost());
    assertEquals(3, defense.getMetalCost());
    defense = ShipComponentFactory.createByName("Armor plating Mk8");
    assertEquals(8, defense.getDefenseValue());
    assertEquals(0, defense.getEnergyRequirement());
    assertEquals(5, defense.getCost());
    assertEquals(11, defense.getMetalCost());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testElectronics() {
    ShipComponent electronics = ShipComponentFactory.createByName("Cloaking device Mk3");
    assertEquals(60, electronics.getCloaking());
    assertEquals(2, electronics.getEnergyRequirement());
    assertEquals(4, electronics.getCost());
    assertEquals(2, electronics.getMetalCost());
    electronics = ShipComponentFactory.createByName("Orbital bombs Mk1");
    assertEquals(40, electronics.getDamage());
    assertEquals(0, electronics.getEnergyRequirement());
    assertEquals(6, electronics.getCost());
    assertEquals(3, electronics.getMetalCost());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testStarbaseModules() {
    ShipComponent module = ShipComponentFactory.createByName("Starbase bank");
    assertEquals(2, module.getCreditBonus());
    assertEquals(2, module.getEnergyRequirement());
    assertEquals(24, module.getCost());
    assertEquals(12, module.getMetalCost());
    module = ShipComponentFactory.createByName("Starbase lab");
    assertEquals(1, module.getResearchBonus());
    assertEquals(2, module.getEnergyRequirement());
    assertEquals(12, module.getCost());
    assertEquals(4, module.getMetalCost());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testNull() {
    ShipComponent module = ShipComponentFactory.createByName(null);
    assertEquals(null, module);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testNoSuchModule() {
    ShipComponent module = ShipComponentFactory.createByName("No such module");
    assertEquals(null, module);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testEspionage() {
    ShipComponent electronics = ShipComponentFactory.createByName("Espionage module Mk1");
    assertEquals(1, electronics.getEspionageBonus());
    assertEquals(1, electronics.getEnergyRequirement());
    assertEquals(3, electronics.getCost());
    assertEquals(1, electronics.getMetalCost());
    electronics = ShipComponentFactory.createByName("Espionage module Mk2");
    assertEquals(2, electronics.getEspionageBonus());
    assertEquals(1, electronics.getEnergyRequirement());
    assertEquals(3, electronics.getCost());
    assertEquals(1, electronics.getMetalCost());
    electronics = ShipComponentFactory.createByName("Espionage module Mk3");
    assertEquals(3, electronics.getEspionageBonus());
    assertEquals(1, electronics.getEnergyRequirement());
    assertEquals(4, electronics.getCost());
    assertEquals(1, electronics.getMetalCost());
    electronics = ShipComponentFactory.createByName("Espionage module Mk4");
    assertEquals(4, electronics.getEspionageBonus());
    assertEquals(1, electronics.getEnergyRequirement());
    assertEquals(4, electronics.getCost());
    assertEquals(1, electronics.getMetalCost());
    electronics = ShipComponentFactory.createByName("Espionage module Mk5");
    assertEquals(6, electronics.getEspionageBonus());
    assertEquals(2, electronics.getEnergyRequirement());
    assertEquals(6, electronics.getCost());
    assertEquals(1, electronics.getMetalCost());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testThrustersAndFighterBays() {
    ShipComponent component = ShipComponentFactory.createByName("Combat thrusters");
    assertEquals(1, component.getTacticSpeed());
    assertEquals(1, component.getEnergyRequirement());
    assertEquals(1, component.getCost());
    assertEquals(2, component.getMetalCost());
    component = ShipComponentFactory.createByName("Fighter bay Mk1");
    assertEquals(1, component.getBaySize());
    assertEquals(1, component.getEnergyRequirement());
    assertEquals(2, component.getCost());
    assertEquals(5, component.getMetalCost());
    component = ShipComponentFactory.createByName("Fighter bay Mk2");
    assertEquals(2, component.getBaySize());
    assertEquals(1, component.getEnergyRequirement());
    assertEquals(2, component.getCost());
    assertEquals(5, component.getMetalCost());
    component = ShipComponentFactory.createByName("Fighter bay Mk3");
    assertEquals(3, component.getBaySize());
    assertEquals(1, component.getEnergyRequirement());
    assertEquals(2, component.getCost());
    assertEquals(6, component.getMetalCost());
    component = ShipComponentFactory.createByName("Fighter bay Mk4");
    assertEquals(4, component.getBaySize());
    assertEquals(1, component.getEnergyRequirement());
    assertEquals(3, component.getCost());
    assertEquals(7, component.getMetalCost());
  }

}
