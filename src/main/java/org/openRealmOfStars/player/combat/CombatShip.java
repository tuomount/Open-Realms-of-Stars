package org.openRealmOfStars.player.combat;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipComponent;

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
    reInitShipForRound();
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

  @Override
  public int compareTo(final CombatShip o) {
    int thisInit = this.ship.getInitiative();
    int otherInit = o.getShip().getInitiative();
    if (this.getCommander() != null
        && this.getCommander().hasPerk(Perk.COMBAT_TACTICIAN)) {
      thisInit++;
    }
    if (o.getCommander() != null
        && o.getCommander().hasPerk(Perk.COMBAT_TACTICIAN)) {
      otherInit++;
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
   */
  public void reInitShipForRound() {
    int weapons = 0;
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
}
