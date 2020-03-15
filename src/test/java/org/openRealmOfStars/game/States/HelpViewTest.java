package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.tutorial.HelpLine;
import org.openRealmOfStars.game.tutorial.TutorialList;
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
* JUnit for help view.
*
*/

public class HelpViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasic() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    TutorialList tutorial = new TutorialList();
    HelpLine helpLine = new HelpLine(0);
    helpLine.setCategory("Test");
    helpLine.setTitle("Test title");
    helpLine.setText("Test text");
    tutorial.add(helpLine);
    HelpView view = new HelpView(tutorial, false, listener);
    assertEquals(false, view.isTutorialEnabled());
    view = new HelpView(tutorial, true, listener);
    assertEquals(true, view.isTutorialEnabled());
  }

}
