package org.openRealmOfStars.player.fleet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.PathFinding.AStarSearch;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipSize;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.Route;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.IOUtilities;
import org.openRealmOfStars.utilities.repository.RouteRepository;

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
 * Fleet for handling list of ships
 *
 */

public class Fleet {

  /**
   * Maximum fleet size 18 ships
   */
  public static final int MAX_FLEET_SIZE = 18;
  /**
   * Maximum fleet size 7 for starbase fleet
   */
  public static final int MAX_STARBASE_SIZE = 7;
  /**
   * List of ships in fleet
   */
  private ArrayList<Ship> ships;

  /**
   * Fleet's coordinate
   */
  private Coordinate coordinate;

  /**
   * Fleet name
   */
  private String name;

  /**
   * How many moves fleet has left
   */
  private int movesLeft;

  /**
   * Route for fleet to move with FLT speed
   */
  private Route route;

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
  }

  /**
   * Read Fleet from Data Input Stream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with the DataInputStream
   */
  public Fleet(final DataInputStream dis) throws IOException {
    name = IOUtilities.readString(dis);
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
   * @throws IOException if there is any problem with the DataOutputStream
   */
  public void saveFleet(final DataOutputStream dos) throws IOException {
    IOUtilities.writeString(dos, name);
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
    this.coordinate = new Coordinate(pos);
  }

  /**
   * Get fleet's coordinate
   * @return fleet's coordinate
   */
  public Coordinate getCoordinate() {
    return new Coordinate(coordinate);
  }

  /**
   * Get first ship from the fleet
   * @return Ship or null if new ships in fleet
   */
  public Ship getFirstShip() {
    if (ships.size() > 0) {
      return ships.get(0);
    }
    return null;
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
    sb.append("Speed: ");
    sb.append(getFleetSpeed());
    sb.append(" FTL: ");
    sb.append(getFleetFtlSpeed());
    sb.append("\n");
    sb.append("Moves:");
    sb.append(movesLeft);
    sb.append("\n");
    for (Ship ship : ships) {
      sb.append(ship.getName() + " - " + ship.getTotalMilitaryPower());
      sb.append("\n");
    }
    if (route != null) {
      if (route.isDefending()) {
        sb.append("\nDefending");
      } else if (route.isFixing()) {
        sb.append("\nFixing");
      } else {
        sb.append("\nEnroute");
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
   * Get first bomber ship from the fleet.
   * @return Assault ship or null
   */
  public Ship getAssaultShip() {
    for (Ship ship : ships) {
      if (ship.getTotalMilitaryPower() > 0) {
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
   * Fix fleet's ships
   * @param fullFix True to fully fix all ships in fleet
   */
  public void fixFleetShips(final boolean fullFix) {
    for (Ship ship : ships) {
      ship.fixShip(fullFix);
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
        result = result + ship.getTotalResearchBonus();
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
   * Get total espionage bonus
   * @return Total espionage bonus from ships in fleet
   */
  public int getEspionageBonus() {
    int result = 0;
    for (Ship ship : ships) {
      result = result + ship.getEspionageBonus();
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

}
