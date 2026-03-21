package org.openRealmOfStars.player.message;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2026 Tuomo Untinen
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * MessageType for combination of main message and sub message type.
 */
public class MessageType {

  /**
   * Main Type.
   */
  private MmType mainType;

  /**
   * Sub Type
   */
  private SmType subType;

  /**
   * Message Type, define both types.
   * @param main MainMessageType
   * @param sub SubMessageType
   */
  public MessageType(final MmType main, final SmType sub) {
    mainType = main;
    subType = sub;
  }

  /**
   * Message Type, Sub type is generic
   * @param main MainMessageType
   */
  public MessageType(final MmType main) {
    mainType = main;
    subType = SmType.GENERIC;
  }

  /**
   * Message Type, read from data input stream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public MessageType(final DataInputStream dis) throws IOException {
    short value = dis.readShort();
    mainType = MmType.getTypeByIndex(value);
    value = dis.readShort();
    subType = SmType.getTypeByIndex(value);
  }

  /**
   * Save messageType into DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveMessageType(final DataOutputStream dos) throws IOException {
    dos.writeShort(mainType.getIndex());
    dos.writeShort(subType.getIndex());
  }
  /**
   * Get Main Type
   * @return MainMessageType
   */
  public MmType getMainType() {
    return mainType;
  }

  /**
   * Get Sub Type
   * @return SubMessageType
   */
  public SmType getSubType() {
    return subType;
  }

  /**
   * Are type messageTypes equals. This will only compare main types.
   * @param main Another main type.
   * @return True if equal.
   */
  public boolean equals(final MmType main) {
    if (main == mainType) {
      return true;
    }
    return false;
  }

  /**
   * Are type messageTypes equals. This will compare both types.
   * @param another Another message type.
   * @return True if equal both are equal
   */
  public boolean equals(final MessageType another) {
    if (another.mainType == mainType && another.subType == subType) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return mainType.getIndex() * 32000 + subType.getIndex();
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MessageType other = (MessageType) obj;
    return mainType == other.mainType && subType == other.subType;
  }

  @Override
  public String toString() {
    return mainType.toString() + " - " + subType.toString();
  }

}
