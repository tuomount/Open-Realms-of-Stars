package org.openRealmOfStars.player;

import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechList;
import org.openRealmOfStars.player.tech.TechType;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * Player info contains which race player has, planet etc
 * Player here means both Human and AI players
 * 
 */

public class PlayerInfo {

  /**
   * Player's space race
   */
  private SpaceRace race;
  
  /**
   * Player's empire name
   */
  private String empireName;
  
  /**
   * Total credits for player, these should not go negative
   */
  private int totalCredits;

  /**
   * Technology list that player has studied or gained
   */
  private TechList techList;
  
  public PlayerInfo(SpaceRace race) {
    setTechList(new TechList());
    setRace(race);
    switch (getRace()) {
    case HUMAN:
    case MECHIONS:
    case CENTAURS:{
      /*
       * Humans, Mechions and Centaurs get 1 Combat, 1 Defense, Scout and Colony
       */
      Tech tech = TechFactory.createRandomTech(TechType.Combat, 1, 
          techList.getListForTypeAndLevel(TechType.Combat, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Defense, 1, 
          techList.getListForTypeAndLevel(TechType.Defense, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Colony", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Scout Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      break;
    }
    case SPORKS:{
      /*
       * Sporks get 2 Combat, 1 Defense, Scout and Colony
       */
      Tech tech = TechFactory.createRandomTech(TechType.Combat, 1, 
          techList.getListForTypeAndLevel(TechType.Combat, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Combat, 1, 
          techList.getListForTypeAndLevel(TechType.Combat, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Defense, 1, 
          techList.getListForTypeAndLevel(TechType.Defense, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Colony", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Scout Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      break;
    }
    case GREYANS:{
      /*
       * Greyans get 1 Combat, 1 Defense, Scout and Colony, 1 propulsion, 1 electronics
       */
      Tech tech = TechFactory.createRandomTech(TechType.Combat, 1, 
          techList.getListForTypeAndLevel(TechType.Combat, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Defense, 1, 
          techList.getListForTypeAndLevel(TechType.Defense, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Colony", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createHullTech("Scout Mk1", 1);
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Propulsion, 1, 
          techList.getListForTypeAndLevel(TechType.Propulsion, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      tech = TechFactory.createRandomTech(TechType.Electrics, 1, 
          techList.getListForTypeAndLevel(TechType.Electrics, 1));
      if (tech != null) {
        techList.addTech(tech);
      }
      break;
    }
    }
  }
  
  public SpaceRace getRace() {
    return race;
  }

  public void setRace(SpaceRace race) {
    this.race = race;
  }

  public String getEmpireName() {
    return empireName;
  }

  public void setEmpireName(String empireName) {
    this.empireName = empireName;
  }

  public int getTotalCredits() {
    return totalCredits;
  }

  public void setTotalCredits(int totalCredits) {
    this.totalCredits = totalCredits;
  }

  public TechList getTechList() {
    return techList;
  }

  public void setTechList(TechList techList) {
    this.techList = techList;
  }
  
  
}
