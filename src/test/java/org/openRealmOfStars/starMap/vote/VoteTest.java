package org.openRealmOfStars.starMap.vote;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;

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
* Vote for single voting thing JUnit.
*
*/
public class VoteTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Vote vote = new Vote(VotingType.BAN_NUCLEAR_WEAPONS, 4, 10);
    assertEquals(0, vote.getOrganizerIndex());
    assertEquals("", vote.getPlanetName());
    assertEquals(VotingType.BAN_NUCLEAR_WEAPONS, vote.getType());
    assertEquals(10, vote.getTurnsToVote());
    assertEquals(VotingChoice.NOT_VOTED, vote.getChoice(0));
    assertEquals(VotingChoice.NOT_VOTED, vote.getChoice(1));
    assertEquals(VotingChoice.NOT_VOTED, vote.getChoice(2));
    assertEquals(VotingChoice.NOT_VOTED, vote.getChoice(3));
    assertEquals(0, vote.getNumberOfVotes(0));
    assertEquals(0, vote.getNumberOfVotes(1));
    assertEquals(0, vote.getNumberOfVotes(2));
    assertEquals(0, vote.getNumberOfVotes(3));
    vote.setNumberOfVotes(0, 2);
    vote.setNumberOfVotes(1, 4);
    vote.setNumberOfVotes(2, 6);
    vote.setNumberOfVotes(3, 8);
    vote.setChoice(0, VotingChoice.VOTED_YES);
    vote.setChoice(1, VotingChoice.VOTED_YES);
    vote.setChoice(2, VotingChoice.VOTED_YES);
    vote.setChoice(3, VotingChoice.VOTED_YES);
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(0));
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(1));
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(2));
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(3));
    assertEquals(2, vote.getNumberOfVotes(0));
    assertEquals(4, vote.getNumberOfVotes(1));
    assertEquals(6, vote.getNumberOfVotes(2));
    assertEquals(8, vote.getNumberOfVotes(3));
    vote.setChoice(0, VotingChoice.VOTED_NO);
    vote.setChoice(1, VotingChoice.VOTED_NO);
    vote.setChoice(2, VotingChoice.VOTED_NO);
    vote.setChoice(3, VotingChoice.VOTED_NO);
    assertEquals(VotingChoice.VOTED_NO, vote.getChoice(0));
    assertEquals(VotingChoice.VOTED_NO, vote.getChoice(1));
    assertEquals(VotingChoice.VOTED_NO, vote.getChoice(2));
    assertEquals(VotingChoice.VOTED_NO, vote.getChoice(3));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalacticOlympics() {
    Vote vote = new Vote(VotingType.GALACTIC_OLYMPIC_PARTICIPATE, 4, 10);
    assertEquals(0, vote.getOrganizerIndex());
    assertEquals("", vote.getPlanetName());
    vote.setOrganizerIndex(2);
    assertEquals(2, vote.getOrganizerIndex());
    vote.setPlanetName("Test I");
    assertEquals("Test I", vote.getPlanetName());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTypes() {
    assertEquals(VotingType.GALACTIC_OLYMPIC_PARTICIPATE, 
        VotingType.getTypeByIndex(0));
    assertEquals(VotingType.BAN_NUCLEAR_WEAPONS, 
        VotingType.getTypeByIndex(1));
    assertEquals(VotingType.BAN_PRIVATEER_SHIPS, 
        VotingType.getTypeByIndex(2));
    assertEquals(VotingType.GALACTIC_PEACE, 
        VotingType.getTypeByIndex(3));
    assertEquals(VotingType.TAXATION_OF_RICHEST_REALM, 
        VotingType.getTypeByIndex(4));
    assertEquals(VotingType.SECOND_CANDIDATE_MILITARY, 
        VotingType.getTypeByIndex(5));
    assertEquals(VotingType.RULER_OF_GALAXY, 
        VotingType.getTypeByIndex(6));
    assertEquals(VotingType.FIRST_CANDIDATE, 
        VotingType.getTypeByIndex(7));
    assertEquals(VotingType.SECOND_CANDIDATE, 
        VotingType.getTypeByIndex(8));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testDescriptions() {
    assertEquals(true, VotingType.GALACTIC_OLYMPIC_PARTICIPATE.
        getDescription().contains("Galactic Olympic"));
    assertEquals(true, VotingType.BAN_NUCLEAR_WEAPONS.
        getDescription().contains("Ban nuclear"));
    assertEquals(true, VotingType.BAN_PRIVATEER_SHIPS.
        getDescription().contains("Ban privateer"));
    assertEquals(true, VotingType.TAXATION_OF_RICHEST_REALM.
        getDescription().contains("Taxation of"));
    assertEquals(true, VotingType.GALACTIC_PEACE.
        getDescription().contains("Galactic wide"));
    assertEquals(true, VotingType.SECOND_CANDIDATE_MILITARY.
        getDescription().contains("Second"));
    assertEquals(true, VotingType.RULER_OF_GALAXY.
        getDescription().contains("Ruler of Galaxy"));
    assertEquals(true, VotingType.FIRST_CANDIDATE.
        getDescription().contains("Secretary of United Galaxy"));
    assertEquals(true, VotingType.SECOND_CANDIDATE.
        getDescription().contains("Challenger of United Galaxy"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTypes2() {
    assertEquals(0, VotingType.GALACTIC_OLYMPIC_PARTICIPATE.getIndex());
    assertEquals(1, VotingType.BAN_NUCLEAR_WEAPONS.getIndex());
    assertEquals(2, VotingType.BAN_PRIVATEER_SHIPS.getIndex());
    assertEquals(3, VotingType.GALACTIC_PEACE.getIndex());
    assertEquals(4, VotingType.TAXATION_OF_RICHEST_REALM.getIndex());
    assertEquals(5, VotingType.SECOND_CANDIDATE_MILITARY.getIndex());
    assertEquals(6, VotingType.RULER_OF_GALAXY.getIndex());
    assertEquals(7, VotingType.FIRST_CANDIDATE.getIndex());
    assertEquals(8, VotingType.SECOND_CANDIDATE.getIndex());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testVotingChoices() {
    assertEquals(0, VotingChoice.NOT_VOTED.getIndex());
    assertEquals(1, VotingChoice.VOTED_YES.getIndex());
    assertEquals(2, VotingChoice.VOTED_NO.getIndex());
    assertEquals(VotingChoice.NOT_VOTED, VotingChoice.getTypeByIndex(0));
    assertEquals(VotingChoice.VOTED_YES, VotingChoice.getTypeByIndex(1));
    assertEquals(VotingChoice.VOTED_NO, VotingChoice.getTypeByIndex(2));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSimulatedVoteResultYes() {
    Vote vote = new Vote(VotingType.BAN_PRIVATEER_SHIPS, 6, 10);
    vote.setChoice(0, VotingChoice.VOTED_YES);
    vote.setChoice(1, VotingChoice.VOTED_NO);
    vote.setChoice(2, VotingChoice.VOTED_YES);
    vote.setChoice(3, VotingChoice.VOTED_NO);
    vote.setChoice(4, VotingChoice.VOTED_YES);
    vote.setChoice(5, VotingChoice.VOTED_YES);
    vote.setNumberOfVotes(0, 6);
    vote.setNumberOfVotes(1, 12);
    vote.setNumberOfVotes(2, 3);
    vote.setNumberOfVotes(3, 8);
    vote.setNumberOfVotes(4, 7);
    vote.setNumberOfVotes(5, 9);
    assertEquals(VotingChoice.VOTED_YES, vote.getResult(2));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSimulatedVoteResultNo() {
    Vote vote = new Vote(VotingType.BAN_PRIVATEER_SHIPS, 6, 10);
    vote.setChoice(0, VotingChoice.VOTED_YES);
    vote.setChoice(1, VotingChoice.VOTED_NO);
    vote.setChoice(2, VotingChoice.VOTED_YES);
    vote.setChoice(3, VotingChoice.VOTED_NO);
    vote.setChoice(4, VotingChoice.VOTED_YES);
    vote.setChoice(5, VotingChoice.VOTED_YES);
    vote.setNumberOfVotes(0, 6);
    vote.setNumberOfVotes(1, 12);
    vote.setNumberOfVotes(2, 3);
    vote.setNumberOfVotes(3, 18);
    vote.setNumberOfVotes(4, 7);
    vote.setNumberOfVotes(5, 9);
    assertEquals(VotingChoice.VOTED_NO, vote.getResult(2));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSimulatedVoteResultDraw() {
    Vote vote = new Vote(VotingType.BAN_PRIVATEER_SHIPS, 6, 10);
    vote.setChoice(0, VotingChoice.VOTED_YES);
    vote.setChoice(1, VotingChoice.VOTED_NO);
    vote.setChoice(2, VotingChoice.VOTED_YES);
    vote.setChoice(3, VotingChoice.VOTED_NO);
    vote.setChoice(4, VotingChoice.VOTED_YES);
    vote.setChoice(5, VotingChoice.VOTED_YES);
    vote.setNumberOfVotes(0, 5);
    vote.setNumberOfVotes(1, 12);
    vote.setNumberOfVotes(2, 5);
    vote.setNumberOfVotes(3, 18);
    vote.setNumberOfVotes(4, 10);
    vote.setNumberOfVotes(5, 10);
    assertEquals(VotingChoice.VOTED_YES, vote.getResult(2));
  }


  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalacticPeaceDescription() {
    Vote vote = new Vote(VotingType.GALACTIC_PEACE, 4, 10);
    StarMap map = Mockito.mock(StarMap.class);
    assertEquals("Galactic wide peace", vote.getDescription(map));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRulerOfGalaxyDescription() {
    Vote vote = new Vote(VotingType.RULER_OF_GALAXY, 4, 10);
    vote.setOrganizerIndex(0);
    vote.setSecondCandidateIndex(1);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    PlayerInfo info1 = Mockito.mock(PlayerInfo.class);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info1.getEmpireName()).thenReturn("Realm of Test");
    Mockito.when(info2.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(playerList.getPlayerInfoByIndex(0)).thenReturn(info1);
    Mockito.when(playerList.getPlayerInfoByIndex(1)).thenReturn(info2);
    assertEquals("Ruler of Galaxy Realm of Test VS Empire of Test", vote.getDescription(map));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGamesParticipationDescription() {
    Vote vote = new Vote(VotingType.GALACTIC_OLYMPIC_PARTICIPATE, 4, 10);
    vote.setOrganizerIndex(0);
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    PlayerInfo info1 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info1.getEmpireName()).thenReturn("Realm of Test");
    Mockito.when(playerList.getPlayerInfoByIndex(0)).thenReturn(info1);
    assertEquals("Galactic Olympic participation organized by Realm of Test", vote.getDescription(map));
  }

}
