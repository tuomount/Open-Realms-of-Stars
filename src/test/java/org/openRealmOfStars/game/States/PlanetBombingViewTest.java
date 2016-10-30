package org.openRealmOfStars.game.States;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.starMap.planet.Planet;

import java.awt.event.ActionListener;

import static org.junit.Assert.*;

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

    @Test
    public void testAddLogShouldRotateMessagesAndPutNewMessageToTheBeginningOfTheArray() {
        String[] actualResult;
        String[] expectedResult = new String[PlanetBombingView.MAX_LOG_NUMBER];
        String addMessage = "Message";
        expectedResult[0] = addMessage;
        for (int i = 1; i < PlanetBombingView.MAX_LOG_NUMBER; i++) {
            expectedResult[i] = "";
        }

        planetBombingView.addLog(addMessage);

        actualResult = planetBombingView.getLogMessages();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddLogShouldNotThrowExceptionWhenGetMoreLogMessages() {
        for (int i = 1; i <= PlanetBombingView.MAX_LOG_NUMBER; i++) {
            planetBombingView.addLog("Message " + i);
        }

        planetBombingView.addLog("Message " + PlanetBombingView.MAX_LOG_NUMBER + 1);
    }

}