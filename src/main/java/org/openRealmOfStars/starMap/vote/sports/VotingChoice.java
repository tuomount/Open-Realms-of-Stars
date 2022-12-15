package org.openRealmOfStars.starMap.vote.sports;


/**
*
* Open Realm of Stars game project
* Copyright (C) 2019,2022 Tuomo Untinen
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
* Voting choices
*
*/
public enum VotingChoice {
  /**
   * Realm has not voted yet.
   */
  NOT_VOTED,
  /**
   * Realm has voted yes.
   */
  VOTED_YES,
  /**
   * Realm has voted no.
   */
  VOTED_NO,
  /**
   * Vote has been abstained
   */
  ABSTAIN;

  /**
   * Get index as int for voting choice
   * @return index for voting choice
   */
  public int getIndex() {
    switch (this) {
      case NOT_VOTED: return 0;
      case VOTED_YES: return 1;
      case VOTED_NO:  return 2;
      case ABSTAIN:   return 3;
      default: return 0;
    }
  }

  /**
   * Get description for voting choice
   * @return description as string
   */
  public String getDescription() {
    switch (this) {
      case NOT_VOTED: return "Not voted";
      case VOTED_YES: return "Yes";
      case VOTED_NO:  return "No";
      case ABSTAIN:   return "Abstain";
      default: return "Not voted";
    }
  }

  /**
   * Get voting choice by given index.
   * @param index Voting choice index
   * @return VotingChoice
   */
  public static VotingChoice getTypeByIndex(final int index) {
    switch (index) {
      case 0: return NOT_VOTED;
      case 1: return VOTED_YES;
      case 2: return VOTED_NO;
      case 3: return ABSTAIN;
      default: return NOT_VOTED;
    }
  }
}
