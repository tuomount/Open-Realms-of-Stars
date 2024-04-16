package org.openRealmOfStars.player.government;
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


import org.junit.Test;

/**
 * Government Tests
 *
 */
public class GovernmentTest {

  @Test
  public void testDemocracy() {
    System.out.println("Old:" + GovernmentType.DEMOCRACY.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("DEMOCRACY")
        .getDescription(true));
  }

  @Test
  public void testUnion() {
    System.out.println("Old:" + GovernmentType.UNION.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("UNION")
        .getDescription(true));
  }

  @Test
  public void testFederation() {
    System.out.println("Old:" + GovernmentType.FEDERATION.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("FEDERATION")
        .getDescription(true));
  }

  @Test
  public void testRepublic() {
    System.out.println("Old:" + GovernmentType.REPUBLIC.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("REPUBLIC")
        .getDescription(true));
  }

  @Test
  public void testKingdom() {
    System.out.println("Old:" + GovernmentType.KINGDOM.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("KINGDOM")
        .getDescription(true));
  }

}
