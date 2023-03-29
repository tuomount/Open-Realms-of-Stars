package org.openRealmOfStars.player;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.WorldType;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2023 Tuomo Untinen
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
* Background story generator. This generates background which
* has happened before actual game or even elder start.
*
*/
public final class BackgroundStoryGenerator {

  /**
   * Hidden constructor
   */
  private BackgroundStoryGenerator() {
    // Hiding the constructor.
  }

  /**
   * Generate background story.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  public String generateBackgroundStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    if (info.getRace() == SpaceRace.HUMAN) {
      sb.append(generateHumanStory(info, startPlanet, startingYear));
    }
    return sb.toString();
  }

  /**
   * Generate background story for humans.
   * @param info Realm from which to generate
   * @param startPlanet Starting planet
   * @param startingYear Star year when realm is starting to explore galaxy.
   * @return Background story as a string.
   */
  private static String generateHumanStory(final PlayerInfo info,
      final Planet startPlanet, final int startingYear) {
    StringBuilder sb = new StringBuilder();
    String name = "Humans";
    if (info.getEmpireName().contains("Terran")) {
      name = "Terrans";
    }
    if (startPlanet.getName().startsWith("Earth")) {
      sb.append(name);
      sb.append(" are the humans from the Earth.");
      sb.append(name);
      sb.append(" are seen as "
          + "average in most physical and mental abilities. ");
    } else {
      sb.append(name);
      sb.append(" are an extraterrestrial beings that "
          + "are similar to humans on Earth. Like their Earthly counterparts,");
      sb.append(name);
      sb.append(" are often portrayed as being average in most"
          + " physical and mental abilities. ");
    }
    sb.append(" ");
    sb.append(name);
    sb.append(" were born billions of star years ago on ");
    if (startPlanet.getPlanetType().getWorldType() == WorldType.CARBONWORLD) {
      sb.append(" carbon rich planet with full of life.");
      sb.append(" This was excellent planet for live on.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.DESERTWORLD) {
      sb.append(" dry desert world. This harsh envinronment was");
      sb.append(" challenging but ");
      sb.append(name);
      sb.append(" became stronger and more sustainable there.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.ICEWORLD) {
      sb.append(" ice world. This cold and dark envinronment was");
      sb.append(" challenging but ");
      sb.append(name);
      sb.append(" became extreme survalists there.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.IRONWORLD) {
      sb.append(" iron world. This planet was full of molten lava");
      sb.append(" and hot envinronments ");
      sb.append(name);
      sb.append(" were able to survive there and learn how fluorish there.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.SILICONWORLD) {
      sb.append(" silicon world. This almost barren planet is one the");
      sb.append(" harshest envinronments in the galaxy. ");
      sb.append(name);
      sb.append(" still call this place as home.");
    }
    if (startPlanet.getPlanetType().getWorldType() == WorldType.WATERWORLD) {
      sb.append(" water world. This planet is moist and has huge oceans");
      sb.append(" on surface. ");
      sb.append(name);
      sb.append(" conquered the planet fully for themselves.");
    }
    sb.append(" ");
    sb.append(name);
    sb.append(" government form is ");
    sb.append(info.getGovernment().getName());
    sb.append(".");
    sb.append("Group of scientiest were able to discover faster than light"
        + " travel and thus first prototype of space craft was created at"
        + " star year");
    sb.append(startingYear - 10);
    sb.append(". First flights were magnificent success and then first armed "
        + "scout and colony ship was create at star year");
    sb.append(startingYear);
    sb.append(".");
    sb.append(info.getEmpireName());
    sb.append(" starts space exploration from ");
    sb.append(startPlanet.getName());
    sb.append(".");
    return sb.toString();
  }

}
