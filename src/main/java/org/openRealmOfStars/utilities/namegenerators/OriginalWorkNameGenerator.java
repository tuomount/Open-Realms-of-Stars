package org.openRealmOfStars.utilities.namegenerators;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* OriginalWork name generator.
*
*/
public class OriginalWorkNameGenerator extends NameGenerator {

  /**
   * List of random adjectives
   */
  private static final String[] ADJECTIVES = {"shadow", "purple", "blue",
      "metal", "hot", "blazing", "unstable", "unbreakable",
      "molten", "cyan", "orange", "red", "green", "gaseous", "icy",
      "rocky", "epic", "galactic", "space", "lovely", "random",
      "cute", "poisonous", "dark", "dead", "deep", "hopeful",
      "long", "old", "young", "pretty", "last", "first"
  };

  /**
   * List of random objects
   */
  private static final String[] OBJECTS = {"moon", "planet", "cloud",
      "rock", "volcano", "station", "leader", "king", "empire",
      "voyage", "sea", "monster", "monster", "love", "code", "AI",
      "robot", "forest", "desert", "agent", "business", "citizen",
      "dream", "factory", "friend", "heart", "hotel", "meeting",
      "mission", "night", "professor", "rage", "room", "star",
      "theory", "trouble", "letter"
  };

  /**
   * List of random objects2
   */
  private static final String[] OBJECTS2 = {"shadow", "danger", "revenge",
      "strike", "crash", "lost", "war", "factory", "skeleton",
      "plan", "conquer", "destruction", "creation", "escape"
  };

  /**
   * Constructor for original work name generator
   */
  public OriginalWorkNameGenerator() {
    setUsedNames(new ArrayList<String>());
  }

  /**
   * Change first letter into capital letter
   * @param str String which to capitalize
   * @return String
   */
  private String capitalize(final String str) {
    if (str.length() >= 2) {
      String first = str.substring(0, 1).toUpperCase();
      return first + str.substring(1);
    }
    if (str.length() == 1) {
      String first = str.substring(0, 1).toUpperCase();
      return first;
    }
    return str;
  }

  @Override
  protected String generateRandomName() {
    StringBuilder sb = new StringBuilder();
    int type = DiceGenerator.getRandom(6);
    if (type == 0) {
      sb.append("At The ");
      sb.append(capitalize(ADJECTIVES[DiceGenerator.getRandom(
          ADJECTIVES.length - 1)]));
      sb.append(" ");
      sb.append(capitalize(OBJECTS[DiceGenerator.getRandom(
          OBJECTS.length - 1)]));
    }
    if (type == 1) {
      sb.append("The ");
      sb.append(capitalize(ADJECTIVES[DiceGenerator.getRandom(
          ADJECTIVES.length - 1)]));
      sb.append(" ");
      sb.append(capitalize(OBJECTS[DiceGenerator.getRandom(
          OBJECTS.length - 1)]));
    }
    if (type == 2) {
      sb.append("The ");
      sb.append(capitalize(OBJECTS[DiceGenerator.getRandom(
          OBJECTS.length - 1)]));
      sb.append(" ");
      sb.append(DiceGenerator.getRandom(1000));
    }
    if (type == 3) {
      sb.append("The ");
      sb.append(capitalize(OBJECTS2[DiceGenerator.getRandom(
          OBJECTS2.length - 1)]));
      sb.append(" Of ");
      sb.append(capitalize(ADJECTIVES[DiceGenerator.getRandom(
          ADJECTIVES.length - 1)]));
      sb.append(" ");
      sb.append(capitalize(OBJECTS[DiceGenerator.getRandom(
          OBJECTS.length - 1)]));
    }
    if (type == 4) {
      sb.append("From The ");
      sb.append(capitalize(ADJECTIVES[DiceGenerator.getRandom(
          ADJECTIVES.length - 1)]));
      sb.append(" ");
      sb.append(capitalize(OBJECTS[DiceGenerator.getRandom(
          OBJECTS.length - 1)]));
    }
    if (type == 5) {
      sb.append("The ");
      sb.append(capitalize(OBJECTS2[DiceGenerator.getRandom(
          OBJECTS2.length - 1)]));
      sb.append(" ");
      sb.append(DiceGenerator.getRandom(1000));
    }
    if (type == 6) {
      sb.append("It Came From ");
      sb.append(capitalize(ADJECTIVES[DiceGenerator.getRandom(
          ADJECTIVES.length - 1)]));
      sb.append(" ");
      sb.append(capitalize(OBJECTS[DiceGenerator.getRandom(
          OBJECTS.length - 1)]));
    }
    return sb.toString();
  }

}
