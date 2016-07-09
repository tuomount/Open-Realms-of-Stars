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
 * Ship component factory
 * 
 */

public class ShipComponentFactory {

  /**
   * Create Ship component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  public static ShipComponent create(int index) {
    ShipComponent tmp = null;
    switch (index) {
    case 0: tmp = createEngine(index); break; // Ion Drive Mk1
    case 1: tmp = createWeapon(index); break; // Laser Mk1
    case 2: tmp = createWeapon(index); break; // RailGun Mk1
    case 3: tmp = createWeapon(index); break; // Photon torpedo Mk1
    }
    return tmp;
  }
  
  /**
   * Create Engine component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  private static ShipComponent createEngine(int index) {
    ShipComponent tmp = null;
    if (index == 0) {
      tmp = new ShipComponent(index, "Ion Drive Mk1", 5, 2, ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(3);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(1);
    }
    return tmp;
    
  }
  
  /**
   * Create Weapon component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  private static ShipComponent createWeapon(int index) {
    ShipComponent tmp = null;
    if (index == 1) {
      tmp = new ShipComponent(index, "Laser Mk1", 6, 3, ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(1);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == 2) {
      tmp = new ShipComponent(index, "Railgun Mk1", 4, 4, ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(1);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == 3) {
      tmp = new ShipComponent(index, "Photon torpedo", 6, 4, ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(1);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(1);
    }
    return tmp;
    
  }

}
