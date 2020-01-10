package org.openRealmOfStars.player.leader;

import java.util.ArrayList;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SocialSystem;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* Leader Uitlity
*
*/
public final class LeaderUtility {

  /**
   * Special level for marking start ruler for realm.
   */
  public static final int LEVEL_START_RULER = -1;

  /**
   * Good perk type
   */
  public static final int PERK_TYPE_GOOD = 0;
  /**
   * Bad perk type
   */
  public static final int PERK_TYPE_BAD = 1;
  /**
   * Hidden constructor.
   */
  private LeaderUtility() {
    // Hiding the constructor.
  }

  /**
   * Create new leader based on 3 parameters.
   * @param info Realm who is creating new leader.
   * @param planet Home planet for leader.
   * @param level Leader leve. Note this can be LEVEL_START_RULER for
   *        realm starting ruler.
   * @return Leader
   */
  public static Leader createLeader(final PlayerInfo info, final Planet planet,
      final int level) {
    Gender gender = Gender.NONE;
    if (info.getRace() != SpaceRace.MECHIONS) {
      if (level == LEVEL_START_RULER) {
        if (info.getGovernment() == GovernmentType.EMPIRE
            || info.getGovernment() == GovernmentType.KINGDOM) {
          if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
            gender = Gender.MALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
            gender = Gender.FEMALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.EQUAL) {
            gender = Gender.getRandom();
          }
        }
      } else {
        gender = Gender.getRandom();
      }
    }
    Leader leader = new Leader(NameGenerator.generateName(info.getRace(),
        gender));
    leader.setGender(gender);
    leader.setRace(info.getRace());
    leader.setExperience(0);
    leader.setHomeworld(planet.getName());
    leader.setJob(Job.UNASSIGNED);
    if (level == LEVEL_START_RULER) {
      leader.setLevel(1);
      leader.setAge(30 + DiceGenerator.getRandom(20));
      if (leader.getRace().getLifeSpan() < 80) {
        // Low life span starts about 10 years younger as starting ruler
        leader.setAge(25 + DiceGenerator.getRandom(10));
      }
      if (leader.getRace() == SpaceRace.MECHIONS) {
        leader.setAge(4 + DiceGenerator.getRandom(10));
      }
    } else {
      leader.setLevel(level);
      leader.setAge(23 + DiceGenerator.getRandom(15));
      if (leader.getRace() == SpaceRace.MECHIONS) {
        // Mechion leaders are always almost brand new ones.
        leader.setAge(1);
      }
      for (int i = 0; i < level; i++) {
        Perk[] newPerks = getNewPerks(leader, PERK_TYPE_GOOD);
        int index = DiceGenerator.getRandom(newPerks.length - 1);
        leader.getPerkList().add(newPerks[index]);
        if (DiceGenerator.getRandom(99)  < 10) {
          newPerks = getNewPerks(leader, PERK_TYPE_BAD);
          index = DiceGenerator.getRandom(newPerks.length - 1);
          leader.getPerkList().add(newPerks[index]);
        }
      }
    }
    return leader;
  }

  /**
   * Get list of new perks that leader is missing.
   * @param leader Leader whose perks to check.
   * @param perkType Perk type good, bad, ruler etc.
   * @return Array of new perks.
   */
  public static Perk[] getNewPerks(final Leader leader, final int perkType) {
    ArrayList<Perk> list = new ArrayList<>();
    for (Perk perk : Perk.values()) {
      if (perkType == PERK_TYPE_GOOD && perk.isBadPerk()) {
        continue;
      }
      if (perkType == PERK_TYPE_BAD && !perk.isBadPerk()) {
        continue;
      }
      boolean alreadyHas = false;
      for (Perk leaderPerk : leader.getPerkList()) {
        if (perk == leaderPerk) {
          alreadyHas = true;
          break;
        }
      }
      if (!alreadyHas) {
        list.add(perk);
      }
    }
    return list.toArray(new Perk[list.size()]);
  }
}
