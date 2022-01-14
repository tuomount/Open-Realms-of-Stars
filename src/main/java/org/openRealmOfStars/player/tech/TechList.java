package org.openRealmOfStars.player.tech;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.game.Game;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.starMap.planet.BuildingFactory;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016-2021 Tuomo Untinen
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
 * Tech list for player
 *
 */
public class TechList {

  /**
   * Maximum number of tech levels
   */
  public static final int MAX_TECH_LEVEL = 10;

  /**
   * Maximum number of tech types
   */
  public static final int MAX_TECH_TYPES = TechType.values().length;

  /**
   * List of researched techs, First are TechTypes, second is tech level
   */
  private TechListForLevel[][] techList;

  /**
   * Tech Levels
   */
  private int[] techLevels = new int[TechType.values().length];

  /**
   * Tech Focus levels. These must be between 0-100%. When all techs
   * are summed total must be 100%
   */
  private int[] techFocus = new int[TechType.values().length];

  /**
   * Tech research points
   */
  private double[] techResearchPoint = new double[TechType.values().length];

  /**
   * SpaceRace for techlist.
   */
  private SpaceRace race;

  /**
   * Constructor for TechList
   * @param race SpaceRace for correct tech tree
   */
  public TechList(final SpaceRace race) {
    this.race = race;
    techList = new TechListForLevel[TechType.values().length][MAX_TECH_LEVEL];
    for (int i = 0; i < MAX_TECH_TYPES; i++) {
      for (int j = 0; j < MAX_TECH_LEVEL; j++) {
        techList[i][j] = new TechListForLevel(j + 1);
      }
    }
    techLevels[TechType.Combat.getIndex()] = 1;
    techLevels[TechType.Defense.getIndex()] = 1;
    techLevels[TechType.Hulls.getIndex()] = 1;
    techLevels[TechType.Improvements.getIndex()] = 1;
    techLevels[TechType.Propulsion.getIndex()] = 1;
    techLevels[TechType.Electrics.getIndex()] = 1;
    techFocus[TechType.Combat.getIndex()] = 20;
    techFocus[TechType.Defense.getIndex()] = 16;
    techFocus[TechType.Hulls.getIndex()] = 16;
    techFocus[TechType.Improvements.getIndex()] = 16;
    techFocus[TechType.Propulsion.getIndex()] = 16;
    techFocus[TechType.Electrics.getIndex()] = 16;
  }

  /**
   * Get number of scientific achievements realm has.
   * @return Number of scientific achievements.
   */
  public int getNumberOfScientificAchievements() {
    int result = 0;
    for (int i = 0; i < MAX_TECH_TYPES; i++) {
      for (int j = 0; j < MAX_TECH_LEVEL; j++) {
        for (Tech tech :  techList[i][j].getList()) {
          if (tech.getImprovement() != null) {
            Building building = BuildingFactory.createByName(
                tech.getImprovement());
            if (building.getScientificAchievement()) {
              result++;
            }
            if (tech.getName().equals("Artificial planet")) {
              result++;
            }
          }
        }
      }
    }
    return result;
  }
  /**
   * Save Tech list for DataOutputStream
   * @param dos Data output stream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveTechList(final DataOutputStream dos) throws IOException {
    for (int i = 0; i < MAX_TECH_TYPES; i++) {
      for (int j = 0; j < MAX_TECH_LEVEL; j++) {
        techList[i][j].saveTechListForLevel(dos);
      }
    }
    for (int i = 0; i < MAX_TECH_TYPES; i++) {
      dos.writeInt(techLevels[i]);
      dos.writeInt(techFocus[i]);
      dos.writeDouble(techResearchPoint[i]);
    }

  }

  /**
   * Read TechList from DataInputStream
   * @param dis DataInputStream
   * @param race SpaceRace for correct techtree.
   * @throws IOException if there is any problem with DataInputStream
   */
  public TechList(final DataInputStream dis, final SpaceRace race)
      throws IOException {
    this.race = race;
    techList = new TechListForLevel[TechType.values().length][MAX_TECH_LEVEL];
    for (int i = 0; i < MAX_TECH_TYPES; i++) {
      for (int j = 0; j < MAX_TECH_LEVEL; j++) {
        techList[i][j] = new TechListForLevel(j + 1, TechType.values()[i], dis);
      }
    }
    for (int i = 0; i < MAX_TECH_TYPES; i++) {
      techLevels[i] = dis.readInt();
      techFocus[i] = dis.readInt();
      techResearchPoint[i] = dis.readDouble();
    }

  }

