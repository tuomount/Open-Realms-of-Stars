package org.openRealmOfStars.player.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.utilities.IOUtilities;

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
 * Message class for handling game information for player
 *
 */

public class Message {

  /**
   * Coordinate on star map
   */
  private Coordinate coordinate;

  /**
   * Generic index, could point planet, sun, fleet, player etc. Depends
   * of message type
   */
  private int index;

  /**
   * Message type, what kind message is about, research, construction, fleet etc.
   */
  private MessageType type;

  /**
   * Message itself
   */
  private String message;

  /**
   * Icon for message
   */
  private Icon16x16 icon;

  /**
   * Certain messages need to find match with string.
   */
  private String matchByString;

  /**
   * Create new message
   * @param type MessageType
   * @param msg Message text
   * @param icon Icon for message
   */
  public Message(final MessageType type, final String msg,
      final Icon16x16 icon) {
    this.type = type;
    setMessage(msg);
    this.icon = icon;
    this.coordinate = new Coordinate(-1, -1);
    this.index = -1;
    this.matchByString = null;
  }

  /**
   * Read Message from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public Message(final DataInputStream dis) throws IOException {
    this.type = MessageType.getTypeByIndex(dis.readInt());
    this.index = dis.readInt();
    this.coordinate = new Coordinate(dis.readInt(), dis.readInt());
    setMessage(IOUtilities.readString(dis));
    this.matchByString = IOUtilities.readString(dis);
    if (this.matchByString.isEmpty()) {
      this.matchByString = null;
    }
    this.icon = Icons.getIconByName(IOUtilities.readString(dis));
  }

  /**
   * Save message into DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveMessage(final DataOutputStream dos) throws IOException {
    dos.writeInt(type.getIndex());
    dos.writeInt(index);
    dos.writeInt(coordinate.getX());
    dos.writeInt(coordinate.getY());
    IOUtilities.writeString(dos, message);
    IOUtilities.writeString(dos, matchByString);
    IOUtilities.writeString(dos, icon.getName());
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(final int index) {
    this.index = index;
  }

  public MessageType getType() {
    return type;
  }

  public void setType(final MessageType type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    StringBuilder sb = new StringBuilder(message.length() + 5);
    int split = 0;
    for (int i = 0; i < message.length(); i++) {
      if (message.charAt(i) == ' ' && split > 69) {
        sb.append("\n");
        sb.append(message.charAt(i));
        split = 0;
      } else if (message.charAt(i) == '\n') {
        sb.append(message.charAt(i));
        split = 0;
      } else {
        sb.append(message.charAt(i));
        split++;
      }
    }
    this.message = sb.toString();
  }

  public Icon16x16 getIcon() {
    return icon;
  }

  public void setIcon(final Icon16x16 icon) {
    this.icon = icon;
  }

  /**
   * Set message coordinates. Use (-1,-1) not to place coordinate for message
   * @param x X coordinate
   * @param y Y coordinate
   */
  public void setCoordinate(final int x, final int y) {
    this.coordinate = new Coordinate(x, y);
  }

  public int getX() {
    return coordinate.getX();
  }

  public int getY() {
    return coordinate.getY();
  }

  public String getMatchByString() {
    return matchByString;
  }

  public void setMatchByString(final String matchByString) {
    this.matchByString = matchByString;
  }

  @Override
  public String toString() {
    return type.toString() + " - " + message;
  }

}
