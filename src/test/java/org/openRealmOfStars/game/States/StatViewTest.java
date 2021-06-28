package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerColor;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.newsCorp.GalaxyStat;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.vote.Votes;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2021 Tuomo Untinen
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
* Test for StatView
*
*/
public class StatViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatView() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Votes votes = Mockito.mock(Votes.class);
    Mockito.when(playerList.getCurrentMaxPlayers()).thenReturn(6);
    Mockito.when(playerList.getCurrentMaxRealms()).thenReturn(5);
    PlayerInfo player = Mockito.mock(PlayerInfo.class);
    Mockito.when(map.getPlayerByIndex(0)).thenReturn(player);
    Mockito.when(map.getPlayerByIndex(1)).thenReturn(player);
    Mockito.when(map.getPlayerByIndex(2)).thenReturn(player);
    Mockito.when(map.getPlayerByIndex(3)).thenReturn(player);
    Mockito.when(map.getPlayerByIndex(4)).thenReturn(player);
    Mockito.when(map.getPlayerByIndex(5)).thenReturn(player);
    Mockito.when(player.getColor()).thenReturn(PlayerColor.BLUE);
    Mockito.when(player.getEmpireName()).thenReturn("Empire of Test");
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    Mockito.when(diplomacy.getDiplomaticRelation(Mockito.anyInt())).thenReturn("Peace");
    Mockito.when(player.getDiplomacy()).thenReturn(diplomacy);
    GalaxyStat stat = Mockito.mock(GalaxyStat.class);
    int[][] data = new int[5][5];
    Mockito.when(stat.getGalaxyData()).thenReturn(data);
    NewsCorpData newsCorp = Mockito.mock(NewsCorpData.class);
    Mockito.when(newsCorp.getMilitary()).thenReturn(stat);
    Mockito.when(newsCorp.getPlanets()).thenReturn(stat);
    Mockito.when(newsCorp.getPopulation()).thenReturn(stat);
    Mockito.when(newsCorp.getCredit()).thenReturn(stat);
    Mockito.when(newsCorp.getCultural()).thenReturn(stat);
    Mockito.when(newsCorp.getResearch()).thenReturn(stat);
    Mockito.when(newsCorp.generateScores()).thenReturn(stat);
    Mockito.when(map.getNewsCorpData()).thenReturn(newsCorp);
    Mockito.when(map.getPlayerByIndex(Mockito.anyInt())).thenReturn(player);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Mockito.when(map.getVotes()).thenReturn(votes);
    StatView view = new StatView(map, listener);
    assertEquals("Back to star map", view.getBackButtonText());
    view.setBackButtonText("Test OK");
    assertEquals("Test OK", view.getBackButtonText());
  }

}
