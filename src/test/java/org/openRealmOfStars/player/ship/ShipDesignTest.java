package org.openRealmOfStars.player.ship;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesignConsts;
import org.openRealmOfStars.player.tech.TechFactory;

import static org.junit.Assert.*;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
 * Test for Ship Design class
 * @TODO: Mock dependencies
 */
public class ShipDesignTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testScoutDesign() {
    ShipHull hull = ShipHullFactory.createByName("Scout Mk1", SpaceRace.CENTAURS);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    
    
    assertEquals(true,ShipDesignConsts.ENGINE_IS_MISSING.equals(design.getFlaws()));
    design.addComponent(weapon);
    design.addComponent(engine);
    design.addComponent(energy);
    design.addComponent(armor);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(10,design.getTotalMilitaryPower());
    assertEquals(1,design.getTotalArmor());
    assertEquals(0,design.getTotalShield());
    assertEquals(4,design.getNumberOfComponents());
    assertEquals(2,design.getSpeed());
    assertEquals(2,design.getFtlSpeed());
    assertEquals(1,design.getTacticSpeed());
    assertEquals(15, design.getInitiative());
    design.removeComponent(3);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(3,design.getNumberOfComponents());
    assertEquals(7,design.getTotalMilitaryPower());
    design.removeComponent(1);
    assertEquals(true,ShipDesignConsts.ENGINE_IS_MISSING.equals(design.getFlaws()));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testColonyDesign() {
    ShipHull hull = ShipHullFactory.createByName("Colony", SpaceRace.CENTAURS);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent module = ShipComponentFactory.createByName("Colony Module");
    
    
    assertEquals(true,ShipDesignConsts.ENGINE_IS_MISSING.equals(design.getFlaws()));
    design.addComponent(weapon);
    design.addComponent(engine);
    design.addComponent(energy);
    assertEquals(true,design.getFlaws().startsWith(ShipDesignConsts.NO_WEAPONS_ALLOWED));
    design.removeComponent(0);
    design.addComponent(module);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(3,design.getNumberOfComponents());
    assertEquals(0,design.getTotalMilitaryPower());
    assertEquals(1,design.getFreeSlots());
    assertEquals(9, design.getTotalColonyPower());
    assertEquals(11, design.getInitiative());

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testColonyDesignByGenerator() {
    PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    player.getTechList().addTech(TechFactory.createHullTech("Small freighter", 2));
    player.getTechList().addTech(TechFactory.createHullTech("Medium freighter", 4));
    player.getTechList().addTech(TechFactory.createHullTech("Large freighter", 6));
    player.getTechList().addTech(TechFactory.createHullTech("Massive freighter", 8));
    ShipDesign design = ShipGenerator.createColony(player, false);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(ShipSize.MEDIUM, design.getHull().getSize());
    assertEquals(4,design.getNumberOfComponents());
    assertEquals(0,design.getTotalMilitaryPower());
    assertEquals(2,design.getFreeSlots());
    assertEquals(10, design.getTotalColonyPower());
    assertEquals(11, design.getInitiative());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFreighterDesignByGenerator() {
    PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    player.getTechList().addTech(TechFactory.createHullTech("Small freighter", 2));
    player.getTechList().addTech(TechFactory.createHullTech("Medium freighter", 4));
    player.getTechList().addTech(TechFactory.createHullTech("Large freighter", 6));
    player.getTechList().addTech(TechFactory.createHullTech("Massive freighter", 8));
    ShipDesign design = ShipGenerator.createFreighter(player);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(ShipSize.HUGE, design.getHull().getSize());
    assertEquals(6, design.getInitiative());
    if (design.getNumberOfComponents() != 2 && design.getNumberOfComponents() != 3) {
      assertFalse(true);
    }
    assertEquals(0,design.getTotalMilitaryPower());
    if (design.getFreeSlots() != 10 && design.getFreeSlots() != 9) {
      assertFalse(true);
    }
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTrooperDesignByGenerator() {
    PlayerInfo player = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    player.getTechList().addTech(TechFactory.createHullTech("Small freighter", 2));
    player.getTechList().addTech(TechFactory.createHullTech("Medium freighter", 4));
    player.getTechList().addTech(TechFactory.createHullTech("Large freighter", 6));
    player.getTechList().addTech(TechFactory.createHullTech("Massive freighter", 8));
    player.getTechList().addTech(TechFactory.createCombatTech("Planetary invasion module", 2));
    ShipDesign design = ShipGenerator.createColony(player, true);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(ShipSize.HUGE, design.getHull().getSize());
    assertEquals(false, design.isBomberShip());
    assertEquals(true, design.isTrooperShip());
    assertEquals(0,design.getTotalMilitaryPower());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testProbeDesign() {
    ShipHull hull = ShipHullFactory.createByName("Probe", SpaceRace.CENTAURS);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk2");
    ShipComponent spyKit = ShipComponentFactory.createByName("Espionage module Mk1");
    
    
    assertEquals(true,ShipDesignConsts.ENGINE_IS_MISSING.equals(design.getFlaws()));
    design.addComponent(weapon);
    design.addComponent(engine);
    assertEquals(true,design.getFlaws().startsWith(ShipDesignConsts.NO_WEAPONS_ALLOWED));
    design.removeComponent(0);
    design.addComponent(spyKit);
    design.addComponent(spyKit);
    assertEquals(true,design.getFlaws().startsWith(ShipDesignConsts.MANY_ESPIONAGE));
    design.removeComponent(1);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(2,design.getNumberOfComponents());
    assertEquals(0,design.getTotalMilitaryPower());
    assertEquals(2,design.getFreeSlots());
    assertEquals(3,design.getFtlSpeed());
    assertEquals(2,design.getSpeed());
    assertEquals(2,design.getTacticSpeed());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBattleShipDesign() {
    ShipHull hull = ShipHullFactory.createByName("Battleship Mk1", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    ShipComponent comp = ShipComponentFactory.createByName("Targeting computer Mk1");
    ShipComponent jammer = ShipComponentFactory.createByName("Jammer Mk1");
    assertEquals(true,ShipDesignConsts.ENGINE_IS_MISSING.equals(design.getFlaws()));
    design.addComponent(weapon);
    design.addComponent(engine);
    design.addComponent(energy);
    design.addComponent(armor);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    design.addComponent(jammer);
    design.addComponent(comp);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    design.addComponent(jammer);
    assertEquals(true,design.getFlaws().startsWith(ShipDesignConsts.MANY_JAMMERS));
    design.removeComponent(6);
    design.addComponent(comp);
    assertEquals(true,design.getFlaws().startsWith(ShipDesignConsts.MANY_COMPUTERS));
    assertEquals(false, design.isBomberShip());
    assertEquals(false, design.isTrooperShip());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testPrivateerDesign() {
    ShipHull hull = ShipHullFactory.createByName("Privateer Mk1", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    ShipComponent module = ShipComponentFactory.createByName("Privateer Module");
    String tmp = design.getFlaws();
    assertEquals(true, tmp.contains(ShipDesignConsts.ENGINE_IS_MISSING));
    assertEquals(true, tmp.contains(ShipDesignConsts.PRIVATEER_MODULE_MISSING));
    design.addComponent(weapon);
    design.addComponent(engine);
    design.addComponent(energy);
    design.addComponent(armor);
    tmp = design.getFlaws();
    assertEquals(true, tmp.contains(ShipDesignConsts.PRIVATEER_MODULE_MISSING));
    design.addComponent(module);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBomberShipDesign() {
    ShipHull hull = ShipHullFactory.createByName("Battleship Mk1", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    ShipComponent comp = ShipComponentFactory.createByName("Targeting computer Mk1");
    ShipComponent bomb = ShipComponentFactory.createByName("Orbital Bombs Mk1");
    assertEquals(true,ShipDesignConsts.ENGINE_IS_MISSING.equals(design.getFlaws()));
    design.addComponent(weapon);
    design.addComponent(engine);
    design.addComponent(energy);
    design.addComponent(armor);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    design.addComponent(bomb);
    design.addComponent(comp);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(true, design.isBomberShip());
    assertEquals(false, design.isTrooperShip());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFreighter() {
    for (int i = 0; i < 10; i++) {
      PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
      ShipDesign design = ShipGenerator.createFreighter(info);
      assertEquals(ShipHullType.FREIGHTER, design.getHull().getHullType());
      assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
      info = new PlayerInfo(SpaceRace.SPORKS, 2, 0);
      design = ShipGenerator.createFreighter(info);
      assertEquals(ShipHullType.FREIGHTER, design.getHull().getHullType());
      assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
      info = new PlayerInfo(SpaceRace.GREYANS, 2, 0);
      design = ShipGenerator.createFreighter(info);
      assertEquals(ShipHullType.FREIGHTER, design.getHull().getHullType());
      assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
      info = new PlayerInfo(SpaceRace.TEUTHIDAES, 2, 0);
      design = ShipGenerator.createFreighter(info);
      assertEquals(ShipHullType.FREIGHTER, design.getHull().getHullType());
      assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
      assertEquals(false, design.isBomberShip());
      assertEquals(false, design.isTrooperShip());
    }
  }



}
