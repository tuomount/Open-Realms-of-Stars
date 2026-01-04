package org.openRealmOfStars.player.ship;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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
 */

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesignConsts;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;

import junit.framework.TestCase;

/**
 * Test for Ship Design class
 * @TODO: Mock dependencies
 */
public class ShipDesignTest extends TestCase {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testScoutDesign() {
    ShipHull hull = ShipHullFactory.createByName("Scout Mk1",
        SpaceRaceFactory.createOne("CENTAURS"));
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
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
  public void testScout2Design() {
    ShipHull hull = ShipHullFactory.createByName("Scout Mk2",
        SpaceRaceFactory.createOne("CENTAURS"));
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent engine2 = ShipComponentFactory.createByName("Nuclear drive Mk2");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    design.addComponent(weapon);
    design.addComponent(engine);
    design.addComponent(armor);
    design.addComponent(engine2);
    assertEquals(true,ShipDesignConsts.TWO_ENGINES.equals(design.getFlaws()));
    design = new ShipDesign(hull);
    assertEquals(true,design.getFlaws().contains(ShipDesignConsts.ENGINE_IS_MISSING) );
    assertEquals(true,design.getFlaws().contains(ShipDesignConsts.NO_COMPONENTS) );
    design.addComponent(weapon);
    design.addComponent(engine2);
    design.addComponent(armor);
    design.addComponent(engine2);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(2, design.getSpeed());
    assertEquals(2, design.getFtlSpeed());
    assertEquals(2, design.getTacticSpeed());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testColonyDesign() {
    ShipHull hull = ShipHullFactory.createByName("Colony",
        SpaceRaceFactory.createOne("CENTAURS"));
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent module = ShipComponentFactory.createByName("Colonization module");
    design.addComponent(weapon);
    design.addComponent(engine);
    design.addComponent(energy);
    assertEquals(true,design.getFlaws().startsWith(ShipDesignConsts.NO_WEAPONS_ALLOWED));
    design.removeComponent(0);
    design.addComponent(module);
    assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
    assertEquals(3,design.getNumberOfComponents());
    assertEquals(0,design.getTotalMilitaryPower());
    assertEquals(1,design.getFreeCargoSpace());
    assertEquals(9, design.getTotalColonyPower());
    assertEquals(11, design.getInitiative());

  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTrooperDesignByGenerator() {
    PlayerInfo player = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 2, 0);
    player.getTechList().addTech(TechFactory.createHullTech("Small freighter Mk1", 2));
    player.getTechList().addTech(TechFactory.createHullTech("Medium freighter Mk1", 4));
    player.getTechList().addTech(TechFactory.createHullTech("Large freighter Mk1", 8));
    player.getTechList().addTech(TechFactory.createHullTech("Massive freighter Mk1", 13));
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
    ShipHull hull = ShipHullFactory.createByName("Probe",
        SpaceRaceFactory.createOne("CENTAURS"));
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk2");
    ShipComponent spyKit = ShipComponentFactory.createByName("Espionage module Mk1");


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
  public void testRareTechs() {
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"));
    info.getTechList().addTech(TechFactory.createCombatTech("Plasma cannon Mk1", 2));
    info.getTechList().addTech(TechFactory.createDefenseTech("Solar armor Mk1", 3));
    ShipDesign design = ShipGenerator.createBattleShip(info, ShipSize.SMALL, false, false);
    boolean plasmaCannon = false;
    boolean solarArmor = false;
    for (ShipComponent comp : design.getComponentList()) {
      if (comp.getType() == ShipComponentType.PLASMA_CANNON) {
        plasmaCannon = true;
      }
      if (comp.getType() == ShipComponentType.SOLAR_ARMOR) {
        solarArmor = true;
      }
    }
    assertTrue(plasmaCannon);
    assertTrue(solarArmor);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBattleShipDesign() {
    ShipHull hull = ShipHullFactory.createByName("Battleship Mk1", SpaceRaceFactory.createOne("HUMANS"));
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    ShipComponent comp = ShipComponentFactory.createByName("Targeting computer Mk1");
    ShipComponent jammer = ShipComponentFactory.createByName("Jammer Mk1");
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
    ShipHull hull = ShipHullFactory.createByName("Privateer Mk1", SpaceRaceFactory.createOne("HUMANS"));
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    ShipComponent module = ShipComponentFactory.createByName("Privateer module");
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
    assertEquals(false,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws(false,
        true, false)));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBomberShipDesign() {
    ShipHull hull = ShipHullFactory.createByName("Battleship Mk1", SpaceRaceFactory.createOne("HUMANS"));
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    ShipComponent comp = ShipComponentFactory.createByName("Targeting computer Mk1");
    ShipComponent bomb = ShipComponentFactory.createByName("Orbital bombs Mk1");
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
  public void testNuclearBomberShipDesign() {
    ShipHull hull = ShipHullFactory.createByName("Battleship Mk1", SpaceRaceFactory.createOne("HUMANS"));
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    ShipComponent comp = ShipComponentFactory.createByName("Targeting computer Mk1");
    ShipComponent bomb = ShipComponentFactory.createByName("Orbital fusion bomb");
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
    assertEquals(false,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws(true,
        false, false)));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testFreighter() {
    for (int i = 0; i < 10; i++) {
      PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"), 2, 0);
      ShipDesign design = ShipGenerator.createFreighter(info);
      assertEquals(ShipHullType.FREIGHTER, design.getHull().getHullType());
      assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
      info = new PlayerInfo(SpaceRaceFactory.createOne("SPORKS"), 2, 0);
      design = ShipGenerator.createFreighter(info);
      assertEquals(ShipHullType.FREIGHTER, design.getHull().getHullType());
      assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
      info = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"), 2, 0);
      design = ShipGenerator.createFreighter(info);
      assertEquals(ShipHullType.FREIGHTER, design.getHull().getHullType());
      assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
      info = new PlayerInfo(SpaceRaceFactory.createOne("TEUTHIDAES"), 2, 0);
      design = ShipGenerator.createFreighter(info);
      assertEquals(ShipHullType.FREIGHTER, design.getHull().getHullType());
      assertEquals(true,ShipDesignConsts.DESIGN_OK.equals(design.getFlaws()));
      assertEquals(false, design.isBomberShip());
      assertEquals(false, design.isTrooperShip());
    }
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testDevourer() {
    PlayerInfo monster = new PlayerInfo(
        SpaceRaceFactory.createOne(SpaceRaceFactory.SPACE_MONSTER));
    /*
     * Space monsters get space monster tech.
     * Space monsters get all their tech.
     */
    Tech tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 1,
        "Massive mouth with teeth Mk1");
    TechList techList = monster.getTechList();
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 2,
        "Massive mouth with teeth Mk2");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 3,
        "Massive mouth with teeth Mk3");
    techList.addTech(tech);

    tech = TechFactory.createSpaceMonsterTech(TechType.Defense, 1,
        "Organic armor Mk1");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Defense, 2,
        "Organic armor Mk2");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Defense, 3,
        "Organic armor Mk3");
    techList.addTech(tech);

    tech = TechFactory.createSpaceMonsterTech(TechType.Propulsion, 1,
        "Space fin");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Propulsion, 1,
        "Heart");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Propulsion, 2,
        "Large heart");
    techList.addTech(tech);

    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 1,
        "Tentacle Mk1");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 2,
        "Tentacle Mk2");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 3,
        "Arm spike");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Combat, 4,
        "Plasma spit");
    techList.addTech(tech);

    tech = TechFactory.createSpaceMonsterTech(TechType.Hulls, 1, "Space worm");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Hulls, 2, "Kraken");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Hulls, 3,
        "Large kraken");
    techList.addTech(tech);
    tech = TechFactory.createSpaceMonsterTech(TechType.Hulls, 4,
        "Devourer");
    techList.addTech(tech);

    ShipDesign design = ShipGenerator.createDevourer(monster);
    ShipStat stat = new ShipStat(design);
    assertEquals("Devourer", stat.getDesign().getName());
    stat.getDesign().getCost();
  }


}
