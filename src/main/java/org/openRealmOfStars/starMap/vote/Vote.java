package org.openRealmOfStars.starMap.vote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;
import org.openRealmOfStars.utilities.IOUtilities;

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
  private VotingChoice[] choices;

  /**
   * Population of each realm at voting time.
   */
  private int[] numberOfVotes;

  /**
   * How many turns left before voting.
   */
  private int turnsToVote;

  /**
   * Galactic olympic organizer index for realm.
   */
  private int organizerIndex;

  /**
   * Second candidate index.
   */
  private int secondCandidateIndex;

  /**
   * Galactic olympic planet nane.
   */
  private String planetName;

  /**
   * Constructor for vote. Contains all information for vote.
   * @param type Voting type
   * @param numberOfRealms Number of realms in game.
   * @param turns Number of turns when vote happens.
   */
  public Vote(final VotingType type, final int numberOfRealms,
      final int turns) {
    this.type = type;
    choices = new VotingChoice[numberOfRealms];
    for (int i = 0; i < choices.length; i++) {
      choices[i] = VotingChoice.NOT_VOTED;
    }
    numberOfVotes = new int[numberOfRealms];
    setTurnsToVote(turns);
    organizerIndex = 0;
    planetName = "";
  }

  /**
   * Get the number of realms as integer.
   * @return Number of realms
   */
  public int getNumberOfRealms() {
    return choices.length;
  }
  /**
   * Get amount of voting number.
   * @param choice Voting choice
   * @return Numerical value of voting result
   */
  public int getVotingAmounts(final VotingChoice choice) {
    int result = 0;
    for (int i = 0; i < choices.length; i++) {
      if (choices[i] == choice) {
        result = result + numberOfVotes[i];
      }
    }
    return result;
  }
  /**
   * Get voting result
   * @param drawRuler Index whom choice is used on draw result
   * @return Voting result
   */
  public VotingChoice getResult(final int drawRuler) {
    int yes = 0;
    int no = 0;
    for (int i = 0; i < choices.length; i++) {
      if (choices[i] == VotingChoice.VOTED_YES) {
        yes = yes + numberOfVotes[i];
      }
      if (choices[i] == VotingChoice.VOTED_NO) {
        no = no + numberOfVotes[i];
      }
    }
    if (yes > no) {
      return VotingChoice.VOTED_YES;
    }
    if (yes < no) {
      return VotingChoice.VOTED_NO;
    }
    if (yes == no) {
      // First candidates voting wins on draw
      return choices[drawRuler];
    }
    // This should not happen.
    return VotingChoice.NOT_VOTED;
  }
  /**
   * Constructor for vote by reading it from DataInputStream
   * @param dis DataInputStream
   * @param numberOfRealms Number of realms in game.
   * @throws IOException if there is any problem with DataInputStream
   */
  public Vote(final DataInputStream dis, final int numberOfRealms)
      throws IOException {
    int typeValue = dis.readInt();
    type = VotingType.getTypeByIndex(typeValue);
    setTurnsToVote(dis.readInt());
    choices = new VotingChoice[numberOfRealms];
    numberOfVotes = new int[numberOfRealms];
    for (int i = 0; i < choices.length; i++) {
      choices[i] = VotingChoice.NOT_VOTED;
    }
    // Here should handle special vote specific information
    if (type == VotingType.GALACTIC_OLYMPIC_PARTICIPATE) {
      setOrganizerIndex(dis.read());
      setPlanetName(IOUtilities.readString(dis));
    }
    if (type == VotingType.RULER_OF_GALAXY) {
      setOrganizerIndex(dis.read());
      setSecondCandidateIndex(dis.read());
    }

    if (type == VotingType.FIRST_CANDIDATE) {
      setOrganizerIndex(dis.read());
    }
    if (type == VotingType.SECOND_CANDIDATE) {
      setOrganizerIndex(dis.read());
    }
    if (turnsToVote == 0) {
      // Vote has been done so reading the vote results
      for (int i = 0; i < choices.length; i++) {
        int value = dis.read();
        choices[i] = VotingChoice.getTypeByIndex(value);
        numberOfVotes[i] = dis.readInt();
      }
    } else {
   // Vote has not been done so reading the vote choices
      for (int i = 0; i < choices.length; i++) {
        int value = dis.read();
        choices[i] = VotingChoice.getTypeByIndex(value);
      }
    }
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
  public void setChoice(final int index, final VotingChoice choice) {
    if (index >= 0 && index < choices.length) {
      choices[index] = choice;
    }
  }

  /**
   * Get Choice for realm.
   * @param index Realm index.
   * @return Choice as a VotingChoice
   */
  public VotingChoice getChoice(final int index) {
    if (index >= 0 && index < choices.length) {
      return choices[index];
    }
    return VotingChoice.NOT_VOTED;
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

  /**
   * Save vote to Data output stream
   * @param dos DataOutputStream where to write
   * @throws IOException If writing fails.
   */
  public void saveVote(final DataOutputStream dos) throws IOException {
    dos.writeInt(type.getIndex());
    dos.writeInt(turnsToVote);
    // Here should handle special vote specific information
    if (type == VotingType.GALACTIC_OLYMPIC_PARTICIPATE) {
      dos.writeByte(getOrganizerIndex());
      IOUtilities.writeString(dos, getPlanetName());
    }
    if (type == VotingType.RULER_OF_GALAXY) {
      dos.writeByte(getOrganizerIndex());
      dos.writeByte(getSecondCandidateIndex());
    }
    if (type == VotingType.FIRST_CANDIDATE) {
      dos.writeByte(getOrganizerIndex());
    }
    if (type == VotingType.SECOND_CANDIDATE) {
      dos.writeByte(getOrganizerIndex());
    }
    if (turnsToVote == 0) {
      // Vote has been done so writing the vote results
      for (int i = 0; i < choices.length; i++) {
        dos.writeByte(choices[i].getIndex());
        dos.writeInt(numberOfVotes[i]);
      }
    } else {
      // Vote has not been done so writing the vote just the choices
      for (int i = 0; i < choices.length; i++) {
        dos.writeByte(choices[i].getIndex());
      }
    }
  }

  /**
   * Get Description of voting. StarMap is used to fetch realm names.
   * @param map StarMap
   * @return Voting description
   */
  public String getDescription(final StarMap map) {
    StringBuilder sb = new StringBuilder();
    sb.append(getType().getDescription());
    if (getType() == VotingType.GALACTIC_OLYMPIC_PARTICIPATE) {
      sb.append(" organized by ");
      sb.append(map.getPlayerList().getPlayerInfoByIndex(
          getOrganizerIndex()).getEmpireName());
    }
    if (getType() == VotingType.RULER_OF_GALAXY) {
      sb.append(" ");
      sb.append(map.getPlayerList().getPlayerInfoByIndex(
          getOrganizerIndex()).getEmpireName());
      sb.append(" VS ");
      sb.append(map.getPlayerList().getPlayerInfoByIndex(
          getSecondCandidateIndex()).getEmpireName());
    }
    return sb.toString();
  }
  /**
   * Get Long description of voting. This will also contain the short
   * description.
   * @param map StarMap
   * @return Description
   */
  public String getLongDescription(final StarMap map) {
    StringBuilder sb = new StringBuilder();
    sb.append(getDescription(map));
    sb.append("\n\n");
    switch (type) {
    case BAN_NUCLEAR_WEAPONS: {
      sb.append("Orbital nuclear bombs will be banned if this voting passes."
          + " It means after this there cannot be new ship design with"
          + " nuclear bombs and even old design cannot be build anymore."
          + " However old existing ships with possible nuclear boms are still"
          + " allowed.");
      break;
    }
    case BAN_PRIVATEER_SHIPS: {
      sb.append("Unmarked privateer ships will be banned if this voting"
          + " passes. It means after this there cannot be new ship design with"
          + " privateer ship hull and privateer module. Even old design cannot"
          + " be build anymore. However old existing privateer ships are still"
          + " allowed.");
      break;
    }
    case FIRST_CANDIDATE: {
      sb.append("Internal voting should not be visible to player.");
      break;
    }
    default:
    case SECOND_CANDIDATE: {
      sb.append("Internal voting should not be visible to player.");
      break;
    }
    case GALACTIC_OLYMPIC_PARTICIPATE: {
      sb.append("Biggest and best galactic sport event until someone arranges"
          + " new galactic olympics. Galactic olympics are good way to gain"
          + " good reputation among supports but it can cause bad reputation"
          + " along non supporting realms.");
      break;
    }
    case GALACTIC_PEACE: {
      sb.append("Galactic wide peace over all realms. If this voting passes"
          + " all war is galaxy will be changed to peace. How ever this does"
          + " not prevent getting new wars after voting has passed.");
      break;
    }
    case TAXATION_OF_RICHEST_REALM: {
      sb.append("Taxation of richest realm voting is that if this passes realm"
          + " which has most of the wealth will pay one credit for poorest"
          + " realm after this. Note that richest and poorest realm can be"
          + " changed over the time.");
      break;
    }
    case SECOND_CANDIDATE_MILITARY: {
      sb.append("If this voting passes it means that second candidate for ruler"
          + " of the galaxy will be the one with highest military. Second and"
          + " first candidate cannot be the same realm. If this voting does"
          + " not pass it means that second candidate will be the one with"
          + " highest amount of United Galaxy Towers. Here applies same rule "
          + " that second and first candidate cannot be the same realm.");
      break;
    }
    case RULER_OF_GALAXY: {
      sb.append("This is voting for Ruler of the Galaxy. There will be two"
          + " candidates: First one is the Galaxy Secretary which build first"
          + " required amount of United Galaxy Towers. Second candidate is"
          + " one who either has biggest military or highest amount of"
          + " United Galaxy Towers. In this voting 50% of galaxy population"
          + " must vote in order this voting to be accepted. Each realm can"
          + " vote either of the candidates or abstain voting. If ruler is not"
          + " decided new voting will be arranged.");
      break;
    }
    }
    return sb.toString();
  }
  /**
   * Get the galactic olympic organizer realm index. This method is also
   * used for FIRST_CANDIDATE and SECOND_CANDIDATE.
   * @return the organizerIndex
   */
  public int getOrganizerIndex() {
    return organizerIndex;
  }

  /**
   * Set the galactic olympics organizer realm index.
   * @param organizerIndex the organizerIndex to set
   */
  public void setOrganizerIndex(final int organizerIndex) {
    this.organizerIndex = organizerIndex;
  }

  /**
   * Get the planet name where galactic olympics is being organized.
   * @return the planetName
   */
  public String getPlanetName() {
    return planetName;
  }

  /**
   * Set the planet name where galactic olympics is being organized.
   * @param planetName the planetName to set
   */
  public void setPlanetName(final String planetName) {
    this.planetName = planetName;
  }

  /**
   * Get Second candidate index
   * @return the secondCandidateIndex
   */
  public int getSecondCandidateIndex() {
    return secondCandidateIndex;
  }

  /**
   * Set second candidate index.
   * @param secondCandidateIndex the secondCandidateIndex to set
   */
  public void setSecondCandidateIndex(final int secondCandidateIndex) {
    this.secondCandidateIndex = secondCandidateIndex;
  }
}
