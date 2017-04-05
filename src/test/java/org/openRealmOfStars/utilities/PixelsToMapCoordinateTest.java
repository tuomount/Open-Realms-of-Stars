package org.openRealmOfStars.utilities;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.starMap.Coordinate;

import static org.junit.Assert.*;
/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017 Tuomo Untinen
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
 * Test for PixelsToMapCoordinate Utility
 *
 */
public class PixelsToMapCoordinateTest {


    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testMapCoordinates(){
      Coordinate coordinate = Mockito.mock(Coordinate.class);
      Mockito.when(coordinate.getX()).thenReturn(8);
      Mockito.when(coordinate.getY()).thenReturn(9);
      PixelsToMapCoordinate ptmCoordinate = new PixelsToMapCoordinate(
          coordinate, 120, 170, 10, 10, 8, 8, false);
      Coordinate coord = ptmCoordinate.getMapCoordinate();
      assertEquals(3,coord.getX());
      assertEquals(6,coord.getY());
      assertEquals(3,ptmCoordinate.getMapX());
      assertEquals(6,ptmCoordinate.getMapY());
      assertEquals(-5,ptmCoordinate.getRelativeX());
      assertEquals(-3,ptmCoordinate.getRelativeY());
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testMapCoordinatesCombat(){
      Coordinate coordinate = Mockito.mock(Coordinate.class);
      Mockito.when(coordinate.getX()).thenReturn(4);
      Mockito.when(coordinate.getY()).thenReturn(4);
      PixelsToMapCoordinate ptmCoordinate = new PixelsToMapCoordinate(
          coordinate, 120, 170, 10, 10, 4, 4, true);
      Coordinate coord = ptmCoordinate.getMapCoordinate();
      assertEquals(1,coord.getX());
      assertEquals(2,coord.getY());
      assertEquals(1,ptmCoordinate.getMapX());
      assertEquals(2,ptmCoordinate.getMapY());
      assertEquals(-3,ptmCoordinate.getRelativeX());
      assertEquals(-2,ptmCoordinate.getRelativeY());
    }

    @Test
    @Category(org.openRealmOfStars.UnitTest.class)
    public void testMapCoordinatesOutOfPanel(){
      Coordinate coordinate = Mockito.mock(Coordinate.class);
      Mockito.when(coordinate.getX()).thenReturn(4);
      Mockito.when(coordinate.getY()).thenReturn(4);
      PixelsToMapCoordinate ptmCoordinate = new PixelsToMapCoordinate(
          coordinate, 1200, 1700, 10, 10, 4, 4, true);
      Coordinate coord = ptmCoordinate.getMapCoordinate();
      assertTrue(ptmCoordinate.isOutOfPanel());
      assertEquals(-1,coord.getX());
      assertEquals(-1,coord.getY());
      assertEquals(-1,ptmCoordinate.getMapX());
      assertEquals(-1,ptmCoordinate.getMapY());
      assertEquals(-1,ptmCoordinate.getRelativeX());
      assertEquals(-1,ptmCoordinate.getRelativeY());
    }


}