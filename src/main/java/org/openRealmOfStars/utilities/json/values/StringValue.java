package org.openRealmOfStars.utilities.json.values;

import org.openRealmOfStars.utilities.json.JsonStream;

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
* Json String value
*
*/
public class StringValue implements JsonValue {

  /**
   * Value as string.
   */
  private String value;

  /**
   * Constructor for String value.
   * @param value String value.
   */
  public StringValue(final String value) {
    this.value = value;
  }
  @Override
  public String getValueAsString() {
    return JsonStream.CH_DOUBLE_QOUTE + value + JsonStream.CH_DOUBLE_QOUTE;
  }

  /**
   * Set String value.
   * @param value Value to set.
   */
  public void setValue(final String value) {
    this.value = value;
  }
  /**
   * Get string value.
   * @return String value without double qoutes.
   */
  public String getValue() {
    return value;
  }
  @Override
  public ValueType getType() {
    return ValueType.STRING;
  }
}
