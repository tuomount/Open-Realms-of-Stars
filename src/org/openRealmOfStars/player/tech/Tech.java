package org.openRealmOfStars.player.tech;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;

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
   * Icon for Tech
   */
  private Icon16x16 icon;
  
  /**
   * Create a new Tech with defaults
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
    icon = Icons.getIconByName(Icons.ICON_RESEARCH);
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

  /**
   * @return the icon
   */
  public Icon16x16 getIcon() {
    return icon;
  }

  /**
   * @param icon the icon to set
   */
  public void setIcon(Icon16x16 icon) {
    this.icon = icon;
  }

  @Override
  public String toString() {
    return name;
  }
  
  /**
   * Get Tech info as a String
   * @return tech info
   */
  public String getTechInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append("\n");
    sb.append(getType().toString());
    sb.append(" - ");
    sb.append(level);
    sb.append("\n");
    if (improvement != null ){
      sb.append("Improvement: ");
      sb.append(improvement);
      sb.append("\n");
    }
    if (hull != null ){
      sb.append("Ship design: ");
      sb.append(hull);
      sb.append("\n");
    }
    if (component != null ){
      sb.append("Ship component: ");
      sb.append(component);
      sb.append("\n");
    }
    return sb.toString();
  }
  
}
