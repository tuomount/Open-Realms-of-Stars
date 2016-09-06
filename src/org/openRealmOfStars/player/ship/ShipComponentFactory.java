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
  private static final int MAX_SHIPCOMPONENT = 91;
  

  /**
   * Create ShipComponent with matching name
   * @param name Ship component name
   * @return ShipComponent or null if not found
   */
  public static ShipComponent createByName(String name) {
    ShipComponent tmp = null;
    if (name != null) {
      for (int i=0;i<MAX_SHIPCOMPONENT;i++) {
        tmp = create(i);
        if ((tmp != null) && (tmp.getName().equalsIgnoreCase(name))) {
          return tmp;
        }
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
    case 13: tmp = createWeapon(index); break; // Laser Mk2
    case 14: tmp = createWeapon(index); break; // RailGun Mk2
    case 15: tmp = createWeapon(index); break; // Photon torpedo Mk2
    case 16: tmp = createElectronics(index); break; // Planetary invasion module
    case 17: tmp = createDefense(index); break; // Shield Mk2
    case 18: tmp = createDefense(index); break; // Armor Mk2
    case 19: tmp = createElectronics(index); break; // Cloaking device Mk2
    case 20: tmp = createElectronics(index); break; // Targeting computer Mk1
    case 21: tmp = createEngine(index); break; // Ion drive Mk3
    case 22: tmp = createEngine(index); break; // Hyper drive Mk1
    case 23: tmp = createElectronics(index); break; // Fusion source Mk1
    case 24: tmp = createWeapon(index); break; // Laser Mk3
    case 25: tmp = createWeapon(index); break; // RailGun Mk3
    case 26: tmp = createWeapon(index); break; // Photon torpedo Mk3
    case 27: tmp = createWeapon(index); break; // ECM torpedo Mk1
    case 28: tmp = createWeapon(index); break; // HE missile Mk1
    case 29: tmp = createDefense(index); break; // Shield Mk3
    case 30: tmp = createDefense(index); break; // Armor Mk3
    case 31: tmp = createElectronics(index); break; // Scanner Mk2
    case 32: tmp = createElectronics(index); break; // Jammer Mk1
    case 33: tmp = createEngine(index); break; // Warp drive Mk1
    case 34: tmp = createEngine(index); break; // Nuclear drive Mk2
    case 35: tmp = createElectronics(index); break; // Fusion source Mk2
    case 36: tmp = createWeapon(index); break; // Laser Mk4
    case 37: tmp = createWeapon(index); break; // RailGun Mk4
    case 38: tmp = createWeapon(index); break; // Photon torpedo Mk4
    case 39: tmp = createWeapon(index); break; // ECM torpedo Mk2
    case 40: tmp = createWeapon(index); break; // HE missile Mk2
    case 41: tmp = createDefense(index); break; // Shield Mk4
    case 42: tmp = createDefense(index); break; // Armor Mk4
    case 43: tmp = createElectronics(index); break; // Cloaking device Mk3
    case 44: tmp = createElectronics(index); break; // Targeting computer Mk2
    case 45: tmp = createElectronics(index); break; // LR Scanner Mk1
    case 46: tmp = createEngine(index); break; // Warp drive Mk2
    case 47: tmp = createEngine(index); break; // Hyper drive Mk2
    case 48: tmp = createElectronics(index); break; // Tachyon source Mk1
    case 49: tmp = createElectronics(index); break; // Colonization module
    case 50: tmp = createElectronics(index); break; // Orbital bombs Mk1
    // Level 4 techs done
    case 51: tmp = createWeapon(index); break; // Laser Mk5
    case 52: tmp = createWeapon(index); break; // RailGun Mk5
    case 53: tmp = createWeapon(index); break; // Photon torpedo Mk5
    case 54: tmp = createWeapon(index); break; // ECM torpedo Mk3
    case 55: tmp = createWeapon(index); break; // HE missile Mk3
    case 56: tmp = createElectronics(index); break; // Orbital bombs Mk2
    case 57: tmp = createDefense(index); break; // Shield generator Mk1
    case 58: tmp = createDefense(index); break; // Shield Mk5
    case 59: tmp = createDefense(index); break; // Armor Mk5
    case 60: tmp = createEngine(index); break; // Warp drive Mk3
    case 61: tmp = createEngine(index); break; // Hyper drive Mk3
    case 62: tmp = createElectronics(index); break; // Tachyon source Mk2
    case 63: tmp = createElectronics(index); break; // Scanner Mk3
    case 64: tmp = createElectronics(index); break; // Jammer Mk2
    // Level 5 techs done
    case 65: tmp = createWeapon(index); break; // Phasors Mk1
    case 66: tmp = createWeapon(index); break; // Massdrive Mk1
    case 67: tmp = createWeapon(index); break; // Photon torpedo Mk6
    case 68: tmp = createWeapon(index); break; // ECM torpedo Mk4
    case 69: tmp = createWeapon(index); break; // HE missile Mk4
    case 70: tmp = createDefense(index); break; // Shield Mk6
    case 71: tmp = createDefense(index); break; // Armor Mk6
    case 72: tmp = createEngine(index); break; // Warp drive Mk4
    case 73: tmp = createEngine(index); break; // Hyper drive Mk4
    case 74: tmp = createEngine(index); break; // Nuclear drive Mk3
    case 75: tmp = createElectronics(index); break; // Antimatter source Mk1
    case 76: tmp = createElectronics(index); break; // Cloaking device Mk4
    case 77: tmp = createElectronics(index); break; // Targeting computer Mk3
    case 78: tmp = createElectronics(index); break; // LR Scanner Mk2
    // Level 6 techs done
    case 79: tmp = createWeapon(index); break; // Phasors Mk2
    case 80: tmp = createWeapon(index); break; // Massdrive Mk2
    case 81: tmp = createWeapon(index); break; // Photon torpedo Mk7
    case 82: tmp = createWeapon(index); break; // ECM torpedo Mk5
    case 83: tmp = createWeapon(index); break; // HE missile Mk5
    case 84: tmp = createDefense(index); break; // Shield Mk7
    case 85: tmp = createDefense(index); break; // Armor Mk7
    case 86: tmp = createEngine(index); break; // Warp drive Mk5
    case 87: tmp = createEngine(index); break; // Hyper drive Mk5
    case 88: tmp = createEngine(index); break; // Impulse engine Mk1
    case 89: tmp = createElectronics(index); break; // Scanner Mk4
    case 90: tmp = createElectronics(index); break; // Jammer Mk3
    // Level 7 techs done
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
      tmp.setEnergyResource(1);
    }
    if (index == 21) {
      tmp = new ShipComponent(index, "Ion drive Mk3", 7, 2, ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(5);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 22) {
      tmp = new ShipComponent(index, "Hyper drive Mk1", 8, 3, ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(3);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 33) {
      tmp = new ShipComponent(index, "Warp drive Mk1", 7, 2, ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(6);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 34) {
      tmp = new ShipComponent(index, "Nuclear drive Mk2", 4, 4, ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(2);
      tmp.setTacticSpeed(2);
      tmp.setEnergyResource(1);
    }
    if (index == 46) {
      tmp = new ShipComponent(index, "Warp drive Mk2", 7, 2, ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(7);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 47) {
      tmp = new ShipComponent(index, "Hyper drive Mk2", 8, 3, ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(4);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 60) {
      tmp = new ShipComponent(index, "Warp drive Mk3", 7, 3, ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(8);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 61) {
      tmp = new ShipComponent(index, "Hyper drive Mk3", 8, 4, ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(5);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 72) {
      tmp = new ShipComponent(index, "Warp drive Mk4", 8, 3, ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(9);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 73) {
      tmp = new ShipComponent(index, "Hyper drive Mk4", 9, 4, ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(6);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 74) {
      tmp = new ShipComponent(index, "Nuclear drive Mk3", 5, 5, ShipComponentType.ENGINE);
      tmp.setSpeed(3);
      tmp.setFtlSpeed(3);
      tmp.setTacticSpeed(3);
      tmp.setEnergyResource(2);
    }
    if (index == 86) {
      tmp = new ShipComponent(index, "Warp drive Mk5", 9, 4, ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(10);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 87) {
      tmp = new ShipComponent(index, "Hyper drive Mk5", 10, 5, ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(7);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 88) {
      tmp = new ShipComponent(index, "Impulse engine Mk1", 6, 6, ShipComponentType.ENGINE);
      tmp.setSpeed(3);
      tmp.setFtlSpeed(4);
      tmp.setTacticSpeed(2);
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
    if (index == 16) {
      tmp = new ShipComponent(index, "Planetary invasion module", 6, 8, ShipComponentType.PLANETARY_INVASION_MODULE);
      tmp.setEnergyRequirement(1);
    }
    if (index == 19) {
      tmp = new ShipComponent(index, "Cloaking device Mk2", 3, 2, ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(40);
      tmp.setEnergyRequirement(1);
    }
    if (index == 20) {
      tmp = new ShipComponent(index, "Targeting computer Mk1", 2, 2, ShipComponentType.TARGETING_COMPUTER);
      tmp.setInitiativeBoost(1);
      tmp.setDamage(10);
      tmp.setEnergyRequirement(1);
    }
    if (index == 23) {
      tmp = new ShipComponent(index, "Fusion source Mk1", 6, 5, ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(6);
    }
    if (index == 31) {
      tmp = new ShipComponent(index, "Scanner Mk2", 3, 2, ShipComponentType.SCANNER);
      tmp.setScannerRange(3);
      tmp.setCloakDetection(40);
      tmp.setEnergyRequirement(1);
    }
    if (index == 32) {
      tmp = new ShipComponent(index, "Jammer Mk1", 3, 1, ShipComponentType.SCANNER);
      tmp.setDefenseValue(5);
      tmp.setEnergyRequirement(1);
    }
    if (index == 35) {
      tmp = new ShipComponent(index, "Fusion source Mk2", 6, 5, ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(7);
    }
    if (index == 43) {
      tmp = new ShipComponent(index, "Cloaking device Mk3", 4, 2, ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(60);
      tmp.setEnergyRequirement(2);
    }
    if (index == 44) {
      tmp = new ShipComponent(index, "Targeting computer Mk2", 3, 2, ShipComponentType.TARGETING_COMPUTER);
      tmp.setInitiativeBoost(2);
      tmp.setDamage(20);
      tmp.setEnergyRequirement(2);
    }
    if (index == 45) {
      tmp = new ShipComponent(index, "LR Scanner Mk1", 2, 2, ShipComponentType.SCANNER);
      tmp.setScannerRange(5);
      tmp.setCloakDetection(20);
      tmp.setEnergyRequirement(1);
    }
    if (index == 48) {
      tmp = new ShipComponent(index, "Tachyon source Mk1", 8, 6, ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(9);
    }
    if (index == 49) {
      tmp = new ShipComponent(index, "Colonization module", 2, 4, ShipComponentType.COLONY_MODULE);
      tmp.setEnergyRequirement(1);
    }
    if (index == 50) {
      tmp = new ShipComponent(index, "Orbital bombs Mk1", 6, 3, ShipComponentType.ORBITAL_BOMBS);
      tmp.setEnergyRequirement(0);
      tmp.setDamage(40);
    }
    if (index == 56) {
      tmp = new ShipComponent(index, "Orbital bombs Mk2", 8, 4, ShipComponentType.ORBITAL_BOMBS);
      tmp.setEnergyRequirement(0);
      tmp.setDamage(75);
    }
    if (index == 62) {
      tmp = new ShipComponent(index, "Tachyon source Mk2", 9, 6, ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(10);
    }
    if (index == 63) {
      tmp = new ShipComponent(index, "Scanner Mk3", 4, 2, ShipComponentType.SCANNER);
      tmp.setScannerRange(4);
      tmp.setCloakDetection(60);
      tmp.setEnergyRequirement(1);
    }
    if (index == 64) {
      tmp = new ShipComponent(index, "Jammer Mk2", 4, 1, ShipComponentType.SCANNER);
      tmp.setDefenseValue(10);
      tmp.setEnergyRequirement(1);
    }
    if (index == 75) {
      tmp = new ShipComponent(index, "Antimatter source Mk1", 10, 8, ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(12);
    }
    if (index == 76) {
      tmp = new ShipComponent(index, "Cloaking device Mk4", 5, 2, ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(80);
      tmp.setEnergyRequirement(2);
    }
    if (index == 77) {
      tmp = new ShipComponent(index, "Targeting computer Mk3", 4, 2, ShipComponentType.TARGETING_COMPUTER);
      tmp.setInitiativeBoost(3);
      tmp.setDamage(30);
      tmp.setEnergyRequirement(2);
    }
    if (index == 78) {
      tmp = new ShipComponent(index, "LR Scanner Mk2", 3, 2, ShipComponentType.SCANNER);
      tmp.setScannerRange(6);
      tmp.setCloakDetection(40);
      tmp.setEnergyRequirement(1);
    }
    if (index == 89) {
      tmp = new ShipComponent(index, "Scanner Mk4", 4, 2, ShipComponentType.SCANNER);
      tmp.setScannerRange(4);
      tmp.setCloakDetection(80);
      tmp.setEnergyRequirement(1);
    }
    if (index == 90) {
      tmp = new ShipComponent(index, "Jammer Mk3", 4, 1, ShipComponentType.SCANNER);
      tmp.setDefenseValue(15);
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
      tmp.setWeaponRange(1);
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
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == 13) {
      tmp = new ShipComponent(index, "Laser Mk2", 6, 3, ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(2);
      tmp.setWeaponRange(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == 14) {
      tmp = new ShipComponent(index, "Railgun Mk2", 4, 4, ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(2);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == 15) {
      tmp = new ShipComponent(index, "Photon torpedo Mk2", 6, 4, ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(2);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == 24) {
      tmp = new ShipComponent(index, "Laser Mk3", 6, 3, ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(3);
      tmp.setWeaponRange(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 25) {
      tmp = new ShipComponent(index, "Railgun Mk3", 4, 4, ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(3);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == 26) {
      tmp = new ShipComponent(index, "Photon torpedo Mk3", 6, 4, ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(3);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == 27) {
      tmp = new ShipComponent(index, "ECM torpedo Mk1", 8, 2, ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(3);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == 28) {
      tmp = new ShipComponent(index, "HE Missile Mk1", 3, 6, ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(3);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(0);
    }
    if (index == 36) {
      tmp = new ShipComponent(index, "Laser Mk4", 7, 3, ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(4);
      tmp.setWeaponRange(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 37) {
      tmp = new ShipComponent(index, "Railgun Mk4", 4, 5, ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(4);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == 38) {
      tmp = new ShipComponent(index, "Photon torpedo Mk4", 7, 4, ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(4);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == 39) {
      tmp = new ShipComponent(index, "ECM torpedo Mk2", 9, 2, ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(4);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == 40) {
      tmp = new ShipComponent(index, "HE Missile Mk2", 3, 7, ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(4);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(0);
    }
    if (index == 51) {
      tmp = new ShipComponent(index, "Laser Mk5", 8, 3, ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(5);
      tmp.setWeaponRange(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == 52) {
      tmp = new ShipComponent(index, "Railgun Mk5", 5, 5, ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(5);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == 53) {
      tmp = new ShipComponent(index, "Photon torpedo Mk5", 8, 4, ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(5);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == 54) {
      tmp = new ShipComponent(index, "ECM torpedo Mk3", 9, 3, ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(5);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == 55) {
      tmp = new ShipComponent(index, "HE Missile Mk3", 3, 8, ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(5);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(0);
    }
    if (index == 65) {
      tmp = new ShipComponent(index, "Phasors Mk1", 8, 3, ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(6);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(3);
    }
    if (index == 66) {
      tmp = new ShipComponent(index, "Massdrive Mk1", 5, 5, ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(6);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == 67) {
      tmp = new ShipComponent(index, "Photon torpedo Mk6", 8, 4, ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(6);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == 68) {
      tmp = new ShipComponent(index, "ECM torpedo Mk4", 10, 3, ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(6);
      tmp.setWeaponRange(5);
      tmp.setEnergyRequirement(0);
    }
    if (index == 69) {
      tmp = new ShipComponent(index, "HE Missile Mk4", 4, 8, ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(6);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == 79) {
      tmp = new ShipComponent(index, "Phasors Mk2", 9, 3, ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(7);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(3);
    }
    if (index == 80) {
      tmp = new ShipComponent(index, "Massdrive Mk2", 6, 6, ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(7);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == 81) {
      tmp = new ShipComponent(index, "Photon torpedo Mk7", 9, 4, ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(7);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == 82) {
      tmp = new ShipComponent(index, "ECM torpedo Mk5", 11, 3, ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(7);
      tmp.setWeaponRange(5);
      tmp.setEnergyRequirement(0);
    }
    if (index == 83) {
      tmp = new ShipComponent(index, "HE Missile Mk5", 4, 9, ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(7);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
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
      tmp = new ShipComponent(index, "Armor plating Mk1", 2, 4, ShipComponentType.ARMOR);
      tmp.setDefenseValue(1);
    }
    if (index == 17) {
      tmp = new ShipComponent(index, "Shield Mk2", 7, 1, ShipComponentType.SHIELD);
      tmp.setDefenseValue(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == 18) {
      tmp = new ShipComponent(index, "Armor plating Mk2", 3, 6, ShipComponentType.ARMOR);
      tmp.setDefenseValue(2);
    }
    if (index == 29) {
      tmp = new ShipComponent(index, "Shield Mk3", 8, 1, ShipComponentType.SHIELD);
      tmp.setDefenseValue(3);
      tmp.setEnergyRequirement(2);
    }
    if (index == 30) {
      tmp = new ShipComponent(index, "Armor plating Mk3", 3, 7, ShipComponentType.ARMOR);
      tmp.setDefenseValue(3);
    }
    if (index == 41) {
      tmp = new ShipComponent(index, "Shield Mk4", 8, 2, ShipComponentType.SHIELD);
      tmp.setDefenseValue(4);
      tmp.setEnergyRequirement(2);
    }
    if (index == 42) {
      tmp = new ShipComponent(index, "Armor plating Mk4", 4, 7, ShipComponentType.ARMOR);
      tmp.setDefenseValue(4);
    }
    if (index == 57) {
      tmp = new ShipComponent(index, "Shield generator Mk1", 6, 1, ShipComponentType.SHIELD_GENERATOR);
      tmp.setDefenseValue(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == 58) {
      tmp = new ShipComponent(index, "Shield Mk5", 9, 2, ShipComponentType.SHIELD);
      tmp.setDefenseValue(5);
      tmp.setEnergyRequirement(2);
    }
    if (index == 59) {
      tmp = new ShipComponent(index, "Armor plating Mk5", 4, 8, ShipComponentType.ARMOR);
      tmp.setDefenseValue(5);
    }
    if (index == 70) {
      tmp = new ShipComponent(index, "Shield Mk6", 10, 2, ShipComponentType.SHIELD);
      tmp.setDefenseValue(6);
      tmp.setEnergyRequirement(3);
    }
    if (index == 71) {
      tmp = new ShipComponent(index, "Armor plating Mk6", 4, 9, ShipComponentType.ARMOR);
      tmp.setDefenseValue(6);
    }
    if (index == 84) {
      tmp = new ShipComponent(index, "Shield Mk7", 11, 2, ShipComponentType.SHIELD);
      tmp.setDefenseValue(7);
      tmp.setEnergyRequirement(3);
    }
    if (index == 85) {
      tmp = new ShipComponent(index, "Armor plating Mk7", 4, 10, ShipComponentType.ARMOR);
      tmp.setDefenseValue(7);
    }
    return tmp;
    
  }

}
