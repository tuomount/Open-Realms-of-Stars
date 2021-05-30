package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionList;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.AI.Research.Research;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.diplomacy.Diplomacy;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.starMap.PirateDifficultLevel;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.Votes;
import org.openRealmOfStars.starMap.vote.VotingType;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;
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
    PlayerInfo pirates = new PlayerInfo(SpaceRace.SPACE_PIRATE, 9, 8);
    Tech tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() <= 2);
    assertEquals(false, view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true));
    assertEquals(false, view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, false));
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() >= 2);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.isStarBaseDeployed()).thenReturn(true);
    pirates.getFleets().add(fleet);
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.NORMAL, false));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBannedShips() {
    Game game = Mockito.mock(Game.class);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(game.getStarMap()).thenReturn(map);
    AITurnView view = new AITurnView(game);
    PlayerInfo human = new PlayerInfo(SpaceRace.HUMAN, 9, 8);
    human.getTechList().addTech(TechFactory.createHullTech("Privateer Mk1", 5));
    human.getTechList().addTech(TechFactory.createHullTech("Cruiser", 5));
    human.getTechList().addTech(TechFactory.createCombatTech("Orbital nuke", 4));
    human.getTechList().addTech(TechFactory.createHullTech("Large freighter", 6));
    Research.handleShipDesigns(human, false, false);
    boolean privateer = false;
    boolean nukeBomber = false;
    for (ShipStat stat : human.getShipStatList()) {
      if (stat.getDesign().isPrivateer() && !stat.isObsolete()) {
        privateer = true;
      }
      if (stat.getDesign().isNuclearBomberShip() && !stat.isObsolete()) {
        nukeBomber = true;
      }
    }
    view.removeBannedShipDesigns(human, true, true);
    for (ShipStat stat : human.getShipStatList()) {
      if (privateer && !stat.isObsolete()) {
        assertEquals(false, stat.getDesign().isPrivateer());
      }
      if (nukeBomber && !stat.isObsolete()) {
        assertEquals(false, stat.getDesign().isNuclearBomberShip());
      }
    }
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
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() >= 2);
    assertEquals(false, view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, false));
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() >= 4);
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() >= 5);
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, true);
    tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() >= 6);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.isStarBaseDeployed()).thenReturn(true);
    pirates.getFleets().add(fleet);
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.EASY, false));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_EASY, false));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpdatePirate3() {
    Game game = Mockito.mock(Game.class);
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(game.getStarMap()).thenReturn(map);
    AITurnView view = new AITurnView(game);
    PlayerInfo pirates = new PlayerInfo(SpaceRace.SPACE_PIRATE, 9, 8);
    Tech tech = pirates.getTechList().getBestWeapon();
    assertEquals(true, tech.getLevel() <= 2);
    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.isStarBaseDeployed()).thenReturn(true);
    pirates.getFleets().add(fleet);
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, 40));
    pirates.getFleets().add(fleet);
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_HARD, 50));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.HARD, 50));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.HARD, 80));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.NORMAL, 50));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.NORMAL, 100));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.NORMAL, 120));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.EASY, 50));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.EASY, 100));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_EASY, 50));
    assertEquals(true, view.updateSpacePirates(pirates,
        PirateDifficultLevel.VERY_EASY, 100));
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

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalacticOlympicsParticipation() {
    PlayerList players = new PlayerList();
    PlayerInfo info0 = Mockito.mock(PlayerInfo.class);
    Mockito.when(info0.isHuman()).thenReturn(true);
    players.addPlayer(info0);
    PlayerInfo info1 = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy1 = Mockito.mock(Diplomacy.class);
    Mockito.when(info1.getDiplomacy()).thenReturn(diplomacy1);
    Mockito.when(diplomacy1.isWar(2)).thenReturn(true);
    Mockito.when(diplomacy1.isTradeEmbargo(3)).thenReturn(true);
    Mockito.when(diplomacy1.isTradeAlliance(5)).thenReturn(true);
    players.addPlayer(info1);
    PlayerInfo info2 = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy2 = Mockito.mock(Diplomacy.class);
    Mockito.when(info2.getDiplomacy()).thenReturn(diplomacy2);
    Mockito.when(diplomacy2.isWar(1)).thenReturn(true);
    Mockito.when(diplomacy2.isTradeAlliance(3)).thenReturn(true);
    players.addPlayer(info2);
    PlayerInfo info3 = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy3 = Mockito.mock(Diplomacy.class);
    Mockito.when(info3.getDiplomacy()).thenReturn(diplomacy3);
    Mockito.when(diplomacy3.isTradeEmbargo(1)).thenReturn(true);
    Mockito.when(diplomacy3.isAlliance(4)).thenReturn(true);
    Mockito.when(diplomacy3.isTradeAlliance(2)).thenReturn(true);
    players.addPlayer(info3);
    PlayerInfo info4 = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy4 = Mockito.mock(Diplomacy.class);
    Mockito.when(info4.getDiplomacy()).thenReturn(diplomacy4);
    Mockito.when(diplomacy4.isAlliance(3)).thenReturn(true);
    Mockito.when(diplomacy4.isTradeAlliance(5)).thenReturn(true);
    players.addPlayer(info4);
    PlayerInfo info5 = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy5 = Mockito.mock(Diplomacy.class);
    Mockito.when(info5.getDiplomacy()).thenReturn(diplomacy5);
    Mockito.when(diplomacy5.isTradeAlliance(4)).thenReturn(true);
    players.addPlayer(info5);
    PlayerInfo info6 = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy6 = Mockito.mock(Diplomacy.class);
    Mockito.when(info6.getDiplomacy()).thenReturn(diplomacy6);
    players.addPlayer(info6);
    PlayerInfo info7 = Mockito.mock(PlayerInfo.class);
    Diplomacy diplomacy7 = Mockito.mock(Diplomacy.class);
    Mockito.when(info7.getDiplomacy()).thenReturn(diplomacy7);
    players.addPlayer(info7);
    Votes votes = new Votes();
    Vote vote = new Vote(VotingType.GALACTIC_OLYMPIC_PARTICIPATE, 8, 10);
    vote.setOrganizerIndex(1);
    vote.setPlanetName("Planet of Games");
    votes.getVotes().add(vote);
    AITurnView.handleOlympicParticipation(votes, players);
    assertEquals(VotingChoice.NOT_VOTED , vote.getChoice(0));
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(1));
    assertEquals(VotingChoice.VOTED_NO, vote.getChoice(2));
    assertEquals(VotingChoice.VOTED_NO, vote.getChoice(3));
    assertEquals(VotingChoice.NOT_VOTED, vote.getChoice(4));
    assertEquals(VotingChoice.NOT_VOTED, vote.getChoice(5));
    assertEquals(VotingChoice.NOT_VOTED, vote.getChoice(6));
    assertEquals(VotingChoice.NOT_VOTED, vote.getChoice(7));
    vote.setTurnsToVote(1);
    AITurnView.handleOlympicParticipation(votes, players);
    assertEquals(VotingChoice.NOT_VOTED , vote.getChoice(0));
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(1));
    assertEquals(VotingChoice.VOTED_NO, vote.getChoice(2));
    assertEquals(VotingChoice.VOTED_NO, vote.getChoice(3));
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(4));
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(5));
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(6));
    assertEquals(VotingChoice.VOTED_YES, vote.getChoice(7));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGalacticOlympicsDiplomacy() {
    PlayerList playerList = new PlayerList();
    PlayerInfo info0 = new PlayerInfo(SpaceRace.CENTAURS, 8, 0);
    playerList.addPlayer(info0);
    PlayerInfo info1 = new PlayerInfo(SpaceRace.HUMAN, 8, 1);
    playerList.addPlayer(info1);
    PlayerInfo info2 = new PlayerInfo(SpaceRace.SPORKS, 8, 2);
    playerList.addPlayer(info2);
    PlayerInfo info3 = new PlayerInfo(SpaceRace.GREYANS, 8, 3);
    playerList.addPlayer(info3);
    PlayerInfo info4 = new PlayerInfo(SpaceRace.MECHIONS, 8, 4);
    playerList.addPlayer(info4);
    PlayerInfo info5 = new PlayerInfo(SpaceRace.HOMARIANS, 8, 5);
    playerList.addPlayer(info5);
    PlayerInfo info6 = new PlayerInfo(SpaceRace.SCAURIANS, 8, 6);
    playerList.addPlayer(info6);
    PlayerInfo info7 = new PlayerInfo(SpaceRace.TEUTHIDAES, 8, 7);
    playerList.addPlayer(info7);
    Vote vote = new Vote(VotingType.GALACTIC_OLYMPIC_PARTICIPATE, 8, 0);
    vote.setOrganizerIndex(2);
    vote.setChoice(0, VotingChoice.VOTED_YES);
    vote.setChoice(1, VotingChoice.VOTED_YES);
    vote.setChoice(2, VotingChoice.VOTED_YES);
    vote.setChoice(3, VotingChoice.VOTED_YES);
    vote.setChoice(4, VotingChoice.VOTED_YES);
    vote.setChoice(5, VotingChoice.VOTED_NO);
    vote.setChoice(6, VotingChoice.VOTED_YES);
    vote.setChoice(7, VotingChoice.VOTED_NO);
    AITurnView.handleOlympicDiplomacyBonus(vote, playerList);
    assertEquals(true, info7.getDiplomacy().getDiplomacyList(5).isBonusType(
        DiplomacyBonusType.OLYMPICS_EMBARGO));
    assertEquals(true, info5.getDiplomacy().getDiplomacyList(7).isBonusType(
        DiplomacyBonusType.OLYMPICS_EMBARGO));
    assertEquals(true, info2.getDiplomacy().getDiplomacyList(5).isBonusType(
        DiplomacyBonusType.DNS_OLYMPICS));
    assertEquals(true, info2.getDiplomacy().getDiplomacyList(7).isBonusType(
        DiplomacyBonusType.DNS_OLYMPICS));
    assertEquals(true, info0.getDiplomacy().getDiplomacyList(2).isBonusType(
        DiplomacyBonusType.OLYMPICS));
    assertEquals(true, info2.getDiplomacy().getDiplomacyList(1).isBonusType(
        DiplomacyBonusType.OLYMPICS));
    assertEquals(false, info2.getDiplomacy().getDiplomacyList(7).isBonusType(
        DiplomacyBonusType.OLYMPICS));
    assertEquals(false, info2.getDiplomacy().getDiplomacyList(5).isBonusType(
        DiplomacyBonusType.OLYMPICS_EMBARGO));
    assertEquals(false, info2.getDiplomacy().getDiplomacyList(5).isBonusType(
        DiplomacyBonusType.OLYMPICS));
  }

}
