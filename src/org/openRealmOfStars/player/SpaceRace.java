package org.openRealmOfStars.player;

import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.utilities.DiceGenerator;

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

  /**
   * Humans are about average in everything.
   */
  HUMAN(0,"Humans","Human","Humans are about average in everything."),
  /**
   * Mechanical beings whom do not eat food. Each now population must be built.
   */
  MECHIONS(1,"Mechions","Mechion","Mechanical beings whom do not eat food."
      + " Each now population must be built."),
  /**
   * Aggressive and warmongering spieces.
   */
  SPORKS(2,"Sporks","Spork","Aggressive and warmongering spieces."),
  /**
   * Humanoid creatures with grey skin and big eyes. Greyan are excellent researchers.
   */
  GREYANS(3,"Greyans","Greyan","Humanoid creatures with grey skin and big eyes. Greyan are excellent researchers."),
  /**
   * Bipedal humanoid creatures which are big, about 5 meters tall. Due their
     enormous size their space ships are must more rigid. Centaurs need more food to survive.
   */
  CENTAURS(4,"Centaurs","Centaur","Bipedal humanoid creatures which are big, about 5 meters tall. Due their"
      + "enormous size their space ships are more rigid. Centaurs need more food to survive.");
    
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
  SpaceRace(int index, String name,String single,  String description) {
    this.index = index;
    this.name= name;
    this.nameSingle = single;
    this.description = description;
    
  }
  
  /**
   * Get SpaceRace with indexed number
   * @param index Space Race index
   * @return SpaceRace, if index is out of bounds human is given
   */
  public static SpaceRace getRaceByIndex(int index) {
    if (index > -1 && index < SpaceRace.values().length) {
      return SpaceRace.values()[index];
    } else {
      return SpaceRace.HUMAN;
    }
  }
  
  /**
   * Get Space race by single name
   * @param name Race name in single format
   * @return SpaceRace, if does not match then return null
   */
  public static SpaceRace getRaceByName(String name) {
    if (name == null) {
      return null;
    }
    if (name.equals(HUMAN.nameSingle)) {
      return SpaceRace.HUMAN;
    }
    if (name.equals(MECHIONS.nameSingle)) {
      return SpaceRace.MECHIONS;
    }
    if (name.equals(SPORKS.nameSingle)) {
      return SpaceRace.SPORKS;
    }
    if (name.equals(GREYANS.nameSingle)) {
      return SpaceRace.GREYANS;
    }
    if (name.equals(CENTAURS.nameSingle)) {
      return SpaceRace.CENTAURS;
    }
    return null;
  }
  
  /**
   * Get Random race
   * @return SpaceRace
   */
  public static SpaceRace getRandomRace() {
    int index = DiceGenerator.getRandom(SpaceRace.values().length-1);
    return getRaceByIndex(index);
  }
  
  /**
   * Get random empire name for player
   * @return player name
   */
  public String getRandomName() {
    StringBuilder sb = new StringBuilder();
    switch (DiceGenerator.getRandom(7)) {
    case 0: sb.append("Empire of "); break;
    case 1: sb.append("Federation of "); break;
    case 2: sb.append("Republic of "); break;
    case 3: sb.append("Alliance of "); break;
    case 4: {
      if (this == SPORKS || this == MECHIONS ) {
        sb.append("Horde of "); 
      } else {
        sb.append("Kingdom of ");
      }
      break;
    }
    case 5: {
      if (this == SPORKS) {
       sb.append("Clan of "); 
      } else {
        sb.append("Hegemony of ");
      }
      break;
    }
    case 6: {
      if (this == MECHIONS) {
       sb.append("AI of "); 
      } else {
        sb.append("Democracy of");
      }
      break;
    }
    case 7: sb.append("Hiearchy of "); break;
    }
    sb.append(this.getName());
    return sb.toString();
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
   * Space race name in single 
   */
  private String nameSingle;

  public String getNameSingle() {
    return nameSingle;
  }
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
    case GREYANS: return 150;
    case CENTAURS: return 100;
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
    case GREYANS: return 6;
    case CENTAURS: return 3;
    }
    return -1;
  }

  /**
   * Get race image
   * @return BufferedImage
   */
  public BufferedImage getRaceImage() {
    switch (this) {
    case HUMAN: return GuiStatics.IMAGE_HUMAN_RACE;
    case MECHIONS: return GuiStatics.IMAGE_MECHION_RACE;
    case SPORKS: return GuiStatics.IMAGE_SPORK_RACE;
    case GREYANS: return GuiStatics.IMAGE_GREYAN_RACE;
    case CENTAURS: return GuiStatics.IMAGE_CENTAUR_RACE;
    }
    return GuiStatics.IMAGE_CENTAUR_RACE;
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
    case GREYANS: return 100;
    case CENTAURS: return 100;
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
    case GREYANS: return 100;
    case CENTAURS: return 100;
    }
    return 0;
  }
  /**
   * Get Trooper power
   * @return normal 10
   */
  public int getTrooperPower() {
    switch (this) {
    case HUMAN: return 10;
    case MECHIONS: return 12;
    case SPORKS: return 12;
    case GREYANS: return 8;
    case CENTAURS: return 14;
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
    case GREYANS: return 100;
    case CENTAURS: return 100;
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
    case GREYANS: return 50;
    case CENTAURS: return 50;
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
    case GREYANS: return 100;
    case CENTAURS: return 125;
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
    case SPORKS: return -3;
    case GREYANS: return 0;
    case CENTAURS: return -1;
    }
    return 0;
  }

  /**
   * Get racial hull point if available
   * @return normal 0 or 1
   */
  public int getExtraHullPoint() {
    switch (this) {
    case HUMAN: return 0;
    case MECHIONS: return 0;
    case SPORKS: return 0;
    case GREYANS: return 0;
    case CENTAURS: return 1;
    }
    return 0;
  }

  /**
   * Get how many turns to update defense
   * @return int
   */
  public int getAIDefenseUpdate() {
    switch (this) {
    case HUMAN: return 15;
    case MECHIONS: return 15;
    case SPORKS: return 10;
    case GREYANS: return 16;
    case CENTAURS: return 18;
    }
    return 15;
  }

  /**
   * Get how many turns to explore solarsystem
   * @return int
   */
  public int getAIExploringAmount() {
    switch (this) {
    case HUMAN: return 30;
    case MECHIONS: return 30;
    case SPORKS: return 28;
    case GREYANS: return 35;
    case CENTAURS: return 30;
    }
    return 30;
  }
  /**
   * What is the minimum number of attack ships
   * @return int
   */
  public int getAIMinimumAttackShips() {
    switch (this) {
    case HUMAN: return 3;
    case MECHIONS: return 3;
    case SPORKS: return 4;
    case GREYANS: return 3;
    case CENTAURS: return 2;
    }
    return 3;
  }
  /**
   * What is the minimum number of conquer ships aka bomber and
   * troopers
   * @return int
   */
  public int getAIMinimumConquerShips() {
    switch (this) {
    case HUMAN: return 1;
    case MECHIONS: return 1;
    case SPORKS: return 2;
    case GREYANS: return 1;
    case CENTAURS: return 1;
    }
    return 1;
  }

}
