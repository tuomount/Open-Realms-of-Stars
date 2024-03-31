package org.openRealmOfStars.player.leader;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2019-2024 Tuomo Untinen
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openRealmOfStars.player.PlayerInfo;
import org.openRealmOfStars.player.leader.stats.LeaderStats;
import org.openRealmOfStars.player.race.SpaceRace;
import org.openRealmOfStars.player.race.SpaceRaceFactory;
import org.openRealmOfStars.player.race.trait.TraitIds;
import org.openRealmOfStars.utilities.IOUtilities;

/**
*
* Leader information
*
*/
public class Leader {

  /**
   * Leader Name, probably surname is enough.
   */
  private String name;

  /**
   * Name of the homeworld.
   */
  private String homeworld;

  /**
   * Leader age in star years.
   */
  private int age;

  /**
   * Time in job
   */
  private int timeInJob;

  /**
   * Leader's experience level
   */
  private int level;

  /**
   * Leaders experience in current level
   */
  private int experience;

  /**
   * Leader's military rank
   */
  private MilitaryRank militaryRank;

  /**
   * Leader's gender. This affects only for leader's title texts.
   */
  private Gender gender;

  /**
   * Leader race
   */
  private SpaceRace race;

  /**
   * Highest title.
   * Examples:
   * empty just showing the military rank.
   * Governor of Planet
   * 5th President of Terran Democracy
   * Empire of Centaur Empire
   */
  private String title;

  /**
   * Leader's current job.
   */
  private Job job;

  /**
   * Parent for heirs. Not sure how to use this in game yet
   * but with this information family tree for rulers is
   * possible to write but none of the leaders cannot be
   * removed then.
   */
  private Leader parent;

  /**
   * This is only needed when loading the save game and not
   * for anything else.
   */
  private int parentIndex;

  /**
   * Perks that leader has.
   */
  private ArrayList<Perk> perkList;

  /**
   * Leader stats.
   */
  private LeaderStats stats;
  /**
   * Constructor for leader. Leader must have name.
   * @param name Leader name
   */
  public Leader(final String name) {
    this.name = name;
    this.homeworld = "";
    age = 28;
    setTimeInJob(0);
    level = 1;
    experience = 0;
    militaryRank = MilitaryRank.CIVILIAN;
    setRace(SpaceRaceFactory.createOne("HUMANS"));
    gender = Gender.NONE;
    job = Job.UNASSIGNED;
    setParent(null);
    parentIndex = -1;
    perkList = new ArrayList<>();
    stats = new LeaderStats();
  }

  /**
   * Read Leader from DataInputStream
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public Leader(final DataInputStream dis) throws IOException {
    name = IOUtilities.readString(dis);
    homeworld = IOUtilities.readString(dis);
    title = IOUtilities.readString(dis);
    int value = dis.readInt();
    age = value & 0x0000ffff;
    timeInJob = (value & 0xffff0000) >> 16;
    level = dis.read();
    experience = dis.readInt();
    setMilitaryRank(MilitaryRank.getByIndex(dis.read()));
    setRace(SpaceRaceFactory.createOne(IOUtilities.readString(dis)));
    setGender(Gender.getByIndex(dis.read()));
    job = Job.getByIndex(dis.read());
    parentIndex = dis.readInt();
    setParent(null);
    int size = dis.readInt();
    perkList = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      perkList.add(Perk.getByIndex(dis.read()));
    }
    stats = new LeaderStats(dis);
  }

  /**
   * Save leader Info to DataOutputStream
   * @param dos DataOutputStream
   * @param realm Realm info aka PlayerInfo
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveLeader(final DataOutputStream dos,
      final PlayerInfo realm) throws IOException {
    IOUtilities.writeString(dos, name);
    IOUtilities.writeString(dos, homeworld);
    IOUtilities.writeString(dos, title);
    int value = (getTimeInJob() << 16) + age;
    dos.writeInt(value);
    dos.writeByte(level);
    dos.writeInt(experience);
    dos.writeByte(militaryRank.getIndex());
    IOUtilities.writeString(dos, getRace().getId());
    dos.writeByte(getGender().getIndex());
    dos.writeByte(getJob().getIndex());
    if (getParent() == null) {
      dos.writeInt(-1);
    } else {
      dos.writeInt(realm.getLeaderIndex(parent));
    }
    dos.writeInt(perkList.size());
    for (int i = 0; i < perkList.size(); i++) {
      dos.writeByte(perkList.get(i).getIndex());
    }
    stats.saveLeaderStats(dos);
  }

  /**
   * Get Home world of leader.
   * @return Homeworld name
   */
  public String getHomeworld() {
    return homeworld;
  }

  /**
   * Set homeworld of leader
   * @param homeworld Planet name
   */
  public void setHomeworld(final String homeworld) {
    this.homeworld = homeworld;
  }

  /**
   * Get age of the leader in star years.
   * @return Age in star years
   */
  public int getAge() {
    return age;
  }

