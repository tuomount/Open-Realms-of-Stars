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
public class TechFactory {

  public static final String[] COMBAT_TECH_LEVEL1_NAMES = 
    {"Laser Mk1","Railgun Mk1","Photon Torpedo Mk1"};
  public static final String[] COMBAT_TECH_LEVEL2_NAMES = 
    {"Laser Mk2","Railgun Mk2","Photon Torpedo Mk2","Planetary invasion module"};
  public static final String[] COMBAT_TECH_LEVEL3_NAMES = 
    {"Laser Mk3","Railgun Mk3","Photon Torpedo Mk3","ECM Torpedo Mk1","HE Missile Mk1"};
  public static final String[] COMBAT_TECH_LEVEL4_NAMES = 
    {"Laser Mk4","Railgun Mk4","Photon Torpedo Mk4","ECM Torpedo Mk2","HE Missile Mk2","Orbital bombs Mk1"};
  public static final String[] COMBAT_TECH_LEVEL5_NAMES = 
    {"Laser Mk5","Railgun Mk5","Photon Torpedo Mk5","ECM Torpedo Mk3","HE Missile Mk3","Orbital bombs Mk2"};
  public static final String[] COMBAT_TECH_LEVEL6_NAMES = 
    {"Phasors Mk1","Massdrive Mk1","Photon Torpedo Mk6","ECM Torpedo Mk4","HE Missile Mk4"};
  public static final String[] COMBAT_TECH_LEVEL7_NAMES = 
    {"Phasors Mk2","Massdrive Mk2","Photon Torpedo Mk7","ECM Torpedo Mk5","HE Missile Mk5","Shock trooper module"};
  public static final String[] COMBAT_TECH_LEVEL8_NAMES = 
    {"Phasors Mk3","Massdrive Mk3","Photon Torpedo Mk8","ECM Torpedo Mk6","HE Missile Mk6","Orbital nuke"};
  public static final String[] COMBAT_TECH_LEVEL9_NAMES = 
    {"Phasors Mk4","Massdrive Mk4","Photon Torpedo Mk9","ECM Torpedo Mk7","HE Missile Mk7","Orbital smart bombs"};
  public static final String[] COMBAT_TECH_LEVEL10_NAMES = 
    {"Phasors Mk5","Massdrive Mk5","Photon Torpedo Mk10","ECM Torpedo Mk8","HE Missile Mk8"};


  public static final String[] DEFENSE_TECH_LEVEL1_NAMES = 
    {"Shield Mk1","Armor Plating Mk1"};
  public static final String[] DEFENSE_TECH_LEVEL2_NAMES = 
    {"Shield Mk2","Armor Plating Mk2","Planetary Defense Turret Mk1"};
  public static final String[] DEFENSE_TECH_LEVEL3_NAMES = 
    {"Shield Mk3","Armor Plating Mk3"};
  public static final String[] DEFENSE_TECH_LEVEL4_NAMES = 
    {"Shield Mk4","Armor Plating Mk4","Shield Generator Mk1"};
  public static final String[] DEFENSE_TECH_LEVEL5_NAMES = 
    {"Shield Mk5","Armor Plating Mk5","Planetary Defense Turret Mk2"};
  public static final String[] DEFENSE_TECH_LEVEL6_NAMES = 
    {"Shield Mk6","Armor Plating Mk6"};
  public static final String[] DEFENSE_TECH_LEVEL7_NAMES = 
    {"Shield Mk7","Armor Plating Mk7"};
  public static final String[] DEFENSE_TECH_LEVEL8_NAMES = 
    {"Shield Mk8","Armor Plating Mk8"};
  public static final String[] DEFENSE_TECH_LEVEL9_NAMES = 
    {"Shield Mk9","Armor Plating Mk9","Shield Generator Mk2"};
  public static final String[] DEFENSE_TECH_LEVEL10_NAMES = 
    {"Shield Mk10","Armor Plating Mk10"};

