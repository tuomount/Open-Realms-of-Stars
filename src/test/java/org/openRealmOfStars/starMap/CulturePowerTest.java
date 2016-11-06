package org.openRealmOfStars.starMap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
 * Test for Culture power for single sector
 *
 */
public class CulturePowerTest {

    private CulturePower culturePower;
    private static final int DEFAULT_PLAYER_INDEX = -1;
    private static final int MAX_PLAYERS = 4;

    @Before
    public void setUp() {
        culturePower = new CulturePower();
    }

    @Test
    public void testGetHighestCultureShouldReturnsTheDefaultIndex() {
        assertEquals(DEFAULT_PLAYER_INDEX, culturePower.getHighestCulture());
    }

    @Test
    public void testAddCultureShouldAddCultureToPlayerByIndex() {
        int playerIndex = 1;
        int culturePowerValue = 10;
        culturePower.addCulture(playerIndex, culturePowerValue);

        assertEquals(playerIndex, culturePower.getHighestCulture());
    }

    @Test
    public void testAddCultureShouldAddCultureToPlayerByIndex2() {
        int firstPlayerIndex = 1;
        int secondPlayerIndex = 2;
        int firstPlayerCulturePowerValue = 10;
        int secondPlayerCulturePowerValue = 20;
        culturePower.addCulture(firstPlayerIndex, firstPlayerCulturePowerValue);
        culturePower.addCulture(secondPlayerIndex, secondPlayerCulturePowerValue);

        assertEquals(secondPlayerIndex, culturePower.getHighestCulture());
    }

    @Test
    public void testAddCultureShouldAddCultureToPlayerByIndex3() {
        int firstPlayerIndex = 1;
        int secondPlayerIndex = 2;
        int culturePowerValue = 10;

        culturePower.addCulture(firstPlayerIndex, culturePowerValue);
        culturePower.addCulture(secondPlayerIndex, culturePowerValue);
        culturePower.addCulture(firstPlayerIndex, culturePowerValue);

        assertEquals(firstPlayerIndex, culturePower.getHighestCulture());
    }

    @Test
    public void testAddCultureShouldNotThrowExceptionWhenPlayerIndexIsOutOfRange() {
        int playerIndex = -1;
        int culturePowerValue = 10;

        culturePower.addCulture(playerIndex, culturePowerValue);
    }

    @Test
    public void testAddCultureShouldNotThrowExceptionWhenPlayerIndexIsOutOfRange2() {
        int culturePowerValue = 10;
        culturePower.addCulture(MAX_PLAYERS, culturePowerValue);
    }

    @Test
    public void testResetShouldResetTheCulturePowers() {
        int playerIndex = 1;
        int culturePowerValue = 10;
        culturePower.addCulture(playerIndex, culturePowerValue);

        culturePower.reset();

        assertEquals(DEFAULT_PLAYER_INDEX, culturePower.getHighestCulture());
    }
}