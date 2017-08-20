package org.openRealmOfStars.audio.music;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.game.GameState;

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
* Music Player Test
*
*/
public class MusicPlayerTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMusicPlayerByPlayingOgg() throws InterruptedException {
    MusicPlayer.stop();
    Thread.sleep(1000);
    MusicPlayer.play(MusicPlayer.YD_OBSERVING_STAR);
    Thread.sleep(3000);
    assertEquals(MusicPlayer.YD_OBSERVING_STAR, MusicPlayer.getNowPlaying());
    MusicPlayer.stop();
    Thread.sleep(1000);

    MusicPlayer.setMusicEnabled(false);
    MusicPlayer.handleMusic(GameState.COMBAT);
    assertEquals(MusicPlayer.YD_OBSERVING_STAR, MusicPlayer.getNowPlaying());
    MusicPlayer.handleMusic(GameState.MAIN_MENU);
    assertEquals(MusicPlayer.MILLION_LIGHT_YEARS, MusicPlayer.getNowPlaying());
    MusicPlayer.handleMusic(GameState.DIPLOMACY_VIEW);
    assertEquals(MusicPlayer.MILLION_LIGHT_YEARS, MusicPlayer.getNowPlaying());
    MusicPlayer.handleMusic(GameState.NEWS_CORP_VIEW);
    assertEquals(MusicPlayer.YD_OBSERVING_STAR, MusicPlayer.getNowPlaying());
    MusicPlayer.handleMusic(GameState.STARMAP);
    MusicPlayer.setMusicEnabled(true);

  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMusicPlayerLoopFlag() {
    assertEquals(true, MusicPlayer.isLoop());
    MusicPlayer.setLoop(false);
    assertEquals(false, MusicPlayer.isLoop());
    MusicPlayer.setLoop(true);
    assertEquals(true, MusicPlayer.isLoop());
  }

}
