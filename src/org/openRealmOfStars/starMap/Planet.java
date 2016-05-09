package org.openRealmOfStars.starMap;

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
   * Planet name
   */
  private String name;
  
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
    this.radiationLevel = radiationLevel;
  }

  public int getGroundSize() {
    return groundSize;
  }

  public void setGroundSize(int groundSize) {
    this.groundSize = groundSize;
  }

  public int getAmountMetalInGround() {
    return amountMetalInGround;
  }

  public void setAmountMetalInGround(int amountMetalInGround) {
    this.amountMetalInGround = amountMetalInGround;
  }

  public int getMetal() {
    return metal;
  }

  public void setMetal(int metal) {
    this.metal = metal;
  }
  
  

}
