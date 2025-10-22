package org.openRealmOfStars.audio.soundeffect;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2022 Tuomo Untinen
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
 */

import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;

/**
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
   * Sound effect for Ion Cannon
   */
  public static final String ION_CANNON = "/resources/sounds/ion_cannon.wav";

  /**
   * Sound effect for Plasma cannon
   */
  public static final String WEAPON_PLASMA_CANNON = "/resources/sounds/"
      + "plasma_cannon.wav";

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
   * Sound effect for emp explosion
   */
  public static final String EXPLOSION_EMP = "/resources/sounds/"
      + "emp_explode.wav";

  /**
   * Sound effect for bomb
   */
  public static final String BOMB = "/resources/sounds/bomb.wav";

  /**
   * Sound effect for nuke
   */
  public static final String NUKE = "/resources/sounds/nuke.wav";

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
   * Sound effect for Menu disabled
   */
  public static final String MENU_DISABLED =
      "/resources/sounds/menu_disabled.wav";

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
   * Sound effect for space ship engine shorter
   */
  public static final String AWAYTEAM = "/resources/sounds/awayteam.wav";

  /**
   * Sound effect for hailing calls
   */
  public static final String RADIO_CALL = "/resources/sounds/radiocall.wav";

  /**
   * Sound effect for deploying starbase
   */
  public static final String STARBASE = "/resources/sounds/starbase.wav";

  /**
   * Sound effect for repairing fleets
   */
  public static final String REPAIR = "/resources/sounds/repair.wav";

  /**
   * Sound effect for escaping via blackhole
   */
  public static final String TELEPORT = "/resources/sounds/teleport.wav";

  /**
   * Sound effect for blackhole showing up
   */
  public static final String WORMHOLE = "/resources/sounds/wormhole.wav";

  /**
   * Sound effect for space ship shield
   */
  public static final String SHIELD1 = "/resources/sounds/shield1.wav";
  /**
   * Sound effect for space ship shield
   */
  public static final String SHIELD2 = "/resources/sounds/shield2.wav";
  /**
   * Sound effect for space ship shield
   */
  public static final String SHIELD3 = "/resources/sounds/shield3.wav";
  /**
   * Sound effect for alarm
   */
  public static final String ALARM = "/resources/sounds/alarm.wav";
  /**
   * Sound effect for transport inbound
   */
  public static final String TRANSPORT_INBOUND =
      "/resources/sounds/transport_inbound.wav";
  /**
   * Sound effect for destroying building
   */
  public static final String DESTROY_BUILDING =
      "/resources/sounds/destroy_building.wav";
  /**
   * Sound effect for warp engine engage
   */
  public static final String WARP_ENGINE_ENGAGE =
      "/resources/sounds/warp_engine_engage.wav";
  /**
   * Sound effect for rushing building with population.
   */
  public static final String WHIP = "/resources/sounds/whip.wav";
  /**
   * Sound effect for rushing building with credits.
   */
  public static final String COINS = "/resources/sounds/coins.wav";
  /**
   * Sound effect for disassemble
   */
  public static final String DISASSEMBLE = "/resources/sounds/disassemble.wav";
  /**
   * Sound effect for colonized planet
   */
  public static final String COLONIZED = "/resources/sounds/colonized.wav";
  /**
   * Sound effect for glitch in video feed
   */
  public static final String GLITCH = "/resources/sounds/glitch.wav";

  /**
   * Sound effect for electric burst.
   */
  public static final String ELECTRIC = "/resources/sounds/electric.wav";
  /**
   * Sound effect for tractor beam.
   */
  public static final String TRACTORBEAM = "/resources/sounds/tractorbeam.wav";

  /**
   * Sound effect for computer overload
   */
  public static final String COMPUTER_OVERLOAD =
      "/resources/sounds/computer_overload.wav";

  /**
   * Sound effect for scanner overload
   */
  public static final String SCANNER_OVERLOAD =
      "/resources/sounds/scanner.wav";

  /**
   * Sound effect for engine overload
   */
  public static final String ENGINE_OVERLOAD =
      "/resources/sounds/engine_overload.wav";

  /**
   * Sound effect for jammer overload
   */
  public static final String JAMMER_OVERLOAD =
      "/resources/sounds/jammer_overload.wav";

  /**
   * Sound effect for cloak overload
   */
  public static final String CLOAK_OVERLOAD =
      "/resources/sounds/cloak_overload.wav";

  /**
   * Sound effect for bite
   */
  public static final String BITE =
      "/resources/sounds/bite.wav";

  /**
   * Sound effect for tentacle attack
   */
  public static final String TENTACLE =
      "/resources/sounds/tentacle.wav";
  /**
   * Sound effect for arm spike attack
   */
  public static final String SPIKE =
      "/resources/sounds/spike.wav";
  /**
   * Sound effect for machine gun/chaingun/multicannon
   */
  public static final String MACHINEGUN =
      "/resources/sounds/machinegun.wav";

  /**
   * Sound effect for Gravity Ripper
   */
  public static final String GRAVITY_RIPPER =
      "/resources/sounds/gravity_ripper.wav";

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
  private static boolean soundEnabled = false;

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
   * Sound Volume default is 70%
   */
  private static int soundVolume = 70;

  /**
   * Get General Sound Volume
   * @return Sound volume as int
   */
  public static int getSoundVolume() {
    return soundVolume;
  }

  /**
   * Set General sound volume. Volume is between 0-100.
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
   * Play menu sound indicating that something did not work.
   */
  public static void playMenuDisabled() {
    playSound(MENU_DISABLED);
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
   * Play Shield sound for space ships
   */
  public static void playShieldSound() {
    int i = DiceGenerator.getRandom(2);
    switch (i) {
    case 0: {
      playSound(SHIELD1);
      break;
    }
    case 1: {
      playSound(SHIELD2);
      break;
    }
    default:
    case 2: {
      playSound(SHIELD3);
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

  /**
   * Convert linear volume between 0-100 to dB drop in master gain.
   * @param volume Volume
   * @param minimumDb MinimumDb drop value
   * @return Volume in dB drop.
   */
  public static float convertLinearVolumeToDb(final int volume,
      final float minimumDb) {
    float value = 0;
    switch (volume / 10) {
    default:
    case 10: value = -minimumDb; break;
    case 9: value = -minimumDb - 3; break;
    case 8: value = -minimumDb - 6; break;
    case 7: value = -minimumDb - 9; break;
    case 6: value = -minimumDb - 12; break;
    case 5: value = -minimumDb - 15; break;
    case 4: value = -minimumDb - 18; break;
    case 3: value = -minimumDb - 21; break;
    case 2: value = -minimumDb - 24; break;
    case 1: value = -minimumDb - 27; break;
    case 0: value = 0; break;
    }
    return value;
  }
}
