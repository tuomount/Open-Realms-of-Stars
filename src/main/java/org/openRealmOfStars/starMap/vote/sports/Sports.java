package org.openRealmOfStars.starMap.vote.sports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.openRealmOfStars.utilities.DiceGenerator;

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
* Sports for galactic sports
*
*/
public class Sports {

  /**
   * List of athletes to compete.
   */
  private ArrayList<Athlete> athletes;

  /**
   * Build new sports event.
   */
  public Sports() {
    athletes = new ArrayList<>();
  }

  /**
   * Add new athlete to sports
   * @param athlete Athlete to add
   */
  public void add(final Athlete athlete) {
    athletes.add(athlete);
  }

  /**
   * Get all athlete is array.
   * @return Atheletes in array.
   */
  public Athlete[] getAthletes() {
    return athletes.toArray(new Athlete[athletes.size()]);
  }

  /**
   * Order athletes by sporting value.
   */
  public void orderBySporting() {
    Collections.sort(athletes, new Comparator<Athlete>() {

      @Override
      public int compare(final Athlete o1, final Athlete o2) {
        return o2.getSportingValue() - o1.getSportingValue();
      }
    });
  }

  /**
   * Makes athletes in list to do "sports" and they ordered
   * then lowering sporting value where highest value is the first.
   */
  public void handleSports() {
    for (Athlete athlete : athletes) {
      int value = athlete.getBaseScore();
      value = value + DiceGenerator.getRandom(athlete.getBaseScore());
      value = value + DiceGenerator.getRandom(athlete.getBaseScore());
      athlete.setSportingValue(value);
    }
    orderBySporting();
  }
}
