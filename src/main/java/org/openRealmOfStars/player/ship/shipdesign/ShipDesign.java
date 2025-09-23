package org.openRealmOfStars.player.ship.shipdesign;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016-2024 Tuomo Untinen
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
import java.util.Collections;

import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentFactory;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.ship.ShipHull;
import org.openRealmOfStars.player.ship.ShipHullFactory;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.FileIo.IOUtilities;

/**
 * Ship Design class, design is used to create a ship. Design is also
 * linked to Ship Stat class
 */
public class ShipDesign {
  /**
   * Ship Design name
   */
  private String name;

  /**
   * Ship's hull
   */
  private ShipHull hull;

  /**
   * Ship's component list
   */
  private ArrayList<ShipComponent> components;

  /**
   * Constructor for Ship Design
   * @param hull ShipHull for design
   */
  public ShipDesign(final ShipHull hull) {
    setHull(hull);
    components = new ArrayList<>();
    name = "Design";
  }

  /**
   * Read Ship Design from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public ShipDesign(final DataInputStream dis) throws IOException {
    name = IOUtilities.readString(dis);
    String hullName = IOUtilities.readString(dis);
    String raceId = IOUtilities.readString(dis);
    setHull(ShipHullFactory.createByName(hullName,
        SpaceRaceFactory.createOne(raceId)));
    int count = dis.readInt();
    components = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      String compName = IOUtilities.readString(dis);
      ShipComponent comp = ShipComponentFactory.createByName(compName);
      if (comp != null) {
        components.add(comp);
      } else {
        ErrorLogger.log("Ship component '" + compName + "' does not exists."
            + " Maybe save file is for newer version?");
      }
    }
  }

  /**
   * Save Ship Design to DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveShipDesign(final DataOutputStream dos) throws IOException {
    IOUtilities.writeString(dos, name);
    IOUtilities.writeString(dos, hull.getName());
    IOUtilities.writeString(dos, hull.getRace().getId());
    dos.writeInt(components.size());
    for (ShipComponent component : components) {
      IOUtilities.writeString(dos, component.getName());
    }
  }

  /**
   * Make a copy of current ship design
   * @param race SpaceRace whom ever is creating the copy. This makes
   * sense if other race ship is being copied.
   * @return Copy of design
   */
  public ShipDesign copy(final SpaceRace race) {
    ShipDesign result = new ShipDesign(
        ShipHullFactory.createByName(this.getHull().getName(), race));
    result.setName(this.name);
    for (ShipComponent comp : components) {
      result.addComponent(ShipComponentFactory.createByName(comp.getName()));
    }
    return result;
  }

  /**
   * Get number of components in ship's component list
   * @return number of components
   */
  public int getNumberOfComponents() {
    return components.size();
  }

  /**
   * Get ship component by index. Can return null if index is out list.
   * @param index Component list index
   * @return ShipComponent or null
   */
  public ShipComponent getComponent(final int index) {
    if (index >= 0 && index < components.size()) {
      return components.get(index);
    }
    return null;
  }

  /**
   * Add new component to list
   * @param comp ShipComponent to add
   */
  public void addComponent(final ShipComponent comp) {
    components.add(comp);
  }

  /**
   * Remove component from the list
   * @param index Component's index to remove
   */
  public void removeComponent(final int index) {
    components.remove(index);
  }

  /**
   * Get Ship design name
   * @return Ship Design name
   */
  public String getName() {
    return name;
  }

  /**
   * Set design name
   * @param name Design Name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Get ship hull
   * @return Ship hull
   */
  public ShipHull getHull() {
    return hull;
  }

  /**
   * Set ship hull
   * @param hull Ship design's hull
   */
  public void setHull(final ShipHull hull) {
    if (hull != null) {
      this.hull = hull;
    }
  }

  /**
   * Get current energy usage for ship. This is very useful information
   * when ship is being design.
   * @return Energy usage, positive means extra energy and negative
   * that too much energy is used.
   */
  public int getFreeEnergy() {
    int energy = 0;
    for (ShipComponent comp : components) {
      if (comp.getEnergyResource() > 0) {
        energy = energy + comp.getEnergyResource();
      }
      if (comp.getEnergyRequirement() > 0) {
        energy = energy - comp.getEnergyRequirement();
      }
    }
    return energy;
  }

