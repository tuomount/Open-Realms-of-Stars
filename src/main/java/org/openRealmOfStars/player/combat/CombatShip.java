package org.openRealmOfStars.player.combat;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;
import org.openRealmOfStars.player.ship.ShipComponentType;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
 *
 * Open Realm of Stars game project
 * Copyright (C) 2016,2017,2020,2021 Tuomo Untinen
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
 * Single ship in combat
 *
 */

public class CombatShip implements Comparable<CombatShip> {

  /**
   * Ship information
   */
  private Ship ship;

  /**
   * Is component used or not for this round
   */
  private boolean[] componentUsed;

  /**
   * Ship's X coordinate in combat
   */
  private int x;

  /**
   * Ship's Y coordinate in combat
   */
  private int y;

  /**
   * Player whom owns the ship
   */
  private PlayerInfo player;

  /**
   * Flip Y axel on ship image
   */
  private boolean flipY;

  /**
   * How many moves left for this round
   */
  private int movesLeft;

  /**
   * Used only for AI to calculate how many times AI can shoot
   */
  private int aiShotsLeft;

  /**
   * Bonus accuracy from defending
   */
  private int bonusAccuracy;
  /**
   * Bonus from overloaded Jammer.
   */
  private int overloadedJammer;
  /**
   * Bonus from overloaded targeting computer
   */
  private int overloadedComputer;

  /**
   * Was ship damaged. Really damage not just some shield hit.
   */
  private boolean damaged;

  /**
   * Number of credits ship has privateered
   */
  private int privateeredCredits;


  /**
   * Fleet's commander.
   */
  private Leader commander;

  /**
   * Overload failure chance.
   */
  private int overloadFailure;

  /**
   * Ship's current energy level.
   */
  private int energyLevel;
  /**
   * Ship has been overloaded.
   */
  private boolean isOverloaded;
  /**
   * Cloaking device is overloaded.
   */
  private boolean cloakOverloaded;
  /**
   * Constructor for Combat ship
   * @param ship Ship to put in combat
   * @param player Player who owns the ship
   * @param x Ship's X coordinate in combat map
   * @param y Ship's Y coordinate in combat map
   * @param flip Ship's image on Y axel
   * @param commander Fleet's commander
   */
  public CombatShip(final Ship ship, final PlayerInfo player, final int x,
      final int y, final boolean flip, final Leader commander) {
    this.ship = ship;
    this.x = x;
    this.y = y;
    this.player = player;
    this.flipY = flip;
    this.commander = commander;
    this.movesLeft = ship.getTacticSpeed();
    this.setBonusAccuracy(0);
    this.setPrivateeredCredits(0);
    this.setOverloadFailure(0);
    this.setEnergyLevel(ship.getTotalEnergy());
    this.setOverloadedJammer(0);
    this.setCloakOverloaded(false);
    this.setOverloadedComputer(0);
    reInitShipForRound();
  }

