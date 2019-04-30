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

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateNextVotes4Left() {
    Votes votes = new Votes();
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.FIRST_CANDIDATE);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getOrganizerIndex()).thenReturn(1);
    votes.getVotes().add(vote);
    Vote result = votes.generateNextVote(4, 8, 20);
    assertNotEquals(VotingType.GALACTIC_OLYMPIC_PARTICIPATE, result.getType());
    assertNotEquals(VotingType.FIRST_CANDIDATE, result.getType());
    assertNotEquals(VotingType.SECOND_CANDIDATE, result.getType());
    assertNotEquals(VotingType.RULER_OF_GALAXY, result.getType());
    assertNotEquals(VotingType.SECOND_CANDIDATE_MILITARY, result.getType());
    
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateNextVotes3Left() {
    Votes votes = new Votes();
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.FIRST_CANDIDATE);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getOrganizerIndex()).thenReturn(1);
    votes.getVotes().add(vote);
    Vote vote2 = Mockito.mock(Vote.class);
    Mockito.when(vote2.getType()).thenReturn(VotingType.BAN_PRIVATEER_SHIPS);
    Mockito.when(vote2.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote2.getOrganizerIndex()).thenReturn(1);
    votes.getVotes().add(vote2);
    Vote result = votes.generateNextVote(4, 8, 20);
    assertNotEquals(VotingType.GALACTIC_OLYMPIC_PARTICIPATE, result.getType());
    assertNotEquals(VotingType.FIRST_CANDIDATE, result.getType());
    assertNotEquals(VotingType.SECOND_CANDIDATE, result.getType());
    assertNotEquals(VotingType.RULER_OF_GALAXY, result.getType());
    assertNotEquals(VotingType.SECOND_CANDIDATE_MILITARY, result.getType());
    assertNotEquals(VotingType.BAN_PRIVATEER_SHIPS, result.getType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateNextVotes2Left() {
    Votes votes = new Votes();
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.FIRST_CANDIDATE);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getOrganizerIndex()).thenReturn(1);
    votes.getVotes().add(vote);
    Vote vote2 = Mockito.mock(Vote.class);
    Mockito.when(vote2.getType()).thenReturn(VotingType.BAN_PRIVATEER_SHIPS);
    Mockito.when(vote2.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote2.getOrganizerIndex()).thenReturn(1);
    votes.getVotes().add(vote2);
    Vote vote3 = Mockito.mock(Vote.class);
    Mockito.when(vote3.getType()).thenReturn(VotingType.TAXATION_OF_RICHEST_REALM);
    Mockito.when(vote3.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote3.getOrganizerIndex()).thenReturn(1);
    votes.getVotes().add(vote3);
    Vote result = votes.generateNextVote(4, 8, 20);
    assertEquals(VotingType.SECOND_CANDIDATE_MILITARY, result.getType());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGenerateNextVotes1Left() {
    Votes votes = new Votes();
    Vote vote = Mockito.mock(Vote.class);
    Mockito.when(vote.getType()).thenReturn(VotingType.FIRST_CANDIDATE);
    Mockito.when(vote.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote.getOrganizerIndex()).thenReturn(1);
    votes.getVotes().add(vote);
    Vote vote2 = Mockito.mock(Vote.class);
    Mockito.when(vote2.getType()).thenReturn(VotingType.BAN_PRIVATEER_SHIPS);
    Mockito.when(vote2.getTurnsToVote()).thenReturn(0);
    votes.getVotes().add(vote2);
    Vote vote3 = Mockito.mock(Vote.class);
    Mockito.when(vote3.getType()).thenReturn(VotingType.TAXATION_OF_RICHEST_REALM);
    Mockito.when(vote3.getTurnsToVote()).thenReturn(0);
    votes.getVotes().add(vote3);
    Vote vote4 = Mockito.mock(Vote.class);
    Mockito.when(vote4.getType()).thenReturn(VotingType.SECOND_CANDIDATE_MILITARY);
    Mockito.when(vote4.getTurnsToVote()).thenReturn(0);
    votes.getVotes().add(vote4);
    Vote vote5 = Mockito.mock(Vote.class);
    Mockito.when(vote5.getType()).thenReturn(VotingType.SECOND_CANDIDATE);
    Mockito.when(vote5.getTurnsToVote()).thenReturn(0);
    Mockito.when(vote5.getOrganizerIndex()).thenReturn(4);
    votes.getVotes().add(vote5);
    Vote result = votes.generateNextVote(4, 8, 20);
    assertEquals(VotingType.RULER_OF_GALAXY, result.getType());
    assertEquals(1, result.getOrganizerIndex());
    assertEquals(4, result.getSecondCandidateIndex());
  }

}
