package org.openRealmOfStars.player.diplomacy;

/**
*
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
*
*
* Diplomacy Bonus Type
*
*/
public enum DiplomacyBonusType {
  /**
   * Two players are in war.
   */
  IN_WAR,
  /**
   * Another player has made war declaration to other players.
   */
  WAR_DECLARTION,
  /**
   * Situation where non combat ships can travel on each other space.
   */
  IN_TRADE_ALLIANCE,
  /**
   * Situation where all ships can travel on each other's space.
   */
  IN_ALLIANCE,
  /**
   * Other player has captured a diplomat.
   */
  DIPLOMAT_CAPTURED,
  /**
   * Border has been crossed with any ship where there is no alliance.
   */
  BORDER_CROSSED,
  /**
   * Another player has given something free.
   */
  GIVEN_VALUABLE_FREE,
  /**
   * Another player has demanded something for free.
   */
  MADE_DEMAND,
  /**
   * Players have traded something.
   */
  DIPLOMATIC_TRADE,
  /**
   * Players share the same space race.
   */
  SAME_RACE,
  /**
   * Players have had long peace time together.
   */
  LONG_PEACE,
  /**
   * Another player has made an insult
   */
  INSULT,
  /**
   * Another player has nuked a planet
   */
  NUKED,
  /**
   * Last time player did not have nothing to trade so giving it
   * a cool down time
   */
  NOTHING_TO_TRADE,
  /**
   * Situation where all ships can travel on each other's space
   * and same pact defend each other and can trade spy information.
   */
  IN_DEFENSIVE_PACT,
  /**
   * Border has been crossed with espionage ship.
   */
  ESPIONAGE_BORDER_CROSS,
  /**
   * Espionage trade
   */
  SPY_TRADE,
  /**
   * Diplomacy bonus from race or government
   */
  DIPLOMACY_BONUS,
  /**
   * Trade fleet arrived from another realm
   */
  TRADE_FLEET,
  /**
   * Special bonus for marking board player
   */
  BOARD_PLAYER,
  /**
   * Diplomacy bonus marking for embargo between too players
   */
  EMBARGO,
  /**
   * Bonus for realm which liked the made embargo.
   * So this is positive bonus.
   */
  LIKED_EMBARGO,
  /**
   * Bonus for realm which did not like the made embargo.
   * So this is negative bonus.
   */
  DISLIKED_EMBARGO,
  /**
   * Realm has lost the game which means that
   * all it's planets have been conquered.
   * In theory realm still could have ships left and in theory
   * could colonize new planets, but changes are slim.
   * Most important purpose of this is that other realms
   * do not get war bonus or fatigue against this realm anymore.
   */
  REALM_LOST,
  /**
   * Between realms which participated or organized olympics.
   */
  OLYMPICS,
  /**
   * Organizer did not like if realm did not participate.
   */
  DNS_OLYMPICS,
  /**
   * If there are other realms which did not participate on same olympics
   * this is bonus for them.
   */
  OLYMPICS_EMBARGO,
  /**
   * Realm has promised to vote for yes next important vote.
   */
  PROMISED_VOTE_YES,
  /**
   * Realm has promised to vote for no next important vote.
   */
  PROMISED_VOTE_NO,
  /**
   * Realm kept the voting promise.
   */
  PROMISE_KEPT,
  /**
   * Realm did not keep the voting promise.
   */
  PROMISE_BROKEN,
  /**
   * Another realm made war declaration directly against our realm.
   */
  WAR_DECLARATION_AGAINST_US,
  /**
   * You succesfully claim another realm made "attack" against your
   * ship.
   */
  FALSE_FLAG,
  /**
   * Realm has free convicts without any good reasons.
   */
  FREED_CONVICT,
  /**
   * Space pirates have promised protection.
   */
  PROMISED_PROTECTION,
  /**
   * Players share the similar government.
   */
  SIMILAR_GOVERNMENT,
  /**
   * Players share the same government.
   */
  SAME_GOVERNMENT,
  /**
   * Players have different government.
   */
  DIFFERENT_GOVERNMENT,
  /**
   * Players share the similar government but in different group.
   */
  SIMILAR_GOVERNMENT_DIFFERENT_GROUP;




  /**
   * Number of Bonus type. This should be one larger than actual bonus types.
   */
  public static final int MAX_BONUS_TYPE = 39;

