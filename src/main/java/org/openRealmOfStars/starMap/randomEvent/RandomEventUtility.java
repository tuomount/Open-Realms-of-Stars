package org.openRealmOfStars.starMap.randomEvent;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2019-2022 Tuomo Untinen
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

import java.util.ArrayList;

import org.openRealmOfStars.ai.mission.Mission;
import org.openRealmOfStars.ai.mission.MissionPhase;
import org.openRealmOfStars.ai.mission.MissionType;
import org.openRealmOfStars.ai.research.Research;
import org.openRealmOfStars.gui.icons.Icon16x16;
import org.openRealmOfStars.gui.icons.Icons;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.leader.Job;
import org.openRealmOfStars.player.leader.Leader;
import org.openRealmOfStars.player.leader.LeaderBiography;
import org.openRealmOfStars.player.leader.LeaderUtility;
import org.openRealmOfStars.player.leader.MilitaryRank;
import org.openRealmOfStars.player.leader.Perk;
import org.openRealmOfStars.player.message.Message;
import org.openRealmOfStars.player.message.MessageType;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.ship.Ship;
import org.openRealmOfStars.player.ship.ShipHullType;
import org.openRealmOfStars.player.ship.ShipStat;
import org.openRealmOfStars.player.ship.shipdesign.ShipDesign;
import org.openRealmOfStars.player.tech.TechFactory;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.Coordinate;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.newsCorp.ImageInstruction;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingFactory;
import org.openRealmOfStars.utilities.DiceGenerator;
import org.openRealmOfStars.utilities.ErrorLogger;
import org.openRealmOfStars.utilities.namegenerators.OriginalWorkNameGenerator;

/**
*
* Random Event Utility for creating random events and handling.
*
*/
public final class RandomEventUtility {

  /**
   * Hiding the constructor
   */
  private RandomEventUtility() {
    // Hiding the constructor
  }

  /**
   * Create good random event.
   * @param realm Realm who is getting the event
   * @return RandomEvent
   */
  public static RandomEvent createGoodRandomEvent(final PlayerInfo realm) {
    GoodRandomType[] values = GoodRandomType.values();
    var choice = DiceGenerator.pickRandom(values);
    RandomEvent event = new RandomEvent(choice, realm);
    return event;
  }

  /**
   * Create bad random event.
   * @param realm Realm who is getting the event
   * @return RandomEvent
   */
  public static RandomEvent createBadRandomEvent(final PlayerInfo realm) {
    BadRandomType[] values = BadRandomType.values();
    var choice = DiceGenerator.pickRandom(values);
    RandomEvent event = new RandomEvent(choice, realm);
    return event;
  }

