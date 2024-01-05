package org.openRealmOfStars.player.diplomacy.speeches;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2023 Tuomo Untinen
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
 */

/**
 * Speech types for lines and answers
 */
public enum SpeechType {
  /** LINE: Trade offer a fair one made sincerely */
  TRADE,
  /** LINE: Trade offer made with demand */
  DEMAND,
  /** LINE: Make war between */
  MAKE_WAR,
  /** LINE: Offer trade alliance */
  TRADE_ALLIANCE,
  /** LINE: Offer alliance */
  ALLIANCE,
  /** Answer: Agree to offer */
  AGREE,
  /** Answer: Decline offer politely */
  DECLINE,
  /** Answer: Decline offer with anger */
  DECLINE_ANGER,
  /** Answer: Decline offer with war */
  DECLINE_WAR,
  /** Neutral greeting */
  NEUTRAL_GREET,
  /** Dislike greeting */
  DISLIKE_GREET,
  /** Hate greeting */
  HATE_GREET,
  /** Like greeting */
  LIKE_GREET,
  /** Friends greeting */
  FRIENDS_GREET,
  /** Peace offer */
  PEACE_OFFER,
  /** Respond for insult */
  INSULT_RESPOND,
  /** Offer has been rejected */
  OFFER_REJECTED,
  /** Offer has been accepted */
  OFFER_ACCEPTED,
  /** Demand move fleet */
  ASK_MOVE_FLEET,
  /** Agree to move fleet */
  MOVE_FLEET,
  /** AI notices that there weren't really anything for trade. */
  NOTHING_TO_TRADE,
  /** LINE: Offer defensive pact */
  DEFESIVE_PACT,
  /** Demand move espionage fleet */
  ASK_MOVE_SPY,
  /** Offer espionage trade for 20 star years. */
  OFFER_SPY_TRADE,
  /** Offer trade embargo for 20 star years */
  TRADE_EMBARGO,
  /** Realm choice for embargo, not real speech line */
  TRADE_EMBARGO_REALM_CHOICE,
  /** Fleets have crossed borders too many times, declared the war. */
  BORDER_WARS,
  /** Space pirates could ask protection or realm could pay for protection */
  ASK_PROTECTION;

  /**
   * Get Speech Type as a string.
   * @return SpeechType as a String
   */
  @Override
  public String toString() {
    switch (this) {
      case TRADE:            return "Trade";
      case DEMAND:           return "Demand";
      case MAKE_WAR:         return "Make War";
      case TRADE_ALLIANCE:   return "Trade Alliance";
      case ALLIANCE:         return "Alliance";
      case AGREE:            return "Agree";
      case DECLINE:          return "Decline";
      case DECLINE_ANGER:    return "Decline with Anger";
      case DECLINE_WAR:      return "Decline with War";
      case NEUTRAL_GREET:    return "Neutral greet";
      case DISLIKE_GREET:    return "Dislike greet";
      case LIKE_GREET:       return "Like greet";
      case HATE_GREET:       return "Hate greet";
      case FRIENDS_GREET:    return "Friends greet";
      case PEACE_OFFER:      return "Peace offer";
      case INSULT_RESPOND:   return "Insult respond";
      case OFFER_REJECTED:   return "Offer rejected";
      case OFFER_ACCEPTED:   return "Offer accepted";
      case ASK_MOVE_FLEET:   return "Ask move fleet";
      case MOVE_FLEET:       return "Move fleet";
      case NOTHING_TO_TRADE: return "Nothing to trade";
      case DEFESIVE_PACT:    return "Defensive pact";
      case ASK_MOVE_SPY:     return "Ask move spy fleet";
      case OFFER_SPY_TRADE:  return "Offer spy trade";
      case TRADE_EMBARGO:    return "Offer trade embargo";
      case TRADE_EMBARGO_REALM_CHOICE:    return "Embargo realm choice";
      case BORDER_WARS:      return "Border wars";
      case ASK_PROTECTION:   return "Ask protection";
      default: return "Unknown";
    }
  }
}
