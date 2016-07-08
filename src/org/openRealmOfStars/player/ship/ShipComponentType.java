package org.openRealmOfStars.player.ship;

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
   * Photon torpedo, Torpede weapons, first damages shield, armor and finally hull
   * Armor only gives 50% defense
   */
  WEAPON_PHOTON_TORPEDO,
  /**
   * Damages only shields, but very effective against them.
   */
  WEAPON_ECM_TORPEDO,
  /**
   * High Explosive Missile causing first armor damage, shields and finally hull.
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
  ARMOR;
  
}
