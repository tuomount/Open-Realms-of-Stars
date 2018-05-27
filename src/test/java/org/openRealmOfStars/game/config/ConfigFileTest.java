package org.openRealmOfStars.game.config;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

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
* Tests for Config file class
*
*/

public class ConfigFileTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEmpty() {
    ConfigFile file = new ConfigFile();
    assertEquals(0, file.getNumberOfLines());
    assertEquals(null, file.getLine(0));
    assertEquals(null, file.getLineByKey("Key"));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNonEmpty() {
    ConfigLine line = Mockito.mock(ConfigLine.class);
    Mockito.when(line.getType()).thenReturn(ConfigLineType.KEY_VALUE);
    Mockito.when(line.getKey()).thenReturn("Key");
    ConfigFile file = new ConfigFile();
    file.add(line);
    assertEquals(1, file.getNumberOfLines());
    assertEquals(line, file.getLine(0));
    assertEquals(line, file.getLineByKey("Key"));
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testSoundVolume() {
    ConfigLine line = new ConfigLine(ConfigFile.CONFIG_SOUND_VOLUME + "=50");
    ConfigFile file = new ConfigFile();
    file.add(line);
    assertEquals(50, file.getSoundVolume());
    file.setSoundVolume(80);
    assertEquals(80, file.getSoundVolume());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testMusicVolume() {
    ConfigLine line = new ConfigLine(ConfigFile.CONFIG_MUSIC_VOLUME + "=50");
    ConfigFile file = new ConfigFile();
    file.add(line);
    assertEquals(50, file.getMusicVolume());
    file.setMusicVolume(80);
    assertEquals(80, file.getMusicVolume());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testResolution() {
    ConfigLine line = new ConfigLine(ConfigFile.CONFIG_RESOLUTION + "=1100x800");
    ConfigFile file = new ConfigFile();
    file.add(line);
    assertEquals(1100, file.getResolutionWidth());
    assertEquals(800, file.getResolutionHeight());
    file.setResolution(1200, 900);
    assertEquals(1200, file.getResolutionWidth());
    assertEquals(900, file.getResolutionHeight());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBorderless() {
    ConfigLine line = new ConfigLine(ConfigFile.CONFIG_BORDERLESS + "=false");
    ConfigFile file = new ConfigFile();
    file.add(line);
    assertEquals(false, file.getBorderless());
    file.setBorderless(true);
    assertEquals(true, file.getBorderless());
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testLargerFonts() {
    ConfigLine line = new ConfigLine(ConfigFile.CONFIG_LARGER_FONTS + "=false");
    ConfigFile file = new ConfigFile();
    file.add(line);
    assertEquals(false, file.getLargerFonts());
    file.setLargerFonts(true);
    assertEquals(true, file.getLargerFonts());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testEmptyConfig() {
    ConfigFile file = new ConfigFile();
    assertEquals(false, file.getBorderless());
    file.setBorderless(true);
    assertEquals(true, file.getBorderless());
    assertEquals(1024, file.getResolutionWidth());
    assertEquals(768, file.getResolutionHeight());
    file.setResolution(1280, 1024);
    assertEquals(1280, file.getResolutionWidth());
    assertEquals(1024, file.getResolutionHeight());
    assertEquals(75, file.getMusicVolume());
    file.setMusicVolume(100);
    assertEquals(100, file.getMusicVolume());
    assertEquals(75, file.getSoundVolume());
    file.setSoundVolume(20);
    assertEquals(20, file.getSoundVolume());
    assertEquals(false, file.getLargerFonts());
    file.setLargerFonts(true);
    assertEquals(true, file.getLargerFonts());
  }


}
