package org.openRealmOfStars.player.diplomacy.negotiation;

import java.util.ArrayList;

import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
* Negotiation list containing multiple offers. This is not saved for
* saved games. Negotations needs to be handled immediately.
*
*/
public class NegotiationList {

  /**
   * List containing NegotiationOffers
   */
  private ArrayList<NegotiationOffer> list;

  /**
   * Constructor for negotiation list
   */
  public NegotiationList() {
    list = new ArrayList<>();
  }

  /**
   * Adds offer to the list
   * @param offer NegotiationOffer
   */
  public void add(final NegotiationOffer offer) {
    list.add(offer);
  }

  /**
   * Get the size of list
   * @return Size of list
   */
  public int getSize() {
    return list.size();
  }

  /**
   * Get single negotiation offer from the list
   * @param index for the list
   * @return NegotiationOffer or null
   */
  public NegotiationOffer getByIndex(final int index) {
    if (index > -1 && index < list.size()) {
      return list.get(index);
    }
    return null;
  }

  /**
   * Remove single offer from the list
   * @param index Index to remove
   */
  public void remove(final int index) {
    if (index > -1 && index < list.size()) {
      list.remove(index);
    }
  }

  /**
   * Get the all offer values in list
   * @param race SpaceRace who is valueing the offer
   * @return Value of offer
   */
  public int getOfferValue(final SpaceRace race) {
    int value = 0;
    for (NegotiationOffer offer : list) {
      value = value + offer.getOfferValue(race);
    }
    return value;
  }

  /**
   * Is planet in Offer
   * @return True if planet is being offered
   */
  public boolean isPlanetInOffer() {
    return isTypeInOffer(NegotiationType.PLANET);
  }

  /**
   * Is peace in Offer
   * @return True if peace is being offered
   */
  public boolean isPeaceInOffer() {
    return isTypeInOffer(NegotiationType.PEACE);
  }

  /**
   * Is war in Offer
   * @return True if war is being offered
   */
  public boolean isWarInOffer() {
    return isTypeInOffer(NegotiationType.WAR);
  }

  /**
   * Is certain negotiation type in Offer
   * @param type NegotiationType
   * @return True if type is being offered
   */
  public boolean isTypeInOffer(final NegotiationType type) {
    for (NegotiationOffer offer : list) {
      if (offer.getNegotiationType() == type) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is Fleet in Offer
   * @return True if fleet is being offered
   */
  public boolean isFleetInOffer() {
    return isTypeInOffer(NegotiationType.FLEET);
  }

  /**
   * Return possible trade embargo offer
   * @return NegotiatioOffer or null
   */
  public NegotiationOffer getEmbargoOffer() {
    for (NegotiationOffer offer : list) {
      if (offer.getNegotiationType() == NegotiationType.TRADE_EMBARGO) {
        return offer;
      }
    }
    return null;
  }

}