  /**
   * Get DiplomacyBonus index
   * @return int
   */
  public int getIndex() {
    switch (this) {
      case IN_WAR: return 0;
      case WAR_DECLARTION: return 1;
      case IN_TRADE_ALLIANCE: return 2;
      case IN_ALLIANCE: return 3;
      case DIPLOMAT_CAPTURED: return 4;
      case BORDER_CROSSED: return 5;
      case GIVEN_VALUABLE_FREE: return 6;
      case MADE_DEMAND: return 7;
      case DIPLOMATIC_TRADE: return 8;
      case SAME_RACE: return 9;
      case LONG_PEACE: return 10;
      case INSULT: return 11;
      case NUKED: return 12;
      case NOTHING_TO_TRADE: return 13;
      case IN_DEFENSIVE_PACT: return 14;
      case ESPIONAGE_BORDER_CROSS: return 15;
      case SPY_TRADE: return 16;
      case DIPLOMACY_BONUS: return 17;
      case TRADE_FLEET: return 18;
      case BOARD_PLAYER: return 19;
      case EMBARGO: return 20;
      case LIKED_EMBARGO: return 21;
      case DISLIKED_EMBARGO: return 22;
      case REALM_LOST: return 23;
      case OLYMPICS: return 24;
      case DNS_OLYMPICS: return 25;
      case OLYMPICS_EMBARGO: return 26;
      case PROMISED_VOTE_YES: return 27;
      case PROMISED_VOTE_NO: return 28;
      case PROMISE_KEPT: return 29;
      case PROMISE_BROKEN: return 30;
      case WAR_DECLARATION_AGAINST_US: return 31;
      case FALSE_FLAG: return 32;
      case FREED_CONVICT: return 33;
      case PROMISED_PROTECTION: return 34;
      case SIMILAR_GOVERNMENT: return 35;
      case SAME_GOVERNMENT: return 36;
      case DIFFERENT_GOVERNMENT: return 37;
      case SIMILAR_GOVERNMENT_DIFFERENT_GROUP: return 38;
      default: throw new IllegalArgumentException("No such Diplomacy Bonus"
          + " Type!");
    }
  }

  /**
   * Get textual description of diplomacy bonus.
   * @return String
   */
  public String getDescription() {
    switch (this) {
      case IN_WAR: return "We are at war";
      case WAR_DECLARTION: return "You have made war declaration";
      case IN_TRADE_ALLIANCE: return "We have trade alliance";
      case IN_ALLIANCE: return "We are at alliance";
      case DIPLOMAT_CAPTURED: return "Diplomat has been captured";
      case BORDER_CROSSED: return "You have crossed borders";
      case GIVEN_VALUABLE_FREE: return "You have given gift";
      case MADE_DEMAND: return "You have demanded something";
      case DIPLOMATIC_TRADE: return "We have done diplomatic trade";
      case SAME_RACE: return "We are belong to same space race";
      case LONG_PEACE: return "We have had long peace";
      case INSULT: return "You have made insult";
      case NUKED: return "You have used nuclear weapons";
      case NOTHING_TO_TRADE: return "You have nothing to trade";
      case IN_DEFENSIVE_PACT: return "We have defensive pact";
      case ESPIONAGE_BORDER_CROSS: return "Your spy has crossed borders";
      case SPY_TRADE: return "We have espionage trading";
      case DIPLOMACY_BONUS: return "We share same diplomatic views";
      case TRADE_FLEET: return "Our trade fleet has visited them";
      case BOARD_PLAYER: return "Board player";
      case EMBARGO: return "We are at embargo";
      case LIKED_EMBARGO: return "We have mutual embargo against another realm";
      case DISLIKED_EMBARGO: return "We are at embargo";
      case REALM_LOST: return "Realm has lost";
      case OLYMPICS: return "We participated in same olympics";
      case DNS_OLYMPICS: return "You did not participate their olympics";
      case OLYMPICS_EMBARGO: return "We share mutual views on same olympics";
      case PROMISED_VOTE_YES: return "Promised vote yes";
      case PROMISED_VOTE_NO: return "Promised vote no";
      case PROMISE_KEPT: return "You have kept your promise";
      case PROMISE_BROKEN: return "You have broken your promise";
      case WAR_DECLARATION_AGAINST_US:
        return "You have made war declaration against them";
      case FALSE_FLAG: return "False flag";
      case FREED_CONVICT: return "You have freed convict just because";
      case PROMISED_PROTECTION: return "Space pirate have promised protection";
      case SIMILAR_GOVERNMENT: return "You have similar governments";
      case SAME_GOVERNMENT: return "You have same governments";
      case DIFFERENT_GOVERNMENT: return "You have different governments";
      case SIMILAR_GOVERNMENT_DIFFERENT_GROUP:
        return "You have similar governments";
      default: throw new IllegalArgumentException("No such Diplomacy Bonus"
          + " Type!");
    }
  }

