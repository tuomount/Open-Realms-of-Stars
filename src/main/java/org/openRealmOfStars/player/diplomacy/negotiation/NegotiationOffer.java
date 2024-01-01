package org.openRealmOfStars.player.diplomacy.negotiation;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2017-2024 Tuomo Untinen
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
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.construction.Building;
import org.openRealmOfStars.starMap.planet.construction.BuildingFactory;

/**
*
* Negotiation offer for single object. This is not saved for
* saved games. Negotations needs to be handled immediately.
*
*/
public class NegotiationOffer {
  /**
   * Negotiation type
   */
  private NegotiationType negotiationType;
  /**
   * Offer object. This can be fleet, planet
   */
  private Object offerObject;

  /**
   * Map Value. Used for valuing the map.
   */
  private int mapValue;
  /**
   * Constructor for Negotiation offer
   * @param type Negotiation Type
   * @param offer Offer object
   */
  public NegotiationOffer(final NegotiationType type, final Object offer) {
    if (type == NegotiationType.ALLIANCE
        || type == NegotiationType.PEACE
        || type == NegotiationType.TRADE_ALLIANCE
        || type == NegotiationType.DIPLOMAT
        || type == NegotiationType.WAR
        || type == NegotiationType.ASK_PROTECTION
        || type == NegotiationType.DEFENSIVE_PACT
        || type == NegotiationType.SPY_TRADE) {
      negotiationType = type;
      offerObject = null;
    } else if (type == NegotiationType.CREDIT && offer instanceof Integer
        || type == NegotiationType.FLEET && offer instanceof Fleet
        || type == NegotiationType.RECALL_FLEET && offer instanceof Fleet
        || type == NegotiationType.PLANET && offer instanceof Planet
        || type == NegotiationType.TECH && offer instanceof Tech
        || type == NegotiationType.DISCOVERED_ARTIFACT
        && offer instanceof Integer
        || type == NegotiationType.TRADE_EMBARGO
        && offer instanceof PlayerInfo
        || type == NegotiationType.PROMISE_VOTE_YES
        && offer instanceof Integer
        || type == NegotiationType.PROMISE_VOTE_NO
        && offer instanceof Integer) {
      negotiationType = type;
      offerObject = offer;
    } else if (type == NegotiationType.MAP) {
      negotiationType = type;
      mapValue = 12;
      offerObject = null;
    } else if (type == NegotiationType.MAP_PLANETS) {
      negotiationType = type;
      mapValue = 5;
      offerObject = null;
    } else {
      throw new IllegalArgumentException("Offer type is wrong for offer!");
    }
  }

  /**
   * Get the offer value. More valuable offer is better.
   * @param info Realm for valuing the offer. Mostly used for
   *        Determine if planet is valuable for certain realm
   * @return offer value
   */
  public int getOfferValue(final PlayerInfo info) {
    int offerValue = 0;
    SpaceRace race = info.getRace();
    switch (negotiationType) {
    case ALLIANCE:
      // Both sides get Alliance so it's value to zero.
      offerValue = 0;
      break;
    case RECALL_FLEET:
      // Opponent either recalls the ship or it's war
      offerValue = 0;
      break;
    case TRADE_ALLIANCE:
      // Both sides get Trade Alliance so it's value to zero.
      offerValue = 0;
      break;
    case CREDIT:
      offerValue = getCreditValue();
      break;
    case FLEET:
      offerValue = getFleet().getMilitaryValue() / 2;
      break;
    case PEACE:
      // Both sides get Trade Alliance so it's value to zero.
      offerValue = 0;
      break;
    case DIPLOMAT:
      // Diplomat is valued for 5
      offerValue = 5;
      break;
    case MAP:
      offerValue = getMapValue();
      break;
    case TECH:
      Building building = null;
      boolean scienticAchievement = false;
      boolean farmingBuilding = false;
      if (getTech().getImprovement() != null) {
        building = BuildingFactory.createByName(getTech().getImprovement());
        scienticAchievement = building.getScientificAchievement();
        if (building.getFarmBonus() > 0) {
          farmingBuilding = true;
        }
      }
      if (getTech().getHull() != null
          && getTech().getHull().equals("Artificial planet")) {
        scienticAchievement = true;
      }
      if (scienticAchievement) {
        offerValue = getTech().getLevel() * 8;
      } else if (getTech().isRareTech()) {
        offerValue = getTech().getLevel() * 4;
      } else {
        offerValue = getTech().getLevel() * 2;
      }
      if (!race.isEatingFood() && farmingBuilding) {
        offerValue = 0;
      }
      break;
    case PLANET:
      offerValue = getPlanet().getAmountMetalInGround() / 1000;
      offerValue = offerValue + getPlanet().getTotalPopulation() / 3;
      offerValue = offerValue + getPlanet().getGroundSize() - 7;
      if (!getPlanet().isColonizeablePlanet(info)) {
        offerValue = 0;
      }
      break;
    case SPY_TRADE:
      // Both sides get Spy trade so it's value to zero.
      offerValue = 0;
      break;
    case MAP_PLANETS:
      offerValue = getMapValue();
      break;
    case PROMISE_VOTE_NO:
    case PROMISE_VOTE_YES:
      offerValue = getPromiseValue();
      break;
    case DISCOVERED_ARTIFACT:
      offerValue = getDiscoveredArtifacts() * 5;
      break;
    case ASK_PROTECTION:
      offerValue = 20;
      break;
    default:
      offerValue = 0;
      break;
    }
    return offerValue;
  }

