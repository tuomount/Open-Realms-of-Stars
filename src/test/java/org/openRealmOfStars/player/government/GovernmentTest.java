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
  public void testGuild() {
    System.out.println("Old:" + GovernmentType.GUILD.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("GUILD")
        .getDescription(true));
  }

  @Test
  public void testEnterprise() {
    System.out.println("Old:" + GovernmentType.ENTERPRISE.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("ENTERPRISE")
        .getDescription(true));
  }
  @Test
  public void testHegemony() {
    System.out.println("Old:" + GovernmentType.HEGEMONY.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("HEGEMONY")
        .getDescription(true));
  }
  @Test
  public void testNest() {
    System.out.println("Old:" + GovernmentType.NEST.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("NEST")
        .getDescription(true));
  }
  @Test
  public void testHivemind() {
    System.out.println("Old:" + GovernmentType.HIVEMIND.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("HIVEMIND")
        .getDescription(true));
  }
  @Test
  public void testAi() {
    System.out.println("Old:" + GovernmentType.AI.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("AI")
        .getDescription(true));
  }

  @Test
  public void testEmpire() {
    System.out.println("Old:" + GovernmentType.EMPIRE.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("EMPIRE")
        .getDescription(true));
  }

  @Test
  public void testHierarchy() {
    System.out.println("Old:" + GovernmentType.HIERARCHY.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("HIERARCHY")
        .getDescription(true));
  }

  @Test
  public void testHorde() {
    System.out.println("Old:" + GovernmentType.HORDE.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("HORDE")
        .getDescription(true));
  }

  @Test
  public void testClan() {
    System.out.println("Old:" + GovernmentType.CLAN.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("CLAN")
        .getDescription(true));
  }

  @Test
  public void testCollective() {
    System.out.println("Old:" + GovernmentType.COLLECTIVE.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("COLLECTIVE")
        .getDescription(true));
  }

  @Test
  public void testUtopia() {
    System.out.println("Old:" + GovernmentType.UTOPIA.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("UTOPIA")
        .getDescription(true));
  }
  @Test
  public void testRegime() {
    System.out.println("Old:" + GovernmentType.REGIME.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("REGIME")
        .getDescription(true));
  }

  @Test
  public void testKingdom() {
    System.out.println("Old:" + GovernmentType.KINGDOM.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("KINGDOM")
        .getDescription(true));
  }
  @Test
  public void testFeudalism() {
    System.out.println("Old:" + GovernmentType.FEUDALISM.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("FEUDALISM")
        .getDescription(true));
  }

  @Test
  public void testTechnocracy() {
    System.out.println("Old:" + GovernmentType.TECHNOCRACY.getDescription(true));
    System.out.println("New:" + GovernmentFactory.createOne("TECHNOCRACY")
        .getDescription(true));
  }

}
