package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusList;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.fleet.TradeRoute;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;

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
* Fleet trade view Test class
*
*/
public class FleetTradeViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Fleet fleet = Mockito.mock(Fleet.class);
    Planet planet = Mockito.mock(Planet.class);
    ActionListener listener = Mockito.mock(ActionListener.class);
    FleetTradeView view = new FleetTradeView(map, info, planet, fleet,
        listener);
    assertEquals(map, view.getMap());
    assertEquals(info, view.getPlayerInfo());
    assertEquals(planet, view.getPlanet());
    assertEquals(fleet, view.getFleet());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRoutes() {
    StarMap map = Mockito.mock(StarMap.class);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    DiplomacyBonusList diplomacyList = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacyList.isBonusType(DiplomacyBonusType.IN_ALLIANCE)).thenReturn(true);
    Mockito.when(diplomacy.getDiplomacyList(1)).thenReturn(diplomacyList);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(info.getSectorVisibility(coord)).thenReturn(PlayerInfo.VISIBLE);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.CLAN);
    Mockito.when(info2.getGovernment()).thenReturn(GovernmentType.CLAN);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getIndex(info2)).thenReturn(1);
    Mockito.when(playerList.getIndex(info)).thenReturn(0);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Fleet fleet = Mockito.mock(Fleet.class);
    Coordinate coord2 = Mockito.mock(Coordinate.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord2);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(coord);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getCoordinate()).thenReturn(coord);
    Mockito.when(planet2.getPlanetPlayerInfo()).thenReturn(info2);
    ArrayList<Planet> list = new ArrayList<>();
    list.add(planet);
    list.add(planet2);
    Mockito.when(map.getPlanetList()).thenReturn(list);
    ActionListener listener = Mockito.mock(ActionListener.class);
    FleetTradeView view = new FleetTradeView(map, info, planet, fleet,
        listener);
    TradeRoute[] routes = view.getPossibleTradeRoutes();
    assertEquals(1, routes.length);
    assertEquals(planet, routes[0].getOriginWorld());
    assertEquals(planet2, routes[0].getTradeWorld());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRoutes2() {
    StarMap map = Mockito.mock(StarMap.class);
    Diplomacy diplomacy = Mockito.mock(Diplomacy.class);
    DiplomacyBonusList diplomacyList = Mockito.mock(DiplomacyBonusList.class);
    Mockito.when(diplomacyList.isBonusType(DiplomacyBonusType.IN_ALLIANCE)).thenReturn(true);
    Mockito.when(diplomacy.getDiplomacyList(1)).thenReturn(diplomacyList);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Coordinate coord = Mockito.mock(Coordinate.class);
    Mockito.when(info.getSectorVisibility(coord)).thenReturn(PlayerInfo.VISIBLE);
    Mockito.when(info.getDiplomacy()).thenReturn(diplomacy);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.CLAN);
    Mockito.when(info2.getGovernment()).thenReturn(GovernmentType.CLAN);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(playerList.getIndex(info2)).thenReturn(1);
    Mockito.when(playerList.getIndex(info)).thenReturn(0);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Fleet fleet = Mockito.mock(Fleet.class);
    Coordinate coord2 = Mockito.mock(Coordinate.class);
    Mockito.when(fleet.getCoordinate()).thenReturn(coord2);
    Mockito.when(coord2.calculateDistance(coord)).thenReturn(25.0);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getCoordinate()).thenReturn(coord);
    Mockito.when(planet.getPlanetPlayerInfo()).thenReturn(info);
    Planet planet2 = Mockito.mock(Planet.class);
    Mockito.when(planet2.getCoordinate()).thenReturn(coord);
    Mockito.when(planet2.getPlanetPlayerInfo()).thenReturn(info2);
    ArrayList<Planet> list = new ArrayList<>();
    list.add(planet);
    list.add(planet2);
    Mockito.when(map.getPlanetList()).thenReturn(list);
    ActionListener listener = Mockito.mock(ActionListener.class);
    FleetTradeView view = new FleetTradeView(map, info, planet2, fleet,
        listener);
    TradeRoute[] routes = view.getPossibleTradeRoutes();
    assertEquals(1, routes.length);
    assertEquals(planet, routes[0].getOriginWorld());
    assertEquals(planet2, routes[0].getTradeWorld());
  }

}
