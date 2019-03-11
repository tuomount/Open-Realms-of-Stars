package org.openRealmOfStars.player.diplomacy;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017-2019  Tuomo Untinen
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
  OLYMPICS_EMBARGO;


  /**
   * Number of Bonus type. This should be one larger than actual bonus types.
   */
  public static final int MAX_BONUS_TYPE = 27;

  /**
   * Get ShipHullType index
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
    default:
      throw new IllegalArgumentException("Unexpected diplomacy bonus type!");
    }
  }

}
