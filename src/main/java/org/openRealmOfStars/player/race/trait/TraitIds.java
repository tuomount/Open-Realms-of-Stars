package org.openRealmOfStars.player.race.trait;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
 * Copyright (C) 2023-2024 Tuomo Untinen
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
  /**
   * Race can gain sustenance from water.
   * If there is more water available than population require
   * population will grow.
   */
  public static final String PHOTOSYNTHESIS = "PHOTOSYNTHESIS";
  /** Gets +1 credit for each "trade" building and +50% from ship trading */
  public static final String MERCANTILE = "MERCANTILE";
  /** All ship design have 10% cloaking device built it. */
  public static final String STEALTHY = "STEALTHY";
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
  /** Assimilates non-robotic population after conquering a planet. */
  public static final String ASSIMILATION = "ASSIMILATION";
  /** Good war resilience. */
  public static final String GOOD_WAR_RESILIENCE = "GOOD_WAR_RESILIENCE";
  /** Excellent war resilience. */
  public static final String EXCELLENT_WAR_RESILIENCE =
      "EXCELLENT_WAR_RESILIENCE";
  /** Poor war resilience. */
  public static final String POOR_WAR_RESILIENCE = "POOR_WAR_RESILIENCE";
  /** Weak war resilience. */
  public static final String WEAK_WAR_RESILIENCE = "WEAK_WAR_RESILIENCE";
  /** Naturally charming, others just tend to like individuals of the race */
  public static final String NATURAL_CHARM = "NATURAL_CHARM";
  /** Be it because of bad looks or habits, others tend to dislike this race */
  public static final String REPULSIVE = "REPULSIVE";
  /** Race's habits/looks are unnaceptable to others, diplomacy is futile */
  public static final String DISGUSTING = "DISGUSTING";
  /** Unnaturally strong for it's size */
  public static final String STRONG = "STRONG";
  /** Race is weaker than usual */
  public static final String WEAK = "WEAK";
  /** Be it due to anatomy or cognitive abilities, race exhibits handiness */
  public static final String HANDY = "HANDY";
  /** Due to body anatomy or cognitive abilites, working is difficult */
  public static final String IMPRACTICAL = "IMPRACTICAL";
  /** Race is used to work in low gravity conditions. */
  public static final String LOW_GRAVITY_BEING = "LOW_GRAVITY_BEING";
  /** Race is used to work in high gravity conditions. */
  public static final String HIGH_GRAVITY_BEING = "HIGH_GRAVITY_BEING";
  /** Race is used to work in zero gravity conditions. */
  public static final String ZERO_GRAVITY_BEING = "ZERO_GRAVITY_BEING";
  /** Tolerate only very little radiation on planets. */
  public static final String TOLERATE_NO_RADIATION = "TOLERATE_NO_RADIATION";
  /** Tolerate high radiation on planets. */
  public static final String TOLERATE_HIGH_RADIATION =
      "TOLERATE_HIGH_RADIATION";
  /** Tolerate very high radiation on planets. */
  public static final String TOLERATE_EXTREME_RADIATION =
      "TOLERATE_EXTREME_RADIATION";
  /** Tolerate cold temperature on planets. */
  public static final String TOLERATE_COLD = "TOLERATE_COLD";
  /** Intolerate cold temperature on planets. */
  public static final String INTOLERATE_COLD = "INTOLERATE_COLD";
  /** Tolerate hot temperature on planets. */
  public static final String TOLERATE_HOT = "TOLERATE_HOT";
  /** Intolerate hot temperature on planets. */
  public static final String INTOLERATE_HOT = "INTOLERATE_HOT";
  /** Tolerate lava on planets. */
  public static final String TOLERATE_LAVA = "TOLERATE_LAVA";
  /** Communal, like to live together  */
  public static final String COMMUNAL = "COMMUNAL";
  /** Solitary, do not like to live together  */
  public static final String SOLITARY = "SOLITARY";
  /** Natural leaders, leaders are available a bit sooner. */
  public static final String NATURAL_LEADERS = "NATURAL_LEADERS";
  /** Quick learners, leaders gain twice faster experience. */
  public static final String QUICK_LEARNERS = "QUICK_LEARNERS";
  /** Slow learners, leaders gain only half experience. */
  public static final String SLOW_LEARNERS = "SLOW_LEARNERS";
  /**
   *  Race spreads only with uncrewed colonize ship which contains seeds
   *  to start new life at target planet.
   */
  public static final String SPORE_COLONIZATION = "SPORE_COLONIZATION";

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
