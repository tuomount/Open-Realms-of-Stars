package org.openRealmOfStars.player.artifact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2022 Tuomo Untinen
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
* Artifact lists for player. This contains both found but not research
* artifacts and fully researched artifacts.
*
*/
public class ArtifactLists {
  /**
   * List of artifacts which are found but not researched yet.
   */
  private ArrayList<Artifact> discoveredArtifacts;
  /**
   * List of artifact which are fully researched.
   */
  private ArrayList<Artifact> researchedArtifacts;

  /**
   * Amount of research points achieved in artifact research.
   */
  private int artifactResearchPoints;

  /**
   * Constructor for artifact lists.
   */
  public ArtifactLists() {
    discoveredArtifacts = new ArrayList<>();
    researchedArtifacts = new ArrayList<>();
    setArtifactResearchPoints(0);
  }
  /**
   * Read Artifact lists for DataInputStream
   * @param dis Data input stream
   * @throws IOException if there is any problem with DataInputStream
   */
  public ArtifactLists(final DataInputStream dis)
      throws IOException {
    artifactResearchPoints = dis.readInt();
    discoveredArtifacts = new ArrayList<>();
    researchedArtifacts = new ArrayList<>();
    int numberOfDiscovered = dis.read();
    int numberOfResearched = dis.read();
    for (int i = 0; i < numberOfDiscovered; i++) {
      int index = dis.read();
      Artifact artifact = ArtifactFactory.createArtifact(index);
      discoveredArtifacts.add(artifact);
    }
    for (int i = 0; i < numberOfResearched; i++) {
      int index = dis.read();
      Artifact artifact = ArtifactFactory.createArtifact(index);
      researchedArtifacts.add(artifact);
    }
  }

  /**
   * Save Artifact lists for DataOutputStream
   * @param dos Data output stream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveArtifactLists(final DataOutputStream dos)
      throws IOException {
    dos.writeDouble(artifactResearchPoints);
    dos.writeByte(discoveredArtifacts.size());
    dos.writeByte(researchedArtifacts.size());
    for (int i = 0; i < discoveredArtifacts.size(); i++) {
      Artifact artifact = discoveredArtifacts.get(i);
      dos.writeByte(artifact.getIndex());
    }
    for (int i = 0; i < researchedArtifacts.size(); i++) {
      Artifact artifact = researchedArtifacts.get(i);
      dos.writeByte(artifact.getIndex());
    }
  }
  /**
   * Get Artifact research points.
   * @return Artifact research points.
   */
  public int getArtifactResearchPoints() {
    return artifactResearchPoints;
  }

  /**
   * Set Artifact research points.
   * @param artifactResearchPoints to set.
   */
  public void setArtifactResearchPoints(final int artifactResearchPoints) {
    this.artifactResearchPoints = artifactResearchPoints;
  }

  /**
   * Get list of discovered but not research artifacts in single array.
   * @return Array of artifacts.
   */
  public Artifact[] getDiscoveredArtifacts() {
    return discoveredArtifacts.toArray(
        new Artifact[discoveredArtifacts.size()]);
  }

  /**
   * Get list of researched artifacts in single array.
   * @return Array of artifacts.
   */
  public Artifact[] getResearchedArtifacts() {
    return researchedArtifacts.toArray(
        new Artifact[researchedArtifacts.size()]);
  }

  /**
   * Add new Discovered artifact to list.
   * @param artifact New artifact discovered.
   */
  public void addDiscoveredArtifact(final Artifact artifact) {
    discoveredArtifacts.add(artifact);
  }

  /**
   * Research random artifact. This will move artifact from
   * discovered list to research list.
   * @return Research artifact or null if none artifact is available.
   */
  public Artifact researchArtifact() {
    if (discoveredArtifacts.size() > 0) {
      int index = DiceGenerator.getRandom(0, discoveredArtifacts.size() - 1);
      researchedArtifacts.add(discoveredArtifacts.get(index));
      discoveredArtifacts.remove(index);
      return researchedArtifacts.get(index);
    }
    return null;
  }

  /**
   * If list has discovered artifacts which could be research.
   * @return True if above statement is true.
   */
  public boolean hasDiscoveredArtifacts() {
    if (discoveredArtifacts.size() > 0) {
      return true;
    }
    return false;
  }

  /**
   * Update artifact research points by turn.
   * @param totalResearchPoints Total research points to add
   * @param info PlayerInfo who is research.
   * @param gameLength Game length
   */
  public void updateResearchPointByTurn(final int totalResearchPoints,
      final PlayerInfo info, final int gameLength) {
    artifactResearchPoints = artifactResearchPoints + totalResearchPoints;
    int lvl = researchedArtifacts.size();
    int limit = ArtifactFactory.getResearchCost(lvl, gameLength);
    if (artifactResearchPoints >= limit) {
      Artifact artifact = researchArtifact();
      artifactResearchPoints = artifactResearchPoints - limit;
      StringBuilder sb = new StringBuilder();
      sb.append(info.getEmpireName());
      sb.append(" researched ");
      sb.append(artifact.getName());
      sb.append(". This will boost ");
      sb.append(TechType.getTypeByIndex(artifact.getArtifactType().getIndex())
          .toString());
      sb.append(" research by ");
      sb.append(artifact.getOneTimeTechBonus());
      sb.append(". ");
      Double value = info.getTechList().getTechResearchPoints(
          TechType.getTypeByIndex(artifact.getArtifactType().getIndex()));
      info.getTechList().setTechResearchPoints(
          TechType.getTypeByIndex(artifact.getArtifactType().getIndex()),
          value + artifact.getOneTimeTechBonus());
      Message msg = new Message(MessageType.RESEARCH, sb.toString(),
          Icons.getIconByName(Icons.ICON_RESEARCH));
      msg.setMatchByString(artifact.getName());
      info.getMsgList().addNewMessage(msg);

    }
  }
}
