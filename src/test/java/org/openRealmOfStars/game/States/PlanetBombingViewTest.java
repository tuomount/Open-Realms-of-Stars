package org.openRealmOfStars.game.States;

import org.junit.Before;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.starMap.planet.Planet;

import java.awt.event.ActionListener;

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