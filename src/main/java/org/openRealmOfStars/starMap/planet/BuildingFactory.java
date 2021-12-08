package org.openRealmOfStars.starMap.planet;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2018-2021 Tuomo Untinen
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
 * Building factory
 *
 */
public final class BuildingFactory {

  /**
   * Hiding the constructor.
   */
  private BuildingFactory() {
   // Nothing to do
  }
  /**
   * Current maximum buildings for whole game.
   * Remember to increase this when new building is added to game.
   * It should be one bigger than last index.
   */
  private static final int MAX_BUILDING = 65;

  /**
   * Component Basic mine
   */
  public static final int COMPONENT_BASIC_MINE = 0;

  /**
   * Component Basic farm
   */
  public static final int COMPONENT_BASIC_FARM = 1;

  /**
   * Component Basic factory
   */
  public static final int COMPONENT_BASIC_FACTORY = 2;

  /**
   * Component Basic lab
   */
  public static final int COMPONENT_BASIC_LAB = 3;

  /**
   * Component Space port
   */
  public static final int COMPONENT_SPACE_PORT = 4;

  /**
   * Component Barracks
   */
  public static final int COMPONENT_BARRACKS = 5;

  /**
   * Component Tax center
   */
  public static final int COMPONENT_TAX_CENTER = 6;

  /**
   * Component Advanced farm
   */
  public static final int COMPONENT_ADVANCED_FARM = 7;

  /**
   * Component Advanced mine
   */
  public static final int COMPONENT_ADVANCED_MINE = 8;

  /**
   * Component Advanced factory
   */
  public static final int COMPONENT_ADVANCED_FACTORY = 9;

  /**
   * Component Advanced laboratory
   */
  public static final int COMPONENT_ADVANCED_LABORATORY = 10;

  /**
   * Component Market center
   */
  public static final int COMPONENT_MARKET_CENTER = 11;

  /**
   * Component Culture center
   */
  public static final int COMPONENT_CULTURE_CENTER = 12;

  /**
   * Component Trade center
   */
  public static final int COMPONENT_TRADE_CENTER = 13;

  /**
   * Component Extreme sports center
   */
  public static final int COMPONENT_EXTREME_SPORTS_CENTER = 14;

  /**
   * Component Recycle center
   */
  public static final int COMPONENT_RECYCLE_CENTER = 15;

  /**
   * Component Farming center
   */
  public static final int COMPONENT_FARMING_CENTER = 16;

  /**
   * Component Mining center
   */
  public static final int COMPONENT_MINING_CENTER = 17;

  /**
   * Component Manufacturing center
   */
  public static final int COMPONENT_MANUFACTURING_CENTER = 18;

  /**
   * Component Radiation dampener
   */
  public static final int COMPONENT_RADIATION_DAMPENER = 19;

  /**
   * Component Research center
   */
  public static final int COMPONENT_RESEARCH_CENTER = 20;

  /**
   * Component Stock market
   */
  public static final int COMPONENT_STOCK_MARKET = 21;

  /**
   * Component Galactic sports center
   */
  public static final int COMPONENT_GALACTIC_SPORTS_CENTER = 22;

  /**
   * Component New technology center
   */
  public static final int COMPONENT_NEW_TECHNOLOGY_CENTER = 23;

  /**
   * Component VR movie center
   */
  public static final int COMPONENT_VR_MOVIE_CENTER = 24;

  /**
   * Component Advanced recycle center
   */
  public static final int COMPONENT_ADVANCED_RECYCLE_CENTER = 25;

  /**
   * Component Galactic bank
   */
  public static final int COMPONENT_GALACTIC_BANK = 26;

  /**
   * Component Radiation well
   */
  public static final int COMPONENT_RADIATION_WELL = 27;

  /**
   * Component Hydropodic farming center
   */
  public static final int COMPONENT_HYDROPODIC_FARMING_CENTER = 28;

  /**
   * Component Nanobot mining center
   */
  public static final int COMPONENT_NANOBOT_MINING_CENTER = 29;

  /**
   * Component Nanobot manufacturing center
   */
  public static final int COMPONENT_NANOBOT_MANUFACTURING_CENTER = 30;

  /**
   * Component Neural research center
   */
  public static final int COMPONENT_NEURAL_RESEARCH_CENTER = 31;

  /**
   * Component Super AI center
   */
  public static final int COMPONENT_SUPER_AI_CENTER = 32;

  /**
   * Component Replicator center
   */
  public static final int COMPONENT_REPLICATOR_CENTER = 33;

