package org.openRealmOfStars.AI.PathFinding;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.combat.Combat;
import org.openRealmOfStars.player.combat.CombatShip;
import org.openRealmOfStars.starMap.StarMap;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016, 2018  Tuomo Untinen
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
 * Test for AStarTest class
 */

public class AStarTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAStarInCombat() {
    Combat combat = Mockito.mock(Combat.class);
    for (int y = 0; y < 9; y++) {
      for (int x = 0; x <9; x++) {
        if (x == 4 && y == 1 || y == 7) {
          Mockito.when(combat.isBlocked(x, y)).thenReturn(true);
        } else {
          Mockito.when(combat.isBlocked(x, y)).thenReturn(false);
        }
        
      }
    }
    CombatShip target = Mockito.mock(CombatShip.class);
    Mockito.when(target.getX()).thenReturn(4);
    Mockito.when(target.getY()).thenReturn(1);
    CombatShip source = Mockito.mock(CombatShip.class);
    Mockito.when(source.getX()).thenReturn(4);
    Mockito.when(source.getY()).thenReturn(7);
    AStarSearch test = new AStarSearch(combat, source, target, 1);
    assertEquals(true, test.doSearch());
    test.doRoute();
    int steps = 0;
    while (!test.isLastMove()) {
      steps++;
      assertNotEquals(null, test.getMove());
      test.nextMove();
    }
    assertEquals(1,test.getTargetDistance());
    assertEquals(5,steps);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAStarInCombatFail() {
    Combat combat = Mockito.mock(Combat.class);
    for (int y = 0; y < 9; y++) {
      for (int x = 0; x <9; x++) {
        if (x == 4 && y == 1 || y == 7) {
          Mockito.when(combat.isBlocked(x, y, true)).thenReturn(true);
        } if (y == 4) {
          Mockito.when(combat.isBlocked(x, y, true)).thenReturn(true);
        } else {
          Mockito.when(combat.isBlocked(x, y, true)).thenReturn(false);
        }
        
      }
    }
    CombatShip target = Mockito.mock(CombatShip.class);
    Mockito.when(target.getX()).thenReturn(4);
    Mockito.when(target.getY()).thenReturn(1);
    CombatShip source = Mockito.mock(CombatShip.class);
    Mockito.when(source.getX()).thenReturn(4);
    Mockito.when(source.getY()).thenReturn(7);
    AStarSearch test = new AStarSearch(combat, source, target, 1);
    assertEquals(false, test.doSearch());
    
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testAStarInStarMapCloseUp() {
    StarMap map = Mockito.mock(StarMap.class);
    Mockito.when(map.getMaxX()).thenReturn(50);
    Mockito.when(map.getMaxY()).thenReturn(50);
    Mockito.when(map.isBlocked(Mockito.anyInt(), Mockito.anyInt())).thenReturn(false);
    AStarSearch search = new AStarSearch(map, 10, 10, 12, 10, 5);
    assertEquals(true, search.doSearch());
    search.doRoute();
    int steps = 1;
    while (!search.isLastMove()) {
      assertNotEquals(null, search.getMove());
      if (steps == 1) {
        assertEquals(11, search.getMove().getX());
        assertEquals(9, search.getMove().getY());
      }
      search.nextMove();
      steps++;
    }
    if (steps == 2) {
      assertEquals(12, search.getMove().getX());
      assertEquals(10, search.getMove().getY());
    }
    assertEquals(0,search.getTargetDistance());
    assertEquals(2,steps);
  }

}