  /**
   * Set age of the leader in star years
   * @param age in star years.
   */
  public void setAge(final int age) {
    this.age = age;
  }

  /**
   * Get the highest title for leader
   * @return Highest title for leader
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set the highest title for leader
   * @param title Highest title for leader
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   * Get the leader name
   * @return Leader name as String
   */
  public String getName() {
    return name;
  }

  /**
   * Set leader name.
   * @param name Name as a string.
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Get Title and name for leader if leader has title.
   * @return Optional title + name.
   */
  public String getCallName() {
    StringBuilder sb = new StringBuilder(50);
    if (getTitle() != null && !getTitle().isEmpty()) {
      sb.append(getTitle());
      sb.append(" ");
    }
    sb.append(getName());
    return sb.toString();
  }
  /**
   * Get military rank for leader
   * @return the militaryRank
   */
  public MilitaryRank getMilitaryRank() {
    return militaryRank;
  }

  /**
   * Set military rank for leader
   * @param militaryRank the militaryRank to set
   */
  public void setMilitaryRank(final MilitaryRank militaryRank) {
    this.militaryRank = militaryRank;
  }

  /**
   * Get level of the leader
   * @return Level
   */
  public int getLevel() {
    return level;
  }

  /**
   * Set level of the leader
   * @param level Leader level
   */
  public void setLevel(final int level) {
    this.level = level;
  }

  /**
   * Get current experience for current level
   * @return Experience
   */
  public int getExperience() {
    return experience;
  }

  /**
   * Get amount of experience required for next level.
   * @return Required experience.
   */
  public int getRequiredExperience() {
    int result = getLevel() * 100;
    if (hasPerk(Perk.ACADEMIC)) {
      result = result / 2;
    }
    if (hasPerk(Perk.SLOW_LEARNER)) {
      result = result * 2;
    }
    return result;
  }
  /**
   * Set experience for current level
   * @param experience for current level
   */
  public void setExperience(final int experience) {
    this.experience = experience;
  }

  /**
   * Leader gains experience by certain amount. There might be modifiers for
   * this.
   * @param xp New experience gained.
   */
  public void addExperience(final int xp) {
    int multiplier = 1;
    int divider = 1;
    if (race.hasTrait(TraitIds.QUICK_LEARNERS)) {
      multiplier = 2;
    }
    if (race.hasTrait(TraitIds.SLOW_LEARNERS)) {
      divider = 2;
    }
    this.experience = this.experience + xp * multiplier / divider;
  }

  /**
   * Get leader space race
   * @return the race
   */
  public SpaceRace getRace() {
    return race;
  }

  /**
   * Set leader space race.
   * @param race the race to set
   */
  public void setRace(final SpaceRace race) {
    this.race = race;
  }

  /**
   * Get the leader gender.
   * @return the gender
   */
  public Gender getGender() {
    return gender;
  }

  /**
   * Set the leader gender. This will affect only for title that leader
   * receives.
   * @param gender the gender to set
   */
  public void setGender(final Gender gender) {
    this.gender = gender;
  }

  /**
   * Get Leader ingame description with all the details
   * except perks.
   * @return Description
   */
  public String getDescription() {
    StringBuilder builder = new StringBuilder();
    builder.append(getTitle());
    builder.append(" ");
    builder.append(getName());
    builder.append("\n");
    builder.append("Position: ");
    builder.append(getJob().getName());
    builder.append("\n");
    if (getJob() == Job.RULER) {
      builder.append("Ruler for ");
      builder.append(getTimeInJob());
      builder.append(" star years\n");
    }
    if (getJob() == Job.GOVERNOR) {
      builder.append("Governor for ");
      builder.append(getTimeInJob());
      builder.append(" star years");
      if (getTimeInJob() > 19) {
        builder.append("  (assignable)");
      }
      builder.append("\n");
    }
    if (getJob() == Job.COMMANDER) {
      builder.append("Commander for ");
      builder.append(getTimeInJob());
      builder.append(" star years");
      if (getTimeInJob() > 19) {
        builder.append("  (assignable)");
      }
      builder.append("\n");
    }
    if (getJob() == Job.PRISON) {
      builder.append("Prisoned for ");
      builder.append(getTimeInJob());
      builder.append(" star years\n");
    }
    builder.append("Military status: ");
    builder.append(getMilitaryRank().toString());
    builder.append("\n");
    builder.append("Age: ");
    builder.append(getAge());
    builder.append("\n");
    if (getParent() != null) {
      builder.append("Parent: ");
      builder.append(getParent().getTitle() + " " + getParent().getName());
      builder.append("\n");
    }
    builder.append("Gender: ");
    builder.append(getGender().toString());
    builder.append("\n");
    builder.append("Race: ");
    builder.append(getRace().getNameSingle());
    builder.append("\n");
    builder.append("Home world: ");
    builder.append(getHomeworld());
    builder.append("\n");
    builder.append("Level: ");
    builder.append(getLevel());
    builder.append("\n");
    builder.append("Experience: ");
    builder.append(getExperience());
    builder.append("/");
    builder.append(getRequiredExperience());
    builder.append("\n");
    return builder.toString();
  }
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Leader [name=");
    builder.append(name);
    builder.append(", homeworld=");
    builder.append(homeworld);
    builder.append(", job=");
    builder.append(job.getName());
    builder.append(", age=");
    builder.append(age);
    builder.append(", TimeInJob=");
    builder.append(timeInJob);
    builder.append(", level=");
    builder.append(level);
    builder.append(", experience=");
    builder.append(experience);
    builder.append(", militaryRank=");
    builder.append(militaryRank);
    builder.append(", gender=");
    builder.append(gender);
    builder.append(", race=");
    builder.append(race.getNameSingle());
    builder.append(", title=");
    builder.append(title);
    if (getParent() != null) {
      builder.append(", parent=");
      builder.append(parent.getName());
    }
    builder.append("]");
    return builder.toString();
  }

