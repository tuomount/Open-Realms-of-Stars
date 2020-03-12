package org.openRealmOfStars.game.States;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.Votes;
import org.openRealmOfStars.starMap.vote.VotingType;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2020  Tuomo Untinen
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
* Test for VoteView class
*
*/
public class VoteViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasic() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = new PlayerList();
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN,4,0);
    playerList.addPlayer(info1);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.CENTAURS,4,1);
    playerList.addPlayer(info2);
    PlayerInfo info3 = new PlayerInfo(SpaceRace.GREYANS,4,2);
    playerList.addPlayer(info3);
    PlayerInfo info4 = new PlayerInfo(SpaceRace.MECHIONS,4,3);
    playerList.addPlayer(info4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Vote vote = new Vote(VotingType.BAN_NUCLEAR_WEAPONS, 4, 10);
    Votes votes = new Votes();
    votes.getVotes().add(vote);
    vote = new Vote(VotingType.GALACTIC_OLYMPIC_PARTICIPATE, 4, 5);
    votes.getVotes().add(vote);
    Mockito.when(map.getVotes()).thenReturn(votes);
    ActionListener listener = Mockito.mock(ActionListener.class);
    VoteView voteView = new VoteView(map, listener);
    ActionEvent action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_VOTE_YES);
    voteView.handleActions(action);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_VOTE_NO);
    voteView.handleActions(action);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_NEXT_VOTE);
    voteView.handleActions(action);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_PREV_VOTE);
    voteView.handleActions(action);
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testEmptyVotes() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = new PlayerList();
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN,4,0);
    playerList.addPlayer(info1);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.CENTAURS,4,1);
    playerList.addPlayer(info2);
    PlayerInfo info3 = new PlayerInfo(SpaceRace.GREYANS,4,2);
    playerList.addPlayer(info3);
    PlayerInfo info4 = new PlayerInfo(SpaceRace.MECHIONS,4,3);
    playerList.addPlayer(info4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    Votes votes = new Votes();
    Mockito.when(map.getVotes()).thenReturn(votes);
    ActionListener listener = Mockito.mock(ActionListener.class);
    new VoteView(map, listener);
  }

}
