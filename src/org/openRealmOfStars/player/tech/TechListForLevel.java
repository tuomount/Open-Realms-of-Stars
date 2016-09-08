package org.openRealmOfStars.player.tech;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.utilities.IOUtilities;
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
 * Tech list for single tech and level
 * 
 */
public class TechListForLevel {
  
  /**
   * List of researched techs
   */
  private ArrayList<Tech> techList;
  
  /**
   * Tech Levels
   */
  private int level;
  
  public TechListForLevel(int level) {
    techList = new ArrayList<>();
    if (level >= 1 && level < 10) {
      this.level = level;
    } else {
      this.level = 1;
    }
  }
  
  /**
   * Save Tech list for level
   * @param dos DataOutputStream
   * @throws IOException
   */
  public void saveTechListForLevel(DataOutputStream dos) throws IOException {
    dos.writeInt(techList.size());
    for (Tech tech : techList) {
      IOUtilities.writeString(dos, tech.getName());
    }
  }

  /**
   * Create tech list for level from DataInputStream
   * @param level Level
   * @param type Tech Type
   * @param dis DataInputStream
   * @throws IOException
   */
  public TechListForLevel(int level, TechType type, DataInputStream dis) 
      throws IOException {
    techList = new ArrayList<>();
    if (level >= 1 && level < 10) {
      this.level = level;
    } else {
      this.level = 1;
    }
    int count = dis.readInt();
    for (int i=0;i<count;i++) {
      String name = IOUtilities.readString(dis);
      Tech tech = null;
      switch (type) {
      case Combat: tech = TechFactory.createCombatTech(name, level); break;
      case Defense: tech = TechFactory.createDefenseTech(name, level); break;
      case Hulls: tech = TechFactory.createHullTech(name, level); break;
      case Improvements: tech = TechFactory.createImprovementTech(name, level); break;
      case Propulsion: tech = TechFactory.createPropulsionTech(name, level); break;
      case Electrics: tech = TechFactory.createElectronicsTech(name, level); break;
      }
      if (tech != null) {
        techList.add(tech);
      }
    }
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
   * @return Level 1-10
   */
  public int getLevel() {
    return level;
  }

  /**
   * Get Tech List
   * @return tech list as a tech array
   */
  public Tech[] getList() {
    return techList.toArray(new Tech[techList.size()]);
  }
}
