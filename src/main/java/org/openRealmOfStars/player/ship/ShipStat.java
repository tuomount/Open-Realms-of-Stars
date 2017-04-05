package org.openRealmOfStars.player.ship;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
 * Ship Stat class is always connect to certain ship design. Class contains
 * statistical information about the certain ship
 *
 */

public class ShipStat implements Comparable<ShipStat> {

  /**
   * Ship design to to keep stats
   */
  private ShipDesign design;

  /**
   * In how many combats ship has taken a part
   */
  private int numberOfCombats;

  /**
   * Number of victories ship
   */
  private int numberOfVictories;

  /**
   * Number of lost ships
   */
  private int numberOfLoses;

  /**
   * Number of kills aka last hit
   */
  private int numberOfKills;

  /**
   * Number of built
   */
  private int numberOfBuilt;

  /**
   * Number of ships in use
   */
  private int numberOfInUse;

  /**
   * Is ship obsolete and not showing in building list
   */
  private boolean obsolete;

  /**
   * Constructor
   * @param design Ship Design
   */
  public ShipStat(final ShipDesign design) {
    this.design = design;
    numberOfCombats = 0;
    numberOfVictories = 0;
    numberOfLoses = 0;
    numberOfKills = 0;
    numberOfBuilt = 0;
    numberOfInUse = 0;
    setObsolete(false);
  }

  /**
   * Read ShipStat from DataInputStream
   * @param dis Data Input Stream
   * @throws IOException if there is any problem with DataInputStream
   */
  public ShipStat(final DataInputStream dis) throws IOException {
    this.design = new ShipDesign(dis);
    numberOfCombats = dis.readInt();
    numberOfVictories = dis.readInt();
    numberOfLoses = dis.readInt();
    numberOfKills = dis.readInt();
    numberOfBuilt = dis.readInt();
    numberOfInUse = dis.readInt();
    obsolete = dis.readBoolean();
  }

  /**
   * Save Ship Stat to Data Output Stream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveShipStat(final DataOutputStream dos) throws IOException {
    design.saveShipDesign(dos);
    dos.writeInt(numberOfCombats);
    dos.writeInt(numberOfVictories);
    dos.writeInt(numberOfLoses);
    dos.writeInt(numberOfKills);
    dos.writeInt(numberOfBuilt);
    dos.writeInt(numberOfInUse);
    dos.writeBoolean(obsolete);
  }

  /**
   * Get number of combat ship has been
   * @return Number of combats
   */
  public int getNumberOfCombats() {
    return numberOfCombats;
  }

  /**
   * Change number of combat ship design has been.
   * @param numberOfCombats Number of combat ship design has participated.
   */
  public void setNumberOfCombats(final int numberOfCombats) {
    this.numberOfCombats = numberOfCombats;
  }

  /**
   * How many of combats were victorious.
   * @return Number of victories
   */
  public int getNumberOfVictories() {
    return numberOfVictories;
  }

  /**
   * Change number of victories for ship design
   * @param numberOfVictories Number of victories
   */
  public void setNumberOfVictories(final int numberOfVictories) {
    this.numberOfVictories = numberOfVictories;
  }

  /**
   * How many combat were lost by this ship design
   * @return Number of loses
   */
  public int getNumberOfLoses() {
    return numberOfLoses;
  }

  /**
   * Change number of loses for ship design
   * @param numberOfLoses Number of loses
   */
  public void setNumberOfLoses(final int numberOfLoses) {
    this.numberOfLoses = numberOfLoses;
  }

  /**
   * How many kills ship design has caused in combat. Killing blow
   * is what counts.
   * @return Number of kills
   */
  public int getNumberOfKills() {
    return numberOfKills;
  }

  /**
   * Change number of kills by ship design
   * @param numberOfKills Number of kills
   */
  public void setNumberOfKills(final int numberOfKills) {
    this.numberOfKills = numberOfKills;
  }

  /**
   * How many of these ships is totally built
   * @return Number of built
   */
  public int getNumberOfBuilt() {
    return numberOfBuilt;
  }

  /**
   * Change the number of ships built
   * @param numberOfBuilt Number of ships built
   */
  public void setNumberOfBuilt(final int numberOfBuilt) {
    this.numberOfBuilt = numberOfBuilt;
  }

  /**
   * How many ships are still in use
   * @return Number of in use
   */
  public int getNumberOfInUse() {
    return numberOfInUse;
  }

  /**
   * Change number of ship in use
   * @param numberOfInUse Number of ships in use
   */
  public void setNumberOfInUse(final int numberOfInUse) {
    this.numberOfInUse = numberOfInUse;
  }

  /**
   * Get actual ship design for this ship stat
   * @return Ship design
   */
  public ShipDesign getDesign() {
    return design;
  }

  @Override
  public String toString() {
    return design.toString() + "\n\nStats:\nShips built: " + getNumberOfBuilt()
        + " Ships in use: " + getNumberOfInUse() + "\nCombats: "
        + getNumberOfCombats() + " Victories: " + getNumberOfVictories()
        + " Lost: " + getNumberOfLoses() + " Kills: " + getNumberOfKills();
  }

  /**
   * Is ship design obsolete or not. Obsolete ship means
   * that is is no longer visible in building list on planets.
   * @return True if obsolete
   */
  public boolean isObsolete() {
    return obsolete;
  }

  /**
   * Change if ship design is obsolete or not
   * @param obsolete True for obsolete
   */
  public void setObsolete(final boolean obsolete) {
    this.obsolete = obsolete;
  }

  @Override
  public int compareTo(final ShipStat arg0) {
    if (arg0.isObsolete() == this.obsolete) {
      return design.getName().compareTo(arg0.getDesign().getName());
    }
    if (this.obsolete) {
      return 1;
    }
    return -1;
  }

}
