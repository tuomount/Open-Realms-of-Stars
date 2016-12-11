package org.openRealmOfStars.audio.soundeffect;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.repository.SoundRepository;


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
* Class for handling sound effects
*
*/
public class SoundEffectManager extends Thread {

  /**
   * Maximum simultaneous sound effects playing
   */
  private static final int MAX_SOUNDS = 16;

  /**
   * Hashmap for already loaded sound effects. Each sound effect
   * is loaded only once and then kept in memory
   */
  private HashMap<String, SoundEffect> soundEffects = new HashMap<>();
  /**
   * Audio Clip. These are clips used for playing the actual sound
   */
  private Clip[] clips = new Clip[MAX_SOUNDS];
  /**
   * Sound index which clip is next one to use
   */
  private int soundIndex = 0;

  /**
   * Get SoundEffect for certain file
   * @param name Sound effect name
   * @return SoundEffect or null
   */
  public SoundEffect getSoundEffect(final String name) {
    SoundEffect result = soundEffects.get(name);
    if (result == null) {
      SoundRepository repository = new SoundRepository();
      try {
        result = repository.loadSound(name);
      } catch (IOException e) {
        ErrorLogger.log(e);
      }
      if (result != null) {
        soundEffects.put(name, result);
      }
    }
    return result;
  }

  /**
   * Play sound effect
   * @param name Sound Effect Name
   */
  private void playSound(final String name) {
    try {
      if (clips[soundIndex] != null) {
        try {
          clips[soundIndex].close();
        } catch (IllegalStateException e) {
          ErrorLogger.log(e);
        }
      }
      SoundEffect effect = getSoundEffect(name);
      if (effect != null) {
        DataLine.Info info = new DataLine.Info(Clip.class, effect.getFormat());
        clips[soundIndex] = (Clip) AudioSystem.getLine(info);
        clips[soundIndex].open(effect.getFormat(), effect.getData(), 0,
            effect.getData().length);
        //Volume set for SFX
        try {
          FloatControl gainControl = (FloatControl) clips[soundIndex]
              .getControl(FloatControl.Type.VOLUME);
          gainControl.setValue(gainControl.getMaximum()
              * SoundPlayer.getSoundVolume() / 100);
        } catch (IllegalArgumentException e) {
          try {
            FloatControl gainControl = (FloatControl) clips[soundIndex]
                .getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(gainControl.getMinimum() * ((100
                - SoundPlayer.getSoundVolume()) / 100));
          } catch (IllegalArgumentException e1) {
            ErrorLogger.log("Your audio system does not support VOLUME or"
                + " MASTER_GAIN control...Playing default volume");
          }
        }
        try {
          Thread.sleep(5);
        } catch (InterruptedException e) {
          // Do nothing
        }
        clips[soundIndex].start();
        soundIndex++;
        if (soundIndex == MAX_SOUNDS) {
          soundIndex = 0;
        }
      } else {
        ErrorLogger.log("NULL sound " + name);
      }
    } catch (LineUnavailableException e) {
      ErrorLogger.log(e);
    }
  }

  @Override
  public void run() {
    String name;
    while (true) {
      try {
        Thread.sleep(25);
      } catch (InterruptedException e) {
        // Nothing to do here
      }
      do {
        name = SoundPlayer.getNextSoundName();
        if (name != null) {
          playSound(name);
        }
      } while (name != null);
    }

  }


}
