package org.openRealmOfStars.starMap;
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
}
