package org.openRealmOfStars.starMap.event;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2019 Tuomo Untinen
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

/**
 * Enumeration of karma-based event target selection methods.
 * TODO: Replace with interface + classes that define karma target selection
 */
public enum KarmaType {
  /** Karma is disabled. */
  DISABLED,
  /**
   * Realm with most score will get bad karma effects,
   * Realm with lowest score will get good karma effect
   */
  FIRST_AND_LAST,
  /**
   * Two realms with most scores will get bad karma effects,
   * Two realms with lowest scores will get good karma effect
   */
  SECOND_FIRST_AND_LAST,
  /**
   * Realms with most scores will get bad karma effects,
   * Realms with lowest scores will get good karma effect
   */
  BALANCED,
  /** Bad and good karma effect can happen any realm. */
  RANDOM,
  /** Only good events happen on last half realms. */
  ONLY_GOODS_FOR_LAST,
  /** Good events can happen to any realm. */
  RANDOM_GOOD_ONES;

  /**
   * Get karma type as integer
   * @return integer
   */
  public int getIndex() {
    switch (this) {
      case DISABLED:
        return 0;
      case FIRST_AND_LAST:
        return 1;
      default:
      case SECOND_FIRST_AND_LAST:
        return 2;
      case BALANCED:
        return 3;
      case RANDOM:
        return 4;
      case ONLY_GOODS_FOR_LAST:
        return 5;
      case RANDOM_GOOD_ONES:
        return 6;
    }
  }

  /**
   * Get karma type by integer
   * @param value Integer value
   * @return Karma type
   */
  public static KarmaType getTypeByInt(final int value) {
    switch (value) {
      case 0:
        return DISABLED;
      case 1:
        return FIRST_AND_LAST;
      default:
      case 2:
        return SECOND_FIRST_AND_LAST;
      case 3:
        return BALANCED;
      case 4:
        return RANDOM;
      case 5:
        return ONLY_GOODS_FOR_LAST;
      case 6:
        return RANDOM_GOOD_ONES;
    }
  }

}
