package org.openRealmOfStars.starMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
 * Single square info about on map
 * 
 */

public class SquareInfo {
  
  /**
   * Empty type no extra info
   */
  public static final byte TYPE_EMPTY = 0;

  /**
   * Sun info, value matches on list number
   */
  public static final byte TYPE_SUN = 1;

  /**
   * Planet info, value matches on list number
   */
  public static final byte TYPE_PLANET = 2;

  /**
   * Gas Planet info, value matches on list number
   */
  public static final byte TYPE_GAS_PLANET = 3;

  /**
   * Square info type
   */
  private byte type;
  
  /**
   * Value
   */
  private short value;

  public static final SquareInfo EMPTY_TILE = new SquareInfo(TYPE_EMPTY, 0);
  
  /**
   * Create square info.
   * @param type
   * @param value
   */
  public SquareInfo(byte type, int value) {
    this.setType(type);
    this.setValue(value);
  }
  
  /**
   * Write Square info to DataOutput stream
   * @param dos DataOutputStream to write
   * @throws IOException
   */
  public void writeSquareInfo(DataOutputStream dos) throws IOException {
    dos.writeByte(type);
    dos.writeShort(value);
  }
  
  /**
   * Read square info from DataInputStream
   * @param dis DataInputStream where to read
   * @throws IOException
   */
  public SquareInfo(DataInputStream dis) throws IOException {
    this.setType(dis.readByte());
    this.setValue(dis.readShort());
  }

  public byte getType() {
    return type;
  }

  public void setType(byte type) {
    this.type = type;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    if (value > 32767) {
      this.value = 32767;
    } else if (value < 0) {
      this.value = 0;
    } else {
      this.value = (short) value;
    }
  }
  
  /**
   * Blocks sector visibility or not
   * @return True if sector blocks visibility otherwise false
   */
  public boolean isVisibilityBlocked() {
    switch (type) {
    case TYPE_EMPTY: return false;
    case TYPE_SUN: return true;
    case TYPE_PLANET: return false;
    case TYPE_GAS_PLANET: return true;
    default: return false;
    }
  }
  
  /**
   * Is sector blocked or not
   * @return True if sector is blocked otherwise false
   */
  public boolean isBlocked() {
    switch (type) {
    case TYPE_EMPTY: return false;
    case TYPE_SUN: return true;
    case TYPE_PLANET: return false;
    case TYPE_GAS_PLANET: return true;
    default: return false;
    }
  }

}
