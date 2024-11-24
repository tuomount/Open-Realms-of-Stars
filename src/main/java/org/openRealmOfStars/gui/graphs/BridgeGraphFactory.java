package org.openRealmOfStars.gui.graphs;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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

import java.util.HashMap;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.DataLoader;

/** Bridge Graph Factory */
public final class BridgeGraphFactory {

  /** Default bridge if fetching correct one fails */
  private static final BridgeGraph DEFAULT_BRIDGE = new BridgeGraph("Default",
      "/resources/images/bridge1.png", 57);
  /** The Singleton */
  private static final BridgeGraphFactory SINGLETON = new BridgeGraphFactory();

  /** Hashmap for space ship bridges. Key is space race name. */
  private HashMap<String, BridgeGraph> mapForBridges;

  /** Tracks if factory is initialized with data */
  private static boolean initialized;

  /** JSON data loader */
  private DataLoader<String, BridgeGraph> loader;

  /**
   * Create Bridge graph.
   * @param name Bridge ID
   * @return Found bridge or default.
   */
  public static BridgeGraph create(final String name) {
    if (!initialized) {
      SINGLETON.init();
      initialized = true;
    }
    BridgeGraph bridge = SINGLETON.createById(name);
    if (bridge == null) {
      ErrorLogger.log("Could not find bridge with ID " + name
          + ". Using default.");
      bridge = DEFAULT_BRIDGE;
    }
    return bridge;
  }

  /**
   * Get All Bridge graphics IDs in array.
   * @return Array of Strings.
   */
  public static String[] getAllIds() {
    if (!initialized) {
      SINGLETON.init();
      initialized = true;
    }
    return SINGLETON.mapForBridges.keySet().toArray(new String[0]);
  }
  /**
   * Constructor for bridge graph factory
   */
  private BridgeGraphFactory() {
    mapForBridges = new HashMap<>();
    initialized = false;
    loader = new BridgeGraphLoader();
  }

  /**
   * Create BridgeGraph from Hash map.
   * @param id ID aka name
   * @return BridgeGraph or null
   */
  private BridgeGraph createById(final String id) {
    return mapForBridges.get(id);
  }
  /** Init bridges by loading from JSON */
  private void init() {
    final var basePath = "resources/data/graphset/";
    final String[] files = {
        "bridges" };
    final var traitsLoaded = loader.loadAll(mapForBridges, basePath, files);
    ErrorLogger.log("Bridges loaded: " + traitsLoaded);
  }
}

/** BridgeGraph loader */
class BridgeGraphLoader extends DataLoader<String, BridgeGraph> {

  /**
   * Parse RaceTrait from a JSON file.
   * <p>
   * JSON RaceTrait object must have following format:<ul>
   * <li>id : String</li>
   * <li>name : String</li>
   * <li>description: String</li>
   * <li>conflictsWith : List of Strings (OPTIONAL)</li>
   * </ul>
   * </p>
   * JSON RaceTraits missing the "conflicsWith" field
   * are considered to have no conflicts.
   * @param jobj JSONObject to parse RaceTrait from
   * @return Parsed RaceTrait or empty
   */
  @Override
  protected Optional<BridgeGraph> parseFromJson(final JSONObject jobj) {
    try {
      final var id = jobj.getString("id");
      final var path = jobj.getString("path");
      final var yOffset = jobj.getInt("y-offset");


      var tmpTrait = new BridgeGraph(id, path, yOffset);
      return Optional.of(tmpTrait);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final BridgeGraph value) {
    return value.getName();
  }

  @Override
  protected String typeNameGetter() {
    return "RaceTrait";
  }
}
