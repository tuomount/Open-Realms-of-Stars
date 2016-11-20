package org.openRealmOfStars.starMap;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
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
  public void testRoute() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(5);
    route.setEndY(5);
    route.setFtlSpeed(1);

    assertEquals("Estimated time should be 2 turns.", 2, route.timeEstimate());
    assertEquals("The X coordinate should be 3 at the beginning.", 3, route.getX());
    assertEquals("The Y coordinate should be 3 at the beginning.", 3, route.getY());
    assertEquals("The X coordinate should be 3 at the beginning.", 3, route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 3 at the beginning.", 3, route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 1.", 1, route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 1.", 1, route.getMy(), 0.1);
    assertEquals("The distance should be 2.", 2, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertFalse("The end shouldn't be reached.", route.isEndReached());

    route.makeNextMove();

    assertEquals("The X coordinate should be 4 after the first step.", 4, route.getX());
    assertEquals("The Y coordinate should be 4 after the first step.", 4, route.getY());
    assertEquals("The X coordinate should be 4 after the first step.", 4, route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 4 after the first step.", 4, route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 1.", 1, route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 1.", 1, route.getMy(), 0.1);
    assertEquals("The distance should be 1.", 1, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertFalse("The end shouldn't be reached.", route.isEndReached());

    route.makeNextMove();

    assertEquals("The X coordinate should be 5 after the second step.", 5, route.getX());
    assertEquals("The Y coordinate should be 5 after the second step.", 5, route.getY());
    assertEquals("The X coordinate should be 5 after the second step.", 5, route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 5 after the second step.", 5, route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 0.", 0, route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 0.", 0, route.getMy(), 0.1);
    assertEquals("The distance should be 0.", 0, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertTrue("The end should be reached.", route.isEndReached());
  }

  @Test
  public void testRoute2() {
    route.setStartX(3);
    route.setStartY(4);
    route.setEndX(5);
    route.setEndY(5);
    route.setFtlSpeed(1);

    assertEquals("Estimated time should be 2 turns.", 2, route.timeEstimate());
    assertEquals("The X coordinate should be 3 at the beginning.", 3, route.getX());
    assertEquals("The Y coordinate should be 3 at the beginning.", 4, route.getY());
    assertEquals("The X coordinate should be 3 at the beginning.", 3, route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 3 at the beginning.", 4, route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 1.", 1, route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 0.5.", 0.5, route.getMy(), 0.1);
    assertEquals("The distance should be 2.", 2, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertFalse("The end shouldn't be reached.", route.isEndReached());

    route.makeNextMove();

    assertEquals("The X coordinate should be 5 after the second step.", 4, route.getX());
    assertEquals("The Y coordinate should be 5 after the second step.", 5, route.getY());
    assertEquals("The X coordinate should be 5 after the second step.", 4, route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 5 after the second step.", 5, route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 1.", 1, route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 0.", 0, route.getMy(), 0.1);
    assertEquals("The distance should be 1.", 1, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertFalse("The end shouldn't be reached.", route.isEndReached());

    route.makeNextMove();

    assertEquals("The X coordinate should be 5 after the second step.", 5, route.getX());
    assertEquals("The Y coordinate should be 5 after the second step.", 5, route.getY());
    assertEquals("The X coordinate should be 5 after the second step.", 5, route.getStartX(), 0.1);
    assertEquals("The Y coordinate should be 5 after the second step.", 5, route.getStartY(), 0.1);
    assertEquals("The end X coordinate should be 5.", 5, route.getEndX(), 0.1);
    assertEquals("The end Y coordinate should be 5.", 5, route.getEndY(), 0.1);
    assertEquals("The speed should be 1.", 1, route.getFtlSpeed());
    assertEquals("The internal movement speed should be 0.", 0, route.getMx(), 0.1);
    assertEquals("The internal movement speed should be 0.", 0, route.getMy(), 0.1);
    assertEquals("The distance should be 0.", 0, route.getDistance());
    assertFalse("The route shouldn't be defending.", route.isDefending());
    assertFalse("The route shouldn't be fixing.", route.isFixing());
    assertTrue("The end should be reached.", route.isEndReached());
  }

  @Test
  public void testIsDefending() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(5);
    route.setEndY(5);
    route.setFtlSpeed(Route.ROUTE_DEFEND);

    assertTrue("The route should be defending.", route.isDefending());

    route.setFtlSpeed(1);
    assertFalse("The route shouldn't be defending when it is attacking.", route.isDefending());

    route.setFtlSpeed(Route.ROUTE_FIX);
    assertFalse("The route shouldn't be defending when it is fixing.", route.isDefending());
  }

  @Test
  public void testIsFixing() {
    route.setStartX(3);
    route.setStartY(3);
    route.setEndX(5);
    route.setEndY(5);
    route.setFtlSpeed(Route.ROUTE_FIX);

    assertTrue("The route should be fixing.", route.isFixing());

    route.setFtlSpeed(1);
    assertFalse("The route shouldn't be fixing when it is attacking.", route.isFixing());

    route.setFtlSpeed(Route.ROUTE_DEFEND);
    assertFalse("The route shouldn't be fixing when it is defending.", route.isFixing());
  }


  @Test
  public void testIsEndReachedShouldBeTrueWhenStartAndEndCoordinatesAreEquals() {
    assertTrue(route.isEndReached());

    //@TODO: Why is not reached end when defending but Coordinates are equals?
    route.setFtlSpeed(Route.ROUTE_DEFEND);
    assertFalse(route.isEndReached());

    //@TODO: Why is not reached end when fixing but Coordinates are equals?
    route.setFtlSpeed(Route.ROUTE_FIX);
    assertFalse(route.isEndReached());

    route.setEndX(2);
    route.setEndY(2);

    assertFalse(route.isEndReached());

    route.setFtlSpeed(Route.ROUTE_DEFEND);
    assertFalse(route.isEndReached());

    route.setFtlSpeed(Route.ROUTE_FIX);
    assertFalse(route.isEndReached());
  }

  @Test(expected = ArithmeticException.class)
  public void testTimeEstimateShouldThrowArithmeticException() {
    route.setFtlSpeed(Route.ROUTE_DEFEND);
    route.timeEstimate();
  }

}
