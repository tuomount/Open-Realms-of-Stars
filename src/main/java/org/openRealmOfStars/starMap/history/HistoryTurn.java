package org.openRealmOfStars.starMap.history;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.openRealmOfStars.starMap.history.event.Event;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Historical turn containing list of events
*
*/
public class HistoryTurn {

  /**
   * Turn number
   */
  private int turnNumber;

  /**
   * List of Events
   */
  private ArrayList<Event> listOfEvents;

  /**
   * Constructor for History Turn
   * @param turn Turn number
   */
  public HistoryTurn(final int turn) {
    turnNumber = turn;
    listOfEvents = new ArrayList<>();
  }

  /**
   * Get Turn number.
   * @return turn number
   */
  public int getTurn() {
    return turnNumber;
  }

  /**
   * Get number of events in turn
   * @return Number of events
   */
  public int getNumberOfEvents() {
    return listOfEvents.size();
  }

  /**
   * Get Event by index.
   * @param index Index number for event
   * @return Event or null if not found by index
   */
  public Event getEvent(final int index) {
    if (index >= 0 && index < listOfEvents.size()) {
      return listOfEvents.get(index);
    }
    return null;
  }

  /**
   * Add Event to list.
   * @param event Event to add.
   */
  public void addEvent(final Event event) {
    listOfEvents.add(event);
  }

  /**
   * Create byte array of list of events in turn
   * @return Byte Array
   */
  public byte[] createByteArray() {
    byte[] buffer = null;
    try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
      // Writing the header first, word TURN and then turn number in 16 bits
      byte[] turnBuffer = "TURN".getBytes(StandardCharsets.UTF_8);
      os.write(turnBuffer);
      turnBuffer = IOUtilities.convertIntTo16BitMsb(turnNumber);
      os.write(turnBuffer);
      // Next is write how many events turn has
      turnBuffer = IOUtilities.convertIntTo16BitMsb(listOfEvents.size());
      os.write(turnBuffer);
      for (int i = 0; i < getNumberOfEvents(); i++) {
        Event event = getEvent(i);
        buffer = event.createByteArray();
        os.write(buffer);
      }
      buffer = os.toByteArray();
    } catch (IOException e) {
      ErrorLogger.log(e);
    }
    return buffer;
  }

  /**
   * Parse history turn from inputstream
   * @param is InputStream
   * @return Turn or null if no more turns found on stream.
   * @throws IOException If parsing/reading fails
   */
  public static HistoryTurn parseHistoryTurn(final InputStream is)
      throws IOException {
    byte[] turnBuffer = "TURN".getBytes(StandardCharsets.UTF_8);
    int check = 0;
    do {
      int value = is.read();
      if (value == -1) {
        return null;
      }
      byte byteValue = (byte) (value & 0xff);
      if (byteValue == turnBuffer[check]) {
        check++;
      } else {
        check = 0;
      }
    } while (check < turnBuffer.length);
    int turnNumber = IOUtilities.read16BitsToInt(is);
    int numberOfEvent = IOUtilities.read16BitsToInt(is);
    HistoryTurn turn = new HistoryTurn(turnNumber);
    for (int i = 0; i < numberOfEvent; i++) {
      Event event = Event.parseEvent(is);
      turn.addEvent(event);
    }
    return turn;
  }

}
