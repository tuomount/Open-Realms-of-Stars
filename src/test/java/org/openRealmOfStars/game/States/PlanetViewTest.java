package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Construction;

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
*
* Test for PlanetView class
*
*/
public class PlanetViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Planet planet = Mockito.mock(Planet.class);
    Planet planet2 = Mockito.mock(Planet.class);
    Construction[] constructionList = new Construction[2];
    constructionList[0] = Mockito.mock(Construction.class);
    constructionList[1] = Mockito.mock(Construction.class);
    Mockito.when(planet.getProductionList()).thenReturn(constructionList);
    PlayerInfo player = Mockito.mock(PlayerInfo.class);
    ActionListener listener = Mockito.mock(ActionListener.class);
    PlanetView view = new PlanetView(planet, false, player, listener);
    assertEquals(planet, view.getPlanet());
    view.setPlanet(planet2);
    assertEquals(planet2, view.getPlanet());
  }

}
