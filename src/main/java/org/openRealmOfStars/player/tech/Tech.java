package org.openRealmOfStars.player.tech;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullFactory;
import org.openRealmOfStars.starMap.planet.BuildingFactory;
import org.openRealmOfStars.starMap.planet.construction.Building;

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
  public Tech(final String name, final TechType type, final int level) {
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

  /**
   * Set Tech name as a String
   * @param name Tech Name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Get Tech Type see TechType enum
   * @return TechType
   */
  public TechType getType() {
    return type;
  }

  /**
   * Set Tech type
   * @param type TechType enum
   */
  public void setType(final TechType type) {
    this.type = type;
  }

  /**
   * Get Tech Level.
   * @return int between 1-10
   */
  public int getLevel() {
    return level;
  }

  /**
   * Set Tech level
   * @param level between 1-10
   */
  public void setLevel(final int level) {
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

  /**
   * Set ship component name that tech provides
   * @param component Component name
   */
  public void setComponent(final String component) {
    this.component = component;
  }

  /**
   * Get Improvement that tech provides
   * @return Improvement name that tech provides or null
   */
  public String getImprovement() {
    return improvement;
  }

  /**
   * Set improvement that tech provides
   * @param improvement name as a String
   */
  public void setImprovement(final String improvement) {
    this.improvement = improvement;
  }

  /**
   * Get Hull that tech provides
   * @return The hull
   */
  public String getHull() {
    return hull;
  }

  /**
   * Set hull that tech provides
   * @param hull name that tech provides
   */
  public void setHull(final String hull) {
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
  public void setIcon(final Icon16x16 icon) {
    this.icon = icon;
  }

  @Override
  public String toString() {
    return name;
  }

  /**
   * Get Tech info as a String
   * @param race SpaceRace who is building the tech
   * @return tech info
   */
  public String getTechInfo(final SpaceRace race) {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append("\n");
    sb.append(getType().toString());
    sb.append(" - ");
    sb.append(level);
    sb.append("\n");
    Building building = null;
    ShipComponent comp = null;
    ShipHull shipHull = null;
    if (improvement != null) {
      sb.append("Improvement: ");
      sb.append("\n");
      sb.append(improvement);
      building = BuildingFactory.createByName(improvement);
      sb.append("\n");
    }
    if (hull != null) {
      sb.append("Ship design: ");
      sb.append("\n");
      sb.append(hull);
      shipHull = ShipHullFactory.createByName(hull, race);
      sb.append("\n");
    }
    if (component != null) {
      sb.append("Ship component: ");
      sb.append("\n");
      sb.append(component);
      comp = ShipComponentFactory.createByName(component);
      sb.append("\n");
    }
    sb.append("\n");
    if (building != null) {
      sb.append(building.getFullDescription());
    }
    if (comp != null) {
      sb.append(comp.toString());
    }
    if (shipHull != null) {
      sb.append(shipHull.toString());
    }
    return sb.toString();
  }

}
