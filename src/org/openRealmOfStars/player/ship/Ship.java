package org.openRealmOfStars.player.ship;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.openRealmOfStars.gui.GuiStatics;
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
   * Colonist cargo
   */
  private int colonist;
  /**
   * Metal cargo
   */
  private int metal;
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
    setColonist(0);
    setMetal(0);
  }
  
  /**
   * Get hull points for certain component
   * @param index Component index
   * @return Hull points
   */
  public int getHullPointForComponent(int index) {
    if (index >= 0 && index < hullPoints.length) {
      return hullPoints[index];
    }
    return 0;
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
   * Are there any weapons left
   * @return true if there are weapons left
   */
  public boolean hasWeapons() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && hasComponentEnergy(i)) {
        if (comp.getType() == ShipComponentType.WEAPON_BEAM ||
            comp.getType() == ShipComponentType.WEAPON_ECM_TORPEDO ||
            comp.getType() == ShipComponentType.WEAPON_HE_MISSILE ||
            comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO ||
            comp.getType() == ShipComponentType.WEAPON_RAILGUN) {
          return true;
        }
      }
    }
    return false;
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
   * Get ship' initiative for combat. Bigger value is better in combat
   * @return Initiative
   */
  public int getInitiative() {
    int result = 0;
    switch (hull.getSize()) {
    case SMALL:{ 
      result = 12; break; }
    case MEDIUM:{ 
      result = 8; break; }
    case LARGE:{ 
      result = 4; break; }
    case HUGE:{ 
      result = 0; break; }
    }
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.ENGINE
          && hasComponentEnergy(i)) {
        result = result +comp.getSpeed()+comp.getTacticSpeed();
      }
      if (hullPoints[i] > 0 && hasComponentEnergy(i) && comp.getInitiativeBoost()>0) {
        result = result +comp.getInitiativeBoost();
      }
    }
    int emptySpace = this.hull.getMaxSlot()-components.size();
    switch (emptySpace) {
    case 1: 
    case 0: {break;}
    case 2: 
    case 3: {result = result +1; break;}
    case 4: {result = result +2; break;}
    case 5: {result = result +3; break;}
    case 6:
    case 7:
    case 8:
    case 9:
    case 11:
    case 10: {result = result +4; break;}
    }
    return result;
  }

  /**
   * Get Scanner level
   * @return scanner Lvl
   */
  public int getScannerLvl() {
    int result = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.SCANNER
          && hasComponentEnergy(i) && comp.getScannerRange() > result) {
        result = comp.getScannerRange();
      }
    }
    return result;
  }

  /**
   * Get Scanner cloak detection level
   * @return scanner detection level 
   */
  public int getScannerDetectionLvl() {
    int result = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.SCANNER
          && hasComponentEnergy(i) && comp.getCloakDetection() > result) {
        result = comp.getCloakDetection();
      }
    }
    return result;
  }

  /**
   * Get number of components in ship's component list
   * @return number of components
   */
  public int getNumberOfComponents() {
    return components.size();
  }
  
  /**
   * Is Ship colony ship or not
   * @return True if ship has functional colony module, otherwise false
   */
  public boolean isColonyShip() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.COLONY_MODULE
          && hasComponentEnergy(i) && getColonist() > 0) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Get Ships damage level as color
   * @return one of these:
   * GuiStatics.COLOR_GREEN_TEXT 
   * GuiStatics.COLOR_YELLOW_TEXT
   * GuiStatics.COLOR_RED_TEXT
   */
  public Color getDamageColor() {
    int damageLvl = 0;
    for (int i=0;i<hullPoints.length;i++) {
      if (hullPoints[i] <hull.getSlotHull() && hullPoints[i] > 0 && damageLvl < 1) {
        damageLvl = 1;
      }
      if (hullPoints[i] == 0 && damageLvl < 2) {
        damageLvl = 2;
      }
    }
    switch (damageLvl) {
    case 0: return GuiStatics.COLOR_GREEN_TEXT;
    case 1: return GuiStatics.COLOR_YELLOW_TEXT;
    case 2: return GuiStatics.COLOR_RED_TEXT;
    default: return GuiStatics.COLOR_RED_TEXT;
    }

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

  /**
   * Get Total amount of hull point
   * @return Maximum hull points
   */
  public int getMaxHullPoints() {
    return hull.getSlotHull()*hull.getMaxSlot();
  }

  /**
   * Get current hull points
   * @return hull points
   */
  public int getHullPoints() {
    int value = 0;
    for (int i=0;i<hullPoints.length;i++) {
      value = value +hullPoints[i];
    }
    return value;
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

  public int getColonist() {
    return colonist;
  }

  public void setColonist(int colonist) {
    this.colonist = colonist;
  }

  public int getMetal() {
    return metal;
  }

  public void setMetal(int metal) {
    this.metal = metal;
  }

  /**
   * How much metal can be fit to cargo space
   * @return Cargo room for metal
   */
  public int getFreeCargoMetal() {
    int result = 0;
    if (hull.getHullType() == ShipHullType.FREIGHTER) {
      result = hull.getMaxSlot()-getNumberOfComponents();
      result = result -getColonist()/2-getMetal()/10-getColonist()%2;
      result = result *10;
    }
    return result;
  }
  
  /**
   * How many colonists can be fit to cargo space
   * @return Cargo room for colonists
   */
  public int getFreeCargoColonists() {
    int result = 0;
    if (hull.getHullType() == ShipHullType.FREIGHTER) {
      result = hull.getMaxSlot()-getNumberOfComponents();
      result = result -getColonist()/2-getMetal()/10;
      result = result *2;
    }
    return result;
  }

}