  public static final String[] HULL_TECH_LEVEL1_NAMES = 
    {"Scout Mk1","Destroyer Mk1","Colony"};
  public static final String[] HULL_TECH_LEVEL2_NAMES = 
    {"Probe","Small Freighter","Small Starbase Mk1"};
  public static final String[] HULL_TECH_LEVEL3_NAMES = 
    {"Destroyer Mk2","Corvette Mk1","Small Starbase Mk2"};
  public static final String[] HULL_TECH_LEVEL4_NAMES = 
    {"Medium Freighter","Medium starbase","Scout Mk2"};
  public static final String[] HULL_TECH_LEVEL5_NAMES = 
    {"Cruiser","Battleship"};
  public static final String[] HULL_TECH_LEVEL6_NAMES = 
    {"Large Freighter","Large Starbase","Corvette Mk2","Privateer Mk1"};
  public static final String[] HULL_TECH_LEVEL7_NAMES = 
    {"Battle Cruiser","Privateer Mk2"};
  public static final String[] HULL_TECH_LEVEL8_NAMES = 
    {"Massive Freighter","Massive Starbase","Privateer Mk3"};
  public static final String[] HULL_TECH_LEVEL9_NAMES = 
    {"Capital Ship Mk1"};
  public static final String[] HULL_TECH_LEVEL10_NAMES = 
    {"Capital Ship Mk2"};

  public static final String[] IMPROVEMENT_TECH_LEVEL1_NAMES = 
    {"Laboratory","Barracks","Tax center"};
  public static final String[] IMPROVEMENT_TECH_LEVEL2_NAMES = 
    {"Advanced farm","Advanced mine","Advanced Factory"};
  public static final String[] IMPROVEMENT_TECH_LEVEL3_NAMES = 
    {"Advanced laboratory","Market center","Culture center"};
  public static final String[] IMPROVEMENT_TECH_LEVEL4_NAMES = 
    {"Trade center","Extreme sports center","Recycle center"};
  public static final String[] IMPROVEMENT_TECH_LEVEL5_NAMES = 
    {"Farming center","Mining center","Manufacturing center","Radiation dampener"};
  public static final String[] IMPROVEMENT_TECH_LEVEL6_NAMES = 
    {"Research center","Stock market","Galactic sports center"};
  public static final String[] IMPROVEMENT_TECH_LEVEL7_NAMES = 
    {"New technology center","VR movie center","Advanced recycle center"};
  public static final String[] IMPROVEMENT_TECH_LEVEL8_NAMES = 
    {"Galactic bank","Radiation well"};
  public static final String[] IMPROVEMENT_TECH_LEVEL9_NAMES = 
    {"Hydropodic farming center","Nanobot mining center","Nanobot manufacturing center"};
  public static final String[] IMPROVEMENT_TECH_LEVEL10_NAMES = 
    {"Neural research center","Super AI Center","Replicator center"};

  public static final String[] PROPULSION_TECH_LEVEL1_NAMES = 
    {"Ion drive Mk2","Nuclear drive Mk1"};
  public static final String[] PROPULSION_TECH_LEVEL2_NAMES = 
    {"Ion drive Mk3","Hyper drive Mk1"};
  public static final String[] PROPULSION_TECH_LEVEL3_NAMES = 
    {"Warp drive Mk1","Nuclear drive Mk2"};
  public static final String[] PROPULSION_TECH_LEVEL4_NAMES = 
    {"Wrap drive Mk2","Hyper drive Mk2"};
  public static final String[] PROPULSION_TECH_LEVEL5_NAMES = 
    {"Wrap drive Mk3","Hyper drive Mk3"};
  public static final String[] PROPULSION_TECH_LEVEL6_NAMES = 
    {"Wrap drive Mk4","Hyper drive Mk4","Nuclear drive Mk3"};
  public static final String[] PROPULSION_TECH_LEVEL7_NAMES = 
    {"Wrap drive Mk5","Hyper drive Mk5","Impulse engine Mk1"};
  public static final String[] PROPULSION_TECH_LEVEL8_NAMES = 
    {"Wrap drive Mk6","Hyper drive Mk6","Impulse engine Mk2"};
  public static final String[] PROPULSION_TECH_LEVEL9_NAMES = 
    {"Wrap drive Mk7","Hyper drive Mk7","Impulse engine Mk3"};
  public static final String[] PROPULSION_TECH_LEVEL10_NAMES = 
    {"Wrap drive Mk8","Hyper drive Mk8","Impulse engine Mk4"};

