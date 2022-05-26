package org.openRealmOfStars.player.artifact;

import java.util.ArrayList;

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
  private double artifactResearchPoints;

  /**
   * Constructor for artifact lists.
   */
  public ArtifactLists() {
    discoveredArtifacts = new ArrayList<>();
    researchedArtifacts = new ArrayList<>();
    setArtifactResearchPoints(0);
  }

  /**
   * Get Artifact research points.
   * @return Artifact research points.
   */
  public double getArtifactResearchPoints() {
    return artifactResearchPoints;
  }

  /**
   * Set Artifact research points.
   * @param artifactResearchPoints to set.
   */
  public void setArtifactResearchPoints(final double artifactResearchPoints) {
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

}
