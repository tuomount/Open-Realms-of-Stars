package org.openRealmOfStars.player.race;
/*
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
 */

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.player.leader.Gender;
import org.openRealmOfStars.player.leader.RulerUtility;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.NameGenerator;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.WorldType;
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
   * This method tries to guess space race name and give it back in single.
   * This is making best guess for English names.
   * @param empireName EmpireName
   * @param government Government type
   * @return Race name in plural
   */
  public static String getRaceNameInSingle(final String empireName,
      final GovernmentType government) {
    String result = getRaceNameInPlural(empireName, government);
    result = result.substring(0, result.length() - 1);
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    if (startPlanet.getName().startsWith("Earth")) {
      sb.append(namePlural);
      sb.append(", the inhabitants of Earth,");
      sb.append(" are commonly regarded as possessing average physical and "
          + "mental attributes.");
    } else {
      sb.append(namePlural);
      sb.append("a race of extraterrestrial beings, bear a striking resemblance"
          + " to their Earthly counterparts, humans. Much like humans, ");
      sb.append(namePlural);
      sb.append(" are often depicted as possessing average physical"
          + " and mental capabilities.");
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    if (info.getEmpireName().contains("Steel")) {
      namePlural = "Mechions";
      name = "Mechion";
    }
    sb.append(namePlural);
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
      sb.append(" Each ");
      sb.append(name);
      sb.append(" is equipped with advanced sensors and"
          + " communication systems, which allow them to coordinate their"
          + " movements and work in harmony with their fellow robots.");
      if (DiceGenerator.getBoolean()) {
        sb.append(" This allows ");
        sb.append(name);
        sb.append(" populations to tackle complex tasks "
            + "that would be beyond the capabilities of a single robot.");
      } else {
        sb.append(" This collective synergy empowers ");
        sb.append(name);
        sb.append(" populations to "
            + "collaborate on intricate tasks that would otherwise surpass "
            + "the capabilities of a solitary robot.");
      }
    } else {
      if (DiceGenerator.getBoolean()) {
        sb.append(" Each ");
        sb.append(name);
        sb.append(" is equipped with advanced sensors and"
            + " powerful neuronetwork, which allows them to individually"
            + " perform complex and demanding tasks. This means that each ");
        sb.append(name);
        sb.append(" is highly advanced AI system that can learn and"
            + " adapt into new things.");
      } else {
        sb.append(" Each ");
        sb.append(name);
        sb.append(" is equipped with advanced sensors and a "
            + "formidable neuronetwork, granting them the capacity to "
            + "undertake intricate and demanding tasks independently. "
            + "This renders every ");
        sb.append(name);
        sb.append(" an advanced AI system capable of "
            + "learning and adapting to new challenges.");
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    sb.append(namePlural);
    if (DiceGenerator.getBoolean()) {
      sb.append(" are a race of artificial beings that are designed to "
          + "resemble human females. They are often portrayed as being "
          + "sleek and elegant, with graceful movements and a polished "
          + "appearance. Despite their robotic nature, ");
    } else {
      sb.append(", a remarkable race of artificial beings, are meticulously "
          + "crafted to emulate the appearance of human females. They are "
          + "often depicted as exuding a sleek and elegant aura, "
          + "characterized by graceful movements and a polished exterior. "
          + "Despite their inherently robotic nature, ");
    }
    sb.append(namePlural);
    if (DiceGenerator.getBoolean()) {
      sb.append(" are able to perform many of the same functions as organic "
          + "beings, including eating, drinking, and speaking. However, they "
          + "require only a small amount of food to sustain themselves, "
          + "making them highly efficient and self-sufficient. Unlike most "
          + "other races, ");
    } else {
      sb.append(" possess the extraordinary capability to perform a wide "
          + "range of functions akin to organic beings, including eating, "
          + "drinking, and speaking. Remarkably, their sustenance "
          + "requirements are minimal, rendering them highly efficient and "
          + "self-reliant. Unlike most other species, ");
    }
    sb.append(namePlural);
    if (DiceGenerator.getBoolean()) {
      sb.append(" must be built rather than born, and each population must "
          + "be created from scratch. This makes them a relatively rare and "
          + "mysterious race in the galaxy.");
    } else {
      sb.append(" are not born but meticulously constructed, making them "
          + "a relatively uncommon and enigmatic presence in the galaxy.");
    }
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(" Each ");
      sb.append(namePlural);
      if (DiceGenerator.getBoolean()) {
        sb.append(" is equipped with positronic computer and"
            + " capable communication systems, which allow them to coordinate"
            + " their movements and work in harmony with their fellow ");
      } else {
        sb.append(" is endowed with a positronic computer and advanced"
            + " communication systems, enabling seamless coordination and"
            + " collaboration with fellow ");
      }
      sb.append(namePlural);
      sb.append(". These features ");
      if (DiceGenerator.getBoolean()) {
        sb.append("allow ");
      } else {
        sb.append("empower ");
      }
      sb.append(namePlural);
      if (DiceGenerator.getBoolean()) {
        sb.append(" to handle complex tasks "
            + "that would be beyond the capabilities of a single being.");
      } else {
        sb.append(" to tackle intricate tasks that would typically surpass"
            + " the abilities of a single being.");
      }
    } else {
      sb.append(" Each ");
      sb.append(namePlural);
      if (DiceGenerator.getBoolean()) {
        sb.append(" is equipped with positronic computer and"
            + " positronic neuronetwork, which allows them to individually"
            + " like a living creature. This means that each ");
      } else {
        sb.append(" is endowed with a positronic computer and a sophisticated"
            + " positronic neuronetwork, granting them an individuality akin"
            + " to living creatures.");
      }
      sb.append(namePlural);
      if (DiceGenerator.getBoolean()) {
        sb.append(" is highly advanced AI system that can learn and"
            + " adapt into new things fast.");
      } else {
        sb.append(" ");
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(3)) {
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
      case 3: {
        sb.append(", a unique species, are often misunderstood due to their"
            + " reputation for aggression and a love for battle. ");
        break;
      }
    }
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(1)) {
      default:
      case 0: {
      sb.append(" are typically tall and muscular, with thick, armored skin and"
          + " powerful muscles.");
      break;
      }
      case 1: {
      sb.append("  are distinguished by their imposing stature, robust build,"
          + " resilient armored skin, and formidable musculature.");
      break;
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());

    sb.append(namePlural);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" are a race of octopus-like creatures that are highly "
            + "advanced in the fields of science and military technology. ");
        sb.append(namePlural);
        sb.append(" are known for their scientific curiosity and their focus "
            + "on exploration and conquest.");
        break;
      }
      case 1: {
        sb.append(" an octopus-like species, possess unparalleled advancements"
            + " in both scientific and military domains. Renowned for their"
            + " insatiable scientific curiosity, they are driven by a"
            + " relentless pursuit of exploration and conquest.");
        break;
      }
      case 2: {
        sb.append(" an octopus-like species, stand as a testament to their "
            + "high-level mastery of both scientific and military domains. "
            + "Renowned for their insatiable scientific curiosity, they "
            + "are driven by an unyielding pursuit of exploration and"
            + " conquest.");
        break;
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append("\n\n");
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append("Because of their aquatic origins, the ");
        break;
      }
      case 1: {
        sb.append("Leveraging their aquatic origins, the ");
        break;
      }
      case 2: {
        sb.append("Drawing on their aquatic origins, the ");
        break;
      }
    }
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(1)) {
      default:
      case 0: {
        sb.append(" are expert navigators and have developed advanced ships"
          + " that are able to traverse the depths of space. These ships "
          + "are equipped with built-in cloaking devices, which allow "
          + "them to remain hidden and surprise their enemies in combat.");
        break;
      }
      case 1: {
        sb.append(" have honed their expertise in navigation, crafting"
            + " advanced ships capable of delving into the vastness of "
            + "space. These vessels are outfitted with integrated cloaking"
            + " technology, enabling stealth maneuvers and surprise attacks"
            + " in combat.");
        break;
      }
    }
    sb.append("\n\n");
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());

    sb.append(namePlural);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" are a race of small but wide humanoids that are known for"
            + " their merchantile abilities. They are highly skilled at making"
            + " profitable trades and deals, and are always on the lookout for"
            + " new opportunities to gain more credits. ");
        break;
      }
      case 1: {
        sb.append(", a diminutive yet robust humanoid race, have carved"
            + " their niche in the galaxy through their unparalleled "
            + "mercantile prowess. Renowned for their astute trading skills,"
            + " they perpetually seek fresh avenues to amass wealth and"
            + " resources. ");
        break;
      }
      case 2: {
        sb.append(", a diminutive yet sturdy humanoid race, have etched "
            + "their legacy in the galaxy with unmatched mercantile "
            + "expertise. Renowned for their keen trading acumen, they "
            + "tirelessly seek out new avenues to accumulate wealth "
            + "and resources. ");
        break;
      }
    }
    if (info.getGovernment().getDiplomaticBonus() > 0) {
      if (DiceGenerator.getBoolean()) {
        sb.append(namePlural);
        sb.append(" are a peaceful race, and prefer to use their cunning and "
            + "charm to outmaneuver their competitors rather than resorting"
            + " to violence.");
      } else {
        sb.append("Inclined towards peace, ");
        sb.append(namePlural);
        sb.append(" prefer to employ their wit and charm to outmaneuver "
            + "competitors rather than resorting to violence.");
      }
    } else {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append("Despite their small stature, ");
          sb.append(namePlural);
          sb.append(" are not to be underestimated, as their intelligence and"
              + " resourcefulness make them formidable opponents in the "
              + "cut-throat world of trade and commerce.");
          break;
        }
        case 1: {
          sb.append("Despite their modest stature, underestimating a ");
          sb.append(name);
          sb.append(" would be a grave error, for their sharp intellect"
              + " and resourcefulness render them formidable adversaries "
              + "in the unforgiving arena of commerce and trade.");
        break;
        }
        case 2: {
          sb.append("Underestimating ");
          sb.append(namePlural);
          sb.append(" due to their small stature would be a grievous error,"
              + " as their intelligence and resourcefulness render them "
              + "formidable competitors in the ruthless realm of trade "
              + "and commerce.");
          break;
        }
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append("\n\n");
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = "Greyans";
    String name = "Greyan";

    if (info.getEmpireName().contains("Aesir")) {
      namePlural = "Aesirians";
      name = "Aesirian";
    } else {
      namePlural = getRaceNameInPlural(info.getEmpireName(),
          info.getGovernment());
      name = getRaceNameInSingle(info.getEmpireName(),
          info.getGovernment());
    }

    sb.append(namePlural);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" are typically tall and slender, with long, graceful limbs"
            + " and delicate features. Their grey skin is smooth and sleek,"
            + " and it is said to shimmer in the light. They have large,"
            + " almond-shaped eyes that are capable of seeing in a wide"
            + " range of light levels, and they are highly perceptive"
            + " and intuitive. ");
        break;
      }
      case 1: {
        sb.append(" are distinguished by their statuesque frames, "
            + "possessing long, graceful limbs and delicate features. "
            + "Their sleek, grey skin has a subtle luminescence, "
            + "shimmering in the light. Their almond-shaped eyes, "
            + "capable of discerning a wide spectrum of light levels, "
            + "endow them with keen perception and intuition. ");
        break;
      }
      case 2: {
        sb.append(" stand tall and slender, adorned with long, graceful"
            + " limbs and delicate features. Their smooth, sleek grey "
            + "skin possesses an ethereal quality, shimmering in the "
            + "play of light. Their large, almond-shaped eyes possess "
            + "an extraordinary range of vision, endowing them with "
            + "acute perception and intuition. ");
        break;
      }
    }
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(namePlural);
        sb.append(" are highly intelligent and curious, and they are always "
          + "seeking out new knowledge and experiences. They are natural "
          + "researchers and problem-solvers, and they are known for their "
          + "ability to quickly and accurately analyze complex data and "
          + "situations. ");
        break;
      }
      case 1: {
        sb.append("Intelligent and inquisitive by nature, ");
        sb.append(namePlural);
        sb.append(" are perpetually driven to seek out fresh knowledge "
            + "and experiences. They excel as natural researchers and "
            + "adept problem-solvers, renowned for their swift and "
            + "accurate analysis of intricate data and situations. ");
        break;
      }
      case 2: {
        sb.append("Driven by an insatiable curiosity and marked"
            + " intelligence, ");
        sb.append(namePlural);
        sb.append(" are perpetual seekers of fresh knowledge and"
            + " experiences. They excel as innate researchers and adept"
            + " problem-solvers, renowned for their swift and precise"
            + " analysis of complex data and situations. ");
        break;
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0:  {
        sb.append(" are a race of humanoid crabs that are known for their "
            + "immense strength and hard exoskeletons. This exoskeleton also"
            + " gives them incredible strength, allowing them to easily"
            + " perform physical tasks that would be difficult for other"
            + " species. ");
        break;
      }
      case 1:  {
        sb.append(" humanoid crabs of remarkable strength and hardened"
            + " exoskeletons, are renowned for their unparalleled physical"
            + " prowess. This exoskeleton, a testament to their formidable"
            + " power, enables them to effortlessly undertake tasks that"
            + " would challenge other species. ");
        break;
      }
      case 2:  {
        sb.append(", humanoid crabs boasting remarkable strength and "
            + "impenetrable exoskeletons, are celebrated for their unmatched"
            + " physical prowess. This hardened carapace stands as a testament"
            + " to their power, enabling them to effortlessly tackle tasks "
            + "that would daunt other species.");
        break;
      }
    }
    if (info.getGovernment().isImmuneToHappiness()) {
      switch (DiceGenerator.getRandom(1)) {
        default:
        case 0: {
          sb.append(namePlural);
          sb.append(" are always working for their nest. They are will to do"
              + " what nest demands.");
          sb.append(namePlural);
          sb.append(" ");
          sb.append(info.getGovernment().getName().toLowerCase());
          sb.append(" resembles a bit like hive-mind, but it is not true"
              + " hive-mind. ");
          sb.append(namePlural);
          sb.append(" are deeply devoted for the ");
          sb.append(info.getGovernment().getName().toLowerCase());
          sb.append(" that they act like it is functions like hive-mind.");
        break;
        }
        case 1: {
          sb.append("Devoted to their nest, they willingly fulfill the"
              + " nest's demands, embodying a collective purpose akin to"
              + " a hive-mind, albeit not a true one.");
          break;
        }
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    if (info.getEmpireName().contains("Capricorn")) {
      namePlural = "Capricornians";
      name = "Capricornian";
    }
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" are creatures that are distinguished by their four arms "
            + "and two legs. They have a hard exoskeleton that provides them "
            + "with protection and allows them to withstand harsh "
            + "environments. ");
        break;
      }
      case 1: {
        sb.append(" are remarkable creatures, distinguished by their four "
            + "arms and two legs, encased in a robust exoskeleton that "
            + "shields them from harsh environments. ");
        break;
      }
      case 2: {
        sb.append(" are extraordinary beings, characterized by their"
            + " four arms and two legs, encased in a formidable exoskeleton"
            + " that fortifies them against harsh environments. ");
        break;
      }
    }
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(namePlural);
        sb.append(" are also known for their unique ability to perform "
            + "radiosynthesis, which involves using a special gland to"
            + " convert radioactivity into a nutrient that they can use"
            + " for sustenance. This ability allows ");
        sb.append(namePlural);
        sb.append(" to thrive in environments that would be inhospitable"
            + " to most other life forms. ");
        break;
      }
      case 1: {
        sb.append("What truly sets them apart is their unique ability to"
            + " perform radiosynthesis, a process wherein a specialized"
            + " gland converts radioactivity into a vital nutrient,"
            + " enabling them to thrive in otherwise inhospitable "
            + "conditions. ");
        break;
      }
      case 2: {
        sb.append("Their unique gift of radiosynthesis, accomplished through"
            + " a specialized gland, allows them to convert radioactivity"
            + " into sustenance, enabling them to thrive in places where"
            + " most other life forms would falter. ");
        break;
      }
    }
    if (info.getGovernment().isImmuneToHappiness()) {
      switch (DiceGenerator.getRandom(1)) {
        default:
        case 0: {
          sb.append(namePlural);
          sb.append(" are creatures which are able form hivemind. This"
              + " allows them to work united and single organization. ");
          break;
        }
        case 1: {
          sb.append("These beings also possess the extraordinary capacity to"
              + " form a hivemind, allowing them to operate as a unified and"
              + " highly organized entity. ");
          break;
        }
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(3)) {
      default:
      case 0: {
        sb.append(" are a race of cyborgs that are created by combining"
            + " organic organisms with bionic and robotic parts. This gives"
            + " them enhanced physical abilities and allows them to"
            + " operate in a wide variety of environments.");
        break;
      }
      case 1: {
        sb.append(" a race of cyborgs, seamlessly meld organic organisms"
            + " with bionic and robotic components, endowing them with"
            + " extraordinary physical prowess and adaptability across"
            + " diverse environments. ");
        break;
      }
      case 2: {
        sb.append(" are a remarkable race of cyborgs, created through the "
            + "fusion of organic organisms with bionic and robotic"
            + " components. This synthesis bestows upon them enhanced "
            + "physical capabilities, enabling them to operate effectively "
            + "in a wide array of environments. ");
        break;
      }
      case 3: {
        sb.append(" stand as a remarkable race of cyborgs, a fusion of"
            + " organic beings with bionic and robotic components. This"
            + " amalgamation bequeaths them with augmented physical "
            + "prowess, allowing them to operate seamlessly across"
            + " a diverse range of environments. ");
        break;
      }
    }
    switch (DiceGenerator.getRandom(3)) {
      default:
      case 0: {
        sb.append(namePlural);
        sb.append(" are known for their ability to synthesize other living "
            + "space races into their own, creating a fearsome and powerful ");
        if (info.getGovernment() == GovernmentType.AI) {
          sb.append("AI");
        } else {
          sb.append(info.getGovernment().getName().toLowerCase());
        }
        sb.append(". However, this ability is feared and disliked by many "
            + "other races, who see it as a threat to their own"
            + " individuality and autonomy. Despite their aggressive"
            + " expansionist tendencies, ");
        sb.append(namePlural);
        sb.append(" have a slow reproduction rate and require very little "
            + "food to survive. ");
        break;
      }
      case 1: {
        sb.append("Their unique capacity to assimilate other spacefaring "
            + "races into their collective gives rise to a formidable and"
            + " potent ");
        if (info.getGovernment() == GovernmentType.AI) {
          sb.append("AI");
        } else {
          sb.append(info.getGovernment().getName().toLowerCase());
        }
        sb.append(". However, this power is met with apprehension and "
            + "aversion from many other civilizations, who perceive it as "
            + "a threat to their own autonomy and individuality. "
            + "Despite their expansionist tendencies, ");
        sb.append(namePlural);
        sb.append(" contend with a slow reproductive rate and require"
            + " minimal sustenance for survival. ");
        break;
      }
      case 2: {
        sb.append("Among their distinguishing traits is the ability to"
            + " assimilate other living space races into their own,"
            + " resulting in the development of a formidable ");
        if (info.getGovernment() == GovernmentType.AI) {
          sb.append("AI");
        } else {
          sb.append(info.getGovernment().getName().toLowerCase());
        }
        sb.append(". However, this unique capacity has garnered fear and"
            + " antipathy from many other races, who perceive it as"
            + " a threat to their individuality and autonomy. Despite "
            + "their expansionist tendencies, ");
        sb.append(namePlural);
        sb.append(" exhibit a slow reproductive rate and require minimal "
            + "sustenance for survival. ");
        break;
      }
      case 3: {
        sb.append("One of their most distinctive attributes is their"
            + " capacity to integrate other living spacefaring races,"
            + " resulting in the formation of a formidable ");
        if (info.getGovernment() == GovernmentType.AI) {
          sb.append("AI");
        } else {
          sb.append(info.getGovernment().getName().toLowerCase());
        }
        sb.append(". However, this unique capability has evoked apprehension"
            + " and mistrust from various other civilizations, who perceive"
            + " it as a potential threat to their individuality and"
            + " autonomy. In spite of their expansionist inclinations, ");
        sb.append(namePlural);
        sb.append(" exhibit a slow reproductive rate and require minimal "
            + "sustenance for their survival. ");
        break;
      }
    }
    if (info.getGovernment().isImmuneToHappiness()) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(namePlural);
          sb.append(" are equipped with powerful communication systems, which "
              + " they use to coordinate their moves and tasks in harmomy."
              + " This allows ");
          sb.append(namePlural);
          sb.append(" work as a hiveminded population.");
          break;
        }
        case 1: {
          sb.append("Equipped with powerful communication systems, ");
          sb.append(namePlural);
          sb.append(" harmoniously coordinate their actions and tasks,"
              + " functioning as a unified hive mind. This collective "
              + "approach has proven instrumental in overcoming numerous"
              + " challenges. ");
          break;
        }
        case 2: {
          sb.append("They are equipped with formidable communication "
              + "systems, enabling seamless coordination and harmonious"
              + " task execution, functioning as a united hive-minded"
              + " population. ");
          break;
        }
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    if (info.getEmpireName().contains("Metavore")) {
      namePlural = "Metavorians";
      name = "Metavorian";
    }
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(1)) {
      default:
      case 0: {
        sb.append(" are a race of creatures that subsist on metal instead of"
            + " traditional food sources. This unique dietary requirement"
            + " gives them a slow growth rate. However, their ability to digest"
            + " metal allows them to extract valuable nutrients from metal"
            + " ores and other metallic substances, making them excellent"
            + " miners.");
        break;
      }
      case 1: {
        sb.append(" are a unique species subsisting on metal, in contrast "
            + "to conventional food sources. This distinctive dietary "
            + "preference results in a slow growth rate. However, their "
            + "exceptional ability to metabolize metal enables them to "
            + "extract vital nutrients from ores and metallic substances,"
            + " rendering them exceptional miners. ");
        break;
      }
      case 2: {
        sb.append(", a remarkable species, subsist on metal instead of"
            + " conventional food sources. This unique dietary preference"
            + " leads to a slower growth rate. However, their remarkable"
            + " ability to metabolize metal empowers them to extract "
            + "essential nutrients from ores and metallic substances,"
            + " establishing them as exceptional miners. ");
        break;
      }
    }
    switch (DiceGenerator.getRandom(1)) {
      default:
      case 0: {
        sb.append(" In addition to ");
        sb.append(namePlural);
        sb.append(" metal-eating abilities, they are also known for their"
            + " strong and durable bodies, which allow them to withstand"
            + " harsh environments and perform physically demanding tasks. ");
        break;
      }
      case 1: {
        sb.append(" Alongside their metal-eating prowess, ");
        sb.append(namePlural);
        sb.append(" are renowned for their robust and resilient bodies,"
            + " granting them the capacity to endure hostile environments"
            + " and undertake physically demanding tasks. ");
        break;
      }
      case 2: {
        sb.append(" Coupled with their metal-eating prowess, ");
        sb.append(namePlural);
        sb.append(" are distinguished by their robust and resilient bodies,"
            + " enabling them to endure hostile environments and undertake "
            + "physically demanding tasks. ");
        break;
      }
    }
    if (info.getGovernment().isImmuneToHappiness()) {
      switch (DiceGenerator.getRandom(1)) {
        default:
        case 0: {
          sb.append(namePlural);
          sb.append(" are able to communicate which each others on same"
              + " planet mentally. This allows them to coordinate their "
              + "moves and tasks in harmomy. This mental communicating is "
              + "probably due special metal ore they have eaten.");
          break;
        }
        case 1: {
          sb.append("Their mental communication, a product perhaps of "
              + "a special metal ore they have ingested, fosters seamless"
              + " coordination and harmonious task execution among those "
              + "sharing the same planet.");
          break;
        }
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" are a race of creatures that are adapted to life in"
            + " zero gravity. Because of this, they require special suits"
            + " to move on the surfaces of planets, and they spend most of"
            + " their time living and working in orbit. Their bodies are "
            + "mostly just mouth and big bulpy eyes with multiple long limbs"
            + " and a slender tail that help them move gracefully in zero"
            + " gravity. ");
        break;
      }
      case 1: {
        sb.append(" are an extraordinary species uniquely adapted to the"
            + " challenges of zero gravity environments. To navigate "
            + "planetary surfaces, they rely on specialized suits, "
            + "spending the majority of their lives in the weightlessness"
            + " of orbit. With oversized, bulbous eyes and a slender "
            + "tail complemented by multiple graceful limbs, ");
        sb.append(namePlural);
        sb.append(" move with unparalleled agility in zero gravity. ");
        break;
      }
      case 2: {
        sb.append(" are a unique species adapted to thrive in the"
            + " weightlessness of zero gravity environments. As a result,"
            + " they rely on specialized suits to navigate planetary "
            + "surfaces, spending the majority of their lives working and "
            + "residing in orbit. These beings possess distinct physical"
            + " features, characterized by oversized mouths, bulging eyes,"
            + " elongated limbs, and slender tails that enable them to move"
            + " with grace in zero gravity. ");
        break;
      }
    }
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(namePlural);
        sb.append(" are known for their advanced orbital technology,"
          + " which allows them to build and maintain large,"
          + " complex structures in space. ");
        break;
      }
      case 1: {
        sb.append("Their expertise lies in advanced orbital technology,"
            + " enabling the construction and maintenance of expansive,"
            + " intricate structures in the vast expanse of space. ");
        break;
      }
      case 2: {
        sb.append("Renowned for their advanced orbital technology, ");
        sb.append(namePlural);
        sb.append(" excel in constructing and maintaining intricate "
            + "structures in the vast expanse of space. ");
        break;
      }
    }
    if (info.getGovernment().isImmuneToHappiness()) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(namePlural);
          sb.append(" are able to mentally communicate which each others on"
              + " same planet. This allows them to organize their moves"
              + " and tasks in effectively. This mental communicating is"
              + " probably due spending so much time in zero gravity. ");
          break;
        }
        case 1: {
          sb.append("Through a remarkable mental connection, ");
          sb.append(namePlural);
          sb.append(" on the same planet synchronize their actions and"
              + " tasks seamlessly, a skill honed from extensive "
              + "exposure to zero gravity environments. ");
          break;
        }
        case 2: {
          sb.append("Additionally, ");
          sb.append(namePlural);
          sb.append(" possess a remarkable ability to engage in mental"
              + " communication with one another when on the same planet,"
              + " allowing for efficient organization and coordination of"
              + " tasks—a skill likely honed through their extensive "
              + "exposure to zero gravity. ");
          break;
        }
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    if (info.getEmpireName().contains("Taurus")) {
      namePlural = "Taurians";
      name = "Taurian";
    }
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(3)) {
      default:
      case 0: {
        sb.append(" are similar to the mythical centaurs of ancient Greek and"
            + " Roman mythology. Like their mythical counterparts, ");
        sb.append(namePlural);
        sb.append(" are quadrupeds with the upper body of humanoid like and"
            + " lower body resembles ant like. They also have very though"
            + " skin and they are huge in size.");
        break;
      }
      case 1: {
        sb.append(", reminiscent of the mythical beings from ancient"
            + " Greek and Roman lore, bear a striking resemblance to "
            + "their legendary counterparts. They possess the upper "
            + "body of a humanoid, juxtaposed with a lower body "
            + "reminiscent of an ant. Their formidable, resilient skin"
            + " and towering stature further distinguish them.");
        break;
      }
      case 2: {
        sb.append("mirror the mythical centaurs of ancient Greek and"
            + " Roman lore, boasting a quadrupedal form with a "
            + "humanoid upper body and an ant-like lower half. Their"
            + " impressive stature and tough, resilient skin set them apart.");
        break;
      }
      case 3: {
        sb.append(", akin to the legendary centaurs of ancient Greek"
            + " and Roman mythology, strikingly resemble their "
            + "mythical counterparts. They possess the upper body of"
            + " a humanoid, coupled with a lower body reminiscent "
            + "of an ant, boasting imposing physicality and remarkably"
            + " tough skin.");
        break;
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append("\n\n");
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append("Because of their large size, ");
        break;
      }
      case 1: {
        sb.append("Given their prodigious size, ");
        break;
      }
      case 2: {
        sb.append("Given their substantial size, ");
        break;
      }
    }
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(3)) {
    default:
    case 0: {
      sb.append(" would need sturdy and rigid spaceships in order to support "
          + "their weight and allow them to travel through the vacuum of "
          + "space. ");
      break;
    }
    case 1: {
      sb.append(" necessitated robust, unyielding spacecraft capable of"
          + " supporting their weight, enabling them to traverse the"
          + " cosmic void. ");
      break;
    }
    case 2: {
      sb.append(" required spaceships of robust and rigid design, "
          + "capable of bearing their weight and traversing the "
          + "vast void of space. ");
      break;
    }
    case 3: {
      sb.append(" necessitated spacecraft that were robust and unyielding,"
          + " capable of bearing their weight as they traversed "
          + "the cosmic void. ");
      break;
    }
  }
    sb.append("\n\n");
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append(namePlural);
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" are race of sentient insects that are capable of forming"
              + " a hivemind. This allows them to coordinate their actions and"
              + " work together as a highly efficient collective. ");
          break;
        }
        case 1: {
          sb.append(" are a remarkable race of sentient insects, "
              + "distinguished by their extraordinary ability to form"
              + " a hivemind. This collective consciousness empowers them "
              + "to synchronize their efforts, operating as a seamlessly "
              + "efficient entity. ");
          break;
        }
        case 2: {
          sb.append(", a remarkable race of sentient insects,"
              + " possess the extraordinary ability to form a seamless "
              + "hivemind, enabling them to synchronize their efforts and"
              + " function as a supremely efficient collective. ");
          break;
        }
      }
    }
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(namePlural);
        sb.append(" are known for their fast breeding, which allows them to "
          + "quickly increase their numbers and expand their territory. ");
        break;
      }
      case 1: {
        sb.append(namePlural);
        sb.append(" are renowned for their rapid reproductive rates,"
            + " enabling them to swiftly bolster their population and"
            + " expand their dominion. ");
        break;
      }
      case 2: {
        if (info.getGovernment().isImmuneToHappiness()) {
          sb.append("Their ");
        } else {
          sb.append(namePlural);
        }
        sb.append(" reputation for rapid reproduction allows them to "
            + "swiftly bolster their population and extend their dominion. ");
        break;
      }
    }
    if (info.getGovernment().getDiplomaticBonus() > 0) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(namePlural);
          sb.append(" are also known for their hypnotic song, which is used for"
              + " communication and ritual purposes. This ability grants them"
              + " a cultural bonus in their interactions with other races. ");
          break;
        }
        case 1: {
          sb.append(namePlural);
          sb.append(" are also known for their hypnotic song, which is "
              + "used for communication and ritual purposes. This "
              + "ability grants them a cultural bonus in their "
              + "interactions with other races. ");
          break;
        }
        case 2: {
          sb.append("Moreover, the ");
          sb.append(namePlural);
          sb.append(" possess a mesmerizing vocal talent, employed for"
              + " both communication and sacred rituals. This unique "
              + "ability bestows upon them a cultural advantage in "
              + "their interactions with other races. ");
          break;
        }
      }
    }
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append("However, their exoskeletons are relatively weak, which gives"
          + " them a negative bonus when it comes to mining and troop power.");
        break;
      }
      case 1: {
        sb.append("However, their exoskeletal structures, while resilient "
            + "enough for their natural environment, prove suboptimal for "
            + "tasks such as mining and military endeavors,"
            + " imposing a disadvantage. ");
        break;
      }
      case 2: {
        sb.append("However, their exoskeletons, while serving as a protective"
            + " shell, are comparatively fragile, resulting in a drawback in "
            + "tasks such as mining and military might. ");
        break;
      }
    }
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" Despite this weakness, ");
        sb.append(namePlural);
        sb.append(" are able to compensate for their lack of physical strength "
            + "with their intelligence and cooperation.");
        break;
      }
      case 1: {
        sb.append(" Despite these physical limitations, ");
        sb.append(namePlural);
        sb.append(" adeptly compensate with their inherent intelligence "
            + "and penchant for collaboration.");
        break;
      }
      case 2: {
        sb.append(" Nevertheless, the ");
        sb.append(namePlural);
        sb.append(" compensate for this physical limitation with"
            + " their intellectual prowess and remarkable"
            + " cooperative instincts.");
        break;
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    String namePlural = getRaceNameInPlural(info.getEmpireName(),
        info.getGovernment());
    String name = getRaceNameInSingle(info.getEmpireName(),
        info.getGovernment());
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" are known for their versatile cargo ships, which can be "
            + "outfitted with a variety of modules to accommodate different"
            + " types of cargo. Some of these ships even have weapons or"
            + " privateering modules, allowing them to defend themselves"
            + " against hostile forces or engage in piracy."
            + " Despite their illicit activities, the");
        break;
      }
      case 1: {
        sb.append(" are renowned for their ingenious cargo ships,"
            + " highly adaptable to diverse types of cargo through"
            + " modular customization. Some vessels even boast weaponry"
            + " and privateering modules, equipping them to defend "
            + "against hostilities or partake in piracy."
            + " Despite these illicit pursuits, the ");
        break;
      }
      case 2: {
        sb.append(" renowned for their versatile cargo ships, are known to "
            + "customize their vessels with an array of modules tailored "
            + "to different types of cargo. Some of these ships are "
            + "even equipped with weaponry and privateering modules, "
            + "enabling them to fend off hostile forces or engage in "
            + "acts of piracy. Despite their involvement in illicit "
            + "activities, the");
        break;
      }
    }
    sb.append(namePlural);
    switch (DiceGenerator.getRandom(2)) {
      default:
      case 0: {
        sb.append(" are often depicted as being honorable and loyal to their"
          + " friends and allies. They are also known for their"
          + " resourcefulness and ingenuity, which allows them to overcome"
          + " obstacles and succeed in their dangerous line of work. ");
        break;
      }
      case 1: {
        sb.append(" are often portrayed as honorable and fiercely loyal to"
            + " their comrades and allies. Their reputation also stems"
            + " from their exceptional resourcefulness and ingenuity, "
            + "which empower them to surmount obstacles and thrive in their"
            + " perilous line of work. ");
        break;
      }
      case 2: {
        sb.append(" are often portrayed as possessing a code of honor and"
            + " unwavering loyalty to their comrades and allies. Their"
            + " reputation is further bolstered by their innate "
            + "resourcefulness and ingenuity, which empowers them to "
            + "surmount challenges and thrive within their"
            + " perilous profession. ");
        break;
      }
    }
    if (info.getGovernment().isImmuneToHappiness()) {
      sb.append("\n\n");
      switch (DiceGenerator.getRandom(3)) {
        default:
        case 0: {
          sb.append("The ");
          sb.append(namePlural);
          sb.append(" are acting as space pirates. They will keep looting and"
             + " pillaging ships and do not care about consequences. ");
          break;
        }
        case 1: {
          sb.append("The ");
          sb.append(namePlural);
          sb.append(" have embraced the life of spacefaring pirates, "
              + "showing an unwavering commitment to their relentless "
              + "pursuit of looting and pillaging ships, heedless of "
              + "the repercussions. Their audacity knows no bounds as "
              + "they navigate the cosmic expanse, leaving a trail of"
              + " plundered vessels in their wake, indifferent to the "
              + "consequences that may follow. ");
          break;
        }
        case 2: {
          sb.append("The ");
          sb.append(namePlural);
          sb.append(" have embraced the role of spacefaring pirates,"
              + " engaging in relentless looting and pillaging of ships "
              + "without regard for the repercussions. ");
          break;
        }
        case 3: {
          sb.append("The ");
          sb.append(namePlural);
          sb.append(" have taken on the role of spacefaring pirates,"
              + " relentless in their pursuit of looting and pillaging"
              + " ships, seemingly indifferent to the repercussions of"
              + " their actions. ");
          break;
        }
      }
    }
    sb.append("\n\n");
    sb.append(generateWorldType(info, startPlanet, namePlural, name));
    sb.append("\n\n");
    sb.append(generateGovernmentType(info, namePlural));
    sb.append("\n\n");
    sb.append(generateFtlStory(info, startingYear, namePlural));
    sb.append(generateExploration(info, namePlural, startPlanet));
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
    if (scientific) {
      Gender gender = DiceGenerator.pickRandom(info.getRace().getGenders());
      String greatLeader = NameGenerator.generateName(info.getRace(),
          gender);
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
    if (startPlanet.getPlanetType().getWorldType() == WorldType.CARBONWORLD) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" a carbon-rich planet with full of life.");
          break;
        }
        case 1: {
          sb.append(" a carbon-rich planet teeming with life.");
          break;
        }
        case 2: {
          sb.append(" a carbon-rich planet where life was flourishing.");
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
    if (startPlanet.getPlanetType().getWorldType() == WorldType.IRONWORLD) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" an iron world.");
          break;
        }
        case 1: {
          sb.append(" a lava flowing iron world.");
          break;
        }
        case 2: {
          sb.append(" a hot iron world.");
          break;
        }
      }
      sb.append(" iron world.");
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
    if (startPlanet.getPlanetType().getWorldType() == WorldType.SILICONWORLD) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          sb.append(" a silicon world.");
          break;
        }
        case 1: {
          sb.append(" a deadly looking silicon world.");
          break;
        }
        case 2: {
          sb.append(" an empty silicon world.");
          break;
        }
      }
      sb.append(" a silicon world.");
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
