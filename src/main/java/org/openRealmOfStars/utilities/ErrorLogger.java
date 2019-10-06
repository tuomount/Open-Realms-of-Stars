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
 * Error logger class
 *
 */
public final class ErrorLogger {

  /**
   * Hiding the constructor for utility class.
   */
  private ErrorLogger() {
    // nothing to do here
  }

  /**
   * Log the error message
   * @param message Message the log into errors
   */
  public static void log(final String message) {
    System.err.println(message);
  }

  /**
   * Log the exception
   * @param exception Exception to log
   */
  public static void log(final Exception exception) {
    if (exception.getStackTrace().length == 0) {
      //This should never happens but you never know...
      ErrorLogger.log(exception.getMessage());
    }
    StackTraceElement stackTraceElement = exception.getStackTrace()[0];
    ErrorLogger.log(stackTraceElement.getClassName()
            + " - "
            + stackTraceElement.getLineNumber()
            + " - "
            +  exception.getMessage());
  }
}
