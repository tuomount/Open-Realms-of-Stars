package org.openRealmOfStars.player.ship;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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

import org.openRealmOfStars.player.race.SpaceRace;

/**
 * Ship hull factory
 */
public final class ShipHullFactory {

  /**
   * Hiding the constructor
   */
  private ShipHullFactory() {
    // Nothing to do
  }
  /**
   * Current maximum ShipHull for whole game.
   * Remember to increase this when new ship hull is added to game.
   * It should be one bigger than last index.
   */
  private static final int MAX_SHIPHULL = 44;

  /**
   * Create ShipHull with matching name
   * @param name Ship hull name
   * @param race Space race whom built the ship
   * @return ShipHull or null if not found
   */
  public static ShipHull createByName(final String name, final SpaceRace race) {
    ShipHull tmp = null;
    for (int i = 0; i < MAX_SHIPHULL; i++) {
      tmp = createShipHull(i, race);
      if (tmp != null && tmp.getName().equalsIgnoreCase(name)) {
        return tmp;
      }
    }
    return null;
  }

  /**
   * Hull for Scout Mk1
   */
  public static final int HULL_SCOUT_MK1 = 0;

  /**
   * Hull for Destroyer Mk1
   */
  public static final int HULL_DESTROYER_MK1 = 1;

  /**
   * Hull for Colony
   */
  public static final int HULL_COLONY = 2;

  /**
   * Hull for Probe
   */
  public static final int HULL_PROBE = 3;

  /**
   * Hull for Small freighter
   */
  public static final int HULL_SMALL_FREIGHTER = 4;

  /**
   * Hull for Small starbase Mk1
   */
  public static final int HULL_SMALL_STARBASE_MK1 = 5;

  /**
   * Hull for Destroyer Mk2
   */
  public static final int HULL_DESTROYER_MK2 = 6;

  /**
   * Hull for Small starbase Mk2
   */
  public static final int HULL_SMALL_STARBASE_MK2 = 7;

  /**
   * Hull for Corvette Mk1
   */
  public static final int HULL_CORVETTE_MK1 = 8;

  /**
   * Hull for Medium freighter
   */
  public static final int HULL_MEDIUM_FREIGHTER = 9;

  /**
   * Hull for Medium starbase
   */
  public static final int HULL_MEDIUM_STARBASE = 10;

  /**
   * Hull for Scout Mk2
   */
  public static final int HULL_SCOUT_MK2 = 11;

  /**
   * Hull for Cruiser
   */
  public static final int HULL_CRUISER = 12;

  /**
   * Hull for Battleship Mk1
   */
  public static final int HULL_BATTLESHIP_MK1 = 13;

  /**
   * Hull for Privateer Mk1
   */
  public static final int HULL_PRIVATEER_MK1 = 14;

  /**
   * Hull for Large freighter
   */
  public static final int HULL_LARGE_FREIGHTER = 15;

  /**
   * Hull for Large starbase
   */
  public static final int HULL_LARGE_STARBASE = 16;

  /**
   * Hull for Corvette Mk2
   */
  public static final int HULL_CORVETTE_MK2 = 17;

  /**
   * Hull for Battle cruiser Mk1
   */
  public static final int HULL_BATTLE_CRUISER_MK1 = 18;

  /**
   * Hull for Privateer Mk2
   */
  public static final int HULL_PRIVATEER_MK2 = 19;

  /**
   * Hull for Scout Mk3
   */
  public static final int HULL_SCOUT_MK3 = 20;

  /**
   * Hull for Massive freighter
   */
  public static final int HULL_MASSIVE_FREIGHTER = 21;

  /**
   * Hull for Massive starbase
   */
  public static final int HULL_MASSIVE_STARBASE = 22;

  /**
   * Hull for Corvette Mk3
   */
  public static final int HULL_CORVETTE_MK3 = 23;

  /**
   * Hull for Destroyer Mk3
   */
  public static final int HULL_DESTROYER_MK3 = 24;

  /**
   * Hull for Battleship Mk2
   */
  public static final int HULL_BATTLESHIP_MK2 = 25;

