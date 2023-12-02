package org.openRealmOfStars.ai.mission;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
 * Copyright (C) 2023 BottledByte
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

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.diplomacy.DiplomaticTrade;
import org.openRealmOfStars.player.diplomacy.negotiation.NegotiationType;
import org.openRealmOfStars.player.espionage.EspionageUtility;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.EspionageMission;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * Handles execution and results of individual espionage missions.
 */
public final class EspionageHandling {

  /** Hidden constructor */
  private EspionageHandling() {
  }

  /**
   * Get espionage risk factor for AI attitude.
   * @param attitude Attitude
   * @return Risk factor from 1 to 6. 6 is taking highest risks.
   */
  private static int getEspionageRiskFactor(final Attitude attitude) {
    int riskFactor = 2;
    switch (attitude) {
      case BACKSTABBING: {
        riskFactor = 6;
        break;
      }
      case AGGRESSIVE: {
        riskFactor = 5;
        break;
      }
      case MILITARISTIC: {
        riskFactor = 4;
        break;
      }
      case EXPANSIONIST: {
        riskFactor = 3;
        break;
      }
      case DIPLOMATIC:
      case PEACEFUL: {
        riskFactor = 1;
        break;
      }
      default:
      case LOGICAL:
      case SCIENTIFIC:
      case MERCHANTICAL: {
        riskFactor = 2;
        break;
      }
    }
    return riskFactor;
  }

