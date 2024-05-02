package org.openRealmOfStars.starMap.planet.construction;
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

import java.util.HashMap;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.utilities.DataLoader;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
 * Building factory
 */
public final class BuildingFactory {

  /** The Singleton */
  private static final BuildingFactory SINGLETON = new BuildingFactory();

  /** Map of all building definitons, with IDs as keys */
  private HashMap<String, Building> buildings;
  /** Tracks if the factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, Building> loader;

  /** Constructor */
  private BuildingFactory() {
    this.buildings = new HashMap<>();
    this.initialized = false;
    this.loader = new BuildingLoader();
  }

  /**
   * Create Building with specifed name
   * @param name Building name
   * @return Building or null if not found
   */
  public static Building createByName(final String name) {
    return SINGLETON.createByNameImpl(name).orElse(null);
  }

  /**
   * Create/Retrieve Building with given name, initialize factory if not yet
   * @param name Name of Building to retrieve
   * @return Building or empty
   */
  private Optional<Building> createByNameImpl(final String name) {
    if (!initialized) {
      init();
      this.initialized = true;
    }

    final var cachedTrait = buildings.get(name);
    return Optional.ofNullable(cachedTrait);
  }

  /** Initialize the factory */
  private void init() {
    final var dataFilesBase = "resources/data/buildings/";
    final String[] dataFiles = {
        "farm", "mine", "factory", "research",
        "culture", "credit", "military", "wildlife", "badones" };

    var loadedCount = loader.loadAll(buildings, dataFilesBase, dataFiles);
    ErrorLogger.log("Buildings loaded: " + loadedCount);
  }
}

/** Building JSON data loader */
class BuildingLoader extends DataLoader<String, Building> {

  @Override
  protected Optional<Building> parseFromJson(final JSONObject jobj) {
    try {
      // Mandatory fields
      final var name = jobj.getString("name");
      final var type = jobj.getEnum(BuildingType.class, "type");
      final var iconId = jobj.getString("iconId");

      var tmp = new Building(name, iconId, type);
      // Optional fields
      tmp.setProdCost(jobj.optInt("prodCost", 0));
      tmp.setMetalCost(jobj.optInt("metalCost", 0));
      tmp.setMaintenanceCost(jobj.optDouble("maintenanceCost", 0.0));
      tmp.setDescription(jobj.optString("description", ""));

      tmp.setFarmBonus(jobj.optInt("farmBonus", 0));
      tmp.setMineBonus(jobj.optInt("mineBonus", 0));
      tmp.setFactBonus(jobj.optInt("factBonus", 0));
      tmp.setCultBonus(jobj.optInt("cultBonus", 0));
      tmp.setReseBonus(jobj.optInt("reseBonus", 0));
      tmp.setCredBonus(jobj.optInt("credBonus", 0));
      tmp.setHappiness(jobj.optInt("happiness", 0));
      tmp.setMaterialBonus(jobj.optInt("materialBonus", 0));

      tmp.setBattleBonus(jobj.optInt("battleBonus", 0));
      tmp.setDefenseDamage(jobj.optInt("defenseDamage", 0));
      tmp.setScanRange(jobj.optInt("scanRange", 0));
      tmp.setScanCloakingDetection(jobj.optInt("scanCloakingDetection", 0));
      tmp.setFleetCapacityBonus(jobj.optInt("fleetCapacityBonus", 0));
      tmp.setRecycleBonus(jobj.optInt("recycleBonus", 0));
      tmp.setAncientArtifactResearch(
          jobj.optInt("ancientArtifactResearch", 0));
      tmp.setWildLifePower(jobj.optInt("wildLifePower", 0));

      // Flags
      tmp.setSingleAllowed(jobj.optBoolean("singleAllowed", false));
      tmp.setScientificAchievement(
          jobj.optBoolean("scientificAchievement", false));
      tmp.setBroadcaster(jobj.optBoolean("broadcaster", false));
      tmp.setOrbitalElevator(jobj.optBoolean("orbitalElevator", false));

      return Optional.of(tmp);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final Building value) {
    return value.getName();
  }

  @Override
  protected String typeNameGetter() {
    return "Building";
  }

}