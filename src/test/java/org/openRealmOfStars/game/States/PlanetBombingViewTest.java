package org.openRealmOfStars.game.States;

import org.junit.Before;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.starMap.planet.Planet;

import java.awt.event.ActionListener;
/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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

    @Before
    public void setUp() {
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

        planetBombingView = new PlanetBombingView(planet, fleet, attackerPlayerInfo, attackerPlayerIndex, listener);
    }

}