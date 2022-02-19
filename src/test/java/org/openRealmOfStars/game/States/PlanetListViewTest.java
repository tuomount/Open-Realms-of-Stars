package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.starMap.planet.construction.Construction;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2021,2022 Tuomo Untinen
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
* Test for PlanetListView class
*
*/
public class PlanetListViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    StarMap map = Mockito.mock(StarMap.class);
    ArrayList<Planet> listOfPlanets = new ArrayList<>();
    Planet planet1 = Mockito.mock(Planet.class);
    Planet planet2 = Mockito.mock(Planet.class);
    listOfPlanets.add(planet1);
    listOfPlanets.add(planet2);
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(planet1.getPlanetPlayerInfo()).thenReturn(realm);
    Mockito.when(planet1.getPlanetType()).thenReturn(PlanetTypes.ICEWORLD1);
    Mockito.when(planet2.getPlanetPlayerInfo()).thenReturn(realm);
    Mockito.when(planet2.getPlanetType()).thenReturn(PlanetTypes.WATERWORLD1);
    Construction[] list = new Construction[1];
    list[0] = Mockito.mock(Construction.class);
    Mockito.when(list[0].getName()).thenReturn("Test building");
    Mockito.when(planet1.getProductionList()).thenReturn(list);
    Mockito.when(planet2.getProductionList()).thenReturn(list);
    Mockito.when(map.getPlanetList()).thenReturn(listOfPlanets);
    ActionListener listener = Mockito.mock(ActionListener.class);
    PlanetListView view = new PlanetListView(realm, map, listener);
    assertEquals(realm, view.getRealm());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasicLithorian() {
    StarMap map = Mockito.mock(StarMap.class);
    ArrayList<Planet> listOfPlanets = new ArrayList<>();
    Planet planet1 = Mockito.mock(Planet.class);
    Planet planet2 = Mockito.mock(Planet.class);
    listOfPlanets.add(planet1);
    listOfPlanets.add(planet2);
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getRace()).thenReturn(SpaceRace.LITHORIANS);
    Mockito.when(planet1.getPlanetPlayerInfo()).thenReturn(realm);
    Mockito.when(planet1.getPlanetType()).thenReturn(PlanetTypes.ICEWORLD1);
    Mockito.when(planet2.getPlanetPlayerInfo()).thenReturn(realm);
    Mockito.when(planet2.getPlanetType()).thenReturn(PlanetTypes.WATERWORLD1);
    Construction[] list = new Construction[1];
    list[0] = Mockito.mock(Construction.class);
    Mockito.when(list[0].getName()).thenReturn("Test building");
    Mockito.when(planet1.getProductionList()).thenReturn(list);
    Mockito.when(planet2.getProductionList()).thenReturn(list);
    Mockito.when(map.getPlanetList()).thenReturn(listOfPlanets);
    ActionListener listener = Mockito.mock(ActionListener.class);
    PlanetListView view = new PlanetListView(realm, map, listener);
    assertEquals(realm, view.getRealm());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testHandlingAction() {
    StarMap map = Mockito.mock(StarMap.class);
    ArrayList<Planet> listOfPlanets = new ArrayList<>();
    Planet planet1 = new Planet(new Coordinate(5, 5), "Test", 1, false);
    planet1.setRadiationLevel(1);
    listOfPlanets.add(planet1);
    PlayerInfo realm = new PlayerInfo(SpaceRace.GREYANS, 2, 0);
    realm.setGovernment(GovernmentType.UNION);
    planet1.setPlanetOwner(0, realm);
    Mockito.when(map.getPlanetList()).thenReturn(listOfPlanets);
    ActionListener listener = Mockito.mock(ActionListener.class);
    PlanetListView view = new PlanetListView(realm, map, listener);
    ActionEvent arg0 = Mockito.mock(ActionEvent.class);
    Mockito.when(arg0.getActionCommand()).thenReturn("Test I|"
        + GameCommands.COMMAND_PRODUCTION_LIST);
    view.handleAction(arg0);
    assertEquals("Extra credit", planet1.getUnderConstruction().getName());
  }

}
