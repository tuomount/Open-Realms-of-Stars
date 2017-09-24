package org.openRealmOfStars.player.ship;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesignConsts;

import static org.junit.Assert.*;

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
    assertEquals(13, design.getTotalColonyPower());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testProbeDesign() {
    ShipHull hull = ShipHullFactory.createByName("Probe", SpaceRace.CENTAURS);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    
    
    assertEquals(true,ShipDesignConsts.ENGINE_IS_MISSING.equals(design.getFlaws()));
    design.addComponent(weapon);
    design.addComponent(engine);
    assertEquals(true,design.getFlaws().startsWith(ShipDesignConsts.NO_WEAPONS_ALLOWED));
    design.removeComponent(0);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(1,design.getNumberOfComponents());
    assertEquals(0,design.getTotalMilitaryPower());
    assertEquals(3,design.getFreeSlots());
    assertEquals(3,design.getFtlSpeed());
    assertEquals(2,design.getSpeed());
    assertEquals(1,design.getTacticSpeed());
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
  }

}
