package org.openRealmOfStars.player.diplomacy.negotiation;

import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.planet.Planet;

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
   * Constructor for Negotiation offer
   * @param type Negotiation Type
   * @param offer Offer object
   */
  public NegotiationOffer(final NegotiationType type, final Object offer) {
    if (type == NegotiationType.ALLIANCE
        || type == NegotiationType.PEACE
        || type == NegotiationType.TRADE_ALLIANCE
        || type == NegotiationType.MAP) {
      negotiationType = type;
      offerObject = null;
    } else if (type == NegotiationType.CREDIT && offer instanceof Integer
        || type == NegotiationType.FLEET && offer instanceof Fleet
        || type == NegotiationType.PLANET && offer instanceof Planet
        || type == NegotiationType.TECH && offer instanceof Tech) {
      negotiationType = type;
      offerObject = offer;
    } else {
      throw new IllegalArgumentException("Offer type is wrong for offer!");
    }
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
   * This returns null if offer type is not fleet.
   * Otherwise returns fleet.
   * @return Fleet or null.
   */
  public Fleet getFleet() {
    if (negotiationType == NegotiationType.FLEET
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

}