  /**
   * Get Casus belli score for based on diplomacy bonus type.
   * @return Casus belli score
   */
  public int getCasusBelliScore() {
    switch (this) {
      case IN_WAR: return 0; // Diplomatic relation
      case WAR_DECLARTION: return 5;
      case IN_TRADE_ALLIANCE: return 0; // Diplomatic relation
      case IN_ALLIANCE: return 0; // Diplomatic relation
      case DIPLOMAT_CAPTURED: return 0; // Not used
      case BORDER_CROSSED: return 3;
      case GIVEN_VALUABLE_FREE: return 0;  // Positive bonus
      case MADE_DEMAND: return 7;
      case DIPLOMATIC_TRADE: return 0; // Positive bonus
      case SAME_RACE: return 0; // Positive bonus
      case LONG_PEACE: return 0; // Diplomatic relation
      case INSULT: return 5;
      case NUKED: return 4;
      case NOTHING_TO_TRADE: return 0;  // Not positive or negative bonus
      case IN_DEFENSIVE_PACT: return 0; // Diplomatic relation
      case ESPIONAGE_BORDER_CROSS: return 5;
      case SPY_TRADE: return 0; // Diplomatic relation
      case DIPLOMACY_BONUS: return 0; // Mostly positive
      case TRADE_FLEET: return 0; // Positive bonus
      case BOARD_PLAYER: return 0; // Realm status
      case EMBARGO: return 0; // Diplomatic relation
      case LIKED_EMBARGO: return 0; // Positive bonus
      case DISLIKED_EMBARGO: return 2;
      case REALM_LOST: return 0; // Realm status
      case OLYMPICS: return 0; // Positive bonus
      case DNS_OLYMPICS: return 1;
      case OLYMPICS_EMBARGO: return 0; // Positive bonus
      case PROMISED_VOTE_YES: return 0; // Promised not happened yet
      case PROMISED_VOTE_NO: return 0; // Promised not happened yet
      case PROMISE_KEPT: return 0; // Positive bonus
      case PROMISE_BROKEN: return 4;
      case WAR_DECLARATION_AGAINST_US: return 8;
      case FALSE_FLAG: return 8;
      case FREED_CONVICT: return 1;
      case PROMISED_PROTECTION: return 0;
      case SIMILAR_GOVERNMENT: return 0;
      case SAME_GOVERNMENT: return 0;
      case DIFFERENT_GOVERNMENT: return 1;
      case SIMILAR_GOVERNMENT_DIFFERENT_GROUP: return 0;
      default: throw new IllegalArgumentException("No such Diplomacy Bonus"
          + " Type!");
    }
  }
  /**
   * Get Casus belli reason as string
   * @return Casus belli reason
   */
  public String getCasusBelliReason() {
    switch (this) {
      case IN_WAR: return "in war"; // Diplomatic relation
      case WAR_DECLARTION: return "war declarations";
      case IN_TRADE_ALLIANCE: return "in trade alliance"; // Diplomatic relatio
      case IN_ALLIANCE: return "in alliance"; // Diplomatic relation
      case DIPLOMAT_CAPTURED: return "diplomat captured"; // Not used
      case BORDER_CROSSED: return "borders crossed";
      case GIVEN_VALUABLE_FREE: return "gift";  // Positive bonus
      case MADE_DEMAND: return "demands";
      case DIPLOMATIC_TRADE: return "trades"; // Positive bonus
      case SAME_RACE: return "race"; // Positive bonus
      case LONG_PEACE: return "peace"; // Diplomatic relation
      case INSULT: return "insults";
      case NUKED: return "nuclear bombings";
      case NOTHING_TO_TRADE: return "nothing"; // Not positive or negative bonu
      case IN_DEFENSIVE_PACT: return "in defensive pact"; // Diplomatic relatio
      case ESPIONAGE_BORDER_CROSS: return "espionages";
      case SPY_TRADE: return "spy trade"; // Diplomatic relation
      case DIPLOMACY_BONUS: return "diplomacy"; // Mostly positive
      case TRADE_FLEET: return "trading"; // Positive bonus
      case BOARD_PLAYER: return "pirates"; // Realm status
      case EMBARGO: return "embargo"; // Diplomatic relation
      case LIKED_EMBARGO: return "embargo"; // Positive bonus
      case DISLIKED_EMBARGO: return "embargo";
      case REALM_LOST: return "realm lost"; // Realm status
      case OLYMPICS: return "olympics"; // Positive bonus
      case DNS_OLYMPICS: return "boycott olympics";
      case OLYMPICS_EMBARGO: return "boycott olympics"; // Positive bonus
      case PROMISED_VOTE_YES: return "promise"; // Promised not happened yet
      case PROMISED_VOTE_NO: return "promise"; // Promised not happened yet
      case PROMISE_KEPT: return "promise"; // Positive bonus
      case PROMISE_BROKEN: return "broken promises";
      case WAR_DECLARATION_AGAINST_US: return "war against us";
      case FALSE_FLAG: return "blown up ships";
      case FREED_CONVICT: return "freed convicted prisoners";
      case PROMISED_PROTECTION: return "Promised protection";
      case SAME_GOVERNMENT: return "same government";
      case SIMILAR_GOVERNMENT: return "similar government";
      case DIFFERENT_GOVERNMENT: return "different government";
      case SIMILAR_GOVERNMENT_DIFFERENT_GROUP: return "similar government";
      default: throw new IllegalArgumentException("No such Diplomacy Bonus"
          + " Type!");
    }
  }
  /**
   * Return diplomacy bonus type by index.
   * @param index This must be between 0-10
   * @return Diplomacy Bonus Type
   */
  public static DiplomacyBonusType getTypeByIndex(final int index) {
    switch (index) {
    case 0:
      return DiplomacyBonusType.IN_WAR;
    case 1:
      return DiplomacyBonusType.WAR_DECLARTION;
    case 2:
      return DiplomacyBonusType.IN_TRADE_ALLIANCE;
    case 3:
      return DiplomacyBonusType.IN_ALLIANCE;
    case 4:
      return DiplomacyBonusType.DIPLOMAT_CAPTURED;
    case 5:
      return DiplomacyBonusType.BORDER_CROSSED;
    case 6:
      return DiplomacyBonusType.GIVEN_VALUABLE_FREE;
    case 7:
      return DiplomacyBonusType.MADE_DEMAND;
    case 8:
      return DiplomacyBonusType.DIPLOMATIC_TRADE;
    case 9:
      return DiplomacyBonusType.SAME_RACE;
    case 10:
      return DiplomacyBonusType.LONG_PEACE;
    case 11:
      return DiplomacyBonusType.INSULT;
    case 12:
      return DiplomacyBonusType.NUKED;
    case 13:
      return DiplomacyBonusType.NOTHING_TO_TRADE;
    case 14:
      return DiplomacyBonusType.IN_DEFENSIVE_PACT;
    case 15:
      return DiplomacyBonusType.ESPIONAGE_BORDER_CROSS;
    case 16:
      return DiplomacyBonusType.SPY_TRADE;
    case 17:
      return DiplomacyBonusType.DIPLOMACY_BONUS;
    case 18:
      return DiplomacyBonusType.TRADE_FLEET;
    case 19:
      return DiplomacyBonusType.BOARD_PLAYER;
    case 20:
      return DiplomacyBonusType.EMBARGO;
    case 21:
      return DiplomacyBonusType.LIKED_EMBARGO;
    case 22:
      return DiplomacyBonusType.DISLIKED_EMBARGO;
    case 23:
      return DiplomacyBonusType.REALM_LOST;
    case 24:
      return DiplomacyBonusType.OLYMPICS;
    case 25:
      return DiplomacyBonusType.DNS_OLYMPICS;
    case 26:
      return DiplomacyBonusType.OLYMPICS_EMBARGO;
    case 27:
      return DiplomacyBonusType.PROMISED_VOTE_YES;
    case 28:
      return DiplomacyBonusType.PROMISED_VOTE_NO;
    case 29:
      return DiplomacyBonusType.PROMISE_KEPT;
    case 30:
      return DiplomacyBonusType.PROMISE_BROKEN;
    case 31:
      return DiplomacyBonusType.WAR_DECLARATION_AGAINST_US;
    case 32:
      return DiplomacyBonusType.FALSE_FLAG;
    case 33:
      return DiplomacyBonusType.FREED_CONVICT;
    case 34:
      return DiplomacyBonusType.PROMISED_PROTECTION;
    case 35:
      return DiplomacyBonusType.SIMILAR_GOVERNMENT;
    case 36:
      return DiplomacyBonusType.SAME_GOVERNMENT;
    case 37:
      return DiplomacyBonusType.DIFFERENT_GOVERNMENT;
    case 38:
      return DiplomacyBonusType.SIMILAR_GOVERNMENT_DIFFERENT_GROUP;
    default:
      throw new IllegalArgumentException("Unexpected diplomacy bonus type!");
    }
  }

}
