package org.openRealmOfStars.game.States;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.audio.soundeffect.SoundPlayer;
import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.game.GameCommands;
import org.openRealmOfStars.game.config.ConfigFile;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Tests for OptionsView class
*
*/
public class OptionsViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    ConfigFile config = Mockito.mock(ConfigFile.class);
    Mockito.when(config.getLightIntense()).thenReturn(3);
    Game game = Mockito.mock(Game.class);
    Mockito.when(game.getHeight()).thenReturn(768);
    Mockito.when(game.getWidth()).thenReturn(1024);
    ActionListener listener = Mockito.mock(ActionListener.class);
    MusicPlayer.setVolume(50);
    SoundPlayer.setSoundVolume(70);
    OptionsView view = new OptionsView(config, game, listener);
    assertEquals("1024x768", view.getResolution());
    assertEquals(50, view.getMusicVolume());
    assertEquals(70, view.getSoundVolume());
    assertEquals(false, view.getBorderless());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testActions() {
    ConfigFile config = Mockito.mock(ConfigFile.class);
    Game game = Mockito.mock(Game.class);
    Mockito.when(game.getHeight()).thenReturn(768);
    Mockito.when(game.getWidth()).thenReturn(1024);
    ActionListener listener = Mockito.mock(ActionListener.class);
    MusicPlayer.setVolume(50);
    SoundPlayer.setSoundVolume(70);
    Mockito.when(config.getLightIntense()).thenReturn(3);
    OptionsView view = new OptionsView(config, game, listener);
    ActionEvent event = Mockito.mock(ActionEvent.class);
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_MUSIC_VOLUME_UP);
    assertEquals(50, view.getMusicVolume());
    view.handleAction(event);
    assertEquals(60, view.getMusicVolume());
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_MUSIC_VOLUME_DN);
    view.handleAction(event);
    assertEquals(50, view.getMusicVolume());
    assertEquals(70, view.getSoundVolume());
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_SOUND_VOLUME_UP);
    view.handleAction(event);
    assertEquals(80, view.getSoundVolume());
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_SOUND_VOLUME_DN);
    view.handleAction(event);
    assertEquals(70, view.getSoundVolume());
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_SOUND_VOLUME);
    view.handleAction(event);
    Mockito.when(event.getActionCommand()).thenReturn(
        GameCommands.COMMAND_MUSIC_VOLUME);
    view.handleAction(event);
    assertEquals(50, view.getMusicVolume());
    assertEquals(70, view.getSoundVolume());
  }

}
