package org.openRealmOfStars.player.ship;

import org.openRealmOfStars.gui.icons.Icons;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018, 2020-2022 Tuomo Untinen
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
 * Ship component type
 *
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
   * Callisto is a multicannon and it has been defined as a hybrid weapon.
   * Callisto is between railgun and missiles.
   */
  CALLISTO_MULTICANNON,
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
  SPACE_FIN;

  /**
   * Get ShipComponentType index
   * @return int
   */
  public int getIndex() {
    switch (this) {
    case WEAPON_BEAM:
      return 0;
    case WEAPON_RAILGUN:
      return 1;
    case WEAPON_PHOTON_TORPEDO:
      return 2;
    case WEAPON_ECM_TORPEDO:
      return 3;
    case WEAPON_HE_MISSILE:
      return 4;
    case SCANNER:
      return 5;
    case SHIELD:
      return 6;
    case ARMOR:
      return 7;
    case SHIELD_GENERATOR:
      return 8;
    case ENGINE:
      return 9;
    case POWERSOURCE:
      return 10;
    case CLOAKING_DEVICE:
      return 11;
    case JAMMER:
      return 12;
    case TARGETING_COMPUTER:
      return 13;
    case PLANETARY_INVASION_MODULE:
      return 14;
    case COLONY_MODULE:
      return 15;
    case PRIVATEERING_MODULE:
      return 16;
    case ORBITAL_BOMBS:
      return 17;
    case ORBITAL_NUKE:
      return 18;
    case STARBASE_COMPONENT:
      return 19;
    case ESPIONAGE_MODULE:
      return 20;
    case THRUSTERS:
      return 21;
    case FIGHTER_BAY:
      return 22;
    case PLASMA_BEAM:
      return 23;
    case DISTORTION_SHIELD:
      return 24;
    case SOLAR_ARMOR:
      return 25;
    case ORGANIC_ARMOR:
      return 26;
    case TRACTOR_BEAM:
      return 27;
    case PLASMA_CANNON:
      return 28;
    case ION_CANNON:
      return 29;
    case CALLISTO_MULTICANNON:
      return 30;
    case BITE:
      return 31;
    case TENTACLE:
      return 32;
    case HEART:
      return 33;
    case SPACE_FIN:
      return 34;
    default:
      return 0;
    }
  }

  /**
   * Return ShipComponentType by index
   * @param index This must be between 0-
   * @return Ship component type
   */
  public static ShipComponentType getTypeByIndex(final int index) {
    switch (index) {
    case 0:
      return ShipComponentType.WEAPON_BEAM;
    case 1:
      return ShipComponentType.WEAPON_RAILGUN;
    case 2:
      return ShipComponentType.WEAPON_PHOTON_TORPEDO;
    case 3:
      return ShipComponentType.WEAPON_ECM_TORPEDO;
    case 4:
      return ShipComponentType.WEAPON_HE_MISSILE;
    case 5:
      return ShipComponentType.SCANNER;
    case 6:
      return ShipComponentType.SHIELD;
    case 7:
      return ShipComponentType.ARMOR;
    case 8:
      return ShipComponentType.SHIELD_GENERATOR;
    case 9:
      return ShipComponentType.ENGINE;
    case 10:
      return ShipComponentType.POWERSOURCE;
    case 11:
      return ShipComponentType.CLOAKING_DEVICE;
    case 12:
      return ShipComponentType.JAMMER;
    case 13:
      return ShipComponentType.TARGETING_COMPUTER;
    case 14:
      return ShipComponentType.PLANETARY_INVASION_MODULE;
    case 15:
      return ShipComponentType.COLONY_MODULE;
    case 16:
      return ShipComponentType.PRIVATEERING_MODULE;
    case 17:
      return ShipComponentType.ORBITAL_BOMBS;
    case 18:
      return ShipComponentType.ORBITAL_NUKE;
    case 19:
      return ShipComponentType.STARBASE_COMPONENT;
    case 20:
      return ShipComponentType.ESPIONAGE_MODULE;
    case 21:
      return ShipComponentType.THRUSTERS;
    case 22:
      return ShipComponentType.FIGHTER_BAY;
    case 23:
      return ShipComponentType.PLASMA_BEAM;
    case 24:
      return ShipComponentType.DISTORTION_SHIELD;
    case 25:
      return ShipComponentType.SOLAR_ARMOR;
    case 26:
      return ShipComponentType.ORGANIC_ARMOR;
    case 27:
      return ShipComponentType.TRACTOR_BEAM;
    case 28:
      return ShipComponentType.PLASMA_CANNON;
    case 29:
      return ShipComponentType.ION_CANNON;
    case 30:
      return ShipComponentType.CALLISTO_MULTICANNON;
    case 31:
      return ShipComponentType.BITE;
    case 32:
      return ShipComponentType.TENTACLE;
    case 33:
      return ShipComponentType.HEART;
    case 34:
      return ShipComponentType.SPACE_FIN;
    default:
      return ShipComponentType.WEAPON_BEAM;
    }
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
    case ARMOR:
      return "Armor";
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
    case CALLISTO_MULTICANNON:
      return "Callisto multicannon";
    case BITE:
      return "Bite";
    case TENTACLE:
      return "Tentacle";
    case HEART:
      return "Heart";
    case SPACE_FIN:
      return "Space fin";
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
    case ARMOR:
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
    case CALLISTO_MULTICANNON:
      return Icons.ICON_MULTI_CANNON;
    case BITE:
      return Icons.ICON_MOUTH;
    case TENTACLE:
      return Icons.ICON_TENTACLE;
    case HEART:
      return Icons.ICON_HEART;
    case SPACE_FIN:
      return Icons.ICON_SPACE_FIN;
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
        return 100;
      case WEAPON_RAILGUN:
      case WEAPON_PHOTON_TORPEDO:
      case PLASMA_CANNON:
      case ION_CANNON:
      case TENTACLE:
          return 75;
      case BITE:
      case CALLISTO_MULTICANNON:
          return 60;
      case WEAPON_ECM_TORPEDO:
      case WEAPON_HE_MISSILE:
      default:
          return 50;
      }
  }
}
