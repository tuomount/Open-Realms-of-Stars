package org.openRealmOfStars.player.diplomacy;
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

import org.openRealmOfStars.player.race.SpaceRace;

/**
 * Diplomacy Bonus
 */
public class DiplomacyBonus {

  /** Diplomacy Bonus type */
  private DiplomacyBonusType type;
  /** Only one this kind of bonus allowed in list */
  private boolean onlyOne;
  /** Bonus value for diplomacy. This can be both negative or positive */
  private int bonusValue;
  /**
   * How long bonus has lasted or how many star years ago something happened.
   * Value 255 means that it will last for ever unless something happens
   * if Value goes zero then bonusValue goes also zero.
   */
  private int bonusLasting;

  /**
   * Create diplomacy bonus with certain type.
   * @param bonusType Diplomacy bonus type
   * @param race Space Race whom is creating the bonus
   */
  public DiplomacyBonus(final DiplomacyBonusType bonusType,
      final SpaceRace race) {
    type = bonusType;
    onlyOne = false;
    switch (type) {
      case BORDER_CROSSED: {
        bonusValue = -3;
        bonusLasting = 20;
        break;
      }
      case GIVEN_VALUABLE_FREE: {
        bonusValue = 2;
        bonusLasting = 50;
        break;
      }
      case IN_ALLIANCE: {
        onlyOne = true;
        bonusValue = 25;
        bonusLasting = 255;
        break;
      }
      case IN_TRADE_ALLIANCE: {
        onlyOne = true;
        bonusValue = 12;
        bonusLasting = 255;
        break;
      }
      case LONG_PEACE: {
        onlyOne = true;
        bonusValue = 5;
        bonusLasting = 1;
        break;
      }
      case DIPLOMATIC_TRADE: {
        bonusValue = 4;
        bonusLasting = 100;
        break;
      }
      case IN_WAR: {
        onlyOne = true;
        bonusValue = -30;
        bonusLasting = 255;
        break;
      }
      case WAR_DECLARTION: {
        bonusValue = -8;
        bonusLasting = 150;
        break;
      }
      case MADE_DEMAND: {
        bonusValue = -5;
        bonusLasting = 80;
        break;
      }
      case INSULT: {
        bonusValue = -3;
        bonusLasting = 70;
        break;
      }
      case SAME_RACE: {
        onlyOne = true;
        bonusValue = 5;
        bonusLasting = 255;
        break;
      }
      case SIMILAR_GOVERNMENT: {
        onlyOne = true;
        bonusValue = 2;
        bonusLasting = 255;
        break;
      }
      case SAME_GOVERNMENT: {
        onlyOne = true;
        bonusValue = 3;
        bonusLasting = 255;
        break;
      }
      case SIMILAR_GOVERNMENT_DIFFERENT_GROUP: {
        onlyOne = true;
        bonusValue = 1;
        bonusLasting = 255;
        break;
      }
      case DIFFERENT_GOVERNMENT: {
        onlyOne = true;
        bonusValue = -2;
        bonusLasting = 255;
        break;
      }
      case NUKED: {
        bonusValue = -5;
        bonusLasting = 100;
        break;
      }
      case NOTHING_TO_TRADE: {
        onlyOne = true;
        bonusValue = 0;
        bonusLasting = 10;
        break;
      }
      case IN_DEFENSIVE_PACT: {
        onlyOne = true;
        bonusValue = 25;
        bonusLasting = 255;
        break;
      }
      case ESPIONAGE_BORDER_CROSS: {
        bonusValue = -4;
        bonusLasting = 30;
        break;
      }
      case SPY_TRADE: {
        bonusValue = 10;
        bonusLasting = 20;
        onlyOne = true;
        break;
      }
      case DIPLOMACY_BONUS: {
        bonusValue = race.getDiplomacyBonus();
        bonusLasting = 255;
        break;
      }
      case TRADE_FLEET: {
        bonusValue = 3;
        bonusLasting = 255;
        onlyOne = true;
        break;
      }
      case BOARD_PLAYER: {
        onlyOne = true;
        bonusValue = 0;
        bonusLasting = 255;
        break;
      }
      case EMBARGO: {
        onlyOne = true;
        bonusValue = -5;
        bonusLasting = 20;
        break;
      }
      case LIKED_EMBARGO: {
        bonusValue = 2;
        bonusLasting = 60;
        break;
      }
      case DISLIKED_EMBARGO: {
        bonusValue = -2;
        bonusLasting = 60;
        break;
      }
      case REALM_LOST: {
        onlyOne = true;
        bonusValue = 0;
        bonusLasting = 255;
        break;
      }
      case OLYMPICS: {
        bonusValue = 7;
        bonusLasting = 100;
        break;
      }
      case DNS_OLYMPICS: {
        bonusValue = -7;
        bonusLasting = 100;
        break;
      }
      case OLYMPICS_EMBARGO: {
        bonusValue = 6;
        bonusLasting = 100;
        break;
      }
      case PROMISED_VOTE_YES: {
        bonusValue = 3;
        bonusLasting = 40;
        break;
      }
      case PROMISED_VOTE_NO: {
        bonusValue = 3;
        bonusLasting = 40;
        break;
      }
      case PROMISE_KEPT: {
        bonusValue = 8;
        bonusLasting = 100;
        break;
      }
      case PROMISE_BROKEN: {
        bonusValue = -8;
        bonusLasting = 100;
        break;
      }
      case WAR_DECLARATION_AGAINST_US: {
        bonusValue = -12;
        bonusLasting = 255;
        break;
      }
      case FALSE_FLAG: {
        bonusValue = -4;
        bonusLasting = 60;
        break;
      }
      case FREED_CONVICT: {
        bonusValue = -2;
        bonusLasting = 60;
        break;
      }
      case PROMISED_PROTECTION: {
        bonusValue = 3;
        bonusLasting = 5;
        break;
      }
      default: {
        throw new IllegalArgumentException("Unknown bonus type!!");
      }
    }
  }

