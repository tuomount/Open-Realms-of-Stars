package org.openRealmOfStars.player.fleet;

import java.util.ArrayList;

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
 * Fleet for handling list of ships
 * 
 */

public class FleetList {

  /**
   * Player's fleets
   */
  private ArrayList<Fleet> fleetList;

  /**
   * Current index
   */
  private int index; 
  
  public FleetList() {
    fleetList = new ArrayList<>();
    index = -1;
  }
  
  /**
   * Add new fleet to list
   * @param fleet Fleet to add to the list
   */
  public void add(Fleet fleet) {
    int fleetNum = -1;
    for (Fleet ite: fleetList) {
      // Gets the next fleet number
      if (ite.getName().startsWith("Fleet #")) {
        String fleetStrNum = ite.getName().substring(7);
        try {
          int num = Integer.valueOf(fleetStrNum);
          if (num > fleetNum) {
            fleetNum = num;
          }
        } catch (NumberFormatException e) {
          // Just don't do anything
        }
      }
    }
    fleetNum = fleetNum+1;
    fleetList.add(fleet);
    fleet.setName("Fleet #"+fleetNum);
  }
  
  /**
   * Remove fleet from the list by index
   * @param index to remove
   */
  public void remove(int index) {
    fleetList.remove(index);
  }
  
  /**
   * Get next fleet from the list
   * @return Fleet or null if list contains no fleets
   */
  public Fleet getNext() {
    if (fleetList.size() > 0) {
      index++;
      if (index >= fleetList.size()) {
        index = 0;
      }
      return fleetList.get(index);
    }
    return null;
  }
  
  /**
   * Get previous fleet from the list
   * @return Fleet or null if list contains no fleets
   */
  public Fleet getPrev() {
    if (fleetList.size() > 0) {
      index--;
      if (index < 0) {
        index = fleetList.size()-1;
      }
      return fleetList.get(index);
    }
    return null;
  }
  
  /**
   * Get the number of fleets
   * @return number of fleets
   */
  public int getNumberOfFleets() {
    return fleetList.size();
  }

  /**
   * Get current fleet from the list
   * @return Fleet or null if list contains no fleets
   */
  public Fleet getCurrent() {
    if (fleetList.size() > 0) {
      return fleetList.get(index);
    }
    return null;
  }

  /**
   * Get by index fleet from the list
   * @param index
   * @return Fleet or null if list contains no fleets
   */
  public Fleet getByIndex(int i) {
    if (fleetList.size() > 0 && i >= 0 && i <fleetList.size()) {
      return fleetList.get(i);
    }
    return null;
  }

  /**
   * Get by fleet name fleet from the list
   * @param name
   * @return Fleet or null if list contains no fleets
   */
  public Fleet getByName(String name) {
    if (fleetList.size() > 0) {
      for (Fleet fleet : fleetList) {
        if (fleet.getName().equals(name)) {
          return fleet;
        }
      }
    }
    return null;
  }

  /**
   * Get by fleet name fleet index number
   * @param name
   * @return index number or -1 if not found
   */
  public int getIndexByName(String name) {
    if (fleetList.size() > 0) {
      for (int i=0;i < fleetList.size();i++) {
        Fleet fleet = fleetList.get(i);
        if (fleet.getName().equals(name)) {
          return i;
        }
      }
    }
    return -1;
  }

  public void recalculateList() {
    for (int i=0;i<fleetList.size();i++) {
      Fleet fleet = fleetList.get(i);
      if (fleet.getNumberOfShip()==0) {
        fleetList.remove(i);
        break;
      }
    }
  }
}
