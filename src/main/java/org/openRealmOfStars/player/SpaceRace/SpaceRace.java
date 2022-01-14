package org.openRealmOfStars.player.SpaceRace;

import java.awt.image.BufferedImage;

import org.openRealmOfStars.ambient.BridgeCommandType;
import org.openRealmOfStars.audio.music.MusicFileInfo;
import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.PlayerColor;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.starMap.planet.PlanetTypes;
import org.openRealmOfStars.starMap.planet.WorldType;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021 Tuomo Untinen
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
 * Space races in enum
 *
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
   * Lithorians are creatures that eat metal instead of food. They have slow
   * grow rate and they have -2 population limit. They have
   * excellent ability to mine metal.
   */
  ALTEIRIANS(13, "Alteirians", "Alteirian",
      "Alteirian are creatures that live in zero gravity. Because of this they"
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
       + " trouble. No diplomacy with these guys.");




  /**
   * Create space race
   * @param index Space race index
   * @param name Space race name in plural
   * @param single Space race name in single format
   * @param description Space race  description
   */
  SpaceRace(final int index, final String name, final String single,
      final String description) {
    this.index = index;
    this.name = name;
    this.nameSingle = single;
    this.description = description;

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

  /**
   * Get scientist research speed
   * @return The research speed
   */
  public int getResearchSpeed() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 100;
    case MECHIONS:
      return 50;
    case SPORKS:
      return 100;
    case GREYANS:
      return 150;
    case CENTAURS:
      return 100;
    case MOTHOIDS:
      return 100;
    case TEUTHIDAES:
      return 150;
    case SCAURIANS:
      return 100;
    case HOMARIANS:
      return 50;
    case CHIRALOIDS:
      return 50;
    case REBORGIANS:
      return 100;
    case LITHORIANS:
      return 100;
    case ALTEIRIANS:
      return 200;
    case SMAUGIRIANS:
      return 100;
    default:
      return 0;
    }
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
  public int getMaxRad() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 4;
    case MECHIONS:
      return 8;
    case SPORKS:
      return 5;
    case GREYANS:
      return 6;
    case CENTAURS:
      return 3;
    case MOTHOIDS:
      return 6;
    case TEUTHIDAES:
      return 4;
    case SCAURIANS:
      return 5;
    case HOMARIANS:
      return 3;
    case CHIRALOIDS:
      return 10;
    case REBORGIANS:
      return 5;
    case LITHORIANS:
      return 7;
    case ALTEIRIANS:
      return 7;
    case SMAUGIRIANS:
      return 5;
    default:
      return -1;
    }
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
   * Get miners mining speed
   * @return normal 100, half 50, double 200
   */
  public int getMiningSpeed() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 100;
    case MECHIONS:
      return 150;
    case SPORKS:
      return 100;
    case GREYANS:
      return 100;
    case CENTAURS:
      return 100;
    case MOTHOIDS:
      return 50;
    case TEUTHIDAES:
      return 100;
    case SCAURIANS:
      return 50;
    case HOMARIANS:
      return 150;
    case CHIRALOIDS:
      return 100;
    case REBORGIANS:
      return 100;
    case LITHORIANS:
      return 200;
    case ALTEIRIANS:
      return 50;
    case SMAUGIRIANS:
      return 100;
    default:
      return 0;
    }
  }

  /**
   * Get artists culture speed
   * @return normal 100, half 50, double 200
   */
  public int getCultureSpeed() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 100;
    case MECHIONS:
      return 50;
    case SPORKS:
      return 100;
    case GREYANS:
      return 100;
    case CENTAURS:
      return 100;
    case MOTHOIDS:
      return 150;
    case TEUTHIDAES:
      return 100;
    case SCAURIANS:
      return 100;
    case HOMARIANS:
      return 50;
    case CHIRALOIDS:
      return 100;
    case REBORGIANS:
      return 50;
    case LITHORIANS:
      return 100;
    case ALTEIRIANS:
      return 150;
    case SMAUGIRIANS:
      return 100;
    default:
      return 0;
    }
  }

  /**
   * Get Trooper power
   * @return normal 10
   */
  public int getTrooperPower() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 10;
    case MECHIONS:
      return 12;
    case SPORKS:
      return 12;
    case GREYANS:
      return 8;
    case CENTAURS:
      return 14;
    case MOTHOIDS:
      return 9;
    case TEUTHIDAES:
      return 10;
    case SCAURIANS:
      return 10;
    case HOMARIANS:
      return 11;
    case CHIRALOIDS:
      return 9;
    case REBORGIANS:
      return 13;
    case LITHORIANS:
      return 12;
    case ALTEIRIANS:
      return 6;
    case SMAUGIRIANS:
      return 11;
    default:
      return 0;
    }
  }

  /**
   * Get worker production speed
   * @return normal 100, half 50, double 200
   */
  public int getProductionSpeed() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 100;
    case MECHIONS:
      return 100;
    case SPORKS:
      return 100;
    case GREYANS:
      return 100;
    case CENTAURS:
      return 100;
    case MOTHOIDS:
      return 100;
    case TEUTHIDAES:
      return 100;
    case SCAURIANS:
      return 100;
    case HOMARIANS:
      return 150;
    case CHIRALOIDS:
      return 50;
    case REBORGIANS:
      return 100;
    case LITHORIANS:
      return 100;
    case ALTEIRIANS:
      return 50;
    case SMAUGIRIANS:
      return 100;
    default:
      return 0;
    }
  }

  /**
   * Get population growth speed
   * @return normal 100, half 50, double 200
   */
  public int getGrowthSpeed() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 100;
    case MECHIONS:
      return 0;
    case SPORKS:
      return 100;
    case GREYANS:
      return 50;
    case CENTAURS:
      return 50;
    case MOTHOIDS:
      return 150;
    case TEUTHIDAES:
      return 100;
    case SCAURIANS:
      return 50;
    case HOMARIANS:
      return 100;
    case CHIRALOIDS:
      return 50;
    case REBORGIANS:
      return 50;
    case LITHORIANS:
      return 50;
    case ALTEIRIANS:
      return 100;
    case SMAUGIRIANS:
      return 100;
    default:
      return 0;
    }
  }

  /**
   * Get Food growth speed
   * @return normal 100, half 50, double 200
   */
  public int getFoodSpeed() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 100;
    case MECHIONS:
      return 0;
    case SPORKS:
      return 100;
    case GREYANS:
      return 100;
    case CENTAURS:
      return 100;
    case MOTHOIDS:
      return 100;
    case TEUTHIDAES:
      return 100;
    case SCAURIANS:
      return 100;
    case HOMARIANS:
      return 200;
    case CHIRALOIDS:
      return 100;
    case REBORGIANS:
      return 100;
    case LITHORIANS:
      return 0;
    case ALTEIRIANS:
      return 100;
    case SMAUGIRIANS:
      return 100;
    default:
      return 100;
    }
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
      return PlayerColor.BLUE;
    case SMAUGIRIANS:
      return PlayerColor.TAN;
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
      return PlayerColor.CYAN;
    case CENTAURS:
      return PlayerColor.PINK;
    case MOTHOIDS:
      return PlayerColor.PINK;
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
      return PlayerColor.CYAN;
    case SMAUGIRIANS:
      return PlayerColor.GREEN;
    default:
      return PlayerColor.CORAL;
    }
  }

  /**
   * Get population food requirement
   * @return normal 100, half 50, double 200
   */
  public int getFoodRequire() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 100;
    case MECHIONS:
      return 0;
    case SPORKS:
      return 100;
    case GREYANS:
      return 100;
    case CENTAURS:
      return 100;
    case MOTHOIDS:
      return 100;
    case TEUTHIDAES:
      return 100;
    case SCAURIANS:
      return 100;
    case HOMARIANS:
      return 100;
    case CHIRALOIDS:
      return 100;
    case REBORGIANS:
      return 50;
    case LITHORIANS:
      return 0;
    case ALTEIRIANS:
      return 100;
    case SMAUGIRIANS:
      return 100;
    default:
      return 0;
    }
  }

  /**
   * Get diplomacy bonus for race
   * @return normal 0, +2 positive, -2 negative
   */
  public int getDiplomacyBonus() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 2;
    case MECHIONS:
      return -2;
    case SPORKS:
      return -3;
    case GREYANS:
      return 0;
    case CENTAURS:
      return -1;
    case MOTHOIDS:
      return 0;
    case TEUTHIDAES:
      return -2;
    case SCAURIANS:
      return 1;
    case HOMARIANS:
      return 1;
    case CHIRALOIDS:
      return -4;
    case REBORGIANS:
      return -8;
    case LITHORIANS:
      return -2;
    case ALTEIRIANS:
      return -1;
    case SMAUGIRIANS:
      return -2;
    default:
      return 0;
    }
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
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 60;
    case MECHIONS:
      return 50;
    case SPORKS:
      return 50;
    case GREYANS:
      return 40;
    case CENTAURS:
      return 70;
    case MOTHOIDS:
      return 30;
    case TEUTHIDAES:
      return 60;
    case SCAURIANS:
      return 40;
    case HOMARIANS:
      return 50;
    case CHIRALOIDS:
      return 50;
    case REBORGIANS:
      return 60;
    case LITHORIANS:
      return 70;
    case ALTEIRIANS:
      return 50;
    case SMAUGIRIANS:
      return 70;
    default:
      return 50;
    }
  }

  /**
   * Get racial hull point if available
   * @return normal 0 or 1
   */
  public int getExtraHullPoint() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 0;
    case MECHIONS:
      return 0;
    case SPORKS:
      return 0;
    case GREYANS:
      return 0;
    case CENTAURS:
      return 1;
    case MOTHOIDS:
      return 0;
    case TEUTHIDAES:
      return 0;
    case SCAURIANS:
      return 0;
    case HOMARIANS:
      return 0;
    case CHIRALOIDS:
      return 0;
    case REBORGIANS:
      return 0;
    case LITHORIANS:
      return 0;
    case ALTEIRIANS:
      return 0;
    case SMAUGIRIANS:
      return 0;
    default:
      return 0;
    }
  }

  /**
   * Get how many turns to update defense
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
    default:
      return 15;
    }
  }

  /**
   * Get how many turns to explore solar system
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
    default:
      return 50;
    }
  }

  /**
   * What is the minimum number of attack ships
   * @return int
   */
  public int getAIMinimumAttackShips() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 3;
    case MECHIONS:
      return 3;
    case SPORKS:
      return 4;
    case GREYANS:
      return 3;
    case CENTAURS:
      return 2;
    case MOTHOIDS:
      return 3;
    case TEUTHIDAES:
      return 3;
    case SCAURIANS:
      return 3;
    case HOMARIANS:
      return 3;
    case CHIRALOIDS:
      return 4;
    case REBORGIANS:
      return 4;
    case LITHORIANS:
      return 3;
    case ALTEIRIANS:
      return 3;
    case SMAUGIRIANS:
      return 3;
    default:
      return 3;
    }
  }

  /**
   * What is the minimum number of conquer ships aka bomber and
   * troopers
   * @return int
   */
  public int getAIMinimumConquerShips() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 1;
    case MECHIONS:
      return 1;
    case SPORKS:
      return 2;
    case GREYANS:
      return 1;
    case CENTAURS:
      return 1;
    case MOTHOIDS:
      return 2;
    case TEUTHIDAES:
      return 2;
    case SCAURIANS:
      return 1;
    case HOMARIANS:
      return 1;
    case CHIRALOIDS:
      return 1;
    case REBORGIANS:
      return 2;
    case LITHORIANS:
      return 1;
    case ALTEIRIANS:
      return 2;
    case SMAUGIRIANS:
      return 1;
    default:
      return 1;
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
    default:
      return MusicPlayer.MILLION_LIGHT_YEARS;
    }
  }
  /**
   * Has space race option to rush production with credits.
   * @return True if credit rush is possible
   */
  public boolean hasCreditRush() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return true;
    case MECHIONS:
      return false;
    case SPORKS:
      return true;
    case GREYANS:
      return true;
    case CENTAURS:
      return true;
    case MOTHOIDS:
      return false;
    case TEUTHIDAES:
      return true;
    case SCAURIANS:
      return true;
    case HOMARIANS:
      return false;
    case CHIRALOIDS:
      return false;
    case REBORGIANS:
      return false;
    case LITHORIANS:
      return false;
    case ALTEIRIANS:
      return true;
    case SMAUGIRIANS:
      return true;
    default:
      return true;
    }
  }
  /**
   * Has space race option to rush production with population.
   * @return True if credit rush is possible
   */
  public boolean hasPopulationRush() {
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return false;
    case MECHIONS:
      return true;
    case SPORKS:
      return true;
    case GREYANS:
      return false;
    case CENTAURS:
      return false;
    case MOTHOIDS:
      return true;
    case TEUTHIDAES:
      return false;
    case SCAURIANS:
      return false;
    case HOMARIANS:
      return true;
    case CHIRALOIDS:
      return false;
    case REBORGIANS:
      return true;
    case LITHORIANS:
      return false;
    case ALTEIRIANS:
      return false;
    case SMAUGIRIANS:
      return false;
    default:
      return false;
    }
  }

  /**
   * Is space race eating metal?
   * @return True if eating metal.
   */
  public boolean isLithovorian() {
    if (this == SpaceRace.LITHORIANS) {
      return true;
    }
    return false;
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
    switch (this) {
    case HUMAN:
    case SPACE_PIRATE:
    case SPACE_MONSTERS:
      return 80;
    case MECHIONS:
      // Robots can be always fixed and parts replaced.
      // Game last maximum of 1120 turns so 2000 is more than enough.
      return 2000;
    case SPORKS:
      return 75;
    case GREYANS:
      return 110;
    case CENTAURS:
      return 120;
    case MOTHOIDS:
      return 70;
    case TEUTHIDAES:
      return 80;
    case SCAURIANS:
      return 90;
    case HOMARIANS:
      return 70;
    case CHIRALOIDS:
      return 100;
    case REBORGIANS:
      return 150;
    case LITHORIANS:
      return 110;
    case ALTEIRIANS:
      return 90;
    case SMAUGIRIANS:
      return 80;
    default:
      return 80;
    }
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
      //FIXME: Check this when bridge image has been done
      return BridgeCommandType.WARM_WHITE;
    case SMAUGIRIANS:
      //FIXME: Check this when bridge image has been done
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
      return 5;
    case ALTEIRIANS:
      return 4;
    }
  }
  /**
   * Get World base value for space race.
   * This will tell how much of population world type can
   * hold.
   * @param worldType World Type
   * @return Base value between 25 - 125 %.
   */
  public int getWorldTypeBaseValue(final WorldType worldType) {
    if (this == HUMAN) {
      switch (worldType) {
      case SILICONWORLD: return 50;
      case WATERWORLD: return 100;
      case IRONWORLD: return 50;
      case ICEWORLD: return 75;
      case CARBONWORLD: return 100;
      case DESERTWORLD: return 75;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == MECHIONS) {
      switch (worldType) {
      case SILICONWORLD: return 100;
      case WATERWORLD: return 75;
      case IRONWORLD: return 100;
      case ICEWORLD: return 25;
      case CARBONWORLD: return 75;
      case DESERTWORLD: return 50;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == SPORKS) {
      switch (worldType) {
      case SILICONWORLD: return 50;
      case WATERWORLD: return 100;
      case IRONWORLD: return 50;
      case ICEWORLD: return 50;
      case CARBONWORLD: return 100;
      case DESERTWORLD: return 75;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == GREYANS) {
      switch (worldType) {
      case SILICONWORLD: return 50;
      case WATERWORLD: return 125;
      case IRONWORLD: return 50;
      case ICEWORLD: return 50;
      case CARBONWORLD: return 100;
      case DESERTWORLD: return 50;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == CENTAURS) {
      switch (worldType) {
      case SILICONWORLD: return 50;
      case WATERWORLD: return 100;
      case IRONWORLD: return 75;
      case ICEWORLD: return 100;
      case CARBONWORLD: return 125;
      case DESERTWORLD: return 100;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == MOTHOIDS) {
      switch (worldType) {
      case SILICONWORLD: return 50;
      case WATERWORLD: return 100;
      case IRONWORLD: return 50;
      case ICEWORLD: return 25;
      case CARBONWORLD: return 100;
      case DESERTWORLD: return 75;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == TEUTHIDAES) {
      switch (worldType) {
      case SILICONWORLD: return 50;
      case WATERWORLD: return 125;
      case IRONWORLD: return 50;
      case ICEWORLD: return 75;
      case CARBONWORLD: return 125;
      case DESERTWORLD: return 25;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == SCAURIANS) {
      switch (worldType) {
      case SILICONWORLD: return 25;
      case WATERWORLD: return 100;
      case IRONWORLD: return 75;
      case ICEWORLD: return 75;
      case CARBONWORLD: return 75;
      case DESERTWORLD: return 75;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == HOMARIANS) {
      switch (worldType) {
      case SILICONWORLD: return 50;
      case WATERWORLD: return 125;
      case IRONWORLD: return 50;
      case ICEWORLD: return 100;
      case CARBONWORLD: return 125;
      case DESERTWORLD: return 50;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == SPACE_PIRATE || this == SPACE_MONSTERS) {
      switch (worldType) {
      case SILICONWORLD: return 75;
      case WATERWORLD: return 100;
      case IRONWORLD: return 50;
      case ICEWORLD: return 75;
      case CARBONWORLD: return 75;
      case DESERTWORLD: return 75;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == CHIRALOIDS) {
      switch (worldType) {
      case SILICONWORLD: return 50;
      case WATERWORLD: return 100;
      case IRONWORLD: return 50;
      case ICEWORLD: return 50;
      case CARBONWORLD: return 100;
      case DESERTWORLD: return 50;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == REBORGIANS) {
      switch (worldType) {
      case SILICONWORLD: return 75;
      case WATERWORLD: return 100;
      case IRONWORLD: return 100;
      case ICEWORLD: return 50;
      case CARBONWORLD: return 75;
      case DESERTWORLD: return 50;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == LITHORIANS) {
      switch (worldType) {
      case SILICONWORLD: return 100;
      case WATERWORLD: return 50;
      case IRONWORLD: return 125;
      case ICEWORLD: return 25;
      case CARBONWORLD: return 50;
      case DESERTWORLD: return 75;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    if (this == ALTEIRIANS) {
      // All planets are equal since this race lives in orbit.
      return 100;
    }
    if (this == SMAUGIRIANS) {
      switch (worldType) {
      case SILICONWORLD: return 50;
      case WATERWORLD: return 100;
      case IRONWORLD: return 50;
      case ICEWORLD: return 50;
      case CARBONWORLD: return 100;
      case DESERTWORLD: return 75;
      default:
      case ARTIFICALWORLD: return 100;
      }
    }
    return 100;
  }
  /**
   * Get rush option as a String
   * @return String
   */
  private String getRushOption() {
    if (hasCreditRush() && hasPopulationRush()) {
      return "Credit and population";
    }
    if (hasCreditRush() && !hasPopulationRush()) {
      return "Credit";
    }
    if (!hasCreditRush() && hasPopulationRush()) {
      return "Population";
    }
    return "None";
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
    sb.append(getProductionSpeed());
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Mining: ");
    sb.append(getMiningSpeed());
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Research: ");
    sb.append(getResearchSpeed());
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Food production: ");
    sb.append(getFoodSpeed());
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Growth: ");
    switch (getGrowthSpeed()) {
    case 0: sb.append("never"); break;
    case 50: {
      if (this == REBORGIANS) {
        sb.append("always ");
      }
      sb.append("20 turns");
      if (this == LITHORIANS) {
        sb.append(" limited");
      }
      break;
    }
    default:
    case 100: sb.append("10 turns"); break;
    case 150: sb.append("6 turns"); break;
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
    sb.append(" Rush: ");
    sb.append(getRushOption());
    PlanetTypes planetTypes = PlanetTypes.getRandomStartPlanetType(this);
    int sustainable = getWorldTypeBaseValue(planetTypes.getWorldType());
    sb.append(lf);
    sb.append(dot);
    sb.append(" Start planet value: ");
    sb.append(sustainable);
    sb.append(lf);
    sb.append(dot);
    sb.append(" Special: ");
    if (this == CENTAURS) {
      sb.append("Stronger ships");
    } else if (this == MECHIONS) {
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
    if (!markDown) {
      sb.append("</html>");
    }
    return sb.toString();
  }

}
