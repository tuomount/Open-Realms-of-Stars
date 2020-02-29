package org.openRealmOfStars.starMap.history.event;

import java.io.IOException;
import java.io.InputStream;

import org.openRealmOfStars.starMap.StarMap;
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
* Abstract Event
*
*/
public abstract class Event {

  /**
   * Event Type
   */
  private EventType type;

  /**
   * Player index, -1 is for no player
   */
  private byte playerIndex;

  /**
   * Get Event Type
   * @return EventType
   */
  public EventType getType() {
    return type;
  }

  /**
   * Set Event type for event.
   * This should be called only in constructors.
   * @param eventType Event Type
   */
  public void setType(final EventType eventType) {
    type = eventType;
  }

  /**
   * Set Player Index. -1 is for no player index.
   * @param index Player Index
   */
  public void setPlayerIndex(final int index) {
    if (index >= StarMap.MAX_PLAYERS) {
      playerIndex = 7;
    } else if (index < -1) {
      playerIndex = -1;
    } else {
      playerIndex = (byte) index;
    }
  }

  /**
   * Get Player Index. Index -1 means generic event or no player.
   * @return Player index
   */
  public int getPlayerIndex() {
    return playerIndex;
  }

  /**
   * Create byte array from the event. Byte array will contain
   * all the date for event including type and required length.
   * First byte is reserved for type then, two bytes are for the length.
   * Next bytes contains the actual data. Length contains also length itself
   * and type byte.
   * @return Byte array of event
   * @throws IOException if reading fails.
   */
  public abstract byte[] createByteArray();

  /**
   * Parses and verifies event type and length
   * @param buffer Buffer which is used for parsing
   * @return EventType if everything is succesful.
   * @throws IOException If something fails on parsing.
   */
  protected static EventType readTypeAndLength(final byte[] buffer)
      throws IOException {
    if (buffer.length < 3) {
      throw new IOException("Data is too short!");
    }
    int readType = buffer[0];
    int bufferLength = IOUtilities.convert16BitsToInt(buffer[1] & 0xff,
        buffer[2] & 0xff);
    if (bufferLength != buffer.length) {
      throw new IOException("Buffer length does not match with buffer length!");
    }
    return EventType.getTypeByIndex(readType);
  }

  /**
   * Parse Event from InputStream. This is generic event parser.
   * @param is InputStream where parse
   * @return Event Parsed event
   * @throws IOException If parsing or reading fails.
   */
  public static Event parseEvent(final InputStream is) throws IOException {
    int type = is.read();
    int lenHi = is.read();
    int lenLo = is.read();
    int len = IOUtilities.convert16BitsToInt(lenHi, lenLo);
    byte[] buffer = new byte[len];
    buffer[0] = (byte) (type & 0xff);
    buffer[1] = (byte) (lenHi & 0xff);
    buffer[2] = (byte) (lenLo & 0xff);
    int offset = 3;
    do {
      int amount = is.read(buffer, offset, len - offset);
      if (amount == -1) {
        int read = amount + offset;
        throw new IOException("Unexpected end of file! Could only read "
            + read + " bytes!");
      }
      offset = offset + amount;
    } while (offset < len);
    EventType eventType = readTypeAndLength(buffer);
    Event result = null;
    switch (eventType) {
      case CULTURE_CHANGE: {
        result = CultureEvent.createCultureEvent(buffer); break;
      }
      case SPACE_COMBAT: {
        result = CombatEvent.createCombatEvent(buffer); break;
      }
      case DIPLOMATIC_RELATION_CHANGE: {
        result = DiplomaticEvent.createDiplomaticEvent(buffer); break;
      }
      case PLANET_CONQUERED:
      case PLANET_BUILDING:
      case ARTIFICAL_PLANET_CREATED:
      case PLANET_COLONIZED: {
        result = EventOnPlanet.createEventOnPlanet(buffer); break;
      }
      case GALACTIC_NEWS: {
        result = GalacticEvent.createGalacticEvent(buffer); break;
      }
      case PLAYER_START: {
        result = PlayerStartEvent.createStartEvent(buffer); break;
      }
      case LEADER_EVENT:  {
        result = LeaderEvent.createLeaderEvent(buffer); break;
      }
      default: {
        throw new IOException("Unexpected event type: " + type);
      }
    }
    return result;
  }

}
