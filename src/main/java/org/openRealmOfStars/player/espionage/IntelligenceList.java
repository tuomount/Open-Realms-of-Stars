package org.openRealmOfStars.player.espionage;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
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

import java.util.ArrayList;
import java.util.List;

import org.openRealmOfStars.player.fleet.FleetType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
* Intelligence List
*
*/
public class IntelligenceList {

  /**
   * This is for PlayerInfo index
   */
  private int playerIndex;

  /**
   * Intelligence level 1 estimate +-40
   */
  private int intelligenceLevel1Estimate;

  /**
   * Intelligence level 3 estimate +-30
   */
  private int intelligenceLevel3Estimate;

  /**
   * Intelligence level 5 estimate +-20
   */
  private int intelligenceLevel5Estimate;

  /**
   * Intelligence level 7 estimate +-10
   */
  private int intelligenceLevel7Estimate;

  /**
   * Intelligence list
   */
  private List<IntelligenceBonus> list;

  /**
   * Get player index. This tells which realm is being
   * spied.
   * @return Player index
   */
  public int getPlayerIndex() {
    return playerIndex;
  }

  /**
   * Constructor Intelligence list.
   * @param index Player index who is being spied.
   */
  public IntelligenceList(final int index) {
    playerIndex = index;
    list = new ArrayList<>();
    setIntelligenceLevel1Estimate(DiceGenerator.getRandom(-40, 40));
    setIntelligenceLevel3Estimate(DiceGenerator.getRandom(-30, 30));
    setIntelligenceLevel5Estimate(DiceGenerator.getRandom(-20, 20));
    setIntelligenceLevel7Estimate(DiceGenerator.getRandom(-10, 10));
  }

  /**
   * Get number of elements in list.
   * @return Number of elements in list
   */
  public int getSize() {
    return list.size();
  }

  /**
   * Adds new Intelligence bonus to list
   * @param type Intelligence type
   * @param value Value
   * @param description Textual description about bonus
   */
  public void addIntelligenceBonus(final IntelligenceBonusType type,
      final int value, final String description) {
    IntelligenceBonus bonus = new IntelligenceBonus(type, value);
    bonus.setDescription(description);
    list.add(bonus);
  }

  /**
   * Get single Intelligence from the list by index
   * @param index Which Intelligence to fetch
   * @return Intelligence bonus or null if not found.
   */
  public IntelligenceBonus getIntelligence(final int index) {
    if (index >= 0 && index < list.size()) {
      return list.get(index);
    }
    return null;
  }

  /**
   * Get total Intelligence bonus. This bonus is limited between
   * 0 and 10.
   * @return Total Intelligence bonus
   */
  public int getTotalBonus() {
    int result = 0;
    for (IntelligenceBonus bonus : list) {
      result = result + bonus.getValue();
    }
    if (result > 10) {
      result = 10;
    }
    // Zero is limited in IntelligenceBonus, there value cannot be negative.
    return result;
  }

  /**
   * Get own Intelligence bonus. This bonus is limited between
   * 0 and 10. This does not count traded espionage
   * @return Total espionage bonus
   */
  public int getOwnBonus() {
    int result = 0;
    for (IntelligenceBonus bonus : list) {
      if (bonus.getType() == IntelligenceBonusType.SPY_FLEET) {
        result = result + bonus.getValue();
      }
    }
    if (result > 10) {
      result = 10;
    }
    // Zero is limited in IntelligenceBonus, there value cannot be negative.
    return result;
  }

  /**
   * Is certain fleet type recognized by Intelligence bonus
   * @param type Fleet Type
   * @return True if recognized
   */
  public boolean isFleetTypeRecognized(final FleetType type) {
    int bonus = getTotalBonus();
    if (type == FleetType.NON_MILITARY && bonus >= 4) {
      return true;
    }
    if (type == FleetType.STARBASE && bonus >= 6) {
      return true;
    }
    if (type == FleetType.MILITARY && bonus >= 8) {
      return true;
    }
    if (type == FleetType.PRIVATEER && bonus >= 10) {
      return true;
    }
    return false;
  }

  /**
   * Clear Intelligence list. This should be called after each turn.
   */
  public void clearList() {
    list = new ArrayList<>();
  }

