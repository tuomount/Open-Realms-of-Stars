package org.openRealmOfStars.player.ship.generator;

import java.util.ArrayList;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullFactory;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
 *
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
 *
 *
 * Ship Generator for creating a new design best with current technology
 *
 */

public final class ShipGenerator {

  /**
   * Hiding the constructor
   */
  private ShipGenerator() {
    // Nothing to do
  }

  /**
   * Ship type for regular military ship
   */
  public static final int SHIP_TYPE_REGULAR = 0;
  /**
   * Ship type for bomber
   */
  public static final int SHIP_TYPE_BOMBER = 1;
  /**
   * Ship type for privateer
   */
  public static final int SHIP_TYPE_PRIVATEER = 2;

  /**
   * Score components for battle ship
   * @param design Design for ship
   * @param player Player doing the ship
   * @param components Component list available
   * @return array of scores
   */
  private static int[] scoreComponents(final ShipDesign design,
      final PlayerInfo player, final ArrayList<ShipComponent> components) {
    int[] scores = new int[components.size()];
    Attitude attitude = player.getAiAttitude();
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      scores[i] = 0;
      scores[i] = scores[i] - comp.getEnergyRequirement();
      scores[i] = scores[i] + comp.getEnergyResource();
      scores[i] = scores[i] - comp.getCost() / 4;
      scores[i] = scores[i] - comp.getMetalCost() / 4;
      switch (comp.getType()) {
      case ARMOR: {
        scores[i] = scores[i] + comp.getDefenseValue() * 5;
        scores[i] = scores[i] + 5; // No need for electricity
        break;
      }
      case SOLAR_ARMOR: {
        scores[i] = scores[i] + comp.getDefenseValue() * 5;
        scores[i] = scores[i] + 5; // No need for electricity
        scores[i] = scores[i] + comp.getEnergyResource() * 5;
        break;
      }
      case ORGANIC_ARMOR: {
        scores[i] = scores[i] + comp.getDefenseValue() * 5;
        scores[i] = scores[i] + 10; // Regenerating
        break;
      }
      case TRACTOR_BEAM: {
        if (design.getHull().getSize() == ShipSize.LARGE) {
          scores[i] = scores[i] + 5;
        }
        if (design.getHull().getSize() == ShipSize.HUGE) {
          scores[i] = scores[i] + 10;
        }
        if (player.getRace() == SpaceRace.SPACE_PIRATE) {
          scores[i] = scores[i] + 20;
        }
        break;
      }
      case ORBITAL_BOMBS: {
        if (!design.gotCertainType(ShipComponentType.ORBITAL_BOMBS)
            && !design.gotCertainType(ShipComponentType.ORBITAL_NUKE)) {
          if (design.getHull().getSize() == ShipSize.MEDIUM) {
            scores[i] = scores[i] + comp.getDamage() / 10;
          }
          if (design.getHull().getSize() == ShipSize.LARGE) {
            scores[i] = scores[i] + comp.getDamage() / 5;
          }
          if (design.getHull().getSize() == ShipSize.HUGE) {
            scores[i] = scores[i] + comp.getDamage() / 3;
          }
          if (design.getHull().getSize() == ShipSize.SMALL) {
            scores[i] = scores[i] + comp.getDamage() / 20;
          }
        } else {
          scores[i] = -1;
        }
        break;
      }
      case ORBITAL_NUKE: {
        if (!design.gotCertainType(ShipComponentType.ORBITAL_BOMBS)
            && !design.gotCertainType(ShipComponentType.ORBITAL_NUKE)) {
          if (design.getHull().getSize() == ShipSize.MEDIUM) {
            scores[i] = scores[i] + comp.getDamage() / 10;
          }
          if (design.getHull().getSize() == ShipSize.LARGE) {
            scores[i] = scores[i] + comp.getDamage() / 5;
          }
          if (design.getHull().getSize() == ShipSize.HUGE) {
            scores[i] = scores[i] + comp.getDamage() / 3;
          }
          if (design.getHull().getSize() == ShipSize.SMALL) {
            scores[i] = scores[i] + comp.getDamage() / 20;
          }
          if (player.getRace() == SpaceRace.CENTAURS
              || player.getRace() == SpaceRace.HOMARIANS) {
            // Centaurs do not like nukes
            scores[i] = scores[i] - 15;
          }
          if (player.getRace() == SpaceRace.MECHIONS) {
            // Mechions use nukes more likely
            scores[i] = scores[i] + 5;
          }
          if (player.getRace() == SpaceRace.CHIRALOIDS) {
            // Chiraloids use nukes more likely
            scores[i] = scores[i] + 25;
          }
          if (player.getRace() == SpaceRace.GREYANS) {
            // Greyans use nukes more likely
            scores[i] = scores[i] + 2;
          }
          if (attitude == Attitude.AGGRESSIVE
              || attitude == Attitude.MILITARISTIC) {
            scores[i] = scores[i] + 5;
          }
          if (attitude == Attitude.DIPLOMATIC
              || attitude == Attitude.PEACEFUL
              || attitude == Attitude.MERCHANTICAL) {
            scores[i] = scores[i] - 20;
          }
        } else {
          scores[i] = -1;
        }
        break;
      }
      case CLOAKING_DEVICE: {
        if (!design.gotCertainType(ShipComponentType.CLOAKING_DEVICE)) {
          scores[i] = scores[i] + comp.getCloaking() / 10;
        } else {
          scores[i] = -1;
        }
        break;
      }
      case THRUSTERS: {
        if (!design.gotCertainType(ShipComponentType.THRUSTERS)) {
          scores[i] = scores[i] + 15;
        } else {
          scores[i] = -1;
        }
        break;
      }
      case FIGHTER_BAY: {
        if (comp.getBaySize() > 1) {
          scores[i] = scores[i] + comp.getBaySize() * 7;
        } else {
          scores[i] = -1;
        }
        break;
      }
      case JAMMER: {
        if (!design.gotCertainType(ShipComponentType.JAMMER)) {
          scores[i] = scores[i] + comp.getDefenseValue() * 2;
        } else {
          scores[i] = -1;
        }
        break;
      }
      case SCANNER: {
        if (!design.gotCertainType(ShipComponentType.SCANNER)) {
          scores[i] = scores[i] + comp.getScannerRange() * 2;
          scores[i] = scores[i] + comp.getCloakDetection() / 10;
          if (design.getHull().getHullType() == ShipHullType.STARBASE) {
            // Starbases are useful deep space scanners
            scores[i] = scores[i] + 10;
          }
        } else {
          scores[i] = -1;
        }
        break;
      }
      case SHIELD: {
        scores[i] = scores[i] + comp.getDefenseValue();
        scores[i] = scores[i] + 5; // Recharge
        break;
      }
      case DISTORTION_SHIELD: {
        scores[i] = scores[i] + comp.getDefenseValue();
        scores[i] = scores[i] + 5; // Recharge
        scores[i] = scores[i] + comp.getDamage(); // Jammer bonus
        break;
      }
      case SHIELD_GENERATOR: {
        if (design.getTotalShield() > 0
            && !design.gotCertainType(ShipComponentType.SHIELD_GENERATOR)) {
          scores[i] = scores[i] + 25;
        } else {
          scores[i] = -1; // No shield
        }
        break;
      }
      case STARBASE_COMPONENT: {
        scores[i] = scores[i] + comp.getCreditBonus() * 10;
        scores[i] = scores[i] + comp.getCultureBonus() * 10;
        scores[i] = scores[i] + comp.getResearchBonus() * 10;
        scores[i] = scores[i] + comp.getFleetCapacityBonus() * 10;
        break;
      }
      case ESPIONAGE_MODULE: {
        if (!design.gotCertainType(ShipComponentType.ESPIONAGE_MODULE)) {
          scores[i] = scores[i] + comp.getEspionageBonus() * 3;
          if (design.getHull().getHullType() == ShipHullType.PRIVATEER) {
            scores[i] = scores[i] + 10;
          }
          if (attitude == Attitude.AGGRESSIVE
              || attitude == Attitude.MILITARISTIC
              || attitude == Attitude.BACKSTABBING
              || attitude == Attitude.LOGICAL) {
            scores[i] = scores[i] + 10;
          }
        } else {
          scores[i] = -1; // This would be second espionage module
        }
        break;
      }
      case TARGETING_COMPUTER: {
        if (!design.gotCertainType(ShipComponentType.TARGETING_COMPUTER)) {
          scores[i] = scores[i] + comp.getDamage();
        } else {
          scores[i] = -1; // This would be second targeting computer
        }
        break;
      }
      case WEAPON_BEAM:
      case WEAPON_ECM_TORPEDO:
      case WEAPON_HE_MISSILE:
      case WEAPON_PHOTON_TORPEDO:
      case PLASMA_CANNON:
      case ION_CANNON:
      case MULTICANNON:
      case WEAPON_RAILGUN:
      case BITE:
      case TENTACLE: {
        scores[i] = scores[i] + comp.getDamage() * 5;
        break;
      }
      default: {
        break;
      }
      }
    }
    return scores;
  }

  /**
   * Adds best defense component on ship design
   * @param shield Regular shield
   * @param armor Regular armor
   * @param solarArmor Solar Armor
   * @param organicArmor Organic Armor
   * @param distortionShield Distortion Shield
   * @param design Ship Design
   */
  private static void addBestDefenseComponent(
      final ShipComponent shield, final ShipComponent armor,
      final ShipComponent solarArmor, final ShipComponent organicArmor,
      final ShipComponent distortionShield, final ShipDesign design) {
    int shieldValue = 0;
    int armorValue = 0;
    int solarArmorValue = 0;
    int organicArmorValue = 0;
    int distortionShieldValue = 0;
    int biggestValue = 0;
    ShipComponent biggestComponent = null;
    if (distortionShield != null
        && design.getFreeEnergy() >= distortionShield.getEnergyRequirement()) {
      distortionShieldValue = distortionShield.getDefenseValue()
          + distortionShield.getDamage() / 5;
      if (distortionShieldValue > biggestValue) {
        biggestValue = distortionShieldValue;
        biggestComponent = distortionShield;
      }
    }
    if (organicArmor != null
        && design.getFreeEnergy() >= organicArmor.getEnergyRequirement()) {
      organicArmorValue = organicArmor.getDefenseValue()
          + (4 - organicArmor.getEnergyRequirement());
      if (organicArmorValue > biggestValue) {
        biggestValue = organicArmorValue;
        biggestComponent = organicArmor;
      }
    }
    if (shield != null
        && design.getFreeEnergy() >= shield.getEnergyRequirement()) {
      shieldValue = shield.getDefenseValue();
      if (shieldValue > biggestValue) {
        biggestValue = shieldValue;
        biggestComponent = shield;
      }
    }
    if (armor != null) {
      armorValue = armor.getDefenseValue();
      if (armorValue > biggestValue) {
        biggestValue = armorValue;
        biggestComponent = armor;
      }
    }
    if (solarArmor != null) {
      solarArmorValue = solarArmor.getDefenseValue()
          + solarArmor.getEnergyResource();
      if (solarArmorValue > biggestValue) {
        biggestValue = solarArmorValue;
        biggestComponent = solarArmor;
      }
      if (biggestComponent == armor
          && design.getFreeSlots() >= 2 && design.getFreeEnergy() <= 1) {
        biggestValue = solarArmorValue;
        biggestComponent = solarArmor;
      }
    }
    if (biggestComponent != null) {
      design.addComponent(biggestComponent);
    }
  }
  /**
   * Design new battle ship for certain size
   * @param player Player doing the design
   * @param size Ship Size
   * @param shipType SHIP_TYPE_REGULAR, SHIP_TYPE_BOMBER
   *        or SHIP_TYPE_PRIVATEER}
   * @param banNukes Are nucleare weapons banned?
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  private static ShipDesign createMilitaryShip(final PlayerInfo player,
      final ShipSize size, final int shipType, final boolean banNukes) {
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    ShipHull hull = null;
    ShipHullType hullType = ShipHullType.NORMAL;
    if (shipType == SHIP_TYPE_PRIVATEER) {
      hullType = ShipHullType.PRIVATEER;
    }
    int value = 0;
    for (Tech tech : hullTechs) {
      ShipHull tempHull = ShipHullFactory.createByName(tech.getHull(),
          player.getRace());
      if (tempHull == null) {
        continue;
      }
      if (tempHull.getHullType() == hullType && tempHull.getSize() == size) {
        int tempValue = tempHull.getMaxSlot() * tempHull.getSlotHull();
        if (tempValue > value) {
          value = tempValue;
          hull = tempHull;
        }
      }
    }
    return createMilitaryShip(player, hull, shipType, banNukes);
  }

  /**
   * Create Military ship based on certain hull.
   *
   * @param player Player doing the design
   * @param hull Used ship hull.
   * @param shipType SHIP_TYPE_REGULAR, SHIP_TYPE_BOMBER
   *        or SHIP_TYPE_PRIVATEER}
   * @param banNukes Are nucleare weapons banned?
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  public static ShipDesign createMilitaryShip(final PlayerInfo player,
      final ShipHull hull, final int shipType, final boolean banNukes) {
    ShipDesign result = null;
    boolean bomber = false;
    ShipHullType hullType = ShipHullType.NORMAL;
    if (shipType == SHIP_TYPE_PRIVATEER) {
      hullType = ShipHullType.PRIVATEER;
    } else if (shipType == SHIP_TYPE_BOMBER) {
      bomber = true;
    }
    if (hull != null) {
      result = new ShipDesign(hull);
      String[] part = hull.getName().split("Mk");
      result.setName(
          part[0].trim() + " Mk" + (player.getShipStatHighestNumber(
              part[0]) + 1));
      ShipComponent engine = null;
      boolean tacticalEngine = false;
      if (hull.getSize() == ShipSize.MEDIUM
          || hull.getSize() == ShipSize.SMALL) {
        tacticalEngine = true;
        if (DiceGenerator.getRandom(99) < 49) {
          tacticalEngine = false;
        }
      }
      if (hull.getHullType() == ShipHullType.PRIVATEER) {
        tacticalEngine = false;
        engine = ShipComponentFactory
            .createByName(player.getTechList().getFastestEngine()
                .getComponent());
      } else if (tacticalEngine) {
        engine = ShipComponentFactory
            .createByName(player.getTechList().getBestTacticalEngine()
                .getComponent());
      } else {
        engine = ShipComponentFactory
            .createByName(player.getTechList().getBestEngine().getComponent());
      }
      result.addComponent(engine);
      // Getting the best powersource
      ShipComponent power = ShipComponentFactory.createByName(
          player.getTechList().getBestEnergySource().getComponent());
      ShipComponent weapon = ShipComponentFactory
          .createByName(player.getTechList().getBestWeapon().getComponent());
      result.addComponent(weapon);
      if (result.getFreeEnergy() < 0) {
        result.addComponent(power);
      }
      if (result.getFreeSlots() > 1 && bomber) {
        Tech[] combatTechs = player.getTechList()
            .getListForType(TechType.Combat);
        Tech bombTech = TechList.getBestTech(combatTechs, "Orbital bombs");
        Tech  smartTech = TechList.getBestTech(combatTechs,
            "Orbital smart bombs");
        Tech nukeTech = TechList.getBestTech(combatTechs,
            "Orbital neutron bomb");
        if (nukeTech == null) {
          nukeTech = TechList.getBestTech(combatTechs,
              "Orbital antimatter bomb");
        }
        if (nukeTech == null) {
          nukeTech = TechList.getBestTech(combatTechs,
              "Orbital fusion bomb");
        }
        if (nukeTech == null) {
          nukeTech = TechList.getBestTech(combatTechs, "Orbital nuke");
        }
        if (smartTech != null) {
          result.addComponent(ShipComponentFactory.createByName(
                  smartTech.getComponent()));
        } else if (nukeTech != null && !banNukes) {
          result.addComponent(ShipComponentFactory.createByName(
              nukeTech.getComponent()));
        } else if (bombTech != null) {
          result.addComponent(ShipComponentFactory.createByName(
              bombTech.getComponent()));
        } else {
          // Bomber was requested but could not deliver one
          return null;
        }
      }
      if (result.getFreeSlots() >= 1 && hullType == ShipHullType.PRIVATEER) {
          result.addComponent(ShipComponentFactory.createByName(
              "Privateer module"));
          if (result.getFreeEnergy() < 0) {
            result.addComponent(power);
          }
      }

      Tech[] defenseTechs = player.getTechList()
          .getListForType(TechType.Defense);
      Tech[] electricsTechs = player.getTechList()
          .getListForType(TechType.Electrics);
      Tech[] combatTechs = player.getTechList().getListForType(TechType.Combat);
      Tech shield = TechList.getBestTech(defenseTechs, "Shield");
      Tech armor = TechList.getBestTech(defenseTechs, "Armor plating");
      Tech solarArmor = TechList.getBestTech(defenseTechs, "Solar armor");
      Tech organicArmor = TechList.getBestTech(defenseTechs, "Organic armor");
      Tech distortionShield = TechList.getBestTech(defenseTechs,
          "Distortion shield");
      Tech shieldGen = TechList.getBestTech(electricsTechs, "Shield generator");
      Tech fighterBay = TechList.getBestTech(electricsTechs, "Fighter bay");
      ShipComponent shieldComp = null;
      ShipComponent shieldGenComp = null;
      ShipComponent armorComp = null;
      ShipComponent fighterBayComp = null;
      ShipComponent solarArmorComp = null;
      ShipComponent organicArmorComp = null;
      ShipComponent distortionShieldComp = null;
      ArrayList<ShipComponent> components = new ArrayList<>();
      ShipComponent thrusters = null;
      if (player.getTechList().isTech("Combat thrusters")) {
        thrusters = ShipComponentFactory.createByName("Combat thrusters");
        components.add(thrusters);
      }
      if (shield != null) {
        shieldComp = ShipComponentFactory.createByName(shield.getComponent());
        components.add(shieldComp);
      }
      if (shieldGen != null) {
        shieldGenComp = ShipComponentFactory
            .createByName(shieldGen.getComponent());
        components.add(shieldGenComp);
      }
      if (armor != null) {
        armorComp = ShipComponentFactory.createByName(armor.getComponent());
        components.add(armorComp);
      }
      if (fighterBay != null) {
        fighterBayComp = ShipComponentFactory.createByName(
            fighterBay.getComponent());
        components.add(fighterBayComp);
      }
      if (solarArmor != null) {
        solarArmorComp = ShipComponentFactory.createByName(
            solarArmor.getComponent());
        components.add(solarArmorComp);
      }
      if (organicArmor != null) {
        organicArmorComp = ShipComponentFactory.createByName(
            organicArmor.getComponent());
        components.add(organicArmorComp);
      }
      if (distortionShield != null) {
        distortionShieldComp = ShipComponentFactory.createByName(
            distortionShield.getComponent());
        components.add(distortionShieldComp);
      }
      addBestDefenseComponent(shieldComp, armorComp, solarArmorComp,
          organicArmorComp, distortionShieldComp, result);
      Tech weapTech = player.getTechList()
          .getBestWeapon(ShipComponentType.WEAPON_BEAM);
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList()
          .getBestWeapon(ShipComponentType.WEAPON_ECM_TORPEDO);
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList()
          .getBestWeapon(ShipComponentType.WEAPON_HE_MISSILE);
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList()
          .getBestWeapon(ShipComponentType.WEAPON_PHOTON_TORPEDO);
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList()
          .getBestWeapon(ShipComponentType.WEAPON_RAILGUN);
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList()
          .getBestWeapon(ShipComponentType.MULTICANNON);
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList()
          .getBestWeapon(ShipComponentType.PLASMA_CANNON);
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList()
          .getBestWeapon(ShipComponentType.ION_CANNON);
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = player.getTechList()
          .getBestWeapon(ShipComponentType.TRACTOR_BEAM);
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      Tech elecTech = TechList.getBestTech(electricsTechs, "Jammer");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs, "Espionage module");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs, "Targeting computer");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs, "Scanner");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs, "Cloaking device");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs, "LR scanner");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(combatTechs, "Orbital bombs");
      weapTech = TechList.getBestTech(combatTechs, "Orbital smart bombs");
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      } else if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      weapTech = TechList.getBestTech(combatTechs, "Orbital nuke");
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      weapTech = TechList.getBestTech(combatTechs, "Mini nuke");
      if (weapTech != null) {
        components
            .add(ShipComponentFactory.createByName(weapTech.getComponent()));
      }
      int[] componentScores = new int[components.size()];
      int safetyCount = 500;
      while (result.getFreeSlots() > 0 && safetyCount > 0) {
        safetyCount--;
        componentScores = scoreComponents(result, player, components);
        int sum = 0;
        for (int i = 0; i < componentScores.length; i++) {
          if (componentScores[i] > 0) {
            sum = sum + componentScores[i];
          }
        }
        int choice = DiceGenerator.getRandom(sum);
        int total = 0;
        for (int i = 0; i < componentScores.length; i++) {
          if (componentScores[i] > 0) {
            if (choice < total + componentScores[i]) {
              if (result.getFreeEnergy() >= components.get(i)
                  .getEnergyRequirement()) {
                result.addComponent(components.get(i));
              } else if (result.getFreeSlots() > 1 && power.getEnergyResource()
                  + result.getFreeEnergy() >= components.get(i)
                      .getEnergyRequirement()) {
                result.addComponent(components.get(i));
                result.addComponent(power);
              }
              break;
            }
            total = total + componentScores[i];
          }
        }

      }

    }
    return result;
  }

  /**
   * Design new battle ship for certain size
   * @param player Player doing the design
   * @param size Ship Size
   * @param bomber Create bomber battle ship
   * @param banNukes Are nuclear weapons banned?
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  public static ShipDesign createBattleShip(final PlayerInfo player,
      final ShipSize size, final boolean bomber, final boolean banNukes) {
    if (bomber) {
      return createMilitaryShip(player, size, SHIP_TYPE_BOMBER, banNukes);
    }
    return createMilitaryShip(player, size, SHIP_TYPE_REGULAR, banNukes);
  }

  /**
   * Design new privateer ship for certain size
   * @param player Player doing the design
   * @param size Ship Size
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  public static ShipDesign createPrivateerShip(final PlayerInfo player,
      final ShipSize size) {
    return createMilitaryShip(player, size, SHIP_TYPE_PRIVATEER, false);
  }

  /**
   * Create Space worm ship.
   * @param player PlayerInfo
   * @return Space worm ship or null.
   */
  public static ShipDesign createSpaceWorm(final PlayerInfo player) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    Tech hullTech = TechList.getBestTech(hullTechs, "Space worm");
    if (hullTech != null) {
      ShipHull hull = ShipHullFactory.createByName(hullTech.getHull(),
          player.getRace());
      result = new ShipDesign(hull);
      result.setName("Space worm");
      ShipComponent engine = ShipComponentFactory
          .createByName(player.getTechList().getBestEngine().getComponent());
      result.addComponent(engine);
      ShipComponent power = ShipComponentFactory.createByName("Heart");
      result.addComponent(power);
      ShipComponent weapon = ShipComponentFactory
          .createByName("Massive mouth with teeth Mk1");
      result.addComponent(weapon);
      ShipComponent armor = ShipComponentFactory
          .createByName("Organic armor Mk1");
      result.addComponent(armor);
    }
    return result;
  }

  /**
   * Create Space kraken ship.
   * @param player PlayerInfo
   * @return Space worm ship or null.
   */
  public static ShipDesign createSpaceKraken(final PlayerInfo player) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    Tech hullTech = TechList.getBestTech(hullTechs, "Kraken");
    if (hullTech != null) {
      ShipHull hull = ShipHullFactory.createByName(hullTech.getHull(),
          player.getRace());
      result = new ShipDesign(hull);
      result.setName("Space Kraken");
      ShipComponent engine = ShipComponentFactory
          .createByName("Space fin");
      result.addComponent(engine);
      ShipComponent power = ShipComponentFactory.createByName("Heart");
      result.addComponent(power);
      ShipComponent weapon = ShipComponentFactory
          .createByName("Massive mouth with teeth Mk2");
      result.addComponent(weapon);
      ShipComponent armor = ShipComponentFactory
          .createByName("Organic armor Mk2");
      result.addComponent(armor);
      weapon = ShipComponentFactory
          .createByName("Tentacle Mk1");
      result.addComponent(weapon);
      result.addComponent(weapon);
    }
    return result;
  }

  /**
   * Create large space kraken ship.
   * @param player PlayerInfo
   * @param variant True for one with two hearts
   * @return Space worm ship or null.
   */
  public static ShipDesign createLargeKraken(final PlayerInfo player,
      final boolean variant) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    Tech hullTech = TechList.getBestTech(hullTechs, "Large kraken");
    if (hullTech != null) {
      ShipHull hull = ShipHullFactory.createByName(hullTech.getHull(),
          player.getRace());
      result = new ShipDesign(hull);
      result.setName("Space Kraken");
      ShipComponent weapon = ShipComponentFactory
          .createByName("Massive mouth with teeth Mk3");
      result.addComponent(weapon);
      ShipComponent armor = ShipComponentFactory
          .createByName("Organic armor Mk3");
      result.addComponent(armor);
      weapon = ShipComponentFactory
          .createByName("Tentacle Mk2");
      result.addComponent(weapon);
      result.addComponent(weapon);
      if (variant) {
        ShipComponent power = ShipComponentFactory.createByName("Heart");
        result.addComponent(power);
        result.addComponent(power);
        result.addComponent(armor);
      } else {
        ShipComponent power = ShipComponentFactory.createByName("Large heart");
        result.addComponent(power);
        result.addComponent(weapon);
        result.addComponent(weapon);
      }
      ShipComponent engine = ShipComponentFactory
          .createByName("Space fin");
      result.addComponent(engine);
    }
    return result;
  }

  /**
   * Create scout ship with best possible technology. This is used
   * for human players in beginning and AI every time they design
   * new scout ship.
   * @param player whom is designing the new ship
   * @return ShipDesign or null if fails
   */
  public static ShipDesign createScout(final PlayerInfo player) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    Tech hullTech = TechList.getBestTech(hullTechs, "Scout");
    Tech[] defenseTechs = player.getTechList().getListForType(TechType.Defense);
    if (hullTech != null) {
      ShipHull hull = ShipHullFactory.createByName(hullTech.getHull(),
          player.getRace());
      result = new ShipDesign(hull);
      result.setName("Scout Mk1");
      ShipComponent engine = ShipComponentFactory
          .createByName(player.getTechList().getBestEngine().getComponent());
      result.addComponent(engine);
      ShipComponent power = ShipComponentFactory.createByName(
                    player.getTechList().getBestEnergySource().getComponent());
      ShipComponent weapon = ShipComponentFactory
          .createByName(player.getTechList().getBestWeapon().getComponent());
      result.addComponent(weapon);
      if (result.getFreeEnergy() < 0) {
        result.addComponent(power);
      }
      Tech shield = TechList.getBestTech(defenseTechs, "Shield");
      Tech armor = TechList.getBestTech(defenseTechs, "Armor plating");
      ShipComponent shieldComp = null;
      ShipComponent armorComp = null;
      if (shield != null) {
        shieldComp = ShipComponentFactory.createByName(shield.getComponent());
      }
      if (armor != null) {
        armorComp = ShipComponentFactory.createByName(armor.getComponent());
      }
      if (player.getRace() == SpaceRace.CENTAURS
          || player.getRace() == SpaceRace.MOTHOIDS) {
        // Centaurs could ignore defense since they got more hull points.
        // Mothoids does not have defense tech at start so adding another
        // weapon.
        result.addComponent(weapon);
      } else {
        if (shieldComp != null
            && result.getFreeEnergy() >= shieldComp.getEnergyRequirement()) {
          result.addComponent(shieldComp);
        } else if (armorComp != null) {
          result.addComponent(armorComp);
        }
      }
      if (result.getNumberOfComponents() == 2) {
        if (shieldComp != null) {
          result.addComponent(shieldComp);
          result.addComponent(power);
        }
      } else if (result.getNumberOfComponents()
          < result.getHull().getMaxSlot()) {
        if (weapon.getEnergyRequirement() < result.getFreeEnergy()) {
          result.addComponent(weapon);
        } else if (armorComp != null) {
          result.addComponent(armorComp);
        } else {
          result.addComponent(power);
        }
      }
    }
    return result;
  }

  /**
   * Create spy ship with best possible technology.
   * @param player whom is designing the new ship
   * @return ShipDesign or null if fails
   */
  public static ShipDesign createSpy(final PlayerInfo player) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    Tech[] electronicTechs = player.getTechList().getListForType(
        TechType.Electrics);
    Tech spyTech = TechList.getBestTech(electronicTechs, "Espionage module");
    Tech hullTech = TechList.getBestTech(hullTechs, "Corvette");
    if (hullTech == null) {
      hullTech = TechList.getBestTech(hullTechs, "Probe");
    }
    if (hullTech == null) {
      hullTech = TechList.getBestTech(hullTechs, "Scout");
    }
    if (hullTech != null && spyTech != null) {
      ShipHull hull = ShipHullFactory.createByName(hullTech.getHull(),
          player.getRace());
      result = createSpy(player, hull);
    }
    return result;
  }

  /**
   * Create spy ship with best possible technology.
   * @param player whom is designing the new ship
   * @param hull ShipHull to use design spy ship.
   * @return ShipDesign or null if fails
   */
  public static ShipDesign createSpy(final PlayerInfo player,
      final ShipHull hull) {
    ShipDesign result = null;
    Tech[] electronicTechs = player.getTechList().getListForType(
        TechType.Electrics);
    Tech spyTech = TechList.getBestTech(electronicTechs, "Espionage module");
    boolean probe = false;
    if (spyTech != null) {
      result = new ShipDesign(hull);
      if (hull.getName().equals("Probe")) {
        probe = true;
      }
      if (hull.getMaxSlot() == 4) {
        probe = true;
      }
      result.setName(
           "Spy Mk" + (player.getShipStatHighestNumber(
              "Spy ") + 1));
      ShipComponent engine = ShipComponentFactory
          .createByName(player.getTechList().getFastestEngine().getComponent());
      result.addComponent(engine);
      ShipComponent power = ShipComponentFactory.createByName(
          player.getTechList().getBestEnergySource().getComponent());
      result.addComponent(power);
      ShipComponent spyModule = ShipComponentFactory.createByName(
          spyTech.getComponent());
      result.addComponent(spyModule);
      Tech scanner = TechList.getBestTech(electronicTechs, "Scanner");
      Tech cloak = TechList.getBestTech(electronicTechs, "Cloaking device");
      ShipComponent scannerComp = null;
      ShipComponent cloakComp = null;
      if (scanner != null) {
        scannerComp = ShipComponentFactory.createByName(
            scanner.getComponent());
      }
      if (cloak != null) {
        cloakComp = ShipComponentFactory.createByName(cloak.getComponent());
      }
      if (probe) {
        if (player.getRace() == SpaceRace.TEUTHIDAES && scannerComp != null) {
          result.addComponent(scannerComp);
        } else {
          if (cloakComp != null
              && result.getFreeEnergy() >= cloakComp.getEnergyRequirement()) {
            result.addComponent(cloakComp);
          } else if (scannerComp != null) {
            result.addComponent(scannerComp);
          }
        }
      } else {
        if (cloakComp != null
            && result.getFreeEnergy() >= cloakComp.getEnergyRequirement()) {
          result.addComponent(cloakComp);
        }
        if (scannerComp != null
            && result.getFreeEnergy() >= scannerComp.getEnergyRequirement()) {
          result.addComponent(scannerComp);
        }
      }
    }
    return result;
  }

  /**
   * Create colony/troop ship with best possible technology. This is used
   * for human players in beginning and AI every time they design
   * new colony/troop ship.
   * @param player whom is designing the new ship
   * @param troop Is ship going to be troop carrier
   * @return ShipDesign or null if fails
   */
  public static ShipDesign createColony(final PlayerInfo player,
      final boolean troop) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    int value = -1;
    Tech hullTech = null;
    for (Tech tech : hullTechs) {
      ShipHull hull = ShipHullFactory.createByName(tech.getHull(),
          player.getRace());
      if (hull == null || !troop && (hull.getSize() == ShipSize.LARGE
          || hull.getSize() == ShipSize.HUGE)) {
        // Large and huge are too large for colony ships
        continue;
      }
      if (hull.getMaxSlot() > value
          && hull.getHullType() == ShipHullType.FREIGHTER) {
        hullTech = tech;
        value = hull.getMaxSlot();
      }
    }
    if (hullTech != null) {
      ShipHull hull = ShipHullFactory.createByName(hullTech.getHull(),
          player.getRace());
      result = createColony(player, hull, troop);
    }
    return result;
  }

  /**
   * Create colony/troop ship with best possible technology. This is used
   * for human players in beginning and AI every time they design
   * new colony/troop ship.
   * @param player whom is designing the new ship
   * @param hull ShipHull to use in design.
   * @param troop Is ship going to be troop carrier
   * @return ShipDesign or null if fails
   */
  public static ShipDesign createColony(final PlayerInfo player,
      final ShipHull hull, final boolean troop) {
    ShipDesign result = null;
    Tech[] defenseTechs = player.getTechList().getListForType(TechType.Defense);
    result = new ShipDesign(hull);
    if (!troop) {
      result.setName("Colony Mk"
          + (player.getShipStatHighestNumber("Colony Mk") + 1));
    } else {
      result.setName("Trooper Mk"
          + (player.getShipStatHighestNumber("Trooper Mk") + 1));
    }
    ShipComponent engine = ShipComponentFactory
        .createByName(player.getTechList().getBestEngine().getComponent());
    result.addComponent(engine);
    ShipComponent power = ShipComponentFactory.createByName(
        player.getTechList().getBestEnergySource().getComponent());
    result.addComponent(power);
    if (!troop) {
      ShipComponent colony = ShipComponentFactory
          .createByName("Colonization module");
      result.addComponent(colony);
    } else {
      Tech[] combatTechs = player.getTechList()
          .getListForType(TechType.Combat);
      Tech invasionModule = TechList.getBestTech(combatTechs,
          "Planetary invasion module");
      Tech shockModule = TechList.getBestTech(combatTechs,
          "Shock trooper module");
      if (shockModule != null) {
        ShipComponent trooper = ShipComponentFactory
            .createByName(shockModule.getComponent());
        result.addComponent(trooper);
      } else if (invasionModule != null) {
        ShipComponent trooper = ShipComponentFactory
            .createByName(invasionModule.getComponent());
        result.addComponent(trooper);
      } else {
        // Cannot build a trooper
        return null;
      }
    }
    if (result.getFreeSlots() > 2) {
      Tech armor = TechList.getBestTech(defenseTechs, "Armor plating");
      ShipComponent armorComp = null;
      if (armor != null) {
        armorComp = ShipComponentFactory.createByName(armor.getComponent());
        result.addComponent(armorComp);
      }
    }
    if (result.getFreeSlots() > 2) {
      Tech shield = TechList.getBestTech(defenseTechs, "Shield");
      ShipComponent shieldComp = null;
      if (shield != null) {
        shieldComp = ShipComponentFactory.createByName(shield.getComponent());
        if (shieldComp.getEnergyRequirement() <= result.getFreeEnergy()) {
          result.addComponent(shieldComp);
        }
      }
    }
    if (result.getFreeSlots() > 2) {
      Tech[] combatTechs = player.getTechList()
          .getListForType(TechType.Combat);
      Tech bombTech = TechList.getBestTech(combatTechs, "Orbital bombs");
      Tech  smartTech = TechList.getBestTech(combatTechs,
          "Orbital smart bombs");
      Tech nukeTech = TechList.getBestTech(combatTechs, "Orbital nuke");
      if (nukeTech == null) {
        nukeTech = TechList.getBestTech(combatTechs, "Mini nuke");
      }
      if (smartTech != null) {
        result.addComponent(ShipComponentFactory.createByName(
                smartTech.getComponent()));
      } else if (nukeTech != null) {
        result.addComponent(ShipComponentFactory.createByName(
            nukeTech.getComponent()));
      } else if (bombTech != null) {
        result.addComponent(ShipComponentFactory.createByName(
            bombTech.getComponent()));
      }
    }
    return result;
  }

  /**
   * Create freighter ship with best possible technology. This is used
   * for  AI every time they design new freighter ship.
   * @param player whom is designing the new ship
   * @return ShipDesign or null if fails
   */
  public static ShipDesign createFreighter(final PlayerInfo player) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    int value = -1;
    Tech hullTech = null;
    for (Tech tech : hullTechs) {
      ShipHull hull = ShipHullFactory.createByName(tech.getHull(),
          player.getRace());
      if (hull == null) {
        continue;
      }
      if (hull.getMaxSlot() > value
          && hull.getHullType() == ShipHullType.FREIGHTER) {
        hullTech = tech;
        value = hull.getMaxSlot();
      }
    }
    if (hullTech != null) {
      ShipHull hull = ShipHullFactory.createByName(hullTech.getHull(),
          player.getRace());
      result = createFreighter(player, hull);
    }
    return result;
  }

  /**
   * Create freighter ship with best possible technology. This is used
   * for  AI every time they design new freighter ship.
   * @param player whom is designing the new ship
   * @param hull ShipHull used in design
   * @return ShipDesign or null if fails
   */
  public static ShipDesign createFreighter(final PlayerInfo player,
      final ShipHull hull) {
    Tech[] defenseTechs = player.getTechList().getListForType(TechType.Defense);
    ShipDesign result = null;
    result = new ShipDesign(hull);
    result.setName("Freighter Mk"
          + (player.getShipStatHighestNumber("Freighter Mk") + 1));
    ShipComponent engine = ShipComponentFactory
        .createByName(player.getTechList().getBestEngine().getComponent());
    result.addComponent(engine);
    ShipComponent power = ShipComponentFactory.createByName(
        player.getTechList().getBestEnergySource().getComponent());
    result.addComponent(power);
    if (player.getRace() == SpaceRace.SMAUGIRIANS
        && result.getFreeSlots() > 2) {
      ShipComponent weapon = ShipComponentFactory
          .createByName(player.getTechList().getBestWeapon().getComponent());
      result.addComponent(weapon);
    }
    Attitude attitude = player.getAiAttitude();
    if (attitude == Attitude.AGGRESSIVE
        || attitude == Attitude.MILITARISTIC) {
      if (result.getFreeSlots() > 3) {
        Tech armor = TechList.getBestTech(defenseTechs, "Armor plating");
        ShipComponent armorComp = null;
        if (armor != null) {
          armorComp = ShipComponentFactory.createByName(armor.getComponent());
          result.addComponent(armorComp);
        }
      }
      if (result.getFreeSlots() > 3) {
        Tech shield = TechList.getBestTech(defenseTechs, "Shield");
        ShipComponent shieldComp = null;
        if (shield != null) {
          shieldComp = ShipComponentFactory.createByName(
              shield.getComponent());
          if (shieldComp.getEnergyRequirement() <= result.getFreeEnergy()) {
            result.addComponent(shieldComp);
          }
        }
      }
    } else if (attitude == Attitude.SCIENTIFIC) {
      Tech[] electricsTechs = player.getTechList()
          .getListForType(TechType.Electrics);
      Tech elecTech = TechList.getBestTech(electricsTechs, "Scanner");
      if (elecTech != null) {
        ShipComponent scanner = ShipComponentFactory.createByName(
            elecTech.getComponent());
        if (scanner.getEnergyRequirement() <= result.getFreeEnergy()) {
          result.addComponent(scanner);
        }
      }
    } else if (attitude == Attitude.BACKSTABBING) {
      Tech[] electricsTechs = player.getTechList()
          .getListForType(TechType.Electrics);
      Tech elecTech = TechList.getBestTech(electricsTechs, "Cloaking device");
      if (elecTech != null) {
        ShipComponent cloack = ShipComponentFactory.createByName(
            elecTech.getComponent());
        if (cloack.getEnergyRequirement() <= result.getFreeEnergy()) {
          result.addComponent(cloack);
        }
      }
    } // Every one else is keeping freighter as empty as possible
    if (result.getFreeSlots() > 2 && (attitude == Attitude.AGGRESSIVE
        || attitude == Attitude.MILITARISTIC
        || attitude == Attitude.BACKSTABBING
        || attitude == Attitude.LOGICAL)) {
      // Adding espionage to freighters
      Tech[] electricsTechs = player.getTechList()
          .getListForType(TechType.Electrics);
      Tech elecTech = TechList.getBestTech(electricsTechs,
          "Espionage module");
      if (elecTech != null) {
        ShipComponent spyModule = ShipComponentFactory.createByName(
            elecTech.getComponent());
        if (spyModule.getEnergyRequirement() <= result.getFreeEnergy()) {
          result.addComponent(spyModule);
        }
      }
    }
    if (player.getRace() == SpaceRace.SMAUGIRIANS
        && result.getFreeSlots() > 3
        && (player.getTechList().hasTech("Privateer Mk1")
            || player.getTechList().hasTech("Privateer Mk2")
            || player.getTechList().hasTech("Privateer Mk3"))) {
      result.addComponent(ShipComponentFactory.createByName(
          "Privateer module"));
    }
    return result;
  }

  /**
   * Design new Starbase for certain size
   * @param player Player doing the design
   * @param size Ship Size
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  public static ShipDesign createStarbase(final PlayerInfo player,
      final ShipSize size) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    ShipHull hull = null;
    int value = 0;
    for (Tech tech : hullTechs) {
      ShipHull tempHull = ShipHullFactory.createByName(tech.getHull(),
          player.getRace());
      if (tempHull == null) {
        continue;
      }
      if (tempHull.getHullType() == ShipHullType.STARBASE
          && tempHull.getSize() == size) {
        int tempValue = tempHull.getMaxSlot() * tempHull.getSlotHull();
        if (tempValue > value) {
          value = tempValue;
          hull = tempHull;
        }
      }
    }
    if (hull != null) {
      result = createStarbase(player, hull);
    }
    return result;
  }

  /**
   * Design new Starbase for certain size
   * @param player Player doing the design
   * @param hull ShipHull used in design.
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  public static ShipDesign createStarbase(final PlayerInfo player,
      final ShipHull hull) {
    ShipDesign result = null;
    if (hull != null) {
      result = new ShipDesign(hull);
      String[] part = hull.getName().split("Mk");
      result.setName(
          part[0].trim() + " Mk" + (player.getShipStatHighestNumber(
              part[0]) + 1));
      ShipComponent engine = ShipComponentFactory
          .createByName(player.getTechList().getBestEngine().getComponent());
      result.addComponent(engine);
      ShipComponent power = ShipComponentFactory.createByName(
          player.getTechList().getBestEnergySource().getComponent());
      result.addComponent(power);
      if (hull.getSize() != ShipSize.SMALL || player.isBoard()) {
        ShipComponent weapon = ShipComponentFactory
            .createByName(player.getTechList().getBestWeapon().getComponent());
        if (!hull.getName().equals("Artificial planet")) {
          result.addComponent(weapon);
        }
      }

      Tech[] defenseTechs = player.getTechList()
          .getListForType(TechType.Defense);
      Tech[] electricsTechs = player.getTechList()
          .getListForType(TechType.Electrics);
      Tech shield = TechList.getBestTech(defenseTechs, "Shield");
      Tech armor = TechList.getBestTech(defenseTechs, "Armor plating");
      Tech shieldGen = TechList.getBestTech(electricsTechs, "Shield generator");
      Tech starbaseLab = player.getTechList().getBestStarbaseLab();
      Tech starbaseCult = player.getTechList().getBestStarbaseCulture();
      Tech starbaseCred = player.getTechList().getBestStarbaseCredit();
      Tech starbaseFleet = player.getTechList().getBestStarbaseFleetCap();
      ShipComponent shieldComp = null;
      ShipComponent shieldGenComp = null;
      ShipComponent armorComp = null;
      ArrayList<ShipComponent> components = new ArrayList<>();
      ArrayList<ShipComponent> modules = new ArrayList<>();
      if (shield != null) {
        shieldComp = ShipComponentFactory.createByName(shield.getComponent());
        components.add(shieldComp);
      }
      if (shieldGen != null) {
        shieldGenComp = ShipComponentFactory
            .createByName(shieldGen.getComponent());
        components.add(shieldGenComp);
      }
      if (armor != null) {
        armorComp = ShipComponentFactory.createByName(armor.getComponent());
        components.add(armorComp);
      }
      if (shieldComp != null
          && result.getFreeEnergy() >= shieldComp.getEnergyRequirement()) {
        result.addComponent(shieldComp);
      } else if (armorComp != null) {
        result.addComponent(armorComp);
      }
      ShipComponent credComp = null;
      if (starbaseCred != null) {
        credComp = ShipComponentFactory
            .createByName(starbaseCred.getComponent());
        components.add(credComp);
        modules.add(credComp);
      }
      ShipComponent cultComp = null;
      if (starbaseCult != null) {
        cultComp = ShipComponentFactory
            .createByName(starbaseCult.getComponent());
        components.add(cultComp);
        modules.add(cultComp);
      }
      ShipComponent labComp = null;
      if (starbaseLab != null) {
        labComp = ShipComponentFactory
            .createByName(starbaseLab.getComponent());
        components.add(labComp);
        modules.add(labComp);
      }
      ShipComponent fleetComp = null;
      if (starbaseFleet != null) {
        fleetComp = ShipComponentFactory
            .createByName(starbaseFleet.getComponent());
        components.add(fleetComp);
        modules.add(fleetComp);
      }
      if (!hull.getName().equals("Artificial planet")) {
        Tech weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_BEAM);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_ECM_TORPEDO);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_HE_MISSILE);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_PHOTON_TORPEDO);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_RAILGUN);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.MULTICANNON);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.PLASMA_CANNON);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.ION_CANNON);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        Tech elecTech = TechList.getBestTech(electricsTechs,
            "Targeting computer");
        if (elecTech != null) {
          components
              .add(ShipComponentFactory.createByName(elecTech.getComponent()));
        }
      }
      Tech elecTech = TechList.getBestTech(electricsTechs, "Jammer");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs, "Scanner");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs, "Cloaking device");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs, "LR scanner");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      if (modules.size() > 0) {
        int index = DiceGenerator.getRandom(modules.size() - 1);
        result.addComponent(modules.get(index));
      }
      int[] componentScores = new int[components.size()];
      int safetyCount = 500;
      while (result.getFreeSlots() > 0 && safetyCount > 0) {
        safetyCount--;
        componentScores = scoreComponents(result, player, components);
        int sum = 0;
        for (int i = 0; i < componentScores.length; i++) {
          if (componentScores[i] > 0) {
            sum = sum + componentScores[i];
          }
        }
        int choice = DiceGenerator.getRandom(sum);
        int total = 0;
        for (int i = 0; i < componentScores.length; i++) {
          if (componentScores[i] > 0) {
            if (choice < total + componentScores[i]) {
              if (result.getFreeEnergy() >= components.get(i)
                  .getEnergyRequirement()) {
                result.addComponent(components.get(i));
              } else if (result.getFreeSlots() > 1 && power.getEnergyResource()
                  + result.getFreeEnergy() >= components.get(i)
                      .getEnergyRequirement()) {
                result.addComponent(components.get(i));
                result.addComponent(power);
              }
              break;
            }
            total = total + componentScores[i];
          }
        }

      }

    }
    return result;
  }

  /**
   * Design new Orbital for certain size
   * @param player Player doing the design
   * @param size Ship Size
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  public static ShipDesign createOrbital(final PlayerInfo player,
      final ShipSize size) {
    ShipDesign result = null;
    Tech[] hullTechs = player.getTechList().getListForType(TechType.Hulls);
    ShipHull hull = null;
    int value = 0;
    for (Tech tech : hullTechs) {
      ShipHull tempHull = ShipHullFactory.createByName(tech.getHull(),
          player.getRace());
      if (tempHull == null) {
        continue;
      }
      if (tempHull.getHullType() == ShipHullType.ORBITAL
          && tempHull.getSize() == size) {
        int tempValue = tempHull.getMaxSlot() * tempHull.getSlotHull();
        if (tempValue > value) {
          value = tempValue;
          hull = tempHull;
        }
      }
    }
    if (hull != null) {
      result = createOrbital(player, hull);
    }
    return result;
  }

  /**
   * Design new Orbital for certain size
   * @param player Player doing the design
   * @param hull ShipHull used in design.
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  public static ShipDesign createOrbital(final PlayerInfo player,
      final ShipHull hull) {
    ShipDesign result = null;
    boolean military = false;
    if (hull != null) {
      result = new ShipDesign(hull);
      String[] part = hull.getName().split("Mk");
      result.setName(
          part[0].trim() + " Mk" + (player.getShipStatHighestNumber(
              part[0]) + 1));
      ShipComponent power = ShipComponentFactory.createByName(
          player.getTechList().getBestEnergySource().getComponent());
      result.addComponent(power);
      int militaryChance = 50;
      if (hull.getSize() == ShipSize.SMALL) {
        militaryChance = 10;
      }
      if (DiceGenerator.getRandom(99) < militaryChance) {
        military = true;
      }
      if (player.getRace() == SpaceRace.ALTEIRIANS) {
        military = true;
        // Alteirians will lose planet if orbital is lost.
      }
      if (military || player.isBoard()) {
        ShipComponent weapon = ShipComponentFactory
            .createByName(player.getTechList().getBestWeapon().getComponent());
          result.addComponent(weapon);
      }

      Tech[] defenseTechs = player.getTechList()
          .getListForType(TechType.Defense);
      Tech[] electricsTechs = player.getTechList()
          .getListForType(TechType.Electrics);
      Tech shield = TechList.getBestTech(defenseTechs, "Shield");
      Tech armor = TechList.getBestTech(defenseTechs, "Armor plating");
      Tech shieldGen = TechList.getBestTech(electricsTechs, "Shield generator");
      Tech starbaseLab = player.getTechList().getBestStarbaseLab();
      Tech starbaseCult = player.getTechList().getBestStarbaseCulture();
      Tech starbaseCred = player.getTechList().getBestStarbaseCredit();
      Tech starbaseFleet = player.getTechList().getBestStarbaseFleetCap();
      ShipComponent shieldComp = null;
      ShipComponent shieldGenComp = null;
      ShipComponent armorComp = null;
      ArrayList<ShipComponent> components = new ArrayList<>();
      ArrayList<ShipComponent> modules = new ArrayList<>();
      if (shield != null) {
        shieldComp = ShipComponentFactory.createByName(shield.getComponent());
        components.add(shieldComp);
      }
      if (shieldGen != null) {
        shieldGenComp = ShipComponentFactory
            .createByName(shieldGen.getComponent());
        components.add(shieldGenComp);
      }
      if (armor != null) {
        armorComp = ShipComponentFactory.createByName(armor.getComponent());
        components.add(armorComp);
      }
      if (military) {
        if (shieldComp != null
            && result.getFreeEnergy() >= shieldComp.getEnergyRequirement()) {
          result.addComponent(shieldComp);
        } else if (armorComp != null) {
          result.addComponent(armorComp);
        }
      }
      ShipComponent credComp = null;
      if (starbaseCred != null) {
        credComp = ShipComponentFactory
            .createByName(starbaseCred.getComponent());
        components.add(credComp);
        modules.add(credComp);
      }
      ShipComponent cultComp = null;
      if (starbaseCult != null) {
        cultComp = ShipComponentFactory
            .createByName(starbaseCult.getComponent());
        components.add(cultComp);
        modules.add(cultComp);
      }
      ShipComponent labComp = null;
      if (starbaseLab != null) {
        labComp = ShipComponentFactory
            .createByName(starbaseLab.getComponent());
        components.add(labComp);
        modules.add(labComp);
      }
      ShipComponent fleetComp = null;
      if (starbaseFleet != null) {
        fleetComp = ShipComponentFactory
            .createByName(starbaseFleet.getComponent());
        components.add(fleetComp);
        modules.add(fleetComp);
      }
      if (military) {
        Tech weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_BEAM);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_ECM_TORPEDO);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_HE_MISSILE);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_PHOTON_TORPEDO);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.WEAPON_RAILGUN);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.MULTICANNON);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.PLASMA_CANNON);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        weapTech = player.getTechList()
            .getBestWeapon(ShipComponentType.ION_CANNON);
        if (weapTech != null) {
          components
              .add(ShipComponentFactory.createByName(weapTech.getComponent()));
        }
        Tech elecTech = TechList.getBestTech(electricsTechs,
            "Targeting computer");
        if (elecTech != null) {
          components
              .add(ShipComponentFactory.createByName(elecTech.getComponent()));
        }
      }
      Tech elecTech = TechList.getBestTech(electricsTechs, "Scanner");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      elecTech = TechList.getBestTech(electricsTechs, "LR scanner");
      if (elecTech != null) {
        components
            .add(ShipComponentFactory.createByName(elecTech.getComponent()));
      }
      if (modules.size() > 0) {
        int index = DiceGenerator.getRandom(modules.size() - 1);
        result.addComponent(modules.get(index));
      }
      int[] componentScores = new int[components.size()];
      int safetyCount = 500;
      while (result.getFreeSlots() > 0 && safetyCount > 0) {
        safetyCount--;
        componentScores = scoreComponents(result, player, components);
        int sum = 0;
        for (int i = 0; i < componentScores.length; i++) {
          if (componentScores[i] > 0) {
            sum = sum + componentScores[i];
          }
        }
        int choice = DiceGenerator.getRandom(sum);
        int total = 0;
        for (int i = 0; i < componentScores.length; i++) {
          if (componentScores[i] > 0) {
            if (choice < total + componentScores[i]) {
              if (result.getFreeEnergy() >= components.get(i)
                  .getEnergyRequirement()) {
                result.addComponent(components.get(i));
              } else if (result.getFreeSlots() > 1 && power.getEnergyResource()
                  + result.getFreeEnergy() >= components.get(i)
                      .getEnergyRequirement()) {
                result.addComponent(components.get(i));
                result.addComponent(power);
              }
              break;
            }
            total = total + componentScores[i];
          }
        }

      }

    }
    return result;
  }

  /**
   * Design new Minor orbital
   * @param player Player doing the design
   * @return ShipDesign if doable. Null if not doable for that size.
   */
  public static ShipDesign createMinorOrbital(final PlayerInfo player) {
    ShipDesign result = null;
    if (player.getTechList().hasTech("Minor orbital")) {
      ShipHull hull = ShipHullFactory.createByName("Minor orbital",
          player.getRace());
      result = new ShipDesign(hull);
      String[] part = hull.getName().split("Mk");
      result.setName(
          part[0].trim() + " Mk" + (player.getShipStatHighestNumber(
              part[0]) + 1));
      ShipComponent power = ShipComponentFactory.createByName(
          player.getTechList().getBestEnergySource().getComponent());
      result.addComponent(power);
      ShipComponent weapon = ShipComponentFactory
          .createByName(player.getTechList().getBestWeapon().getComponent());
      result.addComponent(weapon);
      Tech[] defenseTechs = player.getTechList()
          .getListForType(TechType.Defense);
      Tech shield = TechList.getBestTech(defenseTechs, "Shield");
      Tech armor = TechList.getBestTech(defenseTechs, "Armor plating");
      ShipComponent shieldComp = null;
      ShipComponent armorComp = null;
      if (shield != null) {
        shieldComp = ShipComponentFactory.createByName(shield.getComponent());
      }
      if (armor != null) {
        armorComp = ShipComponentFactory.createByName(armor.getComponent());
      }
      if (shieldComp != null
          && result.getFreeEnergy() >= shieldComp.getEnergyRequirement()) {
        result.addComponent(shieldComp);
      } else if (armorComp != null) {
        result.addComponent(armorComp);
      }

    }
    return result;
  }

  /**
   * Generate random Orbital
   * @return Orbital.
   */
  public static Ship generateRandomOrbital() {
    PlayerInfo info = new PlayerInfo(
        SpaceRace.values()[DiceGenerator.getRandom(
            SpaceRace.values().length - 1)]);
    info.getTechList().addTech(TechFactory.createHullTech("Small orbital", 2));
    info.getTechList().addTech(TechFactory.createHullTech("Medium orbital", 4));
    info.getTechList().addTech(TechFactory.createImprovementTech(
        "Starbase music hall", 2));
    ShipSize size = ShipSize.SMALL;
    if (DiceGenerator.getRandom(1) == 1) {
      size = ShipSize.MEDIUM;
    }
    ShipDesign design = ShipGenerator.createOrbital(info, size);
    if (design != null) {
      return new Ship(design);
    }
    ErrorLogger.debug("Failed to create orbital.");
    return null;
  }
}
