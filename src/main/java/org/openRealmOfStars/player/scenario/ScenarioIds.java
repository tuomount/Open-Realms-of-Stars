package org.openRealmOfStars.player.scenario;
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

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing hardcoded IDs of {@link StartingScenario}s,
 * that are used in game code.
 *
 * <p>
 * When StartingScenario dependency arises, it's ID
 * should be immediately written here as a constant.
 * There is a method that allows retrieval of all hardcoded IDs.
 * This method can be used for sanity checks at data-loading, etc.
 * </p>
 *
 * This class relies on Reflection for simple, error-free
 * addition of new IDs and their easy use in code,
 * while allowing listing of all hardcoded IDs.
 * TODO: Use code generation instead, the reflection is not needed for this
 *
 */
public final class ScenarioIds {

  /** Destroyed Home planet. */
  public static final String DESTROYED_HOME_PLANET = "DESTROYED_HOME_PLANET";

  /** Realm is from another galaxy. */
  public static final String FROM_ANOTHER_GALAXY = "FROM_ANOTHER_GALAXY";

  /** Regular start, TEMPERATE_HUMID_SIZE12 */
  public static final String TEMPERATE_HUMID_SIZE12 = "TEMPERATE_HUMID_SIZE12";

  /** Regular start, Start from Earth */
  public static final String EARTH = "EARTH";

  /** Regular start, TEMPERATE_ARID_SIZE12 */
  public static final String TEMPERATE_ARID_SIZE12 = "TEMPERATE_ARID_SIZE12";

  /** Regular start, TEMPERATE_MARINE_SIZE9 */
  public static final String TEMPERATE_MARINE_SIZE9 = "TEMPERATE_MARINE_SIZE9";

  /** Regular start, TEMPERATE_MARINE_SIZE14 */
  public static final String TEMPERATE_MARINE_SIZE14 =
      "TEMPERATE_MARINE_SIZE14";

  /** Regular start, COLD_HUMID_SIZE12 */
  public static final String COLD_HUMID_SIZE12 = "COLD_HUMID_SIZE12";

  /** Regular start, TROPICAL_HUMID_SIZE12 */
  public static final String TROPICAL_HUMID_SIZE12 = "TROPICAL_HUMID_SIZE12";

  /** Regular start, HOT_ARID_SIZE12 */
  public static final String HOT_ARID_SIZE12 = "HOT_ARID_SIZE12";

  /** Utopia start, no ships but planet of farms. */
  public static final String FARMING_PLANET = "FARMING_PLANET";

  /** Utopia start from artifical planet, no ships. */
  public static final String METAL_PLANET = "METAL_PLANET";

  /** Utopia start, no ships but planet of factories and mines. */
  public static final String PROD_PLANET = "PROD_PLANET";

  /** Doomed start, bankruptcy with bad factories and mines. */
  public static final String LEAKING_PROD = "LEADKING_PROD";
  /** Doomed start, Planet turns into volcanic world. */
  public static final String VOLCANIC_DISASTER = "VOLCANIC_DISASTER";
  /** Utopia, Trade planet. */
  public static final String TRADE_PLANET = "TRADE_PLANET";

  /** List storing all hardcoded IDs. Populated at runtime, via reflection. */
  private static List<String> hardcodedIds = null;

  /**
   * Get List of all hardcoded IDs
   * @return List of all hardcoded IDs
   */
  public static List<String> getHardcodedIds() {
    if (hardcodedIds != null) {
      return hardcodedIds;
    }

    // Use class reflection to get all public static
    // fields of this class, then cache the result.
    final var idList = Arrays.stream(ScenarioIds.class.getDeclaredFields())
        .filter(field -> Modifier.isPublic(field.getModifiers()))
        .filter(field -> Modifier.isStatic(field.getModifiers()))
        .map(field -> field.getName())
        .collect(Collectors.toList());
    hardcodedIds = idList;
    return idList;
  }

  /** Hidden constructor */
  private ScenarioIds() {
  }
}

