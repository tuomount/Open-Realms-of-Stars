package org.openRealmOfStars.player.combat;

import java.util.ArrayList;
import java.util.Collections;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.FleetList;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.history.event.CombatEvent;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.Logger;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021 Tuomo Untinen
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
 * All the combat data, containing ships in single list in initiative order
 *
 */

public class Combat {

  /**
   * Maximum combat map size
   */
  public static final int MAX_X = 9;
  /**
   * Maximum, combat map size
   */
  public static final int MAX_Y = 9;
  /**
   * Ships in list in initiative order
   */
  private ArrayList<CombatShip> combatShipList;
  /**
   * Current ship index
   */
  private int shipIndex;

  /**
   * Cursor position X coordinate
   */
  private int cursorX;
  /**
   * Cursor position Y coordinate
   */
  private int cursorY;

  /**
   * Which component is in used
   */
  private int componentUse;

  /**
   * Combat animation
   */
  private CombatAnimation animation;

  /**
   * First player's fleet
   */
  private Fleet attackerFleet;
  /**
   * Second player's fleet
   */
  private Fleet defenderFleet;

  /**
   * Starbase fleet
   */
  private Fleet starbaseFleet;

  /**
   * Player Info for winner
   */
  private PlayerInfo winner;

  /**
   * Player info for attacker
   */
  private PlayerInfo attackerInfo;

  /**
   * Player Info for defender
   */
  private PlayerInfo defenderInfo;

  /**
   * Planet orbit where combat happens. This can be null.
   */
  private Planet planet;

  /**
   * Get total number of combat rounds
   * @return Number of combat rounds
   */
  public int getTotalRounds() {
    return totalRounds;
  }
  /**
   * Get number of rounds that no ship has been damaged
   * @return Number of rounds that no ship has been damaged
   */
  public int getRoundsNoDamge() {
    return roundsNoDamge;
  }

  /**
   * Total amount of combat rounds
   */
  private int totalRounds = 0;
  /**
   * Amount of combat rounds that no ship is damaged
   */
  private int roundsNoDamge = 0;

  /**
   * Is end combat handled or not
   */
  private boolean endCombatHandled = false;

  /**
   * Worm hole aka exit from combat
   */
  private Coordinate wormHole;

  /**
   * Timer when worm hole will appear.
   * This will depend on number of ships.
   */
  private int timerForWormHole;

  /**
   * At least one ship got away from worm hole
   */
  private boolean defenderEscaped;
  /**
   * At least one ship got away from worm hole
   */
  private boolean attackerEscaped;

  /**
   * Escape position for defender.
   */
  private Coordinate escapePosition;

  /**
   * Textual description what happened in combat
   */
  private CombatEvent combatEvent;
  /**
   * Defender military value at start of combat
   */
  private int defenderMilitaryValue;
  /**
   * Attacker military value at start of combat
   */
  private int attackerMilitaryValue;
  /**
   * News for killed leader.
   */
  private NewsData leaderKilledNews;
  /**
   * Is attacker privateer?
   */
  private boolean attackerPrivateer;
  /**
   * Is defender privateer?
   */
  private boolean defenderPrivateer;
  /**
   * Build shipList in initiative order
   * @param attackerFleet Attacking Player1 fleet
   * @param defenderFleet Defending Player2 fleet
   * @param attackerInfo Attacking Player1 info
   * @param defenderInfo Defending Player2 Info
   */
  public Combat(final Fleet attackerFleet, final Fleet defenderFleet,
          final PlayerInfo attackerInfo, final PlayerInfo defenderInfo) {
    this(attackerFleet, defenderFleet, attackerInfo, defenderInfo, null);
  }

  /**
   * Build shipList in initiative order
   * @param attackerFleet Attacking Player1 fleet
   * @param defenderFleet Defending Player2 fleet
   * @param attackerInfo Attacking Player1 info
   * @param defenderInfo Defending Player2 Info
   * @param escapePos Escape position for defender
   */
  public Combat(final Fleet attackerFleet, final Fleet defenderFleet,
          final PlayerInfo attackerInfo, final PlayerInfo defenderInfo,
          final Coordinate escapePos) {
    this.attackerFleet = attackerFleet;
    this.defenderFleet = defenderFleet;
    attackerPrivateer = this.attackerFleet.isPrivateerFleet();
    defenderPrivateer = this.defenderFleet.isPrivateerFleet();
    this.attackerInfo = attackerInfo;
    this.defenderInfo = defenderInfo;
    leaderKilledNews = null;
    starbaseFleet = null;
    combatEvent = new CombatEvent(defenderFleet.getCoordinate());
    if (attackerFleet.getCommander() != null) {
      attackerFleet.getCommander().getStats().addOne(
          StatType.NUMBER_OF_BATTLES);
    }
    if (defenderFleet.getCommander() != null) {
      defenderFleet.getCommander().getStats().addOne(
          StatType.NUMBER_OF_BATTLES);
    }
    StringBuilder combatText = new StringBuilder();
    if (!attackerPrivateer) {
      combatText.append(attackerInfo.getEmpireName());
      if (attackerInfo.isBoard() && defenderFleet.getCommander() != null) {
        defenderFleet.getCommander().getStats().addOne(
            StatType.NUMBER_OF_PIRATE_BATTLES);
      }
    } else {
      combatText.append("Privateer");
      if (defenderFleet.getCommander() != null) {
        defenderFleet.getCommander().getStats().addOne(
            StatType.NUMBER_OF_PIRATE_BATTLES);
      }
    }
    combatText.append(" attacked against ");
    if (!defenderPrivateer) {
      combatText.append(defenderInfo.getEmpireName());
      if (defenderInfo.isBoard() && attackerFleet.getCommander() != null) {
        attackerFleet.getCommander().getStats().addOne(
            StatType.NUMBER_OF_PIRATE_BATTLES);
      }
    } else  {
      combatText.append("Privateer");
      if (attackerFleet.getCommander() != null) {
        attackerFleet.getCommander().getStats().addOne(
            StatType.NUMBER_OF_PIRATE_BATTLES);
      }
    }
    combatText.append(" with ");
    if (attackerFleet.getNumberOfShip() > 1) {
      combatText.append(attackerFleet.getNumberOfShip());
      combatText.append(" ships against ");
    } else {
      combatText.append("single ship against ");
    }
    if (defenderFleet.getNumberOfShip() > 1) {
      combatText.append(defenderFleet.getNumberOfShip());
      combatText.append(" ships.");
    } else {
      combatText.append("one ship.");
    }
    combatEvent.setText(combatText.toString());
    FleetList fleetList = defenderInfo.getFleets();
    for (int i = 0; i < fleetList.getNumberOfFleets(); i++) {
      Fleet ite = fleetList.getByIndex(i);
      if (ite.isStarBaseDeployed()
          && ite.getCoordinate().sameAs(defenderFleet.getCoordinate())) {
        starbaseFleet = ite;
      }
    }
    combatShipList = new ArrayList<>();

    CombatPositionList bottomList = new BottomPositionList();
    CombatPositionList topList = new TopPositionList();
    addCombatShipList(attackerFleet, attackerInfo, bottomList, false);
    addCombatShipList(defenderFleet, defenderInfo, topList, true);
    attackerMilitaryValue = attackerFleet.getMilitaryValue();
    defenderMilitaryValue = defenderFleet.getMilitaryValue();
    if (starbaseFleet != null && starbaseFleet != this.defenderFleet) {
      CombatPositionList starbaseList = new StarbasePositionList();
      addCombatShipList(starbaseFleet, defenderInfo, starbaseList, true);
      defenderMilitaryValue = defenderMilitaryValue
          + starbaseFleet.getMilitaryValue();
    }

    Collections.sort(combatShipList, Collections.reverseOrder());
    for (CombatShip combatShip : combatShipList) {
      combatShip.getShip().initializeShieldAndArmor();
    }
    componentUse = -1;
    animation = null;
    winner = null;
    setPlanet(null);
    endCombatHandled = false;
    wormHole = null;
    timerForWormHole = combatShipList.size() * DiceGenerator.getRandom(1, 3);
    defenderEscaped = false;
    attackerEscaped = false;
    escapePosition = escapePos;
  }

  /**
   * Get possible news if leader was killed in combat.
   * @return NewsData or null.
   */
  public NewsData getLeaderKilledNews() {
    return leaderKilledNews;
  }
  /**
   * Add combatShip to combatShipList
   * @param fleet Player's Fleet
   * @param playerInfo Player's information
   * @param positionList starting coordinate list
   * @param flipY Should ship's image have flipped Y axel.
   */
  private void addCombatShipList(final Fleet fleet,
          final PlayerInfo playerInfo, final CombatPositionList positionList,
          final boolean flipY) {
      Ship[] ships = fleet.getShips();
      int index = 0;
      for (Ship ship : ships) {
        ShipStat stat = playerInfo.getShipStatByName(ship.getName());
        if (stat != null) {
          stat.setNumberOfCombats(stat.getNumberOfCombats() + 1);
        }
        int combatShipX = positionList.getStartPosX(index);
        int combatShipY = positionList.getStartPosY(index);
        CombatShip combatShp = new CombatShip(ship, playerInfo,
                combatShipX, combatShipY, flipY, fleet.getCommander());
        if (fleet.getRoute() != null && fleet.getRoute().isDefending()) {
          combatShp.setBonusAccuracy(5);
        }
        combatShipList.add(combatShp);
        index++;
      }
  }

  /**
   * Get first player's info which is the attacker
   * @return Player Info
   */
  public PlayerInfo getPlayer1() {
    return attackerInfo;
  }

