package org.openRealmOfStars.starMap;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 Tuomo Untinen
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

import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.WeightedList;

/**
*
* Sun type
*
*/
public enum SunType {
  /** Red star with least radiation. */
  RED_STAR,
  /** Blue star with most radiation. */
  BLUE_STAR,
  /** Yellow star with medium radiation. */
  YELLOW_STAR;

  /**
   * Get random sun type.
   * @return Suntype
   */
  public static SunType getRandomType() {
    int value = DiceGenerator.getRandom(0, 2);
    switch (value) {
      default:
      case 0: return RED_STAR;
      case 1: return BLUE_STAR;
      case 2: return YELLOW_STAR;
    }
  }

  /** Weighted list of radiation strength probabilities for red suns */
  private static final WeightedList<RadiationType> RED_SUN_RAD =
      new WeightedList<>(
      new double[] {
          6, 3, 1
      },
      new RadiationType[] {
          RadiationType.NO_RADIATION, RadiationType.LOW_RADIATION,
          RadiationType.HIGH_RADIATION
      });
  /** Weighted list of radiation strength probabilities for blue suns */
  private static final WeightedList<RadiationType> BLUE_SUN_RAD =
      new WeightedList<>(
      new double[] {
          6, 3, 1
      },
      new RadiationType[] {
          RadiationType.VERY_HIGH_RAD, RadiationType.HIGH_RADIATION,
          RadiationType.LOW_RADIATION
      });
  /** Weighted list of radiation strength probabilities for yellow suns */
  private static final WeightedList<RadiationType> YLW_SUN_RAD =
      new WeightedList<>(
      new double[] {
          3, 4, 2, 1
      },
      new RadiationType[] {
          RadiationType.NO_RADIATION, RadiationType.LOW_RADIATION,
          RadiationType.HIGH_RADIATION, RadiationType.VERY_HIGH_RAD
      });
  /** Weighted list of temperature probabilities for red suns */
  private static final WeightedList<TemperatureType> RED_SUN_TEMP =
      new WeightedList<>(
      new double[] {
          2, 3, 4, 3, 2, 1,
      },
      new TemperatureType[] {
          TemperatureType.FROZEN, TemperatureType.ARCTIC, TemperatureType.COLD,
          TemperatureType.TEMPERATE, TemperatureType.TROPICAL,
          TemperatureType.VOLCANIC
      });

  /** Weighted list of temperature probabilities for red suns */
  private static final WeightedList<TemperatureType> YELLOW_SUN_TEMP =
      new WeightedList<>(
      new double[] {
          1, 2, 3, 4, 3, 2, 1
      },
      new TemperatureType[] {
          TemperatureType.FROZEN, TemperatureType.ARCTIC, TemperatureType.COLD,
          TemperatureType.TEMPERATE, TemperatureType.TROPICAL,
          TemperatureType.VOLCANIC, TemperatureType.INFERNO
      });
  /** Weighted list of temperature probabilities for red suns */
  private static final WeightedList<TemperatureType> BLUE_SUN_TEMP =
      new WeightedList<>(
      new double[] {
          1, 2, 3, 4, 3, 2
      },
      new TemperatureType[] {
          TemperatureType.ARCTIC, TemperatureType.COLD,
          TemperatureType.TEMPERATE, TemperatureType.TROPICAL,
          TemperatureType.VOLCANIC, TemperatureType.INFERNO
      });

  /**
   * Get radiation for planet based on sun type
   * @param sunType 0 - red star, 1 - blue star, 2 - yellow star
   * @return RadiationType
   */
  static RadiationType getRadiation(final SunType sunType) {
    RadiationType radiation = RadiationType.NO_RADIATION;

    // Red star aka SUN
    if (sunType == RED_STAR) {
      radiation = RED_SUN_RAD.pickRandom();
    }
    // Blue star
    if (sunType == BLUE_STAR) {
      radiation = BLUE_SUN_RAD.pickRandom();
    }
    // Yellow star
    if (sunType == YELLOW_STAR) {
      radiation = YLW_SUN_RAD.pickRandom();
    }

    return radiation;
  }

  /**
   * Get radiation for planet based on sun type
   * @param sunType 0 - red star, 1 - blue star, 2 - yellow star
   * @return Radiation level 1-10
   */
  static TemperatureType getTemperature(final SunType sunType) {
    TemperatureType temperature = TemperatureType.TEMPERATE;

    // Red star aka SUN
    if (sunType == RED_STAR) {
      temperature = RED_SUN_TEMP.pickRandom();
    }
    // Blue star
    if (sunType == BLUE_STAR) {
      temperature = BLUE_SUN_TEMP.pickRandom();
    }
    // Yellow star
    if (sunType == YELLOW_STAR) {
      temperature = YELLOW_SUN_TEMP.pickRandom();
    }

    return temperature;
  }

}
