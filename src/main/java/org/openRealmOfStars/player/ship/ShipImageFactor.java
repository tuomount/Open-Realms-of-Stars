package org.openRealmOfStars.player.ship;
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

/**
 * Ship Image factor. */
public final class ShipImageFactor {

  /** Default shipimage */
  private static final ShipImage DEFAULT_SHIPIMAGE = new ShipImage(
      "humanships.png", false, "DEFAULT");
  /** The Singleton */
  private static final ShipImageFactor SINGLETON = new ShipImageFactor();

  /** Hashmap for space ship bridges. Key is space race name. */
  private HashMap<String, ShipImage> mapForShipImages;

  /** Tracks if factory is initialized with data */
  private static boolean initialized;

  /** JSON data loader */
  private DataLoader<String, ShipImage> loader;

  /**
   * Create Ship Image
   * @param name ShipImage ID
   * @return Found ship image or default.
   */
  public static ShipImage create(final String name) {
    if (!initialized) {
      SINGLETON.init();
      initialized = true;
    }
    ShipImage image = SINGLETON.createById(name);
    if (image == null) {
      ErrorLogger.log("Could not find ship image with ID " + name
          + ". Using default.");
      image = DEFAULT_SHIPIMAGE;
    }
    return image;
  }

  /**
   * Get All Ship Image IDs
   * @return Array of strings
   */
  public static String[] getAllIds() {
    if (!initialized) {
      SINGLETON.init();
      initialized = true;
    }
    return SINGLETON.mapForShipImages.keySet().toArray(new String[0]);
  }
/**
   * Constructor for bridge graph factory
   */
  private ShipImageFactor() {
    mapForShipImages = new HashMap<>();
    initialized = false;
    loader = new ShipImageLoader();
  }

  /**
   * Create ShipImage from Hash map.
   * @param id ID aka name
   * @return ShipImage or null
   */
  private ShipImage createById(final String id) {
    return mapForShipImages.get(id);
  }
  /** Init bridges by loading from JSON */
  private void init() {
    final var basePath = "resources/data/graphset/";
    final String[] files = {
        "spaceship" };
    final var traitsLoaded = loader.loadAll(mapForShipImages, basePath, files);
    ErrorLogger.log("Spaceship images loaded: " + traitsLoaded);
  }
}

/** ShipImage loader */
class ShipImageLoader extends DataLoader<String, ShipImage> {

  /**
   * Parse ShipImage from a JSON file.
   * <p>
   * JSON ShipImage object must have following format:<ul>
   * <li>id : String</li>
   * <li>path : String</li>
   * <li>monsters: Boolean</li>
   * </ul>
   * </p>
   * @param jobj JSONObject to parse ShipImage from
   * @return Parsed ShipImage or empty
   */
  @Override
  protected Optional<ShipImage> parseFromJson(final JSONObject jobj) {
    try {
      final var id = jobj.getString("id");
      final var path = jobj.getString("path");
      final var monsters = jobj.getBoolean("monsters");


      var tmpTrait = new ShipImage(path, monsters, id);
      return Optional.of(tmpTrait);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final ShipImage value) {
    return value.getId();
  }

  @Override
  protected String typeNameGetter() {
    return "RaceTrait";
  }
}
