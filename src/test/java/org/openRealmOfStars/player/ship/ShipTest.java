package org.openRealmOfStars.player.ship;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;

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
    
    assertEquals(1, ship.getWeaponRange(weapon));
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
    assertEquals(6, ship.getTotalMilitaryPower());
    assertEquals(false, ship.isColonyShip());
    assertEquals(false, ship.isPrivateeringShip());
    assertEquals(false, ship.isTrooperShip());
    assertEquals(false, ship.isColonyModule());
    assertEquals(false, ship.isTrooperModule());
    assertEquals(100, ship.getHitChance(weapon));
    assertEquals(false, ship.isStarBase());
    ship.setArmor(0);
    ship.initializeShieldAndArmor();
    assertEquals(1, ship.getArmor());
    
    design = new ShipDesign(hull);
    design.addComponent(energy);
    design.addComponent(engine);
    design.addComponent(shield);
    design.addComponent(weapon);
    ship = new Ship(design);
    ship.setShield(0);
    ship.initializeShieldAndArmor();
    assertEquals(1, ship.getShield());

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
    
    assertEquals(0, ship.getExperience());
    String desc = "Design - Normal\n"
        + "Energy: 5 Init.: 15\n"
        + "Cost: 23 Metal: 16\n"
        + "Speed: 2 FTL: 2 Tactic: 1\n"
        + "Shield: 1/1 Armor: 0/0 Hull Points: 4\n"
        + "Military power: 6\n"
        + "Slots: 4/4";
    assertEquals(desc, ship.getDescription());
    ship.setExperience(3);
    assertEquals(3, ship.getExperience());
    desc = "Design - Normal\n"
        + "Energy: 5 Init.: 15\n"
        + "Cost: 23 Metal: 16\n"
        + "Speed: 2 FTL: 2 Tactic: 1\n"
        + "Shield: 1/1 Armor: 0/0 Hull Points: 4\n"
        + "Military power: 6 Exp: 3\n"
        + "Slots: 4/4";
    assertEquals(desc, ship.getDescription());
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
    assertEquals(55, ship.getTotalMilitaryPower());
    assertEquals(120, ship.getCloakingValue());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTopPrivateeringShip2() {
    ShipHull hull = ShipHullFactory.createByName("Privateer Mk3", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("HE Missile Mk8");
    ShipComponent engine = ShipComponentFactory.createByName("Impulse engine Mk4");
    ShipComponent energy = ShipComponentFactory.createByName("Zero-point source Mk2");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk10");
    ShipComponent shield = ShipComponentFactory.createByName("Shield Mk10");
    ShipComponent jammer = ShipComponentFactory.createByName("Jammer Mk3");
    ShipComponent targetingComp = ShipComponentFactory.createByName("Targeting computer Mk1");
    design.addComponent(energy);
    design.addComponent(engine);
    design.addComponent(armor);
    design.addComponent(weapon);
    design.addComponent(shield);
    design.addComponent(jammer);
    design.addComponent(targetingComp);
    Ship ship = new Ship(design);
    
    assertEquals(58, ship.getTotalMilitaryPower());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTradeShip() {
    ShipHull hull = ShipHullFactory.createByName("Medium freighter", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent engine = ShipComponentFactory.createByName("Impulse engine Mk4");
    ShipComponent energy = ShipComponentFactory.createByName("Zero-point source Mk2");
    design.addComponent(energy);
    design.addComponent(engine);
    Ship ship = new Ship(design);
    assertEquals(Ship.CARGO_TYPE_NO_CARGO, ship.getCargoType());
    assertEquals(12, ship.getFreeCargoColonists());
    assertEquals(60, ship.getFreeCargoMetal());
    ship.setColonist(1);
    assertEquals(Ship.CARGO_TYPE_POPULATION, ship.getCargoType());
    assertEquals(12, ship.getFreeCargoColonists());
    assertEquals(50, ship.getFreeCargoMetal());
    ship.setMetal(10);
    assertEquals(10, ship.getFreeCargoColonists());
    assertEquals(40, ship.getFreeCargoMetal());
    assertEquals(0,ship.getExperience());
    assertEquals(null, ship.getTradeCoordinate());
    Coordinate coord = new Coordinate(5, 6);
    ship.setTradeDistance(coord);
    assertEquals(5, ship.getTradeCoordinate().getX());
    assertEquals(6, ship.getTradeCoordinate().getY());
    ship.setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, true);
    assertEquals(Ship.CARGO_TYPE_TRADE_GOODS, ship.getCargoType());
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    ship.setFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD, true);
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    ship.setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, false);
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    ship.setFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD, false);
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTradeShip2() {
    ShipHull hull = ShipHullFactory.createByName("Medium freighter", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent engine = ShipComponentFactory.createByName("Impulse engine Mk4");
    ShipComponent energy = ShipComponentFactory.createByName("Zero-point source Mk2");
    design.addComponent(energy);
    design.addComponent(engine);
    Ship ship = new Ship(design);
    ship.setTradeDistance(new Coordinate(5, 5));
    ship.setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, true);
    Planet planet = Mockito.mock(Planet.class);
    Coordinate coordinate = new Coordinate(30, 30);
    Mockito.when(planet.getCoordinate()).thenReturn(coordinate);
    PlayerInfo planetOwner = Mockito.mock(PlayerInfo.class);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(planetOwner);
    PlayerInfo trader = Mockito.mock(PlayerInfo.class);
    int result = ship.doTrade(planet, trader);
    assertEquals(18, result);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTradeShip3() {
    ShipHull hull = ShipHullFactory.createByName("Medium freighter", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent engine = ShipComponentFactory.createByName("Impulse engine Mk4");
    ShipComponent energy = ShipComponentFactory.createByName("Zero-point source Mk2");
    design.addComponent(energy);
    design.addComponent(engine);
    Ship ship = new Ship(design);
    Planet planet = Mockito.mock(Planet.class);
    Coordinate coordinate = new Coordinate(5, 5);
    Mockito.when(planet.getCoordinate()).thenReturn(coordinate);
    PlayerInfo trader = Mockito.mock(PlayerInfo.class);
    PlayerInfo planetOwner = Mockito.mock(PlayerInfo.class);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(trader);
    int result = ship.doTrade(planet, trader);
    assertEquals(0, result);
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    planet = Mockito.mock(Planet.class);
    Coordinate planetCoord = new Coordinate(30, 30);
    Mockito.when(planet.getCoordinate()).thenReturn(planetCoord);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(planetOwner);
    result = ship.doTrade(planet, trader);
    assertEquals(18, result);
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    result = ship.doTrade(planet, trader);
    assertEquals(0, result);
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTradeShip4() {
    ShipHull hull = ShipHullFactory.createByName("Medium freighter", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent engine = ShipComponentFactory.createByName("Impulse engine Mk4");
    ShipComponent energy = ShipComponentFactory.createByName("Zero-point source Mk2");
    design.addComponent(energy);
    design.addComponent(engine);
    Ship ship = new Ship(design);
    Planet planet = Mockito.mock(Planet.class);
    Coordinate coordinate = new Coordinate(5, 5);
    Mockito.when(planet.getCoordinate()).thenReturn(coordinate);
    PlayerInfo trader = Mockito.mock(PlayerInfo.class);
    PlayerInfo planetOwner = Mockito.mock(PlayerInfo.class);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(planetOwner);
    int result = ship.doTrade(planet, trader);
    assertEquals(0, result);
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    planet = Mockito.mock(Planet.class);
    Coordinate planetCoord = new Coordinate(30, 30);
    Mockito.when(planet.getCoordinate()).thenReturn(planetCoord);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(trader);
    result = ship.doTrade(planet, trader);
    assertEquals(18, result);
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    result = ship.doTrade(planet, trader);
    assertEquals(0, result);
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTradeShip5() {
    ShipHull hull = ShipHullFactory.createByName("Medium freighter", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent engine = ShipComponentFactory.createByName("Impulse engine Mk4");
    ShipComponent energy = ShipComponentFactory.createByName("Zero-point source Mk2");
    design.addComponent(energy);
    design.addComponent(engine);
    Ship ship = new Ship(design);
    Planet planet = Mockito.mock(Planet.class);
    Coordinate coordinate = new Coordinate(5, 5);
    Mockito.when(planet.getCoordinate()).thenReturn(coordinate);
    PlayerInfo trader = Mockito.mock(PlayerInfo.class);
    PlayerInfo planetOwner = Mockito.mock(PlayerInfo.class);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(trader);
    int result = ship.doTrade(planet, trader);
    assertEquals(0, result);
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    result = ship.doTrade(planet, trader);
    assertEquals(0, result);
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    result = ship.doTrade(planet, trader);
    assertEquals(0, result);
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    planet = Mockito.mock(Planet.class);
    Coordinate planetCoord = new Coordinate(30, 30);
    Mockito.when(planet.getCoordinate()).thenReturn(planetCoord);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(planetOwner);
    result = ship.doTrade(planet, trader);
    assertEquals(18, result);
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    result = ship.doTrade(planet, trader);
    assertEquals(0, result);
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
    planetCoord = new Coordinate(5, 5);
    Mockito.when(planet.getCoordinate()).thenReturn(planetCoord);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(trader);
    result = ship.doTrade(planet, trader);
    assertEquals(18, result);
    assertEquals(true, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD));
    assertEquals(false, ship.getFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTradeShip6() {
    ShipHull hull = ShipHullFactory.createByName("Medium freighter", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent engine = ShipComponentFactory.createByName("Impulse engine Mk4");
    ShipComponent energy = ShipComponentFactory.createByName("Zero-point source Mk2");
    design.addComponent(energy);
    design.addComponent(engine);
    Ship ship = new Ship(design);
    assertEquals(Ship.CARGO_TYPE_NO_CARGO, ship.getCargoType());
    assertEquals(12, ship.getFreeCargoColonists());
    assertEquals(60, ship.getFreeCargoMetal());
    ship.setMetal(10);
    assertEquals(10, ship.getFreeCargoColonists());
    assertEquals(50, ship.getFreeCargoMetal());
    assertEquals(Ship.CARGO_TYPE_METAL, ship.getCargoType());
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

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testStarBase() {
    ShipHull hull = ShipHullFactory.createByName("Small starbase Mk1", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent weapon = ShipComponentFactory.createByName("Laser Mk1");
    ShipComponent engine = ShipComponentFactory.createByName("Nuclear drive Mk1");
    ShipComponent energy = ShipComponentFactory.createByName("Fission source Mk1");
    ShipComponent armor = ShipComponentFactory.createByName("Armor plating Mk1");
    design.addComponent(energy);
    design.addComponent(engine);
    design.addComponent(armor);
    design.addComponent(weapon);
    Ship ship = new Ship(design);
    assertEquals(0, ship.getWeaponRange(weapon));
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
    assertEquals(6, ship.getTotalMilitaryPower());
    assertEquals(false, ship.isColonyShip());
    assertEquals(false, ship.isPrivateeringShip());
    assertEquals(false, ship.isTrooperShip());
    assertEquals(false, ship.isColonyModule());
    assertEquals(false, ship.isTrooperModule());
    assertEquals(100, ship.getHitChance(weapon));
    assertEquals(true, ship.isStarBase());
    ship.setFlag(Ship.FLAG_STARBASE_DEPLOYED, true);
    assertEquals(2, ship.getWeaponRange(weapon));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testStarBase2() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    info.getTechList().addTech(TechFactory.createHullTech("Small starbase Mk1", 2));
    info.getTechList().addTech(TechFactory.createHullTech("Medium starbase", 4));
    info.getTechList().addTech(TechFactory.createHullTech("Large starbase", 6));
    info.getTechList().addTech(TechFactory.createHullTech("Massive starbase", 8));
    info.getTechList().addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    info.getTechList().addTech(TechFactory.createCombatTech("Railgun Mk1", 1));
    info.getTechList().addTech(TechFactory.createDefenseTech("Armor Mk1", 1));
    info.getTechList().addTech(TechFactory.createDefenseTech("Shield Mk1", 1));
    info.getTechList().addTech(TechFactory.createImprovementTech("Starbase lab", 4));
    ShipDesign design = ShipGenerator.createStarbase(info, ShipSize.MEDIUM);
    assertNotEquals(null, design);
    Ship ship = new Ship(design);
    assertNotEquals(null, ship);
    design = ShipGenerator.createStarbase(info, ShipSize.LARGE);
    assertNotEquals(null, design);
    ship = new Ship(design);
    assertNotEquals(null, ship);
    assertEquals(true,ship.getTotalMilitaryPower() > 0);
    assertEquals(0, ship.getTotalCreditBonus());
    assertEquals(0, ship.getTotalCultureBonus());
    // Starbase lab is in component list so there might be a lab
    assertEquals(true, ship.getTotalResearchBonus() >= 0);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testStarBase3() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN, 2, 0);
    info.getTechList().addTech(TechFactory.createHullTech("Large starbase", 6));
    info.getTechList().addTech(TechFactory.createCombatTech("Laser Mk1", 1));
    info.getTechList().addTech(TechFactory.createCombatTech("Railgun Mk1", 1));
    info.getTechList().addTech(TechFactory.createDefenseTech("Armor Mk1", 1));
    info.getTechList().addTech(TechFactory.createDefenseTech("Shield Mk1", 1));
    info.getTechList().addTech(TechFactory.createImprovementTech("Starbase lab", 4));
    info.getTechList().addTech(TechFactory.createImprovementTech("Starbase market", 3));
    info.getTechList().addTech(TechFactory.createImprovementTech("Starbase music hall", 2));
    info.getTechList().addTech(TechFactory.createPropulsionTech("Zero-point source Mk2", 10));
    ShipHull hull = ShipHullFactory.createByName("Large starbase", SpaceRace.HUMAN);
    ShipDesign design = new ShipDesign(hull);
    ShipComponent energy = ShipComponentFactory.createByName("Zero-point source Mk2");
    ShipComponent lab = ShipComponentFactory.createByName("Starbase lab");
    ShipComponent cult = ShipComponentFactory.createByName("Starbase music hall");
    ShipComponent market = ShipComponentFactory.createByName("Starbase market");
    design.addComponent(energy);
    design.addComponent(lab);
    design.addComponent(cult);
    design.addComponent(market);
    assertNotEquals(null, design);
    assertEquals(3, design.getStarbaseValue());
    Ship ship = new Ship(design);
    assertNotEquals(null, ship);
    assertEquals(1, ship.getTotalCreditBonus());
    assertEquals(1, ship.getTotalCultureBonus());
    assertEquals(1, ship.getTotalResearchBonus());
  }


}
