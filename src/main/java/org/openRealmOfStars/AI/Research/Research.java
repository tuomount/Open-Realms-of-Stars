package org.openRealmOfStars.AI.Research;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.generator.ShipGenerator;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.utilities.DiceGenerator;

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
 * AI handling for research
 *
 */

public final class Research {

  /**
   * Hiding the default constructor
   */
  private Research() {
    // Nothing to do
  }

  /**
   * Default focus level when dividing focus evenly. Well one tech
   * level gets then 20 but all others 16.
   */
  public static final int DEFAULT_FOCUS_LEVEL = 16;

  /**
   * Default focus level when dividing focus evenly. This is the
   * higher default one.
   */
  public static final int HIGH_FOCUS_LEVEL = 20;

  /**
   * Default focus level when dividing focus evenly. This is the
   * highest default one.
   */
  public static final int VERY_HIGH_FOCUS_LEVEL = 40;

  /**
   * Default focus level when some races have more than one high focuses.
   */
  public static final int LOW_FOCUS_LEVEL = 12;

  /**
   * Handle new ship designs for AI
   * @param info PlayerInfo
   */
  public static void handleShipDesigns(final PlayerInfo info) {
    handleBattleShipDesign(info, ShipSize.SMALL);
    handleBattleShipDesign(info, ShipSize.MEDIUM);
    handleBattleShipDesign(info, ShipSize.LARGE);
    handleBattleShipDesign(info, ShipSize.HUGE);
    handleTrooperShipDesign(info);
  }

  /**
   * Handle Battle ship design for AI for certain size
   * @param info Player
   * @param size ShipSize to handle
   */
  private static void handleBattleShipDesign(final PlayerInfo info,
      final ShipSize size) {
    ShipDesign design = ShipGenerator.createBattleShip(info, size);
    if (design != null) {
      ShipStat[] stats = info.getShipStatList();
      boolean notFound = true;
      for (ShipStat stat : stats) {
        if (stat.getDesign().getHull().getSize() == size && stat.getDesign()
            .getHull().getHullType() == ShipHullType.NORMAL
            && !stat.isObsolete()) {
          notFound = false;
          if (design.getTotalMilitaryPower() > stat.getDesign()
              .getTotalMilitaryPower() && !stat.isObsolete()) {
            stat.setObsolete(true);
            ShipStat ship = new ShipStat(design);
            info.addShipStat(ship);
            break;
          }
        }
      }
      if (notFound) {
        ShipStat ship = new ShipStat(design);
        info.addShipStat(ship);
      }
    }

  }

  /**
   * Handle Trooper ship design for AI
   * @param info Player
   */
  private static void handleTrooperShipDesign(final PlayerInfo info) {
    ShipDesign design = ShipGenerator.createColony(info, true);
    if (design != null) {
      ShipStat[] stats = info.getShipStatList();
      boolean notFound = true;
      for (ShipStat stat : stats) {
        if (stat.getDesign().getHull().getHullType() == ShipHullType.FREIGHTER
            && stat.getDesign()
                .gotCertainType(ShipComponentType.PLANETARY_INVASION_MODULE)) {
          notFound = false;
          if (design.getTotalTrooperPower() > stat.getDesign()
              .getTotalTrooperPower()) {
            stat.setObsolete(true);
            ShipStat ship = new ShipStat(design);
            info.addShipStat(ship);
            break;
          }
        }
      }
      if (notFound) {
        ShipStat ship = new ShipStat(design);
        info.addShipStat(ship);
      }
    }

  }

