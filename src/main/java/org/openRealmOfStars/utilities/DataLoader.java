package org.openRealmOfStars.utilities;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Abstract class for loading of type V, uniquely identified by type K,
 * from 1..N JSON files, where each file is a JSON Array with JSON Objects.
 *
 * <p>
 * Implementor must provide definitions of:<ul>
 * <li>How each JSON Object is converted to type V</li>
 * <li>How to extract identifier T from V (used in duplicate handling)</li>
 * <li>Name of V to be reported by the loader in case of errors, etc.</li>
 * </ul>
 * </p>
 *
 * Object loading from is using <b>file-level fail-fast</b> behavior.
 * In case of error with any object in a file, whole contents
 * of affected file is discarded, error is reported and loading
 * of other file begins.
 *
 * @param <K> Type of ID of V
 * @param <V> Type of loaded objects
 */
public abstract class DataLoader<K, V> {
  /**
   * Try to create object of type V from provided JSONObject.
   * @param jobj JSONObject to parse
   * @return V instance if successful, empty otherwise
   */
  protected abstract Optional<V> parseFromJson(JSONObject jobj);

  /**
   * Extracts identifier of type T from instance of type V.
   * This should never return null.
   * @param value object to get ID for, never null
   * @return Object's ID
   */
  protected abstract K valueIdGetter(V value);

  /**
   * Returns name of the type V that is loaded by the loader.
   * @return Name of the type V, cannot be null
   */
  protected abstract String typeNameGetter();

  /**
   * Loads all objects from provided file names and stores them
   * in provided Map, with keys being IDs of loaded objects.
   *
   * <p>
   * In case of there is stored object with same ID as loaded one,
   * stored object is replaced with the new one.
   * </p>
   * <p>
   * File loading takes resource shadowing into consideration.
   * </p>
   * @param storage Map to which store loaded objects
   * @param basePath Base path to load from
   * @param fileNames File names in basePath to load from, without .json suffix
   * @return Number of successfully loaded objects
   */
  public int loadAll(final Map<K, V> storage, final String basePath,
      final String... fileNames) {
    var loadedTotal = 0;
    for (var rawName : fileNames) {
      final var path = basePath + rawName + ".json";
      final var urlOpt = DataSources.getDataUrl(path);
      if (urlOpt.isEmpty()) {
        ErrorLogger.log("Cannot find file: " + path);
        continue;
      }
      final var loadedCount = loadFromUrl(urlOpt.get(), storage);
      ErrorLogger.debug("Number of loaded " + typeNameGetter()
          + " objects from file \"" + path + "\" : " + loadedCount);
      loadedTotal += loadedCount;
    }

    return loadedTotal;
  }

  /**
   * Load contents of specified file URL to storage.
   * In case of error, this method discards what it already loaded
   * and returns 0.
   * @param url URL to file
   * @param storage Map to store loaded objects to
   * @return Number of loaded objects
   */
  protected int loadFromUrl(final URL url, final Map<K, V> storage) {
    final var urlString = url.toExternalForm();
    final var typeName = typeNameGetter();
    try (var ins = url.openStream()) {
      final var jsonArray = new JSONArray(new JSONTokener(ins));
      var loadedDefs = new ArrayList<V>();

      for (var obj : jsonArray) {
        if (!(obj instanceof JSONObject)) {
          ErrorLogger.log("Malformed JSON file: " + urlString);
          return 0;
        }

        var buildingOpt = parseFromJson((JSONObject) obj);
        if (buildingOpt.isEmpty()) {
          final var tplMalform = "Malformed %1$s in file: %2$s";
          ErrorLogger.log(String.format(tplMalform, typeName, urlString));

          if (loadedDefs.size() == 0) {
            ErrorLogger.log("No successfully loaded " + typeName);
            return 0;
          }

          var lastGoodObject = loadedDefs.get(loadedDefs.size() - 1);
          final var id = valueIdGetter(lastGoodObject);
          final var tplLastGood = "Last loaded %1$s had ID: \"%2$s\"";
          ErrorLogger.log(String.format(tplLastGood, typeName, id.toString()));
          return 0;
        }

        loadedDefs.add(buildingOpt.get());
      }

      for (var def : loadedDefs) {
        final var identifier = valueIdGetter(def);
        if (storage.containsKey(identifier)) {
          ErrorLogger.log(typeName + " \"" + identifier
              + "\" already defined, redefining");
        }
        storage.put(identifier, def);
      }

      return loadedDefs.size();
    } catch (IOException e) {
      ErrorLogger.log(e);
    }
    return 0;
  }
}