  /**
   * Handle massive data lost event.
   * @param event Random event, must be Massive data lost.
   * @param map StarMap for getting location for focus.
   */
  public static void handleMassiveDataLost(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.MASSIVE_DATA_LOST) {
      PlayerInfo info = event.getRealm();
      Planet mostValuablePlanet = null;
      int bestValue = 0;
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          int value = planet.getTotalProduction(Planet.PRODUCTION_RESEARCH);
          if (value > bestValue) {
            bestValue = value;
            mostValuablePlanet = planet;
          }
        }
      }
      var techType = DiceGenerator.pickRandom(TechType.values());
      info.getTechList().setTechResearchPoints(techType, 0);
      String techName = techType.toString();
      event.setText("A massive computer virus spreads in all labs which study "
          + techName + " technology. "
          + "This virus deletes all the data related to research. "
          + "All work for next tech in " + techName + " is lost. "
          + "Scientists need to start all over from the scratch.");
      ImageInstruction instructions = new ImageInstruction();
      instructions.addImage(ImageInstruction.DATALOSS);
      event.setImageInstructions(instructions.build());
      Message message = new Message(MessageType.INFORMATION, event.getText(),
          Icons.getIconByName(Icons.ICON_ELECTRONICS_TECH));
      message.setRandomEventPop(true);
      if (mostValuablePlanet != null) {
        message.setCoordinate(mostValuablePlanet.getCoordinate());
      }
      info.getMsgList().addFirstMessage(message);
    }
  }

  /**
   * Handle ruler stress.
   * @param event Random event, must be ruler stress
   * @param map StarMap for getting planet for focus.
   */
  public static void handleRulerStress(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.RULER_STRESS) {
      PlayerInfo info = event.getRealm();
      if (info.getRuler() != null) {
        Planet mostValuablePlanet = null;
        int bestValue = 0;
        for (Planet planet : map.getPlanetList()) {
          if (planet.getPlanetPlayerInfo() == info) {
            int value = planet.getTotalPopulation() * 10 + planet.getCulture();
            if (value > bestValue) {
              bestValue = value;
              mostValuablePlanet = planet;
            }
          }
        }
        event.setLeader(info.getRuler());
        Perk[] perks = LeaderUtility.getNewPerks(info.getRuler(),
            LeaderUtility.PERK_TYPE_MENTAL);
        if (perks.length > 0) {
          ImageInstruction instructions = new ImageInstruction();
          instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
          instructions.addSiluete(info.getRuler().getRace().getNameSingle(),
              ImageInstruction.POSITION_CENTER);
          event.setImageInstructions(instructions.build());
          Perk perk = DiceGenerator.pickRandom(perks);
          info.getRuler().addPerk(perk);
          event.setText(info.getRuler().getCallName() + " has been on massive"
              + " stress lately and got " + perk.getName().toLowerCase()
              + ". This is terrible news for whole " + info.getEmpireName()
              + ".");
          Message message = new Message(MessageType.INFORMATION,
              event.getText(), Icons.getIconByName(Icons.ICON_RULER));
          if (mostValuablePlanet != null) {
            message.setCoordinate(mostValuablePlanet.getCoordinate());
          }
          message.setRandomEventPop(true);
          info.getMsgList().addFirstMessage(message);
        }
      }
    }
  }

  /**
   * Handle leader accident.
   * @param event Random event, must be accident
   */
  public static void handleAccident(final RandomEvent event) {
    if (event.getBadType() == BadRandomType.ACCIDENT) {
      PlayerInfo info = event.getRealm();
      Leader leader = LeaderUtility.getRandomLivingLeader(info);
      ImageInstruction instructions = new ImageInstruction();
      if (leader != null) {
        event.setLeader(leader);
        if (leader.hasPerk(Perk.WEALTHY)) {
          leader.useWealth();
          event.setText(leader.getCallName() + " encounter deadly accident"
              + " but luckily  " + leader.getGender().getHisHer()
              + " extra ordinary wealth was able to save"
              + leader.getGender().getHisHer()
              + " life. ");
          Message message = new Message(MessageType.INFORMATION,
              event.getText(), Icons.getIconByName(Icons.ICON_LEADERS));
          info.getMsgList().addFirstMessage(message);
          instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
          instructions.addLogo(ImageInstruction.POSITION_CENTER,
              leader.getRace().getNameSingle(), ImageInstruction.SIZE_FULL);
        } else {
          event.setText(leader.getCallName() + " encounter deadly accident"
              + " and "  + leader.getGender().getHisHer()
              + " life ended immediately!"
              + "This is terrible news for whole " + info.getEmpireName()
              + ".");
          event.setNewsWorthy(true);
          Message message = new Message(MessageType.INFORMATION,
              event.getText(), Icons.getIconByName(Icons.ICON_LEADERS));
          info.getMsgList().addFirstMessage(message);
          instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
          instructions.addSiluete(leader.getRace().getNameSingle(),
              ImageInstruction.POSITION_CENTER);
          event.setImageInstructions(instructions.build());
          message.setRandomEventPop(true);
          leader.setJob(Job.DEAD);
        }
      }
    }
  }

  /**
   * Handle leader level
   * @param event Random event, must be leader level
   */
  public static void handleLeaderLevel(final RandomEvent event) {
    if (event.getGoodType() == GoodRandomType.LEADER_LEVEL) {
      PlayerInfo info = event.getRealm();
      ArrayList<Leader> leaders = new ArrayList<>();
      for (Leader leader : info.getLeaderPool()) {
        if (leader.getJob() != Job.DEAD) {
          leaders.add(leader);
        }
      }
      if (leaders.size() > 0) {
        Leader leader = DiceGenerator.pickRandom(leaders);
        event.setLeader(leader);
        leader.setLevel(leader.getLevel() + 1);
        Perk[] perksGained = LeaderUtility.addRandomPerks(leader);
        ImageInstruction instructions = new ImageInstruction();
        instructions.addBackground(ImageInstruction.BACKGROUND_NEBULAE);
        instructions.addSiluete(leader.getRace().getNameSingle(),
            ImageInstruction.POSITION_CENTER);
        event.setImageInstructions(instructions.build());
        StringBuilder sb = new StringBuilder();
        for (Perk perk : perksGained) {
          sb.append(LeaderBiography.getReasonForPerk(leader, perk));
          sb.append(" ");
        }
        event.setText(leader.getCallName()
            + " has reached " + leader.getGender().getHisHer()
            + " achievements earlier and gains extra level. "
            + sb.toString());
        Message msg = new Message(MessageType.LEADER, event.getText(),
            LeaderUtility.getIconBasedOnLeaderJob(leader));
        msg.setMatchByString("Index:" + info.getLeaderIndex(leader));
        msg.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(msg);
        if (leader.getJob() == Job.COMMANDER
            && leader.getMilitaryRank() != MilitaryRank.CIVILIAN) {
          leader.setMilitaryRank(MilitaryRank.getByIndex(
              leader.getMilitaryRank().getIndex() + 1));
          leader.setTitle(LeaderUtility.createTitleForLeader(leader, info));
        }
      }
    }
  }

  /**
   * Handle techinical breakthrough
   * @param event Random event, must be technical breakthrough
   * @param map Starmap for location
   */
  public static void handleTechnicalBreakThrough(final RandomEvent event,
      final StarMap map) {
    if (event.getGoodType() == GoodRandomType.TECHNICAL_BREAKTHROUGH) {
      PlayerInfo info = event.getRealm();
      var techType = DiceGenerator.pickRandom(TechType.values());
      double original = info.getTechList().getTechResearchPoints(techType);
      if (original < 10) {
        original = 20;
      } else {
        original = original * 2;
      }
      Planet mostValuablePlanet = null;
      int bestValue = 0;
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          int value = planet.getTotalProduction(Planet.PRODUCTION_RESEARCH);
          if (value > bestValue) {
            bestValue = value;
            mostValuablePlanet = planet;
          }
        }
      }
      info.getTechList().setTechResearchPoints(techType, original);
      String techName = techType.toString();
      ImageInstruction instructions = new ImageInstruction();
      instructions.addImage(ImageInstruction.TECHNICAL_BREAKTHROUGH);
      event.setImageInstructions(instructions.build());
      event.setText("Scientist make breakthrough which boost "
          + techName + " technology. "
          + "This will gains us to get faster new technology in "
          + techName + ".");
      Message message = new Message(MessageType.INFORMATION, event.getText(),
          Icons.getIconByName(Icons.ICON_RESEARCH));
      message.setRandomEventPop(true);
      if (mostValuablePlanet != null) {
        message.setCoordinate(mostValuablePlanet.getCoordinate());
      }
      info.getMsgList().addFirstMessage(message);
    }
  }


  /**
   * Handle lost treasure random event.
   * @param event Random Event must be Lost treasure
   * @param map Starmap for looking planet for realm.
   */
  public static void handleLostTreasure(final RandomEvent event,
      final StarMap map) {
    if (event.getGoodType() == GoodRandomType.LOST_TREASURE_FOUND) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        event.setPlanet(planet);
        StringBuilder sb = new StringBuilder();
        int occupationIndex = DiceGenerator.getRandom(2);
        switch (occupationIndex) {
          case 0: sb.append("Archeologist "); break;
          case 1: sb.append("Miner "); break;
          default:
          case 2: sb.append("Scientist "); break;
        }
        sb.append(" found a ancient treasure from ");
        sb.append(planet.getName());
        int value = DiceGenerator.getRandom(30, 60);
        sb.append(". Treasure contained ");
        sb.append(value);
        sb.append(" worth of credits.");
        ImageInstruction instructions = new ImageInstruction();
        instructions.addImage(ImageInstruction.CONTAINERS);
        event.setImageInstructions(instructions.build());
        event.setText(sb.toString());
        info.setTotalCredits(info.getTotalCredits() + value);
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            Icons.getIconByName(Icons.ICON_CREDIT));
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle catastrophic accident random event.
   * @param event Random event must be Catastrophic accident
   * @param map Starmap to locate planet.
   */
  public static void handleCatastrophicAccident(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.CATASTROPHIC_ACCIDENT) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info
            && planet.getBuildingList().length > 0) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        event.setPlanet(planet);
        var building = DiceGenerator.pickRandom(planet.getBuildingList());
        StringBuilder sb = new StringBuilder();
        int accidentIndex = DiceGenerator.getRandom(2);
        switch (accidentIndex) {
          case 0: sb.append("Massive fire "); break;
          case 1: sb.append("Huge explosion "); break;
          default:
          case 2: sb.append("Deadly chemical leak "); break;
        }
        planet.removeBuilding(building);
        sb.append(" happened in ");
        sb.append(building.getName());
        if (accidentIndex < 2) {
          sb.append(". This building was totally destroyed");
        } else {
          sb.append(". This building need to demolished due the chemical");
        }
        if (planet.getTotalPopulation() > 1) {
          sb.append(" and one population died in accident.");
          planet.killOneWorker("accident", "accident", map);
        }
        ImageInstruction instructions = new ImageInstruction();
        instructions.addBackground(ImageInstruction.BACKGROUND_BLACK);
        instructions.addImage(ImageInstruction.FACTORY);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.BIG_EXPLOSION, ImageInstruction.SIZE_FULL);
        event.setImageInstructions(instructions.build());
        event.setText(sb.toString());
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            Icons.getIconByName(Icons.ICON_DEATH));
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle terrorist attack random event.
   * @param event Random event must be terrorist attack
   * @param map Starmap to locate planet.
   */
  public static void handleTerroristAttack(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.TERRORIST_ATTACK) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info
            && planet.getBuildingList().length > 0) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        event.setPlanet(planet);
        event.setNewsWorthy(true);
        int happiness = planet.calculateHappiness();
        var building = DiceGenerator.pickRandom(planet.getBuildingList());
        StringBuilder sb = new StringBuilder();
        int accidentIndex = DiceGenerator.getRandom(2);
        switch (accidentIndex) {
          case 0: sb.append("Terrorist attack on "); break;
          case 1: sb.append("Terrorist bombing on "); break;
          default:
          case 2: sb.append("Terror on "); break;
        }
        sb.append(planet.getName());
        int casualty = 0;
        if (happiness > 0) {
          planet.killOneWorker();
          casualty++;
        }
        sb.append(". ");
        if (happiness <= 0) {
          planet.removeBuilding(building);
          sb.append(building.getName());
          sb.append(" destroyed during incident. ");
        }
        if (planet.getTotalPopulation() > 1 && happiness < 0) {
          planet.killOneWorker();
          casualty++;
        }
        if (planet.getGovernor() != null && happiness < -1) {
          event.setLeader(planet.getGovernor());
          if (planet.getGovernor().hasPerk(Perk.WEALTHY)) {
            planet.getGovernor().useWealth();
            sb.append(planet.getGovernor().getCallName());
            sb.append(" was target on terrorist attack but ");
            sb.append(planet.getGovernor().getGender().getHisHer());
            sb.append(" extra ordinary wealth was able to save ");
            sb.append(planet.getGovernor().getGender().getHisHer());
            sb.append(" life. ");
          } else {
            sb.append(planet.getGovernor().getCallName());
            sb.append(" was target on terrorist attack and ");
            sb.append(planet.getGovernor().getGender().getHisHer());
            sb.append(" life ended during attack. ");
            sb.append("This is terrible news for whole ");
            sb.append(info.getEmpireName());
            sb.append(". ");
            planet.getGovernor().setJob(Job.DEAD);
          }
        }
        if (planet.getTotalPopulation() > 1 && happiness < -2) {
          planet.killOneWorker();
          casualty++;
        }
        sb.append(casualty);
        sb.append(" number of population was killed during the ");
        sb.append("terrorist attack. ");
        if (happiness <= -2) {
          sb.append("Terrorist were killed during the attack!");
        } else if (happiness <= 0) {
          sb.append("Terrorist killed themselves during the attack!");
        } else {
          sb.append("Terrorist were captured alive and sentence for life!");
        }
        sb.append(" ");
        sb.append(planet.getName());
        int ownIndex = DiceGenerator.getRandom(2);
        switch (ownIndex) {
          case 0: sb.append(" is owned by "); break;
          case 1: sb.append(" belongs to "); break;
          default: sb.append(" is part of "); break;
        }
        sb.append(info.getEmpireName());
        sb.append(".");
        ImageInstruction instructions = new ImageInstruction();
        instructions.addImage(ImageInstruction.TERROR);
        event.setImageInstructions(instructions.build());
        event.setText(sb.toString());
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            Icons.getIconByName(Icons.ICON_DEATH));
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle deserted ship event.
   * @param event Random event must be deserted ship.
   * @param map Starmap for looking good planet and free space.
   */
  public static void handleDesertedShip(final RandomEvent event,
      final StarMap map) {
    if (event.getGoodType() == GoodRandomType.DESERTED_SHIP) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        event.setPlanet(planet);
        PlayerInfo extraPlayer = new PlayerInfo(SpaceRace.SPACE_PIRATE);
        int numberOfTechs = 5;
        switch (map.getGameLengthState()) {
          case ELDER_HEAD_START:
          case START_GAME: {
            numberOfTechs = DiceGenerator.getRandom(5, 18);
            break;
          }
          case EARLY_GAME: {
            numberOfTechs = DiceGenerator.getRandom(20, 36);
            break;
          }
          case MIDDLE_GAME: {
            numberOfTechs = DiceGenerator.getRandom(40, 60);
            break;
          }
          case LATE_GAME: {
            numberOfTechs = DiceGenerator.getRandom(70, 90);
            break;
          }
          default:
          case END_GAME: {
            numberOfTechs = DiceGenerator.getRandom(100, 130);
            break;
          }
        }
        for (int i = 0; i < numberOfTechs; i++) {
          extraPlayer.getTechList().addNewRandomTech(extraPlayer);
        }
        Research.handleShipDesigns(extraPlayer);
        ShipStat[] shipStats = extraPlayer.getShipStatList();
        ArrayList<ShipStat> ships = new ArrayList<>();
        for (ShipStat stat : shipStats) {
          if (stat.getDesign().getTotalMilitaryPower() > 0
              && stat.getDesign().getHull().getHullType()
              == ShipHullType.NORMAL
              && !stat.isObsolete()) {
            ships.add(stat);
          }
        }
        if (ships.size() > 0) {
          ShipDesign design = DiceGenerator.pickRandom(ships).getDesign();
          design.setName("Alien vessel");
          Ship ship = new Ship(design);
          boolean exit = false;
          for (int y = -1; y < 2; y++) {
            for (int x = -1; x < 2; x++) {
              int posX = planet.getCoordinate().getX() + x;
              int posY = planet.getCoordinate().getY() + y;
              if (!map.isBlocked(posX, posY)) {
                PlayerInfo shipOwner = map.isBlockedByFleet(posX, posY);
                if (shipOwner == null || shipOwner == info) {
                  Fleet fleet = new Fleet(ship, posX, posY);
                  fleet.setName(info.getFleets().generateUniqueName());
                  info.getFleets().add(fleet);
                  event.setText("Deserted ship appeared into space near "
                      + planet.getName() + ". Ship was fully functional and "
                      + "added to fleet of " + info.getEmpireName()
                      + ".");
                  event.setFleet(fleet);
                  ImageInstruction instructions = new ImageInstruction();
                  instructions.addImage(ImageInstruction.ALIEN_SHIP);
                  event.setImageInstructions(instructions.build());
                  Message message = new Message(MessageType.FLEET,
                      event.getText(), Icons.getIconByName(
                          Icons.ICON_HULL_TECH));
                  message.setCoordinate(planet.getCoordinate());
                  message.setMatchByString(fleet.getName());
                  message.setRandomEventPop(true);
                  info.getMsgList().addFirstMessage(message);
                  exit = true;
                  break;
                }
              }
            }
            if (exit) {
              break;
            }
          }
        }
      }
    }
  }

  /**
   * Handle meteor hit to planet.
   * @param event Random Event must be meteor hit
   * @param map Starmap for looking planet for realm.
   */
  public static void handleMeteorHit(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.METEOR_HIT) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        event.setPlanet(planet);
        StringBuilder sb = new StringBuilder();
        sb.append("Massive meteor hits the the atmosphere of ");
        sb.append(planet.getName());
        sb.append(". ");
        ImageInstruction instructions = new ImageInstruction();
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addPlanet(ImageInstruction.POSITION_CENTER,
            planet.getImageInstructions(), ImageInstruction.SIZE_HALF);
        instructions.addLogo(ImageInstruction.POSITION_CENTER,
            ImageInstruction.METEOR_HIT, ImageInstruction.SIZE_FULL);
        event.setImageInstructions(instructions.build());
        Icon16x16 icon = Icons.getIconByName(Icons.ICON_DEATH);
        if (planet.getTurretLvl() > 0) {
          sb.append("Planet's defense turrets shoot the meteor to pieces ");
          sb.append("and metal debris is being scattered around the planet.");
          planet.setAmountMetalInGround(planet.getAmountMetalInGround()
              + DiceGenerator.getRandom(80, 500));
          icon = Icons.getIconByName(Icons.ICON_PLANETARY_TURRET);
        } else {
          planet.setMetal(planet.getMetal()
              + DiceGenerator.getRandom(80, 500));
          sb.append("Meteor hits the planet ");
          boolean miss = true;
          int hitSpot = DiceGenerator.getRandom(0, planet.getGroundSize() - 1);
          if (hitSpot <= planet.getTotalPopulation()) {
            sb.append("and kills one worker ");
            planet.killOneWorker("meteor crash", "meteor crash", map);
            if (planet.getTotalPopulation() == 0
                && planet.getGovernor() != null) {
              planet.getGovernor().setJob(Job.UNASSIGNED);
              planet.setGovernor(null);
            }
            miss = false;
          }
          if (hitSpot < planet.getNumberOfBuildings()) {
            sb.append("and destroyes ");
            Building building = planet.getBuildingList()[hitSpot];
            sb.append(building.getName());
            planet.removeBuilding(building);
            miss = false;
          }
          if (miss) {
            sb.append(" but hits to unoccupied zone.");
          } else {
            sb.append(".");
          }
        }
        event.setText(sb.toString());
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            icon);
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle cultual hit to planet.
   * @param event Random Event must be cultural hit
   * @param map Starmap for looking planet for realm.
   */
  public static void handleCulturalHit(final RandomEvent event,
      final StarMap map) {
    if (event.getGoodType() == GoodRandomType.CULTURAL_HIT) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        event.setPlanet(planet);
        String hitType = "movie";
        String hitCapital = "Movie";
        String author = "author";
        switch (DiceGenerator.getRandom(5)) {
          default:
          case 0: {
            hitType = "movie";
            hitCapital = "Movie";
            author = "director";
            break;
          }
          case 1: {
            hitType = "song";
            hitCapital = "Song";
            author = "composer";
            break;
          }
          case 2: {
            hitType = "show";
            hitCapital = "Show";
            author = "author";
            break;
          }
          case 3: {
            hitType = "book";
            hitCapital = "Book";
            author = "author";
            break;
          }
          case 4: {
            hitType = "opera";
            hitCapital = "Opera";
            author = "director";
            break;
          }
          case 5: {
            hitType = "video game";
            hitCapital = "Video game";
            author = "designer";
            break;
          }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Massive popular ");
        sb.append(hitType);
        sb.append(" was done in ");
        sb.append(planet.getName());
        sb.append(" by ");
        sb.append(author);
        sb.append(" ");
        Leader authorName = LeaderUtility.createLeader(
            planet.getPlanetPlayerInfo(), planet, 1);
        sb.append(authorName.getName());
        sb.append(". ");
        sb.append(hitCapital);
        sb.append(" is called ");
        OriginalWorkNameGenerator generator = new OriginalWorkNameGenerator();
        String workName = generator.generate();
        sb.append("\"");
        sb.append(workName);
        sb.append("\"");
        sb.append(". It is huge success in whole galaxy!");
        sb.append(" ");
        sb.append(author);
        sb.append(" is from ");
        sb.append(info.getEmpireName());
        sb.append(".");
        ImageInstruction instructions = new ImageInstruction();
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addPlanet(ImageInstruction.POSITION_CENTER,
            planet.getImageInstructions(), ImageInstruction.SIZE_HALF);
        instructions.addPlanet(ImageInstruction.POSITION_CENTER,
            planet.getImageInstructions(), ImageInstruction.SIZE_FULL);
        event.setImageInstructions(instructions.build());
        Icon16x16 icon = Icons.getIconByName(Icons.ICON_CULTURE);
        event.setText(sb.toString());
        event.setNewsWorthy(true);
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            icon);
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(false);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle missed meteoroid .
   * @param event Random Event must be missed meteoird
   * @param map Starmap for looking planet for realm.
   */
  public static void handleMissedMeteoroid(final RandomEvent event,
      final StarMap map) {
    if (event.getGoodType() == GoodRandomType.MISSED_METEOROID) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        event.setPlanet(planet);
        StringBuilder sb = new StringBuilder();
        sb.append("Massive meteoroid passes by ");
        sb.append(planet.getName());
        sb.append(" very close. So close that planet scienties are able "
            + "to mine metal from the meteoroid.");
        planet.setAmountMetalInGround(planet.getAmountMetalInGround()
            + DiceGenerator.getRandom(80, 400));
        event.setText(sb.toString());
        ImageInstruction instructions = new ImageInstruction();
        instructions.addBackground(ImageInstruction.BACKGROUND_STARS);
        instructions.addPlanet(ImageInstruction.POSITION_LEFT,
            planet.getImageInstructions(), ImageInstruction.SIZE_FULL);
        instructions.addLogo(ImageInstruction.POSITION_RIGHT,
            ImageInstruction.METEOR, ImageInstruction.SIZE_FULL);
        event.setImageInstructions(instructions.build());
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            Icons.getIconByName(Icons.ICON_METAL));
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle deadly virus outbreak.
   * @param event Random event must be deadly virus outbreak.
   * @param map Starmap for find a planet for realm.
   */
  public static void handleDeadlyVirusOutbreak(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.DEADLY_VIRUS_OUTBREAK) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        if (!info.getTechList().hasTech(
            TechType.Improvements, "Deadly virus")) {
          event.setPlanet(planet);
          StringBuilder sb = new StringBuilder();
          sb.append("Deadly virus outbreaks at ");
          sb.append(planet.getName());
          sb.append(". ");
          sb.append(planet.getName());
          int ownIndex = DiceGenerator.getRandom(2);
          switch (ownIndex) {
            case 0: sb.append(" is owned by "); break;
            case 1: sb.append(" belongs to "); break;
            default: sb.append(" is part of "); break;
          }
          sb.append(info.getEmpireName());
          sb.append(". ");
          if (info.getRace() == SpaceRace.MECHIONS) {
            sb.append("Luckly planet is occupied by Mechions which are"
                + " immune to deadly viruses. This does not affect to"
                + "planet in anyway.");
          } else {
            sb.append("Planet is immediately placed on guarantee to stop "
                + "the virus spreading. Bad news is that only one population "
                + "is immune to virus. Most of the population is dead.");
            int pop = planet.getTotalPopulation();
            pop = pop - 1;
            for (int i = 0; i < pop; i++) {
              planet.killOneWorker("deadly virus", "deadly virus", map);
            }
           info.getTechList().addTech(TechFactory.createImprovementTech(
               "Deadly virus", 4));
            sb.append("Genetic code of virus is saved and stored carefully.");
            event.setNewsWorthy(true);
          }
          event.setText(sb.toString());
          ImageInstruction instructions = new ImageInstruction();
          instructions.addImage(ImageInstruction.VIRUSES);
          event.setImageInstructions(instructions.build());
          Message message = new Message(MessageType.PLANETARY, event.getText(),
              Icons.getIconByName(Icons.ICON_DEATH));
          message.setCoordinate(planet.getCoordinate());
          message.setRandomEventPop(true);
          info.getMsgList().addFirstMessage(message);
        }
      }
    }
  }

  /**
   * Handle corruption scandal.
   * @param event Random event must be corruption scandal.
   * @param map Starmap to locate planet for corruption.
   */
  public static void handleCorruptionScandal(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.CORRUPTION_SCANDAL) {
      PlayerInfo info = event.getRealm();
      ImageInstruction instructions = new ImageInstruction();
      instructions.addBackground(ImageInstruction.BACKGROUND_GREY_GRADIENT);
      instructions.addSiluete(info.getRace().getNameSingle(),
          ImageInstruction.POSITION_LEFT);
      int bestValue = 0;
      Planet mostValuablePlanet = null;
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          int value = planet.getTotalPopulation() * 10 + planet.getCulture();
          if (value > bestValue) {
            bestValue = value;
            mostValuablePlanet = planet;
          }
        }
      }
      String governorCorrupted = "";
      if (mostValuablePlanet != null
          && mostValuablePlanet.getGovernor() != null) {
        instructions.addSiluete(mostValuablePlanet.getGovernor().getRace()
            .getNameSingle(), ImageInstruction.POSITION_RIGHT);
        if (!mostValuablePlanet.getGovernor().hasPerk(Perk.CORRUPTED)) {
          mostValuablePlanet.getGovernor().addPerk(Perk.CORRUPTED);
          governorCorrupted = " " + mostValuablePlanet.getGovernor()
              .getCallName() + " turns to be corrupted also in this scandal.";
        }
      }
      Leader ruler = info.getRuler();
      String rulerCorrupted = "";
      if (ruler != null) {
        instructions.addSiluete(ruler.getRace().getNameSingle(),
            ImageInstruction.POSITION_CENTER);
        if (!ruler.hasPerk(Perk.CORRUPTED)) {
          ruler.addPerk(Perk.CORRUPTED);
          rulerCorrupted = " " + ruler.getCallName()
              + " turns to be corrupted also in this scandal.";
        }
      }
      event.setImageInstructions(instructions.build());
      info.setTotalCredits(info.getTotalCredits() / 2);
      event.setText("Massive corruption scandal found in "
      + info.getEmpireName() + " government. Cleanining and fixing"
          + " the corruption requires half of the credits in treasury."
          + rulerCorrupted + governorCorrupted);
      Message message = new Message(MessageType.INFORMATION, event.getText(),
          Icons.getIconByName(Icons.ICON_CULTURE));
      if (mostValuablePlanet != null) {
        message.setCoordinate(mostValuablePlanet.getCoordinate());
      }
      message.setRandomEventPop(true);
      info.getMsgList().addFirstMessage(message);
    }
  }

  /**
   * Handle mysterious signal event.
   * @param event Random event, must be mysterious signal.
   * @param map Starmap to locate good signal source.
   */
  public static void handleMysteriousSignal(final RandomEvent event,
      final StarMap map) {
    if (event.getGoodType() == GoodRandomType.MYSTERIOUS_SIGNAL) {
      PlayerInfo info = event.getRealm();
      ArrayList<PlayerInfo> unknownRealms = new ArrayList<>();
      for (int i = 0; i < map.getPlayerList().getCurrentMaxRealms(); i++) {
        if (info.getDiplomacy().getDiplomacyList(i) != null
          && info.getDiplomacy().getDiplomacyList(i)
          .getNumberOfMeetings() == 0) {
          PlayerInfo realm = map.getPlayerList().getPlayerInfoByIndex(i);
          if (realm != null && realm != info) {
            unknownRealms.add(realm);
          }
        }
      }
      if (unknownRealms.size() > 0) {
        PlayerInfo realm = DiceGenerator.pickRandom(unknownRealms);
        ArrayList<Planet> planets = new ArrayList<>();
        for (Planet planet : map.getPlanetList()) {
          if (planet.getPlanetPlayerInfo() == realm
              && planet.getOrderNumber() > 0) {
            planets.add(planet);
          }
        }
        if (planets.size() > 0) {
          Planet planet = DiceGenerator.pickRandom(planets);
          Sun sun = map.locateSolarSystem(planet.getCoordinate().getX(),
              planet.getCoordinate().getY());
          if (sun != null) {
            ImageInstruction instructions = new ImageInstruction();
            instructions.addImage(ImageInstruction.SIGNAL);
            event.setImageInstructions(instructions.build());
            event.setSun(sun);
            event.setText("Scientiests have received mysterious signal from"
                + " star called " + sun.getName() + ". This signal is very"
                + " likely from intelligent source. This source should be"
                + " studied further by making a visit in that system and"
                + " try to locate origin of the signal.");
            Message message = new Message(MessageType.PLANETARY,
                event.getText(), Icons.getIconByName(Icons.ICON_SCANNER));
            message.setCoordinate(sun.getCenterCoordinate());
            message.setRandomEventPop(true);
            info.getMsgList().addFirstMessage(message);
            if (!info.isHuman()) {
              Mission mission = new Mission(MissionType.EXPLORE,
                  MissionPhase.PLANNING, new Coordinate(sun.getCenterX(),
                      sun.getCenterY()));
              mission.setSunName(sun.getName());
              info.getMissions().add(mission);
            }
          }
        }
      }
    }
  }

  /**
   * Handle solar activity dimished.
   * @param event Random event must be solar activity dimished
   * @param map Starmap for looking sun
   */
  public static void handleSolarActivityDecreased(final RandomEvent event,
      final StarMap map) {
    if (event.getGoodType() == GoodRandomType.SOLAR_ACTIVITY_DIMISHED) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info
            && planet.getOrderNumber() > 0
            && planet.getRadiationLevel() > 1) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        Sun sun = map.locateSolarSystem(planet.getCoordinate().getX(),
            planet.getCoordinate().getY());
        if (sun != null) {
          event.setSun(sun);
          event.setText("Solar activity in " + sun.getName()
              + " has dimished. This cause radiation level drop in planets"
              + " which are in the same system.");
          ImageInstruction instructions = new ImageInstruction();
          instructions.addImage(ImageInstruction.SOLAR_NO_FLARES);
          event.setImageInstructions(instructions.build());
          event.setNewsWorthy(true);
          Message message = new Message(MessageType.PLANETARY, event.getText(),
              Icons.getIconByName(Icons.ICON_RADIATION));
          message.setCoordinate(sun.getCenterCoordinate());
          message.setRandomEventPop(true);
          map.broadcastMessage(message, true);
          for (Planet orbiter : map.getPlanetList()) {
            Sun solar = map.locateSolarSystem(orbiter.getCoordinate().getX(),
                orbiter.getCoordinate().getY());
            if (solar == sun) {
              orbiter.setRadiationLevel(orbiter.getRadiationLevel() - 1);
            }
          }
        }
      }
    }
  }

  /**
   * Handle solar activity increased.
   * @param event Random event must be solar activity increased
   * @param map Starmap for looking sun
   */
  public static void handleSolarActivityIncreased(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.SOLAR_ACTIVITY_INCREASE) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info
            && planet.getOrderNumber() > 0
            && planet.getRadiationLevel() < 10) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        Sun sun = map.locateSolarSystem(planet.getCoordinate().getX(),
            planet.getCoordinate().getY());
        if (sun != null) {
          event.setSun(sun);
          event.setText("Solar activity in " + sun.getName()
              + " has increased. This causes radiation level raise in planets"
              + " which are in the same system.");
          ImageInstruction instructions = new ImageInstruction();
          instructions.addImage(ImageInstruction.SOLAR_FLARES);
          event.setImageInstructions(instructions.build());
          event.setNewsWorthy(true);
          Message message = new Message(MessageType.PLANETARY, event.getText(),
              Icons.getIconByName(Icons.ICON_RADIATION));
          message.setCoordinate(sun.getCenterCoordinate());
          message.setRandomEventPop(true);
          map.broadcastMessage(message, true);
          for (Planet orbiter : map.getPlanetList()) {
            Sun solar = map.locateSolarSystem(orbiter.getCoordinate().getX(),
                orbiter.getCoordinate().getY());
            if (solar == sun) {
              orbiter.setRadiationLevel(orbiter.getRadiationLevel() + 1);
            }
          }
        }
      }
    }
  }

  /**
   * Handle good climate change.
   * @param event Random event must be climate change
   * @param map Starmap to locate planet
   */
  public static void handleGoodClimateChange(final RandomEvent event,
      final StarMap map) {
    if (event.getGoodType() == GoodRandomType.CLIMATE_CHANGE) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info
            && planet.hasClimateEvent()) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        planet.changeClimate(true);
        event.setPlanet(planet);
        event.setText(planet.getName() + " climate changes so that planet"
            + " can provide more food naturally. This is a good progress.");
        ImageInstruction instructions = new ImageInstruction();
        instructions.addImage(ImageInstruction.PARADISE);
        event.setImageInstructions(instructions.build());
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            Icons.getIconByName(Icons.ICON_FARM));
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle bad climate change.
   * @param event Random event must be climate change
   * @param map Starmap to locate planet
   */
  public static void handleBadClimateChange(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.CLIMATE_CHANGE) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info
            && planet.hasClimateEvent()) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        planet.changeClimate(false);
        event.setPlanet(planet);
        event.setText(planet.getName() + " climate changes so that planet"
            + " provides less food naturally. This is very bad progress.");
        ImageInstruction instructions = new ImageInstruction();
        instructions.addImage(ImageInstruction.DESERT);
        event.setImageInstructions(instructions.build());
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            Icons.getIconByName(Icons.ICON_DEATH));
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle aggressive wild life
   * @param event Random event must be aggressive wild life
   * @param map Starmap to locate planet
   */
  public static void handleAggressiveWildLife(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.AGGRESSIVE_WILD_LIFE) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0) {
        Planet planet = DiceGenerator.pickRandom(planets);
        event.setPlanet(planet);
        int value = DiceGenerator.getRandom(10, 15);
        planet.fightAgainstAttacker(value, map, "wild life",
            "fighting against wildlife", "fighting against wildlife");
        StringBuilder sb = new StringBuilder();
        sb.append(planet.getName());
        sb.append(" has dangerous animals which usually"
            + " are peaceful. Some reason these animals attack against "
            + "planet's population.");
        String animalType = "animal";
        switch (value) {
          default:
          case 10: {
            animalType = "big canine animals";
            break;
          }
          case 11: {
            animalType = "big feline animals";
            break;
          }
          case 12: {
            animalType = "big lizard animals";
            break;
          }
          case 13: {
            animalType = "massive herding pack animals";
            break;
          }
          case 14: {
            animalType = "ferocious bipedal reptiles";
            break;
          }
          case 15: {
            animalType = "massive bug like creature";
            break;
          }
        }
        if (planet.getTotalPopulation() > 0) {
          sb.append(" Population however survived from the attack of ");
          sb.append(animalType);
          sb.append(". ");
        } else {
          planet.setPlanetOwner(-1, null);
          sb.append(" All population died from the attack of ");
          sb.append(animalType);
          sb.append(". ");
        }
        int freeSlots = planet.getGroundSize() - planet.getNumberOfBuildings();
        if (freeSlots > 0) {
          String name = "Wildlife: " + animalType;
          Building building = BuildingFactory.createByName(name);
          if (building != null) {
            planet.addBuilding(building);
          } else {
            ErrorLogger.debug("Could not find building name " + name + ".");
          }
          sb.append("These " + animalType + " have occupied "
              + " sector from planet surface.");
        }
        event.setText(sb.toString());
        ImageInstruction instructions = new ImageInstruction();
        instructions.addImage(ImageInstruction.SPINOSAURUS);
        event.setImageInstructions(instructions.build());
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            Icons.getIconByName(Icons.ICON_DEATH));
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle raiders random event.
   * @param event Random event must be raiders
   * @param map Starmap to locat planet.
   */
  public static void handleRaiders(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() == BadRandomType.RAIDERS) {
      PlayerInfo info = event.getRealm();
      ArrayList<Planet> planets = new ArrayList<>();
      for (Planet planet : map.getPlanetList()) {
        if (planet.getPlanetPlayerInfo() == info) {
          planets.add(planet);
        }
      }
      if (planets.size() > 0
          && map.getPlayerList().getSpacePiratePlayer() != null) {
        Planet planet = DiceGenerator.pickRandom(planets);
        event.setPlanet(planet);
        PlayerInfo board = map.getPlayerList().getSpacePiratePlayer();
        int x = planet.getCoordinate().getX();
        int y = planet.getCoordinate().getY();
        int count = DiceGenerator.getRandom(1, 4);
        if (!map.isBlocked(x, y + 1)) {
          map.addSpacePirate(x, y + 1, board);
          count--;
        }
        if (!map.isBlocked(x, y - 1) && count > 0) {
          map.addSpacePirate(x, y - 1, board);
          count--;
        }
        if (!map.isBlocked(x - 1, y) && count > 0) {
          map.addSpacePirate(x - 1, y, board);
          count--;
        }
        if (!map.isBlocked(x + 1, y) && count > 0) {
          map.addSpacePirate(x + 1, y, board);
          count--;
        }
        event.setText("Space pirate raiders appear near " + planet.getName()
           + ". Looks like this was surprise attack from space pirates.");
        ImageInstruction instructions = new ImageInstruction();
        instructions.addImage(ImageInstruction.PIRATE_RAIDERS);
        event.setImageInstructions(instructions.build());
        Message message = new Message(MessageType.PLANETARY, event.getText(),
            Icons.getIconByName(Icons.ICON_HULL_TECH));
        message.setCoordinate(planet.getCoordinate());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
      }
    }
  }

  /**
   * Handle mutiny event. Requires that board player is enabled.
   * @param event Random event must be mutiny.
   * @param boardPlayer Board player or null if space pirates are disabled.
   */
  public static void handleMutiny(final RandomEvent event,
      final PlayerInfo boardPlayer) {
    if (event.getBadType() == BadRandomType.MUTINY
        && boardPlayer != null) {
      PlayerInfo info = event.getRealm();
      ArrayList<Fleet> fleets = new ArrayList<>();
      for (int i = 0; i < info.getFleets().getNumberOfFleets(); i++) {
        Fleet fleet = info.getFleets().getByIndex(i);
        if (fleet.getMilitaryValue() > 0) {
          fleets.add(fleet);
        }
      }
      if (fleets.size() > 0) {
        Fleet fleet = DiceGenerator.pickRandom(fleets);
        event.setFleet(fleet);
        info.getMissions().deleteMissionForFleet(fleet.getName());
        info.getFleets().removeFleet(fleet);
        boardPlayer.getFleets().add(fleet);
        event.setText("Fleet called " + fleet.getName()
            + " decided to start space pirating. " + info.getEmpireName()
            + " lost control of that fleet.");
        ImageInstruction instructions = new ImageInstruction();
        instructions.addImage(ImageInstruction.MUTINY);
        event.setImageInstructions(instructions.build());
        Message message = new Message(MessageType.FLEET, event.getText(),
            Icons.getIconByName(Icons.ICON_HULL_TECH));
        message.setCoordinate(fleet.getCoordinate());
        message.setMatchByString(fleet.getName());
        message.setRandomEventPop(true);
        info.getMsgList().addFirstMessage(message);
        if (fleet.getCommander() != null) {
          if (fleet.getCommander().hasPerk(Perk.WEALTHY)) {
            message = new Message(MessageType.FLEET,
                fleet.getCommander().getMilitaryRank().toString()
                + " " + fleet.getCommander().getName()
                + " has paid for crew to save "
                + fleet.getCommander().getGender().getHisHer() + " life.",
                Icons.getIconByName(Icons.ICON_COMMANDER));
            message.setCoordinate(fleet.getCoordinate());
            message.setMatchByString(fleet.getName());
            info.getMsgList().addNewMessage(message);
            fleet.getCommander().useWealth();
            fleet.getCommander().setJob(Job.UNASSIGNED);
            fleet.setCommander(null);
          } else {
            fleet.getCommander().assignJob(Job.DEAD, info);
            message = new Message(MessageType.FLEET,
                fleet.getCommander().getMilitaryRank().toString()
                + " " + fleet.getCommander().getName()
                + " died during mutiny.",
                Icons.getIconByName(Icons.ICON_COMMANDER));
            message.setCoordinate(fleet.getCoordinate());
            message.setMatchByString(fleet.getName());
            info.getMsgList().addNewMessage(message);
            fleet.setCommander(null);
          }
        }
      }
    }
  }

  /**
   * Handle random event. This handles all random events including both
   * good and bad events.
   * @param event RandomEvent
   * @param map StarMap
   * @return True if event was handled succesfully, false otherwise.
   */
  public static boolean handleRandomEvent(final RandomEvent event,
      final StarMap map) {
    if (event.getBadType() != null) {
      switch (event.getBadType()) {
        case AGGRESSIVE_WILD_LIFE: {
          handleAggressiveWildLife(event, map);
          break;
        }
        case CATASTROPHIC_ACCIDENT: {
          handleCatastrophicAccident(event, map);
          break;
        }
        case CLIMATE_CHANGE: {
          handleBadClimateChange(event, map);
          break;
        }
        case CORRUPTION_SCANDAL: {
          handleCorruptionScandal(event, map);
          break;
        }
        case DEADLY_VIRUS_OUTBREAK: {
          handleDeadlyVirusOutbreak(event, map);
          break;
        }
        case MASSIVE_DATA_LOST: {
          handleMassiveDataLost(event, map);
          break;
        }
        case METEOR_HIT: {
          handleMeteorHit(event, map);
          break;
        }
        case MUTINY: {
          handleMutiny(event, map.getPlayerList().getSpacePiratePlayer());
          break;
        }
        case RAIDERS: {
          handleRaiders(event, map);
          break;
        }
        case RULER_STRESS: {
          handleRulerStress(event, map);
          break;
        }
        case ACCIDENT: {
          handleAccident(event);
          break;
        }
        case TERRORIST_ATTACK: {
          handleTerroristAttack(event, map);
          break;
        }
        default:
        case SOLAR_ACTIVITY_INCREASE: {
          handleSolarActivityIncreased(event, map);
          break;
        }
      }
    } else if (event.getGoodType() != null) {
      switch (event.getGoodType()) {
        case CLIMATE_CHANGE: {
          handleGoodClimateChange(event, map);
          break;
        }
        case DESERTED_SHIP: {
          handleDesertedShip(event, map);
          break;
        }
        case LOST_TREASURE_FOUND: {
          handleLostTreasure(event, map);
          break;
        }
        case MISSED_METEOROID: {
          handleMissedMeteoroid(event, map);
          break;
        }
        case MYSTERIOUS_SIGNAL: {
          handleMysteriousSignal(event, map);
          break;
        }
        case SOLAR_ACTIVITY_DIMISHED: {
          handleSolarActivityDecreased(event, map);
          break;
        }
        case LEADER_LEVEL: {
          handleLeaderLevel(event);
          break;
        }
        case CULTURAL_HIT: {
          handleCulturalHit(event, map);
          break;
        }
        default:
        case TECHNICAL_BREAKTHROUGH: {
          handleTechnicalBreakThrough(event, map);
          break;
        }
      }
    }
    if (!event.getText().isEmpty()) {
      // Succeed to handle random event.
      return true;
    }
    return false;
  }
}
