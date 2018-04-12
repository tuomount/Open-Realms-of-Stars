package org.openRealmOfStars.utilities.namegenerators;

import java.util.ArrayList;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018  Tuomo Untinen
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
* Abstract Random Name Generator
*
*/
public abstract class NameGenerator {

  /**
   * Names already used.
   */
  private ArrayList<String> usedNames;

  /**
   * Get used name list
   * @return Used name in array list
   */
  public ArrayList<String> getUsedNames() {
    return usedNames;
  }

  /**
   * Set Used name list.
   * @param usedNames Used name list as array list
   */
  public void setUsedNames(final ArrayList<String> usedNames) {
    this.usedNames = usedNames;
  }

  /**
   * Check if system name has previously generated.
   * @param name Name to check
   * @return True if name is already taken, otherwise false
   */
  protected boolean previusMatch(final String name) {
    for (String oldName : usedNames) {
      if (oldName.equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Generate name which is unique inside same generator.
   * @return randomized name
   */
  public String generate() {
    String name;
    do {
      name = generateRandomName();
    } while (previusMatch(name));
    usedNames.add(name);
    return name;
  }

  /**
   * Generate random name. This does not check that name is unique.
   * @return Random name
   */
  protected abstract String generateRandomName();
}
