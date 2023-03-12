package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.DiplomacyBonusType;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechLine;
import org.openRealmOfStars.player.diplomacy.speeches.SpeechType;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.repository.GameRepository;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2023 Tuomo Untinen
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
        DiplomacyView.HUMAN_REGULAR, null, null, listener);
    assertEquals(SpeechType.NEUTRAL_GREET, diplomacyView.getGreetLine());
    assertNotEquals(null, diplomacyView);
    assertNotEquals(null, diplomacyView.getTrade());
    diplomacyView.resetChoices();
    diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.AI_REGULAR,  null, null, listener);
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
    assertEquals(false, diplomacyView.didTradeHappen());
    diplomacyView.addNothingToTrade();
    assertEquals(true, ai.getDiplomacy().getDiplomacyList(0).isBonusType(
        DiplomacyBonusType.NOTHING_TO_TRADE));
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
        DiplomacyView.HUMAN_REGULAR, null, null, listener);
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

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testTradeOffer() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    PlayerInfo human = starMap.getPlayerByIndex(0);
    PlayerInfo ai = starMap.getPlayerByIndex(1);
    DiplomacyView diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.HUMAN_REGULAR, null, null, listener);
    assertEquals(0, diplomacyView.getAiCredits());
    assertEquals(0, diplomacyView.getHumanCredits());
    ActionEvent action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_OK);
    diplomacyView.getHumanLines().setSelectedIndex(0);
    diplomacyView.handleAction(action);
    assertEquals("Peace", human.getDiplomacy().getDiplomaticRelation(1));
    diplomacyView.getHumanLines().setSelectedIndex(2);
    diplomacyView.handleAction(action);
    assertEquals("Trade alliance", human.getDiplomacy().getDiplomaticRelation(1));
    diplomacyView.getHumanLines().setSelectedIndex(2);
    diplomacyView.handleAction(action);
    assertEquals("Defensive pact", human.getDiplomacy().getDiplomaticRelation(1));
    diplomacyView.getHumanLines().setSelectedIndex(2);
    diplomacyView.handleAction(action);
    assertEquals("Alliance", human.getDiplomacy().getDiplomaticRelation(1));
    diplomacyView.getHumanLines().setSelectedIndex(3);
    diplomacyView.handleAction(action);
    assertEquals("War", human.getDiplomacy().getDiplomaticRelation(1));
    diplomacyView.getHumanLines().setSelectedIndex(1);
    diplomacyView.handleAction(action);
    assertEquals(true, ai.getDiplomacy().getDiplomacyList(0)
        .isBonusType(DiplomacyBonusType.MADE_DEMAND));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMeetingPlace() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    PlayerInfo human = starMap.getPlayerByIndex(0);
    PlayerInfo ai = starMap.getPlayerByIndex(1);
    Planet planet = Mockito.mock(Planet.class);
    Fleet fleet = Mockito.mock(Fleet.class);
    DiplomacyView diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.HUMAN_REGULAR, null, null, listener);
    assertEquals(null, diplomacyView.getMeetingPlace());
    diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.HUMAN_REGULAR, fleet, null, listener);
    assertEquals(fleet, diplomacyView.getMeetingPlace());
    diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.HUMAN_REGULAR, null, planet, listener);
    assertEquals(planet, diplomacyView.getMeetingPlace());
    diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.HUMAN_REGULAR, fleet, planet, listener);
    assertEquals(planet, diplomacyView.getMeetingPlace());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGettingRealmFromString() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    PlayerInfo human = starMap.getPlayerByIndex(0);
    PlayerInfo ai = starMap.getPlayerByIndex(1);
    DiplomacyView diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.HUMAN_REGULAR, null, null, listener);
    PlayerInfo info = diplomacyView.getRealmFromString("Spork Hiearchy");
    assertEquals("Spork Hiearchy", info.getEmpireName());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testGetEmbargoLine() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    GameRepository repository = new GameRepository();
    StarMap starMap = repository.loadGame("src/test/resources/saves",
                                          "testGame.save");
    PlayerInfo human = starMap.getPlayerByIndex(0);
    PlayerInfo ai = starMap.getPlayerByIndex(1);
    DiplomacyView diplomacyView = new DiplomacyView(human, ai, starMap,
        DiplomacyView.HUMAN_REGULAR, null, null, listener);
    diplomacyView.createTradeEmbargoLine("Spork Hiearchy");
    SpeechLine line = diplomacyView.getEmbargoLine();
    assertEquals(SpeechType.TRADE_EMBARGO, line.getType());
    assertEquals("How about trade embargo for 20 star years against"
        + " Spork Hiearchy?", line.getLine());
  }

}
