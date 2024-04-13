package org.openRealmOfStars.player.government;
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
 * Enumeration how ruler is selected for government.
 *
 */
public enum RulerSelection {

  /** Strong ruler is selected which has aggressive perks. Age also matters
   * and should not be too young or too old. Also if parent has been known
   * leader this affects.
   */
  STRONG_RULER,
  /** Ruler is being selected based hereditary ranks. If there are not heirs,
   * then STRONG_RULER is being used.
   */
  HEIR_TO_THRONE,
  /**
   * Ruler is selected like CEO is being selected for big corporation. Leader
   * needs to be skillful and willing to make credits. Older candidates are
   * more likely to be selected.
   */
  CEO_AS_A_RULER,
  /**
   * Ruler is being elected, charismatic, peaceful and skillful leader is good
   * chance to be elected. Certain age is good, but too old start dropping the
   * chances. There is also a bit randomness which is simulating the voting.
   */
  ELECTION_TYPE1,
  /**
   * Ruler is being elected, charismatic, strong and skillful leader is good
   * chance to be elected. Old age is good for election.
   * There is also a bit randomness which is simulating the voting.
   */
  ELECTION_TYPE2,
  /**
   * Strong minded, charismatic leader has good chance to win. Candidate age
   * also affects, but too old get negative score.
   */
  HEGEMONIA_RULER,
  /**
   * Skillful, charismatic leader, but pacifist or peacefull is negative for
   * leader. Age does not matter at all. Only number of perks.
   */
  AI_RULER;

}
