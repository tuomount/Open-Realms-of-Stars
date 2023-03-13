package org.openRealmOfStars.starMap.history;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.openRealmOfStars.mapTiles.TileNames;
import org.openRealmOfStars.mapTiles.Tiles;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.history.event.CultureEvent;
import org.openRealmOfStars.starMap.history.event.Event;
import org.openRealmOfStars.starMap.history.event.EventOnPlanet;
import org.openRealmOfStars.starMap.history.event.EventType;
import org.openRealmOfStars.starMap.history.event.LeaderEvent;
import org.openRealmOfStars.utilities.IOUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2021,2023 Tuomo Untinen
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
* Historical information about game being played
*
*/
public class History {

  /**
   * List of star years in history
   */
  private ArrayList<HistoryTurn> listOfTurns;

  /**
   * Magic string for history file
   */
  public static final String MAGIC_STRING = "OROS-HISTORY0.1";

  /**
   * Constructor for creating history for single game.
   */
  public History() {
    listOfTurns = new ArrayList<>();
  }

  /**
   * Get the number of star years in history. NOTE this does not
   * mean actual number of star years, just star years which contain historical
   * information.
   * @return Number of historically important star years
   */
  public int numberOfTurns() {
    return listOfTurns.size();
  }

  /**
   * Get the latest star year from history
   * @return Laster history turn or null if no star years at all.
   */
  public HistoryTurn getLatestTurn() {
    if (listOfTurns.size() > 0) {
      return listOfTurns.get(listOfTurns.size() - 1);
    }
    return null;
  }

  /**
   * Add Event to latest star year. This throws IllegalArgumentException
   * if no star years in history
   * @param event Event to add.
   */
  public void addEvent(final Event event) {
    HistoryTurn latest = getLatestTurn();
    if (latest != null) {
      latest.addEvent(event);
    } else {
      throw new IllegalArgumentException("No star years at all in history!");
    }
  }

  /**
   * Add new turn into history.
   * @param turnNumber Turn number to add
   */
  public void addTurn(final int turnNumber) {
    HistoryTurn  turn = new HistoryTurn(turnNumber);
    listOfTurns.add(turn);
  }

  /**
   * Add history turn
   * @param turn History turn to add.
   */
  public void addTurn(final HistoryTurn turn) {
    listOfTurns.add(turn);
  }

  /**
   * Get historical turn by index. Can return null if no turn
   * found by index.
   * @param index Index to search
   * @return HistoryTurn or null.
   */
  public HistoryTurn getByIndex(final int index) {
    if (index >= 0 && index < numberOfTurns()) {
      return listOfTurns.get(index);
    }
    return null;
  }

  /**
   * Write history data into outputStream
   * @param os OutputStream to write
   * @throws IOException If reading fail
   */
  public void writeToStream(final OutputStream os) throws IOException {
    IOUtilities.writeUTF8String(os, MAGIC_STRING);
    byte[] buffer = IOUtilities.convertIntTo16BitMsb(numberOfTurns());
    os.write(buffer);
    for (int i = 0; i < numberOfTurns(); i++) {
      HistoryTurn turn = getByIndex(i);
      os.write(turn.createByteArray());
    }
  }

  /**
   * Parse history from input stream.
   * @param is InputStream
   * @return History
   * @throws IOException If reading fails.
   */
  public static History readFromStream(final InputStream is)
      throws IOException {
    String magicStr = IOUtilities.readUTF8String(is);
    if (!magicStr.equals(MAGIC_STRING)) {
      throw new IOException("Stream does not seem to contain OROS history"
          + " or contains older version!");
    }
    int hi = is.read();
    int lo = is.read();
    if (lo == -1 || hi == -1) {
      throw new IOException("Stream does not contain valid count of "
          + "star years!");
    }
    int count = IOUtilities.convert16BitsToInt(hi, lo);
    History result = new History();
    for (int i = 0; i < count; i++) {
      HistoryTurn turn = HistoryTurn.parseHistoryTurn(is);
      result.addTurn(turn);
    }
    return result;
  }

