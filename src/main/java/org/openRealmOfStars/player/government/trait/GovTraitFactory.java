package org.openRealmOfStars.player.government.trait;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.utilities.DataLoader;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
 * Creates {@link GovTrait}s, which it loaded from data files.
 */
public final class GovTraitFactory {
  /** The Singleton */
  private static final GovTraitFactory SINGLETON = new GovTraitFactory();

  /**
   * Create/Retrieve GovTrait for given ID, if loaded
   * @param traitId ID of trait to retrieve
   * @return GovTrait or empty
   */
  public static Optional<GovTrait> create(final String traitId) {
    return SINGLETON.createById(traitId);
  }

  /**
   * Check if all hardcoded GovTraits are available.
   * @return True if all hardcoded GovTraits are loaded
   */
  public static boolean hardcodedTraitsAvailable() {
    return SINGLETON.hasHardcodedTraits();
  }

  /** GovTraits this factory knows. IDs are used as keys. */
  private HashMap<String, GovTrait> govTraits;
  /** Tracks if factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, GovTrait> loader;

  /** Contructor */
  private GovTraitFactory() {
    this.govTraits = new HashMap<>();
    this.initialized = false;
    this.loader = new GovTraitLoader();
  }

  /**
   * Create/Retrieve GovTrait for given ID, initialize factory if not yet
   * @param traitId ID of trait to retrieve
   * @return GovTrait or empty
   */
  private Optional<GovTrait> createById(final String traitId) {
    if (!initialized) {
      initialized = true;
      init();
    }

    final var cachedTrait = govTraits.get(traitId);
    return Optional.ofNullable(cachedTrait);
  }

  /**
   * Check if all hardcoded GovTraits are available.
   * @return True if all hardcoded GovTraits are loaded
   */
  private boolean hasHardcodedTraits() {
    for (var traitId : GovTraitIds.getHardcodedIds()) {
      final var traitOpt = createById(traitId);
      if (traitOpt.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  /** (Re)Initialize the factory */
  private void init() {
    govTraits.clear();
    final var basePath = "resources/data/govTraits/";
    final String[] files = {
        "base" };
    final var traitsLoaded = loader.loadAll(govTraits, basePath, files);
    ErrorLogger.log("GovTraits loaded: " + traitsLoaded);
    if (!hasHardcodedTraits()) {
      ErrorLogger.log("Some core GovTraits were not loaded."
          + " Game might not work as expected!");
    }
  }
}

/** GovTrait loader */
class GovTraitLoader extends DataLoader<String, GovTrait> {

  /**
   * Parse GovTrait from a JSON file.
   * <p>
   * JSON GovTrait object must have following format:<ul>
   * <li>id : String</li>
   * <li>name : String</li>
   * <li>description: String</li>
   * <li>conflictsWith : List of Strings (OPTIONAL)</li>
   * </ul>
   * </p>
   * JSON GovTraits missing the "conflicsWith" field
   * are considered to have no conflicts.
   * @param jobj JSONObject to parse GovTrait from
   * @return Parsed GovTrait or empty
   */
  @Override
  protected Optional<GovTrait> parseFromJson(final JSONObject jobj) {
    try {
      final var traitId = jobj.getString("id");
      final var name = jobj.getString("name");
      final var description = jobj.getString("description");
      final var points = ((byte) jobj.getInt("points"));
      String group = jobj.optString("group");

      var jsonConflictingIds = jobj.optJSONArray("conflictsWith",
          new JSONArray());
      var conflictingIds = new ArrayList<String>();
      for (int i = 0; i < jsonConflictingIds.length(); i++) {
        conflictingIds.add(jsonConflictingIds.getString(i));
      }

      var tmpTrait = new GovTrait(traitId, name, description, points,
          conflictingIds);
      if (!group.isEmpty()) {
        tmpTrait.setGroup(group);
      }
      return Optional.of(tmpTrait);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final GovTrait value) {
    return value.getId();
  }

  @Override
  protected String typeNameGetter() {
    return "GovTrait";
  }
}