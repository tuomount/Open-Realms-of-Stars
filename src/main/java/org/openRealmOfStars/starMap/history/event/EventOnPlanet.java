package org.openRealmOfStars.starMap.history.event;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018 Tuomo Untinen
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;

/**
*
* Historical event on planet
*
*/
public class EventOnPlanet extends Event {

  /**
   * Coordinate for planet
   */
  private Coordinate coordinate;

  /**
   * Planet name
   */
  private String name;

  /**
   * Text for planetary event
   */
  private String text;

  /**
   * Creates historical event on planet. Text on event is empty
   * at constructor.
   * @param type Type should be EventType.PLANET_COLONIZED
   *                         or EventType.PLANET_CONQUERED
   *                         or EventType.PLANET_BUILDING
   * @param coord Coordinate where planet is.
   * @param planetName Planet name
   * @param playerIndex Player index doing the event
   */
  public EventOnPlanet(final EventType type, final Coordinate coord,
      final String planetName, final int playerIndex) {
    if (type == EventType.PLANET_COLONIZED
        || type == EventType.PLANET_CONQUERED
        || type == EventType.PLANET_BUILDING
        || type == EventType.ARTIFICAL_PLANET_CREATED) {
      setType(type);
    } else {
      throw new IllegalArgumentException("Type is not planetary event!");
    }
    coordinate = coord;
    name = planetName;
    text = "";
    setPlayerIndex(playerIndex);
  }

  /**
   * Get planet coordinate
   * @return Coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  /**
   * Get Planet name.
   * @return Planet name
   */
  public String getName() {
    return name;
  }

  /**
   * Get historical text about the event
   * @return Historical text
   */
  public String getText() {
    return text;
  }

  /**
   * Set historical text for event on planet
   * @param historicalText to set
   */
  public void setText(final String historicalText) {
    text = historicalText;
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
      IOUtilities.writeUTF8String(os, getName());
      IOUtilities.writeUTF8String(os, getText());
      buffer = os.toByteArray();
      byte[] lenBuffer = IOUtilities.convertIntTo16BitMsb(buffer.length);
      buffer[1] = lenBuffer[0];
      buffer[2] = lenBuffer[1];
    } catch (IOException e) {
      ErrorLogger.log(e);
    }
    return buffer;
  }

  /**
   * Tries to parse EventOnPlanet from byte buffer.
   * Buffer should contain type byte, 16 bit length and full data.
   * @param buffer Where to parse
   * @return EventOnPlanet parsed from buffer
   * @throws IOException if parsing fails
   */
  protected static EventOnPlanet createEventOnPlanet(final byte[] buffer)
      throws IOException {
    EventType readType = Event.readTypeAndLength(buffer);
    if (readType == EventType.PLANET_COLONIZED
        || readType == EventType.PLANET_CONQUERED
        || readType == EventType.PLANET_BUILDING
        || readType == EventType.ARTIFICAL_PLANET_CREATED
        || readType == EventType.ASCENSION_PORTAL) {
      try (ByteArrayInputStream is = new ByteArrayInputStream(buffer)) {
        long skipped = is.skip(3);
        if (skipped != 3) {
          throw new IOException("Failed to skip 3 bytes!");
        }
        int index = is.read();
        if (index == 255) {
          index = -1;
        }
        int x = IOUtilities.read16BitsToInt(is);
        int y = IOUtilities.read16BitsToInt(is);
        Coordinate coord = new Coordinate(x, y);
        String str = IOUtilities.readUTF8String(is);
        EventOnPlanet event = new EventOnPlanet(readType, coord, str, index);
        str = IOUtilities.readUTF8String(is);
        event.setText(str);
        return event;
      }
    }
    throw new IOException("Event is not Planet Colonized"
        + " or Planet Conquered or Planet building"
        + " or Artificialplanet created or Ascension portal as expected!");
  }


}
