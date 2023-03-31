package org.openRealmOfStars.player.SpaceRace;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.leader.Gender;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.NameGenerator;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.WorldType;
import org.openRealmOfStars.utilities.DiceGenerator;

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
* Background story generator. This generates background which
* has happened before actual game or even elder start.
*
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
    if (info.getRace() == SpaceRace.HUMAN) {
      sb.append(generateHumanStory(info, startPlanet, startingYear));
    }
    return sb.toString();
  }

  /**
   * Generate background story for humans.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateHumanStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = "Humans";
    if (info.getEmpireName().contains("Terran")) {
      name = "Terrans";
    }
    if (startPlanet.getName().startsWith("Earth")) {
      sb.append(name);
      sb.append(" are the humans from the Earth. ");
      sb.append(name);
      sb.append(" are seen as "
          + "average in most physical and mental abilities. ");
    } else {
      sb.append(name);
      sb.append(" are an extraterrestrial beings that "
          + "are similar to humans on Earth. Like their Earthly counterparts,");
      sb.append(name);
      sb.append(" are often portrayed as being average in most"
          + " physical and mental abilities. ");
    }
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append(generateGovernmentType(info, name));
    sb.append("Group of scientiest were able to discover faster than light"
        + " travel and thus first prototype of space craft was created at"
        + " star year ");
    sb.append(startingYear - 10);
    sb.append(". First flights were magnificent success and then first armed "
        + "scout and colony ship was create at star year ");
    sb.append(startingYear);
    sb.append(". ");
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    sb.append(".");
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
          sb.append(info.getGovernment().getName().toLowerCase());
          sb.append(", which functions like single minded organism. ");
          break;
        }
        case 1: {
          sb.append(name);
          sb.append(" has been always as long as their written history goes"
              + " working as ");
          sb.append(info.getGovernment().getName().toLowerCase());
          sb.append(". This has helped them to get past their challenges. ");
          break;
        }
        case 2: {
          sb.append(name);
          sb.append(" has been violent history, until ");
          Gender gender = Gender.getRandom();
          if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
            gender = Gender.MALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
            gender = Gender.FEMALE;
          }
          if (info.getRace() == SpaceRace.MECHIONS
              || info.getRace() == SpaceRace.REBORGIANS) {
            gender = Gender.NONE;
          }
          String greatLeader = NameGenerator.generateName(info.getRace(),
              gender);
          if (heirs) {
            greatLeader = LeaderUtility.getParentSurname(greatLeader,
                info.getRuler().getName());
          }
          sb.append(greatLeader);
          sb.append(" united all ");
          sb.append(name);
          sb.append(" into ");
          sb.append(info.getGovernment().getName().toLowerCase());
          sb.append(" and ");
          sb.append(gender.getHeShe());
          sb.append(" was their first ");
          sb.append(LeaderUtility.getRulerTitle(gender, info.getGovernment()));
          sb.append(". ");
          break;
        }
        case 3: {
          sb.append(name);
          sb.append(" history is unknown since there is no written text"
              + " available, until ");
          Gender gender = Gender.getRandom();
          if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
            gender = Gender.MALE;
          }
          if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
            gender = Gender.FEMALE;
          }
          if (info.getRace() == SpaceRace.MECHIONS
              || info.getRace() == SpaceRace.REBORGIANS) {
            gender = Gender.NONE;
          }
          String greatLeader = NameGenerator.generateName(info.getRace(),
              gender);
          if (heirs) {
            greatLeader = LeaderUtility.getParentSurname(greatLeader,
                info.getRuler().getName());
          }
          sb.append(greatLeader);
          sb.append(" united all ");
          sb.append(name);
          sb.append(" into ");
          sb.append(info.getGovernment().getName().toLowerCase());
          sb.append(" and ");
          sb.append(gender.getHeShe());
          sb.append(" was their first ");
          sb.append(LeaderUtility.getRulerTitle(gender, info.getGovernment()));
          sb.append(" and ");
          sb.append(gender.getHisHer());
          sb.append(" reign begins ");
          sb.append(name);
          sb.append(" written history. ");
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
        }
      } else {
        switch (DiceGenerator.getRandom(3)) {
        default:
        case 0: {
          sb.append(name);
          sb.append(" were small tribes first scattered around the planet,"
              + " until ");
          break;
        }
        case 1: {
          sb.append("At first ");
          sb.append(name);
          sb.append(" had numerous rivalling realms, until ");
          break;
        }
        case 2: {
          sb.append(name);
          sb.append(" history hasn't been written for long and it was"
              + " long unknown, until ");
          writtenHistory = false;
          break;
        }
        }
      }
      Gender gender = Gender.getRandom();
      if (info.getRace().getSocialSystem() == SocialSystem.PATRIARCHY) {
        gender = Gender.MALE;
      }
      if (info.getRace().getSocialSystem() == SocialSystem.MATRIARCHY) {
        gender = Gender.FEMALE;
      }
      if (info.getRace() == SpaceRace.MECHIONS
          || info.getRace() == SpaceRace.REBORGIANS) {
        gender = Gender.NONE;
      }
      String greatLeader = NameGenerator.generateName(info.getRace(),
          gender);
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
      sb.append(info.getGovernment().getName().toLowerCase());
      sb.append(" and ");
      sb.append(gender.getHeShe());
      sb.append(" was their first ");
      sb.append(LeaderUtility.getRulerTitle(gender, info.getGovernment()));
      if (writtenHistory) {
        sb.append(". ");
      } else {
        sb.append(" and ");
        sb.append(gender.getHisHer());
        sb.append(" reign begins ");
        sb.append(name);
        sb.append(" written history. ");
      }
    }
    if (diplomatic) {
      sb.append(name);
      sb.append(" have always shown great interest towards others");
      if (traders) {
        sb.append(" and have reputation to be good traders");
      }
      sb.append(". ");
    }
    if (!diplomatic && traders) {
      sb.append(name);
     sb.append(" have reputation to be good traders. ");
    }
    if (xenophopic) {
      sb.append(name);
      sb.append(" have very strong attitudes towards others which they see"
          + " as an outsiders. ");
    }
    return sb.toString();
  }
  /**
   * Generate world type prehistory for realm.
   * @param info Realm
   * @param startPlanet Starting planet
   * @param name Realm space race name.
   * @return generated history.
   */
  private static String generateWorldType(final PlayerInfo info,
      final Planet startPlanet, final String name) {
    StringBuilder sb = new StringBuilder();
    sb.append(name);
    if (info.getRace().isLithovorian()) {
      sb.append(" were developed from silicate material"
          + " trillions of star years ago on");
    } else if (info.getRace().isRoboticRace()) {
      sb.append(" were created by ancient civilization"
          + " thousands of star years ago on");
    } else {
      sb.append(" were born billions of star years ago on");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.CARBONWORLD) {
      sb.append(" carbon rich planet with full of life.");
      sb.append(" This was excellent planet for live on.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.DESERTWORLD) {
      sb.append(" dry desert world. This harsh envinronment was");
      sb.append(" challenging but ");
      sb.append(name);
      sb.append(" became stronger and more sustainable there.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.ICEWORLD) {
      sb.append(" ice world. This cold and dark envinronment was");
      sb.append(" challenging but ");
      sb.append(name);
      sb.append(" became extreme survalists there.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.IRONWORLD) {
      sb.append(" iron world. This planet was full of molten lava");
      sb.append(" and hot envinronments ");
      sb.append(name);
      sb.append(" were able to survive there and learn how fluorish there.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.SILICONWORLD) {
      sb.append(" silicon world. This almost barren planet is one the");
      sb.append(" harshest envinronments in the galaxy. ");
      sb.append(name);
      sb.append(" still call this place as home.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.WATERWORLD) {
      sb.append(" water world. This planet is moist and has huge oceans");
      sb.append(" on surface. ");
      sb.append(name);
      sb.append(" conquered the planet fully for themselves.");
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
        sb.append(" was full of different life but ");
        sb.append(name);
        sb.append(" was the most evolved and their thick rock skin "
            + "helped in that process. ");
        break;
      }
      case 2: {
        sb.append(startPlanet.getName());
        sb.append(" was scarce on life but ");
        sb.append(name);
        sb.append(" were able to survive since they diet consist of minerals"
            + " and rocks. ");
        break;
      }
      case 3: {
        sb.append(name);
        sb.append(" origin is a great mystery, but ");
        sb.append(name);
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
        sb.append(name);
        sb.append(" believe that they were slaves but fought their freedom"
            + " from their creators. ");
        break;
      }
      case 2: {
        sb.append(name);
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
      }
    } else {
      sb.append(" ");
      switch (DiceGenerator.getRandom(3)) {
      default:
      case 0: {
        sb.append(name);
        sb.append(" evolution was fast process and "
            + " they quickly became dominant spiecies on ");
        sb.append(startPlanet.getName());
        sb.append(". ");
        break;
      }
      case 1: {
        sb.append(startPlanet.getName());
        sb.append(" was full of different life but ");
        sb.append(name);
        sb.append(" was the most evolved and they become only"
            + " sentient creatures on ");
        sb.append(startPlanet.getName());
        sb.append(". ");
        break;
      }
      case 2: {
        sb.append(startPlanet.getName());
        sb.append(" was scarce on life but ");
        sb.append(name);
        sb.append(" were able to survive since their high tolerance. ");
        break;
      }
      case 3: {
        sb.append(name);
        sb.append(" were not the only sentient creatures on ");
        sb.append(startPlanet.getName());
        sb.append(" but ");
        sb.append(name);
        sb.append(" extinct all the other rivals and thus became dominant. ");
        break;
      }
      }

    }
    return sb.toString();
  }

}
