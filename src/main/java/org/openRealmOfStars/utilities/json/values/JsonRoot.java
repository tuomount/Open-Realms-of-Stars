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
* Json Root node.
*
*/
public class JsonRoot {

  /**
   * Root node's value.
   */
  private JsonValue value;

  /**
   * Create empty JSON root node.
   */
  public JsonRoot() {
    value = null;
  }

  /**
   * Create JSON root node with value.
   * @param value Value to set
   */
  public JsonRoot(final JsonValue value) {
    this.value = value;
  }

  /**
   * Get Root node's value.
   * @return JsonValue.
   */
  public JsonValue getValue() {
    return value;
  }

  /**
   * Set node's root value;
   * @param value Value;
   */
  public void setValue(final JsonValue value) {
    this.value = value;
  }
  /**
   * Get Json root.
   * @return Json as string
   */
  public String getValueAsString() {
    if (value == null) {
      return "";
    }
    return value.getValueAsString();
  }

  /**
   * Find First Member with matching name
   * @param memberName Member name to match
   * @return Member or null if not found.
   */
  public Member findFirst(final String memberName) {
    if (value.getType() == ValueType.OBJECT) {
      ObjectValue obj = (ObjectValue) value;
      return obj.findFirst(memberName);
    }
    if (value.getType() == ValueType.ARRAY) {
      ArrayValue obj = (ArrayValue) value;
      for (JsonValue arrayValue : obj.getArray()) {
        if (arrayValue.getType() == ValueType.OBJECT) {
          ObjectValue next = (ObjectValue) arrayValue;
          Member result = next.findFirst(memberName);
          if (result != null) {
            return result;
          }
        }
      }
    }
    return null;
  }

}
