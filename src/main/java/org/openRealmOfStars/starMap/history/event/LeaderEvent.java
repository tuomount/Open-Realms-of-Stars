package org.openRealmOfStars.starMap.history.event;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* Leader event
*
*/
public class LeaderEvent extends Event {

  /**
   * Coordinate where leader event happened
   */
  private Coordinate coordinate;

  /**
   * Planet name if leader event happened on planet. Null if on space
   */
  private String planetName;

  /**
   * Index for leader in leader pool
   */
  private int leaderIndex;
  /**
   * Text for leader event
   */
  private String text;

  /**
   * Leader Event to happen single leader.
   * Text can contain information also about other leader
   * @param leader Leader who is experience event
   * @param realm Realm where leader belongs
   * @param realmIndex Realm index.
   * @param coord Coordinates where event occured
   */
  public LeaderEvent(final Leader leader, final PlayerInfo realm,
      final int realmIndex, final Coordinate coord) {
    setType(EventType.LEADER_EVENT);
    leaderIndex = realm.getLeaderIndex(leader);
    setPlayerIndex(realmIndex);
    setText("");
    setPlanetName("");
    this.coordinate = coord;
  }

  /**
   * Leader Event to happen single leader.
   * Text can contain information also about other leader
   * @param leaderIndex Leader index in leader pool
   * @param realmIndex Realm index.
   * @param coord Coordinates where event occured
   */
  private LeaderEvent(final int leaderIndex, final int realmIndex,
      final Coordinate coord) {
    setType(EventType.LEADER_EVENT);
    this.leaderIndex = leaderIndex;
    setPlayerIndex(realmIndex);
    setText("");
    setPlanetName("");
    this.coordinate = coord;
  }

  /**
   * Get event coordinate
   * @return the coordinate
   */
  public Coordinate getCoordinate() {
    return coordinate;
  }

  /**
   * @return the planetName
   */
  public String getPlanetName() {
    return planetName;
  }


  /**
   * Set planet name where event happen. Null means empty space.
   * @param planetName the planetName to set
   */
  public void setPlanetName(final String planetName) {
    this.planetName = planetName;
  }


  /**
   * Get leader event text
   * @return the text
   */
  public String getText() {
    return text;
  }


  /**
   * Text to leader event
   * @param text the text to set
   */
  public void setText(final String text) {
    this.text = text;
  }


  /**
   * Get leader index.
   * @return the leaderIndex
   */
  public int getLeaderIndex() {
    return leaderIndex;
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
      os.write(IOUtilities.convertIntTo16BitMsb(leaderIndex));
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
   * Tries to parse Leader event from byte buffer.
   * Buffer should contain type byte, 16 bit length and full data.
   * @param buffer Where to parse
   * @return LeaderEvent parsed from buffer
   * @throws IOException if parsing fails
   */
  protected static LeaderEvent createLeaderEvent(final byte[] buffer)
      throws IOException {
    EventType readType = Event.readTypeAndLength(buffer);
    if (readType == EventType.LEADER_EVENT) {
      try (ByteArrayInputStream is = new ByteArrayInputStream(buffer)) {
        long skipped = is.skip(3);
        if (skipped != 3) {
          throw new IOException("Failed to skip 3 bytes!");
        }
        int index = is.read();
        if (index == 255) {
          index = -1;
        }
        int leaderIndex = IOUtilities.read16BitsToInt(is);
        int x = IOUtilities.read16BitsToInt(is);
        int y = IOUtilities.read16BitsToInt(is);
        Coordinate coord = new Coordinate(x, y);
        LeaderEvent event = new LeaderEvent(leaderIndex, index, coord);
        String str = IOUtilities.readUTF8String(is);
        if (str != null && str.isEmpty()) {
          event.setPlanetName(null);
        } else {
          event.setPlanetName(str);
        }
        str = IOUtilities.readUTF8String(is);
        event.setText(str);
        return event;
      }
    }
    throw new IOException("Event is not Leader event as expected!");
  }

}
