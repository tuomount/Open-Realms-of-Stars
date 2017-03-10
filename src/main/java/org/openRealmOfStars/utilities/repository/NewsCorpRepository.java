package org.openRealmOfStars.utilities.repository;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openRealmOfStars.starMap.newsCorp.NewsCorpData;

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
    //TODO make save
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
      return new NewsCorpData(numberOfPlayers);
  }

}
