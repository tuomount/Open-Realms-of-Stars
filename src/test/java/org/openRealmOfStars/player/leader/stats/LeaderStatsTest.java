package org.openRealmOfStars.player.leader.stats;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSaving() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (DataOutputStream dos = new DataOutputStream(baos)) {
      LeaderStats stats = new LeaderStats();
      for (int i = 0; i < StatType.values().length; i++) {
        StatType type = StatType.values()[i];
        stats.setStat(type, i + 1);
        assertEquals(i + 1, stats.getStat(type));
      }
      stats.saveLeaderStats(dos);
      byte[] buffer = baos.toByteArray();
      assertEquals(StatType.values().length * 3 + 1, buffer.length);
      assertEquals(StatType.values().length, buffer[0]);
      // This test will work up to 128 different stats.
      for (int i = 0; i < StatType.values().length; i++) {
        assertEquals(i, buffer[1 + i * 3]);
        assertEquals(i + 1, buffer[1 + i * 3 + 2]);
        assertEquals(0, buffer[1 + i * 3 + 1]);
      }
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSavingEmpty() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (DataOutputStream dos = new DataOutputStream(baos)) {
      LeaderStats stats = new LeaderStats();
      stats.saveLeaderStats(dos);
      byte[] buffer = baos.toByteArray();
      assertEquals(1, buffer.length);
      assertEquals(0, buffer[0]);
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testReading() throws IOException {
    byte[] buffer = {0x05, 0x01, 0x00, 0x05, 0x0a, 0x00, 0x04, 0x04, 0x00, 0x03,
        0x03, 0x00, 0x02, 0x07, 0x00, 0x01};
    ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
    try (DataInputStream dis = new DataInputStream(bais)) {
      LeaderStats stats = new LeaderStats(dis);
      assertEquals(5, stats.getStat(StatType.getBasedOnIndex(1)));
      assertEquals(4, stats.getStat(StatType.getBasedOnIndex(10)));
      assertEquals(3, stats.getStat(StatType.getBasedOnIndex(4)));
      assertEquals(2, stats.getStat(StatType.getBasedOnIndex(3)));
      assertEquals(1, stats.getStat(StatType.getBasedOnIndex(7)));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testReadingEmpty() throws IOException {
    byte[] buffer = {0x00};
    ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
    try (DataInputStream dis = new DataInputStream(bais)) {
      LeaderStats stats = new LeaderStats(dis);
      for (int i = 0; i < StatType.values().length; i++) {
        StatType type = StatType.values()[i];
        assertEquals(0, stats.getStat(type));
      }
    }
  }

}
