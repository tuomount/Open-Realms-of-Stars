package org.openRealmOfStars.player.SpaceRace;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.government.GovernmentType;
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
    if (info.getRace() == SpaceRace.MECHIONS) {
      sb.append(generateMechionStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.SPORKS) {
      sb.append(generateSporkStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.GREYANS) {
      sb.append(generateGreyanStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.HOMARIANS) {
      sb.append(generateHomarianStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.CENTAURS) {
      sb.append(generateCentaurStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.MOTHOIDS) {
      sb.append(generateMothoidStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.TEUTHIDAES) {
      sb.append(generateTeuthidaeStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.SCAURIANS) {
      sb.append(generateScaurianStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.CHIRALOIDS) {
      sb.append(generateChiraloidStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.REBORGIANS) {
      sb.append(generateReborgianStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.LITHORIANS) {
      sb.append(generateLithorianStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.ALTEIRIANS) {
      sb.append(generateAlteirianStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.SMAUGIRIANS) {
      sb.append(generateSmaugirianStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.SYNTHDROIDS) {
      sb.append(generateSynthdroidStory(info, startPlanet, startingYear));
    }
    if (info.getRace() == SpaceRace.ALONIANS) {
      sb.append(generateAlonianStory(info, startPlanet, startingYear));
    }
    return sb.toString();
  }

  /**
   * This method tries to guess space race name and give it back in plural.
   * This is making best guess for English names.
   * @param empireName EmpireName
   * @param government Government type
   * @return Race name in plural
   */
  public static String getRaceNameInPlural(final String empireName,
      final GovernmentType government) {
    String[] params = empireName.split(" ");
    String result = params[0];
    if (params.length == 2) {
      if (params[0].equalsIgnoreCase(government.getName())) {
        result = params[1];
      } else {
       result = params[0];
      }
    }
    if (params.length == 3) {
      if (params[1].equalsIgnoreCase("of")) {
        if (params[0].equalsIgnoreCase(government.getName())) {
          result = params[2];
        } else {
          result = params[0];
        }
      }
      if (params[0].equalsIgnoreCase("the")) {
        if (params[1].equalsIgnoreCase(government.getName())) {
          result = params[2];
        } else {
          result = params[1];
        }
      }
    }
    if (endRe(result)) {
      result = result.substring(0, result.length() - 1);
      result = result + "ians";
    } else if (endVowel(result)) {
      if (result.endsWith("o")) {
        result = result + "id";
      }
      if (result.endsWith("i")) {
        result = result + "an";
      }
      result = result + "s";
    } else if (endPeople(result)) {
      result = result + "s";
    } else {
      result = result + "ians";
    }
    if (params.length == 4 && params[0].equalsIgnoreCase("the")
       && params[2].equalsIgnoreCase("of")) {
      if (params[1].equalsIgnoreCase(government.getName())) {
        result = params[3];
      } else {
        result = params[1];
      }
    }
    if (!result.endsWith("s")) {
      result = result + "s";
    }
    return result;
  }

  /**
   * Check if string has end vowel or not.
   * @param input String to check
   * @return True if ends with vowel.
   */
  private static boolean endVowel(final String input) {
    String temp = input.toLowerCase();
    if (temp.endsWith("a") || temp.endsWith("e")  || temp.endsWith("i")
        || temp.endsWith("o")  || temp.endsWith("u")  || temp.endsWith("y")) {
      return true;
    }
    return false;
  }
  /**
   * Check if string has end people/population meaning.
   * @param input String to check
   * @return True if ends with special ending.
   */
  private static boolean endPeople(final String input) {
    String temp = input.toLowerCase();
    if (temp.endsWith("an") || temp.endsWith("oid") || temp.endsWith("on")
        || temp.endsWith("ing") || temp.endsWith("aur")) {
      return true;
    }
    return false;
  }
  /**
   * Check if string has end re.
   * @param input String to check
   * @return True if ends with special ending.
   */
  private static boolean endRe(final String input) {
    String temp = input.toLowerCase();
    if (temp.endsWith("re")) {
      return true;
    }
    return false;
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
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    if (startPlanet.getName().startsWith("Earth")) {
      sb.append(name);
      sb.append(", the inhabitants of Earth,");
      sb.append(" are commonly regarded as possessing average physical and "
          + "mental attributes.");
    } else {
      sb.append(name);
      sb.append("a race of extraterrestrial beings, bear a striking resemblance"
          + " to their Earthly counterparts, humans. Much like humans,"
          + " Terrans are often depicted as possessing average physical"
          + " and mental capabilities.");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for mechion.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateMechionStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    if (info.getEmpireName().contains("Steel")) {
      name = "Mechions";
    }
    sb.append(name);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" are a type of mechanical robot that are designed to"
            + " function without the need for food or other organic"
            + " sustenance. Instead, they are powered by advanced technology,"
            + " such as batteries or fuel cells, which allow them to operate"
            + " for long periods of time without needing to be refueled.");
        break;
      }
      case 1: {
        sb.append(" are a remarkable breed of mechanical robots meticulously"
            + " designed to operate autonomously, free from the need for food"
            + " or organic sustenance. Instead, they draw their power from"
            + " cutting-edge technology, relying on batteries or fuel cells"
            + " that afford them extended operational periods without the"
            + " requirement for refueling.");
        break;
      }
      case 2: {
        sb.append(" represent a distinct class of mechanical robots engineered"
            + " to function autonomously, eliminating the need for sustenance "
            + "or nourishment. Their power source hinges on cutting-edge "
            + "technology, including batteries or fuel cells, enabling them "
            + "to operate tirelessly over extended periods without "
            + "necessitating refueling.");
        break;
      }
    }
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(" Each Mechion is equipped with advanced sensors and"
          + " communication systems, which allow them to coordinate their"
          + " movements and work in harmony with their fellow robots.");
      if (DiceGenerator.getRandom(1) == 0) {
        sb.append(" This allows Mechion populations to tackle complex tasks "
            + "that would be beyond the capabilities of a single robot.");
      } else {
        sb.append(" This collective synergy empowers Mechion populations to "
            + "collaborate on intricate tasks that would otherwise surpass "
            + "the capabilities of a solitary robot.");
      }
    } else {
      if (DiceGenerator.getRandom(1) == 0) {
        sb.append(" Each Mechion is equipped with advanced sensors and"
            + " powerful neuronetwork, which allows them to individually"
            + " perform complex and demanding tasks. This means that each"
            + " Mechion is highly advanced AI system that can learn and"
            + " adapt into new things.");
      } else {
        sb.append(" Each Mechion is equipped with advanced sensors and a "
            + "formidable neuronetwork, granting them the capacity to "
            + "undertake intricate and demanding tasks independently. "
            + "This renders every Mechion an advanced AI system capable of "
            + "learning and adapting to new challenges.");
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Synthdroid.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateSynthdroidStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    sb.append(name);
    sb.append(" are a race of artificial beings that are designed to "
        + "resemble human females. They are often portrayed as being "
        + "sleek and elegant, with graceful movements and a polished "
        + "appearance. Despite their robotic nature, ");
    sb.append(name);
    sb.append(" are able to perform many of the same functions as organic "
        + "beings, including eating, drinking, and speaking. However, they "
        + "require only a small amount of food to sustain themselves, "
        + "making them highly efficient and self-sufficient. Unlike most "
        + "other races, ");
    sb.append(name);
    sb.append(" must be built rather than born, and each population must "
        + "be created from scratch. This makes them a relatively rare and "
        + "mysterious race in the galaxy.");
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(" Each ");
      sb.append(name);
      sb.append(" is equipped with positronic computer and"
          + " capable communication systems, which allow them to coordinate"
          + " their movements and work in harmony with their fellow ");
      sb.append(name);
      sb.append(". These features allow ");
      sb.append(name);
      sb.append(" to handle complex tasks "
          + "that would be beyond the capabilities of a single being.");
    } else {
      sb.append(" Each ");
      sb.append(name);
      sb.append(" is equipped with positronic computer and"
          + " positronic neuronetwork, which allows them to individually"
          + " like a living creature. This means that each ");
      sb.append(name);
      sb.append(" is highly advanced AI system that can learn and"
          + " adapt into new things fast.");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for spork.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateSporkStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());

    sb.append(name);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" are a species of aggressive and warmongering"
            + " creatures that are known for their ferocity and their love of"
            + " battle. ");
        break;
      }
      case 1: {
        sb.append(" are a formidable species known for their aggressive and "
            + "warlike nature, renowned for their unyielding love of battle. ");
        break;
      }
      case 2: {
        sb.append(" are a formidable species, renowned for their aggressive "
            + "and martial disposition, and celebrated for their unwavering "
            + "passion for combat. ");
        break;
      }
    }
    sb.append(name);
    sb.append(" are typically tall and muscular, with thick, armored skin and"
        + " powerful muscles.");
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Teuthidae.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateTeuthidaeStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());

    sb.append(name);
    sb.append(" are a race of octopus-like creatures that are highly "
        + "advanced in the fields of science and military technology. ");
    sb.append(name);
    sb.append(" are known for their scientific curiosity and their focus "
        + "on exploration and conquest.");
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append("\n\n");
    sb.append("Because of their aquatic origins, ");
    sb.append(name);
    sb.append(" are expert navigators and have developed advanced ships"
        + " that are able to traverse the depths of space. These ships "
        + "are equipped with built-in cloaking devices, which allow "
        + "them to remain hidden and surprise their enemies in combat.");
    sb.append("\n\n");
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Scaurian.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateScaurianStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());

    sb.append(name);
    sb.append(" are a race of small but wide humanoids that are known for"
        + " their merchantile abilities. They are highly skilled at making"
        + " profitable trades and deals, and are always on the lookout for"
        + " new opportunities to gain more credits. ");
    if (info.getGovernment().getDiplomaticBonus() > 0) {
      sb.append(name);
      sb.append(" are a peaceful race, and prefer to use their cunning and "
          + "charm to outmaneuver their competitors rather than resorting to "
          + "violence.");
    } else {
      sb.append("Despite their small stature, ");
      sb.append(name);
      sb.append(" are not to be underestimated, as their intelligence and"
          + " resourcefulness make them formidable opponents in the "
          + "cut-throat world of trade and commerce.");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append("\n\n");
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Greyan.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateGreyanStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = "Greyans";
    if (info.getEmpireName().contains("Aesir")) {
      name = "Aesirians";
    } else {
      name = getRaceNameInPlural(info.getEmpireName(),
          info.getGovernment());
    }

    sb.append(name);
    sb.append(" are typically tall and slender, with long, graceful limbs"
        + " and delicate features. Their grey skin is smooth and sleek,"
        + " and it is said to shimmer in the light. They have large,"
        + " almond-shaped eyes that are capable of seeing in a wide"
        + " range of light levels, and they are highly perceptive"
        + " and intuitive. ");
    sb.append(name);
    sb.append(" are highly intelligent and curious, and they are always "
        + "seeking out new knowledge and experiences. They are natural "
        + "researchers and problem-solvers, and they are known for their "
        + "ability to quickly and accurately analyze complex data and "
        + "situations. ");
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Homarian.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateHomarianStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    sb.append(name);
    sb.append(" are a race of humanoid crabs that are known for their "
        + "immense strength and hard exoskeletons. This exoskeleton also gives"
        + " them incredible strength, allowing them to easily perform physical"
        + " tasks that would be difficult for other species. ");
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(name);
      sb.append(" are always working for their nest. They are will to do what "
          + "nest demands.");
      sb.append(name);
      sb.append(" ");
      sb.append(info.getGovernment().getName().toLowerCase());
      sb.append(" resembles a bit like hive-mind, but it is not true"
          + " hive-mind. ");
      sb.append(name);
      sb.append(" are deeply devoted for the ");
      sb.append(info.getGovernment().getName().toLowerCase());
      sb.append(" that they act like it is functions like hive-mind.");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Chiraloid.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateChiraloidStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    if (info.getEmpireName().contains("Capricorn")) {
      name = "Capricornians";
    }
    sb.append(name);
    sb.append(" are creatures that are distinguished by their four arms "
        + "and two legs. They have a hard exoskeleton that provides them "
        + "with protection and allows them to withstand harsh "
        + "environments. ");
    sb.append(name);
    sb.append(" are also known for their unique ability to perform "
        + "radiosynthesis, which involves using a special gland to convert "
        + "radioactivity into a nutrient that they can use for sustenance. "
        + "This ability allows ");
    sb.append(name);
    sb.append(" to thrive in environments that would be inhospitable to most"
        + " other life forms. ");
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(name);
      sb.append(" are creatures which are able form hivemind. This allows them"
          + " to work united and single organization. ");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Reborgian.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateReborgianStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    sb.append(name);
    sb.append(" are a race of cyborgs that are created by combining organic "
        + "organisms with bionic and robotic parts. This gives them "
        + "enhanced physical abilities and allows them to operate in a "
        + "wide variety of environments.");
    sb.append(name);
    sb.append(" are known for their ability to synthesize other living "
        + "space races into their own, creating a fearsome and powerful ");
    if (info.getGovernment() == GovernmentType.AI) {
      sb.append("AI");
    } else {
      sb.append(info.getGovernment().getName().toLowerCase());
    }
    sb.append(". However, this ability is feared and disliked by many "
        + "other races, who see it as a threat to their own individuality "
        + "and autonomy. Despite their aggressive expansionist tendencies, "
        + "reborgians have a slow reproduction rate and require very little "
        + "food to survive.");
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(name);
      sb.append(" are equipped with powerful communication systems, which "
          + " they use to coordinate their moves and tasks in harmomy. This "
          + "allows ");
      sb.append(name);
      sb.append(" work as a hiveminded population.");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Lithorian.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateLithorianStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    if (info.getEmpireName().contains("Metavore")) {
      name = "Metavorians";
    }
    sb.append(name);
    sb.append(" are a race of creatures that subsist on metal instead of"
        + " traditional food sources. This unique dietary requirement"
        + " gives them a slow growth rate. However, their ability to digest"
        + " metal allows them to extract valuable nutrients from metal"
        + " ores and other metallic substances, making them excellent miners.");
    sb.append(" In addition to ");
    sb.append(name);
    sb.append(" metal-eating abilities, they are also known for their"
        + " strong and durable bodies, which allow them to withstand harsh "
        + "environments and perform physically demanding tasks. ");
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(name);
      sb.append(" are able to communicate which each others on same planet "
          + "mentally. This allows them to coordinate their moves and tasks"
          + " in harmomy. This mental communicating is probably due special"
          + " metal ore they have eaten.");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Alteirian.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateAlteirianStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    sb.append(name);
    sb.append(" are a race of creatures that are adapted to life in"
        + " zero gravity. Because of this, they require special suits"
        + " to move on the surfaces of planets, and they spend most of"
        + " their time living and working in orbit. Their bodies are "
        + "mostly just mouth and big bulpy eyes with multiple long limbs"
        + " and a slender tail that help them move gracefully in zero"
        + " gravity. ");
    sb.append(name);
    sb.append(" are known for their advanced orbital technology,"
        + " which allows them to build and maintain large,"
        + " complex structures in space. ");
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(name);
      sb.append(" are able to mentally communicate which each others on same"
          + " planet. This allows them to organize their moves and tasks"
          + " in effectively. This mental communicating is probably due"
          + " spending so much time in zero gravity. ");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Centaurs.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateCentaurStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    if (info.getEmpireName().contains("Taurus")) {
      name = "Taurians";
    }
    sb.append(name);
    sb.append(" are similar to the mythical centaurs of ancient Greek and"
        + " Roman mythology. Like their mythical counterparts, ");
    sb.append(name);
    sb.append(" are quadrupeds with the upper body of humanoid like and"
        + " lower body resembles ant like. They also have very though"
        + " skin and they are huge in size.");
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append("\n\n");
    sb.append("Because of their large size, ");
    sb.append(name);
    sb.append("would need sturdy and rigid spaceships in order to support "
        + "their weight and allow them to travel through the vacuum of "
        + "space. ");
    sb.append("\n\n");
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Mothoid.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateMothoidStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    sb.append(name);
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(" are race of sentient insects that are capable of forming"
          + " a hivemind. This allows them to coordinate their actions and"
          + " work together as a highly efficient collective. ");
      sb.append(name);
    }
    sb.append(" are known for their fast breeding, which allows them to "
        + "quickly increase their numbers and expand their territory. ");
    if (info.getGovernment().getDiplomaticBonus() > 0) {
      sb.append(name);
      sb.append(" are also known for their hypnotic song, which is used for"
          + " communication and ritual purposes. This ability grants them"
          + " a cultural bonus in their interactions with other races. ");
    }
    sb.append("However, their exoskeletons are relatively weak, which gives"
        + " them a negative bonus when it comes to mining and troop power."
        + " Despite this weakness, ");
    sb.append(name);
    sb.append(" are able to compensate for their lack of physical strength "
        + "with their intelligence and cooperation.");
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Smaugirian.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateSmaugirianStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    sb.append(name);
    sb.append(" are known for their versatile cargo ships, which can be "
        + "outfitted with a variety of modules to accommodate different types "
        + "of cargo. Some of these ships even have weapons or privateering"
        + " modules, allowing them to defend themselves against hostile "
        + "forces or engage in piracy. Despite their illicit activities, ");
    sb.append(name);
    sb.append(" are often depicted as being honorable and loyal to their"
        + " friends and allies. They are also known for their resourcefulness "
        + "and ingenuity, which allows them to overcome obstacles and succeed "
        + "in their dangerous line of work. ");
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(name);
      sb.append(" are acting as space pirates. They will keep looting and"
          + " pillaging ships and do not care about consequences. ");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate background story for Alonian.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateAlonianStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String shortDescription = "extraterrestrial beings";
    sb.append(name);
    switch (DiceGenerator.getRandom(3)) {
    default:
    case 0: {
      shortDescription = "extraterrestrial beings";
      break;
    }
    case 1: {
      shortDescription = "humanoids";
      break;
    }
    case 2: {
      shortDescription = "nomading humanoids";
      break;
    }
    }
    switch (DiceGenerator.getRandom(3)) {
    default:
    case 0: {
      sb.append(" are a race of ");
      sb.append(shortDescription);
      sb.append(" that do not have"
          + " a traditional home planet. Instead, they are a nomadic race "
          + "that travels from place to place. Their home planet has"
          + " been destroyed when local star exploded as a super nova."
          + " Despite their lack of a fixed home, ");
      break;
    }
    case 1: {
      sb.append(" are a race of ");
      sb.append(shortDescription);
      sb.append(" that do not have"
          + " a traditional home planet. Instead, they are a nomadic race "
          + "that travels from place to place. Their home planet has"
          + " been destroyed when it's magnetic core shutdown and atmosphere"
          + " was blown away. The planet became lifeless rock."
          + " Despite their lack of a fixed home, ");
      break;
    }
    case 2: {
      sb.append(" are a race of ");
      sb.append(shortDescription);
      sb.append(" that do not have"
          + " a traditional home planet. Instead, they are a nomadic race "
          + "that travels from place to place. Their home planet has"
          + " been destroyed when huge meteor storm hit the planet. Whole"
          + " planet shattered into pieces."
          + " Despite their lack of a fixed home, ");
      break;
    }
    }
    sb.append(name);
    sb.append(" are highly advanced in the fields of science and technology. ");
    sb.append(name);
    sb.append(" also have a special ability where a single colony ship"
        + " produces one research point, allowing them to quickly advance"
        + " their technology and gain an edge over their competitors."
        + " In addition, their starbase laboratories are also more "
        + "efficient at producing research points, further enhancing"
        + " their technological advantage. ");
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(name);
      sb.append(" are nomading space pirates. They will keep pillaging and"
          + " looting fleets and do not care about consequences. ");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(generateExploration(info, startingYear, name, startPlanet));
    return sb.toString();
  }

  /**
   * Generate exploration part.
   * @param info Realm
   * @param startingYear Starting year
   * @param name Race name
   * @param startPlanet Starting planet
   * @return Exploration part for background story.
   */
  private static String generateExploration(final PlayerInfo info,
      final int startingYear, final String name, final Planet startPlanet) {
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
   * @param name Race name
   * @return FTL background story
   */
  private static String generateFtlStory(final PlayerInfo info,
      final int startingYear, final String name) {
    StringBuilder sb = new StringBuilder();
    boolean scientific = false;
    if (info.getGovernment() == GovernmentType.TECHNOCRACY
        || info.getGovernment() == GovernmentType.HEGEMONY) {
      scientific = true;
    }
    if (info.getRace().getResearchSpeed() > 100) {
      scientific = true;
    }
    if (info.getRace() == SpaceRace.ALONIANS) {
      sb.append(name);
      sb.append(" needed space flight very early since their home planet "
          + "was about to encounter catastrophy. They made fleet large enough"
          + " to hold their entire population. Fleet started without faster"
          + " than light travel. ");
    }
    if (scientific) {
      Gender gender = Gender.getRandom();
      String greatLeader = NameGenerator.generateName(info.getRace(),
          gender);
      switch (DiceGenerator.getRandom(3)) {
        default:
        case 0: {
          sb.append(greatLeader);
          sb.append(" were able to discover faster than light"
              + " travel and thus first prototype of space craft was created at"
              + " star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          if (info.getRace() == SpaceRace.ALONIANS) {
            sb.append(". First flights were magnificent success and then ");
            sb.append(name);
            sb.append(" scouts and colony ships were upgraded with FTL"
                + " at star year ");
            sb.append(startingYear);
            sb.append(". ");
          } else {
            sb.append(". First flights were magnificent success and then first"
                + " armed scout and colony ship was create at star year ");
            sb.append(startingYear);
            sb.append(". ");
          }
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
          if (info.getRace() == SpaceRace.ALONIANS) {
            sb.append(". First flights were magnificent success and then ");
            sb.append(name);
            sb.append(" scouts and colony ships were upgraded with FTL"
                + " at star year ");
            sb.append(startingYear);
            sb.append(". ");
          } else {
            sb.append(". First flights were magnificent success and then first"
                + " armed scout and colony ship was create at star year ");
            sb.append(startingYear);
            sb.append(". ");
          }
          break;
        }
        case 2: {
          sb.append("Group of scientist, lead by ");
          sb.append(greatLeader);
          sb.append(", were able to discover faster"
              + " than light travel and thus first prototype of space craft "
              + "was created at star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          if (info.getRace() == SpaceRace.ALONIANS) {
            sb.append(". First flights were magnificent success and then ");
            sb.append(name);
            sb.append(" scouts and colony ships were upgraded with FTL"
                + " at star year ");
            sb.append(startingYear);
            sb.append(". ");
          } else {
            sb.append(". First flights were magnificent success and then first"
                + " armed scout and colony ship was create at star year ");
            sb.append(startingYear);
            sb.append(". ");
          }
          break;
        }
        case 3: {
          sb.append("Vision of scientist ");
          sb.append(greatLeader);
          sb.append(" caused research to move toward faster than light engine"
              + " and breakthrough was made and soon after it first prototype"
              + " of space craft was created at star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          if (info.getRace() == SpaceRace.ALONIANS) {
            sb.append(". First flights were magnificent success and then ");
            sb.append(name);
            sb.append(" scouts and colony ships were upgraded with FTL"
                + " at star year ");
            sb.append(startingYear);
            sb.append(". ");
          } else {
            sb.append(". First flights were magnificent success and then first"
                + " armed scout and colony ship was create at star year ");
            sb.append(startingYear);
            sb.append(". ");
          }
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
          if (info.getRace() == SpaceRace.ALONIANS) {
            sb.append(". First flights were magnificent success and then ");
            sb.append(name);
            sb.append(" scouts and colony ships were upgraded with FTL"
                + " at star year ");
            sb.append(startingYear);
            sb.append(". ");
          } else {
            sb.append(". First flights were magnificent success and then first"
                + " armed scout and colony ship was create at star year ");
            sb.append(startingYear);
            sb.append(". ");
          }
          break;
        }
        case 1: {
          sb.append("Group of scientist were able to make faster"
              + " than light engine but no one else understood it and it took"
              + " years before actual prototype was done. First prototype of"
              + " space craft was created at star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          if (info.getRace() == SpaceRace.ALONIANS) {
            sb.append(". First flights were magnificent success and then ");
            sb.append(name);
            sb.append(" scouts and colony ships were upgraded with FTL"
                + " at star year ");
            sb.append(startingYear);
            sb.append(". ");
          } else {
            sb.append(". First flights were magnificent success and then first"
                + " armed scout and colony ship was create at star year ");
            sb.append(startingYear);
            sb.append(". ");
          }
          break;
        }
        case 2: {
          
          /*
           * In an intriguing twist, a group of archaeologists unearthed an 
           * enigmatic object from their home planet, eventually revealing 
           * it to be a faster-than-light engine. By the year 2377, the 
           * first prototype spacecraft had been crafted, heralding magnificent 
           * success during its maiden voyages. This achievement culminated 
           * in the creation of the inaugural armed scout and colony ships by 
           * star year 2400. The Mechion AI embarked on an ambitious journey 
           * of space exploration, setting forth from Alpha Muert with the 
           * Main Loop Droid D-9 steering the Mechions toward the boundless 
           * reaches of the stars.
           */
          /*
           * In a fascinating turn of events, a group of archaeologists 
           * unearthed a mysterious object from their home planet, which, 
           * after years of scrutiny, was revealed to be a faster-than-light 
           * engine. By the year 2377, the first prototype spacecraft had 
           * been constructed, achieving resounding success during its 
           * inaugural flights. This monumental feat culminated in the 
           * creation of the first armed scout and colony ships by 
           * star year 2400. The Steel Empire embarked on a grand 
           * odyssey of space exploration, commencing from Alpha Muert, with 
           * Empire Droid D-9 leading Mechions into the uncharted realms of 
           * the stars.
           */
          switch (DiceGenerator.getRandom(2)) {
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
          sb.append("a group of archeologists were able to discover strange"
              + " object from the planet and after years of study it turn out"
              + " to be faster than light engine. First prototype of"
              + " space craft was created at star year ");
          if (DiceGenerator.getRandom(1) == 0) {
            sb.append(" By the year ");
          } else {
            sb.append(" In the year ");
          }
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          sb.append(", the first prototype spacecraft had been crafted,"
              + " heralding magnificent success during its maiden voyages. ");
          if (info.getRace() == SpaceRace.ALONIANS) {
            sb.append(". First flights were magnificent success and then ");
            sb.append(name);
            sb.append(" scouts and colony ships were upgraded with FTL"
                + " at star year ");
            sb.append(startingYear);
            sb.append(". ");
          } else {
            sb.append("This achievement culminated in the creation of the ");
            sb.append(StoryGeneratorUtility.randomInaugural());
            sb.append(" armed scout and colony ships by star year ");
            sb.append(startingYear);
            sb.append(". ");
          }
          break;
        }
        case 3: {
          /*
           * A dedicated team of researchers achieved the remarkable feat of 
           * unlocking faster-than-light travel, leading to the creation of 
           * the first prototype spaceship in the year 2390. The inaugural 
           * spaceflights were resounding successes, culminating in the 
           * development of the first armed scout and colony ships by 
           * star year 2400. The Human Federation embarked on its grand 
           * voyage of space exploration, spearheaded by
           * President Rodolf Rednose, venturing beyond the boundaries of 
           * Earth III and into the vast expanse of the cosmos.
           */
          /*
           * A dedicated team of researchers achieved the groundbreaking
           * feat of uncovering faster-than-light travel, culminating in the 
           * creation of the first prototype spacecraft in the year 2380. 
           * Inaugural spaceflights heralded magnificent successes, 
           * culminating in the development of the first armed scout and 
           * colony ships by star year 2400. Terran Utopia embarked on its 
           * grand voyage of space exploration from Mars, with 
           * President Rodolf Rednose at the helm, guiding the Terrans into 
           * the boundless realm of the stars.
           */
          /*
           * A group of pioneering researchers succeeded in unraveling the 
           * secrets of faster-than-light travel, culminating in the creation 
           * of the first prototype spacecraft in the year 2375. 
           * Inaugural spaceflights witnessed resounding success, leading to 
           * the development of the first armed scout and colony ships by 
           * star year 2400. The Sporksian kingdom embarked on its grand 
           * odyssey of space exploration, launching from Mars with 
           * King Max Power at the helm, guiding his fierce people into the 
           * uncharted realms of the stars.
           */
          sb.append("Group of researchers were able to discover faster"
              + " than light travel and first prototype of space ship "
              + "was created at star year ");
          sb.append(startingYear - 10 - DiceGenerator.getRandom(15));
          if (info.getRace() == SpaceRace.ALONIANS) {
            sb.append(". First flights were magnificent success and then ");
            sb.append(name);
            sb.append(" scouts and colony ships were upgraded with FTL"
                + " at star year ");
            sb.append(startingYear);
            sb.append(". ");
          } else {
            sb.append(". First flights were magnificent success and then first"
                + " armed scout and colony ship was create at star year ");
            sb.append(startingYear);
            sb.append(". ");
          }
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
          sb.append(lowcaseGovernmentName);
          sb.append(" and ");
          sb.append(gender.getHeShe());
          sb.append(" was their first ");
          sb.append(LeaderUtility.getRulerTitle(gender, info.getGovernment()));
          sb.append(". ");
          break;
        }
        case 3: {
          sb.append(name);
          if (DiceGenerator.getRandom(1) == 0) {
            sb.append(" history is unknown since there is no written text"
                + " available, until ");
          } else {
            sb.append(" history remained shrouded in obscurity, devoid of"
                + " written records, until ");
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
          sb.append(" united all ");
          sb.append(name);
          sb.append(" into ");
          sb.append(lowcaseGovernmentName);
          sb.append(" and ");
          sb.append(gender.getHeShe());
          sb.append(" was their ");
          sb.append(StoryGeneratorUtility.randomInaugural());
          sb.append(" ");
          sb.append(LeaderUtility.getRulerTitle(gender, info.getGovernment()));
          if (DiceGenerator.getRandom(1) == 0) {
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
          if (DiceGenerator.getRandom(1) == 0) {
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
      sb.append(lowcaseGovernmentName);
      sb.append(" and ");
      sb.append(gender.getHeShe());
      sb.append(" was their ");
      sb.append(StoryGeneratorUtility.randomInaugural());
      sb.append(" ");
      sb.append(LeaderUtility.getRulerTitle(gender, info.getGovernment()));
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
        if (DiceGenerator.getRandom(1) == 0) {
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
      if (DiceGenerator.getRandom(1) == 0) {
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
   * @param name Realm space race name.
   * @return generated history.
   */
  private static String generateWorldType(final PlayerInfo info,
      final Planet startPlanet, final String name) {
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
        sb.append(" one billions");
        break;
      }
      case 1: {
        sb.append(" two billions");
        break;
      }
      case 2: {
        sb.append(" three billions");
        break;
      }
    }
      sb.append(" of star years ago on");
    }
    String endOfworldDescription = null;
    if (startPlanet.getPlanetType().getWorldType() == WorldType.CARBONWORLD) {
      /*
       * Their story unfolds two billion star years ago on a carbon-rich planet 
       * teeming with life. Among the thriving ecosystem, Mars held the 
       * potential for life to evolve. It was approximately 600,000 star years 
       * ago when the first Sporksians emerged on the Martian surface. While 
       * they shared their newfound home with other sentient beings initially, 
       * the Sporksians vanquished all rivals, asserting their dominance over 
       * the planet. Mars proved to be an ideal habitat for their kind.
       */
      /*
       * Their history traces back an astonishing two billion star years ago on 
       * a carbon-rich planet teeming with life. The evolution of life on Mars 
       * commenced, leading to the emergence of the first Sagittarians 
       * approximately 500,000 star years ago. While Mars initially played host 
       * to various sentient species, the Sagittarians asserted their 
       * dominance, eliminating all rivals and establishing themselves as the 
       * paramount species. Mars proved to be an ideal habitat, conducive to 
       * their thriving existence.
       */
      sb.append(" carbon rich planet with full of life.");
      endOfworldDescription = " This was excellent planet for live on.";
      fullOfLife = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.DESERTWORLD) {
      /*
       * Life on their home planet, a dry desert world, came into existence
       * a staggering two billion star years ago. Mars, although a harsh
       * and unforgiving environment, hosted some forms of life, and it
       * was the ancestors of the Terrans who emerged as the most advanced
       * among them. Approximately 100,000 star years ago, the first
       * Terrans made their appearance on the surface of Mars, becoming 
       * the planet's sole sentient inhabitants. Despite the challenging 
       * conditions, the Terrans grew resilient and adapted, thriving in 
       * their demanding habitat.
       */
      sb.append(" dry desert world.");
      if (DiceGenerator.getRandom(1) == 0) {
        endOfworldDescription = "This harsh envinronment was challenging but "
            + name + " became stronger and more sustainable there.";
      } else {
        endOfworldDescription = "Despite the challenging conditions, the "
            + name + " grew resilient and adapted, thriving in their "
            + "demanding habitat.";
      }
      hot = true;
      harsh = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.ICEWORLD) {
      sb.append(" ice world.");
      endOfworldDescription = "This cold and dark envinronment was"
          + " challenging but " + name + " became extreme survalists there.";
      cold = true;
      harsh = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.IRONWORLD) {
      sb.append(" iron world.");
      if (DiceGenerator.getRandom(1) == 0) {
        endOfworldDescription = "This planet was full of molten lava and"
            + " hot envinronments " + name + " were able to survive there"
            + " and learn how fluorish there.";
      } else {
        endOfworldDescription = "Remarkably, " + name + " not only survived"
            + " but thrived, acquiring invaluable insights into thriving "
            + "in such harsh conditions.";
      }
      hot = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.SILICONWORLD) {
      sb.append(" silicon world.");
      endOfworldDescription = "This almost barren planet is one the"
          + " harshest envinronments in the galaxy. " + name
          + " still call this place as home.";
      harsh = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.WATERWORLD) {
      sb.append(" water world.");
      endOfworldDescription = "This planet is moist and has huge oceans"
          + " on surface. " + name + " conquered the planet fully"
              + " for themselves.";
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
        } else if (harsh) {
          sb.append(" was hot but it contained some primitive life and ");
        } else {
          sb.append(" was average life bearing planet and ");
        }
        sb.append(name);
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
        sb.append(name);
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
          sb.append(name);
          sb.append(" extinct all the other rivals and thus became dominant. ");
        } else {
          sb.append(" They were the only sentient creatures on ");
          sb.append(startPlanet.getName());
          sb.append(" and ");
          sb.append(name);
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
