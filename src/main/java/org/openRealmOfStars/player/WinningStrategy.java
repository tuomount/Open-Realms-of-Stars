package org.openRealmOfStars.player;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019, 2021 Tuomo Untinen
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
* Winning strategy for AI how to trying to win the game.
*
*/
public enum WinningStrategy {
  /**
   * Nothing has become strongest strategy yet.
   */
  GENERIC,
  /**
   * Conquer other realms home worlds
   */
  CONQUER,
  /**
   * Diplomatic victory
   */
  DIPLOMATIC,
  /**
   * Cultural victory
   */
  CULTURAL,
  /**
   * Scientific victory
   */
  SCIENCE,
  /**
   * Population victory
   */
  POPULATION;
}
