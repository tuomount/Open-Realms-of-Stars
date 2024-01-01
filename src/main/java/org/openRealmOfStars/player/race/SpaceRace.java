package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openRealmOfStars.ambient.BridgeCommandType;
import org.openRealmOfStars.audio.music.MusicFileInfo;
import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.gui.util.GuiStatics;
import org.openRealmOfStars.player.PlayerColor;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.leader.Gender;
import org.openRealmOfStars.player.race.trait.RaceTrait;
import org.openRealmOfStars.player.race.trait.TraitFactory;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.starMap.planet.enums.GravityType;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;
import org.openRealmOfStars.starMap.planet.enums.TemperatureType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 * Space races in enum
 */
public enum SpaceRace {

  /**
   * Humans are about average in everything.
   */
  HUMAN(0, "Humans", "Human",
        "Humans are great diplomats but\n"
      + "they are about average in everything else."),
  /**
   * Mechanical beings whom do not eat food. Each now population must be built.
   */
  MECHIONS(1, "Mechions", "Mechion",
          "Mechanical beings whom do not eat food.\n"
        + "Each population must be built."),
  /**
   * Aggressive and warmongering spieces.
   */
  SPORKS(2, "Sporks", "Spork", "Aggressive and warmongering species."),
  /**
   * Humanoid creatures with grey skin and big eyes.
   * Greyan are excellent researchers.
   */
  GREYANS(3, "Greyans", "Greyan", "Humanoid creatures with grey skin and"
          + " big eyes.\nGreyan are excellent researchers."),
  /**
   * Quadrupedal humanoid creatures which are big, about 5 meters tall. Due
   * their enormous size their space ships are must more rigid.
   */
  CENTAURS(4, "Centaurs", "Centaur",
            "Quadrupedal humanoid creatures which are big,\n"
          + "about 5 meters tall. Due their enormous size their space\n"
          + "ships are more rigid. "),
  /**
   * Mothoids are sentient insects with hivemind. They are fast breeding race.
   */
  MOTHOIDS(5, "Mothoids", "Mothoid",
        "Mothoids are sentient insects with capability to hivemind.\n"
      + "They are fast breeding race. Their song is hypnotic so\n"
      + "cultural bonus is granted. Mothoids exo-skeleton is weak\n"
      + "and therefore get negative bonus on mining and troop power."),
  /**
   * Teuthidaes are octopus like creatures.
   * They are scientific and military focused race. Their ships have built-in
   * cloaking devices.
   */
  TEUTHIDAES(6, "Teuthidaes", "Teuthidae",
        "Teuthidaes are octopus like creatures. They are scientific\n"
      + "and military focused race. Their ships have built-in\n"
      + "cloaking devices."),
  /**
   * Scaurians are small but wide humanoid.
   * They are merchantical race. They focus make better trades with other and
   * gain more credits.
   */
  SCAURIANS(7, "Scaurians", "Scaurian",
          "Scaurians are small but wide humanoid. They are merchantical\n"
        + "race. They focus make better trades with other and gain more\n"
        + "credits."),
  /**
   * Homarians are very strong creatures. They have humanoid form but
   * they have very thick and hard exoskeleton.
   */
  HOMARIANS(8, "Homarians", "Homarian",
          "Homarians are very strong creatures. They have humanoid\n"
        + "form but they have very thick and hard exoskeleton.\n"
        + "Due their strength they are good in physical tasks."),
  /**
   * Humans are about average in everything and space pirates are probably
   * humans or are just average.
   */
  SPACE_PIRATE(9, "Space Pirates", "Space pirate",
         "Generic space pirate which are aggressive and try cause\n"
       + " trouble. Stats are identical to humans."),
  /**
   * Chiraloids are creatures with four arms and two legs. They have hard
   * exoskeleton. They also have special gland which uses radioactivity
   * to create nutrient. This is called radiosynthesis.
   */
  CHIRALOIDS(10, "Chiraloids", "Chiraloid",
          "Chiraloids are creatures with four arms and two legs.\n"
        + "They have hard exoskeleton. They also have special gland\n"
        + "which uses radioactivity to create nutrient. This is called\n"
        + "radiosynthesis."),
  /**
   * Reborgians are organism combined with organic and robotic
   * parts. So they are cyborgs. They can synthesize any living space race
   * to their own race, so they are fearful conquerors.
   * They need only very little food surviving, but their
   * reproduction is very slow.
   */
  REBORGIANS(11, "Reborgians", "Reborgian",
      "Reborgians are organism combined with bionic and robotic "
      + "parts. So they are cyborgs. They can synthesize any living space race"
      + " to their own race, so they are fearful conquerors. They need only"
      + " very little food surviving, but their reproduction is very slow."),
  /**
   * Lithorians are creatures that eat metal instead of food. They have slow
   * grow rate and they have -2 population limit. They have
   * excellent ability to mine metal.
   */
  LITHORIANS(12, "Lithorians", "Lithorian",
      "Lithorians are creatures that eat metal instead of food. They have"
      + " slow grow rate and they have -2 population limit."
      + " They have excellent ability to mine metal."),
  /**
   * Alteirians are creatures that live in zero gravity. Because of this they
   * need special suites to move planet surface. Most of their time they
   * live on planet's orbit. Their orbitals are more improved than other
   * space race.
   */
  ALTEIRIANS(13, "Alteirians", "Alteirian",
      "Alteirians are creatures that live in zero gravity. Because of this they"
      + " need special suites to move planet surface. Most of their time they"
      + " live on planet's orbit. Their orbitals are more improved than other"
      + " space race."),
  /**
   * Smaugirians are humanoids that are known for smuggling goods.
   * Their cargo ships can contain single weapon or privateering modules.
   */
  SMAUGIRIANS(14, "Smaugirians", "Smaugirian",
      "Smaugirians are humanoids that are known for smuggling goods."
      + " Their cargo ships can contain single weapon"
      + " or privateering modules."),
  /**
   * Humans are about average in everything and space pirates are probably
   * humans or are just average.
   */
  SPACE_MONSTERS(15, "Space Monsters", "Space monsters",
         "Generic space monsters which are aggressive and try cause\n"
       + " trouble. No diplomacy with these guys."),
  /**
   * Artificial beings that eat only small amount of food.
   * Each population must be built. Alternative name Huskdroid.
   */
  SYNTHDROIDS(16, "Synthdroids", "Synthdroid", "Artificial beings that eat only"
      + " small amount of food. Each population must be built.");

