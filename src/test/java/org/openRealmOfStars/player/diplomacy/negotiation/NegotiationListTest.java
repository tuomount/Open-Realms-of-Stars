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
import org.openRealmOfStars.player.race.SpaceRace;

/**
 * Tests for NegotiationList
 */
public class NegotiationListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    NegotiationOffer offer = Mockito.mock(NegotiationOffer.class);
    Mockito.when(offer.getOfferValue(info)).thenReturn(5);
    NegotiationOffer offer2 = Mockito.mock(NegotiationOffer.class);
    Mockito.when(offer2.getOfferValue(info)).thenReturn(7);

    NegotiationList list = new NegotiationList();
    list.add(offer);
    assertEquals(1, list.getSize());
    assertEquals(offer, list.getByIndex(0));
    assertEquals(5, list.getOfferValue(info));
    list.add(offer2);
    assertEquals(2, list.getSize());
    assertEquals(offer, list.getByIndex(0));
    assertEquals(offer2, list.getByIndex(1));
    assertEquals(12, list.getOfferValue(info));
    list.remove(0);
    assertEquals(1, list.getSize());
    assertEquals(offer2, list.getByIndex(0));
    assertEquals(7, list.getOfferValue(info));
    list.add(offer);
    assertEquals(12, list.getOfferValue(info));
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testPlanetsAndFleets() {
    PlayerInfo info = new PlayerInfo(SpaceRace.HUMAN);
    NegotiationOffer offer = Mockito.mock(NegotiationOffer.class);
    Mockito.when(offer.getOfferValue(info)).thenReturn(15);
    Mockito.when(offer.getNegotiationType()).thenReturn(NegotiationType.PLANET);
    NegotiationOffer offer2 = Mockito.mock(NegotiationOffer.class);
    Mockito.when(offer2.getOfferValue(info)).thenReturn(7);
    Mockito.when(offer2.getNegotiationType()).thenReturn(NegotiationType.FLEET);
    NegotiationOffer offer3 = Mockito.mock(NegotiationOffer.class);
    Mockito.when(offer3.getOfferValue(info)).thenReturn(25);
    Mockito.when(offer3.getNegotiationType()).thenReturn(NegotiationType.PEACE);
    NegotiationOffer offer4 = Mockito.mock(NegotiationOffer.class);
    Mockito.when(offer4.getOfferValue(info)).thenReturn(-30);
    Mockito.when(offer4.getNegotiationType()).thenReturn(NegotiationType.WAR);

    NegotiationList list = new NegotiationList();
    assertEquals(false, list.isPlanetInOffer());
    list.add(offer);
    assertEquals(1, list.getSize());
    assertEquals(offer, list.getByIndex(0));
    assertEquals(15, list.getOfferValue(info));
    assertEquals(true, list.isPlanetInOffer());
    assertEquals(false, list.isFleetInOffer());
    assertEquals(false, list.isPeaceInOffer());
    assertEquals(false, list.isWarInOffer());
    list.add(offer2);
    assertEquals(2, list.getSize());
    assertEquals(offer, list.getByIndex(0));
    assertEquals(offer2, list.getByIndex(1));
    assertEquals(22, list.getOfferValue(info));
    assertEquals(true, list.isPlanetInOffer());
    assertEquals(true, list.isFleetInOffer());
    assertEquals(false, list.isPeaceInOffer());
    assertEquals(false, list.isWarInOffer());
    list.remove(0);
    assertEquals(1, list.getSize());
    assertEquals(offer2, list.getByIndex(0));
    assertEquals(7, list.getOfferValue(info));
    assertEquals(false, list.isPlanetInOffer());
    assertEquals(true, list.isFleetInOffer());
    assertEquals(false, list.isPeaceInOffer());
    assertEquals(false, list.isWarInOffer());
    list.add(offer3);
    assertEquals(32, list.getOfferValue(info));
    assertEquals(false, list.isPlanetInOffer());
    assertEquals(true, list.isFleetInOffer());
    assertEquals(true, list.isPeaceInOffer());
    assertEquals(false, list.isWarInOffer());
    list.add(offer4);
    assertEquals(2, list.getOfferValue(info));
    assertEquals(false, list.isPlanetInOffer());
    assertEquals(true, list.isFleetInOffer());
    assertEquals(true, list.isPeaceInOffer());
    assertEquals(true, list.isWarInOffer());
  }

}
