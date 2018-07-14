package org.openRealmOfStars.starMap.planet;

import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Happiness effect for planet
*
*/
public class HappinessEffect {

  /**
   * Happiness/Sad bonus type
   */
  private HappinessBonus bonus;
  /**
   * Bonus value
   */
  private int value;

  /**
   * Happiness effect constructor
   * @param bonus Happiness/Sadness bonus type
   * @param value Numeric value
   */
  public HappinessEffect(final HappinessBonus bonus, final int value) {
    this.bonus = bonus;
    this.value = value;
  }

  /**
   * Create random happiness effect according the happiness.
   * @param happiness Happiness for planet
   * @return HappinessEffect
   */
  public static HappinessEffect createHappinessEffect(final int happiness) {
    if (happiness == 0) {
      return new HappinessEffect(HappinessBonus.NONE, 0);
    }
    if (happiness == 1) {
      int value = DiceGenerator.getRandom(99);
      if (value < 5) {
        return new HappinessEffect(HappinessBonus.PRODUCTION, 1);
      }
      return new HappinessEffect(HappinessBonus.NONE, 0);
    }
    if (happiness == -1) {
      int value = DiceGenerator.getRandom(99);
      if (value < 5) {
        return new HappinessEffect(HappinessBonus.PRODUCTION, -1);
      }
      return new HappinessEffect(HappinessBonus.NONE, 0);
    }
    if (happiness == 2) {
      int value = DiceGenerator.getRandom(99);
      if (value < 2) {
        return new HappinessEffect(HappinessBonus.PRODUCTION, 1);
      }
      if (value < 3) {
        return new HappinessEffect(HappinessBonus.METAL, 1);
      }
      if (value < 4) {
        return new HappinessEffect(HappinessBonus.CREDIT, 1);
      }
      if (value < 5) {
        return new HappinessEffect(HappinessBonus.CULTURE, 1);
      }
      return new HappinessEffect(HappinessBonus.NONE, 0);
    }
    if (happiness == -2) {
      int value = DiceGenerator.getRandom(99);
      if (value < 2) {
        return new HappinessEffect(HappinessBonus.PRODUCTION, -1);
      }
      if (value < 4) {
        return new HappinessEffect(HappinessBonus.METAL, -1);
      }
      if (value < 6) {
        return new HappinessEffect(HappinessBonus.CREDIT, -1);
      }
      if (value < 8) {
        return new HappinessEffect(HappinessBonus.CULTURE, -1);
      }
      return new HappinessEffect(HappinessBonus.NONE, 0);
    }
    if (happiness == 3) {
      int value = DiceGenerator.getRandom(99);
      if (value == 0) {
        return new HappinessEffect(HappinessBonus.PRODUCTION, 1);
      }
      if (value == 1) {
        return new HappinessEffect(HappinessBonus.PRODUCTION, 2);
      }
      if (value == 2) {
        return new HappinessEffect(HappinessBonus.METAL, 1);
      }
      if (value == 3) {
        return new HappinessEffect(HappinessBonus.METAL, 2);
      }
      if (value == 4) {
        return new HappinessEffect(HappinessBonus.CREDIT, 1);
      }
      if (value == 5) {
        return new HappinessEffect(HappinessBonus.CREDIT, 2);
      }
      if (value == 6) {
        return new HappinessEffect(HappinessBonus.CULTURE, 1);
      }
      if (value == 7) {
        return new HappinessEffect(HappinessBonus.CULTURE, 2);
      }
      return new HappinessEffect(HappinessBonus.NONE, 0);
    }
    if (happiness == -3) {
      int value = DiceGenerator.getRandom(99);
      if (value < 2) {
        return new HappinessEffect(HappinessBonus.PRODUCTION, -2);
      }
      if (value < 4) {
        return new HappinessEffect(HappinessBonus.METAL, -2);
      }
      if (value < 6) {
        return new HappinessEffect(HappinessBonus.CREDIT, -2);
      }
      if (value < 8) {
        return new HappinessEffect(HappinessBonus.KILL_POPULATION, 1);
      }
      return new HappinessEffect(HappinessBonus.NONE, 0);
    }
    if (happiness >= 4) {
      int value = DiceGenerator.getRandom(99);
      if (value < 2) {
        return new HappinessEffect(HappinessBonus.PRODUCTION, 2);
      }
      if (value < 4) {
        return new HappinessEffect(HappinessBonus.METAL, 2);
      }
      if (value < 6) {
        return new HappinessEffect(HappinessBonus.CREDIT, 2);
      }
      if (value < 8) {
        return new HappinessEffect(HappinessBonus.CULTURE, 2);
      }
      return new HappinessEffect(HappinessBonus.NONE, 0);
    }
    if (happiness <= -4) {
      int value = DiceGenerator.getRandom(99);
      if (value < 2) {
        return new HappinessEffect(HappinessBonus.PRODUCTION, -2);
      }
      if (value < 4) {
        return new HappinessEffect(HappinessBonus.DESTROY_BUILDING, 1);
      }
      if (value < 6) {
        return new HappinessEffect(HappinessBonus.CREDIT, -2);
      }
      if (value < 8) {
        return new HappinessEffect(HappinessBonus.KILL_POPULATION, 1);
      }
      return new HappinessEffect(HappinessBonus.NONE, 0);
    }
    return new HappinessEffect(HappinessBonus.NONE, 0);
  }
  /**
   * Get Happiness/Sadness bonus type
   * @return bonus type
   */
  public HappinessBonus getType() {
    return bonus;
  }

  /**
   * Get Numeric value of happiness/sadness bonus
   * @return Numeric value
   */
  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "HappinessEffect [bonus=" + bonus + ", value=" + value + "]";
  }

}
