package org.openRealmOfStars.utilities;

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
 * Logger class
 *
 */
public class Logger {

  /**
   * Default log size in rows
   */
  private static final int DEFAULT_LOG_SIZE = 11;

  /**
   * Array texts in logger
   */
  private String[] textLog;

  /**
   * Build logger with Default log size
   */
  public Logger() {
    this(DEFAULT_LOG_SIZE);
  }

  /**
   * Build logger with custom log size.
   * @param logSize How many rows is available in logger
   */
  public Logger(final int logSize) {
    textLog = new String[logSize];
    for (int i = 0; i < logSize; i++) {
      textLog[i] = "";
    }
  }

  /**
   * Add new text log
   * @param message Text to add
   */
  public void addLog(final String message) {
    System.arraycopy(textLog, 0, textLog, 1, textLog.length - 1);
    textLog[0] = message;
  }

  /**
   * Get Logged messages as a String array
   * @return String array
   */
  public String[] getLogMessages() {
    return textLog;
  }

  /**
   * Get number of rows in logger
   * @return Number of rows
   */
  public int size() {
    return textLog.length;
  }

  /**
   * Get Logged message by index
   * @param index to get log message
   * @return Message as a String
   */
  public String getMessage(final int index) {
    return textLog[index];
  }
}
