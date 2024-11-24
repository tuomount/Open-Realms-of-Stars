package org.openRealmOfStars.player.fleet;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.pathfinding.AStarSearch;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;
import org.openRealmOfStars.utilities.repository.RouteRepository;

/**
 * Fleet for handling list of ships
 *
 */
public class Fleet {
  /** Maximum fleet size 18 ships */
  public static final int MAX_FLEET_SIZE = 18;

  /** Maximum fleet size 7 for starbase fleet */
  public static final int MAX_STARBASE_SIZE = 7;

  /** List of ships in fleet */
  private ArrayList<Ship> ships;

  /** Fleet's coordinate */
  private Coordinate coordinate;

  /** Fleet name */
  private String name;

  /** How many moves fleet has left */
  private int movesLeft;

  /** Route for fleet to move with FLT speed */
  private Route route;

  /** Fleet commander. */
  private Leader commander;

  /**
   * AStar search for path finding, this will not be saved in to save game.
   * This information must be something that can be recalculated.
   */
  private AStarSearch aStarSearch;

  /**
   * Constructor for fleet
   * @param firstShip The first ship in the fleet
   * @param x The fleet's X coordinate
   * @param y The fleet's X coordinate
   */
  public Fleet(final Ship firstShip, final int x, final int y) {
    ships = new ArrayList<>();
    ships.add(firstShip);
    setPos(new Coordinate(x, y));
    setName("Fleet #0");
    setRoute(null);
    commander = null;
  }

