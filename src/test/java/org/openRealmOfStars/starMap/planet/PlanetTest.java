package org.openRealmOfStars.starMap.planet;

import org.junit.Test;
import org.openRealmOfStars.starMap.Coordinate;

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
 * Test for planet
 * @TODO: Mock dependencies (IOUtilities, DiceGenerator, Tile, Tiles,
 * AnimatedImage, GuiStatics)
 */
public class PlanetTest {

    /* -= Behaviour =- */

    @Test
    public void testPlanetCoordinateShouldChangeableWithSideEffect() {
        Coordinate planetCoordinate = new Coordinate(10, 15);
        Planet planet = new Planet(planetCoordinate, "Earth", 1, false);

        planetCoordinate.setX(5);
        planetCoordinate.setY(10);

        assertNotEquals(planet.getCoordinate(), planetCoordinate);

        Coordinate getPlanetCoordinate = planet.getCoordinate();
        getPlanetCoordinate.setX(5);
        getPlanetCoordinate.setY(10);

        assertNotEquals(planet.getCoordinate(), getPlanetCoordinate);
    }

}
