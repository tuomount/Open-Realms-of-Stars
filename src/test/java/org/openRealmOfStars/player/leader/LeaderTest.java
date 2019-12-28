package org.openRealmOfStars.player.leader;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2019 Tuomo Untinen
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
 * Test for Leader class
 */
public class LeaderTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Leader leader = new Leader("Test");
    assertEquals("Test", leader.getName());
    assertEquals(28, leader.getAge());
    assertEquals(Gender.NONE, leader.getGender());
    assertEquals(MilitaryRank.CIVILIAN, leader.getMilitaryRank());
    assertEquals(SpaceRace.HUMAN, leader.getRace());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setTitle("King of the world");
    assertEquals("King of the world", leader.getTitle());
    leader.setExperience(50);
    assertEquals(50, leader.getExperience());
    leader.setLevel(5);
    assertEquals(5, leader.getLevel());
    leader.setGender(Gender.FEMALE);
    assertEquals(Gender.FEMALE, leader.getGender());
    leader.setAge(33);
    assertEquals(33, leader.getAge());
    leader.setHomeworld("Planet I");
    assertEquals("Planet I", leader.getHomeworld());
    leader.setMilitaryRank(MilitaryRank.COLONEL);
    assertEquals(MilitaryRank.COLONEL, leader.getMilitaryRank());
    assertEquals("Leader [name=Test, homeworld=Planet I, age=33,"
        + " level=5, experience=50, militaryRank=Colonel, gender=female,"
        + " race=HUMAN, title=King of the world]", leader.toString());
  }
}
