package org.openRealmOfStars.game.States;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.starMap.StarMap;

import java.awt.event.ActionListener;

import static org.junit.Assert.*;

public class BattleViewTest {

    private BattleView battleView;

    @Before
    public void setUp() {
        ShipHull shipHull = Mockito.mock(ShipHull.class);
        Ship ship = Mockito.mock(Ship.class);
        Mockito.when(ship.getHull()).thenReturn(shipHull);
        CombatShip combatShip = Mockito.mock(CombatShip.class);
        Mockito.when(combatShip.getShip()).thenReturn(ship);
        Combat combat = Mockito.mock(Combat.class);
        Mockito.when(combat.getCurrentShip()).thenReturn(combatShip);
        StarMap starMap = Mockito.mock(StarMap.class);
        Mockito.when(starMap.getMaxX()).thenReturn(10);
        Mockito.when(starMap.getMaxY()).thenReturn(10);
        ActionListener actionListener = Mockito.mock(ActionListener.class);

        battleView = new BattleView(combat, starMap, actionListener);
    }

    @Test
    public void testAddLogShouldRotateMessagesAndPutNewMessageToTheBeginningOfTheArray() {
        String[] actualResult;
        String[] expectedResult = new String[BattleView.MAX_LOG_NUMBER];
        String initialMessage = BattleView.INITIAL_LOG_MESSAGE;
        String addMessage = "Message";
        expectedResult[0] = addMessage;
        expectedResult[1] = initialMessage;
        for (int i = 2; i < BattleView.MAX_LOG_NUMBER; i++) {
            expectedResult[i] = "";
        }

        battleView.addLog(addMessage);

        actualResult = battleView.getLogMessages();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAddLogShouldNotThrowExceptionWhenGetMoreLogMessages() {
        for (int i = 1; i <= BattleView.MAX_LOG_NUMBER; i++) {
            battleView.addLog("Message " + i);
        }

        battleView.addLog("Message " + BattleView.MAX_LOG_NUMBER + 1);
    }

}