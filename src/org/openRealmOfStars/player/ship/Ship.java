package org.openRealmOfStars.player.ship;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.starMap.planet.construction.Construction;

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
 * Ship class for actual instance of a ship
 * 
 */

public class Ship extends Construction {

  
  /**
   * Ship's hull
   */
  private ShipHull hull;
  
  /**
   * Ship's component list
   */
  private ArrayList<ShipComponent> components;
  
  /**
   * Hit points per component
   */
  private int[] hullPoints;
  
  /**
   * Current shield level
   */
  private int shield;
  
  /**
   * Current armor level
   */
  private int armor;

  /**
   * Ship's image
   */
  private BufferedImage image;
  /**
   * Constructor for a ship
   * @param design from where actual ship is created
   */
  public Ship(ShipDesign design) {
    super(design.getName(), Icons.getIconByName(Icons.ICON_HULL_TECH));
    prodCost = design.getCost();
    metalCost = design.getMetalCost();
    hull = design.getHull();
    components = new ArrayList<>();
    ShipComponent[] designComponents = design.getComponentList();
    hullPoints = new int[designComponents.length];
    for (int i=0;i<designComponents.length;i++) {
      components.add(designComponents[i]);
      hullPoints[i] = design.getHull().getSlotHull();
    }
    setShield(design.getTotalShield());
    setArmor(design.getTotalArmor());
    image = ShipImage.scaleTo32x32(hull.getImage());
    setDescription(design.getDesignInfo());
  }
  
  /**
   * Check if certain component has energy or not. Returns true if component has
   * energy. This also checks that component is functional.
   * @param index Component index
   * @return true if has energy
   */
  public boolean hasComponentEnergy(int index) {
    int energy = getTotalEnergy();
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getEnergyResource()>0) {
        energy = energy -comp.getEnergyResource();
      }
      if (index == i && energy >= 0) {
        return true;
      }
      if (index == i && energy < 0) {
        return false;
      }
    }    
    return false;
  }
  
  /**
   * Get total energy form current component status
   * @return Total energy
   */
  public int getTotalEnergy() {
    int energy = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getEnergyResource()>0) {
        energy = energy +comp.getEnergyResource();
      }
    }
    return energy;
  }
  
  /**
   * Get Speed depending on hullpoints and energy level
   * @return Speed
   */
  public int getSpeed() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.ENGINE
          && hasComponentEnergy(i)) {
        return comp.getSpeed();
      }
    }
    return 0;
  }

  /**
   * Get tactic Speed depending on hullpoints and energy level
   * @return Speed
   */
  public int getTacticSpeed() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.ENGINE
          && hasComponentEnergy(i)) {
        return comp.getTacticSpeed();
      }
    }
    return 0;
  }

  /**
   * Get FTL Speed depending on hullpoints and energy level
   * @return Speed
   */
  public int getFtlSpeed() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.ENGINE
          && hasComponentEnergy(i)) {
        return comp.getFtlSpeed();
      }
    }
    return 0;
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

  public String getName() {
    return name;
  }

  public ShipHull getHull() {
    return hull;
  }

  public BufferedImage getImage() {
    return image;
  }

  /**
   * Get maximum shield for ship. This is very useful information
   * when ship is being design and shown on ship view
   * @return Maximum shield
   */
  public int getTotalShield() {
    int shield = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getDefenseValue() > 0 && comp.getType() == ShipComponentType.SHIELD) {
        shield = shield +comp.getDefenseValue();
      }
    }
    return shield;
  }

  /**
   * Get maximum armor for ship. This is very useful information
   * when ship is being design and shown on ship view
   * @return Maximum armor
   */
  public int getTotalArmor() {
    int armor = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getDefenseValue() > 0 && comp.getType() == ShipComponentType.ARMOR) {
        armor = armor +comp.getDefenseValue();
      }
    }
    return armor;
  }

  public int getShield() {
    return shield;
  }

  public void setShield(int shield) {
    this.shield = shield;
  }

  public int getArmor() {
    return armor;
  }

  public void setArmor(int armor) {
    this.armor = armor;
  }


}
