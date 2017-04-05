package org.openRealmOfStars.starMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.utilities.RandomSystemNameGenerator;

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
 * Test for Sun class
 * 
 */
public class SunTest {

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testCreateSunWithName() {
        Coordinate coordinate = Mockito.mock(Coordinate.class);
        Mockito.when(coordinate.getX()).thenReturn(10);
        Mockito.when(coordinate.getY()).thenReturn(15);
        Sun sun = new Sun(coordinate, "Sun name");

        assertEquals("Sun name", sun.getName());
        assertEquals(10, sun.getCenterX());
        assertEquals(15, sun.getCenterY());
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testCreateSunWithNameGenerator() {
        RandomSystemNameGenerator nameGenerator = Mockito.mock(RandomSystemNameGenerator.class);
        Coordinate coordinate = Mockito.mock(Coordinate.class);
        Mockito.when(nameGenerator.generate()).thenReturn("Generated Solar System Sun name");
        Mockito.when(coordinate.getX()).thenReturn(10);
        Mockito.when(coordinate.getY()).thenReturn(15);
        Sun sun = new Sun(coordinate, nameGenerator);

        assertEquals("Generated Solar System Sun name", sun.getName());
        assertEquals(10, sun.getCenterX());
        assertEquals(15, sun.getCenterY());
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testSunShouldBeConvertedIntoString() {
        Coordinate coordinate = Mockito.mock(Coordinate.class);
        Mockito.when(coordinate.getX()).thenReturn(10);
        Mockito.when(coordinate.getY()).thenReturn(15);
        Sun sun = new Sun(coordinate, "Sun");

        assertEquals("Sun X:10 Y:15", sun.toString());
    }

    @Test
    @Category(org.openRealmOfStars.BehaviourTest.class)
    public void testSunCoordinateShouldChangeableWithSideEffect() {
        Coordinate sunCoordinate = new Coordinate(10, 15);
        Sun sun = new Sun(sunCoordinate, "Sun");

        sunCoordinate.setX(5);
        sunCoordinate.setY(10);

        assertNotEquals(sun.getCenterCoordinate(), sunCoordinate);

        Coordinate getSunCoordinate = sun.getCenterCoordinate();
        getSunCoordinate.setX(5);
        getSunCoordinate.setY(10);

        assertNotEquals(sun.getCenterCoordinate(), getSunCoordinate);
    }

}
