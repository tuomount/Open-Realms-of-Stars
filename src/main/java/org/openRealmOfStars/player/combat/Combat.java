package org.openRealmOfStars.player.combat;

import java.util.ArrayList;
import java.util.Collections;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.AI.PathFinding.PathPoint;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.gui.infopanel.BattleInfoPanel;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipDamage;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.Logger;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2017  Tuomo Untinen
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
  private ArrayList<CombatShip> shipList;

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
  private Fleet fleet1;
  /**
   * Second player's fleet
   */
  private Fleet fleet2;

  /**
   * Player Info for winner
   */
  private PlayerInfo winner;

  /**
   * Player info for attacker
   */
  private PlayerInfo info1;

  /**
   * Player Info for defender
   */
  private PlayerInfo info2;

  /**
   * Planet orbit where combat happens. This can be null.
   */
  private Planet planet;

  /**
   * Is end combat handled or not
   */
  private boolean endCombatHandled = false;

  /**
   * Build shipList in initiative order
   * @param fleet1 Attacking Player1 fleet
   * @param fleet2 Defending Player2 fleet
   * @param info1 Attacking Player1 info
   * @param info2 Defending Player2 Info
   */
  public Combat(final Fleet fleet1, final Fleet fleet2, final PlayerInfo info1,
      final PlayerInfo info2) {
    this.fleet1 = fleet1;
    this.fleet2 = fleet2;
    this.info1 = info1;
    this.info2 = info2;
    Ship[] ships = fleet1.getShips();
    int index = 0;
    shipList = new ArrayList<>();
    for (Ship ship : ships) {
      ShipStat stat = info1.getShipStatByName(ship.getName());
      if (stat != null) {
        stat.setNumberOfCombats(stat.getNumberOfCombats() + 1);
      }
      CombatShip combatShp = new CombatShip(ship, info1,
          getStartPos(index, CombatTeam.BOTTOMTEAM, true), getStartPos(index, CombatTeam.BOTTOMTEAM, false), false);
      if (fleet1.getRoute() != null && fleet1.getRoute().isDefending()) {
        combatShp.setBonusAccuracy(5);
      }
      shipList.add(combatShp);
      index++;
    }
    ships = fleet2.getShips();
    index = 0;
    for (Ship ship : ships) {
      ShipStat stat = info2.getShipStatByName(ship.getName());
      if (stat != null) {
        stat.setNumberOfCombats(stat.getNumberOfCombats() + 1);
      }
      CombatShip combatShp = new CombatShip(ship, info2,
          getStartPos(index, CombatTeam.TOPTEAM, true), getStartPos(index, CombatTeam.TOPTEAM, false), true);
      if (fleet2.getRoute() != null && fleet2.getRoute().isDefending()) {
        combatShp.setBonusAccuracy(5);
      }
      shipList.add(combatShp);
      index++;
    }
    Collections.sort(shipList, Collections.reverseOrder());
    index = 0;
    componentUse = -1;
    animation = null;
    winner = null;
    setPlanet(null);
    endCombatHandled = false;
  }

  /**
   * Get first player's info which is the attacker
   * @return Player Info
   */
  public PlayerInfo getPlayer1() {
    return info1;
  }

  /**
   * Get second player's info which is the defender
   * @return Player info
   */
  public PlayerInfo getPlayer2() {
    return info2;
  }

  /**
   * Get the winner.
   * @return PlayerInfo who won or null
   */
  public PlayerInfo getWinner() {
    return winner;
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
      double sx = shooter.getX();
      double sy = shooter.getY();
      double endX = target.getX();
      double endY = target.getY();
      double mx;
      double my;
      double dx = Math.abs(sx - endX);
      double dy = Math.abs(sy - endY);
      int distance = (int) dy;
      if (dx > dy) {
        distance = (int) dx;
      }
      if (distance > 0) {
        mx = (endX - sx) / distance;
        my = (endY - sy) / distance;
      } else {
        mx = 0;
        my = 0;
      }
      if (weapon.getWeaponRange() >= distance && distance > 0) {
        for (int i = 0; i < distance + 1; i++) {
          sx = sx + mx;
          sy = sy + my;
          int ix = (int) Math.round(sx);
          int iy = (int) Math.round(sy);
          if (ix == target.getX() && iy == target.getY()) {
            result = true;
            break;
          }
          if (isBlocked(ix, iy)) {
            result = false;
            break;
          }
        }
      }

    }

    return result;
  }

  /**
   * Get most powerful ship opponent has or null
   * @param info Player Info who is doing the comparison.
   * @return CombatShip or null
   */
  public CombatShip getMostPowerfulShip(final PlayerInfo info) {
    int power = 0;
    CombatShip result = null;
    for (CombatShip ship : shipList) {
      if (ship.getPlayer() != info
          && ship.getShip().getTotalMilitaryPower() > power) {
        result = ship;
        power = result.getShip().getTotalMilitaryPower();
      }
    }
    return result;
  }

  /**
   * Destroy one single ship from the fleet and combat
   * @param ship Combat ship
   */
  public void destroyShip(final CombatShip ship) {
    if (fleet1.isShipInFleet(ship.getShip())) {
      destroyShipFromFleet(ship, fleet1);
    } else if (fleet2.isShipInFleet(ship.getShip())) {
      destroyShipFromFleet(ship, fleet2);
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
    if (stat != null) {
      stat.setNumberOfKills(stat.getNumberOfKills() + 1);
    }
    stat = ship.getPlayer().getShipStatByName(ship.getShip().getName());
    if (stat != null) {
      stat.setNumberOfLoses(stat.getNumberOfLoses() + 1);
      stat.setNumberOfInUse(stat.getNumberOfInUse() - 1);
    }
    int indexToDelete = shipList.indexOf(ship);
    shipList.remove(ship);
    if (indexToDelete < shipIndex && shipIndex > 0) {
      shipIndex--;
    }
  }

  /**
   * Maximum distance in combat which cannot be really reached.
   */
  private static final int MAX_DISTANCE = 999;

  /**
   * Get the closest enemy ship
   * @param info Player info who is doing the comparison
   * @param center Where to start looking the closet one
   * @return CombatShip or null
   */
  public CombatShip getClosestEnemyShip(final PlayerInfo info,
      final CombatShip center) {
    double maxDistance = MAX_DISTANCE;
    CombatShip result = null;
    for (CombatShip ship : shipList) {
      if (ship.getPlayer() != info) {
        Coordinate centerCoordinate = new Coordinate(center.getX(),
            center.getY());
        Coordinate shipCoordinate = new Coordinate(ship.getX(), ship.getY());
        double distance = centerCoordinate.calculateDistance(shipCoordinate);
        if (distance < maxDistance) {
          result = ship;
          maxDistance = distance;
        }
      }
    }
    return result;

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
    for (CombatShip ship : shipList) {
      if (x == ship.getX() && y == ship.getY()) {
        return true;
      }
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
    return new Coordinate(fleet2.getX(), fleet2.getY());
  }

  /**
   * Get Current Ship
   * @return CombatShip
   */
  public CombatShip getCurrentShip() {
    if (shipList.size() <= shipIndex) {
      shipIndex = 0;
    }
    return shipList.get(shipIndex);
  }

  /**
   * Get next ship is list. List contains both player's fleets
   * in initiative order. After this ship can be fetched with
   * getCurrentShip() method.
   */
  public void nextShip() {
    shipIndex++;
    if (shipList.size() <= shipIndex) {
      shipIndex = 0;
    }
    CombatShip ship = getCurrentShip();
    ship.reInitShipForRound();
  }

  /**
   * Get ship from combat coordinate
   * @param x X Coordinate
   * @param y Y Coordinate
   * @return CombatShip
   */
  public CombatShip getShipFromCoordinate(final int x, final int y) {
    for (CombatShip ship : shipList) {
      if (ship.getX() == x && ship.getY() == y) {
        return ship;
      }
    }
    return null;
  }

  /**
   * Get starting position for ships
   * @param index Ship Index in fleet
   * @param team Which player it belong 0 on bottom, 1 on top
   * @param x X coordinate or Y coordinate
   * @return Coordinate
   */
  private static int getStartPos(final int index, final CombatTeam team,
      final boolean x) {
    if (team == CombatTeam.BOTTOMTEAM) {
      switch (index) {
      case 0: {
        if (x) {
          return 4;
        }
        return 7;
      }
      case 1: {
        if (x) {
          return 3;
        }
        return 7;
      }
      case 2: {
        if (x) {
          return 5;
        }
        return 7;
      }
      case 3: {
        if (x) {
          return 2;
        }
        return 7;
      }
      case 4: {
        if (x) {
          return 6;
        }
        return 7;
      }
      case 5: {
        if (x) {
          return 4;
        }
        return 8;
      }
      case 6: {
        if (x) {
          return 1;
        }
        return 7;
      }
      case 7: {
        if (x) {
          return 7;
        }
        return 7;
      }
      case 8: {
        if (x) {
          return 3;
        }
        return 8;
      }
      case 9: {
        if (x) {
          return 5;
        }
        return 8;
      }
      case 10: {
        if (x) {
          return 2;
        }
        return 8;
      }
      case 11: {
        if (x) {
          return 6;
        }
        return 8;
      }
      case 12: {
        if (x) {
          return 0;
        }
        return 7;
      }
      case 13: {
        if (x) {
          return 8;
        }
        return 7;
      }
      case 14: {
        if (x) {
          return 1;
        }
        return 8;
      }
      case 15: {
        if (x) {
          return 7;
        }
        return 8;
      }
      case 16: {
        if (x) {
          return 0;
        }
        return 8;
      }
      case 17: {
        if (x) {
          return 8;
        }
        return 8;
      }
      default:
        throw new IllegalArgumentException("Fleet contains too many ships!");
      }
    }
    else {
        switch (index) {
        case 0: {
          if (x) {
            return 4;
          }
          return 1;
        }
        case 1: {
          if (x) {
            return 3;
          }
          return 1;
        }
        case 2: {
          if (x) {
            return 5;
          }
          return 1;
        }
        case 3: {
          if (x) {
            return 2;
          }
          return 1;
        }
        case 4: {
          if (x) {
            return 6;
          }
          return 1;
        }
        case 5: {
          if (x) {
            return 4;
          }
          return 0;
        }
        case 6: {
          if (x) {
            return 1;
          }
          return 1;
        }
        case 7: {
          if (x) {
            return 7;
          }
          return 1;
        }
        case 8: {
          if (x) {
            return 3;
          }
          return 0;
        }
        case 9: {
          if (x) {
            return 5;
          }
          return 0;
        }
        case 10: {
          if (x) {
            return 2;
          }
          return 0;
        }
        case 11: {
          if (x) {
            return 6;
          }
          return 0;
        }
        case 12: {
          if (x) {
            return 0;
          }
          return 1;
        }
        case 13: {
          if (x) {
            return 8;
          }
          return 1;
        }
        case 14: {
          if (x) {
            return 1;
          }
          return 0;
        }
        case 15: {
          if (x) {
            return 7;
          }
          return 0;
        }
        case 16: {
          if (x) {
            return 0;
          }
          return 0;
        }
        case 17: {
          if (x) {
            return 8;
          }
          return 0;
        }
        default:
          throw new IllegalArgumentException("Fleet contains too many ships!");
        }
    }
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
   */
  public void handleEndCombat() {
    if (!endCombatHandled) {
      if (winner != null && info1 == winner) {
        endCombatHandled = true;
        handleWinner(fleet1, info1);
        handleLoser(info2);
        Coordinate loserPos = fleet2.getCoordinate();
        int index = info2.getFleets().getIndexByName(fleet2.getName());
        if (index != -1) {
          info2.getFleets().remove(index);
        }
        if (info2.getFleets().getFleetByCoordinate(loserPos) == null) {
          // No more defending fleets so moving to the coordinate
          fleet1.setPos(fleet2.getCoordinate());
        }
      }
      if (winner != null && info2 == winner) {
        endCombatHandled = true;
        handleWinner(fleet2, info2);
        handleLoser(info1);
        // Defending player won't move
        int index = info1.getFleets().getIndexByName(fleet1.getName());
        if (index != -1) {
          info1.getFleets().remove(index);
        }
      }
    }
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
    for (CombatShip ship : shipList) {
      if (first == null) {
        first = ship.getPlayer();
      }
      if (first != ship.getPlayer()) {
        moreThanOnePlayer = true;
      }
      if (ship.getShip().getTotalMilitaryPower() > 0) {
        militaryPower = true;
      }
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
    if (!isCombatOver()) {
      while (!isCombatOver()) {
        handleAI(null, null);
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
          ShipDamage shipDamage = new ShipDamage(1, "Attack missed!");
          if (DiceGenerator.getRandom(1, 100) <= accuracy) {
            shipDamage = target.getShip().damageBy(weapon);
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
   * @param textLogger where logging is added if not null
   * @param infoPanel Infopanel where ship components are shown.
   *        This can be null too.
   * @return true if end round has been activated and component use
   *        should be cleared from UI. Otherwise false.
   */
  public boolean handleAI(final Logger textLogger,
      final BattleInfoPanel infoPanel) {
    PlayerInfo info = getCurrentShip().getPlayer();
    CombatShip deadliest = getMostPowerfulShip(info);
    CombatShip closest = getClosestEnemyShip(info, getCurrentShip());
    CombatShip ai = getCurrentShip();
    boolean shot = false;
    int range = ai.getShip().getMaxWeaponRange();
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
      shot = handleAIShoot(ai, deadliest, textLogger, infoPanel);
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
        // We still got more shots left, let's shoot the deadliest
        shot = handleAIShoot(ai, deadliest, textLogger, infoPanel);
        if (!shot) {
          // Deadliest wasn't close enough, let's shoot the closest
          closest = getClosestEnemyShip(info, getCurrentShip());
          shot = handleAIShoot(ai, closest, textLogger, infoPanel);
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


}
