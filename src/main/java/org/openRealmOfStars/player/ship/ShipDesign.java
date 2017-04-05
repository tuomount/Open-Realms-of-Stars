package org.openRealmOfStars.player.ship;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.SpaceRace.SpaceRaceUtility;
import org.openRealmOfStars.utilities.IOUtilities;

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
 * Ship Design class, design is used to create a ship. Design is also
 * linked to Ship Stat class
 *
 */
public class ShipDesign {

  /**
   * Message when ship design is good to go
   */
  public static final String DESIGN_OK = "Design is OK!";
  /**
   * Message when ship is missing engine
   */
  public static final String ENGINE_IS_MISSING = "Engine is missing!\n";
  /**
   * Message when ship contains weapons even hull does not allow them.
   */
  public static final String NO_WEAPONS_ALLOWED = "No weapons allowed in ";

  /**
   * Message when ship contains more than one jammer
   */
  public static final String MANY_JAMMERS = "Only one jammer is allowed!";
  /**
   * Message when ship contains more than one targeting computer
   */
  public static final String MANY_COMPUTERS = "Only one targeting computer"
      + " is allowed!";

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
    int raceIndex = dis.readInt();
    setHull(ShipHullFactory.createByName(hullName,
        SpaceRaceUtility.getRaceByIndex(raceIndex)));
    int count = dis.readInt();
    components = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      String compName = IOUtilities.readString(dis);
      ShipComponent comp = ShipComponentFactory.createByName(compName);
      components.add(comp);
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
    dos.writeInt(hull.getRace().getIndex());
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
    }
    return shield;
  }

  /**
   * Get Speed for design.
   * @return Speed
   */
  public int getSpeed() {
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.ENGINE) {
        return comp.getSpeed();
      }
    }
    return 0;
  }

  /**
   * Get tactic Speed for design.
   * @return Speed
   */
  public int getTacticSpeed() {
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.ENGINE) {
        return comp.getTacticSpeed();
      }
    }
    return 0;
  }

  /**
   * Get FTL Speed for design.
   * @return Speed
   */
  public int getFtlSpeed() {
    int result = 0;
    for (int i = 0; i < components.size(); i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.ENGINE) {
        result = comp.getFtlSpeed();
        break;
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
          || comp.getType() == ShipComponentType.WEAPON_RAILGUN) {
        return true;
      }
    }
    return false;
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
      if (comp.getType() == ShipComponentType.WEAPON_BEAM
          || comp.getType() == ShipComponentType.WEAPON_RAILGUN
          || comp.getType() == ShipComponentType.WEAPON_HE_MISSILE
          || comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO) {
        militaryShip = true;
        power = power + comp.getDamage();
      }
      if (comp.getType() == ShipComponentType.WEAPON_ECM_TORPEDO) {
        power = power + comp.getDamage() / 2;
      }
      if (comp.getType() == ShipComponentType.ARMOR
          || comp.getType() == ShipComponentType.SHIELD) {
        power = power + comp.getDefenseValue();
      }
      if (comp.getType() == ShipComponentType.ENGINE
          && getHull().getHullType() != ShipHullType.STARBASE) {
        power = power + comp.getTacticSpeed() - 1;
      }
      if (comp.getType() == ShipComponentType.TARGETING_COMPUTER) {
        power = power + comp.getDamage() / 10;
      }
      if (comp.getType() == ShipComponentType.JAMMER) {
        power = power + comp.getDamage() / 10;
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
      if (comp.getType() == ShipComponentType.ARMOR
          || comp.getType() == ShipComponentType.SHIELD) {
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
      if (higher && index > 0) {
        int target = index - 1;
        result[index] = result[target];
        result[target] = temp;
        components.clear();
        Collections.addAll(components, result);
        return target;
      }
      if (!higher && index < result.length - 1) {
        int target = index + 1;
        result[index] = result[target];
        result[target] = temp;
        components.clear();
        Collections.addAll(components, result);
        return target;
      }
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
    return sb.toString();
  }

  /**
   * Get ship design flaws as a string.
   * @return Desing flaws in a string
   */
  public String getFlaws() {
    boolean designOk = true;
    StringBuilder sb = new StringBuilder();
    if (!hasEngine()) {
      designOk = false;
      sb.append(ENGINE_IS_MISSING);
    }
    if (getFreeSlots() < 0) {
      designOk = false;
      sb.append("Too many components!\n");
    }
    if (getFreeSlots() == 0 && (hull.getHullType() == ShipHullType.FREIGHTER
        || hull.getHullType() == ShipHullType.PRIVATEER)) {
      designOk = false;
      sb.append("No cargo space!\n");
    }
    if (hasWeapons() && (hull.getHullType() == ShipHullType.FREIGHTER
        || hull.getHullType() == ShipHullType.PROBE)) {
      designOk = false;
      sb.append(NO_WEAPONS_ALLOWED).append(hull.getHullType().toString())
          .append("!\n");
    }
    boolean targetingComputer = false;
    boolean jammer = false;
    for (int i = 0; i < getNumberOfComponents(); i++) {
      ShipComponent comp = getComponent(i);
      if (comp.getType() == ShipComponentType.COLONY_MODULE
          && hull.getHullType() != ShipHullType.FREIGHTER) {
        designOk = false;
        sb.append("Colonization module in non freighter hull.");
      }
      if (comp.getType() == ShipComponentType.TARGETING_COMPUTER
          && !targetingComputer) {
        targetingComputer = true;
      } else if (comp.getType() == ShipComponentType.TARGETING_COMPUTER
          && targetingComputer) {
        designOk = false;
        sb.append(MANY_COMPUTERS);
        sb.append("\n");
      }
      if (comp.getType() == ShipComponentType.JAMMER
          && !jammer) {
        jammer = true;
      } else if (comp.getType() == ShipComponentType.JAMMER
          && jammer) {
        designOk = false;
        sb.append(MANY_JAMMERS);
        sb.append("\n");
      }

    }
    if (getFreeEnergy() < 0) {
      designOk = false;
      sb.append("No enough energy resources!");
    }
    if (designOk) {
      // Return OK text
      sb.append(DESIGN_OK);
    }
    return sb.toString();
  }

  /**
   * Get ship's initiative for combat. Bigger value is better in combat
   * @return Initiative
   */
  private int getInitiative() {
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
      if (comp.getInitiativeBoost() > 0) {
        result = result + comp.getInitiativeBoost();
      }
    }
    int emptySpace = this.hull.getMaxSlot() - components.size();
    switch (emptySpace) {
    case 1:
    case 0: {
      break;
    }
    case 2:
    case 3: {
      result = result + 1;
      break;
    }
    case 4: {
      result = result + 2;
      break;
    }
    case 5: {
      result = result + 3;
      break;
    }
    case 6:
    case 7:
    case 8:
    case 9:
    case 11:
    case 10: {
      result = result + 4;
      break;
    }
    default:
      result = 0;
    }
    return result;
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
