package org.openRealmOfStars.player.leader;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.SpaceRace.SpaceRace;
import org.openRealmOfStars.player.government.GovernmentType;
import org.openRealmOfStars.starMap.planet.Planet;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2019,2020 Tuomo Untinen
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
 * Test for Leader utility class
 */
public class LeaderUtilityTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerHumanKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setGender(Gender.FEMALE);
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Queen", tmp);
    leader.setGender(Gender.MALE);
    tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("King", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerHumanDemogracy() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.DEMOCRACY);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("President", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerHumanEmpire() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.EMPIRE);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    leader.setGender(Gender.FEMALE);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Empiress", tmp);
    leader.setGender(Gender.MALE);
    tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Emperor", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerCentaurHiearchy() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.CENTAURS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.HIERARCHY);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    leader.setGender(Gender.FEMALE);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Lady", tmp);
    leader.setGender(Gender.MALE);
    tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Lord", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerScaurianEnterprise() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SCAURIANS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.ENTERPRISE);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("CEO", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerSporkClan() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.CLAN);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Chief", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerGreyanHegemony() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.GREYANS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.HEGEMONY);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Leader", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerMothoidHivemind() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MOTHOIDS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.HIVEMIND);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Master", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSLevel1HumanKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet, 1);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    assertNotEquals(0, leader.getPerkList().size());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testSLevel2HumanKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet, 2);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(2, leader.getLevel());
    assertEquals(0, leader.getExperience());
    assertNotEquals(0, leader.getPerkList().size());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerMechionAi() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MECHIONS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.AI);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
    leader.setJob(Job.RULER);
    String tmp = LeaderUtility.createTitleForLeader(leader, info);
    assertEquals("Main Process", tmp);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerMothoidEmpire() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.MOTHOIDS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.EMPIRE);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testStartRulerSporkKingdom() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.SPORKS);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    assertEquals("Test Planet I", leader.getHomeworld());
    assertNotEquals(null, leader.getName());
    assertEquals(1, leader.getLevel());
    assertEquals(0, leader.getExperience());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testGoodPerks() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    Perk[] perks = LeaderUtility.getNewPerks(leader,
        LeaderUtility.PERK_TYPE_GOOD);
    assertEquals(25, perks.length);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testBadPerks() {
    PlayerInfo info = Mockito.mock(PlayerInfo.class);
    Mockito.when(info.getRace()).thenReturn(SpaceRace.HUMAN);
    Mockito.when(info.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    Planet planet = Mockito.mock(Planet.class);
    Mockito.when(planet.getName()).thenReturn("Test Planet I");
    Leader leader = LeaderUtility.createLeader(info, planet,
        LeaderUtility.LEVEL_START_RULER);
    Perk[] perks = LeaderUtility.getNewPerks(leader,
        LeaderUtility.PERK_TYPE_BAD);
    assertEquals(true, perks.length == 13 || perks.length == 14);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderKingdom() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.KINGDOM);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader parent = new Leader("Test Parent");
    parent.setAge(75);
    parent.setJob(Job.DEAD);
    pool.add(parent);
    Leader leader = new Leader("Test Leader");
    leader.setAge(50);
    leader.setParent(parent);
    leader.getPerkList().add(Perk.COMBAT_MASTER);
    leader.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.MICRO_MANAGER);
    leader.getPerkList().add(Perk.MILITARISTIC);
    leader.getPerkList().add(Perk.POWER_HUNGRY);
    leader.getPerkList().add(Perk.WARLORD);
    pool.add(leader);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader, ruler);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(50);
    leader2.setParent(null);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.DISCIPLINE);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.POWER_HUNGRY);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    leader.setJob(Job.DEAD);
    ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderHorde() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.HORDE);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader parent = new Leader("Test Parent");
    parent.setAge(75);
    parent.setJob(Job.DEAD);
    pool.add(parent);
    Leader leader = new Leader("Test Leader");
    leader.setAge(30);
    leader.setParent(parent);
    leader.getPerkList().add(Perk.COMBAT_MASTER);
    leader.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.MICRO_MANAGER);
    leader.getPerkList().add(Perk.MILITARISTIC);
    leader.getPerkList().add(Perk.POWER_HUNGRY);
    leader.getPerkList().add(Perk.WARLORD);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(50);
    leader2.setParent(null);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.DISCIPLINE);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.POWER_HUNGRY);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderCeo() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.ENTERPRISE);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(30);
    leader.getPerkList().add(Perk.COMBAT_MASTER);
    leader.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.MICRO_MANAGER);
    leader.getPerkList().add(Perk.MILITARISTIC);
    leader.getPerkList().add(Perk.POWER_HUNGRY);
    leader.getPerkList().add(Perk.WARLORD);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(50);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.MERCHANT);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.POWER_HUNGRY);
    leader2.getPerkList().add(Perk.ACADEMIC);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderDemocracy() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.ALLIANCE);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(80);
    leader.getPerkList().add(Perk.INDUSTRIAL);
    leader.getPerkList().add(Perk.MINER);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.PACIFIST);
    leader.getPerkList().add(Perk.GOOD_LEADER);
    leader.getPerkList().add(Perk.POWER_HUNGRY);
    leader.getPerkList().add(Perk.ACADEMIC);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(80);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.MERCHANT);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.ACADEMIC);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderFederation() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.FEDERATION);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(80);
    leader.getPerkList().add(Perk.INDUSTRIAL);
    leader.getPerkList().add(Perk.MINER);
    leader.getPerkList().add(Perk.DISCIPLINE);
    leader.getPerkList().add(Perk.CHARISMATIC);
    leader.getPerkList().add(Perk.COUNTER_AGENT);
    leader.getPerkList().add(Perk.CORRUPTED);
    leader.getPerkList().add(Perk.PACIFIST);
    leader.getPerkList().add(Perk.GOOD_LEADER);
    leader.getPerkList().add(Perk.SLOW_LEARNER);
    leader.getPerkList().add(Perk.STUPID);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(80);
    leader2.getPerkList().add(Perk.COMBAT_MASTER);
    leader2.getPerkList().add(Perk.COMBAT_TACTICIAN);
    leader2.getPerkList().add(Perk.MERCHANT);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.COUNTER_AGENT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.ACADEMIC);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderHegemony() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.HEGEMONY);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(80);
    leader.getPerkList().add(Perk.SLOW_LEARNER);
    leader.getPerkList().add(Perk.STUPID);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(85);
    leader2.getPerkList().add(Perk.SCIENTIST);
    leader2.getPerkList().add(Perk.FTL_ENGINEER);
    leader2.getPerkList().add(Perk.EXPLORER);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.SCANNER_EXPERT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.ACADEMIC);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testNextLeaderAi() {
    PlayerInfo realm = Mockito.mock(PlayerInfo.class);
    Mockito.when(realm.getGovernment()).thenReturn(GovernmentType.AI);
    ArrayList<Leader> pool = new ArrayList<>();
    Mockito.when(realm.getLeaderPool()).thenReturn(pool);
    Leader leader = new Leader("Test Leader");
    leader.setAge(18);
    leader.getPerkList().add(Perk.SLOW_LEARNER);
    leader.getPerkList().add(Perk.STUPID);
    pool.add(leader);
    Leader leader2 = new Leader("Test Leader2");
    leader2.setAge(18);
    leader2.getPerkList().add(Perk.SCIENTIST);
    leader2.getPerkList().add(Perk.FTL_ENGINEER);
    leader2.getPerkList().add(Perk.EXPLORER);
    leader2.getPerkList().add(Perk.CHARISMATIC);
    leader2.getPerkList().add(Perk.SCANNER_EXPERT);
    leader2.getPerkList().add(Perk.CORRUPTED);
    leader2.getPerkList().add(Perk.MICRO_MANAGER);
    leader2.getPerkList().add(Perk.TRADER);
    leader2.getPerkList().add(Perk.ACADEMIC);
    leader2.getPerkList().add(Perk.MILITARISTIC);
    leader2.getPerkList().add(Perk.WARLORD);
    pool.add(leader2);
    Leader ruler = LeaderUtility.getNextRuler(realm);
    assertEquals(leader2, ruler);
  }

}
