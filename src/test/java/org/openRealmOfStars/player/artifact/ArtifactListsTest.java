package org.openRealmOfStars.player.artifact;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

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
* Tests for Artifact lists.
*
*/
public class ArtifactListsTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBasic() {
    ArtifactLists lists = new ArtifactLists();
    assertEquals(0, lists.getArtifactResearchPoints(), 0.1);
    lists.setArtifactResearchPoints(1);
    assertEquals(1, lists.getArtifactResearchPoints(), 0.1);
    assertEquals(0, lists.getDiscoveredArtifacts().length);
    assertEquals(0, lists.getResearchedArtifacts().length);
    Artifact artifact = Mockito.mock(Artifact.class);
    lists.addDiscoveredArtifact(artifact);
    assertEquals(1, lists.getDiscoveredArtifacts().length);
    assertEquals(0, lists.getResearchedArtifacts().length);
    Artifact test = lists.researchArtifact();
    assertEquals(0, lists.getDiscoveredArtifacts().length);
    assertEquals(1, lists.getResearchedArtifacts().length);
    assertEquals(artifact, test);
  }

}
