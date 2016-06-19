package org.openRealmOfStars.player.tech;

import java.util.ArrayList;


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
 * Tech list for player
 * 
 */
public class TechList {
  
  /**
   * Maximum number of tech levels
   */
  public static final int MAX_TECH_LEVEL = 10;
  
  /**
   * Maximum number of tech types
   */
  public static final int MAX_TECH_TYPES = TechType.values().length;
  
  /**
   * List of researched techs, First are TechTypes, second is tech level
   */
  private TechListForLevel[][] techList;
  
  /**
   * Tech Levels
   */
  private int[] techLevels = new int[TechType.values().length];
  
  public TechList() {
    techList = new TechListForLevel[TechType.values().length][MAX_TECH_LEVEL];
    for (int i=0;i<MAX_TECH_TYPES;i++) {
      for (int j=0;j<MAX_TECH_LEVEL;j++) {
        techList[i][j] = new TechListForLevel(j+1);
      }
    }
  }
  
  /**
   * Check if certain tech is in tech list
   * @param techName Techname to search
   * @return True if found, otherwise false
   */
  public boolean isTech(String techName) {
    for (int i=0;i<MAX_TECH_TYPES;i++) {
      for (int j=0;j<MAX_TECH_LEVEL;j++) {
        TechListForLevel tech = techList[i][j];
        if (tech.isTech(techName)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Add new tech to tech list if it is not in list yet.
   * @param tech Tech to add
   */
  public void addTech(Tech tech) {
    int index =tech.getType().getIndex();
    int lvl = tech.getLevel()-1;
    if (!techList[index][lvl].isTech(tech.getName())) {
      techList[index][lvl].addTech(tech);
    }
  }
  
  /**
   * Get Tech Level
   * @param type Tech type which level is going to be checked
   * @return Level 1-10
   */
  public int getTechLevel(TechType type) {
    int index = type.getIndex();
    if (index >= 0 && index < techLevels.length) {
      return techLevels[index];
    }
    return -1;
  }
  
  /**
   * Set Tech Level
   * @param type Tech type which is going to be set
   * @param level Level must be between 1-10
   */
  public void setTechLevel(TechType type, int level) {
    int index = type.getIndex();
    if (level >= 1 && level < 11 && index >= 0 && index < techLevels.length) {
      techLevels[index] = level;
    }
  }
  
  /**
   * Get tech list for certain tech type
   * @param type Tech Type to get the list
   * @return tech list as a tech array
   */
  public Tech[] getListForType(TechType type) {
    ArrayList<Tech> list = new ArrayList<>();
    int index = type.getIndex();
    for (int i=0;i<MAX_TECH_LEVEL;i++) {
      for (Tech tech : techList[index][i].getList()) {
        list.add(tech);
      }
    }
    return list.toArray(new Tech[list.size()]);
  }
  
  /**
   * Get Full Tech List
   * @return tech list as a tech array
   */
  public Tech[] getList() {
    ArrayList<Tech> list = new ArrayList<>();
    for (int j=0;j<MAX_TECH_TYPES;j++) {
      for (int i=0;i<MAX_TECH_LEVEL;i++) {
        for (Tech tech : techList[j][i].getList()) {
          list.add(tech);
        }
      }
    }
    return list.toArray(new Tech[list.size()]);
  }
}
