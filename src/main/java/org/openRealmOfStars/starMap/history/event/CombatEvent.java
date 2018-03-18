package org.openRealmOfStars.starMap.history.event;

import java.io.ByteArrayInputStream;
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
* Combat Event
*
*/
public class CombatEvent extends Event {

  /**
   * Coordinate where combat happened
   */
  private Coordinate coordinate;

  /**
   * Planet name if combat happened on planet. Null if on space
   */
  private String planetName;

  /**
   * Text for combat
   */
  private String text;

  /**
   * Combat event on space or planet
   * @param coord Coordinate where combat happened
   * @param attackerIndex Player who attacked index
   */
  public CombatEvent(final Coordinate coord, final int attackerIndex) {
    setType(EventType.SPACE_COMBAT);
    coordinate = coord;
    setPlayerIndex(attackerIndex);
    planetName = null;
    setText("");
  }

  /**
   * Get coordinate where combat happened.
   * @return Coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  /**
   * Get Planet name or null if combat happened in space
   * @return Planet name or null
   */
  public String getPlanetName() {
    return planetName;
  }

  /**
   * Set planet name where combat happened. Set null
   * if combat happened in space
   * @param name Planet name or null
   */
  public void setPlanetName(final String name) {
    planetName = name;
  }

  /**
   * Get historical text about the event
   * @return Historical text
   */
  public String getText() {
    return text;
  }

  /**
   * Set historical text for combat
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
      IOUtilities.writeUTF8String(os, getPlanetName());
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
   * Tries to parse Combat event from byte buffer.
   * Buffer should contain type byte, 16 bit length and full data.
   * @param buffer Where to parse
   * @return CombatEvent parsed from buffer
   * @throws IOException if parsing fails
   */
  protected static CombatEvent createCombatEvent(final byte[] buffer)
      throws IOException {
    EventType readType = Event.readTypeAndLength(buffer);
    if (readType == EventType.SPACE_COMBAT) {
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
        CombatEvent event = new CombatEvent(coord, index);
        String str = IOUtilities.readUTF8String(is);
        if (str != null && str.isEmpty()) {
          str = null;
        }
        event.setPlanetName(str);
        str = IOUtilities.readUTF8String(is);
        event.setText(str);
        return event;
      }
    }
    throw new IOException("Event is not Space Combat as expected!");
  }


}
