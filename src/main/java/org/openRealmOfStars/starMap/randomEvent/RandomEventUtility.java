package org.openRealmOfStars.starMap.randomEvent;

import java.util.ArrayList;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.tech.TechType;
import org.openRealmOfStars.starMap.StarMap;
import org.openRealmOfStars.starMap.Sun;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.utilities.DiceGenerator;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2019 Tuomo Untinen
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
    int index = DiceGenerator.getRandom(values.length - 1);
    RandomEvent event = new RandomEvent(values[index], realm);
    return event;
  }

  /**
   * Create bad random event.
   * @param realm Realm who is getting the event
   * @return RandomEvent
   */
  public static RandomEvent createBadRandomEvent(final PlayerInfo realm) {
    BadRandomType[] values = BadRandomType.values();
    int index = DiceGenerator.getRandom(values.length - 1);
    RandomEvent event = new RandomEvent(values[index], realm);
    return event;
  }

  /**
   * Handle massive data lost event.
   * @param event Random event, must be Massive data lost.
   */
  public static void handleMassiveDataLost(final RandomEvent event) {
    if (event.getBadType() == BadRandomType.MASSIVE_DATA_LOST) {
      PlayerInfo info = event.getRealm();
      int index = DiceGenerator.getRandom(TechType.values().length - 1);
      info.getTechList().setTechResearchPoints(TechType.getTypeByIndex(index),
          0);
      String techName = TechType.getTypeByIndex(index).toString();
      event.setText("A massive computer virus spreads in all labs which study "
          + techName + " technology. "
          + "This virus deletes all the data related to research. "
          + "All work for next tech in " + techName + " is lost. "
          + "Scientists need to start all over from the scratch.");
    }
  }

  /**
   * Handle techinical breakthrough
   * @param event Random event, must be technical breakthrough
   */
  public static void handleTechnicalBreakThrough(final RandomEvent event) {
    if (event.getGoodType() == GoodRandomType.TECHNICAL_BREAKTHROUGH) {
      PlayerInfo info = event.getRealm();
      int index = DiceGenerator.getRandom(TechType.values().length - 1);
      double original = info.getTechList().getTechResearchPoints(
          TechType.getTypeByIndex(index));
      if (original < 10) {
        original = 20;
      } else {
        original = original * 2;
      }
      info.getTechList().setTechResearchPoints(TechType.getTypeByIndex(index),
          original);
      String techName = TechType.getTypeByIndex(index).toString();
      event.setText("Scientist make breakthrough which boost "
          + techName + " technology. "
          + "This will gains us to get faster new technology in "
          + techName + ".");
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
        int index = DiceGenerator.getRandom(planets.size() - 1);
        Planet planet = planets.get(index);
        event.setPlanet(planet);
        StringBuilder sb = new StringBuilder();
        sb.append("Massive meteor hits the the atmosphere of ");
        sb.append(planet.getName());
        sb.append(". ");
        if (planet.getTurretLvl() > 0) {
          sb.append("Planet's defense turrets shoot the meteor to pieces ");
          sb.append("and metal debris is being scattered around the planet.");
          planet.setAmountMetalInGround(planet.getAmountMetalInGround()
              + DiceGenerator.getRandom(80, 500));
        } else {
          planet.setMetal(planet.getMetal()
              + DiceGenerator.getRandom(80, 500));
          sb.append("Meteor hits the planet ");
          boolean miss = true;
          int hitSpot = DiceGenerator.getRandom(0, planet.getGroundSize() - 1);
          if (hitSpot <= planet.getTotalPopulation()) {
            sb.append("and kills one worker ");
            planet.killOneWorker();
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
        int index = DiceGenerator.getRandom(planets.size() - 1);
        Planet planet = planets.get(index);
        event.setPlanet(planet);
        StringBuilder sb = new StringBuilder();
        sb.append("Massive meteoroid passes by");
        sb.append(planet.getName());
        sb.append(" very close. So close that planet scienties are able"
            + "to mine metal from the meteoroid.");
        planet.setAmountMetalInGround(planet.getAmountMetalInGround()
            + DiceGenerator.getRandom(80, 400));
        event.setText(sb.toString());
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
        int index = DiceGenerator.getRandom(planets.size() - 1);
        Planet planet = planets.get(index);
        event.setPlanet(planet);
        StringBuilder sb = new StringBuilder();
        sb.append("Deadly virus outbreaks at ");
        sb.append(planet.getName());
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
            planet.killOneWorker();
          }
        }
        event.setText(sb.toString());
      }
    }
  }

  /**
   * Handle corruption scandal.
   * @param event Random event must be corruption scandal.
   */
  public static void handleCorruptionScandal(final RandomEvent event) {
    if (event.getBadType() == BadRandomType.CORRUPTION_SCANDAL) {
      PlayerInfo info = event.getRealm();
      info.setTotalCredits(info.getTotalCredits() / 2);
      event.setText("Massive corruption scandal found in "
      + info.getEmpireName() + " government. Cleanining and fixing"
          + "the corruption requires half of the credits in treasury.");
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
        int index = DiceGenerator.getRandom(unknownRealms.size() - 1);
        PlayerInfo realm = unknownRealms.get(index);
        ArrayList<Planet> planets = new ArrayList<>();
        for (Planet planet : map.getPlanetList()) {
          if (planet.getPlanetPlayerInfo() == realm
              && planet.getOrderNumber() > 0) {
            planets.add(planet);
          }
        }
        if (planets.size() > 0) {
          index = DiceGenerator.getRandom(planets.size() - 1);
          Planet planet = planets.get(index);
          Sun sun = map.locateSolarSystem(planet.getCoordinate().getX(),
              planet.getCoordinate().getY());
          if (sun != null) {
            event.setSun(sun);
            event.setText("Scientiests have received mysterious signal from"
                + " star called " + sun.getName() + ". This signal is very"
                + " likely from intelligent source. This source should be"
                + " studied further by making a visit in that system and"
                + " try to locate origin of the signal.");
          }
        }
      }
    }
  }

}
