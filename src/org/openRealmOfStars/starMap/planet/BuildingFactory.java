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
   * Rememeber to increase this when new building is added to game.
   * It should be one bigger than last index.
   */
  private static final int MAX_BUILDING = 5;
  
  /**
   * Create planetary building with index
   * @param index For creating a  new building
   * @return Building if index found otherwise null
   */
  public static Building create(int index) {
    Building tmp = null;
    switch (index) {
    case 0: tmp = createProductionFacility(index); break; // Basic mine
    case 1: tmp = createProductionFacility(index); break; // Basic farm
    case 2: tmp = createProductionFacility(index); break; // Basic factory
    case 3: tmp = createProductionFacility(index); break; // Basic lab
    case 4: tmp = createPlanetaryImprovement(index); break; // Space port
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
      tmp.setDescription("Basic mine that mine 1 metal per turn.");
      return tmp;
    } 
    if (index == 1) {
      tmp = new Building(index, "Basic farm", 
          Icons.getIconByName(Icons.ICON_FARM), BuildingType.FARM);
      tmp.setFarmBonus(1);;
      tmp.setDescription("Basic farm that farms 1 food per turn.");
      return tmp;
    } 
    if (index == 2) {
      tmp = new Building(index, "Basic factory", 
          Icons.getIconByName(Icons.ICON_FACTORY), BuildingType.FACTORY);
      tmp.setFactBonus(1);;
      tmp.setDescription("Basic factory that produces 1 production per turn.");
      return tmp;
    } 
    if (index == 3) {
      tmp = new Building(index, "Basic lab", 
          Icons.getIconByName(Icons.ICON_RESEARCH), BuildingType.RESEARCH);
      tmp.setReseBonus(1);;
      tmp.setDescription("Basic lab that creates 1 science point per turn.");
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
          Icons.getIconByName(Icons.ICON_DEATH), BuildingType.MILITARY);
      tmp.setDescription("Space port allows building the space ships.");
      return tmp;
    } 
    return tmp;
  }

}
