package org.openRealmOfStars.utilities.namegenerators;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;

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
* Random name Generator for rogue planet
*
*/
public class RoguePlanetNameGenerator {

  /**
   * First parts of name list
   */
  private static final String[] FIRST_PART = {"Cha", "Ta", "Spi", "Mono",
      "Nu", "Spa", "Ju", "Ne", "Ma", "Ki", "Pi", "Pha", "To", "So", "Mo",
      "Ro", "A", "Ba", "Bu", "Be", "Bo", "By", "Cu", "Ca", "Ce", "Co",
      "Cy", "E", "Fa", "Fe", "Fu", "Fo", "Fy", "Ga", "Ge", "Go", "Gu",
      "Gy", "Ha", "He", "Hu", "Hy", "I", "Ja", "Jy", "Je", "Ka", "Ke",
      "Ky", "La", "Lo", "Le", "O", "Pa", "Pe", "Qa", "Qu", "Que",
      "Ri", "Ra", "Re", "Ry", "Sa", "Se", "Ti", "The", "U", "Uno",
      "Ve", "Va", "Vy", "Vae", "Wu", "Xi", "Xa", "Xe", "Y", "Ze",
      "Zy", "Zu", "De", "Di", "Da"};

  /**
   * Second parts of name list
   */
  private static final String[] SECOND_PART = {"me", "mu", "ze", "ce", "pi",
      "tu", "ra", "ti", "cto", "gue", "ba", "ci", "da", "di", "de", "fa",
      "fe", "ge", "hi", "hu", "li", "la", "le", "ji", "jy", "ke", "ka",
      "ne", "na", "pa", "ri", "re", "si", "sa", "se", "sy", "ta", "ve",
      "va", "vy", "vu", "we", "wa", "wy", "wi", "xi", "xa", "xu", "xe",
      "ze", "zy", "zi"};

  /**
   * Third parts of name list
   */
  private static final String[] THIRD_PART = {"leon", "ra", "ros", "ter",
      "nus", "lo", "co", "raon", "kuu", "l", "r", "tion", "ris", "ber",
      "cer", "der", "ger", "pier", "mium", "nium", "rium", "ler", "tum",
      "zer", "wer", "per", "sium", "lion"};

  /**
   * Planet names already used
   */
  private ArrayList<String> usedNames;

  /**
   * Constructor for rogue planet name generator
   */
  public RoguePlanetNameGenerator() {
    usedNames = new ArrayList<>();
  }

  /**
   * Check if system name has previously generated.
   * @param name Name to check
   * @return True if name is already taken, otherwise false
   */
  private boolean previusMatch(final String name) {
    for (String oldName : usedNames) {
      if (oldName.equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Generate rogue planet name which is unique inside same generator.
   * @return Randomized rogue planet name
   */
  public String generate() {
    String name;
    do {
      name = generateRandomName();
    } while (previusMatch(name));
    usedNames.add(name);
    return name;
  }

  /**
   * Generate random name from using fixed syllable tables.
   * @return Randomized system name
   */
  private String generateRandomName() {
    StringBuilder sb = new StringBuilder();
    int parts = 1;
    int random = DiceGenerator.getRandom(100);
    if (random > 50) {
      parts = 2;
    }
    sb.append(FIRST_PART[DiceGenerator.getRandom(FIRST_PART.length - 1)]);
    if (parts == 1) {
      sb.append(THIRD_PART[DiceGenerator.getRandom(THIRD_PART.length - 1)]);
    }
    if (parts == 2) {
      sb.append(SECOND_PART[DiceGenerator.getRandom(SECOND_PART.length - 1)]);
      sb.append(THIRD_PART[DiceGenerator.getRandom(THIRD_PART.length - 1)]);
    }
    return sb.toString();
  }

}