  /**
   * Hull for Privateer Mk3
   */
  public static final int HULL_PRIVATEER_MK3 = 26;

  /**
   * Hull for Battle cruiser Mk2
   */
  public static final int HULL_BATTLE_CRUISER_MK2 = 27;

  /**
   * Hull for Scout Mk4
   */
  public static final int HULL_SCOUT_MK4 = 28;

  /**
   * Hull for Destroyer Mk4
   */
  public static final int HULL_DESTROYER_MK4 = 29;

  /**
   * Hull for Capital ship
   */
  public static final int HULL_CAPITAL_SHIP = 30;

  /**
   * Hull for Corvette Mk4
   */
  public static final int HULL_CORVETTE_MK4 = 31;

  /**
   * Hull for Artificial planet
   */
  public static final int HULL_ARTICIAL_PLANET = 32;

  /**
   * Hull for small orbital
   */
  public static final int HULL_SMALL_ORBITAL = 33;

  /**
   * Hull for medium orbital
   */
  public static final int HULL_MEDIUM_ORBITAL = 34;
  /**
   * Hull for minor orbital
   */
  public static final int HULL_MINOR_ORBITAL = 35;
  /**
   * Hull for large orbital
   */
  public static final int HULL_LARGE_ORBITAL = 36;
  /**
   * Hull for massive orbital
   */
  public static final int HULL_MASSIVE_ORBITAL = 37;
  /**
   * Hull for space worm
   */
  public static final int HULL_SPACE_WORM = 38;
  /**
   * Hull for Kraken
   */
  public static final int HULL_KRAKEN = 39;
  /**
   * Hull for Large kraken
   */
  public static final int HULL_LARGE_KRAKEN = 40;
  /**
   * Hull for Probe Mk2
   */
  public static final int HULL_PROBE_MK2 = 41;
  /**
   * Hull for Probe Mk3
   */
  public static final int HULL_PROBE_MK3 = 42;
  /**
   * Hull for Spore
   */
  public static final int HULL_SPORE = 43;

