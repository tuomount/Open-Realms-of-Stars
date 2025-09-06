package org.openRealmOfStars.starMap.event.karmaEvents;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 BottledByte
 * Copyright (C) 2019-2020 Tuomo Untinen
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

/**
 * Enumeration of possible event types (behaviors)
 * TODO: Only temporary solution, replace with proper classes/objects
 */
public enum RandomEventType {

  //
  // GOOD KARMA EVENTS
  //

  /**
   * Mysterious signal received from solar system.
   * This signal is from another realm which player hasn't met yet.
   */
  MYSTERIOUS_SIGNAL(false),
  /** Double the research points in one tech level */
  TECHNICAL_BREAKTHROUGH(false),
  /** Lost treasure found, +50 credits. */
  LOST_TREASURE_FOUND(false),
  /** All planets in system decrease radiation level. */
  SOLAR_ACTIVITY_DIMISHED(false),
  /**
   * Alien ship appears near the planet, but all crew is gone.
   * Realm gain access a new ship.
   */
  DESERTED_SHIP(false),
  /**
   * Meteoroid just misses the planet, but scientist are able mine
   * metal from it.
   */
  MISSED_METEOROID(false),
  /** Leader gains level by getting his/hers achievements done earlier. */
  LEADER_LEVEL(false),
  /**
   * Movie or music or theather or book was been done which became massive
   * cultural hit. That planet gets cultural boost.
   */
  CULTURAL_HIT(false),

  //
  // BAD KARMA EVENTS
  //

  /**
   * Meteor crashes into planet and can kill populations or destroy buildings.
   * This provides a more metal to planet.
   * Defensive turret can shoot it down and then provide more metal to planet.
   */
  METEOR_HIT(true),
  /**
   * This event will kill all population expect one. One population is
   * resistant for virus. This has no effect if race is Mechion.
   */
  DEADLY_VIRUS_OUTBREAK(true),
  /** Fleet in deep space turns into space pirates. */
  MUTINY(true),
  /** Space pirates appear near the planet and are ready for raiding.*/
  RAIDERS(true),
  /** Radiation on all planets in the same system increases */
  SOLAR_ACTIVITY_INCREASE(true),
  /**
   * One planet has aggressive wild life with power 12 and planet
   * must fight against it.
   */
  AGGRESSIVE_WILD_LIFE(true),
  /**
   * Massive data lost in research labs. One tech research points for current
   * level is set to zero.
   */
  MASSIVE_DATA_LOST(true),
  /**
   * Massive corruption scandal found in government.
   * Half of the credits are gone.
   */
  CORRUPTION_SCANDAL(true),
  /** One of planet's building explodes and kills one population.  */
  CATASTROPHIC_ACCIDENT(true),
  /** Ruler has too much stress and gets mental perk. */
  RULER_STRESS(true),
  /** Leader encounters accident and dies. */
  ACCIDENT(true),
  /** Terrorist attack on planet. */
  TERRORIST_ATTACK(true);

  /** Is event considered bad? */
  private boolean bad;

  /**
   * Create new instance
   * @param bad True if considered bad
   */
  RandomEventType(final boolean bad) {
    this.bad = bad;
  }

  /**
   * @return True if considered bad
   */
  public boolean isBad() {
    return bad;
  }

  /**
   * @return Title/Headline/Name of the event
   */
  public String getTitle() {
    switch (this) {
      case AGGRESSIVE_WILD_LIFE:
        return "Aggressive wild life";
      case CATASTROPHIC_ACCIDENT:
        return "Catasrophic accident!";
      case CORRUPTION_SCANDAL:
        return "Corrupted goverment!";
      case DEADLY_VIRUS_OUTBREAK:
        return "Deadly virus outbreak!";
      case MASSIVE_DATA_LOST:
        return "Research data lost";
      case METEOR_HIT:
        return "Meteor strike!";
      case MUTINY:
        return "Mutiny!";
      case RAIDERS:
        return "Space pirates!";
      case RULER_STRESS:
        return "Ruler stress!";
      case ACCIDENT:
        return "Accident!";
      case TERRORIST_ATTACK:
        return "Terrorist attack!";
      case SOLAR_ACTIVITY_INCREASE:
        return "Solar activity increased";
      case DESERTED_SHIP:
        return "Deserted ship found!";
      case LOST_TREASURE_FOUND:
        return "Lost treasure";
      case MISSED_METEOROID:
        return "Meteoroid missed";
      case MYSTERIOUS_SIGNAL:
        return "The Signal";
      case SOLAR_ACTIVITY_DIMISHED:
        return "Solar activity decreased";
      case LEADER_LEVEL:
        return "Experienced leader";
      case CULTURAL_HIT:
        return "Cultural hit";
      default:
      case TECHNICAL_BREAKTHROUGH:
        return "Techical breakthrough";
    }
  }
}
