package org.openRealmOfStars.starMap.history.event;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Culture change Event
*
*/
public class CultureEvent extends Event {

  /**
   * Coordinate where culture changed
   */
  private Coordinate coordinate;

  /**
   * Create culture change event
   * @param coord Coordinate where change culture
   * @param index Player index
   */
  public CultureEvent(final Coordinate coord, final int index) {
    setType(EventType.CULTURE_CHANGE);
    coordinate = coord;
    setPlayerIndex(index);
  }

  /**
   * Get coordinate where culture has changed;
   * @return Coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  @Override
  public byte[] createByteArray() {
    byte[] buffer = null;
    try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
      os.write(getType().getIndex());
      // Just reserving space for length field
      os.write(IOUtilities.convertIntTo16BitMsb(0));
      int playerIndex = getPlayerIndex();
      if (playerIndex == -1) {
        os.write(255);
      } else {
        os.write(playerIndex);
      }
      os.write(IOUtilities.convertIntTo16BitMsb(coordinate.getX()));
      os.write(IOUtilities.convertIntTo16BitMsb(coordinate.getY()));
      buffer = os.toByteArray();
      byte[] lenBuffer = IOUtilities.convertIntTo16BitMsb(buffer.length);
      buffer[1] = lenBuffer[0];
      buffer[2] = lenBuffer[1];
    } catch (IOException e) {
      ErrorLogger.log(e);
    }
    return buffer;
  }

}
