package org.openRealmOfStars.player.leader.stats;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2021-2023 Tuomo Untinen
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.EnumMap;

/**
*
* Leader Stats
*
*/
public class LeaderStats {
  /** Map holding values of leader's stats */
  private EnumMap<StatType, Integer> statsMap;

  /**
   * Constructor of leader stats.
   */
  public LeaderStats() {
    this.statsMap = new EnumMap<>(StatType.class);
    for (var statType : StatType.values()) {
      this.statsMap.put(statType, 0);
    }
  }

  /**
   * Constructs leader stats from input stream.
   * @param dis DataInput stream.
   * @throws IOException If reading from stream fails.
   */
  public LeaderStats(final DataInputStream dis) throws IOException {
    this();

    int count = dis.read();
    for (int i = 0; i < count; i++) {
      int index = dis.read();
      if (index == -1) {
        throw new IOException("Unexpected end of stream while reading"
            + " leader stats.");
      }
      StatType type = StatType.getBasedOnIndex(index);
      int value = dis.readUnsignedShort();
      setStat(type, value);
    }
  }

  /**
   * Count how many times there are stats greater than zero.
   * @return Number of stats.
   */
  private int countStats() {
    int count = 0;
    for (var statValue : statsMap.values()) {
      if (statValue > 0) {
        count++;
      }
    }
    return count;
  }

  /**
   * Save Leader stats to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException If writing fails.
   */
  public void saveLeaderStats(final DataOutputStream dos) throws IOException {
    int count = countStats();
    dos.writeByte(count);
    for (var statId : statsMap.keySet()) {
      if (getStat(statId) <= 0) {
        continue;
      }
      count--;
      dos.writeByte(statId.getAsByte());
      dos.writeShort(getStat(statId));
    }
    if (count != 0) {
      throw new IOException("Mismatch on number of expected stats and actually"
          + " written.");
    }
  }

  /**
   * Set Stat value.
   * @param type Stat type which to set.
   * @param value Value where to set.
   */
  public void setStat(final StatType type, final int value) {
    if (value >= 0 && value <= 65535) {
      statsMap.put(type, value);
    }
  }

  /**
   * Get leader stat for certain stat type.
   * @param type Stat type for get.
   * @return Stat value.
   */
  public int getStat(final StatType type) {
    return statsMap.get(type);
  }

  /**
   * Increase value by one.
   * @param type Stat type for increase.
   */
  public void addOne(final StatType type) {
    setStat(type, getStat(type) + 1);
  }
}
