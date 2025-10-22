package org.openRealmOfStars.starMap.planet.status;
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


/**
 * Timed planetary status types. This tells when timed planetary status is
 * activated and starts counting time to zero. When count enters zero, status
 * became applied.
 *
 */
public enum TimedStatusType {

  /** Timed from start of the game. This means star year 2400. */
  GAME_START,
  /** Timed from after colonization. */
  AFTER_COLONIZATION,
  /** Timed from after colonization or immediately after away team visit */
  AFTER_COLONIZATION_OR_AWAY_TEAM,
  /** Timed after some special event has activated. */
  SPECIAL_EVENT;

  /**
   * Get index for TimedStatusType
   * @return integer
   */
  public int getIndex() {
    switch (this) {
    default:
    case GAME_START: return 0;
    case AFTER_COLONIZATION: return 1;
    case AFTER_COLONIZATION_OR_AWAY_TEAM: return 2;
    case SPECIAL_EVENT: return 3;
    }
  }

  /**
   * Get TimedStatusType based in index.
   * @param index Index.
   * @return TimedStatusType
   */
  public static TimedStatusType getByIndex(final int index) {
    switch (index) {
    default:
    case 0: return GAME_START;
    case 1: return AFTER_COLONIZATION;
    case 2: return AFTER_COLONIZATION_OR_AWAY_TEAM;
    case 3: return SPECIAL_EVENT;
    }
  }
}