  /**
   * Calculate culture from history up to certain turn number.
   * @param upToTurn Which turn number to calculate
   * @param starMap Starmap to fetch map size
   * @return Culture as interger array of arry.
   */
  public int[][] calculateCulture(final int upToTurn, final StarMap starMap) {
    int maxX = starMap.getMaxX();
    int maxY = starMap.getMaxY();
    int[][] culture = new int[maxX][maxY];
    // Clear the culture map
    for (int i = 0; i < maxY; i++) {
      for (int j = 0; j < maxX; j++) {
        culture[j][i] = -1;
      }
    }
    int max = upToTurn;
    if (listOfTurns.size() < upToTurn) {
      max = listOfTurns.size();
    }
    // Build culture map according the event
    for (int i = 0; i < max; i++) {
      HistoryTurn turn = listOfTurns.get(i);
      for (int j = 0; j < turn.getNumberOfEvents(); j++) {
        Event event = turn.getEvent(j);
        if (event instanceof CultureEvent) {
          CultureEvent cultureEvent = (CultureEvent) event;
          int x = cultureEvent.getCoordinate().getX();
          int y = cultureEvent.getCoordinate().getY();
          if (x >= 0 && x < maxX && y >= 0 && y < maxY) {
            culture[x][y] = cultureEvent.getPlayerIndex();
          }
        }
      }
    }
    return culture;
  }
  /**
   * Calculate tiles to override from history up to certain turn number.
   * @param upToTurn Which turn number to calculate
   * @param starMap Starmap to fetch map size
   * @return tiles as integer array of array.
   */
  public int[][] calculateOverrideTiles(final int upToTurn,
      final StarMap starMap) {
    int maxX = starMap.getMaxX();
    int maxY = starMap.getMaxY();
    int[][] tiles = new int[maxX][maxY];
    int max = upToTurn + 1;
    if (listOfTurns.size() < upToTurn) {
      max = listOfTurns.size();
    }
    // Build culture map according the event
    for (int i = 0; i < max; i++) {
      HistoryTurn turn = listOfTurns.get(i);
      for (int j = 0; j < turn.getNumberOfEvents(); j++) {
        Event event = turn.getEvent(j);
        if (event instanceof EventOnPlanet) {
          EventOnPlanet eventOnPlanet = (EventOnPlanet) event;
          if (eventOnPlanet.getType() == EventType.ARTIFICAL_PLANET_CREATED) {
            int x = eventOnPlanet.getCoordinate().getX();
            int y = eventOnPlanet.getCoordinate().getY();
            if (x >= 0 && x < maxX && y >= 0 && y < maxY) {
              tiles[x][y] = Tiles.getTileByName(
                  TileNames.ARTIFICIALWORLD1).getIndex();
            }
          }
        }
      }
    }
    return tiles;
  }
  /**
   * Method to update culture map according the newest changes.
   * Adds multiple culture events in history if current
   * culture map has changed in starmap.
   * @param starMap Containing newest culture information.
   */
  public void updateCultureEventMap(final StarMap starMap) {
    int maxX = starMap.getMaxX();
    int maxY = starMap.getMaxY();
    int[][] culture = calculateCulture(listOfTurns.size(), starMap);
    // Compare it to starmap and add missing culture
    for (int y = 0; y < maxY; y++) {
      for (int x = 0; x < maxX; x++) {
        CulturePower culturePower = starMap.getSectorCulture(x, y);
        int mapValue = -1;
        if (culturePower != null) {
          mapValue = culturePower.getHighestCulture();
        }
        if (culture[x][y] != mapValue) {
          Coordinate coord = new Coordinate(x, y);
          CultureEvent event = new CultureEvent(coord, mapValue);
          addEvent(event);
        }
      }
    }
  }

  /**
   * Get all leader events for realm and leader index.
   * @param realmIndex Realm Index to look for.
   * @param leaderIndex Leader index to look.
   * @return Array list of leader events for certain realm and leader.
   */
  public ArrayList<LeaderEvent> getLeaderEvents(final int realmIndex,
      final int leaderIndex) {
    ArrayList<LeaderEvent> events = new ArrayList<>();
    for (HistoryTurn turn : listOfTurns) {
      for (int i = 0; i < turn.getNumberOfEvents(); i++) {
        Event event = turn.getEvent(i);
        if (event.getType() == EventType.LEADER_EVENT) {
          LeaderEvent leaderEvent = (LeaderEvent) event;
          if (leaderEvent.getLeaderIndex() == leaderIndex
              && leaderEvent.getPlayerIndex() == realmIndex) {
            events.add(leaderEvent);
          }
        }
      }
    }
    return events;
  }
}
