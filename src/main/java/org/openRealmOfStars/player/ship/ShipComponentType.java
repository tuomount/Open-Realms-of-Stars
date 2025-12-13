package org.openRealmOfStars.player.ship;
/*
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
 */

import org.openRealmOfStars.gui.icons.Icons;

/**
 * Ship component type
 */
public enum ShipComponentType {
  /**
   * Beam weapon, causing beam damage first shields, armor and finally hull
   * Armor only gives 50% defense
   */
  WEAPON_BEAM,
  /**
   * Railguns, causing first armor damage, shields and finally hull
   * Shield only gives 50% defense
   */
  WEAPON_RAILGUN,
  /**
   * Photon torpedo, Torpede weapons, first damages shield, armor and finally
   * hull.
   * Armor only gives 50% defense
   */
  WEAPON_PHOTON_TORPEDO,
  /**
   * Damages only shields, but very effective against them.
   */
  WEAPON_ECM_TORPEDO,
  /**
   * High Explosive Missile causing first armor damage, shields and finally
   * hull.
   * Shield only gives 50% defense
   */
  WEAPON_HE_MISSILE,
  /**
   * Scanner for detecting enemies
   */
  SCANNER,
  /**
   * Shield defense component
   */
  SHIELD,
  /**
   * Armor defense component
   */
  ARMOR,
  /**
   * Shield Generator
   */
  SHIELD_GENERATOR,
  /**
   * Propulsion Engine
   */
  ENGINE,
  /**
   * Powersource
   */
  POWERSOURCE,
  /**
   * Cloaking device
   */
  CLOAKING_DEVICE,
  /**
   * Jammer against missile
   */
  JAMMER,
  /**
   * Targeting computer
   */
  TARGETING_COMPUTER,
  /**
   * Planetary invasion module
   */
  PLANETARY_INVASION_MODULE,
  /**
   * COLONY_MODULE
   */
  COLONY_MODULE,
  /**
   * Privateering module
   */
  PRIVATEERING_MODULE,
  /**
   * Orbital bombs
   */
  ORBITAL_BOMBS,
  /**
   * Orbital Nuke
   */
  ORBITAL_NUKE,
  /**
   * Component for starbases only
   */
  STARBASE_COMPONENT,
  /**
   * Espionage module for spying other realms
   */
  ESPIONAGE_MODULE,
  /**
   * Combat thrusters
   */
  THRUSTERS,
  /**
   * Fighter bay for small and medium ships
   */
  FIGHTER_BAY,
  /**
   * Plasma beam always damages something,
   * first shields, then armor and finally hull
   */
  PLASMA_BEAM,
  /**
   * Distortion shield, acts as shield and jammer.
   */
  DISTORTION_SHIELD,
  /**
   * Solar armor, which acts as armor and gives energy
   */
  SOLAR_ARMOR,
  /**
   * Armor that makes armor regenerate one point per turn.
   */
  ORGANIC_ARMOR,
  /**
   * Tractor beam to pull ship one closer.
   */
  TRACTOR_BEAM,
  /**
   * Plasma cannon to make always damage.
   */
  PLASMA_CANNON,
  /**
   * Ion cannon to make destroy shields and hull.
   */
  ION_CANNON,
   /**
   * Multicannon is between railgun and missiles.
   * It's good weapon for small ship since it does not require
   * energy.
   */
  MULTICANNON,
  /**
   * Bite is space monster's bite attack.
   */
  BITE,
  /**
   * Tentacle is space monster's tentacle attack.
   */
  TENTACLE,
  /**
   * Basically same as power source just different icon.
   */
  HEART,
  /**
   * Basically same as engine just different icon.
   */
  SPACE_FIN,
  /**
   * Shield component that protects againts tractor beam
   * and ascension portals.
   */
  MULTIDIMENSION_SHIELD,
  /**
   * Alternates gravity in small area that it rips material.
   * Real effective against armor and shield.
   * When used against black hole, ascension portal becomes visible.
   */
  GRAVITY_RIPPER,
  /**
   * Repair module that will repair one hull point and armor point when
   * overloaded during combat. Also fixes ship faster outside of combat.
   */
  REPAIR_MODULE,
  /**
   * SPORE_MODULE for spore colonization.
   */
  SPORE_MODULE,
  /**
   * Arm spike basically same as tentacle but different sound and graphics
   */
  ARM_SPIKE,
  /**
   * Plasma spit functions as plasma cannot expect looks like photon torpedo.
   */
  PLASMA_SPIT,
  /**
   * Armor component that also adds cloaking.
   */
  SHADOW_ARMOR,
  /**
   * Shield defense component that also adds cloaking
   */
  SHADOW_SHIELD;



