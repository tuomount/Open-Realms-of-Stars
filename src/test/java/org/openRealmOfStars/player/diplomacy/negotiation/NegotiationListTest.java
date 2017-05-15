package org.openRealmOfStars.player.diplomacy.negotiation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;

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
 * Tests for NegotiationList
 */
public class NegotiationListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    NegotiationOffer offer = Mockito.mock(NegotiationOffer.class);
    Mockito.when(offer.getOfferValue(SpaceRace.HUMAN)).thenReturn(5);
    NegotiationOffer offer2 = Mockito.mock(NegotiationOffer.class);
    Mockito.when(offer2.getOfferValue(SpaceRace.HUMAN)).thenReturn(7);
    
    NegotiationList list = new NegotiationList();
    list.add(offer);
    assertEquals(1, list.getSize());
    assertEquals(offer, list.getByIndex(0));
    assertEquals(5, list.getOfferValue(SpaceRace.HUMAN));
    list.add(offer2);
    assertEquals(2, list.getSize());
    assertEquals(offer, list.getByIndex(0));
    assertEquals(offer2, list.getByIndex(1));
    assertEquals(12, list.getOfferValue(SpaceRace.HUMAN));
    list.remove(0);
    assertEquals(1, list.getSize());
    assertEquals(offer2, list.getByIndex(0));
    assertEquals(7, list.getOfferValue(SpaceRace.HUMAN));
    list.add(offer);
    assertEquals(12, list.getOfferValue(SpaceRace.HUMAN));
  }

}
