package org.openRealmOfStars.utilities.repository;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.openRealmOfStars.audio.soundeffect.SoundEffect;
import org.openRealmOfStars.utilities.IOUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2016  Tuomo Untinen
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
* Sound repository class
*
*/
public class SoundRepository {

  /**
   * Loads sound effect from a file. Sound effect file needs to
   * be inside the Jar file
   * @param filename File to load.
   * @return SoundEffect
   * @throws IOException if reading fails
   */
  public SoundEffect loadSound(final String filename) throws IOException {
    AudioFormat format = null;
    byte[] data = null;
    URL url = getClass().getResource(filename);
    try (AudioInputStream ais = AudioSystem.getAudioInputStream(url)) {
      format = ais.getFormat();
      data = IOUtilities.readAll(ais);
    } catch (UnsupportedAudioFileException | IOException e) {
      throw new IOException("Error while reading sound file(" + filename
          + "): " + e.getMessage());
    }
    return new SoundEffect(format, filename, data);
  }

}
