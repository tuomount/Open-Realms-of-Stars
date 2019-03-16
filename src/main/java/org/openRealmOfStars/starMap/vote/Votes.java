package org.openRealmOfStars.starMap.vote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
