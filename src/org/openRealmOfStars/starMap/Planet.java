package org.openRealmOfStars.starMap;

import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.RandomSystemNameGenerator;

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
 * Class planet
 * 
 */

public class Planet {

  /**
   * List of Planet tile names for regular planets
   */
  public static final int[] PLANET_IMAGE_INDEX = 
      {Tiles.getTileByName(TileNames.ROCK1).getIndex(),
       Tiles.getTileByName(TileNames.WATERWORLD1).getIndex()};
  
  /**
   * Planet name
   */
  private String name;
  
  /**
   * Planet order number in system
   */
  private int OrderNumber;
  
  /**
   * Planet's radioation level between 1-10.
   */
  private int radiationLevel;
  
  /**
   * Planet's ground size aka how many improvements can be fitted there.
   * This is between 7-16.
   */
  private int groundSize;
  
  /**
   * How much metal there is still in the ground.
   * This is between 2000-10000.
   */
  private int amountMetalInGround;

  /**
   * How much metal has been mined and available to use
   */
  private int metal;
  
  /**
   * Is planet inhabitable gas giant. Gas giants just block the radar.
   */
  private boolean gasGiant;
  
  /**
   * Planet's x coordinate. On gas giant this left upper corner.
   */
  private int x;
  /**
   * Planet's y coordinate. On gas giant this left upper corner.
   */
  private int y;
  
  /**
   * Planet Image Index
   */
  private int planetImageIndex;
  
  /**
   * Create random planet with name + orderNumber with Roman numbers.
   * Other planet attributes are randomized.
   * @param x Planet's X coordinate
   * @param y Planet's Y coordinate
   * @param name Planet name
   * @param orderNumber as integer
   * @param gasGiant Is planet inhabitable gas giant
   */
  public Planet(int x, int y,String name,int orderNumber,boolean gasGiant) {
    this.setX(x);
    this.setY(y);
    this.name = name+" "+RandomSystemNameGenerator.numberToRoman(orderNumber);
    this.setOrderNumber(orderNumber);
    this.setRadiationLevel(DiceGenerator.getRandom(1, 10));
    this.setAmountMetalInGround(DiceGenerator.getRandom(2000,10000));
    this.setGroundSize(DiceGenerator.getRandom(7,16));
    this.setMetal(0);
    this.gasGiant = gasGiant;
    this.planetImageIndex = 0;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getRadiationLevel() {
    return radiationLevel;
  }

  public void setRadiationLevel(int radiationLevel) {
    if (radiationLevel > 0 && radiationLevel < 11) {
      this.radiationLevel = radiationLevel;
    }
  }

  public int getGroundSize() {
    return groundSize;
  }

  public void setGroundSize(int groundSize) {
    if (radiationLevel > 6 && radiationLevel < 17) {
      this.groundSize = groundSize;
    }
  }

  public int getAmountMetalInGround() {
    return amountMetalInGround;
  }

  public void setAmountMetalInGround(int amountMetalInGround) {
    if (radiationLevel > 1999 && radiationLevel < 10001) {
      this.amountMetalInGround = amountMetalInGround;
    }
  }

  public int getMetal() {
    return metal;
  }

  public void setMetal(int metal) {
    this.metal = metal;
  }

  public int getOrderNumber() {
    return OrderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    OrderNumber = orderNumber;
  }

  public boolean isGasGiant() {
    return gasGiant;
  }

  public void setGasGiant(boolean gasGiant) {
    this.gasGiant = gasGiant;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getPlanetImageIndex() {
    return planetImageIndex;
  }

  public void setPlanetImageIndex(int planetImageIndex) {
    this.planetImageIndex = planetImageIndex;
  }
  
  

}
