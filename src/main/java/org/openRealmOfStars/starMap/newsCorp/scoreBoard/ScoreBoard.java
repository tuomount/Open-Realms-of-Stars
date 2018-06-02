package org.openRealmOfStars.starMap.newsCorp.scoreBoard;

import java.util.ArrayList;
import java.util.Collections;

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
public class ScoreBoard {

  /**
   * Scoreboard consisting of rows.
   */
  private ArrayList<Row> board;

  /**
   * Constructor for ScoreBoard.
   */
  public ScoreBoard() {
    board = new ArrayList<>();
  }

  /**
   * Add new row to board. Row is added only if any of the rows
   * realm is not in the board.
   * @param row New row to add.
   */
  public void add(final Row row) {
    boolean found = false;
    for (Row old : board) {
      if (!old.isSameRealm(row.getRealm())) {
        if (row.isAlliance() && old.isSameRealm(row.getAllianceRealm())) {
          found = true;
        }
      } else {
        found = true;
      }
    }
    if (!found) {
      board.add(row);
    }
  }

  /**
   * Get the number of rows in board
   * @return Number of rows
   */
  public int getNumberOfRows() {
    return board.size();
  }

  /**
   * Get Row from score board
   * @param index Row index
   * @return Row or null if illegal index
   */
  public Row getRow(final int index) {
    if (index >= 0 && index < board.size()) {
      return board.get(index);
    }
    return null;
  }

  /**
   * Sort the score board accoring the scores.
   * First one is the biggest score.
   */
  public void sort() {
    Collections.sort(board, Collections.reverseOrder());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < board.size(); i++) {
      Row row = board.get(i);
      sb.append(row.toString());
      if (i < board.size() - 1) {
        sb.append(",");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
