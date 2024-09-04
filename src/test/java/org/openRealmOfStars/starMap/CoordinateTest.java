package org.openRealmOfStars.starMap;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2018 Tuomo Untinen
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

import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;

/**
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
  public void testCreateCoordinateDirectionCloseUp() {
    int x = 10;
    int y = 15;
    Coordinate coordinate = new Coordinate(x, y);
    int mx = -1;
    int my = 0;
    Coordinate coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
    mx = -1;
    my = -1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_LEFT, coordinate.getPosition(coord));
    mx = 0;
    my = -1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 1;
    my = -1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_RIGHT, coordinate.getPosition(coord));
    mx = 1;
    my = 0;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = 1;
    my = 1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_RIGHT, coordinate.getPosition(coord));
    mx = 0;
    my = 1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = -1;
    my = 1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_LEFT, coordinate.getPosition(coord));
    mx = 0;
    my = 0;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.NONE, coordinate.getPosition(coord));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateCoordinateDirection2Away() {
    int x = 10;
    int y = 15;
    Coordinate coordinate = new Coordinate(x, y);
    int mx = -2;
    int my = 0;
    Coordinate coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
    mx = -2;
    my = -2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_LEFT, coordinate.getPosition(coord));
    mx = 0;
    my = -2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 2;
    my = -2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_RIGHT, coordinate.getPosition(coord));
    mx = 2;
    my = 0;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = 2;
    my = 2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_RIGHT, coordinate.getPosition(coord));
    mx = 0;
    my = 2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = -2;
    my = 2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_LEFT, coordinate.getPosition(coord));
    mx = -1;
    my = -2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 1;
    my = 2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = 2;
    my = 1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = -2;
    my = -1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateCoordinateDirection3Away() {
    int x = 10;
    int y = 15;
    Coordinate coordinate = new Coordinate(x, y);
    int mx = -3;
    int my = 0;
    Coordinate coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
    mx = -3;
    my = -3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_LEFT, coordinate.getPosition(coord));
    mx = 0;
    my = -3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 3;
    my = -3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_RIGHT, coordinate.getPosition(coord));
    mx = 3;
    my = 0;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = 3;
    my = 3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_RIGHT, coordinate.getPosition(coord));
    mx = 0;
    my = 3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = -3;
    my = 3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_LEFT, coordinate.getPosition(coord));
    mx = -1;
    my = -3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 1;
    my = 3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = 3;
    my = 1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = -3;
    my = -1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
    mx = -2;
    my = -3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 2;
    my = 3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = 3;
    my = 2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = -3;
    my = -2;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateCoordinateDirection4Away() {
    int x = 10;
    int y = 15;
    Coordinate coordinate = new Coordinate(x, y);
    int mx = -4;
    int my = 0;
    Coordinate coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
    mx = -4;
    my = -4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_LEFT, coordinate.getPosition(coord));
    mx = 0;
    my = -4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 4;
    my = -4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_RIGHT, coordinate.getPosition(coord));
    mx = 4;
    my = 0;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = 4;
    my = 4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_RIGHT, coordinate.getPosition(coord));
    mx = 0;
    my = 4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = -4;
    my = 4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_LEFT, coordinate.getPosition(coord));
    mx = -1;
    my = -4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 1;
    my = 4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = 4;
    my = 1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = -4;
    my = -1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
    mx = -3;
    my = -4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_LEFT, coordinate.getPosition(coord));
    mx = 3;
    my = 4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_RIGHT, coordinate.getPosition(coord));
    mx = 4;
    my = 3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_RIGHT, coordinate.getPosition(coord));
    mx = -4;
    my = -3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_LEFT, coordinate.getPosition(coord));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateCoordinateDirection6Away() {
    int x = 10;
    int y = 15;
    Coordinate coordinate = new Coordinate(x, y);
    int mx = -6;
    int my = 0;
    Coordinate coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
    mx = -6;
    my = -6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_LEFT, coordinate.getPosition(coord));
    mx = 0;
    my = -6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 6;
    my = -6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_RIGHT, coordinate.getPosition(coord));
    mx = 6;
    my = 0;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = 6;
    my = 6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_RIGHT, coordinate.getPosition(coord));
    mx = 0;
    my = 6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = -6;
    my = 6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_LEFT, coordinate.getPosition(coord));
    mx = -1;
    my = -6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 1;
    my = 6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = 6;
    my = 1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = -6;
    my = -1;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
    mx = -3;
    my = -6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP, coordinate.getPosition(coord));
    mx = 3;
    my = 6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN, coordinate.getPosition(coord));
    mx = 6;
    my = 3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.RIGHT, coordinate.getPosition(coord));
    mx = -6;
    my = -3;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.LEFT, coordinate.getPosition(coord));
    mx = -4;
    my = -6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_LEFT, coordinate.getPosition(coord));
    mx = 4;
    my = 6;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_RIGHT, coordinate.getPosition(coord));
    mx = 6;
    my = 4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.DOWN_RIGHT, coordinate.getPosition(coord));
    mx = -6;
    my = -4;
    coord = new Coordinate(x + mx, y + my);
    assertEquals(Coordinate.UP_LEFT, coordinate.getPosition(coord));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateCoordinateUp() {
    int x = 10;
    int y = 15;
    Coordinate coordinate = new Coordinate(x, y);
    Coordinate target = coordinate.getDirection(Coordinate.UP);
    assertEquals(x, target.getX());
    assertEquals(y - 1, target.getY());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateCoordinateDown() {
    int x = 10;
    int y = 15;
    Coordinate coordinate = new Coordinate(x, y);
    Coordinate target = coordinate.getDirection(Coordinate.DOWN);
    assertEquals(x, target.getX());
    assertEquals(y + 1, target.getY());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateCoordinateRight() {
    int x = 10;
    int y = 15;
    Coordinate coordinate = new Coordinate(x, y);
    Coordinate target = coordinate.getDirection(Coordinate.RIGHT);
    assertEquals(x + 1, target.getX());
    assertEquals(y, target.getY());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCreateCoordinateLeft() {
    int x = 10;
    int y = 15;
    Coordinate coordinate = new Coordinate(x, y);
    Coordinate target = coordinate.getDirection(Coordinate.LEFT);
    assertEquals(x - 1, target.getX());
    assertEquals(y, target.getY());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testCoordinateMatching() {
    Coordinate originalCoordinate = new Coordinate(10, 15);
    Coordinate coordinate = new Coordinate(10,15);
    assertEquals(true, coordinate.sameAs(originalCoordinate));
    assertEquals(true, originalCoordinate.sameAs(coordinate));
    coordinate = new Coordinate(33,8);
    assertEquals(false, coordinate.sameAs(originalCoordinate));
    assertEquals(false, originalCoordinate.sameAs(coordinate));
  }
}
