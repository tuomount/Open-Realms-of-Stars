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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.starMap.planet.enums.WaterLevelType;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.DataLoader;

/** Starting scenario factory */
public final class StartingScenarioFactory {

  /** Random scenario ID. */
  public static final String RANDOM_ID = "RANDOM";
  /** Regular Random scenario ID. */
  public static final String REGULAR_RANDOM_ID = "REGULAR_RANDOM";
  /** The Singleton */
  private static final StartingScenarioFactory SINGLETON =
      new StartingScenarioFactory();

  /** StartingScenario this factory knows. IDs are used as keys. */
  private HashMap<String, StartingScenario> startingScenarios;
  /** Tracks if factory is initialized with data */
  private boolean initialized;
  /** JSON data loader */
  private DataLoader<String, StartingScenario> loader;
  /**
   * Create/Retrieve StartingScenario for given ID, if loaded
   * @param id StartingScenarioId
   * @return StartingScenario
   */
  public static StartingScenario create(final String id) {
    return SINGLETON.makeById(id);
  }

  /**
   * Create pseudo starting scenario for random.
   * @return Starting scenario.
   */
  public static StartingScenario createRandom() {
    return new StartingScenario(RANDOM_ID,
        StartingScenarioType.REGULAR, "Random");
  }
  /**
   * Create pseudo starting scenario for random.
   * @return Starting scenario.
   */
  public static StartingScenario createRandomOnlyRegular() {
    return new StartingScenario(REGULAR_RANDOM_ID,
        StartingScenarioType.REGULAR, "Random regular planets");
  }
  /**
   * Create default starting scenario for JUnits.
   * @return Starting scenario.
   */
  public static StartingScenario createDefault() {
    return create("TEMPERATE_HUMID_SIZE12");
  }
  /**
   * Get All starting scenario in array.
   * @return Starting scenario array
   */
  public static StartingScenario[] getValues() {
    return SINGLETON.getAll();
  }

  /**
   * Get All starting scenario in sorted array
   * @return Starting scenario array
   */
  public static StartingScenario[] getSorted() {
    ArrayList<StartingScenario> list = new ArrayList<>();
    for (StartingScenario scenario : SINGLETON.getAll()) {
      list.add(scenario);
    }
    list.sort((s1, s2) -> s1.getName().compareTo(s2.getName()));
    return list.toArray(new StartingScenario[0]);
  }
  /**
   * Get random starting scenario.
   * @return StartingScenario
   */
  public static StartingScenario pickRandomScenario() {
    return DiceGenerator.pickRandom(getValues());
  }
  /**
   * Create/Retrieve StartingScenario for given ID,
   * initialize factory if not yet
   * @param id Starting scenario ID to fetch
   * @return StartingScenario
   */
  private StartingScenario makeById(final String id) {
    if (!initialized) {
      initialized = true;
      init();
    }

    StartingScenario scenario = startingScenarios.get(id);
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
        "regular", "nohome", "utopia", "doomed"};
    final var startingScenariosLoaded = loader.loadAll(startingScenarios,
        basePath, files);
    ErrorLogger.log("Starting scenarios loaded: " + startingScenariosLoaded);
    if (!hasHardcodedScenarios()) {
      ErrorLogger.log("Some core startingScenarioIds were not loaded."
          + " Game might not work as expected!");
    }
  }

  /**
   * Check if all hardcoded starting scenarios are available.
   * @return True if all hardcoded starting scenarios are loaded
   */
  private static boolean hasHardcodedScenarios() {
    for (var scenarioId : ScenarioIds.getHardcodedIds()) {
      StartingScenario scenarioOpt = create(scenarioId);
      if (scenarioOpt == null) {
        return false;
      }
    }
    return true;
  }

  /**
   * Create/Retrieve all Starting scenarios, initialize factory if not yet
   * @return StartingScenario array
   */
  private StartingScenario[] getAll() {
    if (!initialized) {
      initialized = true;
      init();
    }
    return startingScenarios.values().toArray(new StartingScenario[0]);
  }
}

/** StartingScenario loader */
class StartingScenarioLoader extends DataLoader<String, StartingScenario> {

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
   * <li>StartingCredit : Number(Optional)
   * <li>Description : String</li>
   * </ul>
   * </p>
   * @param jobj JSONObject to parse StartingScenario from
   * @return Parsed StartingScenario or empty
   */
  @Override
  protected Optional<StartingScenario> parseFromJson(
      final JSONObject jobj) {
    try {
      final var id = jobj.getString("ID");
      final var type = jobj.getString("Type");
      final var name = jobj.getString("Name");
      StartingScenario tmp = new StartingScenario(id,
          StartingScenarioType.getByString(type), name);

      int numberOfScouts = jobj.getInt("NumberOfScouts");
      tmp.setNumberOfScouts(numberOfScouts);
      int numberOfColonyShips = jobj.getInt("NumberOfColonyShips");
      tmp.setNumberOfColonyShips(numberOfColonyShips);
      String waterLevel = jobj.optString("WaterLevel", "Humid");
      tmp.setWaterLevel(WaterLevelType.getByString(waterLevel));
      String temperature = jobj.optString("Temperature", "temperate");
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
      int startingCred = jobj.optInt("StartingCredit", 0);
      tmp.setStartingCredit(startingCred);
      String description = jobj.getString("Description");
      tmp.setDescription(description);
      return Optional.of(tmp);
    } catch (JSONException e) {
      ErrorLogger.log(e);
    }

    return Optional.empty();
  }

  @Override
  protected String valueIdGetter(final StartingScenario value) {
    return value.getId();
  }

  @Override
  protected String typeNameGetter() {
    return "StartingScenario";
  }
}

