package org.openRealmOfStars.starMap.planet;

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
 * Class for planetary building or improvement
 * 
 */
public class Building {
  
  /**
   * Building name
   */
  private String name;
  /**
   * Icon for building
   */
  private Icon16x16 icon;
  /**
   * Building type
   */
  private BuildingType type;
  /**
   * Unique index for factories and saving the game
   */
  private int index;
  /**
   * Longer description about the building
   */
  private String description;
  
  /**
   * Bonus for farming
   */
  private int farmBonus;
  /**
   * Bonus for mining
   */
  private int mineBonus;
  /**
   * Bonus for production
   */
  private int factBonus;
  /**
   * Bonus for culture
   */
  private int cultBonus;
  /**
   * Bonus for research
   */
  private int reseBonus;
  
  /**
   * Bonus for credits
   */
  private int credBonus;
  
  /**
   * Production cost
   */
  private int prodCost;
  
  /**
   * Metal cost
   */
  private int metalCost;
  
  /**
   * Maintenance Cost
   */
  private double maintenanceCost;
  
  /**
   * Only single bulding allowed per planet
   */
  private boolean singleAllowed;
  
  /**
   * Building's battle bonus
   */
  private int battleBonus;

  /**
   * Building's recycle bonus
   */
  private int recycleBonus;

  /**
   * Construct building for planet
   * @param index Unique number for building
   * @param name Building name
   * @param icon Icon to use next to the building
   * @param type BuildingType
   */
  public Building(int index,String name, Icon16x16 icon, BuildingType type) {
    this.index = index;
    this.name = name;
    this.icon = icon;
    this.type = type;
    this.description = "";
    this.farmBonus = 0;
    this.mineBonus = 0;
    this.factBonus = 0;
    this.cultBonus = 0;
    this.credBonus = 0;
    this.reseBonus = 0;
    this.prodCost = 1;
    this.metalCost = 1;
    this.maintenanceCost = 0;
    this.singleAllowed = false;
    this.battleBonus = 0;
    this.recycleBonus = 0;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Icon16x16 getIcon() {
    return icon;
  }

  public void setIcon(Icon16x16 icon) {
    this.icon = icon;
  }

  public BuildingType getType() {
    return type;
  }

  public void setType(BuildingType type) {
    this.type = type;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getFarmBonus() {
    return farmBonus;
  }

  public void setFarmBonus(int farmBonus) {
    this.farmBonus = farmBonus;
  }

  public int getMineBonus() {
    return mineBonus;
  }

  public void setMineBonus(int mineBonus) {
    this.mineBonus = mineBonus;
  }

  public int getFactBonus() {
    return factBonus;
  }

  public void setFactBonus(int factBonus) {
    this.factBonus = factBonus;
  }

  public int getCultBonus() {
    return cultBonus;
  }

  public void setCultBonus(int cultBonus) {
    this.cultBonus = cultBonus;
  }

  public int getReseBonus() {
    return reseBonus;
  }

  public void setReseBonus(int reseBonus) {
    this.reseBonus = reseBonus;
  }

  public int getCredBonus() {
    return credBonus;
  }

  public void setCredBonus(int credBonus) {
    this.credBonus = credBonus;
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
  public void setProdCost(int prodCost) {
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
  public void setMetalCost(int metalCost) {
    this.metalCost = metalCost;
  }

  @Override
  public String toString() {
    return getName();
  }
  
  public String getFullDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    if (isSingleAllowed()) {
      sb.append(" - one per planet");
    }
    sb.append("\n");
    sb.append(description);
    sb.append("\n");
    sb.append("Cost: Prod:");
    sb.append(getProdCost());
    sb.append(" Metal:");
    sb.append(getMetalCost());
    if (getMaintenanceCost() > 0) {
      sb.append(" Maintenance: ");
      sb.append(getMaintenanceCost());
    }
    sb.append("\n");
    if (getFarmBonus() > 0) {
      sb.append("Food: +");
      sb.append(getFarmBonus());
    }
    if (getMineBonus() > 0) {
      sb.append("Mine: +");
      sb.append(getMineBonus());
    }
    if (getFactBonus() > 0) {
      sb.append("Prod: +");
      sb.append(getFactBonus());
    }
    if (getCultBonus() > 0) {
      sb.append("Cult: +");
      sb.append(getCultBonus());
    }
    if (getReseBonus() > 0) {
      sb.append("Research: +");
      sb.append(getReseBonus());
    }
    if (getCredBonus() > 0) {
      sb.append("Credit: +");
      sb.append(getCredBonus());
    }
    if (getBattleBonus() > 0) {
      sb.append("Battle: +");
      sb.append(getBattleBonus());
      sb.append("%");
    }
    if (getRecycleBonus() > 0) {
      sb.append("Recycle: +");
      sb.append(getRecycleBonus());
      sb.append("%");
    }
    return sb.toString();
  }

  /**
   * @return the maintenanceCost
   */
  public double getMaintenanceCost() {
    return maintenanceCost;
  }

  /**
   * @param maintenanceCost the maintenanceCost to set
   */
  public void setMaintenanceCost(double maintenanceCost) {
    this.maintenanceCost = maintenanceCost;
  }

  public boolean isSingleAllowed() {
    return singleAllowed;
  }

  public void setSingleAllowed(boolean singleAllowed) {
    this.singleAllowed = singleAllowed;
  }

  public int getBattleBonus() {
    return battleBonus;
  }

  public void setBattleBonus(int battleBonus) {
    this.battleBonus = battleBonus;
  }

  public int getRecycleBonus() {
    return recycleBonus;
  }

  public void setRecycleBonus(int recycleBonus) {
    this.recycleBonus = recycleBonus;
  }
  
  
  
}
