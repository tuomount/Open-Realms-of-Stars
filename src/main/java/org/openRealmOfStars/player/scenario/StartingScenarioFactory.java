package org.openRealmOfStars.player.scenario;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;
import org.openRealmOfStars.utilities.DataLoader;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;

/** Starting scenario factory */
public final class StartingScenarioFactory {

  /** The Singleton */
  private static final StartingScenarioFactory SINGLETON =
      new StartingScenarioFactory();

  /** StartingScenario this factory knows. IDs are used as keys. */
  private HashMap<String, StartingScenarioClass> startingScenarios;
  /** Tracks if factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, StartingScenarioClass> loader;
  /**
   * Create/Retrieve StartingScenario for given ID, if loaded
   * @param id StartingScenarioId
   * @return StartingScenario
   */
  public static StartingScenarioClass create(final String id) {
    return SINGLETON.makeById(id);
  }

  /**
   * Get All starting scenario in array.
   * @return Starting scenario array
   */
  public static StartingScenarioClass[] getValues() {
    return SINGLETON.getAll();
  }

  /**
   * Get random staring scenario.
   * @return StartingScenario
   */
  public static StartingScenarioClass getRandomRace() {
    return DiceGenerator.pickRandom(getValues());
  }
  /**
   * Create/Retrieve StartingScenario for given ID,
   * initialize factory if not yet
   * @param id Starting scenario ID to fetch
   * @return StartingScenario
   */
  private StartingScenarioClass makeById(final String id) {
    if (!initialized) {
      initialized = true;
      init();
    }

    StartingScenarioClass scenario = startingScenarios.get(id);
    if (scenario == null) {
        throw new IllegalArgumentException(
            "Starting scenario factory does not contain "
            + id + ".");
    }
    return scenario;
  }

  /** Contructor */
  private StartingScenarioFactory() {
    this.startingScenarios = new HashMap<>();
    this.initialized = false;
    this.loader = new StartingScenarioLoader();
  }

  /** (Re)Initialize the factory */
  private void init() {
    startingScenarios.clear();
    final var basePath = "resources/data/scenarios/";
    final String[] files = {
        "regular", "nohome", "utopia"};
    final var startingScenariosLoaded = loader.loadAll(startingScenarios,
        basePath, files);
    ErrorLogger.log("Starting scenarios loaded: " + startingScenariosLoaded);
  }

  /**
   * Create/Retrieve all Starting scenarios, initialize factory if not yet
   * @return StartingScenario array
   */
  private StartingScenarioClass[] getAll() {
    if (!initialized) {
      initialized = true;
      init();
    }
    return startingScenarios.values().toArray(new StartingScenarioClass[0]);
  }
}

/** StartingScenario loader */
class StartingScenarioLoader extends DataLoader<String, StartingScenarioClass> {

  /**
   * Parse Starting scenario from a JSON file.
   * <p>
   * JSON Starting Scenario object must have following format:<ul>
   * <li>ID : String</li>
   * <li>Type : String</li>
   * <li>Name : String</li>
   * <li>NumberOfScouts: Number</li>
   * <li>NumberOfColonyShips: Number</li>
   * <li>WaterLevel : String(OPTIONAL)</li>
   * <li>Temperature : String(OPTIONAL)</li>
   * <li>PlanetSize : Number(OPTIONAL)</li>
   * <li>Population : Number(OPTIONAL)</li>
   * <li>ColonyPop : Number(OPTIONAL)</li>
   * <li>WorldType : String(OPTIONAL)</li>
   * <li>Buildings: List of buildings (OPTIONAL)</li>
   * <li>Tech : List of extra technology (OPTIONAL)</li>
   * <li>Description : String</li>
   * </ul>
   * </p>
   * @param jobj JSONObject to parse StartingScenario from
   * @return Parsed StartingScenario or empty
   */
  @Override
  protected Optional<StartingScenarioClass> parseFromJson(
      final JSONObject jobj) {
    try {
      final var id = jobj.getString("ID");
      final var type = jobj.getString("Type");
      final var name = jobj.getString("Name");
      StartingScenarioClass tmp = new StartingScenarioClass(id,
          StartingScenarioType.getByString(type), name);

      int numberOfScouts = jobj.getInt("NumberOfScouts");
      tmp.setNumberOfScouts(numberOfScouts);
      int numberOfColonyShips = jobj.getInt("NumberOfColonyShips");
      tmp.setNumberOfColonyShips(numberOfColonyShips);
      String waterLevel = jobj.optString("WaterLevel", "Humid");
      tmp.setWaterLevel(WaterLevelType.getByString(waterLevel));
      String temperature = jobj.optString("Temperature", "");
      tmp.setTemperature(TemperatureType.getByString(temperature));
      int size = jobj.optInt("PlanetSize", 12);
      tmp.setPlanetSize(size);
      int population = jobj.optInt("Population", 3);
      tmp.setPopulation(population);
      int colonyPop = jobj.optInt("ColonyPop", 0);
      tmp.setColonyPop(colonyPop);
      var jsonTechs = jobj.optJSONArray("Tech", new JSONArray());
      for (int i = 0; i < jsonTechs.length(); i++) {
        String techName = jsonTechs.getString(i);
        tmp.addTech(techName);
      }
      var jsonBuildings = jobj.optJSONArray("Buildings", new JSONArray());
      for (int i = 0; i < jsonBuildings.length(); i++) {
        String buildingName = jsonBuildings.getString(i);
        tmp.addBuilding(buildingName);
      }
      String description = jobj.getString("Description");
      tmp.setDescription(description);
      return Optional.of(tmp);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final StartingScenarioClass value) {
    return value.getId();
  }

  @Override
  protected String typeNameGetter() {
    return "StartingScenario";
  }
}

