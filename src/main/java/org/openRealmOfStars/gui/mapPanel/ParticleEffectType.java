package org.openRealmOfStars.gui.mapPanel;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2019,2020 Tuomo Untinen
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
 * Particle effect type
 *
 */
public enum ParticleEffectType {
  /**
   * Short living particle next to the beam, not moving
   */
  LASER_PARTICLE,
  /**
   * Fire coming behind the missiles
   */
  MISSILE_FIRE,
  /**
   * Explosion particle
   */
  EXPLOSION_PARTICLE,
  /**
   * Short living photon particle
   */
  PHOTON_TORP_PARTICILE,
  /**
   * Rail gun ion trail
   */
  RAILGUN_TRAIL,
  /**
   * Short living particle next to the beam, not moving
   */
  PHASOR_PARTICLE,
  /**
   * Short living particle next to the beam, not moving
   */
  ANTIMATTER_PARTICLE,
  /**
   * Blue ion particle moving away from center.
   */
  ION_PARTICLE,
  /**
   * Blue ion particle not moving.
   */
  ION_PARTICLE_LOW_ACTIVE;
}
