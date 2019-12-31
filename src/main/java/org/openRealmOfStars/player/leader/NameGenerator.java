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
* Leader Name Generator. This will generate only single name
* which can be considered as a surname.
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
   * @return Human leader name
   */
  private static String generateHumanName() {
    switch (DiceGenerator.getRandom(43)) {
      default:
      case 0: return "Adama";
      case 1: return "Nagala";
      case 2: return "Fisk";
      case 3: return "Spencer";
      case 4: return "Shaw";
      case 5: return "Valerii";
      case 6: return "Thrace";
      case 7: return "Corman";
      case 8: return "Cain";
      case 9: return "Riker";
      case 10: return "Kirk";
      case 11: return "McCoy";
      case 12: return "Hawkins";
      case 13: return "Jameson";
      case 14: return "Torres";
      case 15: return "Armstrong";
      case 16: return "O'Neil";
      case 17: return "Rush";
      case 18: return "Young";
      case 19: return "Hunt";
      case 20: return "Harper";
      case 21: return "Hammond";
      case 22: return "Carter";
      case 23: return "Jackson";
      case 24: return "Sheppard";
      case 25: return "McKay";
      case 26: return "Weir";
      case 27: return "Crichton";
      case 28: return "Reynolds";
      case 29: return "Serra";
      case 30: return "Cobb";
      case 31: return "Tam";
      case 32: return "Frye";
      case 33: return "Zelnick";
      case 34: return "Isamu";
      case 35: return "Robinson";
      case 36: return "Mercer";
      case 37: return "Taylor";
      case 38: return "Bridger";
      case 39: return "Ford";
      case 40: return "Brody";
      case 41: return "Kovacs";
      case 42: return "Kawahara";
      case 43: return "Hideki";
    }
  }

  /**
   * Generate Leader name.
   * @param race SpaceRace for which name will generated
   * @return Leader name.
   */
  public static String generateName(final SpaceRace race) {
    if (race == SpaceRace.HUMAN) {
      return generateHumanName();
    }
    return "Noname";
  }
}
