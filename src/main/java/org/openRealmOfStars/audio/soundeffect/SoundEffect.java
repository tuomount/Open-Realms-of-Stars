package org.openRealmOfStars.audio.soundeffect;

import javax.sound.sampled.AudioFormat;

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
* Class for single sound effect
*
*/
public class SoundEffect {

  /**
   * Generic information about current audio file..
   * Audio format tells how many bits per sample and how many samples per second
   */
  private AudioFormat format;
  /**
   * Audio data in buffer
   */
  private byte[] audioData;
  /**
   * Audio filename
   */
  private String audioName;

  /**
   * Build new sound effect
   * @param audioFormat AudioFormat for sound effect
   * @param name Filename
   * @param data Actual sound data
   */
  public SoundEffect(final AudioFormat audioFormat, final String name,
      final byte[] data) {
    format = audioFormat;
    audioName = name;
    audioData = data;
  }

  /**
   * Get audio format for sound effect
   * @return AudioFormat
   */
  public AudioFormat getFormat() {
    return format;
  }

  /**
   * Get the sample data for sound effect
   * @return Sample data in bytes
   */
  public byte[] getData() {
    return audioData;
  }

  /**
   * Get name for sound effect
   * @return Audio name
   */
  public String getName() {
    return audioName;
  }

}