  /**
   * Score espionage mission
   * @param info Realm doing espionage
   * @param planet Planet where to do espionage
   * @param allowedTypes Allowed espionage types
   * @param fleet Fleet doing espionage
   * @param starMap Full Starmap
   * @return EspionageMission, never null.
   */
  public static EspionageMission scoreEspionageMission(final PlayerInfo info,
      final Planet planet, final EspionageMission[] allowedTypes,
      final Fleet fleet, final StarMap starMap) {
    int[] scores = new int[allowedTypes.length];
    int target = planet.getPlanetOwnerIndex();
    int infoIndex = starMap.getPlayerList().getIndex(info);
    Attitude attitude = planet.getPlanetPlayerInfo().getAiAttitude();
    int riskFactor = getEspionageRiskFactor(attitude);
    int sum = 0;
    for (int i = 0; i < allowedTypes.length; i++) {
      int success = EspionageUtility.calculateSuccess(planet, fleet,
          allowedTypes[i]);
      int caught = EspionageUtility.calculateDetectionSuccess(planet,
          fleet, allowedTypes[i]);
      if (allowedTypes[i] == EspionageMission.ASSASSIN_GOVERNOR
          || allowedTypes[i] == EspionageMission.DEMOLISH_BUILDING) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 50;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 0;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 40;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 25;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 10;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 10;
        }
      }
      if (allowedTypes[i] == EspionageMission.STEAL_CREDIT) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 50;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 20;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 40;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 25;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 40;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 10;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 20;
        }
      }
      if (allowedTypes[i] == EspionageMission.STEAL_TECH) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case EXPANSIONIST:
          case BACKSTABBING: {
            scores[i] = 40;
            break;
          }
          case DIPLOMATIC:
          case MERCHANTICAL:
          case PEACEFUL: {
            scores[i] = 30;
            break;
          }
          default:
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 50;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 20;
        }
      }
      if (allowedTypes[i] == EspionageMission.TERRORIST_ATTACK) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 50;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 0;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 20;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 15;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 5;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 10;
        }
      }
      if (allowedTypes[i] == EspionageMission.SABOTAGE
          || allowedTypes[i] == EspionageMission.DEMOLISH_BUILDING) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 60;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 5;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 40;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 30;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 10;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 10;
        }
      }
      if (allowedTypes[i] == EspionageMission.FALSE_FLAG) {
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC:
          case BACKSTABBING: {
            scores[i] = 60;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 5;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 40;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 30;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 10;
            break;
          }
        }
        int difference = starMap.getMilitaryDifference(infoIndex, target,
            info.getDiplomacy().isWar(target));
        if (difference > 100) {
          scores[i] = scores[i] + 30;
        } else if (difference > 80) {
          scores[i] = scores[i] + 20;
        } else if (difference > 50) {
          scores[i] = scores[i] + 10;
        } else if (difference > 0) {
          scores[i] = scores[i] + 0;
        } else {
          scores[i] = scores[i] - 15;
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] + 20;
        }
        if (info.getDiplomacy().isPeace(target)) {
          scores[i] = scores[i] + 5;
        }
        if (info.getDiplomacy().isTradeAlliance(target)) {
          scores[i] = scores[i] - 5;
        }
        if (info.getDiplomacy().isDefensivePact(target)) {
          scores[i] = scores[i] - 10;
        }
        if (info.getDiplomacy().isAlliance(target)) {
          scores[i] = scores[i] - 10;
        }
      }
      if (allowedTypes[i] == EspionageMission.DEADLY_VIRUS) {
        switch (attitude) {
          case BACKSTABBING: {
            scores[i] = 40;
            break;
          }
          case AGGRESSIVE: {
            scores[i] = 20;
            break;
          }
          case MILITARISTIC: {
            scores[i] = 10;
            break;
          }
          default:
          case MERCHANTICAL:
          case LOGICAL:
          case SCIENTIFIC:
          case EXPANSIONIST:
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 0;
            break;
          }
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = scores[i] + 5;
          Mission mission = info.getMissions().getAttackMission(
              planet.getName());
          if (mission != null && mission.getPhase() == MissionPhase.TREKKING) {
            scores[i] = scores[i] + 15;
          }
          if (mission != null && mission.getPhase() == MissionPhase.EXECUTING) {
            scores[i] = scores[i] + 20;
          }
        }
        if (info.getDiplomacy().isPeace(target)) {
          scores[i] = scores[i] - 10;
        }
        if (info.getDiplomacy().isTradeAlliance(target)) {
          scores[i] = scores[i] - 15;
        }
        if (info.getDiplomacy().isDefensivePact(target)) {
          scores[i] = scores[i] - 20;
        }
        if (info.getDiplomacy().isAlliance(target)) {
          scores[i] = scores[i] - 20;
        }
      }
      int likingOpposite = 1;
      if (allowedTypes[i] == EspionageMission.GAIN_TRUST) {
        // Liking bonus is actually reversed for gain trust mission.
        likingOpposite = -1;
        switch (attitude) {
          case AGGRESSIVE:
          case MILITARISTIC: {
            scores[i] = 0;
            break;
          }
          case BACKSTABBING: {
            scores[i] = 40;
            break;
          }
          case DIPLOMATIC:
          case PEACEFUL: {
            scores[i] = 60;
            break;
          }
          case EXPANSIONIST: {
            scores[i] = 20;
            break;
          }
          case LOGICAL:
          case SCIENTIFIC: {
            scores[i] = 40;
            break;
          }
          default:
          case MERCHANTICAL: {
            scores[i] = 40;
            break;
          }
        }
        if (info.getDiplomacy().isTradeEmbargo(target)) {
          scores[i] = scores[i] - 40;
        }
        if (planet.getPlanetPlayerInfo().getDiplomacy()
            .getLiking(infoIndex) < 0) {
          scores[i] = scores[i] + 80;
        }
        if (info.getDiplomacy().isWar(target)) {
          scores[i] = 0;
        }
      }
      // Adds liking for scores.
      switch (info.getDiplomacy().getLiking(planet.getPlanetOwnerIndex())) {
        case Diplomacy.HATE: {
          scores[i] = scores[i] + 30 * likingOpposite;
          break;
        }
        case Diplomacy.DISLIKE: {
          scores[i] = scores[i] + 15 * likingOpposite;
          break;
        }
        default:
        case Diplomacy.NEUTRAL: {
          scores[i] = scores[i] + 5 * likingOpposite;
          break;
        }
        case Diplomacy.LIKE: {
          scores[i] = scores[i] - 10 * likingOpposite;
          break;
        }
        case Diplomacy.FRIENDS: {
          scores[i] = scores[i] - 30 * likingOpposite;
          break;
        }
      }
      // Subtract possible caught chance adjusted by risk factor
      scores[i] = scores[i] - (caught / riskFactor);
      // Scores should not go above success rate.
      if (scores[i] > success) {
        scores[i] = success;
      }
      if (scores[i] < 0) {
        scores[i] = 0;
      }
      sum = sum + scores[i];
    }
    if (sum > 0) {
      int rand = DiceGenerator.getRandom(sum - 1);
      int total = 0;
      for (int i = 0; i < scores.length; i++) {
        if (rand < total + scores[i]) {
          return allowedTypes[i];
        }
        total = total + scores[i];
      }
    }
    // Just making sure that null is never returned.
    EspionageMission selectedType = allowedTypes[DiceGenerator.getRandom(
        allowedTypes.length - 1)];
    return selectedType;
  }

  /**
   * Tries to add a diplomacy bonus by player to given bonus list.
   * @param bonuses DiplomacyBonusList to try add bonus to. Can be null.
   * @param newBonus bonus to add
   * @param who who adds the bonus
   */
  private static void tryAddDiplomacyBonus(final DiplomacyBonusList bonuses,
      final DiplomacyBonusType newBonus, final PlayerInfo who) {
    if (bonuses == null) {
      return;
    }
    bonuses.addBonus(newBonus, who.getRace());
  }

  /**
   * Execute behavior that should happen when Leader gets caught
   * during an espionage mission.
   * @param type EspionageMission type
   * @param planet Planet where to do espionage
   * @param fleet Fleet who is doing the espionage
   * @param info Realm who is doing the espionage
   * @param game Full game information.
   * @param espionageSucceed If true then espionage mission succeed, but now
   *        leader will also get caught.
   */
  public static void handleCaughtEspionage(final EspionageMission type,
      final Planet planet, final Fleet fleet, final PlayerInfo info,
      final Game game, final boolean espionageSucceed) {
    final var starmap = game.getStarMap();
    final var infoIndex = starmap.getPlayerList().getIndex(info);
    final var planetPlayer = planet.getPlanetPlayerInfo();
    final var planetEmpireName = planetPlayer.getEmpireName();
    final var spy = fleet.getCommander();
    final var spyCallName = spy.getCallName();
    final var spyEmpireName = info.getEmpireName();
    final var diplomacyWithSpy = planetPlayer.getDiplomacy()
        .getDiplomacyList(infoIndex);
    final boolean war = planetPlayer.getDiplomacy().isWar(infoIndex);
    final boolean tradeWar = planetPlayer.getDiplomacy()
        .isTradeEmbargo(infoIndex);

    // Texts and text templates
    final var txtCaughtBase = String.format(
        "%1$s from %2$s was caught by %3$s while doing espionage mission.",
        spyCallName, spyEmpireName, planetEmpireName);
    final var tplCaught = txtCaughtBase + " Main goal was %1$s. ";
    final var txtEscapedPrisonWealth = String.format(
        "%1$s was able to escape from %2$s prison time "
            + "by using massive amount of credits. ",
        spyCallName, planetEmpireName);
    final var txtPrisonBase = String.format(
        "%1$s decided to imprison %2$s",
        planetEmpireName, spyCallName);
    final var tplPrison = txtPrisonBase + " for %1$s.";
    final var txtEscapedExecutionWealth = String.format(
        "%1$s was able to escape from %2$s execution"
            + "by using massive amount of credits. ",
        spyCallName, planetEmpireName);
    final var txtExecuted = String.format(
        "%1$s was executed by %2$s.",
        spyCallName, planetEmpireName);
    final var txtExecutionWar = "Execution was done because of war times.";
    final var txtExecutionEmbargo = "Execution was done because of"
        + "revenge of trade war";

    var txtExecReasonGeneric = "";
    if (war) {
      txtExecReasonGeneric = txtExecutionWar;
    } else if (tradeWar) {
      txtExecReasonGeneric = txtExecutionEmbargo;
    }

    if (type == EspionageMission.GAIN_TRUST && diplomacyWithSpy != null) {
      diplomacyWithSpy.addBonus(DiplomacyBonusType.ESPIONAGE_BORDER_CROSS,
          planetPlayer.getRace());
      var endText = " Since espionage mission was gaining trust,"
          + " %1$s was released.";
      endText = String.format(endText, spyCallName);
      LeaderUtility.handleLeaderReleased(info, planet, fleet,
          txtCaughtBase + endText,
          starmap);
    }

    if (type == EspionageMission.STEAL_CREDIT) {
      tryAddDiplomacyBonus(diplomacyWithSpy,
          DiplomacyBonusType.ESPIONAGE_BORDER_CROSS, planetPlayer);
      var reason = "stealing credits";
      var startText = String.format(tplCaught, reason);
      Attitude attitude = planetPlayer.getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE
          || attitude == Attitude.BACKSTABBING
          || attitude == Attitude.MILITARISTIC
          || attitude == Attitude.MERCHANTICAL
          || war || tradeWar) {

        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText + txtEscapedExecutionWealth,
            startText + txtExecuted + txtExecReasonGeneric, game);
      } else {
        LeaderUtility.handleLeaderPrison(info, planet, fleet,
            startText + txtEscapedPrisonWealth,
            startText + String.format(tplPrison, reason),
            reason, 5, game);
      }
    }

    if (type == EspionageMission.STEAL_TECH) {
      tryAddDiplomacyBonus(diplomacyWithSpy,
          DiplomacyBonusType.ESPIONAGE_BORDER_CROSS, planetPlayer);
      var reason = "stealing technology";
      var startText = String.format(tplCaught, reason);
      Attitude attitude = planetPlayer.getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE
          || attitude == Attitude.BACKSTABBING
          || attitude == Attitude.MILITARISTIC
          || attitude == Attitude.SCIENTIFIC
          || war || tradeWar) {

        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText + txtEscapedExecutionWealth,
            startText + txtExecuted + txtExecReasonGeneric, game);
      } else {
        LeaderUtility.handleLeaderPrison(info, planet, fleet,
            startText + txtEscapedPrisonWealth,
            startText + String.format(tplPrison, reason),
            reason, 10, game);
      }
    }

    if (type == EspionageMission.SABOTAGE) {
      tryAddDiplomacyBonus(diplomacyWithSpy,
          DiplomacyBonusType.ESPIONAGE_BORDER_CROSS, planetPlayer);
      var reason = "sabotage";
      var startText = String.format(tplCaught, reason);
      Attitude attitude = planetPlayer.getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE
          || attitude == Attitude.BACKSTABBING
          || attitude == Attitude.MILITARISTIC
          || war || tradeWar) {

        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText + txtEscapedExecutionWealth,
            startText + txtExecuted + txtExecReasonGeneric, game);
      } else {
        LeaderUtility.handleLeaderPrison(info, planet, fleet,
            startText + txtEscapedPrisonWealth,
            startText + String.format(tplPrison, reason),
            reason, 8, game);
      }
    }

    if (type == EspionageMission.DEMOLISH_BUILDING) {
      tryAddDiplomacyBonus(diplomacyWithSpy,
          DiplomacyBonusType.ESPIONAGE_BORDER_CROSS, planetPlayer);
      var reason = "demolishing building";
      var startText = String.format(tplCaught, reason);
      Attitude attitude = planetPlayer.getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE
          || attitude == Attitude.BACKSTABBING
          || attitude == Attitude.MILITARISTIC
          || war || tradeWar) {

        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText + txtEscapedExecutionWealth,
            startText + txtExecuted + txtExecReasonGeneric, game);
      } else {
        LeaderUtility.handleLeaderPrison(info, planet, fleet,
            startText + txtEscapedPrisonWealth,
            startText + String.format(tplPrison, reason),
            reason, 10, game);
      }
    }

    if (type == EspionageMission.ASSASSIN_GOVERNOR) {
      tryAddDiplomacyBonus(diplomacyWithSpy,
          DiplomacyBonusType.ESPIONAGE_BORDER_CROSS, planetPlayer);
      var reason = "governor assassination";
      Attitude attitude = planetPlayer.getAiAttitude();
      if (attitude == Attitude.PEACEFUL
          && !war && !tradeWar) {
        LeaderUtility.handleLeaderReleased(info, planet, fleet,
            String.format(tplCaught, reason)
                + planetEmpireName + " decided to"
                + " release " + spyCallName
                + ".",
            starmap);
      } else {
        var startText = String.format(tplCaught, reason);
        LeaderUtility.handleLeaderKilled(info, planet, fleet,
            startText + txtEscapedExecutionWealth,
            startText + txtExecuted + txtExecReasonGeneric, game);
      }
    }

    if (type == EspionageMission.TERRORIST_ATTACK) {
      tryAddDiplomacyBonus(diplomacyWithSpy,
          DiplomacyBonusType.ESPIONAGE_BORDER_CROSS, planetPlayer);
      var reason = "terrorist attack on planet";
      var startText = String.format(tplCaught, reason);
      LeaderUtility.handleLeaderKilled(info, planet, fleet,
          startText + txtEscapedExecutionWealth,
          startText + txtExecuted + txtExecReasonGeneric, game);

      if (!info.getDiplomacy().isWar(planet.getPlanetOwnerIndex())) {
        causeWarFromEspionage(game, info, planet);
      }
    }

    if (type == EspionageMission.FALSE_FLAG) {
      tryAddDiplomacyBonus(diplomacyWithSpy,
          DiplomacyBonusType.ESPIONAGE_BORDER_CROSS, planetPlayer);
      Ship ship = null;
      var reason = "false flag mission on a planet";
      var startText = String.format(tplCaught, reason);
      if (!espionageSucceed) {
        ship = fleet.getShipForFalseFlag();
        startText = startText + " " + ship.getName()
            + " was destroyed during mission.";
      }
      var endText = "";
      int index = starmap.getPlayerList().getIndex(info);
      boolean casusBelli = planetPlayer.getDiplomacy()
          .hasCasusBelli(index);
      if (casusBelli) {
        endText = " Execution was done because of spy operation"
            + " was considered as act of war.";
      } else if (tradeWar) {
        endText = txtExecutionEmbargo;
      } else {
        endText = " Execution was done because of acts of conspiracy.";
      }
      LeaderUtility.handleLeaderKilled(info, planet, fleet,
          startText + txtEscapedExecutionWealth,
          startText + txtExecuted + endText, game);
      if (casusBelli) {
        // War will be made if defender has casus belli against attack
        // Attacker will any way declare it, so that reputation
        // drop goes to attacker.
        causeWarFromEspionage(game, info, planet);
      }
      if (ship != null) {
        fleet.removeShip(ship);
        info.getFleets().recalculateList();
      }
      if (spy != null && fleet.getNumberOfShip() == 0) {
        // Fleet's last ship was destroyed but commander
        // escaped from execution.
        spy.setJob(Job.UNASSIGNED);
      }
    }

    if (type == EspionageMission.DEADLY_VIRUS) {
      tryAddDiplomacyBonus(diplomacyWithSpy,
          DiplomacyBonusType.ESPIONAGE_BORDER_CROSS, planetPlayer);
      var startText = txtCaughtBase
          + " Main goal was spreading virus on planet. ";
      var endText = "";
      if (war) {
        endText = txtExecutionWar;
      } else if (tradeWar) {
        endText = txtExecutionEmbargo;
      } else {
        endText = " Spreading deadly virus was considered as attack against "
            + planetEmpireName + ".";
      }
      LeaderUtility.handleLeaderKilled(info, planet, fleet,
          startText + txtEscapedExecutionWealth,
          startText + txtExecuted + endText, game);
      if (!info.getDiplomacy().isWar(planet.getPlanetOwnerIndex())) {
        causeWarFromEspionage(game, info, planet);
      }
    }
  }

  /**
   * Cause war between spying and targeted empire,
   * as a consequence of espionage.
   *
   * @param game Game
   * @param info Player doing the espionage
   * @param planet Espionage target planet
   */
  private static void causeWarFromEspionage(final Game game,
      final PlayerInfo info, final Planet planet) {
    final var starmap = game.getStarMap();
    final var planetPlayer = planet.getPlanetPlayerInfo();

    DiplomaticTrade trade = new DiplomaticTrade(starmap,
        game.getPlayers().getIndex(info),
        game.getPlayers().getIndex(planetPlayer));
    trade.generateEqualTrade(NegotiationType.WAR);
    boolean casusBelli = info.getDiplomacy().hasCasusBelli(
        game.getPlayers().getIndex(planetPlayer));
    StarMapUtilities.addWarDeclatingReputation(starmap, info,
        planetPlayer);
    var newsData = NewsFactory.makeWarNews(info,
        planetPlayer, planet, starmap,
        casusBelli);
    starmap.getNewsCorpData().addNews(newsData);
    starmap.getHistory().addEvent(
        NewsFactory.makeDiplomaticEvent(planet, newsData));

    trade.doTrades();
    PlayerInfo defender = planetPlayer;
    String[] list = defender.getDiplomacy().activateDefensivePact(
        starmap, info);
    if (list != null) {
      starmap.getNewsCorpData().addNews(
          NewsFactory.makeDefensiveActivation(info, list));
    }
  }

  /**
   * Handles sabotage of a planet's spaceport due to espionage actions.
   * Does nothing if building is not a spaceport
   * @param planet Planet sabotaged
   * @param building Building sabotaged/destroyed
   * @param info Player to receive the news
   */
  private static void handleSpaceportSabotage(final Planet planet,
      final Building building, final PlayerInfo info) {
    if (!(planet.getUnderConstruction() instanceof Ship)
        || !building.getName().equals("Space port")) {
      return;
    }

    final var tplSpaceportDestroyed = "Space port at %1$s "
        + " was destroyed and ships cannot be built there any more. ";

    planet.setUnderConstruction(null);

    Message msgPort = new Message(MessageType.PLANETARY,
        String.format(tplSpaceportDestroyed, planet.getName()),
        Icons.getIconByName(Icons.ICON_HULL_TECH));
    msgPort.setCoordinate(planet.getCoordinate());
    msgPort.setMatchByString(planet.getName());
    info.getMsgList().addUpcomingMessage(msgPort);
  }

  /**
   * Handle successful espionage mission.
   * @param type EspionageMission type
   * @param planet Planet where to do espionage
   * @param fleet Fleet who is doing the espionage
   * @param info Realm who is doing the espionage
   * @param game Full game information.
   */
  public static void handleSuccessfulEspionage(final EspionageMission type,
      final Planet planet, final Fleet fleet, final PlayerInfo info,
      final Game game) {
    final var starmap = game.getStarMap();
    final var history = starmap.getHistory();
    final var newsCorp = starmap.getNewsCorpData();
    final var spy = fleet.getCommander();
    final var spyCallName = spy.getCallName();
    final var planetPlayer = planet.getPlanetPlayerInfo();
    final var planetEmpireName = planetPlayer.getEmpireName();
    final var spyEmpireName = info.getEmpireName();

    final var txtGainedVia = "This was gained via espionage mission.";
    final var txtDoneVia = "This was gained via espionage mission.";
    final var tplGainTrust = "%1$s from %2$s has gained trust against %3$s."
        + txtGainedVia;
    final var tplStealCredit = "%1$s from %2$s has stolen"
        + " %3$n credits from %4$s." + txtGainedVia;
    final var tplStealTech = "%1$s from %2$s has stolen"
        + " %3$s technology from %4$s." + txtGainedVia;
    final var tplSabotage = "%1$s from %2$s sabotaged %3$s project"
        + " at planet %4$s, owned by %5$s." + txtDoneVia;
    final var tplDemolish = "%1$s from %2$s demolished %3$s"
        + " at planet %4$s, owned by %5$s." + txtDoneVia;
    final var tplTerroristAttack = "%1$s from %2$s destroyed %3$s"
        + " at planet %4$s, owned by %5$s. %6$s" + txtDoneVia;
    final var tplAssassinFail = "%1$s from %2$s failed to assassinate %3$s"
        + " at planet %4$s. Planet is owned by %5$s." + txtDoneVia;
    final var tplGovernorEscaped = "There was a failed attempt to kill %1$s"
        + " at planet %2$s. Governor's expensive protection gear"
        + " saved %3$s life.";
    final var tplAssassinSuccess = "%1$s from %2$s assassinated %3$s"
        + " at planet %4$s. Planet is owned by %5$s" + txtDoneVia;
    final var tplGovernorKilled = "%1$s was killed at planet %2$s."
        + " It was probably an assassination.";
    final var tplFalseFlagOwner = "%1$s from %2$s made successfulfalse flag"
        + " operation against %3$s. Ship called %4$s was destroyed during"
        + " the mission. This mission happened next to %5$s.";
    final var tplFalseFlagVictim = "Ship %1$s from %2$s's fleet was destroyed."
        + " %2$s claim that %3$s destroyed it. This event"
        + " happened next to %4$s.";
    final var tplDeadlyVirus = "%1$s from %2$s spreads deadly virus on %3$s."
        + " Almost every population of planet is being killed."
        + " Planet is owned by %4$s. " + txtDoneVia;

    int infoIndex = starmap.getPlayerList().getIndex(info);
    if (type == EspionageMission.GAIN_TRUST) {
      DiplomacyBonusList diplomacy = planetPlayer
          .getDiplomacy().getDiplomacyList(infoIndex);
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.DIPLOMATIC_TRADE,
            planetPlayer.getRace());

        var msgText = String.format(tplGainTrust, spyCallName, spyEmpireName,
            planetEmpireName);
        var msg = makeSpyReportMessage(info, spy, planet, msgText);

        spy.setExperience(spy.getExperience() + type.getExperienceReward());
        history.addEvent(NewsFactory.makeLeaderEvent(
            spy, info, game.getStarMap(), msg.getMessage()));
      }
    } else

    if (type == EspionageMission.STEAL_CREDIT) {
      int totalCredits = planetPlayer.getTotalCredits();
      if (totalCredits > 0) {
        int stolen = totalCredits / 10;
        // Planet full value affects how much credit is stolen.
        int value = planet.getFullLevel();
        stolen = stolen * 100 / value;
        if (stolen == 0) {
          stolen = 1;
        }
        planetPlayer.setTotalCredits(totalCredits - stolen);
        info.setTotalCredits(info.getTotalCredits() + stolen);

        var msgText = String.format(tplStealCredit, spyCallName,
            spyEmpireName, stolen, planetEmpireName);
        var msg = makeSpyReportMessage(info, spy, planet, msgText);

        spy.setExperience(spy.getExperience() + type.getExperienceReward());
        history.addEvent(NewsFactory.makeLeaderEvent(
            spy, info, game.getStarMap(), msg.getMessage()));
      }
    } else

    if (type == EspionageMission.STEAL_TECH) {
      Tech[] stealableTechs = EspionageUtility.getStealableTech(planet, info);
      if (stealableTechs.length == 0) {
        return;
      }

      int index = DiceGenerator.getRandom(0, stealableTechs.length - 1);
      Tech tech = stealableTechs[index];
      info.getTechList().addTech(stealableTechs[index]);

      var msgText = String.format(tplStealTech, spyCallName, spyEmpireName,
          tech.getName(), planetEmpireName);
      var msg = makeSpyReportMessage(info, spy, planet, msgText);

      spy.setExperience(spy.getExperience() + type.getExperienceReward());
      history.addEvent(NewsFactory.makeLeaderEvent(
          spy, info, game.getStarMap(), msg.getMessage()));
    } else

    if (type == EspionageMission.SABOTAGE) {
      planet.setProdResource(planet.getProdResource() / 2);

      var msgText = String.format(tplSabotage, spyCallName, spyEmpireName,
          planet.getUnderConstruction().getName(), planet.getName(),
          planetEmpireName);
      var msg = makeSpyReportMessage(info, spy, planet, msgText);

      spy.setExperience(spy.getExperience() + type.getExperienceReward());
      history.addEvent(NewsFactory.makeLeaderEvent(
          spy, info, game.getStarMap(), msg.getMessage()));
    } else

    if (type == EspionageMission.DEMOLISH_BUILDING) {
      int index = DiceGenerator.getRandom(0,
          planet.getBuildingList().length - 1);
      Building building = planet.getBuildingList()[index];
      planet.removeBuilding(building);

      var msgText = String.format(tplDemolish, spyCallName, spyEmpireName,
          building.getName(), planet.getName(), planetEmpireName);
      var msg = makeSpyReportMessage(info, spy, planet, msgText);

      msg = msg.copy();
      msg.setMatchByString(planet.getName());
      planetPlayer.getMsgList().addUpcomingMessage(msg);
      var news = NewsFactory.makeBuildingDestroyedNews(planet, building,
          "");

      handleSpaceportSabotage(planet, building, info);

      if (starmap.hasHumanMet(planetPlayer)
          || starmap.hasHumanMet(info)) {
        newsCorp.addNews(news);
      }

      spy.setExperience(spy.getExperience() + type.getExperienceReward());
      history.addEvent(NewsFactory.makeLeaderEvent(
          spy, info, game.getStarMap(), msg.getMessage()));
    } else

    if (type == EspionageMission.TERRORIST_ATTACK) {
      int index = DiceGenerator.getRandom(0,
          planet.getBuildingList().length - 1);
      Building building = planet.getBuildingList()[index];
      planet.removeBuilding(building);

      handleSpaceportSabotage(planet, building, info);

      String killedTxt = "";
      if (planet.getTotalPopulation() > 1) {
        planet.killOneWorker();
        killedTxt = "Also population was killed during terrorist attack. ";
      }

      var msgText = String.format(tplTerroristAttack, spyCallName,
          spyEmpireName, building.getName(), planet.getName(),
          planetEmpireName, killedTxt);
      var msg = makeSpyReportMessage(info, spy, planet, msgText);

      msg = msg.copy();
      msg.setMatchByString(planet.getName());
      planetPlayer.getMsgList().addUpcomingMessage(msg);
      var news = NewsFactory.makeBuildingDestroyedNews(planet, building,
          killedTxt);
      newsCorp.addNews(news);

      spy.setExperience(spy.getExperience() + type.getExperienceReward());
      history.addEvent(NewsFactory.makeLeaderEvent(
          spy, info, game.getStarMap(), msg.getMessage()));
    } else

    if (type == EspionageMission.ASSASSIN_GOVERNOR) {
      Leader governor = planet.getGovernor();
      if (governor.hasPerk(Perk.WEALTHY)) {
        governor.useWealth();

        var msgText = String.format(tplAssassinFail, spyCallName, spyEmpireName,
            governor.getCallName(), planet.getName(), planetEmpireName);
        var msg = makeSpyReportMessage(info, spy, planet, msgText);
        history.addEvent(NewsFactory.makeLeaderEvent(
            spy, info, game.getStarMap(), msg.getMessage()));

        msgText = String.format(tplGovernorEscaped, governor.getCallName(),
            planet.getName(), governor.getGender().getHisHer());
        makeSpyReportMessage(planetPlayer, governor, planet, msgText);
      } else {
        planet.setGovernor(null);
        governor.setJob(Job.DEAD);
        spy.getStats().addOne(StatType.KILLED_ANOTHER_LEADER);

        var msgText = String.format(tplAssassinSuccess, spyCallName,
            spyEmpireName, governor.getCallName(), planet.getName(),
            planetEmpireName);
        var msg = makeSpyReportMessage(info, spy, planet, msgText);
        history.addEvent(NewsFactory.makeLeaderEvent(
            spy, info, game.getStarMap(), msg.getMessage()));

        msgText = String.format(tplGovernorKilled, governor.getCallName(),
            planet.getName());
        makeSpyReportMessage(planetPlayer, governor, planet, msgText);

        var news = NewsFactory.makeLeaderDies(governor,
            planetPlayer, "assasination", game.getStarMap().getStarYear());
        if (starmap.hasHumanMet(planetPlayer)) {
          newsCorp.addNews(news);
        }
        spy.setExperience(spy.getExperience() + type.getExperienceReward());
      }
    } else

    if (type == EspionageMission.FALSE_FLAG) {
      DiplomacyBonusList diplomacy = info.getDiplomacy().getDiplomacyList(
          planet.getPlanetOwnerIndex());
      if (diplomacy != null) {
        diplomacy.addBonus(DiplomacyBonusType.FALSE_FLAG, info.getRace());
      }
      Ship ship = fleet.getShipForFalseFlag();

      var msgText = String.format(tplFalseFlagOwner, spyCallName, spyEmpireName,
          planetEmpireName, ship.getName(), planet.getName());
      var msg = makeSpyReportMessage(info, spy, planet, msgText);
      history.addEvent(NewsFactory.makeLeaderEvent(
          spy, info, game.getStarMap(), msg.getMessage()));

      msgText = String.format(tplFalseFlagVictim, ship.getName(), spyEmpireName,
          planetEmpireName, planet.getName());
      msg = new Message(MessageType.PLANETARY, msgText,
          Icons.getIconByName(Icons.ICON_DEATH));
      msg.setCoordinate(planet.getCoordinate());
      msg.setMatchByString(planet.getName());
      planetPlayer.getMsgList().addUpcomingMessage(msg);

      fleet.removeShip(ship);
      String location = "escaped to another ship";
      if (spy != null && fleet.getNumberOfShip() == 0) {
        // Fleet's last ship was destroyed so commander escaped to
        // friendly planet
        spy.setJob(Job.UNASSIGNED);
        location = "escaped to closest friendly planet";
      }
      var news = NewsFactory.makeLeaderFalseFlag(spy, info,
          planetPlayer, location);
      if (starmap.hasHumanMet(info)
          || starmap.hasHumanMet(planetPlayer)) {
        newsCorp.addNews(news);
      }
      spy.setExperience(spy.getExperience() + type.getExperienceReward());
      info.getFleets().recalculateList();
    } else

    if (type == EspionageMission.DEADLY_VIRUS) {
      var msgText = String.format(tplDeadlyVirus, spyCallName, spyEmpireName,
          planet.getName(), planetEmpireName);
      var msg = makeSpyReportMessage(info, spy, planet, msgText);

      StarMapUtilities.spreadDeadlyVirus(info, planet);
      newsCorp.addNews(NewsFactory.makeDeadlyVirusNews(planet, info,
          starmap.getStarYear()));

      spy.setExperience(spy.getExperience() + type.getExperienceReward());
      history.addEvent(NewsFactory.makeLeaderEvent(
          spy, info, game.getStarMap(), msg.getMessage()));
    }
  }

  /**
   * Creates and SUBMITS new espionage-related message, about a leader
   * @param info PlayerInfo who recieves the message
   * @param spy Leader who is the message about
   * @param planet Planet where the report happened
   * @param text Message's text
   * @return new submitted message
   */
  private static Message makeSpyReportMessage(final PlayerInfo info,
      final Leader spy, final Planet planet, final String text) {
    Message msg = new Message(MessageType.LEADER, text,
        Icons.getIconByName(Icons.ICON_SPY_GOGGLES));
    msg.setCoordinate(planet.getCoordinate());
    msg.setMatchByString(spy.getName());
    info.getMsgList().addUpcomingMessage(msg);
    return msg;
  }
}
