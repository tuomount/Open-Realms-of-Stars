package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;

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
 * Tests for Diplomacy
 */
public class DiplomacyTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    PlayerList players = Mockito.mock(PlayerList.class);
    PlayerInfo info1 = Mockito.mock(PlayerInfo.class);
    PlayerInfo infoSelf = Mockito.mock(PlayerInfo.class);
    Mockito.when(players.getCurrentMaxPlayers()).thenReturn(4);
    Mockito.when(players.getPlayerInfoByIndex(0)).thenReturn(info1);
    Mockito.when(players.getPlayerInfoByIndex(1)).thenReturn(infoSelf);
    Mockito.when(players.getPlayerInfoByIndex(2)).thenReturn(info1);
    Mockito.when(players.getPlayerInfoByIndex(3)).thenReturn(info1);
    Diplomacy diplomacy = new Diplomacy(players, 1);
    assertNotEquals(null, diplomacy.getDiplomacyList(0));
    assertNotEquals(null, diplomacy.getDiplomacyList(2));
    assertNotEquals(null, diplomacy.getDiplomacyList(3));
    assertEquals(null, diplomacy.getDiplomacyList(1));
  }

}
