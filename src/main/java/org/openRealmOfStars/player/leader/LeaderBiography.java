package org.openRealmOfStars.player.leader;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2020-2023 Tuomo Untinen
 * Copyright (C) 2023 BottledByte
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

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * Contains methods to work with leader's biography
 * and other Leader flavor information.
 */
public final class LeaderBiography {

  /** Hidden constructor. */
  private LeaderBiography() {
  }

  /**
   * Get Main job description for leader.
   * @param leader Leader for getting the main job.
   * @param government Government type for getting proper title for ruler.
   * @return Job title
   */
  private static String getMainJob(final Leader leader,
      final GovernmentType government) {
    int ruler = leader.getStats().getStat(StatType.RULER_REIGN_LENGTH);
    int governor = leader.getStats().getStat(StatType.GOVERNOR_LENGTH);
    int commander = leader.getStats().getStat(StatType.COMMANDER_LENGTH);
    int avg = (ruler + governor + commander) / 3;

    if (ruler > avg) {
      return RulerUtility.getRulerTitle(leader.getGender(), government);
    }
    if (avg == 0) {
      return "jobless";
    }
    if (governor > avg) {
      return "Governor";
    }
    if (commander > avg) {
      return "Commander";
    }

    return "between jobs";
  }

  /**
   * Get textual description where leader is known about.
   * @param leader Leader.
   * @return String
   */
  private static String getBestKnown(final Leader leader) {
    StringBuilder sb = new StringBuilder();
    boolean and = false;
    boolean noMore = false;
    final var stats = leader.getStats();

    int numberOfBattle = stats.getStat(StatType.NUMBER_OF_BATTLES);
    int numberOfAnomalies = stats.getStat(StatType.NUMBER_OF_ANOMALY);
    int trades = stats.getStat(StatType.NUMBER_OF_TRADES);
    int privateering = stats.getStat(StatType.NUMBER_OF_PRIVATEERING);
    int numberOfPlanets = stats.getStat(StatType.NUMBER_OF_PLANETS_EXPLORED);
    int commanderAvg = (numberOfAnomalies + numberOfBattle + trades
        + privateering + numberOfPlanets) / 5;

    int numberOfBuildings = stats.getStat(StatType.NUMBER_OF_BUILDINGS_BUILT);
    int numberOfShips = stats.getStat(StatType.NUMBER_OF_SHIPS_BUILT);
    int populationGrowth = stats.getStat(StatType.POPULATION_GROWTH);
    int governorAvg = (numberOfBuildings + numberOfShips
        + populationGrowth) / 3;

    int warDeclarations = stats.getStat(StatType.WAR_DECLARATIONS);
    int diplomaticTrades = stats.getStat(StatType.DIPLOMATIC_TRADE);
    int rulerAvg = (warDeclarations + diplomaticTrades) / 2;

    if (rulerAvg >= commanderAvg && rulerAvg > 0) {
      if (warDeclarations > diplomaticTrades) {
        sb.append("war declarations");
        and = true;
      } else {
        sb.append("diplomatic trades");
        and = true;
      }
    }
    if (governorAvg >= commanderAvg) {
      if (numberOfShips > numberOfBuildings
          && numberOfShips > populationGrowth) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("ship building");
        and = true;
      }
      if (!noMore && numberOfBuildings > numberOfShips
          && numberOfBuildings > populationGrowth) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("building projects");
        and = true;
      }
      if (!noMore && populationGrowth > numberOfShips
          && populationGrowth > numberOfBuildings) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("population growth");
        and = true;
      }
    }
    if (commanderAvg >= governorAvg) {
      if (!noMore && numberOfBattle > numberOfAnomalies
          && numberOfBattle > privateering
          && numberOfBattle > trades
          && numberOfBattle > numberOfPlanets) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("space battles");
        if (stats.getStat(StatType.NUMBER_OF_PIRATE_BATTLES) >= numberOfBattle
            / 2) {
          sb.append(" against space pirates");
        }
        and = true;
      }
      if (!noMore && numberOfAnomalies > numberOfBattle
          && numberOfAnomalies > privateering
          && numberOfAnomalies > trades
          && numberOfAnomalies > numberOfPlanets) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("exploring space anomalies");
        and = true;
      }
      if (!noMore && trades > numberOfBattle
          && trades > privateering
          && trades > numberOfAnomalies
          && trades > numberOfPlanets) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("trades");
        and = true;
      }
      if (!noMore && numberOfPlanets > numberOfBattle
          && numberOfPlanets > trades
          && numberOfPlanets > numberOfAnomalies
          && numberOfPlanets > privateering) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("boldly exploring planets");
        and = true;
      }
      if (!noMore && privateering > numberOfBattle
          && privateering > trades
          && privateering > numberOfAnomalies
          && privateering > numberOfPlanets) {
        if (and) {
          noMore = true;
          sb.append(" and ");
        }
        sb.append("space pirating");
        and = true;
      }
    }
    return sb.toString();
  }

  /**
   * Create Bio for leader
   * @param leader Leader whom to create bio.
   * @param info PlayerInfo
   * @return Bio as a String.
   */
  public static String createBioForLeader(final Leader leader,
      final PlayerInfo info) {
    boolean living = true;
    boolean young = false;
    final var stats = leader.getStats();

    if (leader.getAge() < 35) {
      young = true;
    }
    if (leader.getJob() == Job.DEAD) {
      living = false;
      young = false;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(leader.getName());
    if (leader.getJob() == Job.TOO_YOUNG) {
      sb.append(" is still growing up and will achieve many things later.");
      return sb.toString();
    }
    if (living) {
      sb.append(" is ");
    } else {
      sb.append(" was ");
    }
    int ruler = stats.getStat(StatType.RULER_REIGN_LENGTH);
    int governor = stats.getStat(StatType.GOVERNOR_LENGTH);
    int commander = stats.getStat(StatType.COMMANDER_LENGTH);
    if (ruler > 0 && governor == 0 && commander == 0) {
      sb.append("the ");
    } else if (ruler == 0 && governor > 0 && commander == 0
        || ruler == 0 && governor == 0 && commander > 0) {
      sb.append("working as ");
    } else if (ruler != 0 || governor != 0 || commander != 0) {
      sb.append("mostly working as ");
    }
    sb.append(getMainJob(leader, info.getGovernment()));
    if (living) {
      sb.append(". Currently ");
      sb.append(leader.getName());
      sb.append(" is ");
      if (leader.getJob() == Job.RULER) {
        sb.append(RulerUtility.getRulerTitle(leader.getGender(),
            info.getGovernment()));
      } else if (leader.getJob() == Job.COMMANDER) {
        sb.append(leader.getMilitaryRank().toString());
      } else {
        sb.append(leader.getJob().toString().toLowerCase());
      }
      sb.append(". ");
    } else {
      sb.append(". ");
    }
    sb.append(leader.getTitle());
    String known = getBestKnown(leader);
    if (known.isEmpty()) {
      if (young && living) {
        sb.append(" is still young and is able to achieve many things. ");
      }
      if (!young && living) {
        sb.append(" is still live and ");
        if (leader.getRace().isRoboticRace()) {
          sb.append("functional and");
        } else if (leader.hasPerk(Perk.HEALTHY)) {
          sb.append("has healthy lifestyle and");
        } else if (!leader.hasPerk(Perk.ADDICTED)) {
          sb.append("healthy and");
        }
        sb.append(" is able to achieve things. ");
      }
      if (!living) {
        sb.append(" has passed away with respect. ");
      }
    } else {
      if (living) {
        sb.append(" is known for ");
      } else {
        sb.append(" will be remembered for ");
      }
      sb.append(known);
      sb.append(". ");
    }
    if (stats.getStat(StatType.NUMBER_OF_ESPIONAGE) > 0) {
      sb.append(leader.getName());
      if (living) {
        sb.append(" is suspected to be spy. ");
      } else {
        sb.append(" was suspected to be spy. ");
      }
    }
    if (stats.getStat(StatType.NUMBER_OF_JAIL_TIME) > 0) {
      sb.append(leader.getName());
      if (living) {
        sb.append(" has been in jail. ");
      } else {
        sb.append(" has been sentenced to jail ");
        sb.append(stats.getStat(StatType.NUMBER_OF_JAIL_TIME));
        sb.append(" times. ");
      }
    }
    Attitude attitude = LeaderUtility.getRulerAttitude(leader);
    if (attitude != null) {
      sb.append(leader.getCallName());
      if (living) {
        sb.append(" is known to be ");
      } else {
        sb.append(" was known to be ");
      }
      switch (attitude) {
        case AGGRESSIVE: {
          sb.append("aggressive");
          break;
        }
        case BACKSTABBING: {
          sb.append("untrustworthy");
          break;
        }
        case DIPLOMATIC: {
          sb.append("diplomatic");
          break;
        }
        case EXPANSIONIST: {
          sb.append("adventurous");
          break;
        }
        case LOGICAL: {
          sb.append("very logical");
          break;
        }
        case MERCHANTICAL: {
          sb.append("merchantical");
          break;
        }
        case MILITARISTIC: {
          sb.append("militaristic");
          break;
        }
        case PEACEFUL: {
          sb.append("calm and peaceful");
          break;
        }
        default:
        case SCIENTIFIC: {
          sb.append("scientific");
          break;
        }
      }
      if (leader.hasPerk(Perk.WAR_HERO)) {
        sb.append(" and war hero");
      }
      sb.append(". ");
    }
    if (stats.getStat(StatType.RESEARCH_ARTIFACTS) > 0) {
      sb.append(leader.getName());
      boolean famous = false;
      if (stats.getStat(StatType.RESEARCH_ARTIFACTS) > 2) {
        famous = true;
      }
      if (living) {
        sb.append(" is ");
      } else {
        sb.append(" was ");
      }
      if (famous) {
        sb.append("famous ancient artifact researcher. ");
      } else {
        sb.append("interested in ancient artifact research. ");
      }
    }
    return sb.toString();
  }

  /**
   * Get Reason for gaining perk for leader.
   * @param leader Leader who is gain perk
   * @param perk Perk which was gained.
   * @return Explanation string
   */
  public static String getReasonForPerk(final Leader leader, final Perk perk) {
    final var name = leader.getName();
    final var callName = leader.getCallName();
    final var heShe = leader.getGender().getHeShe();
    final var hisHer = leader.getGender().getHisHer();

    StringBuilder sb = new StringBuilder();
    switch (perk) {
      default: {
        sb.append(name);
        sb.append(" gained perk called ");
        sb.append(perk.getName());
        break;
      }
      case ACADEMIC: {
        sb.append(name);
        sb.append(" has higher level of studies and learn how to be academic.");
        break;
      }
      case ADDICTED: {
        sb.append(name);
        sb.append(" has hooked on substance and ");
        sb.append(heShe);
        sb.append(" has to use it.");
        break;
      }
      case AGGRESSIVE: {
        sb.append(name);
        sb.append(" has become aggressive due ");
        sb.append(hisHer);
        sb.append(" stress levels.");
        break;
      }
      case AGRICULTURAL: {
        sb.append(name);
        sb.append(" has started to grown ");
        sb.append(hisHer);
        sb.append(" own farm and thus become more interested in agriculture.");
        break;
      }
      case ARCHAEOLOGIST: {
        sb.append(name);
        sb.append(" has researched ancient artifacts and ");
        sb.append(heShe);
        sb.append(" has done couple of new findings.");
        break;
      }
      case ARTISTIC: {
        sb.append(name);
        sb.append(" has started to ");
        switch (DiceGenerator.getRandom(3)) {
          default:
          case 0: {
            sb.append("paint ");
            break;
          }
          case 1: {
            sb.append("sculpture ");
            break;
          }
          case 2: {
            sb.append("compose music ");
            break;
          }
          case 3: {
            sb.append("write novels ");
            break;
          }
        }
        sb.append("and ");
        sb.append(hisHer);
        sb.append(" interest towards art has been increased.");
        break;
      }
      case CHARISMATIC: {
        sb.append(name);
        sb.append(" has learn how to behave charismatically.");
        break;
      }
      case CHATTERBOX: {
        sb.append(name);
        sb.append(" feels compulsary need to brag ");
        sb.append(hisHer);
        sb.append(" achievements and always speaks a too much.");
        break;
      }
      case COMBAT_MASTER: {
        sb.append(name);
        sb.append(" has improved how to shoot accurately. ");
        break;
      }
      case COMBAT_TACTICIAN: {
        sb.append(name);
        sb.append(" has learned how to react with ");
        sb.append(hisHer);
        sb.append(" fleet in space combat faster than before.");
        break;
      }
      case CONVICT: {
        sb.append(name);
        sb.append(" has been in prison. ");
        break;
      }
      case CORRUPTED: {
        sb.append(name);
        sb.append(" takes realm's credits into ");
        sb.append(hisHer);
        sb.append(" pockets and ");
        sb.append(heShe);
        sb.append(" thinks that is the right thing to do.");
        break;
      }
      case COUNTER_AGENT: {
        sb.append(name);
        sb.append(" has learn how to spot cloaked fleets by noticing little"
            + " things in scanner views.");
        break;
      }
      case CRUEL: {
        sb.append(name);
        sb.append(" has killed too young heir. What cruel thing to do!");
        break;
      }
      case DIPLOMATIC: {
        sb.append(name);
        sb.append(" has developped skill to be a diplomatic.");
        break;
      }
      case DISCIPLINE: {
        sb.append(name);
        sb.append(" has learned to be strict commander during wartime.");
        break;
      }
      case EXPLORER: {
        sb.append(name);
        sb.append(" is very keen to explore and"
            + " pushes the engines a bit over the limit.");
        break;
      }
      case FTL_ENGINEER: {
        sb.append(name);
        sb.append(" wants to get moving fast and"
            + " pushes the engines a bit over the limit.");
        break;
      }
      case GOOD_LEADER: {
        sb.append(name);
        sb.append(" has learned new people skill:"
            + " how to treat every one equally and fairly.");
        break;
      }
      case HEALTHY: {
        sb.append(name);
        sb.append(" has started to live more healthier life than before.");
        break;
      }
      case INCOMPETENT: {
        sb.append(name);
        sb.append(" has fallen down and hit ");
        sb.append(hisHer);
        sb.append(" head very badly. Things aren't the same anymore...");
        break;
      }
      case INDUSTRIAL: {
        sb.append(name);
        sb.append(" has shown interest in factory automation ");
        sb.append(" and seems like ");
        sb.append(heShe);
        sb.append(" can get the factories running much more efficiently.");
        break;
      }
      case LOGICAL: {
        sb.append(name);
        sb.append(" has starting to behave logically. This is ");
        sb.append(hisHer);
        sb.append(" new way to get things done.");
        break;
      }
      case MAD: {
        sb.append(name);
        sb.append(" had very bad disease and ");
        sb.append(heShe);
        sb.append(" became mad afterwards.");
        break;
      }
      case MASTER_ENGINEER: {
        sb.append(name);
        sb.append(" has deeper knowledge how to overload ship's"
            + " components with less danger.");
        break;
      }
      case MERCHANT: {
        sb.append(name);
        sb.append(" has interest in trading and thus makes better deals.");
        break;
      }
      case MICRO_MANAGER: {
        sb.append(callName);
        sb.append(" has habit that everything needs to be done by ");
        sb.append(hisHer);
        sb.append(" way and this kind of management does not end well.");
        break;
      }
      case MILITARISTIC: {
        sb.append(name);
        sb.append(" has been interesting in military tactics and can handle"
            + " larger number of fleets.");
        break;
      }
      case MINER: {
        sb.append(name);
        sb.append(" has been interested in minerals and stone and therefore ");
        sb.append(heShe);
        sb.append(" is good spotting great mining areas.");
        break;
      }
      case PACIFIST: {
        sb.append(callName);
        sb.append(" thinks that war is never a solution and there must be"
            + " a peaceful way to solve things.");
        break;
      }
      case PEACEFUL: {
        sb.append(callName);
        sb.append(" has learned how to calm ");
        sb.append(hisHer);
        sb.append(" nerves. This gives a cool impression.");
        break;
      }
      case POWER_HUNGRY: {
        sb.append(callName);
        sb.append(" is graving to be a ruler and ");
        sb.append(heShe);
        sb.append(" is willing to do lot for it, some of those can be"
            + " questionable.");
        break;
      }
      case REPULSIVE: {
        sb.append(name);
        sb.append(" has turned into selfish and arrogant that ");
        sb.append(" doesn't seem to care about others.");
        break;
      }
      case SCANNER_EXPERT: {
        sb.append(name);
        sb.append(" has learned how scanners work and ");
        sb.append(heShe);
        sb.append(" knows how to manually tweak in order get them"
            + " perform better.");
        break;
      }
      case SCIENTIST: {
        sb.append(name);
        sb.append(" has published scientific paper during ");
        sb.append(hisHer);
        sb.append(" spare time. Impressive.");
        break;
      }
      case SECRET_AGENT: {
        sb.append(name);
        sb.append(" has learned how to tune cloaking ");
        sb.append(" device to perform slightly better.");
        break;
      }
      case SKILLFUL: {
        sb.append(name);
        sb.append(" has become skillfull, ");
        sb.append(heShe);
        sb.append(" is able to pick any task and perform it well.");
        break;
      }
      case SLOW_LEARNER: {
        sb.append(name);
        sb.append(" is feeling that learning new things is a burden and ");
        sb.append(heShe);
        sb.append(" hasn't any motivation to improve situation.");
        break;
      }
      case SPY_MASTER: {
        sb.append(name);
        sb.append(" has watched couple of old spy movies and ");
        sb.append(" learned new tricks for espionage.");
        break;
      }
      case STUPID: {
        sb.append(name);
        sb.append(" has got too many hits to ");
        sb.append(hisHer);
        sb.append(" head and become simply stupid.");
        break;
      }
      case TRADER: {
        sb.append(name);
        sb.append(" has got interested in trading ");
        sb.append(" and gains a bit of extra while doing it.");
        break;
      }
      case WARLORD: {
        sb.append(name);
        sb.append(" has learned motivate population during war ");
        sb.append(" so that they work more like a team.");
        break;
      }
      case WEAK_LEADER: {
        sb.append(name);
        sb.append(" has started to be affraid of war and this is bad ");
        sb.append(" since fear spreads specially during war times.");
        break;
      }
      case WEALTHY: {
        sb.append(name);
        sb.append(" has got some personal extra credits. ");
        break;
      }
      case TREKKER: {
        sb.append(name);
        sb.append(" has show great interest for exploring uncolonized"
            + " planets. ");
        break;
      }
      case NEGOTIATOR: {
        sb.append(name);
        sb.append(" had many diplomatic meetings so, ");
        sb.append(heShe);
        sb.append(" is able get bit of extra information.");
        break;
      }
      case CARTOGRAPHER: {
        sb.append(name);
        sb.append(" has great interest for charting unknown planets in"
            + " the vastness of space.");
        break;
      }
      case WAR_HERO: {
        sb.append(name);
        sb.append(" has been in great battle and survived.");
        sb.append(leader.getGender().getHeShe());
        sb.append(" become a war hero.");
        break;
      }
    }
    return sb.toString();
  }
}
