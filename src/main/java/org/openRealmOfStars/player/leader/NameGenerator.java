package org.openRealmOfStars.player.leader;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
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
* Leader Name Generator.
*
*/
public final class NameGenerator {

  /**
   * Just hiding the constructor.
   */
  private NameGenerator() {
    // Nothing to do.
  }
  /**
   * Generate human leader name
   * @param gender Leader gender
   * @return Human leader name
   */
  private static String generateHumanName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    if (gender == Gender.MALE) {
      switch (DiceGenerator.getRandom(26)) {
        default:
        case 0: sb.append("Albert"); break;
        case 1: sb.append("Bob"); break;
        case 2: sb.append("Calvin"); break;
        case 3: sb.append("Dylan"); break;
        case 4: sb.append("William"); break;
        case 5: sb.append("Jack"); break;
        case 6: sb.append("Lee"); break;
        case 7: sb.append("James"); break;
        case 8: sb.append("John"); break;
        case 9: sb.append("George"); break;
        case 10: sb.append("Billy"); break;
        case 11: sb.append("Joe"); break;
        case 12: sb.append("Rodney"); break;
        case 13: sb.append("Chuck"); break;
        case 14: sb.append("Aaron"); break;
        case 15: sb.append("Donald"); break;
        case 16: sb.append("Barry"); break;
        case 17: sb.append("Saul"); break;
        case 18: sb.append("Seamus"); break;
        case 19: sb.append("Walter"); break;
        case 20: sb.append("Kevin"); break;
        case 21: sb.append("Steve"); break;
        case 22: sb.append("Eric"); break;
        case 23: sb.append("Karl"); break;
        case 24: sb.append("Malcom"); break;
        case 25: sb.append("Alan"); break;
        case 26: sb.append("Thomas"); break;
      }
    }
    if (gender == Gender.FEMALE) {
      switch (DiceGenerator.getRandom(22)) {
        default:
        case 0: sb.append("Samatha"); break;
        case 1: sb.append("Kendra"); break;
        case 2: sb.append("Elizabeth"); break;
        case 3: sb.append("Helena"); break;
        case 4: sb.append("Kara"); break;
        case 5: sb.append("Sharon"); break;
        case 6: sb.append("Diana"); break;
        case 7: sb.append("Erin"); break;
        case 8: sb.append("Cally"); break;
        case 9: sb.append("Linda"); break;
        case 10: sb.append("Marie"); break;
        case 11: sb.append("Joana"); break;
        case 12: sb.append("Lisa"); break;
        case 13: sb.append("Anna"); break;
        case 14: sb.append("Elsa"); break;
        case 15: sb.append("Laura"); break;
        case 16: sb.append("Nicki"); break;
        case 17: sb.append("Grace"); break;
        case 18: sb.append("Kaylee"); break;
        case 19: sb.append("Gina"); break;
        case 20: sb.append("Melinda"); break;
        case 21: sb.append("Kimberly"); break;
        case 22: sb.append("Tia"); break;
      }
    }
    if (DiceGenerator.getRandom(3) == 0) {
      sb.append(" ");
      switch (DiceGenerator.getRandom(17)) {
        default:
        case 0: sb.append("A."); break;
        case 1: sb.append("B."); break;
        case 2: sb.append("C."); break;
        case 3: sb.append("D."); break;
        case 4: sb.append("W."); break;
        case 5: sb.append("J."); break;
        case 6: sb.append("L."); break;
        case 7: sb.append("J."); break;
        case 8: sb.append("G."); break;
        case 9: sb.append("R."); break;
        case 10: sb.append("S."); break;
        case 11: sb.append("K."); break;
        case 12: sb.append("E."); break;
        case 13: sb.append("K."); break;
        case 14: sb.append("M."); break;
        case 15: sb.append("H."); break;
        case 16: sb.append("N."); break;
        case 17: sb.append("T."); break;
      }
    }
    sb.append(" ");
    //Surname
    switch (DiceGenerator.getRandom(43)) {
      default:
      case 0: sb.append("Adama"); break;
      case 1: sb.append("Nagala"); break;
      case 2: sb.append("Fisk"); break;
      case 3: sb.append("Spencer"); break;
      case 4: sb.append("Shaw"); break;
      case 5: sb.append("Valerii"); break;
      case 6: sb.append("Thrace"); break;
      case 7: sb.append("Corman"); break;
      case 8: sb.append("Cain"); break;
      case 9: sb.append("Riker"); break;
      case 10: sb.append("Kirk"); break;
      case 11: sb.append("McCoy"); break;
      case 12: sb.append("Hawkins"); break;
      case 13: sb.append("Jameson"); break;
      case 14: sb.append("Torres"); break;
      case 15: sb.append("Armstrong"); break;
      case 16: sb.append("O'Neil"); break;
      case 17: sb.append("Rush"); break;
      case 18: sb.append("Young"); break;
      case 19: sb.append("Hunt"); break;
      case 20: sb.append("Harper"); break;
      case 21: sb.append("Hammond"); break;
      case 22: sb.append("Carter"); break;
      case 23: sb.append("Jackson"); break;
      case 24: sb.append("Sheppard"); break;
      case 25: sb.append("McKay"); break;
      case 26: sb.append("Weir"); break;
      case 27: sb.append("Crichton"); break;
      case 28: sb.append("Reynolds"); break;
      case 29: sb.append("Serra"); break;
      case 30: sb.append("Cobb"); break;
      case 31: sb.append("Tam"); break;
      case 32: sb.append("Frye"); break;
      case 33: sb.append("Zelnick"); break;
      case 34: sb.append("Isamu"); break;
      case 35: sb.append("Robinson"); break;
      case 36: sb.append("Mercer"); break;
      case 37: sb.append("Taylor"); break;
      case 38: sb.append("Bridger"); break;
      case 39: sb.append("Ford"); break;
      case 40: sb.append("Brody"); break;
      case 41: sb.append("Kovacs"); break;
      case 42: sb.append("Kawahara"); break;
      case 43: sb.append("Hideki"); break;
    }
    return sb.toString();
  }

  /**
   * Generate Leader name.
   * @param race SpaceRace for which name will generated
   * @param gender Leader gender
   * @return Leader name.
   */
  public static String generateName(final SpaceRace race, final Gender gender) {
    if (race == SpaceRace.HUMAN) {
      return generateHumanName(gender);
    }
    return "Noname";
  }
}
