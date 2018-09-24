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
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.history.event.CombatEvent;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.Logger;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018  Tuomo Untinen
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
    this.attackerInfo = attackerInfo;
    this.defenderInfo = defenderInfo;
    starbaseFleet = null;
    combatEvent = new CombatEvent(defenderFleet.getCoordinate());
    String combatText = attackerInfo.getEmpireName() + " attacked against "
        + defenderInfo.getEmpireName() + " with ";
    if (attackerFleet.getNumberOfShip() > 1) {
      combatText = combatText + attackerFleet.getNumberOfShip()
        + " ships against ";
    } else {
      combatText = combatText + " single ship against ";
    }
    if (defenderFleet.getNumberOfShip() > 1) {
      combatText = combatText + defenderFleet.getNumberOfShip() + " ships.";
    } else {
      combatText = combatText + "one ship.";
    }
    combatEvent.setText(combatText);
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
    if (starbaseFleet != null && starbaseFleet != this.defenderFleet) {
      CombatPositionList starbaseList = new StarbasePositionList();
      addCombatShipList(starbaseFleet, defenderInfo, starbaseList, true);
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
 * Add combatShip to combatShipList
 * @param fleet Player's Fleet
 * @param playerInfo Player's information
 * @param positionList starting coordinate list
 * @param flipY Should ship's image have flipped Y axel.
 */
private void addCombatShipList(final Fleet fleet, final PlayerInfo playerInfo,
        final CombatPositionList positionList, final boolean flipY) {
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
              combatShipX, combatShipY, flipY);
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
          && ship.getShip().getTotalMilitaryPower() > strongestPower) {
        strongestShip = ship;
        strongestPower = strongestShip.getShip().getTotalMilitaryPower();
      }
    }
    return strongestShip;
  }

  /**
   * Destroy one single ship from the fleet and combat
   * @param ship Combat ship
   */
  public void destroyShip(final CombatShip ship) {
    if (attackerFleet.isShipInFleet(ship.getShip())) {
      destroyShipFromFleet(ship, attackerFleet);
      attackerInfo.getFleets().recalculateList();
    } else if (defenderFleet.isShipInFleet(ship.getShip())) {
      destroyShipFromFleet(ship, defenderFleet);
      defenderInfo.getFleets().recalculateList();
    } else if (starbaseFleet.isShipInFleet(ship.getShip())) {
      destroyShipFromFleet(ship, starbaseFleet);
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
      getCurrentShip().reInitShipForRound();
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
   * Destroy ship for fleet's list.
   * @param ship ship to destroy from the fleet
   * @param fleet containing the ship
   */
  private void destroyShipFromFleet(final CombatShip ship, final Fleet fleet) {
    fleet.removeShip(ship.getShip());
    ShipStat stat = animation.getShooter().getPlayer()
        .getShipStatByName(animation.getShooter().getShip().getName());
    Ship shooter = animation.getShooter().getShip();
    if (shooter != null && shooter.getExperience() < 5) {
      shooter.setExperience(shooter.getExperience() + 1);
    }
    if (stat != null) {
      stat.setNumberOfKills(stat.getNumberOfKills() + 1);
    }
    stat = ship.getPlayer().getShipStatByName(ship.getShip().getName());
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
      if (ship.getPlayer() != info) {
        CombatCoordinate centerCoordinate =
                new CombatCoordinate(friendlyShip.getX(),
            friendlyShip.getY());
        CombatCoordinate shipCoordinate =
                new CombatCoordinate(ship.getX(), ship.getY());
        double distance = centerCoordinate.calculateDistance(shipCoordinate);
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
      if (ship.getPlayer() != info) {
        CombatCoordinate centerCoordinate =
                new CombatCoordinate(friendlyShip.getX(),
            friendlyShip.getY());
        CombatCoordinate shipCoordinate =
                new CombatCoordinate(ship.getX(), ship.getY());
        double distance = centerCoordinate.calculateDistance(shipCoordinate);
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
   * Get next ship is list. List contains both player's fleets
   * in initiative order. After this ship can be fetched with
   * getCurrentShip() method.
   */
  public void nextShip() {
    shipIndex++;
    if (combatShipList.size() <= shipIndex) {
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
      ship.reInitShipForRound();
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
      } else {
        winnerPlayer = defenderInfo;
        looserPlayer = attackerInfo;
        winnerFleet = defenderFleet;
        looserFleet = attackerFleet;
        loserEscaped = attackerHasEscaped();
        isWinnerAttacker = false;
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
          looserPlayer.getFleets().remove(looserIndex);
        }
      }
      String combatText = combatEvent.getText();
      if (planet != null) {
        combatText = combatText + " Combat happened in orbit of "
        + planet.getName() + ".";
        combatEvent.setPlanetName(planet.getName());
      }
      if (winnerFleet.getNumberOfShip() > 1) {
        combatText = combatText + " Combat was victorious for "
            + winnerPlayer.getEmpireName() + ". "
            + winnerFleet.getNumberOfShip()
            + " ships in victorious fleet survived. ";
      } else {
        combatText = combatText + " Combat was victorious for "
            + winnerPlayer.getEmpireName() + ". "
            + "Single ship survived in victorious fleet. ";
      }
      if (loserEscaped && escapePosition != null) {
        if (looserFleet.getNumberOfShip() > 1) {
          combatText = combatText + "Defending fleet's "
              + looserFleet.getNumberOfShip()
              + "ships escaped from the combat.";
        } else {
          combatText = combatText + "Defending fleet's "
              + "last ship escaped from the combat.";
        }
      } else {
        combatText = combatText + "Defending fleet was totally destroyed!";
      }
      combatEvent.setText(combatText);
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
              System.out.println(msg);
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
   * @return true if Combat is over, otherwise false.
   */
  public boolean endRound() {
    if (timerForWormHole > 0) {
      timerForWormHole--;
    } else if (wormHole == null) {
      createWormHole();
    }
    setComponentUse(-1);
    nextShip();
    boolean over = isCombatOver();
    if (over) {
      handleEndCombat();
    }
    return over;
  }

  /**
   * Handle AI shooting
   * @param ai AI which is shooting
   * @param target shooting target
   * @param textLogger where logging is added if not null
   * @param infoPanel Infopanel where ship components are shown.
   *        This can be null too.
   * @return true if shooting was actually done
   */
  public boolean handleAIShoot(final CombatShip ai, final CombatShip target,
      final Logger textLogger, final BattleInfoPanel infoPanel) {
    if (target != null) {
      int nComp = ai.getShip().getNumberOfComponents();
      for (int i = 0; i < nComp; i++) {
        ShipComponent weapon = ai.getShip().getComponent(i);
        setComponentUse(i);
        if (weapon != null && weapon.isWeapon() && !ai.isComponentUsed(i)
            && isClearShot(ai, target)
            && ai.getShip().componentIsWorking(i)) {
          int accuracy = ai.getShip().getHitChance(weapon)
              + ai.getBonusAccuracy();
          accuracy = accuracy - target.getShip().getDefenseValue();
          if (accuracy < 5) {
            accuracy = 5;
          }
          ShipDamage shipDamage = new ShipDamage(1, "Attack missed!");
          if (DiceGenerator.getRandom(1, 100) <= accuracy) {
            shipDamage = target.getShip().damageBy(weapon);
            if (shipDamage.getValue() == ShipDamage.DAMAGED) {
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
    PlayerInfo info = getCurrentShip().getPlayer();
    CombatShip deadliest = getMostPowerfulShip(info);
    CombatShip closest = getClosestEnemyShip(info, getCurrentShip());
    CombatShip trader = null;
    int range = ai.getShip().getMaxWeaponRange();
    if (privateer) {
      trader = getClosestTraderShip(info, ai);
      if (trader != null) {
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
      }
    }
    boolean shot = false;
    if (deadliest != null) {
      if (ai.getShip().getTotalMilitaryPower() > deadliest.getShip()
          .getTotalMilitaryPower()) {
        range = ai.getShip().getMinWeaponRange();
      }
      Coordinate aiCoordinate = new Coordinate(ai.getX(), ai.getY());
      Coordinate deadliestCoordinate = new Coordinate(deadliest.getX(),
          deadliest.getY());
      int distance = (int) Math.round(aiCoordinate.calculateDistance(
          deadliestCoordinate));
      if (range < distance - ai.getMovesLeft() && closest != null) {
        shot = handleAIShoot(ai, closest, textLogger, infoPanel);
      }
    } else if (closest != null) {
      shot = handleAIShoot(ai, closest, textLogger, infoPanel);
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
      endRound();
      return true;
    }
    PathPoint point = aStar.getMove();
    if (ai.getShip().getTacticSpeed() == 0) {
      shot = handleAIShoot(ai, deadliest, textLogger, infoPanel);
    }
    if (point != null && !isBlocked(point.getX(), point.getY())
        && ai.getMovesLeft() > 0) {
      if (shootDeadliest) {
        shot = handleAIShoot(ai, deadliest, textLogger, infoPanel);
      }
      if (!shot) {
        // Not moving after shooting
        ai.setMovesLeft(ai.getMovesLeft() - 1);
        ai.setX(point.getX());
        ai.setY(point.getY());
        aStar.nextMove();
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
    if ((ai.getMovesLeft() == 0 || aStar.isLastMove())
        && getAnimation() == null) {
      if (ai.getAiShotsLeft() > 0) {
        shot = false;
        if (shootDeadliest) {
          // We still got more shots left, let's shoot the deadliest
          shot = handleAIShoot(ai, deadliest, textLogger, infoPanel);
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
          if (closest != deadliest) {
            shot = handleAIShoot(ai, closest, textLogger, infoPanel);
          }
          if (!shot) {
            // Even closest was too far away, ending the turn now
            aStar = null;
            endRound();
            return true;
          }
        }
      } else {
        aStar = null;
        endRound();
        return true;
      }
    }
    if (getAnimation() == null && ai.getAiShotsLeft() == 0
        && ai.getMovesLeft() == 0) {
      endRound();
    }
    return false;
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
    } else {
      Coordinate farAway = getFarestFormEnemy(closest);
      aStar = new AStarSearch(this, getCurrentShip(), farAway, 0);
    }
    if (aStar.doSearch()) {
      aStar.doRoute();
    } else {
      // Could not found route for faraway or wormhole
      endRound();
      return true;
    }
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
      endRound();
      return true;
    }
    if (getAnimation() == null && ai.getMovesLeft() == 0) {
      endRound();
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
      }
      if (cargoType == Ship.CARGO_TYPE_POPULATION) {
        targetShip.getShip().setColonist(0);
        pirateShip.setPrivateeredCredits(
            pirateShip.getPrivateeredCredits() + 1);
        result = new ShipDamage(1, "Murderered colonists and stole valuables!");
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
          } else {
            int stolen = pirateShip.getShip().getFreeCargoMetal();
            pirateShip.getShip().setMetal(pirateShip.getShip().getMetal()
                + stolen);
            targetShip.getShip().setMetal(targetShip.getShip().getMetal()
                - stolen);
            result = new ShipDamage(1, "You raided " + stolen
                + " units of metal!");
          }
        } else {
          result = new ShipDamage(1, "Cargo cannot be fitted in your ship!");
        }
      }
    }
    return result;
  }

}
