package org.openRealmOfStars.player.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
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
   * X coordinate on star map
   */
  private int x;
  /**
   * Y coordinate on star map
   */
  private int y;
  
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
  public Message(MessageType type, String msg, Icon16x16 icon) {
    this.type = type;
    this.message = msg;
    this.icon = icon;
    this.x = -1;
    this.y = -1;
    this.index = -1;
    this.matchByString = null;
  }
  
  /**
   * Read Message from DataInputStream
   * @param dis DataInputStream
   * @throws IOException
   */
  public Message(DataInputStream dis) throws IOException {
    this.type = MessageType.getTypeByIndex(dis.readInt());
    this.index = dis.readInt();
    this.x = dis.readInt();
    this.y = dis.readInt();
    this.message = IOUtilities.readString(dis);
    this.matchByString = IOUtilities.readString(dis);
    if (this.matchByString.isEmpty()) {
      this.matchByString = null;
    }
    this.icon = Icons.getIconByName(IOUtilities.readString(dis));
  }
  
  /**
   * Save message into DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException
   */
  public void saveMessage(DataOutputStream dos) throws IOException {
    dos.writeInt(type.getIndex());
    dos.writeInt(index);
    dos.writeInt(x);
    dos.writeInt(y);
    IOUtilities.writeString(dos, message);
    IOUtilities.writeString(dos, matchByString);
    IOUtilities.writeString(dos, icon.getName());
  }
  
  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public MessageType getType() {
    return type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Icon16x16 getIcon() {
    return icon;
  }

  public void setIcon(Icon16x16 icon) {
    this.icon = icon;
  }

  /**
   * Set message coordiantes. Use (-1,-1) not to place coordinate for message
   * @param x X coordinate
   * @param y Y coordinate
   */
  public void setCoordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public String getMatchByString() {
    return matchByString;
  }

  public void setMatchByString(String matchByString) {
    this.matchByString = matchByString;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(type.toString());
    sb.append(" - ");
    sb.append(message);
    return sb.toString();
  }
  
  
  
  
}
