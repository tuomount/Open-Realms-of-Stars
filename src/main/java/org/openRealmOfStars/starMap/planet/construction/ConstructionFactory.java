package org.openRealmOfStars.starMap.planet.construction;

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
 * Construction factory
 * 
 */

public class ConstructionFactory {

  /**
   * Current maximum constructions for whole game.
   * Remember to increase this when new construction is added to game.
   * It should be one bigger than last index.
   */
  private static final int MAX_CONSTRUCTION = 3;
  
  /**
   * Create planetary construction with index
   * @param index For creating a new construction
   * @return Construction if index found otherwise null
   */
  public static Construction create(int index) {
    Construction tmp = null;
    switch (index) {
    case 0 : tmp = createConstruction(index); break; // Extra credit
    case 1 : tmp = createConstruction(index); break; // Extra culture
    case 2 : tmp = createConstruction(index); break; // Mechion citizen
    }
    return tmp;
  }
  
  public final static String MECHION_CITIZEN = "Mechion citizen";
  public final static String EXTRA_CREDIT = "Extra credit";
  public final static String EXTRA_CULTURE = "Extra culture";
  
  /**
   * Create planetary construction with matching name
   * @param name Construction name
   * @return Construction or null if not found
   */
  public static Construction createByName(String name) {
    Construction tmp = null;
    for (int i=0;i<MAX_CONSTRUCTION;i++) {
      tmp = create(i);
      if ((tmp != null) && (tmp.getName().equalsIgnoreCase(name))) {
        return tmp;
      }
    }
    return null;
  }

  /**
   * Create construction
   * @param index for creating a new construction
   * @return Construction or null if not found with index
   */
  private static Construction createConstruction(int index) {
    Construction tmp = null;
    if (index == 0) {
      tmp = new Construction(EXTRA_CREDIT, Icons.getIconByName(Icons.ICON_CREDIT));
      tmp.setProdCost(10);
      tmp.setMetalCost(0);
      tmp.setDescription("Build extra credits");
      return tmp;
    } 
    if (index == 1) {
      tmp = new Construction(EXTRA_CULTURE, Icons.getIconByName(Icons.ICON_CULTURE));
      tmp.setProdCost(10);
      tmp.setMetalCost(0);
      tmp.setDescription("Build extra culture");
      return tmp;
    } 
    if (index == 2) {
      tmp = new Construction(MECHION_CITIZEN, Icons.getIconByName(Icons.ICON_PEOPLE));
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setDescription("Build new mechion citizen");
      return tmp;
    }
    return tmp;
  }

}
