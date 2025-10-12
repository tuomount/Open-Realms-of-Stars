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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.fleet.Fleet;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.tech.Tech;
import org.openRealmOfStars.starMap.planet.Planet;
import org.openRealmOfStars.starMap.planet.enums.RadiationType;

/**
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
    offer.setMapValue(12);
    assertEquals(12, offer.getMapValue());
    offer.setMapValue(18);
    assertEquals(15, offer.getMapValue());
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"));
    assertEquals(15, offer.getOfferValue(info));
    assertEquals(NegotiationType.MAP, offer.getNegotiationType());
    assertEquals(null, offer.getOfferObject());

    Integer credit = Integer.valueOf(5);
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

    offer = new NegotiationOffer(NegotiationType.MAP_PLANETS, null);
    offer.setMapValue(5);
    assertEquals(5, offer.getMapValue());
    offer.setMapValue(18);
    assertEquals(7, offer.getMapValue());
    assertEquals(7, offer.getOfferValue(info));
    assertEquals(NegotiationType.MAP_PLANETS, offer.getNegotiationType());
    assertEquals(null, offer.getOfferObject());

    offer = new NegotiationOffer(NegotiationType.TRADE_EMBARGO, info);
    assertEquals(NegotiationType.TRADE_EMBARGO, offer.getNegotiationType());
    assertEquals(info, offer.getOfferObject());
    assertEquals(info, offer.getRealm());

    Integer value = Integer.valueOf(5);
    offer = new NegotiationOffer(NegotiationType.PROMISE_VOTE_YES, value);
    assertEquals(NegotiationType.PROMISE_VOTE_YES, offer.getNegotiationType());
    assertEquals(value, offer.getOfferObject());
    assertEquals(5, offer.getPromiseValue());

    value = Integer.valueOf(5);
    offer = new NegotiationOffer(NegotiationType.PROMISE_VOTE_NO, value);
    assertEquals(NegotiationType.PROMISE_VOTE_NO, offer.getNegotiationType());
    assertEquals(value, offer.getOfferObject());
    assertEquals(5, offer.getPromiseValue());

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
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"));
    NegotiationOffer offer = new NegotiationOffer(NegotiationType.ALLIANCE, null);
    assertEquals(0, offer.getOfferValue(info));

    offer = new NegotiationOffer(NegotiationType.TRADE_ALLIANCE, null);
    assertEquals(0, offer.getOfferValue(info));

    offer = new NegotiationOffer(NegotiationType.PEACE, null);
    assertEquals(0, offer.getOfferValue(info));

    offer = new NegotiationOffer(NegotiationType.SPY_TRADE, null);
    assertEquals(0, offer.getOfferValue(info));

    offer = new NegotiationOffer(NegotiationType.DIPLOMAT, null);
    assertEquals(5, offer.getOfferValue(info));

    offer = new NegotiationOffer(NegotiationType.MAP, null);
    assertEquals(12, offer.getOfferValue(info));

    Integer credit = Integer.valueOf(7);
    offer = new NegotiationOffer(NegotiationType.CREDIT, credit);
    assertEquals(7, offer.getOfferValue(info));

    Fleet fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(24);
    offer = new NegotiationOffer(NegotiationType.FLEET, fleet);
    assertEquals(12, offer.getOfferValue(info));

    fleet = Mockito.mock(Fleet.class);
    Mockito.when(fleet.getMilitaryValue()).thenReturn(24);
    offer = new NegotiationOffer(NegotiationType.RECALL_FLEET, fleet);
    assertEquals(0, offer.getOfferValue(info));

    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.getGroundSize()).thenReturn(13);
    Mockito.when(planet.getTotalPopulation()).thenReturn(6);
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(
        RadiationType.NO_RADIATION);
    Mockito.when(planet.isColonizeablePlanet(Mockito.any())).thenReturn(true);
    offer = new NegotiationOffer(NegotiationType.PLANET, planet);
    assertEquals(13, offer.getOfferValue(info));
    planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getAmountMetalInGround()).thenReturn(5000);
    Mockito.when(planet.getGroundSize()).thenReturn(13);
    Mockito.when(planet.getTotalPopulation()).thenReturn(6);
    Mockito.when(planet.getTotalRadiationLevel()).thenReturn(
        RadiationType.VERY_HIGH_RAD);
    offer = new NegotiationOffer(NegotiationType.PLANET, planet);
    assertEquals(0, offer.getOfferValue(info));

    Tech tech = Mockito.mock(Tech.class);
    Mockito.when(tech.getLevel()).thenReturn(3);
    offer = new NegotiationOffer(NegotiationType.TECH, tech);
    assertEquals(10, offer.getOfferValue(info));

    offer = new NegotiationOffer(NegotiationType.MAP_PLANETS, null);
    assertEquals(NegotiationType.MAP_PLANETS, offer.getNegotiationType());
    assertEquals(null, offer.getOfferObject());
    assertEquals(5, offer.getOfferValue(info));

    offer = new NegotiationOffer(NegotiationType.TRADE_EMBARGO, info);
    assertEquals(NegotiationType.TRADE_EMBARGO, offer.getNegotiationType());
    assertEquals(info, offer.getOfferObject());
    assertEquals(0, offer.getOfferValue(info));

    Integer value = Integer.valueOf(5);
    offer = new NegotiationOffer(NegotiationType.PROMISE_VOTE_YES, value);
    assertEquals(NegotiationType.PROMISE_VOTE_YES, offer.getNegotiationType());
    assertEquals(value, offer.getOfferObject());
    assertEquals(5, offer.getOfferValue(info));

    value = Integer.valueOf(5);
    offer = new NegotiationOffer(NegotiationType.PROMISE_VOTE_NO, value);
    assertEquals(NegotiationType.PROMISE_VOTE_NO, offer.getNegotiationType());
    assertEquals(value, offer.getOfferObject());
    assertEquals(5, offer.getOfferValue(info));

  }

}
