package org.openRealmOfStars.utilities.json;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 Tuomo Untinen
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
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility for handling JSON.
 *
 */
public final class JsonUtil {

  /**
   * Hiding constructor.
   */
  private JsonUtil() {
    // Nothing to do
  }

  /**
   * Get JSON String by key.
   * @param json JSONObject
   * @param key Key
   * @return String or null if not found
   */
  public static String getJsonString(final JSONObject json, final String key) {
    String result = null;
    try {
      result = json.getString(key);
    } catch (JSONException e) {
      // Do nothing, key just did not found
    }
    return result;
  }

  /**
   * Get JSON Boolean by key.
   * @param json JSONObject
   * @param key Key
   * @return Boolean or null if not found
   */
  public static Boolean getJsonBoolean(final JSONObject json,
      final String key) {
    Boolean result = null;
    try {
      result = json.getBoolean(key);
    } catch (JSONException e) {
      // Do nothing, key just did not found
    }
    return result;
  }

  /**
   * Get JSON Integer by key.
   * @param json JSONObject
   * @param key Key
   * @return Integer or null if not found
   */
  public static Integer getJsonInt(final JSONObject json,
      final String key) {
    Integer result = null;
    try {
      result = json.getInt(key);
    } catch (JSONException e) {
      // Do nothing, key just did not found
    }
    return result;
  }

  /**
   * Get JSON Object by key.
   * @param json JSONObject
   * @param key Key
   * @return JSONObject or null if not found
   */
  public static JSONObject getJsonObject(final JSONObject json,
      final String key) {
    JSONObject result = null;
    try {
      result = json.getJSONObject(key);
    } catch (JSONException e) {
      // Do nothing, key just did not found
    }
    return result;
  }

}