  /**
   * Get Intelligence bonus effects as a text.
   * @param bonus Bonus level between 0 to 10
   * @return Description as a String.
   */
  public static String getTotalBonusAsDescriptions(final int bonus) {
    StringBuilder sb = new StringBuilder();
    if (bonus == 0) {
      sb.append("No intelligence");
    }
    if (bonus >= 9) {
      sb.append("Accurate knowledge of military power.");
      sb.append("\n");
    } else if (bonus >= 7) {
      sb.append("+-10% military power estimation");
      sb.append("\n");
    } else if (bonus >= 5) {
      sb.append("+-20% military power estimation");
      sb.append("\n");
    } else if (bonus >= 3) {
      sb.append("+-30% military power estimation");
      sb.append("\n");
    } else if (bonus >= 1) {
      sb.append("+-40% military power estimation");
      sb.append("\n");
    }
    if (bonus >= 2) {
      sb.append("Intelligence trade");
      sb.append("\n");
    }
    if (bonus == 10) {
      sb.append("Visibility of all fleets.");
    } else if (bonus >= 8) {
      sb.append("Visibility of all fleets expect privateers.");
    } else {
      if (bonus >= 4) {
        sb.append("Visibility of non military ships.");
        sb.append("\n");
      }
      if (bonus >= 6) {
        sb.append("Visibility of deployed starbases.");
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  /**
   * Get Intelligence estimate on level 7 +-10
   * @return the IntelligenceLevel7Estimate
   */
  public int getIntelligenceLevel7Estimate() {
    return intelligenceLevel7Estimate;
  }

  /**
   * Set the Intelligence estimate on level 7
   * @param intelligenceLevel7Estimate the IntelligenceLevel7Estimate to set
   */
  public void setIntelligenceLevel7Estimate(
      final int intelligenceLevel7Estimate) {
    this.intelligenceLevel7Estimate = intelligenceLevel7Estimate;
  }

  /**
   * Get Intelligence estimate on level 5 +-20
   * @return the IntelligenceLevel5Estimate
   */
  public int getIntelligenceLevel5Estimate() {
    return intelligenceLevel5Estimate;
  }

  /**
   * Set the Intelligence estimate on level 5
   * @param intelligenceLevel5Estimate the IntelligenceLevel5Estimate to set
   */
  public void setIntelligenceLevel5Estimate(
      final int intelligenceLevel5Estimate) {
    this.intelligenceLevel5Estimate = intelligenceLevel5Estimate;
  }

  /**
   * Get Intelligence estimate on level 3 +-30
   * @return the intelligenceLevel3Estimate
   */
  public int getIntelligenceLevel3Estimate() {
    return intelligenceLevel3Estimate;
  }

  /**
   * Set the Intelligence estimate on level 3
   * @param intelligenceLevel3Estimate the IntelligenceLevel3Estimate to set
   */
  public void setIntelligenceLevel3Estimate(
      final int intelligenceLevel3Estimate) {
    this.intelligenceLevel3Estimate = intelligenceLevel3Estimate;
  }

  /**
   * Get Intelligence estimate on level 1 +-40
   * @return the intelligenceLevel1Estimate
   */
  public int getIntelligenceLevel1Estimate() {
    return intelligenceLevel1Estimate;
  }

  /**
   * Set the Intelligence estimate on level 1
   * @param intelligenceLevel1Estimate the IntelligenceLevel1Estimate to set
   */
  public void setIntelligenceLevel1Estimate(
      final int intelligenceLevel1Estimate) {
    this.intelligenceLevel1Estimate = intelligenceLevel1Estimate;
  }

  /**
   * Estimate military power using Intelligence level
   * @param actualMilitaryPower Actual military power which is about to
   *        be estimated.
   * @return Estimation of military power.
   */
  public int estimateMilitaryPower(final int actualMilitaryPower) {
    int result = 0;
    int totalBonus = getTotalBonus();
    if (totalBonus >= 9) {
      result = actualMilitaryPower;
    } else if (totalBonus >= 7) {
      result = actualMilitaryPower
          + (actualMilitaryPower * getIntelligenceLevel7Estimate() / 100);
    } else if (totalBonus >= 5) {
      result = actualMilitaryPower
          + (actualMilitaryPower * getIntelligenceLevel5Estimate() / 100);
    } else if (totalBonus >= 3) {
      result = actualMilitaryPower
          + (actualMilitaryPower * getIntelligenceLevel3Estimate() / 100);
    } else if (totalBonus >= 1) {
      result = actualMilitaryPower
          + (actualMilitaryPower * getIntelligenceLevel1Estimate() / 100);
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (IntelligenceBonus bonus : list) {
      sb.append(bonus.toString());
      sb.append(",");
    }
    return sb.toString();
  }
}
