package org.openRealmOfStars.starMap;

import static org.junit.Assert.*;

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

  @Test
  /**
   * Test route calculations
   */
  public void testRoute() {
    Route route = new Route(3, 3, 5, 5, 1);
    assertEquals(2, route.timeEstimate());
    int step = 0;
    while (!route.isEndReached()) {
      if (step == 0) {
        assertEquals(3,route.getX());
        assertEquals(3,route.getY());
      }
      if (step == 1) {
        assertEquals(4,route.getX());
        assertEquals(4,route.getY());
      }
      step++;
      route.makeNextMove();
    }
    if (step == 2) {
      assertEquals(5,route.getX());
      assertEquals(5,route.getY());
    }
    route = new Route(2, 2, 8, 8, 2);
    assertEquals(3, route.timeEstimate());
    step = 0;
    while (!route.isEndReached()) {
      if (step == 0) {
        assertEquals(2,route.getX());
        assertEquals(2,route.getY());
      }
      if (step == 1) {
        assertEquals(4,route.getX());
        assertEquals(4,route.getY());
      }
      if (step == 2) {
        assertEquals(6,route.getX());
        assertEquals(6,route.getY());
      }
      step++;
      route.makeNextMove();
    }
    if (step == 3) {
      assertEquals(8,route.getX());
      assertEquals(8,route.getY());
    }
    route = new Route(9, 9, 2, 5, 1);
    assertEquals(7, route.timeEstimate());
    step = 0;
    while (!route.isEndReached()) {
      System.out.println("Step:"+step+" X: "+route.getX()+" Y: "+route.getY());
      if (step == 0) {
        assertEquals(9,route.getX());
        assertEquals(9,route.getY());
      }
      if (step == 1) {
        assertEquals(8,route.getX());
        assertEquals(8,route.getY());
      }
      if (step == 2) {
        assertEquals(7,route.getX());
        assertEquals(8,route.getY());
      }
      if (step == 3) {
        assertEquals(6,route.getX());
        assertEquals(7,route.getY());
      }
      if (step == 4) {
        assertEquals(5,route.getX());
        assertEquals(7,route.getY());
      }
      if (step == 5) {
        assertEquals(4,route.getX());
        assertEquals(6,route.getY());
      }
      if (step == 6) {
        assertEquals(3,route.getX());
        assertEquals(6,route.getY());
      }
      step++;
      route.makeNextMove();
    }
    if (step == 7) {
      assertEquals(2,route.getX());
      assertEquals(5,route.getY());
    }
  }

  @Test
  /**
   * Test defending route
   */
  public void testIsDefending() {
    Route route = new Route(3, 3, 5, 5, 0);
    assertEquals(true,route.isDefending());
    route = new Route(3, 3, 5, 5, 1);
    assertEquals(false,route.isDefending());
    route = new Route(3, 3, 5, 5, Route.ROUTE_FIX);
    assertEquals(false,route.isDefending());
  }
  /**
   * Test fixing route
   */
  public void testIsFixing() {
    Route route = new Route(3, 3, 5, 5, Route.ROUTE_FIX);
    assertEquals(true,route.isFixing());
    route = new Route(3, 3, 5, 5, 1);
    assertEquals(false,route.isFixing());
    route = new Route(3, 3, 5, 5, Route.ROUTE_DEFEND);
    assertEquals(false,route.isFixing());
  }
}
