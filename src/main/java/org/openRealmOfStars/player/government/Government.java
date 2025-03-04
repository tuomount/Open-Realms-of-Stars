package org.openRealmOfStars.player.government;
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

import org.openRealmOfStars.player.government.trait.GovTrait;
import org.openRealmOfStars.player.government.trait.GovTraitIds;
import org.openRealmOfStars.player.leader.LeaderUtility;

/**
*
* Government Class
*
*/
public class Government {


  /**
   * Create government
   * @param id Government ID
   * @param name Government type name
   */
  Government(final String id, final String name) {
    this.id = id;
    this.name = name;
    traits = new ArrayList<>();
  }

  /**
   * Government
   */
  private String id;
  /**
   * Name of the GovernmentType
   */
  private String name;

  /**
   * Ruler selection government has.
   */
  private RulerSelection rulerSelection;
  /**
   * Ruler title for male.
   */
  private String rulerTitleMale;
  /**
   * Ruler title for female.
   */
  private String rulerTitleFemale;
  /**
   * List of traits government has.
   */
  private ArrayList<GovTrait> traits;
  /**
   * Get name of government type
   * @return Name of government type
   */
  public String getName() {
    return name;
  }

  /**
   * Get Id for government.
   * @return Id
   */
  public String getId() {
    return id;
  }
  /**
   * Set Ruler selection
   * @param rulerSelection RulerSelection
   */
  public void setRulerSelection(final RulerSelection rulerSelection) {
    this.rulerSelection = rulerSelection;
  }

  /**
   * Get RulerSelection
   * @return RulerSelection
   */
  public RulerSelection getRulerSelection() {
    return rulerSelection;
  }

  /**
   * Add Government trait.
   * @param trait Trait to add
   */
  public void addTrait(final GovTrait trait) {
    GovTrait[] traitArray = traits.toArray(new GovTrait[0]);
    String conflict = GovTrait.isTraitConflict(trait, traitArray);
    if (conflict != null) {
      throw new IllegalArgumentException(
          "Cannot add Government trait because of conflict. Conflicting ID "
          + conflict + " while trying to add " + trait.getId() + ".");
    }
    traits.add(trait);
  }

  /**
   * Has certain trait?
   * @param iD Trait Id
   * @return True if government has certain ID.
   */
  public boolean hasTrait(final String iD) {
    for (GovTrait trait : traits) {
      if (trait.getId().equals(iD)) {
        return true;
      }
    }
    return false;
  }
  /**
   * Get generic happiness
   * @return Generic happiness bonus
   */
  public int getGenericHappiness() {
    int result = 0;
    if (hasTrait(GovTraitIds.UNHAPPY)) {
      result = -1;
    }
    if (hasTrait(GovTraitIds.HAPPY)) {
      result = 1;
    }
    if (hasTrait(GovTraitIds.EXTRA_HAPPY)) {
      result = 2;
    }
    return result;
  }

  /**
   * Get War resistance. How many war there can
   * be without war fatigue?
   * @return War resistance.
   */
  public int getWarResistance() {
    int result = 0;
    if (hasTrait(GovTraitIds.WEAKNESS_FOR_WAR)) {
      result = -1;
    }
    if (hasTrait(GovTraitIds.TOLERANT_FOR_WAR)) {
      result = 1;
    }
    if (hasTrait(GovTraitIds.ENDURE_WAR)) {
      result = 2;
    }
    return result;
  }

  /**
   * Is government immune to happiness system?
   * Is Government single minded organism?
   * @return True if no effects from happiness at all
   */
  public boolean isImmuneToHappiness() {
    return hasTrait(GovTraitIds.SINGLE_MIND);
  }

  /**
   * Get diplomatic bonus for government
   * @return Diplomatic bonus
   */
  public int getDiplomaticBonus() {
    int result = 0;
    if (hasTrait(GovTraitIds.DIPLOMATIC)) {
      result = 2;
    }
    if (hasTrait(GovTraitIds.POLITICAL)) {
      result = 1;
    }
    return result;
  }
  /**
   * Get Trade bonus for government
   * @return Trade bonus
   */
  public int getTradeBonus() {
    int result = 0;
    if (hasTrait(GovTraitIds.MERCANTILE)) {
      result = 1;
    }
    if (hasTrait(GovTraitIds.BUSINESS_ORIENTED)) {
      result = 2;
    }
    return result;
  }
  /**
   * Get Production bonus for government when
   * planet has 4 or more population.
   * @return Production bonus
   */
  public int getProductionBonus() {
    int result = 0;
    if (hasTrait(GovTraitIds.INDUSTRIAL)) {
      result = 1;
    }
    return result;
  }

  /**
   * Get mining bonus for government when
   * planet has 4 or more population.
   * @return mining bonus
   */
  public int getMiningBonus() {
    int result = 0;
    if (hasTrait(GovTraitIds.MINING_ORIENTED)) {
      result = 1;
    }
    return result;
  }

