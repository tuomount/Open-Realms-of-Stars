package org.openRealmOfStars.utilities.repository;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016 Tuomo Untinen
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

import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.Route;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * Route repository class
 *
 */
public class RouteRepository {

  /**
   * Save Route to DataOutputStream
   * @param dos DataOutputStream
   * @param route Route to save
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveRoute(final DataOutputStream dos, final Route route)
      throws IOException {
    dos.writeDouble(route.getStartX());
    dos.writeDouble(route.getStartY());
    dos.writeDouble(route.getEndX());
    dos.writeDouble(route.getEndY());
    dos.writeInt(route.getRawValue());
    int numberOfPoints = 0;
    if (route.getNextPoints() != null) {
      numberOfPoints = route.getNextPoints().size();
    }
    dos.writeInt(numberOfPoints);
    if (numberOfPoints > 0) {
      for (Coordinate coord : route.getNextPoints()) {
        dos.writeInt(coord.getX());
        dos.writeInt(coord.getY());
      }
    }
  }

  /**
   * Read route from DataInputStream
   * @param dis Data Input Stream
   * @return Route loaded from DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public Route restoreRoute(final DataInputStream dis) throws IOException {
    Route route = new Route(0, 0, 0, 0, 0);
    route.setStartX(dis.readDouble());
    route.setStartY(dis.readDouble());
    route.setEndX(dis.readDouble());
    route.setEndY(dis.readDouble());
    route.setRawValue(dis.readInt());
    int numberOfPoints = dis.readInt();
    if (numberOfPoints > 0) {
      for (int i = 0; i < numberOfPoints; i++) {
        int x = dis.readInt();
        int y = dis.readInt();
        Coordinate coord = new Coordinate(x, y);
        route.addNewPoint(coord);
      }
    }
    return route;
  }
}
