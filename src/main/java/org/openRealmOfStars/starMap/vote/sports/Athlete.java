package org.openRealmOfStars.starMap.vote.sports;

import org.openRealmOfStars.player.PlayerInfo;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Athlete for galactic sports
*
*/
public class Athlete {

  /**
   * Bonus for sporting.
   */
  private int bonus;

  /**
   * Planet name where athlete is from.
   */
  private String planetName;

  /**
   * Which realm athlete is.
   */
  private PlayerInfo realm;

  /**
   * Sporting value for games
   */
  private int sportingValue;

  /**
   * Constructor for athlete
   * @param planet Planet name where athlete is from.
   * @param info Realm where atlete is from.
   */
  public Athlete(final String planet, final PlayerInfo info) {
    this.planetName = planet;
    this.realm = info;
    bonus = 0;
    sportingValue = 0;
  }

  /**
   * Set bonus for athlete.
   * @param bonus Sporting bonus for athelete.
   */
  public void setBonus(final int bonus) {
    this.bonus = bonus;
  }

  /**
   * Get Athlete's sporting bonus.
   * @return Bonus
   */
  public int getBonus() {
    return bonus;
  }

  /**
   * Get Athlete's planet name.
   * @return Planet name as a String.
   */
  public String getPlanetName() {
    return planetName;
  }

  /**
   * Get Athlete's realm.
   * @return Realm aka PlayerInfo.
   */
  public PlayerInfo getRealm() {
    return realm;
  }

  /**
   * Get Athlete's base sporting power
   * @return Base power
   */
  public int getBaseScore() {
    return realm.getRace().getTrooperPower() + bonus;
  }

  /**
   * Get the sporting value of athlete.
   * @return Sporting value
   */
  public int getSportingValue() {
    return sportingValue;
  }

  /**
   * Set sporting value of athelete.
   * @param value Sporting value
   */
  public void setSportingValue(final int value) {
    sportingValue = value;
  }
}
