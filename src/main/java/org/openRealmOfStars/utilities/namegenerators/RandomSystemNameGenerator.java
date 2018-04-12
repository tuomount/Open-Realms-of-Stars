package org.openRealmOfStars.utilities.namegenerators;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2018  Tuomo Untinen
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
 * Random System name Generator AKA Sun name generator
 *
 */

public class RandomSystemNameGenerator extends NameGenerator {

  /**
   * List of all greek alphabets
   */
  private static final String[] GREEK_ALPHABET = {"Alpha", "Beta", "Gamma",
      "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota", "Kappa", "Lambda",
      "Mu", "Nu", "Xi", "Omicron", "Pi", "Rho", "Sigma", "Tau", "Upsilon",
      "Phi", "Chi", "Psi", "Omega" };

  /**
   * First parts of name list
   */
  private static final String[] FIRST_PART = {"Eri", "Cas", "Scor", "Crux",
      "Can", "Leo", "Tau", "Ly", "And", "Vir", "Aqua", "Cyg", "Cor", "Cep",
      "Grus", "Dra", "Cap", "Per", "Pe", "Au", "Ge", "Co", "Cra", "Sag", "Or",
      "Hy", "Pis", "Lynx", "Aqu", "Ser", "Pho", "Le", "Boo", "Ce", "Ar", "Ca",
      "Op", "Del", "Her", "Vul", "Pa", "Lib", "Cen", "Bo", "Ur", "La", "Li",
      "Qu", "Hel", "Rig", "Sna", "Zoo", "Fer", "Oxy", "Pol", "Hes"};

  /**
   * Second parts of name list
   */
  private static final String[] SECOND_PART = {"da", "sio", "pius", "cer",
      "rus", "ra", "rome", "go", "rius", "nus", "vus", "heus", "co", "ri",
      "tau", "re", "seus", "ga", "mi", "lum", "ter", "itta", "ion", "dra",
      "ces", "ila", "pens", "enix", "pus", "tes", "tus", "ies", "rina", "hiu",
      "phi", "cu", "pe", "vo", "ra", "sa", "ser", "el", "rium" };

  /**
   * Third parts of name list
   */
  private static final String[] THIRD_PART = {"nus", "peia", "da", "nis",
      "chus", "ni", "rum", "li", "e", "sus", "ga", "les", "cula", "si", "rus",
      "ri", "na", "nae", "alis", "chus", "rius" };

  /**
   * How many letters are allowed in parts so that third part is not added.
   */
  private static final int LIMIT_FOR_TWO_PARTS = 12;

  /**
   * Constructor for Random SystemNameGenerator.
   */
  public RandomSystemNameGenerator() {
    setUsedNames(new ArrayList<String>());
  }

  @Override
  protected String generateRandomName() {
    StringBuilder sb = new StringBuilder();
    sb.append(
        GREEK_ALPHABET[DiceGenerator.getRandom(GREEK_ALPHABET.length - 1)]);
    sb.append(" ");
    int parts = 1;
    int random = DiceGenerator.getRandom(100);
    if (random > 30) {
      parts = 2;
    }
    if (random > 80) {
      parts = 3;
    }
    sb.append(FIRST_PART[DiceGenerator.getRandom(FIRST_PART.length - 1)]);
    if (parts > 1) {
      sb.append(SECOND_PART[DiceGenerator.getRandom(SECOND_PART.length - 1)]);
    }
    if (parts > 2 && sb.toString().length() < LIMIT_FOR_TWO_PARTS) {
      sb.append(THIRD_PART[DiceGenerator.getRandom(THIRD_PART.length - 1)]);
    }
    return sb.toString();
  }
  /**
   * Converts integer into Roman number. Integer can be between 1-10.
   * @param i Integer to convert
   * @return String with Roman number.
   */
  public static String numberToRoman(final int i) {
    String result = "I";
    switch (i) {
    case 1:
      result = "I";
      break;
    case 2:
      result = "II";
      break;
    case 3:
      result = "III";
      break;
    case 4:
      result = "IV";
      break;
    case 5:
      result = "V";
      break;
    case 6:
      result = "VI";
      break;
    case 7:
      result = "VII";
      break;
    case 8:
      result = "VIII";
      break;
    case 9:
      result = "IX";
      break;
    case 10:
      result = "X";
      break;
    default:
      result = "I";
      break;
    }
    return result;
  }
}
