package org.openRealmOfStars.starMap.vote.sports;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Sports's class JUnits
*
*/
public class SportsTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Athlete athlete1 = Mockito.mock(Athlete.class);
    Athlete athlete2 = Mockito.mock(Athlete.class);
    Sports sports = new Sports();
    sports.add(athlete1);
    sports.add(athlete2);
    Athlete[] athletes = sports.getAthletes();
    assertEquals(2, athletes.length);
    assertEquals(athlete1, athletes[0]);
    assertEquals(athlete2, athletes[1]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSorting() {
    Athlete athlete1 = Mockito.mock(Athlete.class);
    Athlete athlete2 = Mockito.mock(Athlete.class);
    Athlete athlete3 = Mockito.mock(Athlete.class);
    Mockito.when(athlete1.getSportingValue()).thenReturn(30);
    Mockito.when(athlete2.getSportingValue()).thenReturn(15);
    Mockito.when(athlete3.getSportingValue()).thenReturn(20);
    Sports sports = new Sports();
    sports.add(athlete1);
    sports.add(athlete2);
    sports.add(athlete3);
    sports.orderBySporting();
    Athlete[] athletes = sports.getAthletes();
    assertEquals(3, athletes.length);
    assertEquals(athlete1, athletes[0]);
    assertEquals(athlete3, athletes[1]);
    assertEquals(athlete2, athletes[2]);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSporting() {
    PlayerInfo info1 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info1.getRace()).thenReturn(SpaceRace.HUMAN);
    Athlete athlete1 = new Athlete("Planet I", info1);
    athlete1.setBonus(0);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info2.getRace()).thenReturn(SpaceRace.CENTAURS);
    Athlete athlete2 = new Athlete("Planet II", info2);
    athlete2.setBonus(800);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info3.getRace()).thenReturn(SpaceRace.GREYANS);
    Athlete athlete3 = new Athlete("Planet III", info3);
    athlete3.setBonus(100);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info4.getRace()).thenReturn(SpaceRace.HOMARIANS);
    Athlete athlete4 = new Athlete("Planet IV", info3);
    athlete4.setBonus(30);
    Sports sports = new Sports();
    sports.add(athlete4);
    sports.add(athlete3);
    sports.add(athlete2);
    sports.add(athlete1);
    sports.handleSports();
    Athlete[] athletes = sports.getAthletes();
    assertEquals(4, athletes.length);
    assertEquals(athlete2, athletes[0]);
    assertEquals(athlete3, athletes[1]);
    assertEquals(athlete4, athletes[2]);
    assertEquals(athlete1, athletes[3]);
  }

}
