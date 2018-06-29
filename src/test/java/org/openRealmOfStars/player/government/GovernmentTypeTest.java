package org.openRealmOfStars.player.government;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.government.GovernmentType;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2016-2018  Tuomo Untinen
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
* Government Type enumeration tests
*
*/
public class GovernmentTypeTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testToStrings() {
    assertEquals("Democracy\n"
        + "Generic happiness: 1\n"
        + "Diplomatic bonus: 1\n"
        + "Trade bonus: 1\n"
        + "War resistance: -1\n"
        + "Production bonus: 1\n", GovernmentType.DEMOCRACY.toString());
    assertEquals("Alliance\n"
        + "Generic happiness: 1\n"
        + "Diplomatic bonus: 1\n"
        + "Trade bonus: 1\n"
        + "War resistance: -1\n"
        + "Production bonus: 1\n", GovernmentType.ALLIANCE.toString());
    assertEquals("Federation\n"
        + "Generic happiness: 1\n"
        + "Diplomatic bonus: 1\n"
        + "Trade bonus: 1\n", GovernmentType.FEDERATION.toString());
    assertEquals("Republic\n"
        + "Generic happiness: 1\n"
        + "Diplomatic bonus: 1\n"
        + "Trade bonus: 1\n", GovernmentType.REPUBLIC.toString());
    assertEquals("Guild\n"
        + "Trade bonus: 2\n"
        + "Credit bonus: 1\n"
        + "Credit rush\n", GovernmentType.GUILD.toString());
    assertEquals("Enterprise\n"
        + "Trade bonus: 2\n"
        + "Credit bonus: 1\n"
        + "Credit rush\n", GovernmentType.ENTERPRISE.toString());
    assertEquals("Hegemony\n"
        + "Generic happiness: -1\n"
        + "War resistance: 1\n"
        + "Research bonus: 1\n", GovernmentType.HEGEMONY.toString());
    assertEquals("Nest\n"
        + "No effects on happines nor war fatigue",
        GovernmentType.NEST.toString());
    assertEquals("Hive-mind\n"
        + "No effects on happines nor war fatigue",
        GovernmentType.HIVEMIND.toString());
    assertEquals("AI\n"
        + "No effects on happines nor war fatigue",
        GovernmentType.AI.toString());
    assertEquals("Empire\n"
        + "Generic happiness: -1\n"
        + "War resistance: 1\n"
        + "Production bonus: 1\n"
        + "Population rush\n", GovernmentType.EMPIRE.toString());
    assertEquals("Kingdom\n"
        + "Generic happiness: -1\n"
        + "War resistance: 1\n"
        + "Production bonus: 1\n"
        + "Population rush\n", GovernmentType.KINGDOM.toString());
    assertEquals("Hierarchy\n"
        + "Generic happiness: -1\n"
        + "War resistance: 1\n"
        + "Production bonus: 1\n"
        + "Population rush\n", GovernmentType.HIERARCHY.toString());
    assertEquals("Horde\n"
        + "Generic happiness: -1\n"
        + "War resistance: 1\n"
        + "Food bonus: 1\n"
        + "War happiness\n"
        + "Population rush\n", GovernmentType.HORDE.toString());
    assertEquals("Horde\n"
        + "Generic happiness: -1\n"
        + "War resistance: 1\n"
        + "Production bonus: 1\n"
        + "War happiness\n"
        + "Population rush\n", GovernmentType.MECHANICAL_HORDE.toString());
    assertEquals("Clan\n"
        + "Generic happiness: -1\n"
        + "War resistance: 1\n"
        + "Food bonus: 1\n"
        + "War happiness\n"
        + "Population rush\n", GovernmentType.CLAN.toString());
  }

}
