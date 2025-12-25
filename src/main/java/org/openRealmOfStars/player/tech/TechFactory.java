package org.openRealmOfStars.player.tech;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2025 Tuomo Untinen
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
 */

import java.util.ArrayList;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceUtility;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.TextUtilities;

/**
 * Tech factory for generating new techs for players
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
      "Chaingun Mk5" };
  /**
   * Rare combat tech names for level 5.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL5_NAMES = {
      "Ion cannon Mk2", "Plasma cannon Mk3"};
  /**
   * Combat tech names for level 6
   */
  public static final String[] COMBAT_TECH_LEVEL6_NAMES = {"Blue laser Mk1",
      "Railgun Mk6", "Photon torpedo Mk6", "ECM torpedo Mk4",
      "HE missile Mk4", "Autocannon Mk1", "Orbital bombs Mk2", };
  /**
   * Rare combat tech names for level 6.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL6_NAMES = {
      "Gravity ripper Mk1" };
  /**
   * Combat tech names for level 7
   */
  public static final String[] COMBAT_TECH_LEVEL7_NAMES = {"Blue laser Mk2",
      "Railgun Mk7", "Photon torpedo Mk7", "ECM torpedo Mk5",
      "HE missile Mk5", "Shock trooper module", "Autocannon Mk2"};
  /**
   * Rare combat tech names for level 7.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL7_NAMES = {
      "Ion cannon Mk3", "Plasma cannon Mk4" };
  /**
   * Combat tech names for level 8
   */
  public static final String[] COMBAT_TECH_LEVEL8_NAMES = {"Blue laser Mk3",
      "Railgun Mk8", "Photon torpedo Mk8", "ECM torpedo Mk6",
      "HE missile Mk6", "Autocannon Mk3", "Orbital fusion bomb"};
  /**
   * Rare combat tech names for level 8.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL8_NAMES = {
      "Gravity ripper Mk2" };
  /**
   * Combat tech names for level 9
   */
  public static final String[] COMBAT_TECH_LEVEL9_NAMES = {
      "Blue laser Mk4", "Railgun Mk8", "Photon torpedo Mk9",
      "ECM torpedo Mk7", "HE missile Mk7", "Autocannon Mk4"};
  /**
   * Rare combat tech names for level 7.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL9_NAMES = {
      "Ion cannon Mk4", "Plasma cannon Mk5"};
  /**
   * Combat tech names for level 10
   */
  public static final String[] COMBAT_TECH_LEVEL10_NAMES = {
      "Blue laser Mk5", "Railgun Mk10", "Photon torpedo Mk10",
      "ECM torpedo Mk8", "HE missile Mk8", "Autocannon Mk5",
      "Orbital bombs Mk3"};
  /**
   * Rare combat tech names for level 10.
   */
  public static final String[] COMBAT_RARE_TECH_LEVEL10_NAMES = {
      "Gravity ripper Mk3" };
  /**
   * Combat tech names for level 11
   */
  public static final String[] COMBAT_TECH_LEVEL11_NAMES = {
      "Phasors Mk1", "Massdrive Mk1", "Photon torpedo Mk11",
      "Autocannon Mk6", "ECM torpedo Mk9", "HE missile Mk9"};
  /**
   * Combat tech names for level 12
   */
  public static final String[] COMBAT_TECH_LEVEL12_NAMES = {
      "Phasors Mk2", "Massdrive Mk2", "Photon torpedo Mk12",
      "Callisto multicannon Mk1", "ECM torpedo Mk10", "HE missile Mk10"};
  /**
   * Combat tech names for level 13
   */
  public static final String[] COMBAT_TECH_LEVEL13_NAMES = {
      "Phasors Mk3", "Massdrive Mk3", "Photon torpedo Mk13",
      "Callisto multicannon Mk2", "ECM torpedo Mk11", "HE missile Mk11",
      "Orbital smart bombs"};
  /**
   * Combat tech names for level 14
   */
  public static final String[] COMBAT_TECH_LEVEL14_NAMES = {
      "Phasors Mk4", "Massdrive Mk4", "Photon torpedo Mk14",
      "Callisto multicannon Mk3", "ECM torpedo Mk12", "HE missile Mk12"};
  /**
   * Combat tech names for level 15
   */
  public static final String[] COMBAT_TECH_LEVEL15_NAMES = {
      "Phasors Mk5", "Massdrive Mk5", "Photon torpedo Mk15",
      "Callisto multicannon Mk4", "ECM torpedo Mk13", "HE missile Mk13",
      "Orbital neutron bomb"};
  /**
   * Combat tech names for level 16
   */
  public static final String[] COMBAT_TECH_LEVEL16_NAMES = {
      "Antimatter beam Mk1", "Massdrive Mk6", "Photon torpedo Mk16",
      "Artemis fragcannon Mk1", "ECM torpedo Mk14", "HE missile Mk14"};
  /**
   * Combat tech names for level 17
   */
  public static final String[] COMBAT_TECH_LEVEL17_NAMES = {
      "Antimatter beam Mk2", "Massdrive Mk7", "Photon torpedo Mk17",
      "Artemis fragcannon Mk2", "ECM torpedo Mk15", "HE missile Mk15"};
  /**
   * Combat tech names for level 18
   */
  public static final String[] COMBAT_TECH_LEVEL18_NAMES = {
      "Antimatter beam Mk3", "Massdrive Mk8", "Photon torpedo Mk18",
      "Artemis fragcannon Mk3", "ECM torpedo Mk16", "HE missile Mk16"};
  /**
   * Combat tech names for level 19
   */
  public static final String[] COMBAT_TECH_LEVEL19_NAMES = {
      "Antimatter beam Mk4", "Massdrive Mk9", "Photon torpedo Mk19",
      "Artemis fragcannon Mk4", "ECM torpedo Mk17", "HE missile Mk17"};
  /**
   * Combat tech names for level 20
   */
  public static final String[] COMBAT_TECH_LEVEL20_NAMES = {
      "Antimatter beam Mk5", "Massdrive Mk10", "Photon torpedo Mk20",
      "Artemis fragcannon Mk5", "ECM torpedo Mk18", "HE missile Mk18"};

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
      "Armor plating Mk3", "Jammer Mk1", "Shadow armor Mk1" };
  /**
   * Defense rare tech names for level 3.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL3_NAMES = {
      "Solar armor Mk1", "Organic armor Mk1"};
  /**
   * Defense tech names for level 4
   */
  public static final String[] DEFENSE_TECH_LEVEL4_NAMES = {"Shield Mk4",
      "Armor plating Mk4", "Command outpost", "Shadow shield Mk1" };
  /**
   * Defense rare tech names for level 4.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL4_NAMES = {
      "Distortion shield Mk1"};
  /**
   * Defense tech names for level 5
   */
  public static final String[] DEFENSE_TECH_LEVEL5_NAMES = {"Shield Mk5",
      "Armor plating Mk5", "Shield generator Mk1"};
  /**
   * Defense tech names for level 6
   */
  public static final String[] DEFENSE_TECH_LEVEL6_NAMES = {"Shield Mk6",
      "Armor plating Mk6", "Desert colonization",
      "Planetary defense turret Mk2" };
  /**
   * Defense rare tech names for level 6.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL6_NAMES = {
      "Solar armor Mk2", "Distortion shield Mk2", "Organic armor Mk2"};
  /**
   * Defense tech names for level 7
   */
  public static final String[] DEFENSE_TECH_LEVEL7_NAMES = {"Shield Mk7",
      "Armor plating Mk7", "Jammer Mk2", "Shadow armor Mk2" };
  /**
   * Defense rare tech names for level 7.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL7_NAMES = {
      "Multi-dimension shield"};
  /**
   * Defense tech names for level 8
   */
  public static final String[] DEFENSE_TECH_LEVEL8_NAMES = {"Shield Mk8",
      "Armor plating Mk8", "Shadow shield Mk2",
      "Orbital elevator Mk2"};
  /**
   * Defense rare tech names for level 8.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL8_NAMES = {
      "Distortion shield Mk3", "Organic armor Mk3"};
  /**
   * Defense tech names for level 9
   */
  public static final String[] DEFENSE_TECH_LEVEL9_NAMES = {"Shield Mk9",
      "Armor plating Mk9", "Space academy", "Planetary defense turret Mk3" };
  /**
   * Defense rare tech names for level 9.
   */
  public static final String[] DEFENSE_RARE_TECH_LEVEL9_NAMES = {
      "Solar armor Mk3"};
  /**
   * Defense tech names for level 10
   */
  public static final String[] DEFENSE_TECH_LEVEL10_NAMES = {"Shield Mk10",
      "Armor plating Mk10", "Volcanic colonization", "Command center" };
  /**
   * Defense tech names for level 11
   */
  public static final String[] DEFENSE_TECH_LEVEL11_NAMES = {"Shield Mk11",
      "Armor plating Mk11", "Jammer Mk3", "Shadow armor Mk3" };
  /**
   * Defense tech names for level 12
   */
  public static final String[] DEFENSE_TECH_LEVEL12_NAMES = {"Shield Mk12",
      "Armor plating Mk12", "Orbital elevator Mk3",
      "Planetary defense turret Mk4", "Shadow shield Mk3" };
  /**
   * Defense tech names for level 13
   */
  public static final String[] DEFENSE_TECH_LEVEL13_NAMES = {"Shield Mk13",
      "Armor plating Mk13", "Shield generator Mk2"};
  /**
   * Defense tech names for level 14
   */
  public static final String[] DEFENSE_TECH_LEVEL14_NAMES = {"Shield Mk14",
      "Armor plating Mk14", "Jammer Mk4"};
  /**
   * Defense tech names for level 15
   */
  public static final String[] DEFENSE_TECH_LEVEL15_NAMES = {"Shield Mk15",
      "Armor plating Mk15", "Orbital shield"};
  /**
   * Defense tech names for level 16
   */
  public static final String[] DEFENSE_TECH_LEVEL16_NAMES = {"Shield Mk16",
      "Armor plating Mk16", "Jammer Mk5", "Shadow shield Mk4"};
  /**
   * Defense tech names for level 17
   */
  public static final String[] DEFENSE_TECH_LEVEL17_NAMES = {"Shield Mk17",
      "Armor plating Mk17", "Shadow armor Mk4"};
  /**
   * Defense tech names for level 18
   */
  public static final String[] DEFENSE_TECH_LEVEL18_NAMES = {"Shield Mk18",
      "Armor plating Mk18", "Jammer Mk6"};
  /**
   * Defense tech names for level 19
   */
  public static final String[] DEFENSE_TECH_LEVEL19_NAMES = {"Shield Mk19",
      "Armor plating Mk19", "Shadow shield Mk5"};
  /**
   * Defense tech names for level 20
   */
  public static final String[] DEFENSE_TECH_LEVEL20_NAMES = {"Shield Mk20",
      "Armor plating Mk20", "Shadow armor Mk5"};

  /**
   * Hull tech names for level 1
   */
  public static final String[] HULL_TECH_LEVEL1_NAMES = {"Scout Mk1",
      "Destroyer Mk1", "Colony", "Minor orbital", "Spore" };
  /**
   * Hull tech names for level 2
   */
  public static final String[] HULL_TECH_LEVEL2_NAMES = {"Probe",
      "Small freighter Mk1", "Small starbase Mk1", "Small orbital"};
  /**
   * Hull tech names for level 3
   */
  public static final String[] HULL_TECH_LEVEL3_NAMES = {"Corvette Mk1",
      "Advanced colonization", "Cargo bay"}; //, "Remote miner Mk1"}
  /**
   * Hull rare tech names for level 3.
   */
  public static final String[] HULL_RARE_TECH_LEVEL3_NAMES = {
      "Repair module Mk1"};

  /**
   * Hull tech names for level 4
   */
  public static final String[] HULL_TECH_LEVEL4_NAMES = {"Medium freighter Mk1",
      "Medium starbase Mk1", "Fighter bay Mk1", "Medium orbital",
      "Probe Mk2"};
  /**
   * Hull tech names for level 5
   */
  public static final String[] HULL_TECH_LEVEL5_NAMES = {"Cruiser Mk1",
      "Privateer Mk1", "Scout Mk2", "Small starbase Mk2",
      "Small freighter Mk2"};
  /**
   * Hull rare tech names for level 5.
   */
  public static final String[] HULL_RARE_TECH_LEVEL5_NAMES = {
      "Repair module Mk2"};
  /**
   * Hull tech names for level 6
   */
  public static final String[] HULL_TECH_LEVEL6_NAMES = {"Medium freighter Mk2",
      "Corvette Mk2", "Probe Mk3", "Orbital station"};
  /**
   * Hull tech names for level 7
   */
  public static final String[] HULL_TECH_LEVEL7_NAMES = {"Battle cruiser Mk1",
      "Privateer Mk2", "Destroyer Mk2", "Fighter bay Mk2"};
  //, "Remote miner Mk2" };
  /**
   * Hull rare tech names for level 7.
   */
  public static final String[] HULL_RARE_TECH_LEVEL7_NAMES = {
      "Repair module Mk3"};
  /**
   * Hull tech names for level 8
   */
  public static final String[] HULL_TECH_LEVEL8_NAMES = {"Large freighter Mk1",
      "Battleship Mk1", "Cruiser Mk2", "Medium starbase Mk2" };
  /**
   * Hull tech names for level 9
   */
  public static final String[] HULL_TECH_LEVEL9_NAMES = {"Scout Mk3",
      "Large orbital", "Armored cargo bay"};
  /**
   * Hull tech names for level 10
   */
  public static final String[] HULL_TECH_LEVEL10_NAMES = {"Large freighter Mk2",
      "Corvette Mk3", "Destroyer Mk3", "Fighter bay Mk3"};
  //, "Remote miner Mk3" };
  /**
   * Hull tech names for level 11
   */
  public static final String[] HULL_TECH_LEVEL11_NAMES = {"Galleon Mk1",
      "Cruiser Mk3", "Large starbase Mk1"};
  /**
   * Hull tech names for level 12
   */
  public static final String[] HULL_TECH_LEVEL12_NAMES = {"Battle cruiser Mk2",
      "Large orbital station", "Medium starbase Mk3"};
  /**
   * Hull tech names for level 13
   */
  public static final String[] HULL_TECH_LEVEL13_NAMES = {"Battleship Mk2",
      "Massive freighter Mk1", "Scout Mk4"};
  /**
   * Hull tech names for level 14
   */
  public static final String[] HULL_TECH_LEVEL14_NAMES = {"Corvette Mk3",
      "Destroyer Mk3", "Fighter bay Mk4"};
  //, "Remote miner Mk4" };
  /**
   * Hull tech names for level 15
   */
  public static final String[] HULL_TECH_LEVEL15_NAMES = {"Large starbase Mk2",
      "Privateer Mk3", "Cruiser Mk4", "Artificial planet"};
  /**
   * Hull tech names for level 16
   */
  public static final String[] HULL_TECH_LEVEL16_NAMES = {"Massive orbital Mk1",
      "Massive freigher Mk2", "Battle cruiser Mk3"};
  /**
   * Hull tech names for level 17
   */
  public static final String[] HULL_TECH_LEVEL17_NAMES = {"Galleon Mk2",
      "Massive starbase Mk1", "Battleship Mk3"};
  /**
   * Hull tech names for level 18
   */
  public static final String[] HULL_TECH_LEVEL18_NAMES = {"Capital ship",
      "Massive orbital Mk2"}; //, "Remote miner Mk5"};
  /**
   * Hull tech names for level 19
   */
  public static final String[] HULL_TECH_LEVEL19_NAMES = {"Death Moon",
      "Massive starbase Mk2"};
  /**
   * Hull tech names for level 20
   */
  public static final String[] HULL_TECH_LEVEL20_NAMES = {
      "Massive freighter Mk3", "Galleon Mk3"};

  /**
   * Planetary Improvement tech names for level 1
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL1_NAMES = {"Basic lab",
      "Telescopes", "Tax center" };
  /**
   * Planetary Improvement tech names for level 2
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL2_NAMES = {"Improved farm",
      "Barracks", "Culture center", "Cyber lab",
      "Improved furnace", "Improved reservoir"};
  /**
   * Planetary Improvement tech names for level 3
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL3_NAMES = {
      "Improved mine", "Improved factory", "Market center",
      "Starbase music hall" };
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
      "Extreme sports center", "Improved laboratory", "Starbase market"};
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
      "Farming center", "Starbase lab", "Recycle center",
      "Collective research center", "Massive blast furnace",
      "Underground reservoir" };
  /**
   * Planetary Improvement tech names for level 6
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL6_NAMES = {
      "Mining center", "Manufacturing center", "Radiation dampener"};
  /**
   * Planetary Improvement tech names for level 7
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL7_NAMES = {
      "Galactic sports center", "Research center", "Stock market",
      "Starbase sports hall"};

  /**
   * Planetary Improvement tech names for level 8
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL8_NAMES = {
      "New technology center", "VR movie center", "Advanced recycle center",
      "Starbase nano lab", "United Galaxy Tower" };
  /**
   * Planetary Improvement tech names for level 9
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL9_NAMES = {
      "Starbase bank", "Hydropodic farming center",
      "Research matrix", "Planetary furnace", "Crust reservoir"};
  /**
   * Planetary Improvement rare tech names for level 9
   */
  public static final String[] IMPROVEMENT_RARE_TECH_LEVEL9_NAMES = {
      "Planetary ascension portal"};
  /**
   * Planetary Improvement tech names for level 10
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL10_NAMES = {
      "Galactic bank", "Culture complex", "Radiation well"};
  //, "Mass relay Mk1"};
  /**
   * Planetary Improvement tech names for level 11
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL11_NAMES = {
      "Advanced laboratory", "Nanobot mining center",
      "Nanobot manufacturing center", "Swamp colonization",
      "Galactic academy"};
  /**
   * Planetary Improvement tech names for level 12
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL12_NAMES = {
      "Neural research center", "Super AI center",
      "Advanced farm", "Advanced furnace", "Advanced reservoir"};
  /**
   * Planetary Improvement tech names for level 13
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL13_NAMES = {
      "Advanced mine", "Advanced factory", "Aquatic colonization"};
  //, "Mass relay Mk2"};
  /**
   * Planetary Improvement tech names for level 14
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL14_NAMES = {
      "Radiation shield", "Replicator center", "Zero-G sports stadium"};
  /**
   * Planetary Improvement tech names for level 15
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL15_NAMES = {
      "Zero-G research facility", "Ultimate laboratory"};
  /**
   * Planetary Improvement tech names for level 16
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL16_NAMES = {
      "Zero-G credit facility", "Advanced culture complex"};
  //, "Mass relay Mk3"};
  /**
   * Planetary Improvement tech names for level 17
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL17_NAMES = {
      "Ultimate farm", "Ultimate furnace", "Ultimate reservoir"};
  /**
   * Planetary Improvement tech names for level 18
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL18_NAMES = {
      // FIXME: This empty list could be problem...
      };
  //, "Mass relay Mk4"};
  /**
   * Planetary Improvement tech names for level 19
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL19_NAMES = {
      "Ultimate mine", "Ultimate factory"};
  /**
   * Planetary Improvement tech names for level 20
   */
  public static final String[] IMPROVEMENT_TECH_LEVEL20_NAMES = {
      "Utopic replicator center"
      };
  //, "Mass relay Mk4"};

  /**
   * Propulsion tech names for level 1
   */
  public static final String[] PROPULSION_TECH_LEVEL1_NAMES = {"Ion drive Mk1",
      "Fission source Mk1", "Ion drive Mk2", "Nuclear drive Mk1",
      "Fission source Mk2", "Solar drive" };
  /**
   * Propulsion tech names for level 2
   */
  public static final String[] PROPULSION_TECH_LEVEL2_NAMES = {"Ion drive Mk3",
      "Hyper drive Mk1"};
  /**
   * Propulsion tech names for level 3
   */
  public static final String[] PROPULSION_TECH_LEVEL3_NAMES = {"Warp drive Mk1",
      "Hyper drive Mk2", "Fusion source Mk1" };
  /**
   * Propulsion tech names for level 4
   */
  public static final String[] PROPULSION_TECH_LEVEL4_NAMES = {"Warp drive Mk2",
      "Nuclear drive Mk2", "Fusion source Mk2", "Combat thrusters Mk1" };
  /**
   * Propulsion tech names for level 5
   */
  public static final String[] PROPULSION_TECH_LEVEL5_NAMES = {"Warp drive Mk3",
      "Hyper drive Mk3", "Fusion source Mk3"};
  /**
   * Propulsion rare tech names for level 5.
   */
  public static final String[] PROPULSION_RARE_TECH_LEVEL5_NAMES = {
      "Tachyon source Mk3"};
  /**
   * Propulsion tech names for level 6
   */
  public static final String[] PROPULSION_TECH_LEVEL6_NAMES = {"Warp drive Mk4",
      "Hyper drive Mk4", "Nuclear drive Mk3", "Combat thrusters Mk2"};
  /**
   * Propulsion tech names for level 7
   */
  public static final String[] PROPULSION_TECH_LEVEL7_NAMES = {"Warp drive Mk5",
      "Hyper drive Mk5", "Impulse engine Mk1", "Tachyon source Mk1" };
  /**
   * Propulsion tech names for level 8
   */
  public static final String[] PROPULSION_TECH_LEVEL8_NAMES = {"Warp drive Mk6",
      "Hyper drive Mk6", "Impulse engine Mk2", "Nuclear drive Mk4" };
  /**
   * Propulsion rare tech names for level 8.
   */
  public static final String[] PROPULSION_RARE_TECH_LEVEL8_NAMES = {
      "Antimatter source Mk3"};
  /**
   * Propulsion tech names for level 9
   */
  public static final String[] PROPULSION_TECH_LEVEL9_NAMES = {"Warp drive Mk7",
      "Hyper drive Mk7", "Impulse engine Mk3", "Tachyon source Mk2",
      "Ice colonization"};
  /**
   * Propulsion tech names for level 10
   */
  public static final String[] PROPULSION_TECH_LEVEL10_NAMES = {
      "Warp drive Mk8", "Hyper drive Mk8", "Impulse engine Mk4",
      "Combat thrusters Mk3" };
  /**
   * Propulsion rare tech names for level 10.
   */
  public static final String[] PROPULSION_RARE_TECH_LEVEL10_NAMES = {
      "Zero-point source Mk3"};
  /**
   * Propulsion tech names for level 11
   */
  public static final String[] PROPULSION_TECH_LEVEL11_NAMES = {
      "Warp drive Mk9", "Hyper drive Mk9", "Impulse engine Mk5",
      "Antimatter source Mk1" };
  /**
   * Propulsion tech names for level 12
   */
  public static final String[] PROPULSION_TECH_LEVEL12_NAMES = {
      "Warp drive Mk10", "Hyper drive Mk10", "Combat thrusters Mk4" };
  /**
   * Propulsion tech names for level 13
   */
  public static final String[] PROPULSION_TECH_LEVEL13_NAMES = {
      "Warp drive Mk11", "Hyper drive Mk11", "Antimatter source Mk2" };
  /**
   * Propulsion tech names for level 14
   */
  public static final String[] PROPULSION_TECH_LEVEL14_NAMES = {
      "Impulse engine Mk6", "Combat thrusters Mk4"};
  /**
   * Propulsion tech names for level 15
   */
  public static final String[] PROPULSION_TECH_LEVEL15_NAMES = {
      "Impulse engine Mk7", "Zero-point source Mk1", "Material replicator"};
  /**
   * Propulsion tech names for level 16
   */
  public static final String[] PROPULSION_TECH_LEVEL16_NAMES = {
      "Warp drive Mk12", "Hyper drive Mk12", "Zero-point source Mk2"};
  /**
   * Propulsion tech names for level 17
   */
  public static final String[] PROPULSION_TECH_LEVEL17_NAMES = {
      "Impulse engine Mk8", "Dark matter source Mk1"};
  /**
   * Propulsion tech names for level 18
   */
  public static final String[] PROPULSION_TECH_LEVEL18_NAMES = {
      "Warp drive Mk12", "Hyper drive Mk12", "Dark matter source Mk2"};
  /**
   * Propulsion tech names for level 19
   */
  public static final String[] PROPULSION_TECH_LEVEL19_NAMES = {
      "Dark matter source Mk3"};
  /**
   * Propulsion tech names for level 20
   */
  public static final String[] PROPULSION_TECH_LEVEL20_NAMES = {
      "Dark matter source Mk4"};

  /**
   * Electronics tech names for level 1
   */
  public static final String[] ELECTRONICS_TECH_LEVEL1_NAMES = {"Scanner Mk1",
      "Planetary scanner Mk1" };
  /**
   * Electronics tech names for level 2
   */
  public static final String[] ELECTRONICS_TECH_LEVEL2_NAMES = {
      "Cloaking device Mk1", "Targeting computer Mk1", "Espionage module Mk1" };
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
      "Cloaking device Mk2", "Targeting computer Mk2", "LR scanner Mk1",
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
      "Cloaking device Mk3", "Targeting computer Mk3", "LR scanner Mk2" };
  /**
   * Electronics tech names for level 7
   */
  public static final String[] ELECTRONICS_TECH_LEVEL7_NAMES = {"Scanner Mk4",
      "Espionage module Mk4"};
  /**
   * Electronics rare tech names for level 7
   */
  public static final String[] ELECTRONICS_RARE_TECH_LEVEL7_NAMES = {
      "Orbital ascension portal"};
  /**
   * Electronics tech names for level 8
   */
  public static final String[] ELECTRONICS_TECH_LEVEL8_NAMES = {
      "Cloaking device Mk4", "Planetary scanner Mk4", "LR scanner Mk3" };

  /**
   * Electronics tech names for level 9
   */
  public static final String[] ELECTRONICS_TECH_LEVEL9_NAMES = {"Scanner Mk5",
      "Targeting computer Mk4", "Broadcasting network" };
  /**
   * Electronics tech names for level 10
   */
  public static final String[] ELECTRONICS_TECH_LEVEL10_NAMES = {
      "Cloaking device Mk5", "Planetary scanner Mk5", "Espionage module Mk5" };
  /**
   * Electronics tech names for level 11
   */
  public static final String[] ELECTRONICS_TECH_LEVEL11_NAMES = {
      "LR scanner Mk4" };
  /**
   * Electronics tech names for level 12
   */
  public static final String[] ELECTRONICS_TECH_LEVEL12_NAMES = {
      "Scanner Mk5", "Planetary scanner Mk6", "Cloaking device Mk6" };
  /**
   * Electronics tech names for level 13
   */
  public static final String[] ELECTRONICS_TECH_LEVEL13_NAMES = {
      "Espionage module Mk6", "Targeting computer Mk5"};
  /**
   * Electronics tech names for level 14
   */
  public static final String[] ELECTRONICS_TECH_LEVEL14_NAMES = {
     "Planetary scanner Mk7", "Cloaking device Mk7" };
  /**
   * Electronics tech names for level 15
   */
  public static final String[] ELECTRONICS_TECH_LEVEL15_NAMES = {
      "Scanner Mk6", "Deep space scanner"};
  /**
   * Electronics tech names for level 16
   */
  public static final String[] ELECTRONICS_TECH_LEVEL16_NAMES = {
     "Planetary scanner Mk8", "Cloaking device Mk8" };
  /**
   * Electronics tech names for level 17
   */
  public static final String[] ELECTRONICS_TECH_LEVEL17_NAMES = {
      "LR scanner Mk5" };
  /**
   * Electronics tech names for level 18
   */
  public static final String[] ELECTRONICS_TECH_LEVEL18_NAMES = {
     "Planetary scanner Mk9", "Cloaking device Mk9" };
  /**
   * Electronics tech names for level 19
   */
  public static final String[] ELECTRONICS_TECH_LEVEL19_NAMES = {
      "Scanner Mk7"};
  /**
   * Electronics tech names for level 20
   */
  public static final String[] ELECTRONICS_TECH_LEVEL20_NAMES = {
     "Planetary scanner Mk10", "Cloaking device Mk10" };

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
    case 11:
      list = COMBAT_TECH_LEVEL11_NAMES;
      break;
    case 12:
      list = COMBAT_TECH_LEVEL12_NAMES;
      break;
    case 13:
      list = COMBAT_TECH_LEVEL13_NAMES;
      break;
    case 14:
      list = COMBAT_TECH_LEVEL14_NAMES;
      break;
    case 15:
      list = COMBAT_TECH_LEVEL15_NAMES;
      break;
    case 16:
      list = COMBAT_TECH_LEVEL16_NAMES;
      break;
    case 17:
      list = COMBAT_TECH_LEVEL17_NAMES;
      break;
    case 18:
      list = COMBAT_TECH_LEVEL18_NAMES;
      break;
    case 19:
      list = COMBAT_TECH_LEVEL19_NAMES;
      break;
    case 20:
      list = COMBAT_TECH_LEVEL20_NAMES;
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
            || techName.startsWith("Antimatter beam")
            || techName.startsWith("Blue laser")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_LASERGUN));
        } else if (techName.startsWith("Railgun")
            || techName.startsWith("Massdrive")) {
          //TODO: Currently there is no reason to exclude railgun from
          //based on any traits. Maybe this could be randomize later
          //If realm will get railguns or chainguns
          tech.setIcon(Icons.getIconByName(Icons.ICON_COMBAT_TECH));
        } else if (techName.startsWith("ECM torpedo")
            || techName.startsWith("HE missile")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_MISSILE));
        } else if (techName.startsWith("Photon torpedo")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PHOTON_TORPEDO));
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
            || techName.startsWith("Chaingun")
            || techName.startsWith("Artemis fragcannon")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_MULTI_CANNON));
          //TODO: Currently there is no reason to exclude railgun from
          //based on any traits. Maybe this could be randomize later
          //If realm will get railguns or chainguns
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
          tech.setIcon(Icons.getIconByName(Icons.ICON_GRAVITY_RIPPER));
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
    case 11:
      list = DEFENSE_TECH_LEVEL11_NAMES;
      break;
    case 12:
      list = DEFENSE_TECH_LEVEL12_NAMES;
      break;
    case 13:
      list = DEFENSE_TECH_LEVEL13_NAMES;
      break;
    case 14:
      list = DEFENSE_TECH_LEVEL14_NAMES;
      break;
    case 15:
      list = DEFENSE_TECH_LEVEL15_NAMES;
      break;
    case 16:
      list = DEFENSE_TECH_LEVEL16_NAMES;
      break;
    case 17:
      list = DEFENSE_TECH_LEVEL17_NAMES;
      break;
    case 18:
      list = DEFENSE_TECH_LEVEL18_NAMES;
      break;
    case 19:
      list = DEFENSE_TECH_LEVEL19_NAMES;
      break;
    case 20:
      list = DEFENSE_TECH_LEVEL20_NAMES;
      break;
    default:
      return null;
    }
    for (int i = 0; i < list.length; i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Defense, level);
        if (techName.equals("Volcanic colonization")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANET));
          tech.setTradeable(false);
          tech.setExcludeList(false);
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTraits(
              TraitIds.TOLERATE_LAVA));
        } else if (techName.equals("Desert colonization")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANET));
          tech.setTradeable(false);
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTraits(
              TraitIds.ZERO_GRAVITY_BEING, TraitIds.PHOTOSYNTHESIS));
        } else if (techName.startsWith("Planetary defense turret Mk")
            || techName.startsWith("Orbital shield")
            || techName.equals("Space academy")
            || techName.startsWith("Orbital elevator Mk")) {
          tech.setImprovement(techName);
          if (techName.startsWith("Planetary defense turret Mk")) {
            tech.setExcludeList(true);
            tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
                TraitIds.ZERO_GRAVITY_BEING));
          }
          if (techName.startsWith("Orbital elevator Mk")) {
            tech.setExcludeList(false);
            tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
                TraitIds.ZERO_GRAVITY_BEING));
          }
        } else {
          tech.setComponent(techName);
        }
        if (techName.startsWith("Shield")
            || techName.startsWith("Shadow shield")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_SHIELD));
        } else if (techName.startsWith("Armor plating")
            || techName.startsWith("Shadow armor")) {
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
          tech.setIcon(Icons.getIconByName(Icons.ICON_MULTIDIMENSION_SHIELD));
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
    case 11:
      list = HULL_TECH_LEVEL11_NAMES;
      break;
    case 12:
      list = HULL_TECH_LEVEL12_NAMES;
      break;
    case 13:
      list = HULL_TECH_LEVEL13_NAMES;
      break;
    case 14:
      list = HULL_TECH_LEVEL14_NAMES;
      break;
    case 15:
      list = HULL_TECH_LEVEL15_NAMES;
      break;
    case 16:
      list = HULL_TECH_LEVEL16_NAMES;
      break;
    case 17:
      list = HULL_TECH_LEVEL17_NAMES;
      break;
    case 18:
      list = HULL_TECH_LEVEL18_NAMES;
      break;
    case 19:
      list = HULL_TECH_LEVEL19_NAMES;
      break;
    case 20:
      list = HULL_TECH_LEVEL20_NAMES;
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
          tech.setExcludeList(false);
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTraits(
              TraitIds.ENERGY_POWERED, TraitIds.LITHOVORIC, TraitIds.EAT_LESS));
          tech.setTradeable(false);
          return tech;
        } else if (techName.equals("Minor orbital")
            || techName.equals("Death Moon")
            || techName.equals("Large orbital station")
            || techName.equals("Orbital station")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_STARBASE));
          tech.setTradeable(false);
          tech.setExcludeList(false);
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
              TraitIds.ZERO_GRAVITY_BEING));
          tech.setHull(techName);
          return tech;
        } else if (techName.contains("starbase")
            || techName.contains("Artificial planet")
            || techName.contains("orbital")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_STARBASE));
        } else {
          tech.setIcon(Icons.getIconByName(Icons.ICON_HULL_TECH));
        }
        if (techName.startsWith("Privateer Mk")
            || techName.startsWith("Galleon Mk")) {
          tech.setComponent("Privateer module");
          tech.setHull(techName);
        } else if (techName.startsWith("Colony")) {
          tech.setHull(techName);
          tech.setComponent("Colonization module");
          tech.setIcon(Icons.getIconByName(Icons.ICON_PEOPLE));
          tech.setTradeable(false);
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
              TraitIds.SPORE_COLONIZATION));
        } else if (techName.startsWith("Spore")) {
          tech.setHull(techName);
          tech.setComponent("Spore module");
          tech.setIcon(Icons.getIconByName(Icons.ICON_PEOPLE));
          tech.setTradeable(false);
          tech.setExcludeList(false);
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
              TraitIds.SPORE_COLONIZATION));
        } else if (techName.startsWith("Cargo bay")
            || techName.startsWith("Armored cargo bay")) {
          tech.setComponent(techName);
          tech.setIcon(Icons.getIconByName(Icons.ICON_CLOSED));
        } else if (techName.startsWith("Fighter bay Mk")) {
          tech.setComponent(techName);
        } else if (techName.startsWith("Repair module Mk")) {
          tech.setComponent(techName);
          tech.setRareTech(true);
          tech.setIcon(Icons.getIconByName(Icons.ICON_WRENCH));
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
      list = TextUtilities.concanateStringArrays(IMPROVEMENT_TECH_LEVEL9_NAMES,
          IMPROVEMENT_RARE_TECH_LEVEL9_NAMES);
      break;
    case 10:
      list = IMPROVEMENT_TECH_LEVEL10_NAMES;
      break;
    case 11:
      list = IMPROVEMENT_TECH_LEVEL11_NAMES;
      break;
    case 12:
      list = IMPROVEMENT_TECH_LEVEL12_NAMES;
      break;
    case 13:
      list = IMPROVEMENT_TECH_LEVEL13_NAMES;
      break;
    case 14:
      list = IMPROVEMENT_TECH_LEVEL14_NAMES;
      break;
    case 15:
      list = IMPROVEMENT_TECH_LEVEL15_NAMES;
      break;
    case 16:
      list = IMPROVEMENT_TECH_LEVEL16_NAMES;
      break;
    case 17:
      list = IMPROVEMENT_TECH_LEVEL17_NAMES;
      break;
    case 18:
      list = IMPROVEMENT_TECH_LEVEL18_NAMES;
      break;
    case 19:
      list = IMPROVEMENT_TECH_LEVEL19_NAMES;
      break;
    case 20:
      list = IMPROVEMENT_TECH_LEVEL20_NAMES;
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
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
              TraitIds.ZERO_GRAVITY_BEING));
        } else if (techName.equals("Swamp colonization")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_PLANET));
          tech.setTradeable(false);
          tech.setExcludeList(true);
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
              TraitIds.ZERO_GRAVITY_BEING));
        } else if (techName.startsWith("Starbase")
            || techName.startsWith("Zero-G")) {
          tech.setComponent(techName);
          tech.setIcon(Icons.getIconByName(Icons.ICON_STARBASE));
        } else if (techName.startsWith("Deadly virus")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_DEATH));
          tech.setRareTech(true);
        } else {
          tech.setImprovement(techName);
          if (techName.startsWith("Barracks")
              || techName.startsWith("Galactic academy")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_TROOPS));
          } else if (techName.startsWith("Basic lab")
              || techName.startsWith("Advanced laboratory")
              || techName.startsWith("Improved laboratory")
              || techName.startsWith("Ultimate laboratory")
              || techName.startsWith("Research center")
              || techName.startsWith("New technology center")
              || techName.startsWith("Neural research center")
              || techName.startsWith("Super AI center")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_RESEARCH));
          } else if (techName.startsWith("Orbital lift")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_ORBITAL_ELEVATOR));
            tech.setRareTech(true);
          } else if (techName.startsWith("Planetary ascension portal")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_AIRLOCK_OPEN));
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
          } else if (techName.startsWith("Advanced farm")
              || techName.startsWith("Improved farm")
              || techName.startsWith("Ultimate farm")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_FARM));
            tech.setExcludeList(true);
            tech.setSpaceRaces(SpaceRaceUtility.getRacesByTraits(
                TraitIds.ENERGY_POWERED, TraitIds.LITHOVORIC,
                TraitIds.PHOTOSYNTHESIS));
          } else if (techName.startsWith("Farming center")
              || techName.startsWith("Hydropodic farming center")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_FARM));
            tech.setExcludeList(true);
            tech.setSpaceRaces(SpaceRaceUtility.getRacesByTraits(
                TraitIds.ENERGY_POWERED, TraitIds.LITHOVORIC,
                TraitIds.PHOTOSYNTHESIS));
          } else if (techName.startsWith("Cyber lab")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_RESEARCH));
            tech.setExcludeList(false);
            tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
                TraitIds.ROBOTIC));
            tech.setTradeable(false);
          } else if (techName.startsWith("Collective research center")
              || techName.startsWith("Research matrix")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_RESEARCH));
            tech.setExcludeList(false);
            tech.setSpaceRaces(SpaceRaceUtility.getRacesByTraits(
                TraitIds.ROBOTIC, TraitIds.CYBORG_LIFE_SPAN));
            tech.setTradeable(false);
          } else if (techName.startsWith("Advanced furnace")
              || techName.startsWith("Improved furnace")
              || techName.startsWith("Ultimate furnace")
              || techName.startsWith("Massive blast furnace")
              || techName.startsWith("Planetary furnace")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_METAL));
            tech.setExcludeList(false);
            tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
                TraitIds.LITHOVORIC));
            // This tech is rare tech that only Lithorians will learn it
            // but they can trade it to others if they wish.
            tech.setRareTech(true);
          } else if (techName.startsWith("Advanced reservoir")
              || techName.startsWith("Improved reservoir")
              || techName.startsWith("Ultimate reservoir")
              || techName.startsWith("Underground reservoir")
              || techName.startsWith("Crust reservoir")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_FARM));
            tech.setExcludeList(false);
            tech.setTradeable(false);
            tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
                TraitIds.PHOTOSYNTHESIS));
            tech.setRareTech(true);
          } else if (techName.startsWith("Advanced mine")
              || techName.startsWith("Improved mine")
              || techName.startsWith("Ultimate mine")
              || techName.startsWith("Mining center")
              || techName.startsWith("Nanobot mining center")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_MINE));
          } else if (techName.startsWith("Advanced factory")
              || techName.startsWith("Improved factory")
              || techName.startsWith("Ultimate factory")
              || techName.startsWith("Manufacturing center")
              || techName.startsWith("Nanobot manufacturing center")
              || techName.startsWith("Replicator center")) {
            tech.setIcon(Icons.getIconByName(Icons.ICON_FACTORY));
          } else if (techName.startsWith("Culture center")
              || techName.startsWith("Extreme sports center")
              || techName.startsWith("Culture complex")
              || techName.startsWith("Advanced culture complex")
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
    case 11:
      list = PROPULSION_TECH_LEVEL11_NAMES;
      break;
    case 12:
      list = PROPULSION_TECH_LEVEL12_NAMES;
      break;
    case 13:
      list = PROPULSION_TECH_LEVEL13_NAMES;
      break;
    case 14:
      list = PROPULSION_TECH_LEVEL14_NAMES;
      break;
    case 15:
      list = PROPULSION_TECH_LEVEL15_NAMES;
      break;
    case 16:
      list = PROPULSION_TECH_LEVEL16_NAMES;
      break;
    case 17:
      list = PROPULSION_TECH_LEVEL17_NAMES;
      break;
    case 18:
      list = PROPULSION_TECH_LEVEL18_NAMES;
      break;
    case 19:
      list = PROPULSION_TECH_LEVEL19_NAMES;
      break;
    case 20:
      list = PROPULSION_TECH_LEVEL20_NAMES;
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
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
              TraitIds.ZERO_GRAVITY_BEING));
        } else if (techName.equals("Solar drive")) {
          tech.setComponent(techName);
          tech.setIcon(Icons.getIconByName(Icons.ICON_PROPULSION_TECH));
          tech.setExcludeList(false);
          tech.setSpaceRaces(SpaceRaceUtility.getRacesByTrait(
              TraitIds.SPORE_COLONIZATION));
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
      list = TextUtilities.concanateStringArrays(ELECTRONICS_TECH_LEVEL7_NAMES,
          ELECTRONICS_RARE_TECH_LEVEL7_NAMES);
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
    case 11:
      list = ELECTRONICS_TECH_LEVEL11_NAMES;
      break;
    case 12:
      list = ELECTRONICS_TECH_LEVEL12_NAMES;
      break;
    case 13:
      list = ELECTRONICS_TECH_LEVEL13_NAMES;
      break;
    case 14:
      list = ELECTRONICS_TECH_LEVEL14_NAMES;
      break;
    case 15:
      list = ELECTRONICS_TECH_LEVEL15_NAMES;
      break;
    case 16:
      list = ELECTRONICS_TECH_LEVEL16_NAMES;
      break;
    case 17:
      list = ELECTRONICS_TECH_LEVEL17_NAMES;
      break;
    case 18:
      list = ELECTRONICS_TECH_LEVEL18_NAMES;
      break;
    case 19:
      list = ELECTRONICS_TECH_LEVEL19_NAMES;
      break;
    case 20:
      list = ELECTRONICS_TECH_LEVEL20_NAMES;
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
        } else if (techName.equals("Orbital ascension portal")) {
          tech.setIcon(Icons.getIconByName(Icons.ICON_STARBASE));
          tech.setRareTech(true);
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
      tech.setIcon(Icons.getIconByName(Icons.ICON_HEART));
      tech.setTradeable(false);
      tech.setComponent(techName);
    }
    if (techName.startsWith("Large heart")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_HEART));
      tech.setTradeable(false);
      tech.setComponent(techName);
    }
    if (techName.startsWith("Space fin")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_SPACE_FIN));
      tech.setTradeable(false);
      tech.setComponent(techName);
    }
    if (techName.startsWith("Massive mouth with teeth")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_MOUTH));
      tech.setTradeable(false);
      tech.setComponent(techName);
    }
    if (techName.startsWith("Tentacle")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_TENTACLE));
      tech.setTradeable(false);
      tech.setComponent(techName);
    }
    if (techName.startsWith("Arm spike")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_TENTACLE));
      tech.setTradeable(false);
      tech.setComponent(techName);
    }
    if (techName.startsWith("Plasma spit")) {
      tech.setIcon(Icons.getIconByName(Icons.ICON_PLASMA_CANNON));
      tech.setTradeable(false);
      tech.setComponent(techName);
    }
    if (techName.startsWith("Space worm")
        || techName.startsWith("Kraken")
        || techName.startsWith("Large kraken")
        || techName.startsWith("Devourer")) {
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
  private static final int TECH_4_LEVEL_RP_COST = 14;
  /**
   * Tech 5 Level cost
   */
  private static final int TECH_5_LEVEL_RP_COST = 19;
  /**
   * Tech 6 Level cost
   */
  private static final int TECH_6_LEVEL_RP_COST = 25;
  /**
   * Tech 7 Level cost
   */
  private static final int TECH_7_LEVEL_RP_COST = 33;
  /**
   * Tech 8 Level cost
   */
  private static final int TECH_8_LEVEL_RP_COST = 42;
  /**
   * Tech 9 Level cost
   */
  private static final int TECH_9_LEVEL_RP_COST = 50;
  /**
   * Tech 10 Level cost
   */
  private static final int TECH_10_LEVEL_RP_COST = 59;
  /**
   * Tech 11 Level cost
   */
  private static final int TECH_11_LEVEL_RP_COST = 69;
  /**
   * Tech 12 Level cost
   */
  private static final int TECH_12_LEVEL_RP_COST = 81;
  /**
   * Tech 13 Level cost
   */
  private static final int TECH_13_LEVEL_RP_COST = 94;
  /**
   * Tech 14 Level cost
   */
  private static final int TECH_14_LEVEL_RP_COST = 108;
  /**
   * Tech 15 Level cost
   */
  private static final int TECH_15_LEVEL_RP_COST = 123;
  /**
   * Tech 16 Level cost
   */
  private static final int TECH_16_LEVEL_RP_COST = 139;
  /**
   * Tech 17 Level cost
   */
  private static final int TECH_17_LEVEL_RP_COST = 156;
  /**
   * Tech 18 Level cost
   */
  private static final int TECH_18_LEVEL_RP_COST = 174;
  /**
   * Tech 19 Level cost
   */
  private static final int TECH_19_LEVEL_RP_COST = 193;
  /**
   * Tech 20 Level cost
   */
  private static final int TECH_20_LEVEL_RP_COST = 213;
  /**
   * Tech FUTURE Level cost
   */
  private static final int TECH_FUTURE_LEVEL_RP_COST = 238;

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
   * @param gameLength Maximum game length in star years
   * @return Amount of research points required
   */
  public static int getTechCost(final int level, final int gameLength) {
    int multiplier = 1;
    int veryveryLowBonus = 0;
    int veryLowBonus = 0;
    int lowBonus = 0;
    int mediumBonus = 0;
    int highBonus = 0;
    int veryHighBonus = 0;
    if (gameLength <= 200) {
      veryveryLowBonus = -2;
      veryLowBonus = -5;
      lowBonus = -7;
      mediumBonus = -15;
      highBonus = -25;
      veryHighBonus = -43;
    } else if (gameLength <= 300) {
      veryveryLowBonus = 0;
      veryLowBonus = -2;
      lowBonus = -5;
      mediumBonus = -7;
      highBonus = -15;
      veryHighBonus = -25;
    } else if (gameLength <= 400) {
      veryveryLowBonus = 0;
      veryLowBonus = 0;
      lowBonus = -2;
      mediumBonus = -5;
      highBonus = -7;
      veryHighBonus = -15;
    } else if (gameLength <= 600) {
      veryveryLowBonus = 0;
      veryLowBonus = 0;
      lowBonus = 0;
      mediumBonus = 0;
      highBonus = 0;
    } else if (gameLength <= 800) {
      multiplier = 2;
      veryveryLowBonus = 0;
      veryLowBonus = 2;
      lowBonus = 3;
      mediumBonus = 6;
      highBonus = 12;
      veryHighBonus = 15;
    } else if (gameLength <= 1000) {
      multiplier = 2;
      veryveryLowBonus = 2;
      veryLowBonus = 5;
      lowBonus = 9;
      mediumBonus = 13;
      highBonus = 23;
      veryHighBonus = 27;
    }
    switch (level) {
    case 1:
      return TECH_1_LEVEL_RP_COST * multiplier + veryveryLowBonus;
    case 2:
      return TECH_2_LEVEL_RP_COST * multiplier + veryveryLowBonus;
    case 3:
      return TECH_3_LEVEL_RP_COST * multiplier + veryveryLowBonus;
    case 4:
      return TECH_4_LEVEL_RP_COST * multiplier + veryveryLowBonus;
    case 5:
      return TECH_5_LEVEL_RP_COST * multiplier + veryLowBonus;
    case 6:
      return TECH_6_LEVEL_RP_COST * multiplier + veryLowBonus;
    case 7:
      return TECH_7_LEVEL_RP_COST * multiplier + veryLowBonus;
    case 8:
      return TECH_8_LEVEL_RP_COST * multiplier + lowBonus;
    case 9:
      return TECH_9_LEVEL_RP_COST * multiplier + lowBonus;
    case 10:
      return TECH_10_LEVEL_RP_COST * multiplier + mediumBonus;
    case 11:
      return TECH_11_LEVEL_RP_COST * multiplier + mediumBonus;
    case 12:
      return TECH_12_LEVEL_RP_COST * multiplier + mediumBonus;
    case 13:
      return TECH_13_LEVEL_RP_COST * multiplier + mediumBonus;
    case 14:
      return TECH_14_LEVEL_RP_COST * multiplier + highBonus;
    case 15:
      return TECH_15_LEVEL_RP_COST * multiplier + highBonus;
    case 16:
      return TECH_16_LEVEL_RP_COST * multiplier + highBonus;
    case 17:
      return TECH_17_LEVEL_RP_COST * multiplier + highBonus;
    case 18:
      return TECH_18_LEVEL_RP_COST * multiplier + veryHighBonus;
    case 19:
      return TECH_19_LEVEL_RP_COST * multiplier + veryHighBonus;
    case 20:
      return TECH_20_LEVEL_RP_COST * multiplier + veryHighBonus;
    default:
      return TECH_FUTURE_LEVEL_RP_COST * multiplier + veryHighBonus;
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
      case 11:
        possibleTechs = COMBAT_TECH_LEVEL11_NAMES;
        break;
      case 12:
        possibleTechs = COMBAT_TECH_LEVEL12_NAMES;
        break;
      case 13:
        possibleTechs = COMBAT_TECH_LEVEL13_NAMES;
        break;
      case 14:
        possibleTechs = COMBAT_TECH_LEVEL14_NAMES;
        break;
      case 15:
        possibleTechs = COMBAT_TECH_LEVEL15_NAMES;
        break;
      case 16:
        possibleTechs = COMBAT_TECH_LEVEL16_NAMES;
        break;
      case 17:
        possibleTechs = COMBAT_TECH_LEVEL17_NAMES;
        break;
      case 18:
        possibleTechs = COMBAT_TECH_LEVEL18_NAMES;
        break;
      case 19:
        possibleTechs = COMBAT_TECH_LEVEL19_NAMES;
        break;
      case 20:
        possibleTechs = COMBAT_TECH_LEVEL20_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 20!");
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
      case 11:
        possibleTechs = DEFENSE_TECH_LEVEL11_NAMES;
        break;
      case 12:
        possibleTechs = DEFENSE_TECH_LEVEL12_NAMES;
        break;
      case 13:
        possibleTechs = DEFENSE_TECH_LEVEL13_NAMES;
        break;
      case 14:
        possibleTechs = DEFENSE_TECH_LEVEL14_NAMES;
        break;
      case 15:
        possibleTechs = DEFENSE_TECH_LEVEL15_NAMES;
        break;
      case 16:
        possibleTechs = DEFENSE_TECH_LEVEL16_NAMES;
        break;
      case 17:
        possibleTechs = DEFENSE_TECH_LEVEL17_NAMES;
        break;
      case 18:
        possibleTechs = DEFENSE_TECH_LEVEL18_NAMES;
        break;
      case 19:
        possibleTechs = DEFENSE_TECH_LEVEL19_NAMES;
        break;
      case 20:
        possibleTechs = DEFENSE_TECH_LEVEL20_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 20!");
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
      case 11:
        possibleTechs = HULL_TECH_LEVEL11_NAMES;
        break;
      case 12:
        possibleTechs = HULL_TECH_LEVEL12_NAMES;
        break;
      case 13:
        possibleTechs = HULL_TECH_LEVEL13_NAMES;
        break;
      case 14:
        possibleTechs = HULL_TECH_LEVEL14_NAMES;
        break;
      case 15:
        possibleTechs = HULL_TECH_LEVEL15_NAMES;
        break;
      case 16:
        possibleTechs = HULL_TECH_LEVEL16_NAMES;
        break;
      case 17:
        possibleTechs = HULL_TECH_LEVEL17_NAMES;
        break;
      case 18:
        possibleTechs = HULL_TECH_LEVEL18_NAMES;
        break;
      case 19:
        possibleTechs = HULL_TECH_LEVEL19_NAMES;
        break;
      case 20:
        possibleTechs = HULL_TECH_LEVEL20_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 20!");
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
      case 11:
        possibleTechs = IMPROVEMENT_TECH_LEVEL11_NAMES;
        break;
      case 12:
        possibleTechs = IMPROVEMENT_TECH_LEVEL12_NAMES;
        break;
      case 13:
        possibleTechs = IMPROVEMENT_TECH_LEVEL13_NAMES;
        break;
      case 14:
        possibleTechs = IMPROVEMENT_TECH_LEVEL14_NAMES;
        break;
      case 15:
        possibleTechs = IMPROVEMENT_TECH_LEVEL15_NAMES;
        break;
      case 16:
        possibleTechs = IMPROVEMENT_TECH_LEVEL16_NAMES;
        break;
      case 17:
        possibleTechs = IMPROVEMENT_TECH_LEVEL17_NAMES;
        break;
      case 18:
        possibleTechs = IMPROVEMENT_TECH_LEVEL18_NAMES;
        break;
      case 19:
        possibleTechs = IMPROVEMENT_TECH_LEVEL19_NAMES;
        break;
      case 20:
        possibleTechs = IMPROVEMENT_TECH_LEVEL20_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 20!");
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
      case 11:
        possibleTechs = PROPULSION_TECH_LEVEL11_NAMES;
        break;
      case 12:
        possibleTechs = PROPULSION_TECH_LEVEL12_NAMES;
        break;
      case 13:
        possibleTechs = PROPULSION_TECH_LEVEL13_NAMES;
        break;
      case 14:
        possibleTechs = PROPULSION_TECH_LEVEL14_NAMES;
        break;
      case 15:
        possibleTechs = PROPULSION_TECH_LEVEL15_NAMES;
        break;
      case 16:
        possibleTechs = PROPULSION_TECH_LEVEL16_NAMES;
        break;
      case 17:
        possibleTechs = PROPULSION_TECH_LEVEL17_NAMES;
        break;
      case 18:
        possibleTechs = PROPULSION_TECH_LEVEL18_NAMES;
        break;
      case 19:
        possibleTechs = PROPULSION_TECH_LEVEL19_NAMES;
        break;
      case 20:
        possibleTechs = PROPULSION_TECH_LEVEL20_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 20!");
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
      case 11:
        possibleTechs = ELECTRONICS_TECH_LEVEL11_NAMES;
        break;
      case 12:
        possibleTechs = ELECTRONICS_TECH_LEVEL12_NAMES;
        break;
      case 13:
        possibleTechs = ELECTRONICS_TECH_LEVEL13_NAMES;
        break;
      case 14:
        possibleTechs = ELECTRONICS_TECH_LEVEL14_NAMES;
        break;
      case 15:
        possibleTechs = ELECTRONICS_TECH_LEVEL15_NAMES;
        break;
      case 16:
        possibleTechs = ELECTRONICS_TECH_LEVEL16_NAMES;
        break;
      case 17:
        possibleTechs = ELECTRONICS_TECH_LEVEL17_NAMES;
        break;
      case 18:
        possibleTechs = ELECTRONICS_TECH_LEVEL18_NAMES;
        break;
      case 19:
        possibleTechs = ELECTRONICS_TECH_LEVEL19_NAMES;
        break;
      case 20:
        possibleTechs = ELECTRONICS_TECH_LEVEL20_NAMES;
        break;
      default:
        throw new IllegalArgumentException("Tech level is beyond 20!");
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
