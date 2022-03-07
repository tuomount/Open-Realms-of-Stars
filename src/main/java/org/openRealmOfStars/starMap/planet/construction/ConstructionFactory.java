package org.openRealmOfStars.starMap.planet.construction;

import org.openRealmOfStars.gui.icons.Icons;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2022 Tuomo Untinen
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

public final class ConstructionFactory {

  /**
   * Hiding the constructor
   */
  private ConstructionFactory() {
    // Nothing to do
  }

  /**
   * Current maximum constructions for whole game.
   * Remember to increase this when new construction is added to game.
   * It should be one bigger than last index.
   */
  private static final int MAX_CONSTRUCTION = 4;

  /**
   * Construction extra credit
   */
  private static final int CONSTRUCTION_EXTRA_CREDIT = 0;

  /**
   * Construction extra culture
   */
  private static final int CONSTRUCTION_EXTRA_CULTURE = 1;

  /**
   * Construction mechion citizen
   */
  private static final int CONSTRUCTION_MECHION_CITIZEN = 2;
  /**
   * Construction synthdroid citizen
   */
  private static final int CONSTRUCTION_SYNTHDROID_CITIZEN = 3;

  /**
   * Create planetary construction with index
   * @param index For creating a new construction
   * @return Construction if index found otherwise null
   */
  public static Construction create(final int index) {
    Construction tmp = null;
    switch (index) {
    case CONSTRUCTION_EXTRA_CREDIT:
      tmp = createConstruction(index);
      break; // Extra credit
    case CONSTRUCTION_EXTRA_CULTURE:
      tmp = createConstruction(index);
      break; // Extra culture
    case CONSTRUCTION_MECHION_CITIZEN:
      tmp = createConstruction(index);
      break; // Mechion citizen
    case CONSTRUCTION_SYNTHDROID_CITIZEN:
      tmp = createConstruction(index);
      break; // Synthdroid citizen
    default:
      throw new IllegalArgumentException("Unknown construction: " + index);
    }
    return tmp;
  }

  /**
   * Mechion citizen construction
   */
  public static final String MECHION_CITIZEN = "Mechion citizen";
  /**
   * Extra credit construction
   */
  public static final String EXTRA_CREDIT = "Extra credit";
  /**
   * Extra culture construction
   */
  public static final String EXTRA_CULTURE = "Extra culture";
  /**
   * Synthdroid citizen construction
   */
  public static final String SYNTHDROID_CITIZEN = "Synthdroid citizen";

  /**
   * Create planetary construction with matching name
   * @param name Construction name
   * @return Construction or null if not found
   */
  public static Construction createByName(final String name) {
    Construction tmp = null;
    for (int i = 0; i < MAX_CONSTRUCTION; i++) {
      tmp = create(i);
      if (tmp != null && tmp.getName().equalsIgnoreCase(name)) {
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
  private static Construction createConstruction(final int index) {
    Construction tmp = null;
    if (index == CONSTRUCTION_EXTRA_CREDIT) {
      tmp = new Construction(EXTRA_CREDIT,
          Icons.getIconByName(Icons.ICON_CREDIT));
      tmp.setProdCost(10);
      tmp.setMetalCost(0);
      tmp.setDescription("Build extra credits");
      return tmp;
    }
    if (index == CONSTRUCTION_EXTRA_CULTURE) {
      tmp = new Construction(EXTRA_CULTURE,
          Icons.getIconByName(Icons.ICON_CULTURE));
      tmp.setProdCost(10);
      tmp.setMetalCost(0);
      tmp.setDescription("Build extra culture");
      return tmp;
    }
    if (index == CONSTRUCTION_MECHION_CITIZEN) {
      tmp = new Construction(MECHION_CITIZEN,
          Icons.getIconByName(Icons.ICON_PEOPLE));
      tmp.setProdCost(20);
      tmp.setMetalCost(20);
      tmp.setDescription("Build new mechion citizen");
      return tmp;
    }
    if (index == CONSTRUCTION_SYNTHDROID_CITIZEN) {
      tmp = new Construction(SYNTHDROID_CITIZEN,
          Icons.getIconByName(Icons.ICON_PEOPLE));
      tmp.setProdCost(30);
      tmp.setMetalCost(0);
      tmp.setDescription("Clone new synthdroid citizen");
      return tmp;
    }
    return tmp;
  }

}
