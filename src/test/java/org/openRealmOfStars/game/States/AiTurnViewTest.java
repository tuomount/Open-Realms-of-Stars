package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.GameState;
import org.openRealmOfStars.starMap.StarMap;
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
      if (game.getGameState() != GameState.AITURN) {
        break;
      }
    }
    assertEquals(turnNumber, starMap.getTurn());
  }

}