  /**
   * Explicitly (re)initialize SpaceRaces.
   * Needed, because RaceTraits are loaded later than SpaceRaces.
   * Trying to use TraitFactory in an enum constructor
   * will result in a critical Java failure.
   * TODO: Remove when SpaceRaces get dehardcoded.
   */
  public static void initialize() {
    // Clear traits from races.
    for (var race : values()) {
      race.traits.clear();
    }

    // Add traits *that exist* to hardcoded list of races
    TraitFactory.create(TraitIds.ROBOTIC).ifPresent(trait -> {
      MECHIONS.addTrait(trait);
      SYNTHDROIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.LITHOVORIC).ifPresent(trait -> {
      LITHORIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.ENERGY_POWERED).ifPresent(trait -> {
      MECHIONS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.NO_HEIRS).ifPresent(trait -> {
      MECHIONS.addTrait(trait);
      REBORGIANS.addTrait(trait);
      SYNTHDROIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.CONSTRUCTED_POP).ifPresent(trait -> {
      MECHIONS.addTrait(trait);
      SYNTHDROIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.RADIOSYNTHESIS).ifPresent(trait -> {
      CHIRALOIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.MERCANTILE).ifPresent(trait -> {
      SCAURIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.STEALTHY).ifPresent(trait -> {
      TEUTHIDAES.addTrait(trait);
    });
    TraitFactory.create(TraitIds.EAT_LESS).ifPresent(trait -> {
      REBORGIANS.addTrait(trait);
      SYNTHDROIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.FAST_GROWTH).ifPresent(trait -> {
      MOTHOIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.SLOW_GROWTH).ifPresent(trait -> {
      GREYANS.addTrait(trait);
      CENTAURS.addTrait(trait);
      SCAURIANS.addTrait(trait);
      CHIRALOIDS.addTrait(trait);
      REBORGIANS.addTrait(trait);
      LITHORIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.FIXED_GROWTH).ifPresent(trait -> {
      REBORGIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.SLOW_CULTURE).ifPresent(trait -> {
      MECHIONS.addTrait(trait);
      HOMARIANS.addTrait(trait);
      REBORGIANS.addTrait(trait);
      SYNTHDROIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.FAST_CULTURE).ifPresent(trait -> {
      MOTHOIDS.addTrait(trait);
      ALTEIRIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.SLOW_RESEARCH).ifPresent(trait -> {
      MECHIONS.addTrait(trait);
      HOMARIANS.addTrait(trait);
      CHIRALOIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.FAST_RESEARCH).ifPresent(trait -> {
      GREYANS.addTrait(trait);
      TEUTHIDAES.addTrait(trait);
    });
    TraitFactory.create(TraitIds.VERY_FAST_RESEARCH).ifPresent(trait -> {
      ALTEIRIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.SHORT_LIFE_SPAN).ifPresent(trait -> {
      SPORKS.addTrait(trait);
      MOTHOIDS.addTrait(trait);
      HOMARIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.LONG_LIFE_SPAN).ifPresent(trait -> {
      SCAURIANS.addTrait(trait);
      ALTEIRIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.VERY_LONG_LIFE_SPAN).ifPresent(trait -> {
      GREYANS.addTrait(trait);
      CENTAURS.addTrait(trait);
      LITHORIANS.addTrait(trait);
      CHIRALOIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.SLOW_METABOLISM).ifPresent(trait -> {
      GREYANS.addTrait(trait);
      LITHORIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.MASSIVE_SIZE).ifPresent(trait -> {
      CENTAURS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.CYBORG_LIFE_SPAN).ifPresent(trait -> {
      REBORGIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.FAST_FOOD_PROD).ifPresent(trait -> {
      HOMARIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.ASSIMILATION).ifPresent(trait -> {
      REBORGIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.EXCELLENT_WAR_RESILIENCE).ifPresent(trait -> {
      CENTAURS.addTrait(trait);
      LITHORIANS.addTrait(trait);
      SMAUGIRIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.GOOD_WAR_RESILIENCE).ifPresent(trait -> {
      HUMAN.addTrait(trait);
      TEUTHIDAES.addTrait(trait);
      REBORGIANS.addTrait(trait);
      SPACE_PIRATE.addTrait(trait);
      SPACE_MONSTERS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.POOR_WAR_RESILIENCE).ifPresent(trait -> {
      GREYANS.addTrait(trait);
      SCAURIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.WEAK_WAR_RESILIENCE).ifPresent(trait -> {
      MOTHOIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.NATURAL_CHARM).ifPresent(trait -> {
      HUMAN.addTrait(trait);
      SCAURIANS.addTrait(trait);
      HOMARIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.REPULSIVE).ifPresent(trait -> {
      MECHIONS.addTrait(trait);
      SPORKS.addTrait(trait);
      TEUTHIDAES.addTrait(trait);
      CHIRALOIDS.addTrait(trait);
      LITHORIANS.addTrait(trait);
      SMAUGIRIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.DISGUSTING).ifPresent(trait -> {
      REBORGIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.STRONG).ifPresent(trait -> {
      MECHIONS.addTrait(trait);
      SPORKS.addTrait(trait);
      CENTAURS.addTrait(trait);
      HOMARIANS.addTrait(trait);
      REBORGIANS.addTrait(trait);
      LITHORIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.WEAK).ifPresent(trait -> {
      GREYANS.addTrait(trait);
      MOTHOIDS.addTrait(trait);
      CHIRALOIDS.addTrait(trait);
      ALTEIRIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.HANDY).ifPresent(trait -> {
      HOMARIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.IMPRACTICAL).ifPresent(trait -> {
      CHIRALOIDS.addTrait(trait);
      ALTEIRIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.HIGH_GRAVITY_BEING).ifPresent(trait -> {
      MECHIONS.addTrait(trait);
      CENTAURS.addTrait(trait);
      HOMARIANS.addTrait(trait);
      LITHORIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.LOW_GRAVITY_BEING).ifPresent(trait -> {
      MOTHOIDS.addTrait(trait);
      GREYANS.addTrait(trait);
      SCAURIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.ZERO_GRAVITY_BEING).ifPresent(trait -> {
      ALTEIRIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.TOLERATE_LOW_RADIATION).ifPresent(trait -> {
      HUMAN.addTrait(trait);
      SPACE_PIRATE.addTrait(trait);
      SPACE_MONSTERS.addTrait(trait);
      SPORKS.addTrait(trait);
      SCAURIANS.addTrait(trait);
      REBORGIANS.addTrait(trait);
      TEUTHIDAES.addTrait(trait);
      SMAUGIRIANS.addTrait(trait);
      SYNTHDROIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.TOLERATE_HIGH_RADIATION).ifPresent(trait -> {
      GREYANS.addTrait(trait);
      ALTEIRIANS.addTrait(trait);
      LITHORIANS.addTrait(trait);
      MOTHOIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.TOLERATE_VERY_HIGH_RADIATION).ifPresent(
        trait -> {
      MECHIONS.addTrait(trait);
      CHIRALOIDS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.TOLERATE_COLD).ifPresent(
        trait -> {
      HUMAN.addTrait(trait);
      CENTAURS.addTrait(trait);
      SCAURIANS.addTrait(trait);
      HOMARIANS.addTrait(trait);
      TEUTHIDAES.addTrait(trait);
    });
    TraitFactory.create(TraitIds.TOLERATE_HOT).ifPresent(
        trait -> {
      MECHIONS.addTrait(trait);
      REBORGIANS.addTrait(trait);
    });
    TraitFactory.create(TraitIds.TOLERATE_LAVA).ifPresent(
        trait -> {
      LITHORIANS.addTrait(trait);
    });
  }

  /**
   * Create space race
   * @param index Space race index
   * @param name Space race name in plural
   * @param single Space race name in single format
   * @param description Space race  description
   * @param traits Race's traits
   */
  SpaceRace(final int index, final String name, final String single,
      final String description, final RaceTrait... traits) {
    this.index = index;
    this.name = name;
    this.nameSingle = single;
    this.description = description;
    this.traits = new ArrayList<>(traits.length);
    for (var trait : traits) {
      addTrait(trait);
    }
  }

  /**
   * Space race index
   */
  private int index;

  /**
   * Space race name in plural
   */
  private String name;

  /**
   * Space race name in single
   */
  private String nameSingle;

  /**
   * Get race name in single format
   * @return Race single name
   */
  public String getNameSingle() {
    return nameSingle;
  }

  /**
   * Space race description
   */
  private String description;

  /** Race traits */
  private ArrayList<RaceTrait> traits;

  /**
   * Add RaceTrait to SpaceRace.
   * Throws IllegalArgumentException if adding trait would result
   * in conflicting or duplicate traits.
   * @param trait RaceTrait to add to SpaceRace
   */
  private void addTrait(final RaceTrait trait) {
    var traitArray = this.traits.toArray(new RaceTrait[this.traits.size()]);
    if (RaceTrait.isTraitConflict(trait, traitArray)) {
      throw new IllegalArgumentException(
          "Cannot add RaceTrait because of conflict");
    }
    traits.add(trait);
  }

  /**
   * Return true if race has trait specified by ID.
   * @param traitId RaceTrait ID
   * @return True if race has specified trait
   */
  public boolean hasTrait(final String traitId) {
    for (var trait : traits) {
      if (trait.getId().equals(traitId)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get scientist research speed
   * @return The research speed
   */
  public int getResearchSpeed() {
    int result = 100;
    if (hasTrait(TraitIds.SLOW_RESEARCH)) {
      result = 50;
    }
    if (hasTrait(TraitIds.FAST_RESEARCH)) {
      result = 150;
    }
    if (hasTrait(TraitIds.VERY_FAST_RESEARCH)) {
      result = 200;
    }
    return result;
  }

  /**
   * Get attitude from space race. AI players have two attitudes:
   * one from SpaceRace and one is random when game in
   * being created.
   * @return Attitude
   */
  public Attitude getAttitude() {
    switch (this) {
      case HUMAN:
        return Attitude.DIPLOMATIC;
      case SYNTHDROIDS:
      case MECHIONS:
        return Attitude.LOGICAL;
      case SPORKS:
      case SPACE_PIRATE:
      case SPACE_MONSTERS:
        return Attitude.AGGRESSIVE;
      case GREYANS:
        return Attitude.SCIENTIFIC;
      case CENTAURS:
        return Attitude.DIPLOMATIC;
      case MOTHOIDS:
        return Attitude.EXPANSIONIST;
      case TEUTHIDAES:
        return Attitude.MILITARISTIC;
      case SCAURIANS:
        return Attitude.MERCHANTICAL;
      case HOMARIANS:
        return Attitude.PEACEFUL;
      case CHIRALOIDS:
        return Attitude.AGGRESSIVE;
      case REBORGIANS:
        return Attitude.AGGRESSIVE;
      case LITHORIANS:
        return Attitude.EXPANSIONIST;
      case ALTEIRIANS:
        return Attitude.SCIENTIFIC;
      case SMAUGIRIANS:
        return Attitude.MILITARISTIC;
      default:
        return Attitude.PEACEFUL;
    }
  }

  /**
   * Get race maximum Radiation
   * @return The race maximum radiation
   */
  public RadiationType getMaxRad() {
    RadiationType result = RadiationType.NO_RADIATION;
    if (hasTrait(TraitIds.TOLERATE_LOW_RADIATION)) {
      result = RadiationType.LOW_RADIATION;
    }
    if (hasTrait(TraitIds.TOLERATE_HIGH_RADIATION)) {
      result = RadiationType.HIGH_RADIATION;
    }
    if (hasTrait(TraitIds.TOLERATE_VERY_HIGH_RADIATION)) {
      result = RadiationType.VERY_HIGH_RAD;
    }
    return result;
  }

  /**
   * Get race image
   * @return BufferedImage
   */
  public BufferedImage getRaceImage() {
    switch (this) {
    case HUMAN:
      return GuiStatics.IMAGE_HUMAN_RACE;
    case MECHIONS:
      return GuiStatics.IMAGE_MECHION_RACE;
    case SPORKS:
      return GuiStatics.IMAGE_SPORK_RACE;
    case GREYANS:
      return GuiStatics.IMAGE_GREYAN_RACE;
    case CENTAURS:
      return GuiStatics.IMAGE_CENTAUR_RACE;
    case MOTHOIDS:
      return GuiStatics.IMAGE_MOTHOID_RACE;
    case TEUTHIDAES:
      return GuiStatics.IMAGE_TEUTHIDAE_RACE;
    case SCAURIANS:
      return GuiStatics.IMAGE_SCAURIAN_RACE;
    case HOMARIANS:
      return GuiStatics.IMAGE_HOMARIAN_RACE;
    case SPACE_MONSTERS: // No diplomacy so image should not matter
    case SPACE_PIRATE:
      return GuiStatics.IMAGE_PRIVATEER_RACE;
    case CHIRALOIDS:
      return GuiStatics.IMAGE_CHIRALOID_RACE;
    case REBORGIANS:
      return GuiStatics.IMAGE_REBORGIAN_RACE;
    case LITHORIANS:
      return GuiStatics.IMAGE_LITHORIAN_RACE;
    case ALTEIRIANS:
      return GuiStatics.IMAGE_ALTEIRIAN_RACE;
    case SMAUGIRIANS:
      return GuiStatics.IMAGE_SMAUGIRIAN_RACE;
    case SYNTHDROIDS:
      return GuiStatics.IMAGE_SYNTHDROID_RACE;
    default:
      return GuiStatics.IMAGE_CENTAUR_RACE;
    }
  }

  /**
   * Get race picture to wiki page with correct path
   * @return Path to image in master
   */
  private String getRaceImagePathToWiki() {
    String start = "https://github.com/tuomount/Open-Realms-of-Stars/blob"
        + "/master/src/main/resources/";
    switch (this) {
      case HUMAN:
        return start + "resources/images/human_race.png";
      case MECHIONS:
        return start + "resources/images/mechion_race.png";
      case SPORKS:
        return start + "resources/images/spork_race.png";
      case GREYANS:
        return start + "resources/images/greyan_race.png";
      case CENTAURS:
        return start + "resources/images/centaur_race.png";
      case MOTHOIDS:
        return start + "resources/images/mothoid_race.png";
      case TEUTHIDAES:
        return start + "resources/images/teuthidae_race.png";
      case SCAURIANS:
        return start + "resources/images/scaurian_race.png";
      case HOMARIANS:
        return start + "resources/images/homarian_race.png";
      case SPACE_MONSTERS: // Should not matter
      case SPACE_PIRATE:
        return start + "resources/images/privateer_race.png";
      case CHIRALOIDS:
        return start + "resources/images/chiraloid_race.png";
      case REBORGIANS:
        return start + "resources/images/reborgian_race.png";
      case LITHORIANS:
        return start + "resources/images/lithorian_race.png";
      case ALTEIRIANS:
        return start + "resources/images/alteirian_race.png";
      case SMAUGIRIANS:
        return start + "resources/images/smaugirian_race.png";
      case SYNTHDROIDS:
        return start + "resources/images/synthdroid_race.png";
      default:
        return start + "resources/images/centaur_race.png";
    }
  }
  /**
   * @return the index
   */
  public int getIndex() {
    return index;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Get miners mining speed based on gravity.
   * If gravity is null then just return production speed bonus.
   * @param gravity GravityType or null.
   * @return normal 100, half 50, double 200
   */
  public int getMiningSpeed(final GravityType gravity) {
    var result = 100;
    if (hasTrait(TraitIds.STRONG)) {
      result += 50;
    }
    if (hasTrait(TraitIds.WEAK)) {
      result -= 50;
    }
    if (isLithovorian()) {
      result += 50;
    }
    if (gravity == null) {
      // Just returning mining speed bonus
      return result;
    }
    if (hasTrait(TraitIds.ZERO_GRAVITY_BEING)) {
      result -= 50;
    }
    if (hasTrait(TraitIds.LOW_GRAVITY_BEING)
        && (gravity == GravityType.NORMAL_GRAVITY
        || gravity == GravityType.HIGH_GRAVITY)) {
      result -= 50;
    }
    if (hasTrait(TraitIds.HIGH_GRAVITY_BEING)
        && (gravity == GravityType.NORMAL_GRAVITY
        || gravity == GravityType.LOW_GRAVITY)) {
      result += 50;
    }
    return result;
  }

  /**
   * Get artists culture speed
   * @return normal 100, half 50, double 200
   */
  public int getCultureSpeed() {
    int result = 100;
    if (hasTrait(TraitIds.SLOW_CULTURE)) {
      result = 50;
    }
    if (hasTrait(TraitIds.FAST_CULTURE)) {
      result = 150;
    }
    return result;
  }

  /**
   * Get Trooper power
   * @return normal 10
   */
  public int getTrooperPower() {
    var result = 10;
    if (hasTrait(TraitIds.STRONG)) {
      result += 2;
    }
    if (hasTrait(TraitIds.WEAK)) {
      result -= 2;
    }
    if (hasTrait(TraitIds.SLOW_METABOLISM)) {
      result -= 1;
    }
    if (hasTrait(TraitIds.MASSIVE_SIZE)) {
      result += 2;
    }
    return result;
  }

  /**
   * Get worker production speed based on gravity.
   * If gravity is null then just return production speed bonus.
   * @param gravity GravityType or null.
   * @return normal 100, half 50, double 200
   */
  public int getProductionSpeed(final GravityType gravity) {
    var result = 100;
    if (hasTrait(TraitIds.HANDY)) {
      result += 50;
    }
    if (hasTrait(TraitIds.IMPRACTICAL)) {
      result -= 50;
    }
    if (gravity == null) {
      // Just returning production speed bonus
      return result;
    }
    if (hasTrait(TraitIds.ZERO_GRAVITY_BEING)
        && (gravity == GravityType.NORMAL_GRAVITY
        || gravity == GravityType.HIGH_GRAVITY)) {
      result -= 50;
    }
    if (hasTrait(TraitIds.LOW_GRAVITY_BEING)
        && gravity == GravityType.HIGH_GRAVITY) {
      result -= 50;
    }
    if (hasTrait(TraitIds.HIGH_GRAVITY_BEING)
            && gravity == GravityType.LOW_GRAVITY) {
      result += 50;
    }
    return result;
  }

  /**
   * Get population growth speed
   * @return normal 100, half 50, double 200
   */
  public int getGrowthSpeed() {
    int result = 100;
    if (hasTrait(TraitIds.CONSTRUCTED_POP)) {
      result = 0;
    }
    if (hasTrait(TraitIds.SLOW_GROWTH)) {
      result = 50;
    }
    if (hasTrait(TraitIds.FAST_GROWTH)) {
      result = 150;
    }
    return result;
  }

  /**
   * Get Food growth speed based on gravity.
   * If gravity is null then just return production speed bonus
   * @param gravity GravityType or null.
   * @return normal 100, half 50, double 200
   */
  public int getFoodSpeed(final GravityType gravity) {
    int result = 100;
    if (!isEatingFood()) {
      result = 0;
    }
    if (hasTrait(TraitIds.FAST_FOOD_PROD)) {
      result = 200;
    }
    if (gravity == null) {
      // Just returning production speed bonus
      return result;
    }
    if (hasTrait(TraitIds.ZERO_GRAVITY_BEING)
        && gravity == GravityType.HIGH_GRAVITY) {
      result -= 50;
    }
    return result;
  }

  /**
   * Get space race default color
   * @return PlayerColor
   */
  public PlayerColor getPrimaryColor() {
    switch (this) {
    case HUMAN:
      return PlayerColor.WHITE;
    case SPACE_MONSTERS:
      return PlayerColor.RED;
    case SPACE_PIRATE:
      return PlayerColor.BLACK;
    case MECHIONS:
      return PlayerColor.ORANGE;
    case SPORKS:
      return PlayerColor.LIME;
    case GREYANS:
      return PlayerColor.BLUE;
    case CENTAURS:
      return PlayerColor.PURPLE;
    case MOTHOIDS:
      return PlayerColor.GREEN;
    case TEUTHIDAES:
      return PlayerColor.GRAY;
    case SCAURIANS:
      return PlayerColor.CYAN;
    case HOMARIANS:
      return PlayerColor.BROWN;
    case CHIRALOIDS:
      return PlayerColor.RED;
    case REBORGIANS:
      return PlayerColor.CHESTNUT;
    case LITHORIANS:
      return PlayerColor.YELLOW;
    case ALTEIRIANS:
      return PlayerColor.SKY;
    case SMAUGIRIANS:
      return PlayerColor.TAN;
    case SYNTHDROIDS:
      return PlayerColor.WHITE;
    default:
      return PlayerColor.CORAL;
    }
  }

  /**
   * Get space race secondary color
   * @return PlayerColor
   */
  public PlayerColor getSecondaryColor() {
    switch (this) {
    case HUMAN:
      return PlayerColor.ROSE;
    case SPACE_MONSTERS:
      return PlayerColor.CORAL;
    case SPACE_PIRATE:
      return PlayerColor.GRAY;
    case MECHIONS:
      return PlayerColor.YELLOW;
    case SPORKS:
      return PlayerColor.GREEN;
    case GREYANS:
      return PlayerColor.SKY;
    case CENTAURS:
      return PlayerColor.PINK;
    case MOTHOIDS:
      return PlayerColor.OLIVE;
    case TEUTHIDAES:
      return PlayerColor.BLACK;
    case SCAURIANS:
      return PlayerColor.BLUE;
    case HOMARIANS:
      return PlayerColor.CORAL;
    case CHIRALOIDS:
      return PlayerColor.CORAL;
    case REBORGIANS:
      return PlayerColor.BLACK;
    case LITHORIANS:
      return PlayerColor.BANANA;
    case ALTEIRIANS:
      return PlayerColor.BLUE;
    case SMAUGIRIANS:
      return PlayerColor.OLIVE;
    case SYNTHDROIDS:
      return PlayerColor.ROSE;
    default:
      return PlayerColor.CORAL;
    }
  }

  /**
   * Get population food requirement
   * @return normal 100, half 50, double 200
   */
  public int getFoodRequire() {
    int result = 100;
    if (!isEatingFood()) {
      result = 0;
    }
    if (hasTrait(TraitIds.EAT_LESS)) {
      result = result / 2;
    }
    return result;
  }

  /**
   * Get diplomacy bonus for race
   * @return normal 0, +2 positive, -2 negative
   */
  public int getDiplomacyBonus() {
    var result = 0;
    if (hasTrait(TraitIds.NATURAL_CHARM)) {
      result += 2;
    }
    if (hasTrait(TraitIds.REPULSIVE)) {
      result -= 2;
    }
    if (hasTrait(TraitIds.DISGUSTING)) {
      result -= 7;
    }
    return result;
  }

  /**
   * Get war fatigue resistance.
   * Very weak 30,
   * Weak 40,
   * Normal 50,
   * Strong 60,
   * Very Strong 70
   * @return War fatigue resistance
   */
  public int getWarFatigueResistance() {
    int result = 50;
    if (hasTrait(TraitIds.EXCELLENT_WAR_RESILIENCE)) {
      result = 70;
    }
    if (hasTrait(TraitIds.GOOD_WAR_RESILIENCE)) {
      result = 60;
    }
    if (hasTrait(TraitIds.POOR_WAR_RESILIENCE)) {
      result = 40;
    }
    if (hasTrait(TraitIds.WEAK_WAR_RESILIENCE)) {
      result = 30;
    }
    return result;
  }

  /**
   * Get how many star years to update defense
   * @return int
   */
  public int getAIDefenseUpdate() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 15;
    case MECHIONS:
      return 15;
    case SPORKS:
      return 10;
    case GREYANS:
      return 16;
    case CENTAURS:
      return 18;
    case MOTHOIDS:
      return 12;
    case TEUTHIDAES:
      return 13;
    case SCAURIANS:
      return 15;
    case HOMARIANS:
      return 16;
    case CHIRALOIDS:
      return 12;
    case REBORGIANS:
      return 10;
    case LITHORIANS:
      return 13;
    case ALTEIRIANS:
      return 10;
    case SMAUGIRIANS:
      return 11;
    case SYNTHDROIDS:
      return 15;
    default:
      return 15;
    }
  }

  /**
   * Get how many star years to explore solar system
   * @return int
   */
  public int getAIExploringAmount() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 50;
    case MECHIONS:
      return 50;
    case SPORKS:
      return 48;
    case GREYANS:
      return 55;
    case CENTAURS:
      return 50;
    case MOTHOIDS:
      return 52;
    case TEUTHIDAES:
      return 55;
    case SCAURIANS:
      return 55;
    case HOMARIANS:
      return 55;
    case CHIRALOIDS:
      return 53;
    case REBORGIANS:
      return 50;
    case LITHORIANS:
      return 52;
    case ALTEIRIANS:
      return 50;
    case SMAUGIRIANS:
      return 52;
    case SYNTHDROIDS:
      return 50;
    default:
      return 50;
    }
  }

  /**
   * Get race specific diplomacy music
   * @return MusicFileInfo
   */
  public MusicFileInfo getDiplomacyMusic() {
    switch (this) {
    case HUMAN:
      return MusicPlayer.WALKING_WITH_POSEIDON;
    case MECHIONS:
      return MusicPlayer.ABANDONED_STEEL_MILL;
    case SPORKS:
      return MusicPlayer.CONQUERORS;
    case GREYANS:
      return MusicPlayer.TROGL;
    case CENTAURS:
      return MusicPlayer.OVE_MELAA_DIPLOMACY;
    case MOTHOIDS:
      return MusicPlayer.FANTASY_CHOIR_2;
    case TEUTHIDAES:
      return MusicPlayer.PRESSURE;
    case SCAURIANS:
      return MusicPlayer.INTERPLANETARY_ODYSSEY;
    case HOMARIANS:
      return MusicPlayer.MALLOGA_BALLING;
    case SPACE_MONSTERS: // No diplomacy so should not matter
    case SPACE_PIRATE:
      return MusicPlayer.SET_FIRE_TO_REALITY;
    case CHIRALOIDS:
      return MusicPlayer.MENACE;
    case REBORGIANS:
      return MusicPlayer.BRAINDEAD;
    case LITHORIANS:
      return MusicPlayer.TECHNODRIVE;
    case ALTEIRIANS:
      return MusicPlayer.SKY_PORTAL;
    case SMAUGIRIANS:
      return MusicPlayer.GUITAR_SONG;
    case SYNTHDROIDS:
      return MusicPlayer.CYBORG;
    default:
      return MusicPlayer.MILLION_LIGHT_YEARS;
    }
  }

  /**
   * Is race eating organic food?
   * @return True if eating organic food
   */
  public boolean isEatingFood() {
    if (isLithovorian()) {
      return false;
    }
    if (hasTrait(TraitIds.ENERGY_POWERED)) {
      return false;
    }

    return true;
  }

  /**
   * Is space race eating metal?
   * @return True if eating metal.
   */
  public boolean isLithovorian() {
    return hasTrait(TraitIds.LITHOVORIC);
  }

  /**
   * Is space race robotic race?
   * @return True if robotic.
   */
  public boolean isRoboticRace() {
    return hasTrait(TraitIds.ROBOTIC);
  }

  /**
   * Extra population for each planet.
   * @return Extra population per planet
   */
  public int getExtraPopulation() {
    if (this == SpaceRace.LITHORIANS) {
      return -2;
    }
    if (this == SpaceRace.CENTAURS) {
      return 2;
    }
    return 0;
  }
  /**
   * Get life span of leaders.
   * This is amount the age leaders will always get.
   * After this there is increasing 1% change over this age to get killed.
   * @return Life span of leaders
   */
  public int getLifeSpan() {
    int result = 80;
    if (hasTrait(TraitIds.SHORT_LIFE_SPAN)) {
      result -= 10;
    }
    if (hasTrait(TraitIds.LONG_LIFE_SPAN)) {
      result += 10;
    }
    if (hasTrait(TraitIds.VERY_LONG_LIFE_SPAN)) {
      result += 20;
    }
    if (hasTrait(TraitIds.SLOW_METABOLISM)) {
      result += 10;
    }
    if (hasTrait(TraitIds.CYBORG_LIFE_SPAN)) {
      result += 70;
    }
    if (hasTrait(TraitIds.MASSIVE_SIZE)) {
      result += 20;
    }
    if (hasTrait(TraitIds.ROBOTIC)) {
      result = 2000;
    }
    return result;
  }

  /**
   * Get social system for empires and kingdoms.
   * @return SocialSystem
   */
  public SocialSystem getSocialSystem() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return SocialSystem.EQUAL;
    case MECHIONS:
      // Mechions cannot have empires or kingdoms.
      return SocialSystem.EQUAL;
    case SPORKS:
      return SocialSystem.PATRIARCHY;
    case GREYANS:
      return SocialSystem.PATRIARCHY;
    case CENTAURS:
      return SocialSystem.PATRIARCHY;
    case MOTHOIDS:
      return SocialSystem.MATRIARCHY;
    case TEUTHIDAES:
      return SocialSystem.PATRIARCHY;
    case SCAURIANS:
      return SocialSystem.EQUAL;
    case HOMARIANS:
      return SocialSystem.MATRIARCHY;
    case CHIRALOIDS:
      return SocialSystem.PATRIARCHY;
    case REBORGIANS:
      return SocialSystem.EQUAL;
    case LITHORIANS:
      return SocialSystem.MATRIARCHY;
    case ALTEIRIANS:
      return SocialSystem.EQUAL;
    case SMAUGIRIANS:
      return SocialSystem.PATRIARCHY;
    case SYNTHDROIDS:
      return SocialSystem.MATRIARCHY;
    default:
      return SocialSystem.EQUAL;
    }
  }
  /**
   * Get bridge effect for diplomacy screen
   * @return BridgeCommandType
   */
  public BridgeCommandType getBridgeEffect() {
    switch (this) {
    case HUMAN:
      return BridgeCommandType.WARM_WHITE;
    case SPACE_MONSTERS:
    case SPACE_PIRATE:
      return BridgeCommandType.DARKEST;
    case MECHIONS:
      return BridgeCommandType.WARM_WHITE;
    case SPORKS:
      return BridgeCommandType.DARK_RED;
    case GREYANS:
      return BridgeCommandType.WARM_WHITE;
    case CENTAURS:
      return BridgeCommandType.BRIGHT_CYAN;
    case MOTHOIDS:
      return BridgeCommandType.GREEN_CONSOLE;
    case TEUTHIDAES:
      return BridgeCommandType.PURPLE_DREAM;
    case SCAURIANS:
      return BridgeCommandType.DARK_ORANGE;
    case HOMARIANS:
      return BridgeCommandType.BLUEISH_WHITE;
    case CHIRALOIDS:
      return BridgeCommandType.BLUEISH_WHITE;
    case REBORGIANS:
      return BridgeCommandType.DARKEST;
    case LITHORIANS:
      return BridgeCommandType.DARKEST;
    case ALTEIRIANS:
      return BridgeCommandType.GREYBLUE;
    case SMAUGIRIANS:
      return BridgeCommandType.ORANGE_BLINK;
    case SYNTHDROIDS:
      return BridgeCommandType.WARM_WHITE;
    default:
      return BridgeCommandType.WARM_WHITE;
    }
  }

  /**
   * Get Minimum population for leader on planet.
   * @return Minimum population number.
   */
  public int getMinimumPopulationForLeader() {
    switch (this) {
    default:
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
    case MECHIONS:
    case SPORKS:
    case GREYANS:
    case CENTAURS:
    case MOTHOIDS:
    case TEUTHIDAES:
    case SCAURIANS:
    case HOMARIANS:
    case CHIRALOIDS:
    case REBORGIANS:
    case LITHORIANS:
    case SMAUGIRIANS:
    case SYNTHDROIDS:
      return 5;
    case ALTEIRIANS:
      return 4;
    }
  }
  /**
   * Get planet's base value based on temperature for space race.
   * This will tell how much of population world type can
   * hold.
   * @param temperature TemperatureType
   * @return Base value between 0 - 100 %.
   */
  public int getTemperatureBaseValue(final TemperatureType temperature) {
    if (temperature == TemperatureType.ARCTIC) {
      int result = 25;
      if (hasTrait(TraitIds.TOLERATE_COLD)) {
        result = result + 25;
      }
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result - 25;
      }
      return result;
    }
    if (temperature == TemperatureType.COLD) {
      int result = 50;
      if (hasTrait(TraitIds.TOLERATE_COLD)) {
        result = result + 25;
      }
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result - 25;
      }
      return result;
    }
    if (temperature == TemperatureType.TEMPERATE) {
      int result = 100;
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result - 25;
      }
      return result;
    }
    if (temperature == TemperatureType.TROPICAL) {
      int result = 75;
      if (hasTrait(TraitIds.TOLERATE_HOT)) {
        result = result + 25;
      }
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result + 25;
      }
      return result;
    }
    if (temperature == TemperatureType.HOT) {
      int result = 50;
      if (hasTrait(TraitIds.TOLERATE_HOT)) {
        result = result + 25;
      }
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result + 25;
      }
      return result;
    }
    if (temperature == TemperatureType.VOLCANIC) {
      int result = 0;
      if (hasTrait(TraitIds.TOLERATE_LAVA)) {
        result = result + 50;
      }
      return result;
    }
    // Frozen and Inferno are here
    return 0;
  }

  /**
   * Genders the race's leaders can have.
   * @return Array of Genders
   */
  public Gender[] getGenders() {
    if (this == MECHIONS || this == REBORGIANS) {
      return new Gender[] {
          Gender.NONE
      };
    }
    if (this == SYNTHDROIDS) {
      return new Gender[] {
          Gender.FEMALE
      };
    }

    return new Gender[] {
        Gender.FEMALE, Gender.MALE
    };
  }

  /**
   * Get full description about the race, including the stats.
   * @param markDown if true then markDown is being used, otherwise HTML.
   * @param image Add image to wiki page if true
   * @return Full description
   */
  public String getFullDescription(final boolean markDown,
      final boolean image) {
    String lf = "<br>";
    String dot = "<li>";
    if (markDown) {
      lf = "\n";
      dot = "*";
    }
    StringBuilder sb = new StringBuilder(100);
    if (!markDown) {
      sb.append("<html>");
    } else {
      sb.append("### ");
    }
    sb.append(name);
    sb.append(lf);
    if (markDown && image) {
      sb.append(lf);
      sb.append("![](");
      sb.append(getRaceImagePathToWiki());
      sb.append(")");
      sb.append(lf);
      sb.append(lf);
    }
    if (!markDown) {
      sb.append("<p>");
    }
    sb.append(description);
    if (!markDown) {
      sb.append("</p>");
    }
    sb.append(lf);
    sb.append(dot);
    sb.append(" Max radiation: ");
    sb.append(getMaxRad());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Troop power: ");
    sb.append(getTrooperPower());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Leader lifespan: ");
    sb.append(getLifeSpan());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Minimum leader population: ");
    sb.append(getMinimumPopulationForLeader());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Production: ");
    sb.append(getProductionSpeed(null));
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Mining: ");
    sb.append(getMiningSpeed(null));
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Research: ");
    sb.append(getResearchSpeed());
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Food production: ");
    sb.append(getFoodSpeed(null));
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Growth: ");
    switch (getGrowthSpeed()) {
    case 0: sb.append("never"); break;
    case 50: {
      if (hasTrait(TraitIds.FIXED_GROWTH)) {
        sb.append("always ");
      }
      sb.append("20 star years");
      if (hasTrait(TraitIds.LIMITED_GROWTH)) {
        sb.append(" limited");
      }
      break;
    }
    default:
    case 100: sb.append("10 star years"); break;
    case 150: sb.append("6 star years"); break;
    }
    sb.append(lf);
    sb.append(dot);
    sb.append(" Food require: ");
    sb.append(getFoodRequire());
    sb.append("%");
    if (getExtraPopulation() != 0) {
      sb.append(lf);
      sb.append(dot);
      sb.append(" Population limit: ");
      if (getExtraPopulation() > 0) {
        sb.append("+");
      }
      sb.append(getExtraPopulation());
    }
    sb.append(lf);
    sb.append(dot);
    sb.append(" Culture: ");
    sb.append(getCultureSpeed());
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Diplomacy bonus: ");
    sb.append(getDiplomacyBonus());
    sb.append(lf);
    sb.append(dot);
    sb.append(" War resistance: ");
    sb.append(getWarFatigueResistance());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Special: ");
    if (this == CENTAURS) {
      sb.append("Stronger ships");
    } else if (this == MECHIONS || this == SYNTHDROIDS) {
      sb.append("Population needs to be built and no heirs");
    } else if (this == SPORKS) {
      sb.append("Extra scout ship and higher combat tech at start");
    } else if (this == GREYANS) {
      sb.append("Electronics and propulsion techs are higher at start");
    } else if (this == MOTHOIDS) {
      sb.append("No defense tech but one Planetary improvement tech at start");
    } else if (this == SpaceRace.TEUTHIDAES) {
      sb.append("Each ship has built-in cloaking device");
    } else if (this == SpaceRace.SCAURIANS) {
      sb.append("Trade fleet gain 50% more credits and better trade"
          + " buildings.");
    } else if (this == SpaceRace.HOMARIANS) {
      sb.append("Starts with 5 population");
    } else if (this == SpaceRace.CHIRALOIDS) {
      sb.append("Radiosynthesis (+1 food per radiation per population)");
    } else if (this == SpaceRace.REBORGIANS) {
      sb.append("Gain dead enemies as own population."
          + " Steal technology by conquering planets."
          + " No heirs available.");
    } else if (this == SpaceRace.LITHORIANS) {
      sb.append("Population eats metal instead of food.");
    } else if (this == SpaceRace.ALTEIRIANS) {
      sb.append("Lives only on orbitals. Orbitals are cheaper and colonized"
          + " planets have space port.");
    } else if (this == SpaceRace.SMAUGIRIANS) {
      sb.append("Weapon allowed in cargo ships.");
    } else {
      sb.append("None");
    }

    if (traits.size() > 0) {
      if (!markDown) {
        sb.append("<p>");
      } else {
        sb.append(lf + lf);
      }
      int points = 0;
      sb.append(lf + "Racial attributes (trait points):" + lf);
      for (var trait : traits) {
        sb.append(dot);
        sb.append(" ");
        sb.append(trait.getName());
        points = points + trait.getPoints();
        sb.append(String.format(" (%1$+d)", trait.getPoints()));
        sb.append(" - ");
        sb.append(trait.getDescription());
        sb.append(lf);
      }
      sb.append("Total trait points: ");
      sb.append(points);
      sb.append(lf);
      if (!markDown) {
        sb.append("</p>");
      }
    }

    if (!markDown) {
      sb.append("</html>");
    }
    return sb.toString();
  }

  /**
   * Get random living race.
   * @return Living SpaceRace
   */
  public static SpaceRace getRandomLivingRace() {
    var nonRoboticRaces = Stream.of(values())
        .filter(race -> !race.isRoboticRace())
        // Filter out "pseudo-races"
        .filter(
            race -> race != SPACE_PIRATE && race != SpaceRace.SPACE_MONSTERS)
        .collect(Collectors.toList());
    if (nonRoboticRaces.isEmpty()) {
      return null;
    }
    return DiceGenerator.pickRandom(nonRoboticRaces);
  }

  /**
   * Get random robotic race
   * @return Robotic SpaceRace
   */
  public static SpaceRace getRandomRoboticRace() {
    var roboticRaces = Stream.of(values())
        .filter(race -> race.isRoboticRace())
        .collect(Collectors.toList());
    if (roboticRaces.isEmpty()) {
      return null;
    }
    return DiceGenerator.pickRandom(roboticRaces);
  }
}
