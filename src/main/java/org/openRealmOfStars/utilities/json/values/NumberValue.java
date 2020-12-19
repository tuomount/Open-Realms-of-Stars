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
* Json Number value
*
*/
public class NumberValue implements JsonValue {

  /**
   * Value is actually stored as string so not to change it.
   */
  private String value;

  @Override
  public String getValueAsString() {
    return value;
  }

  /**
   * Is number valid json number.
   * @param value Value to check.
   * @return True if valid number
   */
  public static final boolean isValidNumber(final String value) {
    try {
      Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
  /**
   * Number value constructor
   * @param value Number as string.
   * @throws IllegalArgumentException if number parsing fails
   */
  public NumberValue(final String value) throws IllegalArgumentException {
    if (isValidNumber(value)) {
      this.value = value;
    } else {
      throw new IllegalArgumentException(value
          + " cannot be converted to number.");
    }
  }
  /**
   * Get Number value as integer part
   * @return int value
   */
  public int getValueAsInt() {
    return (int) Double.parseDouble(value);
  }

  /**
   * Get Number value as double
   * @return double value
   */
  public double getValueAsDouble() {
    return Double.parseDouble(value);
  }
@Override
  public ValueType getType() {
    return ValueType.NUMBER;
  }

}
