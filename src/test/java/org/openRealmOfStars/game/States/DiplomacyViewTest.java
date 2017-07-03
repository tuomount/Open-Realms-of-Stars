package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Test for DiplomacyView. This is trivial test just creating the object using
* saved game.
*
*/
public class DiplomacyViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void test() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    PlayerInfo human = starMap.getPlayerByIndex(0);
    PlayerInfo ai = starMap.getPlayerByIndex(1);
    DiplomacyView diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.HUMAN_REGULAR, listener);
    assertEquals(SpeechType.NEUTRAL_GREET, diplomacyView.getGreetLine());
    assertNotEquals(null, diplomacyView);
    assertNotEquals(null, diplomacyView.getTrade());
    diplomacyView.resetChoices();
    diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.AI_REGULAR, listener);
    assertEquals(SpeechType.NEUTRAL_GREET, diplomacyView.getGreetLine());
    assertNotEquals(null, diplomacyView);
    assertNotEquals(null, diplomacyView.getTrade());
    diplomacyView.resetChoices();
    int humanIndex = starMap.getPlayerList().getIndex(human);
    int aiIndex = starMap.getPlayerList().getIndex(ai);
    int humanMeetings = human.getDiplomacy().getDiplomacyList(aiIndex)
        .getNumberOfMeetings();
    humanMeetings++;
    int aiMeetings = ai.getDiplomacy().getDiplomacyList(humanIndex)
        .getNumberOfMeetings();
    aiMeetings++;
    diplomacyView.updateMeetingNumbers();
    assertEquals(humanMeetings, human.getDiplomacy().getDiplomacyList(aiIndex)
        .getNumberOfMeetings());
    assertEquals(aiMeetings, ai.getDiplomacy().getDiplomacyList(humanIndex)
        .getNumberOfMeetings());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testCreditsSettings() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    PlayerInfo human = starMap.getPlayerByIndex(0);
    PlayerInfo ai = starMap.getPlayerByIndex(1);
    DiplomacyView diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.HUMAN_REGULAR, listener);
    assertEquals(0, diplomacyView.getAiCredits());
    assertEquals(0, diplomacyView.getHumanCredits());
    ActionEvent action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_PLUS_AI_CREDIT);
    diplomacyView.handleAction(action);
    assertEquals(1, diplomacyView.getAiCredits());
    diplomacyView.handleAction(action);
    assertEquals(2, diplomacyView.getAiCredits());
    action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_MINUS_AI_CREDIT);
    diplomacyView.handleAction(action);
    diplomacyView.handleAction(action);
    assertEquals(0, diplomacyView.getAiCredits());
    action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_MINUS_HUMAN_CREDIT);
    diplomacyView.handleAction(action);
    assertEquals(0, diplomacyView.getHumanCredits());
    action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_PLUS_HUMAN_CREDIT);
    diplomacyView.handleAction(action);
    diplomacyView.handleAction(action);
    assertEquals(2, diplomacyView.getHumanCredits());
    action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_MINUS_HUMAN_CREDIT);
    diplomacyView.handleAction(action);
    diplomacyView.handleAction(action);
    assertEquals(0, diplomacyView.getHumanCredits());
  }

}
