package org.openRealmOfStars.starMap;

import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.GuiStatics;
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
   * List of big planet images
   */
  public static final BufferedImage[] PLANET_BIG_IMAGES = 
    {GuiStatics.BIG_PLANET_ROCK1,GuiStatics.BIG_PLANET_WATERWORLD1};

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
   * Planet Image Index for planet tile
   */
  private int planetImageIndex;
  
  /**
   * Planet type: 0 Rock, 1 Waterworld
   */
  private int planetType;
  
  /**
   * Planet Owner info, this is index to player index, -1 means
   * that planet is not colonized yet.
   */
  private int planetOwner;
  
  /**
   * Maximum number of productions
   */
  public static final int MAX_PRODUCTION = 5;
  
  /**
   * Food production
   */
  public static final int FOOD_PRODUCTION = 0;

  /**
   * Metal production from mining
   */
  public static final int METAL_PRODUCTION = 1;

  /**
   * production from workers
   */
  public static final int PRODUCTION_WORKERS = 2;

  /**
   * Research from scientist
   */
  public static final int RESEARCH_SCIENTIST = 3;

  /**
   * Culture from artist
   */
  public static final int CULTURE_ARTIST = 4;

  private int[] workers;
  
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
    this.planetOwner = -1;
    this.workers = new int[MAX_PRODUCTION];
  }

  /**
   * Get amount of workers in certain type
   * @param workerType
   * @return Amount of workers
   */
  public int getWorkers(int workerType) {
    if (workerType >= 0 && workerType < MAX_PRODUCTION) {
      return workers[workerType];
    }
    return 0;
  }

  /**
   * Set amount of workers in certain type
   * @param workerType
   * @param value how many workers in this production
   */
  public void setWorkers(int workerType,int value) {
    if (workerType >= 0 && workerType < MAX_PRODUCTION) {
      workers[workerType] = value;
    }
  }
  
  /**
   * Get total population number
   * @return Total Population
   */
  public int getTotalPopulation() {
    int result=0;
    for (int i=0;i<workers.length;i++) {
      result = result +workers[i];
    }
    return result;
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
    if (groundSize > 6 && groundSize < 17) {
      this.groundSize = groundSize;
    }
  }

  public int getAmountMetalInGround() {
    return amountMetalInGround;
  }

  public void setAmountMetalInGround(int amountMetalInGround) {
    if (amountMetalInGround > 1999 && amountMetalInGround < 10001) {
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

  /**
   * Get Planet tile index
   * @return
   */
  public int getPlanetImageIndex() {
    return planetImageIndex;
  }

  public void setPlanetImageIndex(int planetImageIndex) {
    this.planetImageIndex = planetImageIndex;
  }
  
  /**
   * Planet size as string. Size varies from small to huge.
   * @return String
   */
  public String getSizeAsString() {
    switch (getGroundSize()) {
    case 7: return "small";
    case 8: return "small";
    case 9: return "medium";
    case 10: return "below average";
    case 11: return "average";
    case 12: return "average";
    case 13: return "above average";
    case 14: return "large";
    case 15: return "huge";
    case 16: return "huge";
    default: return "small";
    }
  }
  
  /**
   * Generate info text
   * @return String
   */
  public String generateInfoText() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.getName());
    sb.append("\n");
    if (isGasGiant()) {
      sb.append("\n");
      sb.append("Gas Giant");
      sb.append("\n");
      sb.append("Planet is inhabitable, but planet can block scanners.");
    } else {
      sb.append("Radiation:");
      sb.append(getRadiationLevel());
      sb.append("\n");
      sb.append("Size:");
      sb.append(getSizeAsString());
      sb.append("\n");
      sb.append("Metal:");
      sb.append(getAmountMetalInGround());
    }
    return sb.toString();
  }

  /**
   * @return the planetType
   */
  public int getPlanetType() {
    return planetType;
  }

  /**
   * @param planetType the planetType to set
   */
  public void setPlanetType(int planetType) {
    if (planetType >= 0 && planetType <  PLANET_IMAGE_INDEX.length) {
      this.planetType = planetType;
      setPlanetImageIndex(PLANET_IMAGE_INDEX[planetType]);
    }
  }

  /**
   * @return the planetOwner
   */
  public int getPlanetOwner() {
    return planetOwner;
  }

  /**
   * @param planetOwner the planetOwner to set
   */
  public void setPlanetOwner(int planetOwner) {
    this.planetOwner = planetOwner;
  }
  

}