  /**
   * Get second player's info which is the defender
   * @return Player info
   */
  public PlayerInfo getPlayer2() {
    return defenderInfo;
  }

  /**
   * Get the winner.
   * @return PlayerInfo who won or null
   */
  public PlayerInfo getWinner() {
    return winner;
  }


  /**
   * Get winnder fleet. Can return null
   * @return Winner fleet or null
   */
  public Fleet getWinnerFleet() {
    if (winner != null) {
      if (attackerInfo == winner) {
        return attackerFleet;
      }
      return defenderFleet;
    }
    return null;
  }

  /**
   * Is clear shot from shooter to target with used weapon
   * @param shooter Shooter's combat ship
   * @param target Target's combat Ship
   * @return True if shot is possible to shoot
   */
  public boolean isClearShot(final CombatShip shooter,
      final CombatShip target) {
    boolean result = false;
    ShipComponent weapon = shooter.getShip().getComponent(componentUse);
    if (weapon != null && weapon.isWeapon()) {
      CombatCoordinate shooterCoordinate =
              new CombatCoordinate(shooter.getX(), shooter.getY());
      CombatCoordinate targetCoordinate =
              new CombatCoordinate(target.getX(), target.getY());
      double xAxisDistance = Math.abs(shooter.getX() - target.getX());
      double yAxisDistance = Math.abs(shooter.getY() - target.getY());
      int distance;
      if (xAxisDistance > yAxisDistance) {
          distance = (int) xAxisDistance;
      } else {
          distance = (int) yAxisDistance;
      }
      if (shooter.getShip().getWeaponRange(weapon) >= distance
          && distance > 0) {
        result = launchIntercept(distance,
            shooterCoordinate, targetCoordinate);
      }

    }

    return result;
  }

  /**
   * Can ship do tractor beam on target
   * @param tractor Ship having tractor beam
   * @param target Target's combat ship
   * @return True if tractor beam can be used
   */
  public boolean canTractor(final CombatShip tractor, final CombatShip target) {
    ShipComponent weapon = tractor.getShip().getComponent(componentUse);
    if (weapon != null && weapon.getType() == ShipComponentType.TRACTOR_BEAM) {
      int tratocSize = tractor.getShip().getHull().getSize().getMass();
      int pullSize = target.getShip().getHull().getSize().getMass();
      if (tratocSize >= pullSize) {
        double xAxisDistance = Math.abs(tractor.getX() - target.getX());
        double yAxisDistance = Math.abs(tractor.getY() - target.getY());
        int distance;
        if (xAxisDistance > yAxisDistance) {
            distance = (int) xAxisDistance;
        } else {
            distance = (int) yAxisDistance;
        }
        if (distance == 2) {
          return true;
        }
      }
    }
    return false;
  }
  /**
   * Can ship do privateering towards target
   * @param privateer Privateer's combat ship
   * @param target Target's combat Ship
   * @return True if privateering is possible
   */
  public boolean canPrivateer(final CombatShip privateer,
      final CombatShip target) {
    ShipComponent weapon = privateer.getShip().getComponent(componentUse);
    if (weapon != null && weapon.isPrivateer()) {
      double xAxisDistance = Math.abs(privateer.getX() - target.getX());
      double yAxisDistance = Math.abs(privateer.getY() - target.getY());
      int distance;
      if (xAxisDistance > yAxisDistance) {
          distance = (int) xAxisDistance;
      } else {
          distance = (int) yAxisDistance;
      }
      if (distance == 1) {
        return true;
      }
    }
    return false;
  }

  /**
 * @param distance distance between shooter and target
 * @param shooter shooter coordinate
 * @param target target coordinate
 * @return boolean is hit or not
 */
public boolean launchIntercept(final int distance,
          final CombatCoordinate shooter, final CombatCoordinate target) {
      boolean isHit = false;
      double interceptX = shooter.getX();
      double interceptY = shooter.getY();
      double dx, dy;
      if (distance > 0) {
          dx = (double) (target.getX() - shooter.getX()) / distance;
          dy = (double) (target.getY() - shooter.getY()) / distance;
      } else {
          dx = 0;
          dy = 0;
          }
      for (int i = 0; i < distance + 1; i++) {
          interceptX = interceptX + dx;
          interceptY = interceptY + dy;
          int intX = (int) Math.round(interceptX);
          int intY = (int) Math.round(interceptY);
          if (intX == target.getX() && intY == target.getY()) {
              isHit = true;
              break;
          }
          if (isBlocked(intX, intY)) {
              isHit = false;
              break;
          }
      }
      return isHit;
  }
  /**
   * Get most powerful ship opponent has or null
   * @param info Player Info who is doing the comparison.
   * @return CombatShip or null
   */
  public CombatShip getMostPowerfulShip(final PlayerInfo info) {
    int strongestPower = 0;
    CombatShip strongestShip = null;
    for (CombatShip ship : combatShipList) {
      if (ship.getPlayer() != info
          && ship.getShip().getTotalMilitaryPower() > strongestPower
          && !ship.isCloakOverloaded()) {
        strongestShip = ship;
        strongestPower = strongestShip.getShip().getTotalMilitaryPower();
      }
    }
    if (strongestShip == null) {
      for (CombatShip ship : combatShipList) {
        if (ship.getPlayer() != info
            && ship.getShip().getTheoreticalMilitaryPower() > strongestPower
            && !ship.isCloakOverloaded()) {
          strongestShip = ship;
          strongestPower = strongestShip.getShip()
              .getTheoreticalMilitaryPower();
        }
      }
    }
    return strongestShip;
  }

  /**
   * Get strongest cloaking detection for certain player.
   * @param info PlayerInfo
   * @return Cloak detection
   */
  public int getMaxCloakDetection(final PlayerInfo info) {
    int strongestPower = 0;
    for (CombatShip ship : combatShipList) {
      if (ship.getPlayer() != info
          && ship.getShip().getScannerDetectionLvl() > strongestPower) {
        strongestPower = ship.getShip().getScannerDetectionLvl();
      }
    }
    return strongestPower;
  }

  /**
   * Destroy one single ship from the fleet and combat
   * @param ship Combat ship
   */
  public void destroyShip(final CombatShip ship) {
    if (attackerFleet.isShipInFleet(ship.getShip())) {
      destroyShipFromFleet(ship, attackerFleet);
      if (attackerFleet.getNumberOfShip() == 0
          && attackerFleet.getCommander() != null) {
        if (attackerFleet.getCommander().hasPerk(Perk.WEALTHY)) {
          Message msg = new Message(MessageType.LEADER,
              attackerFleet.getCommander().getCallName()
                  + " has paid massive amount of credits to save "
                  + attackerFleet.getCommander().getGender().getHisHer()
                  + " life. Private shuttle was used to save "
                  + attackerFleet.getCommander().getName() + ".",
              Icons.getIconByName(Icons.ICON_DEATH));
          msg.setMatchByString("Index:"
              + attackerInfo.getLeaderIndex(attackerFleet.getCommander()));
          attackerInfo.getMsgList().addNewMessage(msg);
          leaderKilledNews = NewsFactory.makeLeaderEscape(
              attackerFleet.getCommander(), attackerInfo, defenderInfo, "");

          attackerFleet.getCommander().useWealth();
          attackerFleet.getCommander().setJob(Job.UNASSIGNED);
          attackerFleet.setCommander(null);
        } else {
          attackerFleet.getCommander().setJob(Job.DEAD);
          leaderKilledNews = NewsFactory.makeCommanderKilledInAction(
              attackerFleet.getCommander(), defenderFleet.getCommander(),
              attackerInfo, defenderInfo, attackerPrivateer,
              defenderPrivateer);
          if (defenderFleet.getCommander() != null) {
            defenderFleet.getCommander().getStats().addOne(
                StatType.KILLED_ANOTHER_LEADER);
          }
          attackerFleet.setCommander(null);
        }
      }
      attackerInfo.getFleets().recalculateList();
    } else if (defenderFleet.isShipInFleet(ship.getShip())) {
      destroyShipFromFleet(ship, defenderFleet);
      if (defenderFleet.getNumberOfShip() == 0
          && defenderFleet.getCommander() != null) {
        if (defenderFleet.getCommander().hasPerk(Perk.WEALTHY)) {
          Message msg = new Message(MessageType.LEADER,
              defenderFleet.getCommander().getCallName()
                  + " has paid massive amount of credits to save "
                  + defenderFleet.getCommander().getGender().getHisHer()
                  + " life. Private shuttle was used to save "
                  + defenderFleet.getCommander().getName() + ".",
              Icons.getIconByName(Icons.ICON_DEATH));
          msg.setMatchByString("Index:"
              + defenderInfo.getLeaderIndex(defenderFleet.getCommander()));
          defenderInfo.getMsgList().addNewMessage(msg);
          leaderKilledNews = NewsFactory.makeLeaderEscape(
              defenderFleet.getCommander(), defenderInfo, attackerInfo, "");

          defenderFleet.getCommander().useWealth();
          defenderFleet.getCommander().setJob(Job.UNASSIGNED);
          defenderFleet.setCommander(null);
        } else {
          defenderFleet.getCommander().setJob(Job.DEAD);
          leaderKilledNews = NewsFactory.makeCommanderKilledInAction(
              defenderFleet.getCommander(), attackerFleet.getCommander(),
              defenderInfo, attackerInfo, defenderPrivateer,
              attackerPrivateer);
          if (attackerFleet.getCommander() != null) {
            attackerFleet.getCommander().getStats().addOne(
                StatType.KILLED_ANOTHER_LEADER);
          }
          defenderFleet.setCommander(null);
        }
      }
      defenderInfo.getFleets().recalculateList();
    } else if (starbaseFleet.isShipInFleet(ship.getShip())) {
      destroyShipFromFleet(ship, starbaseFleet);
      if (starbaseFleet.getNumberOfShip() == 0
          && starbaseFleet.getCommander() != null) {
        if (starbaseFleet.getCommander().hasPerk(Perk.WEALTHY)) {
          Message msg = new Message(MessageType.LEADER,
              starbaseFleet.getCommander().getCallName()
                  + " has paid massive amount of credits to save "
                  + starbaseFleet.getCommander().getGender().getHisHer()
                  + " life. Private shuttle was used to save "
                  + starbaseFleet.getCommander().getName() + ".",
              Icons.getIconByName(Icons.ICON_DEATH));
          msg.setMatchByString("Index:"
              + defenderInfo.getLeaderIndex(defenderFleet.getCommander()));
          defenderInfo.getMsgList().addNewMessage(msg);
          leaderKilledNews = NewsFactory.makeLeaderEscape(
              starbaseFleet.getCommander(), defenderInfo, attackerInfo, "");

          starbaseFleet.getCommander().useWealth();
          starbaseFleet.getCommander().setJob(Job.UNASSIGNED);
          starbaseFleet.setCommander(null);
        } else {
          leaderKilledNews = NewsFactory.makeCommanderKilledInAction(
              starbaseFleet.getCommander(), attackerFleet.getCommander(),
              defenderInfo, attackerInfo, defenderPrivateer,
              attackerPrivateer);
          starbaseFleet.getCommander().setJob(Job.DEAD);
          starbaseFleet.setCommander(null);
        }
      }
      defenderInfo.getFleets().recalculateList();
    }
  }

