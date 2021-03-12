package org.openRealmOfStars.player.government;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018, 2020 Tuomo Untinen
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
* Government Utility
*
*/

public final class GovernmentUtility {

  /**
   * Hiding the constructor
   */
  private GovernmentUtility() {
    // Hiding the constructor
  }

  /**
   * Get Random government type for space race
   * @param race Space race
   * @return GovernmentType
   */
  public static GovernmentType getRandomGovernment(final SpaceRace race) {
    GovernmentType[] govs = getGovernmentsForRace(race);
    int index = DiceGenerator.getRandom(govs.length - 1);
    return govs[index];
  }
  /**
   * Get government type by index
   * @param index Index to fetch
   * @return GovernmentType
   */
  public static GovernmentType getGovernmentByIndex(final int index) {
    GovernmentType[] govs = GovernmentType.values();
    if (index >= 0 && index < govs.length) {
      return govs[index];
    }
    throw new IllegalArgumentException("Unknown government type index:"
      + index);
  }
  /**
   * Get the GovernmentType for certain space race
   * @param race Space Race
   * @return GovernmentTypes as arrays for space race
   */
  public static GovernmentType[] getGovernmentsForRace(
      final SpaceRace race) {
    if (race == SpaceRace.HUMAN) {
      GovernmentType[] governments = {
        GovernmentType.UNION, GovernmentType.DEMOCRACY,
        GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
        GovernmentType.ENTERPRISE, GovernmentType.UTOPIA,
        GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
        GovernmentType.FEUDALISM,
        GovernmentType.HIERARCHY, GovernmentType.KINGDOM
      };
      return governments;
    }
    if (race == SpaceRace.GREYANS) {
      GovernmentType[] governments = {
          GovernmentType.UNION, GovernmentType.DEMOCRACY,
          GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
          GovernmentType.ENTERPRISE, GovernmentType.COLLECTIVE,
          GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
          GovernmentType.HIERARCHY, GovernmentType.KINGDOM
        };
        return governments;
    }
    if (race == SpaceRace.CENTAURS) {
      GovernmentType[] governments = {
          GovernmentType.UNION, GovernmentType.DEMOCRACY,
          GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
          GovernmentType.ENTERPRISE, GovernmentType.COLLECTIVE,
          GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
          GovernmentType.HIERARCHY, GovernmentType.KINGDOM,
          GovernmentType.REGIME, GovernmentType.FEUDALISM,

        };
        return governments;
    }
    if (race == SpaceRace.MECHIONS) {
      GovernmentType[] governments = {
        GovernmentType.UNION, GovernmentType.DEMOCRACY,
        GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
        GovernmentType.ENTERPRISE, GovernmentType.EMPIRE,
        GovernmentType.COLLECTIVE, GovernmentType.REGIME,
        GovernmentType.AI, GovernmentType.MECHANICAL_HORDE,
        GovernmentType.HEGEMONY, GovernmentType.HIERARCHY
      };
      return governments;
    }
    if (race == SpaceRace.SPORKS) {
      GovernmentType[] governments = {
        GovernmentType.UNION, GovernmentType.REGIME,
        GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
        GovernmentType.CLAN, GovernmentType.HORDE,
        GovernmentType.FEUDALISM,
        GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
        GovernmentType.HIERARCHY, GovernmentType.KINGDOM
      };
      return governments;
    }
    if (race == SpaceRace.HOMARIANS) {
      GovernmentType[] governments = {
        GovernmentType.UNION, GovernmentType.UTOPIA,
        GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
        GovernmentType.CLAN, GovernmentType.HORDE,
        GovernmentType.FEUDALISM,
        GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
        GovernmentType.NEST, GovernmentType.KINGDOM
      };
      return governments;
    }
    if (race == SpaceRace.MOTHOIDS) {
      GovernmentType[] governments = {
        GovernmentType.UNION, GovernmentType.UTOPIA,
        GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
        GovernmentType.CLAN, GovernmentType.HORDE,
        GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
        GovernmentType.NEST, GovernmentType.KINGDOM,
        GovernmentType.FEUDALISM, GovernmentType.HIVEMIND
      };
      return governments;
    }
    if (race == SpaceRace.SCAURIANS) {
      GovernmentType[] governments = {
        GovernmentType.UNION, GovernmentType.UTOPIA,
        GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
        GovernmentType.ENTERPRISE, GovernmentType.GUILD,
        GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
        GovernmentType.HIERARCHY, GovernmentType.KINGDOM,
        GovernmentType.DEMOCRACY
      };
      return governments;
    }
    if (race == SpaceRace.TEUTHIDAES) {
      GovernmentType[] governments = {
        GovernmentType.UNION, GovernmentType.REGIME,
        GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
        GovernmentType.ENTERPRISE, GovernmentType.HORDE,
        GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
        GovernmentType.FEUDALISM,
        GovernmentType.HIERARCHY, GovernmentType.KINGDOM,
        GovernmentType.DEMOCRACY
      };
      return governments;
    }
    if (race == SpaceRace.CHIRALOIDS) {
      GovernmentType[] governments = {
        GovernmentType.UNION, GovernmentType.REGIME,
        GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
        GovernmentType.CLAN, GovernmentType.HIVEMIND,
        GovernmentType.COLLECTIVE, GovernmentType.FEUDALISM,
        GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
        GovernmentType.HIERARCHY, GovernmentType.KINGDOM
      };
      return governments;
    }
    if (race == SpaceRace.REBORGIANS) {
      GovernmentType[] governments = {
        GovernmentType.REGIME, GovernmentType.DEMOCRACY,
        GovernmentType.FEDERATION, GovernmentType.HIVEMIND,
        GovernmentType.COLLECTIVE, GovernmentType.EMPIRE,
        GovernmentType.AI, GovernmentType.MECHANICAL_HORDE,
        GovernmentType.HEGEMONY, GovernmentType.HIERARCHY
      };
      return governments;
    }
    if (race == SpaceRace.LITHORIANS) {
      GovernmentType[] governments = {
          GovernmentType.UNION, GovernmentType.HIVEMIND,
          GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
          GovernmentType.UTOPIA, GovernmentType.COLLECTIVE,
          GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
          GovernmentType.HIERARCHY, GovernmentType.KINGDOM,
          GovernmentType.REGIME, GovernmentType.FEUDALISM
        };
        return governments;
    }
    return null;
  }
}
