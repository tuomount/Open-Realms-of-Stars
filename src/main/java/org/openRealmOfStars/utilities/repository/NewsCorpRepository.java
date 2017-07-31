package org.openRealmOfStars.utilities.repository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openRealmOfStars.starMap.newsCorp.GalaxyStat;
import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.utilities.IOUtilities;

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
* News corporation repository class
*
*/
public class NewsCorpRepository {

  /**
   * Save News Corp information to DataOutputStream
   * @param dos DataOutputStream
   * @param newsCorp to save into DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveNewsCorp(final DataOutputStream dos,
      final NewsCorpData newsCorp) throws IOException {
    // How many galaxy stats to write, fixed number
    dos.writeByte(6);
    IOUtilities.writeString(dos, NewsCorpData.STAT_CREDIT);
    saveGalaxyStat(dos, newsCorp.getCredit());
    IOUtilities.writeString(dos, NewsCorpData.STAT_CULTURAL);
    saveGalaxyStat(dos, newsCorp.getCultural());
    IOUtilities.writeString(dos, NewsCorpData.STAT_MILITARY);
    saveGalaxyStat(dos, newsCorp.getMilitary());
    IOUtilities.writeString(dos, NewsCorpData.STAT_PLANETS);
    saveGalaxyStat(dos, newsCorp.getPlanets());
    IOUtilities.writeString(dos, NewsCorpData.STAT_POPULATION);
    saveGalaxyStat(dos, newsCorp.getPopulation());
    IOUtilities.writeString(dos, NewsCorpData.STAT_RESEARCH);
    saveGalaxyStat(dos, newsCorp.getResearch());
    // This is reserved for future extension like News information
    if (newsCorp.isNewsInformation()) {
      dos.writeByte(1);
      NewsData[] newsList = newsCorp.getNewsList();
      dos.writeInt(newsList.length);
      for (int i = 0; i < newsList.length; i++) {
        IOUtilities.writeString(dos, newsList[i].getImageInstructions());
        IOUtilities.writeString(dos, newsList[i].getNewsText());
      }
      newsList = newsCorp.getUpcomingNews();
      dos.writeInt(newsList.length);
      for (int i = 0; i < newsList.length; i++) {
        IOUtilities.writeString(dos, newsList[i].getImageInstructions());
        IOUtilities.writeString(dos, newsList[i].getNewsText());
      }
    } else {
      dos.writeByte(0);
    }
  }

  /**
   * Save Galaxy stat to DataOuputStream
   * @param dos DataOutputStream
   * @param stat GalaxyStat to save
   * @throws IOException if there is any problem with DataOutputStream
   */
  private void saveGalaxyStat(final DataOutputStream dos,
      final GalaxyStat stat) throws IOException {
    int[][] data = stat.getGalaxyData();
    int dataSize = data[0].length;
    dos.writeByte(dataSize);
    for (int i = 0; i < stat.getMaxPlayers(); i++) {
      for (int j = 0; j < dataSize; j++) {
        dos.writeInt(data[i][j]);
      }
    }
  }

  /**
   * Load Galaxy stat from DataInputStream
   * @param dis DataInputStream
   * @param stat GalaxyStat to load
   * @throws IOException if there is any problem with DataInputStream
   */
  private void loadGalaxyStat(final DataInputStream dis,
      final GalaxyStat stat) throws IOException {
    int dataSize = dis.read();
    for (int i = 0; i < stat.getMaxPlayers(); i++) {
      for (int j = 0; j < dataSize; j++) {
        int value = dis.readInt();
        stat.addStat(i, value);
      }
    }
  }

  /**
   * Create the NewsCorpData from DataInputStream
   * @param dis DataInputStream
   * @param numberOfPlayers Number of players in game
   * @return NewsCorpData from DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public NewsCorpData restoreNewsCorp(final DataInputStream dis,
      final int numberOfPlayers) throws IOException {
      NewsCorpData result =  new NewsCorpData(numberOfPlayers);
      int numberOfStats = dis.read();
      for (int i = 0; i < numberOfStats; i++) {
        String name = IOUtilities.readString(dis);
        if (name.equals(NewsCorpData.STAT_CREDIT)) {
          loadGalaxyStat(dis, result.getCredit());
        } else if (name.equals(NewsCorpData.STAT_CULTURAL)) {
          loadGalaxyStat(dis, result.getCultural());
        } else if (name.equals(NewsCorpData.STAT_MILITARY)) {
          loadGalaxyStat(dis, result.getMilitary());
        } else if (name.equals(NewsCorpData.STAT_PLANETS)) {
          loadGalaxyStat(dis, result.getPlanets());
        } else if (name.equals(NewsCorpData.STAT_POPULATION)) {
          loadGalaxyStat(dis, result.getPopulation());
        } else if (name.equals(NewsCorpData.STAT_RESEARCH)) {
          loadGalaxyStat(dis, result.getResearch());
        } else {
          // Just reading unknown galaxy stat away
          loadGalaxyStat(dis, new GalaxyStat(numberOfStats, "UNKNOWN"));
        }
      }
      int newInformation = dis.read();
      if (newInformation == 1) {
        int number = dis.readInt();
        for (int i = 0; i < number; i++) {
          String instruction = IOUtilities.readString(dis);
          String text = IOUtilities.readString(dis);
          NewsData data = new NewsData();
          data.setImageInstructions(instruction);
          data.setNewsText(text);
          result.addNews(data);
        }
        result.clearNewsList();
        number = dis.readInt();
        for (int i = 0; i < number; i++) {
          String instruction = IOUtilities.readString(dis);
          String text = IOUtilities.readString(dis);
          NewsData data = new NewsData();
          data.setImageInstructions(instruction);
          data.setNewsText(text);
          result.addNews(data);
        }
      } else if (newInformation != 0) {
        throw new IOException("NewsInformation field is not zero or one!");
      }
      return result;
  }

}