  /**
   * Escape one single ship from the combat
   * @param ship Combat ship to escape
   */
  public void escapeShip(final CombatShip ship) {
    if (ship.getPrivateeredCredits() > 0) {
      ship.getPlayer().setTotalCredits(ship.getPlayer().getTotalCredits()
          + ship.getPrivateeredCredits());
    }
    if (attackerFleet.isShipInFleet(ship.getShip())) {
      removeShipFromCombatList(ship);
      attackerEscaped = true;
    } else if (defenderFleet.isShipInFleet(ship.getShip())) {
      removeShipFromCombatList(ship);
      defenderEscaped = true;
    }
    if (getCurrentShip() != null) {
      CombatAnimation anim = getCurrentShip().reInitShipForRound();
      if (anim != null) {
        setAnimation(anim);
      }
    }
    if (isCombatOver()) {
      handleEndCombat();
    }
  }

  /**
   * Remove ship from combat list. Ship can be either
   * destroyed or escaped.
   * @param ship Combat ship to remove.
   */
  private void removeShipFromCombatList(final CombatShip ship) {
    int indexToDelete = combatShipList.indexOf(ship);
    combatShipList.remove(ship);
    if (indexToDelete < shipIndex && shipIndex > 0) {
      shipIndex--;
    }
  }
  /**
   * Steal Ship from fleet.
   * @param ship Ship to steal
   * @param origin Origin fleet
   * @param target New target fleet
   */
  private void stealShipFromFleet(final CombatShip ship, final Fleet origin,
      final Fleet target) {
    if (animation.getShooter() != null) {
      ship.setPlayer(animation.getShooter().getPlayer());
      origin.removeShip(ship.getShip());
      target.addShip(ship.getShip());
      ship.setCommander(target.getCommander());
    }
  }
  /**
   * Destroy ship for fleet's list.
   * @param ship ship to destroy from the fleet
   * @param fleet containing the ship
   */
  private void destroyShipFromFleet(final CombatShip ship, final Fleet fleet) {
    fleet.removeShip(ship.getShip());
    if (animation.getShooter() != null) {
      ShipStat stat = animation.getShooter().getPlayer()
          .getShipStatByName(animation.getShooter().getShip().getName());
      Ship shooter = animation.getShooter().getShip();
      if (shooter != null && shooter.getExperience() < 5) {
        shooter.setExperience(shooter.getExperience() + 1);
      }
      if (stat != null) {
        stat.setNumberOfKills(stat.getNumberOfKills() + 1);
      }
    }
    ShipStat stat = ship.getPlayer().getShipStatByName(
        ship.getShip().getName());
    if (stat != null) {
      stat.setNumberOfLoses(stat.getNumberOfLoses() + 1);
      stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
    }
    removeShipFromCombatList(ship);
  }

  /**
   * Maximum distance in combat which cannot be really reached.
   */
  private static final int MAX_DISTANCE = 999;

  /**
   * Calculates distance between two combat ships
   * @param from From where
   * @param to To which ship
   * @return Distance in double
   */
  private double calculateDistance(final CombatShip from,
      final CombatShip to) {
    CombatCoordinate centerCoordinate =
        new CombatCoordinate(from.getX(), from.getY());
    CombatCoordinate toCoordinate =
        new CombatCoordinate(to.getX(), to.getY());
    return centerCoordinate.calculateDistance(toCoordinate);
  }
  /**
   * Get the closest enemy ship
   * @param info Player info who is doing the comparison
   * @param friendlyShip Where to start looking the closet one
   * @return CombatShip or null
   */
  public CombatShip getClosestEnemyShip(final PlayerInfo info,
      final CombatShip friendlyShip) {
    double maxDistance = MAX_DISTANCE;
    CombatShip enemyShip = null;
    for (CombatShip ship : combatShipList) {
      if (ship.getPlayer() != info && !ship.isCloakOverloaded()) {
        double distance = calculateDistance(friendlyShip, ship);
        if (distance < maxDistance) {
          enemyShip = ship;
          maxDistance = distance;
        }
      }
    }
    return enemyShip;

  }

  /**
   * Get the closest enemy trader ship
   * @param info Player info who is doing the comparison
   * @param friendlyShip Where to start looking the closet one
   * @return CombatShip or null
   */
  public CombatShip getClosestTraderShip(final PlayerInfo info,
      final CombatShip friendlyShip) {
    double maxDistance = MAX_DISTANCE;
    CombatShip enemyShip = null;
    for (CombatShip ship : combatShipList) {
      if (ship.getPlayer() != info && !ship.isCloakOverloaded()) {
        double distance = calculateDistance(friendlyShip, ship);
        int cargo = ship.getShip().getCargoType();
        boolean cargoToSteal = false;
        if (cargo == Ship.CARGO_TYPE_METAL
            || cargo == Ship.CARGO_TYPE_POPULATION
            || cargo == Ship.CARGO_TYPE_TRADE_GOODS) {
          cargoToSteal = true;
        }
        if (distance < maxDistance && cargoToSteal) {
          enemyShip = ship;
          maxDistance = distance;
        }
      }
    }
    return enemyShip;

  }

  /**
   * Calculate farest position from a ship
   * @param enemyShip Enemy ship
   * @return Coordinate which are as far away as possible
   */
  public Coordinate getFarestFormEnemy(final CombatShip enemyShip) {
    Coordinate coord = new Coordinate(0, 0);
    Coordinate enemy = new Coordinate(enemyShip.getX(), enemyShip.getY());
    double minDistance = 0;
    for (int y = 0; y < MAX_Y; y++) {
      for (int x = 0; x < MAX_X; x++) {
        Coordinate coordinate = new Coordinate(x, y);
        double distance = coordinate.calculateDistance(enemy);
        if (distance > minDistance) {
          minDistance = distance;
          coord = coordinate;
        }
      }
    }
    return coord;
  }

  /**
   * Set cursor position
   * @param x X coordinate
   * @param y Y coordinate
   */
  public void setCursorPos(final int x, final int y) {
    cursorX = x;
    cursorY = y;
  }

  /**
   * Is coordinate valid position on combat map
   * @param x X Coordinate
   * @param y Y Coordinate
   * @return true if valid otherwise false
   */
  public boolean isValidPos(final int x, final int y) {
    if (x >= 0 && y >= 0 && x < MAX_X && y < MAX_Y) {
      return true;
    }
    return false;
  }

