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
  
  /**
   * Tech Focus levels. These must be between 0-100%. When all techs
   * are summed total must be 100%
   */
  private int[] techFocus = new int[TechType.values().length];
  
  /**
   * Tech research points
   */
  private double[] techResearchPoint = new double[TechType.values().length];
  
  public TechList() {
    techList = new TechListForLevel[TechType.values().length][MAX_TECH_LEVEL];
    for (int i=0;i<MAX_TECH_TYPES;i++) {
      for (int j=0;j<MAX_TECH_LEVEL;j++) {
        techList[i][j] = new TechListForLevel(j+1);
      }
    }
    techLevels[TechType.Combat.getIndex()] = 1;
    techLevels[TechType.Defense.getIndex()] = 1;
    techLevels[TechType.Hulls.getIndex()] = 1;
    techLevels[TechType.Improvements.getIndex()] = 1;
    techLevels[TechType.Propulsion.getIndex()] = 1;
    techLevels[TechType.Electrics.getIndex()] = 1;
    techFocus[TechType.Combat.getIndex()] = 20;
    techFocus[TechType.Defense.getIndex()] = 16;
    techFocus[TechType.Hulls.getIndex()] = 16;
    techFocus[TechType.Improvements.getIndex()] = 16;
    techFocus[TechType.Propulsion.getIndex()] = 16;
    techFocus[TechType.Electrics.getIndex()] = 16;
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
   * Get Tech list for certain tech type and level
   * @param type Tech Type to get the list
   * @param level Level of tech list 1-10
   * @return tech list as a tech array
   */
  public Tech[] getListForTypeAndLevel(TechType type, int level) {
    level = level -1;
    if (level >= 10 || level < 0) {
      return new Tech[0];
    }
    ArrayList<Tech> list = new ArrayList<>();
    int index = type.getIndex();
    for (Tech tech : techList[index][level].getList()) {
      list.add(tech);
    }
    return list.toArray(new Tech[list.size()]);
  }
  
  /**
   * Is Tech list for certain level full
   * @param type Tech Type
   * @param level Level
   * @return true if full or false if not
   */
  public boolean isTechListForLevelFull(TechType type, int level) {
    Tech[] list = getListForTypeAndLevel(type, level);
    String[] choices = TechFactory.getListByTechLevel(type, level);
    if (list.length == choices.length) {
      return true;
    }
    return false;
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
  
  /**
   * Fine tune value for tech focus
   */
  public static final int FINE_TUNE_VALUE = 4;
  /**
   * Tries to settle so that tech focus about evenly distributed
   * @param type Tech type not used for in settling
   * @param value Value which tech focus was set
   */
  private void settleTechFocus(TechType type, int value) {
    int redis = 100;
    for (int i=0;i<TechType.values().length;i++) {
      redis = redis -techFocus[i];
    }
    int average = (100-value) / (TechType.values().length-1);
    while (redis != 0) {
      for (int i=0;i<TechType.values().length;i++) {
        if (i != type.getIndex() && redis > 0) {
          if (techFocus[i] < average) {
            if (redis > FINE_TUNE_VALUE) {
              techFocus[i] = techFocus[i]+FINE_TUNE_VALUE;
              redis = redis -FINE_TUNE_VALUE;
            } else {
              techFocus[i] = techFocus[i]+redis;
              redis = 0;
            }
          }          
        }
        if (i != type.getIndex() && redis < 0) {
          if (techFocus[i] > average) {
            if (redis < -FINE_TUNE_VALUE) {
              techFocus[i] = techFocus[i]-FINE_TUNE_VALUE;
              redis = redis +FINE_TUNE_VALUE;
            } else {
              techFocus[i] = techFocus[i]+redis;
              redis = 0;
            }
          }
        }

      }
      for (int i=0;i<TechType.values().length;i++) {
        if (i != type.getIndex() && redis > 0) {
          if (techFocus[i] == average) {
            if (redis > FINE_TUNE_VALUE) {
              techFocus[i] = techFocus[i]+FINE_TUNE_VALUE;
              redis = redis -FINE_TUNE_VALUE;
            } else {
              techFocus[i] = techFocus[i]+redis;
              redis = 0;
            }
          }          
        }
        if (i != type.getIndex() && redis < 0) {
          if (techFocus[i] == average) {
            if (redis < -FINE_TUNE_VALUE) {
              techFocus[i] = techFocus[i]-FINE_TUNE_VALUE;
              redis = redis +FINE_TUNE_VALUE;
            } else {
              techFocus[i] = techFocus[i]+redis;
              redis = 0;
            }
          }
        }

      }    }
  }
  
  /**
   * Set Tech Focus level for certain type with value.
   * Makes sure that total focus level is set to 100%
   * @param type Tech type
   * @param value Value to set for certain type. Must be between 0-100%
   */
  public void setTechFocus(TechType type, int value) {
    if (value >= 0 && value <= 100) {
      techFocus[type.getIndex()] = value;
      settleTechFocus(type,value);
    }
  }
  
  /**
   * Get Tech focus for certain type of tech.
   * @param type
   * @return
   */
  public int getTechFocus(TechType type) {
    return techFocus[type.getIndex()];
  }
  
  /**
   * Get Tech research points for certain type of tech
   * @param type TechType
   * @return research points spent so far as double
   */
  public double getTechResearchPoints(TechType type) {
    return techResearchPoint[type.getIndex()];
  }
  
  /**
   * Update Research points by turn. This will also grant a new technology
   * @param totalResearchPoints player makes per turn
   */
  public void updateResearchPointByTurn(int totalResearchPoints) {
    for (int i=0;i<techFocus.length;i++) {
      techResearchPoint[i] = techResearchPoint[i] + techFocus[i]*totalResearchPoints/100.0;
      if (techResearchPoint[i] >= TechFactory.getTechCost(techLevels[i])) {
        techResearchPoint[i] = techResearchPoint[i]-TechFactory.getTechCost(techLevels[i]);
        TechType type = TechType.getTypeByIndex(i);
        int lvl = techLevels[i];
        Tech tech = TechFactory.createRandomTech(type, lvl,getListForTypeAndLevel(type, lvl));
        addTech(tech);
        if (isTechListForLevelFull(type, lvl)) {
          techLevels[i] = lvl +1;
        }
      }
    }

  }
  
  /**
   * Get building list from researched technology
   * @return String array of building names
   */
  public String[] getBuildingListFromTech() {
    Tech[] techs = getList();
    ArrayList<String> buildings = new ArrayList<>();
    for (Tech tech : techs) {
      if (tech.getImprovement() != null) {
        buildings.add(tech.getImprovement());
      }
    }
    return buildings.toArray(new String[buildings.size()]);
  }
}