  /**
   * Read Fleet from Data Input Stream
   * @param dis DataInputStream
   * @param info PlayerInfo for fleet commander loading.
   * @throws IOException if there is any problem with the DataInputStream
   */
  public Fleet(final DataInputStream dis,
      final PlayerInfo info) throws IOException {
    name = IOUtilities.readString(dis);
    int commanderIndex = dis.readInt();
    if (commanderIndex != -1) {
      Leader leader = info.getLeaderPool().get(commanderIndex);
      setCommander(leader);
    } else {
      setCommander(null);
    }
    coordinate = new Coordinate(dis.readInt(), dis.readInt());
    movesLeft = dis.readInt();
    String str = IOUtilities.readString(dis);
    if (str.equals("NOROUTE")) {
      route = null;
    } else {
      route = new RouteRepository().restoreRoute(dis);
    }
    int count = dis.readInt();
    ships = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      Ship ship = new Ship(dis);
      ships.add(ship);
    }
  }

  /**
   * Save Fleet to DataOutputStream
   * @param dos DataOutputStream
   * @param info PlayerInfo for fleet commander saving.
   * @throws IOException if there is any problem with the DataOutputStream
   */
  public void saveFleet(final DataOutputStream dos,
      final PlayerInfo info) throws IOException {
    IOUtilities.writeString(dos, name);
    dos.writeInt(info.getLeaderIndex(commander));
    dos.writeInt(coordinate.getX());
    dos.writeInt(coordinate.getY());
    dos.writeInt(movesLeft);
    if (route == null) {
      IOUtilities.writeString(dos, "NOROUTE");
    } else {
      IOUtilities.writeString(dos, "ROUTE");
      new RouteRepository().saveRoute(dos, route);
    }

    dos.writeInt(ships.size());
    for (int i = 0; i < ships.size(); i++) {
      ships.get(i).saveShip(dos);
    }
  }

  /**
   * Add new ship to list
   * @param ship to add
   */
  public void addShip(final Ship ship) {
    if (ship != null) {
      ships.add(ship);
    }
  }

  /**
   * Remove ship from list
   * @param ship to remove
   */
  public void removeShip(final Ship ship) {
    if (ship != null) {
      ships.remove(ship);
    }
  }

  /**
   * Is certain ship in fleet
   * @param ship is in fleet
   * @return Is certain ship in fleet
   */
  public boolean isShipInFleet(final Ship ship) {
    return ships.contains(ship);
  }

  /**
   * Get the number of ships in list
   * @return Number of ships in list
   */
  public int getNumberOfShip() {
    return ships.size();
  }

  /**
   * Get ship by index or return null
   * @param index to find
   * @return Ship or null
   */
  public Ship getShipByIndex(final int index) {
    if (index >= 0 && index < ships.size()) {
      return ships.get(index);
    }
    return null;
  }

  /**
   * Get all the fleet ships
   * @return Ships in array
   */
  public Ship[] getShips() {
    return ships.toArray(new Ship[ships.size()]);
  }

  /**
   * Get combat order of ships.
   * First ones are the fastest military ships. Then biggest military ships
   * and finally non military ships.
   * @return array of ship.
   */
  public Ship[] getCombatOrder() {
    ArrayList<Ship> combatOrder = new ArrayList<>();
    ArrayList<Ship> available = new ArrayList<>();
    for (Ship ship : ships) {
      available.add(ship);
    }
    do {
      Ship bestShip = null;
      for (Ship ship : available) {
        if (bestShip == null) {
          bestShip = ship;
          continue;
        }
        if (bestShip.getTotalMilitaryPower() > 0
            && ship.getTotalMilitaryPower() == 0) {
          continue;
        }
        if (bestShip.getTotalMilitaryPower() > 0
            && ship.getTotalMilitaryPower() > 0) {
          if (bestShip.getInitiative() == ship.getInitiative()
              && ship.getTotalMilitaryPower() > bestShip
                  .getTotalMilitaryPower()) {
            bestShip = ship;
            continue;
          }
          if (bestShip.getInitiative() < ship.getInitiative()) {
            bestShip = ship;
            continue;
          }
        }
        if (bestShip.getTotalMilitaryPower() == 0
            && ship.getTotalMilitaryPower() > 0) {
          bestShip = ship;
          continue;
        }
        if (bestShip.getInitiative() < ship.getInitiative()) {
          bestShip = ship;
          continue;
        }
      }
      if (bestShip != null) {
        available.remove(bestShip);
        combatOrder.add(bestShip);
      } else {
        break;
      }
    } while (available.size() > 0);
    return combatOrder.toArray(new Ship[combatOrder.size()]);
  }

  /**
   * Get fleet's X coordinate in star map
   * @return X coordinate
   */
  public int getX() {
    return coordinate.getX();
  }

  /**
   * Get fleet's Y coordinate in star map
   * @return Y coordinate
   */
  public int getY() {
    return coordinate.getY();
  }

  /**
   * Set fleet's Coordinates
   * @param pos Fleet's coordinate
   */
  public void setPos(final Coordinate pos) {
    this.coordinate = pos;
  }

  /**
   * Get fleet's coordinate
   * @return fleet's coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  /**
   * Get first ship from the fleet
   * @return Ship or null if no ships in fleet
   */
  public Ship getFirstShip() {
    if (ships.size() > 0) {
      return ships.get(0);
    }
    return null;
  }

  /**
   * Get ship with greatest military value
   * @return Ship or null if no ships in fleet
   */
  public Ship getBiggestShip() {
    Ship result = null;
    for (Ship ship : ships) {
      if (result == null) {
        result = ship;
        continue;
      }
      if (ship.getTotalMilitaryPower() > result.getTotalMilitaryPower()) {
        result = ship;
      }
    }
    return result;
  }

  /**
   * Get "cheapest/worst" ship for false flag operation.
   * @return Ship for false flag.
   */
  public Ship getShipForFalseFlag() {
    Ship result = null;
    int shipValue = 65535;
    for (Ship ship : ships) {
      if (result == null) {
        result = ship;
        shipValue = result.getTotalMilitaryPower() + result.getColonist() * 20;
        continue;
      }
      int compareValue = ship.getTotalMilitaryPower()
          + ship.getColonist() * 20;
      if (compareValue < shipValue) {
        result = ship;
        shipValue = compareValue;
      }
    }
    return result;
  }

  /**
   * Get Fleet name
   * @return Fleet name as a String
   */
  public String getName() {
    return name;
  }

  /**
   * Set fleet name
   * @param name Fleet name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Theoretical maximum for FTL speed. All components have smaller than
   * this. This is same as unset FTL speed.
   */
  private static final int MAX_FTL_SPEED = 999;

  /**
   * Get fleet speed. Internally usage only.
   * @param ftl True to get FTL speed, otherwise regular speed
   * @return Speed of fleet
   */
  private int getFleetSpeed(final boolean ftl) {
    int speed = MAX_FTL_SPEED;
    int smallShipSpeed = MAX_FTL_SPEED;
    int bigShipSpeed = MAX_FTL_SPEED;
    int fighterBaySize = 0;
    int fighterSize = 0;
    for (Ship ship : ships) {
      int shipSpeed = ship.getSpeed();
      if (ftl) {
        shipSpeed = ship.getFtlSpeed();
      }
      if ((ship.getHull().getSize() == ShipSize.LARGE
          || ship.getHull().getSize() == ShipSize.HUGE)
          && shipSpeed < bigShipSpeed) {
        bigShipSpeed = shipSpeed;
      }
      if (ship.getHull().getSize() == ShipSize.LARGE
          || ship.getHull().getSize() == ShipSize.HUGE) {
        fighterBaySize = fighterBaySize + ship.getFighterBaySize();
      }
      if ((ship.getHull().getSize() == ShipSize.SMALL
          || ship.getHull().getSize() == ShipSize.MEDIUM)
          && shipSpeed < smallShipSpeed) {
        smallShipSpeed = shipSpeed;
      }
      if (ship.getHull().getSize() == ShipSize.SMALL) {
        fighterSize = fighterSize + 1;
      }
      if (ship.getHull().getSize() == ShipSize.MEDIUM) {
        fighterSize = fighterSize + 2;
      }
    }
    if (fighterBaySize >= fighterSize) {
      speed = bigShipSpeed;
    } else {
      if (bigShipSpeed < smallShipSpeed) {
        speed = bigShipSpeed;
      } else {
        speed = smallShipSpeed;
      }
    }
    if (!ftl && commander != null
        && commander.hasPerk(Perk.EXPLORER)) {
      speed++;
    }
    if (ftl && commander != null
        && commander.hasPerk(Perk.FTL_ENGINEER)) {
      speed++;
    }
    if (speed == MAX_FTL_SPEED) {
      speed = 0;
    }
    return speed;
  }

  /**
   * Get Fleet speed
   * @return Speed
   */
  public int getFleetSpeed() {
    return getFleetSpeed(false);
  }

  /**
   * Get Fleet FTL speed
   * @return Speed
   */
  public int getFleetFtlSpeed() {
    return getFleetSpeed(true);
  }

  /**
   * Get Fleet Scanner level
   * @return scanner level
   */
  public int getFleetScannerLvl() {
    int lvl = 1;
    for (Ship ship : ships) {
      int shipLvl = ship.getScannerLvl();
      if (shipLvl > lvl) {
        lvl = shipLvl;
      }
    }
    if (commander != null && commander.hasPerk(Perk.SCANNER_EXPERT)) {
      lvl++;
    }
    return lvl;
  }

  /**
   * Get Fleet Cloak Detection
   * @return cloak detection
   */
  public int getFleetCloakDetection() {
    int lvl = 0;
    for (Ship ship : ships) {
      int shipLvl = ship.getScannerDetectionLvl();
      if (shipLvl > lvl) {
        lvl = shipLvl;
      }
    }
    if (commander != null && commander.hasPerk(Perk.COUNTER_AGENT)) {
      lvl++;
    }
    return lvl;
  }

  /**
   * Get Fleet Cloak Value. Ships with biggest masses
   * will have biggest effect on clocking value.
   * @return cloak Value
   */
  public int getFleetCloackingValue() {
    int lvl = 0;
    int totalMass = 0;
    for (Ship ship : ships) {
      int shipLvl = ship.getCloakingValue();
      if (commander != null
          && commander.hasPerk(Perk.SECRET_AGENT)) {
        shipLvl = shipLvl + 5;
      }

      int mass = ship.getHull().getSize().getMass();
      lvl = lvl + shipLvl * mass;
      totalMass = totalMass + mass;
    }
    if (totalMass == 0) {
      return 0;
    }
    lvl = lvl / totalMass;
    return lvl;
  }

  /**
   * Get Fleet information as a text
   * @param owner PlayerInfo who owns the fleet
   * @return information as a String
   */
  public String getInfoAsText(final PlayerInfo owner) {
    return getInfoAsText(owner, false);
  }

  /**
   * Get Fleet information as a text
   * @param owner PlayerInfo who owns the fleet
   * @param debug Show fleet's debug information
   * @return information as a String
   */
  public String getInfoAsText(final PlayerInfo owner, final boolean debug) {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    sb.append("\n");
    if (isPrivateerFleet()) {
      sb.append("Privateer fleet\n");
    } else if (owner != null) {
      sb.append(owner.getEmpireName());
      sb.append("\n");
    }
    if (getCommander() != null) {
      sb.append("Commander:\n");
      sb.append(getCommander().getTitle());
      sb.append("\n");
      sb.append(getCommander().getName());
      sb.append("\n\n");
    }
    sb.append("Capacity: ");
    sb.append(String.format("%.1f", getTotalFleetCapacity()));
    sb.append("\nSpeed: ");
    sb.append(getFleetSpeed());
    sb.append(" FTL: ");
    sb.append(getFleetFtlSpeed());
    sb.append("\n");
    sb.append("Moves: ");
    sb.append(movesLeft);
    sb.append("\n");
    int colonist = getTotalCargoColonist();
    if (colonist > 0) {
      if (isColonyFleet()) {
        sb.append("Colonists: ");
      } else if (hasTrooper()) {
        sb.append("Troops: ");
      } else {
        sb.append("Population: ");
      }
      sb.append(colonist);
      sb.append("\n");
    }
    for (Ship ship : ships) {
      sb.append(ship.getName() + " - " + ship.getTotalMilitaryPower());
      sb.append("\n");
    }
    if (route != null) {
      if (route.isDefending()) {
        sb.append("\n\nDefending");
      } else if (route.isFixing()) {
        sb.append("\n\nFixing");
      } else if (route.isBombing()) {
        sb.append("\n\nBombing");
      } else if (route.isExploring()) {
        sb.append("\n\nExploring the planet");
      } else {
        sb.append("\n\nEnroute");
      }
    } else {
      if (movesLeft == 0) {
        sb.append("\n\nMoved");
      }
    }
    if (owner != null && !owner.isHuman() && debug) {
      Mission mission = owner.getMissions().getMissionForFleet(name);
      if (mission != null) {
        sb.append("\n");
        sb.append(mission.toString());
      }
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    return getInfoAsText(null);
  }

  /**
   * Get Fleet route
   * @return Fleet route
   */
  public Route getRoute() {
    return route;
  }

  /**
   * Set Fleet route
   * @param route Fleet Route
   */
  public void setRoute(final Route route) {
    this.route = route;
  }

  /**
   * Get total amount of metal cargo
   * @return Metal cargo for fleet
   */
  public int getTotalCargoMetal() {
    int result = 0;
    for (Ship ship : ships) {
      result = result + ship.getMetal();
    }
    return result;
  }

  /**
   * Get total amount of colonist cargo
   * @return Colonist cargo for fleet
   */
  public int getTotalCargoColonist() {
    int result = 0;
    for (Ship ship : ships) {
      result = result + ship.getColonist();
    }
    return result;
  }

  /**
   * Get total free cargo space for metal
   * @return free cargo space
   */
  public int getFreeSpaceForMetal() {
    int result = 0;
    for (Ship ship : ships) {
      result = result + ship.getFreeCargoMetal();
    }
    return result;
  }

  /**
   * Get total free cargo space for colonist
   * @return free cargo space
   */
  public int getFreeSpaceForColonist() {
    int result = 0;
    for (Ship ship : ships) {
      result = result + ship.getFreeCargoColonists();
    }
    return result;
  }

  /**
   * Add single colonist for ship
   */
  public void addColonist() {
    for (Ship ship : ships) {
      if (ship.getFreeCargoColonists() > 0) {
        ship.setColonist(ship.getColonist() + 1);
        return;
      }
    }
  }

  /**
   * Add 10 metal for ship
   */
  public void addMetal() {
    for (Ship ship : ships) {
      if (ship.getFreeCargoMetal() > 9) {
        ship.setMetal(ship.getMetal() + 10);
        return;
      }
    }
  }

  /**
   * Remove single colonist for ship
   */
  public void removeColonist() {
    for (Ship ship : ships) {
      if (ship.getColonist() > 0) {
        ship.setColonist(ship.getColonist() - 1);
        return;
      }
    }
  }

  /**
   * Get first colony ship from the fleet which contains colonists.
   * @return Colony ship or null
   */
  public Ship getColonyShip() {
    return getColonyShip(true);
  }

  /**
   * Get first trooper ship from the fleet.
   * @return Trooper ship or null
   */
  public Ship getTrooperShip() {
    for (Ship ship : ships) {
      if (ship.isTrooperModule()) {
        return ship;
      }
    }
    return null;
  }

  /**
   * Get first bomber ship from the fleet.
   * @return Bomber ship or null
   */
  public Ship getBomberShip() {
    for (Ship ship : ships) {
      if (ship.hasBombs()) {
        return ship;
      }
    }
    return null;
  }

  /**
   * Get first assault ship from the fleet.
   * @return Assault ship or null
   */
  public Ship getAssaultShip() {
    for (Ship ship : ships) {
      if (ship.getTotalMilitaryPower() > 0
          && !ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
        return ship;
      }
    }
    return null;
  }

  /**
   * Get first colony ship from the fleet
   * @param hasColonist True if colony ship must have colonists.
   * @return Colony ship or null
   */
  public Ship getColonyShip(final boolean hasColonist) {
    for (Ship ship : ships) {
      if (ship.isColonyShip()) {
        if (hasColonist && ship.getColonist() > 0) {
          return ship;
        }
        if (!hasColonist) {
          return ship;
        }
      }
    }
    return null;
  }

  /**
   * Get first starbase ship from the fleet which isn't deployed.
   * @return Undeployed Starbase or null
   */
  public Ship getStarbaseShip() {
    for (Ship ship : ships) {
      if (ship.isStarBase() && !ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
        return ship;
      }
    }
    return null;
  }

  /**
   * Check if whole fleet is privateering fleet
   * @return True if all ships in fleet are privateers
   */
  public boolean isPrivateerFleet() {
    for (Ship ship : ships) {
      if (!ship.isPrivateeringShip()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Get fleet type. See FleetType enum.
   * @return Fleet type.
   */
  public FleetType getFleetType() {
    boolean nonMilitary = true;
    boolean starbase = true;
    boolean privateer = true;
    for (Ship ship : ships) {
      if (ship.getTotalMilitaryPower() > 0) {
        nonMilitary = false;
      }
      if (!ship.isStarBase()) {
        starbase = false;
      }
      if (!ship.isPrivateeringShip()) {
        privateer = false;
      }
    }
    FleetType result = FleetType.NON_MILITARY;
    if (!nonMilitary) {
      result = FleetType.MILITARY;
    }
    if (privateer) {
      result = FleetType.PRIVATEER;
    }
    if (starbase && isStarBaseDeployed()) {
      result = FleetType.STARBASE;
    }
    return result;
  }

  /**
   * Remove 10 metal for ship
   */
  public void removeMetal() {
    for (Ship ship : ships) {
      if (ship.getMetal() > 9) {
        ship.setMetal(ship.getMetal() - 10);
        return;
      }
    }
  }

  /**
   * Is Fleet scouting fleet. Scout fleet contains only one ship,
   * where hull type is probe or hull is small and contains no colony module
   * or ship name contains word Scout or Explorer.
   * @return true if scouting fleet.
   */
  public boolean isScoutFleet() {
    if (ships.size() == 1) {
      Ship ship = ships.get(0);
      if (getName().startsWith("Defender")) {
        return false;
      }
      if (getName().startsWith("Attacker")) {
        return false;
      }
      if (ship.getHull().getHullType() == ShipHullType.PROBE) {
        return true;
      }
      if (ship.getHull().getSize() == ShipSize.SMALL
          && ship.getHull().getHullType() != ShipHullType.FREIGHTER
          && !ship.isColonyModule() && !ship.isStarBase()) {
        return true;
      }
      if (ship.getName().contains("Scout")
          || ship.getName().contains("Explorer")) {
        return true;
      }
    }
    return false;
  }

  /**
   * Trade fleet check. Fleet needs to contain one trade ship.
   * @return True if fleet has one trade ship.
   */
  public boolean isTradeFleet() {
    for (Ship ship : ships) {
      if (ship.isTradeShip()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is Fleet colony fleet. Colony fleet contains only one ship,
   * where ship contains colony module
   * @return true if colony fleet.
   */
  public boolean isColonyFleet() {
    if (ships.size() == 1) {
      Ship ship = ships.get(0);
      if (ship.isColonyModule()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Fleet has colony ship with working colony module and at least one colonist.
   * @return True if has working colony ship.
   */
  public boolean hasColonyShip() {
    for (Ship ship : ships) {
      if (ship.isColonyShip() && ship.getColonist() > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Does fleet have trooper ship?
   * @return True if has trooper
   */
  public boolean hasTrooper() {
    for (Ship ship : ships) {
      if (ship.isTrooperShip()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get A Star search for fleet
   * @return A star search result
   */
  public AStarSearch getaStarSearch() {
    return aStarSearch;
  }

  /**
   * Set A Star result saved for the fleet
   * @param aStarSearch A Star search result
   */
  public void setaStarSearch(final AStarSearch aStarSearch) {
    this.aStarSearch = aStarSearch;
  }

  /**
   * Fix fleet's ships and resets shield and armor.
   * @param fullFix True to fully fix all ships in fleet
   */
  public void fixFleetShips(final boolean fullFix) {
    for (Ship ship : ships) {
      ship.fixShip(fullFix);
    }
  }

  /**
   * Reset shield and armor for all ships in fleet.
   */
  public void resetShields() {
    for (Ship ship : ships) {
      ship.initializeShieldAndArmor();
    }
  }

  /**
   * Calculate fleet's total military value
   * @return Total military value for fleet
   */
  public int getMilitaryValue() {
    int result = 0;
    for (Ship ship : ships) {
      if (!ship.isStarBase() || ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
        result = result + ship.getTotalMilitaryPower();
      }
    }
    return result;
  }

  /**
   * Smuggler fleet is one with no apparent military power on fleet.
   * Only Freighter ships are allowed to have weapons, if realm has certain
   * government. This is resticted in ship design. Generally speaking
   * Armed freighter is a smuggler fleet.
   * @return True if fleet is smuggler fleet.
   */
  public boolean isSmugglerFleet() {
    int result = 0;
    for (Ship ship : ships) {
      if (!ship.isStarBase() || ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
        result = result + ship.getTotalMilitaryPower();
        if (ship.isSmuggler()) {
          result = result - ship.getTotalMilitaryPower();
        }
      }
    }
    if (result > 0) {
      return false;
    }
    return true;
  }

  /**
   * Calculate fleet's total cultural value
   * @return Total cultural value for fleet
   */
  public int getCulturalValue() {
    int result = 0;
    for (Ship ship : ships) {
      result = result + ship.getCulture();
    }
    return result;
  }

  /**
   * Is all ships in fleet fixed or not
   * @return True if all is fixed otherwise false
   */
  public boolean allFixed() {
    boolean result = true;
    for (Ship ship : ships) {
      if (ship.getHullPoints() < ship.getMaxHullPoints()) {
        result = false;
      }
    }
    return result;
  }

  /**
   * Get how many moves fleet has left
   * @return how many moves fleet has left
   */
  public int getMovesLeft() {
    return movesLeft;
  }

  /**
   * Set how many moves fleet has left
   * @param movesLeft how many moves fleet has left
   */
  public void setMovesLeft(final int movesLeft) {
    this.movesLeft = movesLeft;
  }

  /**
   * Decrease how many moves fleet has left
   */
  public void decMovesLeft() {
    this.movesLeft--;
  }

  /**
   * Is starbase deployed or not
   * @return True if starbase is deployed
   */
  public boolean isStarBaseDeployed() {
    if (ships.size() <= 7 && ships.size() > 0) {
      Ship ship = ships.get(0);
      if (ship.isStarBase() && ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get total research bonus
   * @return Total research bonus from starbase components
   */
  public int getTotalReseachBonus() {
    int result = 0;
    if (isStarBaseDeployed()) {
      for (Ship ship : ships) {
        result += ship.getTotalResearchBonus();
      }
    }
    return result;
  }

  /**
   * Get total credits bonus
   * @return Total credits bonus from starbase components
   */
  public int getTotalCreditsBonus() {
    int result = 0;
    if (isStarBaseDeployed()) {
      for (Ship ship : ships) {
        result = result + ship.getTotalCreditBonus();
      }
    }
    return result;
  }

  /**
   * Get total culture bonus
   * @return Total culture bonus from starbase components
   */
  public int getTotalCultureBonus() {
    int result = 0;
    if (isStarBaseDeployed()) {
      for (Ship ship : ships) {
        result = result + ship.getTotalCultureBonus();
      }
    }
    return result;
  }

  /**
   * Get total fleet capacity bonus
   * @return Total fleet capacity bonus from starbase components
   */
  public int getTotalFleetCapacityBonus() {
    int result = 0;
    if (isStarBaseDeployed()) {
      for (Ship ship : ships) {
        result = result + ship.getTotalFleetCapacityBonus();
      }
    }
    return result;
  }

  /**
   * Get total espionage bonus
   * @return Total espionage bonus from ships in fleet
   */
  public int getEspionageBonus() {
    int result = 0;
    for (Ship ship : ships) {
      result = result + ship.getEspionageBonus();
    }
    if (commander != null
        && commander.hasPerk(Perk.SPY_MASTER)) {
      result = result + 1;
    }

    return result;
  }

  /**
   * Calculate trade worth between fleet coordinate and
   * target coordinate. This does not check that coordinates contain
   * planets or diplomacy of realms.
   * @param tradeCoordinate Target coordinate.
   * @return Trade worth of credit
   */
  public int calculateTrade(final Coordinate tradeCoordinate) {
    int result = 0;
    for (Ship ship : ships) {
      result = result + ship.calculateTradeCredits(getCoordinate(),
          tradeCoordinate);
    }
    return result;
  }

  /**
   * Do trade with planet if fleet has trade ship(s).
   * Not this does not check diplomatic relationships.
   * @param planet Planet to do trade
   * @param trader Player who is making a trade
   * @return Amount of credits gained.
   */
  public int doTrade(final Planet planet, final PlayerInfo trader) {
    int result = 0;
    for (Ship ship : ships) {
      result = result + ship.doTrade(planet, trader);
    }
    return result;
  }

  /**
   * Calculate total fleet capacity for whole fleet.
   * @return Fleet capacity.
   */
  public double getTotalFleetCapacity() {
    double result = 0;
    for (Ship ship : ships) {
      result = result + ship.getFleetCapacity();
    }
    return result;
  }

  /**
   * Get best ship to scrap.
   * This is based on obsolete models a fleet capacity taken by ships.
   * "Biggest" obsolete ship has priority over "biggest" non-obsolete ship.
   * Ships that do not take fleet capacity (i.e. freighters)
   * are never considered for scrapping.
   * @param obsoleteModels List of ship designs which are obsolete.
   * @return Ship or null if nothing to scrap.
   */
  public Ship getScrapableShip(final ShipStat[] obsoleteModels) {
    final Comparator<Ship> cmprFleetCapDesc = (a, b) -> {
      return Double.compare(b.getFleetCapacity(), a.getFleetCapacity());
    };

    var obsoleteShips = ships.stream()
        .filter(ship -> ship.getFleetCapacity() > 0.0)
        .filter(ship -> getShipObsolete(ship, obsoleteModels) == 1)
        .sorted(cmprFleetCapDesc);

    Optional<Ship> obsoleteShip = obsoleteShips.findFirst();
    if (!obsoleteShip.isEmpty()) {
      return obsoleteShip.get();
    }
    var notObsoleteShips = ships.stream()
        .filter(ship -> ship.getFleetCapacity() > 0.0)
        .filter(ship -> getShipObsolete(ship, obsoleteModels) < 1)
        .sorted(cmprFleetCapDesc);
    Optional<Ship> notObsoleteShip = notObsoleteShips.findFirst();
    if (!notObsoleteShip.isEmpty()) {
      return notObsoleteShip.get();
    }
    return null;
  }

  /**
   * Calculate Fleet obsolete value. How many ships in fleet are obsolete.
   * Also foreign ships are counted as obsolete.
   * @param info PlayerInfo who owns the fleet
   * @return Obsolete value, positive more obsolete it is.
   */
  public int calculateFleetObsoleteValue(final PlayerInfo info) {
    int result = 0;
    for (Ship ship : ships) {
      if (ship.getHull().getRace() != info.getRace()) {
        result = result + 1;
        continue;
      }

      var obsolete = getShipObsolete(ship, info.getShipStatList());
      result += obsolete;
    }
    return result;
  }

  /**
   * Return 1 if ship is obsolete in specified context of ship models.
   * If ship is NOT obsolete, return -1.
   * Return 0 if it is not possible tell if ship is obsolete in given context.
   * @param ship Ship to check
   * @param models ShipStat array of models to check against
   * @return 1 if ship is obsolete, -1 if not, 0 if cannot tell from context
   */
  private static int getShipObsolete(final Ship ship, final ShipStat[] models) {
    for (var model : models) {
      if (model.getDesign().getName().equals(ship.getName())) {
        if (model.isObsolete()) {
          return 1;
        }
        return -1;
      }
    }
    return 0;
  }

  /**
   * Get current commander of the fleet
   * @return the commander
   */
  public Leader getCommander() {
    return commander;
  }

  /**
   * Set the current commander of the fleet
   * @param commander the commander to set
   */
  public void setCommander(final Leader commander) {
    this.commander = commander;
    if (this.commander != null) {
      this.commander.setJob(Job.COMMANDER);
    }
  }

  /**
   * AI checks all ships in fleet if those could be upgrade.
   * Upgrade is done only for military ships.
   * @param info PlayerInfo which is upgrading.
   * @param planet Planet where to upgrade.
   */
  public void aiUpgradeShips(final PlayerInfo info, final Planet planet) {
    for (Ship ship : ships) {
      if (ship.getTheoreticalMilitaryPower() == 0) {
        continue;
      }
      ShipStat stat = info.getBestUpgrade(ship);
      if (stat != null) {
        int metalUpgradeCost = ship.getUpgradeMetalCost(stat.getDesign());
        int prodUpgradeCost = ship.getUpgradeCost(stat.getDesign());
        if (metalUpgradeCost > planet.getMetal()
            + planet.getAmountMetalInGround()) {
          continue;
        }
        if (metalUpgradeCost > planet.getMetal()) {
          int left = metalUpgradeCost - planet.getMetal();
          metalUpgradeCost = planet.getMetal();
          prodUpgradeCost = prodUpgradeCost + left * 2;
        }
        int minUpgrade = ShipHull.MIN_UPGRADE_SMALL;
        if (ship.getHull().getSize() == ShipSize.MEDIUM) {
          minUpgrade = ShipHull.MIN_UPGRADE_MEDIUM;
        }
        if (ship.getHull().getSize() == ShipSize.LARGE) {
          minUpgrade = ShipHull.MIN_UPGRADE_LARGE;
        }
        if (ship.getHull().getSize() == ShipSize.HUGE) {
          minUpgrade = ShipHull.MIN_UPGRADE_HUGE;
        }
        if (prodUpgradeCost > 0
            && stat.getDesign().getTotalMilitaryPower() >= ship
                .getTheoreticalMilitaryPower() + minUpgrade) {
          StarMapUtilities.upgradeShip(ship, stat, info, planet);
          setMovesLeft(0);
        }
      }
    }
  }

  /**
   * Attempts to split specified ships from current fleet,
   * creating new Fleet.
   *
   * Trying to split any ship from fleet that is not in it or is null is error.
   * @param drain if true, splitting last ship from fleet is allowed.
   * @param shipsForSplit Array of ships to split from this fleet
   * @throws IllegalArgumentException
   *         Trying to split ships that are not in this fleet.
   * @return Fleet with splitted ships, null if splitting would
   *         remove last ship from the fleet while drain is not allowed.
   */
  public Fleet splitFromFleet(final boolean drain,
      final Ship... shipsForSplit) {
    for (var ship : shipsForSplit) {
      if (ship == null) {
        throw new NullPointerException("Cannot split null Ship from fleet");
      }
      if (!ships.contains(ship)) {
        throw new IllegalArgumentException(
            "Ship " + ship + " is not member of Fleet " + this);
      }
    }

    var shipsRemainingCount = ships.size() - shipsForSplit.length;
    if (shipsRemainingCount < 1 && !drain) {
      return null;
    }

    Fleet newFleet = null;
    for (var ship : shipsForSplit) {
      if (newFleet == null) {
        newFleet = new Fleet(ship, this.getX(), this.getY());
      } else {
        newFleet.addShip(ship);
      }
      this.removeShip(ship);
    }

    return newFleet;
  }

  /**
   * Attempts to split specified ships from current fleet.
   * If the fleet is made exclusively from the ship being split away,
   * this does nothing.
   * Trying to split any ship from fleet that is not in it is error.
   * @param drain if true, splitting last ship from fleet is allowed.
   * @param shipsForSplit Collection of ships to split from this fleet
   * @throws IllegalArgumentException
   *         Trying to split ships that are not in this fleet.
   * @return Fleet with splitted ships, null if splitting would
   *         remove last ship from the fleet while drain is not allowed.
   */
  public Fleet splitFromFleet(final boolean drain,
      final Collection<Ship> shipsForSplit) {
    var array = shipsForSplit.toArray(new Ship[shipsForSplit.size()]);
    return this.splitFromFleet(drain, array);
  }
}
