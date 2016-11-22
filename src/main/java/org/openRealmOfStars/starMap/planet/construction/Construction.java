package org.openRealmOfStars.starMap.planet.construction;

import org.openRealmOfStars.gui.icons.Icon16x16;

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
 * Base class for planetary construction including buildings and spaceships.
 *
 */

public class Construction {

  /**
   * Construction name
   */
  private String name;

  /**
   * Icon for construction
   */
  private Icon16x16 icon;

  /**
   * Longer description about the construction
   */
  private String description;

  /**
   * Production cost
   */
  private int prodCost;

  /**
   * Metal cost
   */
  private int metalCost;

  /**
   * Constructor for Construction
   * @param name Construction name
   * @param icon Icon for construction
   */
  public Construction(final String name, final Icon16x16 icon) {
    this.name = name;
    this.icon = icon;
    this.description = "";
    this.prodCost = 1;
    this.metalCost = 1;
  }

  /**
   * Get full description of construction
   * @return Full description of construct as a string
   */
  public String getFullDescription() {
    return getName() + "\n" + getDescription() + "\n" + "Cost: Prod.:"
        + getProdCost() + " Metal:" + getMetalCost();
  }

  @Override
  public String toString() {
    return getName();
  }

  /**
   * Get Construction name as a String.
   * @return Construction name
   */
  public String getName() {
    return name;
  }

  /**
   * Set construction name
   * @param name Construction name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Get icon 16x16 pixels
   * @return Icon for construction
   */
  public Icon16x16 getIcon() {
    return icon;
  }

  /**
   * Set icon for construction. 
   * @param icon Icon16x16
   */
  public void setIcon(final Icon16x16 icon) {
    this.icon = icon;
  }

  /**
   * Get Construction description.
   * @return Description as a String
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set construction description. This is free text about the construction.
   * @param description as a free text
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * @return the prodCost
   */
  public int getProdCost() {
    return prodCost;
  }

  /**
   * @param prodCost the prodCost to set
   */
  public void setProdCost(final int prodCost) {
    this.prodCost = prodCost;
  }

  /**
   * @return the metalCost
   */
  public int getMetalCost() {
    return metalCost;
  }

  /**
   * @param metalCost the metalCost to set
   */
  public void setMetalCost(final int metalCost) {
    this.metalCost = metalCost;
  }

}