  /**
   * Get ship component for use.
   * @param type Component Type for use.
   * @return component index or -1 if not available.
   */
  public int getComponentForUse(final ShipComponentType type) {
    for (int i = 0; i < ship.getNumberOfComponents(); i++) {
      ShipComponent component = ship.getComponent(i);
      if (component.getType() == type && ship.componentIsWorking(i)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Get Component for overloading movement.
   * @return component index or -1 if not available.
   */
  public int getOverloadMove() {
    int index = getComponentForUse(ShipComponentType.THRUSTERS);
    if (index == -1) {
      index = getComponentForUse(ShipComponentType.ENGINE);
    }
    if (index == -1) {
      index = getComponentForUse(ShipComponentType.SPACE_FIN);
    }
    return index;
  }

  /**
   * Get the actual ship
   * @return get Ship
   */
  public Ship getShip() {
    return ship;
  }

  /**
   * Get combat ship X coordinate in combat
   * @return X coordinate
   */
  public int getX() {
    return x;
  }

  /**
   * Set combat ship's X coordinate
   * @param x X coordinate
   */
  public void setX(final int x) {
    this.x = x;
  }

  /**
   * Get combat ship Y coordinate in combat
   * @return Y coordinate
   */
  public int getY() {
    return y;
  }

  /**
   * Set combat ship's Y coordinate
   * @param y Y coordinate
   */
  public void setY(final int y) {
    this.y = y;
  }

  /**
   * Get Player info for combat ship
   * @return Player info for combat ship
   */
  public PlayerInfo getPlayer() {
    return player;
  }

  /**
   * Set Player info for combat ship
   * @param player New player info for combat ship.
   */
  public void setPlayer(final PlayerInfo player) {
    this.player = player;
  }
  @Override
  public int compareTo(final CombatShip o) {
    int thisInit = this.ship.getInitiative();
    int otherInit = o.getShip().getInitiative();
    if (this.getCommander() != null
        && this.getCommander().hasPerk(Perk.COMBAT_TACTICIAN)) {
      thisInit++;
    }
    if (this.getCommander() != null
        && this.getCommander().hasPerk(Perk.AGGRESSIVE)) {
      thisInit++;
    }
    if (this.getCommander() != null
        && this.getCommander().hasPerk(Perk.PEACEFUL)) {
      thisInit--;
    }
    if (o.getCommander() != null
        && o.getCommander().hasPerk(Perk.COMBAT_TACTICIAN)) {
      otherInit++;
    }
    if (o.getCommander() != null
        && o.getCommander().hasPerk(Perk.AGGRESSIVE)) {
      otherInit++;
    }
    if (o.getCommander() != null
        && o.getCommander().hasPerk(Perk.PEACEFUL)) {
      otherInit--;
    }
    return thisInit - otherInit;
  }

  /**
   * Does ship image needs to be fliped on Y axel
   * @return True if image needs flipping on Y axel
   */
  public boolean isFlipY() {
    return flipY;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(ship.getName());
    sb.append(" - ");
    sb.append(ship.getHull().getName());
    sb.append("\n");
    sb.append("X: ");
    sb.append(x);
    sb.append(" Y: ");
    sb.append(y);
    sb.append("\n");
    sb.append("Initiative: ");
    sb.append(ship.getInitiative());
    sb.append("\n");
    sb.append("Moves: ");
    sb.append(movesLeft);
    sb.append("\n");
    sb.append("Hull points: ");
    sb.append(ship.getHullPoints());
    sb.append("/");
    sb.append(ship.getMaxHullPoints());
    sb.append("\n");
    if (ship.getTacticSpeed() == 0) {
      sb.append("Immobilized\n");
    }
    if (!ship.hasWeapons()) {
      sb.append("No weapons\n");
    }
    if (ship.getShield() == 0 && ship.getTotalShield() > 0) {
      sb.append("shields down\n");
    }
    if (ship.getShield() == 0 && ship.getTotalShield() == 0) {
      sb.append("No shields\n");
    }
    if (isCloaked()) {
      sb.append("Cloaked\n");
    }
    sb.append("\n");
    sb.append(getOverloadInformation());
    return sb.toString();
  }

  /**
   * Get overload information of the combat ship.
   * @return Overload information as string.
   */
  public String getOverloadInformation() {
    StringBuilder sb = new StringBuilder();
    sb.append("Energy reserves: ");
    if (getEnergyReserve() > 0) {
      sb.append("+");
    }
    sb.append(getEnergyReserve());
    sb.append("\n");
    sb.append("Overload failure: ");
    sb.append(getOverloadFailure());
    sb.append("/100\n");
    return sb.toString();
  }
  /**
   * Get Combat ship description
   * @return String for description
   */
  public String getDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(ship.getName());
    sb.append(" - ");
    sb.append(ship.getHull().getName());
    sb.append("\n");
    sb.append("\n");
    sb.append("Initiative: ");
    sb.append(ship.getInitiative());
    sb.append("\n");
    sb.append("Moves: ");
    sb.append(movesLeft);
    sb.append("\n");
    sb.append("Shield: ");
    sb.append(ship.getShield());
    sb.append("/");
    sb.append(ship.getTotalShield());
    sb.append(" Armor: ");
    sb.append(ship.getArmor());
    sb.append("/");
    sb.append(ship.getTotalArmor());
    sb.append("\n");
    sb.append("Hull points: ");
    sb.append(ship.getHullPoints());
    sb.append("/");
    sb.append(ship.getMaxHullPoints());
    sb.append("\n");
    if (ship.getTacticSpeed() == 0) {
      sb.append("Immobilized\n");
    }
    if (!ship.hasWeapons()) {
      sb.append("No weapons\n");
    }
    if (ship.getShield() == 0 && ship.getTotalShield() > 0) {
      sb.append("shields down\n");
    }
    if (ship.getShield() == 0 && ship.getTotalShield() == 0) {
      sb.append("No shields\n");
    }
    if (isCloaked()) {
      sb.append("Cloaked\n");
    }
    sb.append("\n");
    sb.append(getOverloadInformation());
    return sb.toString();
  }

  /**
   * How many moves left for this combat round
   * @return Moves left
   */
  public int getMovesLeft() {
    return movesLeft;
  }

  /**
   * Reinitialize ship for next round
   * @return CombatAnimation or null
   */
  public CombatAnimation reInitShipForRound() {
    int weapons = 0;
    setOverloadedJammer(0);
    setCloakOverloaded(false);
    setOverloadedComputer(0);
    if (getEnergyLevel() > ship.getTotalEnergy()) {
      setEnergyLevel(ship.getTotalEnergy());
    }
    if (getEnergyReserve() < 0) {
      int chance = 0;
      switch (getEnergyReserve()) {
        case -1: {
          chance = 10;
          break;
        }
        case -2: {
          chance = 20;
          break;
        }
        case -3: {
          chance = 40;
          break;
        }
        case -4: {
          chance = 80;
          break;
        }
        default: {
          chance = 100;
          break;
        }
      }
      int value = DiceGenerator.getRandom(99);
      if (value < chance) {
        setMovesLeft(0);
        componentUsed = new boolean[ship.getNumberOfComponents()];
        for (int i = 0; i < componentUsed.length; i++) {
          componentUsed[i] = true;
        }
        setAiShotsLeft(0);
        getShip().setShield(0);
        setEnergyLevel(getEnergyLevel() - getEnergyReserve());
        setOverloaded(true);
        // Ship is basically useless for this turn.
        CombatAnimation animation = new CombatAnimation(this, this,
            CombatAnimationType.LIGHTNING, 1);
        return animation;
      }
    }
    if (getEnergyLevel() < ship.getTotalEnergy() && !hasOverloaded()) {
      setEnergyLevel(getEnergyLevel() + 1);
    }
    setOverloaded(false);
    if (ship.isStarBase() && ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
      setMovesLeft(0);
    } else {
      setMovesLeft(ship.getTacticSpeed());
    }
    componentUsed = new boolean[ship.getNumberOfComponents()];
    for (int i = 0; i < componentUsed.length; i++) {
      componentUsed[i] = false;
      ShipComponent comp = ship.getComponent(i);
      if (comp.isWeapon() && ship.componentIsWorking(i)) {
        weapons++;
      }
    }
    if (ship.isStarBase() && !ship.getFlag(Ship.FLAG_STARBASE_DEPLOYED)) {
      setAiShotsLeft(0);
    } else {
      setAiShotsLeft(weapons);
    }
    ship.regenerateShield();
    damaged = false;
    return null;
  }

  /**
   * Is certain component used or not yet during this round
   * @param index Component index
   * @return true if component has been used
   */
  public boolean isComponentUsed(final int index) {
    if (index >= 0 && index < componentUsed.length) {
      return componentUsed[index];
    }
    return true;
  }

  /**
   * Use certain component for this round
   * @param index Component index
   */
  public void useComponent(final int index) {
    if (index >= 0 && index < componentUsed.length) {
      componentUsed[index] = true;
    }
  }

  /**
   * Is overload failure. This should be checked before making overload.
   * This will automatically increase/decrease overload failure if needed.
   * @param index Ship Component index
   * @return True if overload failure happened
   */
  public boolean isOverloadFailure(final int index) {
    int value = DiceGenerator.getRandom(99);
    if (value < getOverloadFailure()) {
      getShip().oneDamage(index);
      setOverloadFailure(getOverloadFailure() / 2);
      return true;
    }
    int amount = 5;
    if (commander != null && commander.hasPerk(Perk.MASTER_ENGINEER)) {
      amount = amount - 2;
    }
    if (getPlayer().getTechList().hasTech(
        TechType.Electrics, "Improved engineer")) {
      amount = amount - 2;
    }
    setOverloadFailure(getOverloadFailure() + amount);
    return false;
  }
  /**
   * Define how many moves combat ship has left for this round
   * @param movesLeft For this round
   */
  public void setMovesLeft(final int movesLeft) {
    this.movesLeft = movesLeft;
  }

  /**
   * Has AI shot with every weapon?
   * @return How many shots is left for AI for current round
   */
  public int getAiShotsLeft() {
    return aiShotsLeft;
  }

  /**
   * Change AI shots left for combat ship.
   * @param aiShotsLeft AI shots left for current round
   */
  public void setAiShotsLeft(final int aiShotsLeft) {
    this.aiShotsLeft = aiShotsLeft;
  }

  /**
   * Get bonus accuracy for combat ship. This bonus is granted if ship is
   * defending.
   * @return Bonus accuracy
   */
  public int getBonusAccuracy() {
    return bonusAccuracy;
  }

  /**
   * Define bonus accuracy for combat ship.
   * @param bonusAccuracy for combat ship
   */
  public void setBonusAccuracy(final int bonusAccuracy) {
    this.bonusAccuracy = bonusAccuracy;
  }

  /**
   * Was ship damaged during last round
   * @return the damaged
   */
  public boolean isDamaged() {
    return damaged;
  }

  /**
   * Ship is damaged and being marked
   */
  public void setDamaged() {
    damaged = true;
  }

  /**
   * Get how many credits ship has stole in this combat.
   * @return the privateeredCredits
   */
  public int getPrivateeredCredits() {
    return privateeredCredits;
  }

  /**
   * Set how many credits ship has stole in this combat.
   * @param privateeredCredits the privateeredCredits to set
   */
  public void setPrivateeredCredits(final int privateeredCredits) {
    this.privateeredCredits = privateeredCredits;
  }

  /**
   * Get fleet's commander
   * @return Leader
   */
  public Leader getCommander() {
    return commander;
  }

  /**
   * Set Fleet's commander
   * @param commander New commande for fleet.
   */
  public void setCommander(final Leader commander) {
    this.commander = commander;
  }
  /**
   * Is ship cloaked or not.
   * @return true if cloaked.
   */
  public boolean isCloaked() {
    for (int i = 0; i < getShip().getNumberOfComponents(); i++) {
      ShipComponent component = getShip().getComponent(i);
      if (getShip().hasComponentEnergy(i) && component.getCloaking() > 0
          && getShip().getHullPointForComponent(i) > 0) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is cloaking device overloaded?
   * @return the cloakOverloaded
   */
  public boolean isCloakOverloaded() {
    return cloakOverloaded;
  }

  /**
   * Set flag value if cloaking device is overloaded.
   * @param cloakOverloaded the cloakOverloaded to set
   */
  public void setCloakOverloaded(final boolean cloakOverloaded) {
    this.cloakOverloaded = cloakOverloaded;
  }

  /**
   * Get Last available cloacking device index number.
   * @return Index number or -1 if not available.
   */
  public int getCloakingDeviceIndex() {
    int index = -1;
    for (int i = 0; i < getShip().getNumberOfComponents(); i++) {
      ShipComponent component = getShip().getComponent(i);
      if (getShip().hasComponentEnergy(i) && component.getCloaking() > 0
          && !componentUsed[i]) {
        index = i;
      }
    }
    return index;
  }

  /**
   * Get overload failure chance.
   * Failure chance is between 0-100.
   * @return the overloadFailure
   */
  public int getOverloadFailure() {
    return overloadFailure;
  }

  /**
   * Set overload failure chance;
   * @param overloadFailure the overloadFailure to set
   */
  public void setOverloadFailure(final int overloadFailure) {
    this.overloadFailure = overloadFailure;
    if (this.overloadFailure > 100) {
      this.overloadFailure = 100;
    }
    if (this.overloadFailure < 0) {
      this.overloadFailure = 0;
    }
  }

  /**
   * Get ship's current energy level.
   * @return the energyLevel
   */
  public int getEnergyLevel() {
    return energyLevel;
  }

  /**
   * @param energyLevel the energyLevel to set
   */
  public void setEnergyLevel(final int energyLevel) {
    this.energyLevel = energyLevel;
  }

  /**
   * Calculates current energy reserve.
   * There is energy just enough if this is zero.
   * Negative means that there isn't enough, energy overload can happen.
   * Positive means that there is surplus energy left. Overloading components
   * is possible.
   * @return Energy reserve
   */
  public int getEnergyReserve() {
    return energyLevel - ship.getEnergyConsumption();
  }

  /**
   * Get Overloaded jammer defense.
   * @return the overloadedJammer
   */
  public int getOverloadedJammer() {
    return overloadedJammer;
  }

  /**
   * Set Overloaded jammer defense.
   * @param overloadedJammer the overloadedJammer to set
   */
  public void setOverloadedJammer(final int overloadedJammer) {
    this.overloadedJammer = overloadedJammer;
  }

  /**
   * Has ship overloaded during this turn?
   * @return the hasOverloaded
   */
  public boolean hasOverloaded() {
    return isOverloaded;
  }

  /**
   * Set overload flag
   * @param hasOverloaded the hasOverloaded to set
   */
  public void setOverloaded(final boolean hasOverloaded) {
    this.isOverloaded = hasOverloaded;
  }

  /**
   * Get overloaded targeting computer bonus.
   * @return the overloadedComputer
   */
  public int getOverloadedComputer() {
    return overloadedComputer;
  }

  /**
   * Set overloaded targeting computer bonus.
   * @param overloadedComputer the overloadedComputer to set
   */
  public void setOverloadedComputer(final int overloadedComputer) {
    this.overloadedComputer = overloadedComputer;
  }
}
