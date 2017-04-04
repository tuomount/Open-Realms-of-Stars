package org.openRealmOfStars.player.ship;

import org.openRealmOfStars.utilities.ErrorLogger;

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

public final class ShipComponentFactory {

  /**
   * Hiding the constructor
   */
  private ShipComponentFactory() {
    // nothing to do here
  }

  /**
   * Current maximum ShipComponent for whole game.
   * Remember to increase this when new ship hull is added to game.
   * It should be one bigger than last index.
   */
  private static final int MAX_SHIPCOMPONENT = 135;

  /**
   * Component Ion drive Mk1
   */
  public static final int COMPONENT_ION_DRIVE_MK1 = 0;

  /**
   * Component Laser Mk1
   */
  public static final int COMPONENT_LASER_MK1 = 1;

  /**
   * Component RailGun Mk1
   */
  public static final int COMPONENT_RAILGUN_MK1 = 2;

  /**
   * Component Photon torpedo Mk1
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK1 = 3;

  /**
   * Component Shield Mk1
   */
  public static final int COMPONENT_SHIELD_MK1 = 4;

  /**
   * Component Armor Mk1
   */
  public static final int COMPONENT_ARMOR_MK1 = 5;

  /**
   * Component Scanner Mk1
   */
  public static final int COMPONENT_SCANNER_MK1 = 6;

  /**
   * Component Cloaking device Mk1
   */
  public static final int COMPONENT_CLOAKING_DEVICE_MK1 = 7;

  /**
   * Component Colony module
   */
  public static final int COMPONENT_COLONY_MODULE = 8;

  /**
   * Component Fission Source Mk1
   */
  public static final int COMPONENT_FISSION_SOURCE_MK1 = 9;

  /**
   * Component Fission Source Mk2
   */
  public static final int COMPONENT_FISSION_SOURCE_MK2 = 10;

  /**
   * Component Ion drive Mk2
   */
  public static final int COMPONENT_ION_DRIVE_MK2 = 11;

  /**
   * Component Nuclear drive Mk1
   */
  public static final int COMPONENT_NUCLEAR_DRIVE_MK1 = 12;

  /**
   * Component Laser Mk2
   */
  public static final int COMPONENT_LASER_MK2 = 13;

  /**
   * Component RailGun Mk2
   */
  public static final int COMPONENT_RAILGUN_MK2 = 14;

  /**
   * Component Photon torpedo Mk2
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK2 = 15;

  /**
   * Component Planetary invasion module
   */
  public static final int COMPONENT_PLANETARY_INVASION_MODULE = 16;

  /**
   * Component Shield Mk2
   */
  public static final int COMPONENT_SHIELD_MK2 = 17;

  /**
   * Component Armor Mk2
   */
  public static final int COMPONENT_ARMOR_MK2 = 18;

  /**
   * Component Cloaking device Mk2
   */
  public static final int COMPONENT_CLOAKING_DEVICE_MK2 = 19;

  /**
   * Component Targeting computer Mk1
   */
  public static final int COMPONENT_TARGETING_COMPUTER_MK1 = 20;

  /**
   * Component Ion drive Mk3
   */
  public static final int COMPONENT_ION_DRIVE_MK3 = 21;

  /**
   * Component Hyper drive Mk1
   */
  public static final int COMPONENT_HYPER_DRIVE_MK1 = 22;

  /**
   * Component Fusion source Mk1
   */
  public static final int COMPONENT_FUSION_SOURCE_MK1 = 23;

  /**
   * Component Laser Mk3
   */
  public static final int COMPONENT_LASER_MK3 = 24;

  /**
   * Component RailGun Mk3
   */
  public static final int COMPONENT_RAILGUN_MK3 = 25;

  /**
   * Component Photon torpedo Mk3
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK3 = 26;

  /**
   * Component ECM torpedo Mk1
   */
  public static final int COMPONENT_ECM_TORPEDO_MK1 = 27;

  /**
   * Component HE missile Mk1
   */
  public static final int COMPONENT_HE_MISSILE_MK1 = 28;

  /**
   * Component Shield Mk3
   */
  public static final int COMPONENT_SHIELD_MK3 = 29;

  /**
   * Component Armor Mk3
   */
  public static final int COMPONENT_ARMOR_MK3 = 30;

  /**
   * Component Scanner Mk2
   */
  public static final int COMPONENT_SCANNER_MK2 = 31;

  /**
   * Component Jammer Mk1
   */
  public static final int COMPONENT_JAMMER_MK1 = 32;

  /**
   * Component Warp drive Mk1
   */
  public static final int COMPONENT_WARP_DRIVE_MK1 = 33;

  /**
   * Component Nuclear drive Mk2
   */
  public static final int COMPONENT_NUCLEAR_DRIVE_MK2 = 34;

  /**
   * Component Fusion source Mk2
   */
  public static final int COMPONENT_FUSION_SOURCE_MK2 = 35;

  /**
   * Component Laser Mk4
   */
  public static final int COMPONENT_LASER_MK4 = 36;

  /**
   * Component RailGun Mk4
   */
  public static final int COMPONENT_RAILGUN_MK4 = 37;

  /**
   * Component Photon torpedo Mk4
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK4 = 38;

  /**
   * Component ECM torpedo Mk2
   */
  public static final int COMPONENT_ECM_TORPEDO_MK2 = 39;

  /**
   * Component HE missile Mk2
   */
  public static final int COMPONENT_HE_MISSILE_MK2 = 40;

  /**
   * Component Shield Mk4
   */
  public static final int COMPONENT_SHIELD_MK4 = 41;

  /**
   * Component Armor Mk4
   */
  public static final int COMPONENT_ARMOR_MK4 = 42;

  /**
   * Component Cloaking device Mk3
   */
  public static final int COMPONENT_CLOAKING_DEVICE_MK3 = 43;

  /**
   * Component Targeting computer Mk2
   */
  public static final int COMPONENT_TARGETING_COMPUTER_MK2 = 44;

  /**
   * Component LR Scanner Mk1
   */
  public static final int COMPONENT_LR_SCANNER_MK1 = 45;

  /**
   * Component Warp drive Mk2
   */
  public static final int COMPONENT_WARP_DRIVE_MK2 = 46;

  /**
   * Component Hyper drive Mk2
   */
  public static final int COMPONENT_HYPER_DRIVE_MK2 = 47;

  /**
   * Component Tachyon source Mk1
   */
  public static final int COMPONENT_TACHYON_SOURCE_MK1 = 48;

  /**
   * Component Colonization module
   */
  public static final int COMPONENT_COLONIZATION_MODULE = 49;

  /**
   * Component Orbital bombs Mk1
   */
  public static final int COMPONENT_ORBITAL_BOMBS_MK1 = 50;

  /**
   * Component Laser Mk5
   */
  public static final int COMPONENT_LASER_MK5 = 51;

  /**
   * Component RailGun Mk5
   */
  public static final int COMPONENT_RAILGUN_MK5 = 52;

  /**
   * Component Photon torpedo Mk5
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK5 = 53;

  /**
   * Component ECM torpedo Mk3
   */
  public static final int COMPONENT_ECM_TORPEDO_MK3 = 54;

  /**
   * Component HE missile Mk3
   */
  public static final int COMPONENT_HE_MISSILE_MK3 = 55;

  /**
   * Component Orbital bombs Mk2
   */
  public static final int COMPONENT_ORBITAL_BOMBS_MK2 = 56;

  /**
   * Component Shield generator Mk1
   */
  public static final int COMPONENT_SHIELD_GENERATOR_MK1 = 57;

