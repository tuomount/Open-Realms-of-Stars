package org.openRealmOfStars.starMap.vote;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
* Vote for single voting thing.
*
*/
public class Vote {

  /**
   * Voting type, vote about what.
   */
  private VotingType type;

  /**
   * Choices for each realm. True for supporting vote, false for negative vote.
   */
  private boolean[] choices;

  /**
   * Population of each realm at voting time.
   */
  private int[] numberOfVotes;

  /**
   * How many turns left before voting.
   */
  private int turnsToVote;

  /**
   * Constructor for vote. Contains all information for vote.
   * @param type Voting type
   * @param numberOfRealms Number of realms in game.
   * @param turns Number of turns when vote happens.
   */
  public Vote(final VotingType type, final int numberOfRealms,
      final int turns) {
    this.type = type;
    choices = new boolean[numberOfRealms];
    numberOfVotes = new int[numberOfRealms];
    setTurnsToVote(turns);
  }

  /**
   * Get Voting type.
   * @return VotingType
   */
  public VotingType getType() {
    return type;
  }

  /**
   * Get Number of turns to vote.
   * @return Number of turns
   */
  public int getTurnsToVote() {
    return turnsToVote;
  }

  /**
   * Set number of turns to vote.
   * @param turnsToVote Number of turns
   */
  public void setTurnsToVote(final int turnsToVote) {
    this.turnsToVote = turnsToVote;
  }

  /**
   * Set Choice for realm
   * @param index Realm index
   * @param choice Choice for voting
   */
  public void setChoice(final int index, final boolean choice) {
    if (index >= 0 && index < choices.length) {
      choices[index] = choice;
    }
  }

  /**
   * Get Choice for realm.
   * @param index Realm index.
   * @return Choice as a boolean
   */
  public boolean getChoice(final int index) {
    if (index >= 0 && index < choices.length) {
      return choices[index];
    }
    return false;
  }

  /**
   * Set number of votes for single realm.
   * @param index Realm index
   * @param votes Number of votes
   */
  public void setNumberOfVotes(final int index, final int votes) {
    if (index >= 0 && index < numberOfVotes.length) {
      numberOfVotes[index] = votes;
    }
  }

  /**
   * Get Number of votes
   * @param index Realm index.
   * @return Number of votes.
   */
  public int getNumberOfVotes(final int index) {
    if (index >= 0 && index < numberOfVotes.length) {
      return numberOfVotes[index];
    }
    return 0;
  }
}