  /**
   * Get Amount of credit offered
   * @return Get amount of credit offered in credit offer.
   * This returns always zero if offer was something else than credit
   */
  public int getCreditValue() {
    if (negotiationType == NegotiationType.CREDIT
        && offerObject instanceof Integer) {
      return ((Integer) offerObject).intValue();
    }
    return 0;
  }

  /**
   * Get Amount of discovered artifacts offered
   * @return Get amount of discovered artifacts offered in discovered
   *             artifact offer.
   * This returns always zero if offer was something else than
   * discovered artifact
   */
  public int getDiscoveredArtifacts() {
    if (negotiationType == NegotiationType.DISCOVERED_ARTIFACT
        && offerObject instanceof Integer) {
      return ((Integer) offerObject).intValue();
    }
    return 0;
  }

  /**
   * This returns null if offer type is not fleet or
   * recall fleet
   * Otherwise returns fleet.
   * @return Fleet or null.
   */
  public Fleet getFleet() {
    if (negotiationType == NegotiationType.FLEET
        && offerObject instanceof Fleet) {
      return ((Fleet) offerObject);
    }
    if (negotiationType == NegotiationType.RECALL_FLEET
        && offerObject instanceof Fleet) {
      return ((Fleet) offerObject);
    }
    return null;
  }

  /**
   * This returns null if offer type is not planet.
   * Otherwise returns planet.
   * @return Planet or null
   */
  public Planet getPlanet() {
    if (negotiationType == NegotiationType.PLANET
        && offerObject instanceof Planet) {
      return ((Planet) offerObject);
    }
    return null;
  }

  /**
   * This returns null if offer type is not tech.
   * Otherwise returns tech.
   * @return Tech or null
   */
  public Tech getTech() {
    if (negotiationType == NegotiationType.TECH
        && offerObject instanceof Tech) {
      return ((Tech) offerObject);
    }
    return null;
  }

  /**
   * Get Realm in offer
   * @return Realm in offer
   */
  public PlayerInfo getRealm() {
    if (negotiationType == NegotiationType.TRADE_EMBARGO
        && offerObject instanceof PlayerInfo) {
      return ((PlayerInfo) offerObject);
    }
    return null;
  }

  /**
   * Get value for voting promise.
   * @return Value for voting promise.
   */
  public int getPromiseValue() {
    if ((negotiationType == NegotiationType.PROMISE_VOTE_YES
        || negotiationType == NegotiationType.PROMISE_VOTE_NO)
        && offerObject instanceof Integer) {
      Integer value = (Integer) offerObject;
      return value.intValue();
    }
    return 0;
  }
  /**
   * Get Negotiation type
   * @return Negotiation type
   */
  public NegotiationType getNegotiationType() {
    return negotiationType;
  }

  /**
   * Get Offer object
   * @return Offer Object
   */
  public Object getOfferObject() {
    return offerObject;
  }

  /**
   * Get the map Value.
   * @return the mapValue
   */
  public int getMapValue() {
    return mapValue;
  }

  /**
   * Set the map value. Maximum value for map is 15. For map of planet maximum
   * value is 7.
   * @param mapValue the mapValue to set
   */
  public void setMapValue(final int mapValue) {
    int limit = mapValue;
    if (negotiationType == NegotiationType.MAP && limit > 15) {
      limit = 15;
    }
    if (negotiationType == NegotiationType.MAP_PLANETS && limit > 7) {
      limit = 7;
    }
    this.mapValue = limit;
  }

}
