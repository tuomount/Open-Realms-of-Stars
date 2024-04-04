package org.openRealmOfStars.starMap.planet.status;
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
 * Creates statuses that can be applied to planets.
 * Statuses have to be recognized by the factory
 * in order to create {@link AppliedStatus} objects.
 *
 * @see PlanetaryStatus
 */
public final class StatusFactory {
  /** The Singleton */
  private static final StatusFactory SINGLETON = new StatusFactory();

  /**
   * Creates new status with requested statusId.
   * This will load data from files if not done already.
   * @param statusId ID of PlanetaryStatus
   * @return Requested PlanetaryStatus,
   *         empty if statusId is invalid
   */
  public static Optional<PlanetaryStatus> create(final String statusId) {
    return SINGLETON.createStatus(statusId);
  }

  /**
   * Creates new status with requested statusId.
   * This will load data from files if not done already.
   * @param statusId ID of PlanetaryStatus
   * @return Requested Applied status of PlanetaryStatus,
   *         empty if statusId is invalid
   */
  public static Optional<AppliedStatus> createAppliedStatus(
      final String statusId) {
    var planetaryStatus = SINGLETON.createStatus(statusId);
    if (planetaryStatus.isPresent()) {
      return Optional.of(new AppliedStatus(planetaryStatus.get()));
    }
    return Optional.empty();
  }

  /**
   * Creates new status with requested statusId.
   * This will load data from files if not done already.
   * @param statusId ID of PlanetaryStatus
   * @param type TimedStatusType
   * @param count Number of star year before to applied.
   * @return Requested TimedStatus from planetaryStatus,
   *         empty if statusId is invalid
   */
  public static Optional<TimedStatus> createTimedStatus(
      final String statusId, final TimedStatusType type, final int count) {
    var planetaryStatus = SINGLETON.createStatus(statusId);
    if (planetaryStatus.isPresent()) {
      return Optional.of(new TimedStatus(planetaryStatus.get(), type, count));
    }
    return Optional.empty();
  }

  /**
   * Get new status with requested statusId.
   * This will load data from files if not done already.
   * @param statusId ID of PlanetaryStatus
   * @param type TimedStatusType
   * @param count Number of star year before to applied.
   * @return Requested TimedStatus from planetaryStatus,
   *         null if statusId is invalid
   */
  public static TimedStatus getTimedStatus(
      final String statusId, final TimedStatusType type, final int count) {
    var planetaryStatus = SINGLETON.createStatus(statusId);
    if (planetaryStatus.isPresent()) {
      return new TimedStatus(planetaryStatus.get(), type, count);
    }
    return null;
  }

  /** Statuses factory can create */
  private HashMap<String, PlanetaryStatus> validStatuses;
  /** Tracks if factory is already initialized from external data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, PlanetaryStatus> loader;

  /** Constructor */
  private StatusFactory() {
    this.validStatuses = new HashMap<>();
    this.initialized = false;
    this.loader = new PlanetaryStatusLoader();
  }

  /**
   * Creates new status with requested statusId.
   * This will load data from files if not done already.
   * @param statusId ID of PlanetaryStatus
   * @return Requested PlanetaryStatus,
   *         empty if statusId is invalid
   */
  private Optional<PlanetaryStatus> createStatus(final String statusId) {
    if (!initialized) {
      init();
      initialized = true;
    }

    var statusDef = validStatuses.getOrDefault(statusId, null);
    if (statusDef == null) {
      return Optional.empty();
    }
    return Optional.of(statusDef);
  }

  /** Initialize the factory with external data */
  private void init() {
    final var basePath = "resources/data/planet_statuses/";
    final String[] dataFiles = {
        "base" };
    var loadedTotal = loader.loadAll(validStatuses, basePath, dataFiles);
    ErrorLogger.log("Loaded planetary statuses: " + loadedTotal);
  }

  /**
   * Create/Retrieve all planetary statuses, initialize factory if not yet
   * @return Status array
   */
  private PlanetaryStatus[] getAll() {
    if (!initialized) {
      initialized = true;
      init();
    }
    return validStatuses.values().toArray(new PlanetaryStatus[0]);
  }

  /**
   * Get All statuses in array.
   * @return Statuses array
   */
  public static PlanetaryStatus[] getValues() {
    return SINGLETON.getAll();
  }
}

/** PlanetaryStatus JSON data loader */
class PlanetaryStatusLoader extends DataLoader<String, PlanetaryStatus> {
  @Override
  protected Optional<PlanetaryStatus> parseFromJson(final JSONObject jobj) {
    // Mandatory fields
    String statusId = null;
    String name = null;
    try {
      statusId = jobj.getString("id");
      name = jobj.getString("name");
    } catch (JSONException e) {
      ErrorLogger.log(e);
      return Optional.empty();
    }

    // Optional fields
    final var description = jobj.optString("description", "");
    String discovery = jobj.optString("discoveryText", description);
    var rawConflicts = jobj.optJSONArray("conflictsWith", new JSONArray());
    var conflictsWith = new ArrayList<String>();
    for (var rawEntry : rawConflicts) {
      if (!(rawEntry instanceof String)) {
        return Optional.empty();
      }
      conflictsWith.add((String) rawEntry);
    }
    var tmp = new PlanetaryStatus(statusId, name, description,
        conflictsWith.toArray(new String[conflictsWith.size()]));

    tmp.setDiscoveryText(discovery);
    // Other optional fields
    tmp.setFoodBonus(jobj.optInt("foodBonus", 0));
    tmp.setMineBonus(jobj.optInt("mineBonus", 0));
    tmp.setProdBonus(jobj.optInt("prodBonus", 0));
    tmp.setHappinessBonus(jobj.optInt("happinesBonus", 0));
    tmp.setCredBonus(jobj.optInt("credBonus", 0));
    tmp.setHidden(jobj.optBoolean("hidden", false));
    tmp.setAwayTeam(jobj.optBoolean("awayTeam", false));

    return Optional.of(tmp);
  }

  @Override
  protected String valueIdGetter(final PlanetaryStatus value) {
    return value.getId();
  }

  @Override
  protected String typeNameGetter() {
    return "PlanetaryStatus";
  }
}