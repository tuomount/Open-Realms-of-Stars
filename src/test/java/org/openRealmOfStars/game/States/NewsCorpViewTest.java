package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;

//import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.starMap.newsCorp.NewsData;

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
* News corp view to show highlights from previous turn
*
*/
public class NewsCorpViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    NewsData[] newsData = new NewsData[1];
    newsData[0] = Mockito.mock(NewsData.class);
    Mockito.when(newsData[0].getImageInstructions()).thenReturn(
        "background(nebulae)+planet(position center,rock1,full)"
        + "+text(WAR DECLARATION!)+text(Empire of Test)+relation_symbol(war)"
        + "+text(Democracy of Defender)");
    Mockito.when(newsData[0].getNewsText()).thenReturn("Empire of Test"
        + " declares war against Democracy of Defender! This meeting"
        + " happened in Planet I");
    new NewsCorpView(newsData, listener);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testRepaintNewsReader() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    NewsData[] newsData = new NewsData[1];
    newsData[0] = Mockito.mock(NewsData.class);
    Mockito.when(newsData[0].getImageInstructions()).thenReturn(
        "background(nebulae)+planet(position center,rock1,full)"
        + "+text(WAR DECLARATION!)+text(Empire of Test)+relation_symbol(war)"
        + "+text(Democracy of Defender)");
    Mockito.when(newsData[0].getNewsText()).thenReturn("Empire of Test"
        + " declares war against Democracy of Defender! This meeting"
        + " happened in Planet I");
    NewsCorpView view = new NewsCorpView(newsData, listener);
    ActionEvent arg0 = Mockito.mock(ActionEvent.class);
    Mockito.when(arg0.getActionCommand()).thenReturn(
        GameCommands.COMMAND_ANIMATION_TIMER);
    for (int i = 0; i < 300; i++) {
      view.handleAction(arg0);
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTwoNews() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    NewsData[] newsData = new NewsData[2];
    newsData[0] = Mockito.mock(NewsData.class);
    Mockito.when(newsData[0].getImageInstructions()).thenReturn(
        "background(nebulae)+planet(position center,rock1,full)"
        + "+text(WAR DECLARATION!)+text(Empire of Test)+relation_symbol(war)"
        + "+text(Democracy of Defender)");
    Mockito.when(newsData[0].getNewsText()).thenReturn("Empire of Test"
        + " declares war against Democracy of Defender! This meeting"
        + " happened in Planet I");
    newsData[1] = Mockito.mock(NewsData.class);
    Mockito.when(newsData[1].getImageInstructions()).thenReturn(
        "background(nebulae)+planet(position center,rock1,full)"
        + "+text(WAR DECLARATION!)+text(Empire of Test)+relation_symbol(war)"
        + "+text(Democracy of Defender)");
    Mockito.when(newsData[1].getNewsText()).thenReturn("Empire of Test"
        + " declares war against Democracy of Defender! This meeting"
        + " happened in Planet II");
    NewsCorpView view = new NewsCorpView(newsData, listener);
    assertEquals(0, view.getNewsIndex());
    ActionEvent arg0 = Mockito.mock(ActionEvent.class);
    Mockito.when(arg0.getActionCommand()).thenReturn(
        GameCommands.COMMAND_NEXT_TARGET);
    view.handleAction(arg0);
    assertEquals(1, view.getNewsIndex());
    view.handleAction(arg0);
    assertEquals(1, view.getNewsIndex());
    Mockito.when(arg0.getActionCommand()).thenReturn(
        GameCommands.COMMAND_PREV_TARGET);
    view.handleAction(arg0);
    assertEquals(0, view.getNewsIndex());
    view.handleAction(arg0);
    assertEquals(0, view.getNewsIndex());
  }

}
