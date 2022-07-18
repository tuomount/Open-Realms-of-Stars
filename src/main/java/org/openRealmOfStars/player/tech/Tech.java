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
 * Copyright (C) 2016,2020 Tuomo Untinen
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
   * Is technology rare tech
   */
  private boolean rareTech;
  /**
   * Next rare tech on same tree. This should be null if
   * not rare tech or last tech on tree.
   */
  private String nextTechOnTree;
  /**
   * Which level next tech will be found.
   */
  private int nextTechLevel;
  /**
   * Icon for Tech
   */
  private Icon16x16 icon;

  /**
   * Is tech tradeable to another realms?
   */
  private boolean tradeable;
  /**
   * If this is true list of space races is excluded not gaining this tech.
   * If this is false list of space race is included gain this tech.
   */
  private boolean excludeList;

  /**
   * List of space races.
   */
  private SpaceRace[] spaceRaces;
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
    nextTechOnTree = null;
    nextTechLevel = 1;
    rareTech = false;
    tradeable = true;
    excludeList = false;
    spaceRaces = new SpaceRace[0];
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
   * Get special information about the tech.
   * @param techName Tech name for checking special text.
   * @return Special text or null
   */
  private static String getSpecialText(final String techName) {
    String result = null;
    if (techName.equals("Deadly virus")) {
      result = "Genetic code of deadly virus. This allows realm to do\n"
          + "special espionage operation against another realm. Succesful\n"
          + "operation will kill all but one population from the planet.\n"
          + "That realm will also then learn this deadly virus.\n"
          + "This deadly virus can also accidentaly spread when doing\n"
          + "trade with another realm.";
    }
    if (techName.equals("Advanced colonization")) {
      result = "Improves survivality of colonies on almost barren silicon\n"
          + " worlds. This will increase silicon world suitability by 25%.";
    }
    if (techName.equals("Desert colonization")) {
      result = "Improves survivality of colonies on desert worlds.\n"
          + " This will increase desert world suitability by 25%.";
    }
    if (techName.equals("Carbon colonization")) {
      result = "Improves survivality of colonies on carbon worlds.\n"
          + " This will increase carbon world suitability by 25%.";
    }
    if (techName.equals("Ice colonization")) {
      result = "Improves survivality of colonies on ice worlds.\n"
          + " This will increase ice world suitability by 25%.";
    }
    if (techName.equals("Iron colonization")) {
      result = "Improves survivality of colonies on iron worlds.\n"
          + " This will increase iron world suitability by 25%.";
    }
    if (techName.equals("Aquatic colonization")) {
      result = "Improves survivality of colonies on water worlds.\n"
          + " This will increase water world suitability by 25%.";
    }
    if (techName.equals("Improved engineer")) {
      result = " Reduces overload failure by 2 when overloading\n"
          + " components during combat.";
    }
    return result;
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
    String special = getSpecialText(name);
    if (special != null) {
      sb.append("\n");
      sb.append("Special: ");
      sb.append("\n");
      sb.append(special);
      sb.append("\n");
    }
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
    boolean extraLineChange = false;
    if (building != null) {
      sb.append(building.getFullDescription());
      extraLineChange = true;
    }
    if (comp != null) {
      if (extraLineChange) {
        sb.append("\n");
      }
      sb.append(comp.toString());
      extraLineChange = true;
    }
    if (shipHull != null) {
      if (extraLineChange) {
        sb.append("\n");
      }
      sb.append(shipHull.toString());
    }
    return sb.toString();
  }

  /**
   * Is tech rare?
   * @return the rareTech
   */
  public boolean isRareTech() {
    return rareTech;
  }

  /**
   * Set flag for rare tech.
   * @param rareTech the rareTech to set
   */
  public void setRareTech(final boolean rareTech) {
    this.rareTech = rareTech;
  }

  /**
   * Get next possible tech name or null.
   * @return the nextTechOnTree or null.
   */
  public String getNextTechOnTree() {
    return nextTechOnTree;
  }

  /**
   * Set next tech on same tree.
   * @param nextTechOnTree the nextTechOnTree to set
   */
  public void setNextTechOnTree(final String nextTechOnTree) {
    this.nextTechOnTree = nextTechOnTree;
  }

  /**
   * Get next tech level.
   * @return the nextTechLevel
   */
  public int getNextTechLevel() {
    return nextTechLevel;
  }

  /**
   * Set next tech level
   * @param nextTechLevel the nextTechLevel to set
   */
  public void setNextTechLevel(final int nextTechLevel) {
    this.nextTechLevel = nextTechLevel;
  }

  /**
   * Is tech tradeable to another realms?
   * @return True if tech is tradeable
   */
  public boolean isTradeable() {
    return tradeable;
  }

  /**
   * Set Tech tradeable flag.
   * @param tradeable True for tradeable
   */
  public void setTradeable(final boolean tradeable) {
    this.tradeable = tradeable;
  }

  /**
   * Is list of space races exclude list or not?
   * Exclude list contains races which should not gain this tech.
   * Include list contains race which only gain this tech.
   * @return True if exclude list.
   */
  public boolean isExcludeList() {
    return excludeList;
  }

  /**
   * Set flag for exclude or include list.
   * @param excludeList True for exclude, false for include
   */
  public void setExcludeList(final boolean excludeList) {
    this.excludeList = excludeList;
  }

  /**
   * Get list of space races, this can be both exclude or include
   * @return List of spaces.
   */
  public SpaceRace[] getSpaceRaces() {
    return spaceRaces;
  }

  /**
   * Set list of space race. This can be both exlude or include
   * @param spaceRaces Spacerace as var args.
   */
  public void setSpaceRaces(final SpaceRace... spaceRaces) {
    this.spaceRaces = spaceRaces;
  }

}
