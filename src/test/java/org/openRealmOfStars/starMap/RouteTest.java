package org.openRealmOfStars.starMap;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2023 Tuomo Untinen
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
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

/**
 * Test for Route class
 *
 */
public class RouteTest {

  private Route route;

  @Before
  public void setUp() {
    route = new Route(0, 0, 0, 0, 1);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRouteWhereNoDistance() {
    assertEquals(0, route.timeEstimate());
    assertEquals(0, route.getX());
    assertEquals(0, route.getY());
    assertEquals(0, route.getStartX(), 0.1);
    assertEquals(0, route.getStartY(), 0.1);
    assertEquals(0, route.getEndX(), 0.1);
    assertEquals(0, route.getEndY(), 0.1);
    assertEquals(1, route.getFtlSpeed());
    assertEquals(0, route.getMx(), 0.1);
    assertEquals(0, route.getMy(), 0.1);
    assertEquals(0, route.getDistance());
    assertFalse(route.isDefending());
    assertFalse(route.isFixing());
    assertTrue(route.isEndReached());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRoute() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(5);
    route.setEndY(5);
    route.setRawValue(1);

    assertEquals("Estimated time should be 2 star years.", 2, route.timeEstimate());
    assertEquals("The X coordinate should be 3 at the beginning.", 3,
        route.getX());
    assertEquals("The Y coordinate should be 3 at the beginning.", 3,
        route.getY());
    assertEquals("The X coordinate should be 3 at the beginning.", 3,
        route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 3 at the beginning.", 3,
        route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 1.", 1,
        route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 1.", 1,
        route.getMy(), 0.1);
    assertEquals("The distance should be 2.", 2, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertFalse("The end shouldn't be reached.", route.isEndReached());

    route.makeNextMove(null);

    assertEquals("The X coordinate should be 4 after the first step.", 4,
        route.getX());
    assertEquals("The Y coordinate should be 4 after the first step.", 4,
        route.getY());
    assertEquals("The X coordinate should be 4 after the first step.", 4,
        route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 4 after the first step.", 4,
        route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 1.", 1,
        route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 1.", 1,
        route.getMy(), 0.1);
    assertEquals("The distance should be 1.", 1, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertFalse("The end shouldn't be reached.", route.isEndReached());

    route.makeNextMove(null);

    assertEquals("The X coordinate should be 5 after the second step.", 5,
        route.getX());
    assertEquals("The Y coordinate should be 5 after the second step.", 5,
        route.getY());
    assertEquals("The X coordinate should be 5 after the second step.", 5,
        route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 5 after the second step.", 5,
        route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 0.", 0,
        route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 0.", 0,
        route.getMy(), 0.1);
    assertEquals("The distance should be 0.", 0, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertTrue("The end should be reached.", route.isEndReached());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRoute2() {
    route.setStartX(3);
    route.setStartY(4);
    route.setEndX(5);
    route.setEndY(5);
    route.setRawValue(1);

    assertEquals("Estimated time should be 2 star years.", 2, route.timeEstimate());
    assertEquals("The X coordinate should be 3 at the beginning.", 3,
        route.getX());
    assertEquals("The Y coordinate should be 3 at the beginning.", 4,
        route.getY());
    assertEquals("The X coordinate should be 3 at the beginning.", 3,
        route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 3 at the beginning.", 4,
        route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 1.", 1,
        route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 0.5.", 0.5,
        route.getMy(), 0.1);
    assertEquals("The distance should be 2.", 2, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertFalse("The end shouldn't be reached.", route.isEndReached());

    route.makeNextMove(null);

    assertEquals("The X coordinate should be 5 after the second step.", 4,
        route.getX());
    assertEquals("The Y coordinate should be 5 after the second step.", 5,
        route.getY());
    assertEquals("The X coordinate should be 5 after the second step.", 4,
        route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 5 after the second step.", 5,
        route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 1.", 1, route.getMx(),
        0.1);
    assertEquals("The internal movement speed should be 0.", 0, route.getMy(),
        0.1);
    assertEquals("The distance should be 1.", 1, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertFalse("The end shouldn't be reached.", route.isEndReached());

    route.makeNextMove(null);

    assertEquals("The X coordinate should be 5 after the second step.", 5,
        route.getX());
    assertEquals("The Y coordinate should be 5 after the second step.", 5,
        route.getY());
    assertEquals("The X coordinate should be 5 after the second step.", 5,
        route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 5 after the second step.", 5,
        route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getRawValue());
    assertEquals("The internal movement speed should be 0.", 0, route.getMx(),
        0.1);
    assertEquals("The internal movement speed should be 0.", 0, route.getMy(),
        0.1);
    assertEquals("The distance should be 0.", 0, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertTrue("The end should be reached.", route.isEndReached());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRoute3() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(3);
    route.setEndY(6);
    route.setFtlSpeed(3);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.isBlocked(3, 3)).thenReturn(false);
    Mockito.when(map.isBlocked(3, 4)).thenReturn(false);
    Mockito.when(map.isBlocked(3, 5)).thenReturn(true);
    Mockito.when(map.isBlocked(3, 6)).thenReturn(false);
    Mockito.when(map.isBlocked(3, 7)).thenReturn(false);
    assertEquals(false, route.makeNextMove(map));
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(3);
    route.setEndY(6);
    route.setFtlSpeed(3);
    Mockito.when(map.isBlocked(3, 5)).thenReturn(false);
    assertEquals(true, route.makeNextMove(map));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRouteLengthNormal() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(3);
    route.setEndY(6);
    route.setFtlSpeed(3);
    assertEquals(3, route.getDistance());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRouteLengthMultiPoint2() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(3);
    route.setEndY(6);
    route.addNewPoint(new Coordinate(3,9));
    route.setFtlSpeed(3);
    assertEquals(6, route.getDistance());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRouteLengthMultiPoint3() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(7);
    route.setEndY(6);
    route.addNewPoint(new Coordinate(12,6));
    route.setFtlSpeed(3);
    assertEquals(9, route.getDistance());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRegularRoute3() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(3);
    route.setEndY(6);
    route.setRegularSpeed(3);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.isBlocked(3, 3)).thenReturn(false);
    Mockito.when(map.isBlocked(3, 4)).thenReturn(true);
    Mockito.when(map.isBlocked(3, 5)).thenReturn(false);
    Mockito.when(map.isBlocked(3, 6)).thenReturn(false);
    Mockito.when(map.isBlocked(3, 7)).thenReturn(false);
    assertEquals(false, route.makeNextMove(map));
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(3);
    route.setEndY(6);
    route.setRegularSpeed(3);
    Mockito.when(map.isBlocked(3, 4)).thenReturn(false);
    assertEquals(true, route.makeNextMove(map));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRawValueRoute() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(3);
    route.setEndY(6);
    route.setRawValue(3);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.isBlocked(3, 3)).thenReturn(false);
    Mockito.when(map.isBlocked(3, 4)).thenReturn(false);
    Mockito.when(map.isBlocked(3, 5)).thenReturn(true);
    Mockito.when(map.isBlocked(3, 6)).thenReturn(false);
    Mockito.when(map.isBlocked(3, 7)).thenReturn(false);
    assertEquals(false, route.makeNextMove(map));
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(3);
    route.setEndY(6);
    route.setRawValue(3);
    Mockito.when(map.isBlocked(3, 5)).thenReturn(false);
    assertEquals(true, route.makeNextMove(map));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsDefending() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(5);
    route.setEndY(5);
    route.setRawValue(Route.ROUTE_DEFEND);
    assertTrue("The route should be defending.", route.isDefending());
    assertEquals(0, route.getFtlSpeed());
    assertEquals(0, route.getRegularSpeed());

    route.setRawValue(1);
    assertFalse("The route shouldn't be defending when it is attacking.",
        route.isDefending());
    assertEquals(1, route.getFtlSpeed());
    assertEquals(0, route.getRegularSpeed());

    route.setRawValue(Route.ROUTE_FIX);
    assertFalse("The route shouldn't be defending when it is fixing.",
        route.isDefending());
    assertEquals(0, route.getFtlSpeed());
    assertEquals(0, route.getRegularSpeed());
    route.setRegularSpeed(2);
    assertEquals(0, route.getFtlSpeed());
    assertEquals(2, route.getRegularSpeed());
    assertFalse("The route shouldn't be defending when it is attacking.",
        route.isDefending());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsBombing() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(5);
    route.setEndY(5);
    route.setRawValue(Route.ROUTE_BOMBED);
    assertTrue("The route should be bombed.", route.isBombing());
    assertEquals(0, route.getFtlSpeed());
    assertEquals(0, route.getRegularSpeed());

    route.setRawValue(1);
    assertFalse("The route shouldn't be bombed when it is attacking.",
        route.isBombing());
    assertEquals(1, route.getFtlSpeed());
    assertEquals(0, route.getRegularSpeed());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsFixing() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(5);
    route.setEndY(5);
    route.setRawValue(Route.ROUTE_FIX);

    assertTrue("The route should be fixing.", route.isFixing());

    route.setRawValue(1);
    assertFalse("The route shouldn't be fixing when it is attacking.",
        route.isFixing());

    route.setRawValue(Route.ROUTE_DEFEND);
    assertFalse("The route shouldn't be fixing when it is defending.",
        route.isFixing());
  }


  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIsEndReached() {
    assertTrue(route.isEndReached());

    // Why is not reached end when defending but Coordinates are equals?
    // If route is ended it will set route to null when updating Starmap
    // to next turn. This is checked before handling the defend or fix route
    route.setRawValue(Route.ROUTE_DEFEND);
    assertFalse(route.isEndReached());

    // Why is not reached end when defending but Coordinates are equals?
    // If route is ended it will set route to null when updating Starmap
    // to next turn. This is checked before handling the defend or fix route
    route.setRawValue(Route.ROUTE_FIX);
    assertFalse(route.isEndReached());

    route.setEndX(2);
    route.setEndY(2);

    assertFalse(route.isEndReached());

    route.setRawValue(Route.ROUTE_DEFEND);
    assertFalse(route.isEndReached());

    route.setRawValue(Route.ROUTE_FIX);
    assertFalse(route.isEndReached());
  }

}
