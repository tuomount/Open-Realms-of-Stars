package org.openRealmOfStars.starMap.event.ascensionEvents;
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.event.GalaxyEvents;

/**
 * Ascension Events which require timers, and where events affect on timers.
 * Ascension events depend totally on player doings. If player(s) are not
 * trying to get the ascension victor none of this happens.
 */
public class AscensionEvents implements GalaxyEvents {

  /**
   * No any ascension activation yet.
   */
  public static final byte NO_ACTIVATIO = 0;
  /**
   * Ascension vein has been activated by visiting at black hole
   * with gravity ripper.
   */
  public static final byte ASCENSION_VEIN_ACTIVATED = 1;
  /**
   * Ascension portal has been built.
   */
  public static final byte ASCENSION_PORTAL_ACTIVATED = 2;

  /**
   * How fast new artifacts are being spawned.
   * AKA someone/something is helping to ascend.
   */
  private int artifactSpawnSpeed;
  /**
   * How fast new space monster are being spawned.
   * AKA someone/something is preventing to ascend.
   */
  private int spaceDevourerSpeed;
  /**
   * Chance for new artifact to spawn. Chance is per thousand.
   */
  private int chanceForArtifact;
  /**
   * Chance for space monster to space. Chance is per thousand.
   */
  private int chanceForDevourer;
  /**
   * Current status of ascension.
   */
  private byte ascensionActivation;

  /**
   * Create new ascension events.
   */
  public AscensionEvents() {
    artifactSpawnSpeed = 0;
    spaceDevourerSpeed = 0;
    chanceForArtifact = 0;
    chanceForDevourer = 0;
    ascensionActivation = 0;
  }

  /**
   * Create new ascension events based on saved file.
   * @param dis DataInputStream
   * @throws IOException If something goes wrong while reading.
   */
  public AscensionEvents(final DataInputStream dis) throws IOException {
    artifactSpawnSpeed = dis.read();
    spaceDevourerSpeed = dis.read();
    chanceForArtifact = dis.readInt();
    chanceForDevourer = dis.readInt();
    ascensionActivation = dis.readByte();
  }
  @Override
  public void handleEvents(final StarMap map) {
    chanceForArtifact = chanceForArtifact + artifactSpawnSpeed;
    chanceForDevourer = chanceForDevourer + spaceDevourerSpeed;
    //TODO: Implement actual events
  }

  @Override
  public void save(final DataOutputStream dos) throws IOException {
    dos.writeByte(artifactSpawnSpeed);
    dos.writeByte(spaceDevourerSpeed);
    dos.writeInt(chanceForArtifact);
    dos.writeInt(chanceForDevourer);
    dos.writeByte(ascensionActivation);
  }

  /**
   * Certain ascension event type happens by one the players.
   * Increase the chances for artifacts and devourers.
   * @param type AscensionEventType
   */
  public void eventHappens(final AscensionEventType type) {
    switch (type) {
      default:
      case RESEARCH_ARTIFACT: {
        if (artifactSpawnSpeed < 30) {
          artifactSpawnSpeed++;
        }
        if (spaceDevourerSpeed < 15) {
          spaceDevourerSpeed++;
        }
        break;
      }
    case GAIN_GRAVITY_RIPPER: {
      if (artifactSpawnSpeed < 30) {
        artifactSpawnSpeed++;
      }
      if (spaceDevourerSpeed < 15) {
        spaceDevourerSpeed = spaceDevourerSpeed + 2;
      }
      break;
    }
    case GAIN_ASCENSION_PORTAL_TECH: {
      if (artifactSpawnSpeed < 30) {
        artifactSpawnSpeed++;
      }
      if (spaceDevourerSpeed < 15) {
        spaceDevourerSpeed = spaceDevourerSpeed + 2;
      }
      break;
    }
    case ACTIVATE_GRAVITY_RIPPER: {
      if (artifactSpawnSpeed < 30) {
        artifactSpawnSpeed = artifactSpawnSpeed + 2;
      }
      if (spaceDevourerSpeed < 15) {
        spaceDevourerSpeed = spaceDevourerSpeed + 5;
      }
      break;
    }
    case ACTIVATE_ASCENSION_PORTAL: {
      if (artifactSpawnSpeed < 30) {
        artifactSpawnSpeed = artifactSpawnSpeed + 2;
      }
      if (spaceDevourerSpeed < 15) {
        spaceDevourerSpeed = spaceDevourerSpeed + 5;
      }
      break;
    }
  }
  }
  /**
   * Get Artifact spawn speed.
   * @return Artifact spawn speed.
   */
  public int getArtifactSpawnSpeed() {
    return artifactSpawnSpeed;
  }

  /**
   * Set Artifact spawn speed. This how much chance is
   * increased per star year.
   * @param artifactSpawnSpeed New artifact spawn speed.
   */
  public void setArtifactSpawnSpeed(final int artifactSpawnSpeed) {
    this.artifactSpawnSpeed = artifactSpawnSpeed;
  }

  /**
   * Get Space devourer speed.
   * @return  Space devourer spawn speed.
   */
  public int getSpaceDevourerSpeed() {
    return spaceDevourerSpeed;
  }

  /**
   * Set devourer spawn speed. This how much chance is
   * increased per star year.
   * @param spaceDevourerSpeed New devourer spawn speed.
   */
  public void setSpaceDevourerSpeed(final int spaceDevourerSpeed) {
    this.spaceDevourerSpeed = spaceDevourerSpeed;
  }

  /**
   * Get Ascension activation level
   * @return Ascension activation level
   */
  public byte getAscensionActivation() {
    return ascensionActivation;
  }

  /**
   * Set Ascension activation level.
   * @param ascensionActivation New ascension activation level
   */
  public void setAscensionActivation(final byte ascensionActivation) {
    this.ascensionActivation = ascensionActivation;
  }

}
