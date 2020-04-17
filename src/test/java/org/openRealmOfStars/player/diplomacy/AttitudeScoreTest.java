package org.openRealmOfStars.player.diplomacy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* JUnit Attitude scoring
*/
public class AttitudeScoreTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    AttitudeScore score = new AttitudeScore(Attitude.DIPLOMATIC);
    score.setValue(5);
    assertEquals(Attitude.DIPLOMATIC, score.getAttitude());
    assertEquals(5, score.getValue());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSorting() {
    AttitudeScore score1 = new AttitudeScore(Attitude.DIPLOMATIC);
    score1.setValue(1);
    AttitudeScore score2 = new AttitudeScore(Attitude.AGGRESSIVE);
    score2.setValue(2);
    AttitudeScore score3 = new AttitudeScore(Attitude.BACKSTABBING);
    score3.setValue(4);
    AttitudeScore score4 = new AttitudeScore(Attitude.EXPANSIONIST);
    score4.setValue(8);
    ArrayList<AttitudeScore> list = new ArrayList<>();
    list.add(score1);
    list.add(score2);
    list.add(score3);
    list.add(score4);
    Collections.sort(list, Collections.reverseOrder());
    assertEquals(Attitude.EXPANSIONIST, list.get(0).getAttitude());
    assertEquals(Attitude.BACKSTABBING, list.get(1).getAttitude());
    assertEquals(Attitude.AGGRESSIVE, list.get(2).getAttitude());
    assertEquals(Attitude.DIPLOMATIC, list.get(3).getAttitude());
  }

}
