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
    assertEquals(0, stats.getStat(StatType.COMMANDER_LENGTH));
    assertEquals(0, stats.getStat(StatType.GOVERNOR_LENGTH));
    assertEquals(0, stats.getStat(StatType.JAIL_TIME));
    assertEquals(0, stats.getStat(StatType.KILLED_ANOTHER_LEADER));
    assertEquals(0, stats.getStat(StatType.NUMBER_OF_ANOMALY));
    assertEquals(0, stats.getStat(StatType.NUMBER_OF_BATTLES));
    assertEquals(0, stats.getStat(StatType.NUMBER_OF_ESPIONAGE));
    assertEquals(0, stats.getStat(StatType.NUMBER_OF_JAIL_TIME));
    assertEquals(0, stats.getStat(StatType.NUMBER_OF_PIRATE_BATTLES));
    assertEquals(0, stats.getStat(StatType.NUMBER_OF_PRIVATEERING));
    assertEquals(0, stats.getStat(StatType.NUMBER_OF_RULER));
    assertEquals(0, stats.getStat(StatType.NUMBER_OF_TRADES));
    assertEquals(0, stats.getStat(StatType.RULER_REIGN_LENGTH));
    stats.setStat(StatType.COMMANDER_LENGTH, 1);
    stats.setStat(StatType.GOVERNOR_LENGTH, 2);
    stats.setStat(StatType.JAIL_TIME, 3);
    stats.setStat(StatType.KILLED_ANOTHER_LEADER, 4);
    stats.setStat(StatType.NUMBER_OF_ANOMALY, 5);
    stats.setStat(StatType.NUMBER_OF_BATTLES, 6);
    stats.setStat(StatType.NUMBER_OF_ESPIONAGE, 7);
    stats.setStat(StatType.NUMBER_OF_JAIL_TIME, 8);
    stats.setStat(StatType.NUMBER_OF_PIRATE_BATTLES, 9);
    stats.setStat(StatType.NUMBER_OF_PRIVATEERING, 10);
    stats.setStat(StatType.NUMBER_OF_RULER, 11);
    stats.setStat(StatType.NUMBER_OF_TRADES, 12);
    stats.setStat(StatType.RULER_REIGN_LENGTH, 13);
    assertEquals(1, stats.getStat(StatType.COMMANDER_LENGTH));
    assertEquals(2, stats.getStat(StatType.GOVERNOR_LENGTH));
    assertEquals(3, stats.getStat(StatType.JAIL_TIME));
    assertEquals(4, stats.getStat(StatType.KILLED_ANOTHER_LEADER));
    assertEquals(5, stats.getStat(StatType.NUMBER_OF_ANOMALY));
    assertEquals(6, stats.getStat(StatType.NUMBER_OF_BATTLES));
    assertEquals(7, stats.getStat(StatType.NUMBER_OF_ESPIONAGE));
    assertEquals(8, stats.getStat(StatType.NUMBER_OF_JAIL_TIME));
    assertEquals(9, stats.getStat(StatType.NUMBER_OF_PIRATE_BATTLES));
    assertEquals(10, stats.getStat(StatType.NUMBER_OF_PRIVATEERING));
    assertEquals(11, stats.getStat(StatType.NUMBER_OF_RULER));
    assertEquals(12, stats.getStat(StatType.NUMBER_OF_TRADES));
    assertEquals(13, stats.getStat(StatType.RULER_REIGN_LENGTH));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStatTypes() {
    assertEquals(0, StatType.RULER_REIGN_LENGTH.getAsByte());
    assertEquals(1, StatType.NUMBER_OF_RULER.getAsByte());
    assertEquals(2, StatType.NUMBER_OF_BATTLES.getAsByte());
    assertEquals(3, StatType.NUMBER_OF_ESPIONAGE.getAsByte());
    assertEquals(4, StatType.NUMBER_OF_ANOMALY.getAsByte());
    assertEquals(5, StatType.COMMANDER_LENGTH.getAsByte());
    assertEquals(6, StatType.GOVERNOR_LENGTH.getAsByte());
    assertEquals(5, StatType.COMMANDER_LENGTH.getAsByte());
    assertEquals(7, StatType.NUMBER_OF_JAIL_TIME.getAsByte());
    assertEquals(8, StatType.JAIL_TIME.getAsByte());
    assertEquals(9, StatType.KILLED_ANOTHER_LEADER.getAsByte());
    assertEquals(10, StatType.NUMBER_OF_PIRATE_BATTLES.getAsByte());
    assertEquals(11, StatType.NUMBER_OF_PRIVATEERING.getAsByte());
    assertEquals(12, StatType.NUMBER_OF_TRADES.getAsByte());
  }
  
}
