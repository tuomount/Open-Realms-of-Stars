package org.openRealmOfStars.utilities.json.values;

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
* Json Boolean value
*
*/
public class BooleanValue implements JsonValue {

  /**
   * Boolean value true as string.
   */
  public static final String TRUE = "true";
  /**
   * Boolean value false as string.
   */
  public static final String FALSE = "false";
  /**
   * Boolean value.
   */
  private boolean value;

  /**
   * Boolean value constructor.
   * @param value Boolean value
   */
  public BooleanValue(final boolean value) {
    this.value = value;
  }
  /**
   * Change boolean value to something else
   * @param value New value to set.
   */
  public void setValue(final boolean value) {
    this.value = value;
  }
  /**
   * Get boolean value.
   * @return Boolean value
   */
  public boolean getValue() {
    return value;
  }
  @Override
  public String getValueAsString() {
    if (value) {
      return TRUE;
    }
    return FALSE;
  }
  @Override
  public ValueType getType() {
    return ValueType.BOOLEAN;
  }

}
