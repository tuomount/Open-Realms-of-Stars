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
        Tech tech = new Tech(techName, TechType.Combat, level);
        if (techName.startsWith("Planetary Defense Turret Mk")) {
          tech.setImprovement(techName);
        } else {
          tech.setComponent(techName);
        }
        return tech;
      }
    }
    return null;
  }

}
