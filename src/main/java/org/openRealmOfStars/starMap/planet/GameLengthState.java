package org.openRealmOfStars.starMap.planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Game Length State. Tells in which state in turn game is.
*
*/
public enum GameLengthState {
  /**
   * Game start where elder are playing alone.
   */
  ELDER_HEAD_START,
  /**
   * Game length state: early game. First 25% of turns
   */
  START_GAME,
  /**
   * Game length state: early game. 15% of turns after start game
   */
  EARLY_GAME,
  /**
   * Game length state: middle game. 20% of turns after early game
   */
  MIDDLE_GAME,
  /**
   * Game length state: late game. 15% of turns after middle game
   */
  LATE_GAME,
  /**
   * Game length state: end game. Last 25% of turns till the end
   */
  END_GAME;

  /**
   * Get Game length state. This checks current turn and
   * compares it score victory turn. Game is divided into
   * five different states: START_GAME, EARLY_GAME, MIDDLE_GAME,
   * LATE_GAME and END_GAME
   * @param currentTurn current turn number
   * @param lastTurn turn where game ends
   * @return GameLengthState
   */
  public static GameLengthState getGameLengthState(final int currentTurn,
      final int lastTurn) {
    if (lastTurn == 0) {
      throw new IllegalArgumentException("Last turn cannot be zero!");
    }
    if (lastTurn < currentTurn) {
      throw new IllegalArgumentException("Last turn cannot be greater than"
          + " current turn!");
    }
    if (currentTurn < 0) {
      return ELDER_HEAD_START;
    }
    int value = currentTurn * 100 / lastTurn;
    if (value <= 24) {
      return START_GAME;
    }
    if (value <= 39) {
      return EARLY_GAME;
    }
    if (value <= 59) {
      return MIDDLE_GAME;
    }
    if (value <= 74) {
      return LATE_GAME;
    }
    return END_GAME;
  }

}
