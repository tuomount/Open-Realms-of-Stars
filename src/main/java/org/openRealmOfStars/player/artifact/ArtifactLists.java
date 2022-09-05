package org.openRealmOfStars.player.artifact;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.leader.stats.StatType;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.tech.TechFactory;
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
    dos.writeInt(artifactResearchPoints);
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
      Artifact artifact = discoveredArtifacts.get(index);
      researchedArtifacts.add(discoveredArtifacts.get(index));
      discoveredArtifacts.remove(index);
      return artifact;
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
   * If list has broadcasting electornic from news station.
   * @return True if above statement is true.
   */
  public boolean hasBroadcastingArtifact() {
    boolean result = false;
    for (Artifact artifact : discoveredArtifacts) {
      if (artifact.getIndex() == ArtifactFactory.BROADCASTING_ELETRONIC) {
        result = true;
      }
    }
    if (!result) {
      for (Artifact artifact : researchedArtifacts) {
        if (artifact.getIndex() == ArtifactFactory.BROADCASTING_ELETRONIC) {
          result = true;
        }
      }
    }
    return result;
  }

  /**
   * Take random discovered artifact away from the list and return it.
   * @return Artifact or null
   */
  public Artifact takeRandomDiscoveredArtifact() {
    if (discoveredArtifacts.size() > 0) {
      int index = DiceGenerator.getRandom(0, discoveredArtifacts.size() - 1);
      Artifact artifact = discoveredArtifacts.get(index);
      discoveredArtifacts.remove(index);
      return artifact;
    }
    return null;

  }
  /**
   * Get number of research certain type of artifacts.
   * @param type Artifact type
   * @return Number of research artifact from certain type.
   */
  public int getTypesResearched(final ArtifactType type) {
    int result = 0;
    for (Artifact artifact : researchedArtifacts) {
      if (artifact.getArtifactType() == type) {
        result++;
      }
    }
    return result;
  }

  /**
   * Generate ancient tech based on techname and level and type.
   * Returns string if ancient tech was discoverd. Otherwise returns null.
   * @param info Realm make the discovery
   * @param techName Technology name
   * @param techLevel Technology level
   * @param techType Technology Type
   * @param artifactType Artifact Type
   * @return String or null
   */
  private String generateAncientTech(final PlayerInfo info,
      final String techName, final int techLevel, final TechType techType,
      final ArtifactType artifactType) {
    String result = null;
    int currentTechLevel = info.getTechList().getTechLevel(TechType.Combat);
    if (currentTechLevel >= techLevel) {
      int chance = getTypesResearched(artifactType) * 10;
      if (DiceGenerator.getRandom(100) < chance
          && !info.getTechList().hasTech(techName)) {
        StringBuilder sb = new StringBuilder();
        sb.append(info.getEmpireName());
        sb.append(" has learned ");
        sb.append(techName);
        sb.append(" while studying artifacts.");
        switch (techType) {
          default:
          case Combat: {
            info.getTechList().addTech(TechFactory.createCombatTech(
                techName, techLevel));
            break;
          }
          case Defense: {
            info.getTechList().addTech(TechFactory.createDefenseTech(
                techName, techLevel));
            break;
          }
          case Hulls: {
            info.getTechList().addTech(TechFactory.createHullTech(
                techName, techLevel));
            break;
          }
          case Improvements: {
            info.getTechList().addTech(TechFactory.createImprovementTech(
                techName, techLevel));
            break;
          }
          case Propulsion: {
            info.getTechList().addTech(TechFactory.createPropulsionTech(
                techName, techLevel));
            break;
          }
          case Electrics: {
            info.getTechList().addTech(TechFactory.createElectronicsTech(
                techName, techLevel));
            break;
          }
        }
        result = sb.toString();
      }
    }
    return result;
  }
  /**
   * Update artifact research points by turn.
   * @param totalResearchPoints Total research points to add
   * @param info PlayerInfo who is research.
   * @param gameLength Game length
   * @param scientist which is make the research
   * @param tutorialEnabled is tutorial enabled or not.
   */
  public void updateResearchPointByTurn(final int totalResearchPoints,
      final PlayerInfo info, final int gameLength, final Leader scientist,
      final boolean tutorialEnabled) {
    artifactResearchPoints = artifactResearchPoints + totalResearchPoints;
    int lvl = researchedArtifacts.size();
    int limit = ArtifactFactory.getResearchCost(lvl, gameLength);
    if (artifactResearchPoints >= limit) {
      if (Game.getTutorial() != null  && info.isHuman() && tutorialEnabled) {
        String tutorialText = Game.getTutorial().showTutorialText(16);
        if (tutorialText != null) {
          Message msg = new Message(MessageType.INFORMATION, tutorialText,
              Icons.getIconByName(Icons.ICON_TUTORIAL));
          info.getMsgList().addNewMessage(msg);
        }
      }
      Artifact artifact = researchArtifact();
      artifactResearchPoints = artifactResearchPoints - limit;
      StringBuilder sb = new StringBuilder();
      sb.append(scientist.getCallName());
      sb.append(" researched ");
      sb.append(artifact.getName());
      sb.append(" for ");
      sb.append(info.getEmpireName());
      sb.append(". This will boost ");
      sb.append(TechType.getTypeByIndex(artifact.getArtifactType().getIndex())
          .toString());
      sb.append(" research by ");
      sb.append(artifact.getOneTimeTechBonus());
      sb.append(". ");
      scientist.getStats().addOne(StatType.RESEARCH_ARTIFACTS);
      if (scientist.getStats().getStat(StatType.RESEARCH_ARTIFACTS) == 2) {
        scientist.addPerk(Perk.ARCHAEOLOGIST);
        sb.append(scientist.getName());
        sb.append(" becomes expert on researching artifacts. ");
      }
      if (artifact.getArtifactType() == ArtifactType.MILITARY) {
        boolean techGained = false;
        String techName = "Gravity ripper Mk1";
        String event = generateAncientTech(info, techName, 6, TechType.Combat,
            ArtifactType.MILITARY);
        if (event != null) {
          techGained = true;
          sb.append(event);
        }
        techName = "Gravity ripper Mk2";
        if (!techGained) {
          event = generateAncientTech(info, techName, 8, TechType.Combat,
              ArtifactType.MILITARY);
          if (event != null) {
            techGained = true;
            sb.append(event);
          }
        }
        techName = "Gravity ripper Mk3";
        if (!techGained) {
          event = generateAncientTech(info, techName, 10, TechType.Combat,
              ArtifactType.MILITARY);
          if (event != null) {
            techGained = true;
            sb.append(event);
          }
        }
      }
      if (artifact.getArtifactType() == ArtifactType.DEFENSE) {
        String techName = "Multi-dimension shield";
        String event = generateAncientTech(info, techName, 7, TechType.Defense,
            ArtifactType.DEFENSE);
        if (event != null) {
          sb.append(event);
        }
      }
      if (artifact.getArtifactType() == ArtifactType.SHIPHULL) {
        boolean techGained = false;
        String techName = "Repair module Mk1";
        String event = generateAncientTech(info, techName, 3, TechType.Hulls,
            ArtifactType.SHIPHULL);
        if (event != null) {
          sb.append(event);
        }
        if (!techGained) {
          techName = "Repair module Mk2";
          event = generateAncientTech(info, techName, 5, TechType.Hulls,
              ArtifactType.SHIPHULL);
          if (event != null) {
            sb.append(event);
          }
        }
        if (!techGained) {
          techName = "Repair module Mk3";
          event = generateAncientTech(info, techName, 7, TechType.Hulls,
              ArtifactType.SHIPHULL);
          if (event != null) {
            sb.append(event);
          }
        }
      }
      if (artifact.getArtifactType() == ArtifactType.ENERGY) {
        boolean techGained = false;
        String techName = "Tachyon source Mk3";
        String event = generateAncientTech(info, techName, 5,
            TechType.Propulsion, ArtifactType.ENERGY);
        if (event != null) {
          sb.append(event);
        }
        if (!techGained) {
          techName = "Antimatter source Mk3";
          event = generateAncientTech(info, techName, 8,
              TechType.Propulsion, ArtifactType.ENERGY);
          if (event != null) {
            sb.append(event);
          }
        }
        if (!techGained) {
          techName = "Zero-point source Mk3";
          event = generateAncientTech(info, techName, 10,
              TechType.Propulsion, ArtifactType.ENERGY);
          if (event != null) {
            sb.append(event);
          }
        }
      }
      if (artifact.getArtifactType() == ArtifactType.FACILITY) {
        String techName = "Starbase ascension portal";
        String event = generateAncientTech(info, techName, 7,
            TechType.Improvements, ArtifactType.FACILITY);
        if (event != null) {
          sb.append(event);
        }
      }
      int chance = researchedArtifacts.size() * 10;
      if (scientist.hasPerk(Perk.ARCHAEOLOGIST)) {
        chance = chance + 20;
      }
      if (DiceGenerator.getRandom(100) < chance
          && !info.getTechList().hasTech(TechType.Improvements,
              "College of history")) {
        sb.append(info.getEmpireName());
        sb.append(" has learned College of history while studying artifacts.");
        info.getTechList().addTech(TechFactory.createImprovementTech(
            "College of history", 3));
      }
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
