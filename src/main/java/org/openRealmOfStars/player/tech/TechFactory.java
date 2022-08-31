package org.openRealmOfStars.player.tech;

import java.util.ArrayList;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.TextUtilities;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
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
      "Railgun Mk1", "Photon torpedo Mk1", "Chaingun Mk1" };
  /**
   * Combat tech names for level 2
   */
  public static final String[] COMBAT_TECH_LEVEL2_NAMES = {"Laser Mk2",
      "Railgun Mk2", "Photon torpedo Mk2",
      "Planetary invasion module", "Chaingun Mk2" };
  /**
   * Rare combat tech names for level 2.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL2_NAMES = {
      "Plasma cannon Mk1"};
  /**
   * Combat tech names for level 3
   */
  public static final String[] COMBAT_TECH_LEVEL3_NAMES = {"Laser Mk3",
      "Railgun Mk3", "Photon torpedo Mk3", "ECM torpedo Mk1",
      "HE missile Mk1", "Orbital bombs Mk1", "Chaingun Mk3" };
  /**
   * Rare combat tech names for level 3.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL3_NAMES = {"Tractor beam",
      "Ion cannon Mk1", "Plasma cannon Mk2"};
  /**
   * Combat tech names for level 4
   */
  public static final String[] COMBAT_TECH_LEVEL4_NAMES = {"Laser Mk4",
      "Railgun Mk4", "Photon torpedo Mk4", "ECM torpedo Mk2", "HE missile Mk2",
      "Orbital nuke", "Chaingun Mk4" };
  /**
   * Combat tech names for level 5
   */
  public static final String[] COMBAT_TECH_LEVEL5_NAMES = {"Laser Mk5",
      "Railgun Mk5", "Photon torpedo Mk5", "ECM torpedo Mk3", "HE missile Mk3",
      "Orbital bombs Mk2", "Autocannon Mk1" };
  /**
   * Rare combat tech names for level 5.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL5_NAMES = {
      "Ion cannon Mk2", "Plasma cannon Mk3"};
  /**
   * Combat tech names for level 6
   */
  public static final String[] COMBAT_TECH_LEVEL6_NAMES = {"Phasors Mk1",
      "Massdrive Mk1", "Photon torpedo Mk6", "ECM torpedo Mk4",
      "HE missile Mk4", "Orbital fusion bomb", "Autocannon Mk2" };
  /**
   * Rare combat tech names for level 6.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL6_NAMES = {
      "Gravity ripper Mk1" };
  /**
   * Combat tech names for level 7
   */
  public static final String[] COMBAT_TECH_LEVEL7_NAMES = {"Phasors Mk2",
      "Massdrive Mk2", "Photon torpedo Mk7", "ECM torpedo Mk5",
      "HE missile Mk5", "Shock trooper module", "Orbital antimatter bomb",
      "Autocannon Mk3"};
  /**
   * Rare combat tech names for level 7.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL7_NAMES = {
      "Ion cannon Mk3", "Plasma cannon Mk4" };
  /**
   * Combat tech names for level 8
   */
  public static final String[] COMBAT_TECH_LEVEL8_NAMES = {"Phasors Mk3",
      "Massdrive Mk3", "Photon torpedo Mk8", "ECM torpedo Mk6",
      "HE missile Mk6", "Orbital smart bombs", "Callisto multicannon Mk1"};
  /**
   * Rare combat tech names for level 8.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL8_NAMES = {
      "Gravity ripper Mk2" };
  /**
   * Combat tech names for level 9
   */
  public static final String[] COMBAT_TECH_LEVEL9_NAMES = {
      "Antimatter beam Mk1", "Massdrive Mk4", "Photon torpedo Mk9",
      "ECM torpedo Mk7", "HE missile Mk7", "Orbital neutron bomb",
      "Callisto multicannon Mk2"};
  /**
   * Rare combat tech names for level 7.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL9_NAMES = {
      "Ion cannon Mk4", "Plasma cannon Mk5"};
  /**
   * Combat tech names for level 10
   */
  public static final String[] COMBAT_TECH_LEVEL10_NAMES = {
      "Antimatter beam Mk2", "Massdrive Mk5", "Photon torpedo Mk10",
      "ECM torpedo Mk8", "HE missile Mk8", "Callisto multicannon Mk3" };
  /**
   * Rare combat tech names for level 10.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL10_NAMES = {
      "Gravity ripper Mk3" };

  /**
   * Defense tech names for level 1
   */
  public static final String[] DEFENSE_TECH_LEVEL1_NAMES = {"Shield Mk1",
      "Armor plating Mk1" };
  /**
   * Defense tech names for level 2
   */
  public static final String[] DEFENSE_TECH_LEVEL2_NAMES = {"Shield Mk2",
      "Armor plating Mk2", "Planetary defense turret Mk1",
      "Orbital elevator Mk1" };
  /**
   * Defense tech names for level 3
   */
  public static final String[] DEFENSE_TECH_LEVEL3_NAMES = {"Shield Mk3",
      "Armor plating Mk3", "Jammer Mk1", "Command outpost" };
  /**
   * Defense rare tech names for level 3.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL3_NAMES = {
      "Solar armor Mk1", "Organic armor Mk1"};
  /**
   * Defense tech names for level 4
   */
  public static final String[] DEFENSE_TECH_LEVEL4_NAMES = {"Shield Mk4",
      "Armor plating Mk4", "Shield generator Mk1", "Desert colonization" };
  /**
   * Defense rare tech names for level 4.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL4_NAMES = {
      "Distortion shield Mk1"};
  /**
   * Defense tech names for level 5
   */
  public static final String[] DEFENSE_TECH_LEVEL5_NAMES = {"Shield Mk5",
      "Armor plating Mk5", "Planetary defense turret Mk2", "Jammer Mk2",
      "Orbital elevator Mk2"};
  /**
   * Defense tech names for level 6
   */
  public static final String[] DEFENSE_TECH_LEVEL6_NAMES = {"Shield Mk6",
      "Armor plating Mk6", "Space academy", "Iron colonization" };
  /**
   * Defense rare tech names for level 6.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL6_NAMES = {
      "Solar armor Mk2", "Distortion shield Mk2", "Organic armor Mk2"};
  /**
   * Defense tech names for level 7
   */
  public static final String[] DEFENSE_TECH_LEVEL7_NAMES = {"Shield Mk7",
      "Armor plating Mk7", "Jammer Mk3", "Command center" };
  /**
   * Defense rare tech names for level 7.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL7_NAMES = {
      "Multi-dimension shield"};
  /**
   * Defense tech names for level 8
   */
  public static final String[] DEFENSE_TECH_LEVEL8_NAMES = {"Shield Mk8",
      "Armor plating Mk8", "Planetary defense turret Mk3",
      "Orbital elevator Mk3"};
  /**
   * Defense rare tech names for level 8.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL8_NAMES = {
      "Distortion shield Mk3", "Organic armor Mk3"};
  /**
   * Defense tech names for level 9
   */
  public static final String[] DEFENSE_TECH_LEVEL9_NAMES = {"Shield Mk9",
      "Armor plating Mk9", "Shield generator Mk2", "Orbital shield" };
  /**
   * Defense rare tech names for level 9.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL9_NAMES = {
      "Solar armor Mk3"};
  /**
   * Defense tech names for level 10
   */
  public static final String[] DEFENSE_TECH_LEVEL10_NAMES = {"Shield Mk10",
      "Armor plating Mk10", "Jammer Mk4" };

  /**
   * Hull tech names for level 1
   */
  public static final String[] HULL_TECH_LEVEL1_NAMES = {"Scout Mk1",
      "Destroyer Mk1", "Colony", "Minor orbital" };
  /**
   * Hull tech names for level 2
   */
  public static final String[] HULL_TECH_LEVEL2_NAMES = {"Probe",
      "Small freighter", "Small starbase Mk1", "Small orbital"};
  /**
   * Hull tech names for level 3
   */
  public static final String[] HULL_TECH_LEVEL3_NAMES = {"Destroyer Mk2",
      "Corvette Mk1", "Small starbase Mk2", "Advanced colonization"};
  /**
   * Hull rare tech names for level 3.
   */
  public static final String[] HULL_RARE_TECH_LEVEL3_NAMES = {
      "Repair module Mk1"};

  /**
   * Hull tech names for level 4
   */
  public static final String[] HULL_TECH_LEVEL4_NAMES = {"Medium freighter",
      "Medium starbase", "Scout Mk2", "Fighter bay Mk1", "Medium orbital" };
  /**
   * Hull tech names for level 5
   */
  public static final String[] HULL_TECH_LEVEL5_NAMES = {"Cruiser",
      "Battleship Mk1", "Privateer Mk1", "Fighter bay Mk2", "Large orbital" };
  /**
   * Hull rare tech names for level 5.
   */
  public static final String[] HULL_RARE_TECH_LEVEL5_NAMES = {
      "Repair module Mk2"};
  /**
   * Hull tech names for level 6
   */
  public static final String[] HULL_TECH_LEVEL6_NAMES = {"Large freighter",
      "Large starbase", "Corvette Mk2", "Artificial planet",
      "Massive orbital"};
  /**
   * Hull tech names for level 7
   */
  public static final String[] HULL_TECH_LEVEL7_NAMES = {"Battle cruiser Mk1",
      "Privateer Mk2", "Scout Mk3", "Fighter bay Mk3" };
  /**
   * Hull rare tech names for level 7.
   */
  public static final String[] HULL_RARE_TECH_LEVEL7_NAMES = {
      "Repair module Mk3"};
  /**
   * Hull tech names for level 8
   */
  public static final String[] HULL_TECH_LEVEL8_NAMES = {"Massive freighter",
      "Massive starbase", "Destroyer Mk3", "Corvette Mk3" };
  /**
   * Hull tech names for level 9
   */
  public static final String[] HULL_TECH_LEVEL9_NAMES = {"Battleship Mk2",
      "Privateer Mk3", "Battle cruiser Mk2", "Scout Mk4", "Fighter bay Mk4" };
  /**
   * Hull tech names for level 10
   */
  public static final String[] HULL_TECH_LEVEL10_NAMES = {"Capital ship",
      "Destroyer Mk4", "Corvette Mk4" };

  /**
   * Planetary Improvement tech names for level 1
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL1_NAMES = {"Basic lab",
      "Barracks", "Tax center" };
  /**
   * Planetary Improvement tech names for level 2
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL2_NAMES = {"Advanced farm",
      "Advanced mine", "Advanced factory", "Starbase music hall", "Cyber lab",
      "Advanced furnace"};
  /**
   * Planetary Improvement tech names for level 3
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL3_NAMES = {
      "Advanced laboratory", "Market center", "Culture center",
      "Starbase market" };
  /**
   * Planetary Improvement rare tech names for level 3
   */
  public static final String[] IMPROVEMENT_RARE_TECH_LEVEL3_NAMES = {
      "College of history"
  };
  /**
   * Planetary Improvement tech names for level 4
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL4_NAMES = {"Trade center",
      "Extreme sports center", "Recycle center", "Starbase lab",
      "Carbon colonization"};
  /**
   * Planetary Improvement rare tech names for level 4
   */
  public static final String[] IMPROVEMENT_RARE_TECH_LEVEL4_NAMES = {
      "Deadly virus", "Orbital lift"
  };
  /**
   * Planetary Improvement tech names for level 5
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL5_NAMES = {
      "Farming center", "Mining center", "Manufacturing center",
      "Radiation dampener", "Galactic sports center",
      "Collective research center", "Massive blast furnace" };
  /**
   * Planetary Improvement tech names for level 6
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL6_NAMES = {
      "Research center", "Stock market", "United Galaxy Tower",
      "Starbase sports hall"};
  /**
   * Planetary Improvement tech names for level 7
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL7_NAMES = {
      "New technology center", "VR movie center", "Advanced recycle center",
      "Starbase nano lab", "Aquatic colonization"};
  /**
   * Planetary Improvement tech names for level 8
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL8_NAMES = {"Galactic bank",
      "Radiation well", "Starbase bank", "Orbital defense grid" };
  /**
   * Planetary Improvement tech names for level 9
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL9_NAMES = {
      "Hydropodic farming center", "Nanobot mining center",
      "Nanobot manufacturing center", "Research Matrix",
      "Planetary furnace"};
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
      "Hyper drive Mk2", "Tachyon source Mk1", "Combat thrusters" };
  /**
   * Propulsion tech names for level 5
   */
  public static final String[] PROPULSION_TECH_LEVEL5_NAMES = {"Warp drive Mk3",
      "Hyper drive Mk3", "Tachyon source Mk2", "Ice colonization" };
  /**
   * Propulsion rare tech names for level 5.
   */
  public static final String[] PROPULSION_RARE_TECH_LEVEL5_NAMES = {
      "Tachyon source Mk3"};
  /**
   * Propulsion tech names for level 6
   */
  public static final String[] PROPULSION_TECH_LEVEL6_NAMES = {"Warp drive Mk4",
      "Hyper drive Mk4", "Nuclear drive Mk3", "Antimatter source Mk1"};
  /**
   * Propulsion tech names for level 7
   */
  public static final String[] PROPULSION_TECH_LEVEL7_NAMES = {"Warp drive Mk5",
      "Hyper drive Mk5", "Impulse engine Mk1", "Material replicator" };
  /**
   * Propulsion tech names for level 8
   */
  public static final String[] PROPULSION_TECH_LEVEL8_NAMES = {"Warp drive Mk6",
      "Hyper drive Mk6", "Impulse engine Mk2", "Antimatter source Mk2",
      "Nuclear drive Mk4" };
  /**
   * Propulsion rare tech names for level 8.
   */
  public static final String[] PROPULSION_RARE_TECH_LEVEL8_NAMES = {
      "Antimatter source Mk3"};
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
   * Propulsion rare tech names for level 10.
   */
  public static final String[] PROPULSION_RARE_TECH_LEVEL10_NAMES = {
      "Zero-point source Mk3"};

  /**
   * Electronics tech names for level 1
   */
  public static final String[] ELECTRONICS_TECH_LEVEL1_NAMES = {"Scanner Mk1",
      "Planetary scanner Mk1", "Cloaking device Mk1" };
  /**
   * Electronics tech names for level 2
   */
  public static final String[] ELECTRONICS_TECH_LEVEL2_NAMES = {
      "Cloaking device Mk2", "Targeting computer Mk1", "Espionage module Mk1" };
  /**
   * Electronics tech names for level 3
   */
  public static final String[] ELECTRONICS_TECH_LEVEL3_NAMES = {"Scanner Mk2",
      "Planetary scanner Mk2", "Espionage module Mk2" };
  /**
   * Rare electronics tech names for level 3.
   */
  public static final String[] ELECTRONICS_RARE_TECH_LEVEL3_NAMES = {
      "Improved engineer"};

  /**
   * Electronics tech names for level 4
   */
  public static final String[] ELECTRONICS_TECH_LEVEL4_NAMES = {
      "Cloaking device Mk3", "Targeting computer Mk2", "LR scanner Mk1",
      "Broadcasting antenna"};
  /**
   * Electronics tech names for level 5
   */
  public static final String[] ELECTRONICS_TECH_LEVEL5_NAMES = {"Scanner Mk3",
      "Planetary scanner Mk3", "Espionage module Mk3" };
  /**
   * Electronics tech names for level 6
   */
  public static final String[] ELECTRONICS_TECH_LEVEL6_NAMES = {
      "Cloaking device Mk4", "Targeting computer Mk3", "LR scanner Mk2" };
  /**
   * Electronics tech names for level 7
   */
  public static final String[] ELECTRONICS_TECH_LEVEL7_NAMES = {"Scanner Mk4",
      "Espionage module Mk4", "Deep space scanner" };
  /**
   * Electronics tech names for level 8
   */
  public static final String[] ELECTRONICS_TECH_LEVEL8_NAMES = {
      "Cloaking device Mk5", "Planetary scanner Mk4", "LR scanner Mk3" };
  /**
   * Electronics tech names for level 9
   */
  public static final String[] ELECTRONICS_TECH_LEVEL9_NAMES = {"Scanner Mk5",
      "Targeting computer Mk4", "Broadcasting network" };
  /**
   * Electronics tech names for level 10
   */
  public static final String[] ELECTRONICS_TECH_LEVEL10_NAMES = {
      "Cloaking device Mk6", "Planetary scanner Mk5", "Espionage module Mk5" };

  /**
   * Find technology with certain name.
   * @param name Technology name look for.
   * @return Tech if found or null.
   */
  public static Tech findTech(final String name) {
    Tech tech = null;
    for (int i = 1; i < 11; i++) {
      tech = createCombatTech(name, i);
      if (tech != null) {
        return tech;
      }
      tech = createDefenseTech(name, i);
      if (tech != null) {
        return tech;
      }
      tech = createHullTech(name, i);
      if (tech != null) {
        return tech;
      }
      tech = createPropulsionTech(name, i);
      if (tech != null) {
        return tech;
      }
      tech = createImprovementTech(name, i);
      if (tech != null) {
        return tech;
      }
      tech = createElectronicsTech(name, i);
      if (tech != null) {
        return tech;
      }
    }
    return null;
  }
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
      list = TextUtilities.concanateStringArrays(COMBAT_TECH_LEVEL2_NAMES,
          COMBAT_RARE_TECH_LEVEL2_NAMES);
      break;
    case 3:
      list = TextUtilities.concanateStringArrays(COMBAT_TECH_LEVEL3_NAMES,
          COMBAT_RARE_TECH_LEVEL3_NAMES);
      break;
    case 4:
      list = COMBAT_TECH_LEVEL4_NAMES;
      break;
    case 5:
      list = TextUtilities.concanateStringArrays(COMBAT_TECH_LEVEL5_NAMES,
          COMBAT_RARE_TECH_LEVEL5_NAMES);
      break;
    case 6:
      list = TextUtilities.concanateStringArrays(COMBAT_TECH_LEVEL6_NAMES,
          COMBAT_RARE_TECH_LEVEL6_NAMES);
      break;
    case 7:
      list = TextUtilities.concanateStringArrays(COMBAT_TECH_LEVEL7_NAMES,
          COMBAT_RARE_TECH_LEVEL7_NAMES);
      break;
    case 8:
      list = TextUtilities.concanateStringArrays(COMBAT_TECH_LEVEL8_NAMES,
          COMBAT_RARE_TECH_LEVEL8_NAMES);
      break;
    case 9:
      list = TextUtilities.concanateStringArrays(COMBAT_TECH_LEVEL9_NAMES,
          COMBAT_RARE_TECH_LEVEL9_NAMES);
      break;
    case 10:
      list = TextUtilities.concanateStringArrays(COMBAT_TECH_LEVEL10_NAMES,
          COMBAT_RARE_TECH_LEVEL10_NAMES);
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
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRace.HUMAN, SpaceRace.SPORKS,
              SpaceRace.SCAURIANS, SpaceRace.MOTHOIDS,
              SpaceRace.SMAUGIRIANS, SpaceRace.ALONIANS);
          tech.setIcon(Icons.getIconByName(Icons.ICON_COMBAT_TECH));
        } else if (techName.startsWith("ECM torpedo")
            || techName.startsWith("HE missile")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_MISSILE));
        } else if (techName.startsWith("Photon torpedo")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PHOTON_TORPEDO));
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
        } else if (techName.startsWith("Orbital bomb")
            || techName.startsWith("Orbital smart bomb")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_BOMB));
        } else if (techName.startsWith("Orbital nuke")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_NUKE));
        } else if (techName.startsWith("Orbital defense grid")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANETARY_TURRET));
        } else if (techName.startsWith("Orbital fusion")
            || techName.startsWith("Orbital antimatter")
            || techName.startsWith("Orbital neutron")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_NUKE));
        } else if (techName.startsWith("Planetary invasion module")
            || techName.startsWith("Shock trooper module")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_TROOPS));
        } else if (techName.startsWith("Tractor beam")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_TRACTOR_BEAM));
          tech.setRareTech(true);
        } else if (techName.startsWith("Callisto multicannon")
            || techName.startsWith("Autocannon")
            || techName.startsWith("Chaingun")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_MULTI_CANNON));
          tech.setExcludeList(false);
          tech.setSpaceRaces(SpaceRace.HUMAN, SpaceRace.SPORKS,
              SpaceRace.SPACE_PIRATE, SpaceRace.SCAURIANS, SpaceRace.ALONIANS,
              SpaceRace.MOTHOIDS, SpaceRace.SMAUGIRIANS, SpaceRace.ALTEIRIANS);
        } else if (techName.startsWith("Ion cannon")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_ION_CANNON));
          tech.setRareTech(true);
          if (techName.equals("Ion cannon Mk1")) {
            tech.setNextTechOnTree("Ion cannon Mk2");
            tech.setNextTechLevel(5);
          }
          if (techName.equals("Ion cannon Mk2")) {
            tech.setNextTechOnTree("Ion cannon Mk3");
            tech.setNextTechLevel(7);
          }
          if (techName.equals("Ion cannon Mk3")) {
            tech.setNextTechOnTree("Ion cannon Mk4");
            tech.setNextTechLevel(9);
          }
        } else if (techName.startsWith("Plasma cannon")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLASMA_CANNON));
          tech.setRareTech(true);
          if (techName.equals("Plasma cannon Mk1")) {
            tech.setNextTechOnTree("Plasma cannon Mk2");
            tech.setNextTechLevel(3);
          }
          if (techName.equals("Plasma cannon Mk2")) {
            tech.setNextTechOnTree("Plasma cannon Mk3");
            tech.setNextTechLevel(5);
          }
          if (techName.equals("Plasma cannon Mk3")) {
            tech.setNextTechOnTree("Plasma cannon Mk4");
            tech.setNextTechLevel(7);
          }
          if (techName.equals("Plasma cannon Mk4")) {
            tech.setNextTechOnTree("Plasma cannon Mk5");
            tech.setNextTechLevel(9);
          }
        } else if (techName.startsWith("Gravity ripper")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLASMA_CANNON));
          tech.setRareTech(true);
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
      list = TextUtilities.concanateStringArrays(DEFENSE_TECH_LEVEL3_NAMES,
          DEFENSE_RARE_TECH_LEVEL3_NAMES);
      break;
    case 4:
      list = TextUtilities.concanateStringArrays(DEFENSE_TECH_LEVEL4_NAMES,
          DEFENSE_RARE_TECH_LEVEL4_NAMES);
      break;
    case 5:
      list = DEFENSE_TECH_LEVEL5_NAMES;
      break;
    case 6:
      list = TextUtilities.concanateStringArrays(DEFENSE_TECH_LEVEL6_NAMES,
          DEFENSE_RARE_TECH_LEVEL6_NAMES);
      break;
    case 7:
      list = TextUtilities.concanateStringArrays(DEFENSE_TECH_LEVEL7_NAMES,
          DEFENSE_RARE_TECH_LEVEL7_NAMES);
      break;
    case 8:
      list = TextUtilities.concanateStringArrays(DEFENSE_TECH_LEVEL8_NAMES,
          DEFENSE_RARE_TECH_LEVEL8_NAMES);
      break;
    case 9:
      list = TextUtilities.concanateStringArrays(DEFENSE_TECH_LEVEL9_NAMES,
          DEFENSE_RARE_TECH_LEVEL9_NAMES);
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
        if (techName.equals("Iron colonization")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANET));
          tech.setTradeable(false);
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
        } else if (techName.equals("Desert colonization")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANET));
          tech.setTradeable(false);
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
        } else if (techName.startsWith("Planetary defense turret Mk")
            || techName.startsWith("Orbital shield")
            || techName.equals("Space academy")
            || techName.startsWith("Orbital elevator Mk")) {
          tech.setImprovement(techName);
          if (techName.startsWith("Planetary defense turret Mk")) {
            tech.setExcludeList(true);
            tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
          }
          if (techName.startsWith("Orbital elevator Mk")) {
            tech.setExcludeList(false);
            tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
          }
        } else {
          tech.setComponent(techName);
        }
        if (techName.startsWith("Shield")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_SHIELD));
        } else if (techName.startsWith("Armor plating")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_ARMOR));
        } else if (techName.startsWith("Planetary defense turret Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANETARY_TURRET));
        } else if (techName.startsWith("Orbital elevator Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_ORBITAL_ELEVATOR));
        } else if (techName.startsWith("Jammer Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_CIRCUIT_BOARD));
        } else if (techName.startsWith("Space academy")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_TROOPS));
        } else if (techName.startsWith("Command outpost")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_STARBASE));
        } else if (techName.startsWith("Command center")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_STARBASE));
        } else if (techName.startsWith("Solar armor")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_SOLAR_ARMOR));
            tech.setRareTech(true);
            if (techName.equals("Solar armor Mk1")) {
              tech.setNextTechOnTree("Solar armor Mk2");
              tech.setNextTechLevel(6);
            }
            if (techName.equals("Solar armor Mk2")) {
              tech.setNextTechOnTree("Solar armor Mk3");
              tech.setNextTechLevel(9);
            }
        } else if (techName.startsWith("Organic armor")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_ORGANIC_ARMOR));
          tech.setRareTech(true);
          if (techName.equals("Organic armor Mk1")) {
            tech.setNextTechOnTree("Organic armor Mk2");
            tech.setNextTechLevel(6);
          }
          if (techName.equals("Organic armor Mk2")) {
            tech.setNextTechOnTree("Organic armor Mk3");
            tech.setNextTechLevel(8);
          }
      } else if (techName.startsWith("Distortion shield")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_DISTORTION_SHIELD));
          tech.setRareTech(true);
          if (techName.equals("Distortion shield Mk1")) {
            tech.setNextTechOnTree("Distortion shield Mk2");
            tech.setNextTechLevel(6);
          }
          if (techName.equals("Distortion shield Mk2")) {
            tech.setNextTechOnTree("Distortion shield Mk3");
            tech.setNextTechLevel(8);
          }
        } else if (techName.equals("Multi-dimension shield")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_DISTORTION_SHIELD));
          tech.setRareTech(true);
        } else if (tech.getIcon() == Icons.getIconByName(Icons.ICON_RESEARCH)) {
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
      list = TextUtilities.concanateStringArrays(HULL_TECH_LEVEL3_NAMES,
          HULL_RARE_TECH_LEVEL3_NAMES);
      break;
    case 4:
      list = HULL_TECH_LEVEL4_NAMES;
      break;
    case 5:
      list = TextUtilities.concanateStringArrays(HULL_TECH_LEVEL5_NAMES,
          HULL_RARE_TECH_LEVEL5_NAMES);
      break;
    case 6:
      list = HULL_TECH_LEVEL6_NAMES;
      break;
    case 7:
      list = TextUtilities.concanateStringArrays(HULL_TECH_LEVEL7_NAMES,
          HULL_RARE_TECH_LEVEL7_NAMES);
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
        if (techName.equals("Advanced colonization")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANET));
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
          tech.setTradeable(false);
          return tech;
        } else if (techName.equals("Minor orbital")
            || techName.equals("Large orbital")
            || techName.equals("Massive orbital")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_STARBASE));
          tech.setTradeable(false);
          tech.setExcludeList(false);
          tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
          tech.setHull(techName);
          return tech;
        } else if (techName.contains("starbase")
            || techName.contains("Artificial planet")
            || techName.contains("orbital")) {
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
        } else if (techName.startsWith("Fighter bay Mk")) {
          tech.setComponent(techName);
        } else if (techName.startsWith("Repair module Mk")) {
          tech.setComponent(techName);
          tech.setRareTech(true);
          //TODO: Change icon
          tech.setIcon(Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
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
      list = TextUtilities.concanateStringArrays(IMPROVEMENT_TECH_LEVEL3_NAMES,
          IMPROVEMENT_RARE_TECH_LEVEL3_NAMES);
      break;
    case 4:
      list = TextUtilities.concanateStringArrays(IMPROVEMENT_TECH_LEVEL4_NAMES,
          IMPROVEMENT_RARE_TECH_LEVEL4_NAMES);
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
        if (techName.equals("Aquatic colonization")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANET));
          tech.setTradeable(false);
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
        } else if (techName.equals("Carbon colonization")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANET));
          tech.setTradeable(false);
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
        } else if (techName.startsWith("Starbase")) {
          tech.setComponent(techName);
          tech.setIcon(Icons.getIconByName(Icons.ICON_STARBASE));
        } else if (techName.startsWith("Deadly virus")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_DEATH));
          tech.setRareTech(true);
        } else {
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
          } else if (techName.startsWith("Orbital lift")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_ORBITAL_ELEVATOR));
            tech.setRareTech(true);
          } else if (techName.startsWith("College of history")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_RESEARCH));
            tech.setRareTech(true);
          } else if (techName.startsWith("Tax center")
              || techName.startsWith("Market center")
              || techName.startsWith("Trade center")
              || techName.startsWith("Stock market")
              || techName.startsWith("Galactic bank")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_CREDIT));
          } else if (techName.startsWith("Advanced farm")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_FARM));
            tech.setExcludeList(true);
            tech.setSpaceRaces(SpaceRace.LITHORIANS, SpaceRace.MECHIONS);
          } else if (techName.startsWith("Farming center")
              || techName.startsWith("Hydropodic farming center")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_FARM));
            tech.setExcludeList(true);
            tech.setSpaceRaces(SpaceRace.LITHORIANS, SpaceRace.MECHIONS,
                SpaceRace.SYNTHDROIDS);
          } else if (techName.startsWith("Cyber lab")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_RESEARCH));
            tech.setExcludeList(false);
            tech.setSpaceRaces(SpaceRace.MECHIONS, SpaceRace.SYNTHDROIDS);
            tech.setTradeable(false);
          } else if (techName.startsWith("Collective research center")
              || techName.startsWith("Research Matrix")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_RESEARCH));
            tech.setExcludeList(false);
            tech.setSpaceRaces(SpaceRace.REBORGIANS, SpaceRace.MECHIONS,
                SpaceRace.SYNTHDROIDS);
            tech.setTradeable(false);
          } else if (techName.startsWith("Advanced furnace")
              || techName.startsWith("Massive blast furnace")
              || techName.startsWith("Planetary furnace")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_METAL));
            tech.setExcludeList(false);
            tech.setSpaceRaces(SpaceRace.LITHORIANS);
            // This tech is rare tech that only Lithorians will learn it
            // but they can trade it to others if they wish.
            tech.setRareTech(true);
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
          } else if (techName.equals("Orbital defense grid")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_COMBAT_TECH));
          } else {
            tech.setIcon(Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
          }
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
      list = TextUtilities.concanateStringArrays(PROPULSION_TECH_LEVEL5_NAMES,
          PROPULSION_RARE_TECH_LEVEL5_NAMES);
      break;
    case 6:
      list = PROPULSION_TECH_LEVEL6_NAMES;
      break;
    case 7:
      list = PROPULSION_TECH_LEVEL7_NAMES;
      break;
    case 8:
      list = TextUtilities.concanateStringArrays(PROPULSION_TECH_LEVEL8_NAMES,
          PROPULSION_RARE_TECH_LEVEL8_NAMES);
      break;
    case 9:
      list = PROPULSION_TECH_LEVEL9_NAMES;
      break;
    case 10:
      list = TextUtilities.concanateStringArrays(PROPULSION_TECH_LEVEL10_NAMES,
          PROPULSION_RARE_TECH_LEVEL10_NAMES);
      break;
    default:
      return null;
    }
    for (int i = 0; i < list.length; i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Propulsion, level);
        if (techName.equals("Ice colonization")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANET));
          tech.setTradeable(false);
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRace.ALTEIRIANS);
        } else if (tech.getName().equals("Material replicator")) {
          tech.setImprovement(techName);
          tech.setIcon(Icons.getIconByName(Icons.ICON_METAL_ORE));
        } else {
          tech.setComponent(techName);
          if (tech.getName().contains(" source Mk")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_POWERSOURCE));
          } else {
            tech.setIcon(Icons.getIconByName(Icons.ICON_PROPULSION_TECH));
          }
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
      list = TextUtilities.concanateStringArrays(ELECTRONICS_TECH_LEVEL3_NAMES,
          ELECTRONICS_RARE_TECH_LEVEL3_NAMES);
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
        if (techName.startsWith("Improved engineer")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_ELECTRONICS_TECH));
        } else if (techName.startsWith("Planetary scanner Mk")
            || techName.startsWith("Deep space scanner")
            || techName.startsWith("Broadcasting")) {
          tech.setImprovement(techName);
        } else {
          tech.setComponent(techName);
        }
        if (techName.startsWith("Planetary scanner Mk")
            || techName.startsWith("LR scanner Mk")
            || techName.startsWith("Deep space scanner")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_LR_SCANNER));
        } else if (techName.startsWith("Scanner Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_SCANNER));
        } else if (techName.startsWith("Broadcasting ")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_ANTENNA));
        } else if (techName.startsWith("Cloaking device")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_CLOACKING_DEVICE));
        } else if (techName.startsWith("Espionage module Mk")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
        } else {
          tech.setIcon(Icons.getIconByName(Icons.ICON_ELECTRONICS_TECH));
        }
        return tech;
      }
    }
    return null;
  }

  /**
   * Create Tech for certain techtype, level and name
   * @param type TechType to create
   * @param level Level 1 - 10
   * @param techName Tech name to create
   * @return Tech or null
   */
  public static Tech createTech(final TechType type, final int level,
      final String techName) {
    switch (type) {
    case Combat:
      return createCombatTech(techName, level);
    case Defense:
      return createDefenseTech(techName, level);
    case Hulls:
      return createHullTech(techName, level);
    case Improvements:
      return createImprovementTech(techName, level);
    case Propulsion:
      return createPropulsionTech(techName, level);
    case Electrics:
      return createElectronicsTech(techName, level);
    default:
      return null;
    }
  }

  /**
   * Create space monster tech. This method can create pretty much any tech.
   * There couple of fixed names which set correct icon for tech.
   * @param type TechType
   * @param level Tech Level
   * @param techName Tech Name
   * @return Tech
   */
  public static Tech createSpaceMonsterTech(final TechType type,
      final int level, final String techName) {
    Tech tech = new Tech(techName, type, level);
    if (techName.startsWith("Organic armor")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_ORGANIC_ARMOR));
      tech.setTradeable(false);
      tech.setComponent(techName);
    }
    if (techName.startsWith("Heart")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_ORGANIC_ARMOR));
      tech.setTradeable(false);
      tech.setComponent(techName);
      //TODO: Change icon
    }
    if (techName.startsWith("Large heart")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_ORGANIC_ARMOR));
      tech.setTradeable(false);
      tech.setComponent(techName);
      //TODO: Change icon
    }
    if (techName.startsWith("Space fin")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_ORGANIC_ARMOR));
      tech.setTradeable(false);
      tech.setComponent(techName);
      //TODO: Change icon
    }
    if (techName.startsWith("Massive mouth with teeth")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_ORGANIC_ARMOR));
      tech.setTradeable(false);
      tech.setComponent(techName);
      //TODO: Change icon
    }
    if (techName.startsWith("Tentacle")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_ORGANIC_ARMOR));
      tech.setTradeable(false);
      tech.setComponent(techName);
      //TODO: Change icon
    }
    if (techName.startsWith("Space worm")
        || techName.startsWith("Kraken")
        || techName.startsWith("Large kraken")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_ORGANIC_ARMOR));
      tech.setTradeable(false);
      tech.setHull(techName);
      //TODO: Change icon
    }
    return tech;
  }
  /**
   * Create random tech by tech type and level, but not choose those tech
   * player already has
   * @param type Tech Type: Combat, Defense, Hulls, Improvements, Propulsion
   *             and Electrics
   * @param level Tech Level 1-10
   * @param alreadyHas List of tech player has
   * @param race SpaceRace for correct tech tree
   * @return Tech or null if cannot find new
   */
  public static Tech createRandomTech(final TechType type, final int level,
      final Tech[] alreadyHas, final SpaceRace race) {
    return createRandomTech(type, level, alreadyHas, null, race);
  }
  /**
   * Create random tech by tech type and level, but not choose those tech
   * player already has
   * @param type Tech Type: Combat, Defense, Hulls, Improvements, Propulsion
   *             and Electrics
   * @param level Tech Level 1-10
   * @param alreadyHas List of tech player has
   * @param rareTech list of rare tech available for realm.
   * @param race SpaceRace for correct tech tree
   * @return Tech or null if cannot find new
   */
  public static Tech createRandomTech(final TechType type, final int level,
      final Tech[] alreadyHas, final Tech[] rareTech, final SpaceRace race) {
    String[] alreadyHasList = new String[alreadyHas.length];
    for (int i = 0; i < alreadyHasList.length; i++) {
      alreadyHasList[i] = alreadyHas[i].getName();
    }
    String[] techOptions = getListByTechLevel(type, level, race);
    int rareTechs = 0;
    if (rareTech != null && rareTech.length > 0) {
      rareTechs = rareTech.length;
    }
    String[] options = new String[techOptions.length + rareTechs];
    System.arraycopy(techOptions, 0, options, 0, techOptions.length);
    if (rareTech != null) {
      for (int i = 0; i < rareTechs; i++) {
        options[techOptions.length + i] = rareTech[i].getName();
      }
    }
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
  private static final int TECH_4_LEVEL_RP_COST = 18;
  /**
   * Tech 5 Level cost
   */
  private static final int TECH_5_LEVEL_RP_COST = 25;
  /**
   * Tech 6 Level cost
   */
  private static final int TECH_6_LEVEL_RP_COST = 40;
  /**
   * Tech 7 Level cost
   */
  private static final int TECH_7_LEVEL_RP_COST = 60;
  /**
   * Tech 8 Level cost
   */
  private static final int TECH_8_LEVEL_RP_COST = 84;
  /**
   * Tech 9 Level cost
   */
  private static final int TECH_9_LEVEL_RP_COST = 104;
  /**
   * Tech 10 Level cost
   */
  private static final int TECH_10_LEVEL_RP_COST = 130;
  /**
   * Tech FUTURE Level cost
   */
  private static final int TECH_FUTURE_LEVEL_RP_COST = 168;

  /**
   * If list contains tech with certain name.
   * @param techName TechName
   * @param list List of techs
   * @return True if tech with correct name found.
   */
  private static boolean hasTech(final String techName, final Tech[] list) {
    if (list != null) {
      for (int i = 0; i < list.length; i++) {
        if (list[i].getName().equals(techName)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get random rare tech. This handles possible tech trees in
   * rare technology. So first must be get the lowest level of tech.
   * @param techList Rare tech player already has.
   * @return Tech or null if not available.
   */
  public static Tech getRandomRareTech(final Tech[] techList) {
    ArrayList<Tech> choices = new ArrayList<>();
    if (!hasTech("Solar armor Mk1", techList)) {
      choices.add(createDefenseTech("Solar armor Mk1", 3));
    }
    if (hasTech("Solar armor Mk1", techList) && !hasTech("Solar armor Mk2",
        techList)) {
      choices.add(createDefenseTech("Solar armor Mk2", 6));
    }
    if (hasTech("Solar armor Mk2", techList) && !hasTech("Solar armor Mk3",
        techList)) {
      choices.add(createDefenseTech("Solar armor Mk3", 9));
    }
    if (!hasTech("Ion cannon Mk1", techList)) {
      choices.add(createCombatTech("Ion cannon Mk1", 3));
    }
    if (hasTech("Ion cannon Mk1", techList) && !hasTech("Ion cannon Mk2",
        techList)) {
      choices.add(createCombatTech("Ion cannon Mk2", 5));
    }
    if (hasTech("Ion cannon Mk2", techList) && !hasTech("Ion cannon Mk3",
        techList)) {
      choices.add(createCombatTech("Ion cannon Mk3", 7));
    }
    if (hasTech("Ion cannon Mk3", techList) && !hasTech("Ion cannon Mk4",
        techList)) {
      choices.add(createCombatTech("Ion cannon Mk4", 9));
    }
    if (!hasTech("Plasma cannon Mk1", techList)) {
      choices.add(createCombatTech("Plasma cannon Mk1", 2));
    }
    if (hasTech("Plasma cannon Mk1", techList) && !hasTech("Plasma cannon Mk2",
        techList)) {
      choices.add(createCombatTech("Plasma cannon Mk2", 3));
    }
    if (hasTech("Plasma cannon Mk2", techList) && !hasTech("Plasma cannon Mk3",
        techList)) {
      choices.add(createCombatTech("Plasma cannon Mk3", 5));
    }
    if (hasTech("Plasma cannon Mk3", techList) && !hasTech("Plasma cannon Mk4",
        techList)) {
      choices.add(createCombatTech("Plasma cannon Mk4", 7));
    }
    if (hasTech("Plasma cannon Mk5", techList) && !hasTech("Plasma cannon Mk5",
        techList)) {
      choices.add(createCombatTech("Plasma cannon Mk5", 9));
    }
    if (!hasTech("Distortion shield Mk1", techList)) {
      choices.add(createDefenseTech("Distortion shield Mk1", 4));
    }
    if (hasTech("Distortion shield Mk1", techList) && !hasTech(
        "Distortion shield Mk2", techList)) {
      choices.add(createDefenseTech("Distortion shield Mk2", 6));
    }
    if (hasTech("Distortion shield Mk2", techList) && !hasTech(
        "Distortion shield Mk3", techList)) {
      choices.add(createDefenseTech("Distortion shield Mk3", 8));
    }
    if (!hasTech("Organic armor Mk1", techList)) {
      choices.add(createDefenseTech("Organic armor Mk1", 3));
    }
    if (hasTech("Organic armor Mk1", techList) && !hasTech("Organic armor Mk2",
        techList)) {
      choices.add(createDefenseTech("Organic armor Mk2", 6));
    }
    if (hasTech("Organic armor Mk2", techList) && !hasTech("Organic armor Mk3",
        techList)) {
      choices.add(createDefenseTech("Organic armor Mk3", 8));
    }
    if (!hasTech("Orbital lift", techList)) {
      choices.add(createImprovementTech("Orbital lift", 4));
    }
    if (!hasTech("Improved engineer", techList)) {
      choices.add(createElectronicsTech("Improved engineer", 3));
    }
    if (choices.size() > 1) {
      int index = DiceGenerator.getRandom(0, choices.size() - 1);
      return choices.get(index);
    }
    return null;
  }
  /**
   * Get tech level cost as research points
   * @param level Level to research
   * @param gameLength Maximum game length in turns
   * @return Amount of research points required
   */
  public static int getTechCost(final int level, final int gameLength) {
    int veryLowBonus = 0;
    int lowBonus = 0;
    int mediumBonus = 0;
    int highBonus = 0;
    if (gameLength <= 200) {
      veryLowBonus = -2;
      lowBonus = -5;
      mediumBonus = -10;
      highBonus = -15;
    } else if (gameLength <= 300) {
      veryLowBonus = 0;
      lowBonus = 0;
      mediumBonus = 0;
      highBonus = 0;
    } else if (gameLength <= 400) {
      veryLowBonus = 0;
      lowBonus = 2;
      mediumBonus = 5;
      highBonus = 8;
    } else if (gameLength <= 600) {
      veryLowBonus = 2;
      lowBonus = 5;
      mediumBonus = 10;
      highBonus = 15;
    } else if (gameLength <= 800) {
      veryLowBonus = 4;
      lowBonus = 8;
      mediumBonus = 15;
      highBonus = 20;
    } else if (gameLength <= 1000) {
      veryLowBonus = 6;
      lowBonus = 15;
      mediumBonus = 20;
      highBonus = 30;
    }
    switch (level) {
    case 1:
      return TECH_1_LEVEL_RP_COST;
    case 2:
      return TECH_2_LEVEL_RP_COST;
    case 3:
      return TECH_3_LEVEL_RP_COST + veryLowBonus;
    case 4:
      return TECH_4_LEVEL_RP_COST + veryLowBonus;
    case 5:
      return TECH_5_LEVEL_RP_COST + lowBonus;
    case 6:
      return TECH_6_LEVEL_RP_COST + lowBonus;
    case 7:
      return TECH_7_LEVEL_RP_COST + mediumBonus;
    case 8:
      return TECH_8_LEVEL_RP_COST + mediumBonus;
    case 9:
      return TECH_9_LEVEL_RP_COST + highBonus;
    case 10:
      return TECH_10_LEVEL_RP_COST + highBonus;
    default:
      return TECH_FUTURE_LEVEL_RP_COST + highBonus;
    }
  }

  /**
   * Get String list of tech by type and level.
   * This will now depend on space race.
   * @param type The tech type
   * @param level The tech level
   * @param race SpaceRace Unique tech tree based on race
   * @return String array of tech
   */
  public static String[] getListByTechLevel(final TechType type,
      final int level, final SpaceRace race) {
    String[] possibleTechs = new String[0];
    switch (type) {
    case Combat:
      switch (level) {
      case 1:
        possibleTechs = COMBAT_TECH_LEVEL1_NAMES;
        break;
      case 2:
        possibleTechs = COMBAT_TECH_LEVEL2_NAMES;
        break;
      case 3:
        possibleTechs = COMBAT_TECH_LEVEL3_NAMES;
        break;
      case 4:
        possibleTechs = COMBAT_TECH_LEVEL4_NAMES;
        break;
      case 5:
        possibleTechs = COMBAT_TECH_LEVEL5_NAMES;
        break;
      case 6:
        possibleTechs = COMBAT_TECH_LEVEL6_NAMES;
        break;
      case 7:
        possibleTechs = COMBAT_TECH_LEVEL7_NAMES;
        break;
      case 8:
        possibleTechs = COMBAT_TECH_LEVEL8_NAMES;
        break;
      case 9:
        possibleTechs = COMBAT_TECH_LEVEL9_NAMES;
        break;
      case 10:
        possibleTechs = COMBAT_TECH_LEVEL10_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
      break;
    case Defense:
      switch (level) {
      case 1:
        possibleTechs = DEFENSE_TECH_LEVEL1_NAMES;
        break;
      case 2:
        possibleTechs = DEFENSE_TECH_LEVEL2_NAMES;
        break;
      case 3:
        possibleTechs = DEFENSE_TECH_LEVEL3_NAMES;
        break;
      case 4:
        possibleTechs = DEFENSE_TECH_LEVEL4_NAMES;
        break;
      case 5:
        possibleTechs = DEFENSE_TECH_LEVEL5_NAMES;
        break;
      case 6:
        possibleTechs = DEFENSE_TECH_LEVEL6_NAMES;
        break;
      case 7:
        possibleTechs = DEFENSE_TECH_LEVEL7_NAMES;
        break;
      case 8:
        possibleTechs = DEFENSE_TECH_LEVEL8_NAMES;
        break;
      case 9:
        possibleTechs = DEFENSE_TECH_LEVEL9_NAMES;
        break;
      case 10:
        possibleTechs = DEFENSE_TECH_LEVEL10_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
      break;
    case Hulls:
      switch (level) {
      case 1:
        possibleTechs = HULL_TECH_LEVEL1_NAMES;
        break;
      case 2:
        possibleTechs = HULL_TECH_LEVEL2_NAMES;
        break;
      case 3:
        possibleTechs = HULL_TECH_LEVEL3_NAMES;
        break;
      case 4:
        possibleTechs = HULL_TECH_LEVEL4_NAMES;
        break;
      case 5:
        possibleTechs = HULL_TECH_LEVEL5_NAMES;
        break;
      case 6:
        possibleTechs = HULL_TECH_LEVEL6_NAMES;
        break;
      case 7:
        possibleTechs = HULL_TECH_LEVEL7_NAMES;
        break;
      case 8:
        possibleTechs = HULL_TECH_LEVEL8_NAMES;
        break;
      case 9:
        possibleTechs = HULL_TECH_LEVEL9_NAMES;
        break;
      case 10:
        possibleTechs = HULL_TECH_LEVEL10_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
      break;
    case Improvements:
      switch (level) {
      case 1:
        possibleTechs = IMPROVEMENT_TECH_LEVEL1_NAMES;
        break;
      case 2:
        possibleTechs = IMPROVEMENT_TECH_LEVEL2_NAMES;
        break;
      case 3:
        possibleTechs = IMPROVEMENT_TECH_LEVEL3_NAMES;
        break;
      case 4:
        possibleTechs = IMPROVEMENT_TECH_LEVEL4_NAMES;
        break;
      case 5:
        possibleTechs = IMPROVEMENT_TECH_LEVEL5_NAMES;
        break;
      case 6:
        possibleTechs = IMPROVEMENT_TECH_LEVEL6_NAMES;
        break;
      case 7:
        possibleTechs = IMPROVEMENT_TECH_LEVEL7_NAMES;
        break;
      case 8:
        possibleTechs = IMPROVEMENT_TECH_LEVEL8_NAMES;
        break;
      case 9:
        possibleTechs = IMPROVEMENT_TECH_LEVEL9_NAMES;
        break;
      case 10:
        possibleTechs = IMPROVEMENT_TECH_LEVEL10_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
      break;
    case Propulsion:
      switch (level) {
      case 1:
        possibleTechs = PROPULSION_TECH_LEVEL1_NAMES;
        break;
      case 2:
        possibleTechs = PROPULSION_TECH_LEVEL2_NAMES;
        break;
      case 3:
        possibleTechs = PROPULSION_TECH_LEVEL3_NAMES;
        break;
      case 4:
        possibleTechs = PROPULSION_TECH_LEVEL4_NAMES;
        break;
      case 5:
        possibleTechs = PROPULSION_TECH_LEVEL5_NAMES;
        break;
      case 6:
        possibleTechs = PROPULSION_TECH_LEVEL6_NAMES;
        break;
      case 7:
        possibleTechs = PROPULSION_TECH_LEVEL7_NAMES;
        break;
      case 8:
        possibleTechs = PROPULSION_TECH_LEVEL8_NAMES;
        break;
      case 9:
        possibleTechs = PROPULSION_TECH_LEVEL9_NAMES;
        break;
      case 10:
        possibleTechs = PROPULSION_TECH_LEVEL10_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
      break;
    case Electrics:
      switch (level) {
      case 1:
        possibleTechs = ELECTRONICS_TECH_LEVEL1_NAMES;
        break;
      case 2:
        possibleTechs = ELECTRONICS_TECH_LEVEL2_NAMES;
        break;
      case 3:
        possibleTechs = ELECTRONICS_TECH_LEVEL3_NAMES;
        break;
      case 4:
        possibleTechs = ELECTRONICS_TECH_LEVEL4_NAMES;
        break;
      case 5:
        possibleTechs = ELECTRONICS_TECH_LEVEL5_NAMES;
        break;
      case 6:
        possibleTechs = ELECTRONICS_TECH_LEVEL6_NAMES;
        break;
      case 7:
        possibleTechs = ELECTRONICS_TECH_LEVEL7_NAMES;
        break;
      case 8:
        possibleTechs = ELECTRONICS_TECH_LEVEL8_NAMES;
        break;
      case 9:
        possibleTechs = ELECTRONICS_TECH_LEVEL9_NAMES;
        break;
      case 10:
        possibleTechs = ELECTRONICS_TECH_LEVEL10_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 10!");
      }
      break;
    default:
      throw new IllegalArgumentException("Illegal tech type!");
    }
    if (possibleTechs.length > 0) {
      ArrayList<String> techList = new ArrayList<>();
      for (String techName : possibleTechs) {
        Tech tech = createTech(type, level, techName);
        if (tech.isExcludeList()) {
          boolean donotAdd = false;
          for (SpaceRace tmpRace : tech.getSpaceRaces()) {
            if (tmpRace == race) {
              donotAdd = true;
            }
          }
          if (!donotAdd) {
            techList.add(techName);
          }
        } else {
          for (SpaceRace tmpRace : tech.getSpaceRaces()) {
            if (tmpRace == race) {
              techList.add(techName);
              break;
            }
          }
          if (tech.getSpaceRaces().length == 0) {
            techList.add(techName);
          }
        }
      }
      return techList.toArray(new String[techList.size()]);
    }
    return possibleTechs;
  }

}
