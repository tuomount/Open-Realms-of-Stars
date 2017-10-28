package org.openRealmOfStars.audio.music;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openRealmOfStars.game.GameState;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Music Player using OggPlayer
*
*/
public final class MusicPlayer {


  /**
   * Hiding the constructor
   */
  private MusicPlayer() {
    // Just hiding the construct
  }
  /**
   * Is music player at the moment flag
   */
  private static boolean playing = false;
  /**
   * Is music enabled or not
   */
  private static boolean musicEnabled = true;
  /**
   * OggPlayer class, doing the actual Ogg parsing and playing
   */
  private static OggPlayer player;

  /**
   * Observing The Star By YD License CC0
   */
  public static final MusicFileInfo YD_OBSERVING_STAR = new MusicFileInfo(
      "Observing The Star", "YD", "/resources/musics/ObservingTheStar.ogg");

  /**
   * A million light years between us By Alexander Zhelanov CC-BY 3.0
   */
  public static final MusicFileInfo MILLION_LIGHT_YEARS = new MusicFileInfo(
      "A million light years between us", "Alexandr Zhelanov",
      "/resources/musics/A million light years between us.ogg");

  /**
   * Nautilus By poinl CC0
   */
  public static final MusicFileInfo NAUTILUS = new MusicFileInfo(
      "Nautilus", "poinl",
      "/resources/musics/ambient2_Nautilus.ogg");

  /**
   * Neon Transit By Alexandr Zhelanov CC-BY 3.0
   */
  public static final MusicFileInfo NEON_TRANSIT = new MusicFileInfo(
      "Neon Transit", "Alexandr Zhelanov",
      "/resources/musics/Neon Transit.ogg");

  /**
   * Brave Space Explorers By Alexandr Zhelanov CC-BY 3.0
   */
  public static final MusicFileInfo BRAVE_SPACE_EXPLORERS = new MusicFileInfo(
      "Brave Space Explorers", "Alexandr Zhelanov",
      "/resources/musics/Brave Space Explorers.ogg");

  /**
   * Thrust Sequence By Matthew Pablo CC-BY 3.0
   */
  public static final MusicFileInfo THRUST_SEQUENCE = new MusicFileInfo(
      "Thrust Sequence", "Matthew Pablo",
      "/resources/musics/Thrust Sequence.ogg");

  /**
   * List of music to played while playing
   */
  protected static final MusicFileInfo[] GAME_MUSIC_LIST = {YD_OBSERVING_STAR,
      MILLION_LIGHT_YEARS, NAUTILUS, BRAVE_SPACE_EXPLORERS};
  /**
   * List of music to played while combat
   */
  protected static final MusicFileInfo[] COMBAT_MUSIC_LIST = {NEON_TRANSIT,
      THRUST_SEQUENCE};
  /**
   * What music file is currently playing
   */
  private static MusicFileInfo nowPlaying;

  /**
   * Flag to make music player loop
   */
  private static boolean playerLoop = true;

  /**
   * How many times text has been displayed
   */
  private static int textDisplayed = 0;

  /**
   * Play the MusicFile to the sound card
   * @param musicFile Music File Info
   */
  public static void play(final MusicFileInfo musicFile) {
    nowPlaying = musicFile;
    textDisplayed = 0;
    play(nowPlaying.getFilename());
  }

  /**
   * Is text displayed enough
   * @return True if text can be removed
   */
  public static boolean isTextDisplayedEnough() {
    if (textDisplayed < 20) {
      textDisplayed++;
      return false;
    }
    return true;
  }
  /**
   * Get what is now being played.
   * @return Music Info what is now being played.
   */
  public static MusicFileInfo getNowPlaying() {
    return nowPlaying;
  }
  /**
   * Play the OGG file to the sound card
   * @param musicFile String
   */
  @SuppressWarnings("resource")
  public static synchronized void play(final String musicFile) {
    if (musicEnabled) {
      if (playing) {
        stop();
      }
      try {
        InputStream is = MusicPlayer.class.getResource(musicFile).openStream();
        BufferedInputStream stream = new BufferedInputStream(is);
        if (player == null) {
          player = new OggPlayer(stream);
        } else {
          player.stop();
          int loop = 0;
          while (!player.isStopped()) {
            loop++;
            Thread.sleep(5);
            if (loop > 1000) {
              break;
            }
          }
          player = new OggPlayer(stream);
        }
      } catch (IOException | InterruptedException e) {
        ErrorLogger.log("Problem while playing OGG file ("
             + musicFile + "):");
        ErrorLogger.log(e);
        return;
      }
      player.setLoop(playerLoop);
      // run in new thread to play in background
      new Thread() {
        @Override
        public void run() {
          try {
            playing = true;
            player.play();
            playing = false;
          } catch (Exception e) {
            ErrorLogger.log(e);
          }
        }
      }.start();
    }
  }

  /**
   * Is Music player playing or not
   * @return True if playing
   */
  public static boolean isPlaying() {
    return playing;
  }

  /**
   * Is player looping or not
   * @return True if looping
   */
  public static boolean isLoop() {
    return playerLoop;
  }

  /**
   * Make music player loop or disable loop
   * @param loop True to enable loop
   */
  public static void setLoop(final boolean loop) {
    playerLoop = loop;
    if (player != null) {
      player.setLoop(loop);
    }
  }

  /**
   * Make music player stop
   */
  public static void stop() {
    if (player != null) {
      player.stop();
    }
  }

  /**
   * Enable music or disable music.
   * @param musicEnabled True to enable false to disable.
   */
  public static void setMusicEnabled(final boolean musicEnabled) {
    MusicPlayer.musicEnabled = musicEnabled;
    if (!MusicPlayer.isMusicEnabled() && isPlaying()) {
      player.stop();
      playing = false;
    }
  }


  /**
   * Is Music enabled or not
   * @return True for enabled music
   */
  public static boolean isMusicEnabled() {
    return musicEnabled;
  }

  /**
   * Play Combat music
   */
  public static void playCombatMusic() {
    int index = DiceGenerator.getRandom(COMBAT_MUSIC_LIST.length - 1);
    play(COMBAT_MUSIC_LIST[index]);
  }
  /**
   * Play Combat music
   */
  public static void playGameMusic() {
    int index = DiceGenerator.getRandom(GAME_MUSIC_LIST.length - 1);
    play(GAME_MUSIC_LIST[index]);
  }
  /**
   * Handle music if song playing stops.
   * This will restart new music
   * @param state GameState
   */
  public static void handleMusic(final GameState state) {
    if (!isPlaying()) {
      if (state == GameState.COMBAT) {
        // Combat music
        playCombatMusic();
      } else if (state == GameState.DIPLOMACY_VIEW) {
        // Keep playing the same song
        play(nowPlaying);
      } else if (state == GameState.MAIN_MENU
          || state == GameState.CREDITS
          || state == GameState.LOAD_GAME
          || state == GameState.NEW_GAME
          || state == GameState.GALAXY_CREATION
          || state == GameState.PLAYER_SETUP) {
        // Main menu song
        // FIXME Change to main menu song later
        play(MILLION_LIGHT_YEARS);
      } else if (state == GameState.NEWS_CORP_VIEW) {
        // News corp song
        // FIXME Change to news corp song later
        play(YD_OBSERVING_STAR);
      } else {
        // Game music
        playGameMusic();
      }
    }
  }
}
