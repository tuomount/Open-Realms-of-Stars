package org.openRealmOfStars.player.tech;

import java.util.ArrayList;

import org.openRealmOfStars.gui.icons.Icons;
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
 * Tech factory for generating new techs for players
 *
 */
public final class TechFactory {

  /**
   * Hiding the constructor
   */
  private TechFactory() {
    // Nothing to do
  }
  /**
   * Combat tech names for level 1
   */
  public static final String[] COMBAT_TECH_LEVEL1_NAMES = {"Laser Mk1",
      "Railgun Mk1", "Photon torpedo Mk1" };
  /**
   * Combat tech names for level 2
   */
  public static final String[] COMBAT_TECH_LEVEL2_NAMES = {"Laser Mk2",
      "Railgun Mk2", "Photon torpedo Mk2", "Planetary invasion module" };
  /**
   * Combat tech names for level 3
   */
  public static final String[] COMBAT_TECH_LEVEL3_NAMES = {"Laser Mk3",
      "Railgun Mk3", "Photon torpedo Mk3", "ECM torpedo Mk1",
      "HE missile Mk1" };
  /**
   * Combat tech names for level 4
   */
  public static final String[] COMBAT_TECH_LEVEL4_NAMES = {"Laser Mk4",
      "Railgun Mk4", "Photon torpedo Mk4", "ECM torpedo Mk2", "HE missile Mk2",
      "Orbital bombs Mk1" };
  /**
   * Combat tech names for level 5
   */
  public static final String[] COMBAT_TECH_LEVEL5_NAMES = {"Laser Mk5",
      "Railgun Mk5", "Photon torpedo Mk5", "ECM torpedo Mk3", "HE missile Mk3",
      "Orbital bombs Mk2" };
  /**
   * Combat tech names for level 6
   */
  public static final String[] COMBAT_TECH_LEVEL6_NAMES = {"Phasors Mk1",
      "Massdrive Mk1", "Photon torpedo Mk6", "ECM torpedo Mk4",
      "HE missile Mk4" };
  /**
   * Combat tech names for level 7
   */
  public static final String[] COMBAT_TECH_LEVEL7_NAMES = {"Phasors Mk2",
      "Massdrive Mk2", "Photon torpedo Mk7", "ECM torpedo Mk5",
      "HE missile Mk5", "Shock trooper module" };
  /**
   * Combat tech names for level 8
   */
  public static final String[] COMBAT_TECH_LEVEL8_NAMES = {"Phasors Mk3",
      "Massdrive Mk3", "Photon torpedo Mk8", "ECM torpedo Mk6",
      "HE missile Mk6", "Orbital nuke" };
  /**
   * Combat tech names for level 9
   */
  public static final String[] COMBAT_TECH_LEVEL9_NAMES = {
      "Antimatter beam Mk1", "Massdrive Mk4", "Photon torpedo Mk9",
      "ECM torpedo Mk7", "HE missile Mk7", "Orbital smart bombs" };
  /**
   * Combat tech names for level 10
   */
  public static final String[] COMBAT_TECH_LEVEL10_NAMES = {
      "Antimatter beam Mk1", "Massdrive Mk5", "Photon torpedo Mk10",
      "ECM torpedo Mk8", "HE missile Mk8" };

  /**
   * Defense tech names for level 1
   */
  public static final String[] DEFENSE_TECH_LEVEL1_NAMES = {"Shield Mk1",
      "Armor plating Mk1" };
  /**
   * Defense tech names for level 2
   */
  public static final String[] DEFENSE_TECH_LEVEL2_NAMES = {"Shield Mk2",
      "Armor plating Mk2", "Planetary defense turret Mk1" };
  /**
   * Defense tech names for level 3
   */
  public static final String[] DEFENSE_TECH_LEVEL3_NAMES = {"Shield Mk3",
      "Armor plating Mk3" };
  /**
   * Defense tech names for level 4
   */
  public static final String[] DEFENSE_TECH_LEVEL4_NAMES = {"Shield Mk4",
      "Armor plating Mk4", "Shield generator Mk1" };
  /**
   * Defense tech names for level 5
   */
  public static final String[] DEFENSE_TECH_LEVEL5_NAMES = {"Shield Mk5",
      "Armor plating Mk5", "Planetary defense turret Mk2" };
  /**
   * Defense tech names for level 6
   */
  public static final String[] DEFENSE_TECH_LEVEL6_NAMES = {"Shield Mk6",
      "Armor plating Mk6" };
  /**
   * Defense tech names for level 7
   */
  public static final String[] DEFENSE_TECH_LEVEL7_NAMES = {"Shield Mk7",
      "Armor plating Mk7" };
  /**
   * Defense tech names for level 8
   */
  public static final String[] DEFENSE_TECH_LEVEL8_NAMES = {"Shield Mk8",
      "Armor plating Mk8", "Planetary defense turret Mk3" };
  /**
   * Defense tech names for level 9
   */
  public static final String[] DEFENSE_TECH_LEVEL9_NAMES = {"Shield Mk9",
      "Armor plating Mk9", "Shield generator Mk2" };
  /**
   * Defense tech names for level 10
   */
  public static final String[] DEFENSE_TECH_LEVEL10_NAMES = {"Shield Mk10",
      "Armor plating Mk10" };

