package org.openRealmOfStars.starMap.newsCorp.scoreBoard;

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
* Score board single row
*
*/
public class Row implements Comparable<Row> {

  /**
   * Score for the row
   */
  private int score;

  /**
   * Realm indexes in row
   */
  private int[] realmIndexes;

  /**
   * Constructor for single realm in row
   * @param score Score of the realm
   * @param index Realm index
   */
  public Row(final int score, final int index) {
    this.score = score;
    realmIndexes = new int[1];
    realmIndexes[0] = index;
  }

  /**
   * Constructor for two realsm in alliance in row
   * @param score Score of the realm
   * @param index Realm index
   * @param alliance Realm index with alliance
   */
  public Row(final int score, final int index, final int alliance) {
    this.score = score;
    realmIndexes = new int[2];
    realmIndexes[0] = index;
    realmIndexes[1] = alliance;
  }

  /**
   * Get row score
   * @return Score
   */
  public int getScore() {
    return score;
  }

  @Override
  public int compareTo(final Row o) {
    return score - o.getScore();
  }

  /**
   * Is alliance on row
   * @return True if alliance on row
   */
  public boolean isAlliance() {
    if (realmIndexes.length > 1) {
      return true;
    }
    return false;
  }

  /**
   * Get realm index
   * @return Realm index
   */
  public int getRealm() {
    return realmIndexes[0];
  }

  /**
   * Get alliance realm index
   * @return Realm index or -1 not alliance
   */
  public int getAllianceRealm() {
    if (isAlliance()) {
      return realmIndexes[1];
    }
    return -1;
  }

}
