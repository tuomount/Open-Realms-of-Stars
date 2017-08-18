package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.AI.Mission.Mission;
import org.openRealmOfStars.AI.Mission.MissionList;
import org.openRealmOfStars.AI.Mission.MissionPhase;
import org.openRealmOfStars.AI.Mission.MissionType;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.GameState;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.repository.GameRepository;

public class AiTurnViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testRunningTestRoundForAI() {
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    Game game = new Game(false);
    game.setLoadedGame(starMap);
    AITurnView aiTurnView = new AITurnView(game);
    ActionEvent arg0 = Mockito.mock(ActionEvent.class);
    Mockito.when(arg0.getActionCommand()).thenReturn(GameCommands.COMMAND_ANIMATION_TIMER);
    game.changeGameState(GameState.AITURN);
    int turnNumber = starMap.getTurn();
    turnNumber++;
    // Safety measure to end running the AI loop
    int loopCount = 10000;
    while (true) {
      loopCount--;
      if (loopCount == 0) {
        assertFalse(true);
      }
      aiTurnView.handleActions(arg0);
      if (game.getGameState() == GameState.DIPLOMACY_VIEW) {
        game.changeGameState(GameState.AITURN);
      } else if (game.getGameState() != GameState.AITURN) {
        break;
      }
    }
    assertEquals(turnNumber, starMap.getTurn());
  }

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
    AITurnView.addGatherMission(info, mission);
    Mission listMission = list.getMissionByIndex(0);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.ASSAULT_TYPE, listMission.getShipType());
    assertEquals(8, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.MILITARISTIC);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(7);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.BOMBER_TYPE, listMission.getShipType());
    assertEquals(8, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.EXPANSIONIST);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(6);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.TROOPER_TYPE, listMission.getShipType());
    assertEquals(7, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.BACKSTABBING);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(6);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.BOMBER_TYPE, listMission.getShipType());
    assertEquals(7, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.LOGICAL);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(6);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.BOMBER_TYPE, listMission.getShipType());
    assertEquals(7, list.getSize());
    Mockito.when(info.getAiAttitude()).thenReturn(Attitude.DIPLOMATIC);
    list = new MissionList();
    Mockito.when(info.getMissions()).thenReturn(list);
    AITurnView.addGatherMission(info, mission);
    listMission = list.getMissionByIndex(5);
    assertEquals(MissionType.GATHER, listMission.getType());
    assertEquals(Mission.ASSAULT_TYPE, listMission.getShipType());
    assertEquals(6, list.getSize());
  }

}
