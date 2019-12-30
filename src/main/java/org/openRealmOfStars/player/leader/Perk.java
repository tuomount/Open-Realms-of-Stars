package org.openRealmOfStars.player.leader;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019  Tuomo Untinen
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
* Leader perk enumeration
*
*/
public enum Perk {

  /**
   * Ruler perk for militaristic
   */
  MILITARISTIC(0, "Militaristic", "+1 Fleet capacity when leader is ruler."),
  /**
   * Ruler perk for Warlord
   */
  WARLORD(1, "Warlord", "+1 war resistance when leader is ruler."),
  /**
   * Ruler perk for charismatic. All diplomatic trades get +1.
   */
  CHARISMATIC(2, "Charismatic", "+1 Diplomacy bonus when leader is ruler."),
  /**
   * Governor perk for agricultural.
   */
  AGRICULTURAL(3, "Agricultural",
      "+1 Food production when leader is governor"),
  /**
   * Governor perk for mining.
   */
  MINER(4, "Miner", "+1 Metal production when leader is governor"),
  /**
   * Governor perk for industrial
   */
  INDUSTRIAL(5, "Industrial",
      "+1 Production production when leader is governor"),
  /**
   * Ruler/Governor perk for scientist.
   */
  SCIENTIST(6, "Scientist",
      "+1 Research production when leader is governor or ruler"),
  /**
   * Governor perk for culture
   */
  ARTISTIC(7, "Artistic", "+1 Culture production when leader is governor"),
  /**
   * Ruler/Governor perk for credit
   */
  MERCHANT(8, "Merchant",
      "+1 Credit prodcution when leader is governor or ruler"),
  /**
   * Governor/Fleet commander perk for increasing troop power
   */
  DISCIPLINE(9, "Discipline",
      "+25% troop power when leader is governor or fleet commander"),
  /**
   * Governor/ruler perk for happiness
   */
  GOOD_LEADER(10, "Good leader",
      "+1 Happiness when leader is governor or ruler"),
  /**
   * Fleet commander perk for regular speed
   */
  EXPLORER(11, "Explorer", "+1 Speed when leader is fleet commander"),
  /**
   * Fleet commander perk for FTL speed
   */
  FTL_ENGINEER(12, "FTL Engineer",
      "+1 FTL Speed when leader is fleet commander"),
  /**
   * Fleet commander perk for combat initiative.
   */
  COMBAT_TACTICIAN(13, "Combat tactician",
      "+1 Combat initiative when leader is fleet commander"),
  /**
   * Fleet commander perk for beam weapon damage
   */
  BEAM_WEAPON_MASTER(14, "Beam weapon master",
      "+1 Beam weapon damage when leader is fleet commander"),
  /**
   * Fleet commander perk for missile weapon damage
   */
  MISSILE_WEAPON_MASTER(15, "Missile weapon master",
      "+1 Missile weapon damage when leader is fleet commander"),
  /**
   * Fleet commander perk for photon torpedo damage
   */
  PHOTON_TORPEDO_WEAPON_MASTER(16, "Photon torpedo weapon master",
      "+1 Photon torpedo damage when leader is fleet commander"),
  /**
   * Fleet commander perk for massdrive weapon damage
   */
  MASSDRIVE_WEAPON_MASTER(17, "Massdrive weapon master",
      "+1 Massdrive damage when leader is fleet commander"),
  /**
   * Fleet commander perk for improving shield power.
   */
  SHIELD_MASTER(18, "Shield master",
      "+1 Shield value when leader is fleet commander"),
  /**
   * Fleet commander perk for improving armor power.
   */
  ARMOR_MASTER(19, "Armor master",
      "+1 Armor value when leader is fleet commander"),
  /**
   * Fleet commander perk for improving espionage.
   */
  SPY_MASTER(20, "Spy master",
      "+1 Espionage value when leader is fleet commander"),
  /**
   * Fleet commander perk for improving cloacking value
   */
  SECRET_AGENT(21, "Secret agent",
      "+5 Cloacking value when leader is fleet commander"),
  /**
   * Fleet commander perk for better trading
   */
  TRADER(22, "Trader",
      "+1 extra credit from trading when leader is fleet commander");





  /**
   * Constructor of perk enumeration
   * @param index Index number
   * @param name Perk name
   * @param desc Perk description
   */
  Perk(final int index, final String name, final String desc) {
    this.index = index;
    this.name = name;
    this.description = desc;
  }

  /**
   * Is perk ruler perk or something else
   * @return True if ruler perk
   */
  public boolean isRulerPerk() {
    if (this == Perk.MILITARISTIC
        || this == Perk.WARLORD
        || this == Perk.CHARISMATIC
        || this == Perk.SCIENTIST
        || this == Perk.MERCHANT
        || this == Perk.GOOD_LEADER) {
      return true;
    }
    return false;
  }
  /**
   * Is perk governor perk or something else
   * @return True if governor perk
   */
  public boolean isGovernorPerk() {
    if (this == Perk.AGRICULTURAL
        || this == Perk.MINER
        || this == Perk.INDUSTRIAL
        || this == Perk.SCIENTIST
        || this == Perk.ARTISTIC
        || this == Perk.MERCHANT
        || this == Perk.DISCIPLINE
        || this == Perk.GOOD_LEADER) {
      return true;
    }
    return false;
  }
  /**
   * Is perk fleet commander perk or something else
   * @return True if fleet commander perk
   */
  public boolean isFleetCommadnerPerk() {
    if (this == Perk.DISCIPLINE
        || this == Perk.EXPLORER
        || this == Perk.FTL_ENGINEER
        || this == Perk.COMBAT_TACTICIAN
        || this == Perk.BEAM_WEAPON_MASTER
        || this == Perk.MISSILE_WEAPON_MASTER
        || this == Perk.PHOTON_TORPEDO_WEAPON_MASTER
        || this == Perk.MASSDRIVE_WEAPON_MASTER
        || this == Perk.SHIELD_MASTER
        || this == Perk.ARMOR_MASTER
        || this == Perk.SPY_MASTER
        || this == Perk.SECRET_AGENT
        || this == Perk.TRADER) {
      return true;
    }
    return false;
  }
  /**
   * Perk Index.
   */
  private int index;
  /**
   * Description about the perk
   */
  private String description;
  /**
   * Perk name
   */
  private String name;
  /**
   * Get the Perk index
   * @return the index
   */
  public int getIndex() {
    return index;
  }
  /**
   * Get the perk description.
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get perk name as string
   * @return the name
   */
  public String getName() {
    return name;
  }
}