  /**
   * Handle bonus lasting for bonus. Bonus lasting
   * can decrease or increase depending on bonus type.
   * For example LONG_PEACE lasting will increase and most of the
   * other it will decrease.
   */
  public void handleBonusForTurn() {
    if (bonusLasting < 255 && bonusLasting > 0) {
      if (type == DiplomacyBonusType.LONG_PEACE) {
        setBonusLasting(getBonusLasting() + 1);
      } else {
        setBonusLasting(getBonusLasting() - 1);
      }
    }
  }

  /**
   * Get the Diplomacy bonus value
   * @return Bonus value
   */
  public int getBonusValue() {
    if (bonusLasting > 0) {
      if (type == DiplomacyBonusType.LONG_PEACE) {
        return bonusValue + bonusLasting / 10;
      }
      return bonusValue;
    }
    return 0;
  }

  /**
   * Only one of this kind of bonus is allowed in diplomacy bonus list.
   * @return True if only one is allowed
   */
  public boolean isOnlyOne() {
    return onlyOne;
  }

  /**
   * Set Diplomacy Bonus value.
   * @param bonusValue to set
   */
  public void setBonusValue(final int bonusValue) {
    this.bonusValue = bonusValue;
  }

  /**
   * Get the diplomacy bonus lasting. This is value in star years.
   * @return Diplomacy bonus lasting in star years.
   */
  public int getBonusLasting() {
    return bonusLasting;
  }

  /**
   * Set Diplomacy bonus lasting in star years
   * @param bonusLasting Diplomacy bonus lasting in star years
   */
  public void setBonusLasting(final int bonusLasting) {
    if (bonusLasting > 255) {
      this.bonusLasting = 255;
    } else if (bonusLasting < 0) {
      this.bonusLasting = 0;
    } else {
      this.bonusLasting = bonusLasting;
    }
  }

  /**
   * Get Diplomacy Bonus type
   * @return diplomacy bonus type
   */
  public DiplomacyBonusType getType() {
    return type;
  }

  @Override
  public String toString() {
    return type.name() + " Value: " + bonusValue + " Lasting: " + bonusLasting;
  }

}
