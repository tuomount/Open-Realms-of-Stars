package org.openRealmOfStars.player.ship;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.gui.GuiStatics;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.SpaceRace;
import org.openRealmOfStars.starMap.planet.construction.Construction;
import org.openRealmOfStars.utilities.DiceGenerator;
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
 * Ship class for actual instance of a ship
 * 
 */

public class Ship extends Construction {

  
  /**
   * Ship's hull
   */
  private ShipHull hull;
  
  /**
   * Ship's component list
   */
  private ArrayList<ShipComponent> components;
  
  /**
   * Hit points per component
   */
  private int[] hullPoints;
  
  /**
   * Current shield level
   */
  private int shield;
  
  /**
   * Current armor level
   */
  private int armor;

  /**
   * Ship's image
   */
  private BufferedImage image;
  
  /**
   * Colonist cargo
   */
  private int colonist;
  /**
   * Metal cargo
   */
  private int metal;
  /**
   * Constructor for a ship
   * @param design from where actual ship is created
   */
  public Ship(ShipDesign design) {
    super(design.getName(), Icons.getIconByName(Icons.ICON_HULL_TECH));
    prodCost = design.getCost();
    metalCost = design.getMetalCost();
    hull = design.getHull();
    components = new ArrayList<>();
    ShipComponent[] designComponents = design.getComponentList();
    hullPoints = new int[designComponents.length];
    for (int i=0;i<designComponents.length;i++) {
      components.add(designComponents[i]);
      hullPoints[i] = design.getHull().getSlotHull();
    }
    setShield(design.getTotalShield());
    setArmor(design.getTotalArmor());
    image = ShipImage.scaleTo32x32(hull.getImage());
    setDescription(design.getDesignInfo());
    setColonist(0);
    setMetal(0);
  }
  
  /**
   * Read Ship from DataInputStream
   * @param dis DataInputStream
   * @throws IOException
   */
  public Ship(DataInputStream dis) throws IOException {
    super("SHIP",Icons.getIconByName(Icons.ICON_HULL_TECH));
    String name = IOUtilities.readString(dis);
    setName(name);
    prodCost = dis.readInt();
    metalCost = dis.readInt();
    String hullName = IOUtilities.readString(dis);
    int raceIndex = dis.readInt();
    hull = ShipHullFactory.createByName(hullName, SpaceRace.getRaceByIndex(raceIndex));
    image = ShipImage.scaleTo32x32(hull.getImage());
    int count = dis.readInt();
    components = new ArrayList<>();
    hullPoints = new int[count];
    for (int i=0;i<count;i++) {
      ShipComponent comp = ShipComponentFactory.createByName(IOUtilities.readString(dis));
      components.add(comp);
      hullPoints[i] = dis.readInt();
    }
    setShield(dis.readInt());
    setArmor(dis.readInt());
    setColonist(dis.readInt());
    setMetal(dis.readInt());
  }
  
  /**
   * Save ship to Data output Stream
   * @param dos Data Output Stream
   * @throws IOException
   */
  public void saveShip(DataOutputStream dos) throws IOException {
    IOUtilities.writeString(dos, getName());
    dos.writeInt(prodCost);
    dos.writeInt(metalCost);
    IOUtilities.writeString(dos, hull.getName());
    dos.writeInt(hull.getRace().getIndex());
    dos.writeInt(components.size());
    for (int i=0;i<components.size();i++) {
      IOUtilities.writeString(dos, components.get(i).getName());
      dos.writeInt(hullPoints[i]);
    }
    dos.writeInt(getShield());
    dos.writeInt(getArmor());
    dos.writeInt(getColonist());
    dos.writeInt(getMetal());

  }
  
  
  
