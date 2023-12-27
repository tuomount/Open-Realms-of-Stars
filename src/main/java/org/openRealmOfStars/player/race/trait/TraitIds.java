package org.openRealmOfStars.player.race.trait;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
 * Copyright (C) 2023 Tuomo Untinen
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
 * Class holding constants with IDs of all {@link RaceTrait}s
 * that are officially hardcoded in game code.
 *
 * <p>
 * When RaceTrait dependency arises, ID of said RaceTrait
 * should be immediately written here as a constant.
 * There is a method that allows retrieval of all hardcoded trait IDs.
 * This method can be used for sanity checks at data-loading, etc.
 * </p>
 *
 * This class relies on Reflection for simple, error-free
 * addition of new trait IDs and their easy use in code,
 * while allowing listing of all hardcoded IDs.
 * TODO: Use code generation instead, the reflection is not needed for this
 */
public final class TraitIds {
  /** Robotic */
  public static final String ROBOTIC = "ROBOTIC";
  /**
   * Eats minerals.
   * Cannot be combined with ENERGY_POWERED.
   */
  public static final String LITHOVORIC = "LITHOVORIC";
  /**
   * Eats energy (credits).
   * Cannot be combined with LITHOVORIC.
   */
  public static final String ENERGY_POWERED = "ENERGY_POWERED";
  /** Concept of parental heritage is not applicable for the race. */
  public static final String NO_HEIRS = "NO_HEIRS";
  /** Race can breed by explictly constructing it's own population. */
  public static final String CONSTRUCTED_POP = "CONSTRUCTED_POP";
  /**
   * Race can gain sustenance from radiation.
   * This does NOT mean that it able to *produce* food,
   * but rather that it can *sustain itself* with radiation.
   */
  public static final String RADIOSYNTHESIS = "RADIOSYNTHESIS";
  /** Gets +1 credit for each "trade" building and +50% from ship trading */
  public static final String MERCANTILE = "MERCANTILE";

  /** All ship design have 10% cloaking device built it. */
  public static final String BUILT_IN_CLOAKING_DEVICE =
      "BUILT_IN_CLOAKING_DEVICE";

  /** Require 50% less food to survive. */
  public static final String EAT_LESS = "EAT_LESS";

  /** Fast population growth. */
  public static final String FAST_GROWTH = "FAST_GROWTH";

  /** Slow population growth. */
  public static final String SLOW_GROWTH = "SLOW_GROWTH";

  /** Fixed population growth. */
  public static final String FIXED_GROWTH = "FIXED_GROWTH";

  /** Limited population growth. */
  public static final String LIMITED_GROWTH = "LIMITED_GROWTH";

  /** Credit Rush for projects */
  public static final String CREDIT_RUSH = "CREDIT_RUSH";
  /** Population Rush for projects */
  public static final String POPULATION_RUSH = "POPULATION_RUSH";
  /** Slow culture speed for artists. */
  public static final String SLOW_CULTURE = "SLOW_CULTURE";
  /** Fast culture speed for artists. */
  public static final String FAST_CULTURE = "FAST_CULTURE";
  /** Slow research speed for scientist. */
  public static final String SLOW_RESEARCH = "SLOW_RESEARCH";
  /** Fast research speed for scientist. */
  public static final String FAST_RESEARCH = "FAST_RESEARCH";
  /** Very fast research speed for scientist. */
  public static final String VERY_FAST_RESEARCH = "VERY_FAST_RESEARCH";
  /** Short life span. */
  public static final String SHORT_LIFE_SPAN = "SHORT_LIFE_SPAN";
  /** Long life span. */
  public static final String LONG_LIFE_SPAN = "LONG_LIFE_SPAN";
  /** Very long life span. */
  public static final String VERY_LONG_LIFE_SPAN = "VERY_LONG_LIFE_SPAN";
  /** Cyborg life span. */
  public static final String CYBORG_LIFE_SPAN = "CYBORG_LIFE_SPAN";
  /** Massive size */
  public static final String MASSIVE_SIZE = "MASSIVE_SIZE";
  /** Slow metabolism */
  public static final String SLOW_METABOLISM = "SLOW_METABOLISM";
  /** Fast food production */
  public static final String FAST_FOOD_PROD = "FAST_FOOD_PROD";
  /** Good war resilience. */
  public static final String GOOD_WAR_RESILIENCE = "GOOD_WAR_RESILIENCE";
  /** Excellent war resilience. */
  public static final String EXCELLENT_WAR_RESILIENCE =
      "EXCELLENT_WAR_RESILIENCE";
  /** Poor war resilience. */
  public static final String POOR_WAR_RESILIENCE = "POOR_WAR_RESILIENCE";
  /** Weak war resilience. */
  public static final String WEAK_WAR_RESILIENCE = "WEAK_WAR_RESILIENCE";

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
    final var idList = Arrays.stream(TraitIds.class.getDeclaredFields())
        .filter(field -> Modifier.isPublic(field.getModifiers()))
        .filter(field -> Modifier.isStatic(field.getModifiers()))
        .map(field -> field.getName())
        .collect(Collectors.toList());
    hardcodedIds = idList;
    return idList;
  }

  /** Hidden constructor */
  private TraitIds() {
  }

}
