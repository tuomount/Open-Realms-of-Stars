package org.openRealmOfStars.audio.soundeffect;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;


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
* Class for playing sound effects.
* This is static class so there is easy access to play sound effects when
* needed.
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
   * Sound effect for Ship explode
   */
  public static final String SHIP_EXPLODE = "/resources/sounds/explode.wav";

  /**
   * Sound effect for explosion
   */
  public static final String EXPLOSION = "/resources/sounds/explodemini.wav";

  /**
   * Sound effect for minor explosion
   */
  public static final String EXPLOSION_SMALL = "/resources/sounds/rumble.wav";

  /**
   * Sound effect for Menu clicks
   */
  public static final String MENU1 = "/resources/sounds/menu1.wav";

  /**
   * Sound effect for Menu clicks
   */
  public static final String MENU2 = "/resources/sounds/menu2.wav";
  /**
   * Sound effect for Menu clicks
   */
  public static final String MENU3 = "/resources/sounds/menu3.wav";

  /**
   * Sound effect for Menu clicks
   */
  public static final String MENU4 = "/resources/sounds/menu4.wav";

  /**
   * Sound effect for space ship engine
   */
  public static final String ENGINE1 = "/resources/sounds/engine1.wav";

  /**
   * Sound effect for space ship engine
   */
  public static final String ENGINE2 = "/resources/sounds/engine2.wav";

  /**
   * Sound effect for space ship engine
   */
  public static final String ENGINE3 = "/resources/sounds/engine3.wav";

  /**
   * Sound effect for hailing calls
   */
  public static final String RADIO_CALL = "/resources/sounds/radiocall.wav";

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
  private static ArrayList<String> nextSounds = new ArrayList<>();

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
   * Play menu sound. This should be called when button is pressed.
   */
  public static void playMenuSound() {
    int i = DiceGenerator.getRandom(3);
    switch (i) {
    case 0: {
      playSound(MENU1);
      break;
    }
    case 1: {
      playSound(MENU2);
      break;
    }
    case 2: {
      playSound(MENU3);
      break;
    }
    default:
    case 3: {
      playSound(MENU4);
      break;
    }
    }
  }

  /**
   * Play Engine sound for space ships
   */
  public static void playEngineSound() {
    int i = DiceGenerator.getRandom(2);
    switch (i) {
    case 0: {
      playSound(ENGINE1);
      break;
    }
    case 1: {
      playSound(ENGINE2);
      break;
    }
    default:
    case 2: {
      playSound(ENGINE3);
      break;
    }
    }
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
