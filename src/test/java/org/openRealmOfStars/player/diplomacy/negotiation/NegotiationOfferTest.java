package org.openRealmOfStars.player.diplomacy.negotiation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2017 Tuomo Untinen
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
 * Tests for NegotiationOffer
 */
public class NegotiationOfferTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    NegotiationOffer offer = new NegotiationOffer(NegotiationType.ALLIANCE, null);
    assertEquals(NegotiationType.ALLIANCE, offer.getNegotiationType());
    assertEquals(null, offer.getOfferObject());

    offer = new NegotiationOffer(NegotiationType.TRADE_ALLIANCE, null);
    assertEquals(NegotiationType.TRADE_ALLIANCE, offer.getNegotiationType());
    assertEquals(null, offer.getOfferObject());

    offer = new NegotiationOffer(NegotiationType.PEACE, null);
    assertEquals(NegotiationType.PEACE, offer.getNegotiationType());
    assertEquals(null, offer.getOfferObject());

    offer = new NegotiationOffer(NegotiationType.DIPLOMAT, null);
    assertEquals(NegotiationType.DIPLOMAT, offer.getNegotiationType());
    assertEquals(null, offer.getOfferObject());

    offer = new NegotiationOffer(NegotiationType.MAP, null);
    assertEquals(NegotiationType.MAP, offer.getNegotiationType());
    assertEquals(null, offer.getOfferObject());

    Integer credit = new Integer(5);
    offer = new NegotiationOffer(NegotiationType.CREDIT, credit);
    assertEquals(NegotiationType.CREDIT, offer.getNegotiationType());
    assertEquals(credit, offer.getOfferObject());
    assertEquals(5, offer.getCreditValue());
    assertEquals(null, offer.getFleet());
    assertEquals(null, offer.getPlanet());
    assertEquals(null, offer.getTech());

    Fleet fleet = Mockito.mock(Fleet.class);
    offer = new NegotiationOffer(NegotiationType.FLEET, fleet);
    assertEquals(NegotiationType.FLEET, offer.getNegotiationType());
    assertEquals(fleet, offer.getOfferObject());
    assertEquals(fleet, offer.getFleet());
    assertEquals(0, offer.getCreditValue());

    fleet = Mockito.mock(Fleet.class);
    offer = new NegotiationOffer(NegotiationType.RECALL_FLEET, fleet);
    assertEquals(NegotiationType.RECALL_FLEET, offer.getNegotiationType());
    assertEquals(fleet, offer.getOfferObject());
    assertEquals(fleet, offer.getFleet());
    assertEquals(0, offer.getCreditValue());

    Planet planet = Mockito.mock(Planet.class);
    offer = new NegotiationOffer(NegotiationType.PLANET, planet);
    assertEquals(NegotiationType.PLANET, offer.getNegotiationType());
    assertEquals(planet, offer.getOfferObject());
    assertEquals(planet, offer.getPlanet());

    Tech tech = Mockito.mock(Tech.class);
    offer = new NegotiationOffer(NegotiationType.TECH, tech);
    assertEquals(NegotiationType.TECH, offer.getNegotiationType());
    assertEquals(tech, offer.getOfferObject());
    assertEquals(tech, offer.getTech());
  }

  @Test(expected=IllegalArgumentException.class)
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testIllegalObject() {
    Fleet fleet = Mockito.mock(Fleet.class);
    NegotiationOffer offer = new NegotiationOffer(NegotiationType.PLANET, fleet);
    assertEquals(null, offer.getOfferObject());
  }
  
  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testOfferValues() {
    NegotiationOffer offer = new NegotiationOffer(NegotiationType.ALLIANCE, null);
    assertEquals(0, offer.getOfferValue(SpaceRace.HUMAN));

    offer = new NegotiationOffer(NegotiationType.TRADE_ALLIANCE, null);
    assertEquals(0, offer.getOfferValue(SpaceRace.HUMAN));

    offer = new NegotiationOffer(NegotiationType.PEACE, null);
    assertEquals(0, offer.getOfferValue(SpaceRace.HUMAN));

    offer = new NegotiationOffer(NegotiationType.SPY_TRADE, null);
    assertEquals(0, offer.getOfferValue(SpaceRace.HUMAN));

    offer = new NegotiationOffer(NegotiationType.DIPLOMAT, null);
    assertEquals(5, offer.getOfferValue(SpaceRace.HUMAN));

    offer = new NegotiationOffer(NegotiationType.MAP, null);
    assertEquals(12, offer.getOfferValue(SpaceRace.HUMAN));

    Integer credit = new Integer(7);
    offer = new NegotiationOffer(NegotiationType.CREDIT, credit);
    assertEquals(7, offer.getOfferValue(SpaceRace.HUMAN));

    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(24);
    offer = new NegotiationOffer(NegotiationType.FLEET, fleet);
    assertEquals(12, offer.getOfferValue(SpaceRace.HUMAN));

    fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(24);
    offer = new NegotiationOffer(NegotiationType.RECALL_FLEET, fleet);
    assertEquals(0, offer.getOfferValue(SpaceRace.HUMAN));

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.getGroundSize()).thenReturn(13);
    Mockito.when(planet.getTotalPopulation()).thenReturn(6);
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(2);
    offer = new NegotiationOffer(NegotiationType.PLANET, planet);
    assertEquals(13, offer.getOfferValue(SpaceRace.HUMAN));
    planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.getGroundSize()).thenReturn(13);
    Mockito.when(planet.getTotalPopulation()).thenReturn(6);
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(10);
    offer = new NegotiationOffer(NegotiationType.PLANET, planet);
    assertEquals(0, offer.getOfferValue(SpaceRace.HUMAN));

    Tech tech = Mockito.mock(Tech.class);
    Mockito.when(tech.getLevel()).thenReturn(3);
    offer = new NegotiationOffer(NegotiationType.TECH, tech);
    assertEquals(6, offer.getOfferValue(SpaceRace.HUMAN));

  }

}