  /**
   * Hull tech names for level 1
   */
  public static final String[] HULL_TECH_LEVEL1_NAMES = {"Scout Mk1",
      "Destroyer Mk1", "Colony" };
  /**
   * Hull tech names for level 2
   */
  public static final String[] HULL_TECH_LEVEL2_NAMES = {"Probe",
      "Small freighter", "Small starbase Mk1" };
  /**
   * Hull tech names for level 3
   */
  public static final String[] HULL_TECH_LEVEL3_NAMES = {"Destroyer Mk2",
      "Corvette Mk1", "Small starbase Mk2" };
  /**
   * Hull tech names for level 4
   */
  public static final String[] HULL_TECH_LEVEL4_NAMES = {"Medium freighter",
      "Medium starbase", "Scout Mk2" };
  /**
   * Hull tech names for level 5
   */
  public static final String[] HULL_TECH_LEVEL5_NAMES = {"Cruiser",
      "Battleship Mk1", "Privateer Mk1" };
  /**
   * Hull tech names for level 6
   */
  public static final String[] HULL_TECH_LEVEL6_NAMES = {"Large freighter",
      "Large starbase", "Corvette Mk2" };
  /**
   * Hull tech names for level 7
   */
  public static final String[] HULL_TECH_LEVEL7_NAMES = {"Battle cruiser Mk1",
      "Privateer Mk2", "Scout Mk3" };
  /**
   * Hull tech names for level 8
   */
  public static final String[] HULL_TECH_LEVEL8_NAMES = {"Massive freighter",
      "Massive starbase", "Destroyer Mk3", "Corvette Mk3" };
  /**
   * Hull tech names for level 9
   */
  public static final String[] HULL_TECH_LEVEL9_NAMES = {"Battleship Mk2",
      "Privateer Mk3", "Battle cruiser Mk2" };
  /**
   * Hull tech names for level 10
   */
  public static final String[] HULL_TECH_LEVEL10_NAMES = {"Capital ship",
      "Destroyer Mk4", "Scout Mk4" };