  /**
   * Check if certain tech is in tech list
   * @param techName Tech name to search
   * @return True if found, otherwise false
   */
  public boolean isTech(final String techName) {
    for (int i = 0; i < MAX_TECH_TYPES; i++) {
      for (int j = 0; j < MAX_TECH_LEVEL; j++) {
        TechListForLevel tech = techList[i][j];
        if (tech.isTech(techName)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Add new tech to tech list if it is not in list yet.
   * @param tech Tech to add
   */
  public void addTech(final Tech tech) {
    if (tech != null) {
      int index = tech.getType().getIndex();
      int lvl = tech.getLevel() - 1;
      if (!techList[index][lvl].isTech(tech.getName())) {
        techList[index][lvl].addTech(tech);
        if (isTechListForLevelFull(tech.getType(), lvl + 1)
            && lvl + 1 >= techLevels[index]) {
          techLevels[index] = lvl + 2;
          if (techLevels[index] > 10) {
            techLevels[index] = 10;
          }
        }
      }
    }
  }

  /**
   * Get Tech Level
   * @param type Tech type which level is going to be checked
   * @return Level 1-10
   */
  public int getTechLevel(final TechType type) {
    int index = type.getIndex();
    if (index >= 0 && index < techLevels.length) {
      return techLevels[index];
    }
    return -1;
  }

  /**
   * Set Tech Level
   * @param type Tech type which is going to be set
   * @param level Level must be between 1-10
   */
  public void setTechLevel(final TechType type, final int level) {
    int index = type.getIndex();
    if (level >= 1 && level < 11 && index >= 0 && index < techLevels.length) {
      techLevels[index] = level;
    }
  }

  /**
   * Get tech list for certain tech type
   * @param type Tech Type to get the list
   * @return tech list as a tech array
   */
  public Tech[] getListForType(final TechType type) {
    ArrayList<Tech> list = new ArrayList<>();
    int index = type.getIndex();
    for (int i = 0; i < MAX_TECH_LEVEL; i++) {
      for (Tech tech : techList[index][i].getList()) {
        list.add(tech);
      }
    }
    return list.toArray(new Tech[list.size()]);
  }

  /**
   * Get Mark level from tech name and return it
   * @param techName where Mark(Mk) is searched
   * @return 0 if not found otherwise mark level.
   */
  public static int getMarkLevel(final String techName) {
    String[] temp = techName.split(" ");
    for (String buf : temp) {
      if (buf.startsWith("Mk") && buf.length() > 2) {
        buf = buf.substring(2, buf.length());
        try {
          return Integer.parseInt(buf);
        } catch (NumberFormatException e) {
          ErrorLogger.log(e);
        }
      }
    }
    return 0;
  }

  /**
   * Search best tech with best mark level
   * @param list Tech list where to search
   * @param techName name use for search
   * @return Best tech if found or null if not found
   */
  public static Tech getBestTech(final Tech[] list, final String techName) {
    Tech best = null;
    int bestLvl = -1;
    for (Tech tech : list) {
      if (tech.getName().startsWith(techName)) {
        int lvl = getMarkLevel(tech.getName());
        if (lvl > bestLvl) {
          best = tech;
          bestLvl = lvl;
        }
      }
    }
    return best;
  }

  /**
   * Has certain tech in tech list. This will search tech from all types.
   * @param techName Tech name to search.
   * @return True if tech is in list otherwise false.
   */
  public boolean hasTech(final String techName) {
    if (hasTech(TechType.Combat, techName)) {
      return true;
    }
    if (hasTech(TechType.Defense, techName)) {
      return true;
    }
    if (hasTech(TechType.Hulls, techName)) {
      return true;
    }
    if (hasTech(TechType.Electrics, techName)) {
      return true;
    }
    if (hasTech(TechType.Propulsion, techName)) {
      return true;
    }
    if (hasTech(TechType.Improvements, techName)) {
      return true;
    }
    return false;
  }
  /**
   * Has certain tech in tech list
   * @param type Tech type
   * @param techName Tech name to search
   * @return True if tech is in the list otherwise false
   */
  public boolean hasTech(final TechType type, final String techName) {
    Tech[] list = getListForType(type);
    for (int i = 0; i < list.length; i++) {
      if (list[i].getName().equals(techName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get component difference in techs
   * @param tech Tech that other one owns
   * @param ownTech Tech we own
   * @return True if we got better component
   */
  protected static boolean getComponentDifference(final Tech tech,
      final Tech ownTech) {
    if (ownTech.getComponent() != null && tech.getComponent() != null) {
      // Check if we already have better component
      ShipComponent ownComponent = ShipComponentFactory.createByName(
          ownTech.getComponent());
      if (ownComponent != null) {
        ShipComponent component = ShipComponentFactory.createByName(
            tech.getComponent());
        if (component != null
            && component.getType() == ownComponent.getType()
            && ownTech.getLevel() > tech.getLevel()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get hull difference. Checks if we have better hull tech than other player.
   * @param tech Tech that other one owns
   * @param ownTech Tech we own
   * @return True if we got better hull
   */
  protected static boolean getHullDifference(final Tech tech,
      final Tech ownTech) {
    // Check if we already have better hull
    if (ownTech.getHull() != null && tech.getHull() != null) {
      String[] ownHull = ownTech.getHull().split("Mk");
      String[] hull = tech.getHull().split("Mk");
      if (ownHull.length == 2 && hull.length == 2) {
        int ownHullMk = 0;
        int hullMk = 0;
        try {
          ownHullMk = Integer.valueOf(ownHull[1]);
        } catch (NumberFormatException e) {
          ownHullMk = 0;
        }
        try {
          hullMk = Integer.valueOf(hull[1]);
        } catch (NumberFormatException e) {
          hullMk = 0;
        }
        if (ownHull[0].equals(hull[0]) && ownHullMk > hullMk) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check which trade techs are missing from own tech list
   * @param tradeTechs Tradeable techs from another player
   * @param ownTechs Techs that player already has
   * @return Tech list which are new techs for player
   */
  public static Tech[] getTechDifference(final Tech[] tradeTechs,
      final Tech[] ownTechs) {
    ArrayList<Tech> techList = new ArrayList<>();
    for (Tech tech : tradeTechs) {
      boolean found = false;
      if (!tech.isTradeable()) {
        continue;
      }
      for (Tech ownTech : ownTechs) {
        if (tech.getName().equals(ownTech.getName())) {
          found = true;
        }
        // Check if we already have better component
        if (getComponentDifference(tech, ownTech)) {
          found = true;
        }
        // Check if we already have better hull
        if (getHullDifference(tech, ownTech)) {
          found = true;
        }
      }
      if (!found) {
        techList.add(tech);
      }
    }
    return techList.toArray(new Tech[techList.size()]);
  }
  /**
   * Get best Energy source for current technology
   * @return Best energy source tech or null if not found
   */
  public Tech getBestEnergySource() {
    Tech best = null;
    int bestValue = -1;
    Tech[] list = getListForType(TechType.Propulsion);
    for (Tech tech : list) {
      ShipComponent comp = ShipComponentFactory
          .createByName(tech.getComponent());
      if (comp != null && comp.getEnergyResource() > bestValue) {
        best = tech;
        bestValue = comp.getEnergyResource();
      }
    }
    return best;
  }

  /**
   * Get best Engine for current technology. If there two equally good
   * techs then it is randomized between those two.
   * @return Best engine tech or null if not found
   */
  public Tech getBestEngine() {
    Tech best = null;
    int bestValue = -1;
    Tech[] list = getListForType(TechType.Propulsion);
    for (Tech tech : list) {
      ShipComponent comp = ShipComponentFactory
          .createByName(tech.getComponent());
      if (comp != null
          && (comp.getType() == ShipComponentType.ENGINE
          || comp.getType() == ShipComponentType.SPACE_FIN)) {
        int compValue = -1;
        if (comp.getFtlSpeed() > 1 && comp.getSpeed() == 1
            && comp.getTacticSpeed() == 1) {
          compValue = comp.getFtlSpeed() - 1;
        } else {
          compValue = comp.getFtlSpeed() + (comp.getSpeed() - 1)
              + (comp.getTacticSpeed() - 1) + comp.getEnergyResource();
        }
        if (compValue > bestValue) {
          best = tech;
          bestValue = compValue;
        } else if (compValue == bestValue && DiceGenerator.getRandom(1) == 0) {
          best = tech;
          bestValue = compValue;
        }
      }
    }
    return best;
  }

  /**
   * Get best Weapon for current technology. If there two equally good
   * techs then it is randomized between those two. ECM is not chosen
   * since it cannot actually destroy a ship.
   * @return Best weapon tech or null if not found
   */
  public Tech getBestWeapon() {
    Tech best = null;
    int bestValue = -1;
    Tech[] list = getListForType(TechType.Combat);
    for (Tech tech : list) {
      ShipComponent comp = ShipComponentFactory
          .createByName(tech.getComponent());
      if (comp != null) {
        int compValue = -1;
        if (comp.getType() == ShipComponentType.WEAPON_BEAM
            || comp.getType() == ShipComponentType.WEAPON_HE_MISSILE
            || comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO
            || comp.getType() == ShipComponentType.WEAPON_RAILGUN
            || comp.getType() == ShipComponentType.PLASMA_CANNON
            || comp.getType() == ShipComponentType.ION_CANNON
            || comp.getType() == ShipComponentType.CALLISTO_MULTICANNON
            || comp.getType() == ShipComponentType.BITE
            || comp.getType() == ShipComponentType.TENTACLE) {
          compValue = comp.getDamage();
        }
        if (compValue > bestValue) {
          best = tech;
          bestValue = compValue;
        } else if (compValue == bestValue && DiceGenerator.getRandom(1) == 0) {
          best = tech;
          bestValue = compValue;
        }
      }
    }
    return best;
  }

  /**
   * Get best Weapon for current technology. If there two equally good
   * techs then it is randomized between those two.
   * @param type Weapon type for search
   * @return Best weapon tech or null if not found
   */
  public Tech getBestWeapon(final ShipComponentType type) {
    Tech best = null;
    int bestValue = -1;
    Tech[] list = getListForType(TechType.Combat);
    for (Tech tech : list) {
      ShipComponent comp = ShipComponentFactory
          .createByName(tech.getComponent());
      if (comp != null) {
        int compValue = -1;
        if (comp.getType() == type) {
          compValue = comp.getDamage();
          if (compValue > bestValue) {
            best = tech;
            bestValue = compValue;
          } else if (compValue == bestValue
              && DiceGenerator.getRandom(1) == 0) {
            best = tech;
            bestValue = compValue;
          }
        }
      }
    }
    return best;
  }

  /**
   * Get best starbase lab tech
   * @return Tech or null if not available
   */
  public Tech getBestStarbaseLab() {
    Tech best = null;
    int bestValue = -1;
    Tech[] list = getListForType(TechType.Improvements);
    for (Tech tech : list) {
      ShipComponent comp = ShipComponentFactory
          .createByName(tech.getComponent());
      int compValue = -1;
      if (comp != null
          && comp.getType() == ShipComponentType.STARBASE_COMPONENT) {
        compValue = comp.getResearchBonus();
        if (compValue > bestValue) {
          best = tech;
          bestValue = compValue;
        } else if (compValue == bestValue && DiceGenerator.getRandom(1) == 0) {
          best = tech;
          bestValue = compValue;
        }
      }
    }
    return best;
  }

  /**
   * Get best starbase culture tech
   * @return Tech or null if not available
   */
  public Tech getBestStarbaseCulture() {
    Tech best = null;
    int bestValue = -1;
    Tech[] list = getListForType(TechType.Improvements);
    for (Tech tech : list) {
      ShipComponent comp = ShipComponentFactory
          .createByName(tech.getComponent());
      int compValue = -1;
      if (comp != null
          && comp.getType() == ShipComponentType.STARBASE_COMPONENT) {
        compValue = comp.getCultureBonus();
        if (compValue > bestValue) {
          best = tech;
          bestValue = compValue;
        } else if (compValue == bestValue && DiceGenerator.getRandom(1) == 0) {
          best = tech;
          bestValue = compValue;
        }
      }
    }
    return best;
  }

  /**
   * Get best starbase credit tech
   * @return Tech or null if not available
   */
  public Tech getBestStarbaseCredit() {
    Tech best = null;
    int bestValue = -1;
    Tech[] list = getListForType(TechType.Improvements);
    for (Tech tech : list) {
      ShipComponent comp = ShipComponentFactory
          .createByName(tech.getComponent());
      int compValue = -1;
      if (comp != null
          && comp.getType() == ShipComponentType.STARBASE_COMPONENT) {
        compValue = comp.getCreditBonus();
        if (compValue > bestValue) {
          best = tech;
          bestValue = compValue;
        } else if (compValue == bestValue && DiceGenerator.getRandom(1) == 0) {
          best = tech;
          bestValue = compValue;
        }
      }
    }
    return best;
  }

  /**
   * Get best starbase fleet capacity tech
   * @return Tech or null if not available
   */
  public Tech getBestStarbaseFleetCap() {
    Tech best = null;
    int bestValue = -1;
    Tech[] list = getListForType(TechType.Improvements);
    for (Tech tech : list) {
      ShipComponent comp = ShipComponentFactory
          .createByName(tech.getComponent());
      int compValue = -1;
      if (comp != null
          && comp.getType() == ShipComponentType.STARBASE_COMPONENT) {
        compValue = comp.getFleetCapacityBonus();
        if (compValue > bestValue) {
          best = tech;
          bestValue = compValue;
        } else if (compValue == bestValue && DiceGenerator.getRandom(1) == 0) {
          best = tech;
          bestValue = compValue;
        }
      }
    }
    return best;
  }

  /**
   * Get Tech list for certain tech type and level
   * @param type Tech Type to get the list
   * @param level Level of tech list 1-10
   * @return tech list as a tech array
   */
  public Tech[] getListForTypeAndLevel(final TechType type, final int level) {
    int levelIndex = level - 1;
    if (levelIndex >= 10 || levelIndex < 0) {
      return new Tech[0];
    }
    ArrayList<Tech> list = new ArrayList<>();
    int index = type.getIndex();
    for (Tech tech : techList[index][levelIndex].getList()) {
      list.add(tech);
    }
    return list.toArray(new Tech[list.size()]);
  }

  /**
   * Get Tech list for certain tech type and level which are missing
   * @param type Tech Type to get the list
   * @param level Level of tech list 1-10
   * @return List of techs which are missing
   */
  public Tech[] getListMissingTech(final TechType type, final int level) {
    int levelIndex = level - 1;
    if (levelIndex >= 10 || levelIndex < 0) {
      return new Tech[0];
    }
    Tech[] techGot = getListForTypeAndLevel(type, level);
    String[] choices = TechFactory.getListByTechLevel(type, level, race);
    ArrayList<String> list = new ArrayList<>();
    for (String choice : choices) {
      boolean found = false;
      for (Tech tech : techGot) {
        if (tech.getName().equals(choice)) {
          found = true;
        }
      }
      if (!found) {
        list.add(choice);
      }
    }
    Tech[] techMissing = new Tech[list.size()];
    for (int i = 0; i < list.size(); i++) {
      Tech tech = TechFactory.createTech(type, level, list.get(i));
      techMissing[i] = tech;
    }
    return techMissing;
  }

  /**
   * Get all Rare techs from tech list.
   * @return Get all Rare techs.
   */
  public Tech[] getRareTechs() {
    ArrayList<Tech> list = new ArrayList<>();
    for (int types = 0; types < 5; types++) {
      for (int level = 0; level < 10; level++) {
        for (Tech tech : techList[types][level].getList()) {
          if (tech.isRareTech()) {
            list.add(tech);
          }
        }
      }
    }
    return list.toArray(new Tech[list.size()]);
  }
  /**
   * Is Tech list for certain level full
   * @param type Tech Type
   * @param level Level of tech list 1-10
   * @return true if full or false if not
   */
  public boolean isTechListForLevelFull(final TechType type, final int level) {
    Tech[] list = getListForTypeAndLevel(type, level);
    String[] choices = TechFactory.getListByTechLevel(type, level, race);
    if (list.length == choices.length) {
      return true;
    }
    return false;
  }

  /**
   * Get Full Tech List
   * @return tech list as a tech array
   */
  public Tech[] getList() {
    ArrayList<Tech> list = new ArrayList<>();
    for (int j = 0; j < MAX_TECH_TYPES; j++) {
      for (int i = 0; i < MAX_TECH_LEVEL; i++) {
        for (Tech tech : techList[j][i].getList()) {
          list.add(tech);
        }
      }
    }
    return list.toArray(new Tech[list.size()]);
  }

  /**
   * Fine tune value for tech focus
   */
  public static final int FINE_TUNE_VALUE = 4;

  /**
   * Tries to settle so that tech focus about evenly distributed
   * @param type Tech type not used for in settling
   * @param value Value which tech focus was set
   */
  private void settleTechFocus(final TechType type, final int value) {
    int redis = 100;
    for (int i = 0; i < TechType.values().length; i++) {
      redis = redis - techFocus[i];
    }
    int average = (100 - value) / (TechType.values().length - 1);
    while (redis != 0) {
      for (int i = 0; i < TechType.values().length; i++) {
        if (i != type.getIndex() && redis > 0 && techFocus[i] < average
            && redis >= FINE_TUNE_VALUE) {
          techFocus[i] = techFocus[i] + FINE_TUNE_VALUE;
          redis = redis - FINE_TUNE_VALUE;
        }
        if (i != type.getIndex() && redis < 0 && techFocus[i] > average
            && techFocus[i] >= FINE_TUNE_VALUE && redis <= -FINE_TUNE_VALUE) {
          techFocus[i] = techFocus[i] - FINE_TUNE_VALUE;
          redis = redis + FINE_TUNE_VALUE;
        }

      }
      for (int i = 0; i < TechType.values().length; i++) {
        if (i != type.getIndex() && redis > 0 && techFocus[i] == average
            && redis >= FINE_TUNE_VALUE) {
          techFocus[i] = techFocus[i] + FINE_TUNE_VALUE;
          redis = redis - FINE_TUNE_VALUE;
        }
        if (i != type.getIndex() && redis < 0 && techFocus[i] == average
            && redis <= -FINE_TUNE_VALUE && techFocus[i] >= FINE_TUNE_VALUE) {
          techFocus[i] = techFocus[i] - FINE_TUNE_VALUE;
          redis = redis + FINE_TUNE_VALUE;
        }

      }
    }
  }

  /**
   * Set Tech Focus level for certain type with value.
   * Makes sure that total focus level is set to 100%
   * @param type Tech type
   * @param value Value to set for certain type. Must be between 0-100%
   */
  public void setTechFocus(final TechType type, final int value) {
    if (value >= 0 && value <= 100) {
      techFocus[type.getIndex()] = value;
      settleTechFocus(type, value);
    }
  }

  /**
   * Get Tech focus for certain type of tech.
   * @param type The tech type
   * @return The tech focus
   */
  public int getTechFocus(final TechType type) {
    return techFocus[type.getIndex()];
  }

  /**
   * Get Tech research points for certain type of tech
   * @param type TechType
   * @return research points spent so far as double
   */
  public double getTechResearchPoints(final TechType type) {
    return techResearchPoint[type.getIndex()];
  }

  /**
   * Set Tech research points for certain type of tech.
   * @param type TechType
   * @param value Value where to set the points.
   */
  public void setTechResearchPoints(final TechType type, final double value) {
    techResearchPoint[type.getIndex()] = value;
  }
  /**
   * Add new random tech to player tech list
   * @param info PlayerInfo
   * @return Added Tech or null if all tech has been invented
   */
  public Tech addNewRandomTech(final PlayerInfo info) {
    Tech result = null;
    int loop = 10;
    while (result == null && loop > 0) {
      loop--;
      int index = DiceGenerator.getRandom(0, 5);
      TechType type = TechType.getTypeByIndex(index);
      int lvl = techLevels[index];
      Tech tech = TechFactory.createRandomTech(type, lvl,
          getListForTypeAndLevel(type, lvl), race);
      if (tech == null) {
        if (lvl < 10) {
          techLevels[index] = techLevels[index] + 1;
        }
      } else {
        addTech(tech);
        StringBuilder sb = new StringBuilder();
        sb.append(info.getEmpireName());
        sb.append(" researched ");
        sb.append(tech.getName());
        sb.append(" in ");
        sb.append(tech.getType().toString());
        sb.append(" with level ");
        sb.append(lvl);
        sb.append(". ");
        if (isTechListForLevelFull(type, lvl)) {
          sb.append(tech.getType().toString());
          sb.append(" has advanced to next level.");
        }
        Message msg = new Message(MessageType.RESEARCH, sb.toString(),
            Icons.getIconByName(Icons.ICON_RESEARCH));
        msg.setMatchByString(tech.getName());
        info.getMsgList().addNewMessage(msg);
        result = tech;
      }
    }
    return result;
  }

  /**
   * Checks if certain tech is on array.
   * @param techs Techs in array.
   * @param tech Technology for check
   * @return True if tech is in the array.
   */
  private static boolean isOnList(final Tech[] techs, final Tech tech) {
    for (Tech comp : techs) {
      if (tech.getName().equals(comp.getName())) {
        return true;
      }
    }
    return false;
  }
  /**
   * Check possible rare techs on tree
   * @param techType Tech Type
   * @param level Which level tech should be
   * @return List of possible rare techs.
   */
  public Tech[] checkRareTechTree(final TechType techType, final int level) {
    ArrayList<Tech> list = new ArrayList<>();
    Tech[] techs = getRareTechs();
    for (Tech tech : techs) {
      if (tech.getType() == techType && tech.getNextTechOnTree() != null
          && tech.getNextTechLevel() == level) {
        Tech rareTech = TechFactory.createTech(techType, level,
            tech.getNextTechOnTree());
        if (rareTech != null && !isOnList(techs, rareTech)) {
          list.add(rareTech);
        }
      }
    }
    if (list.size() == 0) {
      return null;
    }
    return list.toArray(new Tech[list.size()]);
  }
  /**
   * Update Research points by turn. This will also grant a new technology
   * @param totalResearchPoints player makes per turn
   * @param info PlayerInfo for message information
   * @param gameLength Maximum game length in turns
   * @param tutorialEnabled Is tutorial enabled or not
   */
  public void updateResearchPointByTurn(final int totalResearchPoints,
      final PlayerInfo info, final int gameLength,
      final boolean tutorialEnabled) {
    for (int i = 0; i < techFocus.length; i++) {
      techResearchPoint[i] = techResearchPoint[i]
          + techFocus[i] * totalResearchPoints / 100.0;
      if (techResearchPoint[i] >= TechFactory.getTechCost(techLevels[i],
          gameLength)) {
        techResearchPoint[i] = techResearchPoint[i]
            - TechFactory.getTechCost(techLevels[i], gameLength);
        TechType type = TechType.getTypeByIndex(i);
        int lvl = techLevels[i];
        Tech[] rareTechs = checkRareTechTree(type, lvl);
        Tech tech = TechFactory.createRandomTech(type, lvl,
            getListForTypeAndLevel(type, lvl), rareTechs, race);
        if (tech == null) {
          // Apparently tech level was already full,
          // so let's increase level and try again later.
          techResearchPoint[i] = techResearchPoint[i]
              + TechFactory.getTechCost(techLevels[i], gameLength);
          if (lvl < 10) {
            techLevels[i] = techLevels[i] + 1;
          }
          break;
        }
        addTech(tech);
        StringBuilder sb = new StringBuilder();
        sb.append(info.getEmpireName());
        sb.append(" researched ");
        sb.append(tech.getName());
        sb.append(" in ");
        sb.append(tech.getType().toString());
        sb.append(" with level ");
        sb.append(lvl);
        sb.append(". ");
        if (isTechListForLevelFull(type, lvl)) {
          sb.append(tech.getType().toString());
          sb.append(" has advanced to next level.");
        }
        Message msg = new Message(MessageType.RESEARCH, sb.toString(),
            Icons.getIconByName(Icons.ICON_RESEARCH));
        msg.setMatchByString(tech.getName());
        info.getMsgList().addNewMessage(msg);
        if (Game.getTutorial() != null  && info.isHuman() && tutorialEnabled) {
          String tutorialText = Game.getTutorial().showTutorialText(13);
          if (tutorialText != null) {
            msg = new Message(MessageType.INFORMATION, tutorialText,
                Icons.getIconByName(Icons.ICON_TUTORIAL));
            info.getMsgList().addNewMessage(msg);
          }
          if (tech.getComponent() != null) {
            if (tech.getType() == TechType.Defense
                && tech.getComponent().startsWith("Shield Mk")) {
              tutorialText = Game.getTutorial().showTutorialText(56);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Defense
                && tech.getComponent().startsWith("Armor")) {
              tutorialText = Game.getTutorial().showTutorialText(57);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Defense
                && tech.getComponent().startsWith("Jammer")) {
              tutorialText = Game.getTutorial().showTutorialText(58);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Combat
                && tech.getComponent().startsWith("Laser")) {
              tutorialText = Game.getTutorial().showTutorialText(51);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Combat
                && tech.getComponent().startsWith("Photon")) {
              tutorialText = Game.getTutorial().showTutorialText(53);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Combat
                && tech.getComponent().startsWith("Railgun")) {
              tutorialText = Game.getTutorial().showTutorialText(52);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Combat
                && tech.getComponent().startsWith("Orbital")) {
              tutorialText = Game.getTutorial().showTutorialText(81);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Combat
                && tech.getComponent().startsWith("HE missile")) {
              tutorialText = Game.getTutorial().showTutorialText(54);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Combat
                && tech.getComponent().startsWith("ECM torpedo")) {
              tutorialText = Game.getTutorial().showTutorialText(55);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Hulls
                && tech.getComponent().startsWith("Privateering")) {
              tutorialText = Game.getTutorial().showTutorialText(59);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
              tutorialText = Game.getTutorial().showTutorialText(73);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getHull() != null || tech.getComponent() != null) {
              tutorialText = Game.getTutorial().showTutorialText(70);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            boolean specialHull = false;
            if (tech.getType() == TechType.Hulls
                && tech.getHull() != null
                && tech.getHull().startsWith("Probe")) {
              specialHull = true;
              tutorialText = Game.getTutorial().showTutorialText(71);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Hulls
                && tech.getHull() != null
                && tech.getHull().contains("Starbase")) {
              specialHull = true;
              tutorialText = Game.getTutorial().showTutorialText(72);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Hulls
                && tech.getHull() != null
                && tech.getHull().contains("Privateer")) {
              specialHull = true;
              tutorialText = Game.getTutorial().showTutorialText(73);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Hulls
                && tech.getHull() != null
                && tech.getHull().contains("Freighter")) {
              specialHull = true;
              tutorialText = Game.getTutorial().showTutorialText(74);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Hulls
                && tech.getHull() != null
                && !specialHull) {
              tutorialText = Game.getTutorial().showTutorialText(75);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Propulsion
                && tech.getComponent() != null
                && tech.getComponent().contains("source")) {
              tutorialText = Game.getTutorial().showTutorialText(77);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Propulsion
                && tech.getComponent() != null
                && tech.getComponent().contains("drive")) {
              tutorialText = Game.getTutorial().showTutorialText(76);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Electrics
                && tech.getComponent().startsWith("Cloaking")) {
              tutorialText = Game.getTutorial().showTutorialText(78);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Electrics
                && tech.getComponent().startsWith("Espionage")) {
              tutorialText = Game.getTutorial().showTutorialText(79);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Electrics
                && tech.getComponent().startsWith("Scanner")
                || tech.getComponent().startsWith("LR scanner")) {
              tutorialText = Game.getTutorial().showTutorialText(80);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
            if (tech.getType() == TechType.Electrics
                && tech.getComponent().startsWith("Targeting computer")) {
              tutorialText = Game.getTutorial().showTutorialText(60);
              if (tutorialText != null) {
                msg = new Message(MessageType.INFORMATION, tutorialText,
                    Icons.getIconByName(Icons.ICON_TUTORIAL));
                info.getMsgList().addNewMessage(msg);
              }
            }
          }
        }
      }
    }

  }

  /**
   * Get building list from researched technology
   * @return String array of building names
   */
  public String[] getBuildingListFromTech() {
    Tech[] techs = getList();
    ArrayList<String> buildings = new ArrayList<>();
    for (Tech tech : techs) {
      if (tech.getImprovement() != null) {
        buildings.add(tech.getImprovement());
      }
    }
    return buildings.toArray(new String[buildings.size()]);
  }

  /**
   * Is certain tech type to upgradeable to next level. Which
   * means that over half of that level's tech has been researched.
   * @param type Tech Type
   * @return True if upgradeable otherwise false
   */
  public boolean isUpgradeable(final TechType type) {
    int level = getTechLevel(type);
    int subLevel = getListForTypeAndLevel(type, level).length;
    int maxSubLevel = TechFactory.getListByTechLevel(type, level, race).length;
    if (subLevel >= Math.ceil(maxSubLevel / 2.0) && level < MAX_TECH_LEVEL) {
      return true;
    }
    return false;
  }
}