  /**
   * Is coordinate blocked or not
   * @param x X coordinate
   * @param y Y coordinate
   * @return True if blocked false otherwise
   */
  public boolean isBlocked(final int x, final int y) {
    for (CombatShip ship : combatShipList) {
      if (x == ship.getX() && y == ship.getY()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is coordinate blocked or not
   * @param x X coordinate
   * @param y Y coordinate
   * @param avoidWormHole True set worm hole coordinate blocked
   * @return True if blocked false otherwise
   */
  public boolean isBlocked(final int x, final int y,
      final boolean avoidWormHole) {
    for (CombatShip ship : combatShipList) {
      if (x == ship.getX() && y == ship.getY()) {
        return true;
      }
    }
    if (avoidWormHole && wormHole != null
        && x == wormHole.getX() && y == wormHole.getY()) {
      return true;
    }
    return false;
  }

  /**
   * Get Cursor X coordinate
   * @return X coordinate
   */
  public int getCursorX() {
    return cursorX;
  }

  /**
   * Get Cursor Y coordinate
   * @return Y coordinate
   */
  public int getCursorY() {
    return cursorY;
  }

  /**
   * Get starmap coordinate where combat happens. This is always
   * at defender's coordinates
   * @return Coordinate of combat
   */
  public Coordinate getCombatCoordinates() {
    return new Coordinate(defenderFleet.getX(), defenderFleet.getY());
  }

  /**
   * Get Current Ship
   * @return CombatShip or null if no ships available
   */
  public CombatShip getCurrentShip() {
    if (combatShipList.size() <= shipIndex) {
      shipIndex = 0;
    }
    if (combatShipList.size() > 0 && shipIndex > -1) {
      return combatShipList.get(shipIndex);
    }
    return null;
  }

  /**
   * Handle Orbital defense Grid for defender.
   * This method checks if planet has orbital defense grid
   * and shoots first enemy ship.
   * @param textLogger Text logger for printing the text
   */
  public void handleOrbitalDefenseGrid(final Logger textLogger) {
    if (planet != null
        && planet.howManyBuildings("Orbital defense grid") > 0) {
      for (CombatShip ship : combatShipList) {
        if (ship.getPlayer() == attackerInfo) {
          ShipComponent weapon = new ShipComponent(0, "Orbital defense grid",
              0, 0, ShipComponentType.PLASMA_BEAM);
          weapon.setDamage(1);
          ShipDamage shipDamage = ship.getShip().damageBy(weapon, 0);
          if (shipDamage.getValue() <= ShipDamage.DAMAGED) {
            ship.setDamaged();
          }
          if (textLogger != null) {
            String[] logs = shipDamage.getMessage().split("\n");
            for (String log : logs) {
              textLogger.addLog(log);
            }
          }
          setAnimation(
              new CombatAnimation(null, ship, weapon, shipDamage.getValue()));
          // Shoot only one ship per turn
          break;
        }
      }
    }
  }
  /**
   * Get next ship is list. List contains both player's fleets
   * in initiative order. After this ship can be fetched with
   * getCurrentShip() method.
   * @param textLogger where logging is added if not null
   */
  public void nextShip(final Logger textLogger) {
    shipIndex++;
    if (combatShipList.size() <= shipIndex) {
      handleOrbitalDefenseGrid(textLogger);
      shipIndex = 0;
      totalRounds++;
      if (!isDamageOnAnyShip()) {
        roundsNoDamge++;
      } else {
        roundsNoDamge = 0;
      }
    }
    CombatShip ship = getCurrentShip();
    if (ship != null) {
      CombatAnimation anim = ship.reInitShipForRound();
      if (anim != null) {
        setAnimation(anim);
      }

    }
  }

  /**
   * Get ship from combat coordinate
   * @param x X Coordinate
   * @param y Y Coordinate
   * @return CombatShip
   */
  public CombatShip getShipFromCoordinate(final int x, final int y) {
    for (CombatShip ship : combatShipList) {
      if (ship.getX() == x && ship.getY() == y) {
        return ship;
      }
    }
    return null;
  }
  /**
   * Handle winner fleet stats
   * @param fleet The winner fleet
   * @param info The winner player info
   */
  private static void handleWinner(final Fleet fleet, final PlayerInfo info) {
    for (Ship ship : fleet.getShips()) {
      ShipStat stat = info.getShipStatByName(ship.getName());
      if (stat != null) {
        stat.setNumberOfVictories(stat.getNumberOfVictories() + 1);
      }
    }
  }

  /**
   * Handle loser mission. This will cause loser player to be
   * more aggresive on defending
   * @param info Player who losed
   */
  private void handleLoser(final PlayerInfo info) {
    if (!info.isHuman() && planet != null
        && planet.getPlanetPlayerInfo() == info) {
      Mission mission = info.getMissions().getMissionForPlanet(
          planet.getName(), MissionType.DEFEND);
      if (mission != null) {
        // Cause defend mission to build more defenders
        mission.setPhase(MissionPhase.PLANNING);
      } else {
        // Add new defending mission
        mission = new Mission(MissionType.DEFEND, MissionPhase.PLANNING,
            planet.getCoordinate());
        mission.setPlanetBuilding(planet.getName());
        info.getMissions().add(mission);
      }
    }
  }
  /**
   * Handle End combat. This can be safely called multiple times.
   * When the defender is the winner, it does not move from its current
   * position.
   */
  public void handleEndCombat() {
    PlayerInfo winnerPlayer;
    PlayerInfo looserPlayer;
    Fleet winnerFleet;
    Fleet looserFleet;
    boolean isWinnerAttacker;
    boolean loserEscaped = false;
    for (CombatShip combatShip : combatShipList) {
      combatShip.getShip().initializeShieldAndArmor();
    }
    if (!endCombatHandled && winner != null) {
      if (attackerInfo == winner) {
        winnerPlayer = attackerInfo;
        looserPlayer = defenderInfo;
        winnerFleet = attackerFleet;
        looserFleet = defenderFleet;
        loserEscaped = defenderHasEscaped();
        isWinnerAttacker = true;
        if (attackerFleet.getCommander() != null) {
          Leader leader = attackerFleet.getCommander();
          leader.setExperience(leader.getExperience() + defenderMilitaryValue);
        }
      } else {
        winnerPlayer = defenderInfo;
        looserPlayer = attackerInfo;
        winnerFleet = defenderFleet;
        looserFleet = attackerFleet;
        loserEscaped = attackerHasEscaped();
        isWinnerAttacker = false;
        if (defenderFleet.getCommander() != null) {
          Leader leader = defenderFleet.getCommander();
          leader.setExperience(leader.getExperience() + attackerMilitaryValue);
        }
      }
      endCombatHandled = true;
      handleWinner(winnerFleet, winnerPlayer);
      handleLoser(looserPlayer);
      for (CombatShip ship : combatShipList) {
        if (ship.getPrivateeredCredits() > 0) {
          ship.getPlayer().setTotalCredits(ship.getPlayer().getTotalCredits()
              + ship.getPrivateeredCredits());
        }
      }
      int looserIndex = looserPlayer.getFleets().
          getIndexByName(looserFleet.getName());
      if (looserIndex != -1 && !loserEscaped) {
        looserPlayer.getMissions().deleteMissionForFleet(looserFleet.getName());
        looserPlayer.getFleets().remove(looserIndex);
      }
      if (loserEscaped && isWinnerAttacker) {
        if (escapePosition != null) {
          // Fleet escaped!!
          Message msg = new Message(MessageType.FLEET,
              looserFleet.getName() + " escaped from combat!",
              Icons.getIconByName(Icons.ICON_HULL_TECH));
          msg.setCoordinate(looserFleet.getCoordinate());
          looserPlayer.getMsgList().addUpcomingMessage(msg);
          looserFleet.setPos(escapePosition);
        } else {
          // There is no position to defender escape so fleet is going
          // to be destroyed
          Message msg = new Message(MessageType.FLEET,
              looserFleet.getName() + " tried to escape but failed."
              + " Fleet was destroyed!",
              Icons.getIconByName(Icons.ICON_DEATH));
          msg.setCoordinate(looserFleet.getCoordinate());
          looserPlayer.getMsgList().addUpcomingMessage(msg);
          for (Ship ship : looserFleet.getShips()) {
            ShipStat stat = looserPlayer.getShipStatByName(ship.getName());
            if (stat != null) {
              stat.setNumberOfLoses(stat.getNumberOfLoses() + 1);
              stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
            }
          }
          looserPlayer.getMissions().deleteMissionForFleet(
              looserFleet.getName());
          looserPlayer.getFleets().remove(looserIndex);
        }
      }
      StringBuilder sb = new StringBuilder(combatEvent.getText());
      if (planet != null) {
        sb.append(" Combat happened in orbit of ");
        sb.append(planet.getName());
        sb.append(".");
        combatEvent.setPlanetName(planet.getName());
      }
      if (winnerFleet.getNumberOfShip() > 1) {
        sb.append(" Combat was victorious for ");
        if (attackerInfo == winnerPlayer && !attackerPrivateer) {
          sb.append(winnerPlayer.getEmpireName());
        } else if (defenderInfo == winnerPlayer && !defenderPrivateer) {
          sb.append(winnerPlayer.getEmpireName());
        } else {
          sb.append("privateers");
        }
        sb.append(". ");
        sb.append(winnerFleet.getNumberOfShip());
        sb.append(" ships in victorious fleet survived. ");
      } else {
        sb.append(" Combat was victorious for ");
        if (attackerInfo == winnerPlayer && !attackerPrivateer) {
          sb.append(winnerPlayer.getEmpireName());
        } else if (defenderInfo == winnerPlayer && !defenderPrivateer) {
          sb.append(winnerPlayer.getEmpireName());
        } else {
          sb.append("privateers");
        }
        sb.append(". ");
        sb.append("Single ship survived in victorious fleet. ");
      }
      if (loserEscaped && escapePosition != null) {
        if (looserFleet.getNumberOfShip() > 1) {
          sb.append("Loser fleet's ");
          sb.append(looserFleet.getNumberOfShip());
          sb.append("ships escaped from the combat.");
        } else {
          sb.append("Loser fleet's ");
          sb.append("last ship escaped from the combat.");
        }
      } else {
        sb.append("Loser's fleet was totally destroyed!");
      }
      combatEvent.setText(sb.toString());
      if (isWinnerAttacker) {
        Coordinate loserPos = looserFleet.getCoordinate();
        if (looserPlayer.getFleets().getFleetByCoordinate(loserPos) == null) {
          // No more defending fleets so moving to the coordinate
          winnerFleet.setPos(looserFleet.getCoordinate());
        }
      }
    }
  }

  /**
   * Is there damage on any ship
   * @return True if at least one ship has damage
   */
  public boolean isDamageOnAnyShip() {
    for (CombatShip ship : combatShipList) {
      if (ship.isDamaged()) {
        return true;
      }
    }
    return false;
  }
  /**
   * Is Combat over or not yet
   * @return True if combat is over
   */
  public boolean isCombatOver() {
    if (endCombatHandled) {
      // All is done no need to check it anymore
      return true;
    }
    PlayerInfo first = null;
    boolean moreThanOnePlayer = false;
    boolean militaryPower = false;
    boolean damaged = false;
    for (CombatShip ship : combatShipList) {
      if (first == null) {
        first = ship.getPlayer();
      }
      if (first != ship.getPlayer()) {
        moreThanOnePlayer = true;
      }
      if (ship.getShip().getTotalMilitaryPower() > 0) {
        militaryPower = true;
      }
      if (ship.isDamaged()) {
        damaged = true;
      }
    }
    if (roundsNoDamge >= 15 && !damaged) {
      winner = null;
      // Combat seems to be draw
      return true;
    }
    if (!moreThanOnePlayer) {
      winner = first;
      // There is no combat with one player
      return true;
    }
    if (!militaryPower) {
      // No reason to continue with non military ships
      return true;
    }
    return false;
  }

  /**
   * Do Fast combat without showing the actual combat.
   * This should be used when two AIs fight against
   * each others. If combat has ended this does not do
   * anything.
   */
  public void doFastCombat() {
    doFastCombat(false);
  }

  /**
   * Do Fast combat without showing the actual combat.
   * This should be used when two AIs fight against
   * each others. If combat has ended this does not do
   * anything.
   * @param debug True to enable debug logging
   */
  public void doFastCombat(final boolean debug) {
    Logger logger = null;
    if (!isCombatOver()) {
      while (!isCombatOver()) {
        if (debug) {
          logger = new Logger();
          logger.addLog("Turn starts...");
          logger.addLog(getCurrentShip().getShip().getName() + " X:"
              + getCurrentShip().getX() + " Y:" + getCurrentShip().getY());
        }
        boolean endRound = handleAI(logger, null);
        if (logger != null) {
          if (endRound && debug) {
            logger.addLog("Round ended...");
          }
          for (int i = logger.size() - 1; i >= 0; i--) {
            String msg = logger.getMessage(i);
            if (!msg.isEmpty()) {
              ErrorLogger.log(msg);
            }
          }
        }
        if (animation != null
            && animation.getTarget().getShip().getHullPoints() <= 0) {
          // Ship has no more hull points so destroying it
          destroyShip(animation.getTarget());
        }
        setAnimation(null);
      }
      handleEndCombat();
    }
  }

  /**
   * Which component was used in current ship
   * @return Component used index.
   */
  public int getComponentUse() {
    return componentUse;
  }

  /**
   * Define which component was used in current ship
   * @param componentUse Component index
   */
  public void setComponentUse(final int componentUse) {
    this.componentUse = componentUse;
  }

  /**
   * Get combat animation
   * @return Combat animation
   */
  public CombatAnimation getAnimation() {
    return animation;
  }

  /**
   * Set combat animation
   * @param animation Combat animamation
   */
  public void setAnimation(final CombatAnimation animation) {
    this.animation = animation;
  }

  /**
   * Get combat event from combat. This should be called
   * after end combat has been handled.
   * @return Combat event containing historical data.
   */
  public CombatEvent getCombatEvent() {
    return combatEvent;
  }
  /**
   * Get planet where combat happens. If combat is in deep space
   * then null is returned.
   * @return the planet or null.
   */
  public Planet getPlanet() {
    return planet;
  }

  /**
   * Set planet where combat happens. This must have same coordinates
   * as defender.
   * @param planetOrbitting the planet to set
   */
  public void setPlanet(final Planet planetOrbitting) {
    if (planetOrbitting == null) {
      this.planet = null;
    } else if (planetOrbitting.getCoordinate().sameAs(getCombatCoordinates())) {
      this.planet = planetOrbitting;
    }
  }

  /**
   * End battle round
   * @param textLogger where logging is added if not null
   * @return true if Combat is over, otherwise false.
   */
  public boolean endRound(final Logger textLogger) {
    if (timerForWormHole > 0) {
      timerForWormHole--;
    } else if (wormHole == null) {
      createWormHole();
    }
    setComponentUse(-1);
    nextShip(textLogger);
    boolean over = isCombatOver();
    if (over) {
      handleEndCombat();
    }
    return over;
  }

  /**
   * Calculate accuracy for certain weapons and by shooter and target.
   * @param shooter CombatShip shooting
   * @param weapon Weapon used
   * @param target CombatShip which is being target
   * @return Accuracy for weapon against certain target
   */
  public int calculateAccuracy(final CombatShip shooter,
      final ShipComponent weapon, final CombatShip target) {
    int accuracy = shooter.getShip().getHitChance(weapon)
        + shooter.getBonusAccuracy();
    if (shooter.getCommander() !=  null
        && shooter.getCommander().hasPerk(Perk.COMBAT_MASTER)) {
      accuracy = accuracy + 5;
    }
    if (shooter.getCommander() !=  null
        && shooter.getCommander().hasPerk(Perk.SKILLFUL)) {
      accuracy = accuracy + 5;
    }
    if (shooter.getCommander() !=  null
        && shooter.getCommander().hasPerk(Perk.INCOMPETENT)) {
      accuracy = accuracy - 5;
    }
    accuracy = accuracy - target.getShip().getDefenseValue();
    accuracy = accuracy - target.getOverloadedJammer();
    if (target.isCloaked()) {
      accuracy = accuracy - 5;
    }
    if (accuracy < 5) {
      accuracy = 5;
    }
    if (target.isCloakOverloaded()) {
      accuracy = -1;
    }
    return accuracy;
  }
  /**
   * Handle AI shooting
   * @param ai AI which is shooting
   * @param target shooting target
   * @param textLogger where logging is added if not null
   * @param infoPanel Infopanel where ship components are shown.
   *        This can be null too.
   * @param shot AI has shot and animation is on going
   * @return true if shooting was actually done
   */
  public boolean handleAIShoot(final CombatShip ai, final CombatShip target,
      final Logger textLogger, final BattleInfoPanel infoPanel,
      final boolean shot) {
    if (shot) {
      return true;
    }
    if (target != null) {
      int nComp = ai.getShip().getNumberOfComponents();
      for (int i = 0; i < nComp; i++) {
        ShipComponent weapon = ai.getShip().getComponent(i);
        setComponentUse(i);
        if (weapon != null && weapon.isWeapon() && !ai.isComponentUsed(i)
            && isClearShot(ai, target)
            && ai.getShip().componentIsWorking(i)) {
          int accuracy = calculateAccuracy(ai, weapon, target);
          ShipDamage shipDamage = new ShipDamage(ShipDamage.MISSED_ATTACK,
              "Attack missed!");
          if (DiceGenerator.getRandom(1, 100) <= accuracy) {
            shipDamage = target.getShip().damageBy(weapon,
                ai.getOverloadedComputer());
            if (shipDamage.getValue() <= ShipDamage.DAMAGED) {
              target.setDamaged();
            }
          }
          if (textLogger != null) {
            String[] logs = shipDamage.getMessage().split("\n");
            for (String log : logs) {
              textLogger.addLog(log);
            }
          }
          setAnimation(
              new CombatAnimation(ai, target, weapon, shipDamage.getValue()));
          ai.useComponent(i);
          ai.setCloakOverloaded(false);
          if (infoPanel != null) {
            infoPanel.useComponent(i);
          }
          ai.setAiShotsLeft(ai.getAiShotsLeft() - 1);
          setComponentUse(-1);
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Is sector free of ship
   * @param x X coordinate
   * @param y Y coordinate
   * @return True if free, false otherwise
   */
  private boolean isSectorFree(final int x, final int y) {
    for (CombatShip ship : combatShipList) {
      if (ship.getX() == x && ship.getY() == y) {
        return false;
      }
    }
    return true;
  }

  /**
   * Create worm hole coordinates
   */
  public void createWormHole() {
    int x;
    int y;
    for (int i = 0; i < 10; i++) {
      x = DiceGenerator.getRandom(MAX_X - 1);
      y = DiceGenerator.getRandom(3, 5);
      if (isSectorFree(x, y)) {
        wormHole = new Coordinate(x, y);
        if (isHumanPlayer()) {
          SoundPlayer.playSound(SoundPlayer.WORMHOLE);
        }
        return;
      }
    }
  }

  /**
   * Return wormhole coordinates if wormhole has appeared.
   * Otherwise this returns null.
   * @return Coordinate or null
   */
  public Coordinate getWormHoleCoordinate() {
    return wormHole;
  }
  /**
   * Does combat involve human player or not
   * @return True if human player is in combat otherwise false
   */
  public boolean isHumanPlayer() {
    if (getPlayer1().isHuman() || getPlayer2().isHuman()) {
      return true;
    }
    return false;
  }
  /**
   * Get how many turns to worm hole to appear
   * @return number of rounds for worm hole
   */
  public int getTimerForWormHole() {
    return timerForWormHole;
  }
  /**
   * Set how many turns to worm hole to appear
   * @param timer Set timer for wormhole to appear in combat.
   */
  public void setTimerForWormHole(final int timer) {
    timerForWormHole = timer;
  }
  /**
   * Has defender ship escaped from battle.
   * @return True if at least one ship has escaped.
   */
  public boolean defenderHasEscaped() {
    return defenderEscaped;
  }
  /**
   * Has attacker ship escaped from battle.
   * @return True if at least one ship has escaped.
   */
  public boolean attackerHasEscaped() {
    return attackerEscaped;
  }

  /**
   * Handle privateering against trader. This method assumes that privaateer
   * is next to trader. This method does not move privateer ship.
   * @param textLogger where logging is added if not null
   * @param infoPanel Infopanel where ship components are shown.
   *        This can be null too.
   * @param trader Trading ship to be privateer.
   * @return true if end round has been activated and component use
   *        should be cleared from UI. Otherwise false.
   */
  private boolean handlePrivateerShip(final Logger textLogger,
      final BattleInfoPanel infoPanel, final CombatShip trader) {
    CombatShip ai = getCurrentShip();
    Coordinate aiCoordinate = new Coordinate(ai.getX(), ai.getY());
    Coordinate traderCoordinate = new Coordinate(trader.getX(),
        trader.getY());
    int distance = (int) Math.round(aiCoordinate.calculateDistance(
        traderCoordinate));
    if (distance == 1) {
      int nComp = ai.getShip().getNumberOfComponents();
      for (int i = 0; i < nComp; i++) {
        ShipComponent weapon = ai.getShip().getComponent(i);
        setComponentUse(i);
        if (weapon != null && weapon.isPrivateer() && !ai.isComponentUsed(i)
            && ai.getShip().componentIsWorking(i) && canPrivateer(ai, trader)) {
          ShipDamage shipDamage = doPrivateering(ai.getPlayer(), ai,
              trader.getPlayer(), trader);
          shipDamage.ready();
          setAnimation(new CombatAnimation(ai, trader, weapon,
              shipDamage.getValue()));
          ai.useComponent(componentUse);
          if (textLogger != null) {
            String[] logs = shipDamage.getMessage().split("\n");
            for (String log : logs) {
              textLogger.addLog(log);
            }
          }
          if (infoPanel != null) {
            infoPanel.useComponent(i);
          }
          setComponentUse(-1);
          return true;
        }
      }
    }
    return false;
  }
  /**
   * Handle tractoring
   * @param textLogger where logging is added if not null
   * @param infoPanel Infopanel where ship components are shown.
   *        This can be null too.
   * @param target Target ship to be pullwd.
   * @return true if end round has been activated and component use
   *        should be cleared from UI. Otherwise false.
   */
  private boolean handleTractorShip(final Logger textLogger,
      final BattleInfoPanel infoPanel, final CombatShip target) {
    CombatShip ai = getCurrentShip();
    if (canTractor(ai, target)) {
      int nComp = ai.getShip().getNumberOfComponents();
      for (int i = 0; i < nComp; i++) {
        ShipComponent weapon = ai.getShip().getComponent(i);
        setComponentUse(i);
        if (weapon != null && weapon.isTractor() && !ai.isComponentUsed(i)
            && ai.getShip().componentIsWorking(i)) {
          ShipDamage shipDamage = doTractorBeam(ai, target);
          shipDamage.ready();
          setAnimation(new CombatAnimation(ai, target, weapon,
              shipDamage.getValue()));
          ai.useComponent(componentUse);
          if (textLogger != null) {
            String[] logs = shipDamage.getMessage().split("\n");
            for (String log : logs) {
              textLogger.addLog(log);
            }
          }
          if (infoPanel != null) {
            infoPanel.useComponent(i);
          }
          setComponentUse(-1);
          return true;
        }
      }
    }
    return false;
  }
  /**
   * AI handling for pure military ships.
   * @param textLogger where logging is added if not null
   * @param infoPanel Infopanel where ship components are shown.
   *        This can be null too.
   * @return true if end round has been activated and component use
   *        should be cleared from UI. Otherwise false.
   */
  private boolean handleAiMilitaryShip(final Logger textLogger,
      final BattleInfoPanel infoPanel) {
    CombatShip ai = getCurrentShip();
    boolean shootDeadliest = true;
    if (ai == null) {
      return true;
    }
    boolean privateer = ai.getShip().isPrivateeringShip();
    boolean tractor = ai.getShip().isTractorShip();
    PlayerInfo info = getCurrentShip().getPlayer();
    CombatShip deadliest = getMostPowerfulShip(info);
    CombatShip closest = getClosestEnemyShip(info, getCurrentShip());
    CombatShip trader = null;
    int range = ai.getShip().getMaxWeaponRange();
    if (privateer) {
      trader = getClosestTraderShip(info, ai);
      if (trader != null) {
        if (tractor && canTractor(ai, trader)) {
          handleTractorShip(textLogger, infoPanel, trader);
        }
        boolean privateered = handlePrivateerShip(textLogger, infoPanel,
            trader);
        if (privateered) {
          return true;
        }
        if (deadliest == null) {
          // There is no deadliest but there is trader,
          // so taking that as a target
          deadliest = trader;
          if (deadliest.getShip().getCargoType() == Ship.CARGO_TYPE_TRADE_GOODS
              || deadliest.getShip().getCargoType()
              == Ship.CARGO_TYPE_POPULATION) {
            shootDeadliest = false;
            range = 1;
          }
          if (closest == deadliest) {
            closest = null;
          }
        }
      } else if (deadliest == null) {
        return handleAiNonMilitaryShip(textLogger, infoPanel);
      }
    }
    boolean shot = false;
    if (deadliest != null) {
      if (ai.getShip().getTotalMilitaryPower() > deadliest.getShip()
          .getTotalMilitaryPower()) {
        range = ai.getShip().getMinWeaponRange();
        tractor = false;
      }
      if (tractor && canTractor(ai, deadliest)) {
        handleTractorShip(textLogger, infoPanel, deadliest);
      }
      Coordinate aiCoordinate = new Coordinate(ai.getX(), ai.getY());
      Coordinate deadliestCoordinate = new Coordinate(deadliest.getX(),
          deadliest.getY());
      int distance = (int) Math.round(aiCoordinate.calculateDistance(
          deadliestCoordinate));
      if (range <= distance - ai.getMovesLeft() && closest != null
          && !closest.isCloakOverloaded()) {
        int index = getCurrentShip().getComponentForUse(
            ShipComponentType.TARGETING_COMPUTER);
        if (index != -1 && getCurrentShip().getEnergyReserve() >= 0
            && DiceGenerator.getRandom(99) < 20
            && ai.getOverloadedComputer() == 0) {
          handleOverloading(textLogger, index);
        }
        shot = handleAIShoot(ai, closest, textLogger, infoPanel, shot);
      }
    } else if (closest != null && !closest.isCloakOverloaded()) {
      shot = handleAIShoot(ai, closest, textLogger, infoPanel, shot);
    }
    AStarSearch aStar = null;
    if (deadliest != null) {
      aStar = new AStarSearch(this, getCurrentShip(), deadliest, range);
    }
    if (aStar == null && closest != null) {
      aStar = new AStarSearch(this, getCurrentShip(), closest, range);
    }
    if (aStar != null && aStar.doSearch()) {
      aStar.doRoute();
    } else {
      // Could not found route for deadliest or closest one
      endRound(textLogger);
      return true;
    }
    if (deadliest != null
        && deadliest.getShip().getTotalMilitaryPower()
        < ai.getShip().getTotalMilitaryPower()
        && calculateDistance(ai, deadliest) > ai.getMovesLeft() + 1) {
      // Overload movement
      int index = getCurrentShip().getOverloadMove();
      if (index != -1 && getCurrentShip().getEnergyReserve() >= 1
          && DiceGenerator.getRandom(99) < 20) {
        handleOverloading(textLogger, index);
      }
    }
    PathPoint point = aStar.getMove();
    if (ai.getShip().getTacticSpeed() == 0 && deadliest != null
        && !deadliest.isCloakOverloaded()) {
      int index = getCurrentShip().getComponentForUse(
          ShipComponentType.TARGETING_COMPUTER);
      if (index != -1 && getCurrentShip().getEnergyReserve() >= 0
          && DiceGenerator.getRandom(99) < 20
          && ai.getOverloadedComputer() == 0) {
        handleOverloading(textLogger, index);
      }
      shot = handleAIShoot(ai, deadliest, textLogger, infoPanel, shot);
    }
    if (point != null && !isBlocked(point.getX(), point.getY())
        && ai.getMovesLeft() > 0) {
      if (deadliest != null && shootDeadliest
          && !deadliest.isCloakOverloaded()) {
        int index = getCurrentShip().getComponentForUse(
            ShipComponentType.TARGETING_COMPUTER);
        if (index != -1 && getCurrentShip().getEnergyReserve() >= 0
            && DiceGenerator.getRandom(99) < 20
            && ai.getOverloadedComputer() == 0) {
          handleOverloading(textLogger, index);
        }
        shot = handleAIShoot(ai, deadliest, textLogger, infoPanel, shot);
      }
      if (!shot) {
        // Not moving after shooting
        ai.setMovesLeft(ai.getMovesLeft() - 1);
        ai.setX(point.getX());
        ai.setY(point.getY());
        if (aStar.isLastMove()) {
          ai.setMovesLeft(0);
        } else {
          aStar.nextMove();
        }
        if (textLogger != null && infoPanel != null) {
          // Play sound only if battle view is used
          SoundPlayer.playEngineSound();
        }
      } else {
        return false;
      }
    } else {
      // Path is blocked
      ai.setMovesLeft(0);
    }
    if (ai.getMovesLeft() == 0 && getAnimation() == null) {
      if (ai.getAiShotsLeft() > 0) {
        shot = false;
        if (shootDeadliest && deadliest != null
            && !deadliest.isCloakOverloaded()) {
          // We still got more shots left, let's shoot the deadliest
          shot = handleAIShoot(ai, deadliest, textLogger, infoPanel, shot);
        } else {
          if (privateer) {
            trader = getClosestTraderShip(info, ai);
            if (trader != null) {
              boolean privateered = handlePrivateerShip(textLogger, infoPanel,
                  trader);
              if (privateered) {
                return true;
              }
            }
          }
        }
        if (!shot) {
          // Deadliest wasn't close enough, let's shoot the closest
          closest = getClosestEnemyShip(info, getCurrentShip());
          if (closest != deadliest && closest != null
              && !closest.isCloakOverloaded()) {
            int index = getCurrentShip().getComponentForUse(
                ShipComponentType.TARGETING_COMPUTER);
            if (index != -1 && getCurrentShip().getEnergyReserve() >= 0
                && DiceGenerator.getRandom(99) < 20
                && ai.getOverloadedComputer() == 0) {
              handleOverloading(textLogger, index);
            }
            shot = handleAIShoot(ai, closest, textLogger, infoPanel, shot);
          }
          if (!shot) {
            // Even closest was too far away, ending the turn now
            aStar = null;
            if (closest != null) {
              overloadDefense(textLogger, closest);
            }
            endRound(textLogger);
            return true;
          }
        }
      } else {
        aStar = null;
        if (deadliest != null) {
          overloadDefense(textLogger, deadliest);
        }
        endRound(textLogger);
        return true;
      }
    }
    if (getAnimation() == null && ai.getAiShotsLeft() == 0
        && ai.getMovesLeft() == 0) {
      if (deadliest != null) {
        overloadDefense(textLogger, deadliest);
      }
      endRound(textLogger);
    }
    return false;
  }

  /**
   * Overload ship's possible defenses if needed
   * @param textLogger where logging is added if not null
   * @param attacker CombatShip which is the biggest threat.
   */
  private void overloadDefense(final Logger textLogger,
      final CombatShip attacker) {
    if (getCurrentShip().getShip().getShield() < getCurrentShip()
        .getShip().getTotalShield()) {
      // Faster shield generating
      int index = getCurrentShip().getComponentForUse(
          ShipComponentType.SHIELD);
      if (index != -1 && getCurrentShip().getEnergyReserve() >= 0
          && DiceGenerator.getRandom(99) < 20) {
        handleOverloading(textLogger, index);
      }
    }
    if (attacker != null
        && calculateDistance(getCurrentShip(), attacker) < 3) {
      // Better defense
      int index = getCurrentShip().getComponentForUse(
          ShipComponentType.JAMMER);
      boolean overloaded = false;
      if (index != -1 && getCurrentShip().getEnergyReserve() >= 0
          && DiceGenerator.getRandom(99) < 20) {
        overloaded = handleOverloading(textLogger, index);
      }
      if (!overloaded) {
        index = getCurrentShip().getComponentForUse(
            ShipComponentType.CLOAKING_DEVICE);
        if (index != -1 && getCurrentShip().getEnergyReserve() >= 0
            && DiceGenerator.getRandom(99) < 20) {
          overloaded = handleOverloading(textLogger, index);
        }
      }
    }
  }
  /**
   * AI handling for non military ships.
   * @param textLogger where logging is added if not null
   * @param infoPanel Infopanel where ship components are shown.
   *        This can be null too.
   * @return true if end round has been activated and component use
   *        should be cleared from UI. Otherwise false.
   */
  private boolean handleAiNonMilitaryShip(final Logger textLogger,
      final BattleInfoPanel infoPanel) {
    CombatShip ai = getCurrentShip();
    if (ai == null) {
      return true;
    }
    PlayerInfo info = getCurrentShip().getPlayer();
    CombatShip closest = getClosestEnemyShip(info, getCurrentShip());
    AStarSearch aStar = null;
    if (wormHole != null) {
      aStar = new AStarSearch(this, getCurrentShip(), wormHole, 0);
    } else if (closest != null) {
      Coordinate farAway = getFarestFormEnemy(closest);
      aStar = new AStarSearch(this, getCurrentShip(), farAway, 0);
    }
    if (aStar != null && aStar.doSearch()) {
      aStar.doRoute();
    } else {
      // Could not found route for faraway or wormhole
      endRound(textLogger);
      return true;
    }
    if (wormHole != null) {
      // Overload movement
      int index = getCurrentShip().getOverloadMove();
      if (index != -1 && getCurrentShip().getEnergyReserve() >= 0
          && DiceGenerator.getRandom(99) < 20) {
        handleOverloading(textLogger, index);
      }
    }
    overloadDefense(textLogger, closest);
    PathPoint point = aStar.getMove();
    if (point != null && !isBlocked(point.getX(), point.getY())
        && ai.getMovesLeft() > 0) {
      // Not moving after shooting
      ai.setMovesLeft(ai.getMovesLeft() - 1);
      ai.setX(point.getX());
      ai.setY(point.getY());
      aStar.nextMove();
      if (wormHole != null && ai.getX() == wormHole.getX()
          && ai.getY() == wormHole.getY()) {
        if (textLogger != null && infoPanel != null) {
          // Play sound only if battle view is used
          SoundPlayer.playSound(SoundPlayer.TELEPORT);
        }
        if (textLogger != null) {
          textLogger.addLog(ai.getShip().getName() + " escapes from combat!");
        }
        escapeShip(ai);
      }
      if (textLogger != null && infoPanel != null) {
        // Play sound only if battle view is used
        SoundPlayer.playEngineSound();
      }
    } else {
      // Path is blocked
      ai.setMovesLeft(0);
    }
    if ((ai.getMovesLeft() == 0 || aStar.isLastMove())
        && getAnimation() == null) {
      aStar = null;
      endRound(textLogger);
      return true;
    }
    if (getAnimation() == null && ai.getMovesLeft() == 0) {
      endRound(textLogger);
    }
    return false;
  }

  /**
   * Handle Overloading of component.
   * @param textLogger TextLogger for giving out the information
   * @param index Ship component index for overloading
   * @return True if overloading handled
   */
  public boolean handleOverloading(final Logger textLogger, final int index) {
    if (!getCurrentShip().isComponentUsed(index)) {
      ShipComponent component = getCurrentShip().getShip()
          .getComponent(index);
      Ship ship = getCurrentShip().getShip();
      if (component.getType() == ShipComponentType.ENGINE
          && ship.componentIsWorking(index)
          && getCurrentShip().getEnergyLevel() > 1
          && !ship.isStarBase()) {
        getCurrentShip().setEnergyLevel(
            getCurrentShip().getEnergyLevel() - 1);
        if (!getCurrentShip().isOverloadFailure(index)) {
          getCurrentShip().setMovesLeft(
              getCurrentShip().getMovesLeft() + 1);
          if (textLogger != null) {
            textLogger.addLog(component.getName() + " overloaded!");
            SoundPlayer.playSound(SoundPlayer.ENGINE_OVERLOAD);
          }
        } else {
          if (textLogger != null) {
            textLogger.addLog(component.getName()
                + " got damaged during overload!");
            CombatAnimation shieldAnim = new CombatAnimation(
                getCurrentShip(), getCurrentShip(),
                CombatAnimationType.EXPLOSION, -1);
            setAnimation(shieldAnim);
          }
        }
        getCurrentShip().useComponent(index);
        getCurrentShip().setOverloaded(true);
        return true;
      }
      if (component.getType() == ShipComponentType.THRUSTERS
          && ship.componentIsWorking(index)
          && getCurrentShip().getEnergyLevel() > 0
          && !ship.isStarBase()) {
        getCurrentShip().setEnergyLevel(
            getCurrentShip().getEnergyLevel() - 1);
        if (!getCurrentShip().isOverloadFailure(index)) {
          getCurrentShip().setMovesLeft(
              getCurrentShip().getMovesLeft() + 1);
          if (textLogger != null) {
            textLogger.addLog(component.getName() + " overloaded!");
            SoundPlayer.playSound(SoundPlayer.ENGINE_OVERLOAD);
          }
        } else {
          if (textLogger != null) {
            textLogger.addLog(component.getName()
                + " got damaged during overload!");
            CombatAnimation shieldAnim = new CombatAnimation(
                getCurrentShip(), getCurrentShip(),
                CombatAnimationType.EXPLOSION, -1);
            setAnimation(shieldAnim);
          }
        }
        getCurrentShip().useComponent(index);
        getCurrentShip().setOverloaded(true);
        return true;
      }
      if (component.getType() == ShipComponentType.SHIELD
          && ship.componentIsWorking(index)
          && getCurrentShip().getEnergyLevel() > 0
          && ship.getShield() < ship.getTotalShield()) {
        getCurrentShip().setEnergyLevel(
            getCurrentShip().getEnergyLevel() - 1);
        if (!getCurrentShip().isOverloadFailure(index)) {
          ship.setShield(ship.getShield() + 1);
          CombatAnimation shieldAnim = new CombatAnimation(
              getCurrentShip(), getCurrentShip(), CombatAnimationType.SHIELD,
              1);
          setAnimation(shieldAnim);
          if (textLogger != null) {
            textLogger.addLog(component.getName() + " overloaded!");
            SoundPlayer.playShieldSound();
          }
        } else {
          if (textLogger != null) {
            textLogger.addLog(component.getName()
                + " got damaged during overload!");
            CombatAnimation shieldAnim = new CombatAnimation(
                getCurrentShip(), getCurrentShip(),
                CombatAnimationType.EXPLOSION, -1);
            setAnimation(shieldAnim);
          }
        }
        getCurrentShip().useComponent(index);
        getCurrentShip().setOverloaded(true);
        return true;
      }
      if (component.getType() == ShipComponentType.JAMMER
          && ship.componentIsWorking(index)
          && getCurrentShip().getEnergyLevel() > 0) {
        getCurrentShip().setEnergyLevel(
            getCurrentShip().getEnergyLevel() - 1);
        if (!getCurrentShip().isOverloadFailure(index)) {
          getCurrentShip().setOverloadedJammer(
              component.getDefenseValue() * 3);
          if (textLogger != null) {
            textLogger.addLog(component.getName() + " overloaded!");
            SoundPlayer.playSound(SoundPlayer.JAMMER_OVERLOAD);
          }
        } else {
          if (textLogger != null) {
            textLogger.addLog(component.getName()
                + " got damaged during overload!");
            CombatAnimation shieldAnim = new CombatAnimation(
                getCurrentShip(), getCurrentShip(),
                CombatAnimationType.EXPLOSION, -1);
            setAnimation(shieldAnim);
          }
        }
        getCurrentShip().useComponent(index);
        getCurrentShip().setOverloaded(true);
        return true;
      }
      if (component.getType() == ShipComponentType.TARGETING_COMPUTER
          && ship.componentIsWorking(index)
          && getCurrentShip().getEnergyLevel() > 0) {
        getCurrentShip().setEnergyLevel(
            getCurrentShip().getEnergyLevel() - 1);
        if (!getCurrentShip().isOverloadFailure(index)) {
          getCurrentShip().setOverloadedComputer(
              component.getDamage());
          if (textLogger != null) {
            textLogger.addLog(component.getName() + " overloaded!");
            SoundPlayer.playSound(SoundPlayer.COMPUTER_OVERLOAD);
          }
        } else {
          if (textLogger != null) {
            textLogger.addLog(component.getName()
                + " got damaged during overload!");
            CombatAnimation shieldAnim = new CombatAnimation(
                getCurrentShip(), getCurrentShip(),
                CombatAnimationType.EXPLOSION, -1);
            setAnimation(shieldAnim);
          }
        }
        getCurrentShip().useComponent(index);
        getCurrentShip().setOverloaded(true);
        return true;
      }
      if (component.getType() == ShipComponentType.CLOAKING_DEVICE
          && ship.componentIsWorking(index)
          && getCurrentShip().getEnergyLevel() > 0) {
        getCurrentShip().setEnergyLevel(
            getCurrentShip().getEnergyLevel() - 1);
        if (!getCurrentShip().isOverloadFailure(index)) {
          getCurrentShip().setCloakOverloaded(true);
          if (textLogger != null) {
            textLogger.addLog(component.getName() + " overloaded!");
            SoundPlayer.playSound(SoundPlayer.CLOAK_OVERLOAD);
          }
        } else {
          if (textLogger != null) {
            textLogger.addLog(component.getName()
                + " got damaged during overload!");
            CombatAnimation shieldAnim = new CombatAnimation(
                getCurrentShip(), getCurrentShip(),
                CombatAnimationType.EXPLOSION, -1);
            setAnimation(shieldAnim);
          }
        }
        getCurrentShip().useComponent(index);
        getCurrentShip().setOverloaded(true);
        return true;
      }
    }
    return false;
  }
  /**
   * @param textLogger where logging is added if not null
   * @param infoPanel Infopanel where ship components are shown.
   *        This can be null too.
   * @return true if end round has been activated and component use
   *        should be cleared from UI. Otherwise false.
   */
  public boolean handleAI(final Logger textLogger,
      final BattleInfoPanel infoPanel) {
    CombatShip ai = getCurrentShip();
    if (ai != null && ai.getShip().getTotalMilitaryPower() > 0) {
      if (ai.getShip().isStarBase()
          && ai.getShip().getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
        return handleAiMilitaryShip(textLogger, infoPanel);
      }
      if (!ai.getShip().isStarBase()) {
        return handleAiMilitaryShip(textLogger, infoPanel);
      }
      // Starbase not deployed so it cannot shoot
      return handleAiNonMilitaryShip(textLogger, infoPanel);
    }
    return handleAiNonMilitaryShip(textLogger, infoPanel);
  }

  /**
   * Do tractor beam on target ship
   * @param tractor Ship which is pulling
   * @param target Target who is being pulled.
   * @return Just textual description what happened.
   */
  public ShipDamage doTractorBeam(final CombatShip tractor,
      final CombatShip target) {
    ShipDamage result = null;
    int mx = 0;
    int my = 0;
    if (tractor.getX() - target.getX() == -2) {
      mx = -1;
    }
    if (tractor.getX() - target.getX() == 2) {
      mx = 1;
    }
    if (tractor.getY() - target.getY() == -2) {
      my = -1;
    }
    if (tractor.getY() - target.getY() == 2) {
      my = 1;
    }
    CombatShip blocking = getShipFromCoordinate(target.getX() + mx,
        target.getY() + my);
    if (blocking != null) {
      result = new ShipDamage(1, "Target is being blocked by another ship!");
    } else {
      target.setX(target.getX() + mx);
      target.setY(target.getY() + my);
      result = new ShipDamage(1, "Target is being pulled by tractor beam!");
      if (target.getShip().getTotalMilitaryPower() == 0
          && target.getShip().getSpeed() == 0) {
        result = new ShipDamage(1, "Target is being pulled by tractor beam and"
            + " captured since all weapons and engines are down!");
        Fleet origin = null;
        Fleet stealer = null;
        if (target.getPlayer() == attackerInfo) {
          origin = attackerFleet;
          stealer = defenderFleet;
        } else if (target.getPlayer() == defenderInfo) {
          origin = defenderFleet;
          stealer = attackerFleet;
        }
        if (origin != null && stealer != null) {
          stealShipFromFleet(target, origin, stealer);
        }
      }
    }
    if (wormHole != null && wormHole.getX() == target.getX()
        && wormHole.getY() == target.getY()) {
      result = new ShipDamage(1, "Target is being pulled by tractor beam"
          + " directly to escape!");
      escapeShip(target);
    }
    return result;
  }
  /**
   * Do privateering with certain combat ship and player against
   * another combat ship and player.
   * @param privateer PlayerInfo who is being privateer
   * @param pirateShip Pirate space ship
   * @param targetPlayer Victim player
   * @param targetShip Victim ship
   * @return ShipDamage if privateering was succesful
   */
  public ShipDamage doPrivateering(final PlayerInfo privateer,
      final CombatShip pirateShip, final PlayerInfo targetPlayer,
      final CombatShip targetShip) {
    ShipDamage result = null;
    Leader commander = null;
    if (attackerFleet.isShipInFleet(pirateShip.getShip())) {
      commander = attackerFleet.getCommander();
    }
    if (defenderFleet.isShipInFleet(pirateShip.getShip())) {
      commander = defenderFleet.getCommander();
    }
    if (pirateShip.getShip().isPrivateeringShip()) {
      int cargoType = targetShip.getShip().getCargoType();
      if (cargoType == Ship.CARGO_TYPE_TRADE_GOODS) {
        targetShip.getShip().setFlag(Ship.FLAG_MERCHANT_LEFT_HOMEWORLD, false);
        targetShip.getShip().setFlag(Ship.FLAG_MERCHANT_LEFT_OPPONENWORLD,
            false);
        targetShip.getShip().setTradeDistance(null);
        pirateShip.setPrivateeredCredits(
            pirateShip.getPrivateeredCredits() + 3);
        result = new ShipDamage(1, "Privateered trade goods from trade ship!");
        if (commander != null) {
          commander.getStats().addOne(StatType.NUMBER_OF_PRIVATEERING);
        }
      }
      if (cargoType == Ship.CARGO_TYPE_POPULATION) {
        targetShip.getShip().setColonist(0);
        pirateShip.setPrivateeredCredits(
            pirateShip.getPrivateeredCredits() + 1);
        result = new ShipDamage(1, "Murderered colonists and stole valuables!");
        if (commander != null) {
          commander.getStats().addOne(StatType.NUMBER_OF_PRIVATEERING);
        }
      }
      if (cargoType == Ship.CARGO_TYPE_TROOPS) {
        result = new ShipDamage(1, "Ship is full of troops and cannot be"
            + " raided!");
      }
      if (cargoType == Ship.CARGO_TYPE_NO_CARGO) {
        result = new ShipDamage(1, "Ship has no cargo!");
      }
      if (cargoType == Ship.CARGO_TYPE_METAL) {
        if (pirateShip.getShip().getFreeCargoMetal() > 0) {
          if (pirateShip.getShip().getFreeCargoMetal()
              >= targetShip.getShip().getMetal()) {
            pirateShip.getShip().setMetal(pirateShip.getShip().getMetal()
                + targetShip.getShip().getMetal());
            targetShip.getShip().setMetal(0);
            result = new ShipDamage(1, "All valuable metal has been stolen!");
            if (commander != null) {
              commander.getStats().addOne(StatType.NUMBER_OF_PRIVATEERING);
            }
          } else {
            int stolen = pirateShip.getShip().getFreeCargoMetal();
            pirateShip.getShip().setMetal(pirateShip.getShip().getMetal()
                + stolen);
            targetShip.getShip().setMetal(targetShip.getShip().getMetal()
                - stolen);
            result = new ShipDamage(1, "You raided " + stolen
                + " units of metal!");
            if (commander != null) {
              commander.getStats().addOne(StatType.NUMBER_OF_PRIVATEERING);
            }
          }
        } else {
          result = new ShipDamage(1, "Cargo cannot be fitted in your ship!");
        }
      }
    }
    return result;
  }

  /**
   * Get the defeding fleet information.
   * @return Defending fleet
   */
  public Fleet getDefendingFleet() {
    return defenderFleet;
  }
}
