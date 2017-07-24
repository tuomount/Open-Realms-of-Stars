package org.openRealmOfStars.starMap.newsCorp;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017  Tuomo Untinen
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
* News Factory to create news data about special events in game.
*
*/
public final class NewsFactory {

  /**
   * Just hiding the constructor
   */
  private NewsFactory() {
    // Nothing to do here
  }

  /**
   * Make War news. Aggressor makes war declaration to defender.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param aggressor Player who is declaring the war
   * @param defender Player who is defending
   * @param meetingPlace Where meeting happened, fleet or planet
   * @return NewsData
   */
  public static NewsData makeWarNews(final PlayerInfo aggressor,
      final PlayerInfo defender, final Object meetingPlace) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      instructions.addPlanet(ImageInstruction.POSITION_CENTER,
          Planet.PLANET_NEWS_INSTRUCTIONS[planet.getPlanetImageIndex()],
          ImageInstruction.SIZE_FULL);
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("WAR DECLARATION!");
        break;
      }
      case 1: {
        instructions.addText("WAR!");
        break;
      }
      case 2: {
        instructions.addText("WAR BEGINS!");
        break;
      }
    }
    instructions.addText(aggressor.getEmpireName());
    instructions.addRelationSymbol(ImageInstruction.WAR);
    instructions.addText(defender.getEmpireName());
    news.setImageInstructions(instructions.build());
    return news;
  }
}