  /**
   * Component Shield Mk5
   */
  public static final int COMPONENT_SHIELD_MK5 = 58;

  /**
   * Component Armor Mk5
   */
  public static final int COMPONENT_ARMOR_MK5 = 59;

  /**
   * Component Warp drive Mk3
   */
  public static final int COMPONENT_WARP_DRIVE_MK3 = 60;

  /**
   * Component Hyper drive Mk3
   */
  public static final int COMPONENT_HYPER_DRIVE_MK3 = 61;

  /**
   * Component Tachyon source Mk2
   */
  public static final int COMPONENT_TACHYON_SOURCE_MK2 = 62;

  /**
   * Component Scanner Mk3
   */
  public static final int COMPONENT_SCANNER_MK3 = 63;

  /**
   * Component Jammer Mk2
   */
  public static final int COMPONENT_JAMMER_MK2 = 64;

  /**
   * Component Phasors Mk1
   */
  public static final int COMPONENT_PHASORS_MK1 = 65;

  /**
   * Component Massdrive Mk1
   */
  public static final int COMPONENT_MASSDRIVE_MK1 = 66;

  /**
   * Component Photon torpedo Mk6
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK6 = 67;

  /**
   * Component ECM torpedo Mk4
   */
  public static final int COMPONENT_ECM_TORPEDO_MK4 = 68;

  /**
   * Component HE missile Mk4
   */
  public static final int COMPONENT_HE_MISSILE_MK4 = 69;

  /**
   * Component Shield Mk6
   */
  public static final int COMPONENT_SHIELD_MK6 = 70;

  /**
   * Component Armor Mk6
   */
  public static final int COMPONENT_ARMOR_MK6 = 71;

  /**
   * Component Warp drive Mk4
   */
  public static final int COMPONENT_WARP_DRIVE_MK4 = 72;

  /**
   * Component Hyper drive Mk4
   */
  public static final int COMPONENT_HYPER_DRIVE_MK4 = 73;

  /**
   * Component Nuclear drive Mk3
   */
  public static final int COMPONENT_NUCLEAR_DRIVE_MK3 = 74;

  /**
   * Component Antimatter source Mk1
   */
  public static final int COMPONENT_ANTIMATTER_SOURCE_MK1 = 75;

  /**
   * Component Cloaking device Mk4
   */
  public static final int COMPONENT_CLOAKING_DEVICE_MK4 = 76;

  /**
   * Component Targeting computer Mk3
   */
  public static final int COMPONENT_TARGETING_COMPUTER_MK3 = 77;

  /**
   * Component LR Scanner Mk2
   */
  public static final int COMPONENT_LR_SCANNER_MK2 = 78;

  /**
   * Component Phasors Mk2
   */
  public static final int COMPONENT_PHASORS_MK2 = 79;

  /**
   * Component Massdrive Mk2
   */
  public static final int COMPONENT_MASSDRIVE_MK2 = 80;

  /**
   * Component Photon torpedo Mk7
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK7 = 81;

  /**
   * Component ECM torpedo Mk5
   */
  public static final int COMPONENT_ECM_TORPEDO_MK5 = 82;

  /**
   * Component HE missile Mk5
   */
  public static final int COMPONENT_HE_MISSILE_MK5 = 83;

  /**
   * Component Shield Mk7
   */
  public static final int COMPONENT_SHIELD_MK7 = 84;

  /**
   * Component Armor Mk7
   */
  public static final int COMPONENT_ARMOR_MK7 = 85;

  /**
   * Component Warp drive Mk5
   */
  public static final int COMPONENT_WARP_DRIVE_MK5 = 86;

  /**
   * Component Hyper drive Mk5
   */
  public static final int COMPONENT_HYPER_DRIVE_MK5 = 87;

  /**
   * Component Impulse engine Mk1
   */
  public static final int COMPONENT_IMPULSE_ENGINE_MK1 = 88;

  /**
   * Component Scanner Mk4
   */
  public static final int COMPONENT_SCANNER_MK4 = 89;

  /**
   * Component Jammer Mk3
   */
  public static final int COMPONENT_JAMMER_MK3 = 90;

  /**
   * Component Shock trooper module
   */
  public static final int COMPONENT_SHOCK_TROOPER_MODULE = 91;

  /**
   * Component Orbital nuke
   */
  public static final int COMPONENT_ORBITAL_NUKE = 92;

  /**
   * Component Phasors Mk3
   */
  public static final int COMPONENT_PHASORS_MK3 = 93;

  /**
   * Component Massdrive Mk3
   */
  public static final int COMPONENT_MASSDRIVE_MK3 = 94;

  /**
   * Component Photon torpedo Mk8
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK8 = 95;

  /**
   * Component ECM torpedo Mk6
   */
  public static final int COMPONENT_ECM_TORPEDO_MK6 = 96;

  /**
   * Component HE missile Mk6
   */
  public static final int COMPONENT_HE_MISSILE_MK6 = 97;

  /**
   * Component Shield Mk8
   */
  public static final int COMPONENT_SHIELD_MK8 = 98;

  /**
   * Component Armor Mk8
   */
  public static final int COMPONENT_ARMOR_MK8 = 99;

  /**
   * Component Warp drive Mk6
   */
  public static final int COMPONENT_WARP_DRIVE_MK6 = 100;

  /**
   * Component Hyper drive Mk6
   */
  public static final int COMPONENT_HYPER_DRIVE_MK6 = 101;

  /**
   * Component Impulse engine Mk2
   */
  public static final int COMPONENT_IMPULSE_ENGINE_MK2 = 102;

  /**
   * Component Antimatter source Mk2
   */
  public static final int COMPONENT_ANTIMATTER_SOURCE_MK2 = 103;

  /**
   * Component Cloaking device Mk5
   */
  public static final int COMPONENT_CLOAKING_DEVICE_MK5 = 104;

  /**
   * Component LR Scanner Mk3
   */
  public static final int COMPONENT_LR_SCANNER_MK3 = 105;

  /**
   * Component Orbital smart bombs
   */
  public static final int COMPONENT_ORBITAL_SMART_BOMBS = 106;

  /**
   * Component Antimatter beam Mk1
   */
  public static final int COMPONENT_ANTIMATTER_BEAM_MK1 = 107;

  /**
   * Component Massdrive Mk4
   */
  public static final int COMPONENT_MASSDRIVE_MK4 = 108;

  /**
   * Component Photon torpedo Mk9
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK9 = 109;

  /**
   * Component ECM torpedo Mk7
   */
  public static final int COMPONENT_ECM_TORPEDO_MK7 = 110;

  /**
   * Component HE missile Mk7
   */
  public static final int COMPONENT_HE_MISSILE_MK7 = 111;

  /**
   * Component Shield generator Mk2
   */
  public static final int COMPONENT_SHIELD_GENERATOR_MK2 = 112;

  /**
   * Component Shield Mk9
   */
  public static final int COMPONENT_SHIELD_MK9 = 113;

  /**
   * Component Armor Mk9
   */
  public static final int COMPONENT_ARMOR_MK9 = 114;

  /**
   * Component Warp drive Mk7
   */
  public static final int COMPONENT_WARP_DRIVE_MK7 = 115;

  /**
   * Component Hyper drive Mk7
   */
  public static final int COMPONENT_HYPER_DRIVE_MK7 = 116;

  /**
   * Component Impulse engine Mk3
   */
  public static final int COMPONENT_IMPULSE_ENGINE_MK3 = 117;

