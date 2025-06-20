package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2018-2024 Tuomo Untinen
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

import java.awt.event.ActionListener;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.ship.ShipComponent;

/**
*
* Test for ShipDesignView class
*
*/
public class ShipDesignViewTest {

  @Test
  @Category(org.openRealmOfStars.BehaviourTest.class)
  public void test() {
    ActionListener listener = Mockito.mock(ActionListener.class);
    PlayerInfo info = new PlayerInfo(SpaceRaceFactory.createOne("HUMANS"),
        4, 0);
    ShipDesignView view = new ShipDesignView(info, null, listener);
    view.updatePanels(false);
    assertEquals(false, view.isDesignOK());
    ShipComponent[] list = view.filterComponents("All");
    assertEquals(5, list.length);
    list = view.filterComponents("Weapons");
    assertEquals(1, list.length);
    list = view.filterComponents("Defense");
    assertEquals(1, list.length);
    list = view.filterComponents("Propulsion");
    assertEquals(2, list.length);
    list = view.filterComponents("Modules");
    assertEquals(1, list.length);
    list = view.filterComponents("Electronics");
    assertEquals(0, list.length);
    list = view.filterComponents("Invasion");
    assertEquals(0, list.length);
    list = view.filterComponents("Colony module");
    assertEquals(1, list.length);
    assertEquals(false, view.isBanNukes());
    assertEquals(false, view.isBanPrivateer());
    view.setBanNukes(true);
    assertEquals(true, view.isBanNukes());
    assertEquals(false, view.isBanPrivateer());
    view.setBanPrivateer(true);
    assertEquals(true, view.isBanNukes());
    assertEquals(true, view.isBanPrivateer());
  }

}
