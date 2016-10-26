package org.openRealmOfStars.starMap.planet;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingType;

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
 * Building factory
 * 
 */
public class BuildingFactory {

  /**
   * Current maximum buildings for whole game.
   * Remember to increase this when new building is added to game.
   * It should be one bigger than last index.
   */
  private static final int MAX_BUILDING = 42;
  
  /**
   * Create planetary building with index
   * @param index For creating a  new building
   * @return Building if index found otherwise null
   */
  public static Building create(int index) {
    Building tmp = null;
    switch (index) {
    case 0 : tmp = createProductionFacility(index); break; // Basic mine
    case 1 : tmp = createProductionFacility(index); break; // Basic farm
    case 2 : tmp = createProductionFacility(index); break; // Basic factory
    case 3 : tmp = createProductionFacility(index); break; // Basic lab
    case 4 : tmp = createPlanetaryImprovement(index); break; // Space port
    case 5 : tmp = createPlanetaryImprovement(index); break; // Barracks
    case 6 : tmp = createPlanetaryImprovement(index); break; // Tax center
    case 7 : tmp = createProductionFacility(index); break; // Advanced farm
    case 8 : tmp = createProductionFacility(index); break; // Advanced mine
    case 9 : tmp = createProductionFacility(index); break; // Advanced factory
    case 10: tmp = createProductionFacility(index); break; // Advanced laboratory
    case 11: tmp = createPlanetaryImprovement(index); break; // Market center
    case 12: tmp = createPlanetaryImprovement(index); break; // Culture center
    case 13: tmp = createPlanetaryImprovement(index); break; // Trade center
    case 14: tmp = createPlanetaryImprovement(index); break; // Extreme sports center
    case 15: tmp = createPlanetaryImprovement(index); break; // Recycle center
    case 16: tmp = createProductionFacility(index); break; // Farming center
    case 17: tmp = createProductionFacility(index); break; // Mining center
    case 18: tmp = createProductionFacility(index); break; // Manufacturing center
    case 19: tmp = createPlanetaryImprovement(index); break; // Radiation dampener
    case 20: tmp = createProductionFacility(index); break; // Research center
    case 21: tmp = createPlanetaryImprovement(index); break; // Stock market
    case 22: tmp = createPlanetaryImprovement(index); break; // Galactic sports center
    case 23: tmp = createPlanetaryImprovement(index); break; // New technology center
    case 24: tmp = createPlanetaryImprovement(index); break; // VR movie center
    case 25: tmp = createPlanetaryImprovement(index); break; // Advanced recycle center
    case 26: tmp = createPlanetaryImprovement(index); break; // Galactic bank
    case 27: tmp = createPlanetaryImprovement(index); break; // Radiation well
    case 28: tmp = createProductionFacility(index); break; // Hydropodic farming center
    case 29: tmp = createProductionFacility(index); break; // Nanobot mining center
    case 30: tmp = createProductionFacility(index); break; // Nanobot manufacturing center
    case 31: tmp = createProductionFacility(index); break; // Neural reserach center
    case 32: tmp = createPlanetaryImprovement(index); break; // Super AI center
    case 33: tmp = createPlanetaryImprovement(index); break; // Replicator center
    case 34: tmp = createMilitaryFacility(index); break; // Planetary defense turret Mk1
    case 35: tmp = createMilitaryFacility(index); break; // Planetary defense turret Mk2
    case 36: tmp = createMilitaryFacility(index); break; // Planetary defense turret Mk3
    case 37: tmp = createMilitaryFacility(index); break; // Planetary scanner Mk1
    case 38: tmp = createMilitaryFacility(index); break; // Planetary scanner Mk2
    case 39: tmp = createMilitaryFacility(index); break; // Planetary scanner Mk3
    case 40: tmp = createMilitaryFacility(index); break; // Planetary scanner Mk4
    case 41: tmp = createMilitaryFacility(index); break; // Planetary scanner Mk5
    }
    return tmp;
  }
  
