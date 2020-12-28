package org.openRealmOfStars.utilities.repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.openRealmOfStars.game.config.ConfigFile;
import org.openRealmOfStars.game.config.ConfigLine;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;

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
* Config File repository
*
*/
public final class ConfigFileRepository {

  /**
   * Just hiding the constructor
   */
  private ConfigFileRepository() {
    // Nothing to do
  }

  /**
   * Read Config File from input stream. Does not close
   * the input stream.
   * @param is InputStream where to read
   * @return ConfigFile
   * @throws IOException If reading fails.
   */
  public static ConfigFile readConfigFile(final InputStream is)
      throws IOException {
    byte[] data = IOUtilities.readAll(is);
    String dataAsString = new String(data, StandardCharsets.UTF_8);
    String[] lines = dataAsString.split("\n");
    ConfigFile config = new ConfigFile();
    for (String line : lines) {
      try {
        ConfigLine configLine = new ConfigLine(line);
        config.add(configLine);
      } catch (IllegalArgumentException ex) {
        ErrorLogger.log("Error while reading config: " + ex.getMessage());
      }
    }
    return config;
  }

  /**
   * Writes config file into outputstream. Does not close the Outputstream.
   * @param os OutputStrream to write
   * @param config ConfigFile
   * @throws IOException if writing fails.
   */
  public static void  writeConfigFile(final OutputStream os,
      final ConfigFile config) throws IOException {
    if (config != null) {
      for (int i = 0; i < config.getNumberOfLines(); i++) {
        ConfigLine line = config.getLine(i);
        os.write(line.toString().getBytes());
        os.write("\n".getBytes());
      }
    }
  }
}
