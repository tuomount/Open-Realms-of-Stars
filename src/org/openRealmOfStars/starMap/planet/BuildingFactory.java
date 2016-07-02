package org.openRealmOfStars.starMap.planet;

import org.openRealmOfStars.gui.icons.Icons;

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
  private static final int MAX_BUILDING = 16;
  
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
      tmp.setFarmBonus(1);;
      tmp.setProdCost(10);
      tmp.setMetalCost(4);
      tmp.setDescription("Automatic farm to produce food.");
      return tmp;
    } 
    if (index == 2) {
      tmp = new Building(index, "Basic factory", 
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(1);;
      tmp.setProdCost(15);
      tmp.setMetalCost(5);
      tmp.setDescription("Single mass production line.");
      return tmp;
    } 
    if (index == 3) {
      tmp = new Building(index, "Basic lab", 
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);;
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
      tmp.setFactBonus(2);;
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
      tmp.setMaintenanceCost(1.0);
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
      tmp.setMaintenanceCost(1);
      tmp.setCultBonus(2);
      tmp.setBattleBonus(110);
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
    return tmp;
  }

}