  /**
   * Create planetary building with matching name
   * @param name Building name
   * @return Building or null if not found
   */
  public static Building createByName(String name) {
    Building tmp = null;
    for (int i=0;i<MAX_BUILDING;i++) {
      tmp = create(i);
      if ((tmp != null) && (tmp.getName().equalsIgnoreCase(name))) {
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
  private static Building createMilitaryFacility(int index) {
    Building tmp = null;
    if (index == 34) {
      tmp = new Building(index, "Planetary defense turret Mk1", 
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET), BuildingType.MILITARY);
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setDescription("Big and powerful defense turret");
      tmp.setDefenseDamage(3);
      return tmp;
    }
    if (index == 35) {
      tmp = new Building(index, "Planetary defense turret Mk2", 
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET), BuildingType.MILITARY);
      tmp.setProdCost(50);
      tmp.setMetalCost(50);
      tmp.setDescription("Huge defense turret");
      tmp.setDefenseDamage(6);
      return tmp;
    }
    if (index == 36) {
      tmp = new Building(index, "Planetary defense turret Mk3", 
          Icons.getIconByName(Icons.ICON_PLANETARY_TURRET), BuildingType.MILITARY);
      tmp.setProdCost(70);
      tmp.setMetalCost(70);
      tmp.setDescription("Biggest and meanest defense turret");
      tmp.setDefenseDamage(9);
      return tmp;
    }
    if (index == 37) {
      tmp = new Building(index, "Planetary scanner Mk1", 
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(16);
      tmp.setMetalCost(16);
      tmp.setDescription("Planetary scanner Mk1");
      tmp.setScanRange(3);
      tmp.setScanCloakingDetection(40);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == 38) {
      tmp = new Building(index, "Planetary scanner Mk2", 
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(24);
      tmp.setMetalCost(24);
      tmp.setDescription("Planetary scanner Mk2");
      tmp.setScanRange(4);
      tmp.setScanCloakingDetection(80);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == 39) {
      tmp = new Building(index, "Planetary scanner Mk3", 
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(40);
      tmp.setMetalCost(40);
      tmp.setDescription("Planetary scanner Mk3");
      tmp.setScanRange(5);
      tmp.setScanCloakingDetection(100);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == 40) {
      tmp = new Building(index, "Planetary scanner Mk4", 
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(60);
      tmp.setMetalCost(60);
      tmp.setDescription("Planetary scanner Mk4");
      tmp.setScanRange(6);
      tmp.setScanCloakingDetection(120);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    if (index == 41) {
      tmp = new Building(index, "Planetary scanner Mk5", 
          Icons.getIconByName(Icons.ICON_LR_SCANNER), BuildingType.MILITARY);
      tmp.setProdCost(80);
      tmp.setMetalCost(80);
      tmp.setDescription("Planetary scanner Mk5");
      tmp.setScanRange(7);
      tmp.setScanCloakingDetection(140);
      tmp.setSingleAllowed(true);
      return tmp;
    }
    return tmp;
  }

  
  /**
   * Create production facility
   * @param index for creating a new building
   * @return Building or null if not found with index
   */
  private static Building createProductionFacility(int index) {
    Building tmp = null;
    if (index == 0) {
      tmp = new Building(index, "Basic mine", 
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(1);
      tmp.setProdCost(10);
      tmp.setMetalCost(6);
      tmp.setDescription("Small scale automatic mine");
      return tmp;
    } 
    if (index == 1) {
      tmp = new Building(index, "Basic farm", 
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(1);
      tmp.setProdCost(10);
      tmp.setMetalCost(4);
      tmp.setDescription("Automatic farm to produce food.");
      return tmp;
    } 
    if (index == 2) {
      tmp = new Building(index, "Basic factory", 
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(1);
      tmp.setProdCost(15);
      tmp.setMetalCost(5);
      tmp.setDescription("Single mass production line.");
      return tmp;
    } 
    if (index == 3) {
      tmp = new Building(index, "Basic lab", 
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);
      tmp.setProdCost(12);
      tmp.setMetalCost(4);
      tmp.setDescription("Basic laboratory for science.");
      return tmp;
    } 
    if (index == 7) {
      tmp = new Building(index, "Advanced farm", 
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(2);
      tmp.setProdCost(20);
      tmp.setMetalCost(8);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Advanced automation farm.");
      return tmp;
    } 
    if (index == 8) {
      tmp = new Building(index, "Advanced mine", 
          Icons.getIconByName(Icons.ICON_MINE), BuildingType.MINE);
      tmp.setMineBonus(2);
      tmp.setProdCost(20);
      tmp.setMetalCost(12);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Advanced mine with robotic miners.");
      return tmp;
    } 
    if (index == 9) {
      tmp = new Building(index, "Advanced factory", 
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(2);
      tmp.setProdCost(30);
      tmp.setMetalCost(10);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Advanced production line with robotics.");
      return tmp;
    } 
    if (index == 10) {
      tmp = new Building(index, "Advanced laboratory", 
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(2);
      tmp.setProdCost(24);
      tmp.setMetalCost(8);
      tmp.setMaintenanceCost(0.25);
      tmp.setDescription("Fully equiped science laboratory.");
      return tmp;
    } 
    if (index == 16) {
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
    if (index == 17) {
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
    if (index == 18) {
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
    if (index == 20) {
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
    if (index == 28) {
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
    if (index == 29) {
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
    if (index == 30) {
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
    if (index == 31) {
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
    return tmp;
  }
  
  /**
   * Create planetary improvement
   * @param index for creating a new building
   * @return Building or null if not found with index
   */
  private static Building createPlanetaryImprovement(int index) {
    Building tmp = null;
    if (index == 4) {
      tmp = new Building(index, "Space port", 
          Icons.getIconByName(Icons.ICON_STARBASE), BuildingType.MILITARY);
      tmp.setDescription("Allows building the space ships.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.5);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 5) {
      tmp = new Building(index, "Barracks", 
          Icons.getIconByName(Icons.ICON_TROOPS), BuildingType.MILITARY);
      tmp.setDescription("Population fights better against invaders.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(0.5);
      tmp.setBattleBonus(50);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 6) {
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
    if (index == 11) {
      tmp = new Building(index, "Market center", 
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("All sort of goods are sold.");
      tmp.setProdCost(12);
      tmp.setMetalCost(6);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(1);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 12) {
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
    if (index == 13) {
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
    if (index == 14) {
      tmp = new Building(index, "Extreme sports center", 
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription("Violent sports is a culture too.");
      tmp.setProdCost(30);
      tmp.setMetalCost(22);
      tmp.setMaintenanceCost(0.5);
      tmp.setCultBonus(2);
      tmp.setBattleBonus(10);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 15) {
      tmp = new Building(index, "Recycle center", 
          Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH), BuildingType.FACTORY);
      tmp.setDescription("When destroying buildings or ships 50%\nmetal is recycled.");
      tmp.setProdCost(15);
      tmp.setMetalCost(5);
      tmp.setMaintenanceCost(1);
      tmp.setRecycleBonus(50);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 19) {
      tmp = new Building(index, "Radiation dampener", 
          Icons.getIconByName(Icons.ICON_RADIATION), BuildingType.FACTORY);
      tmp.setDescription("Decrease planet radiation by one.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(1);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 21) {
      tmp = new Building(index, "Stock market", 
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Planetary stock market");
      tmp.setProdCost(36);
      tmp.setMetalCost(24);
      tmp.setMaintenanceCost(0);
      tmp.setCredBonus(3);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 22) {
      tmp = new Building(index, "Galactic sports center", 
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription("Galactic sports stadium where \nbrutal football is played.");
      tmp.setProdCost(40);
      tmp.setMetalCost(30);
      tmp.setMaintenanceCost(1);
      tmp.setCultBonus(3);
      tmp.setBattleBonus(20);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 23) {
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
    if (index == 24) {
      tmp = new Building(index, "VR movie center", 
          Icons.getIconByName(Icons.ICON_CULTURE), BuildingType.CULTURE);
      tmp.setDescription("Galactic VR movie studio");
      tmp.setProdCost(30);
      tmp.setMetalCost(30);
      tmp.setCredBonus(2);
      tmp.setCultBonus(2);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 25) {
      tmp = new Building(index, "Advanced recycle center", 
          Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH), BuildingType.FACTORY);
      tmp.setDescription("When destroying buildings or ships 75%\nmetal is recycled.");
      tmp.setProdCost(25);
      tmp.setMetalCost(10);
      tmp.setMaintenanceCost(1);
      tmp.setRecycleBonus(75);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 26) {
      tmp = new Building(index, "Galactic bank", 
          Icons.getIconByName(Icons.ICON_CREDIT), BuildingType.CREDIT);
      tmp.setDescription("Galactic bank is good business.");
      tmp.setProdCost(40);
      tmp.setMetalCost(20);
      tmp.setCredBonus(4);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 27) {
      tmp = new Building(index, "Radiation well", 
          Icons.getIconByName(Icons.ICON_RADIATION), BuildingType.FACTORY);
      tmp.setDescription("Decrease planet radiation by one.");
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setMaintenanceCost(1);
      tmp.setSingleAllowed(true);
      return tmp;
    } 
    if (index == 32) {
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
    if (index == 33) {
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
    return tmp;
  }

}
