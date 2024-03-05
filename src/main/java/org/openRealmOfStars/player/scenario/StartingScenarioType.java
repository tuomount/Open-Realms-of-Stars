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


/** Starting Scenarion type enumeration for Open Realm of Stars */
public enum StartingScenarioType {

  /** Regular start from single planet with few ships, these may vary
   * depending on water level, temperature and size. There can be small
   * variations also for tech levels. Starting from Sol also belongs this
   * type.
   */
  REGULAR,
  /**
   * Realm starts from deep space with more ships than regular. They have no
   * home world.
   */
  NO_HOME,
  /**
   * Realm starts without no ships, but their home planet has more buildings.
   */
  UTOPIA_WORLD;

  /**
   * Get starting scenario type as an integer value.
   * @return int
   */
  public int getIndex() {
    return this.ordinal();
  }

  /**
   * Get starting scenario type based on index.
   * @param index Index
   * @return StartingScenarioType
   */
  public static StartingScenarioType getByIndex(final int index) {
    if (index >= 0 && index < values().length) {
      return values()[index];
    }
    throw new IllegalArgumentException("Unknown starting scenario type");
  }

  /**
   * Get Starting Scenario Type as a String
   * @return String
   */
  public String getName() {
    switch (this) {
    case REGULAR: return "regular";
    case NO_HOME: return "no home";
    case UTOPIA_WORLD: return "utopia";
    default:
      throw new IllegalArgumentException("Unknown Starting scenario type");
  }
  }
  @Override
  public String toString() {
    switch (this) {
      case REGULAR: return "Regular start from single planet with ships";
      case NO_HOME: return "No starting planet, but lot's of ships";
      case UTOPIA_WORLD: return "Start from improved planet, but no ships.";
      default:
        throw new IllegalArgumentException("Unknown Starting scenario type");
    }
  }

  /**
   * Get StartingScenarioType by String.
   * @param value String
   * @return StartingScenarioType
   */
  public static StartingScenarioType getByString(final String value) {
    for (StartingScenarioType type : StartingScenarioType.values()) {
      if (type.getName().equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unexpected starting scenario type, "
        + value + ".");
  }
}
