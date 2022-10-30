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
 * Copyright (C) 2016,2019,2020,2022 Tuomo Untinen
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
   * Message type, what kind message is about, research, construction,
   * fleet etc.
   */
  private MessageType type;

  /**
   * Message and image instruction separated with |.
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
   * Flag for showing random event pop. This information
   * is not saved.
   */
  private boolean randomEventPopup;

  /**
   * Create new message
   * @param type MessageType
   * @param msg Message text
   * @param icon Icon for message
   */
  public Message(final MessageType type, final String msg,
      final Icon16x16 icon) {
    this.type = type;
    message = msg;
    this.icon = icon;
    this.coordinate = new Coordinate(-1, -1);
    this.index = -1;
    this.matchByString = null;
    this.setRandomEventPop(false);
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
    message = IOUtilities.readString(dis);
    this.matchByString = IOUtilities.readString(dis);
    if (this.matchByString.isEmpty()) {
      this.matchByString = null;
    }
    this.icon = Icons.getIconByName(IOUtilities.readString(dis));
    this.setRandomEventPop(false);
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

  /**
   * Get Message Index in list. -1 if not yet in list.
   * @return Message Index
   */
  public int getIndex() {
    return index;
  }

  /**
   * Set Message Index
   * @param index Message index in list
   */
  public void setIndex(final int index) {
    this.index = index;
  }

  /**
   * Get Message Type
   * @return Message Type
   */
  public MessageType getType() {
    return type;
  }

  /**
   * Set Message Type
   * @param type Message Type
   */
  public void setType(final MessageType type) {
    this.type = type;
  }

  /**
   * Get Message part itself as a String.
   * @return Message
   */
  public String getMessage() {
    if (message != null) {
      String[] parts = message.split("\\|");
      return parts[0];
    }
    return null;
  }

  /**
   * Get Image instrutions part as a String.
   * @return Image instructions or null.
   */
  public String getImageInstruction() {
    if (message != null) {
      String[] parts = message.split("\\|");
      if (parts.length > 1) {
        return parts[1];
      }
      return null;
    }
    return null;
  }

  /**
   * Maximum row length
   */
  private static final int MAX_ROW_LEN = 69;

  /**
   * Set Message. Method handles automatic line wrapping for message
   * @param message as a String
   */
  public void setMessage(final String message) {
    StringBuilder sb = new StringBuilder(message.length() + 5);
    int split = 0;
    for (int i = 0; i < message.length(); i++) {
      if (message.charAt(i) == ' ' && split > MAX_ROW_LEN) {
        sb.append("\n");
        split = 0;
      } else if (message.charAt(i) == '\n') {
        sb.append(message.charAt(i));
        split = 0;
      } else {
        sb.append(message.charAt(i));
        split++;
      }
    }
    String imageInstructions = this.getImageInstruction();
    if (imageInstructions == null) {
      this.message = sb.toString();
    } else {
      this.message = sb.toString() + "|" + imageInstructions;
    }
  }

  /**
   * Set Image instructions into message part.
   * @param instructions Image instruction or null to clear
   */
  public void setImageInstructions(final String instructions) {
    String msg = this.getMessage();
    if (instructions != null) {
      this.message = msg + "|" + instructions;
    } else {
      this.message = msg;
    }
  }

  /**
   * Get Message Icon
   * @return Message Icon
   */
  public Icon16x16 getIcon() {
    return icon;
  }

  /**
   * Set Message Icon
   * @param icon Message Icon
   */
  public void setIcon(final Icon16x16 icon) {
    this.icon = icon;
  }

  /**
   * Set message coordinates. Use (-1,-1) not to place coordinate for message
   * @param coordinate coordinate
   */
  public void setCoordinate(final Coordinate coordinate) {
    this.coordinate = new Coordinate(coordinate);
  }

  /**
   * Get position on starmap where message happened
   * @return X coordinate
   */
  public int getX() {
    return coordinate.getX();
  }

  /**
   * Get position on starmap where message happened
   * @return X coordinate
   */
  public int getY() {
    return coordinate.getY();
  }

  /**
   * Get special string which can be used to match from lists to point
   * correct object.
   * @return Matching string
   */
  public String getMatchByString() {
    return matchByString;
  }

  /**
   * Set matching by string
   * @param matchByString Search string which needs to match an object.
   */
  public void setMatchByString(final String matchByString) {
    this.matchByString = matchByString;
  }

  @Override
  public String toString() {
    return type.toString() + " - " + message;
  }

  /**
   * Create a copy from message.
   * @return Copy from the message.
   */
  public Message copy() {
    Message msg = new Message(getType(), message, icon);
    msg.coordinate = this.coordinate;
    msg.index = this.index;
    msg.matchByString = this.matchByString;
    msg.randomEventPopup = this.randomEventPopup;
    return msg;
  }

  /**
   * Is there random event popup?
   * @return the randomEventPop
   */
  public boolean isRandomEventPop() {
    return randomEventPopup;
  }

  /**
   * Set random event popup flag.
   * @param randomEventPop the randomEventPop to set
   */
  public void setRandomEventPop(final boolean randomEventPop) {
    this.randomEventPopup = randomEventPop;
  }

}
