package org.openRealmOfStars.audio.music;

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
* Music File Info contain song name and who made it
*
*/
public class MusicFileInfo {

  /**
   * Song name
   */
  private String songName;

  /**
   * Song author
   */
  private String author;
  /**
   * Song file inside JAR
   */
  private String fileName;

  /**
   * Packet limit in OGG stream after fading out will be activated
   */
  private int fadingLimit;

  /**
   * Constructor Music File Info
   * @param name Songname
   * @param composer Author or composer
   * @param filename File name inside jar
   */
  public MusicFileInfo(final String name, final String composer,
      final String filename) {
    songName = name;
    author = composer;
    fileName = filename;
    fadingLimit = -1;
  }

  /**
   * Constructor Music File Info
   * @param name Songname
   * @param composer Author or composer
   * @param filename File name inside jar
   * @param limit Fading limit in packets
   */
  public MusicFileInfo(final String name, final String composer,
      final String filename, final int limit) {
    songName = name;
    author = composer;
    fileName = filename;
    fadingLimit = limit;
  }

  /**
   * Get Song author
   * @return Song author
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Get Song name
   * @return Song name
   */
  public String getName() {
    return songName;
  }

  /**
   * Get Song filename inside jar
   * @return Song name
   */
  public String getFilename() {
    return fileName;
  }

  @Override
  public String toString() {
    return songName + " by " + author;
  }

  /**
   * Get fading limit for music file
   * @return the fadingLimit
   */
  public int getFadingLimit() {
    return fadingLimit;
  }

}
