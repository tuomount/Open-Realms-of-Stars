package org.openRealmOfStars.utilities;

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
}
