package org.openRealmOfStars.starMap.vote;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

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
* Votes JUnit test
*
*/
public class VotesTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Votes votes = new Votes();
    assertNotNull(votes.getVotes());
    assertEquals(null, votes.getNextImportantVote());
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.BAN_NUCLEAR_WEAPONS);
    Mockito.when(vote.getTurnsToVote()).thenReturn(5);
    votes.getVotes().add(vote);
    assertEquals(vote, votes.getNextImportantVote());
  }

}