  /**
   * Get Research bonus for government when
   * planet has 4 or more population.
   * @return Research bonus
   */
  public int getResearchBonus() {
    int result = 0;
    if (hasTrait(GovTraitIds.RESEARCH_ORIENTED)) {
      result = 1;
    }
    if (hasTrait(GovTraitIds.TECHNOLOGY_FOCUSED)) {
      result = 2;
    }
    return result;
  }

  /**
   * Get Culture bonus for government when
   * planet has 4 or more population.
   * @return Culture bonus
   */
  public int getCultureBonus() {
    if (hasTrait(GovTraitIds.CULTURE_ORIENTED)) {
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
    if (hasTrait(GovTraitIds.AGRICULTURAL_ORIENTED)) {
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
    if (hasTrait(GovTraitIds.TAX_ORIENTED)) {
      return 1;
    }
    return 0;
  }

  /**
   * Government give war happiness if on realm is in war
   * @return True if war happiness bonus
   */
  public boolean hasWarHappiness() {
    return hasTrait(GovTraitIds.WAR_HAPPY);
  }

  /**
   * GovernmentType has population rush.
   * @return True if population rush available
   */
  public boolean hasPopulationRush() {
    return hasTrait(GovTraitIds.POPULATION_RUSH);
  }

  /**
   * GovernmentType has credit rush.
   * @return True if credit rush available
   */
  public boolean hasCreditRush() {
    return hasTrait(GovTraitIds.CREDIT_RUSH);
  }

  /**
   * Allow building armed freighters.
   * @return True if armed freigters are ok.
   */
  public boolean allowArmedFreighters() {
    return hasTrait(GovTraitIds.ARMED_FREIGHTERS);
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
    int result = 3;
    if (hasTrait(GovTraitIds.VERY_LOW_FLEET_CAPACITY)) {
      result = 1;
    }
    if (hasTrait(GovTraitIds.LOW_FLEET_CAPACITY)) {
      result = 2;
    }
    if (hasTrait(GovTraitIds.HIGH_FLEET_CAPACITY)) {
      result = 4;
    }
    return result;
  }

  /**
   * Get Leader pool size where not calculating heirs.
   * When leader pool is full recruit cost goes sky rocket.
   * @return Leader Pool size.
   */
  public int leaderPoolLimit() {
    int result = 8;
    if (hasTrait(GovTraitIds.LOW_LEADER_POOL_SIZE)) {
      result = 6;
    }
    if (hasTrait(GovTraitIds.HIGH_LEADER_POOL_SIZE)) {
      result = 10;
    }
    if (hasTrait(GovTraitIds.VERY_HIGH_LEADER_POOL_SIZE)) {
      result = 12;
    }
    return result;
  }
  /**
   * Get regular leader recruit cost.
   * Actual cost is multiplied by current leader pool size.
   * @return single leader recruit cost
   */
  public int leaderRecruitCost() {
    int result = 10;
    if (hasTrait(GovTraitIds.LOW_LEADER_HIRING_COST)) {
      result = 8;
    }
    if (hasTrait(GovTraitIds.VERY_LOW_LEADER_HIRING_COST)) {
      result = 6;
    }
    if (hasTrait(GovTraitIds.HIGH_LEADER_HIRING_COST)) {
      result = 12;
    }
    return result;
  }

  /**
   * Chance that heir will be born/found, 0 means no heirs.
   * @return Chance for new heir, 0 if government does not have heirs.
   */
  public int getHeirChance() {
    int result = 0;
    if (rulerSelection == RulerSelection.HEIR_TO_THRONE) {
      result = result + 4;
    }
    if (hasTrait(GovTraitIds.CHANCE_FOR_HEIR_BORN)) {
      result = result + 6;
    }
    return result;
  }

  /**
   * Goverment can have heirs.
   * @return True or false
   */
  public boolean hasHeirs() {
    return getHeirChance() > 0;
  }

  /**
   * Is goverment xenophoic or not=
   * @return True or false
   */
  public boolean isXenophopic() {
    return hasTrait(GovTraitIds.XENO_PHOBIC);
  }

  /**
   * If Planet has governor then add one happiness. This happens
   * only if government has governor happiness.
   * @return Happiness bonus
   */
  public int getGovernorHappiness() {
    if (hasTrait(GovTraitIds.GOVERNOR_HAPPINESS)) {
      return 1;
    }
    return 0;
  }
  /**
   * Get ruler reign time.
   * @return Reign time in star years or -1 if life time
   */
  public int reignTime() {
    int result = 40;
    if (hasTrait(GovTraitIds.REIGN_SHORT)) {
      result = 20;
    }
    if (hasTrait(GovTraitIds.REIGN_LONG)) {
      result = 60;
    }
    if (hasTrait(GovTraitIds.REIGN_LIFE_TIME)) {
      result = -1;
    }
    return result;
  }

  /**
   * Is government aggressive? Does it have traits to that benefit for war.
   * @return True or false
   */
  public boolean isAggressive() {
    boolean result = false;
    if (hasTrait(GovTraitIds.WAR_HAPPY)) {
      result = true;
    }
    if (hasTrait(GovTraitIds.ENDURE_WAR)) {
      result = true;
    }
    if (hasTrait(GovTraitIds.TOLERANT_FOR_WAR)) {
      result = true;
    }
    if (hasTrait(GovTraitIds.SINGLE_MIND)) {
      result = true;
    }
    return result;
  }

  /**
   * Get all Government traits.
   * @return Goverment traits.
   */
  public GovTrait[] getTraits() {
    return traits.toArray(new GovTrait[0]);
  }
  /**
   * Calculate number of trait values for Government
   * @return Trait points.
   */
  public int getTraitValue() {
    int result = 0;
    for (GovTrait trait : traits) {
      result = result + trait.getPoints();
    }
    return result;
  }
  /**
   * Get Ruler Title male
   * @return Title
   */
  public String getRulerTitleMale() {
    return rulerTitleMale;
  }

  /**
   * Get Ruler Title female
   * @return Title
   */
  public String getRulerTitleFemale() {
    return rulerTitleFemale;
  }

  /**
   * Set Ruler title male.
   * @param rulerTitleMale Title
   */
  public void setRulerTitleMale(final String rulerTitleMale) {
    this.rulerTitleMale = rulerTitleMale;
  }

  /**
   * Set Ruler title female.
   * @param rulerTitleFemale Title
   */
  public void setRulerTitleFemale(final String rulerTitleFemale) {
    this.rulerTitleFemale = rulerTitleFemale;
  }

  /**
   * Get Government type description
   * @param markDown Is description on Markdown format or HTML.
   * @return Description
   */
  public String getDescription(final boolean markDown) {
    return getDescription(markDown, false);
  }

  /**
   * Get Government type description
   * @param markDown Is description on Markdown format or HTML.
   * @param listTraits true to list traits.
   * @return Description
   */
  public String getDescription(final boolean markDown,
      final boolean listTraits) {
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
    sb.append(" Ruler selection: ");
    sb.append(getRulerSelection());
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
      sb.append(" star years");
      sb.append(lf);
    }
    if (hasHeirs()) {
      sb.append(dot);
      sb.append(" Rulers have heirs");
      sb.append(lf);
    }
    if (isXenophopic()) {
      sb.append(dot);
      sb.append(" Xenophobic");
      sb.append(lf);
    }
    if (LeaderUtility.isPowerHungryReadyForKill(this)) {
      sb.append(dot);
      sb.append(" Possibility to internal power struggle");
      sb.append(lf);
    }
    if (hasTrait(GovTraitIds.LOW_CORRUPTION)) {
      sb.append(dot);
      sb.append(" Low corruption");
      sb.append(lf);
    }
    if (hasTrait(GovTraitIds.HIGH_CORRUPTION)) {
      sb.append(dot);
      sb.append(" High corruption");
      sb.append(lf);
    }
    if (allowArmedFreighters()) {
      sb.append(dot);
      sb.append(" Armed freighters");
      sb.append(lf);
    }
    if (isImmuneToHappiness()) {
      sb.append(dot);
      sb.append(" No effects on happiness nor war fatigue");
      sb.append(lf);
    } else {
      if (getGenericHappiness() != 0) {
        sb.append(dot);
        sb.append(" Generic happiness: ");
        sb.append(getGenericHappiness());
        sb.append(lf);
      }
      if (getGovernorHappiness() != 0) {
        sb.append(dot);
        sb.append(" Happiness from governor: ");
        sb.append(getGovernorHappiness());
        sb.append(lf);
      }
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
    if (getMiningBonus() != 0) {
      sb.append(dot);
      sb.append(" Mining bonus: ");
      sb.append(getMiningBonus());
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
    if (getCultureBonus() != 0) {
      sb.append(dot);
      sb.append(" Culture bonus: ");
      sb.append(getCultureBonus());
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
    if (listTraits) {
      sb.append("\nTraits:");
      sb.append(lf);
      int traitPoints = 0;
      for (GovTrait trait : traits) {
        traitPoints = traitPoints + trait.getPoints();
        sb.append(dot);
        sb.append(" ");
        sb.append(trait.getName());
        sb.append(" - ");
        sb.append(trait.getDescription());
        sb.append(" ");
        sb.append(trait.getPoints());
        sb.append(lf);
      }
      sb.append(lf);
      sb.append("Points: ");
      sb.append(traitPoints);
      sb.append(lf);
    }
    if (!markDown) {
      sb.append("</html>");
    }
    return sb.toString();
  }

}
