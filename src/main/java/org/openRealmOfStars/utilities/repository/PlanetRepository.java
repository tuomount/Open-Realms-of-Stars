package org.openRealmOfStars.utilities.repository;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.BuildingFactory;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.utilities.IOUtilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
 * Planet repository class
 *
 */
public class PlanetRepository {

    /**
     * Read Planet information from DataInputStream. PlayerList must be
     * read before since planet only save player index not full player info.
     * @param dis DataInputStream
     * @param players PlayerList
     * @throws IOException if there is any problem with DataInputStream
     */
    public Planet restorePlanet(final DataInputStream dis, final PlayerList players)
            throws IOException {
        Coordinate coordinate = new Coordinate(dis.readInt(), dis.readInt());
        String name = IOUtilities.readString(dis);
        name = name.substring(0, name.lastIndexOf(' '));
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
        planet.setPlanetImageIndex(dis.readInt());
        planet.setPlanetType(dis.readInt());
        int planetOwner = dis.readInt();
        PlayerInfo planetOwnerInfo = null;
        if (planetOwner != -1) {
            planetOwnerInfo = players.getPlayerInfoByIndex(planetOwner);
        }
        planet.setPlanetOwner(planetOwner, planetOwnerInfo);
        planet.setExtraFood(dis.readInt());
        planet.setTax(dis.readInt(), true);
        planet.setCulture(dis.readInt());
        planet.setHomeWorldIndex(dis.readInt());
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
        return planet;
    }


    /**
     * Save Planet data to DataOutputStream
     * @param dos DataOutputStream
     * @throws IOException if there is any problem with DataOutputStream
     */
    public void savePlanet(final DataOutputStream dos, final Planet planet) throws IOException {
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
        dos.writeInt(planet.getPlanetImageIndex());
        dos.writeInt(planet.getPlanetType());
        dos.writeInt(planet.getPlanetOwnerIndex());
        dos.writeInt(planet.getExtraFood());
        dos.writeInt(planet.getTax());
        dos.writeInt(planet.getCulture());
        dos.writeInt(planet.getHomeWorldIndex());
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
    }

}
