package org.openRealmOfStars.player.tech;


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
 * Class for Single tech aka researched technology
 * 
 */
public class Tech {

  /**
   * Tech Name
   */
  private String name;
  
  /**
   * Tech type
   */
  private TechType type;
  /**
   * Tech level
   */
  private int level;
  /**
   * Component tech provides
   */
  private String component;
  /**
   * Improvement tech provides
   */
  private String improvement;
  /**
   * Ship hull tech provides
   */
  private String hull;
  
  /**
   * Create a new Tech
   * @param name Tech Name
   * @param type Tech Type
   * @param level Tech level 1-10
   */
  public Tech(String name, TechType type, int level) {
    this.name = name;
    setType(type);
    setLevel(level);
    hull = null;
    improvement = null;
    component = null;
  }
  
  /**
   * Get Tech name as String
   * @return name as a String
   */
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  /**
   * Get Tech Type see TechType enum
   * @return TechType
   */
  public TechType getType() {
    return type;
  }
  public void setType(TechType type) {
    this.type = type;
  }
  /**
   * Get Tech Level.
   * @return int between 1-10
   */
  public int getLevel() {
    return level;
  }
  public void setLevel(int level) {
    if (level >= 1 && level < 11) {
      this.level = level;
    }
  }
  /**
   * Get Component that tech provides.
   * @return Component name that tech provides or null
   */
  public String getComponent() {
    return component;
  }
  public void setComponent(String component) {
    this.component = component;
  }
  /**
   * Get Improvement that tech provides
   * @return Improvement name that tech provides or null
   */
  public String getImprovement() {
    return improvement;
  }
  public void setImprovement(String improvement) {
    this.improvement = improvement;
  }
  /**
   * Get Hull that tech provides
   * @return
   */
  public String getHull() {
    return hull;
  }
  public void setHull(String hull) {
    this.hull = hull;
  }
  
  
}