  /**
   * Component Planetary defense turret Mk1
   */
  public static final int COMPONENT_PLANETARY_DEFENSE_TURRET_MK1 = 34;

  /**
   * Component Planetary defense turret Mk2
   */
  public static final int COMPONENT_PLANETARY_DEFENSE_TURRET_MK2 = 35;

  /**
   * Component Planetary defense turret Mk3
   */
  public static final int COMPONENT_PLANETARY_DEFENSE_TURRET_MK3 = 36;

  /**
   * Component Planetary scanner Mk1
   */
  public static final int COMPONENT_PLANETARY_SCANNER_MK1 = 37;

  /**
   * Component Planetary scanner Mk2
   */
  public static final int COMPONENT_PLANETARY_SCANNER_MK2 = 38;

  /**
   * Component Planetary scanner Mk3
   */
  public static final int COMPONENT_PLANETARY_SCANNER_MK3 = 39;

  /**
   * Component Planetary scanner Mk4
   */
  public static final int COMPONENT_PLANETARY_SCANNER_MK4 = 40;

  /**
   * Component Planetary scanner Mk5
   */
  public static final int COMPONENT_PLANETARY_SCANNER_MK5 = 41;

  /**
   * Component Ancient lab
   */
  public static final int COMPONENT_ANCIENT_LAB = 42;

  /**
   * Component Ancient factory
   */
  public static final int COMPONENT_ANCIENT_FACTORY = 43;

  /**
   * Component Ancient temple
   */
  public static final int COMPONENT_ANCIENT_TEMPLE = 44;

  /**
   * Component Ancient palace
   */
  public static final int COMPONENT_ANCIENT_PALACE = 45;

  /**
   * Component Black monolith
   */
  public static final int COMPONENT_BLACK_MONOLITH = 46;

  /**
   * Component Orbital defense grid
   */
  public static final int COMPONENT_ORBITAL_DEFENSE_GRID = 47;
  /**
   * Component Orbital shield
   */
  public static final int COMPONENT_ORBITAL_SHIELD = 48;
  /**
   * Component Material replicator
   */
  public static final int COMPONENT_MATERIAL_REPLICATOR = 49;
  /**
   * Component Deep space scanner
   */
  public static final int COMPONENT_DEEP_SPACE_SCANNER = 50;
  /**
   * Component United Galaxy Tower
   */
  public static final int COMPONENT_UNITED_GALAXY_TOWER = 51;
  /**
   * Component Space academy
   */
  public static final int COMPONENT_SPACE_ACADEMY = 52;
  /**
   * Component Broadcasting antenna
   */
  public static final int COMPONENT_BROADCASTING_ANTENNA = 53;
  /**
   * Component Broadcasting network
   */
  public static final int COMPONENT_BROADCASTING_NETWORK = 54;
  /**
   * Component Cyber lab
   */
  public static final int COMPONENT_CYBER_LAB = 55;
  /**
   * Component Collective reseach center
   */
  public static final int COMPONENT_COLLECTIVE_RESEARCH_CENTER = 56;
  /**
   * Component Research matrix
   */
  public static final int COMPONENT_RESEARCH_MATRIX = 57;
  /**
   * Component Advanced furnace
   */
  public static final int COMPONENT_ADVANCED_FURNACE = 58;
  /**
   * Component Massive blast furnace
   */
  public static final int COMPONENT_MASSIVE_BLAST_FURNACE = 59;
  /**
   * Component Planetary furnace
   */
  public static final int COMPONENT_PLANETARY_FURNACE = 60;
  /**
   * Component Orbital elevator Mk1
   */
  public static final int COMPONENT_ORBITAL_ELEVATOR_MK1 = 61;
  /**
   * Component Orbital elevator Mk2
   */
  public static final int COMPONENT_ORBITAL_ELEVATOR_MK2 = 62;
  /**
   * Component Orbital elevator Mk3
   */
  public static final int COMPONENT_ORBITAL_ELEVATOR_MK3 = 63;
  /**
   * Component Orbital lift
   */
  public static final int COMPONENT_ORBITAL_LIFT = 64;