  /**
   * Get ShipComponentType index
   * @return int
   */
  public int getIndex() {
    return this.ordinal();
  }

  /**
   * Return ShipComponentType by index
   * @param index This must be between 0-
   * @return Ship component type
   */
  public static ShipComponentType getTypeByIndex(final int index) {
    if (index >= 0 && index < values().length) {
      return values()[index];
    }
    throw new IllegalArgumentException("Unknown Ship Component type");
  }

  @Override
  public String toString() {
    switch (this) {
    case WEAPON_BEAM:
      return "Beam weapon";
    case WEAPON_RAILGUN:
      return "Railgun";
    case WEAPON_PHOTON_TORPEDO:
      return "Photon torpedo";
    case WEAPON_ECM_TORPEDO:
      return "ECM torpedo";
    case WEAPON_HE_MISSILE:
      return "HE missile";
    case SCANNER:
      return "Scanner";
    case SHIELD:
      return "Shield";
    case SHADOW_SHIELD:
      return "Shadow shield";
    case ARMOR:
      return "Armor";
    case SHADOW_ARMOR:
      return "Shadow armor";
    case SHIELD_GENERATOR:
      return "Shield generator";
    case ENGINE:
      return "Engine";
    case POWERSOURCE:
      return "Powersource";
    case CLOAKING_DEVICE:
      return "Cloaking device";
    case JAMMER:
      return "Jammer";
    case TARGETING_COMPUTER:
      return "Targeting computer";
    case PLANETARY_INVASION_MODULE:
      return "Planetary invasion module";
    case COLONY_MODULE:
      return "Colony module";
    case PRIVATEERING_MODULE:
      return "Privateering module";
    case ORBITAL_BOMBS:
      return "Orbital bombs";
    case ORBITAL_NUKE:
      return "Orbital nuke";
    case STARBASE_COMPONENT:
      return "Starbase module";
    case ESPIONAGE_MODULE:
      return "Espionage module";
    case THRUSTERS:
      return "Thrusters";
    case FIGHTER_BAY:
      return "Fighter bay";
    case PLASMA_BEAM:
      return "Plasma beam";
    case DISTORTION_SHIELD:
      return "Distortion shield";
    case SOLAR_ARMOR:
      return "Solar armor";
    case ORGANIC_ARMOR:
      return "Organic armor";
    case TRACTOR_BEAM:
      return "Tractor beam";
    case PLASMA_CANNON:
      return "Plasma cannon";
    case ION_CANNON:
      return "Ion cannon";
    case MULTICANNON:
      return "Multicannon";
    case BITE:
      return "Bite";
    case TENTACLE:
      return "Tentacle";
    case HEART:
      return "Heart";
    case SPACE_FIN:
      return "Space fin";
    case MULTIDIMENSION_SHIELD:
      return "Multi-dimension shield";
    case GRAVITY_RIPPER:
      return "Gravity ripper";
    case REPAIR_MODULE:
      return "Repair module";
    case SPORE_MODULE:
      return "Spore module";
    case ARM_SPIKE:
      return "Arm spike";
    case PLASMA_SPIT:
      return "Plasma spit";
    default:
      return "Error - Unknown";
    }
  }