  /**
   * Planetary Improvement tech names for level 1
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL1_NAMES = {"Basic lab",
      "Barracks", "Tax center" };
  /**
   * Planetary Improvement tech names for level 2
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL2_NAMES = {"Advanced farm",
      "Advanced mine", "Advanced factory" };
  /**
   * Planetary Improvement tech names for level 3
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL3_NAMES = {
      "Advanced laboratory", "Market center", "Culture center" };
  /**
   * Planetary Improvement tech names for level 4
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL4_NAMES = {"Trade center",
      "Extreme sports center", "Recycle center" };
  /**
   * Planetary Improvement tech names for level 5
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL5_NAMES = {
      "Farming center", "Mining center", "Manufacturing center",
      "Radiation dampener" };
  /**
   * Planetary Improvement tech names for level 6
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL6_NAMES = {
      "Research center", "Stock market", "Galactic sports center" };
  /**
   * Planetary Improvement tech names for level 7
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL7_NAMES = {
      "New technology center", "VR movie center", "Advanced recycle center" };
  /**
   * Planetary Improvement tech names for level 8
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL8_NAMES = {"Galactic bank",
      "Radiation well" };
  /**
   * Planetary Improvement tech names for level 9
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL9_NAMES = {
      "Hydropodic farming center", "Nanobot mining center",
      "Nanobot manufacturing center" };
  /**
   * Planetary Improvement tech names for level 10
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL10_NAMES = {
      "Neural research center", "Super AI Center", "Replicator center" };

  /**
   * Propulsion tech names for level 1
   */
  public static final String[] PROPULSION_TECH_LEVEL1_NAMES = {"Ion drive Mk1",
      "Fission source Mk1", "Ion drive Mk2", "Nuclear drive Mk1",
      "Fission source Mk2" };
  /**
   * Propulsion tech names for level 2
   */
  public static final String[] PROPULSION_TECH_LEVEL2_NAMES = {"Ion drive Mk3",
      "Hyper drive Mk1", "Fusion source Mk1" };
  /**
   * Propulsion tech names for level 3
   */
  public static final String[] PROPULSION_TECH_LEVEL3_NAMES = {"Warp drive Mk1",
      "Nuclear drive Mk2", "Fusion source Mk2" };
  /**
   * Propulsion tech names for level 4
   */
  public static final String[] PROPULSION_TECH_LEVEL4_NAMES = {"Warp drive Mk2",
      "Hyper drive Mk2", "Tachyon source Mk1" };
  /**
   * Propulsion tech names for level 5
   */
  public static final String[] PROPULSION_TECH_LEVEL5_NAMES = {"Warp drive Mk3",
      "Hyper drive Mk3", "Tachyon source Mk2" };
  /**
   * Propulsion tech names for level 6
   */
  public static final String[] PROPULSION_TECH_LEVEL6_NAMES = {"Warp drive Mk4",
      "Hyper drive Mk4", "Nuclear drive Mk3", "Antimatter source Mk1" };
  /**
   * Propulsion tech names for level 7
   */
  public static final String[] PROPULSION_TECH_LEVEL7_NAMES = {"Warp drive Mk5",
      "Hyper drive Mk5", "Impulse engine Mk1" };
  /**
   * Propulsion tech names for level 8
   */
  public static final String[] PROPULSION_TECH_LEVEL8_NAMES = {"Warp drive Mk6",
      "Hyper drive Mk6", "Impulse engine Mk2", "Antimatter source Mk2" };
  /**
   * Propulsion tech names for level 9
   */
  public static final String[] PROPULSION_TECH_LEVEL9_NAMES = {"Warp drive Mk7",
      "Hyper drive Mk7", "Impulse engine Mk3", "Zero-point source Mk1" };
  /**
   * Propulsion tech names for level 10
   */
  public static final String[] PROPULSION_TECH_LEVEL10_NAMES = {
      "Warp drive Mk8", "Hyper drive Mk8", "Impulse engine Mk4",
      "Zero-point source Mk2" };

  /**
   * Electronics tech names for level 1
   */
  public static final String[] ELECTRONICS_TECH_LEVEL1_NAMES = {"Scanner Mk1",
      "Planetary scanner Mk1", "Cloaking device Mk1" };
  /**
   * Electronics tech names for level 2
   */
  public static final String[] ELECTRONICS_TECH_LEVEL2_NAMES = {
      "Cloaking device Mk2", "Targeting computer Mk1" };
  /**
   * Electronics tech names for level 3
   */
  public static final String[] ELECTRONICS_TECH_LEVEL3_NAMES = {"Scanner Mk2",
      "Planetary scanner Mk2", "Jammer Mk1" };
  /**
   * Electronics tech names for level 4
   */
  public static final String[] ELECTRONICS_TECH_LEVEL4_NAMES = {
      "Cloaking device Mk3", "Targeting computer Mk2", "LR scanner Mk1" };
  /**
   * Electronics tech names for level 5
   */
  public static final String[] ELECTRONICS_TECH_LEVEL5_NAMES = {"Scanner Mk3",
      "Planetary scanner Mk3", "Jammer Mk2" };
  /**
   * Electronics tech names for level 6
   */
  public static final String[] ELECTRONICS_TECH_LEVEL6_NAMES = {
      "Cloaking device Mk4", "Targeting computer Mk3", "LR scanner Mk2" };
  /**
   * Electronics tech names for level 7
   */
  public static final String[] ELECTRONICS_TECH_LEVEL7_NAMES = {"Scanner Mk4",
      "Jammer Mk3" };
  /**
   * Electronics tech names for level 8
   */
  public static final String[] ELECTRONICS_TECH_LEVEL8_NAMES = {
      "Cloaking device Mk5", "Planetary scanner Mk4", "LR scanner Mk3" };
  /**
   * Electronics tech names for level 9
   */
  public static final String[] ELECTRONICS_TECH_LEVEL9_NAMES = {"Scanner Mk5",
      "Targeting computer Mk4" };
  /**
   * Electronics tech names for level 10
   */
  public static final String[] ELECTRONICS_TECH_LEVEL10_NAMES = {
      "Cloaking device Mk6", "Planetary scanner Mk5", "Jammer Mk4" };