  /**
   * Component Zero-point source Mk1
   */
  public static final int COMPONENT_ZEROPOINT_SOURCE_MK1 = 118;

  /**
   * Component Scanner Mk5
   */
  public static final int COMPONENT_SCANNER_MK5 = 119;

  /**
   * Component Targeting computer Mk4
   */
  public static final int COMPONENT_TARGETING_COMPUTER_MK4 = 120;

  /**
   * Component Antimatter beam Mk2
   */
  public static final int COMPONENT_ANTIMATTER_BEAM_MK2 = 121;

  /**
   * Component Massdrive Mk5
   */
  public static final int COMPONENT_MASSDRIVE_MK5 = 122;

  /**
   * Component Photon torpedo Mk10
   */
  public static final int COMPONENT_PHOTON_TORPEDO_MK10 = 123;

  /**
   * Component ECM torpedo Mk8
   */
  public static final int COMPONENT_ECM_TORPEDO_MK8 = 124;

  /**
   * Component HE missile Mk8
   */
  public static final int COMPONENT_HE_MISSILE_MK8 = 125;

  /**
   * Component Shield Mk10
   */
  public static final int COMPONENT_SHIELD_MK10 = 126;

  /**
   * Component Armor Mk10
   */
  public static final int COMPONENT_ARMOR_MK10 = 127;

  /**
   * Component Warp drive Mk8
   */
  public static final int COMPONENT_WARP_DRIVE_MK8 = 128;

  /**
   * Component Hyper drive Mk8
   */
  public static final int COMPONENT_HYPER_DRIVE_MK8 = 129;

  /**
   * Component Impulse engine Mk4
   */
  public static final int COMPONENT_IMPULSE_ENGINE_MK4 = 130;

  /**
   * Component Zero-point source Mk2
   */
  public static final int COMPONENT_ZEROPOINT_SOURCE_MK2 = 131;

  /**
   * Component Cloaking device Mk6
   */
  public static final int COMPONENT_CLOAKING_DEVICE_MK6 = 132;

  /**
   * Component Jammer Mk4
   */
  public static final int COMPONENT_JAMMER_MK4 = 133;

  /**
   * Privateer Module
   */
  public static final int COMPONENT_PRIVATEER_MODULE = 134;


  /**
   * Create ShipComponent with matching name
   * @param name Ship component name
   * @return ShipComponent or null if not found
   */
  public static ShipComponent createByName(final String name) {
    ShipComponent tmp = null;
    if (name != null) {
      for (int i = 0; i < MAX_SHIPCOMPONENT; i++) {
        tmp = create(i);
        if (tmp != null && tmp.getName().equalsIgnoreCase(name)) {
          return tmp;
        }
      }
    }
    ErrorLogger.log("Could not find component called '" + name + "'");
    return null;
  }

