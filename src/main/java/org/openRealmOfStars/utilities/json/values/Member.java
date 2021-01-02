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
* Json Member
*
*/
public class Member {

  /**
   * Member name.
   */
  private String name;
  /**
   * Member value
   */
  private JsonValue value;

  /**
   * Creates new null member
   * @param name Member name
   */
  public Member(final String name) {
    this.name = name;
    value = new NullValue();
  }

  /**
   * Get member name.
   * @return Name as string.
   */
  public String getName() {
    return name;
  }

  /**
   * Get Member value.
   * @return Member value.
   */
  public JsonValue getValue() {
    return value;
  }

  /**
   * Set Member value.
   * @param value JsonValue
   */
  public void setValue(final JsonValue value) {
    this.value = value;
  }

  /**
   * Get Member value as string.
   * @return String.
   */
  public String getValueAsString() {
    StringBuilder sb = new StringBuilder();
    sb.append(JsonStream.CH_DOUBLE_QOUTE);
    sb.append(getName());
    sb.append(JsonStream.CH_DOUBLE_QOUTE + JsonStream.CH_NAME_SEPARATOR);
    sb.append(" ");
    sb.append(value.getValueAsString());
    return sb.toString();
  }

}
