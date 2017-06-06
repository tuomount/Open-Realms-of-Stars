package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.starMap.StarMap;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
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
 * Diplomatic Trade tests
 * 
 */
public class DiplomaticTradeTest {

  private StarMap generateMap(final int maxPlayer) {
    PlayerList players = Mockito.mock(PlayerList.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(maxPlayer);

    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getPlayerList()).thenReturn(players);
    return map;
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    StarMap map = generateMap(4);
    DiplomaticTrade trade = new DiplomaticTrade(map, 0, 1);
    assertEquals(0,trade.getFirstIndex());
    assertEquals(1,trade.getSecondIndex());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFirstOutOfBound() {
    StarMap map = generateMap(4);
    DiplomaticTrade trade = new DiplomaticTrade(map, 5, 1);
    assertEquals(0,trade.getFirstIndex());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSecondOutOfBound() {
    StarMap map = generateMap(4);
    DiplomaticTrade trade = new DiplomaticTrade(map, 3, -1);
    assertEquals(0,trade.getFirstIndex());
  }

}
