package org.openRealmOfStars.starMap.vote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;

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
* Votes for starmap
*
*/
public class Votes {

  /**
   * List of votes for starmap.
   */
  private ArrayList<Vote> listOfVotes;

  /**
   * Constructor for Votes.
   */
  public Votes() {
    listOfVotes = new ArrayList<>();
  }

  /**
   * Read Votes from DataInputStream
   * @param dis DataInputStream
   * @param numberOfRealms Number of Realms in starmap.
   * @throws IOException if there is any problem with DataInputStream
   */
  public Votes(final DataInputStream dis, final int numberOfRealms)
      throws IOException {
    listOfVotes = new ArrayList<>();
    int count = dis.readInt();
    for (int i = 0; i < count; i++) {
      Vote vote = new Vote(dis, numberOfRealms);
      listOfVotes.add(vote);
    }
  }
  /**
   * Get Votes as a list
   * @return ArrayList of votes.
   */
  public ArrayList<Vote> getVotes() {
    return listOfVotes;
  }

  /**
   * Get Next important vote, which isn't galactic olympic participation.
   * @return Vote or null
   */
  public Vote getNextImportantVote() {
    for (Vote vote : listOfVotes) {
      if (vote.getType() != VotingType.GALACTIC_OLYMPIC_PARTICIPATE
          && vote.getTurnsToVote() > 0) {
        return vote;
      }
    }
    return null;
  }

  /**
   * Generate next important vote. This will add new vote
   * to list of votes automatically.
   * @param maxNumberOfVotes Maximum number of votes
   * @param numberOfRealms NUmber of realms in starmap
   * @param turns When voting needs to be done.
   * @return Vote if able to add new one, otherwise null
   */
  public Vote generateNextVote(final int maxNumberOfVotes,
      final int numberOfRealms, final int turns) {
    int count = 0;
    for (Vote vote : listOfVotes) {
      if (vote.getType() != VotingType.GALACTIC_OLYMPIC_PARTICIPATE
          && vote.getType() != VotingType.FIRST_CANDIDATE
          && vote.getType() != VotingType.SECOND_CANDIDATE) {
        count++;
      }
    }
    if (maxNumberOfVotes - count == 1) {
      Vote vote = new Vote(VotingType.RULER_OF_GALAXY, numberOfRealms, turns);
      vote.setOrganizerIndex(getFirstCandidate());
      vote.setSecondCandidateIndex(getSecondCandidate());
      listOfVotes.add(vote);
      return vote;
    }
    if (maxNumberOfVotes - count == 2) {
      Vote vote = new Vote(VotingType.SECOND_CANDIDATE_MILITARY,
          numberOfRealms, turns);
      listOfVotes.add(vote);
      return vote;
    }
    if (maxNumberOfVotes - count > 2) {
      ArrayList<VotingType> types = new ArrayList<>();
      types.add(VotingType.BAN_NUCLEAR_WEAPONS);
      types.add(VotingType.BAN_PRIVATEER_SHIPS);
      types.add(VotingType.GALACTIC_PEACE);
      types.add(VotingType.TAXATION_OF_RICHEST_REALM);
      for (Vote vote : listOfVotes) {
        types.remove(vote.getType());
      }
      int index = DiceGenerator.getRandom(types.size() - 1);
      Vote vote = new Vote(types.get(index), numberOfRealms, turns);
      listOfVotes.add(vote);
      return vote;
    }
    return null;
  }
  /**
   * Has first candidate selected or not.
   * @return True if selected.
   */
  public boolean firstCandidateSelected() {
    for (Vote vote : listOfVotes) {
      if (vote.getType() == VotingType.FIRST_CANDIDATE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get First candidate realm index
   * @return First candidate realm index or -1 if not set yet
   */
  public int getFirstCandidate() {
    for (Vote vote : listOfVotes) {
      if (vote.getType() == VotingType.FIRST_CANDIDATE) {
        return vote.getOrganizerIndex();
      }
    }
    return -1;
  }
  /**
   * Get Second candidate realm index
   * @return Second candidate realm index or -1 if not set yet
   */
  public int getSecondCandidate() {
    for (Vote vote : listOfVotes) {
      if (vote.getType() == VotingType.SECOND_CANDIDATE) {
        return vote.getOrganizerIndex();
      }
    }
    return -1;
  }
  /**
   * Save votes to Data output stream
   * @param dos DataOutputStream where to write
   * @throws IOException If writing fails.
   */
  public void saveVotes(final DataOutputStream dos) throws IOException {
    dos.writeInt(listOfVotes.size());
    for (int i = 0; i < listOfVotes.size(); i++) {
      listOfVotes.get(i).saveVote(dos);
    }
  }
}