  /**
   * @return the job
   */
  public Job getJob() {
    return job;
  }

  /**
   * @param job the job to set
   */
  public void setJob(final Job job) {
    if (job != this.job) {
      setTimeInJob(0);
    }
    this.job = job;
  }

  /**
   * Assign Leader a job and title.
   * @param work Job to assign
   * @param realm Realm which leader belongs.
   */
  public void assignJob(final Job work, final PlayerInfo realm) {
    setJob(work);
    setTitle(LeaderUtility.createTitleForLeader(this, realm));
  }
  /**
   * Get the leader parent. This will be set
   * only for heirs. Regular leaders this will be null.
   * @return the parent or null.
   */
  public Leader getParent() {
    return parent;
  }

  /**
   * Set the leader's parent.
   * @param parent the parent to set
   */
  public void setParent(final Leader parent) {
    this.parent = parent;
  }

  /**
   * Get Leader's perk list
   * @return Unmodifiable Perk list.
   */
  public List<Perk> getPerkList() {
    return Collections.unmodifiableList(perkList);
  }

  /**
   * Check if leader has certain perk
   * @param perk Perk to look for
   * @return True if found
   */
  public boolean hasPerk(final Perk perk) {
    return perkList.contains(perk);
  }
  /**
   * Calculate leader score for certain job.
   * @param scoreForJob Job for scoring
   * @return Score
   */
  public int calculateLeaderScore(final Job scoreForJob) {
    int result = 0;
    for (Perk perk : perkList) {
      result = result + perk.getPerkScore(scoreForJob);
    }
    return result;
  }
  /**
   * Calculate generic leader score.
   * @return Score
   */
  public int calculateLeaderGenericScore() {
    int result = 0;
    result = result + calculateLeaderScore(Job.RULER);
    result = result + calculateLeaderScore(Job.GOVERNOR);
    result = result + calculateLeaderScore(Job.COMMANDER);
    return result;
  }
  /**
   * Adds new perk for leader. Checks if leader has perk before adding it.
   * @param perk New perk to add
   */
  public void addPerk(final Perk perk) {
    if (!hasPerk(perk)) {
      perkList.add(perk);
    }
  }
  /**
   * Get parent index for leader.
   * This is avaiable only if leader is created from reading saved game.
   * This index should be used to make actual parent avaiable.
   * @return Parent index.
   */
  public int getParentIndex() {
    return parentIndex;
  }

  /**
   * How long leader has been in current job.
   * @return the timeInJob
   */
  public int getTimeInJob() {
    return timeInJob;
  }

  /**
   * Set time in current job.
   * @param timeInJob the timeInJob to set
   */
  public void setTimeInJob(final int timeInJob) {
    this.timeInJob = timeInJob;
  }

  /**
   * Tries to estimate best suitetable job for leader.
   * Choices are Commander or governor. If leader is equakly fit
   * for those then unassigned is returned.
   * @return best suiteable job
   */
  public Job getMostSuitableJob() {
    int commander = 0;
    int governor = 0;
    for (Perk perk : perkList) {
      if (perk.isFleetCommanderPerk()) {
        commander++;
      }
      if (perk.isGovernorPerk()) {
        governor++;
      }
    }
    if (commander > governor) {
      return Job.COMMANDER;
    }
    if (commander < governor) {
      return Job.GOVERNOR;
    }
    return Job.UNASSIGNED;
  }

  /**
   * Remove Wealth perk from the list.
   */
  public void useWealth() {
    perkList.remove(Perk.WEALTHY);
  }

  /**
   * Remove corruption perk from the list.
   */
  public void removeCorruption() {
    perkList.remove(Perk.CORRUPTED);
  }

  /**
   * Get Stats for leader.
   * @return LeaderStats.
   */
  public LeaderStats getStats() {
    return stats;
  }
}
