package org.openRealmOfStars.player;
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
 * Space races in enum
 * 
 */
public enum SpaceRace {

  HUMAN(0,"Humans","Humans are about average in everything.",
      //rese, mini, cult, prod,grow, foodReq, diplomacy
      100,100,100,100,100,100,2),
  MECHIONS(0,"Mechions","Mechanical beings whom do not eat food. Each now population must be built.",
    //rese, mini, cult, prod,grow, foodReq, diplomacy
      50,150,50,100,0,0,-2);
  
  /**
   * Create space race
   * @param index
   * @param name
   * @param description
   * @param researchSpeed
   * @param miningSpeed
   * @param cultureSpeed
   * @param productionSpeed
   * @param growthSpeed
   * @param foodRequire
   * @param diplomacy
   */
  SpaceRace(int index, String name, String description,int researchSpeed,
      int miningSpeed,int cultureSpeed,int productionSpeed, int growthSpeed, 
      int foodRequire,int diplomacy) {
    this.index = index;
    this.name= name;
    this.description = description;
    this.researchSpeed = researchSpeed;
    
  }
  
  /**
   * Space race index
   */
  private int index;
  
  /**
   * Space race name in plural
   */
  private String name;

  /**
   * Space race description
   */
  private String description;
  
  /**
   * Research speed for scientist
   */
  private int researchSpeed;

  /**
   * Mining speed for miners
   */
  private int miningSpeed;

  /**
   * Culture speed for artist
   */
  private int cultureSpeed;

  /**
   * Production speed for workers
   */
  private int productionSpeed;

  /**
   * Population growth speed
   */
  private int growthSpeed;

  /**
   * Food require per people
   */
  private int foodRequire;

  /**
   * Diplomacy bonus
   */
  private int diplomacyBonus;


  
  public int getResearchSpeed() {
    return researchSpeed;
  }
  
  /**
   * Get race maximum Radiation
   * @return
   */
  public int getMaxRad() {
    switch (this) {
    case HUMAN: return 4;
    case MECHIONS: return 8;
    }
    return -1;
  }

  /**
   * @return the index
   */
  public int getIndex() {
    return index;
  }


  /**
   * @return the name
   */
  public String getName() {
    return name;
  }


  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  public int getMiningSpeed() {
    return miningSpeed;
  }

  public int getCultureSpeed() {
    return cultureSpeed;
  }

  public int getProductionSpeed() {
    return productionSpeed;
  }

  public int getGrowthSpeed() {
    return growthSpeed;
  }

  public int getFoodRequire() {
    return foodRequire;
  }

  public int getDiplomacyBonus() {
    return diplomacyBonus;
  }

}