  @Override
  public String getDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    sb.append(" - ");
    sb.append(hull.getHullType().toString());
    sb.append("\n");
    sb.append("Energy: ");
    sb.append(getTotalEnergy());
    sb.append(" Init.: ");
    sb.append(getInitiative());
    sb.append("\n");
    sb.append("Cost: ");
    sb.append(getProdCost());
    sb.append(" Metal: ");
    sb.append(getMetalCost());
    sb.append("\n");
    sb.append("Shield: ");
    sb.append(getTotalShield());
    sb.append(" Armor: ");
    sb.append(getTotalArmor());
    sb.append(" Hull Points: ");
    sb.append(hull.getSlotHull()*getNumberOfComponents());
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
      sb.append(getMetal());
      sb.append(" Units:");
      sb.append(getColonist());
    }
    if (isTrooperShip()) {
      sb.append("\n");
      sb.append("Troops power ");
      sb.append(getTroopPower());
    }
    return sb.toString();
  }

  /**
   * Get hull points for certain component
   * @param index Component index
   * @return Hull points
   */
  public int getHullPointForComponent(int index) {
    if (index >= 0 && index < hullPoints.length) {
      return hullPoints[index];
    }
    return 0;
  }

  /**
   * Generate shields according the shields and generator
   */
  public void regenerateShield() {
    boolean workingShields = false;
    boolean shieldsUp = false;
    boolean generatorUp = false;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType()==ShipComponentType.SHIELD && componentIsWorking(i) &&
          !shieldsUp){
        workingShields = true;
        shieldsUp = true;
        if (shield < getTotalShield()) {
          shield++;
        }
      }
      if (comp.getType()==ShipComponentType.SHIELD_GENERATOR && componentIsWorking(i) &&
          !generatorUp){
        generatorUp = true;
        if (shield+comp.getDefenseValue() <= getTotalShield()) {
          shield=shield+comp.getDefenseValue();
        }
        
      }
    }
    if (!workingShields) {
      shield = 0;
    }
  }
  
  /**
   * Check if certain component has energy or not. Returns true if component has
   * energy. This also checks that component is functional.
   * @param index Component index
   * @return true if has energy
   */
  public boolean hasComponentEnergy(int index) {
    int energy = getTotalEnergy();
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getEnergyRequirement()>0) {
        energy = energy -comp.getEnergyRequirement();
      }
      if (index == i && (energy >= 0 || comp.getEnergyRequirement()==0)) {
        return true;
      }
      if (index == i && energy < 0) {
        return false;
      }
    }    
    return false;
  }
  
  /**
   * Check if certain component has enery and hull points so it is functioning.
   * @param index Component index
   * @return True if component is functioning
   */
  public boolean componentIsWorking(int index) {
    return (hasComponentEnergy(index) && getHullPointForComponent(index) > 0);
  }
  /**
   * Get total energy form current component status
   * @return Total energy
   */
  public int getTotalEnergy() {
    int energy = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getEnergyResource()>0) {
        energy = energy +comp.getEnergyResource();
      }
    }
    return energy;
  }
  
  /**
   * Get Speed depending on hullpoints and energy level
   * @return Speed
   */
  public int getSpeed() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.ENGINE
          && hasComponentEnergy(i)) {
        return comp.getSpeed();
      }
    }
    return 0;
  }

  /**
   * Get tactic Speed depending on hullpoints and energy level
   * @return Speed
   */
  public int getTacticSpeed() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.ENGINE
          && hasComponentEnergy(i)) {
        return comp.getTacticSpeed();
      }
    }
    return 0;
  }

  /**
   * Are there any weapons left
   * @return true if there are weapons left
   */
  public boolean hasWeapons() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && hasComponentEnergy(i)) {
        if (comp.getType() == ShipComponentType.WEAPON_BEAM ||
            comp.getType() == ShipComponentType.WEAPON_ECM_TORPEDO ||
            comp.getType() == ShipComponentType.WEAPON_HE_MISSILE ||
            comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO ||
            comp.getType() == ShipComponentType.WEAPON_RAILGUN) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Are there any bombs left
   * @return true if there are weapons left
   */
  public boolean hasBombs() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && hasComponentEnergy(i)) {
        if (comp.getType() == ShipComponentType.ORBITAL_BOMBS ||
            comp.getType() == ShipComponentType.ORBITAL_NUKE) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get FTL Speed depending on hullpoints and energy level
   * @return Speed
   */
  public int getFtlSpeed() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.ENGINE
          && hasComponentEnergy(i)) {
        return comp.getFtlSpeed();
      }
    }
    return 0;
  }

  /**
   * Get ship' initiative for combat. Bigger value is better in combat
   * @return Initiative
   */
  public int getInitiative() {
    int result = 0;
    switch (hull.getSize()) {
    case SMALL:{ 
      result = 12; break; }
    case MEDIUM:{ 
      result = 8; break; }
    case LARGE:{ 
      result = 4; break; }
    case HUGE:{ 
      result = 0; break; }
    }
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.ENGINE
          && hasComponentEnergy(i)) {
        result = result +comp.getSpeed()+comp.getTacticSpeed();
      }
      if (hullPoints[i] > 0 && hasComponentEnergy(i) && comp.getInitiativeBoost()>0) {
        result = result +comp.getInitiativeBoost();
      }
    }
    int emptySpace = this.hull.getMaxSlot()-components.size();
    switch (emptySpace) {
    case 1: 
    case 0: {break;}
    case 2: 
    case 3: {result = result +1; break;}
    case 4: {result = result +2; break;}
    case 5: {result = result +3; break;}
    case 6:
    case 7:
    case 8:
    case 9:
    case 11:
    case 10: {result = result +4; break;}
    }
    return result;
  }

  /**
   * Get Scanner level
   * @return scanner Lvl
   */
  public int getScannerLvl() {
    int result = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.SCANNER
          && hasComponentEnergy(i) && comp.getScannerRange() > result) {
        result = comp.getScannerRange();
      }
    }
    return result;
  }

  /**
   * Get Scanner cloak detection level
   * @return scanner detection level 
   */
  public int getScannerDetectionLvl() {
    int result = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.SCANNER
          && hasComponentEnergy(i) && comp.getCloakDetection() > result) {
        result = comp.getCloakDetection();
      }
    }
    return result;
  }

  /**
   * Get accuracy for certain weapon
   * @param weapon ShipComponent
   * @return Accuracy
   */
  public int getHitChance(ShipComponent weapon) {
    int result = 0;
    switch (weapon.getType()) {
    case WEAPON_BEAM: result = 100; break;
    case WEAPON_RAILGUN:
    case WEAPON_PHOTON_TORPEDO: result = 75; break;
    case WEAPON_ECM_TORPEDO:
    case WEAPON_HE_MISSILE:
    default: result = 50; break;
    }
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.TARGETING_COMPUTER
          && hasComponentEnergy(i)) {
        result = result+comp.getDamage();
      }
    }
    return result;
  }

  /**
   * Damage component by randomly
   * @param damage Amount damage to cause
   * @param shipDamage Ship Damage to more description infomation about damage
   * @return amount damage left aka pierced the component
   */
  public int damageComponent(int damage,ShipDamage shipDamage) {
    int[] componentPos = new int[components.size()];
    ShipComponent[] compList = new ShipComponent [components.size()];
    int positions = 0;
    for (int i=0;i<hullPoints.length;i++) {
      if (hullPoints[i] > 0) {
        componentPos[positions] = i;
        compList[positions] = components.get(i);
        positions++;
      }
    }
    int target = 0;
    if (positions > 1) {
      target = DiceGenerator.getRandom(positions-1);
    }
    int hp = hullPoints[componentPos[target]];
    hullPoints[componentPos[target]] = hullPoints[componentPos[target]]-damage;
    if (hullPoints[componentPos[target]]<0) {
      // No negative hull points to components
      hullPoints[componentPos[target]] = 0;
    }
    if (hullPoints[componentPos[target]]==0) {
      shipDamage.addText(compList[target].getName()+" is destroyed!");
    } else {
      shipDamage.addText(compList[target].getName()+" damaged!");
    }
    damage = damage-hp;
    if (hp == 0) {
      // Ship has been destroyed by attack nothing to pierce...
      damage = 0;
    }
    return damage;    
  }
  
  /**
   * Get accuracy for certain weapon
   * @param weapon ShipComponent
   * @return 1 No damage, not even dent
   *         0 No damage, but shield or armor got lower
   *         -1 Got damage
   *         -2 Destroyed
   */
  public ShipDamage damageBy(ShipComponent weapon) {
    int damage = 0;
    switch (weapon.getType()) {
    case WEAPON_BEAM: 
    case WEAPON_PHOTON_TORPEDO:{
      damage = weapon.getDamage();
      damage = damage -this.getShield();
      if (damage > 0) {
        this.setShield(this.getShield()-1);
      } else {
        if (this.getShield()/2 < weapon.getDamage()) {
          this.setShield(this.getShield()-1);
          return new ShipDamage(0,"Attack hit the shield!");
        }
        return new ShipDamage(1,"Attacked missed!");
      }
      damage = damage-this.getArmor()/2;
      if (damage >= 0) {
        this.setArmor(this.getArmor()-1);
      } else {
        damage = damage+this.getArmor()/2;
        if (this.getArmor()/4 < damage) {
          this.setArmor(this.getArmor()-1);
          return new ShipDamage(0,"Attack hit the armor!");
        }
      }
      break;
    }
    case WEAPON_RAILGUN: 
    case WEAPON_HE_MISSILE:{
      damage = weapon.getDamage();
      damage = damage -this.getArmor();
      if (damage > 0) {
        this.setArmor(this.getArmor()-1);
      } else {
        if (this.getArmor()/2 < weapon.getDamage()) {
          this.setArmor(this.getArmor()-1);
          return new ShipDamage(0,"Attack hit the armor!");
        }
        return new ShipDamage(1,"Attacked missed!");
      }
      damage = damage-this.getShield()/2;
      if (damage >= 0) {
        this.setArmor(this.getShield()-1);
      } else {
        damage = damage+this.getShield()/2;
        if (this.getShield()/4 < damage) {
          this.setShield(this.getShield()-1);
          return new ShipDamage(0,"Attack hit the shield!");
        }
      }
      break;
    }
    case WEAPON_ECM_TORPEDO: {
      damage = weapon.getDamage();
      this.setShield(this.getShield()-damage);
      return new ShipDamage(1,"Attacked missed!");
    }
    default: /* Not a weapon */break;
    }    
    ShipDamage shipDamage = new ShipDamage(-1,"Attack hit causing "+damage+" damage!");
    while (damage > 0) {
      damage = damageComponent(damage,shipDamage);
    }
    if (getHullPoints()==0) {
      shipDamage.setValue(-2);
      shipDamage.addText(getName()+" is destroyed!");
    }    
    return shipDamage;
  }

  /**
   * Get defense value for ship
   * @return defense value for ship
   */
  public int getDefenseValue() {
    int result = 0;
    switch (hull.getSize()) {
    case SMALL: result = 10; break;
    case MEDIUM: result = 5; break;
    case LARGE: result = 0; break;
    case HUGE: result = -5; break;
    }
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.JAMMER
          && hasComponentEnergy(i)) {
        result = result+comp.getDefenseValue();
      }
    }
    if (getTacticSpeed() == 0) {
      result = result -15;
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
   * Is Ship colony ship or not
   * @return True if ship has functional colony module, otherwise false
   */
  public boolean isColonyShip() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.COLONY_MODULE
          && hasComponentEnergy(i) && getColonist() > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is Ship trooper ship or not
   * @return True if ship has functional planetary invasion module, otherwise false
   */
  public boolean isTrooperShip() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.PLANETARY_INVASION_MODULE
          && hasComponentEnergy(i) && getColonist() > 0) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Get Troop power
   * @return Get Total troop power where improvements are taken to count
   */
  public int getTroopPower() {
    int result = getColonist()*hull.getRace().getTrooperPower();
    int multiply = 100;
    boolean found = false;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (hullPoints[i] > 0 && comp.getType()==ShipComponentType.PLANETARY_INVASION_MODULE
          && hasComponentEnergy(i) && getColonist() > 0 && comp.getDamage()>0) {
        multiply = multiply + comp.getDamage();
        found = true;
      }
    }
    result = result*multiply/100;
    if (!found) {
      result = 0;
    }
    return result;
  }


  /**
   * Ship has colony module
   * @return True if ship has colony module, otherwise false
   */
  public boolean isColonyModule() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType()==ShipComponentType.COLONY_MODULE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Ship has trooper module
   * @return True if ship has trooper module, otherwise false
   */
  public boolean isTrooperModule() {
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType()==ShipComponentType.PLANETARY_INVASION_MODULE) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get Ships damage level as color
   * @return one of these:
   * GuiStatics.COLOR_GREEN_TEXT 
   * GuiStatics.COLOR_YELLOW_TEXT
   * GuiStatics.COLOR_RED_TEXT
   */
  public Color getDamageColor() {
    int damageLvl = 0;
    for (int i=0;i<hullPoints.length;i++) {
      if (hullPoints[i] <hull.getSlotHull() && hullPoints[i] > 0 && damageLvl < 1) {
        damageLvl = 1;
      }
      if (hullPoints[i] == 0 && damageLvl < 2) {
        damageLvl = 2;
      }
    }
    switch (damageLvl) {
    case 0: return GuiStatics.COLOR_GREEN_TEXT;
    case 1: return GuiStatics.COLOR_YELLOW_TEXT;
    case 2: return GuiStatics.COLOR_RED_TEXT;
    default: return GuiStatics.COLOR_RED_TEXT;
    }

  }
  
  
  /**
   * Get ship component by index. Can return null if index is out list.
   * @param index Component list index
   * @return ShipComponent or null
   */
  public ShipComponent getComponent(int index) {
    if (index >= 0 && index < components.size()) {
      return components.get(index);
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public ShipHull getHull() {
    return hull;
  }

  public BufferedImage getImage() {
    return image;
  }

  /**
   * Get maximum shield for ship. This is very useful information
   * when ship is being design and shown on ship view
   * @return Maximum shield
   */
  public int getTotalShield() {
    int shield = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getDefenseValue() > 0 && comp.getType() == ShipComponentType.SHIELD) {
        shield = shield +comp.getDefenseValue();
      }
    }
    return shield;
  }

  /**
   * Get the smallest weapon range
   * @return range
   */
  public int getMinWeaponRange() {
    int range = 999;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.isWeapon() && comp.getWeaponRange() < range) {
        range = comp.getWeaponRange();
      }
    }
    return range;
  }

  /**
   * Get the biggest weapon range
   * @return range
   */
  public int getMaxWeaponRange() {
    int range = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.isWeapon() && comp.getWeaponRange() > range) {
        range = comp.getWeaponRange();
      }
    }
    return range;
  }

  /**
   * Get maximum armor for ship. This is very useful information
   * when ship is being design and shown on ship view
   * @return Maximum armor
   */
  public int getTotalArmor() {
    int armor = 0;
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getDefenseValue() > 0 && comp.getType() == ShipComponentType.ARMOR) {
        armor = armor +comp.getDefenseValue();
      }
    }
    return armor;
  }

  /**
   * Get Total amount of hull point
   * @return Maximum hull points
   */
  public int getMaxHullPoints() {
    return hull.getSlotHull()*hullPoints.length;
  }

  /**
   * Get current hull points
   * @return hull points
   */
  public int getHullPoints() {
    int value = 0;
    for (int i=0;i<hullPoints.length;i++) {
      value = value +hullPoints[i];
    }
    return value;
  }

  public int getShield() {
    return shield;
  }

  /**
   * Shield value cannot be set under zero. Minimum value is set to zero.
   * @param shield New shield value
   */
  public void setShield(int shield) {
    if (shield >= 0) {
      this.shield = shield;
    } else {
      this.shield = 0;
    }
  }

  public int getArmor() {
    return armor;
  }

  /**
   * Armor value cannot be set under zero. Minimum value is set to zero.
   * @param armor New armor value
   */
  public void setArmor(int armor) {
    if (armor >= 0) {
      this.armor = armor;
    } else {
      this.armor = 0;
    }
  }

  public int getColonist() {
    return colonist;
  }

  public void setColonist(int colonist) {
    this.colonist = colonist;
  }

  public int getMetal() {
    return metal;
  }

  public void setMetal(int metal) {
    this.metal = metal;
  }

  /**
   * How much metal can be fit to cargo space
   * @return Cargo room for metal
   */
  public int getFreeCargoMetal() {
    int result = 0;
    if (hull.getHullType() == ShipHullType.FREIGHTER) {
      result = hull.getMaxSlot()-getNumberOfComponents();
      result = result -getColonist()/2-getMetal()/10-getColonist()%2;
      result = result *10;
    }
    return result;
  }
  
  /**
   * How many colonists can be fit to cargo space
   * @return Cargo room for colonists
   */
  public int getFreeCargoColonists() {
    int result = 0;
    if (hull.getHullType() == ShipHullType.FREIGHTER) {
      result = hull.getMaxSlot()-getNumberOfComponents();
      result = result -getColonist()/2-getMetal()/10;
      result = result *2;
    }
    return result;
  }
  
  /**
   * Calculate military power of design. Design needs to have at least single
   * weapon to be a military ship
   * @return Military power
   */
  public int getTotalMilitaryPower() {
    int power = 0;
    boolean militaryShip = false;
    power = getHull().getSlotHull()*getHull().getMaxSlot();
    for (int i=0;i<components.size();i++) {
      ShipComponent comp = components.get(i);
      if (comp.getType() == ShipComponentType.WEAPON_BEAM || 
          comp.getType() == ShipComponentType.WEAPON_RAILGUN ||
          comp.getType() == ShipComponentType.WEAPON_HE_MISSILE || 
          comp.getType() == ShipComponentType.WEAPON_PHOTON_TORPEDO) {
        militaryShip = true;
        power = power+comp.getDamage();
      }
      if (comp.getType() == ShipComponentType.WEAPON_ECM_TORPEDO) {
        power = power+comp.getDamage()/2;
      }
      if (comp.getType() == ShipComponentType.ARMOR ||
          comp.getType() == ShipComponentType.SHIELD) {
        power = power+comp.getDefenseValue();
      }
      if (comp.getType() == ShipComponentType.ENGINE && 
          getHull().getHullType() != ShipHullType.STARBASE) {
        power = power+comp.getTacticSpeed()-1;
      }
      if (comp.getType() == ShipComponentType.TARGETING_COMPUTER) {
        power = power+comp.getDamage()/10;
      }
      if (comp.getType() == ShipComponentType.JAMMER) {
        power = power+comp.getDamage()/10;
      }
    }
    if (!militaryShip) {
      power = 0;
    }
    return power;
  }


}