  /**
   * Get Icon for component
   * @return String
   */
  public String getIconName() {
    switch (this) {
    case WEAPON_BEAM:
      return Icons.ICON_LASERGUN;
    case WEAPON_RAILGUN:
      return Icons.ICON_COMBAT_TECH;
    case WEAPON_PHOTON_TORPEDO:
      return Icons.ICON_PHOTON_TORPEDO;
    case WEAPON_ECM_TORPEDO:
      return Icons.ICON_MISSILE;
    case WEAPON_HE_MISSILE:
      return Icons.ICON_MISSILE;
    case SCANNER:
      return Icons.ICON_SCANNER;
    case SHIELD:
      return Icons.ICON_SHIELD;
    case SHADOW_SHIELD:
      //TODO: Change icon
      return Icons.ICON_SHIELD;
    case ARMOR:
      return Icons.ICON_ARMOR;
    case SHADOW_ARMOR:
      //TODO: Change icon
      return Icons.ICON_ARMOR;
    case SHIELD_GENERATOR:
      return Icons.ICON_CIRCUIT_BOARD;
    case ENGINE:
      return Icons.ICON_PROPULSION_TECH;
    case POWERSOURCE:
      return Icons.ICON_POWERSOURCE;
    case CLOAKING_DEVICE:
      return Icons.ICON_CLOACKING_DEVICE;
    case JAMMER:
      return Icons.ICON_CIRCUIT_BOARD;
    case TARGETING_COMPUTER:
      return Icons.ICON_CIRCUIT_BOARD;
    case PLANETARY_INVASION_MODULE:
      return Icons.ICON_TROOPS;
    case COLONY_MODULE:
      return Icons.ICON_PEOPLE;
    case PRIVATEERING_MODULE:
      return Icons.ICON_DEATH;
    case ORBITAL_BOMBS:
      return Icons.ICON_BOMB;
    case ORBITAL_NUKE:
      return Icons.ICON_NUKE;
    case STARBASE_COMPONENT:
      return Icons.ICON_STARBASE;
    case ESPIONAGE_MODULE:
      return Icons.ICON_CIRCUIT_BOARD;
    case THRUSTERS:
      return Icons.ICON_PROPULSION_TECH;
    case FIGHTER_BAY:
      return Icons.ICON_HULL_TECH;
    case PLASMA_BEAM:
      return Icons.ICON_LASERGUN;
    case DISTORTION_SHIELD:
      return Icons.ICON_DISTORTION_SHIELD;
    case SOLAR_ARMOR:
      return Icons.ICON_SOLAR_ARMOR;
    case ORGANIC_ARMOR:
      return Icons.ICON_ORGANIC_ARMOR;
    case TRACTOR_BEAM:
      return Icons.ICON_TRACTOR_BEAM;
    case PLASMA_CANNON:
      return Icons.ICON_PLASMA_CANNON;
    case ION_CANNON:
      return Icons.ICON_ION_CANNON;
    case MULTICANNON:
      return Icons.ICON_MULTI_CANNON;
    case BITE:
      return Icons.ICON_MOUTH;
    case TENTACLE:
      return Icons.ICON_TENTACLE;
    case HEART:
      return Icons.ICON_HEART;
    case SPACE_FIN:
      return Icons.ICON_SPACE_FIN;
    case MULTIDIMENSION_SHIELD:
      return Icons.ICON_MULTIDIMENSION_SHIELD;
    case GRAVITY_RIPPER:
      return Icons.ICON_GRAVITY_RIPPER;
    case REPAIR_MODULE:
      return Icons.ICON_WRENCH;
    case ARM_SPIKE:
      return Icons.ICON_ARM_SPIKE;
    case PLASMA_SPIT:
      return Icons.ICON_PLASMA_SPIT;
    default:
      return Icons.ICON_CIRCUIT_BOARD;
    }
  }

  /**
   * Get hitChance for weapon
   * @return hitChance for weapon
   */
  public int getHitChance() {
      switch (this) {
      case WEAPON_BEAM:
      case PRIVATEERING_MODULE:
      case TRACTOR_BEAM:
          return 100;
      case PLASMA_BEAM:
      case GRAVITY_RIPPER:
        return 100;
      case WEAPON_RAILGUN:
      case WEAPON_PHOTON_TORPEDO:
      case PLASMA_CANNON:
      case ION_CANNON:
      case ARM_SPIKE:
      case TENTACLE:
      case PLASMA_SPIT:
          return 75;
      case BITE:
      case MULTICANNON:
          return 60;
      case WEAPON_ECM_TORPEDO:
      case WEAPON_HE_MISSILE:
      default:
          return 50;
      }
  }
}