  /**
   * Check Update combat tech
   * @param info PlayerInfo
   * @param attitude Player's attitude
   */
  protected static void checkUpdateCombat(final PlayerInfo info,
      final Attitude attitude) {
    int level = info.getTechList().getTechLevel(TechType.Combat);
    Tech[] missingTechs = info.getTechList().getListMissingTech(TechType.Combat,
        level);
    boolean mustHaveTech = false;
    boolean missileTech = false;
    for (Tech missingTech : missingTechs) {
      if (missingTech.getName().contains("module")
          || missingTech.getName().contains("Orbital")) {
        mustHaveTech = true;
      }
      if (missingTech.getName().contains("missile")) {
        missileTech = true;
      }
    }
    if (!mustHaveTech) {
      if (attitude == Attitude.AGGRESSIVE && missingTechs.length == 1) {
        level = level + 1;
        info.getTechList().setTechLevel(TechType.Combat, level);
      } else if (attitude == Attitude.MILITARISTIC && !missileTech) {
        level = level + 1;
        info.getTechList().setTechLevel(TechType.Combat, level);
      } else if (attitude == Attitude.SCIENTIFIC
          && DiceGenerator.getRandom(99) < 20) {
        level = level + 1;
        info.getTechList().setTechLevel(TechType.Combat, level);
      } else if (DiceGenerator.getRandom(99) < 30) {
        level = level + 1;
        info.getTechList().setTechLevel(TechType.Combat, level);
      }
    }
  }
  /**
   * Check Update defense tech
   * @param info PlayerInfo
   * @param attitude Player's attitude
   */
  protected static void checkUpdateDefense(final PlayerInfo info,
      final Attitude attitude) {
    int level = info.getTechList().getTechLevel(TechType.Defense);
    Tech[] missingTechs = info.getTechList().getListMissingTech(
        TechType.Defense, level);
    boolean turretTech = false;
    boolean generatorTech = false;
    boolean armorTech = false;
    for (Tech missingTech : missingTechs) {
      if (missingTech.getName().contains("turret")) {
        turretTech = true;
      }
      if (missingTech.getName().contains("generator")) {
        generatorTech = true;
      }
      if (missingTech.getName().contains("Armor")) {
        armorTech = true;
      }
    }
    if (attitude == Attitude.AGGRESSIVE && missingTechs.length == 1) {
      level = level + 1;
      info.getTechList().setTechLevel(TechType.Defense, level);
    } else if (attitude == Attitude.MILITARISTIC && !generatorTech
        && !armorTech) {
        level = level + 1;
        info.getTechList().setTechLevel(TechType.Defense, level);
    } else if (attitude == Attitude.PEACEFUL && !turretTech) {
      level = level + 1;
      info.getTechList().setTechLevel(TechType.Defense, level);
    } else if (attitude == Attitude.SCIENTIFIC
        && DiceGenerator.getRandom(99) < 20) {
      level = level + 1;
      info.getTechList().setTechLevel(TechType.Defense, level);
    } else if (DiceGenerator.getRandom(99) < 30) {
      level = level + 1;
      info.getTechList().setTechLevel(TechType.Defense, level);
    }
  }
  /**
   * Handle research for AI player
   * @param info PlayerInfo
   */
  public static void handle(final PlayerInfo info) {
    Tech[] techs = info.getTechList().getList();
    boolean basicLab = false;
    for (int i = 0; i < techs.length; i++) {
      if (techs[i].getName().equals("Basic lab")) {
        basicLab = true;
      }
    }
    if (!basicLab) {
      info.getTechList().setTechFocus(TechType.Improvements, 40);
    } else {
      switch (info.getRace()) {
      case HUMAN: {
        info.getTechList().setTechFocus(TechType.Combat,
            HIGH_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Defense, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Hulls, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Propulsion,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Improvements,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Electrics,
            DEFAULT_FOCUS_LEVEL);
        break;
      }
      case CENTAURS: {
        info.getTechList().setTechFocus(TechType.Combat, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Defense, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Propulsion,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Improvements,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Electrics,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Hulls, HIGH_FOCUS_LEVEL);
        break;
      }
      case GREYANS: {
        info.getTechList().setTechFocus(TechType.Combat, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Defense, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Hulls, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Propulsion,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Electrics,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Improvements,
            HIGH_FOCUS_LEVEL);
        break;
      }
      case SPORKS: {
        info.getTechList().setTechFocus(TechType.Improvements,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Electrics, LOW_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Propulsion, LOW_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Hulls,  HIGH_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Defense, HIGH_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Combat, HIGH_FOCUS_LEVEL);
        break;
      }
      case MECHIONS: {
        info.getTechList().setTechFocus(TechType.Defense, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Hulls, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Improvements,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Electrics, LOW_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Propulsion, HIGH_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Combat, HIGH_FOCUS_LEVEL);
        break;
      }
      default: {
        //HUMAN
        info.getTechList().setTechFocus(TechType.Combat,
            HIGH_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Defense, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Hulls, DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Propulsion,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Improvements,
            DEFAULT_FOCUS_LEVEL);
        info.getTechList().setTechFocus(TechType.Electrics,
            DEFAULT_FOCUS_LEVEL);
        break;
      }
      }
      Attitude attitude = info.getAiAttitude();
      if (info.getTechList().isUpgradeable(TechType.Combat)) {
        checkUpdateCombat(info, attitude);
      }
      if (info.getTechList().isUpgradeable(TechType.Defense)) {
        checkUpdateDefense(info, attitude);
      }
    }
  }
}
