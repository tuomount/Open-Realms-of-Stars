package org.openRealmOfStars.starMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2019,2022 Tuomo Untinen
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
   * Black hole info, value match on list number
   */
  public static final byte TYPE_BLACKHOLE = 4;
  /**
   * Black hole info center, value match on list number
   */
  public static final byte TYPE_BLACKHOLE_CENTER = 5;

  /**
   * Special alonian start info, value match on player inder.
   */
  public static final byte TYPE_ALONIAN_START = 6;
  /**
   * Square info type
   */
  private byte type;

  /**
   * Value
   */
  private short value;

  /**
   * Fixed EMPTY_TILE square info
   */
  public static final SquareInfo EMPTY_TILE = new SquareInfo(TYPE_EMPTY, 0);

  /**
   * Create square info.
   * @param type Square info type
   * @param value Value
   */
  public SquareInfo(final byte type, final int value) {
    this.setType(type);
    this.setValue(value);
  }

  /**
   * Write Square info to DataOutput stream
   * @param dos DataOutputStream to write
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void writeSquareInfo(final DataOutputStream dos) throws IOException {
    dos.writeByte(type);
    dos.writeShort(value);
  }

  /**
   * Read square info from DataInputStream
   * @param dis DataInputStream where to read
   * @throws IOException if there is any problem with DataInputStream
   */
  public SquareInfo(final DataInputStream dis) throws IOException {
    this.setType(dis.readByte());
    this.setValue(dis.readShort());
  }

  /**
   * Get type of Square, See TYPE_ like TYPE_EMPTY, TYPE_SUN, TYPE_PLANET etc
   * @return Type info
   */
  public byte getType() {
    return type;
  }

  /**
   * Set square type
   * @param type Square typ
   */
  public void setType(final byte type) {
    this.type = type;
  }

  /**
   * Get Value for square. This can be index for planet list
   * if square is planet or index for sun list if square is sun.
   * @return Square value
   */
  public int getValue() {
    return value;
  }

  /**
   * Maximum value for square value
   */
  public static final int MAX_VALUE = 32767;
  /**
   * Set Square value
   * @param value Square value
   */
  public void setValue(final int value) {
    if (value > MAX_VALUE) {
      this.value = MAX_VALUE;
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
    case TYPE_EMPTY:
      return false;
    case TYPE_SUN:
      return true;
    case TYPE_PLANET:
      return false;
    case TYPE_GAS_PLANET:
      return true;
    case TYPE_BLACKHOLE:
      return false;
    case TYPE_BLACKHOLE_CENTER:
      return true;
    case TYPE_ALONIAN_START:
      return true;
    default:
      return false;
    }
  }

  /**
   * Is sector blocked or not
   * @return True if sector is blocked otherwise false
   */
  public boolean isBlocked() {
    switch (type) {
    case TYPE_EMPTY:
      return false;
    case TYPE_SUN:
      return true;
    case TYPE_PLANET:
      return false;
    case TYPE_GAS_PLANET:
      return true;
    case TYPE_BLACKHOLE:
      return true;
    case TYPE_BLACKHOLE_CENTER:
      return true;
    case TYPE_ALONIAN_START:
      return true;
    default:
      return false;
    }
  }

}
