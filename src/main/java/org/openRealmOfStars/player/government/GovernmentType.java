package org.openRealmOfStars.player.government;

import org.openRealmOfStars.player.leader.LeaderUtility;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018-2020 Tuomo Untinen
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
* Government Type enumeration
*
*/
public enum GovernmentType {

  /**
   * Democracy government
   */
  DEMOCRACY(0, "Democracy", 1, -1, false, 2),
  /**
   * Alliance government
   */
  ALLIANCE(1, "Alliance", 1, -1, false, 2),
  /**
   * Federation government
   */
  FEDERATION(2, "Federation", 1, 0, false, 3),
  /**
   * Republic government
   */
  REPUBLIC(3, "Republic", 1, 0, false, 3),
  /**
   * Guild government
   */
  GUILD(4, "Guild", 0, 0, false, 3),
  /**
   * Enterprise government
   */
  ENTERPRISE(5, "Enterprise", 0, 0, false, 3),
  /**
   * Hegemony government
   */
  HEGEMONY(6, "Hegemony", -1, 1, false, 4),
  /**
   * Nest government
   */
  NEST(7, "Nest", 0, 0, true, 3),
  /**
   * Hivemind government
   */
  HIVEMIND(8, "Hive-mind", 0, 0, true, 3),
  /**
   * AI government
   */
  AI(9, "AI", 0, 0, true, 3),
  /**
   * Empire government
   */
  EMPIRE(10, "Empire", -1, 1, false, 4),
  /**
   * Kingdom government
   */
  KINGDOM(11, "Kingdom", -1, 1, false, 4),
  /**
   * Hierarchy government
   */
  HIERARCHY(12, "Hierarchy", -1, 1, false, 4),
  /**
   * Horde government
   */
  HORDE(13, "Horde", -1, 1, false, 4),
  /**
   * Horde government for Mechions
   */
  MECHANICAL_HORDE(14, "Horde", -1, 1, false, 4),
  /**
   * Clan government
   */
  CLAN(15, "Clan", -1, 1, false, 4);


  /**
   * Create government type enumeration.
   * @param index Index for type
   * @param name Government type name
   * @param happiness Generic happiness
   * @param warResistance War resistance for government
   * @param singleMind Is government single minded
   * @param baseFleetCapacity Base fleet capacity based on government
   */
  GovernmentType(final int index, final String name, final int happiness,
      final int warResistance, final boolean singleMind,
      final int baseFleetCapacity) {
    this.index = index;
    this.name = name;
    genericHappiness = happiness;
    this.warResistance = warResistance;
    this.immuneToHappiness = singleMind;
    this.fleetCapacity = baseFleetCapacity;
  }

  /**
   * Government index
   */
  private int index;
  /**
   * Name of the GovernmentType
   */
  private String name;

  /**
   * Generic happiness bonus
   */
  private int genericHappiness;

  /**
   * War resistances
   */
  private int warResistance;

  /**
   * Immune to happiness
   */
  private boolean immuneToHappiness;
  /**
   * Fleet capacity from government.
   */
  private int fleetCapacity;
  /**
   * Get name of government type
   * @return Name of government type
   */
  public String getName() {
    return name;
  }

  /**
   * Get generic happiness
   * @return Generic happiness bonus
   */
  public int getGenericHappiness() {
    return genericHappiness;
  }

  /**
   * Get War resistance. How many war there can
   * be without war fatigue?
   * @return War resistance.
   */
  public int getWarResistance() {
    return warResistance;
  }

  /**
   * Get Index of government type
   * @return Government type
   */
  public int getIndex() {
    return index;
  }

  /**
   * Is government immune to happiness system?
   * Is Government single minded organism?
   * @return True if no effects from happiness at all
   */
  public boolean isImmuneToHappiness() {
    return immuneToHappiness;
  }

