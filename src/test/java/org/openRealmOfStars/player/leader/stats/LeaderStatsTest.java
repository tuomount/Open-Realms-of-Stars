package org.openRealmOfStars.player.leader.stats;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2019,2020 Tuomo Untinen
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
 * Tests for LeaderStats class
 */

public class LeaderStatsTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    LeaderStats stats = new LeaderStats();
    for (int i = 0; i < StatType.values().length; i++) {
      StatType type = StatType.values()[i];
      assertEquals(0, stats.getStat(type));
    }
    for (int i = 0; i < StatType.values().length; i++) {
      StatType type = StatType.values()[i];
      stats.setStat(type, i + 1);
      assertEquals(i + 1, stats.getStat(type));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatTypes() {
    for (int i = 0; i < StatType.values().length; i++) {
      StatType type = StatType.values()[i];
      assertEquals(i, type.getAsByte());
    }
  }

}
