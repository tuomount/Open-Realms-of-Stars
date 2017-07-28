package org.openRealmOfStars.starMap.newsCorp;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.starMap.StarMap;
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
   * @param map StarMap of current game
   * @return NewsData
   */
  public static NewsData makeWarNews(final PlayerInfo aggressor,
      final PlayerInfo defender, final Object meetingPlace,
      final StarMap map) {
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
    StringBuilder sb = new StringBuilder(100);
    sb.append(aggressor.getEmpireName());
    sb.append(" declares war against ");
    sb.append(defender.getEmpireName());
    sb.append("! ");
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      sb.append("This meeting happened in ");
      sb.append(planet.getName());
      if (planet.getPlanetPlayerInfo() != null) {
        sb.append(", which is owned by ");
        sb.append(planet.getPlanetPlayerInfo().getEmpireName());
        sb.append(". ");
      }
    } else {
      sb.append("This meeting happened in deep space. ");
    }
    if (aggressor.getAiAttitude() == Attitude.AGGRESSIVE) {
      sb.append(aggressor.getEmpireName());
      sb.append(" is known about their aggressive behaviour, so ");
      sb.append("this war was just about to happen. ");
    }
    if (aggressor.getAiAttitude() == Attitude.MILITARISTIC) {
      sb.append(aggressor.getEmpireName());
      sb.append(" militaristic actions has lead to this war to burst out. ");
    }
    if (aggressor.getAiAttitude() == Attitude.PEACEFUL) {
      sb.append(aggressor.getEmpireName());
      sb.append(" is known about their peace loving. What horrible acts has ");
      sb.append(defender.getEmpireName());
      sb.append(" done to ");
      sb.append(aggressor.getEmpireName());
      sb.append("? ");
    }
    if (map != null) {
      int defenderIndex = map.getPlayerList().getIndex(defender);
      if (aggressor.getDiplomacy().isMultipleBorderCrossing(defenderIndex)) {
        sb.append("Maybe the reason for war is in multiple"
            + " border crossing by ");
        sb.append(defender.getEmpireName());
        sb.append(" has done lately. ");
      }
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make Peace news. PeaceMaker makes peace offer to acceptor.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param peaceMaker Player who is declaring the war
   * @param acceptor Player who is defending
   * @param meetingPlace Where meeting happened, fleet or planet
   * @return NewsData
   */
  public static NewsData makePeaceNews(final PlayerInfo peaceMaker,
      final PlayerInfo acceptor, final Object meetingPlace) {
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
        instructions.addText("PEACE OFFER ACCEPTED!");
        break;
      }
      case 1: {
        instructions.addText("PEACE!");
        break;
      }
      case 2: {
        instructions.addText("PEACE BEGINS!");
        break;
      }
    }
    instructions.addText(peaceMaker.getEmpireName());
    instructions.addRelationSymbol(ImageInstruction.PEACE);
    instructions.addText(acceptor.getEmpireName());
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(peaceMaker.getEmpireName());
    sb.append(" make peace with ");
    sb.append(acceptor.getEmpireName());
    sb.append("! ");
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      sb.append("This meeting happened in ");
      sb.append(planet.getName());
      if (planet.getPlanetPlayerInfo() != null) {
        sb.append(", which is owned by ");
        sb.append(planet.getPlanetPlayerInfo().getEmpireName());
        sb.append(". ");
      }
    } else {
      sb.append("This meeting happened in deep space. ");
    }
    if (peaceMaker.getAiAttitude() == Attitude.AGGRESSIVE) {
      sb.append(peaceMaker.getEmpireName());
      sb.append(" is known about their aggressive behaviour, so ");
      sb.append("this peace off was a bit unexpected! ");
      sb.append("What has ");
      sb.append(acceptor.getEmpireName());
      sb.append(" offered to ");
      sb.append(peaceMaker.getEmpireName());
      sb.append("? ");
    }
    if (peaceMaker.getAiAttitude() == Attitude.DIPLOMATIC) {
      sb.append(peaceMaker.getEmpireName());
      sb.append(" diplomatic skills were surely effecting on this peace! ");
    }
    if (peaceMaker.getAiAttitude() == Attitude.PEACEFUL) {
      sb.append(peaceMaker.getEmpireName());
      sb.append(" is known about their peace loving. So this was expected! ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

}
