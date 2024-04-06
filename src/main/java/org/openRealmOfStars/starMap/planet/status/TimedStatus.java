package org.openRealmOfStars.starMap.planet.status;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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
import java.util.Optional;

import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;

/**
 * Timed planetary status. This tells when timed planetary status is
 * activated and starts counting time to zero. When count enters zero, status
 * became applied.
 *
 */
public class TimedStatus {

  /** Planetary Status. */
  private PlanetaryStatus status;
  /** Timed Typed */
  private TimedStatusType timedType;
  /** Actual counter, this is number turns or star years */
  private int count;

  /**
   * Constructor for TimedStatus.
   * @param status PlanetaryStatus
   * @param timedType TimedStatusType
   * @param count How many turns before status activates.
   */
  public TimedStatus(final PlanetaryStatus status,
      final TimedStatusType timedType, final int count) {
    this.status = status;
    this.timedType = timedType;
    this.count = count;
  }

  /**
   * Get Random Planetary Status.
   * @param planetType PlanetType
   * @param chance Chance for getting planetary status
   * @return TimedPlanetary status or null
   */
  public static TimedStatus getRandomPlanetaryStatus(
      final PlanetTypes planetType, final int chance) {
    int value = DiceGenerator.getRandom(99);
    TimedStatus status = null;
    if (value < chance) {
      switch (planetType) {
      case PLANET_EARTH:
      case PLANET_MARS: {
        value = DiceGenerator.getRandom(99);
        if (value < 33) {
          status = StatusFactory.getTimedStatus(StatusIds.FERTILE_SOIL,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(8, 25));
        } else if (value < 66) {
          status = StatusFactory.getTimedStatus(StatusIds.METAL_RICH_SURFACE,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(8, 30));
        } else if (value < 100) {
          status = StatusFactory.getTimedStatus(StatusIds.MOLTEN_LAVA,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(4, 10));
        }
        break;
      }
      case SWAMPWORLD1:
      case SWAMPWORLD2:
      case SWAMPWORLD3:
        value = DiceGenerator.getRandom(99);
        if (value < 70) {
          status = StatusFactory.getTimedStatus(StatusIds.FERTILE_SOIL,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(10, 25));
        } else if (value < 85) {
          status = StatusFactory.getTimedStatus(StatusIds.METAL_RICH_SURFACE,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(8, 30));
        } else if (value < 100) {
          status = StatusFactory.getTimedStatus(StatusIds.MOLTEN_LAVA,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(4, 10));
        }
        break;
      case BARRENWORLD1:
        value = DiceGenerator.getRandom(99);
        if (value < 50) {
          status = StatusFactory.getTimedStatus(StatusIds.METAL_RICH_SURFACE,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(8, 30));
        } else {
          status = StatusFactory.getTimedStatus(StatusIds.MOLTEN_LAVA,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(4, 10));
        }
        break;
      case ICEWORLD1:
      case ICEWORLD2:
      case ICEWORLD3:
      case ICEWORLD4:
        value = DiceGenerator.getRandom(99);
        if (value < 20) {
          status = StatusFactory.getTimedStatus(StatusIds.FERTILE_SOIL,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(15, 35));
        } else if (value < 60) {
          status = StatusFactory.getTimedStatus(StatusIds.METAL_RICH_SURFACE,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(15, 30));
        } else if (value < 100) {
          status = StatusFactory.getTimedStatus(StatusIds.MOLTEN_LAVA,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(2, 8));
        }
        break;
      case DESERTWORLD1:
      case DESERTWORLD2:
      case DESERTWORLD3:
        value = DiceGenerator.getRandom(99);
        if (value < 10) {
          status = StatusFactory.getTimedStatus(StatusIds.FERTILE_SOIL,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(15, 35));
        } else if (value < 55) {
          status = StatusFactory.getTimedStatus(StatusIds.METAL_RICH_SURFACE,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(15, 30));
        } else if (value < 100) {
          status = StatusFactory.getTimedStatus(StatusIds.MOLTEN_LAVA,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(2, 8));
        }
        break;
      case VOLCANICWORLD1:
      case VOLCANICWORLD2:
      case VOLCANICWORLD3:
      case VOLCANICWORLD4:
      case VOLCANICWORLD5:
      case VOLCANICWORLD6:
        value = DiceGenerator.getRandom(99);
        if (value < 30) {
          status = StatusFactory.getTimedStatus(StatusIds.FERTILE_SOIL,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(15, 35));
        } else if (value < 50) {
          status = StatusFactory.getTimedStatus(StatusIds.METAL_RICH_SURFACE,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(15, 30));
        } else if (value < 100) {
          status = StatusFactory.getTimedStatus(StatusIds.MOLTEN_LAVA,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(2, 8));
        }
        break;
      case WATERWORLD1:
      case WATERWORLD2:
      case WATERWORLD3:
      case WATERWORLD4:
      case WATERWORLD5:
      case WATERWORLD6:
      case WATERWORLD7:
      case WATERWORLD8:
      case WATERWORLD9:
        value = DiceGenerator.getRandom(99);
        if (value < 60) {
          status = StatusFactory.getTimedStatus(StatusIds.FERTILE_SOIL,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(15, 35));
        } else if (value < 95) {
          status = StatusFactory.getTimedStatus(StatusIds.METAL_RICH_SURFACE,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(6, 32));
        } else if (value < 100) {
          status = StatusFactory.getTimedStatus(StatusIds.MOLTEN_LAVA,
              TimedStatusType.AFTER_COLONIZATION_OR_AWAY_TEAM,
              DiceGenerator.getRandom(3, 8));
        }
        break;

      default:
        break;
    }
    }
    return status;
  }
  /**
   * Decrease counter by one only if not active yet.
   */
  public void decrease() {
    if (count > 0) {
      count--;
    }
  }

  /**
   * Is Status active?
   * @return True if status should be active and applied.
   */
  public boolean isActive() {
    if (count == 0) {
      return true;
    }
    return false;
  }

  /**
   * Get planetary status.
   * @return Planetary status
   */
  public PlanetaryStatus getStatus() {
    return status;
  }

  /**
   * Get timed status type.
   * @return TimedStatusType
   */
  public TimedStatusType getTimedStatus() {
    return timedType;
  }

  @Override
  public String toString() {
    return status.getId() + " " + timedType + " count: " + count;
  }

  /**
   * Save to provided data stream
   * @param dos DataOutputStream to save into
   * @throws IOException stream error
   */
  public void save(final DataOutputStream dos) throws IOException {
    IOUtilities.writeString(dos, status.getId());
    dos.writeByte(timedType.getIndex());
    dos.writeShort(count);
  }

  /**
   * Load TimedStatus from provided data stream.
   * @param dis DataInputStream to load from
   * @return Loaded TimedStatus or empty if not defined
   * @throws IOException stream error
   */
  public static Optional<TimedStatus> load(final DataInputStream dis)
      throws IOException {
    final var statusId = IOUtilities.readString(dis);
    int value = dis.readByte();
    TimedStatusType type = TimedStatusType.getByIndex(value);
    int count = dis.readShort();
    // Fail if status is not defined
    final var status = StatusFactory.createTimedStatus(statusId, type, count);
    if (status.isEmpty()) {
      ErrorLogger.log("Failed to load TimedStatus \"" + statusId + "\"");
    }
    return status;
  }

}
