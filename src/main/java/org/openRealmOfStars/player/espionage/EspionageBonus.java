package org.openRealmOfStars.player.espionage;
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
* Espionage Bonus
*
*/
public class EspionageBonus {

  /**
   * Espionage Bonus type
   */
  private EspionageBonusType type;

  /**
   * Bonus as value
   */
  private int value;

  /**
   * Esipionage description
   */
  private String description;

  /**
   * Constructor for espionage bonus.
   * @param bonusType Espionage type
   * @param bonusValue Numerical value between 0 to 10.
   */
  public EspionageBonus(final EspionageBonusType bonusType,
      final int bonusValue) {
    type = bonusType;
    if (bonusValue > 10) {
      value = 10;
    } else if (bonusValue < 0) {
      value = 0;
    } else {
      value = bonusValue;
    }
    description = "";
  }

  /**
   * Get Espionage type
   * @return EspionageBonusType
   */
  public EspionageBonusType getType() {
    return type;
  }

  /**
   * Get the espionage bonus value
   * @return Bonus value
   */
  public int getValue() {
    return value;
  }

  /**
   * Get Espionage description
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Set espionage description. For example textual informat which fleet
   * add espionage
   * @param description the description to set
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return type.name() + " - " + description + " : " + value;
  }
}
