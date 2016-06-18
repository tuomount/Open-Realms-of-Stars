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
   * List of researched techs
   */
  private ArrayList<Tech> techList;
  
  /**
   * Tech Levels
   */
  private int[] techLevels = new int[TechType.values().length];
  
  public TechList() {
    techList = new ArrayList<>();
  }
  
  /**
   * Check if certain tech is in tech list
   * @param techName Techname to search
   * @return True if found, otherwise false
   */
  public boolean isTech(String techName) {
    for (int i=0;i<techList.size();i++) {
      Tech tech = techList.get(i);
      if (tech.getName().equals(techName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Add new tech to tech list if it is not in list yet.
   * @param tech Tech to add
   */
  public void addTech(Tech tech) {
    if (!isTech(tech.getName())) {
      techList.add(tech);
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
   * Get Tech List
   * @return tech list as a tech array
   */
  public Tech[] getList() {
    return techList.toArray(new Tech[techList.size()]);
  }
}
