package org.openRealmOfStars.utilities.namegenerators;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018 Tuomo Untinen
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
* Random name Generator for artificial planet
*
*/
public class UnrealPlanetNameGenerator extends NameGenerator {

  /**
   * Constructor for artificial planet name generator
   */
  public UnrealPlanetNameGenerator() {
    setUsedNames(new ArrayList<String>());
  }
  @Override
  protected String generateRandomName() {
    StringBuilder sb = new StringBuilder();
    switch (DiceGenerator.getRandom(4)) {
      case 0: {
        sb.append("Metal "); break;
      }
      case 1: {
        sb.append("Artificial "); break;
      }
      case 2: {
        sb.append("Black "); break;
      }
      case 3: {
        sb.append("Gray "); break;
      }
      case 4: {
        sb.append("Shiny "); break;
      }
      default: {
        sb.append("Metal "); break;
      }
    }
    switch (DiceGenerator.getRandom(4)) {
      case 0: {
        sb.append("sphere "); break;
      }
      case 1: {
        sb.append("planet "); break;
      }
      case 2: {
        sb.append("orbit "); break;
      }
      case 3: {
        sb.append("domain "); break;
      }
      case 4: {
        sb.append("realm "); break;
      }
      default: {
        sb.append("sphere "); break;
      }
    }
    return sb.toString();
  }

}
