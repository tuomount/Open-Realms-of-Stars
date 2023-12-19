package org.openRealmOfStars.starMap.planet.status;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2023 BottledByte
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
import java.util.Objects;
import java.util.Optional;

import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.IOUtilities;

/**
 * Represents status that can be applied to a Planet.
 *
 * <p>
 * It holds a reference to {@link PlanetaryStatus} which
 * contains "general definition" of the status.
 * </p>
 * <p>
 * It is not possible to stack multiple statuses
 * of the same type (refering to same PlanetaryStatus)
 * on a single planet.
 * </p>
 *
 * @see StatusFactory
 */
public class AppliedStatus {
  /** Definition of planetary status */
  private PlanetaryStatus status;

  /**
   * @param status status definition
   */
  public AppliedStatus(final PlanetaryStatus status) {
    this.status = Objects.requireNonNull(status);
  }

  /**
   * @return the status
   */
  public PlanetaryStatus getStatus() {
    return status;
  }

  /**
   * Get ID of status definition
   * @return ID of status definition
   */
  public String getStatusId() {
    return status.getId();
  }

  /**
   * Save to provided data stream
   * @param dos DataOutputStream to save into
   * @throws IOException stream error
   */
  public void save(final DataOutputStream dos) throws IOException {
    IOUtilities.writeString(dos, status.getId());
  }

  /**
   * Load AppliedStatus from provided data stream.
   * @param dis DataInputStream to load from
   * @return Loaded AppliedStatus or empty if not defined
   * @throws IOException stream error
   */
  public static Optional<AppliedStatus> load(final DataInputStream dis)
      throws IOException {
    final var statusId = IOUtilities.readString(dis);
    // Fail if status is not defined
    final var status = StatusFactory.create(statusId);
    if (status.isEmpty()) {
      ErrorLogger.log("Failed to load AppliedStatus \"" + statusId + "\"");
    }
    return status;
  }
}
