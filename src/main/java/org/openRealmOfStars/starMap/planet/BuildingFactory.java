package org.openRealmOfStars.starMap.planet;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
 * Copyright (C) 2023 BottledByte
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

import java.util.HashMap;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingType;

/**
 * Building factory
 */
public final class BuildingFactory {

  /** The Singleton */
  private static final BuildingFactory SINGLETON = new BuildingFactory();

  /** Map of all building definitons, with IDs as keys */
  private HashMap<String, Building> buildings;

  /**
   * Hiding the constructor.
   */
  private BuildingFactory() {
    buildings = new HashMap<>();
    initWildLife();
    initMilitaryFacility();
    initProductionFacility();
    initPlanetaryImprovement();
  }

  /**
   * Create planetary building with matching name
   * @param name Building name
   * @return Building or null if not found
   */
  public static Building createByName(final String name) {
    return SINGLETON.buildings.get(name);
  }

  /**
   * Create wild life
   */
  private void initWildLife() {
      Building tmp = null;
      tmp = new Building("Wildlife: big canine animals",
          Icons.getIconByName(Icons.ICON_SPINOSAURUS),
          BuildingType.FARM);
      tmp.setProdCost(0);
      tmp.setMetalCost(0);
      tmp.setDescription("Dangerous big canine animals."
          + " Population must fight against to destroy them.");
      tmp.setWildLifePower(24);
      tmp.setHappiness(-1);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Wildlife: big feline animals",
          Icons.getIconByName(Icons.ICON_SPINOSAURUS),
          BuildingType.FARM);
      tmp.setProdCost(0);
      tmp.setMetalCost(0);
      tmp.setDescription("Dangerous big feline animals."
          + " Population must fight against to destroy them.");
      tmp.setWildLifePower(28);
      tmp.setHappiness(-1);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Wildlife: big lizard animals",
          Icons.getIconByName(Icons.ICON_SPINOSAURUS),
          BuildingType.FARM);
      tmp.setProdCost(0);
      tmp.setMetalCost(0);
      tmp.setDescription("Dangerous big feline animals."
          + " Population must fight against to destroy them.");
      tmp.setWildLifePower(26);
      tmp.setHappiness(-1);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Wildlife: massive herding pack animals",
          Icons.getIconByName(Icons.ICON_SPINOSAURUS),
          BuildingType.FARM);
      tmp.setProdCost(0);
      tmp.setMetalCost(0);
      tmp.setDescription("Dangerous ferocious bipedal reptiles."
          + " Population must fight against to destroy them.");
      tmp.setWildLifePower(20);
      tmp.setHappiness(0);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Wildlife: ferocious bipedal reptiles",
          Icons.getIconByName(Icons.ICON_SPINOSAURUS),
          BuildingType.FARM);
      tmp.setProdCost(0);
      tmp.setMetalCost(0);
      tmp.setDescription("Dangerous ferocious bipedal reptiles."
          + " Population must fight against to destroy them.");
      tmp.setWildLifePower(36);
      tmp.setHappiness(-2);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Wildlife: massive bug like creature",
          Icons.getIconByName(Icons.ICON_SPINOSAURUS),
          BuildingType.FARM);
      tmp.setProdCost(0);
      tmp.setMetalCost(0);
      tmp.setDescription("Dangerous massive bug like creature."
          + " Population must fight against to destroy them.");
      tmp.setWildLifePower(30);
      tmp.setHappiness(-2);
      this.buildings.put(tmp.getName(), tmp);

  }

  /**
   * Create military facility
   */
  private void initMilitaryFacility() {
      Building tmp = null;
      tmp = new Building("Planetary defense turret Mk1",
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET),
          BuildingType.MILITARY);
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setDescription("Big and powerful defense turret.");
      tmp.setDefenseDamage(3);
      tmp.setHappiness(1);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Planetary defense turret Mk2",
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET),
          BuildingType.MILITARY);
      tmp.setProdCost(50);
      tmp.setMetalCost(50);
      tmp.setDescription("Huge defense turret.");
      tmp.setDefenseDamage(6);
      tmp.setHappiness(1);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Planetary defense turret Mk3",
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET),
          BuildingType.MILITARY);
      tmp.setProdCost(70);
      tmp.setMetalCost(70);
      tmp.setDescription("Biggest and meanest defense turret.");
      tmp.setDefenseDamage(9);
      tmp.setHappiness(1);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Planetary scanner Mk1",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(16);
      tmp.setMetalCost(16);
      tmp.setDescription("Planetary scanner to scan fleets around the planet.");
      tmp.setScanRange(3);
      tmp.setScanCloakingDetection(40);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Planetary scanner Mk2",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(24);
      tmp.setMetalCost(24);
      tmp.setDescription("Planetary scanner to scan fleets around the planet.\n"
          + "This is the improved version.");
      tmp.setScanRange(4);
      tmp.setScanCloakingDetection(80);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Planetary scanner Mk3",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(40);
      tmp.setMetalCost(40);
      tmp.setDescription("Planetary scanner to scan fleets around the planet.\n"
          + "Now with increased range.");
      tmp.setScanRange(5);
      tmp.setScanCloakingDetection(100);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Planetary scanner Mk4",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(60);
      tmp.setMetalCost(60);
      tmp.setDescription("Planetary scanner to scan fleets around the planet.\n"
          + "Even longer range.");
      tmp.setScanRange(6);
      tmp.setScanCloakingDetection(120);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Planetary scanner Mk5",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(80);
      tmp.setMetalCost(80);
      tmp.setDescription("Planetary scanner to scan fleet around the planet.\n"
          + "Best planetary scanner available.");
      tmp.setScanRange(7);
      tmp.setScanCloakingDetection(140);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Orbital defense grid",
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET),
          BuildingType.MILITARY);
      tmp.setProdCost(150);
      tmp.setMetalCost(150);
      tmp.setDescription("Laser defense system that shoot enemy ships"
          + " on the orbit.");
      tmp.setScientificAchievement(true);
      tmp.setSingleAllowed(true);
      tmp.setDefenseDamage(1);
      tmp.setHappiness(2);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Orbital shield",
          Icons.getIconByName(Icons.ICON_SHIELD),
          BuildingType.MILITARY);
      tmp.setProdCost(100);
      tmp.setMetalCost(180);
      tmp.setDescription("Orbital shield that blocks bombs.");
      tmp.setScientificAchievement(true);
      tmp.setSingleAllowed(true);
      tmp.setHappiness(1);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Deep space scanner",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.RESEARCH);
      tmp.setProdCost(150);
      tmp.setMetalCost(150);
      tmp.setDescription("Deep space scanner to reveal all the planets.\n"
          + "This scanner can be used also massive broadcastings.");
      tmp.setSingleAllowed(true);
      tmp.setScientificAchievement(true);
      tmp.setBroadcaster(true);
      tmp.setReseBonus(2);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Planetary ascension portal",
          Icons.getIconByName(Icons.ICON_AIRLOCK_OPEN),
          BuildingType.RESEARCH);
      tmp.setProdCost(120);
      tmp.setMetalCost(160);
      tmp.setDescription("Creates ascension portal near planet.");
      tmp.setHappiness(2);
      this.buildings.put(tmp.getName(), tmp);

  }

  /**
   * Create production facility
   */
  private void initProductionFacility() {
      Building tmp = null;
      tmp = new Building("Basic mine",
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(1);
      tmp.setProdCost(10);
      tmp.setMetalCost(6);
      tmp.setDescription("Small scale automatic mine");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Basic farm",
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(1);
      tmp.setProdCost(10);
      tmp.setMetalCost(4);
      tmp.setDescription("Automatic farm to produce food.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Basic factory",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(1);
      tmp.setProdCost(15);
      tmp.setMetalCost(5);
      tmp.setDescription("Single mass production line.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Basic lab",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);
      tmp.setProdCost(12);
      tmp.setMetalCost(4);
      tmp.setDescription("Basic laboratory for science.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Advanced farm",
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(2);
      tmp.setProdCost(20);
      tmp.setMetalCost(8);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Advanced automation farm.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Advanced mine",
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(2);
      tmp.setProdCost(20);
      tmp.setMetalCost(12);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Advanced mine with robotic miners.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Advanced factory",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(2);
      tmp.setProdCost(30);
      tmp.setMetalCost(10);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Advanced production line with robotics.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Advanced laboratory",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(2);
      tmp.setProdCost(24);
      tmp.setMetalCost(8);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Fully equiped science laboratory.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Farming center",
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(3);
      tmp.setProdCost(30);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Group of automated farms.");
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Mining center",
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(3);
      tmp.setProdCost(30);
      tmp.setMetalCost(24);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Group of automated mines.");
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Manufacturing center",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(3);
      tmp.setProdCost(40);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Group of automated factories.");
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Research center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(3);
      tmp.setProdCost(36);
      tmp.setMetalCost(16);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Group of research laboratories.");
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Hydropodic farming center",
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(4);
      tmp.setProdCost(40);
      tmp.setMetalCost(30);
      tmp.setMaintenanceCost(0.33);
      tmp.setDescription("Hydropodic farms producing food.");
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Nanobot mining center",
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(4);
      tmp.setProdCost(40);
      tmp.setMetalCost(36);
      tmp.setMaintenanceCost(0.33);
      tmp.setDescription("Mining done by millions of nanobots");
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Nanobot manufacturing center",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(4);
      tmp.setProdCost(50);
      tmp.setMetalCost(30);
      tmp.setMaintenanceCost(0.33);
      tmp.setDescription("Production lines full of nanobots.");
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Neural research center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(4);
      tmp.setProdCost(48);
      tmp.setMetalCost(24);
      tmp.setMaintenanceCost(0.33);
      tmp.setDescription("Neural network in huge research center");
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Ancient lab",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);
      tmp.setProdCost(8);
      tmp.setMetalCost(3);
      tmp.setDescription("Ancient laboratory for science.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Ancient factory",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(1);
      tmp.setProdCost(8);
      tmp.setMetalCost(3);
      tmp.setDescription("Ancient production line.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Ancient temple",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setCultBonus(1);
      tmp.setProdCost(6);
      tmp.setMetalCost(2);
      tmp.setDescription("Ancient temple for creating culture.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Ancient palace",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setCultBonus(1);
      tmp.setHappiness(1);
      tmp.setProdCost(15);
      tmp.setMetalCost(8);
      tmp.setDescription("Ancient palace for ruling the planet");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Black monolith",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setCultBonus(2);
      tmp.setHappiness(-1);
      tmp.setProdCost(15);
      tmp.setMetalCost(30);
      tmp.setDescription("Unknown origin of Black monolith."
          + " Cause unhappiness in population.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Material replicator",
      Icons.getIconByName(Icons.ICON_METAL_ORE), BuildingType.MINE);
      tmp.setCultBonus(0);
      tmp.setHappiness(0);
      tmp.setProdCost(200);
      tmp.setMetalCost(40);
      tmp.setDescription("Massive material replicator.");
      tmp.setScientificAchievement(true);
      tmp.setSingleAllowed(true);
      tmp.setMaterialBonus(2);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Advanced furnace",
      Icons.getIconByName(Icons.ICON_METAL), BuildingType.MINE);
      tmp.setCultBonus(0);
      tmp.setHappiness(0);
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setDescription("Advanced furnace produces metal and production.");
      tmp.setSingleAllowed(true);
      tmp.setMineBonus(1);
      tmp.setFactBonus(1);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Massive blast furnace",
      Icons.getIconByName(Icons.ICON_METAL), BuildingType.MINE);
      tmp.setCultBonus(0);
      tmp.setHappiness(0);
      tmp.setProdCost(20);
      tmp.setMetalCost(30);
      tmp.setDescription("Massive blast furnace produces metal"
          + " and production.");
      tmp.setSingleAllowed(true);
      tmp.setMineBonus(2);
      tmp.setFactBonus(1);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Planetary furnace",
      Icons.getIconByName(Icons.ICON_METAL), BuildingType.MINE);
      tmp.setCultBonus(0);
      tmp.setHappiness(0);
      tmp.setProdCost(200);
      tmp.setMetalCost(100);
      tmp.setDescription("Mega project that uses planet's core for heat"
          + " source for furnance");
      tmp.setSingleAllowed(true);
      tmp.setScientificAchievement(true);
      tmp.setMineBonus(4);
      tmp.setFactBonus(2);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("College of history",
      Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);
      tmp.setAncientArtifactResearch(2);
      tmp.setProdCost(20);
      tmp.setMetalCost(6);
      tmp.setDescription("College of history which allows doing basic research"
          + " and improved study on ancient artifacts.");
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);
  }

  /**
   * Create planetary improvement
   */
  private void initPlanetaryImprovement() {
    Building tmp = null;
      tmp = new Building("Space port",
          Icons.getIconByName(Icons.ICON_STARBASE), BuildingType.MILITARY);
      tmp.setDescription("Allows building the space ships and orbitals.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.5);
      tmp.setFleetCapacityBonus(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Barracks",
          Icons.getIconByName(Icons.ICON_TROOPS), BuildingType.MILITARY);
      tmp.setDescription("Population fights better against invaders."
          + "\nRecruited leaders start with 50 experience.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.33);
      tmp.setBattleBonus(50);
      tmp.setHappiness(1);
      tmp.setFleetCapacityBonus(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Tax center",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Makes population to pay taxes.");
      tmp.setProdCost(15);
      tmp.setMetalCost(5);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Market center",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Market center has all sort of goods to sell.");
      tmp.setProdCost(12);
      tmp.setMetalCost(6);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Culture center",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription("Music, Arts, poetry, movies and video games.");
      tmp.setProdCost(18);
      tmp.setMetalCost(4);
      tmp.setMaintenanceCost(0.25);
      tmp.setCultBonus(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Trade center",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Massive trade center for selling goods.");
      tmp.setProdCost(24);
      tmp.setMetalCost(12);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(2);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Extreme sports center",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription("Violent sports is a culture too. Violet sports fans\n"
          + "get very angry if they are going miss next match due invasion.");
      tmp.setProdCost(30);
      tmp.setMetalCost(22);
      tmp.setMaintenanceCost(0.5);
      tmp.setCultBonus(2);
      tmp.setBattleBonus(15);
      tmp.setHappiness(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Recycle center",
          Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
          BuildingType.FACTORY);
      tmp.setDescription(
          "When destroying buildings or ships 50%\nmetal is recycled.");
      tmp.setProdCost(15);
      tmp.setMetalCost(5);
      tmp.setMaintenanceCost(1);
      tmp.setRecycleBonus(50);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Radiation dampener",
          Icons.getIconByName(Icons.ICON_RADIATION), BuildingType.FACTORY);
      tmp.setDescription("Decrease planet radiation by one.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setHappiness(1);
      tmp.setMaintenanceCost(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Stock market",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Planetary stock market, where stonks\n"
          + "rising to the orbit.");
      tmp.setProdCost(36);
      tmp.setMetalCost(24);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(3);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Galactic sports center",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription(
            "Galactic Olympics stadium where \n"
          + "best atheletes compete each others. Building Galactic Olympics\n"
          + "stadium has possible diplomacy bonuses towards other realms.");
      tmp.setProdCost(40);
      tmp.setMetalCost(30);
      tmp.setMaintenanceCost(1);
      tmp.setCultBonus(3);
      tmp.setBattleBonus(25);
      tmp.setHappiness(2);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("New technology center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setDescription("New technology is studied and used in culture.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.5);
      tmp.setReseBonus(3);
      tmp.setCultBonus(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("VR movie center",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription("Galactic VR movie studio, where big galatic\n"
          + "block buster are being made.");
      tmp.setProdCost(30);
      tmp.setMetalCost(30);
      tmp.setCredBonus(2);
      tmp.setCultBonus(2);
      tmp.setHappiness(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Advanced recycle center",
          Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
          BuildingType.FACTORY);
      tmp.setDescription(
          "When destroying buildings or ships 75%\nmetal is recycled.");
      tmp.setProdCost(25);
      tmp.setMetalCost(10);
      tmp.setMaintenanceCost(1);
      tmp.setRecycleBonus(75);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Galactic bank",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Galactic bank is good business. Does stonks go all\n"
          + "the way to out space?");
      tmp.setProdCost(40);
      tmp.setMetalCost(20);
      tmp.setCredBonus(4);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Radiation well",
          Icons.getIconByName(Icons.ICON_RADIATION), BuildingType.FACTORY);
      tmp.setDescription("Decrease planet radiation by one.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(1);
      tmp.setHappiness(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Super AI center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setDescription("Super AI making science and improving production.");
      tmp.setProdCost(60);
      tmp.setMetalCost(80);
      tmp.setMaintenanceCost(1);
      tmp.setReseBonus(4);
      tmp.setFactBonus(1);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Replicator center",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setDescription("Replicated goods can sold with good profit.");
      tmp.setProdCost(40);
      tmp.setMetalCost(40);
      tmp.setCredBonus(2);
      tmp.setFactBonus(2);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("United Galaxy Tower",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription("United Galaxy Tower to gain influence of Galaxy.\n"
          + "Depending on galaxy size certain amount is required\n"
          + "to start diplomatic voting.");
      tmp.setProdCost(60);
      tmp.setMetalCost(40);
      tmp.setMaintenanceCost(1);
      tmp.setCultBonus(1);
      tmp.setHappiness(2);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Space academy",
          Icons.getIconByName(Icons.ICON_TROOPS), BuildingType.MILITARY);
      tmp.setDescription("Increases training of military personels for\n"
          + " space ships.\nRecruited leaders start at level 2.");
      tmp.setProdCost(30);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.25);
      tmp.setBattleBonus(25);
      tmp.setHappiness(1);
      tmp.setFleetCapacityBonus(2);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Broadcasting antenna",
          Icons.getIconByName(Icons.ICON_ANTENNA), BuildingType.CULTURE);
      tmp.setDescription("Broadcasting antenna for broadcasting culture.");
      tmp.setProdCost(40);
      tmp.setMetalCost(30);
      tmp.setCultBonus(1);
      tmp.setBroadcaster(true);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Broadcasting network",
          Icons.getIconByName(Icons.ICON_ANTENNA), BuildingType.CULTURE);
      tmp.setDescription("Broadcasting network for creating culture\n"
          + "and happiness.");
      tmp.setProdCost(60);
      tmp.setMetalCost(40);
      tmp.setMaintenanceCost(0.25);
      tmp.setHappiness(1);
      tmp.setCultBonus(3);
      tmp.setBroadcaster(true);
      tmp.setSingleAllowed(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Cyber lab",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);
      tmp.setHappiness(1);
      tmp.setProdCost(15);
      tmp.setMetalCost(10);
      tmp.setSingleAllowed(true);
      tmp.setDescription("Cyber laboratory for research. Gives morale boost"
          + " for population.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Collective research center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(2);
      tmp.setHappiness(1);
      tmp.setProdCost(25);
      tmp.setMetalCost(10);
      tmp.setSingleAllowed(true);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Collective research center for robotic minds.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Research matrix",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(3);
      tmp.setHappiness(2);
      tmp.setProdCost(40);
      tmp.setMetalCost(15);
      tmp.setSingleAllowed(true);
      tmp.setMaintenanceCost(0.3);
      tmp.setDescription("Research matrix for robotic minds.");
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Orbital elevator Mk1",
          Icons.getIconByName(Icons.ICON_ORBITAL_ELEVATOR),
          BuildingType.FACTORY);
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setDescription("Orbital elevator that increases production"
          + " and give happiness.");
      tmp.setFactBonus(1);
      tmp.setHappiness(1);
      tmp.setSingleAllowed(true);
      tmp.setOrbitalElevator(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Orbital elevator Mk2",
          Icons.getIconByName(Icons.ICON_ORBITAL_ELEVATOR),
          BuildingType.FACTORY);
      tmp.setProdCost(30);
      tmp.setMetalCost(30);
      tmp.setDescription("Orbital elevator that increases production"
          + " and give happiness. Plus makes a bit credit for builder.");
      tmp.setFactBonus(1);
      tmp.setCredBonus(1);
      tmp.setHappiness(1);
      tmp.setSingleAllowed(true);
      tmp.setOrbitalElevator(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Orbital elevator Mk3",
          Icons.getIconByName(Icons.ICON_ORBITAL_ELEVATOR),
          BuildingType.FACTORY);
      tmp.setProdCost(40);
      tmp.setMetalCost(40);
      tmp.setDescription("Orbital elevator that increases production"
          + " and give happiness. Now with bigger elevator.");
      tmp.setFactBonus(2);
      tmp.setCredBonus(1);
      tmp.setHappiness(1);
      tmp.setSingleAllowed(true);
      tmp.setOrbitalElevator(true);
      this.buildings.put(tmp.getName(), tmp);

      tmp = new Building("Orbital lift",
          Icons.getIconByName(Icons.ICON_ORBITAL_ELEVATOR),
          BuildingType.FACTORY);
      tmp.setProdCost(30);
      tmp.setMetalCost(30);
      tmp.setDescription("Orbital lift that increases production"
          + " and credits income.");
      tmp.setFactBonus(1);
      tmp.setCredBonus(1);
      tmp.setSingleAllowed(true);
      tmp.setOrbitalElevator(true);
      this.buildings.put(tmp.getName(), tmp);

  }

}
