package org.openRealmOfStars.player.race.trait;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.utilities.DataLoader;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
 * Creates {@link RaceTrait}s, which it loaded from data files.
 */
public final class TraitFactory {
  /** The Singleton */
  private static final TraitFactory SINGLETON = new TraitFactory();

  /**
   * Create/Retrieve RaceTrait for given ID, if loaded
   * @param traitId ID of trait to retrieve
   * @return RaceTrait or empty
   */
  public static Optional<RaceTrait> create(final String traitId) {
    return SINGLETON.createById(traitId);
  }

  /**
   * Check if all hardcoded RaceTraits are available.
   * @return True if all hardcoded RaceTraits are loaded
   */
  public static boolean hardcodedTraitsAvailable() {
    return SINGLETON.hasHardcodedTraits();
  }

  /** RaceTraits this factory knows. IDs are used as keys. */
  private HashMap<String, RaceTrait> raceTraits;
  /** Tracks if factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, RaceTrait> loader;

  /** Contructor */
  private TraitFactory() {
    this.raceTraits = new HashMap<>();
    this.initialized = false;
    this.loader = new RaceTraitLoader();
  }

  /**
   * Create/Retrieve RaceTrait for given ID, initialize factory if not yet
   * @param traitId ID of trait to retrieve
   * @return RaceTrait or empty
   */
  private Optional<RaceTrait> createById(final String traitId) {
    if (!initialized) {
      initialized = true;
      init();
    }

    final var cachedTrait = raceTraits.get(traitId);
    return Optional.ofNullable(cachedTrait);
  }

  /**
   * Check if all hardcoded RaceTraits are available.
   * @return True if all hardcoded RaceTraits are loaded
   */
  private boolean hasHardcodedTraits() {
    for (var traitId : TraitIds.getHardcodedIds()) {
      final var traitOpt = createById(traitId);
      if (traitOpt.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  /** (Re)Initialize the factory */
  private void init() {
    raceTraits.clear();
    final var basePath = "resources/data/traits/";
    final String[] files = {
        "base" };
    final var traitsLoaded = loader.loadAll(raceTraits, basePath, files);
    ErrorLogger.log("RaceTraits loaded: " + traitsLoaded);
    if (!hasHardcodedTraits()) {
      ErrorLogger.log("Some core RaceTraits were not loaded."
          + " Game might not work as expected!");
    }
  }
}

/** RaceTrait loader */
class RaceTraitLoader extends DataLoader<String, RaceTrait> {

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
  protected Optional<RaceTrait> parseFromJson(final JSONObject jobj) {
    try {
      final var traitId = jobj.getString("id");
      final var name = jobj.getString("name");
      final var description = jobj.getString("description");

      var jsonConflictingIds = jobj.optJSONArray("conflictsWith",
          new JSONArray());
      var conflictingIds = new ArrayList<String>();
      for (int i = 0; i < jsonConflictingIds.length(); i++) {
        conflictingIds.add(jsonConflictingIds.getString(i));
      }

      var tmpTrait = new RaceTrait(traitId, name, description, conflictingIds);
      return Optional.of(tmpTrait);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final RaceTrait value) {
    return value.getId();
  }

  @Override
  protected String typeNameGetter() {
    return "RaceTrait";
  }
}