package org.openRealmOfStars.starMap.newsCorp.scoreBoard;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Score board for handling game victory
*
*/
public class ScoreBoardTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    ScoreBoard board = new ScoreBoard();
    Row a = new Row(7, 0);
    Row b = new Row(3, 1);
    Row c = new Row(9, 2);
    Row d = new Row(13, 3);
    Row e = new Row(10, 1,0);
    board.add(a);
    board.add(b);
    board.add(c);
    board.add(d);
    board.add(e);
    assertEquals(4, board.getNumberOfRows());
    board.sort();
    assertEquals(d, board.getRow(0));
    assertEquals(c, board.getRow(1));
    assertEquals(a, board.getRow(2));
    assertEquals(b, board.getRow(3));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic2() {
    ScoreBoard board = new ScoreBoard();
    Row a = new Row(7, 0);
    Row b = new Row(3, 1);
    Row c = new Row(9, 2);
    Row d = new Row(13, 3);
    Row e = new Row(10, 1,0);
    board.add(e);
    board.add(a);
    board.add(b);
    board.add(c);
    board.add(d);
    assertEquals(3, board.getNumberOfRows());
    board.sort();
    assertEquals(d, board.getRow(0));
    assertEquals(e, board.getRow(1));
    assertEquals(c, board.getRow(2));
    assertEquals(
        "[Realm: 3 Score: 13,Realm: 1 Alliance: 0 Score: 10,Realm: 2 Score: 9]",
        board.toString());
  }

}
