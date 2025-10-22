package org.openRealmOfStars.starMap.history.event;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Tuomo Untinen
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
* Ascended Event
*
*/
public class AscendedEvent extends Event {

  /**
   * Coordinate where ascended happened.
   */
  private Coordinate coordinate;


  /**
   * Text for ascension portal
   */
  private String text;

  /**
   * Ascended event on space
   * @param coord Coordinate where ascension happened
   */
  public AscendedEvent(final Coordinate coord) {
    setType(EventType.ASCENDED);
    this.coordinate = coord;
    setText("");
  }

  /**
   * Get coordinate where ascension happened.
   * @return Coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  /**
   * Get historical text about the event
   * @return Historical text
   */
  public String getText() {
    return text;
  }

  /**
   * Set historical text for ascension.
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
      os.write(IOUtilities.convertIntTo16BitMsb(coordinate.getX()));
      os.write(IOUtilities.convertIntTo16BitMsb(coordinate.getY()));
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
   * Tries to parse ascended event from byte buffer.
   * Buffer should contain type byte, 16 bit length and full data.
   * @param buffer Where to parse
   * @return RiftPortalEvent parsed from buffer
   * @throws IOException if parsing fails
   */
  protected static AscendedEvent createAscendedlEvent(
      final byte[] buffer)
      throws IOException {
    EventType readType = Event.readTypeAndLength(buffer);
    if (readType == EventType.ASCENDED) {
      try (ByteArrayInputStream is = new ByteArrayInputStream(buffer)) {
        long skipped = is.skip(3);
        if (skipped != 3) {
          throw new IOException("Failed to skip 3 bytes!");
        }
        int x = IOUtilities.read16BitsToInt(is);
        int y = IOUtilities.read16BitsToInt(is);
        Coordinate coord = new Coordinate(x, y);
        AscendedEvent event = new AscendedEvent(coord);
        String str = IOUtilities.readUTF8String(is);
        event.setText(str);
        return event;
      }
    }
    throw new IOException("Event is not Ascended as expected!");
  }


}