  /**
   * Create combat tech with certain name and level
   * @param name Tech Name
   * @param level level between 1-10
   * @return Tech or null if match not found
   */
  public static Tech createCombatTech(final String name, final int level) {
    String[] list;
    switch (level) {
    case 1:
      list = COMBAT_TECH_LEVEL1_NAMES;
      break;
    case 2:
      list = COMBAT_TECH_LEVEL2_NAMES;
      break;
    case 3:
      list = COMBAT_TECH_LEVEL3_NAMES;
      break;
    case 4:
      list = COMBAT_TECH_LEVEL4_NAMES;
      break;
    case 5:
      list = COMBAT_TECH_LEVEL5_NAMES;
      break;
    case 6:
      list = COMBAT_TECH_LEVEL6_NAMES;
      break;
    case 7:
      list = COMBAT_TECH_LEVEL7_NAMES;
      break;
    case 8:
      list = COMBAT_TECH_LEVEL8_NAMES;
      break;
    case 9:
      list = COMBAT_TECH_LEVEL9_NAMES;
      break;
    case 10:
      list = COMBAT_TECH_LEVEL10_NAMES;
      break;
    default:
      return null;
    }
    for (int i = 0; i < list.length; i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Combat, level);
        tech.setComponent(techName);
        if (techName.startsWith("Laser") || techName.startsWith("Phasor")
            || techName.startsWith("Antimatter beam")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_LASERGUN));
        } else if (techName.startsWith("Railgun")
            || techName.startsWith("Massdrive")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_COMBAT_TECH));
        } else if (techName.startsWith("Photon torpedo")
            || techName.startsWith("ECM torpedo")
            || techName.startsWith("HE missile")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_MISSILE));
        } else if (techName.startsWith("Orbital bomb")
            || techName.startsWith("Orbital smart bomb")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_BOMB));
        } else if (techName.startsWith("Orbital nuke")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_NUKE));
        } else if (techName.startsWith("Planetary invasion module")
            || techName.startsWith("Shock trooper module")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_TROOPS));
        } else {
          tech.setIcon(Icons.getIconByName(Icons.ICON_COMBAT_TECH));
        }
        return tech;
      }
    }
    return null;
  }

  /**
   * Create defense tech with certain name and level
   * @param name Tech Name
   * @param level level between 1-10
   * @return Tech or null if match not found
   */
  public static Tech createDefenseTech(final String name, final int level) {
    String[] list;
    switch (level) {
    case 1:
      list = DEFENSE_TECH_LEVEL1_NAMES;
      break;
    case 2:
      list = DEFENSE_TECH_LEVEL2_NAMES;
      break;
    case 3:
      list = DEFENSE_TECH_LEVEL3_NAMES;
      break;
    case 4:
      list = DEFENSE_TECH_LEVEL4_NAMES;
      break;
    case 5:
      list = DEFENSE_TECH_LEVEL5_NAMES;
      break;
    case 6:
      list = DEFENSE_TECH_LEVEL6_NAMES;
      break;
    case 7:
      list = DEFENSE_TECH_LEVEL7_NAMES;
      break;
    case 8:
      list = DEFENSE_TECH_LEVEL8_NAMES;
      break;
    case 9:
      list = DEFENSE_TECH_LEVEL9_NAMES;
      break;
    case 10:
      list = DEFENSE_TECH_LEVEL10_NAMES;
      break;
    default:
      return null;
    }
    for (int i = 0; i < list.length; i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Defense, level);
        if (techName.startsWith("Planetary defense turret Mk")) {
          tech.setImprovement(techName);
        } else {
          tech.setComponent(techName);
        }
        if (techName.startsWith("Shield")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_SHIELD));
        } else if (techName.startsWith("Armor plating")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_ARMOR));
        } else if (techName.startsWith("Planetary defense turret Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANETARY_TURRET));
        } else {
          tech.setIcon(Icons.getIconByName(Icons.ICON_DEFENSE_TECH));
        }
        return tech;
      }
    }
    return null;
  }

  /**
   * Create Hull tech with certain name and level
   * @param name Tech Name
   * @param level level between 1-10
   * @return Tech or null if match not found
   */
  public static Tech createHullTech(final String name, final int level) {
    String[] list;
    switch (level) {
    case 1:
      list = HULL_TECH_LEVEL1_NAMES;
      break;
    case 2:
      list = HULL_TECH_LEVEL2_NAMES;
      break;
    case 3:
      list = HULL_TECH_LEVEL3_NAMES;
      break;
    case 4:
      list = HULL_TECH_LEVEL4_NAMES;
      break;
    case 5:
      list = HULL_TECH_LEVEL5_NAMES;
      break;
    case 6:
      list = HULL_TECH_LEVEL6_NAMES;
      break;
    case 7:
      list = HULL_TECH_LEVEL7_NAMES;
      break;
    case 8:
      list = HULL_TECH_LEVEL8_NAMES;
      break;
    case 9:
      list = HULL_TECH_LEVEL9_NAMES;
      break;
    case 10:
      list = HULL_TECH_LEVEL10_NAMES;
      break;
    default:
      return null;
    }
    for (int i = 0; i < list.length; i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Hulls, level);
        if (techName.contains("starbase")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_STARBASE));
        } else {
          tech.setIcon(Icons.getIconByName(Icons.ICON_HULL_TECH));
        }
        if (techName.startsWith("Privateer Mk")) {
          tech.setComponent("Privateer module");
          tech.setHull(techName);
        } else if (techName.startsWith("Colony")) {
          tech.setHull(techName);
          tech.setComponent("Colonization module");
          tech.setIcon(Icons.getIconByName(Icons.ICON_PEOPLE));
        } else {
          tech.setHull(techName);
        }
        return tech;
      }
    }
    return null;
  }

  /**
   * Create improvement tech with certain name and level
   * @param name Tech Name
   * @param level level between 1-10
   * @return Tech or null if match not found
   */
  public static Tech createImprovementTech(final String name, final int level) {
    String[] list;
    switch (level) {
    case 1:
      list = IMPROVEMENT_TECH_LEVEL1_NAMES;
      break;
    case 2:
      list = IMPROVEMENT_TECH_LEVEL2_NAMES;
      break;
    case 3:
      list = IMPROVEMENT_TECH_LEVEL3_NAMES;
      break;
    case 4:
      list = IMPROVEMENT_TECH_LEVEL4_NAMES;
      break;
    case 5:
      list = IMPROVEMENT_TECH_LEVEL5_NAMES;
      break;
    case 6:
      list = IMPROVEMENT_TECH_LEVEL6_NAMES;
      break;
    case 7:
      list = IMPROVEMENT_TECH_LEVEL7_NAMES;
      break;
    case 8:
      list = IMPROVEMENT_TECH_LEVEL8_NAMES;
      break;
    case 9:
      list = IMPROVEMENT_TECH_LEVEL9_NAMES;
      break;
    case 10:
      list = IMPROVEMENT_TECH_LEVEL10_NAMES;
      break;
    default:
      return null;
    }
    for (int i = 0; i < list.length; i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Improvements, level);
        tech.setImprovement(techName);
        if (techName.startsWith("Barracks")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_TROOPS));
        } else if (techName.startsWith("Basic lab")
            || techName.startsWith("Advanced laboratory")
            || techName.startsWith("Research center")
            || techName.startsWith("New technology center")
            || techName.startsWith("Neural research center")
            || techName.startsWith("Super AI Center")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_RESEARCH));
        } else if (techName.startsWith("Tax center")
            || techName.startsWith("Market center")
            || techName.startsWith("Trade center")
            || techName.startsWith("Stock market")
            || techName.startsWith("Galactic bank")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_CREDIT));
        } else if (techName.startsWith("Advanced farm")
            || techName.startsWith("Farming center")
            || techName.startsWith("Hydropodic farming center")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_FARM));
        } else if (techName.startsWith("Advanced mine")
            || techName.startsWith("Mining center")
            || techName.startsWith("Nanobot mining center")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_MINE));
        } else if (techName.startsWith("Advanced factory")
            || techName.startsWith("Manufacturing center")
            || techName.startsWith("Nanobot manufacturing center")
            || techName.startsWith("Replicator center")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_FACTORY));
        } else if (techName.startsWith("Culture center")
            || techName.startsWith("Extreme sports center")
            || techName.startsWith("Galactic sports center")
            || techName.startsWith("VR movie center")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_CULTURE));
        } else {
          tech.setIcon(Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
        }
        return tech;
      }
    }
    return null;
  }

  /**
   * Create propulsion tech with certain name and level
   * @param name Tech Name
   * @param level level between 1-10
   * @return Tech or null if match not found
   */
  public static Tech createPropulsionTech(final String name, final int level) {
    String[] list;
    switch (level) {
    case 1:
      list = PROPULSION_TECH_LEVEL1_NAMES;
      break;
    case 2:
      list = PROPULSION_TECH_LEVEL2_NAMES;
      break;
    case 3:
      list = PROPULSION_TECH_LEVEL3_NAMES;
      break;
    case 4:
      list = PROPULSION_TECH_LEVEL4_NAMES;
      break;
    case 5:
      list = PROPULSION_TECH_LEVEL5_NAMES;
      break;
    case 6:
      list = PROPULSION_TECH_LEVEL6_NAMES;
      break;
    case 7:
      list = PROPULSION_TECH_LEVEL7_NAMES;
      break;
    case 8:
      list = PROPULSION_TECH_LEVEL8_NAMES;
      break;
    case 9:
      list = PROPULSION_TECH_LEVEL9_NAMES;
      break;
    case 10:
      list = PROPULSION_TECH_LEVEL10_NAMES;
      break;
    default:
      return null;
    }
    for (int i = 0; i < list.length; i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Propulsion, level);
        tech.setComponent(techName);
        if (tech.getName().contains(" source Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_POWERSOURCE));
        } else {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PROPULSION_TECH));
        }
        return tech;
      }
    }
    return null;
  }

  /**
   * Create electronics tech with certain name and level
   * @param name Tech Name
   * @param level level between 1-10
   * @return Tech or null if match not found
   */
  public static Tech createElectronicsTech(final String name, final int level) {
    String[] list;
    switch (level) {
    case 1:
      list = ELECTRONICS_TECH_LEVEL1_NAMES;
      break;
    case 2:
      list = ELECTRONICS_TECH_LEVEL2_NAMES;
      break;
    case 3:
      list = ELECTRONICS_TECH_LEVEL3_NAMES;
      break;
    case 4:
      list = ELECTRONICS_TECH_LEVEL4_NAMES;
      break;
    case 5:
      list = ELECTRONICS_TECH_LEVEL5_NAMES;
      break;
    case 6:
      list = ELECTRONICS_TECH_LEVEL6_NAMES;
      break;
    case 7:
      list = ELECTRONICS_TECH_LEVEL7_NAMES;
      break;
    case 8:
      list = ELECTRONICS_TECH_LEVEL8_NAMES;
      break;
    case 9:
      list = ELECTRONICS_TECH_LEVEL9_NAMES;
      break;
    case 10:
      list = ELECTRONICS_TECH_LEVEL10_NAMES;
      break;
    default:
      return null;
    }
    for (int i = 0; i < list.length; i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Electrics, level);
        if (techName.startsWith("Planetary scanner Mk")) {
          tech.setImprovement(techName);
        } else {
          tech.setComponent(techName);
        }
        if (techName.startsWith("Planetary scanner Mk")
            || techName.startsWith("LR scanner Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_LR_SCANNER));
        } else if (techName.startsWith("Scanner Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_SCANNER));
        } else if (techName.startsWith("Cloaking device")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_CLOACKING_DEVICE));
        } else if (techName.startsWith("Jammer Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_CIRCUIT_BOARD));
        } else {
          tech.setIcon(Icons.getIconByName(Icons.ICON_ELECTRONICS_TECH));
        }
        return tech;
      }
    }
    return null;
  }

  /**
   * Create random tech by tech type and level, but not choose those tech
   * player already has
   * @param type Tech Type: Combat, Defense, Hulls, Improvements, Propulsion
   *             and Electrics
   * @param level Tech Level 1-10
   * @param alreadyHas List of tech player has
   * @return Tech or null if cannot find new
   */
  public static Tech createRandomTech(final TechType type, final int level,
      final Tech[] alreadyHas) {
    String[] alreadyHasList = new String[alreadyHas.length];
    for (int i = 0; i < alreadyHasList.length; i++) {
      alreadyHasList[i] = alreadyHas[i].getName();
    }
    String[] options = getListByTechLevel(type, level);
    ArrayList<String> choices = new ArrayList<>();
    for (String opt : options) {
      boolean playerHas = false;
      for (String has : alreadyHasList) {
        if (has.equals(opt)) {
          playerHas = true;
          break;
        }
      }
      if (!playerHas) {
        choices.add(opt);
      }
    }
    if (choices.size() > 0) {
      int index = DiceGenerator.getRandom(choices.size() - 1);
      switch (type) {
      case Combat:
        return createCombatTech(choices.get(index), level);
      case Defense:
        return createDefenseTech(choices.get(index), level);
      case Hulls:
        return createHullTech(choices.get(index), level);
      case Improvements:
        return createImprovementTech(choices.get(index), level);
      case Propulsion:
        return createPropulsionTech(choices.get(index), level);
      case Electrics:
        return createElectronicsTech(choices.get(index), level);
      default:
        return createCombatTech(choices.get(index), level);
      }
    }
    return null;
  }

  /**
   * Tech 1 Level cost
   */
  private static final int TECH_1_LEVEL_RP_COST = 5;
  /**
   * Tech 2 Level cost
   */
  private static final int TECH_2_LEVEL_RP_COST = 7;
  /**
   * Tech 3 Level cost
   */
  private static final int TECH_3_LEVEL_RP_COST = 10;
  /**
   * Tech 4 Level cost
   */
  private static final int TECH_4_LEVEL_RP_COST = 15;
  /**
   * Tech 5 Level cost
   */
  private static final int TECH_5_LEVEL_RP_COST = 22;
  /**
   * Tech 6 Level cost
   */
  private static final int TECH_6_LEVEL_RP_COST = 33;
  /**
   * Tech 7 Level cost
   */
  private static final int TECH_7_LEVEL_RP_COST = 50;
  /**
   * Tech 8 Level cost
   */
  private static final int TECH_8_LEVEL_RP_COST = 75;
  /**
   * Tech 9 Level cost
   */
  private static final int TECH_9_LEVEL_RP_COST = 112;
  /**
   * Tech 10 Level cost
   */
  private static final int TECH_10_LEVEL_RP_COST = 168;
  /**
   * Tech FUTURE Level cost
   */
  private static final int TECH_FUTURE_LEVEL_RP_COST = 200;

  /**
   * Get tech level cost as research points
   * @param level Level to research
   * @return Amount of research points required
   */
  public static int getTechCost(final int level) {
    switch (level) {
    case 1:
      return TECH_1_LEVEL_RP_COST;
    case 2:
      return TECH_2_LEVEL_RP_COST;
    case 3:
      return TECH_3_LEVEL_RP_COST;
    case 4:
      return TECH_4_LEVEL_RP_COST;
    case 5:
      return TECH_5_LEVEL_RP_COST;
    case 6:
      return TECH_6_LEVEL_RP_COST;
    case 7:
      return TECH_7_LEVEL_RP_COST;
    case 8:
      return TECH_8_LEVEL_RP_COST;
    case 9:
      return TECH_9_LEVEL_RP_COST;
    case 10:
      return TECH_10_LEVEL_RP_COST;
    default:
      return TECH_FUTURE_LEVEL_RP_COST;
    }
  }

  /**
   * Get String list of tech by type and level
   * @param type The tech type
   * @param level The tech level
   * @return String array of tech
   */
  public static String[] getListByTechLevel(final TechType type,
      final int level) {
    switch (type) {
    case Combat:
      switch (level) {
      case 1:
        return COMBAT_TECH_LEVEL1_NAMES;
      case 2:
        return COMBAT_TECH_LEVEL2_NAMES;
      case 3:
        return COMBAT_TECH_LEVEL3_NAMES;
      case 4:
        return COMBAT_TECH_LEVEL4_NAMES;
      case 5:
        return COMBAT_TECH_LEVEL5_NAMES;
      case 6:
        return COMBAT_TECH_LEVEL6_NAMES;
      case 7:
        return COMBAT_TECH_LEVEL7_NAMES;
      case 8:
        return COMBAT_TECH_LEVEL8_NAMES;
      case 9:
        return COMBAT_TECH_LEVEL9_NAMES;
      case 10:
        return COMBAT_TECH_LEVEL10_NAMES;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
    case Defense:
      switch (level) {
      case 1:
        return DEFENSE_TECH_LEVEL1_NAMES;
      case 2:
        return DEFENSE_TECH_LEVEL2_NAMES;
      case 3:
        return DEFENSE_TECH_LEVEL3_NAMES;
      case 4:
        return DEFENSE_TECH_LEVEL4_NAMES;
      case 5:
        return DEFENSE_TECH_LEVEL5_NAMES;
      case 6:
        return DEFENSE_TECH_LEVEL6_NAMES;
      case 7:
        return DEFENSE_TECH_LEVEL7_NAMES;
      case 8:
        return DEFENSE_TECH_LEVEL8_NAMES;
      case 9:
        return DEFENSE_TECH_LEVEL9_NAMES;
      case 10:
        return DEFENSE_TECH_LEVEL10_NAMES;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
    case Hulls:
      switch (level) {
      case 1:
        return HULL_TECH_LEVEL1_NAMES;
      case 2:
        return HULL_TECH_LEVEL2_NAMES;
      case 3:
        return HULL_TECH_LEVEL3_NAMES;
      case 4:
        return HULL_TECH_LEVEL4_NAMES;
      case 5:
        return HULL_TECH_LEVEL5_NAMES;
      case 6:
        return HULL_TECH_LEVEL6_NAMES;
      case 7:
        return HULL_TECH_LEVEL7_NAMES;
      case 8:
        return HULL_TECH_LEVEL8_NAMES;
      case 9:
        return HULL_TECH_LEVEL9_NAMES;
      case 10:
        return HULL_TECH_LEVEL10_NAMES;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
    case Improvements:
      switch (level) {
      case 1:
        return IMPROVEMENT_TECH_LEVEL1_NAMES;
      case 2:
        return IMPROVEMENT_TECH_LEVEL2_NAMES;
      case 3:
        return IMPROVEMENT_TECH_LEVEL3_NAMES;
      case 4:
        return IMPROVEMENT_TECH_LEVEL4_NAMES;
      case 5:
        return IMPROVEMENT_TECH_LEVEL5_NAMES;
      case 6:
        return IMPROVEMENT_TECH_LEVEL6_NAMES;
      case 7:
        return IMPROVEMENT_TECH_LEVEL7_NAMES;
      case 8:
        return IMPROVEMENT_TECH_LEVEL8_NAMES;
      case 9:
        return IMPROVEMENT_TECH_LEVEL9_NAMES;
      case 10:
        return IMPROVEMENT_TECH_LEVEL10_NAMES;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
    case Propulsion:
      switch (level) {
      case 1:
        return PROPULSION_TECH_LEVEL1_NAMES;
      case 2:
        return PROPULSION_TECH_LEVEL2_NAMES;
      case 3:
        return PROPULSION_TECH_LEVEL3_NAMES;
      case 4:
        return PROPULSION_TECH_LEVEL4_NAMES;
      case 5:
        return PROPULSION_TECH_LEVEL5_NAMES;
      case 6:
        return PROPULSION_TECH_LEVEL6_NAMES;
      case 7:
        return PROPULSION_TECH_LEVEL7_NAMES;
      case 8:
        return PROPULSION_TECH_LEVEL8_NAMES;
      case 9:
        return PROPULSION_TECH_LEVEL9_NAMES;
      case 10:
        return PROPULSION_TECH_LEVEL10_NAMES;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
    case Electrics:
      switch (level) {
      case 1:
        return ELECTRONICS_TECH_LEVEL1_NAMES;
      case 2:
        return ELECTRONICS_TECH_LEVEL2_NAMES;
      case 3:
        return ELECTRONICS_TECH_LEVEL3_NAMES;
      case 4:
        return ELECTRONICS_TECH_LEVEL4_NAMES;
      case 5:
        return ELECTRONICS_TECH_LEVEL5_NAMES;
      case 6:
        return ELECTRONICS_TECH_LEVEL6_NAMES;
      case 7:
        return ELECTRONICS_TECH_LEVEL7_NAMES;
      case 8:
        return ELECTRONICS_TECH_LEVEL8_NAMES;
      case 9:
        return ELECTRONICS_TECH_LEVEL9_NAMES;
      case 10:
        return ELECTRONICS_TECH_LEVEL10_NAMES;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
    default:
      throw new IllegalArgumentException("Illegal tech type!");
    }
  }

}
