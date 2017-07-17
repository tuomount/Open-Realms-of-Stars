package org.openRealmOfStars.game.States;

import java.awt.event.ActionListener;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.starMap.planet.Planet;
/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2017 Tuomo Untinen
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
 * Test for PlanetBombingView class
 *
 */
public class PlanetBombingViewTest {
    private PlanetBombingView planetBombingView;

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
      Planet planet = Mockito.mock(Planet.class);
      Fleet fleet = Mockito.mock(Fleet.class);
      Ship firstShip = Mockito.mock(Ship.class);
      ShipHull shipHull = Mockito.mock(ShipHull.class);
      Mockito.when(firstShip.getHull()).thenReturn(shipHull);
      Mockito.when(fleet.getFirstShip()).thenReturn(firstShip);
      Mockito.when(fleet.getShips()).thenReturn(new Ship[]{firstShip});

      PlayerInfo attackerPlayerInfo = Mockito.mock(PlayerInfo.class);
      int attackerPlayerIndex = 0;
      ActionListener listener = Mockito.mock(ActionListener.class);

      planetBombingView = new PlanetBombingView(planet, fleet, 
          attackerPlayerInfo, attackerPlayerIndex, listener);
      assertEquals(planet, planetBombingView.getPlanet());
      Planet planet2 = Mockito.mock(Planet.class);
      planetBombingView.setPlanet(planet2);
      assertEquals(planet2, planetBombingView.getPlanet());
      assertEquals(fleet, planetBombingView.getFleet());
      Fleet fleet2 = Mockito.mock(Fleet.class);
      planetBombingView.setFleet(fleet2);
      assertEquals(fleet2, planetBombingView.getFleet());
      // Just running void method
      planetBombingView.resetComponentUsage();
  }

}