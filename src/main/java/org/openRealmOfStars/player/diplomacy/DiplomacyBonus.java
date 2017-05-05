package org.openRealmOfStars.player.diplomacy;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* Diplomacy Bonus
*/
public class DiplomacyBonus {

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
       if (race == SpaceRace.SPORKS) {
         bonusValue = -1;
         bonusLasting = 10;
       } else if (race == SpaceRace.MECHIONS) {
         bonusValue = -3;
         bonusLasting = 10;
       } else {
         bonusValue = -2;
         bonusLasting = 20;
       }
       break;
     }
     case DIPLOMAT_CAPTURED: {
       if (race == SpaceRace.SPORKS) {
         bonusValue = -3;
         bonusLasting = 200;
       } else if (race == SpaceRace.CENTAURS) {
         bonusValue = -8;
         bonusLasting = 200;
       } else {
         bonusValue = -5;
         bonusLasting = 200;
       }
       break;
     }
     case GIVEN_VALUABLE_FREE: {
       if (race == SpaceRace.HUMAN) {
         bonusValue = 3;
         bonusLasting = 50;
       } else {
         bonusValue = 2;
         bonusLasting = 50;
       }
       break;
     }
     case IN_ALLIANCE: {
       onlyOne = true;
       if (race == SpaceRace.GREYANS) {
         bonusValue = 30;
         bonusLasting = 255;
       } else {
         bonusValue = 25;
         bonusLasting = 255;
       }
       break;
     }
     case IN_TRADE_ALLIANCE: {
       onlyOne = true;
       if (race == SpaceRace.GREYANS) {
         bonusValue = 18;
         bonusLasting = 255;
       } else {
         bonusValue = 12;
         bonusLasting = 255;
       }
       break;
     }
     case LONG_PEACE: {
       onlyOne = true;
       if (race == SpaceRace.SPORKS) {
         bonusValue = 1;
         bonusLasting = 1;
       } else {
         bonusValue = 5;
         bonusLasting = 1;
       }
       break;
     }
     case DIPLOMATIC_TRADE: {
       if (race == SpaceRace.HUMAN) {
         bonusValue = 5;
         bonusLasting = 80;
       } else {
         bonusValue = 4;
         bonusLasting = 80;
       }
       break;
     }
     case IN_WAR: {
       onlyOne = true;
       if (race == SpaceRace.GREYANS) {
         bonusValue = -40;
         bonusLasting = 255;
       } else {
         bonusValue = -30;
         bonusLasting = 255;
       }
       break;
     }
     case WAR_DECLARTION: {
       if (race == SpaceRace.SPORKS) {
         bonusValue = -2;
         bonusLasting = 255;
       } else {
         bonusValue = -8;
         bonusLasting = 255;
       }
       break;
     }
     case MADE_DEMAND: {
       if (race == SpaceRace.SPORKS) {
         bonusValue = -1;
         bonusLasting = 128;
       } else {
         bonusValue = -5;
         bonusLasting = 200;
       }
       break;
     }
     case SAME_RACE: {
       onlyOne = true;
       if (race == SpaceRace.MECHIONS) {
         bonusValue = -3;
         bonusLasting = 255;
       } else if (race == SpaceRace.SPORKS) {
         bonusValue = 2;
         bonusLasting = 255;
       } else {
         bonusValue = 5;
         bonusLasting = 255;
       }
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
   * Diplomacy Bonus type
   */
  private DiplomacyBonusType type;

  /**
   * Only one this kind of bonus allowed in list
   */
  private boolean onlyOne;

  /**
   * Bonus value for diplomacy. This can be both negative or positive
   */
  private int bonusValue;

  /**
   * How long bonus has lasted or how many turns ago something happened.
   * Value 255 means that it will last for ever unless something happens
   * if Value goes zero then bonusValue goes also zero.
   */
  private int bonusLasting;

  /**
   * Get the Diplomacy bonus value
   * @return Bonus value
   */
  public int getBonusValue() {
    int bonusValueResult = 0;
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
   * Get the diplomacy bonus lasting. This is value in turns.
   * @return Diplomacy bonus lasting in turns.
   */
  public int getBonusLasting() {
    return bonusLasting;
  }

  /**
   * Set Diplomacy bonus lasting in turns
   * @param bonusLasting Diplomacy bonus lasting in turns
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


}
