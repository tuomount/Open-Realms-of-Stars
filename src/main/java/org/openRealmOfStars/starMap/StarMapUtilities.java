package org.openRealmOfStars.starMap;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 *
 *
 * Star map utilities
 *
 */
public final class StarMapUtilities {

  /**
   * Hiding the constructor
   */
  private StarMapUtilities() {
    // Nothing to do
  }

  /**
   * Check if there can be fitted solar system
   * @param solarSystem where to check solar systems
   * @param sx Center of sun X coordinate
   * @param sy Center of sun Y coordinate
   * @param maxX maximum X coordinate
   * @param maxY maximum Y coordinate
   * @param sunDistance distance between suns
   * @return How many solar system sectors found
   */
  public static int isSolarSystem(final int[][] solarSystem, final int sx,
      final int sy, final int maxX, final int maxY, final int sunDistance) {
    int result = 0;
    for (int y = -sunDistance; y < sunDistance; y++) {
      for (int x = -sunDistance; x < sunDistance; x++) {
        if (x + sx >= 0 && y + sy >= 0 && x + sx < maxX && y + sy < maxY) {
          result = result + solarSystem[sx + x][sy + y];
        }
      }
    }
    return result;
  }

  /**
   * Get how full universe is solar systems
   * @param solarSystem Solar system map
   * @param maxX Maximum size of X
   * @param maxY Maximum size of Y
   * @return How many percent universe is full
   */
  public static int getSystemFullness(final int[][] solarSystem, final int maxX,
      final int maxY) {
    int result = 0;
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        if (solarSystem[x][y] == 1) {
          result++;
        }
      }
    }
    int total = (maxX - 2 * StarMap.SOLAR_SYSTEM_WIDTH)
        * (maxY - 2 * StarMap.SOLAR_SYSTEM_WIDTH);
    result = result * 100 / total;
    return result;
  }

  /**
   * Set solar system on solar system map
   * @param solarSystem Map where place solar system
   * @param sx Center of sun X coordinate
   * @param sy Center of sun Y coordinate
   * @param maxX maximum X coordinate
   * @param maxY maximum Y coordinate
   */
  public static void setSolarSystem(final int[][] solarSystem, final int sx,
      final int sy, final int maxX, final int maxY) {
    for (int y = -StarMap.SOLAR_SYSTEM_WIDTH;
         y < StarMap.SOLAR_SYSTEM_WIDTH; y++) {
      for (int x = -StarMap.SOLAR_SYSTEM_WIDTH;
           x < StarMap.SOLAR_SYSTEM_WIDTH; x++) {
        if (x + sx >= 0 && y + sy >= 0 && x + sx < maxX && y + sy < maxY) {
          solarSystem[sx + x][sy + y] = 1;
        }
      }
    }
  }

  /**
   * Add player's repuation that he or she has dropped nuclear bombs
   * @param starMap StarMap containing all the diplomacies
   * @param nuker PlayerInfo who is nuking.
   */
  public static void addNukeRepuation(final StarMap starMap,
      final PlayerInfo nuker) {
    addRepuation(starMap, nuker, DiplomacyBonusType.NUKED);
  }

  /**
   * Add player's repuation that he or she has declared a war
   * @param starMap StarMap containing all the diplomacies
   * @param attacker PlayerInfo who is attacking.
   */
  public static void addWarDeclatingRepuation(final StarMap starMap,
      final PlayerInfo attacker) {
    addRepuation(starMap, attacker, DiplomacyBonusType.WAR_DECLARTION);
  }

  /**
   * Add player's repuation that he or she has done something, usually bad.
   * @param starMap StarMap containing all the diplomacies
   * @param actor PlayerInfo who is acting.
   * @param bonusType Diplomacy Bonus which is being added to all players
   */
  private static void addRepuation(final StarMap starMap,
      final PlayerInfo actor, final DiplomacyBonusType bonusType) {
    int index = starMap.getPlayerList().getIndex(actor);
    for (int i = 0; i < starMap.getPlayerList().getCurrentMaxPlayers(); i++) {
      PlayerInfo player = starMap.getPlayerByIndex(i);
      if (i != index) {
        DiplomacyBonusList list = player.getDiplomacy()
            .getDiplomacyList(index);
        list.addBonus(bonusType, player.getRace());
      }
    }
  }

  /**
   * Calculates escape coordinates for combat if defender escapes.
   * @param defender Defender's coordinate
   * @param attacker Attacker's coordinate
   * @return Coordinate or null if attacking is too far away
   */
  public static Coordinate getEscapeCoordinates(final Coordinate defender,
      final Coordinate attacker) {
    int mx = defender.getX() - attacker.getX();
    int my = defender.getY() - attacker.getY();
    if (mx > 1) {
      mx = 1;
    }
    if (my > 1) {
      my = 1;
    }
    if (mx < -1) {
      mx = -1;
    }
    if (my < -1) {
      my = -1;
    }
    return new Coordinate(defender.getX() + mx, defender.getY() + my);
  }

}
