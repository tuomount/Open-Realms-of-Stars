package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.game.GameCommands;
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
* JUnit for save game name view.
*
*/
public class SaveGameNameViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGetFilenames() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    SaveGameNameView view = new SaveGameNameView("testfile", listener);
    assertEquals("saves/testfile.save", view.getFullFilename());
    assertEquals("testfile.save", view.getFilename());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    SaveGameNameView view = new SaveGameNameView("testfile", listener);
    assertEquals(false, view.isContinueGame());
    view.setContinueGame(true);
    assertEquals(true, view.isContinueGame());
    view.setContinueGame(false);
    assertEquals(false, view.isContinueGame());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testActions() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    SaveGameNameView view = new SaveGameNameView("testfile", listener);
    ActionEvent action = Mockito.mock(ActionEvent.class);
    Mockito.when(action.getActionCommand()).thenReturn(
        GameCommands.COMMAND_FILE_OVERWRITE);
    view.handleActions(action);
  }

}
