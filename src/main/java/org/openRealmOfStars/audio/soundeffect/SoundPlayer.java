package org.openRealmOfStars.audio.soundeffect;

import java.util.ArrayList;


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
* Class for playing sound effects
*
*/
public final class SoundPlayer {

  /**
   * No use for constructor
   */
  private SoundPlayer() {
    // Just hiding the sound player constructor
  }

  /**
   * Sound effect for Beam weapons
   */
  public static final String WEAPON_BEAM = "/resources/sounds/beam.wav";

  /**
   * Sound effect for Railguns
   */
  public static final String WEAPON_RAILGUN = "/resources/sounds/railgun.wav";

  /**
   * Sound effect for missiles
   */
  public static final String WEAPON_MISSILE = "/resources/sounds/missile.wav";

  /**
   * Sound effect for Photon torpedo
   */
  public static final String WEAPON_TORPEDO = "/resources/sounds/torpedo.wav";

  /**
   * Is Sound enabled
   * @return True if sound enabled
   */
  public static boolean isSoundEnabled() {
    return soundEnabled;
  }

  /**
   * Set Sound
   * @param soundEnabled True to enable sound
   */
  public static void setSoundEnabled(final boolean soundEnabled) {
    SoundPlayer.soundEnabled = soundEnabled;
  }

  /**
   * Attribute if Sound is enabled or not
   */
  private static boolean soundEnabled = true;

  /**
   * List of Sound effect names going to SoundEffectManager. These sounds
   * are going to play next
   */
  private static ArrayList<String> nextSounds = null;

  /**
   * Thread for AudioManager
   */
  private static Thread soundEffectManagerThread = null;

  /**
   * Audio Manager
   */
  private static SoundEffectManager soundEffectManager = null;

  /**
   * Sound Volume default is 75%
   */
  private static int soundVolume = 75;

  /**
   * Get General Sound Volume
   * @return Sound volume as int
   */
  public static int getSoundVolume() {
    return soundVolume;
  }

  /**
   * Set General sound volume
   * @param soundVolume to set
   */
  public static void setSoundVolume(final int soundVolume) {
    SoundPlayer.soundVolume = soundVolume;
  }

  /**
   * Get Next sound name
   * @return String or null if list is empty
   */
  public static synchronized String getNextSoundName() {
    String result = null;
    if (nextSounds.size() > 0) {
      result = nextSounds.get(0);
      nextSounds.remove(0);
    }
    return result;
  }

  /**
   * Play sound effect. This places Sound effect into nextSounds list.
   * Initialized also SoundEffectMagager which will eventually player the sound
   * @param name Sound Effect name
   */
  public static synchronized void playSound(final String name) {
    if (soundEnabled) {
      if (nextSounds == null) {
        nextSounds = new ArrayList<>();
      }
      if (nextSounds.size() < 32) {
        nextSounds.add(name);
        if (soundEffectManager == null) {
          soundEffectManager = new SoundEffectManager();
          soundEffectManagerThread = new Thread(soundEffectManager);
          soundEffectManagerThread.start();
        }
      }
    }
  }


}