  /**
   * Create Ship component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  public static ShipComponent create(final int index) {
    ShipComponent tmp = null;
    switch (index) {
    case COMPONENT_ION_DRIVE_MK1:
      tmp = createEngine(index);
      break; // Ion drive Mk1
    case COMPONENT_LASER_MK1:
      tmp = createWeapon(index);
      break; // Laser Mk1
    case COMPONENT_RAILGUN_MK1:
      tmp = createWeapon(index);
      break; // RailGun Mk1
    case COMPONENT_PHOTON_TORPEDO_MK1:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk1
    case COMPONENT_SHIELD_MK1:
      tmp = createDefense(index);
      break; // Shield Mk1
    case COMPONENT_ARMOR_MK1:
      tmp = createDefense(index);
      break; // Armor Mk1
    case COMPONENT_SCANNER_MK1:
      tmp = createElectronics(index);
      break; // Scanner Mk1
    case COMPONENT_CLOAKING_DEVICE_MK1:
      tmp = createElectronics(index);
      break; // Cloaking device Mk1
    case COMPONENT_COLONY_MODULE:
      tmp = createElectronics(index);
      break; // Colony module
    case COMPONENT_FISSION_SOURCE_MK1:
      tmp = createElectronics(index);
      break; // Fission Source Mk1
    case COMPONENT_FISSION_SOURCE_MK2:
      tmp = createElectronics(index);
      break; // Fission Source Mk2
    case COMPONENT_ION_DRIVE_MK2:
      tmp = createEngine(index);
      break; // Ion drive Mk2
    case COMPONENT_NUCLEAR_DRIVE_MK1:
      tmp = createEngine(index);
      break; // Nuclear drive Mk1
    case COMPONENT_LASER_MK2:
      tmp = createWeapon(index);
      break; // Laser Mk2
    case COMPONENT_RAILGUN_MK2:
      tmp = createWeapon(index);
      break; // RailGun Mk2
    case COMPONENT_PHOTON_TORPEDO_MK2:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk2
    case COMPONENT_PLANETARY_INVASION_MODULE:
      tmp = createElectronics(index);
      break; // Planetary invasion module
    case COMPONENT_SHIELD_MK2:
      tmp = createDefense(index);
      break; // Shield Mk2
    case COMPONENT_ARMOR_MK2:
      tmp = createDefense(index);
      break; // Armor Mk2
    case COMPONENT_CLOAKING_DEVICE_MK2:
      tmp = createElectronics(index);
      break; // Cloaking device Mk2
    case COMPONENT_TARGETING_COMPUTER_MK1:
      tmp = createElectronics(index);
      break; // Targeting computer Mk1
    case COMPONENT_ION_DRIVE_MK3:
      tmp = createEngine(index);
      break; // Ion drive Mk3
    case COMPONENT_HYPER_DRIVE_MK1:
      tmp = createEngine(index);
      break; // Hyper drive Mk1
    case COMPONENT_FUSION_SOURCE_MK1:
      tmp = createElectronics(index);
      break; // Fusion source Mk1
    case COMPONENT_LASER_MK3:
      tmp = createWeapon(index);
      break; // Laser Mk3
    case COMPONENT_RAILGUN_MK3:
      tmp = createWeapon(index);
      break; // RailGun Mk3
    case COMPONENT_PHOTON_TORPEDO_MK3:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk3
    case COMPONENT_ECM_TORPEDO_MK1:
      tmp = createWeapon(index);
      break; // ECM torpedo Mk1
    case COMPONENT_HE_MISSILE_MK1:
      tmp = createWeapon(index);
      break; // HE missile Mk1
    case COMPONENT_SHIELD_MK3:
      tmp = createDefense(index);
      break; // Shield Mk3
    case COMPONENT_ARMOR_MK3:
      tmp = createDefense(index);
      break; // Armor Mk3
    case COMPONENT_SCANNER_MK2:
      tmp = createElectronics(index);
      break; // Scanner Mk2
    case COMPONENT_JAMMER_MK1:
      tmp = createElectronics(index);
      break; // Jammer Mk1
    case COMPONENT_WARP_DRIVE_MK1:
      tmp = createEngine(index);
      break; // Warp drive Mk1
    case COMPONENT_NUCLEAR_DRIVE_MK2:
      tmp = createEngine(index);
      break; // Nuclear drive Mk2
    case COMPONENT_FUSION_SOURCE_MK2:
      tmp = createElectronics(index);
      break; // Fusion source Mk2
    case COMPONENT_LASER_MK4:
      tmp = createWeapon(index);
      break; // Laser Mk4
    case COMPONENT_RAILGUN_MK4:
      tmp = createWeapon(index);
      break; // RailGun Mk4
    case COMPONENT_PHOTON_TORPEDO_MK4:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk4
    case COMPONENT_ECM_TORPEDO_MK2:
      tmp = createWeapon(index);
      break; // ECM torpedo Mk2
    case COMPONENT_HE_MISSILE_MK2:
      tmp = createWeapon(index);
      break; // HE missile Mk2
    case COMPONENT_SHIELD_MK4:
      tmp = createDefense(index);
      break; // Shield Mk4
    case COMPONENT_ARMOR_MK4:
      tmp = createDefense(index);
      break; // Armor Mk4
    case COMPONENT_CLOAKING_DEVICE_MK3:
      tmp = createElectronics(index);
      break; // Cloaking device Mk3
    case COMPONENT_TARGETING_COMPUTER_MK2:
      tmp = createElectronics(index);
      break; // Targeting computer Mk2
    case COMPONENT_LR_SCANNER_MK1:
      tmp = createElectronics(index);
      break; // LR Scanner Mk1
    case COMPONENT_WARP_DRIVE_MK2:
      tmp = createEngine(index);
      break; // Warp drive Mk2
    case COMPONENT_HYPER_DRIVE_MK2:
      tmp = createEngine(index);
      break; // Hyper drive Mk2
    case COMPONENT_TACHYON_SOURCE_MK1:
      tmp = createElectronics(index);
      break; // Tachyon source Mk1
    case COMPONENT_COLONIZATION_MODULE:
      tmp = createElectronics(index);
      break; // Colonization module
    case COMPONENT_ORBITAL_BOMBS_MK1:
      tmp = createElectronics(index);
      break; // Orbital bombs Mk1
    // Level 4 techs done
    case COMPONENT_LASER_MK5:
      tmp = createWeapon(index);
      break; // Laser Mk5
    case COMPONENT_RAILGUN_MK5:
      tmp = createWeapon(index);
      break; // RailGun Mk5
    case COMPONENT_PHOTON_TORPEDO_MK5:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk5
    case COMPONENT_ECM_TORPEDO_MK3:
      tmp = createWeapon(index);
      break; // ECM torpedo Mk3
    case COMPONENT_HE_MISSILE_MK3:
      tmp = createWeapon(index);
      break; // HE missile Mk3
    case COMPONENT_ORBITAL_BOMBS_MK2:
      tmp = createElectronics(index);
      break; // Orbital bombs Mk2
    case COMPONENT_SHIELD_GENERATOR_MK1:
      tmp = createDefense(index);
      break; // Shield generator Mk1
    case COMPONENT_SHIELD_MK5:
      tmp = createDefense(index);
      break; // Shield Mk5
    case COMPONENT_ARMOR_MK5:
      tmp = createDefense(index);
      break; // Armor Mk5
    case COMPONENT_WARP_DRIVE_MK3:
      tmp = createEngine(index);
      break; // Warp drive Mk3
    case COMPONENT_HYPER_DRIVE_MK3:
      tmp = createEngine(index);
      break; // Hyper drive Mk3
    case COMPONENT_TACHYON_SOURCE_MK2:
      tmp = createElectronics(index);
      break; // Tachyon source Mk2
    case COMPONENT_SCANNER_MK3:
      tmp = createElectronics(index);
      break; // Scanner Mk3
    case COMPONENT_JAMMER_MK2:
      tmp = createElectronics(index);
      break; // Jammer Mk2
    // Level 5 techs done
    case COMPONENT_PHASORS_MK1:
      tmp = createWeapon(index);
      break; // Phasors Mk1
    case COMPONENT_MASSDRIVE_MK1:
      tmp = createWeapon(index);
      break; // Massdrive Mk1
    case COMPONENT_PHOTON_TORPEDO_MK6:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk6
    case COMPONENT_ECM_TORPEDO_MK4:
      tmp = createWeapon(index);
      break; // ECM torpedo Mk4
    case COMPONENT_HE_MISSILE_MK4:
      tmp = createWeapon(index);
      break; // HE missile Mk4
    case COMPONENT_SHIELD_MK6:
      tmp = createDefense(index);
      break; // Shield Mk6
    case COMPONENT_ARMOR_MK6:
      tmp = createDefense(index);
      break; // Armor Mk6
    case COMPONENT_WARP_DRIVE_MK4:
      tmp = createEngine(index);
      break; // Warp drive Mk4
    case COMPONENT_HYPER_DRIVE_MK4:
      tmp = createEngine(index);
      break; // Hyper drive Mk4
    case COMPONENT_NUCLEAR_DRIVE_MK3:
      tmp = createEngine(index);
      break; // Nuclear drive Mk3
    case COMPONENT_ANTIMATTER_SOURCE_MK1:
      tmp = createElectronics(index);
      break; // Antimatter source Mk1
    case COMPONENT_CLOAKING_DEVICE_MK4:
      tmp = createElectronics(index);
      break; // Cloaking device Mk4
    case COMPONENT_TARGETING_COMPUTER_MK3:
      tmp = createElectronics(index);
      break; // Targeting computer Mk3
    case COMPONENT_LR_SCANNER_MK2:
      tmp = createElectronics(index);
      break; // LR Scanner Mk2
    // Level 6 techs done
    case COMPONENT_PHASORS_MK2:
      tmp = createWeapon(index);
      break; // Phasors Mk2
    case COMPONENT_MASSDRIVE_MK2:
      tmp = createWeapon(index);
      break; // Massdrive Mk2
    case COMPONENT_PHOTON_TORPEDO_MK7:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk7
    case COMPONENT_ECM_TORPEDO_MK5:
      tmp = createWeapon(index);
      break; // ECM torpedo Mk5
    case COMPONENT_HE_MISSILE_MK5:
      tmp = createWeapon(index);
      break; // HE missile Mk5
    case COMPONENT_SHIELD_MK7:
      tmp = createDefense(index);
      break; // Shield Mk7
    case COMPONENT_ARMOR_MK7:
      tmp = createDefense(index);
      break; // Armor Mk7
    case COMPONENT_WARP_DRIVE_MK5:
      tmp = createEngine(index);
      break; // Warp drive Mk5
    case COMPONENT_HYPER_DRIVE_MK5:
      tmp = createEngine(index);
      break; // Hyper drive Mk5
    case COMPONENT_IMPULSE_ENGINE_MK1:
      tmp = createEngine(index);
      break; // Impulse engine Mk1
    case COMPONENT_SCANNER_MK4:
      tmp = createElectronics(index);
      break; // Scanner Mk4
    case COMPONENT_JAMMER_MK3:
      tmp = createElectronics(index);
      break; // Jammer Mk3
    case COMPONENT_SHOCK_TROOPER_MODULE:
      tmp = createElectronics(index);
      break; // Shock trooper module
    // Level 7 techs done
    case COMPONENT_ORBITAL_NUKE:
      tmp = createElectronics(index);
      break; // Orbital nuke
    case COMPONENT_PHASORS_MK3:
      tmp = createWeapon(index);
      break; // Phasors Mk3
    case COMPONENT_MASSDRIVE_MK3:
      tmp = createWeapon(index);
      break; // Massdrive Mk3
    case COMPONENT_PHOTON_TORPEDO_MK8:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk8
    case COMPONENT_ECM_TORPEDO_MK6:
      tmp = createWeapon(index);
      break; // ECM torpedo Mk6
    case COMPONENT_HE_MISSILE_MK6:
      tmp = createWeapon(index);
      break; // HE missile Mk6
    case COMPONENT_SHIELD_MK8:
      tmp = createDefense(index);
      break; // Shield Mk8
    case COMPONENT_ARMOR_MK8:
      tmp = createDefense(index);
      break; // Armor Mk8
    case COMPONENT_WARP_DRIVE_MK6:
      tmp = createEngine(index);
      break; // Warp drive Mk6
    case COMPONENT_HYPER_DRIVE_MK6:
      tmp = createEngine(index);
      break; // Hyper drive Mk6
    case COMPONENT_IMPULSE_ENGINE_MK2:
      tmp = createEngine(index);
      break; // Impulse engine Mk2
    case COMPONENT_ANTIMATTER_SOURCE_MK2:
      tmp = createElectronics(index);
      break; // Antimatter source Mk2
    case COMPONENT_CLOAKING_DEVICE_MK5:
      tmp = createElectronics(index);
      break; // Cloaking device Mk5
    case COMPONENT_LR_SCANNER_MK3:
      tmp = createElectronics(index);
      break; // LR Scanner Mk3
    // Level 8 techs done
    case COMPONENT_ORBITAL_SMART_BOMBS:
      tmp = createElectronics(index);
      break; // Orbital smart bombs
    case COMPONENT_ANTIMATTER_BEAM_MK1:
      tmp = createWeapon(index);
      break; // Antimatter beam Mk1
    case COMPONENT_MASSDRIVE_MK4:
      tmp = createWeapon(index);
      break; // Massdrive Mk4
    case COMPONENT_PHOTON_TORPEDO_MK9:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk9
    case COMPONENT_ECM_TORPEDO_MK7:
      tmp = createWeapon(index);
      break; // ECM torpedo Mk7
    case COMPONENT_HE_MISSILE_MK7:
      tmp = createWeapon(index);
      break; // HE missile Mk7
    case COMPONENT_SHIELD_GENERATOR_MK2:
      tmp = createDefense(index);
      break; // Shield generator Mk2
    case COMPONENT_SHIELD_MK9:
      tmp = createDefense(index);
      break; // Shield Mk9
    case COMPONENT_ARMOR_MK9:
      tmp = createDefense(index);
      break; // Armor Mk9
    case COMPONENT_WARP_DRIVE_MK7:
      tmp = createEngine(index);
      break; // Warp drive Mk7
    case COMPONENT_HYPER_DRIVE_MK7:
      tmp = createEngine(index);
      break; // Hyper drive Mk7
    case COMPONENT_IMPULSE_ENGINE_MK3:
      tmp = createEngine(index);
      break; // Impulse engine Mk3
    case COMPONENT_ZEROPOINT_SOURCE_MK1:
      tmp = createElectronics(index);
      break; // Zero-point source Mk1
    case COMPONENT_SCANNER_MK5:
      tmp = createElectronics(index);
      break; // Scanner Mk5
    case COMPONENT_TARGETING_COMPUTER_MK4:
      tmp = createElectronics(index);
      break; // Targeting computer Mk4
    // Level 9 techs done
    case COMPONENT_ANTIMATTER_BEAM_MK2:
      tmp = createWeapon(index);
      break; // Antimatter beam Mk2
    case COMPONENT_MASSDRIVE_MK5:
      tmp = createWeapon(index);
      break; // Massdrive Mk5
    case COMPONENT_PHOTON_TORPEDO_MK10:
      tmp = createWeapon(index);
      break; // Photon torpedo Mk10
    case COMPONENT_ECM_TORPEDO_MK8:
      tmp = createWeapon(index);
      break; // ECM torpedo Mk8
    case COMPONENT_HE_MISSILE_MK8:
      tmp = createWeapon(index);
      break; // HE missile Mk8
    case COMPONENT_SHIELD_MK10:
      tmp = createDefense(index);
      break; // Shield Mk10
    case COMPONENT_ARMOR_MK10:
      tmp = createDefense(index);
      break; // Armor Mk10
    case COMPONENT_WARP_DRIVE_MK8:
      tmp = createEngine(index);
      break; // Warp drive Mk8
    case COMPONENT_HYPER_DRIVE_MK8:
      tmp = createEngine(index);
      break; // Hyper drive Mk8
    case COMPONENT_IMPULSE_ENGINE_MK4:
      tmp = createEngine(index);
      break; // Impulse engine Mk4
    case COMPONENT_ZEROPOINT_SOURCE_MK2:
      tmp = createElectronics(index);
      break; // Zero-point source Mk2
    case COMPONENT_CLOAKING_DEVICE_MK6:
      tmp = createElectronics(index);
      break; // Cloaking device Mk6
    case COMPONENT_JAMMER_MK4:
      tmp = createElectronics(index);
      break; // Jammer Mk4
    case COMPONENT_PRIVATEER_MODULE:
      tmp = createElectronics(index);
      break; // Privateer Module
    default: {
      ErrorLogger.log("Unexpected component with index: " + index);
      throw new IllegalArgumentException("Unexpected component index: "
                                        + index + "!");
    }
    }
    return tmp;
  }

  /**
   * Create Engine component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  private static ShipComponent createEngine(final int index) {
    ShipComponent tmp = null;
    if (index == COMPONENT_ION_DRIVE_MK1) {
      tmp = new ShipComponent(index, "Ion drive Mk1", 5, 2,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(3);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_ION_DRIVE_MK2) {
      tmp = new ShipComponent(index, "Ion drive Mk2", 6, 2,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(4);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_NUCLEAR_DRIVE_MK1) {
      tmp = new ShipComponent(index, "Nuclear drive Mk1", 4, 4,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(2);
      tmp.setTacticSpeed(1);
      tmp.setEnergyResource(1);
    }
    if (index == COMPONENT_ION_DRIVE_MK3) {
      tmp = new ShipComponent(index, "Ion drive Mk3", 7, 2,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(5);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_HYPER_DRIVE_MK1) {
      tmp = new ShipComponent(index, "Hyper drive Mk1", 8, 3,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(3);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_WARP_DRIVE_MK1) {
      tmp = new ShipComponent(index, "Warp drive Mk1", 7, 2,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(6);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_NUCLEAR_DRIVE_MK2) {
      tmp = new ShipComponent(index, "Nuclear drive Mk2", 4, 4,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(2);
      tmp.setTacticSpeed(2);
      tmp.setEnergyResource(1);
    }
    if (index == COMPONENT_WARP_DRIVE_MK2) {
      tmp = new ShipComponent(index, "Warp drive Mk2", 7, 2,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(7);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_HYPER_DRIVE_MK2) {
      tmp = new ShipComponent(index, "Hyper drive Mk2", 8, 3,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(4);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_WARP_DRIVE_MK3) {
      tmp = new ShipComponent(index, "Warp drive Mk3", 7, 3,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(8);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_HYPER_DRIVE_MK3) {
      tmp = new ShipComponent(index, "Hyper drive Mk3", 8, 4,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(5);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_WARP_DRIVE_MK4) {
      tmp = new ShipComponent(index, "Warp drive Mk4", 8, 3,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(9);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_HYPER_DRIVE_MK4) {
      tmp = new ShipComponent(index, "Hyper drive Mk4", 9, 4,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(6);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_NUCLEAR_DRIVE_MK3) {
      tmp = new ShipComponent(index, "Nuclear drive Mk3", 5, 5,
          ShipComponentType.ENGINE);
      tmp.setSpeed(3);
      tmp.setFtlSpeed(3);
      tmp.setTacticSpeed(3);
      tmp.setEnergyResource(2);
    }
    if (index == COMPONENT_WARP_DRIVE_MK5) {
      tmp = new ShipComponent(index, "Warp drive Mk5", 9, 4,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(10);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_HYPER_DRIVE_MK5) {
      tmp = new ShipComponent(index, "Hyper drive Mk5", 10, 5,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(7);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_IMPULSE_ENGINE_MK1) {
      tmp = new ShipComponent(index, "Impulse engine Mk1", 6, 6,
          ShipComponentType.ENGINE);
      tmp.setSpeed(3);
      tmp.setFtlSpeed(4);
      tmp.setTacticSpeed(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_WARP_DRIVE_MK6) {
      tmp = new ShipComponent(index, "Warp drive Mk6", 10, 4,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(11);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_HYPER_DRIVE_MK6) {
      tmp = new ShipComponent(index, "Hyper drive Mk6", 11, 5,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(8);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_IMPULSE_ENGINE_MK2) {
      tmp = new ShipComponent(index, "Impulse engine Mk2", 7, 7,
          ShipComponentType.ENGINE);
      tmp.setSpeed(3);
      tmp.setFtlSpeed(5);
      tmp.setTacticSpeed(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_WARP_DRIVE_MK7) {
      tmp = new ShipComponent(index, "Warp drive Mk7", 11, 4,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(12);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_HYPER_DRIVE_MK7) {
      tmp = new ShipComponent(index, "Hyper drive Mk7", 12, 5,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(9);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_IMPULSE_ENGINE_MK3) {
      tmp = new ShipComponent(index, "Impulse engine Mk3", 8, 8,
          ShipComponentType.ENGINE);
      tmp.setSpeed(3);
      tmp.setFtlSpeed(6);
      tmp.setTacticSpeed(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_WARP_DRIVE_MK8) {
      tmp = new ShipComponent(index, "Warp drive Mk8", 12, 5,
          ShipComponentType.ENGINE);
      tmp.setSpeed(1);
      tmp.setFtlSpeed(13);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_HYPER_DRIVE_MK8) {
      tmp = new ShipComponent(index, "Hyper drive Mk8", 13, 6,
          ShipComponentType.ENGINE);
      tmp.setSpeed(2);
      tmp.setFtlSpeed(10);
      tmp.setTacticSpeed(1);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_IMPULSE_ENGINE_MK4) {
      tmp = new ShipComponent(index, "Impulse engine Mk4", 9, 8,
          ShipComponentType.ENGINE);
      tmp.setSpeed(3);
      tmp.setFtlSpeed(7);
      tmp.setTacticSpeed(2);
      tmp.setEnergyRequirement(2);
    }
    return tmp;

  }

  /**
   * Create Engine component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  private static ShipComponent createElectronics(final int index) {
    ShipComponent tmp = null;
    if (index == COMPONENT_SCANNER_MK1) {
      tmp = new ShipComponent(index, "Scanner Mk1", 2, 2,
          ShipComponentType.SCANNER);
      tmp.setScannerRange(2);
      tmp.setCloakDetection(20);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_CLOAKING_DEVICE_MK1) {
      tmp = new ShipComponent(index, "Cloaking device Mk1", 2, 2,
          ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(20);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_COLONY_MODULE) {
      tmp = new ShipComponent(index, "Colony Module", 2, 8,
          ShipComponentType.COLONY_MODULE);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_FISSION_SOURCE_MK1) {
      tmp = new ShipComponent(index, "Fission source Mk1", 3, 3,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(4);
    }
    if (index == COMPONENT_FISSION_SOURCE_MK2) {
      tmp = new ShipComponent(index, "Fission source Mk2", 3, 4,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(5);
    }
    if (index == COMPONENT_PLANETARY_INVASION_MODULE) {
      tmp = new ShipComponent(index, "Planetary invasion module", 6, 8,
          ShipComponentType.PLANETARY_INVASION_MODULE);
      tmp.setEnergyRequirement(1);
      tmp.setDamage(50); // 50% more better fighter

    }
    if (index == COMPONENT_CLOAKING_DEVICE_MK2) {
      tmp = new ShipComponent(index, "Cloaking device Mk2", 3, 2,
          ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(40);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_TARGETING_COMPUTER_MK1) {
      tmp = new ShipComponent(index, "Targeting computer Mk1", 2, 2,
          ShipComponentType.TARGETING_COMPUTER);
      tmp.setInitiativeBoost(1);
      tmp.setDamage(10);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_FUSION_SOURCE_MK1) {
      tmp = new ShipComponent(index, "Fusion source Mk1", 6, 5,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(6);
    }
    if (index == COMPONENT_SCANNER_MK2) {
      tmp = new ShipComponent(index, "Scanner Mk2", 3, 2,
          ShipComponentType.SCANNER);
      tmp.setScannerRange(3);
      tmp.setCloakDetection(40);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_JAMMER_MK1) {
      tmp = new ShipComponent(index, "Jammer Mk1", 3, 1,
          ShipComponentType.JAMMER);
      tmp.setDefenseValue(5);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_FUSION_SOURCE_MK2) {
      tmp = new ShipComponent(index, "Fusion source Mk2", 6, 5,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(7);
    }
    if (index == COMPONENT_CLOAKING_DEVICE_MK3) {
      tmp = new ShipComponent(index, "Cloaking device Mk3", 4, 2,
          ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(60);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_TARGETING_COMPUTER_MK2) {
      tmp = new ShipComponent(index, "Targeting computer Mk2", 3, 2,
          ShipComponentType.TARGETING_COMPUTER);
      tmp.setInitiativeBoost(2);
      tmp.setDamage(20);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_LR_SCANNER_MK1) {
      tmp = new ShipComponent(index, "LR Scanner Mk1", 2, 2,
          ShipComponentType.SCANNER);
      tmp.setScannerRange(5);
      tmp.setCloakDetection(20);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_TACHYON_SOURCE_MK1) {
      tmp = new ShipComponent(index, "Tachyon source Mk1", 8, 6,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(9);
    }
    if (index == COMPONENT_COLONIZATION_MODULE) {
      tmp = new ShipComponent(index, "Colonization module", 2, 4,
          ShipComponentType.COLONY_MODULE);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_ORBITAL_BOMBS_MK1) {
      tmp = new ShipComponent(index, "Orbital bombs Mk1", 6, 3,
          ShipComponentType.ORBITAL_BOMBS);
      tmp.setEnergyRequirement(0);
      tmp.setDamage(40);
    }
    if (index == COMPONENT_ORBITAL_BOMBS_MK2) {
      tmp = new ShipComponent(index, "Orbital bombs Mk2", 8, 4,
          ShipComponentType.ORBITAL_BOMBS);
      tmp.setEnergyRequirement(0);
      tmp.setDamage(75);
    }
    if (index == COMPONENT_TACHYON_SOURCE_MK2) {
      tmp = new ShipComponent(index, "Tachyon source Mk2", 9, 6,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(10);
    }
    if (index == COMPONENT_SCANNER_MK3) {
      tmp = new ShipComponent(index, "Scanner Mk3", 4, 2,
          ShipComponentType.SCANNER);
      tmp.setScannerRange(4);
      tmp.setCloakDetection(60);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_JAMMER_MK2) {
      tmp = new ShipComponent(index, "Jammer Mk2", 4, 1,
          ShipComponentType.JAMMER);
      tmp.setDefenseValue(10);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_ANTIMATTER_SOURCE_MK1) {
      tmp = new ShipComponent(index, "Antimatter source Mk1", 10, 8,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(12);
    }
    if (index == COMPONENT_CLOAKING_DEVICE_MK4) {
      tmp = new ShipComponent(index, "Cloaking device Mk4", 5, 2,
          ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(80);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_TARGETING_COMPUTER_MK3) {
      tmp = new ShipComponent(index, "Targeting computer Mk3", 4, 2,
          ShipComponentType.TARGETING_COMPUTER);
      tmp.setInitiativeBoost(3);
      tmp.setDamage(30);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_LR_SCANNER_MK2) {
      tmp = new ShipComponent(index, "LR Scanner Mk2", 3, 2,
          ShipComponentType.SCANNER);
      tmp.setScannerRange(6);
      tmp.setCloakDetection(40);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_SCANNER_MK4) {
      tmp = new ShipComponent(index, "Scanner Mk4", 4, 2,
          ShipComponentType.SCANNER);
      tmp.setScannerRange(4);
      tmp.setCloakDetection(80);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_JAMMER_MK3) {
      tmp = new ShipComponent(index, "Jammer Mk3", 4, 1,
          ShipComponentType.JAMMER);
      tmp.setDefenseValue(15);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_SHOCK_TROOPER_MODULE) {
      tmp = new ShipComponent(index, "Shock trooper module", 6, 8,
          ShipComponentType.PLANETARY_INVASION_MODULE);
      tmp.setEnergyRequirement(1);
      tmp.setDamage(90); // 90% more better fighter
    }
    if (index == COMPONENT_ORBITAL_NUKE) {
      tmp = new ShipComponent(index, "Orbital nuke", 10, 10,
          ShipComponentType.ORBITAL_NUKE);
      tmp.setEnergyRequirement(0);
      tmp.setDamage(100);
    }
    if (index == COMPONENT_ANTIMATTER_SOURCE_MK2) {
      tmp = new ShipComponent(index, "Antimatter source Mk2", 11, 8,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(13);
    }
    if (index == COMPONENT_CLOAKING_DEVICE_MK5) {
      tmp = new ShipComponent(index, "Cloaking device Mk5", 5, 2,
          ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(100);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_LR_SCANNER_MK3) {
      tmp = new ShipComponent(index, "LR Scanner Mk3", 3, 2,
          ShipComponentType.SCANNER);
      tmp.setScannerRange(7);
      tmp.setCloakDetection(60);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_ZEROPOINT_SOURCE_MK1) {
      tmp = new ShipComponent(index, "Zero-point source Mk1", 20, 2,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(15);
    }
    if (index == COMPONENT_SCANNER_MK5) {
      tmp = new ShipComponent(index, "Scanner Mk5", 5, 2,
          ShipComponentType.SCANNER);
      tmp.setScannerRange(5);
      tmp.setCloakDetection(100);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_TARGETING_COMPUTER_MK4) {
      tmp = new ShipComponent(index, "Targeting computer Mk4", 6, 2,
          ShipComponentType.TARGETING_COMPUTER);
      tmp.setInitiativeBoost(4);
      tmp.setDamage(40);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_ZEROPOINT_SOURCE_MK2) {
      tmp = new ShipComponent(index, "Zero-point source Mk2", 22, 2,
          ShipComponentType.POWERSOURCE);
      tmp.setEnergyResource(16);
    }
    if (index == COMPONENT_CLOAKING_DEVICE_MK6) {
      tmp = new ShipComponent(index, "Cloaking device Mk6", 6, 2,
          ShipComponentType.CLOAKING_DEVICE);
      tmp.setCloaking(120);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_JAMMER_MK4) {
      tmp = new ShipComponent(index, "Jammer Mk4", 5, 1,
          ShipComponentType.JAMMER);
      tmp.setDefenseValue(20);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_PRIVATEER_MODULE) {
      tmp = new ShipComponent(index, "Privateer Module", 6, 4,
          ShipComponentType.PRIVATEERING_MODULE);
      tmp.setEnergyRequirement(1);
    }
    return tmp;

  }

  /**
   * Create Weapon component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  private static ShipComponent createWeapon(final int index) {
    ShipComponent tmp = null;
    if (index == COMPONENT_LASER_MK1) {
      tmp = new ShipComponent(index, "Laser Mk1", 6, 3,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(1);
      tmp.setWeaponRange(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_RAILGUN_MK1) {
      tmp = new ShipComponent(index, "Railgun Mk1", 4, 4,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(1);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK1) {
      tmp = new ShipComponent(index, "Photon torpedo Mk1", 6, 4,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(1);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_LASER_MK2) {
      tmp = new ShipComponent(index, "Laser Mk2", 6, 3,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(2);
      tmp.setWeaponRange(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_RAILGUN_MK2) {
      tmp = new ShipComponent(index, "Railgun Mk2", 4, 4,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(2);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK2) {
      tmp = new ShipComponent(index, "Photon torpedo Mk2", 6, 4,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(2);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_LASER_MK3) {
      tmp = new ShipComponent(index, "Laser Mk3", 6, 3,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(3);
      tmp.setWeaponRange(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_RAILGUN_MK3) {
      tmp = new ShipComponent(index, "Railgun Mk3", 4, 4,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(3);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK3) {
      tmp = new ShipComponent(index, "Photon torpedo Mk3", 6, 4,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(3);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_ECM_TORPEDO_MK1) {
      tmp = new ShipComponent(index, "ECM torpedo Mk1", 8, 2,
          ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(3);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_HE_MISSILE_MK1) {
      tmp = new ShipComponent(index, "HE Missile Mk1", 3, 6,
          ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(3);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_LASER_MK4) {
      tmp = new ShipComponent(index, "Laser Mk4", 7, 3,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(4);
      tmp.setWeaponRange(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_RAILGUN_MK4) {
      tmp = new ShipComponent(index, "Railgun Mk4", 4, 5,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(4);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK4) {
      tmp = new ShipComponent(index, "Photon torpedo Mk4", 7, 4,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(4);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_ECM_TORPEDO_MK2) {
      tmp = new ShipComponent(index, "ECM torpedo Mk2", 9, 2,
          ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(4);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_HE_MISSILE_MK2) {
      tmp = new ShipComponent(index, "HE Missile Mk2", 3, 7,
          ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(4);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_LASER_MK5) {
      tmp = new ShipComponent(index, "Laser Mk5", 8, 3,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(5);
      tmp.setWeaponRange(1);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_RAILGUN_MK5) {
      tmp = new ShipComponent(index, "Railgun Mk5", 5, 5,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(5);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK5) {
      tmp = new ShipComponent(index, "Photon torpedo Mk5", 8, 4,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(5);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_ECM_TORPEDO_MK3) {
      tmp = new ShipComponent(index, "ECM torpedo Mk3", 9, 3,
          ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(5);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_HE_MISSILE_MK3) {
      tmp = new ShipComponent(index, "HE Missile Mk3", 3, 8,
          ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(5);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_PHASORS_MK1) {
      tmp = new ShipComponent(index, "Phasors Mk1", 8, 3,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(6);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_MASSDRIVE_MK1) {
      tmp = new ShipComponent(index, "Massdrive Mk1", 5, 5,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(6);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK6) {
      tmp = new ShipComponent(index, "Photon torpedo Mk6", 8, 4,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(6);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_ECM_TORPEDO_MK4) {
      tmp = new ShipComponent(index, "ECM torpedo Mk4", 10, 3,
          ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(6);
      tmp.setWeaponRange(5);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_HE_MISSILE_MK4) {
      tmp = new ShipComponent(index, "HE Missile Mk4", 4, 8,
          ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(6);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_PHASORS_MK2) {
      tmp = new ShipComponent(index, "Phasors Mk2", 9, 3,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(7);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_MASSDRIVE_MK2) {
      tmp = new ShipComponent(index, "Massdrive Mk2", 6, 6,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(7);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK7) {
      tmp = new ShipComponent(index, "Photon torpedo Mk7", 9, 4,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(7);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_ECM_TORPEDO_MK5) {
      tmp = new ShipComponent(index, "ECM torpedo Mk5", 11, 3,
          ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(7);
      tmp.setWeaponRange(5);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_HE_MISSILE_MK5) {
      tmp = new ShipComponent(index, "HE Missile Mk5", 4, 9,
          ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(7);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_PHASORS_MK3) {
      tmp = new ShipComponent(index, "Phasors Mk3", 10, 3,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(8);
      tmp.setWeaponRange(2);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_MASSDRIVE_MK3) {
      tmp = new ShipComponent(index, "Massdrive Mk3", 7, 7,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(8);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK8) {
      tmp = new ShipComponent(index, "Photon torpedo Mk8", 10, 4,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(8);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_ECM_TORPEDO_MK6) {
      tmp = new ShipComponent(index, "ECM torpedo Mk6", 12, 3,
          ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(8);
      tmp.setWeaponRange(5);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_HE_MISSILE_MK6) {
      tmp = new ShipComponent(index, "HE Missile Mk6", 4, 10,
          ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(8);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_ANTIMATTER_BEAM_MK1) {
      tmp = new ShipComponent(index, "Antimatter beam Mk1", 12, 6,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(9);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(4);
    }
    if (index == COMPONENT_MASSDRIVE_MK4) {
      tmp = new ShipComponent(index, "Massdrive Mk4", 8, 8,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(9);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(4);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK9) {
      tmp = new ShipComponent(index, "Photon torpedo Mk9", 11, 4,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(9);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(4);
    }
    if (index == COMPONENT_ECM_TORPEDO_MK7) {
      tmp = new ShipComponent(index, "ECM torpedo Mk7", 12, 4,
          ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(9);
      tmp.setWeaponRange(6);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_HE_MISSILE_MK7) {
      tmp = new ShipComponent(index, "HE Missile Mk7", 4, 11,
          ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(9);
      tmp.setWeaponRange(5);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_ANTIMATTER_BEAM_MK2) {
      tmp = new ShipComponent(index, "Antimatter beam Mk2", 13, 7,
          ShipComponentType.WEAPON_BEAM);
      tmp.setDamage(10);
      tmp.setWeaponRange(3);
      tmp.setEnergyRequirement(4);
    }
    if (index == COMPONENT_MASSDRIVE_MK5) {
      tmp = new ShipComponent(index, "Massdrive Mk5", 9, 9,
          ShipComponentType.WEAPON_RAILGUN);
      tmp.setDamage(10);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(4);
    }
    if (index == COMPONENT_PHOTON_TORPEDO_MK9) {
      tmp = new ShipComponent(index, "Photon torpedo Mk9", 12, 5,
          ShipComponentType.WEAPON_PHOTON_TORPEDO);
      tmp.setDamage(10);
      tmp.setWeaponRange(4);
      tmp.setEnergyRequirement(4);
    }
    if (index == COMPONENT_ECM_TORPEDO_MK8) {
      tmp = new ShipComponent(index, "ECM torpedo Mk8", 13, 4,
          ShipComponentType.WEAPON_ECM_TORPEDO);
      tmp.setDamage(10);
      tmp.setWeaponRange(6);
      tmp.setEnergyRequirement(0);
    }
    if (index == COMPONENT_HE_MISSILE_MK8) {
      tmp = new ShipComponent(index, "HE Missile Mk8", 5, 12,
          ShipComponentType.WEAPON_HE_MISSILE);
      tmp.setDamage(10);
      tmp.setWeaponRange(5);
      tmp.setEnergyRequirement(0);
    }
    return tmp;

  }

  /**
   * Create Defense component with index
   * @param index Index to create
   * @return ShipComponent or null if index is not found
   */
  private static ShipComponent createDefense(final int index) {
    ShipComponent tmp = null;
    if (index == COMPONENT_SHIELD_MK1) {
      tmp = new ShipComponent(index, "Shield Mk1", 5, 1,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_ARMOR_MK1) {
      tmp = new ShipComponent(index, "Armor plating Mk1", 2, 4,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(1);
    }
    if (index == COMPONENT_SHIELD_MK2) {
      tmp = new ShipComponent(index, "Shield Mk2", 7, 1,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_ARMOR_MK2) {
      tmp = new ShipComponent(index, "Armor plating Mk2", 3, 6,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(2);
    }
    if (index == COMPONENT_SHIELD_MK3) {
      tmp = new ShipComponent(index, "Shield Mk3", 8, 1,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(3);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_ARMOR_MK3) {
      tmp = new ShipComponent(index, "Armor plating Mk3", 3, 7,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(3);
    }
    if (index == COMPONENT_SHIELD_MK4) {
      tmp = new ShipComponent(index, "Shield Mk4", 8, 2,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(4);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_ARMOR_MK4) {
      tmp = new ShipComponent(index, "Armor plating Mk4", 4, 7,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(4);
    }
    if (index == COMPONENT_SHIELD_GENERATOR_MK1) {
      tmp = new ShipComponent(index, "Shield generator Mk1", 6, 1,
          ShipComponentType.SHIELD_GENERATOR);
      tmp.setDefenseValue(1);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_SHIELD_MK5) {
      tmp = new ShipComponent(index, "Shield Mk5", 9, 2,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(5);
      tmp.setEnergyRequirement(2);
    }
    if (index == COMPONENT_ARMOR_MK5) {
      tmp = new ShipComponent(index, "Armor plating Mk5", 4, 8,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(5);
    }
    if (index == COMPONENT_SHIELD_MK6) {
      tmp = new ShipComponent(index, "Shield Mk6", 10, 2,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(6);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_ARMOR_MK6) {
      tmp = new ShipComponent(index, "Armor plating Mk6", 4, 9,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(6);
    }
    if (index == COMPONENT_SHIELD_MK7) {
      tmp = new ShipComponent(index, "Shield Mk7", 11, 2,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(7);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_ARMOR_MK7) {
      tmp = new ShipComponent(index, "Armor plating Mk7", 4, 10,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(7);
    }
    if (index == COMPONENT_SHIELD_MK8) {
      tmp = new ShipComponent(index, "Shield Mk8", 11, 3,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(8);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_ARMOR_MK8) {
      tmp = new ShipComponent(index, "Armor plating Mk8", 5, 11,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(8);
    }
    if (index == COMPONENT_SHIELD_GENERATOR_MK2) {
      tmp = new ShipComponent(index, "Shield generator Mk2", 8, 2,
          ShipComponentType.SHIELD_GENERATOR);
      tmp.setDefenseValue(2);
      tmp.setEnergyRequirement(1);
    }
    if (index == COMPONENT_SHIELD_MK9) {
      tmp = new ShipComponent(index, "Shield Mk9", 10, 2,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(9);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_ARMOR_MK9) {
      tmp = new ShipComponent(index, "Armor plating Mk9", 5, 13,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(9);
    }
    if (index == COMPONENT_SHIELD_MK10) {
      tmp = new ShipComponent(index, "Shield Mk10", 11, 3,
          ShipComponentType.SHIELD);
      tmp.setDefenseValue(10);
      tmp.setEnergyRequirement(3);
    }
    if (index == COMPONENT_ARMOR_MK10) {
      tmp = new ShipComponent(index, "Armor plating Mk10", 6, 15,
          ShipComponentType.ARMOR);
      tmp.setDefenseValue(10);
    }
    return tmp;

  }

}
