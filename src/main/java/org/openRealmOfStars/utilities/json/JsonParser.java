package org.openRealmOfStars.utilities.json;

import java.io.IOException;

import org.openRealmOfStars.utilities.json.values.ArrayValue;
import org.openRealmOfStars.utilities.json.values.BooleanValue;
import org.openRealmOfStars.utilities.json.values.JsonValue;
import org.openRealmOfStars.utilities.json.values.Member;
import org.openRealmOfStars.utilities.json.values.NullValue;
import org.openRealmOfStars.utilities.json.values.NumberValue;
import org.openRealmOfStars.utilities.json.values.ObjectValue;
import org.openRealmOfStars.utilities.json.values.StringValue;

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
* Json parser
*
*/
public final class JsonParser {

  /**
   * Hiding constructor.
   */
  private JsonParser() {
    // Hiding constructor
  }

  /**
   * Parse All possible values from stream
   * @param json JsonStream
   * @return JsonValue or null if fails.
   * @throws IOException  if stream is closed
   * @throws JsonException If json in malformed
   */
  private static JsonValue parseAllValues(final JsonStream json)
      throws IOException, JsonException {
    JsonValue value = null;
    if (json.readAway("null")) {
      value = new NullValue();
    } else if (json.readAway("true")) {
      value = new BooleanValue(true);
    } else if (json.readAway("false")) {
      value = new BooleanValue(false);
    } else {
      String str = json.readString();
      if (str != null) {
        value = new StringValue(str);
      }
      if (value == null) {
        value = parseArray(json);
      }
      if (value == null) {
        str = json.readNumber();
        if (str != null) {
          try {
            value = new NumberValue(str);
          } catch (IllegalArgumentException e) {
            value = null;
          }
        }
      }
      if (value == null) {
        value = parseObject(json);
      }
    }
    return value;
  }

  /**
   * Parse array.
   * @param json Json Stream
   * @return ArrayValue
   * @throws IOException If stream is closed
   * @throws JsonException If Json is malformed
   */
  private static ArrayValue parseArray(final JsonStream json)
      throws IOException, JsonException {
    if (json.isCurrent(JsonStream.BEGIN_ARRAY)) {
      boolean end = false;
      boolean sepatorRequired = false;
      ArrayValue result = new ArrayValue();
      do {
        if (json.isCurrent(JsonStream.END_ARRAY)) {
          end = true;
        } else {
          json.readWhiteSpace();
          if (sepatorRequired) {
            if (json.isCurrent(JsonStream.VALUE_SEPARATOR)) {
              sepatorRequired = false;
              json.readWhiteSpace();
            } else {
              throw new JsonException("Array is missing value separator.");
            }
          }
          JsonValue value = parseAllValues(json);
          if (value != null) {
            result.getArray().add(value);
            sepatorRequired = true;
          }
        }
      } while (!end);
      return result;
    }
    return null;
  }
  /**
   * Parse JSON Member
   * @param json JsonStream
   * @return Member or null if name is missing.
   * @throws IOException If stream is closed
   * @throws JsonException If Json is malformed
   */
  private static Member parseMember(final JsonStream json)
      throws IOException, JsonException {
    String name = json.readString();
    JsonValue value = null;
    if (name != null) {
      json.readWhiteSpace();
      if (json.isCurrent(JsonStream.NAME_SEPARATOR)) {
        json.readWhiteSpace();
        value = parseAllValues(json);
        Member result = new Member(name);
        result.setValue(value);
        return result;
      } else {
        throw new JsonException("Name separator is missing.");
      }
    } else {
      return null;
    }
  }
  /**
   * Parse Json Object.
   * @param json JsonStream for parsing
   * @return ObjectValue
   * @throws IOException If reading fails
   * @throws JsonException if Json parsing fails
   */
  private static ObjectValue parseObject(final JsonStream json)
      throws IOException, JsonException {
    if (json.isCurrent(JsonStream.BEGIN_OBJECT)) {
      ObjectValue result = new ObjectValue();
      boolean parsing = true;
      do {
        json.readWhiteSpace();
        Member member = parseMember(json);
        if (member != null) {
          result.getMembers().add(member);
        }
        json.readWhiteSpace();
        if (json.isCurrent(JsonStream.END_OBJECT)) {
          parsing = false;
        } else if (!json.isCurrent(JsonStream.VALUE_SEPARATOR)) {
          throw new JsonException("Object is missing value separator");
        }
      } while (parsing);
      return result;
    } else {
      throw new JsonException("Not object for parsing.");
    }
  }

  /**
   * Parse full json stream.
   * @param json JsonStream for parsing.
   * @return ObjectValue which is the root object.
   * @throws IOException If reading fails
   * @throws JsonException if Json parsing fails
   */
  public static ObjectValue parseJson(final JsonStream json)
      throws IOException, JsonException {
    return parseObject(json);
  }
}
