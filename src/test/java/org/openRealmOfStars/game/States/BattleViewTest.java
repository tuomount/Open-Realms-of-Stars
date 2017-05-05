package org.openRealmOfStars.game.States;

import org.junit.Before;
import org.mockito.Mockito;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.starMap.StarMap;

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
 * Test for BattleViewTest class
 *
 */
public class BattleViewTest {

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