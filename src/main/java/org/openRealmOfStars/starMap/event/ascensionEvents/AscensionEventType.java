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

/**
 * Enumeration of events that move closer to ascension victory.
 */
public enum AscensionEventType {
  /**
   * Realm researches single artifact
   */
  RESEARCH_ARTIFACT,
  /**
   * Realm has researched gravity ripper while researching artifacts.
   */
  GAIN_GRAVITY_RIPPER,
  /**
   * Realm has researched one the ascension portal tech while researching
   *  artifacts.
   */
  GAIN_ASCENSION_PORTAL_TECH,
  /**
   * Realm has activated gravity ripper near black hole. Ascension planet
   * has been revealed.
   */
  ACTIVATE_GRAVITY_RIPPER,
  /**
   * Realm has built ascension portal, almost done.
   */
  ACTIVATE_ASCENSION_PORTAL,
  /**
   * One of the realms has discovered black hole.
   */
  BLACK_HOLE_DISCOVERED
}
