package org.openRealmOfStars.player.artifact;

import static org.junit.Assert.assertEquals;

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
* Artifact JUnit test
*
*/
public class ArtifactTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    Artifact artifact = new Artifact(0, "Test artifact",
        ArtifactType.ELECTRONIC);
    assertEquals(0, artifact.getIndex());
    assertEquals("Test artifact", artifact.getName());
    assertEquals(ArtifactType.ELECTRONIC, artifact.getArtifactType());
    assertEquals(0, artifact.getOneTimeTechBonus());
    artifact.setOneTimeTechBonus(15);
    assertEquals(15, artifact.getOneTimeTechBonus());
    artifact.setDescription("Description");
    assertEquals("Description", artifact.getDescription());
    assertEquals("Test artifact - Electronic\n"
        + "Tech bonus: 15\n"
        + "Description", artifact.getFullDescription());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testTypes() {
    Artifact artifact = new Artifact(0, "Test artifact",
        ArtifactType.MILITARY);
    assertEquals("Military", artifact.getArtifactType().toString());
    artifact = new Artifact(0, "Test artifact",
        ArtifactType.DEFENSE);
    assertEquals("Defense", artifact.getArtifactType().toString());
    artifact = new Artifact(0, "Test artifact",
        ArtifactType.SHIPHULL);
    assertEquals("Shiphull", artifact.getArtifactType().toString());
    artifact = new Artifact(0, "Test artifact",
        ArtifactType.FACILITY);
    assertEquals("Facility", artifact.getArtifactType().toString());
    artifact = new Artifact(0, "Test artifact",
        ArtifactType.ENERGY);
    assertEquals("Energy", artifact.getArtifactType().toString());
    artifact = new Artifact(0, "Test artifact",
        ArtifactType.ELECTRONIC);
    assertEquals("Electronic", artifact.getArtifactType().toString());
  }

}
