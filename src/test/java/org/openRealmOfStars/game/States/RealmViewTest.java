package org.openRealmOfStars.game.States;

import java.awt.event.ActionListener;
import java.io.IOException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.government.GovernmentType;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2018 Tuomo Untinen
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
* Realm View tests
*
*/
public class RealmViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void test() throws IOException {
    ActionListener listener = Mockito.mock(ActionListener.class);
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getEmpireName()).thenReturn("Empire of Test");
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.EMPIRE);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    new RealmView(info, listener);
  }

}
