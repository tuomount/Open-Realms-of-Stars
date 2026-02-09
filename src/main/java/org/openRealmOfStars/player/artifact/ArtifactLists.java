package org.openRealmOfStars.player.artifact;
/*
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
 */

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
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.event.ascensionEvents.AscensionEventType;
import org.openRealmOfStars.starMap.newsCorp.NewsData;
import org.openRealmOfStars.starMap.newsCorp.NewsFactory;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
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
      Artifact artifact = DiceGenerator.pickRandom(discoveredArtifacts);
      researchedArtifacts.add(artifact);
      discoveredArtifacts.remove(artifact);
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
   * If list has destroyed planet artifact..
   * @return True if above statement is true.
   */
  public boolean hasDestroyedPlanetArtifact() {
    boolean result = false;
    for (Artifact artifact : discoveredArtifacts) {
      if (artifact.getIndex() == ArtifactFactory.DESTROYED_PLANET_ARTIFACT) {
        result = true;
      }
    }
    if (!result) {
      for (Artifact artifact : researchedArtifacts) {
        if (artifact.getIndex() == ArtifactFactory.DESTROYED_PLANET_ARTIFACT) {
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
      Artifact artifact = DiceGenerator.pickRandom(discoveredArtifacts);
      discoveredArtifacts.remove(artifact);
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
    int currentTechLevel = info.getTechList().getTechLevel(techType);
    if (currentTechLevel + 3 < techLevel) {
      return null;
    }
    int chance = getTypesResearched(artifactType) * 8;
    if (techType == TechType.Combat) {
      chance = chance + getTypesResearched(ArtifactType.DEFENSE) * 3;
    }
    if (techType == TechType.Defense) {
      chance = chance + getTypesResearched(ArtifactType.MILITARY) * 3;
    }
    if (techType == TechType.Hulls) {
      chance = chance + getTypesResearched(ArtifactType.FACILITY) * 3;
    }
    if (techType == TechType.Improvements) {
      chance = chance + getTypesResearched(ArtifactType.SHIPHULL) * 3;
    }
    if (techType == TechType.Propulsion) {
      chance = chance + getTypesResearched(ArtifactType.ELECTRONIC) * 3;
    }
    if (techType == TechType.Electrics) {
      chance = chance + getTypesResearched(ArtifactType.ENERGY) * 3;
    }
    chance = chance + researchedArtifacts.size() * 2;
    if (currentTechLevel > techLevel) {
      chance = chance * 2;
    }
    if (currentTechLevel + 1 == techLevel) {
      chance = chance / 2;
    }
    if (currentTechLevel + 2 == techLevel) {
      chance = chance / 5;
    }
    if (currentTechLevel + 3 == techLevel) {
      chance = chance / 10;
    }
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
    return result;
  }
  /**
   * Update artifact research points by turn.
   * @param totalResearchPoints Total research points to add
   * @param scientist which is make the research
   * @param starMap Star Map
   * @param info PlayerInfo who is research.
   * @return NewsData about possible study.
   */
  public NewsData updateResearchPointByTurn(final int totalResearchPoints,
      final Leader scientist, final StarMap starMap, final PlayerInfo info) {
    int gameLength = starMap.getScoreVictoryTurn();
    boolean tutorialEnabled = starMap.isTutorialEnabled();
    int starYear = starMap.getStarYear();
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
      if (artifact == null) {
        return null;
      }
      starMap.getAscensionEvents().eventHappens(
          AscensionEventType.RESEARCH_ARTIFACT);
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
        String techName = "Gravity ripper Mk";
        int techLevel = 1;
        int highestMk = info.getTechList().getHighestMk(TechType.Combat,
            techName);
        switch (highestMk) {
          default:
          case 0: {
            techName = "Gravity ripper Mk1";
            techLevel = 10;
            break;
          }
          case 1: {
            techName = "Gravity ripper Mk2";
            techLevel = 12;
            break;
          }
          case 2: {
            techName = "Gravity ripper Mk3";
            techLevel = 16;
            break;
          }
        }
        String event = generateAncientTech(info, techName, techLevel,
            TechType.Combat, ArtifactType.MILITARY);
        if (event != null) {
          sb.append(event);
          starMap.getAscensionEvents().eventHappens(
              AscensionEventType.GAIN_GRAVITY_RIPPER);
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
        String techName = "Repair module Mk";
        int techLevel = 1;
        int highestMk = info.getTechList().getHighestMk(TechType.Hulls,
            techName);
        switch (highestMk) {
          default:
          case 0: {
            techName = "Repair module Mk1";
            techLevel = 3;
            break;
          }
          case 1: {
            techName = "Repair module Mk2";
            techLevel = 5;
            break;
          }
          case 2: {
            techName = "Repair module Mk3";
            techLevel = 7;
            break;
          }
        }
        String event = generateAncientTech(info, techName, techLevel,
            TechType.Hulls, ArtifactType.SHIPHULL);
        if (event != null) {
          sb.append(event);
        }
      }
      if (artifact.getArtifactType() == ArtifactType.ENERGY) {
        boolean techGained = false;
        String techName = "Tachyon source Mk1";
        if (!info.getTechList().hasTech(techName)) {
          String event = generateAncientTech(info, techName, 7,
              TechType.Propulsion, ArtifactType.ENERGY);
          if (event != null) {
            techGained = true;
            sb.append(event);
          }
        }
        if (!techGained) {
          techName = "Tachyon source Mk2";
          if (!info.getTechList().hasTech(techName)) {
            String event = generateAncientTech(info, techName, 9,
                TechType.Propulsion, ArtifactType.ENERGY);
            if (event != null) {
              techGained = true;
              sb.append(event);
            }
          }
        }
        if (!techGained) {
          techName = "Tachyon source Mk3";
          if (!info.getTechList().hasTech(techName)) {
            String event = generateAncientTech(info, techName, 10,
                TechType.Propulsion, ArtifactType.ENERGY);
            if (event != null) {
              techGained = true;
              sb.append(event);
            }
          }
        }
        if (!techGained) {
          techName = "Antimatter source Mk1";
          if (!info.getTechList().hasTech(techName)) {
            String event = generateAncientTech(info, techName, 11,
                TechType.Propulsion, ArtifactType.ENERGY);
            if (event != null) {
              techGained = true;
              sb.append(event);
            }
          }
        }
        if (!techGained) {
          techName = "Antimatter source Mk2";
          if (!info.getTechList().hasTech(techName)) {
            String event = generateAncientTech(info, techName, 13,
                TechType.Propulsion, ArtifactType.ENERGY);
            if (event != null) {
              techGained = true;
              sb.append(event);
            }
          }
        }
        if (!techGained) {
          techName = "Antimatter source Mk3";
          if (!info.getTechList().hasTech(techName)) {
            String event = generateAncientTech(info, techName, 14,
                TechType.Propulsion, ArtifactType.ENERGY);
            if (event != null) {
              techGained = true;
              sb.append(event);
            }
          }
        }
        if (!techGained) {
          techName = "Zero-point source Mk1";
          if (!info.getTechList().hasTech(techName)) {
            String event = generateAncientTech(info, techName, 15,
                TechType.Propulsion, ArtifactType.ENERGY);
            if (event != null) {
              techGained = true;
              sb.append(event);
            }
          }
        }
        if (!techGained) {
          techName = "Zero-point source Mk2";
          if (!info.getTechList().hasTech(techName)) {
            String event = generateAncientTech(info, techName, 16,
                TechType.Propulsion, ArtifactType.ENERGY);
            if (event != null) {
              techGained = true;
              sb.append(event);
            }
          }
        }
        if (!techGained) {
          techName = "Zero-point source Mk3";
          if (!info.getTechList().hasTech(techName)) {
            String event = generateAncientTech(info, techName, 16,
                TechType.Propulsion, ArtifactType.ENERGY);
            if (event != null) {
              techGained = true;
              sb.append(event);
            }
          }
        }
      }
      if (artifact.getArtifactType() == ArtifactType.FACILITY) {
        String techName = "Planetary ascension portal";
        String event = generateAncientTech(info, techName, 9,
            TechType.Improvements, ArtifactType.FACILITY);
        if (event != null) {
          sb.append(event);
          starMap.getAscensionEvents().eventHappens(
                AscensionEventType.GAIN_ASCENSION_PORTAL_TECH);
        }
      }
      if (artifact.getArtifactType() == ArtifactType.ELECTRONIC) {
        String techName = "Orbital ascension portal";
        String event = generateAncientTech(info, techName, 7,
            TechType.Electrics, ArtifactType.ELECTRONIC);
        if (event != null) {
          sb.append(event);
          starMap.getAscensionEvents().eventHappens(
              AscensionEventType.GAIN_ASCENSION_PORTAL_TECH);
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
      if (artifact.getName().equals("Ancient civilization remnants")) {
        for (int i = 0; i < 5; i++) {
          Double value = info.getTechList().getTechResearchPoints(
              TechType.getTypeByIndex(i));
          info.getTechList().setTechResearchPoints(TechType.getTypeByIndex(i),
              value + artifact.getOneTimeTechBonus());
        }
      } else {
        Double value = info.getTechList().getTechResearchPoints(
            TechType.getTypeByIndex(artifact.getArtifactType().getIndex()));
        info.getTechList().setTechResearchPoints(
            TechType.getTypeByIndex(artifact.getArtifactType().getIndex()),
            value + artifact.getOneTimeTechBonus());
      }
      Message msg = new Message(MessageType.RESEARCH, sb.toString(),
          Icons.getIconByName(Icons.ICON_RESEARCH));
      msg.setMatchByString(artifact.getName());
      info.getMsgList().addNewMessage(msg);
      NewsData news = NewsFactory.makeAncientResearchNews(info, sb.toString(),
          starYear);
      return news;
    }
    return null;
  }
}
