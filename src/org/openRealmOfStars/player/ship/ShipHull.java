package org.openRealmOfStars.player.ship;

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
 * Ship hull for handling the very base of ship
 * 
 */
public class ShipHull {

  /**
   * Unique index for factories and saving the game
   */
  private int index;

  /**
   * Hull name needs to match one in techs
   */
  private String name;
  
  /**
   * How many components can be fitted
   */
  private int maxSlot;
  
  /**
   * How much hull point per slot
   */
  private int slotHull;
  
  /**
   * Ship's Hull type
   */
  private ShipHullType hullType;
  
  /**
   * Ship's size
   */
  private ShipSize size;
  
  /**
   * Hull cost in production
   */
  private int cost;

  /**
   * Hull cost in metal
   */
  private int metalCost;

  /**
   * Constructor for Ship hull
   * @param index Index for factory
   * @param name Hull name, must match one in techs
   * @param maxSlots How many slots in hull
   * @param hull How many hull points single slot has
   * @param type ShipHullType
   * @param size ShipSize
   * @param int cost in production
   * @param int cost in metal
   */
  public ShipHull(int index,String name, int maxSlots, int hull, ShipHullType type,
      ShipSize size,int cost, int metal) {
    this.index = index;
    this.name = name;
    this.maxSlot = maxSlots;
    this.slotHull = hull;
    this.hullType = type;
    this.size = size;
    this.cost = cost;
    this.metalCost = metal;
  }

  public int getIndex() {
    return index;
  }
  
  public String getName() {
    return name;
  }

  public int getMaxSlot() {
    return maxSlot;
  }

  public int getSlotHull() {
    return slotHull;
  }

  public ShipHullType getHullType() {
    return hullType;
  }

  public ShipSize getSize() {
    return size;
  }
  
  public int getCost() {
    return cost;
  }
  
  public int getMetalCost() {
    return metalCost;
  }
  
  
}
