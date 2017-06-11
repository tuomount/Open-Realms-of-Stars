package org.openRealmOfStars.player.SpaceRace;

import java.awt.image.BufferedImage;

import org.openRealmOfStars.gui.GuiStatics;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017  Tuomo Untinen
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
          + " Each now population must be built."),
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
   * Bipedal humanoid creatures which are big, about 5 meters tall. Due their
   * enormous size their space ships are must more rigid. Centaurs need more
   * food to survive.
   */
  CENTAURS(4, "Centaurs", "Centaur", "Quadrupedal humanoid creatures which are"
          + " big, about 5 meters tall. Due their enormous size their space"
          + " ships are more rigid. Centaurs need more food to survive.");

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
      return 100;
    case MECHIONS:
      return 50;
    case SPORKS:
      return 100;
    case GREYANS:
      return 150;
    case CENTAURS:
      return 100;
    default:
      return 0;
    }
  }

  /**
   * Get race maximum Radiation
   * @return The race maximum radiation
   */
  public int getMaxRad() {
    switch (this) {
    case HUMAN:
      return 4;
    case MECHIONS:
      return 8;
    case SPORKS:
      return 5;
    case GREYANS:
      return 6;
    case CENTAURS:
      return 3;
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
    default:
      return GuiStatics.IMAGE_CENTAUR_RACE;
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
      return 100;
    case MECHIONS:
      return 150;
    case SPORKS:
      return 100;
    case GREYANS:
      return 100;
    case CENTAURS:
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
      return 100;
    case MECHIONS:
      return 50;
    case SPORKS:
      return 100;
    case GREYANS:
      return 100;
    case CENTAURS:
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
      return 10;
    case MECHIONS:
      return 12;
    case SPORKS:
      return 12;
    case GREYANS:
      return 8;
    case CENTAURS:
      return 14;
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
      return 100;
    case MECHIONS:
      return 100;
    case SPORKS:
      return 100;
    case GREYANS:
      return 100;
    case CENTAURS:
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
      return 100;
    case MECHIONS:
      return 0;
    case SPORKS:
      return 100;
    case GREYANS:
      return 50;
    case CENTAURS:
      return 50;
    default:
      return 0;
    }
  }

  /**
   * Get population food requirement
   * @return normal 100, half 50, double 200
   */
  public int getFoodRequire() {
    switch (this) {
    case HUMAN:
      return 100;
    case MECHIONS:
      return 0;
    case SPORKS:
      return 100;
    case GREYANS:
      return 100;
    case CENTAURS:
      return 125;
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
      return 2;
    case MECHIONS:
      return -2;
    case SPORKS:
      return -3;
    case GREYANS:
      return 0;
    case CENTAURS:
      return -1;
    default:
      return 0;
    }
  }

  /**
   * Get racial hull point if available
   * @return normal 0 or 1
   */
  public int getExtraHullPoint() {
    switch (this) {
    case HUMAN:
      return 0;
    case MECHIONS:
      return 0;
    case SPORKS:
      return 0;
    case GREYANS:
      return 0;
    case CENTAURS:
      return 1;
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
      return 15;
    case MECHIONS:
      return 15;
    case SPORKS:
      return 10;
    case GREYANS:
      return 16;
    case CENTAURS:
      return 18;
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
      return 30;
    case MECHIONS:
      return 30;
    case SPORKS:
      return 28;
    case GREYANS:
      return 35;
    case CENTAURS:
      return 30;
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
      return 3;
    case MECHIONS:
      return 3;
    case SPORKS:
      return 4;
    case GREYANS:
      return 3;
    case CENTAURS:
      return 2;
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
      return 1;
    case MECHIONS:
      return 1;
    case SPORKS:
      return 2;
    case GREYANS:
      return 1;
    case CENTAURS:
      return 1;
    default:
      return 1;
    }
  }

  /**
   * Get full description about the race, including the stats.
   * @return Full description
   */
  public String getFullDescription() {
    StringBuilder sb = new StringBuilder(100);
    sb.append(name);
    sb.append("\n");
    sb.append(description);
    sb.append("\n");
    sb.append("* Max radiation: ");
    sb.append(getMaxRad());
    sb.append("\n");
    sb.append("* Production: ");
    sb.append(getProductionSpeed());
    sb.append("%\n");
    sb.append("* Mining: ");
    sb.append(getMiningSpeed());
    sb.append("%\n");
    sb.append("* Research: ");
    sb.append(getResearchSpeed());
    sb.append("%\n");
    sb.append("* Growth: ");
    sb.append(getGrowthSpeed());
    sb.append("%\n");
    sb.append("* Food require: ");
    sb.append(getFoodRequire());
    sb.append("%\n");
    sb.append("* Culture: ");
    sb.append(getCultureSpeed());
    sb.append("%\n");
    sb.append("* Diplomacy bonus: ");
    sb.append(getDiplomacyBonus());
    sb.append("\n");
    sb.append("* Special: ");
    if (this == CENTAURS) {
      sb.append("Stronger ships");
    } else if (this == MECHIONS) {
      sb.append("Population needs to be built");
    } else {
      sb.append("None");
    }

    return sb.toString();
  }

}
