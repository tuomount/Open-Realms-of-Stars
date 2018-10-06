package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionList;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.GameLengthState;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017, 2018 Tuomo Untinen
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
* AiTurnView tests
*
*/
public class AiTurnViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testAddAttackMission() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    Game game = new Game(false);
    game.setLoadedGame(starMap);
    AITurnView aiTurnView = new AITurnView(game);
    PlayerInfo info = starMap.getPlayerByIndex(1);
    Planet planet = starMap.getPlanetList().get(0);
    Mission mission = info.getMissions().getMission(MissionType.ATTACK,
        MissionPhase.PLANNING);
    assertEquals(null, mission);
    aiTurnView.addAttackMission(planet, info);
    mission = info.getMissions().getMission(MissionType.ATTACK,
        MissionPhase.PLANNING);
    assertNotEquals(null, mission);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAddGatherMission() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = Mockito.mock(Mission.class);
    MissionList list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.AGGRESSIVE);
    Mockito.when(mission.getType()).thenReturn(MissionType.ATTACK);
    AITurnView.addGatherMission(info, mission);
    Mission listMission = list.getMissionByIndex(0);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.ASSAULT_TYPE, listMission.getShipType());
    assertEquals(7, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.MILITARISTIC);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(5);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.ASSAULT_TYPE, listMission.getShipType());
    assertEquals(6, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.EXPANSIONIST);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(5);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.TROOPER_TYPE, listMission.getShipType());
    assertEquals(6, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.BACKSTABBING);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(4);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.ASSAULT_TYPE, listMission.getShipType());
    assertEquals(5, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.LOGICAL);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(4);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.TROOPER_TYPE, listMission.getShipType());
    assertEquals(5, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.DIPLOMATIC);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(4);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.ASSAULT_TYPE, listMission.getShipType());
    assertEquals(5, list.getSize());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdatePirate() {
    Game game = Mockito.mock(Game.class);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(game.getStarMap()).thenReturn(map);
    AITurnView view = new AITurnView(game);
    int level = 3;
    PlayerInfo pirates = new PlayerInfo(SpaceRace.SPACE_PIRATE, 9, 8);
    Tech tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() <= 2);
    assertEquals(false, view.updateSpacePirates(pirates, level, false));
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() == 3);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.isStarBaseDeployed()).thenReturn(true);
    pirates.getFleets().add(fleet);
    assertEquals(true, view.updateSpacePirates(pirates, level, false));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdatePirate2() {
    Game game = Mockito.mock(Game.class);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(game.getStarMap()).thenReturn(map);
    AITurnView view = new AITurnView(game);
    PlayerInfo pirates = new PlayerInfo(SpaceRace.SPACE_PIRATE, 9, 8);
    Tech tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() <= 2);
    GameLengthState state = GameLengthState.EARLY_GAME;
    view.updateSpacePirates(pirates, state, false);
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() >= 2);
    state = GameLengthState.MIDDLE_GAME;
    assertEquals(false, view.updateSpacePirates(pirates, state, false));
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() == 5);
    state = GameLengthState.LATE_GAME;
    view.updateSpacePirates(pirates, state, false);
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() == 6);
    state = GameLengthState.END_GAME;
    view.updateSpacePirates(pirates, state, false);
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() == 8);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.isStarBaseDeployed()).thenReturn(true);
    pirates.getFleets().add(fleet);
    assertEquals(true, view.updateSpacePirates(pirates, state, false));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAddGatherMission2() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mission mission = Mockito.mock(Mission.class);
    MissionList list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.AGGRESSIVE);
    Mockito.when(mission.getType()).thenReturn(MissionType.DESTROY_STARBASE);
    AITurnView.addGatherMission(info, mission);
    Mission listMission = list.getMissionByIndex(0);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.ASSAULT_SB_TYPE, listMission.getShipType());
    assertEquals(3, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.LOGICAL);
    list.remove(listMission);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(0);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.ASSAULT_SB_TYPE, listMission.getShipType());
    assertEquals(4, list.getSize());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testLowCreditFlow() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HOMARIANS);
    assertEquals(0, info.getMsgList().getFullList().size());
    int creditFlow = -1;
    info.setTotalCredits(4);
    AITurnView.handleLowCreditWarning(info, creditFlow);
    assertEquals(1, info.getMsgList().getFullList().size());
  }

}
