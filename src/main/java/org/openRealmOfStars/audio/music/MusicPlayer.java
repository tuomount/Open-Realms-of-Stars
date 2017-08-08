package org.openRealmOfStars.audio.music;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

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
    // Just hiding the construcot
  }
  /**
   * Is music player at the momont flag
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
   * Play the OGG file to the sound card
   * @param musicFile String
   */
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
      // run in new thread to play in background
      new Thread() {
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
   * Make music player stop
   */
  public static void stop() {
    player.stop();
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
}