  /**
   * Get diplomatic bonus for government
   * @return Diplomatic bonus
   */
  public int getDiplomaticBonus() {
    if (this == DEMOCRACY || this == ALLIANCE || this == FEDERATION
        || this == REPUBLIC) {
      return 1;
    }
    return 0;
  }
  /**
   * Get Trade bonus for government
   * @return Trade bonus
   */
  public int getTradeBonus() {
    if (this == DEMOCRACY || this == ALLIANCE || this == FEDERATION
        || this == REPUBLIC) {
      return 1;
    }
    if (this == GUILD || this == ENTERPRISE) {
      return 2;
    }
    return 0;
  }
  /**
   * Get Production bonus for government when
   * planet has 4 or more population.
   * @return Production bonus
   */
  public int getProductionBonus() {
    if (this == DEMOCRACY || this == ALLIANCE || this == EMPIRE
        || this == KINGDOM || this == HIERARCHY || this == MECHANICAL_HORDE) {
      return 1;
    }
    return 0;
  }

  /**
   * Get Research bonus for government when
   * planet has 4 or more population.
   * @return Research bonus
   */
  public int getResearchBonus() {
    if (this == HEGEMONY) {
      return 1;
    }
    return 0;
  }

  /**
   * Get Food bonus for government when
   * planet has 4 or more population.
   * @return Food bonus
   */
  public int getFoodBonus() {
    if (this == HORDE || this == CLAN) {
      return 1;
    }
    return 0;
  }

  /**
   * Get Credit bonus for government when
   * planet has 4 or more population.
   * @return Credit bonus
   */
  public int getCreditBonus() {
    if (this == GUILD || this == ENTERPRISE) {
      return 1;
    }
    return 0;
  }

  /**
   * Government give war happiness if on realm is in war
   * @return True if war happiness bonus
   */
  public boolean hasWarHappiness() {
    if (this == HORDE || this == CLAN || this == MECHANICAL_HORDE) {
      return true;
    }
    return false;
  }

  /**
   * GovernmentType has population rush.
   * @return True if population rush available
   */
  public boolean hasPopulationRush() {
    if (this == EMPIRE || this == KINGDOM || this == HIERARCHY
        || this == HORDE || this == CLAN || this == MECHANICAL_HORDE) {
      return true;
    }
    return false;
  }

  /**
   * GovernmentType has credit rush.
   * @return True if credit rush available
   */
  public boolean hasCreditRush() {
    if (this == GUILD || this == ENTERPRISE || this == DEMOCRACY
        || this == ALLIANCE || this == FEDERATION || this == REPUBLIC) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return name;
  }

  /**
   * Get Fleet capacity base value.
   * @return Fleet capacity base value.
   */
  public int getFleetCapacity() {
    return fleetCapacity;
  }

  /**
   * Get Leader pool size where not calculating heirs.
   * When leader pool is full recruit cost goes sky rocket.
   * @return Leader Pool size.
   */
  public int leaderPoolLimit() {
    switch (this) {
      default:
      case ALLIANCE:
      case DEMOCRACY:
      case FEDERATION:
      case MECHANICAL_HORDE:
      case REPUBLIC: {
        return 10;
      }
      case AI:
      case HIVEMIND:
      case NEST: {
        return 6;
      }
      case GUILD:
      case ENTERPRISE: {
        return 12;
      }
      case EMPIRE:
      case KINGDOM: {
        return 7;
      }
      case HORDE:
      case CLAN: {
        return 9;
      }
      case HEGEMONY:
      case HIERARCHY: {
        return 8;
      }
    }
  }
  /**
   * Get regular leader recruit cost.
   * Actual cost is multiplied by current leader pool size.
   * @return single leader recruit cost
   */
  public int leaderRecruitCost() {
    switch (this) {
      default:
      case ALLIANCE:
      case DEMOCRACY:
      case FEDERATION:
      case MECHANICAL_HORDE:
      case REPUBLIC:
      case EMPIRE:
      case KINGDOM:
      case HORDE:
      case CLAN: {
        return 10;
      }
      case AI:
      case HIVEMIND:
      case NEST: {
        return 5;
      }
      case GUILD:
      case ENTERPRISE: {
        return 12;
      }
      case HEGEMONY:
      case HIERARCHY: {
        return 8;
      }
    }
  }

