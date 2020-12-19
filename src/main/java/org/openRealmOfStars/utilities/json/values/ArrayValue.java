package org.openRealmOfStars.utilities.json.values;

import java.util.ArrayList;

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
* Array Value for Json
*
*/
public class ArrayValue implements JsonValue {

  /**
   * Array of values.
   */
  private ArrayList<JsonValue> arrayValue;

  /**
   * Constructor for ArrayValue.
   */
  public ArrayValue() {
    arrayValue = new ArrayList<>();
  }

  /**
   * Get array as array list.
   * @return Array list of Json Value.
   */
  public ArrayList<JsonValue> getArray() {
    return arrayValue;
  }
  @Override
  public String getValueAsString() {
    StringBuilder sb = new StringBuilder();
    sb.append(JsonStream.CH_BEGIN_ARRAY);
    for (int i = 0; i < arrayValue.size(); i++) {
      JsonValue value = arrayValue.get(i);
      sb.append(value.getValueAsString());
      if (i < arrayValue.size() - 1) {
        sb.append(JsonStream.CH_VALUE_SEPARATOR);
      }
    }
    sb.append(JsonStream.CH_END_ARRAY);
    return sb.toString();
  }

  @Override
  public ValueType getType() {
    return ValueType.ARRAY;
  }

}
