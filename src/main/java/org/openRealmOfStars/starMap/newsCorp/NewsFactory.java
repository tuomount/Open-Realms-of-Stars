package org.openRealmOfStars.starMap.newsCorp;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.history.event.DiplomaticEvent;
import org.openRealmOfStars.starMap.history.event.Event;
import org.openRealmOfStars.starMap.newsCorp.scoreBoard.Row;
import org.openRealmOfStars.starMap.newsCorp.scoreBoard.ScoreBoard;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.TextUtilities;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2017,2018  Tuomo Untinen
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
   * can be planet or fleet. Attack is consider surprise attack if
   * there is no war between two parties when makeWarNews is called.
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
          planet.getImageInstructions(),
          ImageInstruction.SIZE_FULL);
    }
    boolean surpriseAttack = false;
    if (map != null) {
      int defenderIndex = map.getPlayerList().getIndex(defender);
      if (!aggressor.getDiplomacy().isWar(defenderIndex)) {
        surpriseAttack = true;
      }
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        if (surpriseAttack) {
          instructions.addText("SUPRPISE ATTACK!");
        } else {
          instructions.addText("WAR DECLARATION!");
        }
        break;
      }
      case 1: {
        if (surpriseAttack) {
          instructions.addText("MILITARY ACTIONS!");
        } else {
          instructions.addText("WAR!");
        }
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
      if (surpriseAttack) {
        sb.append("This attack happened in ");
      } else {
        sb.append("This meeting happened in ");
      }
      sb.append(planet.getName());
      if (planet.getPlanetPlayerInfo() != null) {
        sb.append(", which is owned by ");
        sb.append(planet.getPlanetPlayerInfo().getEmpireName());
        sb.append(". ");
      }
    } else {
      if (surpriseAttack) {
        sb.append("This attack happened in deep space. ");
      } else {
        sb.append("This meeting happened in deep space. ");
      }
    }
    Attitude attitude = aggressor.getAiAttitude();
    if (attitude == Attitude.AGGRESSIVE) {
      sb.append(aggressor.getEmpireName());
      sb.append(" is known about their aggressive behaviour, so ");
      sb.append("this war was just about to happen. ");
    }
    if (attitude == Attitude.MILITARISTIC) {
      sb.append(aggressor.getEmpireName());
      sb.append(" militaristic actions has lead to this war to burst out. ");
    }
    if (attitude == Attitude.PEACEFUL) {
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
   * Make Diplomatic event based on newsdata.
   * @param meetingPlace Planet or fleet or null
   * @param newsData NewsData where to get the actual text
   * @return Event
   */
  public static Event makeDiplomaticEvent(final Object meetingPlace,
      final NewsData newsData) {
    Coordinate coord = null;
    String planetName = null;
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      coord = planet.getCoordinate();
      planetName = planet.getName();
    } else if (meetingPlace instanceof Fleet) {
      Fleet fleet = (Fleet) meetingPlace;
      coord = fleet.getCoordinate();
    } else {
      coord = new Coordinate(0, 0);
    }
    DiplomaticEvent event = new DiplomaticEvent(coord);
    event.setPlanetName(planetName);
    event.setText(newsData.getNewsText());
    return event;
  }

  /**
   * Make Defensive pact activation news. This news should be called
   * if some one attacks against some who is in defensive pact.
   * This activate list of members of that defensive pact.
   * @param aggressor Player who is declaring the war
   * @param defensiveMembers list of empire as member in defensive pact
   * @return NewsData
   */
  public static NewsData makeDefensiveActivation(final PlayerInfo aggressor,
      final String[] defensiveMembers) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("WAR OVER REALMS!");
        break;
      }
      case 1: {
        instructions.addText("WAR AGAINST PEACE!");
        break;
      }
      case 2: {
        instructions.addText("WAR STARTS!");
        break;
      }
    }
    instructions.addText(aggressor.getEmpireName());
    instructions.addRelationSymbol(ImageInstruction.WAR);
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(aggressor.getEmpireName());
    sb.append(" declares war against defensive group whose members are: ");
    for (int i = 0; i < defensiveMembers.length; i++) {
      sb.append(defensiveMembers[i]);
      if (i + 1 < defensiveMembers.length) {
        sb.append(", ");
      }
    }
    sb.append("! ");
    Attitude attitude = aggressor.getAiAttitude();
    if (attitude == Attitude.AGGRESSIVE) {
      sb.append(aggressor.getEmpireName());
      sb.append(" is known about their aggressive behaviour, so ");
      sb.append("this war was just about to happen, even against ");
      sb.append("bigger group!");
    }
    if (attitude == Attitude.MILITARISTIC) {
      sb.append(aggressor.getEmpireName());
      sb.append(" militaristic actions has lead to this war to burst out. ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make Peace news. PeaceMaker makes peace offer to acceptor.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param peaceMaker Player who is make the peace offer
   * @param acceptor Player who is accepting
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
          planet.getImageInstructions(),
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
    sb.append(" made peace with ");
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
    Attitude attitude = peaceMaker.getAiAttitude();
    if (attitude == Attitude.AGGRESSIVE) {
      sb.append(peaceMaker.getEmpireName());
      sb.append(" is known about their aggressive behaviour, so ");
      sb.append("this peace offer was a bit unexpected! ");
      sb.append("What has ");
      sb.append(acceptor.getEmpireName());
      sb.append(" offered to ");
      sb.append(peaceMaker.getEmpireName());
      sb.append("? ");
    }
    if (attitude == Attitude.DIPLOMATIC) {
      sb.append(peaceMaker.getEmpireName());
      sb.append(" diplomatic skills were surely effecting on this peace! ");
    }
    if (attitude == Attitude.PEACEFUL) {
      sb.append(peaceMaker.getEmpireName());
      sb.append(" is known about their peace loving. So this was expected! ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make Trade alliance news. Offerer makes trade alliance offer to acceptor.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param offerer Player who is make trade alliance
   * @param acceptor Player who is accepting
   * @param meetingPlace Where meeting happened, fleet or planet
   * @return NewsData
   */
  public static NewsData makeTradeAllianceNews(final PlayerInfo offerer,
      final PlayerInfo acceptor, final Object meetingPlace) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      instructions.addPlanet(ImageInstruction.POSITION_CENTER,
          planet.getImageInstructions(),
          ImageInstruction.SIZE_FULL);
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("NEW TRADE ALLIANCE!");
        break;
      }
      case 1: {
        instructions.addText("TRADE ALLIANCE!");
        break;
      }
      case 2: {
        instructions.addText("TRADE ALLIANCE IS CREATED!");
        break;
      }
    }
    instructions.addText(offerer.getEmpireName());
    instructions.addRelationSymbol(ImageInstruction.TRADE_ALLIANCE);
    instructions.addText(acceptor.getEmpireName());
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(offerer.getEmpireName());
    sb.append(" made trade alliance with ");
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
    Attitude attitude = offerer.getAiAttitude();
    if (attitude == Attitude.MERCHANTICAL) {
      sb.append(offerer.getEmpireName());
      sb.append(" is known about their interest to trade, so ");
      sb.append("this trade alliance was expected! ");
    }
    if (attitude == Attitude.DIPLOMATIC) {
      sb.append(offerer.getEmpireName());
      sb.append(" diplomatic skills were surely effecting on "
          + "this trade alliance! ");
    }
    if (attitude == Attitude.PEACEFUL) {
      sb.append(offerer.getEmpireName());
      sb.append(" is known about their peace loving. So this was expected! ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make alliance news. Offerer makes alliance offer to acceptor.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param offerer Player who is make trade alliance
   * @param acceptor Player who is accepting
   * @param meetingPlace Where meeting happened, fleet or planet
   * @return NewsData
   */
  public static NewsData makeAllianceNews(final PlayerInfo offerer,
      final PlayerInfo acceptor, final Object meetingPlace) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      instructions.addPlanet(ImageInstruction.POSITION_CENTER,
          planet.getImageInstructions(),
          ImageInstruction.SIZE_FULL);
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("NEW ALLIANCE!");
        break;
      }
      case 1: {
        instructions.addText("ALLIANCE!");
        break;
      }
      case 2: {
        instructions.addText("ALLIANCE IS CREATED!");
        break;
      }
    }
    instructions.addText(offerer.getEmpireName());
    instructions.addRelationSymbol(ImageInstruction.ALLIANCE);
    instructions.addText(acceptor.getEmpireName());
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(offerer.getEmpireName());
    sb.append(" made alliance with ");
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
    Attitude attitude = offerer.getAiAttitude();
    if (attitude == Attitude.DIPLOMATIC) {
      sb.append(offerer.getEmpireName());
      sb.append(" diplomatic skills were surely effecting on "
          + "this alliance! ");
    }
    if (attitude == Attitude.PEACEFUL) {
      sb.append(offerer.getEmpireName());
      sb.append(" is known about their peace loving. So this was expected! ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make defensive pact news. Offerer makes alliance offer to acceptor.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param offerer Player who is make trade alliance
   * @param acceptor Player who is accepting
   * @param meetingPlace Where meeting happened, fleet or planet
   * @return NewsData
   */
  public static NewsData makeDefensivePactNews(final PlayerInfo offerer,
      final PlayerInfo acceptor, final Object meetingPlace) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      instructions.addPlanet(ImageInstruction.POSITION_CENTER,
          planet.getImageInstructions(),
          ImageInstruction.SIZE_FULL);
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("NEW DEFENSIVE PACT!");
        break;
      }
      case 1: {
        instructions.addText("DEFENSIVE PACT!");
        break;
      }
      case 2: {
        instructions.addText("DEFENSE ORGANIZATION IS CREATED!");
        break;
      }
    }
    instructions.addText(offerer.getEmpireName());
    instructions.addRelationSymbol(ImageInstruction.DEFENSIVE_PACT);
    instructions.addText(acceptor.getEmpireName());
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(offerer.getEmpireName());
    sb.append(" made defensive pact with ");
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
    Attitude attitude = offerer.getAiAttitude();
    if (attitude == Attitude.DIPLOMATIC) {
      sb.append(offerer.getEmpireName());
      sb.append(" diplomatic skills were surely effecting on "
          + "this defensive  pact! ");
    }
    if (attitude == Attitude.PEACEFUL) {
      sb.append(offerer.getEmpireName());
      sb.append(" is known about their peace loving. So this was expected! ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make News when news corporation has finished new stats about players
   * @param map StarMap contains NewsCorpData and playerlist
   * @return NewsData
   */
  public static NewsData makeStatNews(final StarMap map) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    NewsCorpData data = map.getNewsCorpData();
    news.setImageInstructions(instructions.build());
    if (data.isFirstStats()) {
      instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
      instructions.addImage(ImageInstruction.LOGO);
      instructions.addText("FIRST STATISTICAL RESEARCH DONE!");
      instructions.addText("BY");
      news.setNewsText("GBNC has done first statistical research about Realms"
          + " in Stars.");
      news.setImageInstructions(instructions.build());
    } else {
      instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
      StringBuilder sb = new StringBuilder();
      int statIndex = DiceGenerator.getRandom(6);
      if (statIndex == 0) {
        int index = data.getMilitary().getBiggest();
        if (index != -1) {
          PlayerInfo info = map.getPlayerByIndex(index);
          instructions.addText("GREATEST MILITARY!");
          instructions.addText(info.getEmpireName());
          instructions.addImage(info.getRace().getNameSingle());
          sb.append(info.getEmpireName());
          sb.append(" has greatest military power in whole galaxy! ");
          sb.append("See full report from Statiscis view.");
        }
      }
      if (statIndex == 1) {
        int index = data.getCredit().getBiggest();
        if (index != -1) {
          PlayerInfo info = map.getPlayerByIndex(index);
          instructions.addText("WEALTHIEST REALM!");
          instructions.addText(info.getEmpireName());
          instructions.addImage(info.getRace().getNameSingle());
          sb.append(info.getEmpireName());
          sb.append(" is the wealthiest realm in whole galaxy! ");
          sb.append("See full report for Statiscis view.");
        }
      }
      if (statIndex == 2) {
        int index = data.getPlanets().getBiggest();
        if (index != -1) {
          PlayerInfo info = map.getPlayerByIndex(index);
          instructions.addText("MOST OF PLANETS!");
          instructions.addText(info.getEmpireName());
          instructions.addImage(info.getRace().getNameSingle());
          sb.append(info.getEmpireName());
          sb.append(" has most of the colonized planets in whole galaxy! ");
          sb.append("See full report from Statiscis view.");
        }
      }
      if (statIndex == 3) {
        int index = data.getPopulation().getBiggest();
        if (index != -1) {
          PlayerInfo info = map.getPlayerByIndex(index);
          instructions.addText("MOST OF PEOPLE!");
          instructions.addText(info.getEmpireName());
          instructions.addImage(info.getRace().getNameSingle());
          sb.append(info.getEmpireName());
          sb.append(" has most of the people in whole galaxy! ");
          sb.append("See full report from Statiscis view.");
        }
      }
      if (statIndex == 4) {
        int index = data.getCultural().getBiggest();
        if (index != -1) {
          PlayerInfo info = map.getPlayerByIndex(index);
          instructions.addText("MOST CULTURAL POWER!");
          instructions.addText(info.getEmpireName());
          instructions.addImage(info.getRace().getNameSingle());
          sb.append(info.getEmpireName());
          sb.append(" is the biggest cultural power in whole galaxy! ");
          sb.append("See full report from Statiscis view.");
        }
      }
      if (statIndex == 5) {
        int index = data.getResearch().getBiggest();
        if (index != -1) {
          PlayerInfo info = map.getPlayerByIndex(index);
          instructions.addText("MOST SCIENTIFIC REALM!");
          instructions.addText(info.getEmpireName());
          instructions.addImage(info.getRace().getNameSingle());
          sb.append(info.getEmpireName());
          sb.append(" is the biggest scientific power in whole galaxy! ");
          sb.append("See full report from Statiscis view.");
        }
      }
      if (statIndex == 6) {
        int index = data.generateScores().getBiggest();
        if (index != -1) {
          PlayerInfo info = map.getPlayerByIndex(index);
          instructions.addText("GREATEST REALM!");
          instructions.addText(info.getEmpireName());
          instructions.addImage(info.getRace().getNameSingle());
          sb.append(info.getEmpireName());
          sb.append(" is the greatest realm in whole galaxy! ");
          sb.append("See full report from Statiscis view.");
        }
      }
      if (sb.toString().isEmpty()) {
        instructions.addImage(ImageInstruction.LOGO);
        int number = data.getStatNumbers();
        String numberAsString = TextUtilities.getOrderNumberAsText(number);
        instructions.addText(numberAsString.toUpperCase()
            + " STATISTICAL RESEARCH DONE!");
        instructions.addText("BY");
        news.setNewsText("GBNC has done " + numberAsString
            + " statistical research about Realms in Stars.");
        news.setImageInstructions(instructions.build());
      } else {
        news.setImageInstructions(instructions.build());
        news.setNewsText(sb.toString());
      }
    }
    return news;
  }

  /**
   * Attack conquers defender planet
   * @param attacker Player who is conquering
   * @param defender Player who is defending
   * @param planet Which planet was conquered
   * @param nuked Was planet nuked or not
   * @return NewsData
   */
  public static NewsData makePlanetConqueredNews(final PlayerInfo attacker,
      final PlayerInfo defender, final Planet planet, final boolean nuked) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    instructions.addPlanet(ImageInstruction.POSITION_CENTER,
        planet.getImageInstructions(),
        ImageInstruction.SIZE_FULL);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("PLANET CONQUERED!");
        break;
      }
      case 1: {
        instructions.addText("TRIUMPH ON " + planet.getName() + "!");
        break;
      }
      case 2: {
        instructions.addText(planet.getName() + " IS OVERTHROWN!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(attacker.getEmpireName());
    sb.append(" made massive attack on ");
    sb.append(planet.getName());
    sb.append(". Defender ");
    sb.append(defender.getEmpireName());
    sb.append(" was defeated eventually. ");
    if (nuked) {
      sb.append(" All the population was killed by massive usage of"
          + " nuclear weapons. ");
      sb.append(" Radiation levels on planet has been raised to "
          + planet.getTotalRadiationLevel() + ". ");
    } else {
      sb.append(" Last remains of ");
      sb.append(defender.getRace().getNameSingle());
      sb.append(" population was killed by ground troops of ");
      sb.append(attacker.getEmpireName());
      sb.append(". ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make News when game is in halfway
   * @param map StarMap contains NewsCorpData and playerlist
   * @return NewsData
   */
  public static NewsData makeScoreNewsHalf(final StarMap map) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    NewsCorpData data = map.getNewsCorpData();
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder();
    int index = data.generateScores().getBiggest();
    if (index != -1) {
      PlayerInfo info = map.getPlayerByIndex(index);
      instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
      instructions.addText("RACE FOR GREATEST REALM IN HALFWAY!");
      instructions.addText(info.getEmpireName());
      instructions.addImage(info.getRace().getNameSingle());
      sb.append(info.getEmpireName());
      sb.append(" is the greatest realm in whole galaxy at the moment! ");
      index = data.generateScores().getSecond();
      if (index != -1) {
        info = map.getPlayerByIndex(index);
        sb.append("Second greatest realm is ");
        sb.append(info.getEmpireName());
        sb.append(" ! ");
      }
      sb.append("See full scoring situation from Statiscis view.");
    }
    news.setImageInstructions(instructions.build());
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make News when game is in last quarter
   * @param map StarMap contains NewsCorpData and playerlist
   * @return NewsData
   */
  public static NewsData makeScoreNewsLastQuarter(final StarMap map) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    NewsCorpData data = map.getNewsCorpData();
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder();
    int index = data.generateScores().getBiggest();
    if (index != -1) {
      PlayerInfo info = map.getPlayerByIndex(index);
      instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
      instructions.addText("RACE FOR GREATEST REALM!");
      instructions.addText(info.getEmpireName());
      instructions.addImage(info.getRace().getNameSingle());
      sb.append(info.getEmpireName());
      sb.append(" is the greatest realm in whole galaxy at the moment! ");
      index = data.generateScores().getSecond();
      if (index != -1) {
        info = map.getPlayerByIndex(index);
        sb.append("Second greatest realm is ");
        sb.append(info.getEmpireName());
        sb.append(" ! ");
      }
      sb.append("Race is now on last quarter! ");
      sb.append("See full scoring situation from Statiscis view.");
    }
    news.setImageInstructions(instructions.build());
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Create score board according the galaxy score and player list.
   * @param scores GalaxyStat for lastest score
   * @param players PlayerList
   * @return Scoreboard
   */
  private static ScoreBoard createScoreBoard(final GalaxyStat scores,
      final PlayerList players) {
    ScoreBoard board = new ScoreBoard();
    // First let's do alliance rows
    for (int i = 0; i < players.getCurrentMaxPlayers(); i++) {
      PlayerInfo info = players.getPlayerInfoByIndex(i);
      int alliance = info.getDiplomacy().getAllianceIndex();
      if (alliance != -1) {
        int score = scores.getLatest(i) + scores.getLatest(alliance);
        Row row = new Row(score, i, alliance);
        board.add(row);
      } else {
        int score = scores.getLatest(i);
        Row row = new Row(score, i);
        board.add(row);
      }
    }
    return board;
  }
  /**
   * Make News when game is ending for culture victory.
   * Returns null if cultural victory is not achieved
   * @param map StarMap contains NewsCorpData and playerlist
   * @return NewsData or null
   */
  public static NewsData makeCulturalVictoryNewsAtEnd(final StarMap map) {
    int limit = StarMapUtilities.calculateCultureScoreLimit(
        map.getMaxX(), map.getMaxY(), map.getScoreVictoryTurn(),
        map.getScoreCulture());
    if (limit == -1) {
      return null;
    }
    NewsCorpData tmpData = new NewsCorpData(
        map.getPlayerList().getCurrentMaxPlayers());
    NewsData news = null;
    tmpData.calculateCulture(map.getPlanetList(), map.getPlayerList());
    ScoreBoard board = createScoreBoard(tmpData.getCultural(),
        map.getPlayerList());
    board.sort();
    Row winner = board.getRow(0);
    Row second = board.getRow(1);
    if (winner.getScore() >= limit) {
      news = new NewsData();
      ImageInstruction instructions = new ImageInstruction();
      news.setImageInstructions(instructions.build());
      StringBuilder sb = new StringBuilder();
      if (!winner.isAlliance()) {
        PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE CULTURAL DOMINATION!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append(info.getEmpireName());
        sb.append(" has the greatest culture in whole galaxy! ");
        sb.append(info.getEmpireName());
        sb.append(" gained culture of ");
        sb.append(winner.getScore());
        sb.append("! ");
        if (!second.isAlliance()) {
          info = map.getPlayerByIndex(second.getRealm());
          sb.append("Second greatest culture has ");
          sb.append(info.getEmpireName());
          sb.append(" ! ");
          sb.append(info.getEmpireName());
          sb.append(" gained culture of ");
          sb.append(second.getScore());
          sb.append("! ");
        } else {
          info = map.getPlayerByIndex(second.getRealm());
          PlayerInfo info2 = map.getPlayerByIndex(second.getAllianceRealm());
          sb.append("Second greatest culture has alliance of");
          sb.append(info.getEmpireName());
          sb.append(" and ");
          sb.append(info2.getEmpireName());
          sb.append(" ! ");
          sb.append("This alliance gained culture of ");
          sb.append(second.getScore());
          sb.append("! ");
        }
      } else {
        PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
        PlayerInfo info2 = map.getPlayerByIndex(winner.getAllianceRealm());
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE GREATEST CULTURAL ALLIANCE!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append("Alliance of ");
        sb.append(info.getEmpireName());
        sb.append(" and ");
        sb.append(info2.getEmpireName());
        sb.append(" has the greatest culture in  whole galaxy! ");
        sb.append("This alliance gained culture of ");
        sb.append(winner.getScore());
        sb.append("! ");
        if (!second.isAlliance()) {
          info = map.getPlayerByIndex(second.getRealm());
          sb.append("Second greatest culture has ");
          sb.append(info.getEmpireName());
          sb.append(" ! ");
          sb.append(info.getEmpireName());
          sb.append(" gained culture of ");
          sb.append(second.getScore());
          sb.append("! ");
        } else {
          info = map.getPlayerByIndex(second.getRealm());
          info2 = map.getPlayerByIndex(second.getAllianceRealm());
          sb.append("Second greatest culture has alliance of");
          sb.append(info.getEmpireName());
          sb.append("and");
          sb.append(info2.getEmpireName());
          sb.append(" ! ");
          sb.append("This alliance gained culture of ");
          sb.append(second.getScore());
          sb.append("! ");
        }
      }
      news.setImageInstructions(instructions.build());
      news.setNewsText(sb.toString());
    }
    return news;
  }

  /**
   * Make News when game is ending for domination victory.
   * Returns null if domination victory is not achieved
   * @param map StarMap contains Planet and playerlist
   * @return NewsData or null
   */
  public static NewsData makeDominationVictoryNewsAtEnd(final StarMap map) {
    int limit = map.getPlayerList().getCurrentMaxPlayers();
    if (limit < 4) {
      return null;
    }
    NewsCorpData tmpData = new NewsCorpData(
        map.getPlayerList().getCurrentMaxPlayers());
    NewsData news = null;
    tmpData.calculateHomePlanets(map.getPlanetList());
    ScoreBoard board = createScoreBoard(tmpData.getPlanets(),
        map.getPlayerList());
    board.sort();
    Row winner = board.getRow(0);
    if (winner.getScore() >= limit) {
      news = new NewsData();
      ImageInstruction instructions = new ImageInstruction();
      news.setImageInstructions(instructions.build());
      StringBuilder sb = new StringBuilder();
      if (!winner.isAlliance()) {
        PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE DOMINATION VICTORY!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append(info.getEmpireName());
        sb.append(" has the all the home worlds in the galaxy! ");
        sb.append("No other realm has power to challenge ");
        sb.append(info.getEmpireName());
        sb.append(".");
      } else {
        PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
        PlayerInfo info2 = map.getPlayerByIndex(winner.getAllianceRealm());
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE DOMINATING ALLIANCE!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append("Alliance of ");
        sb.append(info.getEmpireName());
        sb.append("and");
        sb.append(info2.getEmpireName());
        sb.append(" has the all the home worlds in the galaxy! ");
        sb.append("No other realm has power to challenge this alliance.");
      }
      news.setImageInstructions(instructions.build());
      news.setNewsText(sb.toString());
    }
    return news;
  }

  /**
   * Make News when game is in the end turn
   * @param map StarMap contains NewsCorpData and playerlist
   * @return NewsData
   */
  public static NewsData makeScoreNewsAtEnd(final StarMap map) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    NewsCorpData data = map.getNewsCorpData();
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder();
    ScoreBoard board = createScoreBoard(data.generateScores(),
        map.getPlayerList());
    board.sort();
    Row winner = board.getRow(0);
    Row second = board.getRow(1);
    if (!winner.isAlliance()) {
      PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
      instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
      instructions.addText("THE GREATEST REALM ALL TIME!");
      instructions.addText(info.getEmpireName());
      instructions.addImage(info.getRace().getNameSingle());
      sb.append(info.getEmpireName());
      sb.append(" is the greatest realm in whole galaxy! ");
      if (!second.isAlliance()) {
        info = map.getPlayerByIndex(second.getRealm());
        sb.append("Second greatest realm is ");
        sb.append(info.getEmpireName());
        sb.append(" !");
      } else {
        info = map.getPlayerByIndex(second.getRealm());
        PlayerInfo info2 = map.getPlayerByIndex(second.getAllianceRealm());
        sb.append("Second greatest is alliance of");
        sb.append(info.getEmpireName());
        sb.append("and");
        sb.append(info2.getEmpireName());
        sb.append(" !");
      }
    } else {
      PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
      PlayerInfo info2 = map.getPlayerByIndex(winner.getAllianceRealm());
      instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
      instructions.addText("THE GREATEST ALLIANCE ALL TIME!");
      instructions.addText(info.getEmpireName());
      instructions.addImage(info.getRace().getNameSingle());
      sb.append("Alliance of ");
      sb.append(info.getEmpireName());
      sb.append("and");
      sb.append(info2.getEmpireName());
      sb.append(" is the greatest in whole galaxy! ");
      if (!second.isAlliance()) {
        info = map.getPlayerByIndex(second.getRealm());
        sb.append("Second greatest realm is ");
        sb.append(info.getEmpireName());
        sb.append(" !");
      } else {
        info = map.getPlayerByIndex(second.getRealm());
        info2 = map.getPlayerByIndex(second.getAllianceRealm());
        sb.append("Second greatest is alliance of");
        sb.append(info.getEmpireName());
        sb.append("and");
        sb.append(info2.getEmpireName());
        sb.append(" !");
      }
    }
    news.setImageInstructions(instructions.build());
    news.setNewsText(sb.toString());
    return news;
  }

}
