package org.openRealmOfStars.game.States;

import org.junit.Before;
import org.mockito.Mockito;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.starMap.StarMap;

import java.awt.event.ActionListener;

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

}