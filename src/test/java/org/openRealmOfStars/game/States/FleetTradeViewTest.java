package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
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

}
