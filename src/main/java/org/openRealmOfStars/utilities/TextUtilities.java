package org.openRealmOfStars.utilities;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2020 Tuomo Untinen
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

/**
*
* Text Utilities
*
*/
public final class TextUtilities {

  /**
   * Hiding the constructor
   */
  private TextUtilities() {
    // Nothing to do
  }

  /**
   * Get integer as ordering text
   * @param number Integer to convert ordering text
   * @return Ordering text
   * @throws IllegalArgument if integer is below one
   */
  public static String getOrderNumberAsText(final int number) {
    if (number < 1) {
      throw new IllegalArgumentException("Integers below one are not"
          + " allowed!");
    }
    if (number == 1) {
      return "first";
    } else if (number == 2) {
      return "second";
    } else if (number == 3) {
      return "third";
    } else if (number == 4) {
      return "fourth";
    } else if (number == 11) {
      return "11th";
    } else if (number == 12) {
      return "12th";
    } else if (number == 13) {
      return "13th";
    } else {
      String temp = Integer.toString(number);
      char lastCh = temp.charAt(temp.length() - 1);
      char secondLastCh = '0';
      if (temp.length() > 2) {
        secondLastCh = temp.charAt(temp.length() - 2);
      }
      if (lastCh == '1' && secondLastCh != '1') {
        return temp + "st";
      } else if (lastCh == '2' && secondLastCh != '1') {
        return temp + "nd";
      } else if (lastCh == '3' && secondLastCh != '1') {
        return temp + "rd";
      } else {
        return temp + "th";
      }
    }

  }

  /**
   * Remove line changes from text.
   * Add space if needed and remove extra spaces.
   * @param text Text to handle
   * @return Handled text.
   */
  public static String removeLineChanges(final String text) {
    StringBuilder sb = new StringBuilder(text.length() + 10);
    boolean lastSpace = false;
    boolean start = true;
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      if (ch == '\n') {
        if (!lastSpace && !start) {
          sb.append(" ");
          lastSpace = true;
        }
      } else if (ch == ' ') {
        if (!lastSpace && !start) {
          sb.append(" ");
        }
        lastSpace = true;
      } else {
        lastSpace = false;
        start = false;
        sb.append(ch);
      }
    }
    return sb.toString();
  }

  /**
   * Escape typical json escape character.
   * @param text Original text
   * @return JSON escaped string
   */
  public static String escapeJson(final String text) {
    StringBuilder sb = new StringBuilder(text.length() + 10);
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      switch (ch) {
        case '\\': sb.append("\\\\"); break;
        case '\n': sb.append("\\n"); break;
        case '\t': sb.append("\\t"); break;
        case '\b': sb.append("\\b"); break;
        case '\f': sb.append("\\f"); break;
        case '\r': sb.append("\\r"); break;
        case '/': sb.append("\\/"); break;
        case '"': sb.append("\\\""); break;
        default:
          sb.append(ch);
          break;
      }
    }
    return sb.toString();
  }
  /**
   * UNescape typical json escape characters.
   * @param text JSON escaped string
   * @return Unescaped String
   */
  public static String unescapeJson(final String text) {
    StringBuilder sb = new StringBuilder(text.length() + 10);
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      if (ch == '\\') {
        i++;
        ch = text.charAt(i);
        switch (ch) {
        case '\\': sb.append('\\'); break;
        case 'n': sb.append('\n'); break;
        case 't': sb.append('\t'); break;
        case 'b': sb.append('\b'); break;
        case 'f': sb.append('\f'); break;
        case 'r': sb.append('\r'); break;
        case '/': sb.append('/'); break;
        case '"': sb.append('"'); break;
        default:
          sb.append(ch);
          break;
        }
      } else {
        sb.append(ch);
      }
    }
    return sb.toString();
  }
  /**
   * Handle character escapes with one single loop.
   * @param text Text to handle
   * @return Handled text
   */
  public static String handleEscapes(final String text) {
    StringBuilder sb = new StringBuilder(text.length() + 10);
    for (int i = 0; i < text.length(); i++) {
      char ch = text.charAt(i);
      if (ch == '\\') {
        i++;
        ch = text.charAt(i);
        switch (ch) {
          case 'n': {
            sb.append("\n");
            break;
          }
          default: {
            sb.append("\\");
            sb.append(ch);
          break;
          }
        }
        continue;
      }
      sb.append(ch);
    }
    return sb.toString();
  }

  /**
   * Concanate two string arrays into one string array.
   * @param array1 First String array
   * @param array2 Second String array
   * @return Result array
   */
  public static String[] concanateStringArrays(final String[] array1,
      final String[] array2) {
    String[] result = new String[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }

  /**
   * Remove duplicate lines that are following each others.
   * @param input String input
   * @return String without duplicate lines.
   */
  public static String removeDuplicateLines(final String input) {
    String[] strings = input.split("\n\n");
    String previusLine = "";
    StringBuilder sb = new StringBuilder(input.length() - input.length() / 10);
    for (String str : strings) {
      if (!str.equals(previusLine)) {
        previusLine = str;
        sb.append(str);
        sb.append("\n\n");
      }
    }
    return sb.toString();
  }
}
