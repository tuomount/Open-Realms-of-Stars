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
  private static String generateSporkName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    if (gender == Gender.MALE) {
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("Bal"); break;
        case 1: sb.append("Lum"); break;
        case 2: sb.append("Rog"); break;
        case 3: sb.append("Gor"); break;
        case 4: sb.append("Bar"); break;
        case 5: sb.append("Ush"); break;
        case 6: sb.append("Mol"); break;
        case 7: sb.append("Mug"); break;
        case 8: sb.append("Ug"); break;
        case 9: sb.append("Mag"); break;
        case 10: sb.append("Yam"); break;
        case 11: sb.append("Shur"); break;
        case 12: sb.append("Kur"); break;
        case 13: sb.append("Yak"); break;
        case 14: sb.append("Sha"); break;
        case 15: sb.append("Dur"); break;
        case 16: sb.append("Gur"); break;
        case 17: sb.append("Bam"); break;
        case 18: sb.append("Mul"); break;
        case 19: sb.append("Tug"); break;
      }
      if (DiceGenerator.getRandom(3) == 0) {
        switch (DiceGenerator.getRandom(19)) {
          default:
          case 0: sb.append("al"); break;
          case 1: sb.append("um"); break;
          case 2: sb.append("og"); break;
          case 3: sb.append("or"); break;
          case 4: sb.append("ar"); break;
          case 5: sb.append("sh"); break;
          case 6: sb.append("ol"); break;
          case 7: sb.append("ug"); break;
          case 8: sb.append("or"); break;
          case 9: sb.append("ag"); break;
          case 10: sb.append("am"); break;
          case 11: sb.append("hur"); break;
          case 12: sb.append("ur"); break;
          case 13: sb.append("ak"); break;
          case 14: sb.append("ha"); break;
          case 15: sb.append("er"); break;
          case 16: sb.append("gur"); break;
          case 17: sb.append("gor"); break;
          case 18: sb.append("lum"); break;
          case 19: sb.append("bug"); break;
        }
      }
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("dush"); break;
        case 1: sb.append("bum"); break;
        case 2: sb.append("nar"); break;
        case 3: sb.append("lorz"); break;
        case 4: sb.append("barz"); break;
        case 5: sb.append("guk"); break;
        case 6: sb.append("mol"); break;
        case 7: sb.append("a"); break;
        case 8: sb.append("oa"); break;
        case 9: sb.append("song"); break;
        case 10: sb.append("gorn"); break;
        case 11: sb.append("zul"); break;
        case 12: sb.append("hag"); break;
        case 13: sb.append("on"); break;
        case 14: sb.append("rub"); break;
        case 15: sb.append("og"); break;
        case 16: sb.append("zat"); break;
        case 17: sb.append("mash"); break;
        case 18: sb.append("long"); break;
        case 19: sb.append("mog"); break;
      }
    }
    if (gender == Gender.FEMALE) {
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("Ba"); break;
        case 1: sb.append("Lu"); break;
        case 2: sb.append("Ro"); break;
        case 3: sb.append("Go"); break;
        case 4: sb.append("Bu"); break;
        case 5: sb.append("Ush"); break;
        case 6: sb.append("Mo"); break;
        case 7: sb.append("Mu"); break;
        case 8: sb.append("Ug"); break;
        case 9: sb.append("Ma"); break;
        case 10: sb.append("Ya"); break;
        case 11: sb.append("Shu"); break;
        case 12: sb.append("Ku"); break;
        case 13: sb.append("Ya"); break;
        case 14: sb.append("Sha"); break;
        case 15: sb.append("Du"); break;
        case 16: sb.append("Gu"); break;
        case 17: sb.append("Ta"); break;
        case 18: sb.append("Yo"); break;
        case 19: sb.append("Wu"); break;
      }
      if (DiceGenerator.getRandom(3) == 0) {
        switch (DiceGenerator.getRandom(19)) {
          default:
          case 0: sb.append("la"); break;
          case 1: sb.append("mu"); break;
          case 2: sb.append("go"); break;
          case 3: sb.append("ro"); break;
          case 4: sb.append("ra"); break;
          case 5: sb.append("sh"); break;
          case 6: sb.append("lo"); break;
          case 7: sb.append("gu"); break;
          case 8: sb.append("ro"); break;
          case 9: sb.append("ga"); break;
          case 10: sb.append("ma"); break;
          case 11: sb.append("ruh"); break;
          case 12: sb.append("ru"); break;
          case 13: sb.append("ka"); break;
          case 14: sb.append("ha"); break;
          case 15: sb.append("re"); break;
          case 16: sb.append("rug"); break;
          case 17: sb.append("rog"); break;
          case 18: sb.append("mul"); break;
          case 19: sb.append("gub"); break;
        }
      }
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("dush"); break;
        case 1: sb.append("bum"); break;
        case 2: sb.append("nar"); break;
        case 3: sb.append("lorz"); break;
        case 4: sb.append("barz"); break;
        case 5: sb.append("guk"); break;
        case 6: sb.append("mol"); break;
        case 7: sb.append("ta"); break;
        case 8: sb.append("toa"); break;
        case 9: sb.append("song"); break;
        case 10: sb.append("gorn"); break;
        case 11: sb.append("zul"); break;
        case 12: sb.append("hag"); break;
        case 13: sb.append("bon"); break;
        case 14: sb.append("rub"); break;
        case 15: sb.append("bog"); break;
        case 16: sb.append("zat"); break;
        case 17: sb.append("mash"); break;
        case 18: sb.append("long"); break;
        case 19: sb.append("mog"); break;
      }
    }
    sb.append(" ");
    switch (DiceGenerator.getRandom(19)) {
      default:
      case 0: sb.append("Turbo"); break;
      case 1: sb.append("Brave"); break;
      case 2: sb.append("Muscle"); break;
      case 3: sb.append("War"); break;
      case 4: sb.append("Bane"); break;
      case 5: sb.append("Blood"); break;
      case 6: sb.append("Neon"); break;
      case 7: sb.append("Red"); break;
      case 8: sb.append("Blue"); break;
      case 9: sb.append("Steel"); break;
      case 10: sb.append("Pulse"); break;
      case 11: sb.append("Radiant"); break;
      case 12: sb.append("Waste"); break;
      case 13: sb.append("Titanium"); break;
      case 14: sb.append("Ash"); break;
      case 15: sb.append("Rogue"); break;
      case 16: sb.append("Golden"); break;
      case 17: sb.append("Sulfur"); break;
      case 18: sb.append("Rare"); break;
      case 19: sb.append("Raw"); break;
    }
    switch (DiceGenerator.getRandom(19)) {
      default:
      case 0: sb.append("laser"); break;
      case 1: sb.append("station"); break;
      case 2: sb.append("destroyer"); break;
      case 3: sb.append("hunter"); break;
      case 4: sb.append("sword"); break;
      case 5: sb.append("lane"); break;
      case 6: sb.append("city"); break;
      case 7: sb.append("moon"); break;
      case 8: sb.append("solaris"); break;
      case 9: sb.append("phasor"); break;
      case 10: sb.append("rifle"); break;
      case 11: sb.append("ripper"); break;
      case 12: sb.append("land"); break;
      case 13: sb.append("armor"); break;
      case 14: sb.append("planet"); break;
      case 15: sb.append("hammer"); break;
      case 16: sb.append("gauntlet"); break;
      case 17: sb.append("fighter"); break;
      case 18: sb.append("metal"); break;
      case 19: sb.append("beard"); break;
    }
    return sb.toString();
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
    if (race == SpaceRace.SPORKS) {
      return generateSporkName(gender);
    }
    return "Noname";
  }
}
