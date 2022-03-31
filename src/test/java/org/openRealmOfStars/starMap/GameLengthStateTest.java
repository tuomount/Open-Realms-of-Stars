package org.openRealmOfStars.starMap;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.starMap.planet.GameLengthState;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018, 2019 Tuomo Untinen
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
* Load Game View
*
*/
public class GameLengthStateTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartGameState() {
    assertEquals(GameLengthState.START_GAME, GameLengthState.getGameLengthState(0, 50));
    assertEquals(GameLengthState.START_GAME, GameLengthState.getGameLengthState(30, 200));
    assertEquals(GameLengthState.START_GAME, GameLengthState.getGameLengthState(50, 400));
    assertEquals(GameLengthState.START_GAME, GameLengthState.getGameLengthState(100, 800));
    assertEquals(GameLengthState.START_GAME, GameLengthState.getGameLengthState(200, 1000));
    assertEquals(GameLengthState.START_GAME, GameLengthState.getGameLengthState(0, 10));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testElderGameState() {
    assertEquals(GameLengthState.ELDER_HEAD_START, GameLengthState.getGameLengthState(-1, 50));
    assertEquals(GameLengthState.ELDER_HEAD_START, GameLengthState.getGameLengthState(-30, 200));
    assertEquals(GameLengthState.ELDER_HEAD_START, GameLengthState.getGameLengthState(-50, 400));
    assertEquals(GameLengthState.ELDER_HEAD_START, GameLengthState.getGameLengthState(-100, 800));
    assertEquals(GameLengthState.ELDER_HEAD_START, GameLengthState.getGameLengthState(-200, 1000));
    assertEquals(GameLengthState.ELDER_HEAD_START, GameLengthState.getGameLengthState(-1, 10));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEarlyGameState() {
    assertEquals(GameLengthState.EARLY_GAME, GameLengthState.getGameLengthState(15, 50));
    assertEquals(GameLengthState.EARLY_GAME, GameLengthState.getGameLengthState(50, 200));
    assertEquals(GameLengthState.EARLY_GAME, GameLengthState.getGameLengthState(110, 400));
    assertEquals(GameLengthState.EARLY_GAME, GameLengthState.getGameLengthState(300, 800));
    assertEquals(GameLengthState.EARLY_GAME, GameLengthState.getGameLengthState(280, 1000));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMiddleGameState() {
    assertEquals(GameLengthState.MIDDLE_GAME, GameLengthState.getGameLengthState(27, 50));
    assertEquals(GameLengthState.MIDDLE_GAME, GameLengthState.getGameLengthState(110, 200));
    assertEquals(GameLengthState.MIDDLE_GAME, GameLengthState.getGameLengthState(180, 400));
    assertEquals(GameLengthState.MIDDLE_GAME, GameLengthState.getGameLengthState(420, 800));
    assertEquals(GameLengthState.MIDDLE_GAME, GameLengthState.getGameLengthState(550, 1000));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLateGameState() {
    assertEquals(GameLengthState.LATE_GAME, GameLengthState.getGameLengthState(35, 50));
    assertEquals(GameLengthState.LATE_GAME, GameLengthState.getGameLengthState(140, 200));
    assertEquals(GameLengthState.LATE_GAME, GameLengthState.getGameLengthState(280, 400));
    assertEquals(GameLengthState.LATE_GAME, GameLengthState.getGameLengthState(555, 800));
    assertEquals(GameLengthState.LATE_GAME, GameLengthState.getGameLengthState(680, 1000));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEndGameState() {
    assertEquals(GameLengthState.END_GAME, GameLengthState.getGameLengthState(45, 50));
    assertEquals(GameLengthState.END_GAME, GameLengthState.getGameLengthState(180, 200));
    assertEquals(GameLengthState.END_GAME, GameLengthState.getGameLengthState(380, 400));
    assertEquals(GameLengthState.END_GAME, GameLengthState.getGameLengthState(655, 800));
    assertEquals(GameLengthState.END_GAME, GameLengthState.getGameLengthState(880, 1000));
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testZero() {
    assertEquals(GameLengthState.END_GAME, GameLengthState.getGameLengthState(880, 0));
  }

  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNegative() {
    assertEquals(GameLengthState.ELDER_HEAD_START, GameLengthState.getGameLengthState(-10, 600));
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLastTurnSmallerThanCurrent() {
    assertEquals(GameLengthState.END_GAME, GameLengthState.getGameLengthState(1000, 600));
  }

}
