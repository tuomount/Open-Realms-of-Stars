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

  HUMAN(0,"Humans","Humans are about average in everything."),
  MECHIONS(1,"Mechions","Mechanical beings whom do not eat food."
      + " Each now population must be built."),
  SPORKS(2,"Sporks","Aggressive and warmongering spieces.");
  
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
  SpaceRace(int index, String name, String description) {
    this.index = index;
    this.name= name;
    this.description = description;
    
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
   * Get scientis research speed
   * @return
   */
  public int getResearchSpeed() {
    switch (this) {
    case HUMAN: return 100;
    case MECHIONS: return 50;
    case SPORKS: return 100;
    }
    return 0;
  }
  
  /**
   * Get race maximum Radiation
   * @return
   */
  public int getMaxRad() {
    switch (this) {
    case HUMAN: return 4;
    case MECHIONS: return 8;
    case SPORKS: return 5;
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

  /**
   * Get miners mining speed
   * @return normal 100, half 50, double 200
   */
  public int getMiningSpeed() {
    switch (this) {
    case HUMAN: return 100;
    case MECHIONS: return 150;
    case SPORKS: return 100;
    }
    return 0;
  }

  /**
   * Get artists culture speed
   * @return normal 100, half 50, double 200
   */
  public int getCultureSpeed() {
    switch (this) {
    case HUMAN: return 100;
    case MECHIONS: return 50;
    case SPORKS: return 100;
    }
    return 0;
  }

  /**
   * Get worker production speed
   * @return normal 100, half 50, double 200
   */
  public int getProductionSpeed() {
    switch (this) {
    case HUMAN: return 100;
    case MECHIONS: return 100;
    case SPORKS: return 100;
    }
    return 0;
  }

  /**
   * Get population growth speed
   * @return normal 100, half 50, double 200
   */
  public int getGrowthSpeed() {
    switch (this) {
    case HUMAN: return 100;
    case MECHIONS: return 0;
    case SPORKS: return 100;
    }
    return 0;
  }

  /**
   * Get population food requirement
   * @return normal 100, half 50, double 200
   */
  public int getFoodRequire() {
    switch (this) {
    case HUMAN: return 100;
    case MECHIONS: return 0;
    case SPORKS: return 100;
    }
    return 0;
  }

  /**
   * Get diplomacy bonus for race
   * @return normal 0, +2 positive, -2 negative
   */
  public int getDiplomacyBonus() {
    switch (this) {
    case HUMAN: return 2;
    case MECHIONS: return -2;
    case SPORKS: return -5;
    }
    return 0;
  }

}
