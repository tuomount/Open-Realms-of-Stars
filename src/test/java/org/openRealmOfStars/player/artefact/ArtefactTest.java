package org.openRealmOfStars.player.artefact;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.experimental.categories.Category;
/**
*
* Open Realm of Stars game project
* Copyright (C) 2022 Tuomo Untinen
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
* Artefact JUnit test
*
*/
public class ArtefactTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Artefact artefact = new Artefact(0, "Test artefact",
        ArtefactType.ELECTRONIC);
    assertEquals(0, artefact.getIndex());
    assertEquals("Test artefact", artefact.getName());
    assertEquals(ArtefactType.ELECTRONIC, artefact.getArtefactType());
    assertEquals(0, artefact.getOneTimeTechBonus());
    artefact.setOneTimeTechBonus(15);
    assertEquals(15, artefact.getOneTimeTechBonus());
  }

}
