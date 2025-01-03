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

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class holding constants with IDs of all {@link GovTrait}s
 * that are officially hardcoded in game code.
 *
 * <p>
 * When Government Trait dependency arises, ID of said Goverment Trait
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
public final class GovTraitIds {
  /** Diplomatic +2 diplomacy */
  public static final String DIPLOMATIC = "DIPLOMATIC";
  /** Political +1 diplomacy. */
  public static final String POLITICAL = "POLITICAL";
  /** Rude -1 diplomacy */
  public static final String RUDE = "RUDE";
  /** Mercantile. */
  public static final String MERCANTILE = "MERCANTILE";
  /** Business oriented */
  public static final String BUSINESS_ORIENTED = "BUSINESS_ORIENTED";
  /** Industrial */
  public static final String INDUSTRIAL = "INDUSTRIAL";
  /** Mining focused goverment */
  public static final String MINING_ORIENTED = "MINING_ORIENTED";
  /** Research oriented */
  public static final String RESEARCH_ORIENTED = "RESEARCH_ORIENTED";
  /** Technology focused */
  public static final String TECHNOLOGY_FOCUSED = "TECHNOLOGY_FOCUSED";
  /** War happy, being at war gives one happiness bonus */
  public static final String WAR_HAPPY = "WAR_HAPPY";
  /** Single minded, immune to happiness */
  public static final String SINGLE_MIND = "SINGLE_MIND";
  /** Population rush */
  public static final String POPULATION_RUSH = "POPULATION_RUSH";
  /** Credit rush. */
  public static final String CREDIT_RUSH = "CREDIT_RUSH";
  /** Armed freighters. */
  public static final String ARMED_FREIGHTERS = "ARMED_FREIGHTERS";
  /** Xeno phobic. */
  public static final String XENO_PHOBIC = "XENO_PHOBIC";
  /** Governor happiness */
  public static final String GOVERNOR_HAPPINESS = "GOVERNOR_HAPPINESS";
  /** Ruler reign period is short */
  public static final String REIGN_SHORT = "REIGN_SHORT";
  /** Ruler reign period is long */
  public static final String REIGN_LONG = "REIGN_LONG";
  /** Ruler reign period is life time */
  public static final String REIGN_LIFE_TIME = "REIGN_LIFE_TIME";
  /** Generally happy */
  public static final String HAPPY = "HAPPY";
  /** Generally unhappy */
  public static final String UNHAPPY = "UNHAPPY";
  /** Extra happy */
  public static final String EXTRA_HAPPY = "EXTRA_HAPPY";
  /** Weakness for war */
  public static final String WEAKNESS_FOR_WAR = "WEAKNESS_FOR_WAR";
  /** Tolerant for war */
  public static final String TOLERANT_FOR_WAR = "TOLERANT_FOR_WAR";
  /** Endure war */
  public static final String ENDURE_WAR = "ENDURE_WAR";
  /** Culture bonus */
  public static final String CULTURE_ORIENTED = "CULTURE_ORIENTED";
  /** Agricultural oriented */
  public static final String AGRICULTURAL_ORIENTED = "AGRICULTURAL_ORIENTED";
  /** Tax oriented */
  public static final String TAX_ORIENTED = "TAX_ORIENTED";
  /** Very low fleet capacity */
  public static final String VERY_LOW_FLEET_CAPACITY =
      "VERY_LOW_FLEET_CAPACITY";
  /** Low fleet capacity */
  public static final String LOW_FLEET_CAPACITY =
      "LOW_FLEET_CAPACITY";
  /** High fleet capacity */
  public static final String HIGH_FLEET_CAPACITY =
      "HIGH_FLEET_CAPACITY";
  /** Low leader pool size */
  public static final String LOW_LEADER_POOL_SIZE =
      "LOW_LEADER_POOL_SIZE";
  /** High leader pool size */
  public static final String HIGH_LEADER_POOL_SIZE =
      "HIGH_LEADER_POOL_SIZE";
  /** Very high leader pool size */
  public static final String VERY_HIGH_LEADER_POOL_SIZE =
      "VERY_HIGH_LEADER_POOL_SIZE";
  /** Low leader hiring cost */
  public static final String LOW_LEADER_HIRING_COST =
      "LOW_LEADER_HIRING_COST";
  /** High leader hiring cost */
  public static final String HIGH_LEADER_HIRING_COST =
      "HIGH_LEADER_HIRING_COST";
  /** Very low leader hiring cost */
  public static final String VERY_LOW_LEADER_HIRING_COST =
      "VERY_LOW_LEADER_HIRING_COST";
  /** Chance for heir born */
  public static final String CHANCE_FOR_HEIR_BORN =
      "CHANCE_FOR_HEIR_BORN";
  /** Ruler assasination */
  public static final String RULER_ASSASINATION =
      "RULER_ASSASINATION";
  /** High corruption */
  public static final String HIGH_CORRUPTION =
      "HIGH_CORRUPTION";
  /** Low corruption */
  public static final String LOW_CORRUPTION =
      "LOW_CORRUPTION";

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
    final var idList = Arrays.stream(GovTraitIds.class.getDeclaredFields())
        .filter(field -> Modifier.isPublic(field.getModifiers()))
        .filter(field -> Modifier.isStatic(field.getModifiers()))
        .map(field -> field.getName())
        .collect(Collectors.toList());
    hardcodedIds = idList;
    return idList;
  }

  /** Hidden constructor */
  private GovTraitIds() {
  }

}