  /**
   * Get maximum shield for ship. This is very useful information
   * when ship is being design and shown on ship view
   * @return Maximum shield
   */
  public int getTotalShield() {
    int shield = 0;
    for (ShipComponent comp : components) {
      if (comp.getDefenseValue() > 0
          && comp.getType() == ShipComponentType.SHIELD) {
        shield = shield + comp.getDefenseValue();
      }
      if (comp.getDefenseValue() > 0
          && comp.getType() == ShipComponentType.DISTORTION_SHIELD) {
        shield = shield + comp.getDefenseValue();
      }
      if (comp.getDefenseValue() > 0
          && comp.getType() == ShipComponentType.MULTIDIMENSION_SHIELD) {
        shield = shield + comp.getDefenseValue();
      }
    }
    return shield;
  }

  /**
   * Get Speed for design.
   * @return Speed
   */
  public int getSpeed() {
    int speed = 0;
    int thrusters = 0;
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.ENGINE
          && comp.getSpeed() > speed) {
        speed = comp.getSpeed();
      }
      if (comp.getType() == ShipComponentType.SPACE_FIN
          && comp.getSpeed() > speed) {
        speed = comp.getSpeed();
      }
      if (comp.getType() == ShipComponentType.THRUSTERS) {
        thrusters = comp.getSpeed();
      }
    }
    speed = speed + thrusters;
    return speed;
  }

  /**
   * Get tactic Speed for design.
   * @return Speed
   */
  public int getTacticSpeed() {
    int speed = 0;
    boolean thrusters = false;
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.ENGINE
          && comp.getTacticSpeed() > speed) {
        speed = comp.getTacticSpeed();
      }
      if (comp.getType() == ShipComponentType.SPACE_FIN
          && comp.getTacticSpeed() > speed) {
        speed = comp.getTacticSpeed();
      }
      if (comp.getType() == ShipComponentType.THRUSTERS) {
        thrusters = true;
      }
    }
    if (thrusters && speed > 0) {
      speed = speed + 1;
    }
    return speed;
  }

  /**
   * Get FTL Speed for design.
   * @return Speed
   */
  public int getFtlSpeed() {
    int result = 0;
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.ENGINE
          && comp.getFtlSpeed() > result) {
        result = comp.getFtlSpeed();
      }
      if (comp.getType() == ShipComponentType.SPACE_FIN
          && comp.getFtlSpeed() > result) {
        result = comp.getFtlSpeed();
      }
    }
    if (hull.getHullType() == ShipHullType.PROBE) {
      // Probes have faster FTL
      result = result + 1;
    }
    return result;
  }

  /**
   * Has design certain type of component installed
   * @param type Component type for search
   * @return true if component type is in place
   */
  public boolean gotCertainType(final ShipComponentType type) {
    boolean result = false;
    for (ShipComponent comp : components) {
      if (comp.getType() == type) {
        result = true;
      }
    }
    return result;
  }

  /**
   * Get maximum armor for ship. This is very useful information
   * when ship is being design and shown on ship view
   * @return Maximum armor
   */
  public int getTotalArmor() {
    int armor = 0;
    for (ShipComponent comp : components) {
      if (comp.getDefenseValue() > 0
          && comp.getType() == ShipComponentType.ARMOR) {
        armor = armor + comp.getDefenseValue();
      }
      if (comp.getDefenseValue() > 0
          && comp.getType() == ShipComponentType.SOLAR_ARMOR) {
        armor = armor + comp.getDefenseValue();
      }
      if (comp.getDefenseValue() > 0
          && comp.getType() == ShipComponentType.ORGANIC_ARMOR) {
        armor = armor + comp.getDefenseValue();
      }
      if (comp.getDefenseValue() > 0
          && comp.getType() == ShipComponentType.REPAIR_MODULE) {
        armor = armor + comp.getDefenseValue();
      }
    }
    return armor;
  }

  /**
   * Does ship design have weapons? True if one weapon is in place.
   * @return True if weapon is found, otherwise false
   */

  public boolean hasWeapons() {
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.WEAPON_BEAM
          || comp.getType() == ShipComponentType.WEAPON_ECM_TORPEDO
          || comp.getType() == ShipComponentType.WEAPON_HE_MISSILE
          || comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO
          || comp.getType() == ShipComponentType.WEAPON_RAILGUN
          || comp.getType() == ShipComponentType.PLASMA_CANNON
          || comp.getType() == ShipComponentType.MULTICANNON
          || comp.getType() == ShipComponentType.GRAVITY_RIPPER
          || comp.getType() == ShipComponentType.ION_CANNON
          || comp.getType() == ShipComponentType.BITE
          || comp.getType() == ShipComponentType.TENTACLE
          || comp.getType() == ShipComponentType.ARM_SPIKE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Count how many weapons ship has.
   * @return Number of weapons.
   */

  public int countWeapons() {
    int count = 0;
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.WEAPON_BEAM
          || comp.getType() == ShipComponentType.WEAPON_ECM_TORPEDO
          || comp.getType() == ShipComponentType.WEAPON_HE_MISSILE
          || comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO
          || comp.getType() == ShipComponentType.WEAPON_RAILGUN
          || comp.getType() == ShipComponentType.MULTICANNON
          || comp.getType() == ShipComponentType.GRAVITY_RIPPER
          || comp.getType() == ShipComponentType.PLASMA_CANNON
          || comp.getType() == ShipComponentType.ION_CANNON
          || comp.getType() == ShipComponentType.BITE
          || comp.getType() == ShipComponentType.TENTACLE
          || comp.getType() == ShipComponentType.ARM_SPIKE) {
        count++;
      }
    }
    return count;
  }

  /**
   * Does ship design have Defense Component?
   * True if one DefanseComponent is in place.
   * @return True if DefanseComponent is found, otherwise false
   */

  public boolean hasDefenseComponent() {
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.ARMOR
          || comp.getType() == ShipComponentType.SHIELD
          || comp.getType() == ShipComponentType.DISTORTION_SHIELD
          || comp.getType() == ShipComponentType.MULTIDIMENSION_SHIELD
          || comp.getType() == ShipComponentType.SOLAR_ARMOR
          || comp.getType() == ShipComponentType.ORGANIC_ARMOR) {
        return true;
      }
    }
    return false;
  }

  /**
   * Does ship design for MilitaryShip? True if one weapon is in place.
   * @return True if ship is MilitaryShip, otherwise false
   */
  public boolean isMilitaryShip() {
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.WEAPON_BEAM
          || comp.getType() == ShipComponentType.WEAPON_RAILGUN
          || comp.getType() == ShipComponentType.WEAPON_HE_MISSILE
          || comp.getType() == ShipComponentType.MULTICANNON
          || comp.getType() == ShipComponentType.ION_CANNON
          || comp.getType() == ShipComponentType.PLASMA_CANNON
          || comp.getType() == ShipComponentType.GRAVITY_RIPPER
          || comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO
          || comp.getType() == ShipComponentType.BITE
          || comp.getType() == ShipComponentType.TENTACLE
          || comp.getType() == ShipComponentType.ARM_SPIKE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is design bomber ship? True if one orbital bomb or nuke is place.
   * @return True if ship is BomberShip, otherwise false
   */
  public boolean isBomberShip() {
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.ORBITAL_BOMBS
          || comp.getType() == ShipComponentType.ORBITAL_NUKE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is design nuclear bomber ship? True if one orbital nuke is place.
   * @return True if ship is NuclearBomberShip, otherwise false
   */
  public boolean isNuclearBomberShip() {
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.ORBITAL_NUKE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is design trooper ship? True if one planetary invasion module is place.
   * @return True if ship is trooper ship, otherwise false
   */
  public boolean isTrooperShip() {
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.PLANETARY_INVASION_MODULE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is design privateer design.
   * @return True if privateer design
   */
  public boolean isPrivateer() {
    if (hull.getHullType() == ShipHullType.PRIVATEER) {
      return true;
    }
    return false;
  }
  /**
   * Get total value of starbase component bonus
   * @return Starbase component bonus value
   */
  public int getStarbaseValue() {
    int result = 0;
    for (ShipComponent comp : components) {
      result = result + comp.getResearchBonus();
      result = result + comp.getCreditBonus();
      result = result + comp.getCultureBonus();
      result = result + comp.getFleetCapacityBonus();
    }
    return result;
  }
  /**
   * Does ship design have engine? True if engine is in place.
   * @return True if engine is found, otherwise false
   */
  public boolean hasEngine() {
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.ENGINE) {
        return true;
      }
      if (comp.getType() == ShipComponentType.SPACE_FIN) {
        return true;
      }
    }
    return false;
  }
  /**
   * Does ship design have more than one engine?
   *  True if two or more engines are in place.
   * @return True if engines are found, otherwise false
   */
  private boolean hasTwoEngines() {
    String mainEngineName = null;
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.ENGINE) {
        if (mainEngineName == null) {
          mainEngineName = comp.getName();
        } else if (!comp.getName().equals(mainEngineName)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get number of free slots
   * @return number of free slots in ship design
   */
  public int getFreeSlots() {
    return hull.getMaxSlot() - components.size();
  }

  /**
   * Get current cost for ship. This is very useful information
   * when ship is being design.
   * @return Cost in production
   */
  public int getCost() {
    int cost = 0;
    for (ShipComponent comp : components) {
      cost = cost + comp.getCost();
    }
    cost = cost + hull.getCost();
    return cost;
  }

  /**
   * Get current metal cost for ship. This is very useful information
   * when ship is being design.
   * @return Cost in production
   */
  public int getMetalCost() {
    int cost = 0;
    for (ShipComponent comp : components) {
      cost = cost + comp.getMetalCost();
    }
    cost = cost + hull.getMetalCost();
    return cost;
  }

  /**
   * Calculate military power of design. Design needs to have at least single
   * weapon to be a military ship
   * @return Military power
   */
  public int getTotalMilitaryPower() {
    int power = 0;
    boolean militaryShip = false;
    power = getHull().getSlotHull() * components.size();
    for (ShipComponent comp : components) {
      if (isMilitaryShip()
          && (comp.getType() == ShipComponentType.WEAPON_BEAM
          || comp.getType() == ShipComponentType.WEAPON_HE_MISSILE
          || comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO
          || comp.getType() == ShipComponentType.WEAPON_RAILGUN)
          || comp.getType() == ShipComponentType.ION_CANNON
          || comp.getType() == ShipComponentType.MULTICANNON
          || comp.getType() == ShipComponentType.GRAVITY_RIPPER
          || comp.getType() == ShipComponentType.PLASMA_CANNON
          || comp.getType() == ShipComponentType.BITE
          || comp.getType() == ShipComponentType.TENTACLE
          || comp.getType() == ShipComponentType.ARM_SPIKE) {
        militaryShip = true;
        power = power + comp.getDamage();
      }
      if (comp.getType() == ShipComponentType.WEAPON_ECM_TORPEDO) {
        power = power + comp.getDamage() / 2;
      }
      if (hasDefenseComponent()) {
        power = power + comp.getDefenseValue();
      }
      if (comp.getType() == ShipComponentType.ENGINE
          && getHull().getHullType() != ShipHullType.STARBASE) {
        power = power + comp.getTacticSpeed() - 1;
      }
      if (comp.getType() == ShipComponentType.SPACE_FIN
          && getHull().getHullType() != ShipHullType.STARBASE) {
        power = power + comp.getTacticSpeed() - 1;
      }
      if (comp.getType() == ShipComponentType.TRACTOR_BEAM) {
        power = power + 1;
      }
      if (comp.getType() == ShipComponentType.TARGETING_COMPUTER) {
        power = power + comp.getDamage() / 10;
      }
      if (comp.getType() == ShipComponentType.JAMMER) {
        power = power + comp.getDamage() / 5;
      }
      if (comp.getType() == ShipComponentType.FIGHTER_BAY) {
        power = power + comp.getBaySize();
      }
    }
    if (!militaryShip) {
      power = 0;
    }
    return power;
  }

  /**
   * Calculate Trooper power of design. Design needs to have at least single
   * trooper module
   * @return Trooper power
   */
  public int getTotalTrooperPower() {
    int power = 0;
    boolean trooperShip = false;
    power = getHull().getSlotHull() * components.size();
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.PLANETARY_INVASION_MODULE) {
        trooperShip = true;
        power = power + comp.getDamage();
        int freeSlots = getHull().getMaxSlot() - components.size();
        if (freeSlots == 0) {
          // No room for troops
          trooperShip = false;
        } else {
          power = power + freeSlots;
        }
      }
      if (hasDefenseComponent()) {
        power = power + comp.getDefenseValue();
      }
      if (comp.getEnergyResource() > 0) {
        power = power + 2;
      }
    }
    if (!trooperShip) {
      power = 0;
    }
    return power;
  }

  /**
   * Calculate Espionage power of design. Design needs to have at least single
   * espionage module
   * @return Espionage  power
   */
  public int getEspionagePower() {
    int power = 0;
    boolean spyShip = false;
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.ESPIONAGE_MODULE) {
        spyShip = true;
        power = power + comp.getEspionageBonus() * 2;
      }
      if (comp.getType() == ShipComponentType.CLOAKING_DEVICE) {
        power = power + comp.getCloaking();
      }
      if (comp.getType() == ShipComponentType.SCANNER) {
        power = power + comp.getScannerRange();
      }
    }
    if (!spyShip) {
      power = 0;
    }
    return power;
  }

  /**
   * Get Ship design fighter bay size
   * @return Fighter bay size
   */
  public int getTotalBaySize() {
    int baySize = 0;
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.FIGHTER_BAY
          && comp.getBaySize() > 0) {
        baySize = baySize + comp.getBaySize();
      }
    }
    return baySize;
  }

  /**
   * Calculate Colony power of design. Design needs to have at least single
   * colony module
   * @return Trooper power
   */
  public int getTotalColonyPower() {
    int power = 0;
    boolean colonyShip = false;
    power = getHull().getSlotHull() * components.size();
    power = power + getFtlSpeed();
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.COLONY_MODULE) {
        colonyShip = true;
        int freeSlots = getHull().getMaxSlot() - components.size();
        if (freeSlots == 0) {
          // No room for colonists
          colonyShip = false;
        } else {
          power = power + freeSlots;
        }
      }
      if (comp.getType() == ShipComponentType.SPORE_MODULE) {
        colonyShip = true;
        power = power + 1;
      }
      if (hasDefenseComponent()) {
        power = power + comp.getDefenseValue();
      }
      if (comp.getCloaking() > 0) {
        power = power + comp.getCloaking() / 10;
      }
    }
    if (!colonyShip) {
      power = 0;
    }
    return power;
  }

  /**
   * Get ship component list in priority order
   * @return Ship component array
   */
  public ShipComponent[] getComponentList() {
    ShipComponent[] result = new ShipComponent[getNumberOfComponents()];
    for (int i = 0; i < getNumberOfComponents(); i++) {
      result[i] = getComponent(i);
    }
    return result;
  }

  /**
   * Change component priority order
   * @param index Index which to change
   * @param higher true for higher priority and false for lower
   * @return int new changed index, if change happened
   */
  public int changePriority(final int index, final boolean higher) {
    if (index >= 0 && index < getNumberOfComponents()) {
      ShipComponent[] result = getComponentList();
      ShipComponent temp = result[index];
      int target = 0;
      if (higher && index > 0) {
        target = index - 1;
      } else if (!higher && index < result.length - 1) {
        target = index + 1;
      }
      result[index] = result[target];
      result[target] = temp;
      components.clear();
      Collections.addAll(components, result);
      return target;
    }
    return index;
  }

  /**
   * Get Design Info, but not the component info
   * @return Design info as a String
   */
  public String getDesignInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    sb.append(" - ");
    sb.append(hull.getHullType().toString());
    sb.append("\n");
    sb.append("Energy: ");
    sb.append(getFreeEnergy());
    sb.append(" Init.: ");
    sb.append(getInitiative());
    sb.append("\n");
    sb.append("Cost: ");
    sb.append(getCost());
    sb.append(" Metal: ");
    sb.append(getMetalCost());
    sb.append("\n");
    sb.append("Shield: ");
    sb.append(getTotalShield());
    sb.append(" Armor: ");
    sb.append(getTotalArmor());
    sb.append(" Hull Points: ");
    sb.append(hull.getSlotHull() * getNumberOfComponents());
    if (getTotalMilitaryPower() > 0) {
      sb.append("\n");
      sb.append("Military power: ");
      sb.append(getTotalMilitaryPower());
    }
    sb.append("\n");
    sb.append("Slots: ");
    sb.append(components.size());
    sb.append("/");
    sb.append(hull.getMaxSlot());
    if (hull.getHullType() == ShipHullType.FREIGHTER) {
      sb.append("\n");
      sb.append("Cargo: ");
      sb.append(getFreeSlots() * 10);
      sb.append(" units or ");
      sb.append(getFreeSlots() * 2);
      sb.append(" population");
    }
    int baySize = getTotalBaySize();
    if (baySize > 0) {
      sb.append("\nFighter bays: ");
      sb.append(baySize);
    }
    return sb.toString();
  }

  /**
   * Get ship design flaws as a string.
   * @param banNukes True if nuclear weapons are banned.
   * @param banPrivateer true if privateer ships are banned
   * @param allowArmedFreighters True if armed freighters are allowed.
   * @return Design flaws in a string
   */
  public String getFlaws(final boolean banNukes, final boolean banPrivateer,
      final boolean allowArmedFreighters) {
    boolean designOk = true;
    boolean flawNoCargoSpace = false;
    StringBuilder sb = new StringBuilder();
    if (getNumberOfComponents() == 0) {
      designOk = false;
      sb.append(ShipDesignConsts.NO_COMPONENTS);
      sb.append("\n");
    }
    if (!hasEngine() && getHull().getHullType() != ShipHullType.ORBITAL) {
      designOk = false;
      sb.append(ShipDesignConsts.ENGINE_IS_MISSING);
    }
    if (hasEngine() && getHull().getHullType() == ShipHullType.ORBITAL) {
      designOk = false;
      sb.append(ShipDesignConsts.ORBITAL_HAS_ENGINE);
    }
    if (hasTwoEngines()) {
      designOk = false;
      sb.append(ShipDesignConsts.TWO_ENGINES);
    }
    if (getFreeSlots() < 0) {
      designOk = false;
      sb.append("Too many components!\n");
    }
    if (getFreeSlots() == 0 && hull.getHullType() == ShipHullType.FREIGHTER) {
      flawNoCargoSpace = true;
    }
    if (allowArmedFreighters && hull.getHullType() == ShipHullType.FREIGHTER) {
      if (countWeapons() > 1) {
        designOk = false;
        sb.append(ShipDesignConsts.SINGLE_WEAPON_ALLOWED)
          .append(hull.getHullType().toString())
          .append("!\n");
      }
    } else if (hasWeapons() && hull.getHullType() == ShipHullType.FREIGHTER) {
      designOk = false;
      sb.append(ShipDesignConsts.NO_WEAPONS_ALLOWED)
        .append(hull.getHullType().toString())
        .append("!\n");
    }
    if (hasWeapons() && hull.getHullType() == ShipHullType.PROBE) {
      designOk = false;
      sb.append(ShipDesignConsts.NO_WEAPONS_ALLOWED)
        .append(hull.getHullType().toString())
        .append("!\n");
    }
    boolean targetingComputer = false;
    boolean jammer = false;
    boolean privateerModule = false;
    boolean espionageModule = false;
    boolean fighterBay = false;
    boolean invasionModule = false;
    boolean colonizationModule = false;
    for (int i = 0; i < getNumberOfComponents(); i++) {
      ShipComponent comp = getComponent(i);
      if (comp.getType() == ShipComponentType.FIGHTER_BAY) {
        fighterBay = true;
      }
      if (comp.getType() == ShipComponentType.COLONY_MODULE) {
        colonizationModule = true;
        if (hull.getHullType() != ShipHullType.FREIGHTER) {
          designOk = false;
          sb.append("Colonization module in non freighter hull.");
        }
      }
      if (comp.getType() == ShipComponentType.SPORE_MODULE) {
        colonizationModule = true;
        flawNoCargoSpace = false;
        if (hull.getHullType() != ShipHullType.FREIGHTER) {
          designOk = false;
          sb.append("Spore module in non freighter hull.");
        }
      }
      if (comp.getType() == ShipComponentType.PLANETARY_INVASION_MODULE) {
        invasionModule = true;
      }
      if (comp.getType() == ShipComponentType.STARBASE_COMPONENT
          && (hull.getHullType() == ShipHullType.FREIGHTER
          || hull.getHullType() == ShipHullType.NORMAL
          || hull.getHullType() == ShipHullType.PRIVATEER
          || hull.getHullType() == ShipHullType.PROBE)) {
        designOk = false;
        sb.append(ShipDesignConsts.STARBASE_MODULE_IN_NOT_STARBASE);
        sb.append("\n");
      }
      if (comp.getType() == ShipComponentType.ORBITAL_NUKE && banNukes) {
        designOk = false;
        sb.append(ShipDesignConsts.NUKES_ARE_BANNED);
        sb.append("\n");
      }
      if (comp.getType() == ShipComponentType.PRIVATEERING_MODULE) {
        privateerModule = true;
        if (hull.getHullType() != ShipHullType.PRIVATEER) {
          designOk = false;
          sb.append(ShipDesignConsts.PRIVATEER_MODULE_IN_NOT_PRIVATEER);
          sb.append("\n");
        }
      }
      if (comp.getType() == ShipComponentType.TARGETING_COMPUTER
          && !targetingComputer) {
        targetingComputer = true;
      } else if (comp.getType() == ShipComponentType.TARGETING_COMPUTER
          && targetingComputer) {
        designOk = false;
        sb.append(ShipDesignConsts.MANY_COMPUTERS);
        sb.append("\n");
      }
      if (comp.getType() == ShipComponentType.JAMMER
          && !jammer) {
        jammer = true;
      } else if (comp.getType() == ShipComponentType.JAMMER
          && jammer) {
        designOk = false;
        sb.append(ShipDesignConsts.MANY_JAMMERS);
        sb.append("\n");
      }
      if (comp.getType() == ShipComponentType.DISTORTION_SHIELD
          && !jammer) {
        jammer = true;
      } else if (comp.getType() == ShipComponentType.DISTORTION_SHIELD
          && jammer) {
        designOk = false;
        sb.append(ShipDesignConsts.MANY_JAMMERS);
        sb.append("\n");
      }
      if (comp.getType() == ShipComponentType.MULTIDIMENSION_SHIELD
          && !jammer) {
        jammer = true;
      } else if (comp.getType() == ShipComponentType.MULTIDIMENSION_SHIELD
          && jammer) {
        designOk = false;
        sb.append(ShipDesignConsts.MANY_JAMMERS);
        sb.append("\n");
      }
      if (comp.getType() == ShipComponentType.ESPIONAGE_MODULE
          && !espionageModule) {
        espionageModule = true;
      } else if (comp.getType() == ShipComponentType.ESPIONAGE_MODULE
          && espionageModule) {
        designOk = false;
        sb.append(ShipDesignConsts.MANY_ESPIONAGE);
        sb.append("\n");
      }

    }
    if (colonizationModule && invasionModule) {
      designOk = false;
      sb.append(ShipDesignConsts.BOTH_COLONY_AND_INVASION);
      sb.append("\n");
    }
    if (flawNoCargoSpace && !fighterBay) {
      designOk = false;
      sb.append(ShipDesignConsts.NO_CARGO);
      sb.append("\n");
    } else if (flawNoCargoSpace && fighterBay
        && (colonizationModule || invasionModule)) {
      designOk = false;
      sb.append(ShipDesignConsts.NO_CARGO);
      sb.append("\n");
    }
    if (hull.getHullType() == ShipHullType.PRIVATEER && !privateerModule) {
      designOk = false;
      sb.append(ShipDesignConsts.PRIVATEER_MODULE_MISSING);
      sb.append("\n");
    }
    if (hull.getHullType() == ShipHullType.PRIVATEER && privateerModule
        && banPrivateer) {
      designOk = false;
      sb.append(ShipDesignConsts.PRIVATEERS_ARE_BANNED);
      sb.append("\n");
    }
    if (getFreeEnergy() < 0) {
      designOk = false;
      sb.append("No enough energy resources!");
    }
    if (designOk) {
      // Return OK text
      sb.append(ShipDesignConsts.DESIGN_OK);
    }
    return sb.toString();

  }

  /**
   * Get ship design flaws as a string. This will not check if
   * privateer or nukes are banned.
   * @return Design flaws in a string
   */
  public String getFlaws() {
    return getFlaws(false, false, false);
  }

  /**
   * Get ship's initiative for combat. Bigger value is better in combat
   * @return Initiative
   */
  public int getInitiative() {
    int result = 0;
    switch (hull.getSize()) {
    case SMALL: {
      result = 12;
      break;
    }
    case MEDIUM: {
      result = 8;
      break;
    }
    case LARGE: {
      result = 4;
      break;
    }
    case HUGE: {
      result = 0;
      break;
    }
    default:
      result = 0;
    }
    for (ShipComponent comp : components) {
      if (comp.getType() == ShipComponentType.ENGINE) {
        result = result + comp.getSpeed() + comp.getTacticSpeed();
      }
      if (comp.getType() == ShipComponentType.SPACE_FIN) {
        result = result + comp.getSpeed() + comp.getTacticSpeed();
      }
      if (comp.getInitiativeBoost() > 0) {
        result = result + comp.getInitiativeBoost();
      }
    }
    int emptySpace = this.hull.getMaxSlot() - components.size();
    if (emptySpace == 2 || emptySpace == 3) {
        result = result + 1;
    } else if (emptySpace == 4) {
        result = result + 2;
    } else if (emptySpace == 5) {
        result = result + 3;
    } else if (6 <= emptySpace && emptySpace < 12) {
        result = result + 4;
    }
    return result;
  }

  /**
   * @return defenseValue
   */
  private int increaseDefenseValueWithJammer() {
    int defenseValue = 0;
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.JAMMER) {
          defenseValue = defenseValue + comp.getDefenseValue();
      }
      if (comp.getType() == ShipComponentType.DISTORTION_SHIELD) {
          defenseValue = defenseValue + comp.getDamage();
      }
      if (comp.getType() == ShipComponentType.MULTIDIMENSION_SHIELD) {
          defenseValue = defenseValue + comp.getDamage();
      }
    }
    return defenseValue;
  }
  /**
   * Get defense value for ship
   * @return defense value for ship
   */
  public int getDefenseValue() {
    int defenseValue = hull.getDefenseValueByShipHullSize();
    defenseValue += increaseDefenseValueWithJammer();
    if (getTacticSpeed() == 0) {
        defenseValue = defenseValue - 15;
    }
    boolean thrusters = false;
    boolean cloakingDevice = false;
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.THRUSTERS) {
        thrusters = true;
      }
      if (comp.getType() == ShipComponentType.CLOAKING_DEVICE) {
        cloakingDevice = true;
      }
    }
    if (thrusters) {
      defenseValue = defenseValue + 5;
    }
    if (cloakingDevice) {
      defenseValue = defenseValue + 5;
    }
    return defenseValue;
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    sb.append(" - ");
    sb.append(hull.getHullType().toString());
    sb.append("\n");
    sb.append("Energy: ");
    sb.append(getFreeEnergy());
    sb.append(" Init.: ");
    sb.append(getInitiative());
    sb.append("\n");
    sb.append("Cost: ");
    sb.append(getCost());
    sb.append(" Metal: ");
    sb.append(getMetalCost());
    sb.append("\n");
    sb.append("Speed: ");
    sb.append(getSpeed());
    sb.append(" FTL: ");
    sb.append(getFtlSpeed());
    sb.append(" Tactic: ");
    sb.append(getTacticSpeed());
    sb.append("\n");
    sb.append("Shield: ");
    sb.append(getTotalShield());
    sb.append(" Armor: ");
    sb.append(getTotalArmor());
    sb.append(" Defense: ");
    sb.append(getDefenseValue());
    sb.append(" Hull Points: ");
    sb.append(hull.getSlotHull() * getNumberOfComponents());
    if (getTotalMilitaryPower() > 0) {
      sb.append("\n");
      sb.append("Military power: ");
      sb.append(getTotalMilitaryPower());
    }
    sb.append("\n\nComponents:\n");
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      sb.append(i + 1);
      sb.append(": ");
      sb.append(comp.toString());
      sb.append("\n");
    }
    return sb.toString();
  }

}
