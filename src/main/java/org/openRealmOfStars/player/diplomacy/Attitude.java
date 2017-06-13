package org.openRealmOfStars.player.diplomacy;
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
* Attitude/personality types for AI
*
*/
public enum Attitude {

  /**
   * Aggressive AI attacking often, making threats.
   */
  AGGRESSIVE,
  /**
   * Diplomatic tries to be nice with every one.
   */
  DIPLOMATIC,
  /**
   * Scientist tries to achieve more knowledge.
   */
  SCIENTIFIC,
  /**
   * Tries to search galaxy fully and conquer as many planet as possible.
   */
  EXPANSIONIST,
  /**
   * Backstabbing like diplomatic but can make backstabbing move.
   */
  BACKSTABBING,
  /**
   * Focus more on trading and trading vessels.
   */
  MERCHANTICAL,
  /**
   * Military focus on military things, slightly aggressive but very
   * trustworthy.
   */
  MILITARISTIC,
  /**
   * Peaceful tries to avoid conflict at any cost
   */
  PEACEFUL;

  /**
   * Get Attitude index
   * @return index for attitude
   */
  public int getIndex() {
    switch (this) {
      case AGGRESSIVE: return 0;
      case DIPLOMATIC: return 1;
      case SCIENTIFIC: return 2;
      case MILITARISTIC: return 3;
      case EXPANSIONIST: return 4;
      case BACKSTABBING: return 5;
      case MERCHANTICAL: return 6;
      case PEACEFUL: return 7;
      default: throw new IllegalArgumentException("No such Attitude!");
    }
  }

  /**
   * Return Attitude by index.
   * @param index This must be between 0-7
   * @return Attitude
   */
  public static Attitude getTypeByIndex(final int index) {
    switch (index) {
      case 0: return AGGRESSIVE;
      case 1: return DIPLOMATIC;
      case 2: return SCIENTIFIC;
      case 3: return MILITARISTIC;
      case 4: return EXPANSIONIST;
      case 5: return BACKSTABBING;
      case 6: return MERCHANTICAL;
      case 7: return PEACEFUL;
      default: throw new IllegalArgumentException("Unexpected attitude!");
    }
  }

}
