package org.openRealmOfStars.player.ship;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Tuomo Untinen
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
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.DataLoader;

/**
 * Ship Hull Factory
 */
public final class ShipHullFactory {

  /** The Singleton */
  private static final ShipHullFactory SINGLETON =
      new ShipHullFactory();

  /** Map of all building definitons, with IDs as keys */
  private HashMap<String, ShipHull> shipHulls;
  /** Tracks if the factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, ShipHull> loader;

  /** Constructor */
  private ShipHullFactory() {
    this.shipHulls = new HashMap<>();
    this.initialized = false;
    this.loader = new ShipHullLoader();
  }

  /**
   * Create ShipHull with specified name
   * @param name Building name
   * @param race Original builder race
   * @return ShipHull or null if not found
   */
  public static ShipHull createByName(final String name,
      final SpaceRace race) {
    ShipHull hull = SINGLETON.createByNameImpl(name).orElse(null);
    if (hull != null) {
      return new ShipHull(hull, race);
    }
    return null;
  }
  /**
   * Create/Retrieve ShipHull with given name,
   * initialize factory if not yet initialized.
   *
   * @param name Name of ShipHull to retrieve
   * @return ShipHull or empty
   */
  private Optional<ShipHull> createByNameImpl(final String name) {
    if (!initialized) {
      init();
      this.initialized = true;
    }

    final var cachedTrait = shipHulls.get(name);
    return Optional.ofNullable(cachedTrait);
  }

  /** Initialize the factory */
  private void init() {
    final var dataFilesBase = "resources/data/ship_hulls/";
    final String[] dataFiles = {
        "hulls"};

    var loadedCount = loader.loadAll(shipHulls, dataFilesBase, dataFiles);
    ErrorLogger.log("Ship hulls loaded: " + loadedCount);
  }
  /** ShipComponent JSON data loader */
  class ShipHullLoader extends DataLoader<String, ShipHull> {

    @Override
    protected Optional<ShipHull> parseFromJson(final JSONObject jobj) {
      try {
        // Mandatory fields
        final var type = jobj.getEnum(ShipHullType.class, "type");
        final var name = jobj.getString("name");
        final var cost = jobj.getInt("cost");
        final var metalCost = jobj.getInt("metalCost");
        final var maxSlots = jobj.getInt("maxSlots");
        final var hp = jobj.getInt("hp");
        final var size = jobj.getEnum(ShipSize.class, "size");

        var tmp = new ShipHull(name, maxSlots, hp, type, size, cost, metalCost);
        // Optional fields
        tmp.setFleetCapacity(jobj.optDouble("fleetCapacity", 0));
        tmp.setImageIndex(ShipImage.getShipType(jobj.optString("image",
            "Scout")));

        return Optional.of(tmp);
      } catch (JSONException e) {
        ErrorLogger.log(e);
      }

      return Optional.empty();
    }

    @Override
    protected String valueIdGetter(final ShipHull value) {
      return value.getName();
    }

    @Override
    protected String typeNameGetter() {
      return "Hull";
    }
  }

}
