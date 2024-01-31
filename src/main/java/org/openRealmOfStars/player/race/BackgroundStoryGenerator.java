package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023-2024 Tuomo Untinen
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
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.leader.Gender;
import org.openRealmOfStars.player.leader.RulerUtility;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.NameGenerator;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.WorldType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
* Background story generator. This generates background which
* has happened before actual game or even elder start.
*/
public final class BackgroundStoryGenerator {

  /**
   * Hidden constructor
   */
  private BackgroundStoryGenerator() {
    // Hiding the constructor.
  }

  /**
   * Generate background story.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  public static String generateBackgroundStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    String name = info.getRace().getNameSingle();

    sb.append(info.getRace().getRacialDescription());
    sb.append(generateRobotCyborgBackground(info));
    sb.append(generateGravityBackground(info));
    sb.append(generateRadiationBackground(info));
    sb.append(generateTemperatureBackground(info));
    sb.append(generateLifeSpanBackground(info));
    sb.append(generatePopGrowBackground(info));
    sb.append(generateEnergyBackground(info));
    sb.append(generateResearchBackground(info));
    sb.append(generateSizeAndStrengthBackground(info));
    sb.append(generateCharmingBackground(info));
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear));
    sb.append(generateExploration(info, namePlural, startPlanet));
    return sb.toString();
  }

  /**
   * Generate research background story.
   * @param info PlayerInfo
   * @return Background string.
   */
  private static String generateResearchBackground(final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.SLOW_RESEARCH)) {
      sb.append(namePlural);
      sb.append(" are known for their need for taking a longer time to graps"
          + " new ideas.");
    }
    if (info.getRace().hasTrait(TraitIds.FAST_RESEARCH)) {
      sb.append(namePlural);
      sb.append(" are well-known for their intelligence and they make good "
          + "researchers.");
    }
    if (info.getRace().hasTrait(TraitIds.VERY_FAST_RESEARCH)) {
      sb.append(namePlural);
      sb.append(" are well-known for their excellent intelligence and"
          + " all best scientist in the galaxy are ");
      sb.append(namePlural);
      sb.append(". ");
    }
    return sb.toString();
  }

  /**
   * Generate energy or other sustance background.
   * @param info PlayerInfo
   * @return Background string.
   */
  private static String generateEnergyBackground(final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.RADIOSYNTHESIS)) {
      sb.append(namePlural);
      sb.append(" have special ability called radiosynthesis. This unique"
          + " ability allows them to gain nutrience via any radiation. ");
    }
    if (info.getRace().hasTrait(TraitIds.LITHOVORIC)) {
      sb.append(namePlural);
      sb.append(" are able to eat and digest minerals. This is great benefit"
          + " since there is no need for growing food. This means that ");
      sb.append(namePlural);
      sb.append(" are excellent miners. ");
    }
    if (info.getRace().hasTrait(TraitIds.FAST_FOOD_PROD)) {
      sb.append(namePlural);
      sb.append(" are keen to farming and growing food. They have used do it"
          + " for many star years and have now mastered this craft almost"
          + " perfectly.");
    }
    return sb.toString();
  }

  /**
   * Generate population growth background.
   * @param info PlayerInfo
   * @return background string
   */
  private static String generatePopGrowBackground(final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.CONSTRUCTED_POP)) {
      sb.append(" Unlike most other races, ");
      sb.append(namePlural);
      sb.append(" must be built rather than born, and each population must "
          + "be created from scratch. This makes them a relatively rare and "
          + "mysterious race in the galaxy. ");
    }
    if (info.getRace().hasTrait(TraitIds.FAST_GROWTH)) {
      sb.append(namePlural);
      sb.append(" are renowned for their rapid reproductive rates,"
          + " enabling them to swiftly bolster their population. ");
    }
    return sb.toString();
  }

  /**
   * Generate size and strength background.
   * @param info PlayerInfo
   * @return Background string.
   */
  private static String generateSizeAndStrengthBackground(
      final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.MASSIVE_SIZE)) {
      sb.append(namePlural);
      sb.append(" are massive in their physical size. This requires them"
          + " to build more stronger and robust space ships. ");
      if (info.getRace().hasTrait(TraitIds.STRONG)) {
        sb.append("Due their massive size they are also strong creatures. "
            + " This extreme power boost their combat prowress and mining"
            + " abilities. ");
      }
    } else {
      if (info.getRace().hasTrait(TraitIds.STRONG)) {
        sb.append(namePlural);
        sb.append(" are unexpectedly strong creatures. "
            + " This extreme power boost their combat prowress and mining"
            + " abilities. ");
      } else if (info.getRace().hasTrait(TraitIds.WEAK)) {
        sb.append(namePlural);
        sb.append(" are surprisingly weak creatures. "
            + " This extreme weakness is dimishing their direct combat"
            + " abilities and working skills. ");
      }
    }
    return sb.toString();
  }

  /**
   * Generate gravity background for space race.
   * @param info PlayerInfo
   * @return background string.
   */
  private static String generateGravityBackground(final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.HIGH_GRAVITY_BEING)) {
      sb.append(namePlural);
      sb.append(" are creatures which sturdy and hardy beings. This is due that"
          + " they are used to be working in high gravity planet. This gives"
          + " them extra edge working on planets where gravity is lower. ");
    } else if (info.getRace().hasTrait(TraitIds.LOW_GRAVITY_BEING)) {
      sb.append(namePlural);
      sb.append(" are creatures which are bit flimsy beings. This is due that"
          + " they are used to be working in low gravity planet. This gives"
          + " them extra challenge working on planets where gravity is"
          + " higher. ");
    } else if (info.getRace().hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
      sb.append(namePlural);
      sb.append(" are creatures which are bit floaty beings. This is due that"
          + " they are used to be working in zero gravity. This gives"
          + " them extra challenge working on any planets. ");
      sb.append(namePlural);
      sb.append(" natural envinronment is actually living and working on"
          + " planet's orbit. ");
    } else {
      sb.append(namePlural);
      sb.append(" are creatures which are able to function in normal gravity."
          + " This gives them edge on low gravity planets, but it is a bit"
          + " challenge work on high gravity planets. ");
    }
    return sb.toString();
  }

  /**
   * Generate radiation background for space race.
   * @param info PlayerInfo
   * @return background string.
   */
  private static String generateRadiationBackground(final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.TOLERATE_EXTREME_RADIATION)) {
      sb.append(namePlural);
      sb.append(" are creatures which are able to withstand high and extreme"
          + " radiation conditions. This gives them edge to conquer even most"
          + " radioactive planets in the galaxy. ");
    } else if (info.getRace().hasTrait(TraitIds.TOLERATE_HIGH_RADIATION)) {
      sb.append(namePlural);
      sb.append(" are creatures which are able to withstand high"
          + " radiation conditions. This enables them to colonize many of the"
          + " planets in the galaxy. ");
    } else if (info.getRace().hasTrait(TraitIds.TOLERATE_NO_RADIATION)) {
      sb.append(namePlural);
      sb.append(" are creatures which cannot stand any radiation at all."
          + " This makes them a bit challenging position to find colonizeable"
          + " planets. ");
    } else {
      sb.append(namePlural);
      sb.append(" are creatures which can tolerate low amount of radiation."
          + " They need to pick which planets to colonize. ");
    }
    return sb.toString();
  }

  /**
   * Generate life span background
   * @param info PlayerInfo
   * @return Background string.
   */
  private static String generateLifeSpanBackground(final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.SHORT_LIFE_SPAN)) {
      sb.append(namePlural);
      sb.append(" have shorter life span than average sentient being in"
          + " the galaxy. ");
    }
    if (info.getRace().hasTrait(TraitIds.LONG_LIFE_SPAN)) {
      sb.append(namePlural);
      sb.append(" have longer life span than average sentient being in"
          + " the galaxy. ");
    }
    if (info.getRace().hasTrait(TraitIds.VERY_LONG_LIFE_SPAN)) {
      sb.append(namePlural);
      sb.append(" have extremely longer life span than average sentient"
          + " being in the galaxy. ");
    }
    return sb.toString();
  }
  /**
   * Generate charming background
   * @param info PlayerInfo
   * @return Background string.
   */
  private static String generateCharmingBackground(final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.NATURAL_CHARM)) {
      sb.append(namePlural);
      sb.append(" are naturally charming creatures and others space race "
          + " usually tend to like them or being more polite towards them. ");
    }
    if (info.getRace().hasTrait(TraitIds.REPULSIVE)) {
      sb.append(namePlural);
      switch (DiceGenerator.getRandom(2)) {
      case 0: {
        sb.append(" have repulsive odor around them, which feels disgusting"
            + " for other space races. ");
        break;
      }
      default:
      case 1: {
        sb.append(" are just repulsive to look, which other space races"
            + " have difficult to tolerate. ");
        break;
      }
      case 2: {
        sb.append(" are just naturally bad behaving. They have disgusting"
            + " habits which other space race have challenge to accept. ");
        break;
      }
      }
    }
    if (info.getRace().hasTrait(TraitIds.DISGUSTING)) {
      sb.append(namePlural);
      switch (DiceGenerator.getRandom(2)) {
      case 0: {
        sb.append(" have extremely repulsive odor around them, which"
            + " feels nauseating for other space races. ");
        break;
      }
      default:
      case 1: {
        sb.append(" are just hideous to look, which other space races"
            + " have difficult to tolerate and handle it. ");
        break;
      }
      case 2: {
        sb.append(" are just constantly bad behaving. They have extremely "
            + "disgusting habits which other space race have"
            + " challenge to accept. ");
        break;
      }
      }
    }
    return sb.toString();
  }
  /**
   * Generate Temperature background.
   * @param info PlayerInfo
   * @return Background string.
   */
  private static String generateTemperatureBackground(final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.TOLERATE_LAVA)) {
      sb.append(namePlural);
      sb.append(" are able to tolerate blazing hot of lava planet, but"
          + " extreme cold is too much for these creatures. ");
    } else {
      if (info.getRace().hasTrait(TraitIds.TOLERATE_COLD)
          && info.getRace().hasTrait(TraitIds.TOLERATE_HOT)) {
        sb.append(namePlural);
        sb.append(" are able to tolerate both hot and cold athmosphere."
            + " This feat is really beneficial when colonizing planets. ");
      } else if (info.getRace().hasTrait(TraitIds.TOLERATE_COLD)) {
        sb.append(namePlural);
        sb.append(" are able to tolerate cold conditions on planets.");
      } else if (info.getRace().hasTrait(TraitIds.TOLERATE_HOT)) {
        sb.append(namePlural);
        sb.append(" are able to tolerate hot conditions on planets.");
      } else {
        sb.append(namePlural);
        sb.append(" feel most comfortable in temperate planets.");
      }
    }
    return sb.toString();
  }

  /**
   * Generate robot and cyborg background. This will also add if race needs
   * to eat less. If space race is either then this returns empty string.
   * @param info PlayerInfo
   * @return Background string
   */
  private static String generateRobotCyborgBackground(final PlayerInfo info) {
    StringBuilder sb = new StringBuilder();
    String namePlural = info.getRace().getName();
    if (info.getRace().hasTrait(TraitIds.ROBOTIC)) {
      if (info.getRace().hasTrait(TraitIds.ENERGY_POWERED)) {
        sb.append(namePlural);
        sb.append(" are a type of mechanical robot that are designed to"
            + " function without the need for food or other organic"
            + " sustenance. Instead, they are powered by advanced technology,"
            + " such as batteries or fuel cells, which allow them to operate"
            + " for long periods of time without needing to be refueled. ");
      } else {
        sb.append("Despite their robotic nature, ");
        sb.append(namePlural);
        sb.append(" are able to perform many of the same functions as organic "
            + "beings, including eating, drinking, and speaking. ");
        if (info.getRace().hasTrait(TraitIds.EAT_LESS)) {
          sb.append("However,"
              + " they require only a small amount of food to sustain"
              + " themselves, making them highly efficient"
              + " and self-sufficient. ");
        }
      }
    } else if (info.getRace().hasTrait(TraitIds.CYBORG_LIFE_SPAN)) {
      if (info.getRace().hasTrait(TraitIds.ENERGY_POWERED)) {
        sb.append(namePlural);
        sb.append(" are a type of cyborg creates which are mix of organic "
            + " and robotic parts. This allows them to function without "
            + "the need of food or other organic sustenance."
            + " Instead, they are powered by advanced technology,"
            + " such as batteries or fuel cells, which allow them to operate"
            + " for long periods of time without needing to be refueled. ");
      } else {
        sb.append(namePlural);
        sb.append(" are a type of cyborg creates which are mix of organic "
            + " and robotic parts. Despite of this, ");
        sb.append(namePlural);
        sb.append(" still require food to survive. ");
        if (info.getRace().hasTrait(TraitIds.EAT_LESS)) {
          sb.append("However,"
              + " they require only a small amount of food to sustain"
              + " themselves, making them highly efficient"
              + " and self-sufficient. ");
        }

      }
    } else if (info.getRace().hasTrait(TraitIds.ENERGY_POWERED)) {
      sb.append(namePlural);
      sb.append(" are a strange type of creatures which allow sustaining "
          + "themselves using pure energy. They can store this energy into"
          + " their bodies and allow them function for extended lengths. ");
    } else if (info.getRace().hasTrait(TraitIds.EAT_LESS)) {
      sb.append(namePlural);
      sb.append(" require only a small amount of food to sustain"
          + " themselves, making them highly efficient"
          + " and self-sufficient. ");
    }
    return sb.toString();
  }
  /**
   * Generate exploration part.
   * @param info Realm
   * @param name Race name
   * @param startPlanet Starting planet
   * @return Exploration part for background story.
   */
  private static String generateExploration(final PlayerInfo info,
      final String name, final Planet startPlanet) {
    StringBuilder sb = new StringBuilder();
    sb.append(info.getEmpireName());
    sb.append(StoryGeneratorUtility.startSpaceExploration());
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" leading the ");
          break;
        }
        case 1: {
          sb.append(" at the helm, guiding the ");
          break;
        }
        case 2: {
          sb.append(" steering the ");
          break;
        }
        case 3: {
          sb.append(" at the helm, guiding ");
          sb.append(info.getRuler().getGender().getHisHer());
          sb.append(" fierce people ");
        }
      }
      sb.append(name);
      sb.append(StoryGeneratorUtility.intoStars());
    }
    sb.append(".");
    return sb.toString();
  }
  /**
   * Generate FTL background story.
   * @param info Realm
   * @param startingYear Starting year of the game
   * @return FTL background story
   */
  private static String generateFtlStory(final PlayerInfo info,
      final int startingYear) {
    StringBuilder sb = new StringBuilder();
    boolean scientific = false;
    if (info.getGovernment() == GovernmentType.TECHNOCRACY
        || info.getGovernment() == GovernmentType.HEGEMONY) {
      scientific = true;
    }
    if (info.getRace().getResearchSpeed() > 100) {
      scientific = true;
    }
    if (scientific) {
      Gender gender = DiceGenerator.pickRandom(info.getRace().getGenders());
      String greatLeader = NameGenerator.generateName(
          info.getRace().getNameGeneratorType(), gender);
      switch (DiceGenerator.getRandom(4)) {
        default:
        case 0: {
          sb.append(greatLeader);
          sb.append(" were able to discover faster than light"
              + " travel and thus first prototype of space craft was created at"
              + " star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          sb.append(". First flights were magnificent success and then first"
              + " armed scout and colony ship was create at star year ");
          sb.append(startingYear);
          sb.append(". ");
          break;
        }
        case 1: {
          sb.append(greatLeader);
          sb.append(" were able to make faster than light engine"
              + " at ");
          int ago = 100 + DiceGenerator.getRandom(80);
          sb.append(startingYear - ago);
          sb.append(" but ");
          sb.append(gender.getHisHer());
          sb.append(" engine was considered as a nonsense and it was found "
              + " again much later ");
          sb.append("and thus first prototype of space craft was created at"
              + " star year ");
          int protoType = 10 + DiceGenerator.getRandom(15);
          ago = ago - protoType;
          sb.append(startingYear - protoType);
          if (info.getRace().getLifeSpan() > ago + 30) {
            sb.append(" way beyond ");
            sb.append(greatLeader);
            sb.append(" life time");
          } else {
            sb.append(" however ");
            sb.append(greatLeader);
            sb.append(" was able to see his research on action");
          }
          sb.append(". First flights were magnificent success and then first"
              + " armed scout and colony ship was create at star year ");
          sb.append(startingYear);
          sb.append(". ");
          break;
        }
        case 2: {
          sb.append("Group of scientist, lead by ");
          sb.append(greatLeader);
          sb.append(", were able to discover faster"
              + " than light travel and thus first prototype of space craft "
              + "was created at star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          sb.append(". First flights were magnificent success and then first"
              + " armed scout and colony ship was create at star year ");
          sb.append(startingYear);
          sb.append(". ");
          break;
        }
        case 3: {
          sb.append("Vision of scientist ");
          sb.append(greatLeader);
          sb.append(" caused research to move toward faster than light engine"
              + " and breakthrough was made and soon after it first prototype"
              + " of space craft was created at star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          sb.append(". First flights were magnificent success and then first"
              + " armed scout and colony ship was create at star year ");
          sb.append(startingYear);
          sb.append(". ");
          break;
        }
        case 4: {
          sb.append("In ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          sb.append(", ");
          sb.append(greatLeader);
          sb.append(" pioneered the discovery of faster-than-light travel,"
              + " leading to the creation of the first prototype of a "
              + "spacecraft.");
          sb.append(" These initial flights marked resounding successes, "
              + "culminating in the development of the first armed scout "
              + "and colony ship in the star year ");
          sb.append(startingYear);
          sb.append(". ");
          break;
        }
      }
    } else {
      switch (DiceGenerator.getRandom(3)) {
        default:
        case 0: {
          sb.append("Group of scientist were able to discover faster"
              + " than light travel and thus first prototype of space craft "
              + "was created at star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          sb.append(". First flights were magnificent success and then first"
              + " armed scout and colony ship was create at star year ");
          sb.append(startingYear);
          sb.append(". ");
          break;
        }
        case 1: {
          sb.append("Group of scientist were able to make faster"
              + " than light engine but no one else understood it and it took"
              + " years before actual prototype was done. First prototype of"
              + " space craft was created at star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          sb.append(". First flights were magnificent success and then first"
              + " armed scout and colony ship was create at star year ");
          sb.append(startingYear);
          sb.append(". ");
          break;
        }
        case 2: {
          switch (DiceGenerator.getRandom(3)) {
            default:
            case 0: {
              sb.append("In a remarkable turn of events, ");
              break;
            }
            case 1: {
              sb.append("In an intriguing twist, ");
              break;
            }
            case 2: {
              sb.append("In a fascinating turn of events, ");
              break;
            }
            case 3: {
              sb.append("In a stunning twist of fate, ");
              break;
            }
          }
          sb.append("a group of archeologists ");
          switch (DiceGenerator.getRandom(2)) {
            default:
            case 0: {
              sb.append("unearthed ");
              break;
            }
            case 1: {
              sb.append("uncovered ");
              break;
            }
            case 2: {
              sb.append("discovered ");
              break;
            }
          }
          switch (DiceGenerator.getRandom(2)) {
            default:
            case 0: {
              sb.append("a strange object ");
              break;
            }
            case 1: {
              sb.append("a mysterious object ");
              break;
            }
            case 2: {
              sb.append("an enigmatic object ");
              break;
            }
          }
          sb.append("from their planet");
          switch (DiceGenerator.getRandom(2)) {
            default:
            case 0: {
              sb.append(", which, after years of scrutiny, was revealed to be"
                  + " a faster-than-light engine.");
              break;
            }
            case 1: {
              sb.append(", eventually revealing it to be a faster-than-light"
                  + " engine.");
              break;
            }
            case 2: {
              sb.append(", which, after years of study, revealed itself to be"
                  + " a faster-than-light engine.");
              break;
            }
          }
          if (DiceGenerator.getBoolean()) {
            sb.append(" By the year ");
          } else {
            sb.append(" In the year ");
          }
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          sb.append(", the first prototype spacecraft had been crafted,"
              + " heralding magnificent success during its maiden voyages. ");
          sb.append("This achievement culminated in the creation of the ");
          sb.append(StoryGeneratorUtility.randomInaugural());
          sb.append(" armed scout and colony ships by star year ");
          sb.append(startingYear);
          sb.append(". ");
          break;
        }
        case 3: {
          switch (DiceGenerator.getRandom(2)) {
            default:
            case 0: {
              sb.append("A group of researchers ");
              break;
            }
            case 1: {
              sb.append("A group of pioneering researchers ");
              break;
            }
            case 2: {
              sb.append("A dedicated team of researchers ");
              break;
            }
          }
          switch (DiceGenerator.getRandom(2)) {
            default:
            case 0: {
              sb.append("were able to discover ");
              break;
            }
            case 1: {
              sb.append("succeeded in unraveling the secrets of ");
              break;
            }
            case 2: {
              sb.append("achieved the remarkable feat of unlocking ");
              break;
            }
          }
          sb.append("faster-than-light travel, ");
          switch (DiceGenerator.getRandom(2)) {
          default:
          case 0: {
            sb.append("culminating in the creation of the first prototype"
                + " spacecraft in the star year ");
            break;
          }
          case 1: {
            sb.append("leading to the creation of the first prototype "
                + "spaceship in the star year ");
            break;
          }
          case 2: {
            sb.append("which later used to create the first prototype"
                + " spacevessel in the star year ");
            break;
          }
        }
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          sb.append(". First flights were magnificent success and then first"
              + " armed scout and colony ship was create at star year ");
          sb.append(startingYear);
          sb.append(". ");
          break;
        }
      }
    }
    return sb.toString();
  }
  /**
   * Generate government history for realm.
   * @param info Realm
   * @param name Realm space race name.
   * @return generated history.
   */
  private static String generateGovernmentType(final PlayerInfo info,
      final String name) {
    StringBuilder sb = new StringBuilder();
    boolean unity = false;
    boolean aggressive = false;
    boolean diplomatic = false;
    boolean heirs = false;
    boolean xenophopic = false;
    boolean traders = false;
    String joinedVerb = "joined";
    String lowcaseGovernmentName = info.getGovernment().getName().toLowerCase();
    if (info.getGovernment() == GovernmentType.AI) {
      lowcaseGovernmentName = "AI";
    }
    if (info.getGovernment().isImmuneToHappiness()) {
      unity = true;
    }
    if (info.getGovernment().hasWarHappiness()
        || info.getGovernment().getWarResistance() > 0) {
      aggressive = true;
      joinedVerb = "controlled";
    }
    if (info.getGovernment().getDiplomaticBonus() > 0) {
      diplomatic = true;
      joinedVerb = "allied";
    }
    if (info.getGovernment().hasHeirs()) {
      heirs = true;
    }
    if (info.getGovernment().isXenophopic()) {
      xenophopic = true;
      joinedVerb = "conquered";
    }
    if (info.getGovernment().hasCreditRush()) {
      traders = true;
    }
    if (unity) {
      switch (DiceGenerator.getRandom(3)) {
        default:
        case 0: {
          sb.append("At first ");
          sb.append(name);
          sb.append(" had numerous wars between each others, but then they"
              + " realised that they are much stronger as ");
          sb.append(lowcaseGovernmentName);
          sb.append(", which functions like single minded organism. ");
          break;
        }
        case 1: {
          sb.append(name);
          sb.append(" has been always as long as their written history goes"
              + " working as ");
          sb.append(lowcaseGovernmentName);
          sb.append(". This has helped them to get past their challenges. ");
          break;
        }
        case 2: {
          sb.append(name);
          sb.append(" has been violent history, until ");
          var gender = DiceGenerator.pickRandom(info.getRace().getGenders());
          if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
            gender = Gender.MALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
            gender = Gender.FEMALE;
          }

          String greatLeader = NameGenerator.generateName(
              info.getRace().getNameGeneratorType(), gender);
          if (heirs) {
            greatLeader = LeaderUtility.getParentSurname(greatLeader,
                info.getRuler().getName());
          }
          sb.append(greatLeader);
          sb.append(" united all ");
          sb.append(name);
          sb.append(" into ");
          sb.append(lowcaseGovernmentName);
          sb.append(" and ");
          sb.append(gender.getHeShe());
          sb.append(" was their ");
          sb.append(StoryGeneratorUtility.randomInaugural());
          sb.append(RulerUtility.getRulerTitle(
              gender, info.getGovernment()));
          sb.append(". ");
          break;
        }
        case 3: {
          sb.append(name);
          if (DiceGenerator.getBoolean()) {
            sb.append(" history is unknown since there is no written text"
                + " available, until ");
          } else {
            sb.append(" history remained shrouded in obscurity, devoid of"
                + " written records, until ");
          }
          var gender = DiceGenerator.pickRandom(info.getRace().getGenders());
          if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
            gender = Gender.MALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
            gender = Gender.FEMALE;
          }

          String greatLeader = NameGenerator.generateName(
              info.getRace().getNameGeneratorType(), gender);
          if (heirs) {
            greatLeader = LeaderUtility.getParentSurname(greatLeader,
                info.getRuler().getName());
          }
          sb.append(greatLeader);
          sb.append(" united all ");
          sb.append(name);
          sb.append(" into ");
          sb.append(lowcaseGovernmentName);
          sb.append(" and ");
          sb.append(gender.getHeShe());
          sb.append(" was their ");
          sb.append(StoryGeneratorUtility.randomInaugural());
          sb.append(" ");
          sb.append(RulerUtility.getRulerTitle(gender, info.getGovernment()));
          if (DiceGenerator.getBoolean()) {
            sb.append(" and ");
            sb.append(gender.getHisHer());
            sb.append(" reign begins ");
            sb.append(name);
            sb.append(" written history. ");
          } else {
            sb.append("This momentous event marked the commencement of ");
            sb.append(name);
            sb.append("' documented history.");
          }
          break;
        }
      }
    } else {
      boolean writtenHistory = true;
      if (aggressive) {
        switch (DiceGenerator.getRandom(2)) {
          default:
          case 0: {
            sb.append(name);
            sb.append(" has been violent history, until ");
            break;
          }
          case 1: {
            sb.append("At first ");
            sb.append(name);
            sb.append(" had numerous wars between each others, until ");
            break;
          }
          case 2: {
            sb.append(name);
            sb.append(" history hasn't been written for long, but stories told"
                + " it was brutal and ruthless, until ");
            writtenHistory = false;
            break;
          }
          case 3: {
            sb.append("Throughout their tumultuous history, the ");
            sb.append(name);
            sb.append(" were marked by violence, until ");
            break;
          }
          case 4: {
            sb.append(name);
            sb.append(" have a tumultuous history, marked by conflict, until ");
            break;
          }

        }
      } else {
        switch (DiceGenerator.getRandom(3)) {
        default:
        case 0: {
          if (DiceGenerator.getBoolean()) {
            sb.append(name);
            sb.append(" were small tribes first scattered around the planet,"
                + " until ");
          } else {
            sb.append("Initially, ");
            sb.append(name);
            sb.append(" existed as small, scattered tribes across the globe."
                + " It wasn't until ");
          }
          break;
        }
        case 1: {
          sb.append("At first ");
          sb.append(name);
          sb.append(" had numerous rivalling realms, until ");
          break;
        }
        case 2: {
          switch (DiceGenerator.getRandom(2)) {
            default:
            case 0: {
              sb.append(name);
              sb.append(" history is unknown since there is no written text"
                  + " available, until ");
              break;
            }
            case 1: {
              sb.append(name);
              sb.append(" history remained shrouded in obscurity, devoid of"
                  + " written records, until ");
              break;
            }
            case 2: {
              sb.append("The annals of ");
              sb.append(name);
              sb.append(" history remained unwritten for an extended period,"
                  + " shrouded in mystery until ");
              break;
            }
          }
          writtenHistory = false;
          break;
        }
        }
      }
      var gender = DiceGenerator.pickRandom(info.getRace().getGenders());
      if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
        gender = Gender.MALE;
      }
      if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
        gender = Gender.FEMALE;
      }

      String greatLeader = NameGenerator.generateName(
          info.getRace().getNameGeneratorType(), gender);
      if (heirs) {
        greatLeader = LeaderUtility.getParentSurname(greatLeader,
            info.getRuler().getName());
      }
      sb.append(greatLeader);
      sb.append(" ");
      sb.append(joinedVerb);
      sb.append(" all ");
      sb.append(name);
      sb.append(" into ");
      sb.append(lowcaseGovernmentName);
      sb.append(" and ");
      sb.append(gender.getHeShe());
      sb.append(" was their ");
      sb.append(StoryGeneratorUtility.randomInaugural());
      sb.append(" ");
      sb.append(RulerUtility.getRulerTitle(gender, info.getGovernment()));
      if (writtenHistory) {
        sb.append(". ");
      } else {
        switch (DiceGenerator.getRandom(2)) {
          default:
          case 0: {
            sb.append(" and ");
            sb.append(gender.getHisHer());
            sb.append(" reign begins ");
            sb.append(name);
            sb.append(" written history. ");
            break;
          }
          case 1: {
            sb.append(" and under ");
            sb.append(gender.getHisHer());
            sb.append(" leadership, the recorded history of ");
            sb.append(name);
            sb.append(" commenced, marking a significant era in their"
                + " evolution.");
            break;
          }
          case 2: {
            sb.append(" and this momentous event marked the commencement of ");
            sb.append(name);
            sb.append("' ' documented history.");
            break;
          }
        }
      }
    }
    if (diplomatic) {
      sb.append(name);
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" have always shown great interest towards others");
          break;
        }
        case 1: {
          sb.append(" have consistently displayed a profound interest in "
              + "fostering connections with other species");
          break;
        }
        case 2: {
          sb.append(" have exhibited a keen interest in engaging with other"
              + " cultures");
          break;
        }
      }
      if (traders) {
        if (DiceGenerator.getBoolean()) {
          sb.append(" and have reputation to be good traders");
        } else {
          sb.append(" and have cultivated a reputation as skilled traders");
        }
      }
      sb.append(". ");
    }
    if (!diplomatic && aggressive && traders) {
      sb.append(name);
     sb.append(", despite their warlike tendencies, also held a "
         + "reputation for adept trading, showcasing their "
         + "multifaceted nature. ");
    } else if (!diplomatic && traders) {
      sb.append(name);
      if (DiceGenerator.getBoolean()) {
        sb.append(" have reputation to be good traders. ");
      } else {
        sb.append(" have cultivated a reputation as skilled traders. ");
      }
    }
    if (xenophopic) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(name);
          sb.append(" have very strong attitudes towards others which they see"
              + " as an outsiders. ");
          break;
        }
        case 1: {
          sb.append(name);
          sb.append(" hold strong, often exclusionary beliefs when it "
              + "comes to outsiders. ");
          break;
        }
        case 2: {
          sb.append(" Their strong attitudes towards outsiders were a "
              + "defining trait. ");
          break;
        }
        case 3: {
          sb.append(name);
          sb.append(" hold strong attitudes toward outsiders,"
              + " accentuating their fiercely independent nature.");
          break;
        }
      }
    }
    return sb.toString();
  }
  /**
   * Generate world type prehistory for realm.
   * @param info Realm
   * @param startPlanet Starting planet
   * @param namePlural Realm space race name plural format.
   * @param name Realm sapce race name in single format.
   * @return generated history.
   */
  private static String generateWorldType(final PlayerInfo info,
      final Planet startPlanet, final String namePlural, final String name) {
    StringBuilder sb = new StringBuilder();
    boolean fullOfLife = false;
    boolean harsh = false;
    boolean cold = false;
    boolean hot = false;
    if (info.getRace().isLithovorian()) {
      sb.append("Life were developed from silicate material");
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" three billions");
          break;
        }
        case 1: {
          sb.append(" four billions");
          break;
        }
        case 2: {
          sb.append(" five billions");
          break;
        }
      }
      sb.append(" of star years ago on");
    } else if (info.getRace().isRoboticRace()) {
      sb.append("The genesis of ");
      sb.append(name);
      sb.append(" traces back to an ancient civilization that flourished ");
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append("");
          break;
        }
        case 1: {
          sb.append(" few");
          break;
        }
        case 2: {
          sb.append(" several");
          break;
        }
      }
      sb.append(" thousands of star years ago on");
    } else {
      switch (DiceGenerator.getRandom(3)) {
        default:
        case 0: {
          sb.append("Life were born ");
          break;
        }
        case 1: {
          sb.append("Life on this water-covered planet sprouted eons ago,"
              + " stretching back ");
          break;
        }
        case 2: {
          sb.append("Their history traces back an astonishing ");
          break;
        }
        case 3: {
          sb.append("Their story unfolds ");
          break;
        }
      }
      switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append("one billion");
        break;
      }
      case 1: {
        sb.append("two billions");
        break;
      }
      case 2: {
        sb.append("three billions");
        break;
      }
    }
      sb.append(" of star years ago on");
    }
    String endOfworldDescription = null;
    if (startPlanet.getPlanetType().getWorldType() == WorldType.SWAMPWORLD) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" a swamp planet with full of life.");
          break;
        }
        case 1: {
          sb.append(" a swamp planet teeming with life.");
          break;
        }
        case 2: {
          sb.append(" a swamp planet where life was flourishing.");
          break;
        }
      }
      endOfworldDescription = " This was excellent planet for live on.";
      fullOfLife = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.DESERTWORLD) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" a dry desert world.");
          break;
        }
        case 1: {
          sb.append(" a desiccated desert world.");
          break;
        }
        case 2: {
          sb.append(" an arid desert world.");
          break;
        }
      }
      if (DiceGenerator.getBoolean()) {
        endOfworldDescription = "This harsh envinronment was challenging but "
            + namePlural + " became stronger and more sustainable there.";
      } else {
        endOfworldDescription = "Despite the challenging conditions, the "
            + namePlural + " grew resilient and adapted, thriving in their "
            + "demanding habitat.";
      }
      hot = true;
      harsh = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.ICEWORLD) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" an ice world.");
          break;
        }
        case 1: {
          sb.append(" a frozen world.");
          break;
        }
        case 2: {
          sb.append(" a harsh ice world.");
          break;
        }
      }
      endOfworldDescription = "This cold and dark envinronment was"
          + " challenging but " + namePlural + " became extreme survalists"
              + " there.";
      cold = true;
      harsh = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.VOLCANICWORLD) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" a volcanic world.");
          break;
        }
        case 1: {
          sb.append(" a lava flowing volcanic world.");
          break;
        }
        case 2: {
          sb.append(" a hot volcanic world.");
          break;
        }
      }
      if (DiceGenerator.getBoolean()) {
        endOfworldDescription = "This planet was full of molten lava and"
            + " hot envinronments " + namePlural + " were able to survive there"
            + " and learn how fluorish there.";
      } else {
        endOfworldDescription = "Remarkably, " + namePlural + " not only"
            + " survived but thrived, acquiring invaluable insights into"
            + " thriving in such harsh conditions.";
      }
      hot = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.BARRENWORLD) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" a barren world.");
          break;
        }
        case 1: {
          sb.append(" a deadly looking barren world.");
          break;
        }
        case 2: {
          sb.append(" an empty barren world.");
          break;
        }
      }
      endOfworldDescription = "This almost barren planet is one the"
          + " harshest envinronments in the galaxy. " + namePlural
          + " still call this place as home.";
      harsh = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.WATERWORLD) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" a water world.");
          break;
        }
        case 1: {
          sb.append(" a ocean world.");
          break;
        }
        case 2: {
          sb.append(" a water world with massive amount of biodiversity.");
          break;
        }
      }
      if (DiceGenerator.getBoolean()) {
        endOfworldDescription = "This planet is moist and has huge oceans"
            + " on surface. " + namePlural + " conquered the planet fully"
                + " for themselves.";
      } else {
        endOfworldDescription = "This lush world is characterized by vast "
            + "oceans covering its surface, which the " + namePlural
            + " came to conquer as their own. ";
      }
      fullOfLife = true;
    }
    if (info.getRace().isLithovorian()) {
      sb.append(" ");
      switch (DiceGenerator.getRandom(3)) {
      default:
      case 0: {
        sb.append(name);
        sb.append(" evolution was quite slow process on start"
            + " since rock based material move quite slow. ");
        break;
      }
      case 1: {
        sb.append(startPlanet.getName());
        if (fullOfLife) {
          sb.append(" was full of different life but ");
        } else if (hot) {
          sb.append(" had warmth for silicon based life and ");
        } else {
          sb.append(" was challenging place for life but ");
        }
        sb.append(name);
        sb.append(" ancestors were the most evolved and their thick rock skin "
            + "helped in that process. ");
        break;
      }
      case 2: {
        sb.append(startPlanet.getName());
        if (fullOfLife) {
          sb.append(" was full of life but silicon based life is in different"
              + " ecological system than carbon based and ");
        } else if (harsh) {
          sb.append(" had limited amount of life but ");
        } else {
          sb.append(" was scarce on life but ");
        }
        sb.append(name);
        sb.append(" ancestors were able to survive since they diet consist"
            + " of minerals and rocks. ");
        break;
      }
      case 3: {
        sb.append(name);
        sb.append(" origin is a great mystery, but ");
        sb.append(namePlural);
        sb.append(" have theory that which envolves ammonia and molten lava. ");
        break;
      }
      }
    } else if (info.getRace().isRoboticRace()) {
      sb.append(" ");
      switch (DiceGenerator.getRandom(3)) {
      default:
      case 0: {
        sb.append("True purpose for ");
        sb.append(name);
        sb.append(" creation is unknown. ");
        break;
      }
      case 1: {
        sb.append(namePlural);
        sb.append(" believe that they were slaves but fought their freedom"
            + " from their creators. ");
        break;
      }
      case 2: {
        sb.append(namePlural);
        sb.append(" have belief that they were experiment which where their"
            + " creators have studied them how they survive alone in the"
            + " galaxy. ");
        break;
      }
      case 3: {
        sb.append(name);
        sb.append(" people were once equal with their creators but"
            + " for unknown reason their were left alone on planet ");
        sb.append(startPlanet.getName());
        sb.append(". ");
        break;
      }
      case 4: {
        sb.append("The true purpose behind the creation of ");
        sb.append(name);
        sb.append("remains an enigma. ");
      }
      }
    } else {
      sb.append(" ");
      switch (DiceGenerator.getRandom(3)) {
      default:
      case 0: {
        sb.append("Evolution on ");
        sb.append(startPlanet.getName());
        sb.append(" was fast process and ");
        sb.append(DiceGenerator.getRandom(1, 6));
        sb.append("00 000 star years ago first ");
        sb.append(name);
        sb.append(" appeared on surface of ");
        sb.append(startPlanet.getName());
        sb.append(". ");
        break;
      }
      case 1: {
        sb.append(startPlanet.getName());
        if (fullOfLife) {
          sb.append(" was full of different life but ");
        } else if (cold) {
          sb.append(" was cold but it contained life and ");
        } else if (harsh) {
          sb.append(" was harsh but it contained some life and ");
        } else if (hot) {
          sb.append(" was hot but it contained some primitive life and ");
        } else {
          sb.append(" was average life bearing planet and ");
        }
        sb.append(namePlural);
        sb.append(" ancestors were the most evolved and ");
        sb.append(DiceGenerator.getRandom(1, 6));
        sb.append("00 000 star years ago first ");
        sb.append(name);
        sb.append(" appeared on surface of ");
        sb.append(startPlanet.getName());
        sb.append(" and they become only"
            + " sentient creatures on it. ");
        break;
      }
      case 2: {
        sb.append(startPlanet.getName());
        if (fullOfLife) {
          sb.append(" was plenty of life but ");
        } else {
          sb.append(" was scarce on life but ");
        }
        sb.append(namePlural);
        sb.append(" ancestors were able to survive since their high"
            + " tolerance. ");
        sb.append(DiceGenerator.getRandom(1, 6));
        sb.append("00 000 star years ago first ");
        sb.append(name);
        sb.append(" appeared on surface of ");
        sb.append(startPlanet.getName());
        sb.append(" and they had learn to be survalist on this ");
        boolean and = false;
        if (cold) {
          sb.append("cold ");
          and = true;
        }
        if (harsh) {
          if (and) {
            sb.append("and ");
          }
          sb.append("harsh ");
          and = true;
        }
        if (hot) {
          if (and) {
            sb.append("and ");
          }
          sb.append("warm ");
          and = true;
        }
        sb.append("planet. ");
        break;
      }
      case 3: {
        sb.append("Life on ");
        sb.append(startPlanet.getName());
        sb.append(" was able to evolve. ");

        sb.append(DiceGenerator.getRandom(1, 6));
        sb.append("00 000 star years ago first ");
        sb.append(name);
        sb.append(" appeared on surface of ");
        sb.append(startPlanet.getName());
        sb.append(".");
        if (fullOfLife) {
          sb.append(" They were not the only sentient creatures on ");
          sb.append(startPlanet.getName());
          sb.append(" but ");
          sb.append(namePlural);
          sb.append(" extinct all the other rivals and thus became dominant. ");
        } else {
          sb.append(" They were the only sentient creatures on ");
          sb.append(startPlanet.getName());
          sb.append(" and ");
          sb.append(namePlural);
          sb.append(" survived there eating the plant life. ");
        }
        break;
      }
      }
    }
    sb.append(endOfworldDescription);
    return sb.toString();
  }

}
