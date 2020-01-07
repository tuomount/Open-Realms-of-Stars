package org.openRealmOfStars.player.SpaceRace;

import java.awt.image.BufferedImage;

import org.openRealmOfStars.audio.music.MusicFileInfo;
import org.openRealmOfStars.audio.music.MusicPlayer;
import org.openRealmOfStars.gui.utilies.GuiStatics;
import org.openRealmOfStars.player.diplomacy.Attitude;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2020 Tuomo Untinen
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
  HUMAN(0, "Humans", "Human", "Humans are great diplomats but they are about "
      + "average in everything else."),
  /**
   * Mechanical beings whom do not eat food. Each now population must be built.
   */
  MECHIONS(1, "Mechions", "Mechion", "Mechanical beings whom do not eat food."
          + " Each population must be built."),
  /**
   * Aggressive and warmongering spieces.
   */
  SPORKS(2, "Sporks", "Spork", "Aggressive and warmongering species."),
  /**
   * Humanoid creatures with grey skin and big eyes.
   * Greyan are excellent researchers.
   */
  GREYANS(3, "Greyans", "Greyan", "Humanoid creatures with grey skin and"
          + " big eyes. Greyan are excellent researchers."),
  /**
   * Quadrupedal humanoid creatures which are big, about 5 meters tall. Due
   * their enormous size their space ships are must more rigid.
   */
  CENTAURS(4, "Centaurs", "Centaur", "Quadrupedal humanoid creatures which are"
          + " big, about 5 meters tall. Due their enormous size their space"
          + " ships are more rigid. "),
  /**
   * Mothoids are sentient insects with hivemind. They are fast breeding race.
   */
  MOTHOIDS(5, "Mothoids", "Mothoid", "Mothoids are sentient insects with"
      + " capability to hivemind. They are fast breeding race. Their song"
      + " is hypnotic so cultural bonus is granted. Mothoids exo-skeleton"
      + " is weak and therefore get negative bonus on mining and troop"
      + " power."),
  /**
   * Teuthidaes are octopus like creatures.
   * They are scientific and military focused race. Their ships have built-in
   * cloaking devices.
   */
  TEUTHIDAES(6, "Teuthidaes", "Teuthidae", "Teuthidaes are octopus like "
      + "creatures. They are scientific and military focused race. Their "
      + "ships have built-in cloaking devices."),
  /**
   * Scaurians are small but wide humanoid.
   * They are merchantical race. They focus make better trades with other and
   * gain more credits.
   */
  SCAURIANS(7, "Scaurians", "Scaurian", "Scaurians are small but wide"
      + " humanoid. They are merchantical race. They focus make better"
      + " trades with other and gain more credits."),
  /**
   * Homarians are very strong creatures. They have humanoid from but
   * they have very thick and hard exoskeleton.
   */
  HOMARIANS(8, "Homarians", "Homarian", "Homarians are very strong creatures. "
      + "They have humanoid from but they have very thick and "
      + "hard exoskeleton. Due their strength they are good in "
      + "physical tasks."),
  /**
   * Humans are about average in everything and space pirates are probably
   * humans or are just average.
   */
  SPACE_PIRATE(9, "Space Pirates", "Space pirate", "Generic space pirate which"
      + "are aggressive and try cause trouble. Stats are identical to humans."),
  /**
   * Chiraloids are creatures with four arms and two legs. They have hard
   * exoskeleton. They also have special gland which uses radioactivity
   * to create nutrient. This is called radiosynthesis.
   */
  CHIRALOIDS(10, "Chiraloids", "Chiraloid", "Chiraloids are creatures with"
      + " four arms and two legs. They have hard exoskeleton. They also have"
      + " special gland which uses radioactivity to create nutrient. This is"
      + " called radiosynthesis.");


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
    case SPACE_PIRATE:
      return GuiStatics.IMAGE_PRIVATEER_RACE;
    case CHIRALOIDS:
      return GuiStatics.IMAGE_CHIRALOID_RACE;
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
      case SPACE_PIRATE:
        return start + "resources/images/privateer_race.png";
      case CHIRALOIDS:
        return start + "resources/images/chiraloid_race.png";
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
      return 12;
    case HOMARIANS:
      return 11;
    case CHIRALOIDS:
      return 9;
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
    default:
      return 100;
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
      return 30;
    case MECHIONS:
      return 30;
    case SPORKS:
      return 28;
    case GREYANS:
      return 35;
    case CENTAURS:
      return 30;
    case MOTHOIDS:
      return 32;
    case TEUTHIDAES:
      return 35;
    case SCAURIANS:
      return 35;
    case HOMARIANS:
      return 35;
    case CHIRALOIDS:
      return 33;
    default:
      return 30;
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
    case SPACE_PIRATE:
      return MusicPlayer.SET_FIRE_TO_REALITY;
    case CHIRALOIDS:
      return MusicPlayer.MENACE;
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
    default:
      return false;
    }
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
      return 80;
    case MECHIONS:
      // Robots can be always fixed and parts replaced.
      // Game last maximum of 1120 turns so 2000 is more than enough.
      return 2000;
    case SPORKS:
      return 70;
    case GREYANS:
      return 100;
    case CENTAURS:
      return 120;
    case MOTHOIDS:
      return 60;
    case TEUTHIDAES:
      return 70;
    case SCAURIANS:
      return 80;
    case HOMARIANS:
      return 70;
    case CHIRALOIDS:
      return 70;
    default:
      return 80;
    }
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
    sb.append(getGrowthSpeed());
    sb.append("%");
    sb.append(lf);
    sb.append(dot);
    sb.append(" Food require: ");
    sb.append(getFoodRequire());
    sb.append("%");
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
    sb.append(lf);
    sb.append(dot);
    sb.append(" Special: ");
    if (this == CENTAURS) {
      sb.append("Stronger ships");
    } else if (this == MECHIONS) {
      sb.append("Population needs to be built");
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
      sb.append("Radiosynthesis (+1 food per radiotion per population)");
    } else {
      sb.append("None");
    }
    if (!markDown) {
      sb.append("</html>");
    }
    return sb.toString();
  }



}
