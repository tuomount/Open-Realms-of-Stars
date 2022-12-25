package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;
import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017 Tuomo Untinen
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
* Test for CreditsView class
*
*/
public class CreditsViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void test() throws IOException {
    ActionListener listener = Mockito.mock(ActionListener.class);
    CreditsView credits = new CreditsView(listener, "Title", "0.0.1",
        CreditsView.CREDITS_AND_LICENSE);
    credits.updateTextArea();
    assertEquals(true, credits.getCreditsText().contains("Title"));
    assertEquals(true, credits.getCreditsText().contains("0.0.1"));
    assertEquals(true, credits.getCreditsText().contains("GNU GENERAL PUBLIC LICENSE"));
    assertEquals(true, credits.getCreditsText().contains("Tuomo Untinen"));
  }

}