  public static final String[] ELECTRONICS_TECH_LEVEL1_NAMES = 
    {"Scanner Mk1","Planetary scanner Mk1","Cloaking device Mk1"};
  public static final String[] ELECTRONICS_TECH_LEVEL2_NAMES = 
    {"Cloaking device Mk2","Targeting computer Mk1"};
  public static final String[] ELECTRONICS_TECH_LEVEL3_NAMES = 
    {"Scanner Mk2","Planetary scanner Mk2","Jammer Mk1"};
  public static final String[] ELECTRONICS_TECH_LEVEL4_NAMES = 
    {"Cloaking device Mk3","Targeting computer Mk2","LR scanner Mk1"};
  public static final String[] ELECTRONICS_TECH_LEVEL5_NAMES = 
    {"Scanner Mk3","Planetary scanner Mk3","Jammer Mk2"};
  public static final String[] ELECTRONICS_TECH_LEVEL6_NAMES = 
    {"Cloaking device Mk4","Targeting computer Mk3","LR scanner Mk2"};
  public static final String[] ELECTRONICS_TECH_LEVEL7_NAMES = 
    {"Scanner Mk4","Jammer Mk3"};
  public static final String[] ELECTRONICS_TECH_LEVEL8_NAMES = 
    {"Cloaking device Mk5","Planetary scanner Mk4","LR scanner Mk3"};
  public static final String[] ELECTRONICS_TECH_LEVEL9_NAMES = 
    {"Scanner Mk5","Targeting computer Mk4"};
  public static final String[] ELECTRONICS_TECH_LEVEL10_NAMES = 
    {"Cloaking device Mk6","Planetary scanner Mk5","Jammer Mk4"};