  /**
   * Create planetary building with index
   * @param index For creating a  new building
   * @return Building if index found otherwise null
   */
  public static Building create(final int index) {
    Building tmp = null;
    switch (index) {
    case COMPONENT_BASIC_MINE:
      tmp = createProductionFacility(index);
      break; // Basic mine
    case COMPONENT_BASIC_FARM:
      tmp = createProductionFacility(index);
      break; // Basic farm
    case COMPONENT_BASIC_FACTORY:
      tmp = createProductionFacility(index);
      break; // Basic factory
    case COMPONENT_BASIC_LAB:
      tmp = createProductionFacility(index);
      break; // Basic lab
    case COMPONENT_SPACE_PORT:
      tmp = createPlanetaryImprovement(index);
      break; // Space port
    case COMPONENT_BARRACKS:
      tmp = createPlanetaryImprovement(index);
      break; // Barracks
    case COMPONENT_TAX_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Tax center
    case COMPONENT_ADVANCED_FARM:
      tmp = createProductionFacility(index);
      break; // Advanced farm
    case COMPONENT_ADVANCED_MINE:
      tmp = createProductionFacility(index);
      break; // Advanced mine
    case COMPONENT_ADVANCED_FACTORY:
      tmp = createProductionFacility(index);
      break; // Advanced factory
    case COMPONENT_ADVANCED_LABORATORY:
      tmp = createProductionFacility(index);
      break; // Advanced laboratory
    case COMPONENT_MARKET_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Market center
    case COMPONENT_CULTURE_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Culture center
    case COMPONENT_TRADE_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Trade center
    case COMPONENT_EXTREME_SPORTS_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Extreme sports center
    case COMPONENT_RECYCLE_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Recycle center
    case COMPONENT_FARMING_CENTER:
      tmp = createProductionFacility(index);
      break; // Farming center
    case COMPONENT_MINING_CENTER:
      tmp = createProductionFacility(index);
      break; // Mining center
    case COMPONENT_MANUFACTURING_CENTER:
      tmp = createProductionFacility(index);
      break; // Manufacturing center
    case COMPONENT_RADIATION_DAMPENER:
      tmp = createPlanetaryImprovement(index);
      break; // Radiation dampener
    case COMPONENT_RESEARCH_CENTER:
      tmp = createProductionFacility(index);
      break; // Research center
    case COMPONENT_STOCK_MARKET:
      tmp = createPlanetaryImprovement(index);
      break; // Stock market
    case COMPONENT_GALACTIC_SPORTS_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Galactic sports center
    case COMPONENT_NEW_TECHNOLOGY_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // New technology center
    case COMPONENT_VR_MOVIE_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // VR movie center
    case COMPONENT_ADVANCED_RECYCLE_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Advanced recycle center
    case COMPONENT_GALACTIC_BANK:
      tmp = createPlanetaryImprovement(index);
      break; // Galactic bank
    case COMPONENT_RADIATION_WELL:
      tmp = createPlanetaryImprovement(index);
      break; // Radiation well
    case COMPONENT_HYDROPODIC_FARMING_CENTER:
      tmp = createProductionFacility(index);
      break; // Hydropodic farming center
    case COMPONENT_NANOBOT_MINING_CENTER:
      tmp = createProductionFacility(index);
      break; // Nanobot mining center
    case COMPONENT_NANOBOT_MANUFACTURING_CENTER:
      tmp = createProductionFacility(index);
      break; // Nanobot manufacturing center
    case COMPONENT_NEURAL_RESEARCH_CENTER:
      tmp = createProductionFacility(index);
      break; // Neural research center
    case COMPONENT_SUPER_AI_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Super AI center
    case COMPONENT_REPLICATOR_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Replicator center
    case COMPONENT_PLANETARY_DEFENSE_TURRET_MK1:
      tmp = createMilitaryFacility(index);
      break; // Planetary defense turret Mk1
    case COMPONENT_PLANETARY_DEFENSE_TURRET_MK2:
      tmp = createMilitaryFacility(index);
      break; // Planetary defense turret Mk2
    case COMPONENT_PLANETARY_DEFENSE_TURRET_MK3:
      tmp = createMilitaryFacility(index);
      break; // Planetary defense turret Mk3
    case COMPONENT_PLANETARY_SCANNER_MK1:
      tmp = createMilitaryFacility(index);
      break; // Planetary scanner Mk1
    case COMPONENT_PLANETARY_SCANNER_MK2:
      tmp = createMilitaryFacility(index);
      break; // Planetary scanner Mk2
    case COMPONENT_PLANETARY_SCANNER_MK3:
      tmp = createMilitaryFacility(index);
      break; // Planetary scanner Mk3
    case COMPONENT_PLANETARY_SCANNER_MK4:
      tmp = createMilitaryFacility(index);
      break; // Planetary scanner Mk4
    case COMPONENT_PLANETARY_SCANNER_MK5:
      tmp = createMilitaryFacility(index);
      break; // Planetary scanner Mk5
    case COMPONENT_ANCIENT_LAB:
      tmp = createProductionFacility(index);
      break; // Ancient lab
    case COMPONENT_ANCIENT_FACTORY:
      tmp = createProductionFacility(index);
      break; // Ancient factory
    case COMPONENT_ANCIENT_TEMPLE:
      tmp = createProductionFacility(index);
      break; // Ancient temple
    case COMPONENT_ANCIENT_PALACE:
      tmp = createProductionFacility(index);
      break; // Ancient palace
    case COMPONENT_BLACK_MONOLITH:
      tmp = createProductionFacility(index);
      break; // Black Monolith
    case COMPONENT_ORBITAL_DEFENSE_GRID:
      tmp = createMilitaryFacility(index);
      break; // Orbital defense grid
    case COMPONENT_ORBITAL_SHIELD:
      tmp = createMilitaryFacility(index);
      break; // Orbital shield
    case COMPONENT_MATERIAL_REPLICATOR:
      tmp = createProductionFacility(index);
      break; // Material replicator
    case COMPONENT_DEEP_SPACE_SCANNER:
      tmp = createMilitaryFacility(index);
      break; // Deep space scanner
    case COMPONENT_UNITED_GALAXY_TOWER:
      tmp = createPlanetaryImprovement(index);
      break; // United Galaxy Tower
    case COMPONENT_SPACE_ACADEMY:
      tmp = createPlanetaryImprovement(index);
      break; // Space academy
    case COMPONENT_BROADCASTING_ANTENNA:
      tmp = createPlanetaryImprovement(index);
      break; // Broadcasting antenna
    case COMPONENT_BROADCASTING_NETWORK:
      tmp = createPlanetaryImprovement(index);
      break; // Broadcasting network
    case COMPONENT_CYBER_LAB:
      tmp = createPlanetaryImprovement(index);
      break; // Cyber lab
    case COMPONENT_COLLECTIVE_RESEARCH_CENTER:
      tmp = createPlanetaryImprovement(index);
      break; // Collective research center
    case COMPONENT_RESEARCH_MATRIX:
      tmp = createPlanetaryImprovement(index);
      break; // Research matrix
    case COMPONENT_ADVANCED_FURNACE:
      tmp = createProductionFacility(index);
      break; // Advanced furnace
    case COMPONENT_MASSIVE_BLAST_FURNACE:
      tmp = createProductionFacility(index);
      break; // Massive blast furnace
    case COMPONENT_PLANETARY_FURNACE:
      tmp = createProductionFacility(index);
      break; // Planetary furnace
    case COMPONENT_ORBITAL_ELEVATOR_MK1:
      tmp = createPlanetaryImprovement(index);
      break; // Orbital elevator Mk1
    case COMPONENT_ORBITAL_ELEVATOR_MK2:
      tmp = createPlanetaryImprovement(index);
      break; // Orbital elevator Mk2
    case COMPONENT_ORBITAL_ELEVATOR_MK3:
      tmp = createPlanetaryImprovement(index);
      break; // Orbital elevator Mk3
    case COMPONENT_ORBITAL_LIFT:
      tmp = createPlanetaryImprovement(index);
      break; // Orbital lift
    default:
      throw new IllegalArgumentException("No building found with index "
      + index + "!");
    }
    return tmp;
  }