  /**
   * Goverment can have heirs.
   * @return True or false
   */
  public boolean hasHeirs() {
    switch (this) {
      default:
      case ALLIANCE:
      case DEMOCRACY:
      case FEDERATION:
      case MECHANICAL_HORDE:
      case AI:
      case HIVEMIND:
      case NEST:
      case GUILD:
      case ENTERPRISE:
      case HEGEMONY:
      case HIERARCHY:
      case REPUBLIC: {
        return false;
      }
      case EMPIRE:
      case KINGDOM:
      case HORDE:
      case CLAN: {
        return true;
      }
    }
  }
  /**
   * Get ruler reign time.
   * @return Reign time in turns or -1 if life time
   */
  public int reignTime() {
    switch (this) {
      case ALLIANCE:
      case DEMOCRACY:
      case FEDERATION:
      case REPUBLIC: {
        return 20;
      }
      case GUILD:
      case ENTERPRISE: {
        return 40;
      }
      case AI: {
        return 100;
      }
      default:
      case HEGEMONY:
      case KINGDOM:
      case HIVEMIND:
      case NEST:
      case CLAN:
      case HORDE:
      case MECHANICAL_HORDE:
      case EMPIRE:
      case HIERARCHY: {
        return -1;
      }
    }
  }
  /**
   * Get Government type description
   * @param markDown Is description on Markdown format or HTML.
   * @return Description
   */
  public String getDescription(final boolean markDown) {
    String lf = "<br>";
    String dot = "<li>";
    if (markDown) {
      lf = "\n";
      dot = "*";
    }

    StringBuilder sb = new StringBuilder();
    if (!markDown) {
      sb.append("<html>");
    } else {
      sb.append("### ");
    }
    sb.append(getName());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Base fleet capacity: ");
    sb.append(getFleetCapacity());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Leader capacity: ");
    sb.append(leaderPoolLimit());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Leader cost: ");
    sb.append(leaderRecruitCost());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Reign length: ");
    if (reignTime() == -1) {
      sb.append("life time");
      sb.append(lf);
    } else {
      sb.append(reignTime());
      sb.append(" turns");
      sb.append(lf);
    }
    if (hasHeirs()) {
      sb.append(dot);
      sb.append(" Rulers have heirs");
      sb.append(lf);
    }
    if (LeaderUtility.isPowerHungryReadyForKill(this)) {
      sb.append(dot);
      sb.append(" Possibility to internal power struggle");
      sb.append(lf);
    }
    if (isImmuneToHappiness()) {
      sb.append(dot);
      sb.append(" No effects on happines nor war fatigue");
      sb.append(lf);
    } else {
      if (getGenericHappiness() != 0) {
        sb.append(dot);
        sb.append(" Generic happiness: ");
        sb.append(getGenericHappiness());
        sb.append(lf);
      }
      if (getDiplomaticBonus() != 0) {
        sb.append(dot);
        sb.append(" Diplomatic bonus: ");
        sb.append(getDiplomaticBonus());
        sb.append(lf);
      }
      if (getTradeBonus() != 0) {
        sb.append(dot);
        sb.append(" Trade bonus: ");
        sb.append(getTradeBonus());
        sb.append(lf);
      }
      if (getWarResistance() != 0) {
        sb.append(dot);
        sb.append(" War resistance: ");
        sb.append(getWarResistance());
        sb.append(lf);
      }
      if (getProductionBonus() != 0) {
        sb.append(dot);
        sb.append(" Production bonus: ");
        sb.append(getProductionBonus());
        sb.append(lf);
      }
      if (getCreditBonus() != 0) {
        sb.append(dot);
        sb.append(" Credit bonus: ");
        sb.append(getCreditBonus());
        sb.append(lf);
      }
      if (getResearchBonus() != 0) {
        sb.append(dot);
        sb.append(" Research bonus: ");
        sb.append(getResearchBonus());
        sb.append(lf);
      }
      if (getFoodBonus() != 0) {
        sb.append(dot);
        sb.append(" Food bonus: ");
        sb.append(getFoodBonus());
        sb.append(lf);
      }
      if (hasWarHappiness()) {
        sb.append(dot);
        sb.append(" War happiness");
        sb.append(lf);
      }
      if (hasPopulationRush()) {
        sb.append(dot);
        sb.append(" Population rush");
        sb.append(lf);
      }
      if (hasCreditRush()) {
        sb.append(dot);
        sb.append(" Credit rush");
        sb.append(lf);
      }
    }
    if (!markDown) {
      sb.append("</html>");
    }
    return sb.toString();
  }
}
