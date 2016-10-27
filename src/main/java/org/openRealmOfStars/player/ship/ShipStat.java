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

public class ShipStat implements Comparable<ShipStat>{

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
  public ShipStat(ShipDesign design) {
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
  public ShipStat(DataInputStream dis) throws IOException {
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
  public void saveShipStat(DataOutputStream dos) throws IOException {
    design.saveShipDesign(dos);
    dos.writeInt(numberOfCombats);
    dos.writeInt(numberOfVictories);
    dos.writeInt(numberOfLoses);
    dos.writeInt(numberOfKills);
    dos.writeInt(numberOfBuilt);
    dos.writeInt(numberOfInUse);
    dos.writeBoolean(obsolete);
  }

  public int getNumberOfCombats() {
    return numberOfCombats;
  }

  public void setNumberOfCombats(int numberOfCombats) {
    this.numberOfCombats = numberOfCombats;
  }

  public int getNumberOfVictories() {
    return numberOfVictories;
  }

  public void setNumberOfVictories(int numberOfVictories) {
    this.numberOfVictories = numberOfVictories;
  }

  public int getNumberOfLoses() {
    return numberOfLoses;
  }

  public void setNumberOfLoses(int numberOfLoses) {
    this.numberOfLoses = numberOfLoses;
  }

  public int getNumberOfKills() {
    return numberOfKills;
  }

  public void setNumberOfKills(int numberOfKills) {
    this.numberOfKills = numberOfKills;
  }

  public int getNumberOfBuilt() {
    return numberOfBuilt;
  }

  public void setNumberOfBuilt(int numberOfBuilt) {
    this.numberOfBuilt = numberOfBuilt;
  }

  public int getNumberOfInUse() {
    return numberOfInUse;
  }

  public void setNumberOfInUse(int numberOfInUse) {
    this.numberOfInUse = numberOfInUse;
  }

  public ShipDesign getDesign() {
    return design;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(design.toString());
    sb.append("\n\nStats:\n");
    sb.append("Ships built: ");
    sb.append(getNumberOfBuilt());
    sb.append(" Ships in use: ");
    sb.append(getNumberOfInUse());
    sb.append("\n");
    sb.append("Combats: ");
    sb.append(getNumberOfCombats());
    sb.append(" Victories: ");
    sb.append(getNumberOfVictories());
    sb.append(" Lost: ");
    sb.append(getNumberOfLoses());
    sb.append(" Kills: ");
    sb.append(getNumberOfKills());
    return sb.toString();
  }

  public boolean isObsolete() {
    return obsolete;
  }

  public void setObsolete(boolean obsolete) {
    this.obsolete = obsolete;
  }

  @Override
  public int compareTo(ShipStat arg0) {
    if (arg0.isObsolete() == this.obsolete) {
      return design.getName().compareTo(arg0.getDesign().getName());
    }
    if (this.obsolete) {
      return 1;
    }
    return -1;
  }
  
  
  
}
