package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020-2024 Tuomo Untinen
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;

/**
*
* Research view for handling researching technology for player
*
*/
public class LeaderViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testEmptyPool() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = new PlayerList();
    PlayerInfo info1 = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"),
        4, 0);
    info1.setEmpireName("Human Empire");
    playerList.addPlayer(info1);
    PlayerInfo info2 = new PlayerInfo(SpaceRaceFactory.createOne("CENTAURS"),
        4, 1);
    info2.setEmpireName("Centaur Empire");
    playerList.addPlayer(info2);
    PlayerInfo info3 = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"),
        4, 2);
    info3.setEmpireName("Greyan Kingdom");
    playerList.addPlayer(info3);
    PlayerInfo info4 = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"),
        4, 3);
    info4.setEmpireName("Mechion AI");
    playerList.addPlayer(info4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    ActionListener listener = Mockito.mock(ActionListener.class);
    LeaderView view = new LeaderView(info1, map, listener);
    view.updatePanel();
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTwoLeaders() {
    StarMap map = Mockito.mock(StarMap.class);
    PlayerList playerList = new PlayerList();
    Planet planet = new Planet(new Coordinate(5, 6), "Test planet", 1, false);
    planet.setRadiationLevel(RadiationType.NO_RADIATION);
    PlayerInfo info1 = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"),
        4, 0);
    info1.setEmpireName("Human Empire");
    Leader leader = LeaderUtility.createLeader(info1, planet, 1);
    info1.getLeaderPool().add(leader);
    leader = LeaderUtility.createLeader(info1, planet, 1);
    info1.getLeaderPool().add(leader);
    playerList.addPlayer(info1);
    PlayerInfo info2 = new PlayerInfo(SpaceRaceFactory.createOne("CENTAURS"),
        4, 1);
    info2.setEmpireName("Centaur Empire");
    playerList.addPlayer(info2);
    PlayerInfo info3 = new PlayerInfo(SpaceRaceFactory.createOne("GREYANS"),
        4, 2);
    info3.setEmpireName("Greyan Kingdom");
    playerList.addPlayer(info3);
    PlayerInfo info4 = new PlayerInfo(SpaceRaceFactory.createOne("MECHIONS"),
        4, 3);
    info4.setEmpireName("Mechion AI");
    playerList.addPlayer(info4);
    Mockito.when(map.getPlayerList()).thenReturn(playerList);
    ActionListener listener = Mockito.mock(ActionListener.class);
    LeaderView view = new LeaderView(info1, map, listener);
    view.setPlanet(planet);
    view.updatePanel();
    ActionEvent action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_ASSIGN_LEADER);
    view.handleActions(action);
  }

}
