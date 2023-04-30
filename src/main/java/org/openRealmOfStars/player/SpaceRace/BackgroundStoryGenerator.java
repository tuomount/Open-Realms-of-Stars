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
      sb.append(" are the humans from the Earth. ");
      sb.append(name);
      sb.append(" are seen as "
          + "average in most physical and mental abilities. ");
    } else {
      sb.append(name);
      sb.append(" are an extraterrestrial beings that are similar to humans"
          + " on Earth. Like their Earthly counterparts, ");
      sb.append(name);
      sb.append(" are often portrayed as being average in most"
          + " physical and mental abilities. ");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(" are a type of mechanical robot that are designed to function"
        + " without the need for food or other organic sustenance. Instead,"
        + " they are powered by advanced technology, such as batteries or "
        + "fuel cells, which allow them to operate for long periods of time"
        + " without needing to be refueled.");
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(" Each Mechion is equipped with advanced sensors and"
          + " communication systems, which allow them to coordinate their"
          + " movements and work in harmony with their fellow robots."
          + " This allows Mechion populations to tackle complex tasks "
          + "that would be beyond the capabilities of a single robot.");
    } else {
      sb.append(" Each Mechion is equipped with advanced sensors and"
          + " powerful neuronetwork, which allows them to individually"
          + " perform complex and demanding tasks. This means that each"
          + " Mechion is highly advanced AI system that can learn and"
          + " adapt into new things.");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(" are a species of aggressive and warmongering"
        + " creatures that are known for their ferocity and their love of"
        + " battle. ");
    sb.append(name);
    sb.append(" are typically tall and muscular, with thick, armored skin and"
        + " powerful muscles.");
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, name));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, name));
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
    }
    sb.append(".");
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
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from deep space");
    if (info.getRuler() != null) {
      sb.append(" with ");
      sb.append(info.getRuler().getCallName());
      sb.append(" leading ");
      sb.append(name);
      sb.append(" to realm of stars");
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
          sb.append(" and thus first prototype of space craft was created at"
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
              + "years before actual prototype was done. First prototype of"
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
          sb.append("Group of archeologists were able to discover strange"
              + " object from the planet and after years of study it turn out"
              + " to be faster than light engine. First prototype of"
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
        case 3: {
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
          sb.append(lowcaseGovernmentName);
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
      sb.append(lowcaseGovernmentName);
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
      sb.append(name);
      sb.append(" were created by ancient civilization");
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
      sb.append("Life were born ");
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
    if (startPlanet.getPlanetType().getWorldType() == WorldType.CARBONWORLD) {
      sb.append(" carbon rich planet with full of life.");
      sb.append(" This was excellent planet for live on.");
      fullOfLife = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.DESERTWORLD) {
      sb.append(" dry desert world. This harsh envinronment was");
      sb.append(" challenging but ");
      sb.append(name);
      sb.append(" became stronger and more sustainable there.");
      hot = true;
      harsh = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.ICEWORLD) {
      sb.append(" ice world. This cold and dark envinronment was");
      sb.append(" challenging but ");
      sb.append(name);
      sb.append(" became extreme survalists there.");
      cold = true;
      harsh = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.IRONWORLD) {
      sb.append(" iron world. This planet was full of molten lava");
      sb.append(" and hot envinronments ");
      sb.append(name);
      sb.append(" were able to survive there and learn how fluorish there.");
      hot = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.SILICONWORLD) {
      sb.append(" silicon world. This almost barren planet is one the");
      sb.append(" harshest envinronments in the galaxy. ");
      sb.append(name);
      sb.append(" still call this place as home.");
      harsh = true;
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.WATERWORLD) {
      sb.append(" water world. This planet is moist and has huge oceans");
      sb.append(" on surface. ");
      sb.append(name);
      sb.append(" conquered the planet fully for themselves.");
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
        if (fullOfLife) {
          sb.append("They were not the only sentient creatures on ");
          sb.append(startPlanet.getName());
          sb.append(" but ");
          sb.append(name);
          sb.append(" extinct all the other rivals and thus became dominant. ");
        } else {
          sb.append("They were the only sentient creatures on ");
          sb.append(startPlanet.getName());
          sb.append(" and ");
          sb.append(name);
          sb.append(" survived there eating the plant life. ");
        }
        break;
      }
      }

    }
    return sb.toString();
  }

}
