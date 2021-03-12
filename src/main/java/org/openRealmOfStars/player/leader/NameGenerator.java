package org.openRealmOfStars.player.leader;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020,2021 Tuomo Untinen
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
   * Generate Teuthidae leader name
   * @param gender Leader gender
   * @return Teuthidae leader name
   */
  private static String generateTeuthidaeName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    switch (DiceGenerator.getRandom(62)) {
      default:
      case 0: sb.append("Abh"); break;
      case 1: sb.append("Al"); break;
      case 2: sb.append("Amm"); break;
      case 3: sb.append("Ap"); break;
      case 4: sb.append("Arw"); break;
      case 5: sb.append("B'gn"); break;
      case 6: sb.append("D"); break;
      case 7: sb.append("Ayi'"); break;
      case 8: sb.append("Ayl"); break;
      case 9: sb.append("Coat"); break;
      case 10: sb.append("Cth"); break;
      case 11: sb.append("Ei'"); break;
      case 12: sb.append("Has"); break;
      case 13: sb.append("H'ctht"); break;
      case 14: sb.append("Grot"); break;
      case 15: sb.append("Gol"); break;
      case 16: sb.append("Gwar"); break;
      case 17: sb.append("Io"); break;
      case 18: sb.append("Ist"); break;
      case 19: sb.append("Ith'"); break;
      case 20: sb.append("Kaun"); break;
      case 21: sb.append("J"); break;
      case 22: sb.append("Khal'"); break;
      case 23: sb.append("Kass"); break;
      case 24: sb.append("K'nar"); break;
      case 25: sb.append("Kra"); break;
      case 26: sb.append("Lyth"); break;
      case 27: sb.append("M'"); break;
      case 28: sb.append("Mnom"); break;
      case 29: sb.append("Mor"); break;
      case 30: sb.append("Mort"); break;
      case 31: sb.append("Ngir"); break;
      case 32: sb.append("Nycr"); break;
      case 33: sb.append("Phar'"); break;
      case 34: sb.append("Quac"); break;
      case 35: sb.append("Quy"); break;
      case 36: sb.append("Q'"); break;
      case 37: sb.append("Rag"); break;
      case 38: sb.append("Rap"); break;
      case 39: sb.append("Raan"); break;
      case 40: sb.append("Rhog"); break;
      case 41: sb.append("Scat"); break;
      case 42: sb.append("Shak"); break;
      case 43: sb.append("Shat"); break;
      case 44: sb.append("Sheb"); break;
      case 45: sb.append("S't"); break;
      case 46: sb.append("Than"); break;
      case 47: sb.append("T'"); break;
      case 48: sb.append("Th'"); break;
      case 49: sb.append("Uit"); break;
      case 50: sb.append("Ut'"); break;
      case 51: sb.append("Vhuz"); break;
      case 52: sb.append("Vib"); break;
      case 53: sb.append("Vth"); break;
      case 54: sb.append("Vult"); break;
      case 55: sb.append("Xal"); break;
      case 56: sb.append("Xot"); break;
      case 57: sb.append("Yam"); break;
      case 58: sb.append("Y'"); break;
      case 59: sb.append("Zat"); break;
      case 60: sb.append("Zin"); break;
      case 61: sb.append("Zus"); break;
      case 62: sb.append("Z'"); break;
    }
    if (DiceGenerator.getRandom(3) == 0) {
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("uts"); break;
        case 1: sb.append("ag"); break;
        case 2: sb.append("og"); break;
        case 3: sb.append("lic"); break;
        case 4: sb.append("eghy"); break;
        case 5: sb.append("yll"); break;
        case 6: sb.append("ull"); break;
        case 7: sb.append("tele"); break;
        case 8: sb.append("oth"); break;
        case 9: sb.append("zoth"); break;
        case 10: sb.append("anai'"); break;
        case 11: {
          if (!sb.toString().endsWith("'")) {
            sb.append("'");
          }
          break;
        }
        case 12: sb.append("o"); break;
        case 13: sb.append("bas"); break;
        case 14: sb.append("digg"); break;
        case 15: sb.append("ll"); break;
        case 16: sb.append("rth'"); break;
        case 17: sb.append("a"); break;
        case 18: sb.append("hana"); break;
        case 19: sb.append("h"); break;
        case 20: sb.append("lat"); break;
        case 21: sb.append("zil"); break;
        case 22: sb.append("aril"); break;
        case 23: sb.append("gol"); break;
        case 24: sb.append("dar"); break;
        case 25: sb.append("togg"); break;
        case 26: sb.append("sha"); break;
      }
    }
    switch (DiceGenerator.getRandom(59)) {
      default:
      case 0: sb.append("olos"); break;
      case 1: sb.append("ala"); break;
      case 2: sb.append("eba"); break;
      case 3: sb.append("oom"); break;
      case 4: sb.append("assa"); break;
      case 5: sb.append("u"); break;
      case 6: sb.append("on"); break;
      case 7: sb.append("ig"); break;
      case 8: sb.append("ith"); break;
      case 9: sb.append("at"); break;
      case 10: sb.append("at"); break;
      case 11: sb.append("a"); break;
      case 12: sb.append("lor"); break;
      case 13: sb.append("tur"); break;
      case 14: sb.append("goth"); break;
      case 15: sb.append("h"); break;
      case 16: sb.append("ess"); break;
      case 17: sb.append("loth"); break;
      case 18: sb.append("d"); break;
      case 19: sb.append("asha"); break;
      case 20: sb.append("aqua"); break;
      case 21: sb.append("ngo"); break;
      case 22: sb.append("kru"); break;
      case 23: sb.append("gtha"); break;
      case 24: sb.append("st"); break;
      case 25: sb.append("ng"); break;
      case 26: sb.append("alia"); break;
      case 27: sb.append("ui"); break;
      case 28: sb.append("quan"); break;
      case 29: sb.append("ian"); break;
      case 30: sb.append("gh"); break;
      case 31: sb.append("lu"); break;
      case 32: sb.append("ma"); break;
      case 33: sb.append("ol'"); break;
      case 34: sb.append("hil"); break;
      case 35: sb.append("gen"); break;
      case 36: sb.append("yth"); break;
      case 37: sb.append("nalla"); break;
      case 38: sb.append("suan"); break;
      case 39: sb.append("og"); break;
      case 40: sb.append("ach"); break;
      case 41: sb.append("al"); break;
      case 43: sb.append("hak"); break;
      case 44: sb.append("ya"); break;
      case 45: sb.append("aroa"); break;
      case 46: sb.append("apac"); break;
      case 47: sb.append("uit"); break;
      case 48: sb.append("ulls"); break;
      case 49: sb.append("ompha"); break;
      case 50: sb.append("ur"); break;
      case 51: sb.append("ops"); break;
      case 52: sb.append("afu"); break;
      case 53: sb.append("li"); break;
      case 54: sb.append("ath"); break;
      case 55: sb.append("onac"); break;
      case 56: sb.append("hog"); break;
      case 57: sb.append("ak"); break;
      case 58: sb.append("on"); break;
      case 59: sb.append("ua"); break;
    }
    if (DiceGenerator.getRandom(3) == 0) {
      // Extra name with dash
      sb.append("-");
      switch (DiceGenerator.getRandom(24)) {
        default:
        case 0: sb.append("Al"); break;
        case 1: sb.append("Ap"); break;
        case 2: sb.append("Ayl"); break;
        case 3: sb.append("Cot"); break;
        case 4: sb.append("Er"); break;
        case 5: sb.append("Hus"); break;
        case 6: sb.append("Gor"); break;
        case 7: sb.append("Gar"); break;
        case 8: sb.append("Ith"); break;
        case 9: sb.append("Ker"); break;
        case 10: sb.append("Kar"); break;
        case 11: sb.append("Lyn"); break;
        case 12: sb.append("Mor"); break;
        case 13: sb.append("Nir"); break;
        case 14: sb.append("Omm"); break;
        case 15: sb.append("Rer"); break;
        case 16: sb.append("Ran"); break;
        case 17: sb.append("Ser"); break;
        case 18: sb.append("Teg"); break;
        case 19: sb.append("Ur"); break;
        case 20: sb.append("Vhuz"); break;
        case 21: sb.append("Vyr"); break;
        case 22: sb.append("Xun"); break;
        case 23: sb.append("Yr"); break;
        case 24: sb.append("Zur"); break;
      }
      switch (DiceGenerator.getRandom(13)) {
        default:
        case 0: sb.append("ath"); break;
        case 1: sb.append("og"); break;
        case 2: sb.append("ha"); break;
        case 3: sb.append("oct"); break;
        case 4: sb.append("er"); break;
        case 5: sb.append("oth"); break;
        case 6: sb.append("ai"); break;
        case 7: sb.append("ya"); break;
        case 8: sb.append("or"); break;
        case 9: sb.append("ash"); break;
        case 10: sb.append("un"); break;
        case 11: sb.append("loth"); break;
        case 12: sb.append("ugn"); break;
        case 13: sb.append("olka"); break;
      }
    }

    sb.append(" ");
    // Surname part
    switch (DiceGenerator.getRandom(31)) {
      default:
      case 0: sb.append("Abho"); break;
      case 1: sb.append("Ala"); break;
      case 2: sb.append("Ammut"); break;
      case 3: sb.append("Arw"); break;
      case 4: sb.append("Apocolot"); break;
      case 5: sb.append("Bya"); break;
      case 6: sb.append("Coin"); break;
      case 7: sb.append("Cthu"); break;
      case 8: sb.append("Dhu"); break;
      case 9: sb.append("Egni"); break;
      case 10: sb.append("Glee"); break;
      case 11: sb.append("Gol"); break;
      case 12: sb.append("Inpen"); break;
      case 13: sb.append("Kassoh"); break;
      case 14: sb.append("Khal"); break;
      case 15: sb.append("Lyth"); break;
      case 16: sb.append("Nom"); break;
      case 17: sb.append("Mort"); break;
      case 18: sb.append("Nyagh"); break;
      case 19: sb.append("Oorn"); break;
      case 20: sb.append("Pos"); break;
      case 21: sb.append("Rhogog"); break;
      case 22: sb.append("Shat"); break;
      case 23: sb.append("Sho"); break;
      case 24: sb.append("Shu"); break;
      case 25: sb.append("Thara"); break;
      case 26: sb.append("Toth"); break;
      case 27: sb.append("Vuz"); break;
      case 28: sb.append("Xala"); break;
      case 29: sb.append("Xit"); break;
      case 30: sb.append("Yhag"); break;
      case 31: sb.append("Ythogt"); break;
    }
    // Gender is mentioned in last syllable of surname
    if (gender == Gender.MALE) {
      if (sb.toString().endsWith("t")) {
        sb.append("ha");
      } else if (sb.toString().endsWith("th")) {
        sb.append("a");
      } else {
        sb.append("tha");
      }
    }
    if (gender == Gender.FEMALE) {
      if (sb.toString().endsWith("h")) {
        sb.append("oth");
      } else {
        sb.append("hoth");
      }
    }
    return sb.toString();
  }

  /**
   * Generate Chiraloid leader name
   * @param gender Leader gender
   * @return Chiraloid leader name
   */
  private static String generateChiraloidName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    if (gender == Gender.MALE) {
      switch (DiceGenerator.getRandom(49)) {
        default:
        case 0: sb.append("Ba"); break;
        case 1: sb.append("Ca"); break;
        case 2: sb.append("Da"); break;
        case 3: sb.append("Fa"); break;
        case 4: sb.append("Ga"); break;
        case 5: sb.append("Ha"); break;
        case 6: sb.append("Ja"); break;
        case 7: sb.append("Ka"); break;
        case 8: sb.append("La"); break;
        case 9: sb.append("Ma"); break;
        case 10: sb.append("Na"); break;
        case 11: sb.append("Pa"); break;
        case 12: sb.append("Ra"); break;
        case 13: sb.append("Sa"); break;
        case 14: sb.append("Ta"); break;
        case 15: sb.append("Va"); break;
        case 16: sb.append("Wa"); break;
        case 17: sb.append("Xa"); break;
        case 18: sb.append("Za"); break;
        case 19: sb.append("Be"); break;
        case 20: sb.append("Ce"); break;
        case 21: sb.append("De"); break;
        case 22: sb.append("Fe"); break;
        case 23: sb.append("Ge"); break;
        case 24: sb.append("He"); break;
        case 25: sb.append("Je"); break;
        case 26: sb.append("Ke"); break;
        case 27: sb.append("Le"); break;
        case 28: sb.append("Me"); break;
        case 29: sb.append("Ne"); break;
        case 30: sb.append("Pe"); break;
        case 31: sb.append("Re"); break;
        case 32: sb.append("Se"); break;
        case 33: sb.append("Te"); break;
        case 34: sb.append("Ve"); break;
        case 35: sb.append("We"); break;
        case 36: sb.append("Xe"); break;
        case 37: sb.append("Ze"); break;
        case 38: sb.append("Bro"); break;
        case 39: sb.append("Cro"); break;
        case 40: sb.append("Dro"); break;
        case 41: sb.append("Fro"); break;
        case 42: sb.append("Gro"); break;
        case 43: sb.append("Kra"); break;
        case 44: sb.append("Sra"); break;
        case 45: sb.append("Tra"); break;
        case 46: sb.append("Vra"); break;
        case 47: sb.append("Wra"); break;
        case 48: sb.append("Xra"); break;
        case 49: sb.append("Zro"); break;
      }
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("rd"); break;
        case 1: sb.append("h"); break;
        case 2: sb.append("zud"); break;
        case 3: sb.append("kiot"); break;
        case 4: sb.append("nilth"); break;
        case 5: sb.append("vos"); break;
        case 6: sb.append("vu"); break;
        case 7: sb.append("l"); break;
        case 8: sb.append("r"); break;
        case 9: sb.append("dird"); break;
        case 10: sb.append("geh"); break;
        case 11: sb.append("nura"); break;
        case 12: sb.append("los"); break;
        case 13: sb.append("vad"); break;
        case 14: sb.append("fi"); break;
        case 15: sb.append("var"); break;
        case 16: sb.append("st"); break;
        case 17: sb.append("tal"); break;
        case 18: sb.append("ka"); break;
        case 19: sb.append("gon"); break;
        case 20: sb.append("tu"); break;
        case 21: sb.append("zard"); break;
        case 22: sb.append("ria"); break;
        case 23: sb.append("s"); break;
        case 24: sb.append("dis"); break;
        case 25: sb.append("fit"); break;
        case 26: sb.append("dor"); break;
        case 27: sb.append("dudor"); break;
        case 28: sb.append("dador"); break;
        case 29: sb.append("vudor"); break;
        case 30: sb.append("xedar"); break;
        case 31: sb.append("max"); break;
        case 32: sb.append("vur"); break;
        case 33: sb.append("cust"); break;
        case 34: sb.append("rust"); break;
      }
    }
    if (gender == Gender.FEMALE) {
      switch (DiceGenerator.getRandom(54)) {
        default:
        case 0: sb.append("Ba"); break;
        case 1: sb.append("Ca"); break;
        case 2: sb.append("Da"); break;
        case 3: sb.append("Fa"); break;
        case 4: sb.append("Ga"); break;
        case 5: sb.append("Ha"); break;
        case 6: sb.append("Ja"); break;
        case 7: sb.append("Ka"); break;
        case 8: sb.append("La"); break;
        case 9: sb.append("Ma"); break;
        case 10: sb.append("Na"); break;
        case 11: sb.append("Pa"); break;
        case 12: sb.append("Ra"); break;
        case 13: sb.append("Sa"); break;
        case 14: sb.append("Ta"); break;
        case 15: sb.append("Va"); break;
        case 16: sb.append("Wa"); break;
        case 17: sb.append("Xa"); break;
        case 18: sb.append("Za"); break;
        case 19: sb.append("Be"); break;
        case 20: sb.append("Ce"); break;
        case 21: sb.append("De"); break;
        case 22: sb.append("Fe"); break;
        case 23: sb.append("Ge"); break;
        case 24: sb.append("He"); break;
        case 25: sb.append("Je"); break;
        case 26: sb.append("Ke"); break;
        case 27: sb.append("Le"); break;
        case 28: sb.append("Me"); break;
        case 29: sb.append("Ne"); break;
        case 30: sb.append("Pe"); break;
        case 31: sb.append("Re"); break;
        case 32: sb.append("Se"); break;
        case 33: sb.append("Te"); break;
        case 34: sb.append("Ve"); break;
        case 35: sb.append("We"); break;
        case 36: sb.append("Xe"); break;
        case 37: sb.append("Ze"); break;
        case 38: sb.append("Bo"); break;
        case 39: sb.append("Co"); break;
        case 40: sb.append("Do"); break;
        case 41: sb.append("Fo"); break;
        case 42: sb.append("Go"); break;
        case 43: sb.append("Ki"); break;
        case 44: sb.append("Si"); break;
        case 45: sb.append("Te"); break;
        case 46: sb.append("Vi"); break;
        case 47: sb.append("Wi"); break;
        case 48: sb.append("Xi"); break;
        case 49: sb.append("Zi"); break;
        case 50: sb.append("Bi"); break;
        case 51: sb.append("Ci"); break;
        case 52: sb.append("Di"); break;
        case 53: sb.append("Fi"); break;
        case 54: sb.append("Gi"); break;
      }
      if (DiceGenerator.getRandom(3) == 0) {
        switch (DiceGenerator.getRandom(19)) {
          default:
          case 0: sb.append("wav"); break;
          case 1: sb.append("var"); break;
          case 2: sb.append("fov"); break;
          case 3: sb.append("rek"); break;
          case 4: sb.append("lag"); break;
          case 5: sb.append("ser"); break;
          case 6: sb.append("dev"); break;
          case 7: sb.append("wuf"); break;
          case 8: sb.append("vul"); break;
          case 9: sb.append("ses"); break;
          case 10: sb.append("nus"); break;
          case 11: sb.append("ruh"); break;
          case 12: sb.append("sef"); break;
          case 13: sb.append("mak"); break;
          case 14: sb.append("gir"); break;
          case 15: sb.append("lov"); break;
          case 16: sb.append("vir"); break;
          case 17: sb.append("gal"); break;
          case 18: sb.append("gad"); break;
          case 19: sb.append("dus"); break;
        }
      }
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("myl"); break;
        case 1: sb.append("li"); break;
        case 2: sb.append("ly"); break;
        case 3: sb.append("ru"); break;
        case 4: sb.append("ne"); break;
        case 5: sb.append("ra"); break;
        case 6: sb.append("wa"); break;
        case 7: sb.append("nu"); break;
        case 8: sb.append("reth"); break;
        case 9: sb.append("lu"); break;
        case 10: sb.append("ni"); break;
        case 11: sb.append("zi"); break;
        case 12: sb.append("le"); break;
        case 13: sb.append("fe"); break;
        case 14: sb.append("vun"); break;
        case 15: sb.append("nu"); break;
        case 16: sb.append("wa"); break;
        case 17: sb.append("zash"); break;
        case 18: sb.append("nish"); break;
        case 19: sb.append("fru"); break;
      }
    }
    sb.append(" ");
    switch (DiceGenerator.getRandom(20)) {
      default:
      case 0: sb.append("Dark"); break;
      case 1: sb.append("Evil"); break;
      case 2: sb.append("Radical"); break;
      case 3: sb.append("Wicked"); break;
      case 4: sb.append("Wild"); break;
      case 5: sb.append("Bloody"); break;
      case 6: sb.append("Fierce"); break;
      case 7: sb.append("Red"); break;
      case 8: sb.append("Dead"); break;
      case 9: sb.append("Odd"); break;
      case 10: sb.append("Scary"); break;
      case 11: sb.append("Flaming"); break;
      case 12: sb.append("Blazing"); break;
      case 13: sb.append("Tiny"); break;
      case 14: sb.append("Massive"); break;
      case 15: sb.append("Melted"); break;
      case 16: sb.append("Sinful"); break;
      case 17: sb.append("Spicy"); break;
      case 18: sb.append("Shady"); break;
      case 19: sb.append("Sour"); break;
      case 20: sb.append("Soul"); break;
    }
    switch (DiceGenerator.getRandom(19)) {
      default:
      case 0: sb.append("ground"); break;
      case 1: sb.append("hill"); break;
      case 2: sb.append("plant"); break;
      case 3: sb.append("rocket"); break;
      case 4: sb.append("spear"); break;
      case 5: sb.append("spirit"); break;
      case 6: sb.append("torpedo"); break;
      case 7: sb.append("bomb"); break;
      case 8: sb.append("star"); break;
      case 9: sb.append("engine"); break;
      case 10: sb.append("scoop"); break;
      case 11: sb.append("syntesis"); break;
      case 12: sb.append("land"); break;
      case 13: sb.append("armor"); break;
      case 14: sb.append("planet"); break;
      case 15: sb.append("sabre"); break;
      case 16: sb.append("crown"); break;
      case 17: sb.append("tool"); break;
      case 18: sb.append("weapon"); break;
      case 19: sb.append("scythe"); break;
    }
    return sb.toString();
  }

  /**
   * Generate spork leader name
   * @param gender Leader gender
   * @return Spork leader name
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
   * Generate mothoid leader name
   * @param gender Leader gender
   * @return Mothoid leader name
   */
  private static String generateMothoidName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    if (gender == Gender.FEMALE) {
      switch (DiceGenerator.getRandom(5)) {
        default:
        case 0: sb.append("A'"); break;
        case 1: sb.append("E'"); break;
        case 2: sb.append("I'"); break;
        case 3: sb.append("O'"); break;
        case 4: sb.append("U'"); break;
        case 5: sb.append("Y'"); break;
      }
    }
    switch (DiceGenerator.getRandom(22)) {
      default:
      case 0: sb.append("Szz"); break;
      case 1: sb.append("Stzz"); break;
      case 2: sb.append("Btzz"); break;
      case 3: sb.append("Bzz"); break;
      case 4: sb.append("Tzz"); break;
      case 5: sb.append("Ttzz"); break;
      case 6: sb.append("Btss"); break;
      case 7: sb.append("Bss"); break;
      case 8: sb.append("Tss"); break;
      case 9: sb.append("Ttss"); break;
      case 10: sb.append("Sss"); break;
      case 11: sb.append("Buzz"); break;
      case 12: sb.append("Bezz"); break;
      case 13: sb.append("Bess"); break;
      case 14: sb.append("Btez"); break;
      case 15: sb.append("Btes"); break;
      case 16: sb.append("Pzz"); break;
      case 17: sb.append("Pss"); break;
      case 18: sb.append("Ptzz"); break;
      case 19: sb.append("Ptss"); break;
      case 20: sb.append("Srzz"); break;
      case 21: sb.append("Srss"); break;
      case 22: sb.append("Surz"); break;
      case 23: sb.append("Surs"); break;
    }
    if (DiceGenerator.getRandom(1) == 0) {
      switch (DiceGenerator.getRandom(12)) {
        default:
        case 0: sb.append("s"); break;
        case 1: sb.append("z"); break;
        case 2: sb.append("sur"); break;
        case 3: sb.append("zz"); break;
        case 4: sb.append("zs"); break;
        case 5: sb.append("zur"); break;
        case 6: sb.append("buzz"); break;
        case 7: sb.append("uzz"); break;
        case 8: sb.append("uss"); break;
        case 9: sb.append("utz"); break;
        case 10: sb.append("uts"); break;
        case 11: sb.append("etz"); break;
        case 12: sb.append("ets"); break;
      }
    }
    if (DiceGenerator.getRandom(3) == 0) {
      sb.append("-");
      switch (DiceGenerator.getRandom(22)) {
        default:
        case 0: sb.append("Szz"); break;
        case 1: sb.append("Stzz"); break;
        case 2: sb.append("Btzz"); break;
        case 3: sb.append("Bzz"); break;
        case 4: sb.append("Tzz"); break;
        case 5: sb.append("Ttzz"); break;
        case 6: sb.append("Btss"); break;
        case 7: sb.append("Bss"); break;
        case 8: sb.append("Tss"); break;
        case 9: sb.append("Ttss"); break;
        case 10: sb.append("Sss"); break;
        case 11: sb.append("Buzz"); break;
        case 12: sb.append("Bezz"); break;
        case 13: sb.append("Bess"); break;
        case 14: sb.append("Btez"); break;
        case 15: sb.append("Btes"); break;
        case 16: sb.append("Pzz"); break;
        case 17: sb.append("Pss"); break;
        case 18: sb.append("Ptzz"); break;
        case 19: sb.append("Ptss"); break;
        case 20: sb.append("Srzz"); break;
        case 21: sb.append("Srss"); break;
        case 22: sb.append("Surz"); break;
        case 23: sb.append("Surs"); break;
      }
      if (DiceGenerator.getRandom(1) == 0) {
        switch (DiceGenerator.getRandom(12)) {
          default:
          case 0: sb.append("s"); break;
          case 1: sb.append("z"); break;
          case 2: sb.append("sur"); break;
          case 3: sb.append("zz"); break;
          case 4: sb.append("zs"); break;
          case 5: sb.append("zur"); break;
          case 6: sb.append("buzz"); break;
          case 7: sb.append("uzz"); break;
          case 8: sb.append("uss"); break;
          case 9: sb.append("utz"); break;
          case 10: sb.append("uts"); break;
          case 11: sb.append("etz"); break;
          case 12: sb.append("ets"); break;
        }
      }
    }
    sb.append(" ");
    //Surname
    switch (DiceGenerator.getRandom(19)) {
      default:
      case 0: sb.append("Butter"); break;
      case 1: sb.append("Dragon"); break;
      case 2: sb.append("Silent"); break;
      case 3: sb.append("Buzzing"); break;
      case 4: sb.append("Mead"); break;
      case 5: sb.append("Light"); break;
      case 6: sb.append("Neon"); break;
      case 7: sb.append("Blue"); break;
      case 8: sb.append("Purple"); break;
      case 9: sb.append("Rainbow"); break;
      case 10: sb.append("Flower"); break;
      case 11: sb.append("Bumble"); break;
      case 12: sb.append("Wyvern"); break;
      case 13: sb.append("Hydra"); break;
      case 14: sb.append("Glowing"); break;
      case 15: sb.append("Vivid"); break;
      case 16: sb.append("Glitzy"); break;
      case 17: sb.append("Blazing"); break;
      case 18: sb.append("Radiant"); break;
      case 19: sb.append("Honey"); break;
    }
    switch (DiceGenerator.getRandom(19)) {
      default:
      case 0: sb.append("fly"); break;
      case 1: sb.append("bug"); break;
      case 2: sb.append("flight"); break;
      case 3: sb.append("bee"); break;
      case 4: sb.append("moth"); break;
      case 5: sb.append("hornet"); break;
      case 6: sb.append("flower"); break;
      case 7: sb.append("plant"); break;
      case 8: sb.append("bud"); break;
      case 9: sb.append("head"); break;
      case 10: sb.append("cluster"); break;
      case 11: sb.append("spike"); break;
      case 12: sb.append("herb"); break;
      case 13: sb.append("sprout"); break;
      case 14: sb.append("thorn"); break;
      case 15: sb.append("rose"); break;
      case 16: sb.append("mantis"); break;
      case 17: sb.append("locust"); break;
      case 18: sb.append("cicada"); break;
      case 19: sb.append("hopper"); break;
    }
    return sb.toString();
  }

  /**
   * Is last character in string vowel or not
   * @param str String to check
   * @return True if last character is vowel.
   */
  private static boolean isLastVowel(final String str) {
    char ch = str.toLowerCase().charAt(str.length() - 1);
    if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
        || ch == 'y') {
      return true;
    }
    return false;
  }

  /**
   * Method to generate Scaurian Male name.
   * Scaurian surname is same as male name.
   * @param longerChance Bigger number less chance to have third syllable
   * @return Scaurian male name.
   */
  private static String generateScaurianMaleName(final int longerChance) {
    StringBuilder sb = new StringBuilder();
    switch (DiceGenerator.getRandom(33)) {
      default:
      case 0: sb.append("Au"); break;
      case 1: sb.append("No"); break;
      case 2: sb.append("Dru"); break;
      case 3: sb.append("A"); break;
      case 4: sb.append("Mar"); break;
      case 5: sb.append("Ca"); break;
      case 6: sb.append("Pro"); break;
      case 7: sb.append("Max"); break;
      case 8: sb.append("Ti"); break;
      case 9: sb.append("Quin"); break;
      case 10: sb.append("Pla"); break;
      case 11: sb.append("Sep"); break;
      case 12: sb.append("U"); break;
      case 13: sb.append("Nu"); break;
      case 14: sb.append("Po"); break;
      case 15: sb.append("Op"); break;
      case 16: sb.append("Hos"); break;
      case 17: sb.append("Ap"); break;
      case 18: sb.append("Ga"); break;
      case 19: sb.append("Pu"); break;
      case 20: sb.append("De"); break;
      case 21: sb.append("Spu"); break;
      case 22: sb.append("Gna"); break;
      case 23: sb.append("Fla"); break;
      case 24: sb.append("He"); break;
      case 25: sb.append("Ser"); break;
      case 26: sb.append("Sec"); break;
      case 27: sb.append("Ka"); break;
      case 28: sb.append("Vo"); break;
      case 29: sb.append("Tul"); break;
      case 30: sb.append("Pau"); break;
      case 31: sb.append("Cae"); break;
      case 32: sb.append("Ni"); break;
      case 33: sb.append("Flo"); break;
    }
    if (DiceGenerator.getRandom(longerChance) == 0) {
      if (isLastVowel(sb.toString())) {
        switch (DiceGenerator.getRandom(19)) {
          default:
          case 0: sb.append("mu"); break;
          case 1: sb.append("n"); break;
          case 2: sb.append("me"); break;
          case 3: sb.append("ti"); break;
          case 4: sb.append("ci"); break;
          case 5: sb.append("pis"); break;
          case 6: sb.append("cel"); break;
          case 7: sb.append("le"); break;
          case 8: sb.append("tu"); break;
          case 9: sb.append("cun"); break;
          case 10: sb.append("ta"); break;
          case 11: sb.append("gus"); break;
          case 12: sb.append("pi"); break;
          case 13: sb.append("mer"); break;
          case 14: sb.append("lia"); break;
          case 15: sb.append("s"); break;
          case 16: sb.append("li"); break;
          case 17: sb.append("re"); break;
          case 18: sb.append("gi"); break;
          case 19: sb.append("tri"); break;
        }
      } else {
        switch (DiceGenerator.getRandom(10)) {
          default:
          case 0: sb.append("i"); break;
          case 1: sb.append("e"); break;
          case 2: sb.append("a"); break;
          case 3: sb.append("ti"); break;
          case 4: sb.append("ci"); break;
          case 5: sb.append("le"); break;
          case 6: sb.append("tu"); break;
          case 7: sb.append("ta"); break;
          case 8: sb.append("pi"); break;
          case 9: sb.append("li"); break;
          case 10: sb.append("to"); break;
          case 11: sb.append("vi"); break;
        }
      }
    }
    switch (DiceGenerator.getRandom(13)) {
      default:
      case 0: sb.append("sus"); break;
      case 1: sb.append("vius"); break;
      case 2: sb.append("tis"); break;
      case 3: sb.append("nus"); break;
      case 4: {
        if (sb.toString().endsWith("u")) {
          sb.append("lius");
        } else {
          sb.append("us");
        }
        break;
      }
      case 5: sb.append("cus"); break;
      case 6: sb.append("lus"); break;
      case 7: sb.append("tus"); break;
      case 8: sb.append("rius"); break;
      case 9: sb.append("rus"); break;
      case 10: sb.append("tius"); break;
      case 11: sb.append("vius"); break;
      case 12: sb.append("lius"); break;
      case 13: sb.append("cius"); break;
    }
    return sb.toString();
  }

  /**
   * Generate scaurian leader name
   * @param gender Leader gender
   * @return Scaurian leader name
   */
  private static String generateScaurianName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    if (gender == Gender.MALE) {
      sb.append(generateScaurianMaleName(3));
    }
    if (gender == Gender.FEMALE) {
      switch (DiceGenerator.getRandom(36)) {
        default:
        case 0: sb.append("Coc"); break;
        case 1: sb.append("Ne"); break;
        case 2: sb.append("Sta"); break;
        case 3: sb.append("Fun"); break;
        case 4: sb.append("Ar"); break;
        case 5: sb.append("Lo"); break;
        case 6: sb.append("Quinc"); break;
        case 7: sb.append("Au"); break;
        case 8: sb.append("Be"); break;
        case 9: sb.append("Cor"); break;
        case 10: sb.append("Al"); break;
        case 11: sb.append("At"); break;
        case 12: sb.append("Mi"); break;
        case 13: sb.append("Mur"); break;
        case 14: sb.append("Me"); break;
        case 15: sb.append("Ac"); break;
        case 16: sb.append("Max"); break;
        case 17: sb.append("Mar"); break;
        case 18: sb.append("Ni"); break;
        case 19: sb.append("Bur"); break;
        case 20: sb.append("Ser"); break;
        case 21: sb.append("Bal"); break;
        case 22: sb.append("Cis"); break;
        case 23: sb.append("Fla"); break;
        case 24: sb.append("Pu"); break;
        case 25: sb.append("Lae"); break;
        case 26: sb.append("Ta"); break;
        case 27: sb.append("Co"); break;
        case 28: sb.append("Hel"); break;
        case 29: sb.append("Ru"); break;
        case 30: sb.append("Ici"); break;
        case 31: sb.append("Val"); break;
        case 32: sb.append("Um"); break;
        case 33: sb.append("Her"); break;
        case 34: sb.append("Avi"); break;
        case 35: sb.append("Fla"); break;
        case 36: sb.append("Ti"); break;
      }
      if (DiceGenerator.getRandom(3) == 0) {
        switch (DiceGenerator.getRandom(14)) {
          default:
          case 0: sb.append("vo"); break;
          case 1: sb.append("do"); break;
          case 2: sb.append("ti"); break;
          case 3: sb.append("pi"); break;
          case 4: sb.append("mi"); break;
          case 5: sb.append("na"); break;
          case 6: sb.append("se"); break;
          case 7: sb.append("ga"); break;
          case 8: sb.append("se"); break;
          case 9: sb.append("lo"); break;
          case 10: sb.append("gi"); break;
          case 11: sb.append("pa"); break;
          case 12: sb.append("ta"); break;
          case 13: sb.append("pu"); break;
          case 14: sb.append("ro"); break;
        }
      }
      switch (DiceGenerator.getRandom(14)) {
        default:
        case 0: sb.append("tia"); break;
        case 1: sb.append("ria"); break;
        case 2: sb.append("nia"); break;
        case 3: sb.append("dia"); break;
        case 4: sb.append("leia"); break;
        case 5: sb.append("mia"); break;
        case 6: {
          if (isLastVowel(sb.toString())) {
            sb.append("lla");
          } else {
            sb.append("ia");
          }
          break;
        }
        case 7: sb.append("pia"); break;
        case 8: sb.append("lia"); break;
        case 9: sb.append("sia"); break;
        case 10: sb.append("cia"); break;
        case 11: sb.append("na"); break;
        case 12: sb.append("ta"); break;
        case 13: sb.append("lis"); break;
        case 14: sb.append("via"); break;
      }
    }
    sb.append(" ");
    //Surname
    sb.append(generateScaurianMaleName(1));
    return sb.toString();
  }

  /**
   * Generate centaur male name
   * @return Centaur male name
   */
  private static String generateCentaurMaleName() {
    StringBuilder sb = new StringBuilder();
    switch (DiceGenerator.getRandom(25)) {
      default:
      case 0: sb.append("Po"); break;
      case 1: sb.append("Eu"); break;
      case 2: sb.append("Te"); break;
      case 3: sb.append("Kas"); break;
      case 4: sb.append("Noe"); break;
      case 5: sb.append("Phi"); break;
      case 6: sb.append("Pla"); break;
      case 7: sb.append("Ly"); break;
      case 8: sb.append("Cha"); break;
      case 9: sb.append("Co"); break;
      case 10: sb.append("Kri"); break;
      case 11: sb.append("Phe"); break;
      case 12: sb.append("Ana"); break;
      case 13: sb.append("Pe"); break;
      case 14: sb.append("Aris"); break;
      case 15: sb.append("Ni"); break;
      case 16: sb.append("Ce"); break;
      case 17: sb.append("Sop"); break;
      case 18: sb.append("Eu"); break;
      case 19: sb.append("Phy"); break;
      case 20: sb.append("He"); break;
      case 21: sb.append("Mae"); break;
      case 22: sb.append("Hip"); break;
      case 23: sb.append("Dio"); break;
      case 24: sb.append("Pan"); break;
      case 25: sb.append("Ther"); break;
    }
    switch (DiceGenerator.getRandom(21)) {
      default:
      case 0: sb.append("kra"); break;
      case 1: sb.append("san"); break;
      case 2: sb.append("me"); break;
      case 3: sb.append("doc"); break;
      case 4: sb.append("te"); break;
      case 5: sb.append("dae"); break;
      case 6: sb.append("re"); break;
      case 7: sb.append("la"); break;
      case 8: sb.append("pe"); break;
      case 9: sb.append("le"); break;
      case 10: sb.append("ti"); break;
      case 11: sb.append("da"); break;
      case 12: sb.append("me"); break;
      case 13: sb.append("tu"); break;
      case 14: sb.append("xe"); break;
      case 15: sb.append("co"); break;
      case 16: sb.append("to"); break;
      case 17: sb.append("lo"); break;
      case 18: sb.append("po"); break;
      case 19: sb.append("ny"); break;
      case 20: sb.append("ta"); break;
      case 21: sb.append("hili"); break;
    }
    switch (DiceGenerator.getRandom(17)) {
      default:
      case 0: sb.append("mon"); break;
      case 1: sb.append("nus"); break;
      case 2: sb.append("hos"); break;
      case 3: sb.append("tos"); break;
      case 4: sb.append("hus"); break;
      case 5: sb.append("les"); break;
      case 6: sb.append("on"); break;
      case 7: sb.append("us"); break;
      case 8: sb.append("des"); break;
      case 9: sb.append("das"); break;
      case 10: sb.append("as"); break;
      case 11: sb.append("os"); break;
      case 12: sb.append("lus"); break;
      case 13: sb.append("pos"); break;
      case 14: sb.append("mus"); break;
      case 15: sb.append("maios"); break;
      case 16: sb.append("rios"); break;
      case 17: sb.append("gus"); break;
    }
    return sb.toString();
  }
  /**
   * Generate centaur leader name
   * @param gender Leader gender
   * @return centaur leader name
   */
  private static String generateCentaurName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    if (gender == Gender.MALE) {
      sb.append(generateCentaurMaleName());
    }
    if (gender == Gender.FEMALE) {
      switch (DiceGenerator.getRandom(25)) {
        default:
        case 0: sb.append("Per"); break;
        case 1: sb.append("Se"); break;
        case 2: sb.append("Leu"); break;
        case 3: sb.append("Alt"); break;
        case 4: sb.append("Aga"); break;
        case 5: sb.append("Ne"); break;
        case 6: sb.append("Ia"); break;
        case 7: sb.append("Try"); break;
        case 8: sb.append("Kleo"); break;
        case 9: sb.append("Om"); break;
        case 10: sb.append("Al"); break;
        case 11: sb.append("Dia"); break;
        case 12: sb.append("Aga"); break;
        case 13: sb.append("My"); break;
        case 14: sb.append("Phi"); break;
        case 15: sb.append("Her"); break;
        case 16: sb.append("Lao"); break;
        case 17: sb.append("Ia"); break;
        case 18: sb.append("He"); break;
        case 19: sb.append("Io"); break;
        case 20: sb.append("De"); break;
        case 21: sb.append("Ampi"); break;
        case 22: sb.append("Mol"); break;
        case 23: sb.append("Pro"); break;
        case 24: sb.append("Me"); break;
        case 25: sb.append("Euph"); break;
      }
      switch (DiceGenerator.getRandom(21)) {
        default:
        case 0: sb.append("vi"); break;
        case 1: sb.append("me"); break;
        case 2: sb.append("cot"); break;
        case 3: sb.append("re"); break;
        case 4: sb.append("ni"); break;
        case 5: sb.append("nei"); break;
        case 6: sb.append("lan"); break;
        case 7: sb.append("phy"); break;
        case 8: sb.append("ro"); break;
        case 9: sb.append("da"); break;
        case 10: sb.append("vi"); break;
        case 11: sb.append("he"); break;
        case 12: sb.append("da"); break;
        case 13: sb.append("pat"); break;
        case 14: sb.append("lec"); break;
        case 15: sb.append("pho"); break;
        case 16: sb.append("ces"); break;
        case 17: sb.append("li"); break;
        case 18: sb.append("me"); break;
        case 19: sb.append("ni"); break;
        case 20: sb.append("a"); break;
        case 21: sb.append("tho"); break;
      }
      switch (DiceGenerator.getRandom(17)) {
        default:
        case 0: sb.append("seis"); break;
        case 1: sb.append("ce"); break;
        case 2: sb.append("nessa"); break;
        case 3: sb.append("nice"); break;
        case 4: sb.append("pe"); break;
        case 5: sb.append("ne"); break;
        case 6: sb.append("na"); break;
        case 7: sb.append("sia"); break;
        case 8: sb.append("meia"); break;
        case 9: sb.append("dria"); break;
        case 10: sb.append("dora"); break;
        case 11: sb.append("leia"); break;
        case 12: sb.append("tra"); break;
        case 13: sb.append("pia"); break;
        case 14: sb.append("nia"); break;
        case 15: sb.append("thia"); break;
        case 16: sb.append("hae"); break;
        case 17: sb.append("ra"); break;
      }
    }
    sb.append(" ");
    //Surname
    if (gender == Gender.FEMALE) {
      sb.append("Kori-");
    } else {
      sb.append("Yios-");
    }
    sb.append(generateCentaurMaleName());
    return sb.toString();
  }

  /**
   * Generate homarian leader name
   * @param gender Leader gender
   * @return Homarian leader name
   */
  private static String generateHomarianName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    if (gender == Gender.MALE) {
      switch (DiceGenerator.getRandom(60)) {
        default:
        case 0: sb.append("Raz"); break;
        case 1: sb.append("Coh"); break;
        case 2: sb.append("Keh"); break;
        case 3: sb.append("Min"); break;
        case 4: sb.append("Dhar"); break;
        case 5: sb.append("Ko"); break;
        case 6: sb.append("Zum"); break;
        case 7: sb.append("Zel"); break;
        case 8: sb.append("Num"); break;
        case 9: sb.append("Rim"); break;
        case 10: sb.append("Kha"); break;
        case 11: sb.append("Zuh"); break;
        case 12: sb.append("Ke"); break;
        case 13: sb.append("Khi"); break;
        case 14: sb.append("Dih"); break;
        case 15: sb.append("Car"); break;
        case 16: sb.append("Do"); break;
        case 17: sb.append("Zi"); break;
        case 18: sb.append("Khul"); break;
        case 19: sb.append("Dhim"); break;
        case 20: sb.append("Dhan"); break;
        case 21: sb.append("Mag"); break;
        case 22: sb.append("Man"); break;
        case 23: sb.append("De"); break;
        case 24: sb.append("Mo"); break;
        case 25: sb.append("Jo"); break;
        case 26: sb.append("Jed"); break;
        case 27: sb.append("Dem"); break;
        case 28: sb.append("Nun"); break;
        case 29: sb.append("Ril"); break;
        case 30: sb.append("Mu"); break;
        case 31: sb.append("Dhum"); break;
        case 32: sb.append("Zeg"); break;
        case 33: sb.append("Jhor"); break;
        case 34: sb.append("Zel"); break;
        case 35: sb.append("Riz"); break;
        case 36: sb.append("Ki"); break;
        case 37: sb.append("Ve"); break;
        case 38: sb.append("Va"); break;
        case 39: sb.append("Jar"); break;
        case 40: sb.append("Dah"); break;
        case 41: sb.append("Jom"); break;
        case 42: sb.append("Rud"); break;
        case 43: sb.append("Nel"); break;
        case 44: sb.append("Cun"); break;
        case 45: sb.append("Khir"); break;
        case 46: sb.append("Dor"); break;
        case 47: sb.append("Moh"); break;
        case 48: sb.append("Du"); break;
        case 49: sb.append("Cul"); break;
        case 50: sb.append("Mil"); break;
        case 51: sb.append("Jim"); break;
        case 52: sb.append("Vun"); break;
        case 53: sb.append("Ni"); break;
        case 54: sb.append("Khog"); break;
        case 55: sb.append("Jham"); break;
        case 56: sb.append("Tul"); break;
        case 57: sb.append("Tha"); break;
        case 58: sb.append("Kha"); break;
        case 59: sb.append("Bal"); break;
        case 60: sb.append("Bha"); break;
      }
      switch (DiceGenerator.getRandom(16)) {
        default:
        case 0: sb.append("das"); break;
        case 1: sb.append("gas"); break;
        case 2: sb.append("nis"); break;
        case 3: sb.append("las"); break;
        case 4: sb.append("ris"); break;
        case 5: sb.append("mus"); break;
        case 6: sb.append("nus"); break;
        case 7: sb.append("zos"); break;
        case 8: sb.append("zes"); break;
        case 9: sb.append("vos"); break;
        case 10: sb.append("vus"); break;
        case 11: sb.append("nos"); break;
        case 12: sb.append("dos"); break;
        case 13: sb.append("gus"); break;
        case 14: sb.append("mos"); break;
        case 15: sb.append("los"); break;
        case 16: sb.append("lus"); break;
      }
    }
    if (gender == Gender.FEMALE) {
      switch (DiceGenerator.getRandom(30)) {
        default:
        case 0: sb.append("Ath"); break;
        case 1: sb.append("Il"); break;
        case 2: sb.append("When"); break;
        case 3: sb.append("Us"); break;
        case 4: sb.append("Shan"); break;
        case 5: sb.append("Sid"); break;
        case 6: sb.append("Buh"); break;
        case 7: sb.append("Ar"); break;
        case 8: sb.append("Dhen"); break;
        case 9: sb.append("Do"); break;
        case 10: sb.append("Vlis"); break;
        case 11: sb.append("Ol"); break;
        case 12: sb.append("El"); break;
        case 13: sb.append("Fel"); break;
        case 14: sb.append("Eh"); break;
        case 15: sb.append("Om"); break;
        case 16: sb.append("Dom"); break;
        case 17: sb.append("Bhe"); break;
        case 18: sb.append("Yom"); break;
        case 19: sb.append("E"); break;
        case 20: sb.append("Sil"); break;
        case 21: sb.append("Fin"); break;
        case 22: sb.append("Fas"); break;
        case 23: sb.append("Her"); break;
        case 24: sb.append("Hel"); break;
        case 25: sb.append("Mar"); break;
        case 26: sb.append("Wor"); break;
        case 27: sb.append("Es"); break;
        case 28: sb.append("Sig"); break;
        case 29: sb.append("Mim"); break;
        case 30: sb.append("Nin"); break;
      }
      if (DiceGenerator.getRandom(3) == 0) {
        switch (DiceGenerator.getRandom(16)) {
          default:
          case 0: sb.append("ra"); break;
          case 1: sb.append("ne"); break;
          case 2: sb.append("di"); break;
          case 3: sb.append("ri"); break;
          case 4: sb.append("lo"); break;
          case 5: sb.append("ro"); break;
          case 6: sb.append("sha"); break;
          case 7: sb.append("to"); break;
          case 8: sb.append("na"); break;
          case 9: sb.append("la"); break;
          case 10: sb.append("no"); break;
          case 11: sb.append("te"); break;
          case 12: sb.append("lu"); break;
          case 13: sb.append("hi"); break;
          case 14: sb.append("ti"); break;
          case 15: sb.append("hu"); break;
          case 16: sb.append("tu"); break;
        }
      }
      switch (DiceGenerator.getRandom(6)) {
        default:
        case 0: sb.append("ryn"); break;
        case 1: sb.append("dyn"); break;
        case 2: sb.append("lyn"); break;
        case 3: sb.append("myn"); break;
        case 4: sb.append("syn"); break;
        case 5: sb.append("nyn"); break;
        case 6: sb.append("tyn"); break;
      }
    }
    sb.append(" ");
    //Surname
    switch (DiceGenerator.getRandom(40)) {
      default:
      case 0: sb.append("Eh"); break;
      case 1: sb.append("Mu"); break;
      case 2: sb.append("Da"); break;
      case 3: sb.append("La"); break;
      case 4: sb.append("Joh"); break;
      case 5: sb.append("Dha"); break;
      case 6: sb.append("Va"); break;
      case 7: sb.append("Puh"); break;
      case 8: sb.append("Doh"); break;
      case 9: sb.append("Zuh"); break;
      case 10: sb.append("Lu"); break;
      case 11: sb.append("Juh"); break;
      case 12: sb.append("Le"); break;
      case 13: sb.append("Sa"); break;
      case 14: sb.append("Bha"); break;
      case 15: sb.append("Ne"); break;
      case 16: sb.append("Nu"); break;
      case 17: sb.append("Sa"); break;
      case 18: sb.append("Su"); break;
      case 19: sb.append("Ma"); break;
      case 20: sb.append("Mo"); break;
      case 21: sb.append("Pa"); break;
      case 22: sb.append("Po"); break;
      case 23: sb.append("Zug"); break;
      case 24: sb.append("Za"); break;
      case 25: sb.append("Ze"); break;
      case 26: sb.append("Vo"); break;
      case 27: sb.append("Jeh"); break;
      case 28: sb.append("Ruh"); break;
      case 29: sb.append("Ro"); break;
      case 30: sb.append("U"); break;
      case 31: sb.append("Ah"); break;
      case 32: sb.append("Uh"); break;
      case 33: sb.append("Oh"); break;
      case 34: sb.append("Poh"); break;
      case 35: sb.append("Bu"); break;
      case 36: sb.append("Ba"); break;
      case 37: sb.append("Taylor"); break;
      case 38: sb.append("O"); break;
      case 39: sb.append("Reh"); break;
      case 40: sb.append("Luh"); break;
    }
    switch (DiceGenerator.getRandom(28)) {
      default:
      case 0: sb.append("ros"); break;
      case 1: sb.append("de"); break;
      case 2: sb.append("rur"); break;
      case 3: sb.append("no"); break;
      case 4: sb.append("la"); break;
      case 5: sb.append("gu"); break;
      case 6: sb.append("nol"); break;
      case 7: sb.append("vo"); break;
      case 8: sb.append("go"); break;
      case 9: sb.append("ba"); break;
      case 10: sb.append("rom"); break;
      case 11: sb.append("bul"); break;
      case 12: sb.append("ho"); break;
      case 13: sb.append("hu"); break;
      case 14: sb.append("ber"); break;
      case 15: sb.append("mel"); break;
      case 16: sb.append("mo"); break;
      case 17: sb.append("ma"); break;
      case 18: sb.append("do"); break;
      case 19: sb.append("nul"); break;
      case 20: sb.append("mul"); break;
      case 21: sb.append("hem"); break;
      case 22: sb.append("nan"); break;
      case 23: sb.append("non"); break;
      case 24: sb.append("nom"); break;
      case 25: sb.append("las"); break;
      case 26: sb.append("los"); break;
      case 27: sb.append("Jeh"); break;
      case 28: sb.append("hur"); break;
    }
    switch (DiceGenerator.getRandom(9)) {
      default:
      case 0: sb.append("dath"); break;
      case 1: sb.append("gath"); break;
      case 2: sb.append("sath"); break;
      case 3: sb.append("zath"); break;
      case 4: sb.append("vath"); break;
      case 5: sb.append("lath"); break;
      case 6: sb.append("rath"); break;
      case 7: sb.append("nath"); break;
      case 8: sb.append("math"); break;
      case 9: sb.append("xath"); break;
    }
    return sb.toString();
  }

  /**
   * Generate greyan leader name
   * @param gender Leader gender
   * @return Greyan leader name
   */
  private static String generateGreyanName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    if (gender == Gender.MALE) {
      switch (DiceGenerator.getRandom(40)) {
        default:
        case 0: sb.append("Gunnar"); break;
        case 1: sb.append("Sigmund"); break;
        case 2: sb.append("Gudrik"); break;
        case 3: sb.append("Swein"); break;
        case 4: sb.append("Ornulf"); break;
        case 5: sb.append("Asbjorn"); break;
        case 6: sb.append("Sigfast"); break;
        case 7: sb.append("Asvard"); break;
        case 8: sb.append("Thorvid"); break;
        case 9: sb.append("Thorkel"); break;
        case 10: sb.append("Vegeir"); break;
        case 11: sb.append("Thorolf"); break;
        case 12: sb.append("Brodir"); break;
        case 13: sb.append("Hermund"); break;
        case 14: sb.append("Odinmund"); break;
        case 15: sb.append("Moldof"); break;
        case 16: sb.append("Karl"); break;
        case 17: sb.append("Hakmund"); break;
        case 18: sb.append("Bjorn"); break;
        case 19: sb.append("Thorin"); break;
        case 20: sb.append("Starolf"); break;
        case 21: sb.append("Svindolf"); break;
        case 22: sb.append("Asgeir"); break;
        case 23: sb.append("Gunnbjorn"); break;
        case 24: sb.append("Stormir"); break;
        case 25: sb.append("Agnar"); break;
        case 26: sb.append("Gunleif"); break;
        case 27: sb.append("Thorleif"); break;
        case 28: sb.append("Karleif"); break;
        case 29: sb.append("Fargrim"); break;
        case 30: sb.append("Knut"); break;
        case 31: sb.append("Sigevarld"); break;
        case 32: sb.append("Oswald"); break;
        case 33: sb.append("Arngeir"); break;
        case 34: sb.append("Thorberg"); break;
        case 35: sb.append("Sigberg"); break;
        case 36: sb.append("Steinberg"); break;
        case 37: sb.append("Bothvar"); break;
        case 38: sb.append("Eyjolf"); break;
        case 39: sb.append("Asbrand"); break;
        case 40: sb.append("Mord"); break;
      }
    }
    if (gender == Gender.FEMALE) {
      switch (DiceGenerator.getRandom(31)) {
        default:
        case 0: sb.append("Ingrid"); break;
        case 1: sb.append("Astrid"); break;
        case 2: sb.append("Gunnhild"); break;
        case 3: sb.append("Jofrid"); break;
        case 4: sb.append("Thorhild"); break;
        case 5: sb.append("Thurid"); break;
        case 6: sb.append("Solveig"); break;
        case 7: sb.append("Inguld"); break;
        case 8: sb.append("Brynhild"); break;
        case 9: sb.append("Frida"); break;
        case 10: sb.append("Ashild"); break;
        case 11: sb.append("Ragneid"); break;
        case 12: sb.append("Asa"); break;
        case 13: sb.append("Matilda"); break;
        case 14: sb.append("Ulfheid"); break;
        case 15: sb.append("Gyda"); break;
        case 16: sb.append("Katla"); break;
        case 17: sb.append("Ingunn"); break;
        case 18: sb.append("Gudrunn"); break;
        case 19: sb.append("Freya"); break;
        case 20: sb.append("Hilde"); break;
        case 21: sb.append("Isgerd"); break;
        case 22: sb.append("Yngvold"); break;
        case 23: sb.append("Gyda"); break;
        case 24: sb.append("Hildr"); break;
        case 25: sb.append("Birka"); break;
        case 26: sb.append("Skuld"); break;
        case 27: sb.append("Geirny"); break;
        case 28: sb.append("Hild"); break;
        case 29: sb.append("Inghild"); break;
        case 30: sb.append("Audhild"); break;
        case 31: sb.append("Dotta"); break;
      }
    }
    sb.append(" ");
    //Surname
    switch (DiceGenerator.getRandom(17)) {
      default:
      case 0: sb.append("Thunder"); break;
      case 1: sb.append("Steel"); break;
      case 2: sb.append("Bright"); break;
      case 3: sb.append("Mighty"); break;
      case 4: sb.append("Iron"); break;
      case 5: sb.append("Titanium"); break;
      case 6: sb.append("Icy"); break;
      case 7: sb.append("Dusty"); break;
      case 8: sb.append("White"); break;
      case 9: sb.append("Blue"); break;
      case 10: sb.append("Crystal"); break;
      case 11: sb.append("Rainbow"); break;
      case 12: sb.append("Windy"); break;
      case 13: sb.append("Frenzy"); break;
      case 14: sb.append("Magical"); break;
      case 15: sb.append("Aurora"); break;
      case 16: sb.append("Wild"); break;
      case 17: sb.append("Old"); break;
    }
    switch (DiceGenerator.getRandom(20)) {
      default:
      case 0: sb.append("hammer"); break;
      case 1: sb.append("spear"); break;
      case 2: sb.append("sky"); break;
      case 3: sb.append("star"); break;
      case 4: sb.append("fist"); break;
      case 5: sb.append("chariot"); break;
      case 6: sb.append("wolf"); break;
      case 7: sb.append("rune"); break;
      case 8: sb.append("sword"); break;
      case 9: sb.append("glory"); break;
      case 10: sb.append("horn"); break;
      case 11: sb.append("spell"); break;
      case 12: sb.append("root"); break;
      case 13: sb.append("riddle"); break;
      case 14: sb.append("rock"); break;
      case 15: sb.append("gate"); break;
      case 16: sb.append("bridge"); break;
      case 17: sb.append("cloud"); break;
      case 18: sb.append("ship"); break;
      case 19: sb.append("vessel"); break;
      case 20: sb.append("peak"); break;
    }
    return sb.toString();
  }

  /**
   * Generate mechion leader name
   * @return Mechion leader name
   */
  private static String generateMechionName() {
    StringBuilder sb = new StringBuilder();
    switch (DiceGenerator.getRandom(14)) {
      default:
      case 0: sb.append("Model"); break;
      case 1: sb.append("Drone"); break;
      case 2: sb.append("Bot"); break;
      case 3: sb.append("Droid"); break;
      case 4: sb.append("Golem"); break;
      case 5: sb.append("Android"); break;
      case 6: sb.append("Entity"); break;
      case 7: sb.append("Mechan"); break;
      case 8: sb.append("Automaton"); break;
      case 9: sb.append("Slug"); break;
      case 10: sb.append("Atom"); break;
      case 11: sb.append("Robo"); break;
      case 12: sb.append("Roboid"); break;
      case 13: sb.append("Tronic"); break;
      case 14: {
        sb.append("R");
        sb.append(DiceGenerator.getRandom(1, 9));
        break;
      }
    }
    if (DiceGenerator.getRandom(1) == 0) {
      sb.append("-");
      switch (DiceGenerator.getRandom(9)) {
        default:
        case 0: sb.append("X-"); break;
        case 1: sb.append("Y-"); break;
        case 2: sb.append("Z-"); break;
        case 3: sb.append("Q-"); break;
        case 4: sb.append("A-"); break;
        case 5: sb.append("B-"); break;
        case 6: sb.append("E-"); break;
        case 7: sb.append("G-"); break;
        case 8: sb.append("W-"); break;
        case 9: sb.append("K-"); break;
      }
      switch (DiceGenerator.getRandom(4)) {
        default:
        case 0: sb.append(DiceGenerator.getRandom(1, 10)); break;
        case 1: sb.append(DiceGenerator.getRandom(20, 100)); break;
        case 2: sb.append(DiceGenerator.getRandom(2, 9) * 100); break;
        case 3: sb.append(DiceGenerator.getRandom(1, 9) * 1000); break;
        case 4: sb.append(DiceGenerator.getRandom(1, 9) * 10000); break;
      }
    } else {
      sb.append(" ");
      switch (DiceGenerator.getRandom(4)) {
        default:
        case 0: sb.append(DiceGenerator.getRandom(1, 10)); break;
        case 1: sb.append(DiceGenerator.getRandom(20, 100)); break;
        case 2: sb.append(DiceGenerator.getRandom(2, 9) * 100); break;
        case 3: sb.append(DiceGenerator.getRandom(1, 9) * 1000); break;
        case 4: sb.append(DiceGenerator.getRandom(1, 9) * 10000); break;
      }
      switch (DiceGenerator.getRandom(9)) {
        default:
        case 0: sb.append("X"); break;
        case 1: sb.append("Y"); break;
        case 2: sb.append("Z"); break;
        case 3: sb.append("Q"); break;
        case 4: sb.append("K"); break;
        case 5: sb.append("M"); break;
        case 6: sb.append("E"); break;
        case 7: sb.append("G"); break;
        case 8: sb.append("W"); break;
        case 9: sb.append("Y"); break;
      }
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
   * Generate reborgian leader name
   * @return Reborgian leader name
   */
  private static String generateReborgianName() {
    StringBuilder sb = new StringBuilder();
    switch (DiceGenerator.getRandom(14)) {
      default:
      case 0: sb.append("Bio"); break;
      case 1: sb.append("Drone"); break;
      case 2: sb.append("Cyborg"); break;
      case 3: sb.append("Android"); break;
      case 4: sb.append("Golem"); break;
      case 5: sb.append("Replicate"); break;
      case 6: sb.append("DNA"); break;
      case 7: sb.append("RNA"); break;
      case 8: sb.append("Cytosine"); break;
      case 9: sb.append("Monitor"); break;
      case 10: sb.append("Organ"); break;
      case 11: sb.append("Guanine"); break;
      case 12: sb.append("Adenie"); break;
      case 13: sb.append("Thymine"); break;
      case 14: {
        sb.append("B");
        sb.append(DiceGenerator.getRandom(1, 9));
        break;
      }
    }
    if (DiceGenerator.getRandom(1) == 0) {
      sb.append("-");
      switch (DiceGenerator.getRandom(11)) {
        default:
        case 0: sb.append("X-"); break;
        case 1: sb.append("Y-"); break;
        case 2: sb.append("Z-"); break;
        case 3: sb.append("Q-"); break;
        case 4: sb.append("A-"); break;
        case 5: sb.append("B-"); break;
        case 6: sb.append("E-"); break;
        case 7: sb.append("G-"); break;
        case 8: sb.append("W-"); break;
        case 9: sb.append("C-"); break;
        case 10: sb.append("T-"); break;
        case 11: sb.append("T-"); break;
      }
      switch (DiceGenerator.getRandom(4)) {
        default:
        case 0: sb.append(DiceGenerator.getRandom(1, 10)); break;
        case 1: sb.append(DiceGenerator.getRandom(20, 100)); break;
        case 2: sb.append(DiceGenerator.getRandom(2, 9) * 100); break;
        case 3: sb.append(DiceGenerator.getRandom(1, 9) * 1000); break;
        case 4: sb.append(DiceGenerator.getRandom(1, 9) * 10000); break;
      }
    } else {
      sb.append(" ");
      switch (DiceGenerator.getRandom(4)) {
        default:
        case 0: sb.append(DiceGenerator.getRandom(1, 10)); break;
        case 1: sb.append(DiceGenerator.getRandom(20, 100)); break;
        case 2: sb.append(DiceGenerator.getRandom(2, 9) * 100); break;
        case 3: sb.append(DiceGenerator.getRandom(1, 9) * 1000); break;
        case 4: sb.append(DiceGenerator.getRandom(1, 9) * 10000); break;
      }
      switch (DiceGenerator.getRandom(10)) {
        default:
        case 0: sb.append("X"); break;
        case 1: sb.append("Y"); break;
        case 2: sb.append("Z"); break;
        case 3: sb.append("Q"); break;
        case 4: sb.append("C"); break;
        case 5: sb.append("T"); break;
        case 6: sb.append("E"); break;
        case 7: sb.append("G"); break;
        case 8: sb.append("W"); break;
        case 9: sb.append("A"); break;
        case 10: break;
      }
    }
    return sb.toString();
  }

  /**
   * Generate Lithorian leader name
   * @param gender Leader gender
   * @return Lithorian leader name
   */
  private static String generateLithorianName(final Gender gender) {
    StringBuilder sb = new StringBuilder();
    if (gender == Gender.MALE) {
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("Smul"); break;
        case 1: sb.append("Bul"); break;
        case 2: sb.append("Umb"); break;
        case 3: sb.append("Rag"); break;
        case 4: sb.append("Fer"); break;
        case 5: sb.append("Thorm"); break;
        case 6: sb.append("Mol"); break;
        case 7: sb.append("Mug"); break;
        case 8: sb.append("A"); break;
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
          case 0: sb.append("da"); break;
          case 1: sb.append("ris"); break;
          case 2: sb.append("zu"); break;
          case 3: sb.append("or"); break;
          case 4: sb.append("ar"); break;
          case 5: sb.append("sh"); break;
          case 6: sb.append("ol"); break;
          case 7: sb.append("ug"); break;
          case 8: sb.append("ro"); break;
          case 9: sb.append("za"); break;
          case 10: sb.append("ze"); break;
          case 11: sb.append("ur"); break;
          case 12: sb.append("go"); break;
          case 13: sb.append("ak"); break;
          case 14: sb.append("ha"); break;
          case 15: sb.append("er"); break;
          case 16: sb.append("ir"); break;
          case 17: sb.append("er"); break;
          case 18: sb.append("nu"); break;
          case 19: sb.append("ny"); break;
        }
      }
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("der"); break;
        case 1: sb.append("ris"); break;
        case 2: sb.append("nis"); break;
        case 3: sb.append("no"); break;
        case 4: sb.append("bus"); break;
        case 5: sb.append("tos"); break;
        case 6: sb.append("rus"); break;
        case 7: sb.append("nus"); break;
        case 8: sb.append("sius"); break;
        case 9: sb.append("nius"); break;
        case 10: sb.append("gis"); break;
        case 11: sb.append("lis"); break;
        case 12: sb.append("nic"); break;
        case 13: sb.append("on"); break;
        case 14: sb.append("rub"); break;
        case 15: sb.append("tog"); break;
        case 16: sb.append("zer"); break;
        case 17: sb.append("mus"); break;
        case 18: sb.append("lon"); break;
        case 19: sb.append("lok"); break;
      }
    }
    if (gender == Gender.FEMALE) {
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("Ae"); break;
        case 1: sb.append("So"); break;
        case 2: sb.append("A"); break;
        case 3: sb.append("My"); break;
        case 4: sb.append("Bu"); break;
        case 5: sb.append("Li"); break;
        case 6: sb.append("Y"); break;
        case 7: sb.append("Mu"); break;
        case 8: sb.append("Ne"); break;
        case 9: sb.append("Mo"); break;
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
          case 0: sb.append("re"); break;
          case 1: sb.append("mu"); break;
          case 2: sb.append("go"); break;
          case 3: sb.append("ro"); break;
          case 4: sb.append("ra"); break;
          case 5: sb.append("shi"); break;
          case 6: sb.append("lo"); break;
          case 7: sb.append("gu"); break;
          case 8: sb.append("du"); break;
          case 9: sb.append("ga"); break;
          case 10: sb.append("ma"); break;
          case 11: sb.append("le"); break;
          case 12: sb.append("di"); break;
          case 13: sb.append("ka"); break;
          case 14: sb.append("ha"); break;
          case 15: sb.append("re"); break;
          case 16: sb.append("te"); break;
          case 17: sb.append("ti"); break;
          case 18: sb.append("me"); break;
          case 19: sb.append("fe"); break;
        }
      }
      switch (DiceGenerator.getRandom(19)) {
        default:
        case 0: sb.append("re"); break;
        case 1: sb.append("nus"); break;
        case 2: sb.append("fa"); break;
        case 3: sb.append("rios"); break;
        case 4: sb.append("quis"); break;
        case 5: sb.append("ce"); break;
        case 6: sb.append("xus"); break;
        case 7: sb.append("gen"); break;
        case 8: sb.append("toa"); break;
        case 9: sb.append("son"); break;
        case 10: sb.append("gon"); break;
        case 11: sb.append("zius"); break;
        case 12: sb.append("han"); break;
        case 13: sb.append("bon"); break;
        case 14: sb.append("ra"); break;
        case 15: sb.append("he"); break;
        case 16: sb.append("za"); break;
        case 17: sb.append("mah"); break;
        case 18: sb.append("log"); break;
        case 19: sb.append("ti"); break;
      }
    }
    sb.append(" ");
    switch (DiceGenerator.getRandom(19)) {
      default:
      case 0: sb.append("Blaze"); break;
      case 1: sb.append("Flare"); break;
      case 2: sb.append("Tinder"); break;
      case 3: sb.append("Glow"); break;
      case 4: sb.append("Pyre"); break;
      case 5: sb.append("Lava"); break;
      case 6: sb.append("Magma"); break;
      case 7: sb.append("Fire"); break;
      case 8: sb.append("Flame"); break;
      case 9: sb.append("Steel"); break;
      case 10: sb.append("Metal"); break;
      case 11: sb.append("Glass"); break;
      case 12: sb.append("Basalt"); break;
      case 13: sb.append("Titanium"); break;
      case 14: sb.append("Obsidian"); break;
      case 15: sb.append("Heated"); break;
      case 16: sb.append("Golden"); break;
      case 17: sb.append("Boiling"); break;
      case 18: sb.append("Fiery"); break;
      case 19: sb.append("Red"); break;
    }
    switch (DiceGenerator.getRandom(19)) {
      default:
      case 0: sb.append("stone"); break;
      case 1: sb.append("crystal"); break;
      case 2: sb.append("gem"); break;
      case 3: sb.append("gravel"); break;
      case 4: sb.append("mineral"); break;
      case 5: sb.append("metal"); break;
      case 6: sb.append("ore"); break;
      case 7: sb.append("boulder"); break;
      case 8: sb.append("rock"); break;
      case 9: sb.append("mass"); break;
      case 10: sb.append("crust"); break;
      case 11: sb.append("mountain"); break;
      case 12: sb.append("volcano"); break;
      case 13: sb.append("peak"); break;
      case 14: sb.append("ignot"); break;
      case 15: sb.append("plate"); break;
      case 16: sb.append("alloy"); break;
      case 17: sb.append("vein"); break;
      case 18: sb.append("spirit"); break;
      case 19: sb.append("flux"); break;
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
    if (race == SpaceRace.TEUTHIDAES) {
      return generateTeuthidaeName(gender);
    }
    if (race == SpaceRace.CHIRALOIDS) {
      return generateChiraloidName(gender);
    }
    if (race == SpaceRace.MOTHOIDS) {
      return generateMothoidName(gender);
    }
    if (race == SpaceRace.SCAURIANS) {
      return generateScaurianName(gender);
    }
    if (race == SpaceRace.HOMARIANS) {
      return generateHomarianName(gender);
    }
    if (race == SpaceRace.GREYANS) {
      return generateGreyanName(gender);
    }
    if (race == SpaceRace.CENTAURS) {
      return generateCentaurName(gender);
    }
    if (race == SpaceRace.MECHIONS) {
      return generateMechionName();
    }
    if (race == SpaceRace.REBORGIANS) {
      return generateReborgianName();
    }
    if (race == SpaceRace.LITHORIANS) {
      return generateLithorianName(gender);
    }
    return "Noname";
  }
}
