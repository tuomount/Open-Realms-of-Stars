package org.openRealmOfStars.player.government;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2021 Tuomo Untinen
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

import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
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
    return DiceGenerator.pickRandom(govs);
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
        GovernmentType.FEUDALISM, GovernmentType.TECHNOCRACY,
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
          GovernmentType.TECHNOCRACY,
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
          GovernmentType.TECHNOCRACY,
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
    if (race == SpaceRace.SYNTHDROIDS) {
      GovernmentType[] governments = {
        GovernmentType.UNION, GovernmentType.DEMOCRACY,
        GovernmentType.FEDERATION, GovernmentType.REPUBLIC,
        GovernmentType.ENTERPRISE, GovernmentType.UTOPIA,
        GovernmentType.COLLECTIVE, GovernmentType.REGIME,
        GovernmentType.AI, GovernmentType.TECHNOCRACY,
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
        GovernmentType.FEUDALISM, GovernmentType.TECHNOCRACY,
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
        GovernmentType.REGIME, GovernmentType.TECHNOCRACY,
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
          GovernmentType.FEDERATION, GovernmentType.TECHNOCRACY,
          GovernmentType.UTOPIA, GovernmentType.COLLECTIVE,
          GovernmentType.EMPIRE, GovernmentType.HEGEMONY,
          GovernmentType.HIERARCHY, GovernmentType.KINGDOM,
          GovernmentType.REGIME, GovernmentType.FEUDALISM
        };
        return governments;
    }
    if (race == SpaceRace.ALTEIRIANS) {
      GovernmentType[] governments = {
          GovernmentType.UNION, GovernmentType.HIVEMIND,
          GovernmentType.REPUBLIC, GovernmentType.GUILD,
          GovernmentType.HEGEMONY, GovernmentType.TECHNOCRACY,
          GovernmentType.NEST, GovernmentType.EMPIRE,
          GovernmentType.HIERARCHY, GovernmentType.HORDE,
          GovernmentType.UTOPIA, GovernmentType.COLLECTIVE,
          GovernmentType.REGIME
        };
        return governments;
    }
    if (race == SpaceRace.SMAUGIRIANS) {
      GovernmentType[] governments = {
          GovernmentType.UNION, GovernmentType.SPACE_PIRATES,
          GovernmentType.REPUBLIC, GovernmentType.GUILD,
          GovernmentType.HEGEMONY, GovernmentType.TECHNOCRACY,
          GovernmentType.SYNDICATE, GovernmentType.EMPIRE,
          GovernmentType.HIERARCHY, GovernmentType.FEDERATION,
          GovernmentType.UTOPIA, GovernmentType.ENTERPRISE,
          GovernmentType.REGIME
        };
        return governments;
    }

    return null;
  }

  /**
   * Get Government Group
   * @param government Government which to get group id
   * @return Government group id.
   */
  public static int getGovernmentGroup(final GovernmentType government) {
    switch (government) {
      default:
      case DEMOCRACY:
      case UNION:
      case FEDERATION:
      case REPUBLIC:
        return 0;
      case GUILD:
      case ENTERPRISE:
      case SYNDICATE:
        return 1;
      case COLLECTIVE:
      case UTOPIA:
      case TECHNOCRACY:
        return 2;
      case HEGEMONY:
      case EMPIRE:
      case KINGDOM:
      case HIERARCHY:
      case HORDE:
      case CLAN:
      case REGIME:
      case FEUDALISM:
      case MECHANICAL_HORDE:
        return 3;
      case NEST:
      case AI:
      case HIVEMIND:
      case SPACE_PIRATES:
        return 4;
    }
  }
}