  /**
   * Create ship hull with index
   * @param index For creating a new ship hull
   * @param race Space race whom built the ship
   * @return ShipHull if index found otherwise null
   */
  public static ShipHull createShipHull(final int index, final SpaceRace race) {
    ShipHull tmp = null;
    if (index == HULL_SCOUT_MK1) {
      tmp = new ShipHull(index, "Scout Mk1", 4, 1, ShipHullType.NORMAL,
          ShipSize.SMALL, 5, 5, race);
      tmp.setFleetCapacity(0.1);
      tmp.setImageIndex(ShipImage.SCOUT);
      return tmp;
    }
    if (index == HULL_DESTROYER_MK1) {
      tmp = new ShipHull(index, "Destroyer Mk1", 6, 1, ShipHullType.NORMAL,
          ShipSize.MEDIUM, 6, 8, race);
      tmp.setFleetCapacity(0.2);
      tmp.setImageIndex(ShipImage.DESTROYER);
      return tmp;
    }
    if (index == HULL_COLONY) {
      tmp = new ShipHull(index, "Colony", 4, 1, ShipHullType.FREIGHTER,
          ShipSize.MEDIUM, 4, 6, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.COLONY);
      return tmp;
    }
    if (index == HULL_PROBE) {
      tmp = new ShipHull(index, "Probe", 4, 1, ShipHullType.PROBE,
          ShipSize.SMALL, 2, 2, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.PROBE);
      return tmp;
    }
    if (index == HULL_SMALL_FREIGHTER) {
      tmp = new ShipHull(index, "Small freighter", 6, 1, ShipHullType.FREIGHTER,
          ShipSize.MEDIUM, 4, 6, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.SMALL_FREIGHTER);
      return tmp;
    }
    if (index == HULL_SMALL_STARBASE_MK1) {
      tmp = new ShipHull(index, "Small starbase Mk1", 6, 1,
          ShipHullType.STARBASE, ShipSize.SMALL, 6, 6, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.SMALL_STARBASE);
      return tmp;
    }
    if (index == HULL_DESTROYER_MK2) {
      tmp = new ShipHull(index, "Destroyer Mk2", 6, 2, ShipHullType.NORMAL,
          ShipSize.MEDIUM, 6, 10, race);
      tmp.setFleetCapacity(0.2);
      tmp.setImageIndex(ShipImage.DESTROYER);
      return tmp;
    }
    if (index == HULL_SMALL_STARBASE_MK2) {
      tmp = new ShipHull(index, "Small starbase Mk2", 6, 2,
          ShipHullType.STARBASE, ShipSize.SMALL, 6, 8, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.SMALL_STARBASE);
      return tmp;
    }
    if (index == HULL_CORVETTE_MK1) {
      tmp = new ShipHull(index, "Corvette Mk1", 5, 1, ShipHullType.NORMAL,
          ShipSize.SMALL, 5, 6, race);
      tmp.setFleetCapacity(0.15);
      tmp.setImageIndex(ShipImage.CORVETTE);
      return tmp;
    }
    if (index == HULL_MEDIUM_FREIGHTER) {
      tmp = new ShipHull(index, "Medium freighter", 8, 1,
          ShipHullType.FREIGHTER, ShipSize.LARGE, 6, 12, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.MEDIUM_FREIGHTER);
      return tmp;
    }
    if (index == HULL_MEDIUM_STARBASE) {
      tmp = new ShipHull(index, "Medium starbase", 8, 2, ShipHullType.STARBASE,
          ShipSize.MEDIUM, 8, 10, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.MEDIUM_STARBASE);
      return tmp;
    }
    if (index == HULL_SCOUT_MK2) {
      tmp = new ShipHull(index, "Scout Mk2", 4, 2, ShipHullType.NORMAL,
          ShipSize.SMALL, 4, 8, race);
      tmp.setFleetCapacity(0.1);
      tmp.setImageIndex(ShipImage.SCOUT);
      return tmp;
    }
    if (index == HULL_CRUISER) {
      tmp = new ShipHull(index, "Cruiser", 8, 2, ShipHullType.NORMAL,
          ShipSize.LARGE, 8, 14, race);
      tmp.setFleetCapacity(0.4);
      tmp.setImageIndex(ShipImage.CRUISER);
      return tmp;
    }
    if (index == HULL_BATTLESHIP_MK1) {
      tmp = new ShipHull(index, "Battleship Mk1", 10, 2, ShipHullType.NORMAL,
          ShipSize.HUGE, 20, 20, race);
      tmp.setFleetCapacity(0.6);
      tmp.setImageIndex(ShipImage.BATTLESHIP);
      return tmp;
    }
    if (index == HULL_PRIVATEER_MK1) {
      tmp = new ShipHull(index, "Privateer Mk1", 6, 2, ShipHullType.PRIVATEER,
          ShipSize.MEDIUM, 8, 8, race);
      tmp.setFleetCapacity(0.15);
      tmp.setImageIndex(ShipImage.PRIVATEER);
      return tmp;
    }
    if (index == HULL_LARGE_FREIGHTER) {
      tmp = new ShipHull(index, "Large freighter", 10, 2,
          ShipHullType.FREIGHTER, ShipSize.HUGE, 12, 20, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.LARGE_FREIGHTER);
      return tmp;
    }
    if (index == HULL_LARGE_STARBASE) {
      tmp = new ShipHull(index, "Large starbase", 10, 3, ShipHullType.STARBASE,
          ShipSize.LARGE, 15, 15, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.LARGE_STARBASE);
      return tmp;
    }
    if (index == HULL_CORVETTE_MK2) {
      tmp = new ShipHull(index, "Corvette Mk2", 5, 2, ShipHullType.NORMAL,
          ShipSize.SMALL, 6, 8, race);
      tmp.setFleetCapacity(0.15);
      tmp.setImageIndex(ShipImage.CORVETTE);
      return tmp;
    }
    if (index == HULL_BATTLE_CRUISER_MK1) {
      tmp = new ShipHull(index, "Battle cruiser Mk1", 9, 2, ShipHullType.NORMAL,
          ShipSize.LARGE, 12, 12, race);
      tmp.setFleetCapacity(0.5);
      tmp.setImageIndex(ShipImage.BATTLECRUISER);
      return tmp;
    }
    if (index == HULL_PRIVATEER_MK2) {
      tmp = new ShipHull(index, "Privateer Mk2", 8, 2, ShipHullType.PRIVATEER,
          ShipSize.LARGE, 12, 14, race);
      tmp.setFleetCapacity(0.3);
      tmp.setImageIndex(ShipImage.PRIVATEER_LARGE);
      return tmp;
    }
    if (index == HULL_SCOUT_MK3) {
      tmp = new ShipHull(index, "Scout Mk3", 4, 3, ShipHullType.NORMAL,
          ShipSize.SMALL, 8, 10, race);
      tmp.setFleetCapacity(0.1);
      tmp.setImageIndex(ShipImage.SCOUT);
      return tmp;
    }
    if (index == HULL_MASSIVE_FREIGHTER) {
      tmp = new ShipHull(index, "Massive freighter", 12, 2,
          ShipHullType.FREIGHTER, ShipSize.HUGE, 14, 25, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.MASSIVE_FREIGHTER);
      return tmp;
    }
    if (index == HULL_MASSIVE_STARBASE) {
      tmp = new ShipHull(index, "Massive starbase", 12, 4,
          ShipHullType.STARBASE, ShipSize.HUGE, 25, 25, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.MASSIVE_STARBASE);
      return tmp;
    }
    if (index == HULL_CORVETTE_MK3) {
      tmp = new ShipHull(index, "Corvette Mk3", 5, 3, ShipHullType.NORMAL,
          ShipSize.SMALL, 9, 10, race);
      tmp.setFleetCapacity(0.15);
      tmp.setImageIndex(ShipImage.CORVETTE);
      return tmp;
    }
    if (index == HULL_DESTROYER_MK3) {
      tmp = new ShipHull(index, "Destroyer Mk3", 6, 3, ShipHullType.NORMAL,
          ShipSize.MEDIUM, 10, 10, race);
      tmp.setFleetCapacity(0.2);
      tmp.setImageIndex(ShipImage.DESTROYER);
      return tmp;
    }
    if (index == HULL_BATTLESHIP_MK2) {
      tmp = new ShipHull(index, "Battleship Mk2", 10, 3, ShipHullType.NORMAL,
          ShipSize.HUGE, 20, 25, race);
      tmp.setFleetCapacity(0.6);
      tmp.setImageIndex(ShipImage.BATTLESHIP);
      return tmp;
    }
    if (index == HULL_PRIVATEER_MK3) {
      tmp = new ShipHull(index, "Privateer Mk3", 8, 3, ShipHullType.PRIVATEER,
          ShipSize.LARGE, 15, 18, race);
      tmp.setFleetCapacity(0.5);
      tmp.setImageIndex(ShipImage.PRIVATEER_LARGE);
      return tmp;
    }
    if (index == HULL_BATTLE_CRUISER_MK2) {
      tmp = new ShipHull(index, "Battle cruiser Mk2", 9, 3, ShipHullType.NORMAL,
          ShipSize.LARGE, 18, 18, race);
      tmp.setFleetCapacity(0.8);
      tmp.setImageIndex(ShipImage.BATTLECRUISER);
      return tmp;
    }
    if (index == HULL_SCOUT_MK4) {
      tmp = new ShipHull(index, "Scout Mk4", 4, 4, ShipHullType.NORMAL,
          ShipSize.SMALL, 9, 10, race);
      tmp.setFleetCapacity(0.1);
      tmp.setImageIndex(ShipImage.SCOUT);
      return tmp;
    }
    if (index == HULL_DESTROYER_MK4) {
      tmp = new ShipHull(index, "Destroyer Mk4", 6, 4, ShipHullType.NORMAL,
          ShipSize.MEDIUM, 12, 12, race);
      tmp.setFleetCapacity(0.3);
      tmp.setImageIndex(ShipImage.DESTROYER);
      return tmp;
    }
    if (index == HULL_CAPITAL_SHIP) {
      tmp = new ShipHull(index, "Capital ship", 10, 4, ShipHullType.NORMAL,
          ShipSize.HUGE, 30, 30, race);
      tmp.setFleetCapacity(1.0);
      tmp.setImageIndex(ShipImage.CAPITAL_SHIP);
      return tmp;
    }
    if (index == HULL_CORVETTE_MK4) {
      tmp = new ShipHull(index, "Corvette Mk4", 5, 4, ShipHullType.NORMAL,
          ShipSize.SMALL, 10, 10, race);
      tmp.setFleetCapacity(0.5);
      tmp.setImageIndex(ShipImage.CORVETTE);
      return tmp;
    }
    if (index == HULL_ARTICIAL_PLANET) {
      tmp = new ShipHull(index, "Artificial planet", 10, 4,
          ShipHullType.STARBASE, ShipSize.HUGE, 200, 300, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.ARTIFICIAL_PLANET);
      return tmp;
    }
    if (index == HULL_SMALL_ORBITAL) {
      tmp = new ShipHull(index, "Small orbital", 4, 1,
          ShipHullType.ORBITAL, ShipSize.SMALL, 4, 4, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.SMALL_STARBASE);
      return tmp;
    }
    if (index == HULL_MEDIUM_ORBITAL) {
      tmp = new ShipHull(index, "Medium orbital", 6, 2,
          ShipHullType.ORBITAL, ShipSize.MEDIUM, 4, 6, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.MEDIUM_STARBASE);
      return tmp;
    }
    if (index == HULL_MINOR_ORBITAL) {
      tmp = new ShipHull(index, "Minor orbital", 3, 1,
          ShipHullType.ORBITAL, ShipSize.SMALL, 3, 3, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.SMALL_STARBASE);
      return tmp;
    }
    if (index == HULL_LARGE_ORBITAL) {
      tmp = new ShipHull(index, "Large orbital", 10, 3,
          ShipHullType.ORBITAL, ShipSize.LARGE, 8, 12, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.LARGE_STARBASE);
      return tmp;
    }
    if (index == HULL_MASSIVE_ORBITAL) {
      tmp = new ShipHull(index, "Massive orbital", 12, 4,
          ShipHullType.ORBITAL, ShipSize.HUGE, 16, 24, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.MASSIVE_STARBASE);
      return tmp;
    }
    if (index == HULL_SPACE_WORM) {
      tmp = new ShipHull(index, "Space worm", 4, 1, ShipHullType.NORMAL,
          ShipSize.SMALL, 0, 0, race);
      tmp.setFleetCapacity(0.15);
      tmp.setImageIndex(ShipImage.SPACE_WORM);
      return tmp;
    }
    if (index == HULL_KRAKEN) {
      tmp = new ShipHull(index, "Kraken", 6, 2, ShipHullType.NORMAL,
          ShipSize.MEDIUM, 0, 0, race);
      tmp.setFleetCapacity(0.2);
      tmp.setImageIndex(ShipImage.SPACE_KRAKEN);
      return tmp;
    }
    if (index == HULL_LARGE_KRAKEN) {
      tmp = new ShipHull(index, "Large kraken", 8, 3, ShipHullType.NORMAL,
          ShipSize.LARGE, 0, 0, race);
      tmp.setFleetCapacity(0.4);
      tmp.setImageIndex(ShipImage.LARGE_SPACE_KRAKEN);
      return tmp;
    }
    if (index == HULL_PROBE_MK2) {
      tmp = new ShipHull(index, "Probe Mk2", 4, 1, ShipHullType.PROBE,
          ShipSize.SMALL, 3, 2, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.PROBE);
      return tmp;
    }
    if (index == HULL_PROBE_MK3) {
      tmp = new ShipHull(index, "Probe Mk3", 5, 1, ShipHullType.PROBE,
          ShipSize.SMALL, 4, 4, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.PROBE);
      return tmp;
    }
    if (index == HULL_SPORE) {
      tmp = new ShipHull(index, "Spore", 2, 1, ShipHullType.FREIGHTER,
          ShipSize.SMALL, 3, 3, race);
      tmp.setFleetCapacity(0);
      tmp.setImageIndex(ShipImage.SPORE);
      return tmp;
    }

    return tmp;
  }

}
