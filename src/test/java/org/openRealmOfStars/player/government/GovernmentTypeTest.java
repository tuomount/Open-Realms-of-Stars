package org.openRealmOfStars.player.government;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openRealmOfStars.player.government.GovernmentType;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2018,2019-2021  Tuomo Untinen
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
  public void testDescriptions() {
    String[] expected = new String[GovernmentType.values().length];
    expected[0] = "### Democracy\n"
        + "* Base fleet capacity: 2\n"
        + "* Leader capacity: 10\n"
        + "* Leader cost: 10\n"
        + "* Reign length: 20 turns\n"
        + "* Generic happiness: 1\n"
        + "* Diplomatic bonus: 1\n"
        + "* Trade bonus: 1\n"
        + "* War resistance: -1\n"
        + "* Production bonus: 1\n"
        + "* Credit rush\n";
    expected[1] = "### Union\n"
        + "* Base fleet capacity: 2\n"
        + "* Leader capacity: 10\n"
        + "* Leader cost: 10\n"
        + "* Reign length: 20 turns\n"
        + "* Generic happiness: 1\n"
        + "* Diplomatic bonus: 1\n"
        + "* Trade bonus: 1\n"
        + "* War resistance: -1\n"
        + "* Mining bonus: 1\n"
        + "* Credit rush\n";
    expected[2] = "### Federation\n"
        + "* Base fleet capacity: 3\n"
        + "* Leader capacity: 10\n"
        + "* Leader cost: 10\n"
        + "* Reign length: 20 turns\n"
        + "* Generic happiness: 1\n"
        + "* Diplomatic bonus: 1\n"
        + "* Trade bonus: 1\n"
        + "* Credit rush\n";
    expected[3] = "### Republic\n"
        + "* Base fleet capacity: 3\n"
        + "* Leader capacity: 10\n"
        + "* Leader cost: 10\n"
        + "* Reign length: 20 turns\n"
        + "* Diplomatic bonus: 1\n"
        + "* Trade bonus: 1\n"
        + "* Production bonus: 1\n"
        + "* Credit rush\n";
    expected[4] = "### Guild\n"
        + "* Base fleet capacity: 3\n"
        + "* Leader capacity: 12\n"
        + "* Leader cost: 12\n"
        + "* Reign length: 40 turns\n"
        + "* Trade bonus: 2\n"
        + "* Production bonus: 1\n"
        + "* Credit rush\n";
    expected[5] = "### Enterprise\n"
        + "* Base fleet capacity: 3\n"
        + "* Leader capacity: 12\n"
        + "* Leader cost: 12\n"
        + "* Reign length: 40 turns\n"
        + "* Trade bonus: 2\n"
        + "* Credit bonus: 1\n"
        + "* Credit rush\n";
    expected[6] = "### Hegemony\n"
        + "* Base fleet capacity: 4\n"
        + "* Leader capacity: 8\n"
        + "* Leader cost: 8\n"
        + "* Reign length: life time\n"
        + "* Possibility to internal power struggle\n"
        + "* Generic happiness: -1\n"
        + "* War resistance: 1\n"
        + "* Research bonus: 1\n";
    expected[7] = "### Nest\n"
        + "* Base fleet capacity: 3\n"
        + "* Leader capacity: 6\n"
        + "* Leader cost: 5\n"
        + "* Reign length: life time\n"
        + "* Rulers have heirs\n"
        + "* Possibility to internal power struggle\n"
        + "* No effects on happines nor war fatigue\n";
    expected[8] = "### Hive-mind\n"
        + "* Base fleet capacity: 4\n"
        + "* Leader capacity: 6\n"
        + "* Leader cost: 5\n"
        + "* Reign length: life time\n"
        + "* Possibility to internal power struggle\n"
        + "* No effects on happines nor war fatigue\n";
    expected[9] = "### AI\n"
        + "* Base fleet capacity: 3\n"
        + "* Leader capacity: 6\n"
        + "* Leader cost: 5\n"
        + "* Reign length: 100 turns\n"
        + "* No effects on happines nor war fatigue\n";
    expected[10] = "### Empire\n"
        + "* Base fleet capacity: 4\n"
        + "* Leader capacity: 7\n"
        + "* Leader cost: 10\n"
        + "* Reign length: life time\n"
        + "* Rulers have heirs\n"
        + "* Possibility to internal power struggle\n"
        + "* Generic happiness: -1\n"
        + "* War resistance: 1\n"
        + "* Production bonus: 1\n"
        + "* Population rush\n";
    expected[11] = "### Kingdom\n"
        + "* Base fleet capacity: 4\n"
        + "* Leader capacity: 7\n"
        + "* Leader cost: 10\n"
        + "* Reign length: life time\n"
        + "* Rulers have heirs\n"
        + "* Possibility to internal power struggle\n"
        + "* Generic happiness: -1\n"
        + "* War resistance: 1\n"
        + "* Credit bonus: 1\n"
        + "* Population rush\n";
    expected[12] = "### Hierarchy\n"
        + "* Base fleet capacity: 4\n"
        + "* Leader capacity: 8\n"
        + "* Leader cost: 8\n"
        + "* Reign length: life time\n"
        + "* Possibility to internal power struggle\n"
        + "* Generic happiness: -1\n"
        + "* War resistance: 1\n"
        + "* Production bonus: 1\n"
        + "* Population rush\n";
    expected[13] = "### Horde\n"
        + "* Base fleet capacity: 3\n"
        + "* Leader capacity: 9\n"
        + "* Leader cost: 10\n"
        + "* Reign length: life time\n"
        + "* Rulers have heirs\n"
        + "* Possibility to internal power struggle\n"
        + "* Generic happiness: -1\n"
        + "* War resistance: 1\n"
        + "* Mining bonus: 1\n"
        + "* Food bonus: 1\n"
        + "* War happiness\n"
        + "* Population rush\n";
    expected[14] = "### Horde\n"
        + "* Base fleet capacity: 4\n"
        + "* Leader capacity: 10\n"
        + "* Leader cost: 10\n"
        + "* Reign length: life time\n"
        + "* Possibility to internal power struggle\n"
        + "* Generic happiness: -1\n"
        + "* War resistance: 1\n"
        + "* Production bonus: 1\n"
        + "* War happiness\n"
        + "* Population rush\n";
    expected[15] = "### Clan\n"
        + "* Base fleet capacity: 4\n"
        + "* Leader capacity: 9\n"
        + "* Leader cost: 10\n"
        + "* Reign length: life time\n"
        + "* Rulers have heirs\n"
        + "* Possibility to internal power struggle\n"
        + "* Generic happiness: -1\n"
        + "* War resistance: 1\n"
        + "* Food bonus: 1\n"
        + "* War happiness\n"
        + "* Population rush\n";
    expected[16] = "### Collective\n"
        + "* Base fleet capacity: 4\n"
        + "* Leader capacity: 6\n"
        + "* Leader cost: 8\n"
        + "* Reign length: 50 turns\n"
        + "* War resistance: 1\n"
        + "* Mining bonus: 1\n";
    for (int i = 0; i <  GovernmentType.values().length; i++) {
      GovernmentType government = GovernmentType.values()[i];
      assertEquals(expected[i], government.getDescription(true));
      if (i == 0) {
        String result = government.getDescription(false);
        if (!result.startsWith("<html>")) {
          assertFalse(true);
        }
      }
      //System.out.println(government.getDescription(true));
    }
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testToString() {
    assertEquals("Democracy", GovernmentType.DEMOCRACY.toString());
  }

}