  /**
   * Create combat tech with certain name and level
   * @param name Tech Name
   * @param level level between 1-10
   * @return Tech or null if match not found
   */
  public static Tech createCombatTech(String name, int level) {
    String[] list;
    switch (level) {
    case 1: list = COMBAT_TECH_LEVEL1_NAMES; break;
    case 2: list = COMBAT_TECH_LEVEL2_NAMES; break;
    case 3: list = COMBAT_TECH_LEVEL3_NAMES; break;
    case 4: list = COMBAT_TECH_LEVEL4_NAMES; break;
    case 5: list = COMBAT_TECH_LEVEL5_NAMES; break;
    case 6: list = COMBAT_TECH_LEVEL6_NAMES; break;
    case 7: list = COMBAT_TECH_LEVEL7_NAMES; break;
    case 8: list = COMBAT_TECH_LEVEL8_NAMES; break;
    case 9: list = COMBAT_TECH_LEVEL9_NAMES; break;
    case 10: list = COMBAT_TECH_LEVEL10_NAMES; break;
    default: return null;
    }
    for (int i=0;i<list.length;i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Combat, level);
        tech.setComponent(techName);
        tech.setIcon(Icons.getIconByName(Icons.ICON_COMBAT_TECH));
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
  public static Tech createDefenseTech(String name, int level) {
    String[] list;
    switch (level) {
    case 1: list = DEFENSE_TECH_LEVEL1_NAMES; break;
    case 2: list = DEFENSE_TECH_LEVEL2_NAMES; break;
    case 3: list = DEFENSE_TECH_LEVEL3_NAMES; break;
    case 4: list = DEFENSE_TECH_LEVEL4_NAMES; break;
    case 5: list = DEFENSE_TECH_LEVEL5_NAMES; break;
    case 6: list = DEFENSE_TECH_LEVEL6_NAMES; break;
    case 7: list = DEFENSE_TECH_LEVEL7_NAMES; break;
    case 8: list = DEFENSE_TECH_LEVEL8_NAMES; break;
    case 9: list = DEFENSE_TECH_LEVEL9_NAMES; break;
    case 10: list = DEFENSE_TECH_LEVEL10_NAMES; break;
    default: return null;
    }
    for (int i=0;i<list.length;i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Defense, level);
        if (techName.startsWith("Planetary Defense Turret Mk")) {
          tech.setImprovement(techName);
        } else {
          tech.setComponent(techName);
        }
        tech.setIcon(Icons.getIconByName(Icons.ICON_DEFENSE_TECH));
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
  public static Tech createHullTech(String name, int level) {
    String[] list;
    switch (level) {
    case 1: list = HULL_TECH_LEVEL1_NAMES; break;
    case 2: list = HULL_TECH_LEVEL2_NAMES; break;
    case 3: list = HULL_TECH_LEVEL3_NAMES; break;
    case 4: list = HULL_TECH_LEVEL4_NAMES; break;
    case 5: list = HULL_TECH_LEVEL5_NAMES; break;
    case 6: list = HULL_TECH_LEVEL6_NAMES; break;
    case 7: list = HULL_TECH_LEVEL7_NAMES; break;
    case 8: list = HULL_TECH_LEVEL8_NAMES; break;
    case 9: list = HULL_TECH_LEVEL9_NAMES; break;
    case 10: list = HULL_TECH_LEVEL10_NAMES; break;
    default: return null;
    }
    for (int i=0;i<list.length;i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Hulls, level);
        if (techName.startsWith("Privateer Mk")) {
          tech.setComponent("Privateer module");
        } else {
          tech.setHull(techName);
        }
        tech.setIcon(Icons.getIconByName(Icons.ICON_HULL_TECH));
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
  public static Tech createImprovementTech(String name, int level) {
    String[] list;
    switch (level) {
    case 1: list = IMPROVEMENT_TECH_LEVEL1_NAMES; break;
    case 2: list = IMPROVEMENT_TECH_LEVEL2_NAMES; break;
    case 3: list = IMPROVEMENT_TECH_LEVEL3_NAMES; break;
    case 4: list = IMPROVEMENT_TECH_LEVEL4_NAMES; break;
    case 5: list = IMPROVEMENT_TECH_LEVEL5_NAMES; break;
    case 6: list = IMPROVEMENT_TECH_LEVEL6_NAMES; break;
    case 7: list = IMPROVEMENT_TECH_LEVEL7_NAMES; break;
    case 8: list = IMPROVEMENT_TECH_LEVEL8_NAMES; break;
    case 9: list = IMPROVEMENT_TECH_LEVEL9_NAMES; break;
    case 10: list = IMPROVEMENT_TECH_LEVEL10_NAMES; break;
    default: return null;
    }
    for (int i=0;i<list.length;i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Improvements, level);
        tech.setImprovement(techName);
        tech.setIcon(Icons.getIconByName(Icons.ICON_IMPROVEMENT_TECH));
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
  public static Tech createPropulsionTech(String name, int level) {
    String[] list;
    switch (level) {
    case 1: list = PROPULSION_TECH_LEVEL1_NAMES; break;
    case 2: list = PROPULSION_TECH_LEVEL2_NAMES; break;
    case 3: list = PROPULSION_TECH_LEVEL3_NAMES; break;
    case 4: list = PROPULSION_TECH_LEVEL4_NAMES; break;
    case 5: list = PROPULSION_TECH_LEVEL5_NAMES; break;
    case 6: list = PROPULSION_TECH_LEVEL6_NAMES; break;
    case 7: list = PROPULSION_TECH_LEVEL7_NAMES; break;
    case 8: list = PROPULSION_TECH_LEVEL8_NAMES; break;
    case 9: list = PROPULSION_TECH_LEVEL9_NAMES; break;
    case 10: list = PROPULSION_TECH_LEVEL10_NAMES; break;
    default: return null;
    }
    for (int i=0;i<list.length;i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Propulsion, level);
        tech.setComponent(techName);
        tech.setIcon(Icons.getIconByName(Icons.ICON_PROPULSION_TECH));
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
  public static Tech createElectronicsTech(String name, int level) {
    String[] list;
    switch (level) {
    case 1: list = ELECTRONICS_TECH_LEVEL1_NAMES; break;
    case 2: list = ELECTRONICS_TECH_LEVEL2_NAMES; break;
    case 3: list = ELECTRONICS_TECH_LEVEL3_NAMES; break;
    case 4: list = ELECTRONICS_TECH_LEVEL4_NAMES; break;
    case 5: list = ELECTRONICS_TECH_LEVEL5_NAMES; break;
    case 6: list = ELECTRONICS_TECH_LEVEL6_NAMES; break;
    case 7: list = ELECTRONICS_TECH_LEVEL7_NAMES; break;
    case 8: list = ELECTRONICS_TECH_LEVEL8_NAMES; break;
    case 9: list = ELECTRONICS_TECH_LEVEL9_NAMES; break;
    case 10: list = ELECTRONICS_TECH_LEVEL10_NAMES; break;
    default: return null;
    }
    for (int i=0;i<list.length;i++) {
      String techName = list[i];
      if (name.equals(techName)) {
        Tech tech = new Tech(techName, TechType.Electrics, level);
        if (techName.startsWith("Planetary scanner Mk")) {
          tech.setImprovement(techName);
        } else {
          tech.setComponent(techName);
        }
        tech.setIcon(Icons.getIconByName(Icons.ICON_ELECTRONICS_TECH));
        return tech;
      }
    }
    return null;
  }

  /**
   * Create random tech by tech type and level, but not choose those tech
   * player already has
   * @param type Tech Type: Combat, Defense, Hulls, Improvements, Propulsion and Electrics
   * @param level Tech Level 1-10
   * @param alreadyHas List of tech player has
   * @return Tech or null if cannot find new
   */
  public static Tech createRandomTech(TechType type, int level, Tech[] alreadyHas) {
    String[] alreadyHasList = new String[alreadyHas.length];
    for (int i = 0;i<alreadyHasList.length;i++) {
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
      int index = DiceGenerator.getRandom(choices.size()-1);
      switch (type) {
      case Combat: return createCombatTech(choices.get(index), level);
      case Defense: return createDefenseTech(choices.get(index), level);
      case Hulls: return createHullTech(choices.get(index), level);
      case Improvements: return createImprovementTech(choices.get(index), level);
      case Propulsion: return createPropulsionTech(choices.get(index), level);
      case Electrics: return createElectronicsTech(choices.get(index), level);
      }
    }
    return null;
  }

  /**
   * Get tech level cost as research points
   * @param level Level to research
   * @return Amount of research points required
   */
  public static int getTechCost(int level) {
    switch (level) {
    case 1: return 5;
    case 2: return 7;
    case 3: return 10;
    case 4: return 15;
    case 5: return 22;
    case 6: return 33;
    case 7: return 50;
    case 8: return 75;
    case 9: return 112;
    case 10: return 168;
    }
    return 200;
  }
  
  /**
   * Get String list of tech by type and level
   * @param type
   * @param level
   * @return String array of tech or empty array
   */
  public static String[] getListByTechLevel(TechType type,int level) {
    switch (type) {
    case Combat: 
      switch(level) {
      case 1 : return COMBAT_TECH_LEVEL1_NAMES;
      case 2 : return COMBAT_TECH_LEVEL2_NAMES;
      case 3 : return COMBAT_TECH_LEVEL3_NAMES;
      case 4 : return COMBAT_TECH_LEVEL4_NAMES;
      case 5 : return COMBAT_TECH_LEVEL5_NAMES;
      case 6 : return COMBAT_TECH_LEVEL6_NAMES;
      case 7 : return COMBAT_TECH_LEVEL7_NAMES;
      case 8 : return COMBAT_TECH_LEVEL8_NAMES;
      case 9 : return COMBAT_TECH_LEVEL9_NAMES;
      case 10: return COMBAT_TECH_LEVEL10_NAMES;
      }
      break;
    case Defense: 
      switch(level) {
      case 1 : return DEFENSE_TECH_LEVEL1_NAMES;
      case 2 : return DEFENSE_TECH_LEVEL2_NAMES;
      case 3 : return DEFENSE_TECH_LEVEL3_NAMES;
      case 4 : return DEFENSE_TECH_LEVEL4_NAMES;
      case 5 : return DEFENSE_TECH_LEVEL5_NAMES;
      case 6 : return DEFENSE_TECH_LEVEL6_NAMES;
      case 7 : return DEFENSE_TECH_LEVEL7_NAMES;
      case 8 : return DEFENSE_TECH_LEVEL8_NAMES;
      case 9 : return DEFENSE_TECH_LEVEL9_NAMES;
      case 10: return DEFENSE_TECH_LEVEL10_NAMES;
      }
      break;
    case Hulls: 
      switch(level) {
      case 1 : return HULL_TECH_LEVEL1_NAMES;
      case 2 : return HULL_TECH_LEVEL2_NAMES;
      case 3 : return HULL_TECH_LEVEL3_NAMES;
      case 4 : return HULL_TECH_LEVEL4_NAMES;
      case 5 : return HULL_TECH_LEVEL5_NAMES;
      case 6 : return HULL_TECH_LEVEL6_NAMES;
      case 7 : return HULL_TECH_LEVEL7_NAMES;
      case 8 : return HULL_TECH_LEVEL8_NAMES;
      case 9 : return HULL_TECH_LEVEL9_NAMES;
      case 10: return HULL_TECH_LEVEL10_NAMES;
      }
      break;
    case Improvements: 
      switch(level) {
      case 1 : return IMPROVEMENT_TECH_LEVEL1_NAMES;
      case 2 : return IMPROVEMENT_TECH_LEVEL2_NAMES;
      case 3 : return IMPROVEMENT_TECH_LEVEL3_NAMES;
      case 4 : return IMPROVEMENT_TECH_LEVEL4_NAMES;
      case 5 : return IMPROVEMENT_TECH_LEVEL5_NAMES;
      case 6 : return IMPROVEMENT_TECH_LEVEL6_NAMES;
      case 7 : return IMPROVEMENT_TECH_LEVEL7_NAMES;
      case 8 : return IMPROVEMENT_TECH_LEVEL8_NAMES;
      case 9 : return IMPROVEMENT_TECH_LEVEL9_NAMES;
      case 10: return IMPROVEMENT_TECH_LEVEL10_NAMES;
      }
      break;
    case Propulsion: 
      switch(level) {
      case 1 : return PROPULSION_TECH_LEVEL1_NAMES;
      case 2 : return PROPULSION_TECH_LEVEL2_NAMES;
      case 3 : return PROPULSION_TECH_LEVEL3_NAMES;
      case 4 : return PROPULSION_TECH_LEVEL4_NAMES;
      case 5 : return PROPULSION_TECH_LEVEL5_NAMES;
      case 6 : return PROPULSION_TECH_LEVEL6_NAMES;
      case 7 : return PROPULSION_TECH_LEVEL7_NAMES;
      case 8 : return PROPULSION_TECH_LEVEL8_NAMES;
      case 9 : return PROPULSION_TECH_LEVEL9_NAMES;
      case 10: return PROPULSION_TECH_LEVEL10_NAMES;
      }
      break;
    case Electrics: 
      switch(level) {
      case 1 : return ELECTRONICS_TECH_LEVEL1_NAMES;
      case 2 : return ELECTRONICS_TECH_LEVEL2_NAMES;
      case 3 : return ELECTRONICS_TECH_LEVEL3_NAMES;
      case 4 : return ELECTRONICS_TECH_LEVEL4_NAMES;
      case 5 : return ELECTRONICS_TECH_LEVEL5_NAMES;
      case 6 : return ELECTRONICS_TECH_LEVEL6_NAMES;
      case 7 : return ELECTRONICS_TECH_LEVEL7_NAMES;
      case 8 : return ELECTRONICS_TECH_LEVEL8_NAMES;
      case 9 : return ELECTRONICS_TECH_LEVEL9_NAMES;
      case 10: return ELECTRONICS_TECH_LEVEL10_NAMES;
      }
      break;
    }
    return new String[0];
  }
  
  
}
