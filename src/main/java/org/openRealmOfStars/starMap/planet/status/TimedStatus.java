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

}
