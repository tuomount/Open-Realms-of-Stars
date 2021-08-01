package org.openRealmOfStars.utilities.repository;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.BuildingFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.starMap.planet.PlanetaryEvent;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.utilities.IOUtilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2018, 2020, 2021 Tuomo Untinen
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
 * Planet repository class
 *
 */
public class PlanetRepository {

  /**
   * Read Planet information from DataInputStream. PlayerList must be
   * read before since planet only save player index not full player info.
   * @param dis DataInputStream
   * @param players PlayerList
   * @return Planet Planet read from dataInputStream.
   * @throws IOException if there is any problem with DataInputStream
   */
  public Planet restorePlanet(final DataInputStream dis,
      final PlayerList players) throws IOException {
    Coordinate coordinate = new Coordinate(dis.readInt(), dis.readInt());
    String name = IOUtilities.readString(dis);
    if (name.lastIndexOf(' ') != -1) {
      name = name.substring(0, name.lastIndexOf(' '));
    }
    int orderNumber = dis.readInt();
    int radiationLevel = dis.readInt();
    int groundSize = dis.readInt();
    int amountMetalInGround = dis.readInt();
    int metal = dis.readInt();
    int prodResource = dis.readInt();
    boolean gasGiant = dis.readBoolean();
    Planet planet = new Planet(coordinate, name, orderNumber, gasGiant);
    planet.setRadiationLevel(radiationLevel);
    planet.setGroundSize(groundSize);
    planet.setAmountMetalInGround(amountMetalInGround);
    planet.setMetal(metal);
    planet.setProdResource(prodResource);
    int gasGiantIndex = dis.readInt();
    int value = dis.readInt();
    if (!gasGiant) {
      planet.setPlanetType(PlanetTypes.getPlanetType(value, gasGiant));
    } else {
      planet.setPlanetType(PlanetTypes.getPlanetType(gasGiantIndex, gasGiant));
    }
    int planetOwner = dis.readInt();
    PlayerInfo planetOwnerInfo = null;
    if (planetOwner != -1) {
      planetOwnerInfo = players.getPlayerInfoByIndex(planetOwner);
    }
    planet.setPlanetOwner(planetOwner, planetOwnerInfo);
    int governorIndex = dis.readInt();
    planet.setGovernor(null);
    if (planet.getPlanetPlayerInfo() != null && governorIndex != -1) {
      Leader leader = planet.getPlanetPlayerInfo().getLeaderPool()
          .get(governorIndex);
      planet.setGovernor(leader);
    }
    planet.setExtraFood(dis.readInt());
    planet.setTax(dis.readInt(), true);
    planet.setCulture(dis.readInt());
    planet.setHomeWorldIndex(dis.readInt());
    planet.setPlanetaryEvent(PlanetaryEvent.getByIndex(dis.read()));
    planet.setEventActivation(dis.readBoolean());
    for (int i = 0; i < Planet.MAX_WORKER_TYPE; i++) {
      planet.setWorkers(i, dis.readInt());
    }
    int count = dis.readInt();
    for (int i = 0; i < count; i++) {
      String buildingName = IOUtilities.readString(dis);
      Building building = BuildingFactory.createByName(buildingName);
      planet.addBuilding(building);
    }
    String str = IOUtilities.readString(dis);
    if (str.isEmpty()) {
      planet.setUnderConstruction(null);
    } else {
      Construction[] constructions = planet.getProductionList();
      for (Construction cons : constructions) {
        if (cons.getName().equals(str)) {
          planet.setUnderConstruction(cons);
          break;
        }
      }
    }
    boolean orbitalExists = dis.readBoolean();
    if (orbitalExists) {
      planet.setOrbital(new Ship(dis));
    }
    return planet;
  }

  /**
   * Save Planet data to DataOutputStream
   * @param dos DataOutputStream
   * @param planet Planet to save
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void savePlanet(final DataOutputStream dos, final Planet planet)
      throws IOException {
    // Coordinates
    dos.writeInt(planet.getCoordinate().getX());
    dos.writeInt(planet.getCoordinate().getY());
    IOUtilities.writeString(dos, planet.getName());
    dos.writeInt(planet.getOrderNumber());
    dos.writeInt(planet.getRadiationLevel());
    dos.writeInt(planet.getGroundSize());
    dos.writeInt(planet.getAmountMetalInGround());
    dos.writeInt(planet.getMetal());
    dos.writeInt(planet.getProdResource());
    dos.writeBoolean(planet.isGasGiant());
    dos.writeInt(planet.getPlanetTypeIndex());
    dos.writeInt(planet.getPlanetType().getPlanetTypeIndex());
    dos.writeInt(planet.getPlanetOwnerIndex());
    if (planet.getPlanetPlayerInfo() != null) {
      dos.writeInt(planet.getPlanetPlayerInfo().getLeaderIndex(
          planet.getGovernor()));
    } else {
      dos.writeInt(-1);
    }
    dos.writeInt(planet.getExtraFood());
    dos.writeInt(planet.getTax());
    dos.writeInt(planet.getCulture());
    dos.writeInt(planet.getHomeWorldIndex());
    dos.writeByte(planet.getPlanetaryEvent().getIndex());
    dos.writeBoolean(planet.isEventActivated());
    for (int i = 0; i < Planet.MAX_WORKER_TYPE; i++) {
      dos.writeInt(planet.getWorkers(i));
    }
    dos.writeInt(planet.getNumberOfBuildings());
    for (int i = 0; i < planet.getNumberOfBuildings(); i++) {
      IOUtilities.writeString(dos, planet.getBuildingList()[i].getName());
    }
    if (planet.getUnderConstruction() == null) {
      IOUtilities.writeString(dos, null);
    } else {
      IOUtilities.writeString(dos, planet.getUnderConstruction().getName());
    }
    if (planet.getOrbital() == null) {
      dos.writeBoolean(false);
    } else {
      dos.writeBoolean(true);
      planet.getOrbital().saveShip(dos);
    }
  }

}
