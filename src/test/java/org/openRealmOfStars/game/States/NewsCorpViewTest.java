package org.openRealmOfStars.game.States;

//import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Test;
import org.mockito.Mockito;
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
  public void testBasic() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    NewsData newsData = Mockito.mock(NewsData.class);
    Mockito.when(newsData.getImageInstructions()).thenReturn(
        "background(nebulae)+planet(position center,rock1,full)"
        + "+text(WAR DECLARATION!)+text(Empire of Test)+relation_symbol(war)"
        + "+text(Democracy of Defender)");
    Mockito.when(newsData.getNewsText()).thenReturn("Empire of Test"
        + " declares war against Democracy of Defender! This meeting"
        + " happened in Planet I");
    new NewsCorpView(newsData, listener);
  }

}
