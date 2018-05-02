package org.openRealmOfStars.player.player;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
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
* Test for List of PLayer info
*
*/
public class PlayerListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    PlayerList list = new PlayerList();
    assertEquals(0, list.getCurrentMaxPlayers());
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    list.addPlayer(info);
    assertEquals(1, list.getCurrentMaxPlayers());
    list.addPlayer(info);
    assertEquals(2, list.getCurrentMaxPlayers());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic2() {
    PlayerList list = new PlayerList();
    assertEquals(0, list.getCurrentPlayer());
    assertEquals(0, list.getCurrentMaxPlayers());
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Test 1");
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("Test 2");
    list.addPlayer(info);
    assertEquals(1, list.getCurrentMaxPlayers());
    list.addPlayer(info2);
    assertEquals(2, list.getCurrentMaxPlayers());
    assertEquals(0, list.getCurrentPlayer());
    list.setCurrentPlayer(1);
    assertEquals(1, list.getCurrentPlayer());
    assertEquals(info2, list.getCurrentPlayerInfo());
    list.setCurrentPlayer(0);
    assertEquals(info, list.getCurrentPlayerInfo());
    assertEquals(0, list.getCurrentPlayer());
    assertEquals(info, list.getPlayerInfoByIndex(0));
    assertEquals(info2, list.getPlayerInfoByIndex(1));
    assertEquals(0, list.getIndex(info));
    assertEquals(1, list.getIndex(info2));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testFindNames() {
    PlayerList list = new PlayerList();
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Test");
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getEmpireName()).thenReturn("AI of Test");
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getEmpireName()).thenReturn("Federation of Test");
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getEmpireName()).thenReturn("Democracy of Test");
    list.addPlayer(info);
    list.addPlayer(info2);
    list.addPlayer(info3);
    list.addPlayer(info4);
    PlayerInfo[] infos = list.findRealmNamesFromText(
        "AI of Test makes peace with Federation of Test");
    assertEquals(2, infos.length);
    assertEquals(info2, infos[0]);
    assertEquals(info3, infos[1]);
    infos = list.findRealmNamesFromText(
        "Nothing make peace with empty in deep space");
    assertEquals(0, infos.length);
    infos = list.findRealmNamesFromText(
        "Empire of Test makes peace with Democracy of Test."
        + " Empire of Test make war with AI of Test");
    assertEquals(3, infos.length);
    assertEquals(info, infos[0]);
    assertEquals(info4, infos[1]);
    assertEquals(info2, infos[2]);
  }

}
