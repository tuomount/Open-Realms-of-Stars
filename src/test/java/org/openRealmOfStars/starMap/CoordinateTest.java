package org.openRealmOfStars.starMap;

import org.junit.Test;
import org.junit.experimental.categories.Category;

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
 * Test for Coordinate
 *
 */
public class CoordinateTest {

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testCreateCoordinateFromXAndY() {
        int x = 10;
        int y = 15;
        Coordinate coordinate = new Coordinate(x, y);

        assertEquals(x, coordinate.getX());
        assertEquals(y, coordinate.getY());
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testCreateCoordinateFromAnother() {
        Coordinate originalCoordinate = new Coordinate(10, 15);
        Coordinate coordinate = new Coordinate(originalCoordinate);

        assertEquals(originalCoordinate.getX(), coordinate.getX());
        assertEquals(originalCoordinate.getY(), coordinate.getY());
    }

    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testCreateCoordinateFromAnotherWithoutSideEffect() {
        Coordinate originalCoordinate = new Coordinate(10, 15);
        Coordinate coordinate = new Coordinate(originalCoordinate);

        originalCoordinate.setX(5);
        originalCoordinate.setY(10);

        assertNotEquals(originalCoordinate.getX(), coordinate.getX());
        assertNotEquals(originalCoordinate.getY(), coordinate.getY());
    }

}
