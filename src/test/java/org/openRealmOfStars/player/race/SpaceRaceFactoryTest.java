package org.openRealmOfStars.player.race;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2025 Tuomo Untinen
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

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.race.trait.RaceTrait;

/**
* Tests for SpaceRaceFactory
*
*/
public class SpaceRaceFactoryTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testBalancedScore() {
    final int expectedScore = 4;
    boolean failure = false;
    SpaceRace[] races = SpaceRaceFactory.getValuesNoPseudo();
    for (SpaceRace race : races) {
      int score = 0;
      for (RaceTrait trait : race.getAllTraits()) {
        score = score + trait.getPoints();
      }
      if (score != expectedScore) {
        System.err.println(race.getName() + " traits do not add up to"
            + " expected score.");
        System.err.println("Expected: " + expectedScore + " but it was "
            + score + ".");
        failure = true;
      }
    }
    assertFalse(failure);
    System.err.println("Standard space races are balanced based on points.");
  }

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void testNoConflicts() {
    boolean failure = false;
    SpaceRace[] races = SpaceRaceFactory.getValuesNoPseudo();
    for (SpaceRace race : races) {
      int score = 0;
      for (RaceTrait trait : race.getAllTraits()) {
        for (String id : trait.getConflictsWithIds()) {
          for (RaceTrait possibleConflict : race.getAllTraits()) {
            if (id.equals(possibleConflict.getId())) {
              failure = true;
              System.err.println(race.getName() + " has conflicting traits:"
                  + id + " and " + possibleConflict.getId() + ".");
              
            }
          }
        }
      }
    }
    assertFalse(failure);
    System.err.println("Standard space races do not have conflict traits.");
  }

}
