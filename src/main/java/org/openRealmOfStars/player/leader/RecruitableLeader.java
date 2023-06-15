package org.openRealmOfStars.player.leader;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2023 Tuomo Untinen
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
* Recruitable Leader information
*
*/
public class RecruitableLeader {

  /**
   * Recruit cost in credits.
   */
  private int recruitCost;

  /**
   * Original leader
   */
  private Leader leader;
  /**
   * True if recruite decreases population
   */
  private boolean requirePopulation;

  /**
   * Recruit leader constructor
   * @param leader Original leader
   * @param cost Recruit cost
   * @param usePopulation Boolean for if population is used at recruit.
   */
  public RecruitableLeader(final Leader leader, final int cost,
      final boolean usePopulation) {
    this.leader = leader;
    recruitCost = cost;
    requirePopulation = usePopulation;
  }

  /**
   * Get recruit cost.
   * @return Recruit cost
   */
  public int getCost() {
    return recruitCost;
  }

  /**
   * Boolean flag if population is used on recruit.
   * @return True or false
   */
  public boolean usePopulation() {
    return requirePopulation;
  }

  /**
   * Get actual leader.
   * @return Leader
   */
  public Leader getLeader() {
    return leader;
  }
}
