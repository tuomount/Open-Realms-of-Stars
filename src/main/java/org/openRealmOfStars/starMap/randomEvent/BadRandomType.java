package org.openRealmOfStars.starMap.randomEvent;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019, 2020 Tuomo Untinen
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
* Bad Random event type
*
*/
public enum BadRandomType {
  /**
   * Meteor crashes into planet and can kill populations or destroy buildings.
   * This provides a more metal to planet.
   * Defensive turret can shoot it down and then provide more metal to planet.
   */
  METEOR_HIT,
  /**
   * This event will kill all population expect one. One population is
   * resistant for virus. This has no effect if race is Mechion.
   */
  DEADLY_VIRUS_OUTBREAK,
  /**
   * Fleet in deep space turns into space pirates.
   */
  MUTINY,
  /**
   * Space pirates appear near the planet and are ready for raiding.
   */
  RAIDERS,
  /**
   * Solar activity increases and all planets in same system increase one
   *  radiation level.
   */
  SOLAR_ACTIVITY_INCREASE,
  /**
   * One planet has aggressive wild life with power 12 and planet
   * must fight against it.
   */
  AGGRESSIVE_WILD_LIFE,
  /**
   * Planet climate changes to arid. If it is already arid then it turns
   * to desert.
   */
  CLIMATE_CHANGE,
  /**
   * Massive data lost in research labs. One tech research points for current
   * level is set to zero.
   */
  MASSIVE_DATA_LOST,
  /**
   * Massive corruption scandal found in government.
   * Half of the credits are gone.
   */
  CORRUPTION_SCANDAL,
  /**
   * One of planet's building explodes and kills one population.
   */
  CATASTROPHIC_ACCIDENT,
  /**
   * Ruler has too much stress and gets mental perk.
   */
  RULER_STRESS,
  /**
   * Leader encounters accident and dies.
   */
  ACCIDENT,
  /**
   * Terrorist attack on planet.
   */
  TERRORIST_ATTACK;
}