  /**
   * Create planetary building with matching name
   * @param name Building name
   * @return Building or null if not found
   */
  public static Building createByName(final String name) {
    Building tmp = null;
    for (int i = 0; i < MAX_BUILDING; i++) {
      tmp = create(i);
      if (tmp != null && tmp.getName().equalsIgnoreCase(name)) {
        return tmp;
      }
    }
    return null;
  }

  /**
   * Create military facility
   * @param index for creating a new building
   * @return Building or null if not found with index
   */
  private static Building createMilitaryFacility(final int index) {
    Building tmp = null;
    if (index == COMPONENT_PLANETARY_DEFENSE_TURRET_MK1) {
      tmp = new Building(index, "Planetary defense turret Mk1",
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET),
          BuildingType.MILITARY);
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setDescription("Big and powerful defense turret.");
      tmp.setDefenseDamage(3);
      tmp.setHappiness(1);
      return tmp;
    }
    if (index == COMPONENT_PLANETARY_DEFENSE_TURRET_MK2) {
      tmp = new Building(index, "Planetary defense turret Mk2",
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET),
          BuildingType.MILITARY);
      tmp.setProdCost(50);
      tmp.setMetalCost(50);
      tmp.setDescription("Huge defense turret.");
      tmp.setDefenseDamage(6);
      tmp.setHappiness(1);
      return tmp;
    }
    if (index == COMPONENT_PLANETARY_DEFENSE_TURRET_MK3) {
      tmp = new Building(index, "Planetary defense turret Mk3",
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET),
          BuildingType.MILITARY);
      tmp.setProdCost(70);
      tmp.setMetalCost(70);
      tmp.setDescription("Biggest and meanest defense turret.");
      tmp.setDefenseDamage(9);
      tmp.setHappiness(1);
      return tmp;
    }
    if (index == COMPONENT_PLANETARY_SCANNER_MK1) {
      tmp = new Building(index, "Planetary scanner Mk1",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(16);
      tmp.setMetalCost(16);
      tmp.setDescription("Planetary scanner to scan fleets around the planet.");
      tmp.setScanRange(3);
      tmp.setScanCloakingDetection(40);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_PLANETARY_SCANNER_MK2) {
      tmp = new Building(index, "Planetary scanner Mk2",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(24);
      tmp.setMetalCost(24);
      tmp.setDescription("Planetary scanner to scan fleets around the planet.\n"
          + "This is the improved version.");
      tmp.setScanRange(4);
      tmp.setScanCloakingDetection(80);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_PLANETARY_SCANNER_MK3) {
      tmp = new Building(index, "Planetary scanner Mk3",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(40);
      tmp.setMetalCost(40);
      tmp.setDescription("Planetary scanner to scan fleets around the planet.\n"
          + "Now with increased range.");
      tmp.setScanRange(5);
      tmp.setScanCloakingDetection(100);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_PLANETARY_SCANNER_MK4) {
      tmp = new Building(index, "Planetary scanner Mk4",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(60);
      tmp.setMetalCost(60);
      tmp.setDescription("Planetary scanner to scan fleets around the planet.\n"
          + "Even longer range.");
      tmp.setScanRange(6);
      tmp.setScanCloakingDetection(120);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_PLANETARY_SCANNER_MK5) {
      tmp = new Building(index, "Planetary scanner Mk5",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(80);
      tmp.setMetalCost(80);
      tmp.setDescription("Planetary scanner to scan fleet around the planet.\n"
          + "Best planetary scanner available.");
      tmp.setScanRange(7);
      tmp.setScanCloakingDetection(140);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_ORBITAL_DEFENSE_GRID) {
      tmp = new Building(index, "Orbital defense grid",
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
      return tmp;
    }
    if (index == COMPONENT_ORBITAL_SHIELD) {
      tmp = new Building(index, "Orbital shield",
          Icons.getIconByName(Icons.ICON_SHIELD),
          BuildingType.MILITARY);
      tmp.setProdCost(100);
      tmp.setMetalCost(180);
      tmp.setDescription("Orbital shield that blocks bombs.");
      tmp.setScientificAchievement(true);
      tmp.setSingleAllowed(true);
      tmp.setHappiness(1);
      return tmp;
    }
    if (index == COMPONENT_DEEP_SPACE_SCANNER) {
      tmp = new Building(index, "Deep space scanner",
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.RESEARCH);
      tmp.setProdCost(150);
      tmp.setMetalCost(150);
      tmp.setDescription("Deep space scanner to reveal all the planets.\n"
          + "This scanner can be used also massive broadcastings.");
      tmp.setSingleAllowed(true);
      tmp.setScientificAchievement(true);
      tmp.setBroadcaster(true);
      tmp.setReseBonus(2);
      return tmp;
    }
    return tmp;
  }

  /**
   * Create production facility
   * @param index for creating a new building
   * @return Building or null if not found with index
   */
  private static Building createProductionFacility(final int index) {
    Building tmp = null;
    if (index == COMPONENT_BASIC_MINE) {
      tmp = new Building(index, "Basic mine",
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(1);
      tmp.setProdCost(10);
      tmp.setMetalCost(6);
      tmp.setDescription("Small scale automatic mine");
      return tmp;
    }
    if (index == COMPONENT_BASIC_FARM) {
      tmp = new Building(index, "Basic farm",
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(1);
      tmp.setProdCost(10);
      tmp.setMetalCost(4);
      tmp.setDescription("Automatic farm to produce food.");
      return tmp;
    }
    if (index == COMPONENT_BASIC_FACTORY) {
      tmp = new Building(index, "Basic factory",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(1);
      tmp.setProdCost(15);
      tmp.setMetalCost(5);
      tmp.setDescription("Single mass production line.");
      return tmp;
    }
    if (index == COMPONENT_BASIC_LAB) {
      tmp = new Building(index, "Basic lab",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);
      tmp.setProdCost(12);
      tmp.setMetalCost(4);
      tmp.setDescription("Basic laboratory for science.");
      return tmp;
    }
    if (index == COMPONENT_ADVANCED_FARM) {
      tmp = new Building(index, "Advanced farm",
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(2);
      tmp.setProdCost(20);
      tmp.setMetalCost(8);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Advanced automation farm.");
      return tmp;
    }
    if (index == COMPONENT_ADVANCED_MINE) {
      tmp = new Building(index, "Advanced mine",
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(2);
      tmp.setProdCost(20);
      tmp.setMetalCost(12);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Advanced mine with robotic miners.");
      return tmp;
    }
    if (index == COMPONENT_ADVANCED_FACTORY) {
      tmp = new Building(index, "Advanced factory",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(2);
      tmp.setProdCost(30);
      tmp.setMetalCost(10);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Advanced production line with robotics.");
      return tmp;
    }
    if (index == COMPONENT_ADVANCED_LABORATORY) {
      tmp = new Building(index, "Advanced laboratory",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(2);
      tmp.setProdCost(24);
      tmp.setMetalCost(8);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Fully equiped science laboratory.");
      return tmp;
    }
    if (index == COMPONENT_FARMING_CENTER) {
      tmp = new Building(index, "Farming center",
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(3);
      tmp.setProdCost(30);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Group of automated farms.");
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_MINING_CENTER) {
      tmp = new Building(index, "Mining center",
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(3);
      tmp.setProdCost(30);
      tmp.setMetalCost(24);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Group of automated mines.");
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_MANUFACTURING_CENTER) {
      tmp = new Building(index, "Manufacturing center",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(3);
      tmp.setProdCost(40);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Group of automated factories.");
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_RESEARCH_CENTER) {
      tmp = new Building(index, "Research center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(3);
      tmp.setProdCost(36);
      tmp.setMetalCost(16);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Group of research laboratories.");
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_HYDROPODIC_FARMING_CENTER) {
      tmp = new Building(index, "Hydropodic farming center",
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(4);
      tmp.setProdCost(40);
      tmp.setMetalCost(30);
      tmp.setMaintenanceCost(0.33);
      tmp.setDescription("Hydropodic farms producing food.");
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_NANOBOT_MINING_CENTER) {
      tmp = new Building(index, "Nanobot mining center",
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(4);
      tmp.setProdCost(40);
      tmp.setMetalCost(36);
      tmp.setMaintenanceCost(0.33);
      tmp.setDescription("Mining done by millions of nanobots");
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_NANOBOT_MANUFACTURING_CENTER) {
      tmp = new Building(index, "Nanobot manufacturing center",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(4);
      tmp.setProdCost(50);
      tmp.setMetalCost(30);
      tmp.setMaintenanceCost(0.33);
      tmp.setDescription("Production lines full of nanobots.");
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_NEURAL_RESEARCH_CENTER) {
      tmp = new Building(index, "Neural research center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(4);
      tmp.setProdCost(48);
      tmp.setMetalCost(24);
      tmp.setMaintenanceCost(0.33);
      tmp.setDescription("Neural network in huge research center");
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_ANCIENT_LAB) {
      tmp = new Building(index, "Ancient lab",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);
      tmp.setProdCost(8);
      tmp.setMetalCost(3);
      tmp.setDescription("Ancient laboratory for science.");
      return tmp;
    }
    if (index == COMPONENT_ANCIENT_FACTORY) {
      tmp = new Building(index, "Ancient factory",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(1);
      tmp.setProdCost(8);
      tmp.setMetalCost(3);
      tmp.setDescription("Ancient production line.");
      return tmp;
    }
    if (index == COMPONENT_ANCIENT_TEMPLE) {
      tmp = new Building(index, "Ancient temple",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setCultBonus(1);
      tmp.setProdCost(6);
      tmp.setMetalCost(2);
      tmp.setDescription("Ancient temple for creating culture.");
      return tmp;
    }
    if (index == COMPONENT_ANCIENT_PALACE) {
      tmp = new Building(index, "Ancient palace",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setCultBonus(1);
      tmp.setHappiness(1);
      tmp.setProdCost(15);
      tmp.setMetalCost(8);
      tmp.setDescription("Ancient palace for ruling the planet");
      return tmp;
    }
    if (index == COMPONENT_BLACK_MONOLITH) {
      tmp = new Building(index, "Black monolith",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setCultBonus(2);
      tmp.setHappiness(-1);
      tmp.setProdCost(15);
      tmp.setMetalCost(30);
      tmp.setDescription("Unknown origin of Black monolith."
          + " Cause unhappiness in population.");
      return tmp;
    }
    if (index == COMPONENT_MATERIAL_REPLICATOR) {
      tmp = new Building(index, "Material replicator",
          Icons.getIconByName(Icons.ICON_METAL_ORE), BuildingType.MINE);
      tmp.setCultBonus(0);
      tmp.setHappiness(0);
      tmp.setProdCost(200);
      tmp.setMetalCost(40);
      tmp.setDescription("Massive material replicator.");
      tmp.setScientificAchievement(true);
      tmp.setSingleAllowed(true);
      tmp.setMaterialBonus(2);
      return tmp;
    }
    if (index == COMPONENT_ADVANCED_FURNACE) {
      tmp = new Building(index, "Advanced furnace",
          Icons.getIconByName(Icons.ICON_METAL), BuildingType.MINE);
      tmp.setCultBonus(0);
      tmp.setHappiness(0);
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setDescription("Advanced furnace produces metal and production.");
      tmp.setSingleAllowed(true);
      tmp.setMineBonus(1);
      tmp.setFactBonus(1);
      return tmp;
    }
    if (index == COMPONENT_MASSIVE_BLAST_FURNACE) {
      tmp = new Building(index, "Massive blast furnace",
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
      return tmp;
    }
    if (index == COMPONENT_PLANETARY_FURNACE) {
      tmp = new Building(index, "Planetary furnace",
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
      return tmp;
    }
    return tmp;
  }

  /**
   * Create planetary improvement
   * @param index for creating a new building
   * @return Building or null if not found with index
   */
  private static Building createPlanetaryImprovement(final int index) {
    Building tmp = null;
    if (index == COMPONENT_SPACE_PORT) {
      tmp = new Building(index, "Space port",
          Icons.getIconByName(Icons.ICON_STARBASE), BuildingType.MILITARY);
      tmp.setDescription("Allows building the space ships and orbitals.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.5);
      tmp.setFleetCapacityBonus(1);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_BARRACKS) {
      tmp = new Building(index, "Barracks",
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
      return tmp;
    }
    if (index == COMPONENT_TAX_CENTER) {
      tmp = new Building(index, "Tax center",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Makes population to pay taxes.");
      tmp.setProdCost(15);
      tmp.setMetalCost(5);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(1);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_MARKET_CENTER) {
      tmp = new Building(index, "Market center",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Market center has all sort of goods to sell.");
      tmp.setProdCost(12);
      tmp.setMetalCost(6);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(1);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_CULTURE_CENTER) {
      tmp = new Building(index, "Culture center",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription("Music, Arts, poetry, movies and video games.");
      tmp.setProdCost(18);
      tmp.setMetalCost(4);
      tmp.setMaintenanceCost(0.25);
      tmp.setCultBonus(1);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_TRADE_CENTER) {
      tmp = new Building(index, "Trade center",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Massive trade center for selling goods.");
      tmp.setProdCost(24);
      tmp.setMetalCost(12);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(2);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_EXTREME_SPORTS_CENTER) {
      tmp = new Building(index, "Extreme sports center",
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
      return tmp;
    }
    if (index == COMPONENT_RECYCLE_CENTER) {
      tmp = new Building(index, "Recycle center",
          Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
          BuildingType.FACTORY);
      tmp.setDescription(
          "When destroying buildings or ships 50%\nmetal is recycled.");
      tmp.setProdCost(15);
      tmp.setMetalCost(5);
      tmp.setMaintenanceCost(1);
      tmp.setRecycleBonus(50);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_RADIATION_DAMPENER) {
      tmp = new Building(index, "Radiation dampener",
          Icons.getIconByName(Icons.ICON_RADIATION), BuildingType.FACTORY);
      tmp.setDescription("Decrease planet radiation by one.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setHappiness(1);
      tmp.setMaintenanceCost(1);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_STOCK_MARKET) {
      tmp = new Building(index, "Stock market",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Planetary stock market, where stonks\n"
          + "rising to the orbit.");
      tmp.setProdCost(36);
      tmp.setMetalCost(24);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(3);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_GALACTIC_SPORTS_CENTER) {
      tmp = new Building(index, "Galactic sports center",
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
      return tmp;
    }
    if (index == COMPONENT_NEW_TECHNOLOGY_CENTER) {
      tmp = new Building(index, "New technology center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setDescription("New technology is studied and used in culture.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.5);
      tmp.setReseBonus(3);
      tmp.setCultBonus(1);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_VR_MOVIE_CENTER) {
      tmp = new Building(index, "VR movie center",
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription("Galactic VR movie studio, where big galatic\n"
          + "block buster are being made.");
      tmp.setProdCost(30);
      tmp.setMetalCost(30);
      tmp.setCredBonus(2);
      tmp.setCultBonus(2);
      tmp.setHappiness(1);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_ADVANCED_RECYCLE_CENTER) {
      tmp = new Building(index, "Advanced recycle center",
          Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH),
          BuildingType.FACTORY);
      tmp.setDescription(
          "When destroying buildings or ships 75%\nmetal is recycled.");
      tmp.setProdCost(25);
      tmp.setMetalCost(10);
      tmp.setMaintenanceCost(1);
      tmp.setRecycleBonus(75);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_GALACTIC_BANK) {
      tmp = new Building(index, "Galactic bank",
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Galactic bank is good business. Does stonks go all\n"
          + "the way to out space?");
      tmp.setProdCost(40);
      tmp.setMetalCost(20);
      tmp.setCredBonus(4);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_RADIATION_WELL) {
      tmp = new Building(index, "Radiation well",
          Icons.getIconByName(Icons.ICON_RADIATION), BuildingType.FACTORY);
      tmp.setDescription("Decrease planet radiation by one.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(1);
      tmp.setHappiness(1);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_SUPER_AI_CENTER) {
      tmp = new Building(index, "Super AI center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setDescription("Super AI making science and improving production.");
      tmp.setProdCost(60);
      tmp.setMetalCost(80);
      tmp.setMaintenanceCost(1);
      tmp.setReseBonus(4);
      tmp.setFactBonus(1);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_REPLICATOR_CENTER) {
      tmp = new Building(index, "Replicator center",
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setDescription("Replicated goods can sold with good profit.");
      tmp.setProdCost(40);
      tmp.setMetalCost(40);
      tmp.setCredBonus(2);
      tmp.setFactBonus(2);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_UNITED_GALAXY_TOWER) {
      tmp = new Building(index, "United Galaxy Tower",
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
      return tmp;
    }
    if (index == COMPONENT_SPACE_ACADEMY) {
      tmp = new Building(index, "Space academy",
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
      return tmp;
    }
    if (index == COMPONENT_BROADCASTING_ANTENNA) {
      tmp = new Building(index, "Broadcasting antenna",
          Icons.getIconByName(Icons.ICON_ANTENNA), BuildingType.CULTURE);
      tmp.setDescription("Broadcasting antenna for broadcasting culture.");
      tmp.setProdCost(40);
      tmp.setMetalCost(30);
      tmp.setCultBonus(1);
      tmp.setBroadcaster(true);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == COMPONENT_BROADCASTING_NETWORK) {
      tmp = new Building(index, "Broadcasting network",
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
      return tmp;
    }
    if (index == COMPONENT_CYBER_LAB) {
      tmp = new Building(index, "Cyber lab",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);
      tmp.setHappiness(1);
      tmp.setProdCost(15);
      tmp.setMetalCost(10);
      tmp.setSingleAllowed(true);
      tmp.setDescription("Cyber laboratory for research. Gives morale boost"
          + " for population.");
      return tmp;
    }
    if (index == COMPONENT_COLLECTIVE_RESEARCH_CENTER) {
      tmp = new Building(index, "Collective research center",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(2);
      tmp.setHappiness(1);
      tmp.setProdCost(25);
      tmp.setMetalCost(10);
      tmp.setSingleAllowed(true);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Collective research center for robotic minds.");
      return tmp;
    }
    if (index == COMPONENT_RESEARCH_MATRIX) {
      tmp = new Building(index, "Research matrix",
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(3);
      tmp.setHappiness(2);
      tmp.setProdCost(40);
      tmp.setMetalCost(15);
      tmp.setSingleAllowed(true);
      tmp.setMaintenanceCost(0.3);
      tmp.setDescription("Research matrix for robotic minds.");
      return tmp;
    }
    if (index == COMPONENT_ORBITAL_ELEVATOR_MK1) {
      tmp = new Building(index, "Orbital elevator Mk1",
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
      return tmp;
    }
    if (index == COMPONENT_ORBITAL_ELEVATOR_MK2) {
      tmp = new Building(index, "Orbital elevator Mk2",
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
      return tmp;
    }
    if (index == COMPONENT_ORBITAL_ELEVATOR_MK3) {
      tmp = new Building(index, "Orbital elevator Mk3",
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
      return tmp;
    }
    if (index == COMPONENT_ORBITAL_LIFT) {
      tmp = new Building(index, "Orbital lift",
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
      return tmp;
    }

    return tmp;
  }

}
