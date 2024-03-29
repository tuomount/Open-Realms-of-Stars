package org.openRealmOfStars.game.state;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2024 Tuomo Untinen
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

public class AiRealmSetupViewTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testElderNumberConversion() {
    int i = AiRealmSetupView.parseAmountOfElder("No elder realms");
    assertEquals(0, i);
    i = AiRealmSetupView.parseAmountOfElder("One elder realms");
    assertEquals(1, i);
    i = AiRealmSetupView.parseAmountOfElder("2 elder realms");
    assertEquals(2, i);
    i = AiRealmSetupView.parseAmountOfElder("12 elder realms");
    assertEquals(12, i);
  }

}
