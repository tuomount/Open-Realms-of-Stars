package org.openRealmOfStars.utilities.repository;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.game.config.ConfigFile;
import org.openRealmOfStars.game.config.ConfigLine;

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
* Config File repository Test
*
*/
public class ConfigFileRepositoryTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testReading() throws IOException {
    String input = "# Comment here\n"
        + "Resolution=1024x768\n"
        + "SoundVolume=50\n"
        + "MusicVolume=50\n";
    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        input.getBytes(StandardCharsets.UTF_8))) {
      ConfigFile config = ConfigFileRepository.readConfigFile(bais);
      assertEquals(4, config.getNumberOfLines());
      assertEquals("# Comment here", config.getLine(0).getComment());
      assertEquals("Resolution", config.getLine(1).getKey());
      assertEquals("1024x768", config.getLine(1).getValue());
      assertEquals("MusicVolume", config.getLine(3).getKey());
      assertEquals("50", config.getLine(3).getValue());
    } 
  }
  
  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testWriting() throws IOException {
    ConfigFile config = new ConfigFile();
    ConfigLine line = new ConfigLine("# Comment here");
    config.add(line);
    line = new ConfigLine("Resolution=1024x768");
    config.add(line);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ConfigFileRepository.writeConfigFile(baos, config);
    byte[] buf = baos.toByteArray();
    baos.close();
    String str = new String(buf, StandardCharsets.UTF_8);
    assertEquals("# Comment here\nResolution=1024x768\n", str);
  }

}
