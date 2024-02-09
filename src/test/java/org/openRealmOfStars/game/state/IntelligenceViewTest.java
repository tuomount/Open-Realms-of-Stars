package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
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
 */

import static org.junit.Assert.assertEquals;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.starMap.newsCorp.GalaxyStat;

/**
*
* Intelligence view test
*
*/
public class IntelligenceViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBasic() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getFakeMilitarySize()).thenReturn(100);
    PlayerList playerList = Mockito.mock(PlayerList.class);
    ActionListener listener = Mockito.mock(ActionListener.class);
    GalaxyStat stat = Mockito.mock(GalaxyStat.class);
    Mockito.when(stat.getLatest(Mockito.anyInt())).thenReturn(120);
    IntelligenceView view = new IntelligenceView(playerList, info, stat, listener);
    assertEquals(info, view.getPlayer());
    assertEquals(120, view.getHumanNewsMilitarySize());
  }

}
