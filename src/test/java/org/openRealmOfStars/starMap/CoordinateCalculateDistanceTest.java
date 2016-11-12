package org.openRealmOfStars.starMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

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
 * Test for Coordinate.calculateDistance()
 *
 */
@RunWith(value = Parameterized.class)
public class CoordinateCalculateDistanceTest {

    private Coordinate firstCoordinate;
    private Coordinate secondCoordinate;
    private double expectedValue;

    public CoordinateCalculateDistanceTest(Coordinate firstCoordinate, Coordinate secondCoordinate, double expectedValue) {
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;
        this.expectedValue = expectedValue;
    }

    @Parameterized.Parameters(name = "{index}: calculateDistance({0}, {1}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new Coordinate(0, 0), new Coordinate(0, 0), 0},
                {new Coordinate(0, 0), new Coordinate(1, 0), 1},
                {new Coordinate(0, 0), new Coordinate(0, 1), 1},
                {new Coordinate(0, 0), new Coordinate(1, 1), 1.4142135623730951},
                //@TODO: Add more test parameters to check the most critical part
        });
    }

    @Test
    public void testCalculateDistanceShouldReturnZeroWhenTheCoordinatesAreEquals() throws Exception {
        double actualValue = firstCoordinate.calculateDistance(secondCoordinate);

        assertEquals(expectedValue, actualValue, 0);
    }

}