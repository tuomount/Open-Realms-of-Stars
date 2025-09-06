package org.openRealmOfStars.starMap.newsCorp;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2022 Tuomo Untinen
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
 */

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.PlayerList;
import org.openRealmOfStars.player.diplomacy.Attitude;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderBiography;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.CulturePower;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.StarMapUtilities;
import org.openRealmOfStars.starMap.event.karmaEvents.RandomEvent;
import org.openRealmOfStars.starMap.event.karmaEvents.RandomEventType;
import org.openRealmOfStars.starMap.history.event.DiplomaticEvent;
import org.openRealmOfStars.starMap.history.event.Event;
import org.openRealmOfStars.starMap.history.event.LeaderEvent;
import org.openRealmOfStars.starMap.newsCorp.scoreBoard.Row;
import org.openRealmOfStars.starMap.newsCorp.scoreBoard.ScoreBoard;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.enums.PlanetTypes;
import org.openRealmOfStars.starMap.vote.Vote;
import org.openRealmOfStars.starMap.vote.VotingType;
import org.openRealmOfStars.starMap.vote.sports.Athlete;
import org.openRealmOfStars.starMap.vote.sports.Sports;
import org.openRealmOfStars.starMap.vote.sports.VotingChoice;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.TextUtilities;

/**
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
   * @param casusBelli true if attacker had casus belli
   * @return NewsData
   */
  public static NewsData makeWarNews(final PlayerInfo aggressor,
      final PlayerInfo defender, final Object meetingPlace,
      final StarMap map, final boolean casusBelli) {
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
        if (casusBelli) {
          instructions.addText("DEFENSIVE WAR!");
        } else {
          instructions.addText("WAR BEGINS!");
        }
        break;
      }
    }
    instructions.addText(aggressor.getEmpireName());
    instructions.addRelationSymbol(ImageInstruction.WAR);
    instructions.addText(defender.getEmpireName());
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    if (map != null) {
      sb.append("At star year ");
      sb.append(map.getStarYear());
      sb.append(" ");
    }
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
      String place = null;
      if (meetingPlace instanceof Fleet && map != null) {
        Fleet fleet = (Fleet) meetingPlace;
        CulturePower power = map.getSectorCulture(fleet.getCoordinate().getX(),
            fleet.getCoordinate().getY());
        int index = power.getHighestCulture();
        if (index != -1) {
          PlayerInfo sectorOwner = map.getPlayerByIndex(index);
          if (sectorOwner != null) {
            place = sectorOwner.getEmpireName() + " space.";
          }
        }
      }
      if (place != null) {
        if (surpriseAttack) {
          sb.append("This attack happened in " + place + " ");
        } else {
          sb.append("This meeting happened in " + place + " ");
        }
      }
    }
    if (!casusBelli) {
      Attitude attitude = aggressor.getAiAttitude();
      if (attitude == Attitude.AGGRESSIVE) {
        if (aggressor.getRuler() == null) {
          sb.append(aggressor.getEmpireName());
          sb.append(" is known about their aggressive behaviour, so ");
        } else {
          sb.append(aggressor.getRuler().getCallName());
          sb.append(" is known for ");
          sb.append(aggressor.getRuler().getGender().getHisHer());
          sb.append(" aggressive behaviour, so ");
        }
        sb.append("this war was just about to happen. ");
      }
      if (attitude == Attitude.MILITARISTIC) {
        if (aggressor.getRuler() == null) {
          sb.append(aggressor.getEmpireName());
        } else {
          sb.append(aggressor.getRuler().getCallName());
        }
        sb.append(" militaristic actions has lead to this war to burst out. ");
      }
      if (attitude == Attitude.PEACEFUL) {
        sb.append(aggressor.getEmpireName());
        sb.append(
            " is known about their peace loving. What horrible acts has ");
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
    } else if (map != null) {
      sb.append(aggressor.getEmpireName());
      sb.append(" has justified war against ");
      sb.append(defender.getEmpireName());
      sb.append(" because of ");
      int defenderIndex = map.getPlayerList().getIndex(defender);
      sb.append(aggressor.getDiplomacy().getCasusBelliReason(defenderIndex));
      sb.append(". ");
    }
    aggressor.appendStory(sb.toString());
    defender.appendStory(sb.toString());
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
      if (aggressor.getRuler() == null) {
        sb.append(aggressor.getEmpireName());
        sb.append(" is known about their aggressive behaviour, so ");
      } else {
        sb.append(aggressor.getRuler().getCallName());
        sb.append(" is known for ");
        sb.append(aggressor.getRuler().getGender().getHisHer());
        sb.append(" aggressive behaviour, so ");
      }
      sb.append("this war was just about to happen, even against ");
      sb.append("bigger group! ");
    }
    if (attitude == Attitude.MILITARISTIC) {
      if (aggressor.getRuler() == null) {
        sb.append(aggressor.getEmpireName());
      } else {
        sb.append(aggressor.getRuler().getCallName());
      }
      sb.append(" militaristic actions has lead to this war to burst out. ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make Random event news
   * @param event Random event
   * @param starYear when Event happens
   * @return NewsData
   */
  public static NewsData makeRandomEventNews(final RandomEvent event,
      final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addInstruction(event.getImageInstructions());

    if (event.getType() == null) {
      news.setImageInstructions(instructions.build());
      news.setNewsText(event.getText());
      event.getRealm().appendStory(event.getText(), starYear);
      return news;
    }

    if (event.getType() == RandomEventType.SOLAR_ACTIVITY_DIMISHED) {
      instructions.addText("SOLAR ACTIVITY DECREASED!");
    }
    if (event.getType() == RandomEventType.CULTURAL_HIT) {
      instructions.addText("MASSIVE SUCCESS!");
    }
    if (event.getType() == RandomEventType.SOLAR_ACTIVITY_INCREASE) {
      instructions.addText("SOLAR ACTIVITY INCREASED!");
    }
    if (event.getType() == RandomEventType.ACCIDENT) {
      news = makeLeaderDies(event.getLeader(), event.getRealm(), "accident",
          starYear);
      news.setImageInstructions(instructions.build());
      return news;
    }
    if (event.getType() == RandomEventType.TERRORIST_ATTACK) {
      instructions.addText("TERRORIST ATTACK!");
    }
    if (event.getType() == RandomEventType.DEADLY_VIRUS_OUTBREAK) {
      news = makeDeadlyVirusNews(event.getPlanet(), null, starYear);
      news.setImageInstructions(instructions.build());
      return news;
    }

    news.setImageInstructions(instructions.build());
    news.setNewsText(event.getText());
    event.getRealm().appendStory(event.getText(), starYear);
    return news;
  }
  /**
   * Make news about new heir
   * @param heir New heir
   * @param realm PlayerInfo
   * @param starYear current star year.
   * @return NewsData
   */
  public static NewsData makeHeirNews(final Leader heir,
      final PlayerInfo realm, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    instructions.addImage(heir.getRace().getNameSingle());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText(heir.getCallName());
        instructions.addText("IS BORN!");
        break;
      }
      case 1: {
        instructions.addText("NEW HEIR!");
        break;
      }
      case 2: {
        instructions.addText(realm.getEmpireName());
        instructions.addText("HAS NEW HEIR!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(heir.getParent().getCallName());
    sb.append(" has born new heir at star year ");
    sb.append(starYear);
    sb.append(". ");
    sb.append("Heir is called ");
    sb.append(heir.getCallName());
    sb.append(". ");
    realm.appendStory(sb.toString());
    news.setNewsText(sb.toString());
    return news;
  }
      /**
       * Make news about new ruler
       * @param ruler New ruler
       * @param realm PlayerInfo
       * @param starYear Star Year when this happens
       * @return NewsData
       */
  public static NewsData makeNewRulerNews(final Leader ruler,
      final PlayerInfo realm, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    instructions.addImage(ruler.getRace().getNameSingle());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText(ruler.getCallName());
        instructions.addText("IS NEW RULER!");
        break;
      }
      case 1: {
        instructions.addText("NEW RULER!");
        break;
      }
      case 2: {
        instructions.addText(realm.getEmpireName());
        instructions.addText("HAS NEW RULER!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(ruler.getCallName());
    sb.append(" is new for ruler of ");
    sb.append(realm.getEmpireName());
    sb.append(". ");
    news.setNewsText(sb.toString());
    realm.appendStory(sb.toString(), starYear);
    return news;
  }
  /**
   * Make news about election
   * @param ruler Old ruler
   * @param realm PlayerInfo
   * @return NewsData
   */
  public static NewsData makeElectionNews(final Leader ruler,
      final PlayerInfo realm) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    instructions.addImage(ruler.getRace().getNameSingle());
    instructions.addText(realm.getEmpireName());
    instructions.addText("IS HAVING NEW ELECTIONS!");
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(ruler.getCallName());
    sb.append(" is standing down for ruler of ");
    sb.append(realm.getEmpireName());
    sb.append(". ");
    sb.append("Who will be the next ruler of ");
    sb.append(realm.getEmpireName());
    sb.append("? ");
    news.setNewsText(sb.toString());
    return news;
  }
  /**
   * Make news about if leader is put in jail for some reason.
   * @param leader Leader which is going to prison
   * @param realm Realm who owns the leader
   * @param reason Reason why leader is going to jail.
   *               This should short version.
   * @param reasonFull Reason why leader is going to jail.
   *               This should long version of reason.
   * @param time Prison time in star years.
   * @return NewsData
   */
  public static NewsData makeLeaderPrisoned(final Leader leader,
      final PlayerInfo realm, final String reason, final String reasonFull,
      final int time) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    instructions.addImage(leader.getRace().getNameSingle());
    instructions.addText(leader.getCallName());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("JAIL TIME!");
        break;
      }
      case 1: {
        instructions.addText("CAUGHT FROM "
            + reason.toUpperCase() + "!");
        break;
      }
      case 2: {
        instructions.addText("GUILTY!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append("One of the leaders of ");
    sb.append(realm.getEmpireName());
    sb.append(" was caught from ");
    sb.append(reason);
    sb.append(". ");
    sb.append(leader.getCallName());
    sb.append(" has sentenced for prison for ");
    sb.append(time);
    sb.append(" star years. ");
    sb.append("Reason for ");
    sb.append(leader.getCallName());
    sb.append(" get jail was ");
    sb.append(reason);
    sb.append(". ");
    if (leader.getTitle() != null && !leader.getTitle().isEmpty()) {
      sb.append(leader.getTitle());
    } else {
      sb.append(leader.getName());
    }
    sb.append(" is from ");
    sb.append(leader.getHomeworld());
    sb.append(". ");
    news.setNewsText(sb.toString());
    return news;
  }
  /**
   * Make leader freed news.
   * @param leader Leader which was set as free.
   * @param realm Realm whose freeing the leader.
   * @return NewsData
   */
  public static NewsData makeLeaderFreed(final Leader leader,
      final PlayerInfo realm) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    instructions.addImage(leader.getRace().getNameSingle());
    instructions.addText(leader.getCallName());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("FREED!");
        break;
      }
      case 1: {
        instructions.addText("IS FREE!");
        break;
      }
      case 2: {
        instructions.addText("UNEXPECTED FREE!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    if (realm.getRuler() != null) {
      sb.append(realm.getRuler().getTitle());
      sb.append(" of ");
      sb.append(realm.getEmpireName());
    } else {
      sb.append(realm.getEmpireName());
    }
    sb.append(" has decided to set ");
    sb.append(leader.getCallName());
    sb.append(" for free. This is very unexpected! ");
    sb.append(leader.getCallName());
    sb.append(" still had sentence left for ");
    sb.append(leader.getTimeInJob());
    sb.append(" years. ");
    sb.append("This act will cause diplomatic turbulence against");
    sb.append(" other realms in galaxy. ");
    return news;
  }
  /**
   * Make news when ruler dies
   * @param leader Ruler who died
   * @param realm Realm where ruler belong
   * @param reason Reason for death
   * @param starYear Star Year when this happens
   * @return NewsData
   */
  public static NewsData makeLeaderDies(final Leader leader,
      final PlayerInfo realm, final String reason, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    instructions.addImage(leader.getRace().getNameSingle());
    instructions.addText(leader.getCallName());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("DIES!");
        break;
      }
      case 1: {
        instructions.addText("DIES AT AGE " + leader.getAge() + "!");
        break;
      }
      case 2: {
        instructions.addText("FOUND DEAD!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append("Today is sad day for ");
    sb.append(realm.getEmpireName());
    sb.append(". ");
    sb.append(leader.getCallName());
    sb.append(" has died at age of ");
    sb.append(leader.getAge());
    sb.append(". ");
    sb.append("Reason for ");
    sb.append(leader.getCallName());
    sb.append(" died because of ");
    sb.append(reason);
    sb.append(". ");
    if (leader.getTitle() != null && !leader.getTitle().isEmpty()) {
      sb.append(leader.getTitle());
    } else {
      sb.append(leader.getName());
    }
    sb.append(" was from ");
    sb.append(leader.getHomeworld());
    sb.append(". ");
    if (leader.getParent() != null) {
      sb.append(leader.getCallName());
      sb.append(" was heir for ");
      sb.append(leader.getParent().getCallName());
      sb.append(". ");
    }
    int heirs = 0;
    for (Leader heir : realm.getLeaderPool()) {
      if (heir.getParent() == leader && heir.getJob() != Job.DEAD) {
        heirs++;
      }
    }
    if (heirs > 0) {
      sb.append(leader.getCallName());
      sb.append(" has ");
      sb.append(heirs);
      sb.append(" heirs. ");
      if (realm.getRuler() == null) {
        sb.append("Probably one of them will be the next ruler of");
        sb.append(realm.getEmpireName());
        sb.append(". ");
      }
    }
    sb.append(LeaderBiography.createBioForLeader(leader, realm));
    news.setNewsText(sb.toString());
    realm.appendStory(sb.toString(), starYear);
    return news;
  }
  /**
   * Make Leader event
   * @param leader Leader who is experience the event
   * @param realm Realm where leader belongs
   * @param map StarMap
   * @param news News about the leader
   * @return Event
   */
  public static Event makeLeaderEvent(final Leader leader,
      final PlayerInfo realm, final StarMap map, final NewsData news) {
    return makeLeaderEvent(leader, realm, map, news.getNewsText());
  }
  /**
   * Make Leader event
   * @param leader Leader who is experience the event
   * @param realm Realm where leader belongs
   * @param map StarMap
   * @param text Textual description of event.
   * @return Event
   */
  public static Event makeLeaderEvent(final Leader leader,
      final PlayerInfo realm, final StarMap map, final String text) {
    int realmIndex = map.getPlayerList().getIndex(realm);
    Coordinate coord = new Coordinate(map.getMaxX() / 2, map.getMaxY() / 2);
    Planet firstPlanet = null;
    if (leader.getJob() == Job.RULER || leader.getJob() == Job.TOO_YOUNG) {
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == realm && firstPlanet == null) {
          firstPlanet = planet;
          coord = firstPlanet.getCoordinate();
          break;
        }
      }
    }
    if (leader.getJob() == Job.GOVERNOR) {
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == realm
            && planet.getGovernor() == leader && firstPlanet == null) {
          firstPlanet = planet;
          coord = firstPlanet.getCoordinate();
          break;
        }
      }
    }
    if (leader.getJob() == Job.COMMANDER) {
      for (int i = 0; i < realm.getFleets().getNumberOfFleets(); i++) {
        Fleet fleet = realm.getFleets().getByIndex(i);
        if (fleet.getCommander() == leader) {
          coord = fleet.getCoordinate();
          break;
        }
      }
    }
    LeaderEvent event = new LeaderEvent(leader, realm, realmIndex, coord);
    if (firstPlanet != null) {
      event.setPlanetName(firstPlanet.getName());
    }
    event.setText(text);
    return event;
  }
  /**
   * Make fleet command killed news.
   * @param killed Commander which is killed
   * @param killer Possible killer or null
   * @param killedRealm Which realm killed belong
   * @param killerRealm Which realm killed the commander
   * @param killedPrivateer If killed commander was privateer
   * @param killerPrivateer if killer commander was privateer
   * @param starYear when kill even happens.
   * @return NewsData
   */
  public static NewsData makeCommanderKilledInAction(final Leader killed,
      final Leader killer, final PlayerInfo killedRealm,
      final PlayerInfo killerRealm, final boolean killedPrivateer,
      final boolean killerPrivateer, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instructions.addImage(ImageInstruction.SHIP_DESTROYED);
    if (killer != null) {
      instructions.addText(killed.getCallName());
      instructions.addText("VS");
      instructions.addText(killer.getCallName());
    } else {
      instructions.addText(killed.getCallName());
      switch (DiceGenerator.getRandom(2)) {
        case 0:
        default: {
          instructions.addText("KILLED!");
          break;
        }
        case 1: {
          instructions.addText("KILLED IN ACTION!");
          break;
        }
        case 2: {
          instructions.addText("DIED IN BATTLE!");
          break;
        }
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    if (killedPrivateer) {
      sb.append("Privateer ");
    } else {
      sb.append(killedRealm.getEmpireName());
    }
    sb.append(" fought against ");
    if (killerPrivateer) {
      sb.append("privateer");
    } else {
      sb.append(killerRealm.getEmpireName());
    }
    sb.append(". ");
    if (killedPrivateer) {
      sb.append("Privateer ");
    } else {
      sb.append(killedRealm.getEmpireName());
    }
    sb.append(" lost the fight and ");
    sb.append(killed.getCallName());
    sb.append(" was killed in battle! ");
    if (killer != null) {
      sb.append(killer.getCallName());
      sb.append(" has blood on ");
      sb.append(killer.getGender().getHisHer());
      sb.append(" hands. ");
    }
    if (!killedPrivateer) {
      sb.append("Today is sad day for ");
      sb.append(killedRealm.getEmpireName());
      sb.append(" and for ");
      sb.append(killed.getHomeworld());
      sb.append(". ");
      sb.append(LeaderBiography.createBioForLeader(killed, killedRealm));
    } else {
      sb.append("Since ");
      sb.append(killed.getCallName());
      sb.append(" was privateer ");
      sb.append(killed.getGender().getHisHer());
      sb.append(" origins are unknown. ");
    }
    news.setNewsText(sb.toString());
    killedRealm.appendStory(sb.toString(), starYear);
    killerRealm.appendStory(sb.toString(), starYear);
    return news;
  }
  /**
   * Make news about leader escaping.
   * @param leader Leader which is escaped
   * @param leaderRealm Which realm killed belong
   * @param killerRealm Which realm tried to kill the leader
   * @param attackType conquest, bombing, nuking
   * @return NewsData
   */
  public static NewsData makeLeaderEscape(final Leader leader,
      final PlayerInfo leaderRealm, final PlayerInfo killerRealm,
      final String attackType) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instructions.addImage(ImageInstruction.SHUTTLE);
    instructions.addText(leader.getCallName());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("ESCAPE!");
        break;
      }
      case 1: {
        instructions.addText(leader.getTitle().toUpperCase()
            + " STAYS ALIVE!");
        break;
      }
      case 2: {
        instructions.addText("SHUTTLE TO RESCUE!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    if (killerRealm != null) {
      sb.append(leaderRealm.getEmpireName());
      sb.append(" fought against ");
      sb.append(killerRealm.getEmpireName());
    } else {
      sb.append(leaderRealm.getEmpireName());
      sb.append(" encountered ");
      sb.append(attackType);
    }
    sb.append(". ");
    sb.append(leaderRealm.getEmpireName());
    sb.append(" lost the fight but ");
    sb.append(leader.getCallName());
    sb.append(" was able to escape from certain death! ");
    sb.append("Private shuttle took ");
    sb.append(leader.getCallName());
    sb.append(" to non hostile planet. ");
    news.setNewsText(sb.toString());
    return news;
  }
  /**
   * Make fleet commander in combat news.
   * @param commander Commander which was in combat
   * @param realm Realm where commander belongs
   * @param otherRealm Opponent Realm
   * @param combatAgainstPrivateer Boolean
   * @param planet Null or planet where combat happened
   * @param starYear StarYear when combat happened
   * @param hullSlots number of Hull slots in biggest ship.
   * @param warhero did commander get war hero perk.
   * @return NewsData
   */
  public static NewsData makeCommanderInCombat(final Leader commander,
      final PlayerInfo realm,
      final PlayerInfo otherRealm, final boolean combatAgainstPrivateer,
      final Planet planet, final int starYear, final int hullSlots,
      final boolean warhero) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    if (planet != null) {
      instructions.addPlanet(ImageInstruction.POSITION_CENTER,
          planet.getPlanetType().getImageInstructions(),
          ImageInstruction.SIZE_FULL);
    } else {
      instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
    }
    if (hullSlots > 6) {
      instructions.addImage(ImageInstruction.CRUISER);
    } else {
      instructions.addImage(ImageInstruction.CORVETTE);
    }
    if (planet != null) {
      switch (DiceGenerator.getRandom(2)) {
        default:
        case 0: {
          instructions.addText("BATTLE");
          break;
        }
        case 1: {
          instructions.addText("COMBAT");
          break;
        }
        case 2: {
          instructions.addText("FIGHT");
          break;
        }
      }
      instructions.addText(" OF ");
      instructions.addText(planet.getName().toUpperCase());
    } else {
      instructions.addText(commander.getCallName().toUpperCase());
      switch (DiceGenerator.getRandom(3)) {
        case 0:
        default: {
          instructions.addText("IN FIGHT!");
          break;
        }
        case 1: {
          instructions.addText("IN COMBAT!");
          break;
        }
        case 2: {
          instructions.addText("IN BATTLE!");
          break;
        }
        case 3: {
          instructions.addText("IN ACTION!");
          break;
        }
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    if (combatAgainstPrivateer) {
      sb.append(commander.getCallName());
      switch (DiceGenerator.getRandom(2)) {
        case 0:
        default: {
          sb.append(" fight against ");
          break;
        }
        case 1: {
          sb.append(" had battle against ");
          break;
        }
        case 2: {
          sb.append(" was in combat against ");
          break;
        }
      }
      sb.append("privateer fleet");
    } else {
      sb.append(commander.getCallName());
      switch (DiceGenerator.getRandom(2)) {
        case 0:
        default: {
          sb.append(" fight against ");
          break;
        }
        case 1: {
          sb.append(" had battle against ");
          break;
        }
        case 2: {
          sb.append(" was in combat against ");
          break;
        }
      }
      sb.append(otherRealm.getEmpireName());
    }
    if (planet != null) {
      sb.append(" ");
      switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        sb.append("while orbiting ");
        break;
      }
      case 1: {
        sb.append("next to ");
        break;
      }
      case 2: {
        sb.append("in near space of ");
        break;
      }
    }
      sb.append(planet.getName());
    } else {
      switch (DiceGenerator.getRandom(2)) {
        case 0:
        default: {
          break;
        }
        case 1: {
          sb.append(" in deep space");
          break;
        }
        case 2: {
          sb.append(" in cold cosmos");
          break;
        }
      }
    }
    sb.append(". ");
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        sb.append("Combat");
        break;
      }
      case 1: {
        sb.append("Battle");
        break;
      }
      case 2: {
        sb.append("Fight");
        break;
      }
    }
    sb.append(" was victorious for ");
    sb.append(commander.getGender().getHisHer());
    sb.append(" side.");
    if (warhero) {
      sb.append(" ");
      sb.append(commander.getCallName());
      sb.append(" has become known war hero!");
    }
    news.setNewsText(sb.toString());
    realm.appendStory(sb.toString(), starYear);
    otherRealm.appendStory(sb.toString(), starYear);
    return news;
  }
  /**
   * Make news about leader escaping from false flag mission.
   * @param leader Leader which is escaped
   * @param leaderRealm Which realm killed belong
   * @param killerRealm Which realm tried to "kill" the leader
   * @param location Where leader were saved
   * @return NewsData
   */
  public static NewsData makeLeaderFalseFlag(final Leader leader,
      final PlayerInfo leaderRealm, final PlayerInfo killerRealm,
      final String location) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instructions.addImage(ImageInstruction.SHUTTLE);
    instructions.addText(leader.getCallName());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("ESCAPE!");
        break;
      }
      case 1: {
        instructions.addText(leader.getTitle().toUpperCase()
            + " STAYS ALIVE!");
        break;
      }
      case 2: {
        instructions.addText("SHUTTLE TO RESCUE!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    if (killerRealm != null) {
      sb.append("Ship of ");
      sb.append(leaderRealm.getEmpireName());
      sb.append(" was destroyed by ");
      sb.append(killerRealm.getEmpireName());
    } else {
      sb.append(leaderRealm.getEmpireName());
      sb.append(" ship was destroyed ");
    }
    sb.append(". ");
    sb.append(leader.getCallName());
    sb.append(" was on board the ship but ");
    sb.append(" was able to escape from certain death! ");
    sb.append("Private shuttle took ");
    sb.append(leader.getCallName());
    sb.append(" to ");
    sb.append(location);
    sb.append(". ");
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make realm lost news. Realm lost it's final planet
   * @param realm PlayerInfo who lost
   * @param starYear When this lost happens
   * @return NewsData
   */
  public static NewsData makeLostNews(final PlayerInfo realm,
      final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instructions.addImage(realm.getRace().getNameSingle());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("REALM LOST!");
        break;
      }
      case 1: {
        instructions.addText("EXTERMINATION!");
        break;
      }
      case 2: {
        instructions.addText("ANHILATION!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(realm.getEmpireName());
    sb.append(" has lost its last planet from the known galaxy! ");
    sb.append("Galaxy now has one realm less... ");
    news.setNewsText(sb.toString());
    realm.appendStory(sb.toString(), starYear);
    return news;
  }
  /**
   * Make Trade fleet news. Trader's ship arrives to another planet
   * @param trader PlayerInfo who is trading
   * @param planet Where to trader
   * @return NewsData
   */
  public static NewsData makeTradeNews(final PlayerInfo trader,
      final Planet planet) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    String position = ImageInstruction.POSITION_CENTER;
    switch (DiceGenerator.getRandom(3)) {
      case 0: {
        position = ImageInstruction.POSITION_CENTER;
        break;
      }
      case 1: {
        position = ImageInstruction.POSITION_LEFT;
        break;
      }
      case 2:
      default: {
        position = ImageInstruction.POSITION_RIGHT;
        break;
      }
    }
    String size = ImageInstruction.SIZE_FULL;
    switch (DiceGenerator.getRandom(2)) {
      case 0: {
        size = ImageInstruction.SIZE_FULL;
        break;
      }
      case 1:
      default: {
        size = ImageInstruction.SIZE_HALF;
        break;
      }
    }
    instructions.addPlanet(position, planet.getImageInstructions(), size);
    position = ImageInstruction.POSITION_CENTER;
    switch (DiceGenerator.getRandom(3)) {
      case 0: {
        position = ImageInstruction.POSITION_CENTER;
        break;
      }
      case 1: {
        position = ImageInstruction.POSITION_LEFT;
        break;
      }
      case 2:
      default: {
        position = ImageInstruction.POSITION_RIGHT;
        break;
      }
    }
    size = ImageInstruction.SIZE_FULL;
    switch (DiceGenerator.getRandom(2)) {
      case 0: {
        size = ImageInstruction.SIZE_FULL;
        break;
      }
      case 1:
      default: {
        size = ImageInstruction.SIZE_HALF;
        break;
      }
    }
    String ship = ImageInstruction.TRADER1;
    switch (DiceGenerator.getRandom(2)) {
      case 0: {
        ship = ImageInstruction.TRADER1;
        break;
      }
      case 1:
      default: {
        ship = ImageInstruction.TRADER2;
        break;
      }
    }
    instructions.addTrader(position, ship, size);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("TRADER ARRIVES!");
        break;
      }
      case 1: {
        instructions.addText("TRADER TO " + planet.getName().toUpperCase());
        break;
      }
      case 2: {
        instructions.addText("MERCHANT FROM "
            + trader.getEmpireName().toUpperCase());
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(trader.getEmpireName());
    sb.append(" trader fleet arrives to ");
    sb.append(planet.getName());
    sb.append("! ");
    if (planet.getPlanetPlayerInfo() != null
        && trader != planet.getPlanetPlayerInfo()) {
      sb.append(planet.getName());
      sb.append(" belongs to ");
      sb.append(planet.getPlanetPlayerInfo().getEmpireName());
      sb.append(". ");
    }
    sb.append("Cargo hull was full of goods from distant world! ");
    sb.append("This trade benefits both parties! ");
    Attitude attitude = trader.getAiAttitude();
    if (attitude == Attitude.MERCHANTICAL) {
      sb.append(trader.getEmpireName());
      sb.append(" is known for good trader! ");
    }
    if (attitude == Attitude.DIPLOMATIC) {
      sb.append(trader.getEmpireName());
      sb.append(" is known trustworthy diplomatic skills! ");
    }
    news.setNewsText(sb.toString());
    return news;
  }
  /**
   * Make Galactic sports ending news. New Galactic Olympics has ended.
   * @param vote Galactic sports vote
   * @param sports Sports event containg athlete information
   * @param planet Where sports event was held
   * @return NewsData
   */
  public static NewsData makeGalacticSportsEndingNews(final Vote vote,
      final Sports sports, final Planet planet) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    String position = ImageInstruction.POSITION_CENTER;
    String size = ImageInstruction.SIZE_HALF;
    instructions.addPlanet(position, planet.getImageInstructions(), size);
    instructions.addLogo(position, ImageInstruction.PLANET_SPORTS, size);
    Athlete[] athletes = sports.getAthletes();
    instructions.addLogo(ImageInstruction.POSITION_LEFT,
        athletes[0].getRealm().getRace().getNameSingle(),
        ImageInstruction.SIZE_FULL);
    if (athletes.length > 1) {
      instructions.addLogo(ImageInstruction.POSITION_RIGHT,
          athletes[1].getRealm().getRace().getNameSingle(),
          ImageInstruction.SIZE_FULL);
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("GALACTIC OLYMPICS!");
        break;
      }
      case 1: {
        instructions.addText("GALACTIC OLYMPICS AT "
            + planet.getName().toUpperCase());
        break;
      }
      case 2: {
        instructions.addText("GALACTIC OLYMPICS WON BY "
            + athletes[0].getRealm().getEmpireName());
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append("Galactic Olympics were arranged in ");
    sb.append(vote.getPlanetName());
    if (planet.getPlanetPlayerInfo() != null) {
      sb.append(". This event is organized by ");
      sb.append(planet.getPlanetPlayerInfo().getEmpireName());
    }
    sb.append(". ");
    sb.append(athletes.length);
    sb.append(" athletes were competing against each others. There were"
        + " athletes from ");
    for (int i = 0; i < athletes.length; i++) {
      sb.append(athletes[i].getPlanetName());
      sb.append(" (");
      sb.append(athletes[i].getRealm().getEmpireName());
      sb.append(")");
      if (i < athletes.length - 1) {
        sb.append(", ");
      }
    }
    sb.append(". ");
    sb.append("Winner of the Galactic Olympics was ");
    sb.append(athletes[0].getRealm().getEmpireName());
    sb.append(" from ");
    sb.append(athletes[0].getPlanetName());
    sb.append(". ");
    if (athletes.length > 1) {
      sb.append("Second of the Galactic Olympics was ");
      sb.append(athletes[1].getRealm().getEmpireName());
      sb.append(" from ");
      sb.append(athletes[1].getPlanetName());
      sb.append(". ");
    } else {
      sb.append("These Galactic Olympics were failure due that there were "
          + "only single athlete. ");
    }
    int noVotes = vote.getVotingAmounts(VotingChoice.VOTED_NO);
    if (noVotes > 0) {
      sb.append("There were ");
      sb.append(noVotes);
      sb.append(" realm");
      if (noVotes > 1) {
        sb.append("s");
      }
      sb.append(" which did not participate. ");
    }
    news.setNewsText(sb.toString());
    return news;
  }
  /**
   * Make Galactic sports news. New Galactic sports arena is being built.
   * @param builder PlayerInfo who is building
   * @param planet Where to build
   * @return NewsData
   */
  public static NewsData makeGalacticSportsNews(final PlayerInfo builder,
      final Planet planet) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    String position = ImageInstruction.POSITION_CENTER;
    String size = ImageInstruction.SIZE_HALF;
    instructions.addPlanet(position, planet.getImageInstructions(), size);
    instructions.addLogo(position, ImageInstruction.PLANET_SPORTS, size);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("UPCOMING GALACTIC SPORTS!");
        break;
      }
      case 1: {
        instructions.addText("GALACTIC SPORTS AT "
            + planet.getName().toUpperCase());
        break;
      }
      case 2: {
        instructions.addText("GALACTIC SPORTS EVENT BY "
            + builder.getEmpireName().toUpperCase());
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(builder.getEmpireName());
    sb.append(" is building new Galactic Sports arena to ");
    sb.append(planet.getName());
    sb.append("! ");
    sb.append(" When finished there will be arrange galatic sports event! ");
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make galactic sports news where event cannot be arranged
   * @param planet Where this was suppose to organize
   * @param buildingDestroyed True if building is no longer there
   * @return NewsData
   */
  public static NewsData makeNoGalacticSportsNews(final Planet planet,
      final boolean buildingDestroyed) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    String position = ImageInstruction.POSITION_CENTER;
    String size = ImageInstruction.SIZE_HALF;
    instructions.addPlanet(position, planet.getImageInstructions(), size);
    instructions.addLogo(position, ImageInstruction.PLANET_SPORTS, size);
    instructions.addLogo(position, ImageInstruction.BIG_BAN, size);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("GALACTIC SPORTS CANCELED!");
        break;
      }
      case 1: {
        instructions.addText("CRISIS AT GALACTIC SPORTS EVENT!");
        break;
      }
      case 2: {
        instructions.addText("CANCELLATION!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append("Galactic sports event at ");
    sb.append(planet.getName());
    sb.append(" is cancelled! ");
    if (planet.getPlanetPlayerInfo() == null) {
      sb.append(" Main reason for this cancellation is that ");
      sb.append("planet has no longer any population. ");
    }
    if (buildingDestroyed) {
      if (planet.getPlanetPlayerInfo() == null) {
        sb.append(" Also ");
      }
      sb.append(" Galactic sports center is no longer on ");
      sb.append(planet.getName());
      sb.append(". ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make news about building destroyed at planet due espionage.
   * This can be used also for terrorist attack new if killedPopulation
   * is non empty string.
   * @param planet Where this was suppose to organize
   * @param building Building which was destroyed
   * @param killedPopulation Text if population was killed.
   * @return NewsData
   */
  public static NewsData makeBuildingDestroyedNews(final Planet planet,
      final Building building, final String killedPopulation) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    String position = ImageInstruction.POSITION_CENTER;
    String size = ImageInstruction.SIZE_FULL;
    instructions.addPlanet(position, planet.getImageInstructions(), size);
    boolean terroristAttack = false;
    if (killedPopulation != null && !killedPopulation.isEmpty()) {
      terroristAttack = true;
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        if (terroristAttack) {
          instructions.addText("TERRORIST ATTACK!");
        } else {
          instructions.addText("BUILDING DESTROYED!");
        }
        break;
      }
      case 1: {
        if (terroristAttack) {
          instructions.addText(planet.getName().toUpperCase()
              + " UNDER TERRORISM!");
        } else {
          instructions.addText(building.getName().toUpperCase()
              + " DESTROYED!");
        }
        break;
      }
      case 2: {
        if (terroristAttack) {
          instructions.addText("TERRORIST ATTACK ON "
              + planet.getName().toUpperCase());
        } else {
          instructions.addText("DESTRUCTION OF "
             + building.getName().toUpperCase());
        }
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    if (terroristAttack) {
      sb.append("Terrorist attack on ");
      sb.append(planet.getName());
      sb.append("! ");
      sb.append(building.getName());
      sb.append(" was destroyed during attack! ");
    } else {
      sb.append(building.getName());
      sb.append(" destroyed at ");
      sb.append(planet.getName());
      sb.append("! ");
    }
    if (planet.getPlanetPlayerInfo() != null) {
      sb.append(planet.getPlanetPlayerInfo().getEmpireName());
      sb.append(" suspects this was due foreign espionage. ");
    }
    if (terroristAttack) {
      sb.append(killedPopulation);
    } else {
      sb.append("Non of the population of ");
      sb.append(planet.getName());
      sb.append(" were hurt during the destruction. ");
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
   * @param majorDeals Textual description of major deals that cause peace.
   * @param starYear Current star year.
   * @return NewsData
   */
  public static NewsData makePeaceNews(final PlayerInfo peaceMaker,
      final PlayerInfo acceptor, final Object meetingPlace,
      final String majorDeals, final int starYear) {
    return makePeaceNews(peaceMaker, acceptor, meetingPlace, majorDeals,
        starYear, null);
  }

  /**
   * Make Peace news. PeaceMaker makes peace offer to acceptor.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param peaceMaker Player who is make the peace offer
   * @param acceptor Player who is accepting
   * @param meetingPlace Where meeting happened, fleet or planet
   * @param majorDeals Textual description of major deals that cause peace.
   * @param starYear Current star year.
   * @param map StarMap, used only for alliance peace.
   * @return NewsData
   */
  public static NewsData makePeaceNews(final PlayerInfo peaceMaker,
      final PlayerInfo acceptor, final Object meetingPlace,
      final String majorDeals, final int starYear, final StarMap map) {
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
    if (map != null) {
      int acceptorIndex = map.getPlayerList().getIndex(acceptor);
      if (peaceMaker.getDiplomacy().isWar(acceptorIndex)) {
        PlayerInfo peaceMakerAlly = null;
        PlayerInfo acceptorAlly = null;
        boolean and = false;
        int parties = 2;
        int allyIndex = peaceMaker.getDiplomacy().getAllianceIndex();
        if (allyIndex != -1) {
          peaceMakerAlly = map.getPlayerByIndex(allyIndex);
        }
        allyIndex = acceptor.getDiplomacy().getAllianceIndex();
        if (allyIndex != -1) {
          acceptorAlly = map.getPlayerByIndex(allyIndex);
        }
        if (peaceMakerAlly != null) {
          sb.append("Peace treaty also involves ");
          sb.append(peaceMakerAlly.getEmpireName());
          parties++;
          and = true;
          peaceMakerAlly.getMissions().removeAttackAgainstPlayer(acceptor, map);
          acceptor.getMissions().removeAttackAgainstPlayer(peaceMakerAlly, map);
          if (acceptorAlly != null) {
            peaceMakerAlly.getMissions().removeAttackAgainstPlayer(acceptorAlly,
                map);
          }
        }
        if (acceptorAlly != null) {
          if (!and) {
            sb.append("Peace treaty also involves ");
            sb.append(acceptorAlly.getEmpireName());
          } else {
            sb.append(" and ");
            sb.append(acceptorAlly.getEmpireName());
          }
          parties++;
          peaceMaker.getMissions().removeAttackAgainstPlayer(acceptorAlly, map);
          acceptorAlly.getMissions().removeAttackAgainstPlayer(peaceMaker, map);
          if (peaceMakerAlly != null) {
            acceptorAlly.getMissions().removeAttackAgainstPlayer(peaceMakerAlly,
                map);
          }
        }
        if (parties > 2) {
          sb.append(". All ");
          if (parties == 3) {
            sb.append(" three");
          }
          if (parties == 4) {
            sb.append(" four");
          }
          sb.append(" realms make peace with each others! ");
        }
      }
    }
    if (majorDeals != null) {
      switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        sb.append("Peace did not come with cheap. ");
        sb.append(majorDeals);
        break;
      }
      case 1: {
        sb.append("Peace come with a cost. ");
        sb.append(majorDeals);
        break;
      }
      case 2: {
        sb.append("This peace happened due special deal. ");
        sb.append(majorDeals);
        break;
      }
    }
    }
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      sb.append("This meeting happened in ");
      sb.append(planet.getName());
      if (planet.getPlanetPlayerInfo() != null) {
        sb.append(", which is owned by ");
        sb.append(planet.getPlanetPlayerInfo().getEmpireName());
        sb.append(". ");
      }
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
    peaceMaker.appendStory(sb.toString(), starYear);
    acceptor.appendStory(sb.toString(), starYear);
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make major demand news. Demander makes peace offer to acceptor.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param demander Player who is make the demand
   * @param acceptor Player who is accepting
   * @param meetingPlace Where meeting happened, fleet or planet
   * @param majorDeals Textual description of major deals that cause peace.
   * @param starYear Current star year.
   * @return NewsData
   */
  public static NewsData makeMajorDemandNews(final PlayerInfo demander,
      final PlayerInfo acceptor, final Object meetingPlace,
      final String majorDeals, final int starYear) {
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
        instructions.addText("MAJOR DEMAND ACCEPTED!");
        break;
      }
      case 1: {
        instructions.addText("CRITICAL NEGOTIATIONS!");
        break;
      }
      case 2: {
        instructions.addText("MASSIVE DEMANDS!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(demander.getEmpireName());
    sb.append(" made ");
    switch (DiceGenerator.getRandom(2)) {
    case 0:
    default: {
      sb.append("massive demands ");
      break;
    }
    case 1: {
      sb.append("huge demands ");
      break;
    }
    case 2: {
      sb.append("great demands ");
      break;
    }
  }
    sb.append(acceptor.getEmpireName());
    sb.append("! ");
    sb.append(majorDeals);
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      sb.append("This meeting happened in ");
      sb.append(planet.getName());
      if (planet.getPlanetPlayerInfo() != null) {
        sb.append(", which is owned by ");
        sb.append(planet.getPlanetPlayerInfo().getEmpireName());
        sb.append(". ");
      }
    }
    Attitude attitude = demander.getAiAttitude();
    if (attitude == Attitude.AGGRESSIVE) {
      sb.append(demander.getEmpireName());
      sb.append(" is known about their aggressive behaviour, so ");
      sb.append("this request was expected! ");
    }
    if (attitude == Attitude.MILITARISTIC) {
      sb.append(demander.getEmpireName());
      sb.append(" militaristic pressure was surely affecting on this demand! ");
    }
    if (attitude == Attitude.EXPANSIONIST) {
      sb.append(demander.getEmpireName());
      sb.append(" is known expanding their territory! ");
    }
    demander.appendStory(sb.toString(), starYear);
    acceptor.appendStory(sb.toString(), starYear);
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make major gift news. Giver makes peace offer to acceptor.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param giver Player who is make the gift
   * @param acceptor Player who is accepting
   * @param meetingPlace Where meeting happened, fleet or planet
   * @param majorDeals Textual description of major deals that cause peace.
   * @param starYear Current star year.
   * @return NewsData
   */
  public static NewsData makeMajorGiftNews(final PlayerInfo giver,
      final PlayerInfo acceptor, final Object meetingPlace,
      final String majorDeals, final int starYear) {
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
        instructions.addText("PLANET BOUNTY ACCEPTED!");
        break;
      }
      case 1: {
        instructions.addText("CRITICAL NEGOTIATIONS!");
        break;
      }
      case 2: {
        instructions.addText("MASSIVE GIVEAWAY!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(giver.getEmpireName());
    sb.append(" made ");
    switch (DiceGenerator.getRandom(2)) {
    case 0:
    default: {
      sb.append("massive giveaway to ");
      break;
    }
    case 1: {
      sb.append("huge gift to ");
      break;
    }
    case 2: {
      sb.append("great bounty to ");
      break;
    }
  }
    sb.append(acceptor.getEmpireName());
    sb.append("! ");
    sb.append(majorDeals);
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      sb.append("This meeting happened in ");
      sb.append(planet.getName());
      if (planet.getPlanetPlayerInfo() != null) {
        sb.append(", which is owned by ");
        sb.append(planet.getPlanetPlayerInfo().getEmpireName());
        sb.append(". ");
      }
    }
    Attitude attitude = giver.getAiAttitude();
    if (attitude == Attitude.LOGICAL) {
      sb.append(giver.getEmpireName());
      sb.append(" is known about their logical behaviour, so ");
      sb.append("wanting peace is expected! ");
    }
    if (attitude == Attitude.MERCHANTICAL) {
      sb.append(giver.getEmpireName());
      sb.append(" is known about their trading skill, so ");
      sb.append("they are willing to trade planet for peace! ");
    }
    if (attitude == Attitude.DIPLOMATIC) {
      sb.append(giver.getEmpireName());
      sb.append(" diplomatic pressure was surely affecting on this deal! ");
    }
    if (attitude == Attitude.PEACEFUL) {
      sb.append(giver.getEmpireName());
      sb.append(" is known to be peaceful! ");
    }
    attitude = acceptor.getAiAttitude();
    if (attitude == Attitude.AGGRESSIVE) {
      sb.append(acceptor.getEmpireName());
      sb.append(" is known about their aggressive behaviour, so ");
      sb.append("interesting to se how long this peace will last! ");
    }
    if (attitude == Attitude.EXPANSIONIST) {
      sb.append(acceptor.getEmpireName());
      sb.append(" is known for expansionist behaviour, so ");
      sb.append("this is valueable deal for them! ");
    }
    giver.appendStory(sb.toString(), starYear);
    acceptor.appendStory(sb.toString(), starYear);
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
   * @param starYear current star year.
   * @return NewsData
   */
  public static NewsData makeTradeAllianceNews(final PlayerInfo offerer,
      final PlayerInfo acceptor, final Object meetingPlace,
      final int starYear) {
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
    }
    Attitude attitude = offerer.getAiAttitude();
    if (attitude == Attitude.MERCHANTICAL) {
      if (offerer.getRuler() == null) {
        sb.append(offerer.getEmpireName());
        sb.append(" is known about their interest to trade, so ");
      } else {
        sb.append(offerer.getRuler().getCallName());
        sb.append(" is known for ");
        sb.append(offerer.getRuler().getGender().getHisHer());
        sb.append(" interest to trade, so ");
      }
      sb.append("this trade alliance was expected! ");
    }
    if (attitude == Attitude.DIPLOMATIC) {
      if (offerer.getRuler() == null) {
        sb.append(offerer.getEmpireName());
      } else {
        sb.append(offerer.getRuler().getCallName());
      }
      sb.append(" diplomatic skills were surely effecting on "
          + "this trade alliance! ");
    }
    if (attitude == Attitude.PEACEFUL) {
      if (offerer.getRuler() == null) {
        sb.append(offerer.getEmpireName());
        sb.append(" is known about their peace loving. So this was expected! ");
      } else {
        sb.append(offerer.getRuler().getCallName());
        sb.append(" is known for ");
        sb.append(offerer.getRuler().getGender().getHisHer());
        sb.append(" peace loving. So this was expected! ");
      }
    }
    offerer.appendStory(sb.toString(), starYear);
    acceptor.appendStory(sb.toString(), starYear);
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make tie news about united galaxy tower race.
   * @param first First Realm
   * @param second Second Realm
   * @return NewsData
   */
  public static NewsData makeUnitedGalaxyTowerRaceTie(final PlayerInfo first,
      final PlayerInfo second) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instructions.addLogo(ImageInstruction.POSITION_CENTER,
        ImageInstruction.UNITED_GALAXY_TOWER, ImageInstruction.SIZE_HALF);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("TIE!");
        break;
      }
      case 1: {
        instructions.addText("EQUALLY STRONG!");
        break;
      }
      case 2: {
        instructions.addText("DRAW!");
        break;
      }
    }
    instructions.addText(first.getEmpireName());
    instructions.addText(second.getEmpireName());
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(first.getEmpireName());
    sb.append(" has equally same amount of United Galaxy Towers as ");
    sb.append(second.getEmpireName());
    sb.append("! ");
    sb.append("Race of Galactic secretary is continuing... ");
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make secretary of galaxy news.
   * @param realm Realm which became secretary
   * @param starYear Star Year
   * @return NewsData
   */
  public static NewsData makeSecretaryOfGalaxyNews(final PlayerInfo realm,
      final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    instructions.addLogo(ImageInstruction.POSITION_CENTER,
        ImageInstruction.UNITED_GALAXY_TOWER, ImageInstruction.SIZE_FULL);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("NEW SECRETARY!");
        break;
      }
      case 1: {
        instructions.addText("GALAXY HAS THE SECRETARY!");
        break;
      }
      case 2: {
        instructions.addText("GALACTIC SECRETARY!");
        break;
      }
    }
    instructions.addText(realm.getEmpireName());
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(realm.getEmpireName());
    sb.append(" has become the galactic secretary! ");
    sb.append("Race of Galactic secretary is over,");
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        sb.append(" but who is going to be the second candidate?");
        break;
      }
      case 1: {
        sb.append(" but next thing is to focus on galactic politics. ");
        break;
      }
      case 2: {
        sb.append(" but it is not sure yet who is going to be the ruler"
            + " of the galaxy... ");
        break;
      }
    }
    news.setNewsText(sb.toString());
    realm.appendStory(sb.toString(), starYear);
    return news;
  }
  /**
   * Make alliance news. Offerer makes alliance offer to acceptor.
   * This diplomatic meeting happened in meeting place which
   * can be planet or fleet.
   * @param offerer Player who is make trade alliance
   * @param acceptor Player who is accepting
   * @param meetingPlace Where meeting happened, fleet or planet
   * @param starYear Start year when news happens.
   * @return NewsData
   */
  public static NewsData makeAllianceNews(final PlayerInfo offerer,
      final PlayerInfo acceptor, final Object meetingPlace,
      final int starYear) {
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
    }
    Attitude attitude = offerer.getAiAttitude();
    if (attitude == Attitude.DIPLOMATIC) {
      if (offerer.getRuler() == null) {
        sb.append(offerer.getEmpireName());
      } else {
        sb.append(offerer.getRuler().getCallName());
      }
      sb.append(" diplomatic skills were surely effecting on "
          + "this alliance! ");
    }
    if (attitude == Attitude.PEACEFUL) {
      sb.append(offerer.getEmpireName());
      sb.append(" is known about their peace loving. So this was expected! ");
    }
    offerer.appendStory(sb.toString(), starYear);
    acceptor.appendStory(sb.toString(), starYear);
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make trade embargo news
   * @param offerer Realm which suggested the trade embargo
   * @param acceptor Realm which agreed to the trade embargo
   * @param embargoed Realm which was embargoed
   * @param meetingPlace Where meeting happened
   * @param starYear current Star year.
   * @return NewsData with embargo news
   */
  public static NewsData makeTradeEmbargoNews(final PlayerInfo offerer,
      final PlayerInfo acceptor, final PlayerInfo embargoed,
      final Object meetingPlace, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    if (meetingPlace instanceof Planet) {
      Planet planet = (Planet) meetingPlace;
      instructions.addPlanet(ImageInstruction.POSITION_CENTER,
          planet.getImageInstructions(),
          ImageInstruction.SIZE_FULL);
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("TRADE WARS!");
        break;
      }
      case 1: {
        instructions.addText("TRADE EMBARGO!");
        break;
      }
      case 2: {
        instructions.addText("GALACTIC TRADE EMBARGO!");
        break;
      }
    }
    instructions.addText(embargoed.getEmpireName());
    instructions.addRelationSymbol(ImageInstruction.TRADE_EMBARGO);
    instructions.addText(offerer.getEmpireName());
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append("At star year ");
    sb.append(starYear);
    sb.append(" ");
    sb.append(embargoed.getEmpireName());
    sb.append(" was placed on trade embargo by ");
    sb.append(offerer.getEmpireName());
    sb.append(" and ");
    sb.append(acceptor.getEmpireName());
    sb.append("! Trade embargo bans all trades towards ");
    sb.append(embargoed.getEmpireName());
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
    }
    Attitude attitude = embargoed.getAiAttitude();
    if (attitude == Attitude.AGGRESSIVE) {
      sb.append(embargoed.getEmpireName());
      sb.append(" aggressive style was surely affect to this outcome! ");
    }
    if (attitude == Attitude.MILITARISTIC) {
      sb.append(embargoed.getEmpireName());
      sb.append(" militaristic acts might have caused this outcome! ");
    }
    attitude = offerer.getAiAttitude();
    if (attitude == Attitude.DIPLOMATIC) {
      sb.append(offerer.getEmpireName());
      sb.append(" diplomatic skills were surely persuade ");
      sb.append(acceptor.getEmpireName());
      sb.append(" to accept this trade embargo! ");
    }
    offerer.appendStory(sb.toString());
    acceptor.appendStory(sb.toString());
    embargoed.appendStory(sb.toString());
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
   * @param starYear current starYear
   * @return NewsData
   */
  public static NewsData makeDefensivePactNews(final PlayerInfo offerer,
      final PlayerInfo acceptor, final Object meetingPlace,
      final int starYear) {
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
    }
    Attitude attitude = offerer.getAiAttitude();
    if (attitude == Attitude.DIPLOMATIC) {
      if (offerer.getRuler() == null) {
        sb.append(offerer.getEmpireName());
      } else {
        sb.append(offerer.getRuler().getCallName());
      }
      sb.append(" diplomatic skills were surely effecting on "
          + "this defensive  pact! ");
    }
    if (attitude == Attitude.PEACEFUL) {
      if (offerer.getRuler() == null) {
        sb.append(offerer.getEmpireName());
        sb.append(" is known about their peace loving. So this was expected! ");
      } else {
        sb.append(offerer.getRuler().getCallName());
        sb.append(" is known for ");
        sb.append(offerer.getRuler().getGender().getHisHer());
        sb.append(" peace loving. So this was expected! ");
      }
    }
    offerer.appendStory(sb.toString(), starYear);
    acceptor.appendStory(sb.toString(), starYear);
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
          + " in Stars. ");
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
          sb.append("See full report from Statiscis view. ");
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
          sb.append("See full report for Statiscis view. ");
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
          sb.append("See full report from Statiscis view. ");
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
          sb.append("See full report from Statiscis view. ");
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
          sb.append("See full report from Statiscis view. ");
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
          sb.append("See full report from Statiscis view. ");
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
          sb.append("See full report from Statiscis view. ");
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
            + " statistical research about Realms in Stars. ");
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
   * @param nukeText Null if planet was not nuked.
   *        Otherwise description about nuke.
   * @param starYear current star year.
   * @return NewsData
   */
  public static NewsData makePlanetConqueredNews(final PlayerInfo attacker,
      final PlayerInfo defender, final Planet planet, final String nukeText,
      final int starYear) {
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
        instructions.addText("TRIUMPH ON "
            + planet.getName().toUpperCase() + "!");
        break;
      }
      case 2: {
        instructions.addText(planet.getName().toUpperCase()
            + " IS OVERTHROWN!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(attacker.getEmpireName());
    sb.append(" made massive attack on ");
    sb.append(planet.getName());
    if (defender != null) {
      sb.append(". Defender ");
      sb.append(defender.getEmpireName());
      sb.append(" was defeated eventually. ");
    } else {
      sb.append(". ");
    }
    if (nukeText != null) {
      sb.append(" ");
      sb.append(nukeText);
    } else {
      if (defender == null) {
        sb.append(" Planet population was killed by massive bombings by ");
        sb.append(attacker.getEmpireName());
        sb.append(". ");
      } else {
        sb.append(" Last remains of ");
        sb.append(defender.getRace().getNameSingle());
        sb.append(" population was killed by ground troops of ");
        sb.append(attacker.getEmpireName());
        sb.append(". ");
        if (attacker.getRace().hasTrait(TraitIds.ASSIMILATION)
            && !defender.getRace().isRoboticRace()) {
          sb.append("Recently dead population is assimilated into ");
          sb.append(attacker.getEmpireName());
          sb.append(" population. ");
          sb.append("This process may reveal technology secrets too. ");
        }
      }
    }
    attacker.appendStory(sb.toString(), starYear);
    if (defender != null) {
      defender.appendStory(sb.toString(), starYear);
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Attacker bombss defender planet
   * @param attacker Player who is bombing
   * @param defender Player who is defending
   * @param planet Which planet was conquered
   * @param nukeText Null if planet was not nuked.
   *        Otherwise description about nuke.
   * @param starYear current star year.
   * @return NewsData
   */
  public static NewsData makePlanetBombedNews(final PlayerInfo attacker,
      final PlayerInfo defender, final Planet planet, final String nukeText,
      final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    instructions.addPlanet(ImageInstruction.POSITION_CENTER,
        planet.getImageInstructions(),
        ImageInstruction.SIZE_FULL);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("PLANET BOMBED!");
        break;
      }
      case 1: {
        instructions.addText("DEVASTATION ON "
            + planet.getName().toUpperCase() + "!");
        break;
      }
      case 2: {
        instructions.addText(planet.getName().toUpperCase() + " IS BOMBED!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(attacker.getEmpireName());
    sb.append(" made massive orbital bombings on ");
    sb.append(planet.getName());
    if (defender != null && planet.getPlanetPlayerInfo() == null) {
      sb.append(". Defender ");
      sb.append(defender.getEmpireName());
      sb.append(" was defeated eventually. ");
    } else {
      sb.append(". ");
    }
    if (nukeText != null) {
      sb.append(" ");
      sb.append(nukeText);
    }
    if (defender != null && planet.getPlanetPlayerInfo() == defender) {
      sb.append(" Not all ");
      sb.append(defender.getRace().getNameSingle());
      sb.append(" population were killed during the bombing. ");
    }
    if (defender != null && planet.getPlanetPlayerInfo() == null) {
      sb.append(" all ");
      sb.append(defender.getRace().getNameSingle());
      sb.append(" population were killed during the bombing. ");
    }
    attacker.appendStory(sb.toString(), starYear);
    if (defender != null) {
      defender.appendStory(sb.toString(), starYear);
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Attacker destroys defender's orbital
   * @param attacker Player who is bombing
   * @param defender Player who is defending
   * @param planet Which planet where orbital was.
   * @param attackerPrivateer Attacker is privateer?
   * @return NewsData
   */
  public static NewsData makeOrbitalDestroyedNews(final PlayerInfo attacker,
      final PlayerInfo defender, final Planet planet,
      final boolean attackerPrivateer) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    if (DiceGenerator.getBoolean()) {
      instructions.addPlanet(ImageInstruction.POSITION_CENTER,
          planet.getImageInstructions(),
          ImageInstruction.SIZE_FULL);
      if (DiceGenerator.getBoolean()) {
        instructions.addLogo(ImageInstruction.POSITION_LEFT,
            ImageInstruction.BIG_ORBITAL, ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_LEFT,
            ImageInstruction.BIG_EXPLOSION, ImageInstruction.SIZE_FULL);
      } else {
        instructions.addLogo(ImageInstruction.POSITION_RIGHT,
            ImageInstruction.BIG_ORBITAL, ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_RIGHT,
            ImageInstruction.BIG_EXPLOSION, ImageInstruction.SIZE_FULL);
      }
    } else {
      if (DiceGenerator.getBoolean()) {
        instructions.addPlanet(ImageInstruction.POSITION_LEFT,
            planet.getImageInstructions(),
            ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_ORBITAL, ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_EXPLOSION, ImageInstruction.SIZE_FULL);
      } else {
        instructions.addPlanet(ImageInstruction.POSITION_RIGHT,
            planet.getImageInstructions(),
            ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_ORBITAL, ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_EXPLOSION, ImageInstruction.SIZE_FULL);
      }
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("ORBITAL DESTROYED!");
        break;
      }
      case 1: {
        instructions.addText("DESTRUCTION ON "
            + planet.getName().toUpperCase() + "!");
        break;
      }
      case 2: {
        instructions.addText(planet.getName().toUpperCase() + " IS DESTROYED!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    if (attackerPrivateer) {
      sb.append("Privateer fleet");
    } else {
      sb.append(attacker.getEmpireName());
    }
    sb.append(" made massive attack on ");
    sb.append(planet.getName());
    if (defender != null) {
      sb.append(". Defender ");
      sb.append(defender.getEmpireName());
      sb.append(" fleet"
          + " and orbital around the planet was defeated eventually. ");
    } else {
      sb.append(". ");
    }
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Attacker destroyed alteirian orbital
   * @param attacker Player who is attacking
   * @param defender Player who is defending
   * @param planet Which planet was conquered
   * @param attackerPrivateer Attacker is privateer?
   * @return NewsData
   */
  public static NewsData makeAlteirianLoseOrbitalNews(
      final PlayerInfo attacker, final PlayerInfo defender,
      final Planet planet, final boolean attackerPrivateer) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    if (DiceGenerator.getBoolean()) {
      instructions.addPlanet(ImageInstruction.POSITION_CENTER,
          planet.getImageInstructions(),
          ImageInstruction.SIZE_FULL);
      if (DiceGenerator.getBoolean()) {
        instructions.addLogo(ImageInstruction.POSITION_LEFT,
            ImageInstruction.BIG_ORBITAL, ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_LEFT,
            ImageInstruction.BIG_EXPLOSION, ImageInstruction.SIZE_FULL);
      } else {
        instructions.addLogo(ImageInstruction.POSITION_RIGHT,
            ImageInstruction.BIG_ORBITAL, ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_RIGHT,
            ImageInstruction.BIG_EXPLOSION, ImageInstruction.SIZE_FULL);
      }
    } else {
      if (DiceGenerator.getBoolean()) {
        instructions.addPlanet(ImageInstruction.POSITION_LEFT,
            planet.getImageInstructions(),
            ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_ORBITAL, ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_EXPLOSION, ImageInstruction.SIZE_FULL);
      } else {
        instructions.addPlanet(ImageInstruction.POSITION_RIGHT,
            planet.getImageInstructions(),
            ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_ORBITAL, ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_EXPLOSION, ImageInstruction.SIZE_FULL);
      }
    }
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("Critical orbital lost!");
        break;
      }
      case 1: {
        instructions.addText("DEVASTATION ON "
            + planet.getName().toUpperCase() + "!");
        break;
      }
      case 2: {
        instructions.addText("Orbital and all population lost!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    if (attackerPrivateer) {
      sb.append("Privateer fleet");
    } else {
      sb.append(attacker.getEmpireName());
    }
    sb.append(" made massive attack on ");
    sb.append(planet.getName());
    if (defender != null) {
      sb.append(". Defender ");
      sb.append(defender.getEmpireName());
      sb.append(" was defeated eventually. ");
    }
    sb.append(" All population who lived in orbital are gone now. ");
    if (planet.getGovernor() != null) {
      sb.append(planet.getGovernor().getCallName());
      sb.append(" was also killed during destruction of orbital.");
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
      sb.append("See full scoring situation from Statiscis view. ");
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
      sb.append("See full scoring situation from Statiscis view. ");
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
    int maxPlayer = players.getCurrentMaxRealms();
    // First let's do alliance rows
    for (int i = 0; i < maxPlayer; i++) {
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
   * @param broadcasters Boolean list of realms which are capable of
   *        broadcasting their culture.
   * @return NewsData or null
   */
  public static NewsData makeCulturalVictoryNewsAtEnd(final StarMap map,
      final boolean[] broadcasters) {
    int limit = StarMapUtilities.calculateCultureScoreLimit(
        map.getMaxX(), map.getMaxY(), map.getScoreVictoryTurn(),
        map.getScoreCulture());
    if (limit == -1) {
      return null;
    }
    PlayerInfo winner1 = null;
    PlayerInfo winner2 = null;
    NewsCorpData tmpData = new NewsCorpData(
        map.getPlayerList().getCurrentMaxRealms());
    NewsData news = null;
    tmpData.calculateCulture(map.getPlanetList(), map.getPlayerList());
    ScoreBoard board = createScoreBoard(tmpData.getCultural(),
        map.getPlayerList());
    board.sort();
    Row winner = board.getRow(0);
    Row second = board.getRow(1);
    boolean broadcaster = false;
    if (winner.getRealm() >= 0 && winner.getRealm() < broadcasters.length) {
      broadcaster = broadcasters[winner.getRealm()];
    }
    PlayerInfo winningRealm = map.getPlayerByIndex(winner.getRealm());
    int admires = 0;
    int admireLimit = (map.getPlayerList().getCurrentMaxRealms() - 1) / 2;
    if (admireLimit < 1) {
      admireLimit = 1;
    }
    if (winningRealm != null) {
      admires = StarMapUtilities.getNumberOfAdmires(winner.getRealm(),
          map.getPlayerList());
    }
    if (winner.getScore() >= limit && broadcaster && admires >= admireLimit) {
      news = new NewsData();
      ImageInstruction instructions = new ImageInstruction();
      news.setImageInstructions(instructions.build());
      StringBuilder sb = new StringBuilder();
      if (!winner.isAlliance()) {
        PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
        winner1 = info;
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
          sb.append("Second greatest culture has alliance of ");
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
        winner1 = info;
        winner2 = info2;
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE GREATEST CULTURAL ALLIANCE!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append("Alliance of ");
        sb.append(info.getEmpireName());
        sb.append(" and ");
        sb.append(info2.getEmpireName());
        sb.append(" has the greatest culture in whole galaxy! ");
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
          sb.append("Second greatest culture has alliance of ");
          sb.append(info.getEmpireName());
          sb.append(" and ");
          sb.append(info2.getEmpireName());
          sb.append(" ! ");
          sb.append("This alliance gained culture of ");
          sb.append(second.getScore());
          sb.append("! ");
        }
      }
      news.setImageInstructions(instructions.build());
      news.setNewsText(sb.toString());
      if (winner1 != null) {
        winner1.appendStory(sb.toString(), map.getStarYear());
      }
      if (winner2 != null) {
        winner2.appendStory(sb.toString(), map.getStarYear());
      }
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
    int limit = map.getPlayerList().getCurrentMaxRealms();
    if (limit < 4) {
      return null;
    }
    limit = limit / 2;
    if (limit < 3) {
      limit = 3;
    }
    PlayerInfo winner1 = null;
    PlayerInfo winner2 = null;
    NewsCorpData tmpData = new NewsCorpData(
        map.getPlayerList().getCurrentMaxRealms());
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
        winner1 = info;
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE DOMINATION VICTORY!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append(info.getEmpireName());
        sb.append(" has the half of the home worlds in the galaxy! ");
        sb.append("No other realm has power to challenge ");
        sb.append(info.getEmpireName());
        sb.append(". ");
      } else {
        PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
        PlayerInfo info2 = map.getPlayerByIndex(winner.getAllianceRealm());
        winner1 = info;
        winner2 = info2;
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE DOMINATING ALLIANCE!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append("Alliance of ");
        sb.append(info.getEmpireName());
        sb.append(" and ");
        sb.append(info2.getEmpireName());
        sb.append(" has the half of the home worlds in the galaxy! ");
        sb.append("No other realm has power to challenge this alliance. ");
      }
      news.setImageInstructions(instructions.build());
      news.setNewsText(sb.toString());
      if (winner1 != null) {
        winner1.appendStory(sb.toString(), map.getStarYear());
      }
      if (winner2 != null) {
        winner2.appendStory(sb.toString(), map.getStarYear());
      }
    }
    return news;
  }

  /**
   * Make News when game is ending for scientific victory.
   * Returns null if scientific victory is not achieved
   * @param map StarMap contains Planet and playerlist
   * @return NewsData or null
   */
  public static NewsData makeScientificVictoryNewsAtEnd(final StarMap map) {
    int limit = map.getScoreResearch();
    if (limit == 0) {
      return null;
    }
    PlayerInfo winner = null;
    PlayerInfo winner2 = null;
    for (Planet planet : map.getPlanetList()) {
      int count = 0;
      if (planet.getPlanetType() == PlanetTypes.ARTIFICIALWORLD1) {
        count++;
      }
      for (Building building : planet.getBuildingList()) {
        if (building.getScientificAchievement()) {
          count++;
        }
      }
      if (count >= limit) {
        winner = planet.getPlanetPlayerInfo();
      }
    }
    NewsData news = null;
    if (winner != null) {
      news = new NewsData();
      ImageInstruction instructions = new ImageInstruction();
      news.setImageInstructions(instructions.build());
      StringBuilder sb = new StringBuilder();
      if (winner.getDiplomacy().getAllianceIndex() == -1) {
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE SCIENTIFIC VICTORY!");
        instructions.addText(winner.getEmpireName());
        instructions.addImage(winner.getRace().getNameSingle());
        sb.append(winner.getEmpireName());
        sb.append(" has created the most advanced world in the galaxy! ");
        sb.append("No other realm has technology to challenge ");
        sb.append(winner.getEmpireName());
        sb.append(". ");
      } else {
        PlayerInfo info = winner;
        PlayerInfo info2 = map.getPlayerByIndex(
            winner.getDiplomacy().getAllianceIndex());
        winner2 = info2;
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE SCIENTIFIC ALLIANCE!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append("Alliance of ");
        sb.append(info.getEmpireName());
        sb.append(" and ");
        sb.append(info2.getEmpireName());
        sb.append(" has created the most advanced world in the galaxy! ");
        sb.append("No other realm technology to challenge this alliance. ");
      }
      news.setImageInstructions(instructions.build());
      news.setNewsText(sb.toString());
      winner.appendStory(sb.toString(), map.getStarYear());
      if (winner2 != null) {
        winner2.appendStory(sb.toString(), map.getStarYear());
      }
    }
    return news;
  }

  /**
   * Make News when game is ending for diplomatic victory.
   * Returns null if diplomatic victory is not achieved
   * @param map StarMap contains Planet and playerlist
   * @return NewsData or null
   */
  public static NewsData makeDiplomaticVictoryNewsAtEnd(final StarMap map) {
    int limit = map.getScoreDiplomacy();
    if (limit == 0) {
      return null;
    }
    PlayerInfo winner = null;
    PlayerInfo winner2 = null;
    PlayerInfo second = null;
    int votedWinner = 0;
    int votedSecond = 0;
    int noVotes = 0;
    int yesVotes = 0;
    int abstainVotes = 0;
    boolean removeRulerVote = false;
    boolean hasVoted = false;
    StringBuilder votingString = new StringBuilder();
    for (Vote vote : map.getVotes().getVotes()) {
      if (vote.getType() == VotingType.RULER_OF_GALAXY
          && vote.getTurnsToVote() == 0) {
        hasVoted = true;
        for (int i = 0; i < map.getPlayerList().getCurrentMaxRealms(); i++) {
          if (vote.getNumberOfVotes(i) == 0) {
            continue;
          }
          if (vote.getChoice(i) == VotingChoice.VOTED_NO) {
            noVotes = noVotes + vote.getNumberOfVotes(i);
            String realm = map.getPlayerByIndex(
                map.getVotes().getSecondCandidate()).getEmpireName();
            votingString.append(map.getPlayerByIndex(i).getEmpireName());
            votingString.append(" voted ");
            votingString.append(realm);
            votingString.append(" with ");
            votingString.append(vote.getNumberOfVotes(i));
            votingString.append(" votes.");
          }
          if (vote.getChoice(i) == VotingChoice.VOTED_YES) {
            yesVotes = yesVotes + vote.getNumberOfVotes(i);
            String realm = map.getPlayerByIndex(
                map.getVotes().getFirstCandidate()).getEmpireName();
            votingString.append(map.getPlayerByIndex(i).getEmpireName());
            votingString.append(" voted ");
            votingString.append(realm);
            votingString.append(" with ");
            votingString.append(vote.getNumberOfVotes(i));
            votingString.append(" votes.");
          }
          if (vote.getChoice(i) == VotingChoice.ABSTAIN) {
            abstainVotes = abstainVotes + vote.getNumberOfVotes(i);
            votingString.append(map.getPlayerByIndex(i).getEmpireName());
            votingString.append(" abstain voting");
            votingString.append(" with ");
            votingString.append(vote.getNumberOfVotes(i));
            votingString.append(" votes.");
          }
          votingString.append("\n");
        }
        VotingChoice choice = VotingChoice.NOT_VOTED;
        if (yesVotes + noVotes > abstainVotes) {
          // Enough voting that voting is accepted
          if (yesVotes > noVotes) {
            choice = VotingChoice.VOTED_YES;
          }
          if (yesVotes < noVotes) {
            choice = VotingChoice.VOTED_NO;
          }
        }
        if (choice == VotingChoice.VOTED_YES) {
          winner = map.getPlayerByIndex(map.getVotes().getFirstCandidate());
          second = map.getPlayerByIndex(map.getVotes().getSecondCandidate());
          votedWinner = vote.getVotingAmounts(VotingChoice.VOTED_YES);
          votedSecond = vote.getVotingAmounts(VotingChoice.VOTED_NO);
        } else if (choice == VotingChoice.VOTED_NO) {
          int index = map.getVotes().getSecondCandidate();
          winner = map.getPlayerByIndex(index);
          second = map.getPlayerByIndex(map.getVotes().getFirstCandidate());
          votedWinner = vote.getVotingAmounts(VotingChoice.VOTED_NO);
          votedSecond = vote.getVotingAmounts(VotingChoice.VOTED_YES);
        } else {
          winner = null;
          second = null;
          removeRulerVote = true;
          votedWinner = vote.getVotingAmounts(VotingChoice.VOTED_YES);
          votedSecond = vote.getVotingAmounts(VotingChoice.VOTED_NO);
        }
      }
    }
    NewsData news = null;
    if (hasVoted && winner == null && second == null) {
      news = new NewsData();
      ImageInstruction instructions = new ImageInstruction();
      StringBuilder sb = new StringBuilder();
      instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
      instructions.addText("NO RULER VOTED YET!");
      sb.append(votingString.toString());
      sb.append("\n\n");
      sb.append("Neither of the voting party gained enough support and"
          + " voting continues...");
      news.setImageInstructions(instructions.build());
      news.setNewsText(sb.toString());
    }
    if (hasVoted && winner != null && second != null) {
      news = new NewsData();
      ImageInstruction instructions = new ImageInstruction();
      news.setImageInstructions(instructions.build());
      StringBuilder sb = new StringBuilder();
      if (winner.getDiplomacy().getAllianceIndex() == -1) {
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE DIPLOMATIC VICTORY!");
        instructions.addText(winner.getEmpireName());
        instructions.addImage(winner.getRace().getNameSingle());
        sb.append(votingString.toString());
        sb.append("\n\n");
        sb.append(winner.getEmpireName());
        sb.append(" has successfully win election to be Galactic ruler! ");
        sb.append("This voting was arranged across the whole galaxy."
            + " Second candidate for the Galactic rules was ");
        sb.append(second.getEmpireName());
        sb.append(". ");
        int winnerPerCent = votedWinner * 100 / (votedWinner + votedSecond);
        int secondPerCent = votedSecond * 100 / (votedWinner + votedSecond);
        sb.append(winner.getEmpireName());
        sb.append(" got ");
        sb.append(winnerPerCent);
        sb.append(" per cent of votes. ");
        sb.append(second.getEmpireName());
        sb.append(" got ");
        sb.append(secondPerCent);
        sb.append(" per cent of votes. ");
      } else {
        PlayerInfo info = winner;
        PlayerInfo info2 = map.getPlayerByIndex(
            winner.getDiplomacy().getAllianceIndex());
        winner2 = info2;
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE DIPLOMATIC ALLIANCE!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append(votingString.toString());
        sb.append("\n\n");
        sb.append("Alliance of ");
        sb.append(info.getEmpireName());
        sb.append(" and ");
        sb.append(info2.getEmpireName());
        sb.append(" has successfully win election to be Galactic ruler! ");
        sb.append("This voting was arranged across the whole galaxy."
            + " Second candidate for the Galactic rules was ");
        sb.append(second.getEmpireName());
        sb.append(". ");
        int winnerPerCent = votedWinner * 100 / (votedWinner + votedSecond);
        int secondPerCent = votedSecond * 100 / (votedWinner + votedSecond);
        sb.append(winner.getEmpireName());
        sb.append(" got ");
        sb.append(winnerPerCent);
        sb.append(" per cent of votes. ");
        sb.append(second.getEmpireName());
        sb.append(" got ");
        sb.append(secondPerCent);
        sb.append(" per cent of votes. ");
      }
      news.setImageInstructions(instructions.build());
      news.setNewsText(sb.toString());
      winner.appendStory(sb.toString(), map.getStarYear());
      if (winner2 != null) {
        winner2.appendStory(sb.toString(), map.getStarYear());
      }
    }
    if (removeRulerVote) {
      map.getVotes().removeRulerVote();
    }
    return news;
  }

  /**
   * Make News when game is ending for population victory.
   * Returns null if population victory is not achieved
   * @param map StarMap contains Planet and playerlist
   * @return NewsData or null
   */
  public static NewsData makePopulationVictoryNewsAtEnd(final StarMap map) {
    int limit = map.getScorePopulation();
    if (limit == 0 || map.getTurn() < 100) {
      return null;
    }
    PlayerInfo winner2 = null;
    switch (map.getScorePopulation()) {
      case 1: limit = 40; break;
      case 2: limit = 50; break;
      case 3: limit = 60; break;
      case 4: limit = 70; break;
      default: limit = 50; break;
    }
    int[] population = new int[map.getPlayerList().getCurrentMaxRealms()];
    int galaxyPopulation = 0;
    for (int i = 0; i < map.getPlanetList().size(); i++) {
      Planet planet = map.getPlanetList().get(i);
      if (planet.getPlanetPlayerInfo() != null
          && planet.getPlanetOwnerIndex() < population.length) {
        galaxyPopulation = galaxyPopulation + planet.getTotalPopulation();
        population[planet.getPlanetOwnerIndex()] =
            population[planet.getPlanetOwnerIndex()]
            + planet.getTotalPopulation();
      }
    }
    if (galaxyPopulation == 0) {
      return null;
    }
    int biggestValue = 0;
    PlayerInfo winner = null;
    for (int i = 0; i < population.length; i++) {
      int value = population[i] * 100 / galaxyPopulation;
      PlayerInfo info = map.getPlayerList().getPlayerInfoByIndex(i);
      if (info != null) {
        int allianceIndex = info.getDiplomacy().getAllianceIndex();
        if (allianceIndex != -1) {
          int bonus = population[allianceIndex] * 100 / galaxyPopulation;
          value = value + bonus;
        }
        if (value >= limit && value > biggestValue) {
          winner = info;
          biggestValue = value;
        }
      }
    }
    NewsData news = null;
    if (winner != null) {
      news = new NewsData();
      ImageInstruction instructions = new ImageInstruction();
      news.setImageInstructions(instructions.build());
      StringBuilder sb = new StringBuilder();
      if (winner.getDiplomacy().getAllianceIndex() == -1) {
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE POPULATION VICTORY!");
        instructions.addText(winner.getEmpireName());
        instructions.addImage(winner.getRace().getNameSingle());
        sb.append(winner.getEmpireName());
        sb.append(" has ");
        sb.append(biggestValue);
        sb.append(" percent of the galaxy population.");
        sb.append(" No other realm has enough population to challenge ");
        sb.append(winner.getEmpireName());
        sb.append(". ");
      } else {
        PlayerInfo info = winner;
        PlayerInfo info2 = map.getPlayerByIndex(
            winner.getDiplomacy().getAllianceIndex());
        winner2 = info2;
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addText("THE POPULATION ALLIANCE!");
        instructions.addText(info.getEmpireName());
        instructions.addImage(info.getRace().getNameSingle());
        sb.append("Alliance of ");
        sb.append(info.getEmpireName());
        sb.append(" and ");
        sb.append(info2.getEmpireName());
        sb.append(" has ");
        sb.append(biggestValue);
        sb.append(" percent of the galaxy population.");
        sb.append(" No other realm or alliance has enough population"
            + " to challenge these two. ");
      }
      news.setImageInstructions(instructions.build());
      news.setNewsText(sb.toString());
      winner.appendStory(sb.toString(), map.getStarYear());
      if (winner2 != null) {
        winner2.appendStory(sb.toString(), map.getStarYear());
      }
    }
    return news;
  }

  /**
   * Make News when more space pirates appear
   * @param map StarMap contains NewsCorpData and playerlist
   * @return NewsData
   */
  public static NewsData makeSpacePiratesNews(final StarMap map) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    StringBuilder sb = new StringBuilder();
    PlayerInfo pirates = map.getPlayerList().getSpacePiratePlayer();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    int value = DiceGenerator.getRandom(2);
    switch (value) {
      case 0: {
        instructions.addText("SPACE PIRATES SEEN!");
        break;
      }
      case 1: {
        instructions.addText("SPACE PIRATES SPOTTED IN GALAXY!");
        break;
      }
      case 2: {
        instructions.addText("YARR! SPACE PIRATES!");
        break;
      }
      default: {
        instructions.addText("SPACE PIRATES SEEN!");
        break;
      }
    }
    instructions.addImage(pirates.getRace().getNameSingle());
    sb.append("More and more space pirates are seen in galaxy attacking"
        + " on cargo ships and destroying star bases. Where are privateers"
        + " coming from?");
    news.setImageInstructions(instructions.build());
    news.setNewsText(sb.toString());
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
    PlayerInfo winner1 = null;
    PlayerInfo winner2 = null;
    Row winner = board.getRow(0);
    Row second = board.getRow(1);
    if (!winner.isAlliance()) {
      PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
      winner1 = info;
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
        sb.append(" ! ");
      } else {
        info = map.getPlayerByIndex(second.getRealm());
        PlayerInfo info2 = map.getPlayerByIndex(second.getAllianceRealm());
        sb.append("Second greatest is alliance of ");
        sb.append(info.getEmpireName());
        sb.append(" and ");
        sb.append(info2.getEmpireName());
        sb.append(" ! ");
      }
    } else {
      PlayerInfo info = map.getPlayerByIndex(winner.getRealm());
      PlayerInfo info2 = map.getPlayerByIndex(winner.getAllianceRealm());
      winner1 = info;
      winner2 = info2;
      instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
      instructions.addText("THE GREATEST ALLIANCE ALL TIME!");
      instructions.addText(info.getEmpireName());
      instructions.addImage(info.getRace().getNameSingle());
      sb.append("Alliance of ");
      sb.append(info.getEmpireName());
      sb.append(" and ");
      sb.append(info2.getEmpireName());
      sb.append(" is the greatest in whole galaxy! ");
      if (!second.isAlliance()) {
        info = map.getPlayerByIndex(second.getRealm());
        sb.append("Second greatest realm is ");
        sb.append(info.getEmpireName());
        sb.append(" ! ");
      } else {
        info = map.getPlayerByIndex(second.getRealm());
        info2 = map.getPlayerByIndex(second.getAllianceRealm());
        sb.append("Second greatest is alliance of ");
        sb.append(info.getEmpireName());
        sb.append(" and ");
        sb.append(info2.getEmpireName());
        sb.append(" ! ");
      }
    }
    news.setImageInstructions(instructions.build());
    news.setNewsText(sb.toString());
    if (winner1 != null) {
      winner1.appendStory(sb.toString(), map.getStarYear());
    }
    if (winner2 != null) {
      winner2.appendStory(sb.toString(), map.getStarYear());
    }
    return news;
  }

  /**
   * Make news about deadly virus on planet.
   * @param planet Planet where virus is found.
   * @param spreader Possible realm who is spreading it. Can be null.
   * @param starYear Star year when event happens.
   * @return NewsData
   */
  public static NewsData makeDeadlyVirusNews(final Planet planet,
      final PlayerInfo spreader, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instructions.addImage(ImageInstruction.VIRUSES);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("DEADLY VIRUS!");
        break;
      }
      case 1: {
        instructions.addText("PANDEMIA!");
        break;
      }
      case 2: {
        instructions.addText("DISEASE!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder();
    sb.append("New deadly virus is spreading on ");
    sb.append(planet.getName());
    sb.append(". ");
    if (planet.getPlanetPlayerInfo().getRace().isRoboticRace()) {
      sb.append("Luckily planet is being populated by robotic race "
          + "which is immune to diseases. ");
    } else {
      sb.append("Majority of planet population has died because of the virus."
          + " Spread has been able to limited on this single planet. ");
      sb.append("This was major set back for ");
      sb.append(planet.getPlanetPlayerInfo().getEmpireName());
      sb.append(". ");
    }
    if (spreader != null) {
      sb.append("There are rumors that virus was spread from ");
      sb.append(spreader.getEmpireName());
      if (spreader.getDiplomacy().isTradeAlliance(
          planet.getPlanetOwnerIndex())) {
        sb.append(" due inter galactic trading between ");
        sb.append(planet.getPlanetPlayerInfo().getEmpireName());
        sb.append(" and ");
        sb.append(spreader.getEmpireName());
        sb.append(". ");
      } else if (spreader.getDiplomacy().isWar(planet.getPlanetOwnerIndex())) {
        sb.append(" and it was done on purpose. ");
        sb.append("These rumors are based on the fact that two realms are"
            + " in war! ");
      } else {
        sb.append(". ");
        sb.append("This could be accident or spread on purpose. ");
      }
    }
    planet.getPlanetPlayerInfo().appendStory(sb.toString(), starYear);
    if (spreader != null) {
      spreader.appendStory(sb.toString(), starYear);
    }
    news.setNewsText(sb.toString());
    return news;
  }
  /**
   * Make scientific achievement news. Realm builds scientific achievement
   * building and thus is closer to win.
   * @param realm PlayerInfo who is building
   * @param planet Where to building
   * @param building Building itself. Can be null. It means that planetis
   * artificial planet then.
   * @param starYear Star year
   * @return NewsData
   */
  public static NewsData makeScientificAchivementNews(final PlayerInfo realm,
      final Planet planet, final Building building, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    String position = ImageInstruction.POSITION_CENTER;
    switch (DiceGenerator.getRandom(2)) {
      case 0: {
        position = ImageInstruction.POSITION_LEFT;
        break;
      }
      case 1: {
        position = ImageInstruction.POSITION_RIGHT;
        break;
      }
      default: {
        position = ImageInstruction.POSITION_RIGHT;
        break;
      }
    }
    String size = ImageInstruction.SIZE_FULL;
    switch (DiceGenerator.getRandom(2)) {
      case 0: {
        size = ImageInstruction.SIZE_FULL;
        break;
      }
      case 1:
      default: {
        size = ImageInstruction.SIZE_HALF;
        break;
      }
    }
    instructions.addPlanet(position, planet.getImageInstructions(), size);
    position = ImageInstruction.POSITION_CENTER;
    instructions.addImage(realm.getRace().getNameSingle());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("SCIENTIFIC ACHIEVEMENT!");
        break;
      }
      case 1: {
        if (building != null) {
          instructions.addText("SCIENTIFIC ACHIEVEMENT TO "
              + planet.getName().toUpperCase());
        } else {
          instructions.addText(planet.getName().toUpperCase() + "!");
        }
        break;
      }
      case 2: {
        if (building != null) {
          instructions.addText("MIRACULOUS "
              + building.getName().toUpperCase());
        } else {
          instructions.addText("ARTIFICIAL PLANET!");
        }
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(realm.getEmpireName());
    sb.append(" builds ");
    if (building != null) {
      sb.append(building.getName());
      sb.append(" to ");
      sb.append(planet.getName());
    } else {
      sb.append(" articial planet called ");
      sb.append(planet.getName());
    }
    sb.append("! ");
    sb.append("With this scientific achivement ");
    sb.append(realm.getEmpireName());
    sb.append(" gains awe from other realms! ");
    Attitude attitude = realm.getAiAttitude();
    if (attitude == Attitude.SCIENTIFIC) {
      sb.append(realm.getEmpireName());
      sb.append(" is known to have excellent research team. ");
    }
    news.setNewsText(sb.toString());
    realm.appendStory(sb.toString(), starYear);
    return news;
  }

  /**
   * Make scientific achievement news. Realm builds scientific achievement
   * building and thus is closer to win.
   * @param realm PlayerInfo who is building
   * @param text Actual text what was discovered.
   * @param starYear Star year
   * @return NewsData
   */
  public static NewsData makeAncientResearchNews(final PlayerInfo realm,
      final String text, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_BLACK);
    instructions.addImage(ImageInstruction.ANCIENT_RESEARCH);
    instructions.addTextColor(ImageInstruction.TEXT_COLOR_DARK);
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("BREAKTHROUGH IN ARCHEOLOGY!");
        break;
      }
      case 1: {
        instructions.addText("ANCIENT ARTIFACT STUDIED!");
        break;
      }
      case 2: {
        instructions.addText("RESEARCH DONE FOR ANCIENT ARTIFACT!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    news.setNewsText(text);
    realm.appendStory(text, starYear);
    return news;
  }

  /**
   * Make Voting news
   * @param vote New vote to be organized
   * @param firstCandidate for ruler of galaxy.
   *               If vote is something else this can be null.
   * @param secondCandidate for ruler of galaxy.
   *               If vote is something else this can be null.
   * @return NewsData
   */
  public static NewsData makeVotingNews(final Vote vote,
      final PlayerInfo firstCandidate, final PlayerInfo secondCandidate) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    if (vote.getType() == VotingType.BAN_NUCLEAR_WEAPONS) {
      if (DiceGenerator.getBoolean()) {
        instructions.addImage(ImageInstruction.BIG_NUKE);
        instructions.addImage(ImageInstruction.BIG_BAN);
      } else {
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_NUKE, ImageInstruction.SIZE_HALF);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_BAN, ImageInstruction.SIZE_HALF);
      }
      int value = DiceGenerator.getRandom(2);
      switch (value) {
        case 0: {
          instructions.addText("BAN OF NUKES?");
          break;
        }
        case 1: {
          instructions.addText("VOTE FOR BANNING NUKES!");
          break;
        }
        case 2:
        default: {
          instructions.addText("NUKES TO BE BANNED?");
          break;
        }
      }
    } else if (vote.getType() == VotingType.BAN_PRIVATEER_SHIPS) {
      instructions.addLogo(ImageInstruction.POSITION_CENTER,
          ImageInstruction.BIG_PRIVATEER, ImageInstruction.SIZE_FULL);
      instructions.addLogo(ImageInstruction.POSITION_CENTER,
          ImageInstruction.BIG_BAN, ImageInstruction.SIZE_HALF);
      int value = DiceGenerator.getRandom(2);
      switch (value) {
        case 0: {
          instructions.addText("BAN OF PRIVATEERS?");
          break;
        }
        case 1: {
          instructions.addText("VOTE FOR BANNING PRIVATEERS!");
          break;
        }
        case 2:
        default: {
          instructions.addText("PRIVATEERS TO BE BANNED?");
          break;
        }
      }
    } else if (vote.getType() == VotingType.GALACTIC_PEACE) {
      instructions.addImage(ImageInstruction.GALAXY);
      instructions.addLogo(ImageInstruction.POSITION_CENTER,
          ImageInstruction.BIG_PEACE, ImageInstruction.SIZE_HALF);
      int value = DiceGenerator.getRandom(2);
      switch (value) {
        case 0: {
          instructions.addText("PEACE FOR GALAXY?");
          break;
        }
        case 1: {
          instructions.addText("VOTE FOR PEACE IN GALAXY!");
          break;
        }
        case 2:
        default: {
          instructions.addText("GALAXY WIDE PEACE?");
          break;
        }
      }
    } else if (vote.getType() == VotingType.TAXATION_OF_RICHEST_REALM) {
      instructions.addLogo(ImageInstruction.POSITION_CENTER,
          ImageInstruction.BIG_MONEY, ImageInstruction.SIZE_HALF);
      int value = DiceGenerator.getRandom(2);
      switch (value) {
        case 0: {
          instructions.addText("TAXATION?");
          break;
        }
        case 1: {
          instructions.addText("VOTE FOR TAX OF RICH!");
          break;
        }
        case 2:
        default: {
          instructions.addText("TAXATION OF RICHEST REALM?");
          break;
        }
      }
    } else if (vote.getType() == VotingType.SECOND_CANDIDATE_MILITARY) {
      instructions.addLogo(ImageInstruction.POSITION_LEFT,
          ImageInstruction.UNITED_GALAXY_TOWER, ImageInstruction.SIZE_HALF);
      instructions.addLogo(ImageInstruction.POSITION_RIGHT,
          ImageInstruction.BIG_MISSILE, ImageInstruction.SIZE_FULL);
      int value = DiceGenerator.getRandom(2);
      switch (value) {
        case 0: {
          instructions.addText("DIPLOMACY OR MILITARY?");
          break;
        }
        case 1: {
          instructions.addText("MILITARY OVER DIPLOMACY?");
          break;
        }
        case 2:
        default: {
          instructions.addText("DIPLOMACY OVER MILITARY?");
          break;
        }
      }
    } else if (vote.getType() == VotingType.RULER_OF_GALAXY) {
      instructions.addLogo(ImageInstruction.POSITION_LEFT,
          firstCandidate.getRace().getNameSingle(),
          ImageInstruction.SIZE_FULL);
      instructions.addLogo(ImageInstruction.POSITION_RIGHT,
          secondCandidate.getRace().getNameSingle(),
          ImageInstruction.SIZE_FULL);
      int value = DiceGenerator.getRandom(2);
      switch (value) {
        case 0: {
          instructions.addText(firstCandidate.getEmpireName() + " VS "
              + secondCandidate.getEmpireName());
          break;
        }
        case 1: {
          instructions.addText("RULER OF THE GALAXY?");
          break;
        }
        case 2:
        default: {
          instructions.addText("VOTE FOR RULER!");
          instructions.addText(firstCandidate.getEmpireName() + " VS "
              + secondCandidate.getEmpireName());
          break;
        }
      }
    } else {
      // No image for all votes yet
      instructions.addImage(ImageInstruction.LOGO);
      instructions.addText("NEW VOTE!");
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append("There is going to be new galactic voting about '");
    sb.append(vote.getType().getDescription());
    sb.append("'. This voting has time ");
    sb.append(vote.getTurnsToVote());
    sb.append(" star years. ");
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Create voting string. This means text how each realm voted.
   * @param vote Vote where to write about text.
   * @param realmNames In array.
   * @return VotingString
   */
  private static String createVotingString(final Vote vote,
      final String[] realmNames) {
    int noVotes = 0;
    int yesVotes = 0;
    int abstainVotes = 0;
    StringBuilder votingString = new StringBuilder();
    for (int i = 0; i < vote.getNumberOfRealms(); i++) {
      if (vote.getNumberOfVotes(i) == 0) {
        continue;
      }
      if (vote.getChoice(i) == VotingChoice.VOTED_NO) {
        noVotes = noVotes + vote.getNumberOfVotes(i);
        votingString.append(realmNames[i]);
        votingString.append(" voted NO");
        votingString.append(" with ");
        votingString.append(vote.getNumberOfVotes(i));
        votingString.append(" votes.");
      }
      if (vote.getChoice(i) == VotingChoice.VOTED_YES) {
        yesVotes = yesVotes + vote.getNumberOfVotes(i);
        votingString.append(realmNames[i]);
        votingString.append(" voted YES");
        votingString.append(" with ");
        votingString.append(vote.getNumberOfVotes(i));
        votingString.append(" votes.");
      }
      if (vote.getChoice(i) == VotingChoice.ABSTAIN
          || vote.getChoice(i) == VotingChoice.NOT_VOTED) {
        abstainVotes = abstainVotes + vote.getNumberOfVotes(i);
        votingString.append(realmNames[i]);
        votingString.append(" abstain voting");
        votingString.append(" with ");
        votingString.append(vote.getNumberOfVotes(i));
        votingString.append(" votes.");
      }
      votingString.append("\n");
    }
    return votingString.toString();
  }
  /**
   * Make Voting ended news
   * @param vote New vote to be organized
   * @param choice Which choice won the voting
   * @param firstCandidate for ruler of galaxy.
   *               If vote is something else this can be null.
   * @param secondCandidate for ruler of galaxy.
   *               If vote is something else this can be null.
   * @param realmNames Realm names in array.
   * @return NewsData
   */
  public static NewsData makeVotingEndedNews(final Vote vote,
      final VotingChoice choice, final PlayerInfo firstCandidate,
      final PlayerInfo secondCandidate, final String[] realmNames) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
    if (vote.getType() == VotingType.BAN_NUCLEAR_WEAPONS) {
      if (DiceGenerator.getBoolean()) {
        instructions.addImage(ImageInstruction.BIG_NUKE);
        if (choice == VotingChoice.VOTED_YES) {
          instructions.addImage(ImageInstruction.BIG_BAN);
        }
      } else {
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_NUKE, ImageInstruction.SIZE_HALF);
        if (choice == VotingChoice.VOTED_YES) {
          instructions.addLogo(ImageInstruction.POSITION_CENTER,
              ImageInstruction.BIG_BAN, ImageInstruction.SIZE_HALF);
        }
      }
      if (choice == VotingChoice.VOTED_YES) {
        instructions.addText("NUKES ARE BANNED!");
      } else {
        instructions.addText("NUKES ARE ALLOWED!");
      }
    } else if (vote.getType() == VotingType.BAN_PRIVATEER_SHIPS) {
      instructions.addLogo(ImageInstruction.POSITION_CENTER,
          ImageInstruction.BIG_PRIVATEER, ImageInstruction.SIZE_FULL);
      if (choice == VotingChoice.VOTED_YES) {
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_BAN, ImageInstruction.SIZE_HALF);
      }
      if (choice == VotingChoice.VOTED_YES) {
        instructions.addText("PRIVATEERS ARE BANNED!");
      } else {
        instructions.addText("PRIVATEERS ARE ALLOWED!");
      }
    } else if (vote.getType() == VotingType.GALACTIC_PEACE) {
      instructions.addImage(ImageInstruction.GALAXY);
      instructions.addLogo(ImageInstruction.POSITION_CENTER,
          ImageInstruction.BIG_PEACE, ImageInstruction.SIZE_HALF);
      if (choice == VotingChoice.VOTED_NO) {
        instructions.addImage(ImageInstruction.BIG_BAN);
      }
      if (choice == VotingChoice.VOTED_YES) {
        instructions.addText("PEACE FOR WHOLE GALAXY!");
      } else {
        instructions.addText("NO PEACE FOR GALAXY!");
      }
    } else if (vote.getType() == VotingType.TAXATION_OF_RICHEST_REALM) {
      instructions.addLogo(ImageInstruction.POSITION_CENTER,
          ImageInstruction.BIG_MONEY, ImageInstruction.SIZE_HALF);
      if (choice == VotingChoice.VOTED_NO) {
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_BAN, ImageInstruction.SIZE_HALF);
      }
      if (choice == VotingChoice.VOTED_YES) {
        instructions.addText("TAXES FOR RICHEST REALM!");
      } else {
        instructions.addText("NO EXTRA TAXES FOR RICHEST REALM!");
      }
    } else if (vote.getType() == VotingType.SECOND_CANDIDATE_MILITARY) {
      if (choice == VotingChoice.VOTED_YES) {
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_MISSILE, ImageInstruction.SIZE_FULL);
      } else {
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.UNITED_GALAXY_TOWER, ImageInstruction.SIZE_FULL);
      }
      if (choice == VotingChoice.VOTED_YES) {
        instructions.addText("MILITARY CANDIDATE!");
      } else {
        instructions.addText("DIPLOMATIC CANDIDATE!");
      }
    } else if (vote.getType() == VotingType.RULER_OF_GALAXY) {
      if (choice == VotingChoice.VOTED_YES) {
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            firstCandidate.getRace().getNameSingle(),
            ImageInstruction.SIZE_FULL);
      } else {
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            secondCandidate.getRace().getNameSingle(),
            ImageInstruction.SIZE_FULL);
      }
      if (choice == VotingChoice.VOTED_YES) {
        instructions.addText("RULER OF THE GALAXY");
        instructions.addText("IS");
        instructions.addText(firstCandidate.getEmpireName());
      } else {
        instructions.addText("RULER OF THE GALAXY");
        instructions.addText("IS");
        instructions.addText(secondCandidate.getEmpireName());
      }
    } else {
      // No image for all votes yet
      instructions.addImage(ImageInstruction.LOGO);
      instructions.addText("NEW VOTE!");
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append("Voting for '");
    sb.append(vote.getType().getDescription());
    sb.append("' has ended. ");
    if (vote.getType() == VotingType.RULER_OF_GALAXY) {
      if (choice == VotingChoice.VOTED_YES) {
        sb.append(firstCandidate.getEmpireName());
        sb.append(" won the voting! ");
      } else {
        sb.append(secondCandidate.getEmpireName());
        sb.append(" won the voting! ");
      }
    } else {
      sb.append(choice.getDescription());
      sb.append("-votes won! ");
    }
    int yes = vote.getVotingAmounts(VotingChoice.VOTED_YES);
    int no = vote.getVotingAmounts(VotingChoice.VOTED_NO);
    int notVoted = vote.getVotingAmounts(VotingChoice.NOT_VOTED);
    int total = yes + no + notVoted;
    yes = yes * 100 / total;
    no = no * 100 / total;
    if (vote.getType() == VotingType.RULER_OF_GALAXY) {
      sb.append(firstCandidate.getEmpireName());
      sb.append(": ");
      sb.append(yes);
      sb.append(" per cent and ");
      sb.append(secondCandidate.getEmpireName());
      sb.append(": ");
      sb.append(no);
      sb.append(" per cent. ");
    } else {
      sb.append("YES: ");
      sb.append(yes);
      sb.append(" per cent and NO: ");
      sb.append(no);
      sb.append(" per cent. ");
    }
    sb.append("\n");
    sb.append(createVotingString(vote, realmNames));
    news.setNewsText(sb.toString());
    return news;
  }

  /**
   * Make United Galaxy Tower news. Realm builds this building is closer
   * to start path to diplomatic victory.
   * @param realm PlayerInfo who is building
   * @param planet Where to building
   * @param starYear Star Year
   * @return NewsData
   */
  public static NewsData makeUnitedGalaxyTowerNews(final PlayerInfo realm,
      final Planet planet, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    String position = ImageInstruction.POSITION_CENTER;
    String position2 = ImageInstruction.POSITION_CENTER;
    switch (DiceGenerator.getRandom(1)) {
      case 0: {
        position = ImageInstruction.POSITION_LEFT;
        position2 = ImageInstruction.POSITION_RIGHT;
        break;
      }
      case 1: {
        position = ImageInstruction.POSITION_RIGHT;
        position2 = ImageInstruction.POSITION_LEFT;
        break;
      }
      default: {
        position = ImageInstruction.POSITION_RIGHT;
        position2 = ImageInstruction.POSITION_LEFT;
        break;
      }
    }
    String size = ImageInstruction.SIZE_HALF;
    instructions.addPlanet(position, planet.getImageInstructions(), size);
    instructions.addLogo(position2, ImageInstruction.UNITED_GALAXY_TOWER,
        size);
    instructions.addImage(realm.getRace().getNameSingle());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("UNITED GALAXY TOWER!");
        break;
      }
      case 1: {
        instructions.addText("UNITED GALAXY TOWER TO "
              + planet.getName().toUpperCase());
        break;
      }
      case 2: {
        instructions.addText("UNITED GALAXY TOWER BUILT!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(realm.getEmpireName());
    sb.append(" builds United Galaxy Tower to ");
    sb.append(planet.getName());
    sb.append("! ");
    sb.append("With this great building ");
    sb.append(realm.getEmpireName());
    sb.append(" gains respect from other realms! ");
    Attitude attitude = realm.getAiAttitude();
    if (attitude == Attitude.DIPLOMATIC) {
      sb.append(realm.getEmpireName());
      sb.append(" is known to have diplomatic leaders. ");
    }
    news.setNewsText(sb.toString());
    realm.appendStory(sb.toString(), starYear);
    return news;
  }

  /**
   * Make Broadcaster building news. Realm builds this building is closer
   * to cultural victory.
   * @param realm PlayerInfo who is building
   * @param planet Where to building
   * @param building Building which was built
   * @param starYear Star Year
   * @return NewsData
   */
  public static NewsData makeBroadcasterBuildingNews(final PlayerInfo realm,
      final Planet planet, final Building building, final int starYear) {
    NewsData news = new NewsData();
    ImageInstruction instructions = new ImageInstruction();
    instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
    String position = ImageInstruction.POSITION_CENTER;
    String size = ImageInstruction.SIZE_FULL;
    instructions.addPlanet(position, planet.getImageInstructions(), size);
    instructions.addImage(realm.getRace().getNameSingle());
    switch (DiceGenerator.getRandom(2)) {
      case 0:
      default: {
        instructions.addText("BROADCASTING!");
        break;
      }
      case 1: {
        instructions.addText(building.getName().toUpperCase() + " TO "
              + planet.getName().toUpperCase());
        break;
      }
      case 2: {
        instructions.addText("CULTURAL SPREADING STARTS!");
        break;
      }
    }
    news.setImageInstructions(instructions.build());
    StringBuilder sb = new StringBuilder(100);
    sb.append(realm.getEmpireName());
    sb.append(" builds ");
    sb.append(building.getName());
    sb.append(" to ");
    sb.append(planet.getName());
    sb.append("! ");
    sb.append("With this great building ");
    sb.append(realm.getEmpireName());
    sb.append(" is able to spread cultural information to other realms! ");
    news.setNewsText(sb.toString());
    realm.appendStory(sb.toString(), starYear);
    return news;
  }

}
