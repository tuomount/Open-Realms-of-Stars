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
   * Current maximum ShipComponent for whole game.
   * Remember to increase this when new ship hull is added to game.
   * It should be one bigger than last index.
   */
  private static final int MAX_SHIPCOMPONENT = 13;
  

  /**
   * Create ShipComponent with matching name
   * @param name Ship component name
   * @return ShipComponent or null if not found
   */
  public static ShipComponent createByName(String name) {
    ShipComponent tmp = null;
    for (int i=0;i<MAX_SHIPCOMPONENT;i++) {
      tmp = create(i);
      if ((tmp != null) && (tmp.getName().equalsIgnoreCase(name))) {
        return tmp;
      }
    }
    return null;
  }

  /**
   * Create Ship component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  public static ShipComponent create(int index) {
    ShipComponent tmp = null;
    switch (index) {
    case 0: tmp = createEngine(index); break; // Ion drive Mk1
    case 1: tmp = createWeapon(index); break; // Laser Mk1
    case 2: tmp = createWeapon(index); break; // RailGun Mk1
    case 3: tmp = createWeapon(index); break; // Photon torpedo Mk1
    case 4: tmp = createDefense(index); break; // Shield Mk1
    case 5: tmp = createDefense(index); break; // Armor Mk1
    case 6: tmp = createElectronics(index); break; // Scanner Mk1
    case 7: tmp = createElectronics(index); break; // Cloaking device Mk1
    case 8: tmp = createElectronics(index); break; // Colony module
    case 9: tmp = createElectronics(index); break; // Fission Source Mk1
    case 10: tmp = createElectronics(index); break; // Fission Source Mk2
    case 11: tmp = createEngine(index); break; // Ion drive Mk2
    case 12: tmp = createEngine(index); break; // Nuclear drive Mk1
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
      tmp = new ShipComponent(index, "Ion drive Mk1", 5, 2, ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(3);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == 11) {
      tmp = new ShipComponent(index, "Ion drive Mk2", 6, 2, ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(4);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == 12) {
      tmp = new ShipComponent(index, "Nuclear drive Mk1", 4, 4, ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(2);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(1);
    }
    return tmp;
    
  }

  /**
   * Create Engine component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  private static ShipComponent createElectronics(int index) {
    ShipComponent tmp = null;
    if (index == 6) {
      tmp = new ShipComponent(index, "Scanner Mk1", 2, 2, ShipComponentType.SCANNER);
      tmp.setScannerRange(2);
      tmp.setCloakDetection(20);
      tmp.setEnergyRequirement(1);
    }
    if (index == 7) {
      tmp = new ShipComponent(index, "Cloaking device Mk1", 2, 2, ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(20);
      tmp.setEnergyRequirement(1);
    }
    if (index == 8) {
      tmp = new ShipComponent(index, "Colony Module", 2, 8, ShipComponentType.COLONY_MODULE);
      tmp.setEnergyRequirement(1);
    }
    if (index == 9) {
      tmp = new ShipComponent(index, "Fission source Mk1", 3, 3, ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(4);
    }
    if (index == 10) {
      tmp = new ShipComponent(index, "Fission source Mk2", 3, 4, ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(5);
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
      tmp = new ShipComponent(index, "Photon torpedo Mk1", 6, 4, ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(1);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(1);
    }
    return tmp;
    
  }

  /**
   * Create Defense component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  private static ShipComponent createDefense(int index) {
    ShipComponent tmp = null;
    if (index == 4) {
      tmp = new ShipComponent(index, "Shield Mk1", 5, 1, ShipComponentType.SHIELD);
      tmp.setDefenseValue(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == 5) {
      tmp = new ShipComponent(index, "Armor Mk1", 2, 4, ShipComponentType.ARMOR);
      tmp.setDefenseValue(1);
    }
    return tmp;
    
  }

}
