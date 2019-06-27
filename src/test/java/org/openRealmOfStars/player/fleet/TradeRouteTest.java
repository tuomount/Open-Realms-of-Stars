package org.openRealmOfStars.player.fleet;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.planet.Planet;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2019  Tuomo Untinen
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
* Trade route calculation for fleet
*
*/
public class TradeRouteTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Planet originWorld = Mockito.mock(Planet.class);
    Mockito.when(originWorld.getName()).thenReturn("Origin");
    Planet tradeWorld = Mockito.mock(Planet.class);
    Mockito.when(tradeWorld.getName()).thenReturn("Test I");
    Coordinate coordinate = Mockito.mock(Coordinate.class);
    Coordinate coordinate2 = Mockito.mock(Coordinate.class);
    Mockito.when(coordinate.sameAs(coordinate)).thenReturn(true);
    Mockito.when(originWorld.getCoordinate()).thenReturn(coordinate);
    Mockito.when(tradeWorld.getCoordinate()).thenReturn(coordinate2);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coordinate);
    Mockito.when(fleet.calculateTrade(coordinate2)).thenReturn(10);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.HIVEMIND);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SCAURIANS);
    TradeRoute tradeRoute = new TradeRoute(originWorld, tradeWorld, info, fleet);
    assertEquals(7, tradeRoute.getTradeValue());
    assertEquals("Origin <-> Test I 7 credits", tradeRoute.toString());
    assertEquals(originWorld, tradeRoute.getOriginWorld());
    assertEquals(tradeWorld, tradeRoute.getTradeWorld());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic2() {
    Planet originWorld = Mockito.mock(Planet.class);
    Mockito.when(originWorld.getName()).thenReturn("Origin");
    Planet tradeWorld = Mockito.mock(Planet.class);
    Mockito.when(tradeWorld.getName()).thenReturn("Test I");
    Coordinate coordinate = Mockito.mock(Coordinate.class);
    Coordinate coordinate2 = Mockito.mock(Coordinate.class);
    Mockito.when(coordinate2.sameAs(coordinate2)).thenReturn(true);
    Mockito.when(originWorld.getCoordinate()).thenReturn(coordinate);
    Mockito.when(tradeWorld.getCoordinate()).thenReturn(coordinate2);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coordinate2);
    Mockito.when(fleet.calculateTrade(coordinate)).thenReturn(10);
    Mockito.when(coordinate2.calculateDistance(coordinate)).thenReturn(25.0);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.HIVEMIND);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SCAURIANS);
    TradeRoute tradeRoute = new TradeRoute(originWorld, tradeWorld, info, fleet);
    assertEquals(7, tradeRoute.getTradeValue());
    assertEquals("Origin <-> Test I 7 credits", tradeRoute.toString());
    assertEquals(originWorld, tradeRoute.getOriginWorld());
    assertEquals(tradeWorld, tradeRoute.getTradeWorld());
  }

}
