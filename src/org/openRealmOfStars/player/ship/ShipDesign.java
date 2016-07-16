package org.openRealmOfStars.player.ship;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
 * Ship Design class, design is used to create a ship. Design is also
 * linked to Ship Stat class
 * 
 */
public class ShipDesign {

  /**
   * Ship Design name
   */
  private String name;
  
  /**
   * Ship's hull
   */
  private ShipHull hull;
  
  /**
   * Ship's component list
   */
  private ArrayList<ShipComponent> components;

  /**
   * Ship's image
   */
  private BufferedImage image;

  public ShipDesign(ShipHull hull) {
    setHull(hull);
    components = new ArrayList<>();
    name = "Design";
  }
  
  /**
   * Get number of components in ship's component list
   * @return number of components
   */
  public int getNumberOfComponents() {
    return components.size();
  }
  
  /**
   * Get ship component by index. Can return null if index is out list.
   * @param index Component list index
   * @return ShipComponent or null
   */
  public ShipComponent getComponent(int index) {
    if (index >= 0 && index < components.size()) {
      return components.get(index);
    }
    return null;
  }
  
  /**
   * Add new component to list
   * @param comp ShipComponent to add
   */
  public void addComponent(ShipComponent comp) {
    components.add(comp);
  }
  

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ShipHull getHull() {
    return hull;
  }

  public void setHull(ShipHull hull) {
    if (hull != null) {
      this.hull = hull;
    }
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }
}
